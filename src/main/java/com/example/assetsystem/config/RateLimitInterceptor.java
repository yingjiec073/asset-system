package com.example.assetsystem.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private final int requests;
    private final long windowMs;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitInterceptor(@Value("${rate-limit.requests}") int requests,
                                @Value("${rate-limit.window-seconds}") int windowSeconds) {
        this.requests = requests;
        this.windowMs = windowSeconds * 1000L;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, k -> new Bucket());
        long now = System.currentTimeMillis();
        synchronized (bucket) {
            if (now - bucket.windowStart > windowMs) {
                bucket.windowStart = now;
                bucket.count = 0;
            }
            bucket.count++;
            if (bucket.count > requests) {
                response.setStatus(429);
                response.getWriter().write("{\"code\":429,\"message\":\"too many requests\",\"data\":null}");
                return false;
            }
        }
        return true;
    }

    private static class Bucket { long windowStart = System.currentTimeMillis(); int count = 0; }
}
