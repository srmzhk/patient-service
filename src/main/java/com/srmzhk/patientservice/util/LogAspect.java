package com.srmzhk.patientservice.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Around("execution(* com.srmzhk.patientservice..*(..)) " +
            "&& !execution(* com.srmzhk.patientservice.repository..*(..))")
    // logging requests and response for each controller in com.srmzhk.patientservice
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        // get className and methodName for logging
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Class: {} | Method: {} | Request: {}", className, methodName, Arrays.toString(args));

        // process method
        Object result = joinPoint.proceed();

        log.info("Class: {} | Method: {} | Response: {}", className, methodName, result);
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.srmzhk.patientservice..*(..)) " +
            "&& !execution(* com.srmzhk.patientservice.config..*(..))", throwing = "exception")
    // logging exceptions for com.srmzhk.patientservice
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        log.error("Exception occurred in Class: {} | Method: {} | Exception: {}",
                className, methodName, exception.getMessage(), exception);
    }

    @Before("execution(* com.srmzhk.patientservice.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName());

        // get args for method
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            StringBuilder sb = new StringBuilder("Arguments: ");
            for (Object arg : args) {
                sb.append(arg).append(" ");
            }
            log.info(sb.toString());
        }
    }

    @After("execution(* com.srmzhk.patientservice.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Exiting method: {}", joinPoint.getSignature().getName());
    }
}
