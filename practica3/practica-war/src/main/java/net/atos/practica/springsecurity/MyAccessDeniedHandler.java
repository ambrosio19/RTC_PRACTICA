package net.atos.practica.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
	
	private static final Logger log = Logger.getLogger(MyAccessDeniedHandler.class);
	

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {		
		
		log.info(accessDeniedException.getMessage(), accessDeniedException);
		
		response.sendRedirect(request.getContextPath() + "/403.xhtml");
	}

}
