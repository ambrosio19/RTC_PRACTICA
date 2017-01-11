package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.TitulacionDao;
import net.atos.practica.dto.FiltroTitulacionDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Titulacion;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TitulacionBO implements
		InterfaceBO<Titulacion, FiltroTitulacionDto> {

	@Autowired
	private TitulacionDao titulacionDao;

	@Override
	public List<Titulacion> buscar(FiltroTitulacionDto filtro) {
		return titulacionDao.buscar(filtro);
	}

	public List<Titulacion> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroTitulacionDto filtro) {
		return titulacionDao.buscar(first, pageSize, sortField, sortOrder,
				filtro);
	}

	public int numeroTitulaciones(FiltroTitulacionDto filtro) {
		return titulacionDao.numeroTitulaciones(filtro);
	}

	public boolean buscar(String titulacion) {
		return titulacionDao.buscar(titulacion).isEmpty();
	}

	public List<Colaborador> buscar(int idTitulacion) {
		return titulacionDao.buscar(idTitulacion);
	}

	@Override
	public void actualizar(Titulacion t) {
		titulacionDao.actualizar(t);
	}

	@Override
	public void borrar(Titulacion t) {
		titulacionDao.borrar(t);
	}

	@Override
	public void crear(Titulacion t) {
		titulacionDao.crear(t);
	}

	public List<Titulacion> listarTitulaciones() {
		return titulacionDao.listarTitulaciones();
	}

	public List<Colaborador> listarColaboradorTitulacion(String nombreTitulacion) {
		return titulacionDao.listarColaboradorTitulacion(nombreTitulacion);
	}

	public List<Colaborador> listarColaboradores(String nombreTitulacion) {
		return titulacionDao.listarColaboradores(nombreTitulacion);
	}

	public List<Colaborador> listarColaboradores() {
		return titulacionDao.listarColaboradores();
	}

}