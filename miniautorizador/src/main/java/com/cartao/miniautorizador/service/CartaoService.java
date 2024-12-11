package com.cartao.miniautorizador.service;

import org.springframework.stereotype.Service;

import com.cartao.miniautorizador.model.Cartao;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public Cartao criarCartao(CartaoRequest request) {
        if (cartaoRepository.existsById(request.getNumeroCartao())) {
            throw new CartaoJaExisteException("Cartão já existe");
        }
        return cartaoRepository.save(new Cartao(request.getNumeroCartao(), request.getSenha()));
    }

    public BigDecimal consultarSaldo(String numeroCartao) {
        Cartao cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado"));
        return cartao.getSaldo();
    }
}