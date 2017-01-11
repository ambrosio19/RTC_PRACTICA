package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.controllers.lazyDataModelClases.LazyTitulacionesDataModel;
import net.atos.practica.dto.FiltroTitulacionDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Nivel;
import net.atos.practica.entity.Titulacion;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorAdminBO;
import net.atos.practica.negocio.NivelBO;
import net.atos.practica.negocio.TitulacionBO;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class TitulacionController {
	@Autowired
	private TitulacionBO titulacionBO;

	@Autowired
	private NivelBO nivelBO;

	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;

	private static final String ERROR = "Error:";

	private Titulacion tituloSelec;
	private List<Titulacion> lista;
	private List<Nivel> niveles;
	private FiltroTitulacionDto filtro = new FiltroTitulacionDto();
	private List<Colaborador> colaboradorEnTitulacion;
	private String nombreTitulacionActual;

	private DualListModel<Colaborador> listaDual;
	private List<Colaborador> listaColaboradores;
	private List<Colaborador> listaColaboradoresTit;

	private LazyDataModel<Titulacion> lazyModelListTitulaciones;

	@PostConstruct
	public void init() {
		niveles = nivelBO.listarNiveles();
		listaColaboradoresTit = new ArrayList<Colaborador>();
		lazyModelListTitulaciones = new LazyTitulacionesDataModel(titulacionBO,
				filtro);
	}

	public void nuevaTitulacion() {
		tituloSelec = new Titulacion();
		listarTitulaciones();
	}

	public void buscar() {
		lista = titulacionBO.buscar(filtro);
	}

	public void buscarPaginando() {
		lazyModelListTitulaciones = new LazyTitulacionesDataModel(titulacionBO,
				filtro);
	}

	public void crear() {
		try {
			titulacionBO.crear(tituloSelec);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Titulación insertada correctamente", tituloSelec
									.getNombreTitulacion()));
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();

			tituloSelec.setNombreTitulacion(null);
		}
		buscar();
	}

	public void actualizar() {
		try {
			titulacionBO.actualizar(tituloSelec);
			try {
				for (Colaborador c : listaDual.getSource()) {
					if (!listaColaboradores.contains(c)) {
						c.setTitulacion(null);
						colaboradorAdminBO.actualizar(c);
					}
				}
				for (Colaborador c : listaDual.getTarget()) {
					if (!listaColaboradoresTit.contains(c)) {
						c.setTitulacion(tituloSelec);
						colaboradorAdminBO.actualizar(c);
					}
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Titulación modificada correctamente",
								tituloSelec.getNombreTitulacion()));
			} catch (LlamaloXException e) {
				FacesContext msg = FacesContext.getCurrentInstance();
				msg.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_FATAL, ERROR, e.getMessage()));
				FacesContext.getCurrentInstance().validationFailed();
			}
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscarPaginando();
	}

	public void borrar() {
		try {
			titulacionBO.borrar(tituloSelec);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Titulación borrada correctamente", tituloSelec
									.getNombreTitulacion()));
			buscarPaginando();
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	public void listarTitulaciones() {
		limpiarFormulario();
		nombreTitulacionActual = tituloSelec.getNombreTitulacion();
		if (tituloSelec.getNombreTitulacion() != null) {
			listaColaboradores = titulacionBO.listarColaboradores(tituloSelec
					.getNombreTitulacion());
			listarColaboradoresTit(tituloSelec.getNombreTitulacion());
		} else {
			listaColaboradores = titulacionBO.listarColaboradores();
			listaColaboradoresTit = new ArrayList<Colaborador>();
		}
		listaDual = new DualListModel<Colaborador>(listaColaboradores,
				listaColaboradoresTit);
	}

	public void listarColaboradoresTit(String nombreTitulacion) {
		listaColaboradoresTit = titulacionBO
				.listarColaboradorTitulacion(nombreTitulacion);
	}

	public void obtenerTitulacionSeleccionada(int idTitulacion) {
		colaboradorEnTitulacion = titulacionBO.buscar(idTitulacion);
	}

	public List<Colaborador> getColaboradorEnTitulacion() {
		return colaboradorEnTitulacion;
	}

	public void setColaboradorEnTitulacion(
			List<Colaborador> colaboradorEnTitulacion) {
		this.colaboradorEnTitulacion = colaboradorEnTitulacion;
	}

	private void limpiarFormulario() {
		RequestContext.getCurrentInstance().reset("detalleForm");
	}

	// Getters && Setters
	public String getNombreTitulacionActual() {
		return nombreTitulacionActual;
	}

	public void setNombreTitulacionActual(String nombreTitulacionActual) {
		this.nombreTitulacionActual = nombreTitulacionActual;
	}

	public Titulacion getTituloSelec() {
		return tituloSelec;
	}

	public void setTituloSelec(Titulacion tituloSelec) {
		this.tituloSelec = tituloSelec;
	}

	public List<Titulacion> getLista() {
		return lista;
	}

	public void setLista(List<Titulacion> lista) {
		this.lista = lista;
	}

	public FiltroTitulacionDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroTitulacionDto filtro) {
		this.filtro = filtro;
	}

	public List<Nivel> getNiveles() {
		return niveles;
	}

	public void setNiveles(List<Nivel> niveles) {
		this.niveles = niveles;
	}

	public DualListModel<Colaborador> getListaDual() {
		return listaDual;
	}

	public void setListaDual(DualListModel<Colaborador> listaDual) {
		this.listaDual = listaDual;
	}

	public List<Colaborador> getListaColaboradores() {
		return listaColaboradores;
	}

	public void setListaColaboradores(List<Colaborador> listaColaboradores) {
		this.listaColaboradores = listaColaboradores;
	}

	public List<Colaborador> getListaColaboradoresTit() {
		return listaColaboradoresTit;
	}

	public void setListaColaboradoresTit(List<Colaborador> listaColaboradoresTit) {
		this.listaColaboradoresTit = listaColaboradoresTit;
	}

	public LazyDataModel<Titulacion> getLazyModelListTitulaciones() {
		return lazyModelListTitulaciones;
	}

	public void setLazyModelListTitulaciones(
			LazyDataModel<Titulacion> lazyModelListTitulaciones) {
		this.lazyModelListTitulaciones = lazyModelListTitulaciones;
	}
}
