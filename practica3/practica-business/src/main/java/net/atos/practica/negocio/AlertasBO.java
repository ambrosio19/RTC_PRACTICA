package net.atos.practica.negocio;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;




import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.AlertasDao;
import net.atos.practica.dto.FiltroAlertasDto;
import net.atos.practica.entity.Alertas;
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class AlertasBO implements InterfaceBO<Alertas, FiltroAlertasDto>{
	@Autowired
	private AlertasDao alertasDao;
	
	private List<Alertas> alertas;
	
	@PostConstruct
	public void init(){
		alertas = new ArrayList<Alertas>();
	}
	@Override
	public void crear(Alertas alerta) {
		alertasDao.crear(alerta);
	}

	@Override
	public List<?> buscar(FiltroAlertasDto filtro){
		if(filtro.getFechaMinina() != null){
			filtro.setFmin(true);
		}

		if(filtro.getFechaMaxima() != null){
			filtro.setFmax(true);
		}
		return alertasDao.buscar(filtro);
	}

	@Override
	public void actualizar(Alertas alerta) {
		alertasDao.actualizar(alerta);
	}

	@Override
	public void borrar(Alertas alerta) {
		alertasDao.borrar(alerta);
	}
	
	public List<Alertas> buscarEnPromocion(FiltroAlertasDto filtro){
		return alertasDao.buscarEnPromocion(filtro);
	}
	
	public List<Alertas> buscarActivas(){
		FiltroAlertasDto filtro=new FiltroAlertasDto();
		filtro.setEstado(true);
		return alertasDao.buscarActivas(filtro);
	}
	
	@Scheduled(cron="0 0 4 * * ?")
	public void buscarFinPrimerPeriodo() throws ParseException{
		FiltroAlertasDto filtro = new FiltroAlertasDto();
		filtro.setEstado(true);
		alertas = alertasDao.buscarFinPrimerPeriodo(filtro);
		for(Alertas a : alertas){
			if((a.getPromocion() != null) && (a.getFechaDeReferencia().equals(a.getPromocion().getFechaFinPeriodo1()))){
				a.setFechaDeReferencia(a.getPromocion().getFechaFinFinal());
				cambiaFechas(a);
				alertasDao.actualizar(a);
			}
		}
	}
	
	public void cambiaFechas(Alertas a) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(a.getFechaDeReferencia());
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(fecha));
		c.add(Calendar.DATE, -30);
		a.setFechaDeAviso(c.getTime());
	}
	
	public List<Alertas> getAlertas() {
		return alertas;
	}
	public void setAlertas(List<Alertas> alertas) {
		this.alertas = alertas;
	}

	
}
