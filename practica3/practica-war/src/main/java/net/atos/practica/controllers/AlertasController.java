package net.atos.practica.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import net.atos.practica.dto.FiltroAlertasDto;
import net.atos.practica.entity.Alertas;
import net.atos.practica.entity.Promocion;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.AlertasBO;
import net.atos.practica.negocio.PromocionBO;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("view")
@ApplicationScoped
public class AlertasController{
	@Autowired
	private AlertasBO alertasBO;
	
	@Autowired
	private PromocionBO promocionBO;
	
	private List<Alertas> alertas;
	private FiltroAlertasDto filtroAlertas;
	private Alertas alerta;
	private List<Promocion> promociones;
	private List<Integer> numDias;
	private int numAlertas;
	private List<Alertas> alertasActivas;
	private List<Alertas> aux; //Esta lista se utiliza para ver las alarmas que hay y añadirlas al calendario cuando se inicia la aplicacion
	static Logger log = Logger.getLogger(LoginController.class);
	private boolean buttonColor;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private static final String ERROR="Error: ";
	

	@PostConstruct
	public void init(){
		buttonColor = false;
		numAlertas = 0;
		alertasActivas = new ArrayList<Alertas>();
		aux = new ArrayList<Alertas>();
		notificaciones();
		filtroAlertas = new FiltroAlertasDto();
		filtroAlertas.setAlerta("");
		numDias = new ArrayList<Integer>();
		for(Integer i=1; i<=31; i++){
			numDias.add(i);
		}
		eventModel = new DefaultScheduleModel();
		addEvents();
	}


	public void crear() {
		try{
			cambiaFechas(alerta);
			alertasBO.crear(alerta);
			notificaciones();
			addEvent(alerta);
			
		} catch(LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
//		}catch(PersistenceException e){
//			Throwable t = e.getCause();
//			while ((t != null) && !(t instanceof ConstraintViolationException)) {
//				t = t.getCause();
//			}
//			if (t != null) {
//				FacesContext msg = FacesContext.getCurrentInstance();
//				msg.addMessage(
//						null,
//						new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
//								"Esa alerta ya existe"));
//				FacesContext.getCurrentInstance().validationFailed();
//			}
//		}
		buscar();
	}
	
	public void crear(Alertas a){
		try{
			cambiaFechas(a);
			alertasBO.crear(a);
			addEvent(a);
			notificaciones();
		} catch(LlamaloXException e){
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
							e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}

//		}catch(PersistenceException e){
//			Throwable t = e.getCause();
//			while ((t != null) && !(t instanceof ConstraintViolationException)) {
//				t = t.getCause();
//			}
//			if (t != null) {
//				FacesContext msg = FacesContext.getCurrentInstance();
//				msg.addMessage(
//						null,
//						new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR ,
//								"Esa alerta ya existe"));
//				FacesContext.getCurrentInstance().validationFailed();
//			}
//		}
		buscar();
	}
	
	public void crearDesdePromocion(Promocion p){//Se crean las dos alarmas correspondientes, cuando se crea una promoción nueva
		Alertas alert = new Alertas();
		Alertas alert1 = new Alertas();
		
		alert.setNombreAlerta(p.getNombrePromocion() + " fin de promoción");
		alert.setEstado(true);
		alert.setFechaDeReferencia(p.getFechaFinPeriodo1());
		alert.setPeriodoPreAviso(30);
		alert.setPromocion(p);
		crear(alert);
		
		alert1.setNombreAlerta(p.getNombrePromocion() + " proceso de evaluación");
		alert1.setEstado(true);
		alert1.setFechaDeReferencia(p.getFechaFinPeriodo1());
		alert1.setPeriodoPreAviso(30);
		alert1.setPromocion(p);
		crear(alert1);
	}
	
	@SuppressWarnings("unchecked")
	public void buscar() {
		filtroAlertas.setFmin(false);
		filtroAlertas.setFmax(false);
		alertas = (List<Alertas>) alertasBO.buscar(filtroAlertas);
	}

	public void actualizar() {
		cambiaFechas(alerta);
		alertasBO.actualizar(alerta);
		notificaciones();
		addEvents();
		buscar();
	}
	
	public void actualizar(Promocion p){
		filtroAlertas.setPromocion(p);
		alertas = (List<Alertas>) alertasBO.buscarEnPromocion(filtroAlertas);
		Date fechaHoy = new Date();
		for(Alertas a : alertas){
			if(p.getFechaFinPeriodo1().before(fechaHoy) || p.getFechaFinPeriodo1().equals(fechaHoy)){
				a.setFechaDeReferencia(p.getFechaFinPeriodo1());
			}
			else{
				a.setFechaDeReferencia(p.getFechaFinFinal());
			}
			cambiaFechas(a);
			alertasBO.actualizar(a);
		}
		addEvents();
		notificaciones();
	}
	
	public void modificarEventos(Alertas a){
		if(a.isEstado()){
			addEvent(a);
		}
		else{
			removeEvent(a);
		}
	}

	public void borrar() {
		alertasBO.borrar(alerta);
		notificaciones();
		removeEvent(alerta);
		buscar();
	}
	
	public void notificaciones(){	
		numAlertas = alertasBO.buscarActivas().size();
		 RequestContext context = RequestContext.getCurrentInstance();
		 if(numAlertas >0){
			 buttonColor = true;
		 }
		 else{
			 buttonColor = false;
		 }
		 context.update("menuAdmin:alertasActuales:list");
		 context.update("menuAdmin:alerts"); 
		 listarAlertas();
	}
	
	public void listarAlertas(){
		alertasActivas = alertasBO.buscarActivas();
	}
	
	public void nuevaAlerta(){
		alerta = new Alertas();
		alerta.setEstado(true);
		buscarPromociones();
	}
	
	public void buscarPromociones(){
		promociones = promocionBO.listarPromociones();
	}
	
	public void cambiaFechas(Alertas a){ //Guarda la fecha en la que hay que avisar al administrador en el campo fechaDeAviso de alertas
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(a.getFechaDeReferencia());
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(fecha));
		} catch (ParseException e) {
			log.error("Esa fecha no es valida");
		}
		c.add(Calendar.DATE, a.getPeriodoPreAviso()*-1);
		a.setFechaDeAviso(c.getTime());
	}
	
	@SuppressWarnings("unchecked")
	public void addEvents(){
		eventModel.clear();
		aux = (List<Alertas>) alertasBO.buscar(filtroAlertas);
		for(Alertas a : aux){
			if(a.isEstado()){
				eventModel.addEvent(new DefaultScheduleEvent(a.getNombreAlerta(), a.getFechaDeReferencia(), a.getFechaDeReferencia()));
			}
		}
			
	}
	
	public void addEvent(Alertas a){
		eventModel.addEvent(new DefaultScheduleEvent(a.getNombreAlerta(), a.getFechaDeReferencia(), a.getFechaDeReferencia()));
		//event = new DefaultScheduleEvent();
	}
	
	public void removeEvent(Alertas a){
		eventModel.deleteEvent(new DefaultScheduleEvent(a.getNombreAlerta(), a.getFechaDeReferencia(), a.getFechaDeReferencia()));
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
        FiltroAlertasDto filtro = new FiltroAlertasDto();
        filtro.setAlerta(event.getTitle());
        alerta = (Alertas) alertasBO.buscar(filtro).get(0);
    }
	
	//Getters and setters
	public List<Alertas> getAlertas() {
		return alertas;
	}

	public void setAlertas(List<Alertas> alertas) {
		this.alertas = alertas;
	}

	public FiltroAlertasDto getFiltroAlertas() {
		return filtroAlertas;
	}

	public void setFiltroAlertas(FiltroAlertasDto filtroAlertas) {
		this.filtroAlertas = filtroAlertas;
	}

	public Alertas getAlerta() {
		return alerta;
	}

	public void setAlerta(Alertas alerta) {
		this.alerta = alerta;
	}

	public List<Promocion> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<Promocion> promociones) {
		this.promociones = promociones;
	}

	public List<Integer> getNumDias() {
		return numDias;
	}

	public void setNumDias(List<Integer> numDias) {
		this.numDias = numDias;
	}

	public int getNumAlertas() {
		return numAlertas;
	}

	public void setNumAlertas(int numAlertas) {
		this.numAlertas = numAlertas;
	}

	public List<Alertas> getAlertasActivas() {
		return alertasActivas;
	}

	public void setAlertasActivas(List<Alertas> alertasActivas) {
		this.alertasActivas = alertasActivas;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
	
	public boolean isButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(boolean buttonColor) {
		this.buttonColor = buttonColor;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public List<Alertas> getAux() {
		return aux;
	}

	public void setAux(List<Alertas> aux) {
		this.aux = aux;
	}
	
}
