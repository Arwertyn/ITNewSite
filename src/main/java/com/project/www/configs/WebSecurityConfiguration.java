package com.project.www.configs;


import com.project.www.services.SecUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecUserDetailsService userDetailsService;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);
        http.authorizeHttpRequests((authz)-> authz
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/myPosts").authenticated()
                .requestMatchers("/createPost").authenticated()
                .requestMatchers("/post/**").authenticated()
                .requestMatchers("/registration").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/forgotPassword/**").permitAll()
                .requestMatchers("/updatePassword/**").permitAll()
                .requestMatchers("/ASU_EXPRESS").permitAll()
                .requestMatchers("/about/**").permitAll()
                .requestMatchers("/img/**").permitAll()
                .requestMatchers("/fonts/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**", "/css/**").permitAll()
                        .requestMatchers("/addComment").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN").anyRequest().authenticated())

                .formLogin((form)-> form
                        .loginPage("/login").failureUrl("/login?error=true")
                        .defaultSuccessUrl("/",true)
                        .usernameParameter("user_name")
                        .passwordParameter("password"))
                        .logout((logout) -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")

                        ).exceptionHandling((exp)-> exp.accessDeniedPage("/access-denied"));

        http.requestCache((cache) -> cache.requestCache(requestCache));
        return http.build();
    }



}
