package com.kpay.common.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

	private Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

	@Around("execution(* com.kpay.moneyspraying.service..*Service.*(..))")
	public Object ctrlAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = signature.getMethod().getName();
		
		logger.info("Service Start... {}.{}", className, methodName);

        return joinPoint.proceed();
	}
}