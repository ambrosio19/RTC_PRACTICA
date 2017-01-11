package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroTitulacionDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Nivel;
import net.atos.practica.entity.Titulacion;
import net.atos.practica.exception.ManejadorException;
import net.atos.practica.interfaces.InterfaceDao;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TitulacionDao implements
		InterfaceDao<Titulacion, FiltroTitulacionDto> {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void crear(Titulacion t) {
		try {
			em.persist(t);
			em.flush();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Titulacion actualizar(Titulacion t) {
		try {
			Titulacion merge = em.merge(t);
			em.flush();
			return merge;
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	public void borrar(Titulacion t) {
		try {
			Titulacion ref = em.find(Titulacion.class, t.getIdTitulacion());
			em.remove(ref);
			em.flush();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Titulacion> buscar(FiltroTitulacionDto filtro) {
		try {
			QueryBuilder qb = new QueryBuilder(
					"Select p from Titulacion p Left Join Fetch p.nivelTitulacion nt where 1=1");

			qb.addConditionalJPQLClause(
					"and upper(p.nombreTitulacion) like :nombreTitulacion",
					"nombreTitulacion", "%"
							+ filtro.getNombreTitulacion().toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro.getNombreTitulacion()));
			qb.addConditionalJPQLClause(
					"and upper(nt.nombreNivel) like :nombreNivel",
					"nombreNivel", "%"
							+ filtro.getNivelTitulacion().toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro.getNivelTitulacion()));

			return qb.executeQuery(em);
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	public List<Titulacion> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroTitulacionDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Titulacion> query = cb.createQuery(Titulacion.class);
			Root<Titulacion> titulacion = query.from(Titulacion.class);
			query.select(titulacion);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			Fetch<Titulacion, Nivel> nivelTitulacion = titulacion.fetch(
					"nivelTitulacion", JoinType.LEFT);

			if (filtro.getNombreTitulacion() != null
					&& !filtro.getNombreTitulacion().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(titulacion.get("titulacion")
								.get("nombreTitulacion").as(String.class)), "%"
								+ filtro.getNombreTitulacion().toUpperCase()
								+ "%"));
			}
			if ((filtro.getNivelTitulacion() != null)
					&& !filtro.getNivelTitulacion().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(titulacion.get("nivelTitulacion")
								.get("nombreNivel").as(String.class)), "%"
								+ filtro.getNivelTitulacion().toUpperCase()
								+ "%"));

			}

			if (sortOrder.equals(SortOrder.ASCENDING)) {
				query.orderBy(cb.asc(titulacion.get(sortField)));
			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				query.orderBy(cb.desc(titulacion.get(sortField)));
			}

			if (filtrosBusqueda.getExpressions().size() > 0) {
				// query.select(proyecto);
				query.where(filtrosBusqueda);
			}

			return em.createQuery(query).setFirstResult(first)
					.setMaxResults(pageSize).getResultList();

		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}

	}

	public int numeroTitulaciones(FiltroTitulacionDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Titulacion> query = cb.createQuery(Titulacion.class);
			Root<Titulacion> titulacion = query.from(Titulacion.class);
			query.select(titulacion);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombreTitulacion() != null
					&& !filtro.getNombreTitulacion().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(titulacion.get("nombreTitulacion").as(
								String.class)), "%"
								+ filtro.getNombreTitulacion().toUpperCase()
								+ "%"));
			}
			// if (filtro. != null
			// && filtro.getnConvocatoria() != 0) {
			// filtrosBusqueda.getExpressions().add(
			// cb.and(cb.equal(
			// titulacion.get("nConvocatoria")
			// .as(Integer.class), filtro
			// .getnConvocatoria())));
			// }

			if (filtrosBusqueda.getExpressions().size() > 0) {
				// query.select(proyecto);
				query.where(filtrosBusqueda);
			}

			return em.createQuery(query).getResultList().size();

		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Titulacion> buscar(String titulacion) {
		try {
			return em
					.createQuery(
							"From Titulacion t Where t.nombreTitulacion = :titulacion and 1=1")
					.setParameter("titulacion", titulacion).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(int idTitulacion) {
		try {
			return em
					.createQuery(
							"From Colaborador c Left Join Fetch c.titulacion ct left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND ct.idTitulacion = :idTitulacion")
					.setParameter("idTitulacion", idTitulacion).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Titulacion> listarTitulaciones() {
		try {
			return em.createQuery("FROM Titulacion t").getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradorTitulacion(String nombreTitulacion) {
		try {
			return em
					.createQuery(
							"from Colaborador c left join fetch c.titulacion t left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND t.nombreTitulacion = :nombre")
					.setParameter("nombre", nombreTitulacion).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(String nombreTitulacion) {
		try {
			return em
					.createQuery(
							"FROM Colaborador c left join fetch c.titulacion t left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND (t is null OR t.nombreTitulacion != :nombre)")
					.setParameter("nombre", nombreTitulacion).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores() {
		try {
			return em
					.createQuery(
							"FROM Colaborador c left join fetch c.titulacion t WHERE 1=1")
					.getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}
}
