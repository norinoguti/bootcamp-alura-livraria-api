package br.com.alura.livraria.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class LivroService {
	@Autowired
	private LivroRepository livroRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	
	public List<LivroDto>listar(){
		List<Livro>livros = livroRepository.findAll();
		return livros
				.stream()
				.map(l-> modelMapper.map(l, LivroDto.class))
				.collect(Collectors.toList());				
	}
	
	public void cadastrar(LivroFormDto dto) {
		Livro livro = modelMapper.map(dto, Livro.class);
		livroRepository.save(livro);
	}

}
