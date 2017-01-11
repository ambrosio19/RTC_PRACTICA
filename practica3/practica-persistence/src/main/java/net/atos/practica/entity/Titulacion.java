package net.atos.practica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TITULACION")
public class Titulacion {

	@Id
	@Column(name = "ID_TITULACION")
	@SequenceGenerator(name = "sequenceTitulacion", sequenceName = "sequenceTitulacion",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceTitulacion")
	private int idTitulacion;

	@Column(name = "NOMBRE",length=255, unique= true)
	private String nombreTitulacion;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_NIVEL")
	private Nivel nivelTitulacion;
	
	//Getter and Setters	
	
	public int getIdTitulacion() {
		return idTitulacion;
	}
	public void setIdTitulacion(int idTitulacion) {
		this.idTitulacion = idTitulacion;
	}
	public String getNombreTitulacion() {
		return nombreTitulacion;
	}
	public void setNombreTitulacion(String nombreTitulacion) {
		this.nombreTitulacion = nombreTitulacion;
	}	
	
	public Nivel getNivelTitulacion() {
		return nivelTitulacion;
	}
	public void setNivelTitulacion(Nivel nivelTitulacion) {
		this.nivelTitulacion = nivelTitulacion;
	}
	
	//HASHCODE and EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idTitulacion;
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
		Titulacion other = (Titulacion) obj;
		if (idTitulacion != other.idTitulacion)
			return false;
		return true;
	}

}
