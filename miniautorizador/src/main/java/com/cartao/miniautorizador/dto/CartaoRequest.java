package com.cartao.miniautorizador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO para receber os dados de criação de um cartão.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoRequest {

    @NotBlank(message = "O número do cartão não pode estar vazio.")
    @Pattern(regexp = "\\d{16}", message = "O número do cartão deve conter 16 dígitos.")
    @NotNull
    private String numeroCartao;

    @NotBlank(message = "A senha não pode estar vazia.")
    @Pattern(regexp = "\\d{4}", message = "A senha deve conter exatamente 4 dígitos.")
    @NotNull
    private String senha;

}