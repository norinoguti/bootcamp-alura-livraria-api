package br.com.alura.livraria.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LivroDto {
	private String titulo;
	private LocalDate dataLancamento;
	private int numeroDePaginas;
	private AutorLivroDto autor;

}
