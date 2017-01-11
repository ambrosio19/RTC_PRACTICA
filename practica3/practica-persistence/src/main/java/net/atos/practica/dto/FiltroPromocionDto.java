package net.atos.practica.dto;

public class FiltroPromocionDto {

	private String nombrePromocion;
	private Integer nConvocatoria;

	// Constructors
	public FiltroPromocionDto() {
	}

	// Getters & Setters
	public String getNombrePromocion() {
		return nombrePromocion;
	}

	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}

	public Integer getnConvocatoria() {
		return nConvocatoria;
	}

	public void setnConvocatoria(Integer nConvocatoria) {
		this.nConvocatoria = nConvocatoria;
	}

}
