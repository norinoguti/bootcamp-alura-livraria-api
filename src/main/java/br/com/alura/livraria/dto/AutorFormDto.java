package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorFormDto {
	@NotBlank
	@Size(min=5)
	private String nome;
	@Email
	private String email;
	@Past
	private LocalDate dataNascimento;
	@NotBlank
	@Size(min=20, max=250)
	private String miniCurriculo;
}
