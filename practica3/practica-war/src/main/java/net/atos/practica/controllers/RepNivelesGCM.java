package net.atos.practica.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.negocio.ColaboradorAdminBO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("view")
public class RepNivelesGCM {

	private static Logger log = Logger.getLogger(RepNivelesGCM.class);

	@Autowired
	private ColaboradorAdminBO colaboradorAdminBo;

	private List<Colaborador> listaColaboradores;
	private FiltroColaboradorAdminDto filtroColaboradorAdminDto = new FiltroColaboradorAdminDto();

	Colaborador colaborador1;
	Colaborador colaborador2;
	Colaborador colaborador3;

	Writer jsonWriter;

	String jsonString;

	@JsonView
	@PostConstruct
	public void init() {

		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		// Hibernate4Module hbm = new Hibernate4Module();
		// hbm.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
		// mapper.registerModule(hbm);

		filtroColaboradorAdminDto.setNombre("");
		filtroColaboradorAdminDto.setCodigo("");
		filtroColaboradorAdminDto.setMostrarBaja(false);

		listaColaboradores = colaboradorAdminBo
				.buscar(filtroColaboradorAdminDto);
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		// Ruta en el servidor del directorio usuarios
		String pathServer = servletContext.getContextPath();
		
		for(Colaborador c : listaColaboradores){
			c.setFoto("<img src='"+pathServer+c.getFoto() + "' style='max-height: 100px; max-width: 100px;'/>");
		}

		try {
			// mapper.writeValue(stringWriter, listaColaboradores);

			// jsonString = mapper.writerWithDefaultPrettyPrinter()
			// .writeValueAsString(listaColaboradores);
			jsonString = mapper.writeValueAsString(listaColaboradores);
			// jsonString = StringEscapeUtils.escapeJavaScript(jsonString);

		} catch (JsonGenerationException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public ColaboradorAdminBO getColaboradorAdminBo() {
		return colaboradorAdminBo;
	}

	public void setColaboradorAdminBo(ColaboradorAdminBO colaboradorAdminBo) {
		this.colaboradorAdminBo = colaboradorAdminBo;
	}

	public List<Colaborador> getListaColaboradores() {
		return listaColaboradores;
	}

	public void setListaColaboradores(List<Colaborador> listaColaboradores) {
		this.listaColaboradores = listaColaboradores;
	}

}
