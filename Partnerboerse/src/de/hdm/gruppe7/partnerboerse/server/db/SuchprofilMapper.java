package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilMapper {
	private static SuchprofilMapper suchprofilMapper = null;
	
	protected SuchprofilMapper() {
	}
	
	public static SuchprofilMapper suchprofilMapper() {
		if (suchprofilMapper == null) {
			suchprofilMapper = new SuchprofilMapper();
		}

		return suchprofilMapper;
	}
	
	/**
	 * Suchprofil-Objekt in die Datenbank einfügen.
	 */
	public Suchprofil insertSuchprofil(Suchprofil s) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			// Größte profil_id aus der Tabelle t_profil ermitteln.
			ResultSet rs = stmt.executeQuery("SELECT MAX(profil_id) AS maxprofil_id " + "FROM t_profil1");

			// Wenn wir etwas zurueckerhalten...
			if (rs.next()) {	
				
				// Suchprofil-Objekt mit bisher maximalem, nun um 1 inkrementierten Primärschlüssel versehen. 
				s.setProfilId(rs.getInt("maxprofil_id") + 1);
				
				// Tabelle t_profil befüllen:
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil1 (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
								+ "VALUES(" + s.getProfilId() + ",'" + s.getGeschlecht() + "','"
								+ s.getHaarfarbe() + "'," + s.getKoerpergroesseInt() + ",'"
								+ s.getRaucher() + "','" + s.getReligion() + "')");
				
				// Tablle t_suchprofil befüllen: 
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_suchprofil1 (suchprofil_id, nutzerprofil_id, suchprofilname, alter_von, alter_bis) "
						+ "VALUES(" + s.getProfilId() + "," + Benutzer.getProfilId() + ",'" + s.getSuchprofilName() + "'," 
						+ s.getAlterMinInt() + "," + s.getAlterMaxInt() + ")");	
			}
			
		}
		 catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Suchprofil-Objekt zurückgeben.
		 */
		return s;	
		}
	
	/**
	 * Suchprofil-Objekt wiederholt in die Datenbank schreiben. 
	 */
	public void updateSuchprofil(Suchprofil s) { 
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement(); 
			stmt.executeUpdate(
					"UPDATE t_suchprofil1 " 
							+ "SET suchprofilname=\"" + s.getSuchprofilName() + "\", "
							+ "alter_von=\"" + s.getAlterMinInt() + "\", " 
							+ "alter_bis=\"" + s.getAlterMaxInt() + "\" "
							+ "WHERE suchprofil_id=" + s.getProfilId()); 
			
			stmt = con.createStatement();
			stmt.executeUpdate(
					"UPDATE t_profil1 " 
							+ "SET geschlecht=\"" + s.getGeschlecht() + "\", " 
							+ "koerpergroesse=\"" + s.getKoerpergroesseInt() + "\", " 
							+ "haarfarbe=\"" + s.getHaarfarbe() + "\", " 
							+ "raucher=\"" + s.getRaucher() + "\", "
							+ "religion=\"" + s.getReligion() + "\" "
							+ "WHERE profil_id=" + s.getProfilId()); 
			
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Suchprofil-Objekt aus der Datenbank löschen.
	 */
	public void deleteSuchprofil(int profilId, String suchprofilName) {
		Connection con = DBConnection.connection();
		
		int suchprofilIdInt = 0;

		try {
							
			Statement stmt = con.createStatement();
			
			// Zu löschende suchprofil_id aus der Tabelle t_suchprofil holen.
			ResultSet rs = stmt.executeQuery("SELECT suchprofil_id AS sp_id FROM t_suchprofil1 "
					+ "WHERE t_suchprofil1.nutzerprofil_id=" + profilId
					+ " AND t_suchprofil1.suchprofilname LIKE '" + suchprofilName + "'");
			
			
			// Wenn wir etwas zurückerhalten...
			if(rs.next()) {
				suchprofilIdInt = rs.getInt("sp_id");
			
			// Daten aus der Tabelle t_suchprofil mit der entsprechenden profil_id löschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_suchprofil1 "
					+ "WHERE t_suchprofil1.suchprofil_id=" + suchprofilIdInt);
			
			// Daten aus der Tabelle t_profil mit der entsprechenden suchprofil_id löschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_profil1 WHERE t_profil1.profil_id=" + suchprofilIdInt);
								
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	


	/**
	 * Suchprofil mit vorgegebener Profil-ID suchen.
	 */
	public Suchprofil findSuchprofilByName(int profilId, String suchprofilName) { 
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil1 INNER JOIN t_profil1 "
					+ "ON t_suchprofil1.suchprofil_id = t_profil1.profil_id "
					+ "WHERE t_suchprofil1.nutzerprofil_id=" + profilId
					+ " AND t_suchprofil1.suchprofilname LIKE '" + suchprofilName + "'");
					
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Suchprofil s = new Suchprofil();
				
				s.setProfilId(rs.getInt("suchprofil_id"));
				s.setSuchprofilName(rs.getString("suchprofilname"));   
				s.setGeschlecht(rs.getString("geschlecht"));
				s.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				s.setHaarfarbe(rs.getString("haarfarbe"));
				s.setAlterMinInt(rs.getInt("alter_von"));
				s.setAlterMaxInt(rs.getInt("alter_bis"));	
				s.setRaucher(rs.getString("raucher"));
				s.setReligion(rs.getString("religion"));
				
				return s;
					
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	


	/**
	 * Alle Suchprofile auslesen.
	 */
	public List<Suchprofil> findAllSuchprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Suchprofil> result = new ArrayList<Suchprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_profil1 INNER JOIN"
					+ "t_suchprofil1 ON t_profil1.profil_id = " + "t_nutzerprofil1.profil_id ORDER BY profil_id");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Suchprofil-Objekt erstellt.
			while (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setProfilId(rs.getInt("profil_id"));
				s.setAlterMin(rs.getString("Alter minimal"));
				s.setAlterMax(rs.getString("Alter maximal"));
				s.setGeschlecht(rs.getString("geschlecht"));
				s.setHaarfarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesse(rs.getString("koerpergroesse"));
				s.setRaucher(rs.getString("raucher"));
				s.setReligion(rs.getString("religion"));

				// Hinzufuegen des neuen Objekts zur Ergebnisliste
				result.add(s);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurueckgeben
		return result;

	}
	

	
	// Aehnlichkeit berechnen und in Partnervorschlaege ausgeben
	
	/**
	 * Aehnlichkeit hinzufuegen. 
	 */
	public void insertAehnlichkeit(int nutzerprofilId, int suchprofilId, String suchprofilName, int fremdprofilId, int aehnlichkeitSp) { 
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_aehnlichkeitsp1 (nutzerprofil_id, suchprofil_id, suchprofilname, fremdprofil_id, aehnlichkeit) " + "VALUES (" 
			+ nutzerprofilId + "," + suchprofilId + ",'" + suchprofilName +  "'," +  fremdprofilId + "," + aehnlichkeitSp + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}	
	}
	
	/**
	 * Aehnlichkeit loeschen.
	 */
	public void deleteAehnlichkeitSp (int nutzerprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_aehnlichkeitsp1 WHERE nutzerprofil_id=" + nutzerprofilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}
	


	

	/**
	 * Existenz des Suchprofilnamens beim Anlegen überprüfen.
	 */
	public int pruefeSuchprofilname(int profilId, String suchprofilName) {
		Connection con = DBConnection.connection();
		
		// Ergebnisvariable (Ausgang: Der Suchprofilname liegt nicht vor.)
		int existenz = 0; 
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM t_suchprofil1 "
					+ "WHERE nutzerprofil_id=" + profilId + " AND suchprofilname LIKE '" + suchprofilName + "'");
			
			if (rs.next()) {
		        if(rs.getInt("COUNT(*)") == 1)
		        	// Der Suchprofilname existiert bereits.
		        	existenz = 1; 
		      } else {
		    	  // Der Suchprofilname existiert bisher nicht.
		    	  existenz = 0; 
		      }
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return existenz; 
	}
	
	/**
	 * Existenz des Suchprofilnames beim Editieren überprüfen.
	 */
	public String pruefeSuchprofilnameEdit(int profilId, int suchprofilId) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT suchprofilname FROM t_suchprofil1 "
					+ "WHERE nutzerprofil_id=" + profilId + " AND suchprofil_id=" + suchprofilId);
			
			if (rs.next()) {
				String suchprofilname = rs.getString("suchprofilname"); 
				return suchprofilname; 
		      }
			
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null; 
		}
		return null;
	}
	
	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 */
	public List<Suchprofil> findAllSuchprofileFor(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Suchprofil> result = new ArrayList<Suchprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil1 INNER JOIN "
					+ "t_profil1 ON t_suchprofil1.suchprofil_id = t_profil1.profil_id "
					+ "WHERE t_suchprofil1.nutzerprofil_id=" + profilId); 

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Suchprofil-Objekt erstellt.
			while (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setProfilId(rs.getInt("suchprofil_id"));
				s.setSuchprofilName(rs.getString("suchprofilname"));
				s.setAlterMinInt(rs.getInt("alter_von"));
				s.setAlterMaxInt(rs.getInt("alter_bis"));
				s.setGeschlecht(rs.getString("geschlecht"));
				s.setHaarfarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				s.setRaucher(rs.getString("raucher"));
				s.setReligion(rs.getString("religion"));

				// Hinzufuegen des neuen Objekts zur Ergebnisliste
				result.add(s);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurueckgeben
		return result;
	}
	
	 /**
	  * Alle Suchprofile eines Nutzers auslesen.
	  * Diese Methode ruft die gleichnamige Methode mit dem Übergabeparameter Profil-ID auf.
	  * @param n
	  * @return
	  */
	public List<Suchprofil> findAllSuchprofileFor(Nutzerprofil n){
		 return findAllSuchprofileFor(n.getProfilId()); 
	 }

	
	
	
	public Suchprofil findSuchprofilById(int suchprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil1, t_profil " + "WHERE profil_id= " + suchprofilId
					+ " AND suchprofil_id=" + suchprofilId);

			/*
			 * Es kann max. ein Ergebnis-Tupel zurückgegeben werden. Prüfen, ob
			 * ein Ergebnis-Tupel vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Nutzerprofil-Objekt umwandeln.
				Suchprofil s = new Suchprofil();
				s.setProfilId(rs.getInt("suchprofil_id"));
				s.setSuchprofilName(rs.getString("suchprofilname"));
				s.setAlterMinInt(rs.getInt("alter_von"));
				s.setAlterMaxInt(rs.getInt("alter_bis"));
				s.setGeschlecht(rs.getString("geschlecht"));
				s.setHaarfarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				s.setRaucher(rs.getString("raucher"));
				s.setReligion(rs.getString("religion"));
				return s;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	
	

}