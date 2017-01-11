package net.atos.practica.springsecurity;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private static final Logger log = Logger.getLogger(MyAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,	HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		if(exception instanceof BadCredentialsException){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o contraseña incorrectos", "Por favor, introduzca el usuario o contraseña válidos"));
			log.info("<-------USUARIO O PASSWORD NO VÁLIDOS:------> ");
		}
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		response.sendRedirect(request.getContextPath() + "/login.xhtml");

	}

}
