package br.com.alura.livraria.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	
	public Page<LivroDto>listar(Pageable paginacao){
		Page<Livro> livros = livroRepository.findAll(paginacao);
		return livros.map(l-> modelMapper.map(l, LivroDto.class));								
	}
	
	public LivroDto cadastrar(LivroFormDto dto) {
		Livro livro = modelMapper.map(dto, Livro.class);
		livro.setId(null);
		
		livroRepository.save(livro);
		return modelMapper.map(livro, LivroDto.class);
			
	}

}
