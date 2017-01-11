package net.atos.practica.test;

import java.io.IOException;

import net.atos.common.identity.Identity;
import net.atos.practica.controllers.LoginController;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.negocio.LoginBO;

import org.apache.log4j.Logger;

public class MockLoginController extends LoginController {
	
	/*
	private LoginBO loginBO;
	private Colaborador colaborador;
	private Identity identity = new Identity();
	private String usuario;
	private String password;
	
	private static final Logger LOG = Logger.getLogger(LoginController.class);
	
	public void setLoginBO(LoginBO loginBO){
		this.loginBO = loginBO;				
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String getUsuario() {
		return usuario;
	}
	@Override
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public boolean doLogin() {				
		try{		
			colaborador = loginBO.validate(usuario, password);
			//System.out.println(colaborador.getPwd() + " despues de BBDD");
			//System.out.println(colaborador.getCodigo() + " despues de BBDD");
		}
		catch(Exception e){
			LOG.info("**** Usuario log incorrecto: " + usuario);
			return false;
			
		}		
		if (colaborador != null) {
			//identidad();
			//identity.setLoggedIn(true);
			//identity.setUsuario(usuario);
			LOG.info("==== Usuario logueado: " + usuario);

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("identity", identity);

				if(colaborador.getRol().getNombreRol().equalsIgnoreCase("ADMINISTRADOR")){
				identity.setAdmin(true);	
				try {
					context.redirect(context.getRequestContextPath() + "/pages/crudColaborador.xhtml");
				} catch (IOException e) {
					//FacesMessage msg2 = new FacesMessage("Fallo en el redireccionamiento", null);
					//msg2.setSeverity(FacesMessage.SEVERITY_INFO);
					//FacesContext.getCurrentInstance().addMessage(null, msg2);
					LOG.info("Fallo en el redireccionamiento: " + usuario);
				}	
			}
			else {
				try {
					context.redirect(context.getRequestContextPath() + "/pages/crudColaborador.xhtml");
				} catch (IOException e) {
					//FacesMessage msg2 = new FacesMessage("Fallo en el redireccionamiento", null);
					//msg2.setSeverity(FacesMessage.SEVERITY_INFO);
					//FacesContext.getCurrentInstance().addMessage(null, msg2);
					LOG.info("Fallo en el redireccionamiento: " + usuario);
				}
			}				
		}
		return false;
	}
	
	public void identidad() {
		//System.out.println("entra identidad");
		identity.setLoggedIn(true);
		identity.setUsuario(usuario);
		if(colaborador.getRol().getNombreRol().equalsIgnoreCase("ADMINISTRADOR")){
			identity.setAdmin(true);	
			try {
				LOG.info(usuario + " es administrador y redirigimos a su crud");
			} catch (IOException e) {
				LOG.info(" Fallo en el redireccionamiento del administrador = " + usuario);
			}	
		}
		else {
			try {
				//context.redirect(context.getRequestContextPath() + "/pages/crudColaborador.xhtml");
			} catch (IOException e) {
				//FacesMessage msg2 = new FacesMessage("Fallo en el redireccionamiento", null);
				//msg2.setSeverity(FacesMessage.SEVERITY_INFO);
				//FacesContext.getCurrentInstance().addMessage(null, msg2);
				LOG.info("Fallo en el redireccionamiento: " + usuario);
			}
		}				
	}
		
	
*/
}
