package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.livraria.infra.security.TokenService;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutorControllerTest {
	
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private String token;
	
	@BeforeEach
	private void gerarToken() {
		Usuario logado = new Usuario("floriano", "nori@email.com", "123456");
		Perfil admin = perfilRepository.findById(1l).get(); 
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);		
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado , logado.getLogin());
		this.token = tokenService.gerarToken(authentication);
	}

	@Test
	void naoDeveriaCadastarAutorComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(
				 post("/autores")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(json)
				 .header("Authorization","Bearer " + token))
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
					.content(json)
					.header("Authorization","Bearer " + token))
					.andExpect(status().isCreated())
					.andExpect(header().exists("Location"))
					.andExpect(content().json(json));
		}
	
	}
	
	

