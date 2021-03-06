package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Mapper-Klasse, die Merkliste-Objekte auf eine relationale Datenbank abbildet. 
 * Das Mapping ist bidirektional, d.h. Objekte koennen in DB-Strukturen und DB-Strukturen in 
 * Objekte umgewandelt werden. 
 */
public class MerklisteMapper {

	/**
	   * Die Klasse MerklisteMapper wird nur einmal instantiiert (Singelton). 
	   * Diese Variable ist durch den Bezeichner static nur einmal fuer saemtliche 
	   * eventuellen Instanzen dieser Klasse vorhanden, sie speichert die einzige
	   * Instanz dieser Klasse.
	   * 
	   * @see #merklisteMapper()
	   */
	private static MerklisteMapper merklisteMapper = null;

	/**
	 * Geschuetzter Konstruktor, der verhinder, mit new neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected MerklisteMapper() {
	}

	 /**
	   * Diese statische Methode kann aufgrufen werden durch MerklisteMapper.merklisteMapper(). 
	   * Sie stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von MerklisteMapper existiert.

	   * @return MerklisteMapper-Objekt
	   * @see merklisteMapper
	   */
	public static MerklisteMapper merklisteMapper() {
		if (merklisteMapper == null) {
			merklisteMapper = new MerklisteMapper();
		}
		return merklisteMapper;
	}

	/**
	 * Alle gemerkten Nutzerprofil-Objekte eines Nutzerprofils auslesen.
	 * @param 	profilId Die Profil-ID des Nutzerprofils, fuer das die gemerkten Nutzerprofil-Objekte
	 * 			ausgelesen werden sollen. 
	 * @return 	Liste von gemerkten Nutzerprofil-Objekten.
	 */
	public List<Nutzerprofil> findGemerkteNutzerprofileFor(int profilId) {
		Connection con = DBConnection.connection();

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT t_nutzerprofil1.nutzerprofil_id, t_nutzerprofil1.vorname, t_nutzerprofil1.nachname, "
							+ "t_nutzerprofil1.geburtsdatum, t_profil1.geschlecht "
							+ "FROM t_nutzerprofil1, t_profil1, t_vermerk1 " + "WHERE t_vermerk1.nutzerprofil_id="
							+ profilId + " AND t_nutzerprofil1.nutzerprofil_id = t_vermerk1.fremdprofil_id "
							+ "AND t_profil1.profil_id = t_vermerk1.fremdprofil_id");

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
	 * Vermerkstatus pruefen.
	 * @param 	profilId Die Profil-ID des eigenen Nutzerprofils.
	 * @param 	fremdprofilId Die Profil-ID des Nutzerprofils, das auf die Existenz eines Vermerks ueberprueft werden soll. 
	 * @return Status, der angibt, ob bereits ein Vermerk vorliegt oder nicht.
	 */
	public int pruefeVermerk(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		int vermerkstatus = 0;

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_vermerk1 " + "WHERE nutzerprofil_id=" + profilId
					+ " AND fremdprofil_id=" + fremdprofilId);

			if (rs.next()) {
				vermerkstatus = 1;
			} else {
				vermerkstatus = 0;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return vermerkstatus;
	}


    /**
     * Vermerk in die Datenbank einfuegen.
     * @param profilId Die Profil-ID des eigenen Nutzerprofils. 
     * @param fremdprofilId Die Profil-ID des Nutzerprofils, das vermerkt werden soll. 
     */
	public void insertVermerk(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO t_vermerk1 (nutzerprofil_id, fremdprofil_id) " + "VALUES (" + profilId + ","
					+ fremdprofilId + ")");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Vermerk aus der Datenbank loeschen.
	 * @param profilId Die Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Die Profil-ID des Nutzerprofils, dessen Vermerk geloescht werden soll. 
	 */
	public void deleteVermerk(int profilId, int fremdprofilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"DELETE FROM t_vermerk1 WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id=" + fremdprofilId);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

}
