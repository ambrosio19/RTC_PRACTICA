package net.atos.practica.controllers.lazyDataModelClases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroPromocionDto;
import net.atos.practica.entity.Promocion;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.PromocionBO;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * LazyDataModel de PrimeFaces para mostrar la tabla de Proyectos paginada en
 * base de datos. Se evitar cargar todos los registros en memoria realizando la
 * paginación de resultados
 */

public class LazyPromocionDataModel extends LazyDataModel<Promocion> {

	private static final long serialVersionUID = -30233829039910L;

	private PromocionBO promocionBO;
	private FiltroPromocionDto filtroPromocion;

	private List<Promocion> datasource;

	private static final String ERROR = "Error: ";

	private static Logger log = org.apache.log4j.Logger
			.getLogger(LazyPromocionDataModel.class);

	// Contructor
	public LazyPromocionDataModel(PromocionBO promocionBO,
			FiltroPromocionDto filtroPromocion) {

		this.promocionBO = promocionBO;
		this.filtroPromocion = filtroPromocion;

	}

	@Override
	public List<Promocion> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Promocion> listaParcial = new ArrayList<Promocion>();

		// RowCount
		try {
			// RowCount
			int tamanoListaParcial = promocionBO
					.numeroPromociones(filtroPromocion);
			this.setRowCount(tamanoListaParcial);

			listaParcial = promocionBO.buscar(first, pageSize, sortField,
					sortOrder, filtroPromocion);

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
	public Object getRowKey(Promocion promocion) {
		return promocion;
	}

	@Override
	public Promocion getRowData(String rowKey) {
		for (Promocion promocion : datasource)
			if (promocion.getNombrePromocion().toString().equals(rowKey)) {
				return promocion;
			}
		return null;

	}

}
