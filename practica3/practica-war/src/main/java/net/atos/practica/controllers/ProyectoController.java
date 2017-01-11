package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.controllers.lazyDataModelClases.LazyProyectosDataModel;
import net.atos.practica.dto.FiltroProyectoDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Proyecto;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorAdminBO;
import net.atos.practica.negocio.HistoricoProyectoBO;
import net.atos.practica.negocio.ProyectoBO;

import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ProyectoController {

	private FiltroProyectoDto filtro = new FiltroProyectoDto();
	private List<Proyecto> lista;
	private Proyecto proyectoSelec;
	private static final String ERROR = "Error: ";

	private DualListModel<Colaborador> listaDual;
	private List<Colaborador> listaColaboradores;
	private List<Colaborador> listaColaboradoresPro;

	@Autowired
	private ProyectoBO proyectoBO;

	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;

	@Autowired
	private HistoricoProyectoBO historicoProyectoBO;

	private LazyDataModel<Proyecto> lazyModelListProyectos;

	@PostConstruct
	public void init() {
		listaColaboradoresPro = new ArrayList<Colaborador>();

		lazyModelListProyectos = new LazyProyectosDataModel(proyectoBO, filtro);

	}

	public void nuevoProyecto() {
		proyectoSelec = new Proyecto();
		listarColaboradores();
	}

	public void crear() {
		try {
			if (proyectoSelec.getFechaFinPro() != null
					&& proyectoSelec.getFechaFinPro().before(
							proyectoSelec.getFechaInicioPro())) {
				proyectoSelec.setWbs(null);
				proyectoSelec.setFechaFinPro(null);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR,
								"La fecha de finalización es errónea"));
				FacesContext.getCurrentInstance().validationFailed();
			} else {
				proyectoBO.crear(proyectoSelec);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Proyecto insertado correctamente",
								proyectoSelec.getWbs()));
			}
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();

			proyectoSelec.setWbs(null);
		}
		buscar();
	}

	public void actualizar() {
		try {
			if (proyectoSelec.getFechaFinPro() != null
					&& proyectoSelec.getFechaFinPro().before(
							proyectoSelec.getFechaInicioPro())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR,
								"La fecha de finalización es errónea"));
				proyectoSelec.setFechaFinPro(null);
				FacesContext.getCurrentInstance().validationFailed();
			} else {
				proyectoBO.actualizar(proyectoSelec);

				for (Colaborador c : listaDual.getSource()) {
					if (!listaColaboradores.contains(c)) {
						c.setProyecto(null);
						colaboradorAdminBO.actualizar(c);
						historicoProyectoBO.insertar(null, c);
					}
				}

				for (Colaborador c : listaDual.getTarget()) {
					if (!listaColaboradoresPro.contains(c)) {
						c.setProyecto(proyectoSelec);
						colaboradorAdminBO.actualizar(c);
						historicoProyectoBO.insertar(proyectoSelec, c);
					}
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Proyecto modificado correctamente",
								proyectoSelec.getWbs()));
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
			proyectoBO.borrar(proyectoSelec);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("Proyecto borrado correctamente",
							proyectoSelec.getWbs()));
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscarPaginando();
	}

	public void buscar() {
		filtro.setFechaIn(false);
		filtro.setFechaFi(false);
		lista = proyectoBO.buscar(filtro);
	}

	public void buscarPaginando() {
		listaColaboradoresPro = new ArrayList<Colaborador>();
		lazyModelListProyectos = new LazyProyectosDataModel(proyectoBO, filtro);

	}

	public void listarColaboradores() {
		if (proyectoSelec.getWbs() != null) {
			listaColaboradores = proyectoBO.listarColaboradores(proyectoSelec
					.getWbs());
			listarColaboradoresPro(proyectoSelec.getWbs());
		} else {
			listaColaboradores = proyectoBO.listarColaboradores();
			listaColaboradoresPro = new ArrayList<Colaborador>();
		}
		listaDual = new DualListModel<Colaborador>(listaColaboradores,
				listaColaboradoresPro);
	}

	// GETTERS AND SETTERS
	public FiltroProyectoDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroProyectoDto filtro) {
		this.filtro = filtro;
	}

	public List<Proyecto> getLista() {
		return lista;
	}

	public void setLista(List<Proyecto> lista) {
		this.lista = lista;
	}

	public Proyecto getProyectoSelec() {
		return proyectoSelec;
	}

	public void setProyectoSelec(Proyecto proyectoSelec) {
		this.proyectoSelec = proyectoSelec;
	}

	public List<Colaborador> getListaColaboradoresPro() {
		return listaColaboradoresPro;
	}

	public void setListaColaboradoresPro(List<Colaborador> listaColaboradoresPro) {
		this.listaColaboradoresPro = listaColaboradoresPro;
	}

	public void listarColaboradoresPro(String proyectoWbs) {
		listaColaboradoresPro = proyectoBO
				.listarColaboradorProyectos(proyectoWbs);
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

	public LazyDataModel<Proyecto> getLazyModelListProyectos() {
		return lazyModelListProyectos;
	}

	public void setLazyModelListProyectos(
			LazyDataModel<Proyecto> lazyModelListProyectos) {
		this.lazyModelListProyectos = lazyModelListProyectos;
	}

	public void execute() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Executed",
						"Using RemoteCommand."));
	}
}