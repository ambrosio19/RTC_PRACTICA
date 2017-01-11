package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.HistoricoProyectos;
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
public class HistoricoProyectosDao implements InterfaceDao<HistoricoProyectos, Colaborador>{

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void crear(HistoricoProyectos hp) {
		try{
			em.persist(hp);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	// Retorna el ultimo historico asociado al colaborador cuya fecha
	// de finalización es nula.
	@Override
	@SuppressWarnings("unchecked")
	public List<HistoricoProyectos> buscar(Colaborador colaborador) {
		try{
			return em.createQuery("FROM HistoricoProyectos HP WHERE HP.fechaFinColabProy is null and HP.colaborador.idColaborador = :id_colaborador")
					.setParameter("id_colaborador", colaborador.getIdColaborador())
					.getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public HistoricoProyectos actualizar(HistoricoProyectos hp) {
		try{
			return em.merge(hp);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public void borrar(HistoricoProyectos objeto) {}
}