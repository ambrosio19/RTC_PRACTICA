package net.atos.practica.negocio;

import java.util.List;

import net.atos.practica.business.interfaces.InterfaceBO;
import net.atos.practica.dao.PromocionDao;
import net.atos.practica.dto.FiltroPromocionDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Promocion;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromocionBO implements InterfaceBO<Promocion, FiltroPromocionDto> {

	@Autowired
	private PromocionDao promocionDao;

	@Override
	public List<Promocion> buscar(FiltroPromocionDto filtro) {
		return promocionDao.buscar(filtro);
	}

	public List<Colaborador> buscar(int idPromocion) {
		return promocionDao.buscar(idPromocion);
	}

	public List<Promocion> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroPromocionDto filtro) {
		return promocionDao.buscar(first, pageSize, sortField, sortOrder,
				filtro);

	}

	public int numeroPromociones(FiltroPromocionDto filtro) {
		return promocionDao.numeroPromociones(filtro);
	}

	@Override
	public void actualizar(Promocion p) {
		promocionDao.actualizar(p);
	}

	@Override
	public void borrar(Promocion p) {
		promocionDao.borrar(p);
	}

	@Override
	public void crear(Promocion p) {
		promocionDao.crear(p);
	}

	public List<Promocion> listarPromociones() {
		return promocionDao.listarPromociones();
	}

	public List<Colaborador> listarColaboradorPromocion(Promocion promocionSelec) {
		return promocionDao.listarColaboradorPromocion(promocionSelec);
	}

	public List<Colaborador> listarColaboradores(String nombrePromocion) {
		return promocionDao.listarColaboradores(nombrePromocion);
	}

	public List<Colaborador> listarColaboradores() {
		return promocionDao.listarColaboradores();
	}
}