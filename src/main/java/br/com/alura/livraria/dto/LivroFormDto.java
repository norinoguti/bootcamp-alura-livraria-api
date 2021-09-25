package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

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
public class LivroFormDto {
	@NotBlank
	@Size(min=10, message="Título deve ter no mínimo 10 caracteres")
	private String titulo;
	@PastOrPresent
	private LocalDate dataLancamento;
	@Min(100)
	private int numeroDePaginas;
	private AutorLivroDto autor;

}
