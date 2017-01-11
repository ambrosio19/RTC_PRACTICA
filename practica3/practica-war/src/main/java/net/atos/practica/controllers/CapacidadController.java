package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.controllers.lazyDataModelClases.LazyCapacidadDataModel;
import net.atos.practica.dto.FiltroCapacidadDto;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.CapacidadBO;
import net.atos.practica.negocio.ColaboradorBO;
import net.atos.practica.negocio.HistoricoCapacidadesBO;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class CapacidadController {
	@Autowired
	private CapacidadBO capacidadBO;

	@Autowired
	private ColaboradorBO colaboradorBO;

	@Autowired
	private HistoricoCapacidadesBO historicoCapacidadesBO;

	private List<Capacidad> capacidades;
	private Capacidad nuevaCapacidad;
	private String capacidad;
	private String auxCapacidad; // variable donde se guardará el nombre de la
									// capacidad antes de ser modificada para
									// ver si coincide
	private static final String ERROR = "Error: ";
	private DualListModel<Colaborador> dualCapacidad;
	private List<Colaborador> listaColaboradoresConCapacidad;
	private List<Colaborador> listaColaboradoresSinCapacidad;
	private List<Colaborador> colaboradorEnCapacidad;
	private FiltroCapacidadDto filtro = new FiltroCapacidadDto();
	private LazyDataModel<Capacidad> lazyModelListCapacidades;

	@PostConstruct
	public void init() {
		capacidades = new ArrayList<Capacidad>();
		listaColaboradoresConCapacidad = new ArrayList<Colaborador>();
		lazyModelListCapacidades = new LazyCapacidadDataModel(capacidadBO,
				filtro);
	}

	public void buscar() {
		capacidades = capacidadBO.buscar(capacidad);
	}

	public void buscarPaginando() {
		lazyModelListCapacidades = new LazyCapacidadDataModel(capacidadBO,
				filtro);
	}

	public void nuevaCapacidad() {
		nuevaCapacidad = new Capacidad();
		listarColaboradores();
	}

	public void listarColaboradores() {
		limpiarFormulario();
		auxCapacidad = nuevaCapacidad.getNombreCapacidad();

		if (nuevaCapacidad.getNombreCapacidad() != null) {
			listaColaboradoresConCapacidad = capacidadBO
					.buscarColaboradoresCapacidadActual(nuevaCapacidad
							.getNombreCapacidad());
			listaColaboradoresSinCapacidad = capacidadBO
					.buscarColaboradores(nuevaCapacidad.getNombreCapacidad());
		} else {
			listaColaboradoresSinCapacidad = capacidadBO
					.buscarListaColaboradores();
			listaColaboradoresConCapacidad = new ArrayList<Colaborador>();
		}
		dualCapacidad = new DualListModel<Colaborador>(
				listaColaboradoresSinCapacidad, listaColaboradoresConCapacidad);
	}

	public void actualizar() {
		if ((capacidadBO.buscarIgual(nuevaCapacidad.getNombreCapacidad()))
				|| (auxCapacidad.equals(nuevaCapacidad.getNombreCapacidad()))) { // no
																					// existe
																					// una
																					// capacidad
																					// que
																					// se
																					// llame
																					// igual
			capacidadBO.actualizar(nuevaCapacidad);

			for (Colaborador c : dualCapacidad.getTarget()) {
				if (!listaColaboradoresConCapacidad.contains(c)) {
					c.setCapacidad(nuevaCapacidad);
					colaboradorBO.actualizar(c);
					historicoCapacidadesBO.insertar(nuevaCapacidad, c);
				}
			}

			for (Colaborador c : dualCapacidad.getSource()) {
				if (!listaColaboradoresSinCapacidad.contains(c)) {
					c.setCapacidad(null);
					colaboradorBO.actualizar(c);
					historicoCapacidadesBO.insertar(null, c);
				}
			}

			capacidad = "";
			buscar();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Capacidad insertada correctamente", nuevaCapacidad
									.getNombreCapacidad()));
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR,
							"Esa capacidad ya existe"));
			capacidad = "";
			buscar();
		}
		capacidad = "";
		buscar();
	}

	public void crear() {
		if (capacidadBO.buscarIgual(nuevaCapacidad.getNombreCapacidad())
				|| ((nuevaCapacidad.getNombreCapacidad()).equals(auxCapacidad))) { // si
																					// el
																					// bo
																					// devuelve
																					// que
																					// la
																					// lista
																					// está
																					// vacía
																					// quiere
																					// decir
																					// que
																					// no
																					// existe
																					// uno
																					// igual
			capacidadBO.crear(nuevaCapacidad);

			for (Colaborador c : dualCapacidad.getTarget()) {
				if (!listaColaboradoresConCapacidad.contains(c)) {
					c.setCapacidad(nuevaCapacidad);
					colaboradorBO.actualizar(c);
					historicoCapacidadesBO.insertar(nuevaCapacidad, c);
				}
			}

			capacidad = "";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Capacidad guardada correctamente", nuevaCapacidad
									.getNombreCapacidad()));
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR,
							"Esa capacidad ya existe"));
			nuevaCapacidad.setNombreCapacidad(null);
			capacidad = "";
		}
		buscar();
	}

	public void borrar() {
		try {
			capacidadBO.borrar(nuevaCapacidad);
			capacidad = "";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Capacidad borrada correctamente", nuevaCapacidad
									.getNombreCapacidad()));

		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscar();
	}

	public void obtenerCapacidadSeleccionada(String cap) {
		colaboradorEnCapacidad = capacidadBO
				.buscarColaboradoresCapacidadActual(cap);
	}

	private void limpiarFormulario() {
		RequestContext.getCurrentInstance().reset("modifica");
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public Capacidad getNuevaCapacidad() {
		return nuevaCapacidad;
	}

	public void setNuevaCapacidad(Capacidad nuevaCapacidad) {
		this.nuevaCapacidad = nuevaCapacidad;
	}

	public List<Capacidad> getCapacidades() {
		return capacidades;
	}

	public void setCapacidades(List<Capacidad> capacidades) {
		this.capacidades = capacidades;
	}

	public DualListModel<Colaborador> getDualCapacidad() {
		return dualCapacidad;
	}

	public void setDualCapacidad(DualListModel<Colaborador> dualCapacidad) {
		this.dualCapacidad = dualCapacidad;
	}

	public List<Colaborador> getListaColaboradoresConCapacidad() {
		return listaColaboradoresConCapacidad;
	}

	public void setListaColaboradoresConCapacidad(
			List<Colaborador> listaColaboradoresConCapacidad) {
		this.listaColaboradoresConCapacidad = listaColaboradoresConCapacidad;
	}

	public List<Colaborador> getListaColaboradoresSinCapacidad() {
		return listaColaboradoresSinCapacidad;
	}

	public void setListaColaboradoresSinCapacidad(
			List<Colaborador> listaColaboradoresSinCapacidad) {
		this.listaColaboradoresSinCapacidad = listaColaboradoresSinCapacidad;
	}

	public String getAuxCapacidad() {
		return auxCapacidad;
	}

	public void setAuxCapacidad(String auxCapacidad) {
		this.auxCapacidad = auxCapacidad;
	}

	public List<Colaborador> getColaboradorEnCapacidad() {
		return colaboradorEnCapacidad;
	}

	public void setColaboradorEnCapacidad(
			List<Colaborador> colaboradorEnCapacidad) {
		this.colaboradorEnCapacidad = colaboradorEnCapacidad;
	}

	public FiltroCapacidadDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroCapacidadDto filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Capacidad> getLazyModelListCapacidades() {
		return lazyModelListCapacidades;
	}

	public void setLazyModelListCapacidades(
			LazyDataModel<Capacidad> lazyModelListCapacidades) {
		this.lazyModelListCapacidades = lazyModelListCapacidades;
	}

}
