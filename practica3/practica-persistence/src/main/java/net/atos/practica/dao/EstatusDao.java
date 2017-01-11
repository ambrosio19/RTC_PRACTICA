package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroEstatusDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Estatus;
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
public class EstatusDao implements InterfaceDao<Estatus, FiltroEstatusDto>{
	
	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void crear(Estatus e) {
		try{
			em.persist(e);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Estatus actualizar(Estatus e) {
		try{
			return em.merge(e);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	public void borrar(Estatus e) {
		try{
			Estatus ref = em.find(Estatus.class, e.getIdEstatus());
			em.remove(ref);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Estatus> buscar(FiltroEstatusDto filtro) {
		try{
			QueryBuilder qb = new QueryBuilder("Select e from Estatus e where 1=1");
		
			qb.addConditionalJPQLClause("and upper(e.nombreEstatus) like :nombreEstatus", "nombreEstatus", "%" + filtro.getNombreEstatus().toUpperCase() + "%", !Utils.isNullOrBlank(filtro.getNombreEstatus()));
				
			return qb.executeQuery(em);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(int idEstatus) {
		try{
			return em.createQuery("From Colaborador c Left Join Fetch c.estatus ce Where ce.idEstatus = :idEstatus and 1=1").setParameter("idEstatus", idEstatus).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Estatus> listarEstatus() {
		try{
			return em.createQuery("FROM Estatus").getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradorEstatus(String nombreEstatus) {
		try{
			return em
					.createQuery(
							"from Colaborador c left join fetch c.estatus e WHERE e.nombreEstatus = :nombre")
					.setParameter("nombre", nombreEstatus).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(String nombreEstatus) {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND (e is null OR e.nombreEstatus != :nombre)").setParameter("nombre", nombreEstatus).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores() {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.estatus e WHERE 1=1").getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Estatus> validarNombre(Estatus estatusSelec) {
		try{
			return em.createQuery("FROM Estatus e WHERE e.nombreEstatus = :nombre").setParameter("nombre", estatusSelec.getNombreEstatus()).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
}
