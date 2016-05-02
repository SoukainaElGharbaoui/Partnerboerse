package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Mapper-Klasse, die <code>Nutzerprofil</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur VerfÃ¼gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelÃ¶scht werden kÃ¶nnen. Das Mapping ist bidirektional. D.h., Objekte
 * kÃ¶nnen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @see ***
 * @author Soukaina ElGharbaoui
 */

public class NutzerprofilMapper {

	/**
	 * Die Klasse NutzerprofilMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fÃ¼r sÃ¤mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see nutzerprofilMapper()
	 */

	private static NutzerprofilMapper nutzerprofilMapper = null;

	/**
	 * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected NutzerprofilMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>NutzerprofilMapper.nutzerprofilMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafÃ¼r sorgt, dass nur eine
	 * einzige Instanz von <code>NutzerprofilMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> NutzerprofilMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>NutzerprofilMapper</code>-Objekt.
	 * @see nutzerprofilMapper
	 */
	public static NutzerprofilMapper nutzerprofilMapper() {
		if (nutzerprofilMapper == null) {
			nutzerprofilMapper = new NutzerprofilMapper();
		}

		return nutzerprofilMapper;
	}

	/**
	 * Suchen eines Nutzerprofils mit vorgegebener Nutzerprofil ID. Da diese
	 * eindeutig ist, wird genau ein Objekt zurï¿½ckgegeben.
	 * 
	 * @param id
	 *            PrimÃ¤rschlÃ¼sselattribut (->DB)
	 * @return Nutzerprofil-Objekt, das dem Ã¼bergebenen SchlÃ¼ssel entspricht,
	 *         null bei nicht vorhandenem DB-Tupel.
	 */
	public Nutzerprofil findByNutzerprofilId(int profilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT profil_id, vorname, nachname FROM t_nutzerprofil "
							+ "WHERE profil_id=" + profilId);

			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("profil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				return nutzerprofil;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Auslesen aller Nutzerprofile.
	 * 
	 * @return Eine Liste mit Nutzerprofil-Objekten, die sÃ¤mtliche
	 *         Nutzerprofile reprÃ¤sentieren. Bei evtl. Exceptions wird eine
	 *         partiell gefÃ¼llte oder ggf. auch eine leere Liste
	 *         zurÃ¼ckgeliefert.
	 */
	public List<Nutzerprofil> findAllNutzerprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisliste vorbereiten
		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_profil INNER JOIN"
							+ "t_nutzerprofil ON t_profil.profil_id = "
							+ "t_nutzerprofil.profil_id ORDER BY profil_id");

			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
			// Nutzerprofil-Objekt erstellt.
			while (rs.next()) {
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("profil_id"));
				nutzerprofil.setVorname(rs.getString("vorname"));
				nutzerprofil.setNachname(rs.getString("nachname"));
				nutzerprofil.setGeburtsdatum(rs.getDate("geburtsdatum"));
				nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
				nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
				nutzerprofil.setKoerpergroesse(rs.getString("koerpergroesse"));
				nutzerprofil.setRaucher(rs.getString("raucher"));
				nutzerprofil.setReligion(rs.getString("religion"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(nutzerprofil);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param nutzerprofil
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter Ã¼bergebene Objekt
	 */
	public Nutzerprofil updateNutzerprofil(Nutzerprofil nutzerprofil) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_nutzerprofil INNER JOIN t_profil"
					+ "ON t_nutzerprofil.profil_id = t_profil.profil_id"
					+ "SET vorname=\", nachname=\", geburtsdatum=\""
					+ "koerpergroesse=\", raucher=\", geschlecht=\", haarfarbe=\""
					+ "WHERE profil_id=" + nutzerprofil.getProfilId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Nutzerprofil nutzerprofil) zu wahren,
		// geben wir nuterprofil zurÃ¼ck
		return nutzerprofil;
	}

	/**
	 * Lï¿½schen der Daten eines <code>Nutzerprofil</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param nutzerprofil
	 *            das aus der DB zu lï¿½schende "Objekt"
	 */
	public void deleteNutzerprofil(Nutzerprofil nutzerprofil) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_nutzerprofil "
					+ "WHERE profil_id =" + nutzerprofil.getProfilId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Einfï¿½gen eines <code>Nutzerprofil</code>-Objekts in die Datenbank.
	 * Dabei wird auch der Primï¿½rschlï¿½ssel des ï¿½bergebenen Objekts
	 * geprï¿½ft und ggf. berichtigt.
	 * 
	 * @param nutzerprofil
	 *            das zu speichernde Objekt
	 * @return das bereits ï¿½bergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>profilId</code>.
	 */
	public Nutzerprofil insertNutzerprofil(Nutzerprofil nutzerprofil) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunï¿½chst schauen wir nach, welches der momentan hï¿½chste
			 * Primï¿½rschlï¿½sselwert ist.
			 */
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(profil_id) AS maxProfil_id "
							+ "FROM t_nutzerprofil");

			// Wenn wir etwas zurï¿½ckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * np erhï¿½lt den bisher maximalen, nun um 1 inkrementierten
				 * Primï¿½rschlï¿½ssel.
				 */
				nutzerprofil.setProfilId(rs.getInt("maxProfil_id") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsï¿½chliche Einfï¿½geoperation
				stmt.executeUpdate("INSERT INTO t_nutzerprofil (profil_id, "
						+ "vorname, nachname, geburtsdatum)" + "VALUES("
						+ nutzerprofil.getProfilId() + ",'"
						+ nutzerprofil.getVorname() + "','"
						+ nutzerprofil.getNachname() + "','"
						+ nutzerprofil.getGeburtsdatum() + "')");

				stmt.executeUpdate("INSERT INTO t_profil (profil_id, "
						+ "geschlecht, haarfarbe, koerpergroesse, raucher)"
						+ "VALUES('" + nutzerprofil.getGeschlecht() + "','"
						+ nutzerprofil.getHaarfarbe() + "','"
						+ nutzerprofil.getKoerpergroesse() + "','"
						+ nutzerprofil.getGeschlecht() + "')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Rï¿½ckgabe, des evtl. korrigierten Nutzerprofils.
		 */
		return nutzerprofil;
	}

}
