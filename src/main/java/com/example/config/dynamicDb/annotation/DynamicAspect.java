package com.example.config.dynamicDb.annotation;

import com.example.config.dynamicDb.DbThreadLocalContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * PROJECT:chart
 * PACkAGE:com.example.config.dynamicDb.annotation
 * Date:2023/11/27 17:34
 * EMAIL:
 *
 * @author BaiYiChen
 */
@Aspect
@Component
@Slf4j
public class DynamicAspect {
    @Pointcut("@annotation(com.example.config.dynamicDb.annotation.DBUSE)")
    public void pointCut() {
    }

    ;

    @Around("pointCut()")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) {
        try {
            final Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            if (method.isAnnotationPresent(DBUSE.class)) {
                final String value = method.getDeclaredAnnotation(DBUSE.class).value();
                DbThreadLocalContextHolder.setDbUse(value);
                // log.info(method.getName() + "方法：切换到{}数据库", value);
            }
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            log.error(e.getMessage());
        } finally {
            DbThreadLocalContextHolder.poll();
        }
        return pjp.getTarget();
    }
}
