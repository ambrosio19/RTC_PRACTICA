package net.atos.practica.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

public class ManejadorException extends RuntimeException{

	private static final long serialVersionUID = -958446646808329854L;
	
	private static final int CASE2292 = 2292; 
    private static final int CASE2258 = 2258; 
	
	public ManejadorException(PersistenceException p) {
		Throwable t = p.getCause();
		while (t != null){
			t = t.getCause();
			if(t instanceof SQLIntegrityConstraintViolationException){ 
                switch(((SQLIntegrityConstraintViolationException) t).getErrorCode()){ 
                	case 1:  throw new LlamaloXException("Está duplicado/a.", t); 
                    case CASE2292: throw new LlamaloXException("No se puede eliminar, tiene datos relacionados.", t); 

                    case CASE2258: throw new LlamaloXException("Hay valores que no pueden estar vacíos", t);

                    default: throw new LlamaloXException(t.getMessage(), t); 
                } 
			} 
			else if(t instanceof ConstraintViolationException){
				switch(((ConstraintViolationException) t).getErrorCode()){ 
                	case 1:  throw new LlamaloXException("Está duplicado/a.", t); 
                	case CASE2292: throw new LlamaloXException("No se puede eliminar, tiene datos relacionados.", t); 

                	case CASE2258: throw new LlamaloXException("Hay valores que no pueden estar vacíos", t);

                	default: throw new LlamaloXException(t.getMessage(), t); 
				} 
			} 
			else if(t instanceof NoResultException){ 
                throw new LlamaloXException("No se han encontrado resultados.", t); 
			} 
			else if(t instanceof JDBCConnectionException){ 
                throw new LlamaloXException("No hay conexión con la Base de Datos.", t); 
			} 
			else{ 
                throw new LlamaloXException(t.getMessage(), t); 
			}
		}
	}
}
