package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class MerklisteMapper {
	
	private static MerklisteMapper merklisteMapper = null;

	protected MerklisteMapper() {
	}

	public static MerklisteMapper merklisteMapper() {
		if (merklisteMapper == null) {
			merklisteMapper = new MerklisteMapper();
		}
		return merklisteMapper;
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Merkliste
	 * ***************************************************************************
	 */
		
	 /**
	  * Alle Vermerke eines Nutzerprofils auslesen.
	  */
	 public Vector<Nutzerprofil> findGemerkteNutzerprofileFor(int profilId) {
			Connection con = DBConnection.connection();
			
			Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();
			
			try {
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname, "
						+ "t_nutzerprofil.geburtsdatum, t_profil.geschlecht "
						+ "FROM t_nutzerprofil, t_profil, t_vermerk "
						+ "WHERE t_vermerk.nutzerprofil_id=" + profilId 
						+ " AND t_nutzerprofil.nutzerprofil_id = t_vermerk.fremdprofil_id "
						+ "AND t_profil.profil_id = t_vermerk.fremdprofil_id"); 

				while (rs.next()) {
					Nutzerprofil n = new Nutzerprofil();
					n.setProfilId(rs.getInt("nutzerprofil_id")); 
					n.setVorname(rs.getString("vorname"));
					n.setNachname(rs.getString("nachname"));
					n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
					n.setGeschlecht(rs.getString("geschlecht"));
					
					// Hinzufügen des neuen Objekts zum Ergebnisvektor
					result.addElement(n);
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
		
		/*
		 * ***************************************************************************
		 * ABSCHNITT, Ende: Merkliste
		 * ***************************************************************************
		 */

}
