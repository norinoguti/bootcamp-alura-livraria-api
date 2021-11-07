package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;


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
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc.perform(
				 post("/livros") 
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization","Bearer " + token))
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
					.content(json)
					.header("Authorization","Bearer " + token))
					.andExpect(status().isCreated())
					.andExpect(header().exists("Location"))
					.andExpect(content().json(jsonRetorno));
		}

}
