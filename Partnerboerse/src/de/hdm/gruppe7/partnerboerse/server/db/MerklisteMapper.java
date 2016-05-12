package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;

public class MerklisteMapper {

	private static MerklisteMapper merklisteMapper = null;

	// Konstruktor
	protected MerklisteMapper() {

	}

	public static MerklisteMapper merklisteMapper() {
		if (merklisteMapper == null) {
			merklisteMapper = new MerklisteMapper();
		}

		return merklisteMapper;
	}

	/**
	 * Alle Vermerke eines Nutzerprofils auslesen.
	 */
	public Vector<Merkliste> findAllVermerkeFor(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste
		Vector<Merkliste> result = new Vector<Merkliste>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_vermerk.fremdprofil_id, t_nutzerprofil.vorname, "
							+ "t_nutzerprofil.nachname, t_nutzerprofil.geburtsdatum, t_profil.geschlecht FROM t_vermerk, t_nutzerprofil, t_profil  "
							+ "WHERE t_vermerk.nutzerprofil_id="
							+ profilId
							+ " AND t_nutzerprofil.nutzerprofil_id = t_vermerk.fremdprofil_id "
							+ "AND t_profil.profil_id = t_vermerk.fremdprofil_id");

			// Für jeden Eintrag im Suchergebnis wird nun ein Eintrag im Vektor
			// der Merkliste erstellt.
			while (rs.next()) {
				Merkliste m = new Merkliste();
				m.setmFremdprofilId(rs.getInt("fremdprofil_id"));
				m.setmVorname(rs.getString("vorname"));
				m.setmNachname(rs.getString("nachname"));
				m.setmGeburtsdatum(rs.getString("geburtsdatum"));
				m.setmGeschlecht(rs.getString("geschlecht"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(m);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Vermerkstatus ermitteln. 
	 */
	public int pruefeVermerk(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();
		
		// Ergebnisvariable (Ausgang: Es liegt kein Vermerk vor.)
		int vermerkstatus = 0; 
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM t_vermerk "
					+ "WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id=" + fremdprofilId);
			
			if (rs.next()) {
		        // Es liegt ein Vermerk vor.  
				vermerkstatus = 1; 
		      } else {
		    	  // Es liegt kein Vermerk vor. 
		    	  vermerkstatus = 0; 
		      }
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return vermerkstatus; 
	}
	
	/**
	 * Vermerk einfügen. 
	 */
	public void insertVermerk(int profilId, int fremdprofilId) { 
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_vermerk (nutzerprofil_id, fremdprofil_id) " + "VALUES (" 
			+ profilId + "," + fremdprofilId + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}	
	}
	
	/**
	 * Vermerk löschen.
	 */
	public void deleteVermerk(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_vermerk WHERE nutzerprofil_id=" + profilId
					+ " AND fremdprofil_id=" + fremdprofilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

}
