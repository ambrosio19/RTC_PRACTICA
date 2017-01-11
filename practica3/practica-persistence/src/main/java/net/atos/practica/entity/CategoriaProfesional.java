package net.atos.practica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORIA_PROFESIONAL")
public class CategoriaProfesional {
	
	
		@Id
		@Column(name="ID_CATEGORIAPRO")
		@SequenceGenerator(name = "sequenceCategoriaPro", sequenceName = "sequenceCategoriaPro",allocationSize=1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceCategoriaPro")
		private int idCategoriaPro;
				
		@Column(name="NOMBRE_CATEGORIA",length=255,unique=true,nullable=false)
		private String nombreCategoriaPro;

		//Getter and Setters
		
		
		public int getIdCategoriaPro() {
			return idCategoriaPro;
		}

		public void setIdCategoriaPro(int idCategoriaPro) {
			this.idCategoriaPro = idCategoriaPro;
		}

		public String getNombreCategoriaPro() {
			return nombreCategoriaPro;
		}

		public void setNombreCategoriaPro(String nombreCategoriaPro) {
			this.nombreCategoriaPro = nombreCategoriaPro;
		}
		
		// HASHCODE and EQUALS
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + idCategoriaPro;
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
			CategoriaProfesional other = (CategoriaProfesional) obj;
			if (idCategoriaPro != other.idCategoriaPro)
				return false;
			return true;
		}

}

