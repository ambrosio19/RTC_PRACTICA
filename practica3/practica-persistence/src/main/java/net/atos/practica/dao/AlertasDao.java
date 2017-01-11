package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroAlertasDto;
import net.atos.practica.entity.Alertas;
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
public class AlertasDao implements InterfaceDao<Alertas, FiltroAlertasDto> {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	public void crear(Alertas alerta) {
		try{
			em.persist(alerta);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public List<?> buscar(FiltroAlertasDto filtro) {
		try{
			QueryBuilder qb = new QueryBuilder(
					"Select a from Alertas a where 1=1");
		
			qb.addConditionalJPQLClause("and upper(a.nombreAlerta) like :alerta", "alerta", "%" + filtro.getAlerta().toUpperCase() + "%", !Utils.isNullOrBlank(filtro.getAlerta()));
			qb.addConditionalJPQLClause("and a.fechaDeAviso >= :fechaIni", "fechaIni", filtro.getFechaMinina(), filtro.isFmin());
			qb.addConditionalJPQLClause("and a.fechaDeReferencia <= :fechaFin", "fechaFin", filtro.getFechaMaxima(), filtro.isFmax());
			return qb.executeQuery(em);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Alertas> buscarEnPromocion(FiltroAlertasDto filtro) {
		try{
			QueryBuilder qb = new QueryBuilder("Select a from Alertas a left join fetch a.promocion pr where 1=1");
	
			qb.addConditionalJPQLClause("and pr.idPromociones = :promocion", "promocion", filtro.getPromocion().getIdPromociones(), !Utils.isNullOrBlank(filtro.getPromocion().getNombrePromocion()));
			return qb.executeQuery(em);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Alertas> buscarActivas(FiltroAlertasDto filtro) {
		try{
			return em
					.createQuery(
							"From Alertas a where a.estado = :estado and CURRENT_DATE between a.fechaDeAviso and a.fechaDeReferencia+1")
					.setParameter("estado", filtro.isEstado())
					.getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Alertas> buscarFinPrimerPeriodo(FiltroAlertasDto filtro) {
		try{
			return em
					.createQuery(
							"From Alertas a left join fetch a.promocion p where 1=1 and a.estado = :estado and CURRENT_DATE between a.fechaDeReferencia+1 and a.fechaDeReferencia+2")
					.setParameter("estado", filtro.isEstado())
					.getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public Alertas actualizar(Alertas alerta) {
		try{
			Alertas merge = em.merge(alerta);
			em.flush();
			return merge;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public void borrar(Alertas alerta) {
		try{
			Alertas ref = em.find(Alertas.class, alerta.getId_Alertas());
			em.remove(ref);
			em.flush();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
}
