package com.cartao.miniautorizador.exception;

/**
 * Exceção personalizada
 */
public class SaldoInsuficienteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569748209931693245L;

	public SaldoInsuficienteException(String message) {
		super(message);
	}

}