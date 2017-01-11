package net.atos.practica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity 
@Table(name="ESTATUS")
public class Estatus {
	//id unico asociado a cada estatus
	@Id
	@Column(name="ID_ESTATUS")
	@SequenceGenerator(name = "sequenceEstatus", sequenceName = "sequenceEstatus",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceEstatus")
	private int idEstatus;
	
	@Column(name="NOMBRE",length=30,unique=true ,nullable=false)
	private String nombreEstatus;
	
	//Getter and Setters
	public int getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(int idEstatus) {
		this.idEstatus = idEstatus;
	}
	public String getNombreEstatus() {
		return nombreEstatus;
	}
	public void setNombreEstatus(String nombreEstatus) {
		this.nombreEstatus = nombreEstatus;
	}
	
	//HASHCODE AND EQUALS
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEstatus;
		result = prime * result
				+ ((nombreEstatus == null) ? 0 : nombreEstatus.hashCode());
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
		Estatus other = (Estatus) obj;
		if (idEstatus != other.idEstatus)
			return false;
		if (nombreEstatus == null) {
			if (other.nombreEstatus != null)
				return false;
		} else if (!nombreEstatus.equals(other.nombreEstatus))
			return false;
		return true;
	}
}
