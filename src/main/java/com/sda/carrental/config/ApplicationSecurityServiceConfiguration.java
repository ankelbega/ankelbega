package com.sda.carrental.config;

import com.sda.carrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityServiceConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/register/user/{roleId}").permitAll()
                .antMatchers("/cars").permitAll()
                .antMatchers("/branches").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/customers",
                        "/count/**",
                        "/car/maxMileage",
                        "/all/reservation").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/rental/{rentalId}",
                        "/car/{id}",
                        "/car-status/{carStatusId}",
                        "/assign/**",
                        "/update/revenue/{revenueId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/branch/{branchId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/branch",
                        "/refund",
                        "/loan").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/reservation").hasRole("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll()
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}


