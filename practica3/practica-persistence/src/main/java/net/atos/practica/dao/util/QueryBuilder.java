package net.atos.practica.dao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QueryBuilder {

	private StringBuilder jpql = new StringBuilder("");
	private Map<String, Object> params = new HashMap<String, Object>();
	
	public QueryBuilder(String jpqlInicial) {
		super();
		jpql.append(jpqlInicial);
	}
		
	public void addConditionalJPQLClause(String jpqlClause, String paramName, Object paramValue, Boolean condition) {
		if (condition) {
			jpql.append(" " + jpqlClause);
			params.put(paramName, paramValue);
		}
	}

	public List executeQuery(EntityManager em) {
		Query query = em.createQuery(jpql.toString());
		for (String paramName : params.keySet()) {
			query.setParameter(paramName, params.get(paramName));
		}
		return query.getResultList();
	}
	
}
