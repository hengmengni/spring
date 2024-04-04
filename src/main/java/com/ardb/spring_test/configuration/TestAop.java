package com.ardb.spring_test.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAop {

    Logger logger = LoggerFactory.getLogger(TestAop.class);

    @Pointcut("execution(* com.ardb.spring_test.controller.*.*(..))")

    public void getMethodName() {
    }

    @Before("getMethodName()")
    public void logBeforeGetName(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        logger.info(String.format("Get Method Name: %s", method));
    }

}
