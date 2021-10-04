package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class LivroFormDto {
	@NotBlank
	@Size(min=10, message="Título deve ter no mínimo 10 caracteres")
	private String titulo;
	@PastOrPresent
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonAlias("data_lancamento")
	private LocalDate dataLancamento;
	@Min(100)
	@JsonAlias("numero_de_paginas")
	private int numeroDePaginas;
	@JsonAlias("autor_id")
	private Long autorId;

}
