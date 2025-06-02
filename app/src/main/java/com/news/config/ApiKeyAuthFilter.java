package com.news.config;

import com.news.app.infrastructure.security.ApiKeyAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyAuthFilter.class);
    private final String apiKeyHeader = "X-API-Key";
    private final String apiKey = "WCexPQK8etFGDRVY8ZX54pFZ2eNAbu5mCJX79Qttg63Bh2c5ZQpytVOP2YGbIcCZ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String apiKeyHeaderValue = request.getHeader(apiKeyHeader);
            
            if (apiKeyHeaderValue == null) {
                logger.warn("API Key missing in request to: {}", request.getRequestURI());
                throw new ApiKeyAuthenticationException("API Key is required");
            }

            if (!apiKeyHeaderValue.equals(apiKey)) {
                logger.warn("Invalid API Key provided in request to: {}", request.getRequestURI());
                throw new ApiKeyAuthenticationException("Invalid API Key");
            }

            logger.debug("Valid API Key provided for request to: {}", request.getRequestURI());
            ApiKeyAuthenticationToken auth = new ApiKeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            filterChain.doFilter(request, response);
        } catch (ApiKeyAuthenticationException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(String.format(
                "{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"%s\"}", 
                e.getMessage()
            ));
        }
    }
} 