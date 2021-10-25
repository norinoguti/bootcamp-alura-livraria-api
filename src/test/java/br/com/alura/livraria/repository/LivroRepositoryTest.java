package br.com.alura.livraria.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.livraria.dto.LivroPorAutorDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ActiveProfiles("test")
class LivroRepositoryTest {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private TestEntityManager em;
	
	@Test
	void deveriaRetornarRelatorioDeLivrosPorAutor() {
		Autor autor1 = new Autor(
				"Dan Brown",
				"dan@email.com",
				LocalDate.of(1950, 06, 04),
				"Autor de diversos livros de sucesso");
		em.persist(autor1);
		
		Livro livro1 = new Livro(
				"O codigo da Vinci",
				LocalDate.now(),
				450,
				autor1);
		em.persist(livro1);
		
		Livro livro2 = new Livro(
				"Fortaleza Digital",
				LocalDate.of(1999, 02,10),
				450,
				autor1);
		em.persist(livro2);
		
		Autor autor2 = new Autor(
				"Markus Zusak",
				"markys@email.com",
				LocalDate.of(1975, 05, 04),
				"Autor do livro de estrondoso sucesso: A menina que roubava livros");
		em.persist(autor2);
		
		Livro livro3 = new Livro(
				"A menina que roubava livros",
				LocalDate.of(2010,02,01),
				450,
				autor2);
		em.persist(livro3);
		
		List<LivroPorAutorDto> relatorio = livroRepository.relatorioLivroPorAutor();
		Assertions
		.assertThat(relatorio)
		.hasSize(2)
		.extracting(LivroPorAutorDto::getAutor, LivroPorAutorDto::getQuantidadeLivros, LivroPorAutorDto::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("Dan Brown", 2L, new BigDecimal("66.67")),
				Assertions.tuple("Markus Zusak", 1L, new BigDecimal("33.33"))
				
				);
		
	}
		

}
