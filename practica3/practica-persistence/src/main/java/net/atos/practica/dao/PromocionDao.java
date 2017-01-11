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
import net.atos.practica.dto.FiltroPromocionDto;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Promocion;
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
public class PromocionDao implements
		InterfaceDao<Promocion, FiltroPromocionDto> {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void crear(Promocion p) {
		try {
			em.persist(p); // Merge en lugar de Persist: Merge si está
							// actualiza, si no está inserta
			em.flush();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Promocion actualizar(Promocion p) {
		try {
			Promocion merge = em.merge(p);
			em.flush();
			return merge;
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	public void borrar(Promocion p) {
		try {
			Promocion ref = em.find(Promocion.class, p.getIdPromociones());
			em.remove(ref);
			em.flush();
		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Promocion> buscar(FiltroPromocionDto filtro) {
		try {
			QueryBuilder qb = new QueryBuilder(
					"Select p from Promocion p where 1=1");

			qb.addConditionalJPQLClause(
					"and upper(p.nombrePromocion) like :nombrePromocion",
					"nombrePromocion", "%"
							+ filtro.getNombrePromocion().toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro.getNombrePromocion()));
			qb.addConditionalJPQLClause("and p.nConvocatoria = :nConvocatoria",
					"nConvocatoria", filtro.getnConvocatoria(),
					filtro.getnConvocatoria() != 0);

			return qb.executeQuery(em);
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	public List<Promocion> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroPromocionDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Promocion> query = cb.createQuery(Promocion.class);
			Root<Promocion> promocion = query.from(Promocion.class);
			query.select(promocion);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombrePromocion() != null
					&& !filtro.getNombrePromocion().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(promocion.get("nombrePromocion").as(
								String.class)), "%"
								+ filtro.getNombrePromocion().toUpperCase()
								+ "%"));
			}
			if ((filtro.getnConvocatoria() != null)
					&& (filtro.getnConvocatoria() != 0)) {
				filtrosBusqueda.getExpressions().add(
						cb.and(cb.equal(
								promocion.get("nConvocatoria")
										.as(Integer.class), filtro
										.getnConvocatoria())));
			}

			if (sortOrder.equals(SortOrder.ASCENDING)) {
				query.orderBy(cb.asc(promocion.get(sortField)));
			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				query.orderBy(cb.desc(promocion.get(sortField)));
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

	public int numeroPromociones(FiltroPromocionDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Promocion> query = cb.createQuery(Promocion.class);
			Root<Promocion> promocion = query.from(Promocion.class);
			query.select(promocion);
			// Filtro de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getNombrePromocion() != null
					&& !filtro.getNombrePromocion().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(promocion.get("nombrePromocion").as(
								String.class)), "%"
								+ filtro.getNombrePromocion().toUpperCase()
								+ "%"));
			}
			if (filtro.getnConvocatoria() != null
					&& filtro.getnConvocatoria() != 0) {
				filtrosBusqueda.getExpressions().add(
						cb.and(cb.equal(
								promocion.get("nConvocatoria")
										.as(Integer.class), filtro
										.getnConvocatoria())));
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
	public List<Colaborador> listarColaboradorPromocion(Promocion filtro) {
		try {
			return em
					.createQuery(
							"FROM Colaborador c LEFT JOIN FETCH c.promocion p left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND p.idPromociones = :idPromociones")
					.setParameter("idPromociones", filtro.getIdPromociones())
					.getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Promocion> listarPromociones() {
		try {
			return em.createQuery("FROM Promocion").getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(int idPromocion) {
		try {
			return em
					.createQuery(
							"From Colaborador c Left Join Fetch c.promocion p left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND p.idPromociones = :idPromocion")
					.setParameter("idPromocion", idPromocion).getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(String nombrePromocion) {
		try {
			return em
					.createQuery(
							"FROM Colaborador c left join fetch c.promocion p left join fetch c.estatus e WHERE e.nombreEstatus != 'BAJA' AND (p.nombrePromocion is null OR p.nombrePromocion != :nombre)")
					.setParameter("nombre", nombrePromocion).getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores() {
		try {
			return em
					.createQuery(
							"FROM Colaborador c left join fetch c.promocion p where 1=1")
					.getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}
}