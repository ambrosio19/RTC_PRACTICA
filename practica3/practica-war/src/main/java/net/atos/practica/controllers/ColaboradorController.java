package net.atos.practica.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorBO;
import net.atos.practica.springsecurity.CustomUser;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ColaboradorController{
	
	private static final Logger log = Logger.getLogger(ColaboradorController.class);
	
	@Autowired
	private ColaboradorBO colaboradorBO;

	private static final String ERROR = "Error:";
	
	private Colaborador colaborador;
	private CustomUser user;
	private String passActual;
	private String newPass1;
	private String newPass2;
	private String message;

	@PostConstruct
	public void init() {
		user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		colaborador = new Colaborador();
		colaborador = (Colaborador) colaboradorBO.buscar(user.getUsername()).get(0);
		passActual = "";
		newPass1 = "";
		newPass2 = "";
	}

	public void actualizar() {
		try {
			colaboradorBO.actualizar(colaborador);	
		} catch (LlamaloXException e){
			log.info(e.getMessage(), e);
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		colaborador = (Colaborador) colaboradorBO.buscar(user.getUsername()).get(0);
	}

	public void verificarPass() {
		if (newPass1.equals(newPass2)) {
			if (colaboradorBO.verificarPassActual(passActual, user.getUsername())) {
				colaboradorBO.verificarPass(passActual, newPass1, user.getUsername());
				passActual = "";
				newPass1 = "";
				newPass2 = "";
			} else {
				FacesContext.getCurrentInstance().validationFailed();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR, "Esa no es su contraseña actual"));
			}
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR, "Las contraseñas no coinciden"));
		}
	}

	public void subirFoto(FileUploadEvent event) {
		UploadedFile fichero = (UploadedFile) event.getFile();
		// Obtiene el nombre del fichero
		String nombreFichero = FilenameUtils.getName(fichero.getFileName());
		// Obtiene la extension del fichero
		String extensionFichero = FilenameUtils.getExtension(fichero.getFileName());

		InputStream inputStr = null;
		BufferedImage image = null;
		File ficheroDestino;

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		// Ruta en el servidor del directorio usuarios
		String pathServer = servletContext.getRealPath("/fotos_usuarios") + "/"; 

		// Obtiene el directorio externo donde se guardan las fotos
		// Este directorio se configura en el fichero web.xml del war
		// ExternalContext externalContext =
		// FacesContext.getCurrentInstance()
		// .getExternalContext();
		// String directorioFotos =
		// externalContext.getInitParameter("uploadDirectoryFotosUsuarios");

		try {
			inputStr = fichero.getInputstream();
			image = ImageIO.read(inputStr);

			if (image != null) {
				ficheroDestino = new File(pathServer, colaborador.getCodigo()
						+ "." + extensionFichero);

				ImageIO.write(image, extensionFichero,ficheroDestino);
				//FileUtils.copyInputStreamToFile(inputStr, ficheroDestino);
				colaborador.setFoto("/fotos_usuarios/"+ colaborador.getCodigo() + "." + extensionFichero);
				colaboradorBO.actualizar(colaborador);

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Foto actualizada",	nombreFichero);
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, ERROR, "Fotografía no válida");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, ERROR, "No se ha podido leer la foto subida");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getPassActual() {
		return passActual;
	}

	public void setPassActual(String passActual) {
		this.passActual = passActual;
	}

	public String getNewPass1() {
		return newPass1;
	}

	public void setNewPass1(String newPass1) {
		this.newPass1 = newPass1;
	}

	public String getNewPass2() {
		return newPass2;
	}

	public void setNewPass2(String newPass2) {
		this.newPass2 = newPass2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
