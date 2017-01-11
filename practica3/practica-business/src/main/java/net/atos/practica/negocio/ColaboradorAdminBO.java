package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.ColaboradorAdminDao;
import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.entity.Colaborador;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColaboradorAdminBO implements
		InterfaceBO<Colaborador, FiltroColaboradorAdminDto> {

	@Autowired
	private ColaboradorAdminDao colaboradorAdminDao;

	@Override
	public List<Colaborador> buscar(FiltroColaboradorAdminDto filtro) {
		return colaboradorAdminDao.buscar(filtro);
	}

	public List<Colaborador> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroColaboradorAdminDto filtroAdmin) {
		return colaboradorAdminDao.buscar(first, pageSize, sortField,
				sortOrder, filtroAdmin);

	}

	public int numeroColaboradores(FiltroColaboradorAdminDto filtroAdmin) {
		return colaboradorAdminDao.numeroColaboradores(filtroAdmin);
	}

	@Override
	public void actualizar(Colaborador c) {
		colaboradorAdminDao.actualizar(c);
	}

	@Override
	public void borrar(Colaborador c) {
		colaboradorAdminDao.borrar(c);
	}

	@Override
	public void crear(Colaborador c) {
		colaboradorAdminDao.crear(c);
	}

	public boolean validar(Colaborador colaboradorSelec) {
		return !colaboradorAdminDao.validar(colaboradorSelec).isEmpty();
	}

	public boolean validarNif(Colaborador colaboradorSelec) {
		return !colaboradorAdminDao.validarNif(colaboradorSelec).isEmpty();

	}

	public Colaborador buscarPorDas(String usuario) {
		return colaboradorAdminDao.buscarPorDas(usuario);
	}
}