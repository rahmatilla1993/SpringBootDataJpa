package com.example.springbootdatajpa.logging;

import com.example.springbootdatajpa.exception.NotFoundException;
import com.example.springbootdatajpa.utils.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class Logger {

    private final SessionUser sessionUser;

    @Autowired
    public Logger(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    @Pointcut("@annotation(com.example.springbootdatajpa.logging.Logging)")
    public void loggerAnnotation() {
    }

    @AfterReturning("loggerAnnotation()")
    public void logger(JoinPoint joinPoint) {
        String requestURI = servletRequest().getRequestURI();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String username = sessionUser.getUsername()
                .orElseThrow(() -> new NotFoundException("User not found"));
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(Logging.class)) {
            Logging logging = method.getAnnotation(Logging.class);
            String message = logging.message();
            String logMsg = "User with {} username send a request to {} endpoint and done action {}";
            log.info(logMsg, username, requestURI, message);
        }
    }

    private HttpServletRequest servletRequest() {
        return ((ServletRequestAttributes) Objects
                .requireNonNull(
                        RequestContextHolder.getRequestAttributes()))
                .getRequest();
    }
}
