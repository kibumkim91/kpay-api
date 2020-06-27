package com.kpay.common.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import com.kpay.common.Constants.ResponseCode;
import com.kpay.common.exception.GeneralException;

@Aspect
@Configuration
public class ControllerAdvice {

	private Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

	@Around("execution(* com.kpay.*.controller..*Controller.*(..))")
	public Object ctrlAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = signature.getMethod().getName();
		
		logger.info("Controller Start... {}.{}", className, methodName);
		
		StopWatch stopWatch = new StopWatch();
		Object result = null;
		try {
			stopWatch.start(joinPoint.getSignature().getName());
			result = joinPoint.proceed();
		} catch(Exception e) {
			throw new GeneralException(ResponseCode.FAIL, e.getLocalizedMessage(), e);
		} finally {
			stopWatch.stop();
			logger.info(stopWatch.prettyPrint());
		}
		
		return result;
	}
	
}