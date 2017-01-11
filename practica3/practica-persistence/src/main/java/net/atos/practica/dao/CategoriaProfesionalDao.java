package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroCatProfesionalDto;
import net.atos.practica.entity.CategoriaProfesional;
import net.atos.practica.entity.Colaborador;
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
public class CategoriaProfesionalDao implements InterfaceDao<CategoriaProfesional, FiltroCatProfesionalDto>{
	
	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void crear(CategoriaProfesional cp) {
		try{
			em.persist(cp);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public CategoriaProfesional actualizar(CategoriaProfesional cp) {
		try{
			CategoriaProfesional merge = em.merge(cp);
			em.flush();
			return merge;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	public void borrar(CategoriaProfesional cp) {
		try{
			CategoriaProfesional ref = em.find(CategoriaProfesional.class, cp.getIdCategoriaPro());
			em.remove(ref);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CategoriaProfesional> buscar(FiltroCatProfesionalDto filtro) {
		try{
			QueryBuilder qb = new QueryBuilder("Select cp from CategoriaProfesional cp where 1=1");
		
			qb.addConditionalJPQLClause("and upper(cp.nombreCategoriaPro) like :nombreCategoriaPro", "nombreCategoriaPro", "%" + filtro.getNombreCategoriaPro().toUpperCase() + "%", !Utils.isNullOrBlank(filtro.getNombreCategoriaPro()));
				
			return qb.executeQuery(em);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(int idCategoria) {
		try{
			return em.createQuery("From Colaborador c Left Join Fetch c.categoriaPro catp left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND catp.idCategoriaPro = :idCategoria").setParameter("idCategoria", idCategoria).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoriaProfesional> listarCategoriasProfesionales() {
		try{
			return em.createQuery("FROM CategoriaProfesional").getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradorCategorias(String nombreCatProfesional) {
		try{
			return em
					.createQuery(
							"from Colaborador c left join fetch c.categoriaPro cp left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND cp.nombreCategoriaPro = :nombre")
					.setParameter("nombre", nombreCatProfesional).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(String nombreCatProfesional) {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.categoriaPro cp left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND (cp is null OR cp.nombreCategoriaPro != :nombre)").setParameter("nombre", nombreCatProfesional).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores() {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.categoriaPro cp WHERE 1=1").getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<CategoriaProfesional> buscarIgual(String filtro) {
		try{
			if(filtro == null || filtro.equals("")){
				QueryBuilder qb = new QueryBuilder("from CategoriaProfesional");
				return qb.executeQuery(em);
			}
			else{
				QueryBuilder qb = new QueryBuilder("from CategoriaProfesional cp where 1=1 ");
				qb.addConditionalJPQLClause("and upper(cp.nombreCategoriaPro) like :nombre", "nombre", filtro.toUpperCase(), !Utils.isNullOrBlank(filtro));
				return qb.executeQuery(em);
			}
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
}
