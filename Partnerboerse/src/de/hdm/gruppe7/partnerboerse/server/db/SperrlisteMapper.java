package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Mapper-Klasse, die <code>Sperrliste</code>-Objekte auf eine relationale
 * Datenbank abbildet. Das Mapping ist bidirektional, d.h. Objekte koennen in
 * DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 */

public class SperrlisteMapper {

	/**
	 * Die Klasse SperrlisteMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>. Diese Variable ist durch
	 * den Bezeichner <code>static</code> nur einmal für sämtliche eventuellen
	 * Instanzen dieser Klasse vorhanden. Sie speichert die einzige Instanz
	 * dieser Klasse.
	 * 
	 * @see #sperrlisteMapper()
	 */
	private static SperrlisteMapper sperrlisteMapper = null;

	/**
	 * Geschützter Konstruktor, der verhinder, mit <code>new</code> neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected SperrlisteMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>SperrlisteMapper.sperrlisteMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>SperrlisteMapper</code> existiert.
	 * <p>
	 * 
	 * @return <code>SperrlisteMapper</code>-Objekt
	 * @see sperrlisteMapper
	 */
	public static SperrlisteMapper sperrlisteMapper() {
		if (sperrlisteMapper == null) {
			sperrlisteMapper = new SperrlisteMapper();
		}
		return sperrlisteMapper;
	}

	/**
	 * Alle gesperrten Nutzerprofil-Objekte eines Nutzerprofils auslesen.
	 * @param 	profilId Die Profil-ID des Nutzerprofils, fuer das die gesperrten Nutzerprofil-Objekte
	 * 			ausgelesen werden sollen. 
	 * @return 	Liste von gesperrten Nutzerprofil-Objekten.
	 */
	public List<Nutzerprofil> findGesperrteNutzerprofileFor(int profilId) {
		Connection con = DBConnection.connection();

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht "
							+ "FROM t_nutzerprofil1, t_profil1, t_sperrung1 "
							+ "WHERE t_sperrung1.nutzerprofil_id="
							+ profilId
							+ " AND t_nutzerprofil1.nutzerprofil_id = t_sperrung1.fremdprofil_id "
							+ "AND t_profil1.profil_id = t_sperrung1.fremdprofil_id");

			while (rs.next()) {
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));

				result.add(n);

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Pruefen, ob ein Fremdprofil vom eigenen Nutzerprofil gesperrt wurde. 
	 * @param profilId Die Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Die Profil-ID des Fremdprofils, das auf die Existenz einer Sperrung ueberprueft werden soll. 
	 * @return Status, ob das Fremdprofil vom eigenen Nutzerprofil gesperrt wurde.
	 */
	public int pruefeSperrungFremdprofil(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		int sperrstatusFremdprofil = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_sperrung1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " AND fremdprofil_id=" + fremdprofilId);

			if (rs.next()) {
				sperrstatusFremdprofil = 1;
			} else {
				sperrstatusFremdprofil = 0;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return sperrstatusFremdprofil;
	}

	/**
	 * Pruefen, ob das eigene Nutzerprofil vom Fremdprofil gesperrt wurde. 
	 * @param profilId Die Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Die Profil-ID des Fremdprofils.
	 * @return Status, ob das eigene Nutzerprofil vom Fremdprofil gesperrt wurde.
	 */
	public int pruefeSperrungEigenesProfil(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		int sperrstatusEigenesProfil = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_sperrung1 "
					+ "WHERE nutzerprofil_id=" + fremdprofilId
					+ " AND fremdprofil_id=" + profilId);

			if (rs.next()) {
				sperrstatusEigenesProfil = 1;
			} else {
				sperrstatusEigenesProfil = 0;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return sperrstatusEigenesProfil;
	}

	/**
	 * Sperrung einfuegen. 
	 * @param profilId Die Profil-ID des eigenen Nutzerprofils.
	 * @param fremdprofilId Die Profil-ID des Fremdprofils, das gesperrt werden soll. 
	 */
	public void insertSperrung(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_sperrung1 (nutzerprofil_id, fremdprofil_id) "
					+ "VALUES (" + profilId + "," + fremdprofilId + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Sperrung loeschen.
	 * @param profilId Die Profil-ID des eigenen Nutzerprofils.
	 * @param fremdprofilId Die Profil-ID des Fremdprofils, dessen Sperrung geloescht werden soll.
	 */
	public void deleteSperrung(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_sperrung1 WHERE nutzerprofil_id="
					+ profilId + " AND fremdprofil_id=" + fremdprofilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

}
