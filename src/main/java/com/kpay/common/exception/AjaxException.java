package com.kpay.common.exception;

public class AjaxException extends HttpResponseException {

	private static final long serialVersionUID = 6319258594588558914L;

	public AjaxException() {
		super();
	}

	public AjaxException(Throwable e) {
		super(e);
	}

	public AjaxException(String errorMsg) {
		super(errorMsg);
	}

	public AjaxException(String errorMsg, Throwable e) {
		super(errorMsg, e);
	}

	public AjaxException(int httpStatusCode, String errorCode, String errorMsg) {
		super(httpStatusCode, errorCode, errorMsg);
	}

	public AjaxException(String errorCode, String errorMsg, Throwable e) {
		super(errorCode, errorMsg, e);
	}
}
