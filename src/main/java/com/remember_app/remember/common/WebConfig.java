package com.remember_app.remember.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    private final RsaEncryptInterceptor rsaEncryptInterceptor;

    public WebConfig(RsaEncryptInterceptor rsaEncryptInterceptor) {
        this.rsaEncryptInterceptor = rsaEncryptInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EncryptResponseInterceptor(rsaEncryptInterceptor))
                .addPathPatterns("/users/**");
    }

    public static class EncryptResponseInterceptor implements HandlerInterceptor {
        private final RsaEncryptInterceptor target;

        public EncryptResponseInterceptor(RsaEncryptInterceptor target) {
            this.target = target;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            return target.preHandle(request, response, handler);
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                               org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                    Exception ex) throws Exception {
            target.afterCompletion(request, response, handler, ex);
        }
    }
}