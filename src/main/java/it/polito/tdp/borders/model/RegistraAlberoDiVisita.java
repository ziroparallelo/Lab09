package it.polito.tdp.borders.model;

import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;


public class RegistraAlberoDiVisita implements TraversalListener<Country, DefaultEdge> {

	private Set<Country> vRagg;
	private Graph<Country, DefaultEdge> grafo;
	private boolean connected = true;
	
	
	
	public RegistraAlberoDiVisita(Set<Country> vRagg, Graph<Country, DefaultEdge> grafo) {
		this.vRagg = vRagg;
		this.grafo = grafo;
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		connected = false;
		System.out.println("\n\nInizio altro grafo\n\n");
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
		

	}
	
	@Override
	public void vertexTraversed(VertexTraversalEvent<Country> e) {
		// TODO Auto-generated method stub
		if(connected)
			vRagg.add(e.getVertex());
			
		
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Country> e) {
		// TODO Auto-generated method stub

	}

}
