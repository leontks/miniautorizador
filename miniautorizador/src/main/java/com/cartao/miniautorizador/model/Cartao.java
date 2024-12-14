package com.cartao.miniautorizador.model;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa uma transação realizada com um Cartao no sistema.
 */
@EntityScan
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cartao")
public class Cartao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_cartao", nullable = false)
    private String numeroCartao;
    
    @Column(name = "senha", nullable = false, length = 2048)
    private String senha;
    
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo = new BigDecimal("500.00");
    
	public Cartao(String numeroCartao, String senha) {
		super();
		this.numeroCartao = numeroCartao;
		this.senha = senha;
	}    
    
}