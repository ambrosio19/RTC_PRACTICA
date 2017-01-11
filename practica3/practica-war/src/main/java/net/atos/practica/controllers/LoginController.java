package net.atos.practica.controllers;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

@ManagedBean(name="loginController")
@RequestScoped
public class LoginController implements PhaseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(LoginController.class);
	
	public String doLogin() throws ServletException, IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");		
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());		
		FacesContext.getCurrentInstance().responseComplete();		
		return null;
	}
 
	public String doLogout() throws ServletException, IOException {
	    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	    RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
	    dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
	    FacesContext.getCurrentInstance().responseComplete();
	    return null;
	}
	
	public void accesoDenegado(){
		FacesContext.getCurrentInstance().addMessage("denegadoMessage" , new FacesMessage(FacesMessage.SEVERITY_FATAL, "ACCESO DENEGADO \n", "\n No tiene permiso para ver esta página"));
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
	
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		if (e instanceof BadCredentialsException){
			log.debug("Excepcion encontrada en el mapa de sesión: "+e);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage("loginMessage" , new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o contraseña incorrectos \n", "Por favor, introduzca el usuario o contraseña válidos"));
		} else if (e instanceof AccountExpiredException){
			log.debug("Excepcion encontrada en el mapa de sesión: "+e);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage("loginMessage" , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su cuenta ha sido desactivada \n", "Por favor, ponganse en contacto con su administrador"));
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
