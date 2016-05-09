package de.hdm.gruppe7.partnerboerse.server.db;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

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

	// public Info findByInfoId(int nutzerprofilId, int eigenschaftId) {
	// // DB-Verbindung holen
	// Connection con = DBConnection.connection();
	//
	// try {
	// // Leeres SQL-Statement (JDBC) anlegen
	// Statement stmt = con.createStatement();
	//
	// // Statement ausfÃ¼llen und als Query an die DB schicken
	// ResultSet rs = stmt
	// .executeQuery("SELECT nutzerprofil_id, eigenschaft_id, infotext FROM
	// t_beschreibungsinfo "
	// + "WHERE nutzerprofil_id=" + nutzerprofilId + ", eigenschaft_id=" +
	// eigenschaftId);
	//
	//
	// /*
	// * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
	// * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
	// */
	// if (rs.next()) {
	// // Ergebnis-Tupel in Objekt umwandeln
	// Info info = new Info();
	// nutzerprofil.setNutzerprofilId(rs.getInt("nutzerprofil_id"));
	// eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
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

	 public List<Eigenschaft> findAllEigenschaften() {
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
	 
	 

	public Info insertInfo(Info info) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// // Größte info_id ermitteln.
			// ResultSet rs = stmt.executeQuery("SELECT MAX(info_id) AS
			// maxinfo_id " + "FROM t_info");

			// // Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein.
			// if (rs.next()) {
			//
			// // info erhaelt den bisher maximalen, nun um 1 inkrementierten
			// // Primärschlüssel.
			// info.setInfoId(rs.getInt("maxinfo_id") + 1);

			// Tabelle t_info befüllen
			// stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO t_beschreibungsinfo (nutzerprofil_id, eigenschaft_id, infotext) "
					+ "VALUES(" + Benutzer.getProfilId() + "," + 1 + ",'" + info.getInfotext() + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Rückgabe der Info mit evtl. korrigierter InfoId.
		 */
		return info;
	}

//	public Info updateInfo(Info info) {
//		Connection con = DBConnection.connection();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt.executeUpdate("UPDATE t_info" + "SET infotext=\"WHERE info_id=" + info.getInfoId());
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		// Um Analogie zu insert(Info info) zu wahren,
//		// geben wir info zurÃ¼ck
//		return info;
//	}
//
//	public void deleteInfo(Info info) {
//		Connection con = DBConnection.connection();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt.executeUpdate("DELETE FROM t_info " + "WHERE info_id =" + info.getInfoId());
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//	}

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
