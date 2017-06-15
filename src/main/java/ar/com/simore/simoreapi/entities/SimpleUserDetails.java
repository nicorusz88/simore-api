package ar.com.simore.simoreapi.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleUserDetails implements UserDetails {

        private String userName;
        private String password;
        private boolean enabled = true;
        private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        public SimpleUserDetails(String userName, String pw, String... extraRoles) {
            this.userName = userName;
            this.password = pw;

            // setup roles
            Set<String> roles = new HashSet<String>();
            roles.addAll(Arrays.<String>asList(null == extraRoles ? new String[0] : extraRoles));

            // export them as part of authorities
            for (String r : roles) {
                authorities.add(new SimpleGrantedAuthority(role(r)));
            }

        }

        public String toString() {
            return "{enabled:" + isEnabled() + ", userName:'" + getUsername() + "', password:'" + getPassword() + "'}";
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.enabled;
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.enabled;
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.enabled;
        }

        @Override
        public String getUsername() {
            return this.userName;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        private String role(String i) {
            return "ROLE_" + i;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authorities;
        }
    }