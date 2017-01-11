package net.atos.practica.interfaces;

import java.util.List;

import net.atos.practica.exception.LlamaloXException;

public interface InterfaceDao<T,E>{
	public void crear(T objeto);
	public List<?> buscar(E objeto);
	public T actualizar(T objeto);
	public void borrar(T objeto);
}
