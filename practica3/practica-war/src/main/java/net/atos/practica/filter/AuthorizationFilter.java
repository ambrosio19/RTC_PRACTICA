package net.atos.practica.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.common.identity.Identity;

import org.apache.log4j.Logger;

//@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	static final Logger LOG= Logger.getLogger(AuthorizationFilter.class);
			
	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			
			String reqURI = ((HttpServletRequest) request).getRequestURI();
			
            if (reqURI.indexOf("/login.xhtml") != -1 || reqURI.indexOf("javax.faces.resource") != -1){
            	chain.doFilter(request, response);
            	return;            	
            }
            
            
    		//Verificar que el usuario está logado sin crear sesión si es que ésta no existe.
            HttpSession session=((HttpServletRequest) request).getSession(false);
            if (session == null ) {
				String contextPath = ((HttpServletRequest) request)	.getContextPath();
				((HttpServletResponse) response).sendRedirect(contextPath+ "/login.xhtml");
				return;
			}
			Identity identity = (Identity) session.getAttribute("identity");
			
			if (identity == null || !identity.isLoggedIn()) {
				String contextPath = ((HttpServletRequest) request)	.getContextPath();
				((HttpServletResponse) response).sendRedirect(contextPath+ "/login.xhtml");
				return;
			}

			chain.doFilter(request, response);
		} catch (Exception e) {
			LOG.error("Error en filtro Login.",e);
		}
	}

	@Override
	public void destroy() {

	}
}