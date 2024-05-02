package com.userproduct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public UserDetailsService UserDetailsService (PasswordEncoder encoder) {
//        UserDetails admin = User.withUsername("admin")
//                .password (encoder.encode("prakash"))
//                .roles ("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password (encoder.encode("saiprakash"))
//                .roles ("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("sai"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("prakash"))
                .roles("ADMIN");
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/post").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/getProduct/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT,"/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/products/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin()
//                .and()zzz
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .cors().disable().csrf().disable();
    // Disable CSRF protection (if needed)
   }
  @Bean
    public DisableEncodeUrlFilter disableEncodeUrlFilter()
  {
      return  new DisableEncodeUrlFilter();
  }



}
