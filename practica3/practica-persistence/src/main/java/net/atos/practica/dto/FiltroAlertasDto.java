package net.atos.practica.dto;

import java.util.Date;

import net.atos.practica.entity.Promocion;

public class FiltroAlertasDto {
	private String alerta;
	private boolean estado;
	private Date fechaActual;
	private Date fechaMinina;
	private Date fechaMaxima;
	private Promocion promocion;
	private boolean fmax; //parametros para controlar en el bo
	private boolean fmin;

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public Date getFechaMinina() {
		return fechaMinina;
	}

	public void setFechaMinina(Date fechaMinina) {
		this.fechaMinina = fechaMinina;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public boolean isFmax() {
		return fmax;
	}

	public void setFmax(boolean fmax) {
		this.fmax = fmax;
	}

	public boolean isFmin() {
		return fmin;
	}

	public void setFmin(boolean fmin) {
		this.fmin = fmin;
	}
	
}
