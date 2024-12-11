package com.cartao.miniautorizador.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {
    @Transactional
    public String realizarTransacao(TransacaoRequest request) {
        Cartao cartao = cartaoRepository.findById(request.getNumeroCartao())
                .orElseThrow(() -> new CartaoNaoEncontradoException("CARTAO_INEXISTENTE"));

        if (!cartao.getSenha().equals(request.getSenhaCartao())) {
            throw new SenhaInvalidaException("SENHA_INVALIDA");
        }

        if (cartao.getSaldo().compareTo(request.getValor()) < 0) {
            throw new SaldoInsuficienteException("SALDO_INSUFICIENTE");
        }

        cartao.setSaldo(cartao.getSaldo().subtract(request.getValor()));
        cartaoRepository.save(cartao);

        return "OK";
    }
}