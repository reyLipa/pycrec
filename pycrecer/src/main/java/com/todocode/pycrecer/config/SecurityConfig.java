package com.todocode.pycrecer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/asistentes").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login").permitAll()
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                )
//                .build();
        http.authorizeHttpRequests().requestMatchers("/asistentes/formulario").permitAll()
                .requestMatchers("/asistentes").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/asistentes/buscar").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "asistentes").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "asistentes/{id}").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder){
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder().encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("user2")
                .password(bCryptPasswordEncoder.encode("user2"))
                .roles("USER2", "ADMIN")
                .build());
        return manager;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
