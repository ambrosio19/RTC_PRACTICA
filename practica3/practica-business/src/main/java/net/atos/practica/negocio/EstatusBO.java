package net.atos.practica.negocio;
import java.util.List;



import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.EstatusDao;
import net.atos.practica.dto.FiltroEstatusDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Estatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EstatusBO implements InterfaceBO<Estatus, FiltroEstatusDto>{

	@Autowired
	private  EstatusDao estatusDao;

	@Override
	public List<Estatus> buscar(FiltroEstatusDto filtro){
		return estatusDao.buscar(filtro);
	}
	
	public List<Colaborador> buscar(int idEstatus){
		return estatusDao.buscar(idEstatus);
	}
	
	@Override
	public void actualizar(Estatus e){
		estatusDao.actualizar(e);	
	}
	
	@Override
	public void borrar(Estatus e){
		estatusDao.borrar(e);		
	}
	
	@Override
	public void crear(Estatus e) {		
		estatusDao.crear(e);
	}
	
	public List<Estatus> listarEstatus(){
		return estatusDao.listarEstatus();
	}

	public List<Colaborador> listarColaboradorEstatus(String nombreEstatus) {
		return estatusDao.listarColaboradorEstatus(nombreEstatus);
	}

	public List<Colaborador> listarColaboradores(String nombreEstatus) {
		return estatusDao.listarColaboradores(nombreEstatus);
	}
	
	public List<Colaborador> listarColaboradores() {
		return estatusDao.listarColaboradores();
	}

	public boolean validarNombre(Estatus estatusSelec) {
		return !estatusDao.validarNombre(estatusSelec).isEmpty();
	}
}