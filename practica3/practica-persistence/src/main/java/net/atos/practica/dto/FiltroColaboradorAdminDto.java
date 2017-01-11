package net.atos.practica.dto;

import net.atos.practica.entity.RolesSeguridad;

public class FiltroColaboradorAdminDto {

	private String codigo;
	private String nombre;
	private RolesSeguridad rol;
	private boolean mostrarBaja;
	
	public FiltroColaboradorAdminDto() {
		// TODO Auto-generated constructor stub
	}
	
	public RolesSeguridad getRol() {
		return rol;
	}

	public void setRol(RolesSeguridad rol) {
		this.rol = rol;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isMostrarBaja() {
		return mostrarBaja;
	}

	public void setMostrarBaja(boolean mostrarBaja) {
		this.mostrarBaja = mostrarBaja;
	}
	
}