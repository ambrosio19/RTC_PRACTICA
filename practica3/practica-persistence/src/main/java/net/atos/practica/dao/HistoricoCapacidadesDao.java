package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.HistoricoCapacidades;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.exception.ManejadorException;
import net.atos.practica.interfaces.InterfaceDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class HistoricoCapacidadesDao implements InterfaceDao<HistoricoCapacidades, Colaborador>{

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void crear(HistoricoCapacidades hc) {
		try{
			em.persist(hc);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	// Retorna el ultimo historico asociado al colaborador cuya fecha
	// de finalización es nula.
	@Override
	@SuppressWarnings("unchecked")
	public List<HistoricoCapacidades> buscar(Colaborador colaborador) {
		try{
			return em.createQuery(
							"FROM HistoricoCapacidades HC WHERE HC.fechaFinColabCap is null and HC.colaborador.idColaborador = :id_colaborador")
					.setParameter("id_colaborador", colaborador.getIdColaborador())
					.getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	// Actualiza la fecha de finalizacion de la ultima capacidad en la que
	// trabajaba
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public HistoricoCapacidades actualizar(HistoricoCapacidades hc) {
		try{
			HistoricoCapacidades merge = em.merge(hc);
			return merge;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public void borrar(HistoricoCapacidades objeto) {}
}