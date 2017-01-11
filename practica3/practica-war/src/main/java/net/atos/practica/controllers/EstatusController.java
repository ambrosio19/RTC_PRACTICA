package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.dto.FiltroEstatusDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Estatus;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorAdminBO;
import net.atos.practica.negocio.EstatusBO;
import net.atos.practica.negocio.HistoricoEstatusBO;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class EstatusController{
	@Autowired
	private EstatusBO estatusBO;
	
	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;
	
	@Autowired
	private HistoricoEstatusBO historicoEstatusBO;
	
	private Estatus estatusSelec;
	private List<Estatus> lista;
	private FiltroEstatusDto filtro = new FiltroEstatusDto();
	
	private DualListModel<Colaborador> listaDual;
	private List<Colaborador> listaColaboradores;
	private List<Colaborador> listaColaboradoresEst;
	
	private List<Colaborador> colaboradorEnEstatus;
	
	private static final String ERROR = "Error:";
	
	@PostConstruct
	public void init(){
		listaColaboradoresEst = new ArrayList<Colaborador>();
	}
	
	public void nuevoEstatus(){
		estatusSelec = new Estatus();
		listarEstatus();
	}
	
	public void buscar(){
		lista = estatusBO.buscar(filtro);
	}
	
	public void crear(){
		try{
			estatusBO.crear(estatusSelec);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Estatus insertado correctamente", estatusSelec.getNombreEstatus()));
		} catch (LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
			
			estatusSelec.setNombreEstatus(null);
		}
		buscar();
	}
	
	public void actualizar(){
		try{
			estatusBO.actualizar(estatusSelec);
			try{
				for(Colaborador c : listaDual.getSource()){
					if(!listaColaboradores.contains(c)){
						c.setEstatus(null);
						colaboradorAdminBO.actualizar(c);
						aniadirHistoricoEstatus(estatusSelec, c);
					}
				}
	
				for(Colaborador c : listaDual.getTarget()){
					if(!listaColaboradoresEst.contains(c)){
						c.setEstatus(estatusSelec);
						colaboradorAdminBO.actualizar(c);
						aniadirHistoricoEstatus(estatusSelec, c);
					}
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Estatus modificado correctamente", estatusSelec.getNombreEstatus()));
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

	public void borrar(){
		try{
			estatusBO.borrar(estatusSelec);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Estatus borrado correctamente", estatusSelec.getNombreEstatus()));
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
	
	public void aniadirHistoricoEstatus(Estatus e, Colaborador c){
		historicoEstatusBO.insertar(e, c);
	}

	public void listarEstatus(){
		limpiarFormulario();
		if(estatusSelec.getNombreEstatus() != null){
			listaColaboradores = estatusBO.listarColaboradores(estatusSelec.getNombreEstatus());
			listarColaboradoresEst(estatusSelec.getNombreEstatus());
		}
		else{
			listaColaboradores = estatusBO.listarColaboradores();
			listaColaboradoresEst = new ArrayList<Colaborador>();
		}
		listaDual = new DualListModel<Colaborador>(listaColaboradores, listaColaboradoresEst);
	}
	
	public void listarColaboradoresEst(String nombreEstatus) {
		listaColaboradoresEst = estatusBO
				.listarColaboradorEstatus(nombreEstatus);
	}
	
	public void obtenerEstatusSeleccionado(int idEstatus){
		colaboradorEnEstatus = estatusBO.buscar(idEstatus);
	}
	
	private void limpiarFormulario(){
		RequestContext.getCurrentInstance().reset("detalleForm");
	}
	
	//Getters & Setters	
	public Estatus getEstatusSelec() {
		return estatusSelec;
	}

	public List<Colaborador> getColaboradorEnEstatus() {
		return colaboradorEnEstatus;
	}

	public void setColaboradorEnEstatus(List<Colaborador> colaboradorEnEstatus) {
		this.colaboradorEnEstatus = colaboradorEnEstatus;
	}

	public void setEstatusSelec(Estatus estatusSelec) {
		this.estatusSelec = estatusSelec;
	}

	public List<Estatus> getLista() {
		return lista;
	}

	public void setLista(List<Estatus> lista) {
		this.lista = lista;
	}

	public FiltroEstatusDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroEstatusDto filtro) {
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

	public List<Colaborador> getListaColaboradoresEst() {
		return listaColaboradoresEst;
	}

	public void setListaColaboradoresEst(List<Colaborador> listaColaboradoresEst) {
		this.listaColaboradoresEst = listaColaboradoresEst;
	}
}
