package com.stargazer.springapplicationtemplate.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

public class LocaleChangeInterceptor implements HandlerInterceptor {

    private String paramName = "Accept-Language";
    private LocaleResolver localeResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String newLocale = request.getHeader(paramName);
        if (newLocale != null && !newLocale.isEmpty()) {
            Locale targetLocale = parseLocale(newLocale);
            if (targetLocale != null) {
                LocaleResolver localeResolver = getLocaleResolver(request);
                if (localeResolver != null) {
                    localeResolver.setLocale(request, response, targetLocale);
                }
            }
        }
        return true;
    }

    private Locale parseLocale(String localeString) {
        String[] parts = localeString.split("-");
        if (parts.length >= 2) {
            return new Locale(parts[0], parts[1].toUpperCase());
        } else if (parts.length == 1) {
            return new Locale(parts[0].toLowerCase());
        }
        return null;
    }

    private LocaleResolver getLocaleResolver(HttpServletRequest request) {
        if (this.localeResolver == null) {
            this.localeResolver = RequestContextUtils.getLocaleResolver(request);
        }
        return this.localeResolver;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}