package net.atos.practica.test;

import net.atos.practica.dao.LoginDao;
import net.atos.practica.entity.Colaborador;

public class MockLoginDao extends LoginDao {
	
	private Colaborador cola = new Colaborador();

	@Override
	public Colaborador buscarUsuarioPorUsernameYPassword(String codigo, String password){
		//System.out.println("pasa por MockLoginDao");
		cola.setCodigo(codigo);
		cola.setPwd(password);	
		return cola;
	}
}
