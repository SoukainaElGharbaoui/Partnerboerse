package de.hdm.gruppe7.partnerboerse.server.db;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

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

			ResultSet rs = stmt.executeQuery("SELECT eigenschaft_id, erlaeuterung FROM t_eigenschaft WHERE typ='B'");

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

			ResultSet rs = stmt.executeQuery("SELECT eigenschaft_id, erlaeuterung FROM t_eigenschaft WHERE typ='A'");

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

			ResultSet rs = stmt.executeQuery("SELECT "
					+ "t_beschreibungsinfo.nutzerprofil_id, "
					+ "t_beschreibungsinfo.infotext, "
					+ "t_eigenschaft.erlaeuterung "
					+ "FROM t_beschreibungsinfo INNER JOIN t_eigenschaft "
					+ "ON t_beschreibungsinfo.eigenschaft_id = t_eigenschaft.eigenschaft_id "
					+ "WHERE t_beschreibungsinfo.nutzerprofil_id=" + profilId);
			
			
			while (rs.next()) {
				Info info = new Info();
				info.setNutzerprofilId(profilId);
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
		int auswahloptionId;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT "
					+ "t_auswahlinfo.nutzerprofil_id, "
					+ "t_auswahlinfo.auswahloption_id, "
					+ "t_eigenschaft.erlaeuterung "
					+ "FROM t_auswahlinfo INNER JOIN t_eigenschaft "
					+ "ON t_auswahlinfo.eigenschaft_id = t_eigenschaft.eigenschaft_id "
					+ "WHERE t_auswahlinfo.nutzerprofil_id=" + profilId);
			
//			auswahloptionId = rs.getInt("auswahloption_id");
			
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("SELECT "
//					+ "t_auswahloption.optionsbezeichnung "
//					+ "FROM t_auswahloption INNER JOIN t_auswahlinfo "
//					+ "ON t_auswahlinfo.auswahloption_id = t_auswahloption.auswahloption_id "
//					+ "AND t_auswahlinfo.eigenschaft_id = t_auswahloption.eigenschaft_id "
//					+ "WHERE t_auswahlinfo.auswahloption_id=" + auswahloptionId
//					+ "AND t_auswahlinfo.nutzerprofil_id=" + profilId);		
			
			while (rs.next()) {
				Info info = new Info();
				info.setNutzerprofilId(profilId);
				info.setEigenschaftErlaeuterung(rs.getString("erlaeuterung"));
//				info.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));
				
				result.add(info);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}
	
	

	// public Info findByInfoId(int infoId) {
	// // DB-Verbindung holen
	// Connection con = DBConnection.connection();
	//
	// try {
	// // Leeres SQL-Statement (JDBC) anlegen
	// Statement stmt = con.createStatement();
	//
	// // Statement ausfÃ¼llen und als Query an die DB schicken
	// ResultSet rs = stmt.executeQuery(
	// "SELECT info_id, t_nutzerprofil.nutzerprofil_id,
	// t_eigenschaft.eigenschaft_id, infotext FROM t_beschreibungsinfo,
	// t_nutzerprofil, t_eigenschaft "
	// + "WHERE info_id=" + infoId);
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
	//
	// return null;
	// }

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

			stmt.executeUpdate("INSERT INTO t_auswahlinfo (nutzerprofil_id, eigenschaft_id, auswahloption_id) "
					+ "VALUES(" + info.getNutzerprofilId() + "," + info.getEigenschaftId() + ",'"
					+ auswahloptionId + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return info;
	}

	// public Info updateInfo(Info info) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("UPDATE t_info" + "SET infotext=\"WHERE info_id=" +
	// info.getInfoId());
	//
	// } catch (SQLException e2) {
	// e2.printStackTrace();
	// }
	//
	// // Um Analogie zu insert(Info info) zu wahren,
	// // geben wir info zurÃ¼ck
	// return info;
	// }
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

	// public void deleteInfo(Info info) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM t_beschreibungsinfo " + "WHERE info_id ="
	// + info.getInfoId());
	//
	// } catch (SQLException e2) {
	// e2.printStackTrace();
	// }
	// }
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
