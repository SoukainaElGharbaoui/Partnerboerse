package de.hdm.gruppe7.partnerboerse.server.db;

//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahlinfo;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungsinfo;
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

	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}

		return infoMapper;
	}

//	public List<Eigenschaft> findAllEigenschaftenB() {
//		Connection con = DBConnection.connection();
//
//		// Ergebnisliste vorbereiten
//		List<Eigenschaft> result = new ArrayList<Eigenschaft>();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			ResultSet rs = stmt
//					.executeQuery("SELECT * FROM t_eigenschaft "
//							+ "WHERE typ='B' ORDER BY eigenschaft_id ");
//
//			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
//			// Nutzerprofil-Objekt erstellt.
//			while (rs.next()) {
//				Eigenschaft eigenschaft = new Eigenschaft();
//				eigenschaft.setTyp(rs.getString("typ"));
//				eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				eigenschaft.setErlaeuterung(rs.getString("erlaeuterung"));
//
//				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
//				result.add(eigenschaft);
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		// Ergebnisliste zurÃ¼ckgeben
//		return result;
//	}
//
//	
//	
//
//	public List<Eigenschaft> findAllEigenschaftenA() {
//		Connection con = DBConnection.connection();
//
//		// Ergebnisliste vorbereiten
//		List<Eigenschaft> result = new ArrayList<Eigenschaft>();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			ResultSet rs = stmt
//					.executeQuery("SELECT * FROM t_eigenschaft "
//							+ "WHERE typ='A' ORDER BY eigenschaft_id ");
//
//			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
//			// Nutzerprofil-Objekt erstellt.
//			while (rs.next()) {
//				Eigenschaft eigenschaft = new Eigenschaft();
//				eigenschaft.setTyp(rs.getString("typ"));
//				eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				eigenschaft.setErlaeuterung(rs.getString("erlaeuterung"));
//
//				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
//				result.add(eigenschaft);
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		// Ergebnisliste zurÃ¼ckgeben
//		return result;
//	}
//	
//	
//	
//	
//	// FindAllAuswahloptionen erstellen
//		public List<Auswahloption> findAllAuswahloptionen(int eigenschaftId) {
//			Connection con = DBConnection.connection();
//
//			// Ergebnisliste vorbereiten
//			List<Auswahloption> result = new ArrayList<Auswahloption>();
//
//			try {
//				Statement stmt = con.createStatement();
//
//				ResultSet rs = stmt
//						.executeQuery("SELECT auswahloption_id, eigenschaft_id, optionsbezeichnung "
//								+ "FROM t_auswahloption WHERE eigenschaft_id="
//								+ eigenschaftId);
//
//				// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
//				// Nutzerprofil-Objekt erstellt.
//				while (rs.next()) {
//					Auswahloption auswahloption = new Auswahloption();
//					auswahloption.setAuswahloptionId(rs.getInt("auswahloption_id"));
//					auswahloption.setEigenschaftId(rs.getInt("eigenschaft_id"));
//					auswahloption.setOptionsbezeichnung(rs
//							.getString("optionsbezeichnung"));
//
//					// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
//					result.add(auswahloption);
//				}
//			} catch (SQLException e2) {
//				e2.printStackTrace();
//			}
//
//			// Ergebnisliste zurÃ¼ckgeben
//			return result;
//		}
		
//
//	
//	
//	public List<Beschreibungsinfo> findAllInfosB(int profilId) {
//		Connection con = DBConnection.connection();
//
//		List<Beschreibungsinfo> result = new ArrayList<Beschreibungsinfo>();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			ResultSet rs = stmt
//					.executeQuery("SELECT "
//							+ "t_beschreibungsinfo.nutzerprofil_id, "
//							+ "t_beschreibungsinfo.infotext, "
//							+ "t_beschreibungsinfo.eigenschaft_id "
////							+ "t_eigenschaft.erlaeuterung "
//							+ "FROM t_beschreibungsinfo, t_eigenschaft "
//							+ "WHERE t_beschreibungsinfo.nutzerprofil_id=" + profilId
//							+ " AND t_beschreibungsinfo.eigenschaft_id = t_eigenschaft.eigenschaft_id "
//							+ "ORDER BY t_beschreibungsinfo.eigenschaft_id ");
//
//			while (rs.next()) {
//				Beschreibungsinfo infoB = new Beschreibungsinfo();
//				infoB.setNutzerprofilId(profilId);
//				infoB.setEigenschaftId(rs.getInt("eigenschaft_id"));
////				infoB.setEigenschaftErlaeuterung(rs.getString("erlaeuterung"));
//				infoB.setInfotext(rs.getString("infotext"));
//
//				result.add(infoB);
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		return result;
//	}

	
	public List<Info> findAllInfosNeu(int nutzerprofilId){
		Connection con = DBConnection.connection();
		
		List<Info> result = new ArrayList<Info>();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_info1 WHERE nutzerprofil_id=" + nutzerprofilId);
			
//							SELECT * FROM t_info1 WHERE nutzerprofil_id=1 klappt !
			
			while(rs.next()){
				Info i = new Info();
				i.setNutzerprofilId(rs.getInt("nutzerprofil_id"));
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
	
	
	

	public Eigenschaft findEigenschaftById(int eigenschaftId) {
		Connection con = DBConnection.connection();
		
		Eigenschaft e = new Eigenschaft();
		
		try {
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM t_eigenschaft1 WHERE eigenschaft_id=" + eigenschaftId);
		
		while(rs.next()){
		e.setEigenschaftId(rs.getInt("eigenschaft_id"));
		e.setErlaeuterung(rs.getString("erlaeuterung"));
		e.setTyp(rs.getString("typ"));
		}
	
	} catch (SQLException e2) {
		e2.printStackTrace();
		return null;
	}
		return e;
}
	
	
	public void deleteOneInfoNeu(int profilId, int eigenschaftId) {
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_info1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " AND eigenschaft_id=" + eigenschaftId);
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	
	
//		public String findOptionById(int eigenschaftId, int nutzerprofilId) {
//			Connection con = DBConnection.connection();
//			
//			try {
//				Statement stmt = con.createStatement();
//	
//				ResultSet rs = stmt
//						.executeQuery("SELECT "
//								+ "t_auswahloption.optionsbezeichnung "
//								+ "FROM t_auswahloption, t_auswahlinfo "
//								+ "WHERE t_auswahloption.auswahloption_id = t_auswahlinfo.auswahloption_id "
//								+ "AND t_auswahloption.eigenschaft_id = t_auswahlinfo.eigenschaft_id "
//								+ "AND t_auswahlinfo.nutzerprofil_id=" + nutzerprofilId
//								+ " AND t_auswahloption.eigenschaft_id=" + eigenschaftId);
				
//				if (rs.next()) {
//				Auswahloption optionA = new Auswahloption();
//				
//				String optionsbez = rs.getString("optionsbezeichnung");
//				
////					optionA.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));
//					
////				return optionA;
//				return optionsbez;
//				
////				}
//				
//			} catch (SQLException e2) {
//				e2.printStackTrace();
//				return null;
//			}
////			return null;
//		}
//		
//	
//	
//	public List<Auswahlinfo> findAllInfosA(int profilId) {
//		Connection con = DBConnection.connection();
//
//		List<Auswahlinfo> result = new ArrayList<Auswahlinfo>();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			ResultSet rs = stmt
//					.executeQuery("SELECT "
////							+ "t_eigenschaft.erlaeuterung, "
////							+ "t_auswahloption.optionsbezeichnung, "
//							+ "t_auswahlinfo.nutzerprofil_id, "
//							+ "t_auswahlinfo.eigenschaft_id, "
//							+ "t_auswahlinfo.auswahloption_id "
//							+ "FROM t_eigenschaft, t_auswahloption, t_auswahlinfo "
//							+ "WHERE t_auswahlinfo.nutzerprofil_id=" + profilId
//							+ " AND t_auswahlinfo.eigenschaft_id = t_eigenschaft.eigenschaft_id "
//							+ "AND t_auswahloption.eigenschaft_id = t_auswahlinfo.eigenschaft_id "
//							+ "AND t_auswahloption.auswahloption_id = t_auswahlinfo.auswahloption_id "
//							+ "ORDER BY t_auswahlinfo.eigenschaft_id ");
//
//			while (rs.next()) {
//								
//				Auswahlinfo infoA = new Auswahlinfo();
//				infoA.setNutzerprofilId(rs.getInt("nutzerprofil_id"));
//				infoA.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				infoA.setAuswahloptionId(rs.getInt("auswahloption_id"));
//				
////				infoA.setEigenschaftErlaeuterung(rs.getString("erlaeuterung"));
////				infoA.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));
//
//				result.add(infoA);
//
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		return result;
//	}

//	
//	public Info findByInfoAId(String optionsbezeichnung, int eigenschaftId) {
//		// DB-Verbindung holen
//		Connection con = DBConnection.connection();
//
//		try {
//			// Leeres SQL-Statement (JDBC) anlegen
//			Statement stmt = con.createStatement();
//
//			// Statement ausfÃ¼llen und als Query an die DB schicken
//			ResultSet rs = stmt
//					.executeQuery("SELECT auswahloption_id, eigenschaft_id "
//							+ "FROM t_auswahloption "
//							+ "WHERE optionsbezeichnung='" + optionsbezeichnung + "' "
//							+ "AND eigenschaft_id=" + eigenschaftId);
//
//			if (rs.next()) {
//
//				Info info = new Info();
//				info.setAuswahloptionId(rs.getInt("auswahloption_id"));
//				info.setEigenschaftId(rs.getInt("eigenschaft_id"));
//				return info;
//
//			}
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//			return null;
//		}
//
//		return null;
//	}
//
//
//	
//	public Beschreibungsinfo insertBeschreibungsinfo(Beschreibungsinfo infoB) {
//		Connection con = DBConnection.connection();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt.executeUpdate("INSERT INTO t_beschreibungsinfo (nutzerprofil_id, eigenschaft_id, infotext) "
//					+ "VALUES("
//					+ infoB.getNutzerprofilId()
//					+ ","
//					+ infoB.getEigenschaftId()
//					+ ",'"
//					+ infoB.getInfotext()
//					+ "')");
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		return infoB;
//	}
//
//	public Auswahlinfo insertAuswahlinfo(Auswahlinfo infoA) {
//		Connection con = DBConnection.connection();
//
//		int auswahloptionId = infoA.getAuswahloptionId() + 1;
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt.executeUpdate("INSERT INTO t_auswahlinfo (nutzerprofil_id, eigenschaft_id, auswahloption_id) "
//					+ "VALUES("
//					+ infoA.getNutzerprofilId()
//					+ ","
//					+ infoA.getEigenschaftId() + ",'" + auswahloptionId + "')");
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//		return infoA;
//	}
//	
//
//	public void updateInfoA(int profilId, int neueAuswahloptionId, int eigenschaftId) {
//		Connection con = DBConnection.connection();
//		
//		 int auswahloptionId = 0; 
//
//			try {
//				Statement stmt = con.createStatement(); 
//				
//				// Holen der zu löschenden SuchprofilId aus der Tabelle t_suchprofil
//				ResultSet rs = stmt.executeQuery("SELECT auswahloption_id AS ao_id "
//						+ "FROM t_auswahlinfo WHERE nutzerprofil_id=" + profilId
//						+ " AND eigenschaft_id=" + eigenschaftId);
//				
//				// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein.
//				if(rs.next()) {
//					auswahloptionId = rs.getInt("ao_id"); 
//					
//		  stmt = con.createStatement();
//
//			stmt.executeUpdate("UPDATE t_auswahlinfo "
//					+ "SET auswahloption_id=" + neueAuswahloptionId
//							+ " WHERE auswahloption_id=" + auswahloptionId
//                            + " AND nutzerprofil_id=" + profilId
//							+ " AND eigenschaft_id=" + eigenschaftId);
//							
//				}
//				} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//	}
//
//	
//	
//	
//	
//	public void updateInfoB(Beschreibungsinfo infoB) {
//		Connection con = DBConnection.connection();
//		
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt.executeUpdate("UPDATE t_beschreibungsinfo "
//					+ "SET infotext=\"" + infoB.getInfotext() + "\" "
//					+ "WHERE nutzerprofil_id=" + infoB.getNutzerprofilId()
//					+ " AND eigenschaft_id=" + infoB.getEigenschaftId());
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//
//	}

	

//	/**
//	 * Gesamte Info löschen.
//	 */
//	public void deleteAllInfos(int profilId) {
//		Connection con = DBConnection.connection();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt = con.createStatement();
//			stmt.executeUpdate("DELETE FROM t_beschreibungsinfo WHERE nutzerprofil_id="
//					+ profilId);
//
//			stmt = con.createStatement();
//			stmt.executeUpdate("DELETE FROM t_auswahlinfo WHERE nutzerprofil_id="
//					+ profilId);
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//	}
//
//	
//	/**
//	 * Eine gesamte Beschreibungsinfo löschen.
//	 */
//	public void deleteOneInfoB(int profilId, int eigenschaftId) {
//		Connection con = DBConnection.connection();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt = con.createStatement();
//			stmt.executeUpdate("DELETE FROM t_beschreibungsinfo WHERE nutzerprofil_id="
//					+ profilId + " AND eigenschaft_id=" + eigenschaftId);
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//	}
//
//	
//	/**
//	 * Eine gesamte Auswahlinfo löschen.
//	 */
//	public void deleteOneInfoA(int profilId, int eigenschaftId) {
//		Connection con = DBConnection.connection();
//
//		try {
//			Statement stmt = con.createStatement();
//
//			stmt = con.createStatement();
//			stmt.executeUpdate("DELETE FROM t_auswahlinfo WHERE nutzerprofil_id="
//					+ profilId + " AND eigenschaft_id=" + eigenschaftId);
//
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//	}

	
	
}
