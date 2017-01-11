package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.NivelDao;
import net.atos.practica.entity.Nivel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class NivelBO implements InterfaceBO<Nivel, Nivel>{

	@Autowired
	private NivelDao nivelDao;

	@Override
	public List<Nivel> buscar(Nivel filtro) {
		// Debe recibir por parametro un FiltroEstatusDto no Estatus
		return nivelDao.buscar(filtro);
	}
	
	@Override
	public void actualizar(Nivel n) {
		nivelDao.actualizar(n);
	}
	
	@Override
	public void borrar(Nivel n) {
		nivelDao.borrar(n);
	}

	@Override
	public void crear(Nivel n) {
		nivelDao.crear(n);
	}

	public List<Nivel> listarNiveles() {
		return nivelDao.listarNiveles();
	}
}