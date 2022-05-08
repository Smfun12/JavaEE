package kma.topic8.config;

import kma.topic8.model.Role;
import kma.topic8.repository.UserRepo;
import kma.topic8.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@AllArgsConstructor
public class WebConfig extends WebSecurityConfigurerAdapter {

    UserRepo repo;

    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(repo);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/book").hasAuthority(Role.USER.name())
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }
}
