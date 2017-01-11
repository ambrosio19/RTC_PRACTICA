package net.atos.practica.springsecurity;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SeleccionaHome implements AuthenticationSuccessHandler {
	
	protected Logger log = Logger.getLogger(this.getClass());

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (roles.contains("ROLE_ADMIN")){
			response.sendRedirect(request.getContextPath() + "/pages/crudColaborador.xhtml" );
		} else if (roles.contains("ROLE_USER")){
			response.sendRedirect(request.getContextPath() + "/pages/crudColaborador.xhtml" );
		} else {
			throw new IllegalStateException();
		}
	}

}
