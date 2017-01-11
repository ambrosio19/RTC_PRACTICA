package net.atos.practica.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.exception.LlamaloXException;
import net.atos.practica.exception.ManejadorException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class LoginDao {
	private static Logger log =Logger.getLogger(LoginDao.class);

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	
	public Colaborador buscarUsuarioPorUsernameYPassword(String codigo, String password) {
		try{
			Query query = em.createQuery("Select u from Colaborador u left join fetch u.rol p where u.codigo=:campoBuscar and u.pwd=:campoPass  ");
			query.setParameter("campoBuscar",codigo);
			query.setParameter("campoPass",password);
			
			Colaborador listo=(Colaborador) query.getSingleResult();
			log.debug("<-----LoginDao: Colaborador.getNombre: " + listo.getNombre() );
			return listo;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
}
