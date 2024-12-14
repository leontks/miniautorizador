package com.cartao.miniautorizador.repository;

import com.cartao.miniautorizador.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as transações no sistema.
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Nennhum método personalizados foi necessário.
}