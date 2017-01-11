package net.atos.practica.negocio;
import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.CategoriaProfesionalDao;
import net.atos.practica.dto.FiltroCatProfesionalDto;
import net.atos.practica.entity.CategoriaProfesional;


import net.atos.practica.entity.Colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProfesionalBO implements InterfaceBO<CategoriaProfesional, FiltroCatProfesionalDto>{

	@Autowired
	private CategoriaProfesionalDao categoriaProfesionalDao;

	@Override
	public List<CategoriaProfesional> buscar(FiltroCatProfesionalDto filtro){
		return categoriaProfesionalDao.buscar(filtro);
	}
	
	public List<Colaborador> buscar(int idCategoria){
		return categoriaProfesionalDao.buscar(idCategoria);
	}
	
	@Override
	public void actualizar(CategoriaProfesional cp){
		categoriaProfesionalDao.actualizar(cp);	
	}
	
	@Override
	public void borrar(CategoriaProfesional cp){
		categoriaProfesionalDao.borrar(cp);		
	}
	
	@Override
	public void crear(CategoriaProfesional cp) {		
		categoriaProfesionalDao.crear(cp);
	}
	
	public List<CategoriaProfesional> listarCategoriasProfesionales(){
		return categoriaProfesionalDao.listarCategoriasProfesionales();
	}
	

	public List<Colaborador> listarColaboradorCategorias(String nombreCatProfesional) {
		return categoriaProfesionalDao.listarColaboradorCategorias(nombreCatProfesional);
	}

	public List<Colaborador> listarColaboradores(String nombreCatProfesional) {
		return categoriaProfesionalDao.listarColaboradores(nombreCatProfesional);
	}
	
	public List<Colaborador> listarColaboradores() {
		return categoriaProfesionalDao.listarColaboradores();
	}

	public List<CategoriaProfesional> buscarIgual(String filtro) {
		return categoriaProfesionalDao.buscarIgual(filtro);
	}
}