package net.atos.practica.dto;

public class FiltroColaboradorCapacidad {
	private String nombreCapacidad;
	private Long numColaboradores;
		
	public String getNombreCapacidad() {
		return nombreCapacidad;
	}

	public void setNombreCapacidad(String nombreCapacidad) {
		this.nombreCapacidad = nombreCapacidad;
	}

	public Long getNumColaboradores() {
		return numColaboradores;
	}

	public void setNumColaboradores(Long numColaboradores) {
		this.numColaboradores = numColaboradores;
	}
}
