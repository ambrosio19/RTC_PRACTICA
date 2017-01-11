package net.atos.practica.controllers.lazyDataModelClases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroProyectoDto;
import net.atos.practica.entity.Proyecto;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ProyectoBO;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * LazyDataModel de PrimeFaces para mostrar la tabla de Proyectos paginada en
 * base de datos. Se evitar cargar todos los registros en memoria realizando la
 * paginación de resultados
 */

public class LazyProyectosDataModel extends LazyDataModel<Proyecto> {

	private static final long serialVersionUID = -6385230233829039910L;

	private ProyectoBO proyectoBO;
	private FiltroProyectoDto filtroProyecto;

	private List<Proyecto> datasource;

	private static final String ERROR = "Error: ";

	private static Logger log = org.apache.log4j.Logger
			.getLogger(LazyProyectosDataModel.class);

	// Contructor
	public LazyProyectosDataModel(ProyectoBO proyectoBO,
			FiltroProyectoDto filtroProyecto) {

		this.proyectoBO = proyectoBO;
		this.filtroProyecto = filtroProyecto;

	}

	@Override
	public List<Proyecto> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Proyecto> listaParcial = new ArrayList<Proyecto>();

		// RowCount
		try {
			// RowCount
			int tamanoListaParcial = proyectoBO.numeroProyectos(filtroProyecto);
			this.setRowCount(tamanoListaParcial);

			listaParcial = proyectoBO.buscar(first, pageSize, sortField,
					sortOrder, filtroProyecto);

		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			log.error("<---CLASE: LazyProyectoDataModel - METODO: Load"
					+ e.getMessage());
		}
		datasource = listaParcial;

		return listaParcial;
	}

	@Override
	public Object getRowKey(Proyecto proyecto) {
		return proyecto.getWbs();
	}

	@Override
	public Proyecto getRowData(String rowKey) {
		for (Proyecto proyecto : datasource)
			if (proyecto.getWbs().toString().equals(rowKey)) {
				return proyecto;
			}
		return null;

	}

}
