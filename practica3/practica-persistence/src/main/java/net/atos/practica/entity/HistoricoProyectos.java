package net.atos.practica.entity;
//
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
@Table(name = "HISTORICO_PROYECTOS")
public class HistoricoProyectos {
	
	@Id
	@SequenceGenerator(name = "sequenceHistoricoProyecto", sequenceName = "sequenceHistoricoProyecto",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceHistoricoProyecto")
	@Column(name = "ID_HISTORICO")
	private long idHistorico;
	
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_PROYECTO")
	private Proyecto proyecto;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="ID_COLABORADOR")
	private Colaborador colaborador;
	
	@Column(name="FECHA_INI_COLAB_PROY", nullable=false,columnDefinition="DATE")
	private Date fechaIniColabProy;
	
	@Column (name="FECHA_FIN_COLAB_PROY",columnDefinition="DATE")
	private Date fechaFinColabProy;
	
	
	@Column (name="COMENTARIOS",length=255)
	private String comentarios;


	// Getters AND SETTERS
	public Long getId_Historico() {
		return idHistorico;
	}


	public void setId_Historico(Long id_Historico) {
		this.idHistorico = id_Historico;
	}


	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}


	public Colaborador getColaborador() {
		return colaborador;
	}


	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}


	public Date getFechaIniColabProy() {
		return fechaIniColabProy;
	}


	public void setFechaIniColabProy(Date fechaIniColabProy) {
		this.fechaIniColabProy = fechaIniColabProy;
	}


	public Date getFechaFinColabProy() {
		return fechaFinColabProy;
	}


	public void setFechaFinColabProy(Date fechaFinColabProy) {
		this.fechaFinColabProy = fechaFinColabProy;
	}


	public String getComentarios() {
		return comentarios;
	}


	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	// HASHCODE AND EQUALS
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
		HistoricoProyectos other = (HistoricoProyectos) obj;
		if (idHistorico != other.idHistorico)
			return false;
		return true;
	}

}

