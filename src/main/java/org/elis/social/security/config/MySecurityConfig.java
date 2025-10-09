package org.elis.social.security.config;

import lombok.RequiredArgsConstructor;
import org.elis.social.model.Ruolo;
import org.elis.social.security.filter.MyAuthFilter;
import org.elis.social.security.filter.MyCorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MySecurityConfig {
    private final MyAuthFilter myAuthFilter;
    private final MyCorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity securityConfigurer) throws Exception{
        securityConfigurer.csrf(t->t.disable());
        securityConfigurer
                .sessionManagement(t->t
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        securityConfigurer.authorizeHttpRequests(t->{
            t.requestMatchers("/all/**").permitAll();
//            t.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
            t.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
            t.requestMatchers("/base/**").authenticated();
            t.requestMatchers("/moderator/**").hasAnyRole(Ruolo.MODERATOR.toString(),Ruolo.ADMIN.toString());
            t.requestMatchers("/admin/**").hasRole(Ruolo.ADMIN.toString());
            t.anyRequest().permitAll();
        });
        securityConfigurer.addFilterAfter(corsFilter,UsernamePasswordAuthenticationFilter.class);
        securityConfigurer.addFilterBefore(myAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return securityConfigurer.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
