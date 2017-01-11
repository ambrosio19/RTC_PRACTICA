package net.atos.practica.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroProyectoDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Proyecto;
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
public class ProyectoDao implements InterfaceDao<Proyecto, FiltroProyectoDto> {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void crear(Proyecto proyecto) {
		try {
			em.merge(proyecto); // Merge en lugar de persist: Merge si está
								// actualiza, si no está inserta.
			em.flush();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Proyecto actualizar(Proyecto proyecto) {
		try {
			return em.merge(proyecto);
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	public void borrar(Proyecto proyecto) {
		try {
			Proyecto ref = em.find(Proyecto.class, proyecto.getId_Proyecto());
			em.remove(ref);
			em.flush();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Proyecto> buscar(FiltroProyectoDto filtro) {

		try {

			QueryBuilder qb = new QueryBuilder(
					"Select p from Proyecto p where 1=1 ");

			qb.addConditionalJPQLClause("and upper(p.wbs) like :wbs", "wbs",
					"%" + filtro.getWbs().toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro.getWbs()));
			qb.addConditionalJPQLClause(
					"and upper(p.nombreProyecto) like :nombreProyecto",
					"nombreProyecto", "%" + filtro.getNombre().toUpperCase()
							+ "%", !Utils.isNullOrBlank(filtro.getNombre()));
			qb.addConditionalJPQLClause("and p.fechaInicioPro = :fechaInicio",
					"fechaInicio", filtro.getFechaInicio(), filtro.isFechaIn());
			qb.addConditionalJPQLClause("and p.fechaFinPro = :fechaFin",
					"fechaFin", filtro.getFechaFin(), filtro.isFechaFi());

			return qb.executeQuery(em);

		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	public List<Proyecto> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroProyectoDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Proyecto> query = cb.createQuery(Proyecto.class);
			Root<Proyecto> proyecto = query.from(Proyecto.class);
			query.select(proyecto);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombre() != null && !filtro.getNombre().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(proyecto.get("nombreProyecto").as(
								String.class)), "%"
								+ filtro.getNombre().toUpperCase() + "%"));
			}
			if (filtro.getWbs() != null && !filtro.getWbs().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(proyecto.get("wbs").as(String.class)),
								"%" + filtro.getWbs().toUpperCase() + "%"));
			}
			if (filtro.getFechaInicio() != null) {
				filtrosBusqueda.getExpressions().add(
						cb.greaterThanOrEqualTo(proyecto.get("fechaInicioPro")
								.as(Date.class), filtro.getFechaInicio()));
			}
			if (filtro.getFechaFin() != null) {
				filtrosBusqueda.getExpressions().add(
						cb.lessThan(proyecto.get("fechaFinPro").as(Date.class),
								filtro.getFechaFin()));
			}

			if (sortOrder.equals(SortOrder.ASCENDING)) {
				query.orderBy(cb.asc(proyecto.get(sortField)));
			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				query.orderBy(cb.desc(proyecto.get(sortField)));
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

	public int numeroProyectos(FiltroProyectoDto filtro) {

		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Proyecto> query = cb.createQuery(Proyecto.class);
			Root<Proyecto> proyecto = query.from(Proyecto.class);
			query.select(proyecto);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombre() != null && !filtro.getNombre().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(proyecto.get("nombreProyecto").as(
								String.class)), "%"
								+ filtro.getNombre().toUpperCase() + "%"));
			}
			if (filtro.getWbs() != null && !filtro.getWbs().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(proyecto.get("wbs").as(String.class)),
								"%" + filtro.getWbs().toUpperCase() + "%"));
			}
			if (filtro.getFechaInicio() != null) {
				filtrosBusqueda.getExpressions().add(
						cb.greaterThanOrEqualTo(proyecto.get("fechaInicioPro")
								.as(Date.class), filtro.getFechaInicio()));
			}
			if (filtro.getFechaFin() != null) {
				filtrosBusqueda.getExpressions().add(
						cb.lessThan(proyecto.get("fechaFinPro").as(Date.class),
								filtro.getFechaFin()));
			}

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
	public List<Colaborador> listarColaboradorProyectos(String proyectoWbs) {
		try {
			return em
					.createQuery(
							"from Colaborador c left join fetch c.proyecto p left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND p.wbs= :wbs")
					.setParameter("wbs", proyectoWbs).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> listarProyectos() {
		try {
			return em.createQuery("FROM Proyecto").getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> existeProyectoWbs(Proyecto proyecto) {
		try {
			return em.createQuery("FROM Proyecto p WHERE p.wbs = :wbs")
					.setParameter("wbs", proyecto.getWbs()).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(String wbs) {
		try {
			return em
					.createQuery(
							"FROM Colaborador c left join fetch c.proyecto p left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND (p.wbs is null OR p.wbs != :wbs)")
					.setParameter("wbs", wbs).getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores() {
		try {
			return em
					.createQuery(
							"FROM Colaborador c left join fetch c.proyecto p WHERE 1=1")
					.getResultList();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}
}
