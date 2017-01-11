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

@Entity
@Table(name = "HISTORICO_CAPACIDADES")
public class HistoricoCapacidades {
	
	@Id
	@SequenceGenerator(name = "sequenceHistoricoCapacidades", sequenceName = "sequenceHistoricoCapacidades",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceHistoricoCapacidades")
	@Column(name = "ID_HISTORICO")
	private long idHistorico;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_CAPACIDAD")
	private Capacidad capacidad;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_COLABORADOR")
	private Colaborador colaborador;
	
	@Column(name="FECHA_INI_COLAB_CAPACIDAD",nullable=false,columnDefinition="DATE")
	private Date fechaIniColabCap;
	
	@Column (name="FECHA_FIN_COLAB_CAPACIDAD",columnDefinition="DATE")
	private Date fechaFinColabCap;
	
	@Column (name="COMENTARIOS",length=255)
	private String comentarios;

	// Getters AND SETTERS
	public long getId_Historico() {
		return idHistorico;
	}

	public void setId_Historico(long id_Historico) {
		this.idHistorico = id_Historico;
	}

	public Capacidad getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Capacidad capacidad) {
		this.capacidad = capacidad;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Date getFechaIniColabCap() {
		return fechaIniColabCap;
	}

	public void setFechaIniColabCap(Date fechaIniColabCap) {
		this.fechaIniColabCap = fechaIniColabCap;
	}

	public Date getFechaFinColabCap() {
		return fechaFinColabCap;
	}

	public void setFechaFinColabCap(Date fechaFinColabCap) {
		this.fechaFinColabCap = fechaFinColabCap;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	//HASHCODE AND EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idHistorico ^ (idHistorico >>> 32));
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
		HistoricoCapacidades other = (HistoricoCapacidades) obj;
		if (idHistorico != other.idHistorico)
			return false;
		return true;
	}	

}