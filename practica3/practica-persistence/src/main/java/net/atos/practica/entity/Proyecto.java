package net.atos.practica.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PROYECTO")
public class Proyecto {
	// Codigo del proyecto
	@Id
	@SequenceGenerator(name = "sequenceProyecto", sequenceName = "sequenceProyecto",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceProyecto")
	@Column(name = "ID_PROYECTO")
	private Long idProyecto;
	@Column(name = "WBS",unique = true)
	private String wbs;// suponiendo que el código es Alfanumerico
	
	@Column (name="NOMBREPROYECTO", length=30,nullable=false)
	private String nombreProyecto;
	@Column (name="FECHAINI",columnDefinition = "DATE",nullable=false)
	private Date fechaInicioPro;
	@Column (name="FECHAFIN",columnDefinition = "DATE")
	private Date fechaFinPro;

	// Getters AND SETTERS
	public Long getId_Proyecto() {
		return idProyecto;
	}

	public void setId_Proyecto(Long id_Proyecto) {
		this.idProyecto = id_Proyecto;
	}
	
	public String getWbs() {
		return wbs;
	}


	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public Date getFechaInicioPro() {
		return fechaInicioPro;
	}

	public void setFechaInicioPro(Date fechaInicioPro) {
		this.fechaInicioPro = fechaInicioPro;
	}

	public Date getFechaFinPro() {
		return fechaFinPro;
	}

	public void setFechaFinPro(Date fechaFinPro) {
		this.fechaFinPro = fechaFinPro;
	}
	
	// HASHCODE AND EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idProyecto == null) ? 0 : idProyecto.hashCode());
		result = prime * result + ((wbs == null) ? 0 : wbs.hashCode());
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
		Proyecto other = (Proyecto) obj;
		if (idProyecto == null) {
			if (other.idProyecto != null)
				return false;
		} else if (!idProyecto.equals(other.idProyecto))
			return false;
		if (wbs == null) {
			if (other.wbs != null)
				return false;
		} else if (!wbs.equals(other.wbs))
			return false;
		return true;
	}
}

