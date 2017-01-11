package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.RolesSeguridad;
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
public class RolesSeguridadDao implements InterfaceDao<RolesSeguridad, String>{
	
	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void crear(RolesSeguridad r) {
		try{
			em.persist(r);
			em.flush();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public RolesSeguridad actualizar(RolesSeguridad r) {
		try{
			RolesSeguridad merge = em.merge(r);
			em.flush();
			return merge;
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@Override
	public void borrar(RolesSeguridad r) {
		try{
			RolesSeguridad ref = em.find(RolesSeguridad.class, r.getIdRol());
			em.remove(ref);
			em.flush();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RolesSeguridad> buscar(String filtro) {
		try{
			QueryBuilder qb = new QueryBuilder("from RolesSeguridad r where 1=1 ");
			qb.addConditionalJPQLClause("and upper(r.nombreRol) like :nombre", "nombre", "%" + filtro.toUpperCase() + "%", !Utils.isNullOrBlank(filtro));
			return qb.executeQuery(em);
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RolesSeguridad> buscarIgual(String filtro){
		try{
			QueryBuilder qb = new QueryBuilder("from RolesSeguridad r where 1=1 ");
			qb.addConditionalJPQLClause("and upper(r.nombreRol) like :nombre", "nombre", filtro.toUpperCase(), !Utils.isNullOrBlank(filtro));
			return qb.executeQuery(em);
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RolesSeguridad> listarRoles() {
		try{
			return em.createQuery("FROM RolesSeguridad").getResultList();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradorRol(String nombreRol) {
		try{
			return em.createQuery(
							"from Colaborador c left join fetch c.rol r left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND r.nombreRol = :nombre")
					.setParameter("nombre", nombreRol).getResultList();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(String nombreRol) {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.rol r left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND (r is null OR r.nombreRol != :nombre)").setParameter("nombre", nombreRol).getResultList();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores() {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.rol WHERE 1=1").getResultList();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(int idRol){
		try{
			return em.createQuery("From Colaborador c left join fetch c.rol crol left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND crol.idRol = :idRol").setParameter("idRol", idRol).getResultList();
		} catch(PersistenceException pe){
			throw new ManejadorException(pe);
		}
	}
}
