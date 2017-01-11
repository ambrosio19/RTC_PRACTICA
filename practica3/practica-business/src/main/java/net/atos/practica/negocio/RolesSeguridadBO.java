package net.atos.practica.negocio;
import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.RolesSeguridadDao;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.RolesSeguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RolesSeguridadBO implements InterfaceBO<RolesSeguridad, String>{

	@Autowired
	private RolesSeguridadDao rolesSeguridadDao;

	@Override
	public List<RolesSeguridad> buscar(String filtro){
		return rolesSeguridadDao.buscar(filtro);
	}
	
	public List<Colaborador> buscar(int idRol){
		return rolesSeguridadDao.buscar(idRol);
	}
	
	public List<RolesSeguridad> buscarIgual(String filtro){
		return rolesSeguridadDao.buscarIgual(filtro);
	}
	
	@Override
	public void actualizar(RolesSeguridad r){
		rolesSeguridadDao.actualizar(r);	
	}
	
	@Override
	public void borrar(RolesSeguridad r){
		rolesSeguridadDao.borrar(r);		
	}
	
	@Override
	public void crear(RolesSeguridad r) {	
		rolesSeguridadDao.crear(r);
	}
	
	public List<RolesSeguridad> listarRoles(){
		return rolesSeguridadDao.listarRoles();
	}
	
	public List<Colaborador> listarColaboradorRol(String nombreRol) {
		return rolesSeguridadDao.listarColaboradorRol(nombreRol);
	}

	public List<Colaborador> listarColaboradores(String nombreRol) {
		return rolesSeguridadDao.listarColaboradores(nombreRol);
	}
	
	public List<Colaborador> listarColaboradores() {
		return rolesSeguridadDao.listarColaboradores();
	}
}