package de.hdm.gruppe7.partnerboerse.server.db;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dunja
 *
 */
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


	/**
	 * Auslesen aller Eigenschaften.
	 * 
	 * @return Eine Liste mit Eigenschaft-Objekten, die saemtliche Eigenschaften repraesentieren.
	 */
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

				System.out.println(e.getErlaeuterung());
				result.add(e);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	/**
	 * Auslesen aller Eigenschaften die vom Nutzer nicht mit Infos befuellt wurde.
	 * 
	 * @param profilId
	 * @return Eine Liste von Eigenschaften, 
	 * die saemtliche Eigenschaften repraesentieren die vom Nutzer nicht genutzt werden.
	 */
	public List<Eigenschaft> findAllUnusedEigenschaftenNeu(int profilId) {
		Connection con = DBConnection.connection();
		
		List<Eigenschaft> result = new ArrayList<Eigenschaft>();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_eigenschaft1 "
							+ "WHERE t_eigenschaft1.eigenschaft_id NOT IN (SELECT t_info1.eigenschaft_id "
							+ "FROM t_info1 WHERE t_info1.profil_id=" + profilId + ")");

			
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


	/**
	 * Anlegen mehrerer Infos, welche im GUI vom Nutzer selbst gesetzt werden.
	 * 
	 * @param profilId
	 * @param infos
	 * @return Eine Liste von Info-Objekten, welche die angelegten Infos repraesentieren.
	 */
	public List<Info> insertInfoNeu(int profilId, List<Info> infos) {

		Connection con = DBConnection.connection();
		
		for (Info i : infos) {

		
			try {
				Statement stmt = con.createStatement();
	
				stmt.executeUpdate("INSERT INTO t_info1 (profil_id, eigenschaft_id, infotext) " + "VALUES("
						+ profilId + "," + i.getEigenschaftId() + ",'" + i.getInfotext() + "')");
	
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return infos;
	}

	/**
	 * Alle Infos eines Nutzers auslesen, anhand der Profil-ID.
	 * 
	 * @param profilId
	 * @return Eine Liste von Info-Objekten, welche saemtliche Infos eines Nutzers repraesentieren.
	 */
	public List<Info> findAllInfosNeu(int profilId) {
		
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_info1 WHERE profil_id=" + profilId);

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

	/**
	 * Auslesen einer Eigenschaft anhand der Eigenschaft-ID.
	 * 
	 * @param eigenschaftId
	 * @return Ein Eigenschaft-Objekt, welches die ausgelesene Eigenschaft repraesentiert.
	 */
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

	/**
	 * Auslesen einer Auswahleigenschaft anhand der Eigenschaft-ID.
	 * 
	 * @param eigenschaftId
	 * @return Ein Auswahleigenschaft-Objekt, welches die ausgelesene Auswahleigenschaft repraesentiert.
	 */
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

	/**
	 * Auslesen einer Beschreibungseigenschaft anhand der Eigenschaft-ID.
	 * 
	 * @param eigenschaftId
	 * @return Ein Beschreibungseigenschaft-Objekt, welches die ausgelesene Beschreibungseigenschaft repraesentiert.
	 */
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
	

	/**
	 * Loeschen aller Infos eines Nutzers, anhand der Profil-ID.
	 * 
	 * @param profilId
	 */
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

	/**
	 * Loeschen einer Info eines Nutzers anhand der Profil-ID und der Eigenschaft-ID.
	 * 
	 * @param profilId
	 * @param eigenschaftId
	 */
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

	
//	public List<Info> findInfosByProfilId(int profilId) {
//
//		// DB-Verbindung holen
//		Connection con = DBConnection.connection();
//
//		List<Info> result = new ArrayList<Info>();
//		try {
//			// Leeres SQL-Statement (JDBC) anlegen
//			Statement stmt = con.createStatement();
//
//			ResultSet rs = stmt.executeQuery("SELECT * FROM t_info1 WHERE t_info1.profil_id =" + profilId);
//
//			while (rs.next()) {
//				Info info = new Info();
//				info.setInfotext(rs.getString("infotext"));
//				info.setProfilId(rs.getInt("profil_id"));
//				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				result.add(info);
//
//			}
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		return result;
//	}
	

	/**
	 * Infos eines Nutzers aktualisieren.
	 * 
	 * @param profilId
	 * @param listI
	 */
	public void updateInfos(int profilId, List<Info> listI) {
		Connection con = DBConnection.connection();
		String infotextAlt;
		
		for (Info i : listI) {

			try {
				Statement stmt = con.createStatement();
	
				ResultSet rs = stmt.executeQuery("SELECT infotext AS infotext_alt " + "FROM t_info1 WHERE profil_id="
						+ profilId + " AND eigenschaft_id=" + i.getEigenschaftId());
	
				if (rs.next()) {
					infotextAlt = rs.getString("infotext_alt");
	
					stmt = con.createStatement();
	
					stmt.executeUpdate(
							"UPDATE t_info1 " + "SET infotext='" + i.getInfotext() + "' WHERE infotext='" + infotextAlt
									+ "' AND profil_id=" + profilId + " AND eigenschaft_id=" + i.getEigenschaftId());
	
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @param profilId
	 * @return
	 */
//	public List<Info> findAInfoByProfilId(int profilId) {
//		// DB-Verbindung holen
//		Connection con = DBConnection.connection();
//
//		List<Info> result = new ArrayList<Info>();
//		
//		try {
//			// Leeres SQL-Statement (JDBC) anlegen
//			Statement stmt = con.createStatement();
//
//			// Statement ausfÃ¼llen und als Query an die DB schicken
//			ResultSet rs = stmt.executeQuery("SELECT auswahloption_id, eigenschaft_id " + "FROM t_auswahlinfo "
//					+ "WHERE t_auswahlinfo.nutzerprofil_id =" + profilId);
//
//			while (rs.next()) {
//
//				Info info = new Info();
//				// info.setAuswahloptionId(rs.getInt("auswahloption_id"));
//				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				result.add(info);
//			}
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		return result;
//	}

	/**
	 * Alle Beschreibungsinfos fuer ein Profil auslesen
	 * 
	 * @param profilId 
	 * @return List<Info>
	 */
//	public List<Info> findBInfoByProfilId(int profilId) {
//		// DB-Verbindung holen
//		Connection con = DBConnection.connection();
//
//		List<Info> result = new ArrayList<Info>();
//		
//		try {
//			// Leeres SQL-Statement (JDBC) anlegen
//			Statement stmt = con.createStatement();
//
//			// Statement ausfÃ¼llen und als Query an die DB schicken
//			ResultSet rs = stmt.executeQuery("SELECT infotext, eigenschaft_id " + "FROM t_beschreibungsinfo "
//					+ "WHERE t_beschreibungsinfo.nutzerprofil_id =" + profilId);
//
//			while (rs.next()) {
//
//				Info info = new Info();
//				info.setInfotext(rs.getString("infotext"));
//				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				result.add(info);
//			}
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		return result;
//	}


	/**
	 * Auslesen des Eigenschaftstext anhand der Eigenschaft-ID.
	 * 
	 * @param eigenschaftId
	 * @return Ein String, der den Eigenschaftstext repraesentiert.
	 */
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
	 * Beschreibungseigenschaft_Objekt in die Datenbank einfuegen. F�r den Administrator.
	 * 
	 * @param b Das einzufugende Beschreibungseigenschaft-Objekt.
	 * @return Das bereits uebergebene Beschreibungseigenschaft-Objekt, 
	 * 			jedoch mit ggf. korrigierte Eigenschaft-ID.
	 */
	public Beschreibungseigenschaft insertBeschreibungseigenschaft (Beschreibungseigenschaft b){
		
		
		Connection con = DBConnection.connection();
			
		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
				.executeQuery("SELECT MAX(eigenschaft_id) AS maxeigenschaft_id FROM t_eigenschaft1");
			
			if(rs.next()) {
				
				b.setEigenschaftId(rs.getInt("maxeigenschaft_id") + 1);
			
			 stmt = con.createStatement();	
			 stmt.executeUpdate("INSERT INTO t_eigenschaft1(eigenschaft_id,erlaeuterung,typ) "
					+ "VALUES (" + b.getEigenschaftId() + ",'" + b.getErlaeuterung() + "','" + b.getTyp() + "')" );
			 
			 stmt = con.createStatement();
			 stmt.executeUpdate("INSERT INTO t_beschreibungseigenschaft1(eigenschaft_id, beschreibungstext) "
			 		+ "VALUES (" + b.getEigenschaftId() + ",'" + b.getBeschreibungstext() + "')" );
				}
			
			} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return b;
	}
	
	
	/**
	 * Auswahleigenschaft_Objekt in die Datenbank einfuegen. F�r den Administrator. Fuer den Administrator.
	 * @param a Das einzufugende Beschreibungseigenschaft-Objekt.
	 * @return Das bereits uebergebene Beschreibungseigenschaft-Objekt, 
	 * 			jedoch mit ggf. korrigierte Eigenschaft-ID.
	 */
	public Auswahleigenschaft insertAuswahleigenschaft (Auswahleigenschaft a) {
		
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
				.executeQuery("SELECT MAX(eigenschaft_id) AS maxeigenschaft_id FROM t_eigenschaft1");
			
			if(rs.next()) {
				
				a.setEigenschaftId(rs.getInt("maxeigenschaft_id") + 1);
			
			 stmt = con.createStatement();	
			 stmt.executeUpdate("INSERT INTO t_eigenschaft1(eigenschaft_id,erlaeuterung,typ) "
					+ "VALUES (" + a.getEigenschaftId() + ",'" + a.getErlaeuterung() + "','" + a.getTyp() + "')" );
			 
			 stmt = con.createStatement();
			 stmt.executeUpdate("INSERT INTO t_auswahleigenschaft1(eigenschaft_id, auswahloption) "
			 		+ "VALUES (" + a.getEigenschaftId() + ",'" + a.getOptionen() + "')" );
				}
			
			} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		return a;
		
		
	}	
	
	/**
	 * Beschreibungseigenschaft-Objekt wiederholt in die Datenbank schreiben. Fuer den Administrator.
	 * @param b Das in die Datenbank zu schreibende Beschreibungseigenschaft-Objekt.
	 */
	public void updateBeschreibungseigenschaft(Beschreibungseigenschaft b) {

		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_eigenschaft1 " + "SET erlaeuterung=\""
					+ b.getErlaeuterung() + "\", " + " typ=\""
					+ b.getTyp() + "\" "
					+ "WHERE eigenschaft_id=" + b.getEigenschaftId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_Beschreibungseigenschaft1 " + "SET beschreibungstext=\""
					+ b.getBeschreibungstext() + "\" "  + "WHERE eigenschaft_id="
					+ b.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}
	
	/**
	 * Auswahleigenschaft-Objekt wiederholt in die Datenbank schreiben. Fuer den Administrator.
	 * @param a  Das in die Datenbank zu schreibende Auswahleigenschaft-Objekt.
	 */
	public void updateAuswahleigenschaft(Auswahleigenschaft a) {

		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_eigenschaft1 " + "SET erlaeuterung=\""
					+ a.getErlaeuterung() + "\", " + " typ=\""
					+ a.getTyp() + "\" "
					+ "WHERE eigenschaft_id=" + a.getEigenschaftId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_Auswahleigenschaft1 " + "SET auswahloption=\""
					+ a.getOptionen() + "\" "  + "WHERE eigenschaft_id="
					+ a.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}
	
	
	/**
	 * Beschreibungseigenschaft-Objekt aus der Datenbank loeschen. Fuer den Administrator.
	 * @param eigenschaftId
	 */
	public void deleteBeschreibungseigenschaft(int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_beschreibungseigenschaft1 "
					+ "WHERE eigenschaft_id=" + eigenschaftId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_eigenschaft1 "
					+ "WHERE eigenschaft_id=" + eigenschaftId);				

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	/**
	 * Auswahleigenschaft-Objekt aus der Datenbank loeschen. Fuer den Administrator.
	 * @param eigenschaftId
	 */
	public void deleteAuswahleigenschaft(int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_auswahleigenschaft1 "
					+ "WHERE eigenschaft_id=" + eigenschaftId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_eigenschaft1 "
					+ "WHERE eigenschaft_id=" + eigenschaftId);				

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	

}
