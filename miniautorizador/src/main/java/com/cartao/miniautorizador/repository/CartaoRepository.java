package com.cartao.miniautorizador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cartao.miniautorizador.model.Cartao;


/**
 * Repositório responsável pelo acesso aos dados da entidade Cartão.
 */

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    // Método para buscar um cartão por número
	Optional<Cartao> findByNumeroCartao(String numeroCartao);

    // Método para verificar se já existe um cartão com o mesmo número
    boolean existsByNumeroCartao(String numeroCartao);
}