package com.cartao.miniautorizador.model;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Id;

@EntityScan
public class Cartao {
    @Id
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo = new BigDecimal("500.00");
}