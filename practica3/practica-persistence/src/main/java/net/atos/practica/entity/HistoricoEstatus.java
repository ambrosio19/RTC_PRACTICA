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
@Table(name = "HISTORICO_ESTATUS")
public class HistoricoEstatus {
	
	@Id
	@SequenceGenerator(name = "sequenceHistoricoEstatus", sequenceName = "sequenceHistoricoEstatus",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceHistoricoEstatus")
	@Column(name = "ID_HISTORICO")
	private long idHistorico;
	
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_ESTATUS")
	private Estatus estatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_COLABORADOR")
	private Colaborador colaborador;
	
	@Column(name="FECHA_INI_COLAB_ESTATUS",nullable=false,columnDefinition="DATE")
	private Date fechaIniColabEst;
	
	@Column (name="FECHA_FIN_COLAB_ESTATUS",columnDefinition="DATE")
	private Date fechaFinColabEst;
	
	
	@Column (name="COMENTARIOS", length=255)
	private String comentarios;

	
	// Getters AND SETTERS
	public void setId_Historico(long id_Historico) {
		this.idHistorico = id_Historico;
	}


	public Estatus getEstatus() {
		return estatus;
	}


	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}


	public Colaborador getColaborador() {
		return colaborador;
	}


	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}


	public Date getFechaIniColabEst() {
		return fechaIniColabEst;
	}


	public void setFechaIniColabEst(Date fechaIniColabEst) {
		this.fechaIniColabEst = fechaIniColabEst;
	}


	public Date getFechaFinColabEst() {
		return fechaFinColabEst;
	}


	public void setFechaFinColabEst(Date fechaFinColabEst) {
		this.fechaFinColabEst = fechaFinColabEst;
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

	public long getId_Historico() {
		return idHistorico;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoEstatus other = (HistoricoEstatus) obj;
		if (idHistorico != other.idHistorico)
			return false;
		return true;
	}

}