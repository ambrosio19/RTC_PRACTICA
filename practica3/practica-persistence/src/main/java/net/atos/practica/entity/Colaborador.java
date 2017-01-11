package net.atos.practica.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

import net.atos.common.validators.Nif;
import net.atos.practica.entitySerializer.IntToStringSerializer;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

class View {
	interface Summary {
	}

}

@Entity
@Table(name = "COLABORADOR")
public class Colaborador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1732073407797522346L;
	// Nif unico y Clave Primaria
	@Id
	@SequenceGenerator(name = "sequenceIdColaborador", sequenceName = "sequenceIdColaborador", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceIdColaborador")
	@Column(name = "ID_COLABORADOR")
	@JsonIgnore
	private Long idColaborador;

	@Nif
	@Column(name = "NIF", length = 9, unique = true)
	@JsonIgnore
	private String nif;

	@Column(name = "CODIGO", length = 7, unique = true)
	@JsonView
	@JsonProperty("DAS")
	private String codigo;

	@Column(name = "PWD")
	@JsonIgnore
	private String pwd;

	@Column(name = "NOMBRE", length = 30)
	@JsonView
	@JsonProperty("Nombre:")
	private String nombre;

	@Column(name = "PRIMERAPELLIDO", length = 30)
	@JsonView
	@JsonProperty("1 Apellido:")
	private String primerApellido;

	@Column(name = "SEGUNDOAPELLIDO", length = 30)
	@JsonView
	@JsonProperty("2 Apellido:")
	private String segundoApellido;

	@Email
	@Column(name = "EMAIL", length = 30)
	@JsonView
	@JsonProperty("Email:")
	private String email;

	@NumberFormat
	@Column(name = "TELEFONO", length = 12)
	@JsonIgnore
	private String telefono;

	@Column(name = "FECHANACIMIENTO", columnDefinition="DATE")
	@JsonView
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
	@JsonProperty("Fecha de nacimiento")
	private Date fechaNacimiento;

	@Column(name = "FECHAALTA", columnDefinition="DATE")
	@JsonView
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
	@JsonProperty("Fecha de Alta")
	private Date fechaAlta;

	@Column(name = "FECHABAJA", columnDefinition="DATE")
	@JsonIgnore
	private Date fechaBaja;

	@Column(name = "FECHAINIPRO", columnDefinition="DATE")
	@JsonView
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
	@JsonProperty("F. Inicio Proy.")
	private Date fechaInicioProyecto;

	@Column(name = "FECHAFINPRO", columnDefinition="DATE")
	@JsonView
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
	@JsonProperty("F. Fin Proy.")
	private Date fechaFinProyecto;

	@NumberFormat
	@Column(name = "NIVELGCM")
	@JsonView
	@JsonProperty("parent")
	@JsonSerialize(using = IntToStringSerializer.class)
	private Integer nivelGCM;

	@NumberFormat
	@Column(name = "SBA")
	@JsonView
	@JsonProperty("value")
	private BigDecimal sba;
	// Supongo que por simplicidad y rendimiento las fotos se guardan en disco y
	// se guardamos la ruta
	// tb es posible poner private byte [] foto para guardar la foto en la BBDD
	// directamente.
	@Column(name = "FOTO")
	@JsonView
	@JsonProperty("FOTO")
	private String foto;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	// Rol de seguridad no es opcional
	@JoinColumn(name = "ID_ROL")
	@JsonIgnore
	private RolesSeguridad rol;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_ESTATUS")
	@JsonIgnore
	private Estatus estatus;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_CATEGORIA_PROFESIONAL")
	@JsonIgnore
	private CategoriaProfesional categoriaPro;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_TITULACION")
	@JsonIgnore
	private Titulacion titulacion;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_CAPACIDAD")
	@JsonIgnore
	private Capacidad capacidad;

	// Como dijo Joaquin, 1 colaborador 1 proyecto de momento, por simplificar
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_PROYECTO")
	@JsonIgnore
	private Proyecto proyecto;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_PROMOCION")
	@JsonIgnore
	private Promocion promocion;

	// GETTERS AND SETTER

	public Long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(Long id_Colaborador) {
		this.idColaborador = id_Colaborador;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaInicioProyecto() {
		return fechaInicioProyecto;
	}

	public void setFechaInicioProyecto(Date fechaInicioProyecto) {
		this.fechaInicioProyecto = fechaInicioProyecto;
	}

	public Date getFechaFinProyecto() {
		return fechaFinProyecto;
	}

	public void setFechaFinProyecto(Date fechaFinProyecto) {
		this.fechaFinProyecto = fechaFinProyecto;
	}

	public Integer getNivelGCM() {
		return nivelGCM;
	}

	public void setNivelGCM(Integer nivelGCM) {
		this.nivelGCM = nivelGCM;
	}

	public BigDecimal getSba() {
		return sba;
	}

	public void setSba(BigDecimal sba) {
		this.sba = sba;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public RolesSeguridad getRol() {
		return rol;
	}

	public void setRol(RolesSeguridad rol) {
		this.rol = rol;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public CategoriaProfesional getCategoriaPro() {
		return categoriaPro;
	}

	public void setCategoriaPro(CategoriaProfesional categoriaPro) {
		this.categoriaPro = categoriaPro;
	}

	public Titulacion getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(Titulacion titulacion) {
		this.titulacion = titulacion;
	}

	public Capacidad getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Capacidad capacidad) {
		this.capacidad = capacidad;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	// HASHCODE AND EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idColaborador == null) ? 0 : idColaborador.hashCode());
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
		Colaborador other = (Colaborador) obj;
		if (idColaborador == null) {
			if (other.idColaborador != null)
				return false;
		} else if (!idColaborador.equals(other.idColaborador))
			return false;
		return true;
	}

}
