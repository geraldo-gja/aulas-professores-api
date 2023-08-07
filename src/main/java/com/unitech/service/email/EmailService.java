package com.unitech.service.email;

import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Esta classe é responsável por enviar e-mails com anexos usando o serviço JavaMailSender.
 *
 */
@Service
@Slf4j
public class EmailService {

	private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Método para enviar um e-mail com um anexo.
     *
     * @param para     O destinatário do e-mail.
     * @param titulo   O título (assunto) do e-mail.
     * @param conteudo O conteúdo do e-mail, que pode ser em formato HTML.
     * @param arquivo  O nome do arquivo a ser anexado ao e-mail.
     */
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
