package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

	@Mock
	private AutorRepository repository;
	
	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private AutorService service;

	private Usuario logado;

	@BeforeEach
	public void Before() {
		this.logado = new Usuario("Floriano", "nori@email.com", "123456");
	}

	private AutorFormDto criarAutorFormDto() {
		AutorFormDto dto = new AutorFormDto("Jose de Alencar", "email@email.com.br", LocalDate.of(1900, 01, 05),
				"José de Alencar foi um escritor brasileiro.....");

		return dto;

	}

	@Test
	void deveriaCadastrarUmAutor() {
		AutorFormDto formDto = criarAutorFormDto();
		
		
		AutorDto dto = service.cadastrar(formDto);

		Mockito.verify(repository).save(Mockito.any());

		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
		assertEquals(formDto.getMiniCurriculo(), dto.getMiniCurriculo());

	}

}
