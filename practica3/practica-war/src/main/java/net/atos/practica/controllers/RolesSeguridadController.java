package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import net.atos.common.identity.Identity;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.RolesSeguridad;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorAdminBO;
import net.atos.practica.negocio.RolesSeguridadBO;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class RolesSeguridadController{
	@Autowired
	private RolesSeguridadBO rolesSeguridadBO;
	
	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;
	
	@Autowired
	private Identity identity;
	
	private List<RolesSeguridad> roles;
	private RolesSeguridad rolSelec;
	private String rol;
	private String guardaRolAux;
	private String nombreActual;
	
	private DualListModel<Colaborador> listaDual;
	private List<Colaborador> listaColaboradores;
	private List<Colaborador> listaColaboradoresRol;
	
	private List<Colaborador> colaboradorEnRol;
	
	private static final String ERROR = "Error:";

	@PostConstruct
	public void init(){
		roles = new ArrayList<RolesSeguridad>();
		rol = "";
		listaColaboradoresRol = new ArrayList<Colaborador>();
	}
	
	public void buscar(){
		roles = rolesSeguridadBO.buscar(rol);
	}
	
	public void guardaRol(){
		guardaRolAux = rolSelec.getNombreRol();
	}
	
	public void nuevoRol(){
		rolSelec = new RolesSeguridad();
		listarRoles();
	}
	
	public void crear(){		
		try {
			rolesSeguridadBO.crear(rolSelec);

		} catch (LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
			
			rolSelec.setNombreRol(null);
		}
		buscar();	
	}
	
	public void borrar(){
		try {
			rolesSeguridadBO.borrar(rolSelec);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol borrado correctamente", rolSelec.getNombreRol()));
			buscar();
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

	public void actualizar(){
		try {
			rolesSeguridadBO.actualizar(rolSelec);			
			try {
				for(Colaborador c : listaDual.getSource()){
					if(!listaColaboradores.contains(c)){
						c.setRol(null);
						colaboradorAdminBO.actualizar(c);
					}
				}
				
				for(Colaborador c : listaDual.getTarget()){
					if(!listaColaboradoresRol.contains(c)){
						if(!c.getCodigo().equals(identity.getUsuario())){
							c.setRol(rolSelec);
							colaboradorAdminBO.actualizar(c);
						}else{
							listaDual.setSource(listaColaboradores);
							listaDual.setTarget(listaColaboradoresRol);
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR, "No puedes cambiarte de rol a ti mismo"));
							FacesContext.getCurrentInstance().validationFailed();
							throw new PersistenceException("");
						}
					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol modificado correctamente", rolSelec.getNombreRol()));
				buscar();
			} catch (LlamaloXException e){
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
	
	public void listarRoles(){
		limpiarFormulario();
		nombreActual = rolSelec.getNombreRol();
		if(rolSelec.getNombreRol() != null){
			listaColaboradores = rolesSeguridadBO.listarColaboradores(rolSelec.getNombreRol());
			listarColaboradoresRol(rolSelec.getNombreRol());
		}
		else{
			listaColaboradores = rolesSeguridadBO.listarColaboradores();
			listaColaboradoresRol = new ArrayList<Colaborador>();
		}
		listaDual = new DualListModel<Colaborador>(listaColaboradores, listaColaboradoresRol);
	}
	
	public void listarColaboradoresRol(String nombreRol) {
		listaColaboradoresRol = rolesSeguridadBO
				.listarColaboradorRol(nombreRol);
	}
	

	public void obtenerRolSeleccionado(int idRol){
		colaboradorEnRol = rolesSeguridadBO.buscar(idRol);
	}
	
	public List<Colaborador> getColaboradorEnRol() {
		return colaboradorEnRol;
	}
	
	private void limpiarFormulario(){
		RequestContext.getCurrentInstance().reset("modifica");
	}

	public void setColaboradorEnRol(List<Colaborador> colaboradorEnRol) {
		this.colaboradorEnRol = colaboradorEnRol;
	}
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public List<RolesSeguridad> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesSeguridad> roles) {
		this.roles = roles;
	}
	
	public RolesSeguridad getRolSelec() {
		return rolSelec;
	}

	public void setRolSelec(RolesSeguridad rolSelec) {
		this.rolSelec = rolSelec;
	}

	public String getGuardaRolAux() {
		return guardaRolAux;
	}

	public void setGuardaRolAux(String guardaRolAux) {
		this.guardaRolAux = guardaRolAux;
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

	public List<Colaborador> getListaColaboradoresRol() {
		return listaColaboradoresRol;
	}

	public void setListaColaboradoresRol(List<Colaborador> listaColaboradoresRol) {
		this.listaColaboradoresRol = listaColaboradoresRol;
	}

	public String getNombreActual() {
		return nombreActual;
	}

	public void setNombreActual(String nombreActual) {
		this.nombreActual = nombreActual;
	}
}
