package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;


@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;
	
	@Mock
	private AutorRepository autorRepository;
	
	@InjectMocks
	private LivroService service;
	

	private LivroFormDto criarLivroFormDto () {
		LivroFormDto formDto = new LivroFormDto(
				"Cem anos de solidão",
				LocalDate.of(1950, 12, 05),
				490,
				1L);
				
		
		return formDto;
		
	}
	
	@Test
	void deveriaCadastrarUmLivro() {
		LivroFormDto formDto = criarLivroFormDto();
		
		LivroDto dto = service.cadastrar(formDto);
		
		Mockito.verify(livroRepository).save(Mockito.any());
		
		assertEquals(formDto.getTitulo(), dto.getTitulo());
		assertEquals(formDto.getDataLancamento(), dto.getDataLancamento());
		assertEquals(formDto.getNumeroDePaginas(), dto.getNumeroDePaginas());
		
		
	}	
	
	@Test
	void naoDeveriaCadastrarUmLivroComAutorInexistente() {
		LivroFormDto formDto = criarLivroFormDto();
				

		Mockito
		.when(autorRepository
		.getById(formDto.getAutorId()))
		.thenThrow(EntityNotFoundException.class);

		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto));
	}

}
