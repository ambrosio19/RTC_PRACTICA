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
import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.entity.Capacidad;
import net.atos.practica.entity.CategoriaProfesional;
import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Estatus;
import net.atos.practica.entity.Promocion;
import net.atos.practica.entity.Proyecto;
import net.atos.practica.entity.RolesSeguridad;
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
public class ColaboradorAdminDao implements
		InterfaceDao<Colaborador, FiltroColaboradorAdminDto> {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void crear(Colaborador c) {
		try {
			em.persist(c);
			em.flush();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Colaborador actualizar(Colaborador c) {
		try {
			Colaborador merge = em.merge(c);
			em.flush();
			return merge;
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@Override
	public void borrar(Colaborador c) {
		try {
			Colaborador ref = em.find(Colaborador.class, c.getIdColaborador());
			em.remove(ref);
			em.flush();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(FiltroColaboradorAdminDto filtro) {
		try {
			QueryBuilder qb = new QueryBuilder(
					"Select c from Colaborador c left join fetch c.rol r "
							+ "left join fetch c.estatus e "
							+ "left join fetch c.categoriaPro cp "
							+ "left join fetch c.titulacion t "
							+ "left join fetch c.capacidad ca "
							+ "left join fetch c.proyecto p "
							+ "left join fetch c.promocion pr WHERE 1=1");

			qb.addConditionalJPQLClause(
					"and upper(e.nombreEstatus) not like :nombreEstatus",
					"nombreEstatus", "BAJA", !filtro.isMostrarBaja());
			qb.addConditionalJPQLClause("and upper(c.codigo) like :codigo",
					"codigo", "%" + filtro.getCodigo().toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro.getCodigo()));
			qb.addConditionalJPQLClause("and upper(c.nombre) like :nombre",
					"nombre", "%" + filtro.getNombre().toUpperCase() + "%",
					!Utils.isNullOrBlank(filtro.getNombre()));

			return qb.executeQuery(em);
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	public List<Colaborador> buscar(int first, int pageSize, String sortField,
			SortOrder sortOrder, FiltroColaboradorAdminDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Colaborador> query = cb
					.createQuery(Colaborador.class);
			Root<Colaborador> colaborador = query.from(Colaborador.class);

			// Join<Colaborador, RolesSeguridad> nombreRol = colaborador.join(
			// "rol", JoinType.LEFT);
			// Join<Colaborador, Estatus> estatus = colaborador.join("estatus",
			// JoinType.LEFT);
			// Join<Colaborador, CategoriaProfesional> categoriaPro =
			// colaborador
			// .join("categoriaPro", JoinType.LEFT);
			// Join<Colaborador, Titulacion> titulacion = colaborador.join(
			// "titulacion", JoinType.LEFT);
			// Join<Colaborador, Capacidad> capacidad = colaborador.join(
			// "capacidad", JoinType.LEFT);
			// Join<Colaborador, Proyecto> proyecto =
			// colaborador.join("proyecto",
			// JoinType.LEFT);
			// Join<Colaborador, Promocion> promocion = colaborador.join(
			// "promocion", JoinType.LEFT);

			Fetch<Colaborador, RolesSeguridad> rol = colaborador.fetch("rol",
					JoinType.LEFT);
			Fetch<Colaborador, Estatus> estatus = colaborador.fetch("estatus",
					JoinType.LEFT);
			Fetch<Colaborador, CategoriaProfesional> categoriaPro = colaborador
					.fetch("categoriaPro", JoinType.LEFT);
			Fetch<Colaborador, Titulacion> titulacion = colaborador.fetch(
					"titulacion", JoinType.LEFT);
			Fetch<Colaborador, Capacidad> capacidad = colaborador.fetch(
					"capacidad", JoinType.LEFT);
			Fetch<Colaborador, Proyecto> proyecto = colaborador.fetch(
					"proyecto", JoinType.LEFT);
			Fetch<Colaborador, Promocion> promocion = colaborador.fetch(
					"promocion", JoinType.LEFT);

			query.select(colaborador);

			// Añade Filtros de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getCodigo() != null && !filtro.getCodigo().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(colaborador.get("codigo").as(
								String.class)), "%"
								+ filtro.getCodigo().toUpperCase() + "%"));
			}
			if (filtro.getNombre() != null && !filtro.getNombre().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(colaborador.get("nombre").as(
								String.class)), "%"
								+ filtro.getNombre().toUpperCase() + "%"));
			}
			if (!filtro.isMostrarBaja()) {
				filtrosBusqueda.getExpressions().add(
						cb.and(cb.notLike(
								colaborador.get("estatus").get("nombreEstatus")
										.as(String.class), "BAJA")));
			}

			if (sortOrder.equals(SortOrder.ASCENDING)) {

				query.orderBy(cb.asc(colaborador.get(sortField)));

			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				query.orderBy(cb.desc(colaborador.get(sortField)));
			}

			if (filtrosBusqueda.getExpressions().size() > 0) {
				query.select(colaborador);
				query.where(filtrosBusqueda);
			}

			return em.createQuery(query).setFirstResult(first)
					.setMaxResults(pageSize).getResultList();

		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}

	}

	public int numeroColaboradores(FiltroColaboradorAdminDto filtro) {

		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Colaborador> query = cb
					.createQuery(Colaborador.class);
			Root<Colaborador> colaborador = query.from(Colaborador.class);

			// Fetch<Colaborador, RolesSeguridad> rol = colaborador.fetch("rol",
			// JoinType.LEFT);
			// Fetch<Colaborador, Estatus> estatus =
			// colaborador.fetch("estatus",
			// JoinType.LEFT);
			// Fetch<Colaborador, CategoriaProfesional> categoriaPro =
			// colaborador
			// .fetch("categoriaPro", JoinType.LEFT);
			// Fetch<Colaborador, Titulacion> titulacion = colaborador.fetch(
			// "titulacion", JoinType.LEFT);
			// Fetch<Colaborador, Capacidad> capacidad = colaborador.fetch(
			// "capacidad", JoinType.LEFT);
			// Fetch<Colaborador, Proyecto> proyecto = colaborador.fetch(
			// "proyecto", JoinType.LEFT);
			// Fetch<Colaborador, Promocion> promocion = colaborador.fetch(
			// "promocion", JoinType.LEFT);

			query.select(colaborador);

			// Añade Filtros de busqueda
			Predicate filtrosBusqueda = cb.conjunction();

			if (filtro.getCodigo() != null && !filtro.getCodigo().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(colaborador.get("codigo").as(
								String.class)), "%"
								+ filtro.getCodigo().toUpperCase() + "%"));
			}
			if (filtro.getNombre() != null && !filtro.getNombre().equals("")) {
				filtrosBusqueda.getExpressions().add(
						cb.like(cb.upper(colaborador.get("nombre").as(
								String.class)), "%"
								+ filtro.getNombre().toUpperCase() + "%"));
			}
			if (!filtro.isMostrarBaja()) {
				filtrosBusqueda.getExpressions().add(
						cb.and(cb.notLike(
								colaborador.get("estatus").get("nombreEstatus")
										.as(String.class), "BAJA")));
			}

			if (filtrosBusqueda.getExpressions().size() > 0) {
				query.select(colaborador);
				query.where(filtrosBusqueda);
			}

			return em.createQuery(query).getResultList().size();

		} catch (PersistenceException pe) {
			throw new ManejadorException(pe);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> validar(Colaborador colaboradorSelec) {
		try {
			return em
					.createQuery("FROM Colaborador c WHERE c.codigo = :codigo")
					.setParameter("codigo", colaboradorSelec.getCodigo())
					.getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> validarNif(Colaborador colaboradorSelec) {
		try {
			return em.createQuery("FROM Colaborador c WHERE c.nif = :nif")
					.setParameter("nif", colaboradorSelec.getNif())
					.getResultList();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}

	public Colaborador buscarPorDas(String usuario) {
		try {
			return (Colaborador) em
					.createQuery("FROM Colaborador c WHERE c.codigo = :codigo")
					.setParameter("codigo", usuario).getSingleResult();
		} catch (PersistenceException p) {
			throw new ManejadorException(p);
		}
	}
}
