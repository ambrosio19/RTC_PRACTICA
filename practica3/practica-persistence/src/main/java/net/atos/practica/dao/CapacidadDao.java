package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroCapacidadDto;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.Colaborador;
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
public class CapacidadDao implements InterfaceDao<Capacidad, String> {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void crear(Capacidad c) {
		try {
			em.persist(c);
			em.flush();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Capacidad actualizar(Capacidad c) {
		try {
			return em.merge(c);
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@Override
	public void borrar(Capacidad c) {
		try {
			Capacidad ref = em.find(Capacidad.class, c.getIdCapacidad());
			em.remove(ref);
			em.flush();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Capacidad> buscar(String filtro) {
		try {
			QueryBuilder qb = new QueryBuilder("from Capacidad c where 1=1 ");

			qb.addConditionalJPQLClause(
					"and upper(c.nombreCapacidad) like :nombre", "nombre", "%"
							+ filtro.toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro));

			return qb.executeQuery(em);
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	public List<Capacidad> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroCapacidadDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Capacidad> query = cb.createQuery(Capacidad.class);
			Root<Capacidad> capacidad = query.from(Capacidad.class);
			query.select(capacidad);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombreCapacidad() != null
					&& !filtro.getNombreCapacidad().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(capacidad.get("nombreCapacidad").as(
								String.class)), "%"
								+ filtro.getNombreCapacidad().toUpperCase()
								+ "%"));
			}

			if (sortOrder.equals(SortOrder.ASCENDING)) {
				query.orderBy(cb.asc(capacidad.get(sortField)));
			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				query.orderBy(cb.desc(capacidad.get(sortField)));
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

	public int numeroCapacidades(FiltroCapacidadDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Capacidad> query = cb.createQuery(Capacidad.class);
			Root<Capacidad> capacidad = query.from(Capacidad.class);
			query.select(capacidad);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombreCapacidad() != null
					&& !filtro.getNombreCapacidad().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(capacidad.get("nombreCapacidad").as(
								String.class)), "%"
								+ filtro.getNombreCapacidad().toUpperCase()
								+ "%"));
			}

			if (filtrosBusqueda.getExpressions().size() > 0) {

				query.where(filtrosBusqueda);
			}

			return em.createQuery(query).getResultList().size();

		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Capacidad> buscarIgual(String filtro) {
		try {
			QueryBuilder qb = new QueryBuilder("from Capacidad c where 1=1 ");

			qb.addConditionalJPQLClause(
					"and upper(c.nombreCapacidad) like :nombre", "nombre",
					filtro.toUpperCase(), !Utils.isNullOrBlank(filtro));

			return qb.executeQuery(em);
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Capacidad> listarCapacidades() {
		try {
			return em.createQuery("FROM Capacidad").getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Capacidad> guardar(Capacidad nuevaCapacidad) {
		try {
			return em
					.createQuery(
							"From Capacidad c WHERE c.nombreCapacidad = :nuevaCapacidad")
					.setParameter("nuevaCapacidad",
							nuevaCapacidad.getNombreCapacidad())
					.getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> buscarColaboradoresCapacidadActual(String capacidad) {
		try {
			return em
					.createQuery(
							"From Colaborador c Left Join Fetch c.capacidad ca left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND ca.nombreCapacidad = :capacidad")
					.setParameter("capacidad", capacidad).getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> buscarColaboradoes(String capacidad) {
		try {
			return em
					.createQuery(
							"From Colaborador c Left Join Fetch c.capacidad ca WHERE ca.nombreCapacidad != 'BAJA' AND (ca.nombreCapacidad is null OR ca.nombreCapacidad != :capacidad)")
					.setParameter("capacidad", capacidad).getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> buscarListaColaboradores() {
		try {
			return em.createQuery("From Colaborador").getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}
}