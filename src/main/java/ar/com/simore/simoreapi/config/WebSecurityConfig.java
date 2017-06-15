package ar.com.simore.simoreapi.config;

import ar.com.simore.simoreapi.entities.SimpleUserDetails;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.xauth.XAuthTokenConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Configuration
@Order
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/conductores/**").hasAnyRole(CustomUserDetailsService.ROLE_CONDUCTOR, CustomUserDetailsService.ROLE_ADMINISTRADOR, CustomUserDetailsService.ROLE_OPERADOR);
        http.authorizeRequests().antMatchers("/operadores/**").hasAnyRole(CustomUserDetailsService.ROLE_ADMINISTRADOR, CustomUserDetailsService.ROLE_OPERADOR);
        http.authorizeRequests().antMatchers("/administradores/**").hasRole(CustomUserDetailsService.ROLE_ADMINISTRADOR);
        http.authorizeRequests().antMatchers("/clientes/**").hasAnyRole(CustomUserDetailsService.ROLE_ADMINISTRADOR, CustomUserDetailsService.ROLE_OPERADOR);


        SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new XAuthTokenConfigurer(
                userDetailsServiceBean());
        http.apply(securityConfigurerAdapter);
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
    UserRepository userRepository;

    public static final String ROLE_ADMINISTRADOR = "ADMINISTRADOR";
    public static final String ROLE_OPERADOR = "OPERADOR";
    public static final String ROLE_CONDUCTOR = "CONDUCTOR";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if (user != null) {
            return new SimpleUserDetails(user.getUserName(), user.getPassword(), user.getRole().toUpperCase());
        } else {
            return null;
        }
    }
}
