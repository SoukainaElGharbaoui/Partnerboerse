package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;

public class SperrlisteMapper {
	
	private static SperrlisteMapper sperrlisteMapper = null;

	// Konstruktor
	protected SperrlisteMapper() {
	}

	public static SperrlisteMapper sperrlisteMapper() {
		if (sperrlisteMapper == null) {
			sperrlisteMapper = new SperrlisteMapper();
		}
		return sperrlisteMapper;
	}
	
	/**
	 * Alle Sperrungen eines Nutzerprofils auslesen.
	 */
	public Vector<Sperrliste> findAllSperrungenFor(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste
		Vector<Sperrliste> result = new Vector<Sperrliste>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_sperrung.fremdprofil_id, t_nutzerprofil.vorname, "
							+ "t_nutzerprofil.nachname, t_nutzerprofil.geburtsdatum, t_profil.geschlecht FROM t_sperrung, t_nutzerprofil, t_profil  "
							+ "WHERE t_sperrung.nutzerprofil_id="
							+ profilId
							+ " AND t_nutzerprofil.nutzerprofil_id = t_sperrung.fremdprofil_id "
							+ "AND t_profil.profil_id = t_sperrung.fremdprofil_id");

			// Für jeden Eintrag im Suchergebnis wird nun ein Eintrag im Vektor der Sperrliste erstellt.
			while (rs.next()) {
				Sperrliste s = new Sperrliste();
				s.setsFremdprofilId(rs.getInt("fremdprofil_id"));
				s.setsVorname(rs.getString("vorname"));
				s.setsNachname(rs.getString("nachname"));
				s.setsGeburtsdatum(rs.getString("geburtsdatum"));
				s.setsGeschlecht(rs.getString("geschlecht"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor.
				result.addElement(s);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Sperrung löschen.
	 */
	public void deleteSperrung(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_sperrung WHERE nutzerprofil_id=" + profilId
					+ " AND fremdprofil_id=" + fremdprofilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

}
