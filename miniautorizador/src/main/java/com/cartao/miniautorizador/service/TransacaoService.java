package com.cartao.miniautorizador.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cartao.miniautorizador.dto.TransacaoRequest;
import com.cartao.miniautorizador.exception.CartaoNaoEncontradoException;
import com.cartao.miniautorizador.exception.SaldoInsuficienteException;
import com.cartao.miniautorizador.exception.SenhaInvalidaException;
import com.cartao.miniautorizador.model.Cartao;
import com.cartao.miniautorizador.model.Transacao;
import com.cartao.miniautorizador.repository.CartaoRepository;
import com.cartao.miniautorizador.repository.TransacaoRepository;
import com.cartao.miniautorizador.security.RSAEncryption;

import jakarta.validation.Valid;

@Service
public class TransacaoService {

	private final TransacaoRepository transacaoRepository;
	private final CartaoRepository cartaoRepository;

	public TransacaoService(TransacaoRepository transacaoRepository, CartaoRepository cartaoRepository) {
		this.transacaoRepository = transacaoRepository;
		this.cartaoRepository = cartaoRepository;
	}

	@Transactional
	public String realizarTransacao(@Valid TransacaoRequest request) throws Exception {

		try {

			Cartao cartao = buscaCartao(request.getNumeroCartao());
			if (!RSAEncryption.decrypt(cartao.getSenha()).equals(request.getSenhaCartao())) {
				throw new SenhaInvalidaException("SENHA_INVALIDA");
			}

			if (cartao.getSaldo().compareTo(request.getValor()) < 0) {
				throw new SaldoInsuficienteException("SALDO_INSUFICIENTE");
			}

			cartao.setSaldo(cartao.getSaldo().subtract(request.getValor()));
			transacaoRepository.save(new Transacao(request.getNumeroCartao(),
					RSAEncryption.encrypt(request.getSenhaCartao()), request.getValor()));
		} catch (CartaoNaoEncontradoException e) {
			throw new CartaoNaoEncontradoException("CARTAO_INEXISTENTE");
		}

		return "OK";
	}

	public Cartao buscaCartao(String numeroCartao) {
		try {
			return cartaoRepository.findByNumeroCartao(numeroCartao).get();
		} catch (NoSuchElementException e) {
			throw new CartaoNaoEncontradoException("CARTAO_INEXISTENTE");
		}
	}
}