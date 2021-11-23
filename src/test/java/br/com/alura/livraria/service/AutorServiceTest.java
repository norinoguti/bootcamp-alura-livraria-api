package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.modelo.Autor;
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
	
	@Mock
	private ModelMapper modelMapper;
	

	private AutorFormDto criarAutorFormDto() {
		AutorFormDto formDto = new AutorFormDto("Jose de Alencar", "email@email.com.br", LocalDate.of(1900, 01, 05),
				"José de Alencar foi um escritor brasileiro.....");

		return formDto;

	}

	@Test
	void deveriaCadastrarUmAutor() {
		AutorFormDto formDto = criarAutorFormDto();
		
		Autor autor = new Autor(formDto.getNome(),
				formDto.getEmail(),
				formDto.getDataNascimento(),
				formDto.getMiniCurriculo());
		
		Mockito
		.when(modelMapper.map(formDto, Autor.class))
		.thenReturn(autor);
		
		Mockito
		.when(modelMapper.map(autor, AutorDto.class))
		.thenReturn(new AutorDto(
				null,
				autor.getNome(),
				autor.getEmail(),
				autor.getDataNascimento(),
				autor.getMiniCurriculo()));
		
		AutorDto dto = service.cadastrar(formDto);
		

		Mockito.verify(repository).save(Mockito.any());

		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
		assertEquals(formDto.getMiniCurriculo(), dto.getMiniCurriculo());

	}

}
