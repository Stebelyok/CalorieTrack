package dev.steblev.calorietrack.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(@org.springframework.transaction.annotation.Transactional * *(..))")
    public void transactionalMethods() {}

    @Before("transactionalMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Запуск метода: {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "transactionalMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("Метод {} выполнен успешно с результатом: {}", joinPoint.getSignature(), result);
    }
}
