package de.hdm.gruppe7.partnerboerse.server.db;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;

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

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>InfoMapper.infoMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafÃ¼r sorgt, dass nur eine
	 * einzige Instanz von <code>InfoMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> InfoMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>InfoMapper</code>-Objekt.
	 * @see infoMapper
	 */
	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}

		return infoMapper;
	}

	public List<Eigenschaft> findAllEigenschaftenB() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Eigenschaft> result = new ArrayList<Eigenschaft>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT eigenschaft_id, erlaeuterung FROM t_eigenschaft "
					+ "WHERE typ='B' ORDER BY eigenschaft_id ");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Eigenschaft eigenschaft = new Eigenschaft();
				eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
				eigenschaft.setErlaeuterung(rs.getString("erlaeuterung"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(eigenschaft);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	// FindAllAuswahloptionen erstellen
	public List<Auswahloption> findAllAuswahloptionen(int eigenschaftId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Auswahloption> result = new ArrayList<Auswahloption>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT auswahloption_id, eigenschaft_id, optionsbezeichnung "
					+ "FROM t_auswahloption WHERE eigenschaft_id=" + eigenschaftId);

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Auswahloption auswahloption = new Auswahloption();
				auswahloption.setAuswahloptionId(rs.getInt("auswahloption_id"));
				auswahloption.setEigenschaftId(rs.getInt("eigenschaft_id"));
				auswahloption.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(auswahloption);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	public List<Eigenschaft> findAllEigenschaftenA() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Eigenschaft> result = new ArrayList<Eigenschaft>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT eigenschaft_id, erlaeuterung FROM t_eigenschaft "
					+ "WHERE typ='A' ORDER BY eigenschaft_id ");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Eigenschaft eigenschaft = new Eigenschaft();
				eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
				eigenschaft.setErlaeuterung(rs.getString("erlaeuterung"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(eigenschaft);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	public List<Info> findAllInfosB(int profilId) {
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT " + "t_beschreibungsinfo.nutzerprofil_id, " + "t_beschreibungsinfo.infotext, "
							+ "t_beschreibungsinfo.eigenschaft_id, " + "t_eigenschaft.erlaeuterung "
							+ "FROM t_beschreibungsinfo, t_eigenschaft " + "WHERE t_beschreibungsinfo.nutzerprofil_id="
							+ profilId + " AND t_beschreibungsinfo.eigenschaft_id = t_eigenschaft.eigenschaft_id "
							+ "ORDER BY t_beschreibungsinfo.eigenschaft_id ");

			while (rs.next()) {
				Info info = new Info();
				info.setNutzerprofilId(profilId);
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				info.setEigenschaftErlaeuterung(rs.getString("erlaeuterung"));
				info.setInfotext(rs.getString("infotext"));

				result.add(info);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public List<Info> findAllInfosA(int profilId) {
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT " + "t_eigenschaft.erlaeuterung, "
					+ "t_auswahloption.optionsbezeichnung, " + "t_auswahlinfo.nutzerprofil_id, "
					+ "t_auswahlinfo.eigenschaft_id " + "FROM t_eigenschaft, t_auswahloption, t_auswahlinfo "
					+ "WHERE t_auswahlinfo.nutzerprofil_id=" + profilId
					+ " AND t_auswahlinfo.eigenschaft_id = t_eigenschaft.eigenschaft_id "
					+ "AND t_auswahloption.eigenschaft_id = t_auswahlinfo.eigenschaft_id "
					+ "AND t_auswahloption.auswahloption_id = t_auswahlinfo.auswahloption_id "
					+ "ORDER BY t_auswahlinfo.eigenschaft_id ");

			while (rs.next()) {
				Info info = new Info();
				info.setNutzerprofilId(rs.getInt("nutzerprofil_id"));
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				info.setEigenschaftErlaeuterung(rs.getString("erlaeuterung"));
				info.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));

				result.add(info);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public Info findOptionById(int eigenschaftId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT t_auswahlinfo.auswahloption_id, "
					+ "t_auswahlinfo.eigenschaft_id, " + "t_auswahloption.optionsbezeichnung "
					+ "FROM t_auswahlinfo, t_auswahloption " + "WHERE t_auswahlinfo.eigenschaft_id=" + eigenschaftId
					+ " AND t_auswahlinfo.eigenschaft_id = t_auswahloption.eigenschaft_id "
					+ "AND t_auswahlinfo.auswahloption_id = t_auswahloption.auswahloption_id ");

			if (rs.next()) {

				Info info = new Info();
				info.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));
				return info;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	public Info findByInfoAId(String optionsbezeichnung, int eigenschaftId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT auswahloption_id, eigenschaft_id " + "FROM t_auswahloption "
					+ "WHERE optionsbezeichnung='" + optionsbezeichnung + "' " + "AND eigenschaft_id=" + eigenschaftId);

			if (rs.next()) {

				Info info = new Info();
				info.setAuswahloptionId(rs.getInt("auswahloption_id"));
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				return info;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	// public List<Info> findAllInfos() {
	// Connection con = DBConnection.connection();
	//
	// // Ergebnisliste vorbereiten
	// List<Info> result = new ArrayList<Info>();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// ResultSet rs = stmt.executeQuery("SELECT * FROM t_beschreibungsinfo ORDER
	// BY info_id");
	//
	// // FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
	// // Nutzerprofil-Objekt erstellt.
	// while (rs.next()) {
	// Info info = new Info();
	// info.setInfoId(rs.getInt("info_id"));
	// info.setInfotext(rs.getString("infotext"));
	//
	// // HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
	// result.add(info);
	// }
	// } catch (SQLException e2) {
	// e2.printStackTrace();
	// }
	//
	// // Ergebnisliste zurÃ¼ckgeben
	// return result;
	// }

	public Info insertBeschreibungsinfo(Info info) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_beschreibungsinfo (nutzerprofil_id, eigenschaft_id, infotext) "
					+ "VALUES(" + info.getNutzerprofilId() + "," + info.getEigenschaftId() + ",'" + info.getInfotext()
					+ "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return info;
	}

	public Info insertAuswahlinfo(Info info) {
		Connection con = DBConnection.connection();

		int auswahloptionId = info.getAuswahloptionId() + 1;

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"INSERT INTO t_auswahlinfo (nutzerprofil_id, eigenschaft_id, auswahloption_id) " + "VALUES("
							+ info.getNutzerprofilId() + "," + info.getEigenschaftId() + ",'" + auswahloptionId + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return info;
	}

	public void updateInfoA(int profilId, int neueAuswahloptionId, int eigenschaftId) {
		Connection con = DBConnection.connection();

		int auswahloptionId = 0;

		try {
			Statement stmt = con.createStatement();

			// Holen der zu löschenden SuchprofilId aus der Tabelle t_suchprofil
			ResultSet rs = stmt.executeQuery("SELECT auswahloption_id AS ao_id "
					+ "FROM t_auswahlinfo WHERE nutzerprofil_id=" + profilId + " AND eigenschaft_id=" + eigenschaftId);

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein.
			if (rs.next()) {
				auswahloptionId = rs.getInt("ao_id");

				stmt = con.createStatement();

				stmt.executeUpdate("UPDATE t_auswahlinfo " + "SET auswahloption_id=" + neueAuswahloptionId
						+ " WHERE auswahloption_id=" + auswahloptionId + " AND nutzerprofil_id=" + profilId
						+ " AND eigenschaft_id=" + eigenschaftId);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public void updateInfoB(int profilId, int eigenschaftId, String infotext) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_beschreibungsinfo " + "SET infotext=\"" + infotext + "\" "
					+ "WHERE nutzerprofil_id=" + profilId + " AND eigenschaft_id=" + eigenschaftId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	//
	// public void deleteInfo(Info info) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM t_info " + "WHERE info_id =" +
	// info.getInfoId());
	//
	// } catch (SQLException e2) {
	// e2.printStackTrace();
	// }
	// }

	/**
	 * Gesamte Info löschen.
	 */
	public void deleteAllInfos(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_beschreibungsinfo WHERE nutzerprofil_id=" + profilId);

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_auswahlinfo WHERE nutzerprofil_id=" + profilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Bestimmten Teil der Beschreibungsinfo löschen.
	 */
	public void deleteOneInfoB(int profilId, int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_beschreibungsinfo WHERE nutzerprofil_id=" + profilId
					+ " AND eigenschaft_id=" + eigenschaftId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Bestimmten Teil der Auswahlinfo löschen.
	 */
	public void deleteOneInfoA(int profilId, int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_auswahlinfo WHERE nutzerprofil_id=" + profilId + " AND eigenschaft_id="
					+ eigenschaftId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

/**
 * alle Auswahlinfos f�r ein Profil auslesen
 * @param profilId
 * @return
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
				info.setAuswahloptionId(rs.getInt("auswahloption_id"));
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
	 * @return
	 */
	public List<Info> findBInfoByProfilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();
		
		List<Info> result = new ArrayList<Info>();
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT infotext, eigenschaft_id "
							+ "FROM t_beschreibungsinfo "
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
	

	
	
	
	public List<Info> findAllAInfos() {
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT t_auswahlinfo.nutzerprofil_id, t_auswahlinfo.auswahloption_id, t_auswahlinfo.eigenschaft_id "
							+ "FROM t_auswahlinfo");

			while (rs.next()) {
				Info info = new Info();
				info.setNutzerprofilId(rs.getInt("nutzerprofil_id"));
				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
				info.setAuswahloptionId(rs.getInt("auswahloption_id"));

				result.add(info);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	// public List<Auswahleigenschaft> findByAuswahl (Auswahleigenschaft
	// auswahl){
	//
	//
	// // DB-Verbindung holen
	// Connection con = DBConnection.connection();
	//
	// try {
	// // Leeres SQL-Statement (JDBC) anlegen
	// Statement stmt = con.createStatement();
	//
	// // Statement ausfÃ¼llen und als Query an die DB schicken
	// ResultSet rs = stmt.executeQuery(
	// "SELECT eigenschaft_id, auswahltext FROM t_auswahleigenschaft " + "WHERE
	// auswahl_id=" + auswahlId);
	//
	// /*
	// * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
	// * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
	// */
	// if (rs.next()) {
	// // Ergebnis-Tupel in Objekt umwandeln
	// Info info = new Info();
	// info.setInfoId(rs.getInt("info_id"));
	// info.setInfotext(rs.getString("infotext"));
	// return info;
	// }
	// } catch (SQLException e2) {
	// e2.printStackTrace();
	// return null;
	// }
}
