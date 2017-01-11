package net.atos.practica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CAPACIDAD")
public class Capacidad {

	@Id
	@SequenceGenerator(name = "sequenceCapacidad", sequenceName = "sequenceCapacidad", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceCapacidad")
	@Column(name = "ID_CAPACIDAD")
	private int idCapacidad;
	@Column(name = "NOMBRE", unique = true)
	private String nombreCapacidad;

	// GETTERS AND SETTERS

	public int getIdCapacidad() {
		return idCapacidad;
	}

	public void setIdCapacidad(int idCapacidad) {
		this.idCapacidad = idCapacidad;
	}

	public String getNombreCapacidad() {
		return nombreCapacidad;
	}

	public void setNombreCapacidad(String nombreCapacidad) {
		this.nombreCapacidad = nombreCapacidad;
	}

	// HASHCODE AND EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCapacidad;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Capacidad other = (Capacidad) obj;
		if (idCapacidad != other.idCapacidad)
			return false;
		return true;
	}

}
