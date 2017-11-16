package ar.com.simore.simoreapi;

import ar.com.simore.simoreapi.entities.Role;
import ar.com.simore.simoreapi.entities.SimpleUserDetails;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.xauth.XAuthTokenConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@PropertySource("file:${user.home}/opt/simore/application.properties")
@EnableOAuth2Client
@Order
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Value("${fitbit.client.clientId}")
    private String fitbitClientId;

    @Value("${fitbit.client.clientSecret}")
    private String fitbitClientSecret;

    @Value("${fitbit.client.accessTokenUri}")
    private String fitbitAccessTokenUri;

    @Value("${fitbit.client.userAuthorizationUri}")
    private String fitbitUserAuthorizationUri;

    @Value("${fitbit.client.tokenName}")
    private String fitbitTokenName;

    @Value("${fitbit.client.authenticationScheme}")
    private String fitbitAuthenticationScheme;

    @Value("${fitbit.client.clientAuthenticationScheme}")
    private String fitbitClientAuthenticationScheme;

    @Value("${fitbit.client.scope}")
    private String fitbitScope;
    @Value("${fitbit.client.preEstablishedRedirectUri}")
    private String fitbitPreEstablishedRedirectUri;


    @Value("${fitbit.resource.userInfoUri}")
    private String fitbitUserInfoUri;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/**").authenticated().
                and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);


        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(
                userDetailsServiceBean());
        http.apply(securityConfigurerAdapter);
    }

    /**
     * This filter is created in new method where we use the OAuth2ClientContext
     *
     * @return
     */
    private Filter ssoFilter() {

        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter fitbitFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/fitbit");
        OAuth2RestTemplate fitbitTemplate = new OAuth2RestTemplate(fitbit(), oauth2ClientContext);
        fitbitFilter.setRestTemplate(fitbitTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(fitbitResource().getUserInfoUri(), fitbit().getClientId());
        tokenServices.setRestTemplate(fitbitTemplate);
        fitbitFilter.setTokenServices(tokenServices);
        filters.add(fitbitFilter);

        OAuth2ClientAuthenticationProcessingFilter withingsFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/withings");
        OAuth2RestTemplate withingsTemplate = new OAuth2RestTemplate(withings(), oauth2ClientContext);
        withingsFilter.setRestTemplate(withingsTemplate);
        tokenServices = new UserInfoTokenServices(withingsResource().getUserInfoUri(), withings().getClientId());
        tokenServices.setRestTemplate(withingsTemplate);
        withingsFilter.setTokenServices(tokenServices);
        filters.add(withingsFilter);
        filter.setFilters(filters);
        return filter;
    }

    /**
     * The filter also needs to know about the client registration with Fitbit
     *
     * @return
     */
    @Bean
    public AuthorizationCodeResourceDetails fitbit() {
        final AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setClientId(fitbitClientId);
        authorizationCodeResourceDetails.setClientSecret(fitbitClientSecret);
        authorizationCodeResourceDetails.setAccessTokenUri(fitbitAccessTokenUri);
        authorizationCodeResourceDetails.setUserAuthorizationUri(fitbitUserAuthorizationUri);
        authorizationCodeResourceDetails.setTokenName(fitbitTokenName);
        authorizationCodeResourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
        authorizationCodeResourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        authorizationCodeResourceDetails.setPreEstablishedRedirectUri(fitbitPreEstablishedRedirectUri);
        String[] scopeString = fitbitScope.split(",");
        authorizationCodeResourceDetails.setScope(Arrays.asList(scopeString));
        return authorizationCodeResourceDetails;
    }


    /**
     * To complete the authentication it needs to know where the user info endpoint is in Fitbit
     *
     * @return
     */
    @Bean
    public ResourceServerProperties fitbitResource() {
        final ResourceServerProperties resourceServerProperties = new ResourceServerProperties();
        resourceServerProperties.setUserInfoUri(fitbitUserInfoUri);
        return resourceServerProperties;
    }

    /**
     * The filter also needs to know about the client registration with Withings
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("withings.client")
    public AuthorizationCodeResourceDetails withings() {
        return new AuthorizationCodeResourceDetails();
    }


    /**
     * To complete the authentication it needs to know where the user info endpoint is in Withings
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("withings.resource")
    public ResourceServerProperties withingsResource() {
        return new ResourceServerProperties();
    }


    /**
     * Handling the Redirects
     * The last change we need to make is to explicitly support the redirects from our app to Facebook.
     * This is handled in Spring OAuth2 with a servlet Filter, and the filter is already available in the application context because we used @EnableOAuth2Client.
     * All that is needed is to wire the filter up so that it gets called in the right order in our Spring Boot application.
     * To do that we need a FilterRegistrationBean
     *
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(customUserDetailsService());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

}

@Component
class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if (user != null) {
            List<Role> roles1 = user.getRoles();
            String[] roles = new String[10];
            for (int i = 0; i < roles1.size(); i++) {
                roles[i] = roles1.get(i).getName();
            }
            return new SimpleUserDetails(user.getUserName(), user.getPassword(), roles);
        } else {
            return null;
        }
    }
}
