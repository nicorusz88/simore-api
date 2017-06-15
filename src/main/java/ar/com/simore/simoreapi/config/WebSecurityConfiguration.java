package ar.com.simore.simoreapi.config;


import ar.com.simore.simoreapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
                ar.com.simore.simoreapi.entities.User user = userRepository.findByUserName(userName);


                if (user != null) {
                    return new User(user.getUserName(), user.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(user.getRole().toUpperCase()));
                } else {
                    throw new UsernameNotFoundException("Could not find the user '"
                            + userName + "'");
                }
            }

        };
    }
}