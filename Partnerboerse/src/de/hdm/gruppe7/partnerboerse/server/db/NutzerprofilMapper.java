package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class NutzerprofilMapper {

	private static NutzerprofilMapper nutzerprofilMapper = null;

	// Konstruktor
	protected NutzerprofilMapper() {
	}

	public static NutzerprofilMapper nutzerprofilMapper() {
		if (nutzerprofilMapper == null) {
			nutzerprofilMapper = new NutzerprofilMapper();
		}

		return nutzerprofilMapper;
	}

	/**
	 * Suchen eines Nutzerprofils mit vorgegebener ProfilId.
	 */
	public Nutzerprofil findByNutzerprofilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(

			"SELECT * FROM t_nutzerprofil, t_profil " + "WHERE profil_id= "
					+ profilId + " AND nutzerprofil_id=" + profilId);

			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatum(rs.getString("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setKoerpergroesse(rs.getString("Koerpergroesse"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setRaucher(rs.getString("raucher"));
				nutzerprofil.setReligion(rs.getString("religion"));
				return nutzerprofil;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * Auslesen des Fremdprofils
	 * 
	 * @param profilId
	 * @return
	 */

	public Nutzerprofil findByFremdprofilId(int fremdprofilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(

			"SELECT * FROM t_nutzerprofil, t_profil " + "WHERE nutzerprofil_id="
					+ fremdprofilId + " AND profil_id=" + fremdprofilId);

			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				return nutzerprofil;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Auslesen aller Nutzerprofile.
	 */
	public Vector<Nutzerprofil> findAllNutzerprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt

					.executeQuery("SELECT * FROM t_profil,"
							+ "t_nutzerprofil WHERE t_profil.profil_id = "
							+ "t_nutzerprofil.nutzerprofil_id ORDER BY nutzerprofil_id");
			



			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatum(rs.getString("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesse(rs.getString("koerpergroesse"));
				nutzerprofil.setRaucher(rs.getString("raucher"));
				nutzerprofil.setReligion(rs.getString("religion"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(nutzerprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;

	}
	
	/**
	 * Auslesen aller unangesehenen Nutzerprofile.
	 */
	public List<Nutzerprofil> findUnangeseheneNutzerprofile(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery(							
							"SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname, t_nutzerprofil.geburtsdatum, "
							+ "t_profil.geschlecht, t_profil.koerpergroesse, t_profil.haarfarbe, t_profil.raucher, t_profil.religion "
							+ "FROM t_nutzerprofil LEFT JOIN t_besuch ON t_nutzerprofil.nutzerprofil_id = t_besuch.fremdprofil_id "
							+ "LEFT JOIN t_profil ON t_nutzerprofil.nutzerprofil_id = t_profil.profil_id "
							+ "LEFT JOIN t_sperrung ON t_nutzerprofil.nutzerprofil_id = t_sperrung.nutzerprofil_id "
							+ "WHERE t_nutzerprofil.nutzerprofil_id !=" + profilId
							+ " AND (t_besuch.nutzerprofil_id !=" + profilId
							+ " OR t_besuch.fremdprofil_id IS NULL) "
							+ "AND (t_sperrung.fremdprofil_id !=" + profilId
							+ " OR t_sperrung.nutzerprofil_id IS NULL) ORDER BY t_nutzerprofil.nutzerprofil_id");


			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatum(rs.getString("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesse(rs.getString("koerpergroesse"));
				nutzerprofil.setRaucher(rs.getString("raucher"));
				nutzerprofil.setReligion(rs.getString("religion"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(nutzerprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	
	/**
	 * Einfügen eines <code>Nutzerprofil</code>-Objekts in die Datenbank.
	 */
	public Nutzerprofil insertNutzerprofil(Nutzerprofil nutzerprofil) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Größte profil_id ermitteln.
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(profil_id) AS maxprofil_id "
							+ "FROM t_profil");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein.
			if (rs.next()) {

				// nutzerprofil erhaelt den bisher maximalen, nun um 1
				// inkrementierten Primärschlüssel.
				nutzerprofil.setProfilId(rs.getInt("maxprofil_id") + 1);

				// Tabelle t_profil befüllen - funktioniert.
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
						+ "VALUES("
						+ nutzerprofil.getProfilId()
						+ ",'"
						+ nutzerprofil.getGeschlecht()
						+ "','"
						+ nutzerprofil.getHaarfarbe()
						+ "','"
						+ nutzerprofil.getKoerpergroesse()
						+ "','"
						+ nutzerprofil.getRaucher()
						+ "','"
						+ nutzerprofil.getReligion() + "')");

				// Tablle t_nutzerprofil befüllen - funktioniert.
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_nutzerprofil (nutzerprofil_id, vorname, nachname, geburtsdatum) "
						+ "VALUES("
						+ nutzerprofil.getProfilId()
						+ ",'"
						+ nutzerprofil.getVorname()
						+ "','"
						+ nutzerprofil.getNachname()
						+ "','"
						+ nutzerprofil.getGeburtsdatum() + "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Rückgabe des Nutzerprofils mit evtl. korrigierter ProfilId.
		 */
		return nutzerprofil;
	}

	/**
	 * Wiederholtes Schreiben eines <code>Nutzerprofil</code>-Objekts in die
	 * Datenbank.
	 */
	public void updateNutzerprofil(String vorname, String nachname,
			String geburtsdatum, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE t_nutzerprofil " + "SET vorname=\""
					+ vorname + "\", " + " nachname=\"" + nachname + "\", "
					+ " geburtsdatum=\"" + geburtsdatum + "\" "
					+ "WHERE nutzerprofil_id=" + Benutzer.getProfilId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_profil " + "SET geschlecht=\""
					+ geschlecht + "\", " + " haarfarbe=\"" + haarfarbe
					+ "\", " + " koerpergroesse=\"" + koerpergroesse + "\", "
					+ "raucher=\"" + raucher + "\", " + " religion=\""
					+ religion + "\" " + "WHERE profil_id="
					+ Benutzer.getProfilId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Loeschen der Daten eines <code>Nutzerprofil</code>-Objekts aus der
	 * Datenbank.
	 */
	public void deleteNutzerprofil(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisvariable, d.h. die NutzerprofilId
		int nutzerprofilIdInt = 0;

		try {

			Statement stmt = con.createStatement();

			// Holen der zu löschenden NutzerprofilId aus der Tabelle
			// t_nutzerprofil
			ResultSet rs = stmt
					.executeQuery("SELECT nutzerprofil_id AS np_id "
							+ "FROM t_nutzerprofil WHERE t_nutzerprofil.nutzerprofil_id="
							+ profilId);

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein.
			if (rs.next()) {
				nutzerprofilIdInt = rs.getInt("np_id");

				// Löschen der Daten in der Tabelle t_nutzerprofil mit der
				// entsprechenden ProfilId
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_nutzerprofil "
						+ "WHERE t_nutzerprofil.nutzerprofil_id=" + profilId);

				// Löschen der Daten in der Tabelle t_profil mit der
				// entsprechenden NutzerprofilId
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil WHERE t_profil.profil_id="
						+ nutzerprofilIdInt);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
		
		/**
		 * Besuch hinzufuegen. 
		 */
		public void insertBesuch(int profilId, int fremdprofilId) { 
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO t_besuch (nutzerprofil_id, fremdprofil_id) " + "VALUES (" 
				+ profilId + "," + fremdprofilId + ")");

			} catch (SQLException e2) {
				e2.printStackTrace();
			}	
		}
	
		
		/**
		 * Besuchstatus ermitteln. 
		 */
		public int pruefeBesuch(int profilId, int fremdprofilId) {
			Connection con = DBConnection.connection();
			
			// Ergebnisvariable (Ausgang: Es liegt kein Besuch vor.)
			int besuchstatus = 0; 
			
			try {
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM t_besuch "
						+ "WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id=" + fremdprofilId);
				
				if (rs.next()) {
			        // Es liegt ein Besuch vor.  
					besuchstatus = 1; 
			      } else {
			    	  // Es liegt kein Besuch vor. 
			    	  besuchstatus = 0; 
			      }
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return besuchstatus; 
		}
}
