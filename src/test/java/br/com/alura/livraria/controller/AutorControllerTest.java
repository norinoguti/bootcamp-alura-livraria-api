package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutorControllerTest {
	
	
	@Autowired
	private MockMvc mvc;

	@Test
	void naoDeveriaCadastarAutorComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(
				 post("/autores")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json))
				 .andExpect(status().isBadRequest());
	
	}
	
	
	@Test
	void deveriaCadastarAutorComDadosCompletos() throws Exception {
	
		String json = "{\"nome\":\"Dan Brown\","
				+ "\"email\":\"dan@email.com\","
				+ "\"dataNascimento\":\"20/05/1950\","
				+ "\"miniCurriculo\":\"Autor de inumeros sucessos\"}";
		
		mvc.perform(
				 post("/autores")
				 	.contentType(MediaType.APPLICATION_JSON)
					.content(json))
					.andExpect(status().isCreated())
					.andExpect(header().exists("Location"))
					.andExpect(content().json(json));
		}
	
	}
	
	

