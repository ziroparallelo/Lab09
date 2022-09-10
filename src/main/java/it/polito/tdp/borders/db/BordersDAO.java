package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.CoppiaId;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Country> loadAllCountriesByYear(int anno) {

		String sql = "select StateAbb, ccode, StateNme "
				+ "from country "
				+ "where ccode IN ( "
				+ "Select state1no as stateId "
				+ "from contiguity "
				+ "where conttype=1 and year <= ? "
				+ "union "
				+ "select state2no as stateId "
				+ "from contiguity "
				+ "where conttype=1 and year <= ? "
				+ "group by stateId)";
		
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme")));
			}
			
			System.out.println("Ci sono "+result.size()+" nazioni");
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public List<CoppiaId> getIdVerticiConnessi(int anno) {
		
		String sql = "select state1no, state2no "
				+ "from contiguity "
				+ "where conttype=1 and year <= ?";
		
		List<CoppiaId> result = new ArrayList<CoppiaId>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new CoppiaId(rs.getInt("state1no"), rs.getInt("state2no")));
			}
			
//			System.out.println("Ci sono "+result.size()+" nazioni");
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
