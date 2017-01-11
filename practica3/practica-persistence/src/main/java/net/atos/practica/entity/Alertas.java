package net.atos.practica.entity;

import java.util.Date;

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

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name="ALERTAS")
public class Alertas {
	@Id
	@SequenceGenerator(name = "sequenceAlertas", sequenceName = "sequenceAlertas", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceAlertas")
	@Column(name="ID_ALERTAS")
	private int idAlertas;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="ESTADO")
	private boolean estado;
	
	@Column(name= "FECHADEREFERENCIA", columnDefinition = "DATE") //FECHA DE FIN DE LA ALERTA
	private Date fechaDeReferencia;
	
	@Column(name="NOMBREALARMA", unique=true)
	private String nombreAlerta;
	
	@NumberFormat
	@Column(name = "PERIODOPREAVISO")
	private int periodoPreAviso;
	
	@Column(name= "FECHADEAVISO", columnDefinition = "DATE")
	private Date fechaDeAviso;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_PROMOCION")
	private Promocion promocion;
	
	
	//Getters and setters
	public int getId_Alertas() {
		return idAlertas;
	}

	public void setId_Alertas(int idAlertas) {
		this.idAlertas = idAlertas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPeriodoPreAviso() {
		return periodoPreAviso;
	}

	public void setPeriodoPreAviso(int periodoPreAviso) {
		this.periodoPreAviso = periodoPreAviso;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public Date getFechaDeReferencia() {
		return fechaDeReferencia;
	}

	public void setFechaDeReferencia(Date fechaDeReferencia) {
		this.fechaDeReferencia = fechaDeReferencia;
	}

	public String getNombreAlerta() {
		return nombreAlerta;
	}

	public void setNombreAlerta(String nombreAlerta) {
		this.nombreAlerta = nombreAlerta;
	}

	public Date getFechaDeAviso() {
		return fechaDeAviso;
	}

	public void setFechaDeAviso(Date fechaDeAviso) {
		this.fechaDeAviso = fechaDeAviso;
	}
	
}
