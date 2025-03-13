package am.registration.system.demo.security.token.jwt;

import am.registration.system.demo.security.token.validation.JwtTokenValidator;
import am.registration.system.demo.service.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * JWT Authentication Filter to validatePasswordResetRequest incoming JWT tokens in HTTP requests.
 * This filter is triggered for every incoming request and checks the "Authorization" header
 * for a Bearer token. If a valid token is found, it sets the authentication context.
 * *
 * * Dependencies:
 * - JwtTokenValidator: Validates the JWT token and extracts the username.
 * - CustomUserDetails: Loads user details by username.
 * - HandlerExceptionResolver: Handles exceptions that occur during filtering.
 * *
 * Author: Artyom Aroyan
 * Date: 25.02.25
 * Time: 00:00:40
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final CustomUserDetails customUserDetails;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Filters incoming HTTP requests to validatePasswordResetRequest JWT tokens.
     * Extracts the token from the "Authorization" header and verifies its validity.
     * If valid, set the authentication context with the corresponding user details.
     *
     * @param request     the HTTP request to be processed
     * @param response    the HTTP response to be sent
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs during request processing
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String token = authorizationHeader.substring(7);
            final String username = jwtTokenValidator.extractUsername(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails userDetails = customUserDetails.loadUserByUsername(username);

                if (jwtTokenValidator.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}