package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class SperrlisteMapper {

	private static SperrlisteMapper sperrlisteMapper = null;

	protected SperrlisteMapper() {
	}

	public static SperrlisteMapper sperrlisteMapper() {
		if (sperrlisteMapper == null) {
			sperrlisteMapper = new SperrlisteMapper();
		}
		return sperrlisteMapper;
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/**
	 * Alle Sperrungen eines Nutzerprofils auslesen.
	 */
	public Vector<Nutzerprofil> findGesperrteNutzerprofileFor(int profilId) {
		Connection con = DBConnection.connection();

		// Ergebnisliste
		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht "
							+ "FROM t_nutzerprofil1, t_profil1, t_sperrung1 " + "WHERE t_sperrung1.nutzerprofil_id="
							+ profilId + " AND t_nutzerprofil1.nutzerprofil_id = t_sperrung1.fremdprofil_id "
							+ "AND t_profil1.profil_id = t_sperrung1.fremdprofil_id");

			while (rs.next()) {
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor.
				result.addElement(n);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Prüfen, ob Fremdprofil von Benutzer gesperrt wurde.
	 */
	public int pruefeSperrungFremdprofil(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		// Ergebnisvariable (Ausgang: Es liegt keine Sperrung vor.)
		int sperrstatusFremdprofil = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_sperrung1 " + "WHERE nutzerprofil_id=" + profilId
					+ " AND fremdprofil_id=" + fremdprofilId);

			if (rs.next()) {
				// Es liegt eine Sperrung vor.
				sperrstatusFremdprofil = 1;
			} else {
				// Es liegt keine Sperrung vor.
				sperrstatusFremdprofil = 0;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return sperrstatusFremdprofil;
	}

	/**
	 * Prüfen, ob Benutzer von Fremdprofil gesperrt wurde.
	 */
	public int pruefeSperrungEigenesProfil(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		// Ergebnisvariable (Ausgang: Es liegt keine Sperrung vor.)
		int sperrstatusEigenesProfil = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_sperrung1 " + "WHERE nutzerprofil_id=" + fremdprofilId
					+ " AND fremdprofil_id=" + profilId);

			if (rs.next()) {
				// Es liegt eine Sperrung vor.
				sperrstatusEigenesProfil = 1;
			} else {
				// Es liegt keine Sperrung vor.
				sperrstatusEigenesProfil = 0;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return sperrstatusEigenesProfil;
	}

	/**
	 * Sperrung einfügen.
	 */
	public void insertSperrung(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_sperrung1 (nutzerprofil_id, fremdprofil_id) " + "VALUES (" + profilId + ","
					+ fremdprofilId + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Sperrung löschen.
	 */
	public void deleteSperrung(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_sperrung1 WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id="
					+ fremdprofilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */
}
