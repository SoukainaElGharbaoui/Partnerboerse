package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
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
			ResultSet rs = stmt.executeQuery("SELECT MAX(profil_id) AS maxprofil_id " + "FROM t_profil");

			// Wenn wir etwas zurueckerhalten...
			if (rs.next()) {	
				
				// Suchprofil-Objekt mit bisher maximalem, nun um 1 inkrementierten Primärschlüssel versehen. 
				s.setProfilId(rs.getInt("maxprofil_id") + 1);
				
				// Tabelle t_profil befüllen:
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
								+ "VALUES(" + s.getProfilId() + ",'" + s.getGeschlecht() + "','"
								+ s.getHaarfarbe() + "'," + s.getKoerpergroesseInt() + ",'"
								+ s.getRaucher() + "','" + s.getReligion() + "')");
				
				// Tablle t_suchprofil befüllen: 
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_suchprofil (suchprofil_id, nutzerprofil_id, suchprofilname, alter_von, alter_bis) "
						+ "VALUES(" + s.getProfilId() + "," + s.getProfilId() + ",'" + s.getSuchprofilName() + "'," 
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
					"UPDATE t_suchprofil " 
							+ "SET suchprofilname=\"" + s.getSuchprofilName() + "\", "
							+ "alter_von=\"" + s.getAlterMinInt() + "\", " 
							+ "alter_bis=\"" + s.getAlterMaxInt() + "\" "
							+ "WHERE suchprofil_id=" + s.getProfilId()); 
			
			stmt = con.createStatement();
			stmt.executeUpdate(
					"UPDATE t_profil " 
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
			ResultSet rs = stmt.executeQuery("SELECT suchprofil_id AS sp_id FROM t_suchprofil "
					+ "WHERE t_suchprofil.nutzerprofil_id=" + profilId
					+ " AND t_suchprofil.suchprofilname LIKE '" + suchprofilName + "'");
			
			
			// Wenn wir etwas zurückerhalten...
			if(rs.next()) {
				suchprofilIdInt = rs.getInt("sp_id");
			
			// Daten aus der Tabelle t_suchprofil mit der entsprechenden profil_id löschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_suchprofil "
					+ "WHERE t_suchprofil.suchprofil_id=" + suchprofilIdInt);
			
			// Daten aus der Tabelle t_profil mit der entsprechenden suchprofil_id löschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_profil WHERE t_profil.profil_id=" + suchprofilIdInt);
								
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Suchprofil mit vorgegebener Profil-ID suchen.
	 */
	public Suchprofil findBySuchprofilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
			
			"SELECT t_suchprofil.alter_von, t_suchprofil.alter_bis, t_suchprofil.suchprofil_id, "
			+ "t_suchprofil.nutzerprofil_id, t_profil.koerpergroesse, t_profil.geschlecht, " 
			+ "t_profil.haarfarbe, t_profil.religion, t_profil.raucher " 
			+ "FROM t_suchprofil INNER JOIN t_profil ON t_suchprofil.suchprofil_id = t_profil.profil_id");
			
					
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Suchprofil s = new Suchprofil();
				
				s.setProfilId(rs.getInt("suchprofil_id"));
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
	 * ************************
	 * Brauchen wir das???
	 * ************************
	 */
//	/**
//	 * Suchen eines Nutzerprofils mit vorgegebener ProfilId. 
//	 */
//	public Suchprofil findBySuchprofilId(int profilId) {
//		// DB-Verbindung holen
//		Connection con = DBConnection.connection();
//
//		try {
//			// Leeres SQL-Statement (JDBC) anlegen
//			Statement stmt = con.createStatement();
//
//			// Statement ausfüllen und als Query an die DB schicken
//			ResultSet rs = stmt.executeQuery(
//					"SELECT profil_id, alterMin, alterMax FROM t_suchprofil " + "WHERE profil_id=" + profilId);
//
//			/*
//			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
//			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
//			 */
//			if (rs.next()) {
//				// Ergebnis-Tupel in Objekt umwandeln
//				Suchprofil suchprofil = new Suchprofil();
//				suchprofil.setProfilId(rs.getInt("profil_id"));
//				suchprofil.setAlterMin(rs.getInt("Alter minimal"));
//				suchprofil.setAlterMax(rs.getInt("Alter maximal"));
//				return suchprofil;
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//			return null;
//		}
//
//		return null;
//	}

	/**
	 * Alle Suchprofile auslesen.
	 */
	public List<Suchprofil> findAllSuchprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Suchprofil> result = new ArrayList<Suchprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_profil INNER JOIN"
					+ "t_suchprofil ON t_profil.profil_id = " + "t_nutzerprofil.profil_id ORDER BY profil_id");

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
	
	/**
	 * Alle Suchprofile eines Nutzerprofils auslesen.
	 */
	public List<Suchprofil> findAllSuchprofileFor(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Suchprofil> result = new ArrayList<Suchprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil INNER JOIN "
					+ "t_profil ON t_suchprofil.suchprofil_id = t_profil.profil_id "
					+ "WHERE t_suchprofil.nutzerprofil_id=" + profilId); 

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Suchprofil-Objekt erstellt.
			while (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setProfilId(rs.getInt("profil_id"));
				s.setSuchprofilName(rs.getString("suchprofilname"));
				s.setAlterMin(rs.getString("alter_von"));
				s.setAlterMax(rs.getString("alter_bis"));
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil INNER JOIN t_profil "
					+ "ON t_suchprofil.suchprofil_id = t_profil.profil_id "
					+ "WHERE t_suchprofil.nutzerprofil_id=" + profilId
					+ " AND t_suchprofil.suchprofilname LIKE '" + suchprofilName + "'");
					
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
	
	

}