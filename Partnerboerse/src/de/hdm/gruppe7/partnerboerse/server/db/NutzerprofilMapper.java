package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Mapper-Klasse, die Nutzerprofil-Objekte auf eine relationale Datenbank abbildet. 
 * Das Mapping ist bidirektional, d.h. Objekte koennen in DB-Strukturen und DB-Strukturen in 
 * Objekte umgewandelt werden. 
 */
public class NutzerprofilMapper {

	/**
	   * Die Klasse Nutzerprofil wird nur einmal instantiiert (Singelton). 
	   * Diese Variable ist durch den Bezeichner static nur einmal fuer saemtliche 
	   * eventuellen Instanzen dieser Klasse vorhanden, sie speichert die einzige
	   * Instanz dieser Klasse.
	   * 
	   * @see #nutzerprofilMapper()
	   */
	private static NutzerprofilMapper nutzerprofilMapper = null;

	/**
	 * Geschuetzter Konstruktor, der verhinder, mit new neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerprofilMapper() {
	}

	 /**
	   * Diese statische Methode kann aufgrufen werden durch NutzerprofilMapper.nutzerprofilMapper(). 
	   * Sie stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von NutzerprofilMapper existiert.

	   * @return NutzerprofilMapper-Objekt
	   * @see nutzerprofilMapper
	   */
	public static NutzerprofilMapper nutzerprofilMapper() {
		if (nutzerprofilMapper == null) {
			nutzerprofilMapper = new NutzerprofilMapper();
		}
		return nutzerprofilMapper;
	}

	/**
	 * Nutzerprofil-Objekt in die Datenbank einfuegen.
	 * 
	 * @param n Das einzufuegende Nutzerprofil-Objekt.
	 * @return 	Das bereits uebergebene Nutzerprofil-Objekt, 
	 * 			jedoch mit ggf. korrigierte Profil-ID.
	 */
	public Nutzerprofil insertNutzerprofil(Nutzerprofil n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT MAX(profil_id) AS maxprofil_id FROM t_profil1");

			if (rs.next()) {

				n.setProfilId(rs.getInt("maxprofil_id") + 1);

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil1 (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
						+ "VALUES(" + n.getProfilId() + ",'" + n.getGeschlecht() + "','" + n.getHaarfarbe() + "','" 
						+ n.getKoerpergroesseInt() + "','" + n.getRaucher() + "','" + n.getReligion() + "')");

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_nutzerprofil1 (nutzerprofil_id, vorname, nachname, geburtsdatum, email) "
						+ "VALUES(" + n.getProfilId() + ",'" + n.getVorname() + "','" + n.getNachname() + "','"
						+ n.getGeburtsdatumDate() + "','" + n.getEmailAddress() + "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return n;
	}

	/**
	 * Nutzerprofil-Objekt wiederholt in die Datenbank schreiben.
	 * @param n Das in die Datenbank zu schreibende Nutzerprofil-Objekt.
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
	 * Nutzerprofil-Objekt aus der Datenbank loeschen.
	 * @param profilId Die Profil-ID des Nutzerprofils, das geloescht werden soll.
	 */
	public void deleteNutzerprofil(int profilId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_vermerk1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_sperrung1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_besuch1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_aehnlichkeitnp1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_aehnlichkeitsp1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " OR fremdprofil_id=" + profilId);

			stmt = con.createStatement();
			ResultSet rs1 = stmt
					.executeQuery("SELECT suchprofil_id AS sp_id FROM t_suchprofil1 WHERE nutzerprofil_id="
							+ profilId);

			while (rs1.next()) {

				int suchprofilId = rs1.getInt("sp_id");

				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_info1 " + "WHERE profil_id="
						+ profilId + " OR profil_id=" + suchprofilId);
			}
			
			ResultSet rs2 = stmt
					.executeQuery("SELECT suchprofil_id AS sp_id FROM t_suchprofil1 WHERE nutzerprofil_id="
							+ profilId);
			
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_suchprofil1 "
					+ "WHERE nutzerprofil_id=" + profilId);

			while (rs2.next()) {

				int suchprofilId2 = rs2.getInt("sp_id");

				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil1 "
						+ "WHERE profil_id=" + suchprofilId2); 
			}
			
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_nutzerprofil1 "
					+ "WHERE nutzerprofil_id=" + profilId);
			
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_profil1 " + "WHERE profil_id="
					+ profilId);
			

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Nutzerprofil-Objekt anhand der Profil-ID auslesen. 
	 * @param profilId Die Profil-ID des Nutzerprofils, das ausgelesen werden soll.
	 * @return Das ausgelesene Nutzerprofil-Objekt.
	 */
	public Nutzerprofil findByNutzerprofilId(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_nutzerprofil1, t_profil1 "
							+ "WHERE profil_id= " + profilId
							+ " AND nutzerprofil_id= " + profilId);

			if (rs.next()) {
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
	 * Nutzerprofil-Objekt anhand der E-Mail auslesen.
	 * @param email Die E-Mail des Nutzerprofils, das ausgelesen werden soll.
	 * @return Das ausgelesene Nutzerprofil-Objekt.
	 */
	public Nutzerprofil findByNutzerprofilMitEmail(String email) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_nutzerprofil1 "
							+ "LEFT JOIN t_profil1 "
							+ "ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id "
							+ "WHERE email ='"
							+ email + "'");

			if (rs.next()) {
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
	 * Alle unangesehenen Nutzerprofil-Objekte eines Nutzerprofils auslesen. 
	 * Es werden lediglich diejenigen Nutzerprofil-Objekte ausgelesen, von denen das Nutzerprofil nicht gesperrt wurde.
	 * @param profilId Profil-ID des Nutzersprofils, dessen unangesehenen Nutzerprofil-Objekte ausgelesen werden sollen. 
	 * @return Liste von unangesehenen Nutzerprofil-Objekten. 
	 */
	public List<Nutzerprofil> findUnangeseheneNutzerprofile(int profilId) {
		Connection con = DBConnection.connection();

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_profil1 "
							+ "INNER JOIN t_nutzerprofil1 "
							+ "ON t_profil1.profil_id = t_nutzerprofil1.nutzerprofil_id "
							+ "WHERE t_nutzerprofil1.nutzerprofil_id "
							+ "NOT IN (SELECT t_besuch1.fremdprofil_id "
							+ "FROM t_besuch1 WHERE t_besuch1.nutzerprofil_id=" + profilId + ")");


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

				result.add(nutzerprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Besuch in die Datenbank einfuegen.
	 * @param profilId Die Profil-ID des Nutzerprofils, fuer das der Besuch gesetzt werden soll.
	 * @param fremdprofilId Die Profil-ID des Nutzerprofils, das besucht wurde. 
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
	 * Aehnlichkeit in die Datenbank einfuegen. 
	 * @param profilId Die Profil-ID des Referenzprofils.
	 * @param fremdprofilId Die Profil-ID des Vergleichsprofils. 
	 * @param aehnlichkeit Der Aehnlichkeitswert. 
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
	 * Aehnlichkeit aus der Datenbank loeschen. 
	 * @param 	profilId Die Profil-ID des Nutzerprofils, dessen Aehnlichkeitswerte 
	 * 			entfernt werden sollen.  
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

	/**
	 * Geordnete Partnervorschlaege fuer ein Nutzerprofil auslesen. 
	 * @param profilId Die Profil-ID des Nutzerprofils, fuer das die Partnervorschlaege ausgelesen werden sollen. 
	 * @return Liste von vorgeschlagenenen Nutzerprofil-Objekten.
	 */
	public List<Nutzerprofil> findGeordnetePartnervorschlaegeNp(int profilId) {
		Connection con = DBConnection.connection();

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht, t_profil1.koerpergroesse, t_profil1.haarfarbe, "
							+ "t_profil1.raucher, t_profil1.religion, t_aehnlichkeitnp1.aehnlichkeit FROM t_nutzerprofil1 "
							+ "LEFT JOIN t_profil1 ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id "
							+ "LEFT JOIN t_sperrung1 ON t_nutzerprofil1.nutzerprofil_id = t_sperrung1.nutzerprofil_id "
							+ "LEFT JOIN t_aehnlichkeitnp1 ON t_aehnlichkeitnp1.fremdprofil_id = t_nutzerprofil1.nutzerprofil_id "
							+ "WHERE t_aehnlichkeitnp1.nutzerprofil_id =" + profilId
							+ " AND t_nutzerprofil1.nutzerprofil_id !=" + profilId
							+ " AND (t_sperrung1.fremdprofil_id !=" + profilId
							+ " OR t_sperrung1.nutzerprofil_id IS NULL) "
							+ "ORDER BY t_nutzerprofil1.nutzerprofil_id");


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

				result.add(nutzerprofil);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Alle nicht gesperrten Nutzerprofil-Objekte eines Nutzerprofils auslesen. 
	 * @param profilId Die Profil-ID des Nutzerprofils, fuer das die nicht gesperrten Nutzerprofil-Objekte ausgelesen werden sollen.
	 * @return Liste von nicht gesperrten Nutzerprofil-Objekten. 
	 */
	public List<Nutzerprofil> findNutzerprofileOhneGesetzeSperrung(int profilId) {

		Connection con = DBConnection.connection();

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

				result.add(nutzerprofil);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Geordnete Partnervorschlaege fuer ein Nutzerprofil anhand eines Suchprofils auslesen. 
	 * @param profilId Die Profil-ID des Nutzerprofils, fuer das die Partnervorschlaege ausgelesen werden sollen.
	 * @param suchprofilId Die Profil-ID des Suchprofils, fuer das die Partnervorschlaege ausgelesen werden sollen.
	 * @return Liste von vorgeschlagenenen Nutzerprofil-Objekten. 
	 */

	public List<Nutzerprofil> findGeordnetePartnervorschlaegeSp(int profilId, int suchprofilId) {
		Connection con = DBConnection.connection();

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht, t_profil1.koerpergroesse, t_profil1.haarfarbe,"
							+ " t_profil1.raucher, t_profil1.religion , t_aehnlichkeitsp1.aehnlichkeit"
							+ " FROM t_nutzerprofil1 LEFT JOIN t_profil1 "
							+ "ON t_nutzerprofil1.nutzerprofil_id = t_profil1.profil_id , t_aehnlichkeitsp1 "
							+ "WHERE t_nutzerprofil1.nutzerprofil_id !=" + profilId + " AND t_aehnlichkeitsp1.suchprofil_id = " + suchprofilId 
							+ " AND t_aehnlichkeitsp1.fremdprofil_id = t_nutzerprofil1.nutzerprofil_id "
							+ "ORDER BY t_aehnlichkeitsp1.aehnlichkeit DESC");

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

				result.add(nutzerprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

}
