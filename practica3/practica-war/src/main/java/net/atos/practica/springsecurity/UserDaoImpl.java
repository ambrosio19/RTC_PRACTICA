package net.atos.practica.springsecurity;

import java.util.ArrayList;
import java.util.List;

import net.atos.practica.dao.ColaboradorDao;
import net.atos.practica.entity.Colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public class UserDaoImpl {
	
	@Autowired
	private ColaboradorDao colaboradorDao;
	
	private List<Colaborador> colaborador = new ArrayList<Colaborador>();
	
	public CustomUser cargarColaboradorPorDas(final String das){
		

		colaborador = colaboradorDao.buscar(das);
		
		if(!colaborador.isEmpty()){
			
			CustomUser user = new CustomUser();
			user.setUsername(colaborador.get(0).getCodigo());
			user.setPassword(colaborador.get(0).getPwd());
			
			if (colaborador.get(0).getEstatus().getNombreEstatus().equalsIgnoreCase("BAJA")){
				user.setEnabled(false);
			} else{
				user.setEnabled(true);
			}
			
			Role r = new Role();
			
			if(colaborador.get(0).getRol().getNombreRol().equalsIgnoreCase("Administrador")){
				r.setNombre("ROLE_ADMIN");
			} else {
				r.setNombre("ROLE_USER");
			}
			
			List<Role> roles = new ArrayList<Role>();
			roles.add(r);
			user.setAuthorities(roles);
			return user;
		}
		return null;
	}

}
