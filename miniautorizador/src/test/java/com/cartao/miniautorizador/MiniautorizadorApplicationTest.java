package com.cartao.miniautorizador;

import com.cartao.miniautorizador.repository.CartaoRepository;
import com.cartao.miniautorizador.repository.TransacaoRepository;
import com.cartao.miniautorizador.security.RSAEncryption;
import com.cartao.miniautorizador.model.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Classe de testes para o CartaoController.
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MiniautorizadorApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private TransacaoRepository transacaoRepository;

	private static final String CARTAO_URL = "/cartoes";
	private static final String TRANSACAO_URL = "/transacoes";

	@BeforeEach
	void setUp() {
		cartaoRepository.deleteAll(); // Limpar o banco de dados antes de cada teste
		transacaoRepository.deleteAll(); // Limpar o banco de dados antes de cada teste
	}

	@Test
	public void testCriarCartao() throws Exception {
		mockMvc.perform(post(CARTAO_URL).with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"6549873025634501\", \"senha\": \"1234\" }"))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao").value("6549873025634501"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("1234"));
	}

	@Test
	public void testCriarCartaoNaoAutorizado() throws Exception {
		mockMvc.perform(post(CARTAO_URL).contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"6549873025634501\", \"senha\": \"1234\" }"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void testConsultarSaldoCartao() throws Exception {
		// Cria o cartão com saldo baixo
		
		Cartao cartao = new Cartao("6549873025634501", RSAEncryption.encrypt("1234"));
		cartaoRepository.save(cartao);

		mockMvc.perform(get(CARTAO_URL + "/6549873025634501")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("500.00"));
	}

	@Test
	public void testConsultarSaldoCartaoNnaoAutorizado() throws Exception {
		// Cria o cartão com saldo baixo
		
		Cartao cartao = new Cartao("6549873025634501", RSAEncryption.encrypt("1234"));
		cartaoRepository.save(cartao);

		mockMvc.perform(get(CARTAO_URL + "/6549873025634501").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void testRealizarTransacao() throws Exception {
		// Cria o cartão com saldo baixo
		
		Cartao cartao = new Cartao("6549873025634501", RSAEncryption.encrypt("1234"));
		cartaoRepository.save(cartao);
		mockMvc.perform(post(TRANSACAO_URL).with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"1234\", \"valor\": 50.0 }"))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("OK"));

		mockMvc.perform(get(CARTAO_URL + "/6549873025634501")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("450.00"));

	}

	@Test
	public void testRealizarTransacaoNaoAutorizado() throws Exception {
		// Cria o cartão com saldo baixo
		
		Cartao cartao = new Cartao("6549873025634501", RSAEncryption.encrypt("1234"));
		cartaoRepository.save(cartao);
		mockMvc.perform(post(TRANSACAO_URL).contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"1234\", \"valor\": 50.0 }"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());

	}

	@Test
	public void testTransacaoComSaldoInsuficiente() throws Exception {

		// Cria o cartão com saldo baixo
		
		Cartao cartao = new Cartao("6549873025634501", RSAEncryption.encrypt("1234"));
		cartaoRepository.save(cartao);

		// Tenta realizar uma transação maior que o saldo
		mockMvc.perform(post(TRANSACAO_URL).with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"1234\", \"valor\": 5000.0 }"))
				.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
				.andExpect(MockMvcResultMatchers.content().string("SALDO_INSUFICIENTE"));
	}

	@Test
	public void testTransacaoComSenhaInvalida() throws Exception {
		
		// Cria o cartão com saldo baixo
		Cartao cartao = new Cartao("6549873025634501", RSAEncryption.encrypt("1234"));
		cartaoRepository.save(cartao);

		// Tenta realizar uma transação com senha errada
		mockMvc.perform(post(TRANSACAO_URL).with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"9999\", \"valor\": 50.0 }"))
				.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
				.andExpect(MockMvcResultMatchers.content().string("SENHA_INVALIDA"));
	}

	@Test
	public void testTransacaoComCartaoInexistente() throws Exception {
		// Tenta realizar uma transação com um cartão inexistente
		mockMvc.perform(post(TRANSACAO_URL).with(SecurityMockMvcRequestPostProcessors.httpBasic("username", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"numeroCartao\": \"9999999999999999\", \"senhaCartao\": \"1234\", \"valor\": 50.0 }"))
				.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
				.andExpect(MockMvcResultMatchers.content().string("CARTAO_INEXISTENTE"));
	}
}