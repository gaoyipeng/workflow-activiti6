package com.sxdx.common.exception.base;

import java.io.Serializable;

/**
 * 
 * @ClassName: CommonException
 * @Description: 自定义通用异常类
 */
public class CommonException extends RuntimeException implements Serializable {


	private static final long serialVersionUID = -8733329279060146320L;

	public CommonException() {
		super();
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

}
