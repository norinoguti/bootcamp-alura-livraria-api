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

import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LivroControllerTest {
	
	@Autowired
	private MockMvc mvc;	
	
	@Autowired
	private AutorRepository repository;
	

	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(
				 post("/livros") 
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());
	}	
	
	
	@Test
	void deveriaCadastarLivroComDadosCompletos() throws Exception {
		Autor autor = new Autor();
			autor.setNome("Marcos Piangers");
			autor.setDataNascimento(LocalDate.of(1970,07,20));
			autor.setEmail("paipop@email.com");
			autor.setMiniCurriculo("Palestrante de renome internacional");
			autor.setId(null);
			repository.save(autor);			
				
			
		String json = "{\"titulo\":\"O codigo da Vinci\","
				+ "\"dataLancamento\":\"10/09/1990\","
				+ "\"numeroDePaginas\":450,\"autorId\":"+ autor.getId()+ "}";
		
		String jsonRetorno = "{\"titulo\":\"O codigo da Vinci\","
				+ "\"dataLancamento\":\"10/09/1990\","
				+ "\"numeroDePaginas\":450}";

		
		mvc.perform(
				 post("/livros")
				 	.contentType(MediaType.APPLICATION_JSON)
					.content(json))
					.andExpect(status().isCreated())
					.andExpect(header().exists("Location"))
					.andExpect(content().json(jsonRetorno));
		}

}
