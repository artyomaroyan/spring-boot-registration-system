package am.registration.system.demo.configuration.application;

import am.registration.system.demo.security.password.Argon2Hashing;
import am.registration.system.demo.security.token.jwt.JwtAuthenticationFilter;
import am.registration.system.demo.service.user.CustomUserDetails;
import am.registration.system.demo.util.CustomPermissionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Security configuration class that sets up the authentication and authorization mechanisms
 * using JWT and Argon2 password hashing. It also configures CORS and CSRF settings for the application.
 * <p>
 * This configuration ensures that public endpoints are accessible without authentication,
 * while protected endpoints require proper authentication and authorization.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 23:57:40
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] PUBLIC_URLS = {
            "/webjars/**",
            "/v2/api-docs",
            "/v3/api-docs/",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/security"
    };

    private final Argon2Hashing argon2Hashing;
    private final CustomUserDetails customUserDetails;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomPermissionEvaluator customPermissionEvaluator;

    /**
     * Configures the authentication manager bean.
     *
     * @param configuration the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configures the authentication provider with Argon2 password hashing and custom user details service.
     *
     * @return the authentication provider
     */
    @Bean
    protected DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetails);
        provider.setPasswordEncoder(argon2Hashing);
        return provider;
    }

    /**
     * Configures CORS settings to allow specific origins, methods, and headers.
     *
     * @return the CORS configuration source
     */
    @Bean
    protected CorsConfigurationSource configurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("authorization", "content-type", "bearer"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures the method security expression handler with a custom permission evaluator.
     *
     * @return the method security expression handler
     */
    @Bean
    protected MethodSecurityExpressionHandler expressionHandler() {
        final DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(customPermissionEvaluator);
        return handler;
    }

    /**
     * Configures the security filter chain to manage HTTP security settings.
     *
     * @param http the HttpSecurity object
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        configureHttpBasic(http);
        configureCsrf(http);
        configureAuthentication(http);
        configureSessionManagement(http);
        configureJwtAuthentication(http);
        return http.build();
    }

    /**
     * Configures HTTP basic authentication.
     *
     * @param http the HttpSecurity object
     * @throws Exception if an error occurs during configuration
     */
    private void configureHttpBasic(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
    }

    /**
     * Disables CSRF protection for specific public endpoints to allow unauthenticated access.
     *
     * @param http the HttpSecurity object
     * @throws Exception if an error occurs during configuration
     */
    private void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers(PUBLIC_URLS)
                .ignoringRequestMatchers("/api/v1/user/account/register/**")
                .ignoringRequestMatchers("/api/v1/user/account/login/**")
                .ignoringRequestMatchers("/api/v1/user/account/verify-email/**")
                .ignoringRequestMatchers("/api/v1/user/password-reset/send-email")
                .ignoringRequestMatchers("/api/v1/user/password-reset/reset/**")
                .ignoringRequestMatchers("/swagger-ui/**")
                .ignoringRequestMatchers("/v3/api-docs/**")
        );
    }

    /**
     * Configures authentication settings, allowing public URLs and securing other endpoints.
     *
     * @param http the HttpSecurity object
     * @throws Exception if an error occurs during configuration
     */
    private void configureAuthentication(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_URLS)
                            .permitAll()
                        .requestMatchers("/api/v1/user/account/register/**",
                                "/api/v1/user/account/login",
                                "/api/v1/user/account/verify-email/**",
                                "/api/v1/user/password-reset/send-email",
                                "/api/v1/user/password-reset/reset/**")
                        .permitAll()
                            .anyRequest()
                        .authenticated());
    }

    /**
     * Configures session management to be stateless, as JWT authentication is used.
     *
     * @param http the HttpSecurity object
     * @throws Exception if an error occurs during configuration
     */
    private void configureSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
    }

    /**
     * Adds a JWT authentication filter to the security filter chain.
     *
     * @param http the HttpSecurity object
     */
    private void configureJwtAuthentication(HttpSecurity http) {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}