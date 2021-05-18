package it.polito.tdp.artsmia.model;

import java.util.HashMap;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject,DefaultWeightedEdge>grafo;
	private ArtsmiaDAO dao;
	
	private Map<Integer,ArtObject> idMap;
	
	public Model() {
		dao= new ArtsmiaDAO();
		idMap= new HashMap<Integer,ArtObject>();
	}
	
	public void creaGrafo() {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiunta dei vertici
		//1. recupero tutti gli artobject dal DB
		//2. li inserisco come vertici
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//Aggiunta Archi
		//APPROCCIO 1
		//--> doppio ciclo for sui vertici dati due vertici controllo se sono collegati
		
		/*for(ArtObject a1 : this.grafo.vertexSet()) {
			for(ArtObject a2 : this.grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
					//devo collegare a1 ad a2?
					int peso=dao.getPeso(a1,a2);
					if(peso>0) {
						Graphs.addEdge(this.grafo, a1, a2, peso);
					}
				}
			}
		}*/ // non giunge a termine ci sono troppi vertici
		
		//APPROCCIO 3
		
		for(Adiacenza a : dao.getAdiacenze()) {
			if(a.getPeso()>0) {
				Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
				
			}
		}
		
		System.out.println("GRAFO CREATO");
		System.out.println("# VERTICI: " +grafo.vertexSet().size());
		System.out.println("# ARCHI:" +grafo.edgeSet().size());
		
	}

}
