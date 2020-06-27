package com.kpay.common.exception;

public class InvalidParameterException extends GeneralException {

	private static final long serialVersionUID = -8759538977806214298L;

	public InvalidParameterException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

}
