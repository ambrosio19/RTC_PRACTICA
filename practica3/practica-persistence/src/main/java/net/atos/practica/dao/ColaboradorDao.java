package net.atos.practica.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import net.atos.practica.dto.FiltroCapacidadDto;
import net.atos.practica.dto.FiltroColaboradorAdminDto;
import net.atos.practica.dto.FiltroColaboradorCapacidad;
import net.atos.practica.entity.Capacidad;
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
public class ColaboradorDao implements InterfaceDao<Colaborador, String>{
	@Autowired
	@Qualifier("entityManager")
	private EntityManager em;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Colaborador actualizar(Colaborador c) {
		try{
			Colaborador merge = em.merge(c);
			em.flush();
			return merge;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@Override
	public List<Colaborador> buscar(String id) {
		try{
			return em.createQuery("FROM Colaborador c WHERE c.codigo = :cod").setParameter("cod", id).getResultList();	
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FiltroColaboradorCapacidad> colaboradoresCapacidad(){
		try{
			//return em.createQuery("SELECT cap.nombreCapacidad, count(cap.nombreCapacidad) FROM Colaborador c INNER JOIN c.capacidad cap GROUP BY cap.nombreCapacidad").getResultList();
			List<String> nombreCap = em.createQuery("SELECT cap.nombreCapacidad FROM Capacidad cap WHERE 1=1").getResultList();
			List<Long> numColaboradores = em.createQuery("SELECT count(cap.nombreCapacidad) FROM Colaborador c INNER JOIN c.capacidad cap GROUP BY cap.nombreCapacidad").getResultList();
			List<FiltroColaboradorCapacidad> listaCapacidadesUsuarios = new ArrayList<FiltroColaboradorCapacidad>();
			for(int i=0; i<nombreCap.size(); i++){
				FiltroColaboradorCapacidad fcc = new FiltroColaboradorCapacidad();
				fcc.setNombreCapacidad(nombreCap.get(i));
				if (i<numColaboradores.size()){
					fcc.setNumColaboradores(numColaboradores.get(i));
				} else {
					fcc.setNumColaboradores(0L);
				}
				listaCapacidadesUsuarios.add(fcc);
			}
			return listaCapacidadesUsuarios;
		}catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> buscar(FiltroColaboradorAdminDto filtro) {
		try{
			return em.createQuery("FROM Colaborador c left join fetch c.rol r WHERE r.nombreRol= :nombreRol" ).setParameter("nombreRol", filtro.getRol().getNombreRol()).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	public Colaborador buscarColaboradorInicial(String id, String passwd) {
		try{
			return (em.find(Colaborador.class, id));	
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> verificarPassActual(String passActual, String das) {
		try{
			return em.createQuery("FROM Colaborador c WHERE c.codigo = :das and c.pwd = :pwd").setParameter("das", das).setParameter("pwd", passActual).getResultList();
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}
	
	public boolean verificarPass(String passActual, String newPass, String das) {
		try{
			Colaborador c = (Colaborador) em.createQuery("FROM Colaborador c WHERE c.pwd = :pass and c.codigo = :das").setParameter("pass", passActual).setParameter("das", das).getSingleResult();
			if(c != null){
				c.setPwd(newPass);
				em.merge(c);
				return true;
			}
			return false;
		} catch(PersistenceException p){
			throw new ManejadorException(p);
		}
	}


	@Override
	public void crear(Colaborador objeto) {}

	@Override
	public void borrar(Colaborador objeto) {}
}
