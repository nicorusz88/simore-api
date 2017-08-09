package ar.com.simore.simoreapi;


import ar.com.simore.simoreapi.entities.Role;
import ar.com.simore.simoreapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
                ar.com.simore.simoreapi.entities.User user = userRepository.findByUserName(userName);


                if (user != null) {
                    List<Role> roles1 = user.getRoles();
                    String[] roles = new String[roles1.size()];
                    for (int i = 0; i < roles1.size(); i++) {
                        roles[i] = roles1.get(i).getName();
                    }
                    return new User(user.getUserName(), user.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(roles));
                } else {
                    throw new UsernameNotFoundException("Could not find the user '"
                            + userName + "'");
                }
            }

        };
    }
}