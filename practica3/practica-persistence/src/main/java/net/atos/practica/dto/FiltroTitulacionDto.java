package net.atos.practica.dto;

public class FiltroTitulacionDto {
	
	private String nombreTitulacion;
	private String nivelTitulacion;

	//Constructors
	public FiltroTitulacionDto() {
		// TODO Auto-generated constructor stub
	}

	//Getters & Setters
	public String getNombreTitulacion() {
		return nombreTitulacion;
	}

	public void setNombreTitulacion(String nombreTitulacion) {
		this.nombreTitulacion = nombreTitulacion;
	}

	public String getNivelTitulacion() {
		return nivelTitulacion;
	}

	public void setNivelTitulacion(String nivelTitulacion) {
		this.nivelTitulacion = nivelTitulacion;
	}	

}
