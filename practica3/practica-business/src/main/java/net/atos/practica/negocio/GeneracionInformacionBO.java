package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.GeneracionDeInformacionDao;
import net.atos.practica.dto.FiltroGenerarInformacion;
import net.atos.practica.entity.Colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GeneracionInformacionBO implements InterfaceBO<FiltroGenerarInformacion, FiltroGenerarInformacion>{
	@Autowired
	private GeneracionDeInformacionDao generacionDeInformacionDao;
	
	@Override
	public List<Colaborador> buscar(FiltroGenerarInformacion filtroBusqueda){
		return generacionDeInformacionDao.buscar(filtroBusqueda);
	}

	@Override
	public void crear(FiltroGenerarInformacion objeto) {}


	@Override
	public void actualizar(FiltroGenerarInformacion objeto) {}

	@Override
	public void borrar(FiltroGenerarInformacion objeto) {}
}
