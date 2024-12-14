package com.cartao.miniautorizador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Representa uma transação realizada com um Transacao no sistema.
 */
@EntityScan
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transacao")
public class Transacao {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao", nullable = false)
    private String numeroCartao;

    @Column(name = "senha_cartao", nullable = false, length = 2048)
    private String senhaCartao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;
    
    public Transacao(String numeroCartao, String senhaCartao, BigDecimal valor) {
		super();
		this.numeroCartao = numeroCartao;
		this.senhaCartao = senhaCartao;
		this.valor = valor; 
	} 
}