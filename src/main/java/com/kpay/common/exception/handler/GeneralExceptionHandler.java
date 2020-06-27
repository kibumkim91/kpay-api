package com.kpay.common.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kpay.common.Constants.ResponseCode;
import com.kpay.common.domain.Response;
import com.kpay.common.exception.GeneralException;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GeneralExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

	@ExceptionHandler(value = { GeneralException.class })
	protected ResponseEntity<Response> handleGeneralException(HttpServletRequest request, GeneralException ex) {

		logger.error("GeneralException", ex);
		Response response = new Response();
		response.setCode(ex.getErrorCode());
		response.setMessage(ex.getMessage());
		
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception {
		
		logger.error("GeneralException", ex);
		Response response = new Response();
		response.setCode(ResponseCode.FAIL);
		response.setMessage(ex.getMessage());
		
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

}