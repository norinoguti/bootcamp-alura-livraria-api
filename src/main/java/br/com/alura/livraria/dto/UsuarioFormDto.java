package br.com.alura.livraria.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFormDto {
	
	@NotBlank
	@Size(min=3,max=100)
	private String nome;
	
	@NotBlank
	@Size(min=5,max=100)
	private String login;
	
	@NotNull
	private Long perfilId;

}
