package net.atos.practica.controllers;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import net.atos.practica.entity.Colaborador;
import net.atos.practica.entity.Proyecto;
import net.atos.practica.negocio.ProyectoBO;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class VisualizacionProyectosController{

	@Autowired
	private ProyectoBO proyectoBO;
	
	private List<Proyecto> listaProyectos;
	private List<Colaborador> listaColaboradores;
	private MindmapNode root;
	private MindmapNode selectedNode;
	    
	@PostConstruct
	public void init(){
		listaProyectos = new ArrayList<Proyecto>();
		listaProyectos = proyectoBO.listarProyectos();
		listaColaboradores = new ArrayList<Colaborador>();
		dibujarGrafo();
	}

	public void dibujarGrafo(){
		root = new DefaultMindmapNode("Proyectos", true);
		for(int i=0; i<listaProyectos.size(); i++){
			 MindmapNode proyectos = new DefaultMindmapNode(listaProyectos.get(i).getNombreProyecto(),"","2E9AFE", true);
		     root.addNode(proyectos); 
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
 
    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        if(node.getChildren().isEmpty()) {
        	  Object label = node.getLabel();
        	for(int i=0; i<listaProyectos.size(); i++){
        		if(label.equals(listaProyectos.get(i).getNombreProyecto())){
        			listaColaboradores = proyectoBO.listarColaboradorProyectos(listaProyectos.get(i).getWbs());
        			for(int j=0; j<listaColaboradores.size(); j++){
        	        	node.addNode(new DefaultMindmapNode(listaColaboradores.get(j).getCodigo(), "", "2E9AFE", true));	
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
     
    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();        
    }

	public List<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<Proyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public List<Colaborador> getListaColaboradores() {
		return listaColaboradores;
	}

	public void setListaColaboradores(List<Colaborador> listaColaboradores) {
		this.listaColaboradores = listaColaboradores;
	}

}
