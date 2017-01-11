package net.atos.practica.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Promocion;
import net.atos.practica.negocio.PromocionBO;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("view")
public class VisualizacionPromocionController {
	@Autowired 
	private PromocionBO promocionBO;
	
	private List<Promocion> listaPromociones;
	private List<Colaborador> listaColaboradores;
	private MindmapNode root;
	private MindmapNode selectedNode;
	
	
	@PostConstruct
	public void init(){
		listaPromociones = promocionBO.listarPromociones();
		listaColaboradores = new ArrayList<Colaborador>();
		dibujarGrafo();
		
	}
	
	public void dibujarGrafo(){
		root = new DefaultMindmapNode("Promociones", true);
		for(int i=0; i<listaPromociones.size(); i++){
			 MindmapNode proyectos = new DefaultMindmapNode(listaPromociones.get(i).getNombrePromocion(), "", "2E9AFE", true);
		     root.addNode(proyectos); 
		}

	}
	
	 public void onNodeSelect(SelectEvent event) {
	        MindmapNode node = (MindmapNode) event.getObject();

	        if(node.getChildren().isEmpty()) {
	        	Object label = node.getLabel();
	        	for(int i=0; i<listaPromociones.size(); i++){
	        		if(label.equals(listaPromociones.get(i).getNombrePromocion())){
	        			listaColaboradores = promocionBO.listarColaboradores(listaPromociones.get(i).getNombrePromocion());
	        			for(int j=0; j<listaColaboradores.size(); j++){
	        	        	node.addNode(new DefaultMindmapNode(listaColaboradores.get(j).getCodigo(),  "", "2E9AFE", true));	
	        			}
	        		}
	        	}
	        	for(int i=0; i<listaColaboradores.size(); i++){
	        		if(label.equals(listaColaboradores.get(i).getCodigo())){
	        			node.addNode(new DefaultMindmapNode(listaColaboradores.get(i).getNombre(), false));
	        			node.addNode(new DefaultMindmapNode(listaColaboradores.get(i).getEmail(), false));
	        			node.addNode(new DefaultMindmapNode(listaColaboradores.get(i).getNif(), false));
	        		}
	        	}
	        	
	        	
	        }
	 }
	 
	 public MindmapNode getRoot() {
	        return root;
	    }
	 
	    public MindmapNode getSelectedNode() {
	        return selectedNode;
	    }
	    public void setSelectedNode(MindmapNode selectedNode) {
	        this.selectedNode = selectedNode;
	    }
	    public void onNodeDblselect(SelectEvent event) {
	        this.selectedNode = (MindmapNode) event.getObject();        
	    }

		public List<Promocion> getListaPromociones() {
			return listaPromociones;
		}

		public void setListaPromociones(List<Promocion> listaPromociones) {
			this.listaPromociones = listaPromociones;
		}

		public List<Colaborador> getListaColaboradores() {
			return listaColaboradores;
		}

		public void setListaColaboradores(List<Colaborador> listaColaboradores) {
			this.listaColaboradores = listaColaboradores;
		}
}
