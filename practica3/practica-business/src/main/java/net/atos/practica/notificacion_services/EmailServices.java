package net.atos.practica.notificacion_services;

import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import net.atos.practica.entity.Colaborador;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailServices {
	private static Logger log = Logger.getLogger(EmailServices.class);

	private JavaMailSender mailSender;

	// Propiedad inicializada mediante bean de spring email-bean-context.xml
	private String emailRemitente;

	
	public void enviarMailList(List<Colaborador> listaColab, 
			String asunto, String contenido) throws MessagingException {
		log.debug("<------COMIENZA enviarMail Clase MailMail2----------->");
		try {
			MimeMessage mensaje = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
			Iterator<Colaborador> it = listaColab.iterator();
			while (it.hasNext()) {
				Colaborador colaborador = it.next();
				helper.addTo(colaborador.getEmail());
			}
			helper.setFrom(emailRemitente);
			helper.setSubject(asunto);
			helper.setText(contenido, true);
			mailSender.send(mensaje);
			log.debug("<------FINALIZA enviarEmailList Clase EmailServices ");
		} catch (MailException e) {
			log.error("<--------CLASE MAILMAIL2: ERROR AL ENVIAR EL EMAIL------>"
					+ e.getMessage());
		}
	}

	public void enviarMailSimple(String para, String de, String asunto,
			String contenido) throws MessagingException {
		log.debug("<------COMIENZA enviarMail Clase MailMail2----------->");
		try {
			MimeMessage mensaje = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

			helper.addTo(para);
			helper.setFrom(de);
			helper.setSubject(asunto);
			helper.setText(contenido, true);

			mailSender.send(mensaje);

			log.debug("<------FINALIZA enviarEmailList Clase EmailServices ");
		} catch (MailException e) {
			log.error("<--------CLASE MAILMAIL2: ERROR AL ENVIAR EL EMAIL------>"
					+ e.getMessage());
		}
	}

	

	// GETTER Y SETTERS
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getEmailRemitente() {
		return emailRemitente;
	}

	public void setEmailRemitente(String emailRemitente) {
		this.emailRemitente = emailRemitente;
	}

	
	
}