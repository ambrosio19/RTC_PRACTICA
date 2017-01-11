package net.atos.practica.controllers.lazyDataModelClases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorAdminBO;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * LazyDataModel de PrimeFaces para mostrar la tabla de Colaboradores paginada
 * en base de datos. Se evitar cargar todos los registros en memoria realizando
 * la paginación de resultados
 */

public class LazyColaboradorDataModel extends LazyDataModel<Colaborador> {

	private static final long serialVersionUID = -6384564569910L;

	private ColaboradorAdminBO colaboradorBO;
	private FiltroColaboradorAdminDto filtroColaborador;

	private List<Colaborador> datasource;

	private static final String ERROR = "Error: ";

	private static Logger log = org.apache.log4j.Logger
			.getLogger(LazyColaboradorDataModel.class);

	// Contructor
	public LazyColaboradorDataModel(ColaboradorAdminBO colaboradorBO,
			FiltroColaboradorAdminDto filtroColaborador) {

		this.colaboradorBO = colaboradorBO;
		this.filtroColaborador = filtroColaborador;

	}

	@Override
	public List<Colaborador> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Colaborador> listaParcial = new ArrayList<Colaborador>();

		// RowCount
		try {
			// RowCount
			int tamanoListaParcial = colaboradorBO
					.numeroColaboradores(filtroColaborador);
			this.setRowCount(tamanoListaParcial);

			listaParcial = colaboradorBO.buscar(first, pageSize, sortField,
					sortOrder, filtroColaborador);

		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			log.error("<---CLASE: LazyColaboradorDataModel - METODO: Load"
					+ e.getMessage());
		}
		datasource = listaParcial;

		return listaParcial;
	}

	@Override
	public Object getRowKey(Colaborador colaborador) {
		return colaborador.getCodigo();
	}

	@Override
	public Colaborador getRowData(String rowKey) {
		for (Colaborador colaborador : datasource)
			if (colaborador.getCodigo().toString().equals(rowKey)) {
				return colaborador;
			}
		return null;

	}

}
