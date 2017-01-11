package net.atos.practica.dto;

public class FiltroGenerarInformacion{
	private Integer gcm;
	private String nombrePromocion;
	private String wbs;
	private String nombreCapacidad;
	
	public Integer getGcm() {
		return gcm;
	}
	public void setGcm(Integer gcm) {
		this.gcm = gcm;
	}
	public String getNombrePromocion() {
		return nombrePromocion;
	}
	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}
	
	public String getWbs() {
		return wbs;
	}
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
	public String getNombreCapacidad() {
		return nombreCapacidad;
	}
	public void setNombreCapacidad(String nombreCapacidad) {
		this.nombreCapacidad = nombreCapacidad;
	}
	
}