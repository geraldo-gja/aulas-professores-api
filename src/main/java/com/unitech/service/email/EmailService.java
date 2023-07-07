package com.unitech.service.email;

import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmailComAnexo(String para, String titulo, String conteudo, String arquivo) {
    	
        log.info("Enviando email com anexo");
        var mensagem = javaMailSender.createMimeMessage();

        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mensagem, true);
			
			helper.setTo(para);
	        helper.setSubject(titulo);
	        helper.setText(conteudo, true);

	        helper.addAttachment("CV-Geraldo.pdf", new ClassPathResource(arquivo));
		} catch (MessagingException e) {
			e.printStackTrace();
		}

        javaMailSender.send(mensagem);
        log.info("Email com anexo enviado com sucesso.");
    }
}
