package br.com.alura.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.dto.LivroPorAutorDto;
import br.com.alura.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	@Query("SELECT "
			+ "new br.com.alura.livraria.dto.LivroPorAutorDto("
			+ " a.nome, "
			+ "COUNT(l.titulo), "
			+ "(SELECT COUNT(l1.titulo) FROM Livro l1))"
			+ " FROM Livro l INNER JOIN l.autor a "
			+ " GROUP BY a.nome")			
	
	List<LivroPorAutorDto> relatorioLivroPorAutor();
	
}
