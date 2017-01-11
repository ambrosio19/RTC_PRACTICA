package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.controllers.lazyDataModelClases.LazyColaboradorDataModel;
import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.CategoriaProfesional;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Estatus;
import net.atos.practica.entity.Promocion;
import net.atos.practica.entity.Proyecto;
import net.atos.practica.entity.RolesSeguridad;
import net.atos.practica.entity.Titulacion;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.CapacidadBO;
import net.atos.practica.negocio.CategoriaProfesionalBO;
import net.atos.practica.negocio.ColaboradorAdminBO;
import net.atos.practica.negocio.EstatusBO;
import net.atos.practica.negocio.HistoricoCapacidadesBO;
import net.atos.practica.negocio.HistoricoEstatusBO;
import net.atos.practica.negocio.HistoricoProyectoBO;
import net.atos.practica.negocio.PromocionBO;
import net.atos.practica.negocio.ProyectoBO;
import net.atos.practica.negocio.RolesSeguridadBO;
import net.atos.practica.negocio.TitulacionBO;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ColaboradorAdminController {

	private FiltroColaboradorAdminDto filtro = new FiltroColaboradorAdminDto();
	private List<Colaborador> lista;
	private Colaborador colaboradorSelec;

	private List<RolesSeguridad> roles;
	private List<Proyecto> proyectos;
	private List<Estatus> listaEstatus;
	private List<CategoriaProfesional> categoriasProfesionales;
	private List<Promocion> promociones;
	private List<Capacidad> capacidades;
	private List<Titulacion> titulaciones;
	private Proyecto proyectoActual;
	private Capacidad capacidadActual;
	private Estatus estatusActual;
	private List<Integer> nivelesGCM;

	private static final int GCMIN = 0;
	private static final int GCMAX = 10;

	private static final String ERROR = "Error:";

	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;

	@Autowired
	private RolesSeguridadBO rolesSeguridadBO;

	@Autowired
	private ProyectoBO proyectoBO;

	@Autowired
	private EstatusBO estatusBO;

	@Autowired
	private PromocionBO promocionBO;

	@Autowired
	private CapacidadBO capacidadBO;

	@Autowired
	private CategoriaProfesionalBO categoriaProfesionalBO;

	@Autowired
	private TitulacionBO titulacionBO;

	@Autowired
	private HistoricoProyectoBO historicoProyectoBO;

	@Autowired
	private HistoricoCapacidadesBO historicoCapacidadesBO;

	@Autowired
	private HistoricoEstatusBO historicoEstatusBO;

	private LazyDataModel<Colaborador> lazyModelListColaborador;

	@PostConstruct
	public void init() {
		roles = rolesSeguridadBO.listarRoles();
		listaEstatus = estatusBO.listarEstatus();
		promociones = promocionBO.listarPromociones();
		capacidades = capacidadBO.listarCapacidades();
		categoriasProfesionales = categoriaProfesionalBO
				.listarCategoriasProfesionales();
		titulaciones = titulacionBO.listarTitulaciones();
		nivelesGCM = new ArrayList<Integer>();
		for (int i = GCMIN; i < GCMAX; i++) {
			nivelesGCM.add(i);
		}

		lazyModelListColaborador = new LazyColaboradorDataModel(
				colaboradorAdminBO, filtro);
	}

	public void nuevoColaborador() {
		limpiarFormulario();
		colaboradorSelec = new Colaborador();
		colaboradorSelec.setPwd("CEDeI_PWD1");
		colaboradorSelec.setFoto("/fotos_usuarios/avatar_colaborador.jpg");
		proyectos = proyectoBO.listarProyectos();
	}

	public void guardarDatosActuales() {
		limpiarFormulario();
		proyectos = proyectoBO.listarProyectos();
		proyectoActual = colaboradorSelec.getProyecto();
		capacidadActual = colaboradorSelec.getCapacidad();
		estatusActual = colaboradorSelec.getEstatus();
	}

	public void crear() {
		try {
			if (colaboradorSelec.getFechaBaja() != null
					&& colaboradorSelec.getFechaBaja().before(
							colaboradorSelec.getFechaAlta())) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										ERROR,
										"La fecha de baja no puede ser anterior a la fecha de alta"));
				colaboradorSelec.setFechaBaja(null);
				colaboradorSelec.setCodigo(null);
				FacesContext.getCurrentInstance().validationFailed();
			} else {
				colaboradorAdminBO.crear(colaboradorSelec);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Colaborador insertado correctamente",
								colaboradorSelec.getCodigo()));
				if (colaboradorSelec.getProyecto() != null) {
					historicoProyectoBO.insertar(
							colaboradorSelec.getProyecto(), colaboradorSelec);
				}
				if (colaboradorSelec.getCapacidad() != null) {
					historicoCapacidadesBO.insertar(
							colaboradorSelec.getCapacidad(), colaboradorSelec);
				}
				historicoEstatusBO.insertar(colaboradorSelec.getEstatus(),
						colaboradorSelec);
			}
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
			colaboradorSelec.setCodigo(null);
		}
		buscar();
	}

	public void actualizar() {
		try {
			if (colaboradorSelec.getFechaBaja() != null
					&& colaboradorSelec.getFechaBaja().before(
							colaboradorSelec.getFechaAlta())) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_FATAL,
										ERROR,
										"La fecha de baja no puede ser anterior a la fecha de alta"));
				colaboradorSelec.setFechaBaja(null);
				FacesContext.getCurrentInstance().validationFailed();
			} else {
				colaboradorAdminBO.actualizar(colaboradorSelec);
				if (!Objects.equals(colaboradorSelec.getProyecto(),
						proyectoActual)) {
					historicoProyectoBO.insertar(
							colaboradorSelec.getProyecto(), colaboradorSelec);
				}
				if (!Objects.equals(colaboradorSelec.getCapacidad(),
						capacidadActual)) {
					historicoCapacidadesBO.insertar(
							colaboradorSelec.getCapacidad(), colaboradorSelec);
				}
				if (!Objects.equals(colaboradorSelec.getEstatus(),
						estatusActual)) {
					historicoEstatusBO.insertar(colaboradorSelec.getEstatus(),
							colaboradorSelec);
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Colaborador modificado correctamente",
								colaboradorSelec.getCodigo()));
			}
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscar();
	}

	public void borrar() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!auth.getName().equalsIgnoreCase(colaboradorSelec.getCodigo())) {
			try {
				colaboradorSelec.setCapacidad(null);
				colaboradorSelec.setProyecto(null);
				for (Estatus e : listaEstatus) {
					if (e.getNombreEstatus().equals("BAJA")) {
						colaboradorSelec.setEstatus(e);
						break;
					}
				}
				colaboradorAdminBO.actualizar(colaboradorSelec);
				if (!Objects.equals(colaboradorSelec.getProyecto(),
						proyectoActual)) {
					historicoProyectoBO.insertar(
							colaboradorSelec.getProyecto(), colaboradorSelec);
				}
				if (!Objects.equals(colaboradorSelec.getCapacidad(),
						capacidadActual)) {
					historicoCapacidadesBO.insertar(
							colaboradorSelec.getCapacidad(), colaboradorSelec);
				}
				if (!Objects.equals(colaboradorSelec.getEstatus(),
						estatusActual)) {
					historicoEstatusBO.insertar(colaboradorSelec.getEstatus(),
							colaboradorSelec);
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Colaborador borrado correctamente",
								colaboradorSelec.getCodigo()));
				buscar();
			} catch (LlamaloXException e) {
				FacesContext msg = FacesContext.getCurrentInstance();
				msg.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_FATAL, ERROR, e.getMessage()));
				FacesContext.getCurrentInstance().validationFailed();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR,
							"No puedes borrarte a ti mismo"));
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	private void limpiarFormulario() {
		RequestContext.getCurrentInstance().reset("detalleForm");
	}

	public void buscar() {
		lazyModelListColaborador = new LazyColaboradorDataModel(
				colaboradorAdminBO, filtro);
		// lista = colaboradorAdminBO.buscar(filtro);
	}

	public FiltroColaboradorAdminDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroColaboradorAdminDto filtro) {
		this.filtro = filtro;
	}

	public List<Colaborador> getLista() {
		return lista;
	}

	public void setLista(List<Colaborador> lista) {
		this.lista = lista;
	}

	public Colaborador getColaboradorSelec() {
		return colaboradorSelec;
	}

	public void setColaboradorSelec(Colaborador colaboradorSelec) {
		this.colaboradorSelec = colaboradorSelec;
	}

	public List<RolesSeguridad> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesSeguridad> roles) {
		this.roles = roles;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public List<Estatus> getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(List<Estatus> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	public List<CategoriaProfesional> getCategoriasProfesionales() {
		return categoriasProfesionales;
	}

	public void setCategoriasProfesionales(
			List<CategoriaProfesional> categoriasProfesionales) {
		this.categoriasProfesionales = categoriasProfesionales;
	}

	public List<Promocion> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
	}

	public List<Capacidad> getCapacidades() {
		return capacidades;
	}

	public void setCapacidades(List<Capacidad> capacidades) {
		this.capacidades = capacidades;
	}

	public List<Titulacion> getTitulaciones() {
		return titulaciones;
	}

	public void setTitulaciones(List<Titulacion> titulaciones) {
		this.titulaciones = titulaciones;
	}

	public Proyecto getProyectoActual() {
		return proyectoActual;
	}

	public void setProyectoActual(Proyecto proyectoActual) {
		this.proyectoActual = proyectoActual;
	}

	public List<Integer> getNivelesGCM() {
		return nivelesGCM;
	}

	public void setNivelesGCM(List<Integer> nivelesGCM) {
		this.nivelesGCM = nivelesGCM;
	}

	public LazyDataModel<Colaborador> getLazyModelListColaborador() {
		return lazyModelListColaborador;
	}

	public void setLazyModelListColaborador(
			LazyDataModel<Colaborador> lazyModelListColaborador) {
		this.lazyModelListColaborador = lazyModelListColaborador;
	}

}