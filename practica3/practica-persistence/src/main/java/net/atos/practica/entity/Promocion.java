package net.atos.practica.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PROMOCION", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"NOMBRE", "CONVOCATORIA" }) })
public class Promocion {

	private static final String DATE = "DATE";

	@Id
	@SequenceGenerator(name = "sequencePromocion", sequenceName = "sequencePromocion", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencePromocion")
	@Column(name = "ID_PROMOCION")
	private int idPromociones;

	@Column(name = "NOMBRE", nullable = false)
	private String nombrePromocion;

	@Column(name = "CONVOCATORIA", nullable = false)
	private Integer nConvocatoria;

	@Column(name = "FECHAINI", columnDefinition = DATE, nullable = false)
	private Date fechaInicioPromo;
	@Column(name = "FECHAFIN1", columnDefinition = DATE, nullable = false)
	private Date fechaFinPeriodo1;
	@Column(name = "FECHAFIN2", columnDefinition = DATE)
	private Date fechaFinFinal;
	@Column(name = "FECHANOLEC1", columnDefinition = DATE, nullable = false)
	private Date fechaNoLectivo1;
	@Column(name = "FECHANOLEC2", columnDefinition = DATE, nullable = false)
	private Date fechaNoLectivo2;

	// GETTERS AND SETTERS

	public int getIdPromociones() {
		return idPromociones;
	}

	public void setIdPromociones(int idPromociones) {
		this.idPromociones = idPromociones;
	}

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

	public Date getFechaInicioPromo() {
		return fechaInicioPromo;
	}

	public void setFechaInicioPromo(Date fechaInicioPromo) {
		this.fechaInicioPromo = new Date(fechaInicioPromo.getTime());
	}

	public Date getFechaFinPeriodo1() {
		return fechaFinPeriodo1;
	}

	public void setFechaFinPeriodo1(Date fechaFinPeriodo1) {
		this.fechaFinPeriodo1 = new Date(fechaFinPeriodo1.getTime());
	}

	public Date getFechaFinFinal() {
		return fechaFinFinal;
	}

	public void setFechaFinFinal(Date fechaFinFinal) {
		this.fechaFinFinal = new Date(fechaFinFinal.getTime());
	}

	public Date getFechaNoLectivo1() {
		return fechaNoLectivo1;
	}

	public void setFechaNoLectivo1(Date fechaNoLectivo1) {
		this.fechaNoLectivo1 = fechaNoLectivo1;
	}

	public Date getFechaNoLectivo2() {
		return fechaNoLectivo2;
	}

	public void setFechaNoLectivo2(Date fechaNoLectivo2) {
		this.fechaNoLectivo2 = new Date(fechaNoLectivo2.getTime());
	}

	// HASHCODE AND EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPromociones;
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
		Promocion other = (Promocion) obj;
		if (idPromociones != other.idPromociones)
			return false;
		return true;
	}

}
