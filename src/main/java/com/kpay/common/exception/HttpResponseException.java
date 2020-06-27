package com.kpay.common.exception;

public class HttpResponseException extends GeneralException {

	private static final long serialVersionUID = 6319258594588558914L;
	protected int httpStatusCode;

	public HttpResponseException() {
		super();
	}

	public HttpResponseException(Throwable e) {
		super(e);
	}

	public HttpResponseException(String errorMsg) {
		super(errorMsg);
	}

	public HttpResponseException(String errorMsg, Throwable e) {
		super(errorMsg, e);
	}

	public HttpResponseException(int httpStatusCode, String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
		this.httpStatusCode = httpStatusCode;
	}

	public HttpResponseException(String errorCode, String errorMsg, Throwable e) {
		super(errorCode, errorMsg, e);
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
}
