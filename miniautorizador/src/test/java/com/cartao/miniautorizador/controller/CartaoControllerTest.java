package com.cartao.miniautorizador.controller;

import java.awt.PageAttributes.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CartaoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarCartaoComSucesso() throws Exception {
        mockMvc.perform(post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\"}"))
                .andExpect(status().isCreated());
    }
}