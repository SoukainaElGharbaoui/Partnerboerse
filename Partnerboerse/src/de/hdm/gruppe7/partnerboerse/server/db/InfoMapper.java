
package de.hdm.gruppe7.partnerboerse.server.db;


import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InfoMapper {

	private static InfoMapper infoMapper = null;

	protected InfoMapper() {

	}

	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}

		return infoMapper;
	}

	public List<Eigenschaft> findAllEigenschaftenNeu() {
		Connection con = DBConnection.connection();

		List<Eigenschaft> result = new ArrayList<Eigenschaft>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_eigenschaft1");

			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setEigenschaftId(rs.getInt("eigenschaft_id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));
				e.setTyp(rs.getString("typ"));

				result.add(e);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	public List<Eigenschaft> findAllUnusedEigenschaftenNeu(int profilId) {
		Connection con = DBConnection.connection();
		
		List<Eigenschaft> result = new ArrayList<Eigenschaft>();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_eigenschaft1.eigenschaft_id, "
							+ "t_eigenschaft1.erlaeuterung, t_eigenschaft1.typ "
							+ "FROM t_eigenschaft1 LEFT JOIN t_info1 "
							+ "ON t_eigenschaft1.eigenschaft_id = t_info1.eigenschaft_id "
							+ "WHERE t_info1.profil_id !=" + profilId + " OR t_info1.eigenschaft_id IS NULL");
			
			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setEigenschaftId(rs.getInt("eigenschaft_id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));
				e.setTyp(rs.getString("typ"));

				result.add(e);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
		
	}


	public Info insertInfoNeu(Info i) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_info1 (profil_id, eigenschaft_id, infotext) " + "VALUES("
					+ i.getProfilId() + "," + i.getEigenschaftId() + ",'" + i.getInfotext() + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();

		}
		return i;
	}

	public List<Info> findAllInfosNeu(int profilId) {
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_info1 WHERE profil_id=" + profilId);

			// ResultSet rs = stmt.executeQuery("SELECT eigenschaft_id,
			// erlaeuterung FROM t_eigenschaft "
			// + "WHERE typ='B' ORDER BY eigenschaft_id ");

			while (rs.next()) {
				Info i = new Info();

				i.setProfilId(rs.getInt("profil_id"));
				i.setEigenschaftId(rs.getInt("eigenschaft_id"));
				i.setInfotext(rs.getString("infotext"));

				result.add(i);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}

	public Eigenschaft findEigenschaftByIdNeu(int eigenschaftId) {
		Connection con = DBConnection.connection();

		Eigenschaft e = new Eigenschaft();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_eigenschaft1 WHERE eigenschaft_id=" + eigenschaftId);

			while (rs.next()) {
				e.setEigenschaftId(rs.getInt("eigenschaft_id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));
				e.setTyp(rs.getString("typ"));

				return e;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	public Auswahleigenschaft findEigAByIdNeu(int eigenschaftId) {
		Connection con = DBConnection.connection();

		List<String> optionen = new ArrayList<String>();
		Auswahleigenschaft eigA = new Auswahleigenschaft();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_auswahleigenschaft1 " + "WHERE eigenschaft_id=" + eigenschaftId);

			while (rs.next()) {

				eigA.setEigenschaftId(rs.getInt("eigenschaft_id"));

				String option = rs.getString("auswahloption");
				optionen.add(option);
			}

			eigA.setOptionen(optionen);
			return eigA;

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	public Beschreibungseigenschaft findEigBByIdNeu(int eigenschaftId) {
		Connection con = DBConnection.connection();

		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM t_beschreibungseigenschaft1 " + "WHERE eigenschaft_id=" + eigenschaftId);

			while (rs.next()) {

				eigB.setEigenschaftId(rs.getInt("eigenschaft_id"));
				eigB.setBeschreibungstext(rs.getString("beschreibungstext"));
			}

			return eigB;

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	public void deleteAllInfosNeu(int profilId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_info1 " + "WHERE profil_id=" + profilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public void deleteOneInfoNeu(int profilId, int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"DELETE FROM t_info1 " + "WHERE profil_id=" + profilId + " AND eigenschaft_id=" + eigenschaftId);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public List<Info> findInfosByProfilId(int profilId) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_info1 WHERE t_info1.profil_id =" + profilId);

			while (rs.next()) {
				Info info = new Info();
				info.setInfotext(rs.getString("infotext"));
				info.setProfilId(rs.getInt("profil_id"));
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				result.add(info);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public void updateInfosNeu(Info i) {
		Connection con = DBConnection.connection();

		String infotextAlt;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT infotext AS infotext_alt " + "FROM t_info1 WHERE profil_id="
					+ i.getProfilId() + " AND eigenschaft_id=" + i.getEigenschaftId());

			if (rs.next()) {
				infotextAlt = rs.getString("infotext_alt");

				stmt = con.createStatement();

				stmt.executeUpdate(
						"UPDATE t_info1 " + "SET infotext='" + i.getInfotext() + "' WHERE infotext='" + infotextAlt
								+ "' AND profil_id=" + i.getProfilId() + " AND eigenschaft_id=" + i.getEigenschaftId());

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Caros Methoden: Beginn
	 */

	public List<Info> findAInfoByProfilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT auswahloption_id, eigenschaft_id " + "FROM t_auswahlinfo "
					+ "WHERE t_auswahlinfo.nutzerprofil_id =" + profilId);

			while (rs.next()) {

				Info info = new Info();
				// info.setAuswahloptionId(rs.getInt("auswahloption_id"));
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				result.add(info);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Alle Beschreibungsinfos f�r ein Profil auslesen
	 * 
	 * @return List<Info>
	 */
	public List<Info> findBInfoByProfilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT infotext, eigenschaft_id " + "FROM t_beschreibungsinfo "
					+ "WHERE t_beschreibungsinfo.nutzerprofil_id =" + profilId);

			while (rs.next()) {

				Info info = new Info();
				info.setInfotext(rs.getString("infotext"));
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				result.add(info);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public String findEigenschaftstextById(int eigenschaftId) {
		Connection con = DBConnection.connection();

		String eigenschaftstext = new String();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM t_eigenschaft1 WHERE eigenschaft_id =" + eigenschaftId);

			while (rs.next()) {

				eigenschaftstext = rs.getString("erlaeuterung");
			}

			return eigenschaftstext;

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return eigenschaftstext;
	}

	/**
	 * Caros Methoden: Ende
	 */
}
