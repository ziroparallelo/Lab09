package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;


public class Model {
	
	private Graph<Country, DefaultEdge> grafo;
	private BordersDAO dao;
	private Map<Integer, Country> idMap;
	
	public Model() {
		dao = new BordersDAO();
	}
	
	public List<Country> loadAllCountries(){
		List<Country> stati = dao.loadAllCountries();
		return stati;
	}
	public void creaGrafo(int anno) {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		idMap = new HashMap<Integer, Country>();
		
		//Aggiungo i vertici
		List<Country> stati = dao.loadAllCountriesByYear(anno);
		for(Country c: stati) {
			idMap.put(c.getCCode(), c);
			System.out.println(c);
		}
		
		Graphs.addAllVertices( grafo, stati);
		
		List<CoppiaId> coppiaId = dao.getIdVerticiConnessi(anno);
		
		for(CoppiaId  c: coppiaId) {
			Country source = idMap.get(c.getCcode1());
			Country target = idMap.get(c.getCcode2());
			grafo.addEdge(source, target);
//			System.out.println(grafo.getEdge(source, target));
		}
		
		System.out.println("Ci sono "+grafo.vertexSet().size()+" vertici");
		System.out.println("Ci sono "+grafo.edgeSet().size()+" archi");
		
		
	}
	
	public Map<Country, Integer> getStatiGrado(int anno) {
		
		Map<Country, Integer> statiGrado= new HashMap<Country, Integer>();
		
		Set<Country> stati = this.grafo.vertexSet();
		for(Country c : stati) {
			statiGrado.put(c, grafo.degreeOf(c));
		}
		
		return statiGrado;
	}


	public int getConnectivty() {
		
		int connectivity;
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		connectivity = ci.connectedSets().size();
		
		return connectivity;
	}
	
	public Set<Country> esploraGrafo(Country partenza) {
		
		GraphIterator<Country, DefaultEdge> visita = 
				new BreadthFirstIterator<Country, DefaultEdge> (this.grafo, partenza);
		
		Set<Country> vRagg = new HashSet<Country>();
		
	
		//Ci sto passando la mappa dove voglio che passi le informazioni che voglio
		visita.addTraversalListener(new RegistraAlberoDiVisita(vRagg, this.grafo));
		
		while(visita.hasNext()) {
			Country c = visita.next();
			System.out.println(c);
		}


		return vRagg;
	}

}
