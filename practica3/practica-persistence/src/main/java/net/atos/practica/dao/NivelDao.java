package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.entity.Nivel;
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
public class NivelDao implements InterfaceDao<Nivel, Nivel>{
	
	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void crear(Nivel n) {
		try{
			em.persist(n);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Nivel actualizar(Nivel n) {
		try{
			return em.merge(n);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	public void borrar(Nivel n) {
		try{
			Nivel ref = em.find(Nivel.class, n.getIdNivel());
			em.remove(ref);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	public List<Nivel> buscar(Nivel filtro) {
		try{
			return null;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Nivel> listarNiveles() {
		try{
			return em.createQuery("FROM Nivel").getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
}