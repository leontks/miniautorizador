package com.cartao.miniautorizador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartao.miniautorizador.dto.TransacaoRequest;
import com.cartao.miniautorizador.exception.CartaoNaoEncontradoException;
import com.cartao.miniautorizador.exception.SaldoInsuficienteException;
import com.cartao.miniautorizador.exception.SenhaInvalidaException;
import com.cartao.miniautorizador.service.TransacaoService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/transacoes")
@PreAuthorize("hasRole('USER')")
@Tag(name = "Transações", description = "APIs para gerenciamento de transações")
public class TransacaoController {
	
	private final TransacaoService transacaoService;

	@Autowired
	public TransacaoController(TransacaoService transacaoService) {
		this.transacaoService = transacaoService;
	}
	
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Criar um nova transação", description = "Cria uma transação com o valor estipulado na requisição.", responses = {
			@ApiResponse(responseCode = "201", description = "Tansação criado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Tansação com dados errados ou faltando"),
			@ApiResponse(responseCode = "422", description = "Tansação com cartão sem senha inválida, Tansação com cartão sem saldo, Tansação com cartão não encontrado")})
    public ResponseEntity<String> realizarTransacao(@Valid @RequestBody TransacaoRequest request) {
    	try {
    		String resultado = transacaoService.realizarTransacao(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (SenhaInvalidaException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}catch (SaldoInsuficienteException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} catch (CartaoNaoEncontradoException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de cartão nulo.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro genérico com cartão " + request.getNumeroCartao() + e.getMessage());
		}
		
    }
}