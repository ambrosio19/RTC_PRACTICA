package net.atos.practica.negocio;

import java.util.Date;
import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.HistoricoEstatusDao;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Estatus;
import net.atos.practica.entity.HistoricoEstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HistoricoEstatusBO implements InterfaceBO<Estatus, Colaborador>{
	
	@Autowired
	private HistoricoEstatusDao historicoEstatusDao;
	
		
	public void insertar (Estatus estatus,Colaborador colaborador) {
		HistoricoEstatus he = new HistoricoEstatus();
		HistoricoEstatus he2;
		List<HistoricoEstatus> listaHe = buscar(colaborador);
		
		if(!listaHe.isEmpty()){
			he2 = listaHe.get(0);
			//Asignar fecha actual al proyecto anterior
			he2.setFechaFinColabEst(new Date());
			historicoEstatusDao.actualizar(he2);
		}
		
		if(estatus != null){
			he.setColaborador(colaborador);
			he.setEstatus(estatus);
			he.setFechaIniColabEst(new Date());
			he.setFechaFinColabEst(null);
		
			historicoEstatusDao.crear(he);
		}
	}
	
	@Override
	public List<HistoricoEstatus> buscar(Colaborador colaborador){
		return historicoEstatusDao.buscar(colaborador);
	}

	@Override
	public void crear(Estatus objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(Estatus objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrar(Estatus objeto) {
		// TODO Auto-generated method stub
		
	}
}
