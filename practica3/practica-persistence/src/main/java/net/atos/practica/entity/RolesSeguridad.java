package net.atos.practica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ROLESSEGURIDAD")
public class RolesSeguridad {

	@Id
	@Column(name = "ID_ROL")
	@SequenceGenerator(name = "sequenceRol", sequenceName = "sequenceRol",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceRol")
	private int idRol;

	@Column(name = "NOMBRE", length = 15,unique=true,nullable=false)
	private String nombreRol;
	@Column(name = "DESCRIPCION", length = 100)
	private String descripcionRol;
	
	//Baja = false por defecto => no es baja, es decir, se tiene encuenta a efectos de seguridad
	//Baja = true => baja del rol activa, es decir, el rol esté de baja, no se tendrá en cuenta a efectos de seguridad.
	@Column(name = "BAJA")
	private boolean baja=false;

	// GETTERS AND SETTERS
	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	// HASHCODE and EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idRol;
		result = prime * result
				+ ((nombreRol == null) ? 0 : nombreRol.hashCode());
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
		RolesSeguridad other = (RolesSeguridad) obj;
		if (idRol != other.idRol)
			return false;
		if (nombreRol == null) {
			if (other.nombreRol != null)
				return false;
		} else if (!nombreRol.equals(other.nombreRol))
			return false;
		return true;
	}
}
