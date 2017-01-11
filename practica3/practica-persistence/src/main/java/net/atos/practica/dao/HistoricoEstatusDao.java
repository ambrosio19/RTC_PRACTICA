package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.HistoricoEstatus;
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
@Transactional(propagation=Propagation.REQUIRED)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class HistoricoEstatusDao implements InterfaceDao<HistoricoEstatus, Colaborador>{

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void crear(HistoricoEstatus he) {
		try{
			em.persist(he);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	// Retorna el ultimo historico asociado al colaborador cuya fecha
	// de finalización es nula.

	@Override
	@SuppressWarnings("unchecked")
	public List<HistoricoEstatus> buscar(Colaborador colaborador) {
		try{
			return em.createQuery("FROM HistoricoEstatus HE WHERE HE.fechaFinColabEst is null and HE.colaborador.idColaborador = :id_colaborador")
					.setParameter("id_colaborador", colaborador.getIdColaborador())
					.getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public HistoricoEstatus actualizar(HistoricoEstatus he) {
		try{
			return em.merge(he);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public void borrar(HistoricoEstatus objeto) {}
}