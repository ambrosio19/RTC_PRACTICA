package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import net.atos.practica.dto.FiltroColaboradorCapacidad;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.negocio.CapacidadBO;
import net.atos.practica.negocio.ColaboradorBO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;


@Component
@Scope("view")
public class VisualizarProyectos {
	
	@Autowired
	private CapacidadBO capacidadBO;
	
	@Autowired
	private ColaboradorBO colaboradorBO;
	
	private List<Capacidad> listaCapacidades;
	private List<Colaborador> listaColaboradores;
	private List<FiltroColaboradorCapacidad> colaboradoresCapacidades;
	private String data = "[";
	@JsonView
	@PostConstruct
	public void init(){
		colaboradoresCapacidades = new ArrayList<FiltroColaboradorCapacidad>();
		listaCapacidades = capacidadBO.listarCapacidades();
		listaColaboradores = new ArrayList<Colaborador>();
		
		colaboradoresCapacidades = colaboradorBO.colaboradoresCapacidad();
		
		for(FiltroColaboradorCapacidad fcc : colaboradoresCapacidades){
			data += "{'name' :" + '"'+fcc.getNombreCapacidad()+'"';
			data += ", 'y' : " + fcc.getNumColaboradores();
			data += "}, ";
		}
		
		data = data.substring(0, data.length()-2);
		data += "]";
	}

	public List<Capacidad> getListaCapacidades() {
		return listaCapacidades;
	}

	public void setListaCapacidades(List<Capacidad> listaCapacidades) {
		this.listaCapacidades = listaCapacidades;
	}

	public List<Colaborador> getListaColaboradores() {
		return listaColaboradores;
	}

	public void setListaColaboradores(List<Colaborador> listaColaboradores) {
		this.listaColaboradores = listaColaboradores;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<FiltroColaboradorCapacidad> getColaboradoresCapacidades() {
		return colaboradoresCapacidades;
	}

	public void setColaboradoresCapacidades(
			List<FiltroColaboradorCapacidad> colaboradoresCapacidades) {
		this.colaboradoresCapacidades = colaboradoresCapacidades;
	}
	
}
