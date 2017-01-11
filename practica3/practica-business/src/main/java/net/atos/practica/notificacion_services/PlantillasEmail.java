package net.atos.practica.notificacion_services;

import java.io.StringWriter;
import java.util.List;

import net.atos.practica.entity.Alertas;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DateTool;

public class PlantillasEmail {

	private static Logger log = Logger.getLogger(PlantillasEmail.class);

	// Bean de spring configurado email-bean-context.xml
	private VelocityEngine velocityEngine;

	// Propiedad inicializada mediante bean de spring email-bean-context.xml
	private String pathPlantillaAlertas;

	public String generarEmailAlertas(List<Alertas> listaAlertas) {
		log.debug("<------COMIENZA generarEmailAlertas Clase PlantillasEmail----------->");

		StringWriter stringWriter = new StringWriter();
		Template plantilla = null;

		VelocityContext context = new VelocityContext();
		context.put("listaAlertas", listaAlertas);
		// Añadimos al contexto un instancia de la clase DateTool que permite
		// formatear las fechas con date.format(
		context.put("date", new DateTool());

		try {
			plantilla = velocityEngine.getTemplate(pathPlantillaAlertas,
					"UTF-8");
			plantilla.merge(context, stringWriter);

			log.debug(stringWriter.toString());

		} catch (ResourceNotFoundException rnfe) {
			log.error("<------------ERROR: Plantilla NO LOCALIZADA--------->"
					+ rnfe.getMessage());

		} catch (ParseErrorException pee) {
			log.error("<------------ERROR: Problema parseando la plantilla---------->"
					+ pee.getMessage());
		}

		log.debug("<------FINALIZA generarEmailAlertas Clase PlantillasEmail----------->");

		return stringWriter.toString();
	}

	// GETTER Y SETTERS

	public String getPathPlantillaAlertas() {
		return pathPlantillaAlertas;
	}

	public void setPathPlantillaAlertas(String pathPlantillaAlertas) {
		this.pathPlantillaAlertas = pathPlantillaAlertas;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
