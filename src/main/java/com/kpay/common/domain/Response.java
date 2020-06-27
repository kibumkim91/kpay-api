package com.kpay.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * <pre>
 * JSON response 메세지 포멧
 * 
 * 일반적인 응답 예시
 * {   
 *     "code" : 'SUCCESS',
 *     "result" : {
 *     		...
 *     }
 * }
 * 
 * 오류 응답 예시
 * {
 *     "code" : 'FAIL',
 *     "message": "UnsupportedMediaType",
 * }
 * </pre>
 */
@JsonInclude(Include.NON_NULL)
public class Response {

	/** The code. */
	private String code;

	/** The message. */
	private String message;

	/** The data. */
	@JsonUnwrapped
	private Object result;

	public Response() {
		super();
	}

	public Response(String code) {
		super();
		this.code = code;
	}

	public Response(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Response(String code, Object result) {
		super();
		this.code = code;
		this.result = result;
	}
	
	public Response(String code, String message, Object result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
