package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.CapacidadDao;
import net.atos.practica.dto.FiltroCapacidadDto;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CapacidadBO implements InterfaceBO<Capacidad, String> {

	@Autowired
	private CapacidadDao capacidadDao;

	@Override
	public List<Capacidad> buscar(String capacidad) {
		return capacidadDao.buscar(capacidad);
	}

	public List<Capacidad> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroCapacidadDto filtro) {
		return capacidadDao.buscar(first, pageSize, sortField, sortOrder,
				filtro);
	}

	public int numeroCapacidades(FiltroCapacidadDto filtro) {
		return capacidadDao.numeroCapacidades(filtro);
	}

	public boolean guardar(Capacidad nuevaCapacidad) { // controlar si existe o
														// no
		if (capacidadDao.guardar(nuevaCapacidad).isEmpty()) {
			capacidadDao.crear(nuevaCapacidad);
			return true;
		}
		return false;
	}

	public Boolean buscarIgual(String filtro) {
		return capacidadDao.buscarIgual(filtro).isEmpty();
	}

	@Override
	public void actualizar(Capacidad c) {
		capacidadDao.actualizar(c);
	}

	@Override
	public void borrar(Capacidad c) {
		capacidadDao.borrar(c);
	}

	@Override
	public void crear(Capacidad c) {
		capacidadDao.crear(c);
	}

	public List<Capacidad> listarCapacidades() {
		return capacidadDao.listarCapacidades();
	}

	public List<Colaborador> buscarColaboradoresCapacidadActual(String capacidad) { // buscar
																					// los
																					// colaboradores
																					// que
																					// están
																					// en
																					// la
																					// capacidad
																					// actual
		return capacidadDao.buscarColaboradoresCapacidadActual(capacidad);
	}

	public List<Colaborador> buscarColaboradores(String capacidad) { // busca
																		// los
																		// colaboradores
																		// que
																		// no
																		// estén
																		// en la
																		// capacidad
		return capacidadDao.buscarColaboradoes(capacidad);
	}

	public List<Colaborador> buscarListaColaboradores() {
		return capacidadDao.buscarListaColaboradores();
	}

}