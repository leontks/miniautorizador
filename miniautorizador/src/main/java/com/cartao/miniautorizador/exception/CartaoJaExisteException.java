package com.cartao.miniautorizador.exception;

/**
 * Exceção personalizada
 */
public class CartaoJaExisteException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2023410080824781289L;

	public CartaoJaExisteException(String message) {
        super(message);
    }
}