package de.hdm.gruppe7.partnerboerse.server.db;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AuswahloptionMapper {
	
	private static AuswahloptionMapper auswahloptionMapper = null;
	
	
	protected AuswahloptionMapper() {
		
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
	
	public static AuswahloptionMapper auswahloptionMapper() {
		if (auswahloptionMapper == null) {
			auswahloptionMapper = new AuswahloptionMapper();
		}
		return auswahloptionMapper;
	}
	
	public Auswahloption findByAuswahloptionId(int auswahloptionId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT auswahloption_id, eigenschaft_id, optionsbezeichnung FROM t_auswahloption " + "WHERE auswahloption_id=" + auswahloptionId);

			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Auswahloption auswahloption = new Auswahloption();
				Eigenschaft eigenschaft = new Eigenschaft();
				auswahloption.setAuswahloptionId(rs.getInt("auswahloption_id"));
				eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
				auswahloption.setOptionsbezeichnung(rs.getString("optionsbezeichnung"));
				return auswahloption;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	public List<Auswahloption> findAllAuswahloptionen() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Auswahloption> result = new ArrayList<Auswahloption>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_auswahloption ORDER BY auswahloption_id");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Auswahloption auswahloption = new Auswahloption();
				Eigenschaft eigenschaft = new Eigenschaft();
				auswahloption.setAuswahloptionId(rs.getInt("auswahloption_id"));
				eigenschaft.setEigenschaftId(rs.getInt("eigenschaft_id"));
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

	
	public Auswahloption insertAuswahloption(Auswahloption auswahloption) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			// Größte info_id ermitteln. 
			ResultSet rs = stmt.executeQuery("SELECT MAX(auswahloption_id) AS maxauswahloption_id " + "FROM t_auswahloption");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein.
			if (rs.next()) {	
			Eigenschaft eigenschaft = new Eigenschaft();
			
			// info erhaelt den bisher maximalen, nun um 1 inkrementierten Primärschlüssel. 
				auswahloption.setAuswahloptionId(rs.getInt("maxauswahloption_id") + 1);
				
				// Tabelle t_info befüllen 
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_auswahloption (auswahloption_id, eigenschaft_id, optionsbezeichnung) "
								+ "VALUES(" + auswahloption.getAuswahloptionId() + ",'"+ eigenschaft.getEigenschaftId() +",'"+ auswahloption.getOptionsbezeichnung() + "')");
			}
			
		}
		 catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Rückgabe der Info mit evtl. korrigierter InfoId. 
		 */
		return auswahloption;	
		}

	
		public Auswahloption updateAuswahloption(Auswahloption auswahloption) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE t_auswahloption"
							+ "SET optionsbezeichnung=\"WHERE auswahloption_id="
							+ auswahloption.getAuswahloptionId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Info info) zu wahren,
		// geben wir info zurÃ¼ck
		return auswahloption;
	}
	

	public void deleteInfo(Auswahloption auswahloption) {
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE FROM t_auswahloption " + "WHERE auswahloption_id =" + auswahloption.getAuswahloptionId());

			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	
}
