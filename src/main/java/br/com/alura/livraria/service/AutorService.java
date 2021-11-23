package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AtualizacaoAutorFormDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Page<AutorDto>listar(Pageable paginacao){
		Page<Autor> autores = autorRepository.findAll(paginacao);
		return autores.map(a-> modelMapper.map(a, AutorDto.class));
	}	
	
	@Transactional
	public AutorDto cadastrar(AutorFormDto dto) {
		Autor autor = modelMapper.map(dto, Autor.class);
		autor.setId(null);
		autorRepository.save(autor);
		return modelMapper.map(autor, AutorDto.class);
	}

	@Transactional
	public AutorDto atualizar(AtualizacaoAutorFormDto dto) {
		Autor autor = autorRepository.getById(dto.getAutorId());
		autor.atualizarInformacoes(dto.getNome(), dto.getEmail(), dto.getDataNascimento(), dto.getMiniCurriculo());
		
		return modelMapper.map(autor, AutorDto.class);
	}

	public void remover(@NotNull Long id) {
		try {	 		
			autorRepository.deleteById(id);
			autorRepository.flush();
		}catch(org.springframework.dao.DataIntegrityViolationException e) {
			throw new RuntimeException("Existem livros vinculados a esse autor, é preciso excluir o livro primeiro");
		}
	}

	public AutorDto detalhar(Long id) {
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(autor, AutorDto.class);
	}




}
