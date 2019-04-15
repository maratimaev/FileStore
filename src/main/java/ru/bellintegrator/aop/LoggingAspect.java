package ru.bellintegrator.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Аспект логирования
 */
@Component
@Aspect
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    /**
     * Точка среза сервисов
     */
    @Pointcut("execution(public * ru.bellintegrator.service.*.*(..))")
    public void processingServiceMethods() {
    }

    /**
     * Точка среза контроллеров
     */
    @Pointcut("execution(public * ru.bellintegrator.controller.*.*(..))")
    public void processingControllerMethods() {
    }

    /** Вызов совета до вызова метода
     * @param jp точка выполнения программы
     */
    @Before("processingServiceMethods() || processingControllerMethods()")
    public void logMethodCall(JoinPoint jp) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        logger.info("calling method: \"" + methodName + "\" in class \"" + className + "\" with arguments: " + Arrays.toString(jp.getArgs()));
    }

    /** Вызов совета после нормального завершения метода
     * @param jp точка выполнения программы
     * @param result результат выполнения метода
     */
    @AfterReturning(pointcut = "processingServiceMethods() || processingControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint jp, Object result) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        if(result != null) {
            logger.info("result of method \"" + methodName + "\" in class \"" + className + "\": " + result.toString() + "\n");
        }
    }

    /** Логирование времени выполнения метода
     * @param jp точка выполнения программы
     * @return продолжение выполнения метода
     * @throws Throwable исключение
     */
    @Around("processingServiceMethods() || processingControllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint jp) throws Throwable {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("method: \"" + methodName + "\" in class \"" + className + "\" executed for " + executionTime + " ms");
        return proceed;
    }
}
