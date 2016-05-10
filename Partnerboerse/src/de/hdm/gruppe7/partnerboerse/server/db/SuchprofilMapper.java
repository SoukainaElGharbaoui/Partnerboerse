package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilMapper {
	private static SuchprofilMapper suchprofilMapper = null;
	
	/**
	 * Konstruktor 
	 */
	protected SuchprofilMapper() {
	}
	
	public static SuchprofilMapper suchprofilMapper() {
		if (suchprofilMapper == null) {
			suchprofilMapper = new SuchprofilMapper();
		}

		return suchprofilMapper;
	}

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
//			// Statement ausfÃ¼llen und als Query an die DB schicken
//			ResultSet rs = stmt.executeQuery(
//					"SELECT profil_id, alterMin, alterMax FROM t_suchprofil " + "WHERE profil_id=" + profilId);
//
//			/*
//			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
//			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
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
	 * Ausgeben aller Suchprofile.
	 */
	public List<Suchprofil> findAllSuchprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Suchprofil> result = new ArrayList<Suchprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_profil INNER JOIN"
					+ "t_suchprofil ON t_profil.profil_id = " + "t_nutzerprofil.profil_id ORDER BY profil_id");

			// F�r jeden Eintrag im Suchergebnis wird nun ein
			// Suchprofil-Objekt erstellt.
			while (rs.next()) {
				Suchprofil suchprofil = new Suchprofil();
				suchprofil.setProfilId(rs.getInt("profil_id"));
				suchprofil.setAlterMin(rs.getString("Alter minimal"));
				suchprofil.setAlterMax(rs.getString("Alter maximal"));
				suchprofil.setGeschlecht(rs.getString("geschlecht"));
				suchprofil.setHaarfarbe(rs.getString("haarfarbe"));
				suchprofil.setKoerpergroesse(rs.getString("koerpergroesse"));
				suchprofil.setRaucher(rs.getString("raucher"));
				suchprofil.setReligion(rs.getString("religion"));

				// Hinzuf�gen des neuen Objekts zur Ergebnisliste
				result.add(suchprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zur�ckgeben
		return result;
	}
	
	
	/**
	 * Einf�gen eines <code>Suchprofil</code>-Objekts in die Datenbank.
	 */
	public Suchprofil insertSuchprofil(Suchprofil suchprofil) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			// Gr��te profil_id ermitteln. 
			ResultSet rs = stmt.executeQuery("SELECT MAX(profil_id) AS maxprofil_id " + "FROM t_profil");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein.
			if (rs.next()) {	
				
				// suchprofil erhaelt den bisher maximalen, nun um 1 inkrementierten Primärschlüssel. 
				suchprofil.setProfilId(rs.getInt("maxprofil_id") + 1);
				
				// Tabelle t_profil bef�llen - funktioniert! 
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
								+ "VALUES(" + suchprofil.getProfilId() + ",'" + suchprofil.getGeschlecht() + "','"
								+ suchprofil.getHaarfarbe() + "','" + suchprofil.getKoerpergroesse() + "','"
								+ suchprofil.getRaucher() + "','" + suchprofil.getReligion() + "')");
				
				// Tablle t_suchprofil befüllen - funktioniert! 
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_suchprofil (suchprofil_id, nutzerprofil_id, alter_von, alter_bis) "
						+ "VALUES(" + suchprofil.getProfilId() + "," + Benutzer.getProfilId() + ",'" + suchprofil.getAlterMin() + "','"
						+ suchprofil.getAlterMax() + "')");	
			}
			
		}
		 catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Rückgabe des Suchprofils mit evtl. korrigierter ProfilId. 
		 */
		return suchprofil;	
		}

	/**
	 * Wiederholtes Schreiben eines <code>Suchprofil</code>-Objekts in die Datenbank.
	 */
	public Suchprofil updateSuchprofil(Suchprofil suchprofil) { 
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE t_suchprofil " + "SET alter_von=\"" + "suchprofil.getAlterMin()" 
					+ "\", " + "alter_bis=\"" + "suchprofil.getAlterMax()" + "\" "
					+ "WHERE suchprofil_id=" + suchprofil.getProfilId());
			
			stmt = con.createStatement();
			
			stmt.executeUpdate(
					"UPDATE t_profil " + "SET geschlecht=\"" + suchprofil.getGeschlecht() 
					+ "\", " + "koerpergroesse=\"" + suchprofil.getKoerpergroesse() 
					+ "\", " + "haarfarbe=\"" + suchprofil.getHaarfarbe()
					+ "\", " + "raucher=\"" + suchprofil.getRaucher()
					+ "\", " + "religion=\"" + suchprofil.getReligion() + "\" "
					+ "WHERE profil_id=" + suchprofil.getProfilId());


		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return suchprofil;
	}

	/**
	 * Loeschen der Daten eines <code>Suchprofil</code>-Objekts aus der Datenbank.
	 */
	public void deleteSuchprofil(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE * FROM t_suchprofil " 
					+ "WHERE suchprofil_id =" + profilId);
			
			stmt.executeUpdate("DELETE * FROM t_profil " 
					+ "WHERE profil_id =" + profilId);
			
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Suchen eines Suchprofils mit vorgegebener ProfilId. 
	 */
	public Suchprofil findBySuchprofilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
			
			"SELECT t_suchprofil.alter_von, t_suchprofil.alter_bis, t_suchprofil.suchprofil_id, "
			+ "t_suchprofil.nutzerprofil_id, t_profil.koerpergroesse, t_profil.geschlecht, " 
			+ "t_profil.haarfarbe, t_profil.religion, t_profil.raucher " 
			+ "FROM t_suchprofil INNER JOIN t_profil ON t_suchprofil.suchprofil_id = t_profil.profil_id");
			
					
			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Suchprofil suchprofil = new Suchprofil();
				
				suchprofil.setProfilId(rs.getInt("suchprofil_id"));
				suchprofil.setGeschlecht(rs.getString("geschlecht"));
				suchprofil.setKoerpergroesse(rs.getString("koerpergroesse"));
				suchprofil.setHaarfarbe(rs.getString("haarfarbe"));
				suchprofil.setAlterMin(rs.getString("alter_von"));
				suchprofil.setAlterMax(rs.getString("alter_bis"));	
				suchprofil.setRaucher(rs.getString("raucher"));
				suchprofil.setReligion(rs.getString("religion"));
				
				return suchprofil;
					
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

}
