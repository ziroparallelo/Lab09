package it.polito.tdp.borders.model;

import java.util.Map;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();


		model.creaGrafo(2006);
		
		Map<Country, Integer> statiGrado = model.getStatiGrado(2006);
		Set<Country> stati = statiGrado.keySet();
		
		Country stato = null;
		
		for(Country c: stati) {
			stato = c;
			break;
		}
		
		model.esploraGrafo(stato);
//		
//		System.out.println("TestModel -- TODO");
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
