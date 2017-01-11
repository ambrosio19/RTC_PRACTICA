package net.atos.practica.test;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.negocio.LoginBO;

public class MockLoginBO extends LoginBO{

	private MockLoginDao loginDao = new MockLoginDao();
	/*
	private LoginDao loginDao;
		
	public void setLoginDao(LoginDao loginDao){
		this.loginDao = loginDao;	
		System.out.println("dentroBO");
	}*/

	@Override
	public Colaborador validate(String usuario, String password) {
		//System.out.println("pasa por MockLoginBO");
		//System.out.println(usuario + " de MockLoginBO");
		//System.out.println(password + " de MockLoginBO");
		Colaborador u = loginDao.buscarUsuarioPorUsernameYPassword(usuario, password);		
		return (u);
	}
	
	
	
	
}
