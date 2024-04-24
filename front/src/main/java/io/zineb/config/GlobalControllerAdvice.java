package io.zineb.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("currentUri")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getRequestURI();
    }
}

