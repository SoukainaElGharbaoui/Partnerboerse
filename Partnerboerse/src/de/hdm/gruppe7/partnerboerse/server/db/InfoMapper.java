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
 * Mapper-Klasse, die Info-Objekte auf eine relationale Datenbank abbildet. Das
 * Mapping ist bidirektional, d.h. Objekte koennen in DB-Strukturen und
 * DB-Strukturen in Objekte umgewandelt werden.
 */
public class InfoMapper {

	/**
	   * Die Klasse InfoMapper wird nur einmal instantiiert (Singelton). 
	   * Diese Variable ist durch den Bezeichner static nur einmal fuer saemtliche 
	   * eventuellen Instanzen dieser Klasse vorhanden, sie speichert die einzige
	   * Instanz dieser Klasse.
	   * 
	   * @see #infoMapper()
	   */
	private static InfoMapper infoMapper = null;

	/**
	 * Geschuetzter Konstruktor, der verhinder, mit new neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected InfoMapper() {
	}

	/**
	   * Diese statische Methode kann aufgrufen werden durch InfoMapper.infoMapper(). 
	   * Sie stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von InfoMapper existiert.

	   * @return InfoMapper-Objekt
	   * @see infoMapper
	   */
	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}
		return infoMapper;
	}

	/**
	 * Alle Eigenschafte-Objekte auslesen.
	 * @return Liste von Eigenschaft-Objekten.
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
	 * Alle bisher nicht angelgten Eigenschaft-Objekte auslesen. 
	 * @param profilId Profil-ID des Nutzerprofils, fuer das die bisher nicht 
	 * angelegten Eigenschaften ausgelesen werden sollen. 
	 * @return Liste von bisher nicht angelegten Eigenschaft-Objekten.
	 */
	public List<Eigenschaft> findAllUnusedEigenschaftenNeu(int profilId) {
		Connection con = DBConnection.connection();

		List<Eigenschaft> result = new ArrayList<Eigenschaft>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_eigenschaft1 "
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
	 * Info-Objekte in die Datenbank einfuegen. 
	 * @param profilId Profil-ID des Nutzerprofils, fuer das die Infos angelegt werden sollen.
	 * @param infos Liste von Info-Objekten, die angelegt werden sollen. 
	 * @return Liste von angelegten Info-Objekten.
	 */
	public List<Info> insertInfoNeu(int profilId, List<Info> infos) {

		Connection con = DBConnection.connection();

		for (Info i : infos) {

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO t_info1 (profil_id, eigenschaft_id, infotext) " + "VALUES(" + profilId
						+ "," + i.getEigenschaftId() + ",'" + i.getInfotext() + "')");

			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return infos;
	}

	/**
	 * Alle Info-Objekte eines Nutzerprofils anhand der Profil-ID auslesen. 
	 * @param profilId Profil-ID des Nutzerprofils, fuer den die Infos ausgelesen werden sollen.
	 * @return Liste von ausgelesenen Info-Objekten.
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
	 * Ein Eigenschaft-Objekt anhand der Eigenschaft-ID auslesen. 
	 * @param eigenschaftId Die Eigenschaft-ID der Eigenschaft, die ausgelesen werden soll. 
	 * @return Das ausgelesene Eigenschaft-Objekt.
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
	 * Ein Auswahleigenschaft-Objekt anhand der Eigenschaft-ID auslesen. 
	 * @param eigenschaftId Eigenschaft-ID der Auswahleigenschaft, die ausgelesen werden soll. 
	 * @return Das ausgelesene Auswahleigenschaft-Objekt.
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
	 * Ein Beschreibungseigenschaft-Objekt anhand der Eigenschaft-ID auslesen. 
	 * @param eigenschaftId Eigenschaft-ID der Beschreibungseigenschaft, die ausgelesen werden soll. 
	 * @return Das ausgelesene Beschreibungseigenschaft-Objekt.
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
	 * Alle Info-Objekte eines Nutzerprofils loeschen. 
	 * @param profilId Profil-ID des Nutzerprofils, dessen Infos geloescht werden sollen.
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
	 * Ein Info-Objekt eines Nutzerprofils anhand der Profil-ID und der Eigenschaft-ID loeschen. 
	 * @param profilId Profil-ID des Nutzerprofils, dessen Info geloescht werden soll. 
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, deren zugehoerige Info geloescht werden soll.
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

	/**
	 * Info-Objekte eines Nutzerprofils aktualisieren.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Infos aktualisiert werden sollen. 
	 * @param listI Liste von Info-Objekte, die aktualisiert werden sollen.
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
	 * Ein Beschreibungseigenschaft-Objekt in die Datenbank einfuegen (Administrator-Funktion).
	 * @param b Das Beschreibungseigenschaft-Objekt, das angelegt werden soll. 
	 * @return Das angelegte Beschreibungseigenschaft-Objekt. 
	 */
	public Beschreibungseigenschaft insertBeschreibungseigenschaft(Beschreibungseigenschaft b) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(eigenschaft_id) AS maxeigenschaft_id FROM t_eigenschaft1");

			if (rs.next()) {

				b.setEigenschaftId(rs.getInt("maxeigenschaft_id") + 1);

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_eigenschaft1(eigenschaft_id,erlaeuterung,typ) " + "VALUES ("
						+ b.getEigenschaftId() + ",'" + b.getErlaeuterung() + "','" + b.getTyp() + "')");

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_beschreibungseigenschaft1(eigenschaft_id, beschreibungstext) "
						+ "VALUES (" + b.getEigenschaftId() + ",'" + b.getBeschreibungstext() + "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return b;
	}

	/**
	 * Ein Auswahleigenschaft-Objekt in die Datenbank einfuegen (Administrator-Funktion).
	 * @param b Das Auswahleigenschaft-Objekt, das angelegt werden soll. 
	 * @return Das angelegte Auswahleigenschaft-Objekt. 
	 */
	public Auswahleigenschaft insertAuswahleigenschaft(Auswahleigenschaft a) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(eigenschaft_id) AS maxeigenschaft_id FROM t_eigenschaft1");

			if (rs.next()) {

				a.setEigenschaftId(rs.getInt("maxeigenschaft_id") + 1);

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_eigenschaft1(eigenschaft_id,erlaeuterung,typ) " + "VALUES ("
						+ a.getEigenschaftId() + ",'" + a.getErlaeuterung() + "','" + a.getTyp() + "')");

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_auswahleigenschaft1(eigenschaft_id, auswahloption) " + "VALUES ("
						+ a.getEigenschaftId() + ",'" + a.getOptionen() + "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return a;

	}

	/**
	 * Ein Beschreibungseigenschaft-Objekt wiederholt in die Datenbank schreiben (Administrator-Funktion).
	 * @param b Das Beschreibungseigenschaft-Objekt, das wiederholt in die Datenbank geschrieben werden soll.
	 */
	public void updateBeschreibungseigenschaft(Beschreibungseigenschaft b) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_eigenschaft1 " + "SET erlaeuterung=\"" + b.getErlaeuterung() + "\", "
					+ " typ=\"" + b.getTyp() + "\" " + "WHERE eigenschaft_id=" + b.getEigenschaftId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_Beschreibungseigenschaft1 " + "SET beschreibungstext=\""
					+ b.getBeschreibungstext() + "\" " + "WHERE eigenschaft_id=" + b.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Ein Auswahleigenschaft-Objekt wiederholt in die Datenbank schreiben (Administrator-Funktion).
	 * @param a Das Auswahleigenschaft-Objekt, das wiederholt in die Datenbank geschrieben werden soll.
	 */
	public void updateAuswahleigenschaft(Auswahleigenschaft a) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_eigenschaft1 " + "SET erlaeuterung=\"" + a.getErlaeuterung() + "\", "
					+ " typ=\"" + a.getTyp() + "\" " + "WHERE eigenschaft_id=" + a.getEigenschaftId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_Auswahleigenschaft1 " + "SET auswahloption=\"" + a.getOptionen() + "\" "
					+ "WHERE eigenschaft_id=" + a.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Ein Beschreibungseigenschaft-Objekt aus der Datenbank loeschen (Administrator-Funktion). 
	 * @param eigenschaftId Eigenschaft-ID der Beschreibungseigenschaft, die geloescht werden soll. 
	 */
	public void deleteBeschreibungseigenschaft(int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_beschreibungseigenschaft1 " + "WHERE eigenschaft_id=" + eigenschaftId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_eigenschaft1 " + "WHERE eigenschaft_id=" + eigenschaftId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ein Auswahleigenschaft-Objekt aus der Datenbank loeschen (Administrator-Funktion). 
	 * @param eigenschaftId Eigenschaft-ID der Auswahleigenschaft, die geloescht werden soll. 
	 */
	public void deleteAuswahleigenschaft(int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_auswahleigenschaft1 " + "WHERE eigenschaft_id=" + eigenschaftId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_eigenschaft1 " + "WHERE eigenschaft_id=" + eigenschaftId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}
