package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.livraria.modelo.Autor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutorControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
	
	//está falhando o teste de cadastrar com dados completos, acredito que é algo relacionado a data
	@Test
	void deveriaCadastarAutorComDadosCompletos() throws Exception {
		LocalDate dataNascimento = LocalDate.parse("1975-01-01");
		Autor autor = new Autor("Dan Brown", "dan@email.com", dataNascimento, "Autor de inumeros sucessos");
		
		
		mvc.perform(
				 post("/autores")
				 .contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(autor)))
					.andExpect(status().isCreated())
					.andExpect(header().exists("Location"));
		}
	
	}
	
	

