package net.atos.practica.negocio;

import net.atos.practica.dao.LoginDao;
import net.atos.practica.entity.Colaborador;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Component
public class LoginBO {
	
	private static Logger log =Logger.getLogger(LoginBO.class);

	@Autowired
	private LoginDao loginDao;
	
	public Colaborador validate(String usuario, String password) {
		
		Colaborador u = loginDao.buscarUsuarioPorUsernameYPassword(usuario, password);
		log.debug("<-------LoginBO: "+ usuario + "-------->");
		return (u);
	}


}
