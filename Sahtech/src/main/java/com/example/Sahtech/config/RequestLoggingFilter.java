package com.example.Sahtech.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (request.getRequestURI().contains("/auth/register")) {
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            
            // Call the rest of the filter chain
            filterChain.doFilter(wrappedRequest, response);
            
            // After the request is processed, the content will be cached
            byte[] content = wrappedRequest.getContentAsByteArray();
            if (content.length > 0) {
                String requestBody = new String(content, StandardCharsets.UTF_8);
                System.out.println("DEBUG: Raw register request body: " + requestBody);
            }
        } else {
            // For other requests, just continue the chain
            filterChain.doFilter(request, response);
        }
    }
} 