package net.atos.practica.controllers.lazyDataModelClases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroCapacidadDto;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.CapacidadBO;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * LazyDataModel de PrimeFaces para mostrar la tabla de Proyectos paginada en
 * base de datos. Se evitar cargar todos los registros en memoria realizando la
 * paginación de resultados
 */

public class LazyCapacidadDataModel extends LazyDataModel<Capacidad> {

	private static final long serialVersionUID = -30233213129039910L;

	private CapacidadBO capacidadBO;
	private FiltroCapacidadDto filtroCapacidad;

	private List<Capacidad> datasource;

	private static final String ERROR = "Error: ";

	private static Logger log = org.apache.log4j.Logger
			.getLogger(LazyCapacidadDataModel.class);

	// Contructor
	public LazyCapacidadDataModel(CapacidadBO capacidadBO,
			FiltroCapacidadDto filtro) {

		this.capacidadBO = capacidadBO;
		this.filtroCapacidad = filtro;

	}

	@Override
	public List<Capacidad> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Capacidad> listaParcial = new ArrayList<Capacidad>();

		// RowCount
		try {
			// RowCount
			int tamanoListaParcial = capacidadBO
					.numeroCapacidades(filtroCapacidad);
			this.setRowCount(tamanoListaParcial);

			listaParcial = capacidadBO.buscar(first, pageSize, sortField,
					sortOrder, filtroCapacidad);

		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			log.error("<---CLASE: LazyPromocionDataModel - METODO: Load"
					+ e.getMessage());
		}
		datasource = listaParcial;

		return listaParcial;
	}

	@Override
	public Object getRowKey(Capacidad capacidad) {
		return capacidad.getNombreCapacidad();
	}

	@Override
	public Capacidad getRowData(String rowKey) {
		for (Capacidad capacidad : datasource)
			if (capacidad.getNombreCapacidad().toString().equals(rowKey)) {
				return capacidad;
			}
		return null;

	}

}
