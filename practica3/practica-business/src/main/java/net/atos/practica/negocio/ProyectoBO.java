package net.atos.practica.negocio;
import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.ProyectoDao;
import net.atos.practica.dto.FiltroProyectoDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Proyecto;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProyectoBO implements InterfaceBO<Proyecto, FiltroProyectoDto>{

	@Autowired
	private ProyectoDao proyectoDao;

	@Override
	public List<Proyecto> buscar(FiltroProyectoDto filtro){
		if(filtro.getFechaInicio() != null){
			filtro.setFechaIn(true);
		}
		if(filtro.getFechaFin() != null){
			filtro.setFechaFi(true);
		}
		return proyectoDao.buscar(filtro);
	}

	public List<Proyecto> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroProyectoDto filtro) {
		if (filtro.getFechaInicio() != null) {
			filtro.setFechaIn(true);
		}
		if (filtro.getFechaFin() != null) {
			filtro.setFechaFi(true);
		}
		return proyectoDao
				.buscar(first, pageSize, sortField, sortOrder, filtro);
	}

	public int numeroProyectos(FiltroProyectoDto filtro) {
		return proyectoDao.numeroProyectos(filtro);
	}

	@Override
	public void actualizar(Proyecto proyecto) {
		proyectoDao.actualizar(proyecto);
	}

	@Override
	public void borrar(Proyecto proyecto) {
		proyectoDao.borrar(proyecto);
	}

	@Override
	public void crear(Proyecto proyecto) {
		proyectoDao.crear(proyecto);
	}

	public List<Proyecto> listarProyectos() {
		return proyectoDao.listarProyectos();
	}

	public List<Colaborador> listarColaboradorProyectos(String proyectoWbs) {
		return proyectoDao.listarColaboradorProyectos(proyectoWbs);
	}

	public boolean existeProyectoWbs(Proyecto proyecto) {
		if (!proyectoDao.existeProyectoWbs(proyecto).isEmpty()) {

			return true;
		}
		return false;
	}

	public List<Colaborador> listarColaboradores(String wbs) {
		return proyectoDao.listarColaboradores(wbs);
	}

	public List<Colaborador> listarColaboradores() {
		return proyectoDao.listarColaboradores();
	}
}