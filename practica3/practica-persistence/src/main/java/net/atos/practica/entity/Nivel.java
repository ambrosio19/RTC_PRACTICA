package net.atos.practica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NIVEL")
public class Nivel {

	@Id
	@Column(name = "ID_NIVEL")
	@SequenceGenerator(name = "sequenceNivelTi", sequenceName = "sequenceNivelTi",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceNivelTi")
	private int idNivel;

	@Column(name = "NOMBRE", length = 30)
	private String nombreNivel;

	// Getter and Setters

	public int getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(int idNivel) {
		this.idNivel = idNivel;
	}

	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

	// HASHCODE AND EQUAL
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idNivel;
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
		Nivel other = (Nivel) obj;
		if (idNivel != other.idNivel)
			return false;
		return true;
	}

}
