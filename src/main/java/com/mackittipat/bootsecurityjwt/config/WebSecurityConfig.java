package com.mackittipat.bootsecurityjwt.config;

import com.mackittipat.bootsecurityjwt.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable csrf to allow HTTP POST method
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                // Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mac").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }

    /*
    * This allow @Autowired private AuthenticationManager authenticationManager;
    */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
