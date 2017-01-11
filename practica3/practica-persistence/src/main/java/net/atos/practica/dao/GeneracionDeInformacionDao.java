package net.atos.practica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.dao.util.QueryBuilder;
import net.atos.practica.dao.util.Utils;
import net.atos.practica.dto.FiltroGenerarInformacion;
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
public class GeneracionDeInformacionDao implements InterfaceDao<FiltroGenerarInformacion, FiltroGenerarInformacion>{
	
	@Autowired
	@Qualifier("entityManager")
	public EntityManager em;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(FiltroGenerarInformacion filtroBusqueda) {
		try{
			QueryBuilder qb = new QueryBuilder("Select c From Colaborador c Left Join Fetch c.proyecto proyect Left Join Fetch c.capacidad cap Left Join Fetch c.rol rol Left Join Fetch c.estatus e Left Join Fetch c.categoriaPro cat Left Join Fetch c.titulacion tit Left Join Fetch c.promocion pr Where 1=1");
				qb.addConditionalJPQLClause("and proyect.wbs like :wbs ", "wbs", filtroBusqueda.getWbs(), !Utils.isNullOrBlank(filtroBusqueda.getWbs()));
				qb.addConditionalJPQLClause("and upper(pr.nombrePromocion) like :nombrePromocion", "nombrePromocion", filtroBusqueda.getNombrePromocion().toUpperCase(), !Utils.isNullOrBlank(filtroBusqueda.getNombrePromocion()));
				qb.addConditionalJPQLClause("and upper(cap.nombreCapacidad) like :nombreCapacidad", "nombreCapacidad", filtroBusqueda.getNombreCapacidad().toUpperCase(), !Utils.isNullOrBlank(filtroBusqueda.getNombreCapacidad()));
				qb.addConditionalJPQLClause("and c.nivelGCM = :nivelGCM", "nivelGCM", filtroBusqueda.getGcm(), filtroBusqueda.getGcm() != -1);
			return qb.executeQuery(em);
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}

	@Override
	public void crear(FiltroGenerarInformacion objeto) {}

	@Override
	public FiltroGenerarInformacion actualizar(FiltroGenerarInformacion objeto) {
		return null;
	}

	@Override
	public void borrar(FiltroGenerarInformacion objeto) {}
}
