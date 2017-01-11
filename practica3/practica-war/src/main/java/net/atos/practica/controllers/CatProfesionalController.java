package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import net.atos.common.identity.Identity;
import net.atos.practica.dto.FiltroCatProfesionalDto;
import net.atos.practica.entity.CategoriaProfesional;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.CategoriaProfesionalBO;
import net.atos.practica.negocio.ColaboradorAdminBO;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class CatProfesionalController{

	@Autowired
	private CategoriaProfesionalBO catProfesionalBO;

	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;

	@Autowired
	private Identity identity;
	
	private static final String ERROR = "Error:";

	private CategoriaProfesional catProfesionalSelec;

	private List<CategoriaProfesional> lista;
	private FiltroCatProfesionalDto filtro = new FiltroCatProfesionalDto();
	private List<Colaborador> colaboradoresEnCategoria;

	private DualListModel<Colaborador> listaDual;
	private List<Colaborador> listaColaboradores;
	private List<Colaborador> listaColaboradoresCat;

	@PostConstruct
	public void init() {
		listaColaboradoresCat = new ArrayList<Colaborador>();
	}

	public void nuevoEstatus() {
		catProfesionalSelec = new CategoriaProfesional();
		listarColaboradores();
	}

	public void buscar() {
		lista = catProfesionalBO.buscar(filtro);
	}
	
	public void crear() {
		try {
			catProfesionalBO.crear(catProfesionalSelec);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Categoría Profesional insertada correctamente",
							catProfesionalSelec.getNombreCategoriaPro()));
		} catch (LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
			
			catProfesionalSelec.setNombreCategoriaPro(null);
		}
		buscar();
	}

	public void actualizar() {
		try {
			catProfesionalBO.actualizar(catProfesionalSelec);
			try {
				for (Colaborador c : listaDual.getSource()) {
					if (!listaColaboradores.contains(c)) {
						c.setCategoriaPro(null);
						colaboradorAdminBO.actualizar(c);
					}
				}
				for (Colaborador c : listaDual.getTarget()) {
					if (!listaColaboradoresCat.contains(c)) {
						c.setCategoriaPro(catProfesionalSelec);
						colaboradorAdminBO.actualizar(c);
					}
				}
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										"Categoría Profesional modificada correctamente",
										catProfesionalSelec
												.getNombreCategoriaPro()));
			} catch (LlamaloXException e){
				listaDual.setSource(listaColaboradores);
				listaDual.setTarget(listaColaboradoresCat);
				FacesContext msg = FacesContext.getCurrentInstance();
				msg.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
								e.getMessage()));
				FacesContext.getCurrentInstance().validationFailed();
			}
		} catch (LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscar();
	}

	public void borrar() {
		try {
			catProfesionalBO.borrar(catProfesionalSelec);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Categoría Profesional borrada correctamente",
							catProfesionalSelec.getNombreCategoriaPro()));
			buscar();
		} catch (LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	public void obtenerCategoriaSeleccionada(int idCategoria) {
		colaboradoresEnCategoria = catProfesionalBO.buscar(idCategoria);
	}

	public void listarColaboradores() {
		limpiarFormulario();
		if (catProfesionalSelec.getNombreCategoriaPro() != null) {
			listaColaboradores = catProfesionalBO
					.listarColaboradores(catProfesionalSelec
							.getNombreCategoriaPro());
			listarColaboradoresCat(catProfesionalSelec.getNombreCategoriaPro());
		} else {
			listaColaboradores = catProfesionalBO.listarColaboradores();
			listaColaboradoresCat = new ArrayList<Colaborador>();
		}
		listaDual = new DualListModel<Colaborador>(listaColaboradores,
				listaColaboradoresCat);
	}

	public void listarColaboradoresCat(String nombreCatProfesional) {
		listaColaboradoresCat = catProfesionalBO
				.listarColaboradorCategorias(nombreCatProfesional);
	}
	
	private void limpiarFormulario(){
		RequestContext.getCurrentInstance().reset("detalleForm");
	}
	
	// Getters && Setters

	public CategoriaProfesional getCatProfesionalSelec() {
		return catProfesionalSelec;
	}

	public List<Colaborador> getColaboradoresEnCategoria() {
		return colaboradoresEnCategoria;
	}

	public void setColaboradoresEnCategoria(
			List<Colaborador> colaboradoresEnCategoria) {
		this.colaboradoresEnCategoria = colaboradoresEnCategoria;
	}

	public void setCatProfesionalSelec(CategoriaProfesional catProfesionalSelec) {
		this.catProfesionalSelec = catProfesionalSelec;
	}

	public List<CategoriaProfesional> getLista() {
		return lista;
	}

	public void setLista(List<CategoriaProfesional> lista) {
		this.lista = lista;
	}

	public FiltroCatProfesionalDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroCatProfesionalDto filtro) {
		this.filtro = filtro;
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

	public List<Colaborador> getListaColaboradoresCat() {
		return listaColaboradoresCat;
	}

	public void setListaColaboradoresCat(List<Colaborador> listaColaboradoresCat) {
		this.listaColaboradoresCat = listaColaboradoresCat;
	}
}
