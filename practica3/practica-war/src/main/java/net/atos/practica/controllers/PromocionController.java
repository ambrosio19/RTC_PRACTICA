package net.atos.practica.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.atos.practica.controllers.lazyDataModelClases.LazyPromocionDataModel;
import net.atos.practica.dto.FiltroPromocionDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Promocion;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.negocio.ColaboradorAdminBO;
import net.atos.practica.negocio.PromocionBO;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class PromocionController {

	public static final int SEIS_MESES = 6;
	public static final int UN_DIA_MENOS = -1;
	private static final String ERROR = "Error: ";
	@Autowired
	private PromocionBO promocionBO;

	@Autowired
	private ColaboradorAdminBO colaboradorAdminBO;

	@Autowired
	private AlertasController alerta;

	private Promocion promocionSelec;

	private List<Promocion> lista;
	private FiltroPromocionDto filtro = new FiltroPromocionDto();

	private List<Colaborador> listaColab;

	private DualListModel<Colaborador> listaDual;
	private List<Colaborador> listaColaboradores;
	private List<Colaborador> listaColaboradoresPro;

	private LazyDataModel<Promocion> lazyModelListPromociones;

	@PostConstruct
	public void init() {
		promocionSelec = new Promocion();
		lazyModelListPromociones = new LazyPromocionDataModel(promocionBO,
				filtro);

	}

	public void nuevaPromocion() {
		promocionSelec = new Promocion();
		listarColaboradores();
	}

	public void buscar() {
		lista = promocionBO.buscar(filtro);

	}

	public void buscarPaginando() {

		lazyModelListPromociones = new LazyPromocionDataModel(promocionBO,
				filtro);
	}

	public void crear() {
		try {

			calculaFechas();
			promocionBO.crear(promocionSelec);
			alerta.crearDesdePromocion(promocionSelec);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Promoción insertada correctamente", promocionSelec
									.getNombrePromocion()));

		} catch (LlamaloXException e) {
			promocionSelec.setNombrePromocion(null);
			promocionSelec.setnConvocatoria(null);
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscarPaginando();
	}

	public void actualizar() {

		try {
			calculaFechas();
			promocionBO.actualizar(promocionSelec);
			alerta.actualizar(promocionSelec);
			for (Colaborador c : listaDual.getSource()) {
				if (!listaColaboradores.contains(c)) {
					c.setPromocion(null);
					colaboradorAdminBO.actualizar(c);
				}
			}

			for (Colaborador c : listaDual.getTarget()) {
				if (!listaColaboradoresPro.contains(c)) {
					c.setPromocion(promocionSelec);
					colaboradorAdminBO.actualizar(c);
				}
			}

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Promoción modificada correctamente",
							promocionSelec.getNombrePromocion()));

			// Si Nombre de promocion y nConvocatoria coinciden con alguna
			// insertada
			// lanza la excepción
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
			promocionBO.borrar(promocionSelec);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Promoción borrada correctamente", promocionSelec
									.getNombrePromocion()));
		} catch (LlamaloXException e) {
			FacesContext msg = FacesContext.getCurrentInstance();
			msg.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					ERROR, e.getMessage()));
			FacesContext.getCurrentInstance().validationFailed();
		}
		buscarPaginando();
	}

	public void listarColaboradores() {
		limpiarFormulario();
		if (promocionSelec.getNombrePromocion() != null) {
			listaColaboradores = promocionBO.listarColaboradores(promocionSelec
					.getNombrePromocion());
			listarColaboradoresPro(promocionSelec);
		} else {
			listaColaboradores = promocionBO.listarColaboradores();
			listaColaboradoresPro = new ArrayList<Colaborador>();
		}
		listaDual = new DualListModel<Colaborador>(listaColaboradores,
				listaColaboradoresPro);
	}

	public void listarColaboradoresPro(Promocion promocion) {
		listaColaboradoresPro = promocionBO
				.listarColaboradorPromocion(promocion);
	}

	public void calculaFechas() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(promocionSelec.getFechaInicioPromo());
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(fecha));
		} catch (ParseException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, ERROR,
							"Formato de fecha incorrecto."));
			FacesContext.getCurrentInstance().validationFailed();
		}
		c.add(Calendar.MONTH, SEIS_MESES);
		c.add(Calendar.DATE, UN_DIA_MENOS);
		promocionSelec.setFechaFinPeriodo1(c.getTime());
		c.add(Calendar.MONTH, SEIS_MESES);
		promocionSelec.setFechaFinFinal(c.getTime());
	}

	public void obtenerPromocionSeleccionada(int idPromocion) {
		listaColab = promocionBO.buscar(idPromocion);
	}

	private void limpiarFormulario() {
		RequestContext.getCurrentInstance().reset("detalleForm");
	}

	// Getters & Setters
	public Promocion getPromocion() {
		return promocionSelec;
	}

	public void setPromocion(Promocion promocion) {
		this.promocionSelec = promocion;
	}

	public Promocion getPromocionSelec() {
		return promocionSelec;
	}

	public void setPromocionSelec(Promocion promocionSelec) {
		this.promocionSelec = promocionSelec;
	}

	public List<Promocion> getLista() {
		return lista;
	}

	public void setLista(List<Promocion> lista) {
		this.lista = lista;
	}

	public FiltroPromocionDto getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroPromocionDto filtro) {
		this.filtro = filtro;
	}

	public List<Colaborador> getListaColab() {
		return listaColab;
	}

	public void setListaColab(List<Colaborador> listaColab) {
		this.listaColab = listaColab;
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

	public List<Colaborador> getListaColaboradoresPro() {
		return listaColaboradoresPro;
	}

	public void setListaColaboradoresPro(List<Colaborador> listaColaboradoresPro) {
		this.listaColaboradoresPro = listaColaboradoresPro;
	}

	public LazyDataModel<Promocion> getLazyModelListPromociones() {
		return lazyModelListPromociones;
	}

	public void setLazyModelListPromociones(
			LazyDataModel<Promocion> lazyModelListPromociones) {
		this.lazyModelListPromociones = lazyModelListPromociones;
	}

}
