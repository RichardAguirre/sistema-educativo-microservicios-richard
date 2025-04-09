package com.educativo.asignaturas.config;

import com.educativo.asignaturas.security.JwtAuthenticationFilter;
import com.educativo.asignaturas.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/asignaturas").permitAll() // Permitir listar asignaturas públicamente
                .requestMatchers("/asignaturas/{id}").permitAll() // Permitir ver detalles de asignaturas
                .requestMatchers("/dashboard/**").permitAll() // Permitir acceso al dashboard
                .requestMatchers("/asignaturas/crear").hasRole("ADMIN") // Solo admin puede crear
                .requestMatchers("/actuator/**").permitAll() // Para monitoreo de salud
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}