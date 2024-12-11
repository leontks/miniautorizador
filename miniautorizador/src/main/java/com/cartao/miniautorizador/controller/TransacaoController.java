package com.cartao.miniautorizador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoRequest request) {
        String resultado = transacaoService.realizarTransacao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }
}