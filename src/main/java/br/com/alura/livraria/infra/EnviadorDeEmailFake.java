package br.com.alura.livraria.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "test"})
public class EnviadorDeEmailFake implements EnviadorDeEmail{
	
	@Async
	public void enviarEmail(String destinatario,String assunto, String mensagem) {
		System.out.println("ENVIANDO EMAIL: ");
		System.out.println("Login: " + destinatario);
		System.out.println("Assunto: " + assunto);
		System.out.println("Mensagem: " + mensagem);
	}
	

}
