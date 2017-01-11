package net.atos.practica.notificacion_services;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.entity.Alertas;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.RolesSeguridad;
import net.atos.practica.negocio.AlertasBO;
import net.atos.practica.negocio.ColaboradorBO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//@Configuration
//@EnableScheduling
public class NotificacionServices {

	private static Logger log = Logger.getLogger(NotificacionServices.class);
	@Autowired
	private EmailServices emailServices;
	@Autowired
	private ColaboradorBO colaboradorBO;
	@Autowired
	private AlertasBO alertasBo;
	@Autowired
	PlantillasEmail plantillaAlertas;

	// Propiedades inicializada mediante bean de spring email-bean-context.xml
	
	private String asunto;

	// Notificacion L,M,X,J,V, a las 5 de la mañana
	// @Scheduled(cron="0 0 5 ? * MON-FRI *")
	// Notifica cada 3 minutos como prueba
	// @Scheduled(cron="0 0/3 * * * ?")
	public void notificarAlertas() throws MessagingException, IOException {
		log.debug("<------COMIENZA notificarAlertas Clase notificar----------->");

		List<Alertas> listaAlertas = alertasBo.buscarActivas();

		if (!listaAlertas.isEmpty()) {

			// asunto="Notificación de Alertas desde Llámalo X";

			List<Colaborador> listaColabAdmin = buscarListaColabAdmin();

			String contenidoAlertas = plantillaAlertas
					.generarEmailAlertas(listaAlertas);

			emailServices.enviarMailList(listaColabAdmin, asunto,
					contenidoAlertas);
		} else {
			return;
		}
		log.debug("<------FINALIZA notificarAlertas Clase notificar----------->");

	}

	public List<Colaborador> buscarListaColabAdmin() {
		log.debug("<------COMIENZA buscarListaColabAdmin Clase notificar----------->");

		RolesSeguridad rol = new RolesSeguridad();
		FiltroColaboradorAdminDto filtroColabAdminDto = new FiltroColaboradorAdminDto();
		rol.setNombreRol("ADMINISTRADOR");
		filtroColabAdminDto.setRol(rol);
		log.debug("<------FINALIZA buscarListaColabAdmin Clase notificar----------->");

		return colaboradorBO.buscar(filtroColabAdminDto);

	}

	// GETTER Y SETTER

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

}
