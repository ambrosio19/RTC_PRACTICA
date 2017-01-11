package net.atos.practica.dto;

import java.util.Date;

public class FiltroProyectoDto {

	private String wbs;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean fechaIn;
	private boolean fechaFi;

	public FiltroProyectoDto() {
		// TODO Auto-generated constructor stub
	}

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isFechaIn() {
		return fechaIn;
	}

	public void setFechaIn(boolean fechaIn) {
		this.fechaIn = fechaIn;
	}

	public boolean isFechaFi() {
		return fechaFi;
	}

	public void setFechaFi(boolean fechaFi) {
		this.fechaFi = fechaFi;
	}

}