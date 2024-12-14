package com.cartao.miniautorizador.exception;

/**
 * Exceção personalizada
 */
public class CartaoNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3511383406295892548L;

	public CartaoNaoEncontradoException(String message) {
		super(message);
	}

}
