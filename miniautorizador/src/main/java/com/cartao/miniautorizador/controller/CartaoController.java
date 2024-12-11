package com.cartao.miniautorizador.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartao.miniautorizador.model.Cartao;
import com.cartao.miniautorizador.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {
    @PostMapping
    public ResponseEntity<Cartao> criarCartao(@RequestBody CartaoRequest request) {
    	CartaoService cartaoService= new CartaoService();
        Cartao novoCartao = cartaoService.criarCartao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable String numeroCartao) {
    	CartaoService cartaoService= new CartaoService();
        return ResponseEntity.ok(cartaoService.consultarSaldo(numeroCartao));
    }
}