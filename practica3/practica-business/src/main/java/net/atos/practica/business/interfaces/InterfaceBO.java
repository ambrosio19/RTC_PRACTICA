package net.atos.practica.business.interfaces;

import java.util.List;

import net.atos.practica.exception.LlamaloXException;

import org.apache.poi.ss.formula.functions.T;

public interface InterfaceBO<T, E> {
	public void crear(T objeto);
	public List<?> buscar(E objeto);
	public void actualizar(T objeto);
	public void borrar(T objeto);
}
