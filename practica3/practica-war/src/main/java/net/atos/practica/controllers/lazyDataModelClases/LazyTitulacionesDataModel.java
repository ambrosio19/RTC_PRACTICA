package net.atos.practica.controllers.lazyDataModelClases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroTitulacionDto;
import net.atos.practica.entity.Titulacion;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.TitulacionBO;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * LazyDataModel de PrimeFaces para mostrar la tabla de Proyectos paginada en
 * base de datos. Se evitar cargar todos los registros en memoria realizando la
 * paginación de resultados
 */

public class LazyTitulacionesDataModel extends LazyDataModel<Titulacion> {

	private static final long serialVersionUID = -6385230232312310L;

	private TitulacionBO titulacionBO;
	private FiltroTitulacionDto filtroTitulacion;

	private List<Titulacion> datasource;

	private static final String ERROR = "Error: ";

	private static Logger log = org.apache.log4j.Logger
			.getLogger(LazyTitulacionesDataModel.class);

	// Contructor
	public LazyTitulacionesDataModel(TitulacionBO titulacionBO,
			FiltroTitulacionDto filtroTitulacion) {

		this.titulacionBO = titulacionBO;
		this.filtroTitulacion = filtroTitulacion;

	}

	@Override
	public List<Titulacion> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Titulacion> listaParcial = new ArrayList<Titulacion>();

		// RowCount
		try {
			// RowCount
			int tamanoListaParcial = titulacionBO
					.numeroTitulaciones(filtroTitulacion);
			this.setRowCount(tamanoListaParcial);

			listaParcial = titulacionBO.buscar(first, pageSize, sortField,
					sortOrder, filtroTitulacion);

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
	public Object getRowKey(Titulacion titulacion) {
		return titulacion.getIdTitulacion();
	}

	@Override
	public Titulacion getRowData(String rowKey) {
		for (Titulacion titulacion : datasource)
			if (titulacion.getNombreTitulacion().toString().equals(rowKey)) {
				return titulacion;
			}
		return null;

	}

}
