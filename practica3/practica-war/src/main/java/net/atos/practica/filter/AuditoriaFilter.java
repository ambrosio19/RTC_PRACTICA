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
import javax.servlet.http.HttpSession;

import net.atos.common.identity.Identity;

import org.apache.log4j.Logger;

//@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuditoriaFilter implements Filter {
	
	static final Logger LOG = Logger.getLogger(AuditoriaFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			String usuario = null;			
			HttpSession session=((HttpServletRequest) request).getSession(false);
			if (session != null ) {
				Identity identity = (Identity) session.getAttribute("identity");
				usuario = identity.getUsuario();				
			}
			String reqURI = ((HttpServletRequest) request).getRequestURI();
			LOG.info("El usuario " + usuario + " ha intentado acceder a: " + reqURI );
			chain.doFilter(request, response);
		} catch (Exception e) {
			LOG.error("Error en filtro Login.",e);
		}

	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
