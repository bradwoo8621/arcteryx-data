/**
 * 
 */
package com.github.nnest.arcteryx.data.routing;

/**
 * incorrect data source routing exception
 * 
 * @author brad.wu
 */
public class IncorrectDataSourceRoutingException extends RuntimeException {
	private static final long serialVersionUID = 7821954852576446667L;

	public IncorrectDataSourceRoutingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncorrectDataSourceRoutingException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectDataSourceRoutingException(String message) {
		super(message);
	}

	public IncorrectDataSourceRoutingException(Throwable cause) {
		super(cause);
	}
}
