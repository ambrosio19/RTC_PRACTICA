package net.atos.practica.negocio;

import java.util.Date;
import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.HistoricoProyectosDao;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.HistoricoProyectos;
import net.atos.practica.entity.Proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HistoricoProyectoBO implements InterfaceBO<Proyecto, Colaborador>{
	
	@Autowired
	private HistoricoProyectosDao historicoProyectoDao;
	
		
	public void insertar (Proyecto proyecto,Colaborador colaborador) {
		HistoricoProyectos hp = new HistoricoProyectos();
		HistoricoProyectos hp2;
		List<HistoricoProyectos> listaHp = buscar(colaborador);
		
		if(!listaHp.isEmpty()){
			hp2 = listaHp.get(0);
			//Asignar fecha actual al proyecto anterior
			hp2.setFechaFinColabProy(new Date());
			historicoProyectoDao.actualizar(hp2);
		}
		
		if(proyecto != null){
			hp.setColaborador(colaborador);
			hp.setProyecto(proyecto);
			hp.setFechaIniColabProy(new Date());
			hp.setFechaFinColabProy(null);
		
			historicoProyectoDao.crear(hp);
		}
	}
	
	@Override
	public List<HistoricoProyectos> buscar(Colaborador colaborador){
		return historicoProyectoDao.buscar(colaborador);
	}

	@Override
	public void crear(Proyecto objeto) {}

	@Override
	public void actualizar(Proyecto objeto) {}

	@Override
	public void borrar(Proyecto objeto) {}
}
