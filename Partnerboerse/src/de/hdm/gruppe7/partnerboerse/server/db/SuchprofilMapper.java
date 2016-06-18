package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	 * Suchprofil-Objekt in die Datenbank einfuegen.
	 * 
	 * @param s Das einzufuegende Nutzerprofil-Objekt.
	 * @param profilId Die Profil-Id des eigenen Nutzerprofils. 
	 * @return 	Das bereits uebergebene Suchprofil-Objekt, 
	 * 			jedoch mit ggf. korrigierte Profil-ID.
	 */
	public Suchprofil insertSuchprofil(Suchprofil s, int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(profil_id) AS maxprofil_id " + "FROM t_profil1");

			if (rs.next()) {

				s.setProfilId(rs.getInt("maxprofil_id") + 1);

				stmt = con.createStatement();
				stmt.executeUpdate(
						"INSERT INTO t_profil1 (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
								+ "VALUES(" + s.getProfilId() + ",'" + s.getGeschlecht() + "','" + s.getHaarfarbe()
								+ "'," + s.getKoerpergroesseInt() + ",'" + s.getRaucher() + "','" + s.getReligion()
								+ "')");

				stmt = con.createStatement();
				stmt.executeUpdate(
						"INSERT INTO t_suchprofil1 (suchprofil_id, nutzerprofil_id, suchprofilname, alter_von, alter_bis) "
								+ "VALUES(" + s.getProfilId() + "," + profilId + ",'"
								+ s.getSuchprofilName() + "'," + s.getAlterMinInt() + "," + s.getAlterMaxInt() + ")");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return s;
	}

	/**
	 * Suchprofil-Objekt wiederholt in die Datenbank schreiben.
	 * @param s Das in die Datenbank zu schreibende Suchprofil-Objekt.
	 */
	public void updateSuchprofil(Suchprofil s) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE t_suchprofil1 " + "SET suchprofilname=\"" + s.getSuchprofilName() + "\", "
					+ "alter_von=\"" + s.getAlterMinInt() + "\", " + "alter_bis=\"" + s.getAlterMaxInt() + "\" "
					+ "WHERE suchprofil_id=" + s.getProfilId());

			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE t_profil1 " + "SET geschlecht=\"" + s.getGeschlecht() + "\", "
					+ "koerpergroesse=\"" + s.getKoerpergroesseInt() + "\", " + "haarfarbe=\"" + s.getHaarfarbe()
					+ "\", " + "raucher=\"" + s.getRaucher() + "\", " + "religion=\"" + s.getReligion() + "\" "
					+ "WHERE profil_id=" + s.getProfilId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Suchprofil-Objekt aus der Datenbank loeschen.
	 * @param profilId Die Profil-ID des Nutzerprofils, das dessen Suchprofil geloescht werden soll.
	 * @param suchprofilName Der Suchprofilname des Suchprofils, das geloescht werden soll. 
	 */
	public void deleteSuchprofil(int profilId, String suchprofilName) {
		Connection con = DBConnection.connection();

		int suchprofilIdInt = 0;

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT suchprofil_id AS sp_id FROM t_suchprofil1 " + "WHERE t_suchprofil1.nutzerprofil_id="
							+ profilId + " AND t_suchprofil1.suchprofilname LIKE '" + suchprofilName + "'");

			if (rs.next()) {
				suchprofilIdInt = rs.getInt("sp_id");

				stmt = con.createStatement();
				stmt.executeUpdate(
						"DELETE FROM t_aehnlichkeitsp1 " + "WHERE t_aehnlichkeitsp1.suchprofil_id=" + suchprofilIdInt);
				
				stmt = con.createStatement();
				stmt.executeUpdate(
						"DELETE FROM t_info1 " + "WHERE t_info1.profil_id=" + suchprofilIdInt);
				
				stmt = con.createStatement();
				stmt.executeUpdate(
						"DELETE FROM t_suchprofil1 " + "WHERE t_suchprofil1.suchprofil_id=" + suchprofilIdInt);

				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil1 WHERE t_profil1.profil_id=" + suchprofilIdInt);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Alle Suchprofil-Objekte eines Nutzerprofils auslesen. 
	 * @param profilId Die Profil-ID des Nutzers, dessen Suchprofile ausgelesen werden sollen.
	 * @return Liste von ausgelesenen Suchprofil-Objekten. 
	 */
	public List<Suchprofil> findAllSuchprofileFor(int profilId) {
		Connection con = DBConnection.connection();

		List<Suchprofil> result = new ArrayList<Suchprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil1 INNER JOIN "
					+ "t_profil1 ON t_suchprofil1.suchprofil_id = t_profil1.profil_id "
					+ "WHERE t_suchprofil1.nutzerprofil_id=" + profilId);

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

				result.add(s);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Suchprofil-Objekt anhand der Profil-ID und anhand des Suchprofilnamens auslesen.
	 * @param profilId Die Profil-ID des Nutzers, dessen Suchprofil ausgelesen werden soll.
	 * @param suchprofilname Der Name des Suchprofils, das augelesen werden soll.
	 * @return Ausgelesenes Suchprofil-Objekt.
	 */
	public Suchprofil findSuchprofilByName(int profilId, String suchprofilname) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil1 INNER JOIN t_profil1 "
					+ "ON t_suchprofil1.suchprofil_id = t_profil1.profil_id " + "WHERE t_suchprofil1.nutzerprofil_id="
					+ profilId + " AND t_suchprofil1.suchprofilname LIKE '" + suchprofilname + "'");

			if (rs.next()) {
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
	 * Suchprofil-Objekt anhand der Profil-ID und der Suchprofil-ID auslesen.
	 * @param profilId
	 * @param suchprofilId
	 * @return
	 */
	
	public Suchprofil findSuchprofilById (int profilId, int suchprofilId){
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM t_suchprofil1 INNER JOIN t_profil1 "
					+ "ON t_suchprofil1.suchprofil_id = t_profil1.profil_id " + "WHERE t_suchprofil1.nutzerprofil_id="
					+ profilId + " AND t_suchprofil1.suchprofil_id =" + suchprofilId );
			
			if (rs.next()){
				
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
	 * Existenz des Suchprofilnames ueberpruefen. 
	 * @param profilId Die Profil-ID des Nutzers, dessen Suchprofil ueberprueft werden soll.
	 * @param suchprofilName Der Suchprofilname des Suchprofils, dessen Name ueberprueft werden soll.
	 * @return Status, ob der Suchprofilname bereits existiert. 
	 */
	public int pruefeSuchprofilnameExistenz(int profilId, String suchprofilName) {
		Connection con = DBConnection.connection();

		int existenz = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM t_suchprofil1 " + "WHERE nutzerprofil_id=" + profilId
					+ " AND suchprofilname LIKE '" + suchprofilName + "'");

			if (rs.next()) {
				if (rs.getInt("COUNT(*)") == 1)
					existenz = 1;
			} else {
				existenz = 0;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return existenz;
	}
	
	/**
	 * Suchprofilname auslesen. 
	 * @param suchprofilId Die Suchprofil-ID des Suchprofils, das ausgelesen werden soll. 
	 * @return Ausgelesener Suchprofilname. 
	 */
	public String getSuchprofilName(int suchprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT suchprofilname FROM t_suchprofil1 " + "WHERE suchprofil_id=" + suchprofilId);

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
	 * Aehnlichkeit setzen. 
	 * @param profilId Die Profil-ID des Referenzprofils.
	 * @param suchprofilId Die einzufuegende Suchprofil-ID.
	 * @param suchprofilName Der einzufuegende Suchprofilname.
	 * @param fremdprofilId Die Profil-ID des Vergleichsprofils.
	 * @param aehnlichkeitSp Der einzufuegende Aehnlichkeitswert. 
	 */

	public void insertAehnlichkeit(int profilId, int suchprofilId, int fremdprofilId, int aehnlichkeitSp) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_aehnlichkeitsp1 (nutzerprofil_id, suchprofil_id, fremdprofil_id, aehnlichkeit) "
					+ "VALUES  (" + profilId + "," + suchprofilId + "," +  fremdprofilId + "," + aehnlichkeitSp + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Aehnlichkeit entfernen.
	 * @param profilId Die Profil-ID des Nutzerprofils, dessen Aehnlichkeitswerte 
	 * 		  entfernt werden sollen. 
	 */
	public void deleteAehnlichkeitSp(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_aehnlichkeitsp1 WHERE nutzerprofil_id=" + profilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

}
