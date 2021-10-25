package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private AutorRepository autorRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	public Page<LivroDto>listar(Pageable paginacao){
		Page<Livro> livros = livroRepository.findAll(paginacao);
		return livros.map(l-> modelMapper.map(l, LivroDto.class));								
	}
	
	@Transactional
	public LivroDto cadastrar(LivroFormDto dto) {
		Long idAutor = dto.getAutorId();
		try {
			Autor autor = autorRepository.getById(idAutor);
			Livro livro = modelMapper.map(dto, Livro.class);
			livro.setId(null);
			livro.setAutor(autor);
			
			livroRepository.save(livro);
			return modelMapper.map(livro, LivroDto.class);
			
		}catch(EntityNotFoundException e) {
			throw new IllegalArgumentException("Autor inexistente");
		}
			
			
	}

	@Transactional
	public LivroDto atualizar(AtualizacaoLivroFormDto dto) {
		Livro livro = livroRepository.getById(dto.getId());
		livro.atualizarInformacoes(dto.getTitulo(), dto.getDataLancamento(), dto.getNumeroDePaginas());
		
		return modelMapper.map(livro, LivroDto.class);
	}

	@Transactional
	public void remover(Long id) {
		livroRepository.deleteById(id);			
	}

	public LivroDto detalhar(Long id) {
		Livro livro = livroRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
				return modelMapper.map(livro, LivroDto.class);
	}

	
}
