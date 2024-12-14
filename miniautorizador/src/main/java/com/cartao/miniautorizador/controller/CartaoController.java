package com.cartao.miniautorizador.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartao.miniautorizador.dto.CartaoRequest;
import com.cartao.miniautorizador.exception.CartaoJaExisteException;
import com.cartao.miniautorizador.exception.CartaoNaoEncontradoException;
import com.cartao.miniautorizador.model.Cartao;
import com.cartao.miniautorizador.security.RSAEncryption;
import com.cartao.miniautorizador.service.CartaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "Cartões", description = "APIs para gerenciamento de cartões")
public class CartaoController {

	private final CartaoService cartaoService;

	@Autowired
	public CartaoController(CartaoService cartaoService) {
		this.cartaoService = cartaoService;
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Criar um novo cartão", description = "Cria um novo cartão com um saldo inicial de R$ 500,00", responses = {
			@ApiResponse(responseCode = "201", description = "Cartão criado com sucesso"),
			@ApiResponse(responseCode = "422", description = "Cartão já existe"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação") })
	public ResponseEntity<?> criarCartao(@Valid @RequestBody CartaoRequest request) throws Exception {
		try {
			Cartao novoCartao = cartaoService.criarCartao(request);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new CartaoRequest(novoCartao.getNumeroCartao(), RSAEncryption.decrypt(novoCartao.getSenha())));
		} catch (CartaoJaExisteException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new CartaoRequest(request.getNumeroCartao(), request.getSenha()));
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de cartão nulo.");
		}
	}

	@GetMapping("/{numeroCartao}")
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Consultar saldo do cartão", description = "Obtém o saldo de um cartão existente pelo número do cartão", responses = {
			@ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cartão não encontrado"),
			@ApiResponse(responseCode = "401", description = "Erro de autenticação") })
	public ResponseEntity<?> consultarSaldo(@PathVariable String numeroCartao) {
		try {
			BigDecimal saldo = cartaoService.consultarSaldo(numeroCartao);
			return ResponseEntity.ok(saldo);
		} catch (CartaoNaoEncontradoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de cartão nulo.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro genérico com cartão " + numeroCartao);
		}
	}
}