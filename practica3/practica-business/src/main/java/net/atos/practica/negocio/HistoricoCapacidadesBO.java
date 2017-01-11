package net.atos.practica.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.HistoricoCapacidadesDao;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.HistoricoCapacidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HistoricoCapacidadesBO implements InterfaceBO<Capacidad, Colaborador>{
	
	@Autowired
	private HistoricoCapacidadesDao historicoCapacidadesDao;
	
		
	public void insertar (Capacidad capacidad,Colaborador colaborador) {
		List<HistoricoCapacidades> listaHc=buscar(colaborador);
		HistoricoCapacidades hc = new HistoricoCapacidades();
		HistoricoCapacidades hc2;
		
		if (!listaHc.isEmpty()){
			hc2=listaHc.get(0);
			hc2.setFechaFinColabCap(new Date());
			historicoCapacidadesDao.actualizar(hc2);
		}
		
		if(capacidad != null){
			hc.setColaborador(colaborador);
			hc.setCapacidad(capacidad);
			hc.setFechaIniColabCap(Calendar.getInstance().getTime());
			hc.setFechaFinColabCap(null);
		
			historicoCapacidadesDao.crear(hc);
		}
	}
	
	@Override
	public List<HistoricoCapacidades> buscar(Colaborador colaborador) throws NoResultException{
		return historicoCapacidadesDao.buscar(colaborador);
		
	}

	@Override
	public void crear(Capacidad objeto) {}

	@Override
	public void actualizar(Capacidad objeto) {}

	@Override
	public void borrar(Capacidad objeto) {}
}
