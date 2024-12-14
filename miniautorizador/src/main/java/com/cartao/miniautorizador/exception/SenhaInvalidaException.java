package com.cartao.miniautorizador.exception;

/**
 * Exceção personalizada
 */
public class SenhaInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException(String message) {
		super(message);
	}

}
