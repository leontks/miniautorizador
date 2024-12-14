package com.cartao.miniautorizador.service;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

import com.cartao.miniautorizador.dto.CartaoRequest;
import com.cartao.miniautorizador.exception.CartaoJaExisteException;
import com.cartao.miniautorizador.exception.CartaoNaoEncontradoException;
import com.cartao.miniautorizador.model.Cartao;
import com.cartao.miniautorizador.repository.CartaoRepository;
import com.cartao.miniautorizador.security.RSAEncryption;

import jakarta.validation.Valid;

@Service
public class CartaoService {

	private final CartaoRepository cartaoRepository;

	public CartaoService(CartaoRepository cartaoRepository) {
		this.cartaoRepository = cartaoRepository;
	}

	public Cartao criarCartao(@Valid CartaoRequest request) throws Exception {
		try {
			
			if (request != null) {
				if (cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())) {
					throw new CartaoJaExisteException(
							"Cartão com número " + request.getNumeroCartao() + " já existe no sistema.");
				}
				return cartaoRepository.save(
						new Cartao(request.getNumeroCartao(), RSAEncryption.encrypt(request.getSenha())));
			}
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmException();
		}
		return null;
	}

	public BigDecimal consultarSaldo(String numeroCartao) {

		try {
			if (cartaoRepository.existsByNumeroCartao(numeroCartao)) {
				Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao).get();
				return cartao.getSaldo();
			}
			throw new CartaoNaoEncontradoException("Cartão " + numeroCartao + " não encontrado");

		} catch (CartaoNaoEncontradoException e) {
			throw new CartaoNaoEncontradoException("Cartão " + numeroCartao + " não encontrado" + e);

		}
	}
}