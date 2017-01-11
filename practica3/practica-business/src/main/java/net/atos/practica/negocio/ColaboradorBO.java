package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.ColaboradorDao;
import net.atos.practica.dto.FiltroCapacidadDto;
import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.dto.FiltroColaboradorCapacidad;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ColaboradorBO implements InterfaceBO<Colaborador, String>{
	
	@Autowired
	private ColaboradorDao colaboradorDao;
	
	@Override
	public void actualizar(Colaborador c){
		colaboradorDao.actualizar(c);
	}

	public boolean verificarPassActual(String passActual, String das){
		
		return 	!colaboradorDao.verificarPassActual(passActual, das).isEmpty();
	}
	
	public boolean verificarPass(String passActual, String newPass, String das){
		return colaboradorDao.verificarPass(passActual, newPass, das);
	}

	@Override
	public void crear(Colaborador objeto) {}

	@Override
	public List<?> buscar(String objeto) {
		return (List<?>) colaboradorDao.buscar(objeto);
	}
	
	public List<Colaborador> buscar(FiltroColaboradorAdminDto filtro){
		return colaboradorDao.buscar(filtro);
	}

	@Override
	public void borrar(Colaborador objeto) {}

	public List<FiltroColaboradorCapacidad> colaboradoresCapacidad(){
		return colaboradorDao.colaboradoresCapacidad();
	}
}
