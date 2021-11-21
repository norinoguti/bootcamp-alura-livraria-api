package br.com.alura.livraria.service;

import java.security.SecureRandom;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AtualizacaoUsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.infra.EnviadorDeEmail;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private EnviadorDeEmail enviadorDeEmail;

	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(t -> modelMapper.map(t, UsuarioDto.class));

	}

	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuario.setId(null);
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		usuario.adicionarPerfil(perfil);
		// cria senha automaticamente
		String senha = new SecureRandom().nextInt(99999) + "";
		System.out.println(usuario.getSenha());
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		//System.out.println(usuario.getSenha());
		
		usuarioRepository.save(usuario);
		
		String destinatario = usuario.getEmail();
		String assunto = "Carteira - Seja bem vindo(a)!";
		
		String mensagem = String.format("Olá %s!\n\n"
				+ " Segue seus dados de acesso ao Sistema Carteira:"
				+ "\nLogin: %s "
				+ "\nSenha:%s ", usuario.getNome(), usuario.getLogin(),senha);
		
		enviadorDeEmail.enviarEmail(destinatario, assunto, mensagem);
		
		return modelMapper.map(usuario, UsuarioDto.class);
	}
	
	@Transactional
	public UsuarioDto atualizar(AtualizacaoUsuarioFormDto dto) {
		Usuario usuario = usuarioRepository.getById(dto.getId());		
		usuario.atualizarInformacoes(dto.getLogin(),dto.getNome());		
		return modelMapper.map(usuario, UsuarioDto.class);
	}
	
	@Transactional
	public void remover(Long id) {
		try{
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
		}catch(org.springframework.dao.DataIntegrityViolationException e) {
			throw new RuntimeException("Existe registro vinculado a esse usuário, exclua o registro primeiro");
		}
		
		
	}
	
	public UsuarioDto detalhar(Long id) {
		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
				return modelMapper.map(usuario, UsuarioDto.class);
	}
}
