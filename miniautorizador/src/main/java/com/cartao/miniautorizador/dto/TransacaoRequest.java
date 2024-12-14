package com.cartao.miniautorizador.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Classe DTO para receber os dados de uma transação.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {

    @NotBlank(message = "O número do cartão não pode estar vazio.")
    @Pattern(regexp = "\\d{16}", message = "O número do cartão deve conter 16 dígitos.")
    private String numeroCartao;

    @NotBlank(message = "A senha do cartão não pode estar vazia.")
    @Pattern(regexp = "\\d{4}", message = "A senha do cartão deve conter exatamente 4 dígitos.")
    private String senhaCartao;

    @NotNull(message = "O valor da transação não pode ser nulo.")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser maior que zero.")
    private BigDecimal valor;
}