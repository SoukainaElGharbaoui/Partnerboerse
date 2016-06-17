package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;

public class NutzerprofilMapper {

	private static NutzerprofilMapper nutzerprofilMapper = null;

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	protected NutzerprofilMapper() {
	}

	public static NutzerprofilMapper nutzerprofilMapper() {
		if (nutzerprofilMapper == null) {
			nutzerprofilMapper = new NutzerprofilMapper();
		}
		return nutzerprofilMapper;
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	/**
	 * Nutzerprofil-Objekt in die Datenbank einfügen.
	 */
	public Nutzerprofil insertNutzerprofil(Nutzerprofil n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Größte profil_id aus Tabelle t_profil ermitteln.
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(profil_id) AS maxprofil_id FROM t_profil1");

			// Wenn wir etwas zurückerhalten...
			if (rs.next()) {

				// Nutzerprofil-Objekt mit bisher maximalem, nun um 1
				// inkrementierten Primärschlüssel versehen.
				n.setProfilId(rs.getInt("maxprofil_id") + 1);

				// Tabelle t_profil befüllen.
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil1 (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
						+ "VALUES("
						+ n.getProfilId()
						+ ",'"
						+ n.getGeschlecht()
						+ "','"
						+ n.getHaarfarbe()
						+ "','"
						+ n.getKoerpergroesseInt()
						+ "','"
						+ n.getRaucher()
						+ "','" + n.getReligion() + "')");

				// Tablle t_nutzerprofil befüllen.
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_nutzerprofil1 (nutzerprofil_id, vorname, nachname, geburtsdatum, email) "
						+ "VALUES("
						+ n.getProfilId()
						+ ",'"
						+ n.getVorname()
						+ "','"
						+ n.getNachname()
						+ "','"
						+ n.getGeburtsdatumDate()
						+ "','"
						+ n.getEmailAddress()
						+ "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Nutzerprofil-Objekt zurückgeben.
		 */
		return n;
	}

	/**
	 * Nutzerprofil-Objekt wiederholt in die Datenbank schreiben.
	 */
	public void updateNutzerprofil(Nutzerprofil n) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_nutzerprofil1 " + "SET vorname=\""
					+ n.getVorname() + "\", " + " nachname=\""
					+ n.getNachname() + "\", " + " geburtsdatum=\""
					+ n.getGeburtsdatumDate() + "\" "
					+ "WHERE nutzerprofil_id=" + n.getProfilId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_profil1 " + "SET geschlecht=\""
					+ n.getGeschlecht() + "\", " + " haarfarbe=\""
					+ n.getHaarfarbe() + "\", " + " koerpergroesse=\""
					+ n.getKoerpergroesseInt() + "\", " + "raucher=\""
					+ n.getRaucher() + "\", " + " religion=\""
					+ n.getReligion() + "\" " + "WHERE profil_id="
					+ n.getProfilId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Nutzerprofil-Objekt aus der Datenbank löschen.
	 */
	public void deleteNutzerprofil(int profilId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			// Nutzer aus Tabelle Vermerk loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_vermerk1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			// Nutzer aus Tabelle Sperrung loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_sperrung1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			// Nutzer aus Tabelle Besuch loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_besuch1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			// Nutzer aus Tabelle Aehnlichkeit-Nutzerprofil loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_aehnlichkeitnp1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			// Nutzer aus Tabelle Aehnlichkeit-Suchprofil loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_aehnlichkeitsp1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			// Alle Suchprofil-IDs eines Nutzers auslesen.
			stmt = con.createStatement();
			ResultSet rs1 = stmt
					.executeQuery("SELECT suchprofil_id AS sp_id FROM t_suchprofil1 WHERE nutzerprofil_id="
							+ profilId);

			while (rs1.next()) {

				int suchprofilId = rs1.getInt("sp_id");

				// Alle Infos des Nutzers und dessen Suchprofilen loeschen.
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_info1 " + "WHERE profil_id="
						+ profilId + " OR profil_id=" + suchprofilId);
			}
			
			// Alle Suchprofile des Nutzers aus der Tabelle t_suchprofil1
			// loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_suchprofil1 "
					+ "WHERE nutzerprofil_id=" + profilId);
			
			
			while(rs1.next()) {
				
				int suchprofilId2 = rs1.getInt("sp_id");
				
				// Alle Suchprofile des Nutzers aus der Tabelle t_profil1 loeschen.
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil1 "
						+ "WHERE profil_id=" + suchprofilId2);
				
				
			}
			
			// Nutzer aus der Tabelle Nutzerprofil loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_nutzerprofil1 "
					+ "WHERE nutzerprofil_id=" + profilId);

			// Nutzer aus der Tabelle Profil loeschen.
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_profil1 " + "WHERE profil_id="
					+ profilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Nutzerprofil mit vorgegebener Profil-ID suchen.
	 */
	public Nutzerprofil findByNutzerprofilId(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_nutzerprofil1, t_profil1 "
							+ "WHERE profil_id= " + profilId
							+ " AND nutzerprofil_id= " + profilId);

			/*
			 * Es kann max. ein Ergebnis-Tupel zurückgegeben werden. Prüfen, ob
			 * ein Ergebnis-Tupel vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Nutzerprofil-Objekt umwandeln.
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));
				n.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				n.setHaarfarbe(rs.getString("haarfarbe"));
				n.setRaucher(rs.getString("raucher"));
				n.setReligion(rs.getString("religion"));
				n.setEmailAddress(rs.getString("email"));
				return n;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * ********************************* Nutzer mit Email suchen
	 * *********************************
	 */
	public Nutzerprofil findByNutzerprofilMitEmail(String email) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_nutzerprofil1, t_profil1 WHERE email ='"
							+ email + "'");

			if (rs.next()) {
				// return findByNutzerprofilId(rs.getInt("nutzerprofil_id"));
				// Ergebnis-Tupel in Nutzerprofil-Objekt umwandeln.
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));
				n.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				n.setHaarfarbe(rs.getString("haarfarbe"));
				n.setRaucher(rs.getString("raucher"));
				n.setReligion(rs.getString("religion"));
				n.setEmailAddress(rs.getString("email"));
				return n;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * *********************************** Unnötig, da gleicher Mapper-Aufruf!
	 * ***********************************
	 */
	public Nutzerprofil findByFremdprofilId(int fremdprofilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(

			"SELECT * FROM t_nutzerprofil1, t_profil1 "
					+ "WHERE nutzerprofil_id=" + fremdprofilId
					+ " AND profil_id=" + fremdprofilId);

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
	 * 
	 * Auslesen aller Nutzerprofile.
	 * 
	 * @param nutzerprofil
	 */
	public Vector<Nutzerprofil> findAllNutzerprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten.
		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_profil1, t_nutzerprofil1 "
							+ "WHERE t_profil1.profil_id = t_nutzerprofil1.nutzerprofil_id "
							+ "ORDER BY nutzerprofil_id");

			// Für jedes Ergebnis-Tupel ein Nutzerprofil-Objekt erstellen.
			while (rs.next()) {
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));
				n.setHaarfarbe(rs.getString("haarfarbe"));
				n.setKoerpergroesse(rs.getString("koerpergroesse"));
				n.setRaucher(rs.getString("raucher"));
				n.setReligion(rs.getString("religion"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(n);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;

	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Partnervorschläge
	 * *************************************************************************
	 * **
	 */

	/**
	 * Alle unangesehenen Nutzerprofile auslesen.
	 */
	public List<Nutzerprofil> findUnangeseheneNutzerprofile(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, t_nutzerprofil1.geburtsdatum, "
							+ "t_profil1.geschlecht, t_profil1.koerpergroesse, t_profil1.haarfarbe, t_profil1.raucher, t_profil1.religion "
							+ "FROM t_nutzerprofil1 LEFT JOIN t_besuch1 ON t_nutzerprofil1.nutzerprofil_id = t_besuch1.fremdprofil_id "
							+ "LEFT JOIN t_profil1 ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id "
							+ "LEFT JOIN t_sperrung1 ON t_nutzerprofil1.nutzerprofil_id = t_sperrung1.nutzerprofil_id "
							+ "WHERE t_nutzerprofil1.nutzerprofil_id !="
							+ profilId
							+ " AND (t_besuch1.nutzerprofil_id !="
							+ profilId
							+ " OR t_besuch1.fremdprofil_id IS NULL) "
							+ "AND (t_sperrung1.fremdprofil_id !="
							+ profilId
							+ " OR t_sperrung1.nutzerprofil_id IS NULL) ORDER BY t_nutzerprofil1.nutzerprofil_id");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
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
	 * Besuch setzen.
	 */
	public void insertBesuch(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_besuch1 (nutzerprofil_id, fremdprofil_id) "
					+ "VALUES (" + profilId + "," + fremdprofilId + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * ******************* Brauchen wir das??? *******************
	 */
	public int pruefeBesuch(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		// Ergebnisvariable (Ausgang: Es liegt kein Besuch vor.)
		int besuchstatus = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_besuch1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " AND fremdprofil_id=" + fremdprofilId);

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

	/**
	 * Aehnlichkeit hinzufuegen.
	 */
	public void insertAehnlichkeit(int profilId, int fremdprofilId,
			int aehnlichkeit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_aehnlichkeitnp1 (nutzerprofil_id, fremdprofil_id, aehnlichkeit) "
					+ "VALUES ("
					+ profilId
					+ ","
					+ fremdprofilId
					+ ","
					+ aehnlichkeit + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Geordnete Partnervorschlaege ausgeben
	 */
	public List<Nutzerprofil> findGeordnetePartnervorschlaegeNp(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht, t_profil1.koerpergroesse, "
							+ "t_profil1.haarfarbe, t_profil1.raucher, t_profil1.religion , t_aehnlichkeitnp1.aehnlichkeit "
							+ "FROM t_nutzerprofil1 LEFT JOIN t_besuch1 "
							+ "ON t_nutzerprofil1.nutzerprofil_id = t_besuch1.fremdprofil_id "
							+ "LEFT JOIN t_profil1 ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id "
							+ "LEFT JOIN t_sperrung1 ON t_nutzerprofil1.nutzerprofil_id = t_sperrung1.nutzerprofil_id "
							+ "LEFT JOIN t_aehnlichkeitnp1 ON t_nutzerprofil1.nutzerprofil_id = t_aehnlichkeitnp1.fremdprofil_id "

							+ "WHERE t_nutzerprofil1.nutzerprofil_id != " + profilId
							+ " AND (t_besuch1.nutzerprofil_id !=" + profilId
							+ " OR t_besuch1.fremdprofil_id IS NULL) "
							+ "AND (t_sperrung1.fremdprofil_id !=" + profilId
							+ " OR t_sperrung1.nutzerprofil_id IS NULL) "
							+ "AND t_aehnlichkeitnp1.nutzerprofil_id =" + profilId
							+ " ORDER BY t_aehnlichkeitnp1.aehnlichkeit DESC");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				nutzerprofil.setRaucher(rs.getString("raucher"));
				nutzerprofil.setReligion(rs.getString("religion"));
				nutzerprofil.setAehnlichkeit(rs.getInt("aehnlichkeit"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(nutzerprofil);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Partnervorschläge
	 * ***************************************
	 * ************************************
	 */

	/**
	 * Alle Nutzerprofile die mich nicht gesperrt haben
	 */

	public List<Nutzerprofil> findNutzerprofileOhneGesetzeSperrung(int profilId) {

		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, "
							+ "t_nutzerprofil1.nachname, t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht, "
							+ "t_profil1.koerpergroesse, t_profil1.haarfarbe, t_profil1.raucher, t_profil1.religion "
							+ "FROM t_nutzerprofil1 LEFT JOIN t_profil1 ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id "
							+ "LEFT JOIN t_sperrung1 ON t_nutzerprofil1.nutzerprofil_id = t_sperrung1.nutzerprofil_id "
							+ "WHERE t_nutzerprofil1.nutzerprofil_id !="
							+ profilId
							+ " AND (t_sperrung1.fremdprofil_id !="
							+ profilId
							+ " OR t_sperrung1.nutzerprofil_id IS NULL) ORDER BY t_nutzerprofil1.nutzerprofil_id");

			while (rs.next()) {

				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
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
	 * Geordnete Partnervorschlaege ausgeben
	 */
	public List<Nutzerprofil> findGeordnetePartnervorschlaegeSp(int profilId, int suchprofilId) {
		Connection con = DBConnection.connection();


		// Ergebnisliste vorbereiten
		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery(							
							"SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht, t_profil1.koerpergroesse, t_profil1.haarfarbe,"
							+ " t_profil1.raucher, t_profil1.religion , t_aehnlichkeitsp1.aehnlichkeit"
							+ " FROM t_nutzerprofil1 LEFT JOIN t_profil1 "
							+ "ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id , t_aehnlichkeitsp1 "
							+ "WHERE t_nutzerprofil1.nutzerprofil_id !=" + profilId + " AND t_aehnlichkeitsp1.suchprofil_id = " + suchprofilId 
							+ " AND t_aehnlichkeitsp1.fremdprofil_id = t_nutzerprofil1.nutzerprofil_id "
							+ "ORDER BY t_aehnlichkeitsp1.aehnlichkeit DESC  ");

			// Für jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				nutzerprofil.setRaucher(rs.getString("raucher"));
				nutzerprofil.setReligion(rs.getString("religion"));
				nutzerprofil.setAehnlichkeitSp(rs.getInt("aehnlichkeit"));
				

				// Hinzufügen des neuen Objekts zur Ergebnisliste
				result.add(nutzerprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurückgeben
		return result;
	}

	/**
	 * Aehnlichkeit loeschen.
	 */
	public void deleteAehnlichkeit(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_aehnlichkeitnp1 WHERE nutzerprofil_id="
					+ profilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Partnervorschläge
	 * *************************************************************************
	 * **
	 */

	/**
	 * E-Mail bei Login setzen.
	 */
	public void insertEmail(String emailAdress) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_nutzerprofil1 (email) "
					+ "VALUES ('" + emailAdress + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
