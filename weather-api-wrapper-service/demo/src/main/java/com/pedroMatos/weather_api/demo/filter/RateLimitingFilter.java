package com.pedroMatos.weather_api.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimitingFilter implements Filter {

    private record Counter(Integer count, Instant timeStarted){}
    private final ConcurrentHashMap<String, Counter> cache = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String ip = servletRequest.getRemoteAddr();
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if(!cache.containsKey(ip)){
            cache.put(ip,new Counter(1, Instant.now()));
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            Counter counter = cache.get(ip);
            Instant now = Instant.now();
            Duration duration = Duration.between(counter.timeStarted(),now);
            if (duration.toMinutes() > 1) {
                cache.put(ip,new Counter(1, Instant.now()));
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else if (duration.toMinutes() < 1 && counter.count() >= 10){
                httpResponse.setStatus(429);
                httpResponse.setContentType("text/plain;charset=UTF-8");
                httpResponse.getWriter().write("Too many request");
            } else {
                cache.put(ip,new Counter(counter.count() + 1, Instant.now()));
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
