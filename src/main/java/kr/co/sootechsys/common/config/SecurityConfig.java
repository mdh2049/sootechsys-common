package kr.co.sootechsys.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import kr.co.sootechsys.common.jwt.JwtAccessDeniedHandler;
import kr.co.sootechsys.common.jwt.JwtAuthenticationEntryPoint;
import kr.co.sootechsys.common.jwt.TokenProvider;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico"));
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 구현전이므로 모든 요청 허용
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                    .anyRequest().permitAll()); 
        return http.build();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
    //         .csrf(AbstractHttpConfigurer::disable)

    //         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
    //         .exceptionHandling(exceptionHandling -> exceptionHandling
    //             .accessDeniedHandler(jwtAccessDeniedHandler)
    //             .authenticationEntryPoint(jwtAuthenticationEntryPoint)
    //         )

    //         .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
    //             .requestMatchers("/api/hello", "/api/authenticate", "/api/signup").permitAll()
    //             .requestMatchers(PathRequest.toH2Console()).permitAll()
    //             .anyRequest().authenticated()
    //         )

    //         // 세션을 사용하지 않기 때문에 STATELESS로 설정
    //         .sessionManagement(sessionManagement ->
    //             sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //         )

    //         // enable h2-console
    //         .headers(headers ->
    //             headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
    //         )

    //         .with(new JwtSecurityConfig(tokenProvider), customizer -> {});
    //     return http.build();
    // }
}
