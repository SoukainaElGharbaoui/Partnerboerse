package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class NutzerprofilMapper {

	private static NutzerprofilMapper nutzerprofilMapper = null;

	protected NutzerprofilMapper() {
	}

	public static NutzerprofilMapper nutzerprofilMapper() {
		if (nutzerprofilMapper == null) {
			nutzerprofilMapper = new NutzerprofilMapper();
		}
		return nutzerprofilMapper;
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Nutzerprofil
	 * ***************************************************************************
	 */
	
	/**
	 * Nutzerprofil-Objekt in die Datenbank einfügen.
	 */
	public Nutzerprofil insertNutzerprofil(Nutzerprofil n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			// Größte profil_id aus Tabelle t_profil ermitteln.
			ResultSet rs = stmt.executeQuery("SELECT MAX(profil_id) AS maxprofil_id FROM t_profil");

			// Wenn wir etwas zurückerhalten...
			if (rs.next()) {

				// Nutzerprofil-Objekt mit bisher maximalem, nun um 1 inkrementierten Primärschlüssel versehen. 
				n.setProfilId(rs.getInt("maxprofil_id") + 1);

				// Tabelle t_profil befüllen.
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_profil (profil_id, geschlecht, haarfarbe, koerpergroesse, raucher, religion) "
						+ "VALUES(" + n.getProfilId() + ",'" + n.getGeschlecht() + "','" + n.getHaarfarbe() + "','"
						+ n.getKoerpergroesseInt() + "','" + n.getRaucher() + "','" + n.getReligion() + "')");

				// Tablle t_nutzerprofil befüllen.
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_nutzerprofil (nutzerprofil_id, vorname, nachname, geburtsdatum) "
						+ "VALUES(" + n.getProfilId() + ",'" + n.getVorname() + "','" + n.getNachname() + "','"
						+ n.getGeburtsdatumDate() + "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/*
		 * Nutzerprofil-Objekt zurückgeben. 
		 */
		return n;
	}

	/**
	 * Nutzerprofil-Objekt wiederholt in die Datenbank schreiben.
	 */
	public void updateNutzerprofil(Nutzerprofil n) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE t_nutzerprofil " + "SET vorname=\""
					+ n.getVorname() + "\", " + " nachname=\"" + n.getNachname() + "\", "
					+ " geburtsdatum=\"" + n.getGeburtsdatumDate() + "\" "
					+ "WHERE nutzerprofil_id=" + Benutzer.getProfilId());

			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_profil " + "SET geschlecht=\""
					+ n.getGeschlecht() + "\", " + " haarfarbe=\"" + n.getHaarfarbe()
					+ "\", " + " koerpergroesse=\"" + n.getKoerpergroesseInt() + "\", "
					+ "raucher=\"" + n.getRaucher() + "\", " + " religion=\""
					+ n.getReligion() + "\" " + "WHERE profil_id="
					+ Benutzer.getProfilId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Nutzerprofil-Objekt aus der Datenbank löschen.
	 */
	public void deleteNutzerprofil(int profilId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_nutzerprofil "
						+ "WHERE nutzerprofil_id=" + profilId);

				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil WHERE profil_id=" + profilId);
				
				int suchprofilIdInt = 0; 
				
				ResultSet rs = stmt.executeQuery("SELECT suchprofil_id AS sp_id "
						+ "FROM t_suchprofil WHERE nutzerprofil_id=" + profilId);
				
				if(rs.next()) {
					suchprofilIdInt = rs.getInt("sp_id");
				
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_suchprofil "
						+ "WHERE nutzerprofil_id=" + profilId);
				
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil WHERE profil_id=" + suchprofilIdInt);					
				}
				
				stmt = con.createStatement();
				stmt =con.createStatement();
				stmt.executeUpdate("DELETE FROM t_vermerk "
						+ "WHERE nutzerprofil_id=" + profilId 
						+ " OR fremdprofil_id=" + profilId);
				
				stmt =con.createStatement();
				stmt.executeUpdate("DELETE FROM t_sperrung "
						+ "WHERE nutzerprofil_id=" + profilId 
						+ " OR fremdprofil_id=" + profilId);
				
				stmt =con.createStatement();
				stmt.executeUpdate("DELETE FROM t_besuch "
						+ "WHERE nutzerprofil_id=" + profilId 
						+ " OR fremdprofil_id=" + profilId);
				
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_beschreibungsinfo "
						+ "WHERE nutzerprofil_id=" + profilId);

				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_auswahlinfo "
						+ "WHERE nutzerprofil_id=" + profilId);


		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Nutzerprofil mit vorgegebener Profil-ID suchen. 
	 */
	public Nutzerprofil findByNutzerprofilId(int profilId) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_nutzerprofil, t_profil " 
			+ "WHERE profil_id= " + profilId + " AND nutzerprofil_id=" + profilId);

			/*
			 * Es kann max. ein Ergebnis-Tupel zurückgegeben werden.
			 * Prüfen, ob ein Ergebnis-Tupel vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Nutzerprofil-Objekt umwandeln.
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));
				n.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
				n.setHaarfarbe(rs.getString("haarfarbe"));
				n.setRaucher(rs.getString("raucher"));
				n.setReligion(rs.getString("religion"));
				return n;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * ***********************************
	 * Unnötig, da gleicher Mapper-Aufruf!
	 * ***********************************
	 */
	public Nutzerprofil findByFremdprofilId(int fremdprofilId) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(

			"SELECT * FROM t_nutzerprofil, t_profil " + "WHERE nutzerprofil_id="
					+ fremdprofilId + " AND profil_id=" + fremdprofilId);

			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel
			 * zurÃ¼ckgegeben werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
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
	 * @param nutzerprofil 

	 */
	public Vector<Nutzerprofil> findAllNutzerprofile() {
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten.
		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM t_profil, t_nutzerprofil "
							+ "WHERE t_profil.profil_id = t_nutzerprofil.nutzerprofil_id "
							+ "ORDER BY nutzerprofil_id");

			// Für jedes Ergebnis-Tupel ein Nutzerprofil-Objekt erstellen.
			while (rs.next()) {
				Nutzerprofil n = new Nutzerprofil();
				n.setProfilId(rs.getInt("nutzerprofil_id"));
				n.setVorname(rs.getString("vorname"));
				n.setNachname(rs.getString("nachname"));
				n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
				n.setGeschlecht(rs.getString("geschlecht"));
				n.setHaarfarbe(rs.getString("haarfarbe"));
				n.setKoerpergroesse(rs.getString("koerpergroesse"));
				n.setRaucher(rs.getString("raucher"));
				n.setReligion(rs.getString("religion"));

				// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
				result.add(n);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisliste zurÃ¼ckgeben
		return result;

	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Nutzerprofil
	 * ***************************************************************************
	 */
		
		/*
		 * ***************************************************************************
		 * ABSCHNITT, Beginn: Partnervorschläge
		 * ***************************************************************************
		 */

		/**
		 * Alle unangesehenen Nutzerprofile auslesen.
		 */
		public List<Nutzerprofil> findUnangeseheneNutzerprofile(int profilId) {
			Connection con = DBConnection.connection();

			// Ergebnisliste vorbereiten
			List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

			try {
				Statement stmt = con.createStatement();

				ResultSet rs = stmt
						.executeQuery(							
								"SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname, t_nutzerprofil.geburtsdatum, "
								+ "t_profil.geschlecht, t_profil.koerpergroesse, t_profil.haarfarbe, t_profil.raucher, t_profil.religion "
								+ "FROM t_nutzerprofil LEFT JOIN t_besuch ON t_nutzerprofil.nutzerprofil_id = t_besuch.fremdprofil_id "
								+ "LEFT JOIN t_profil ON t_nutzerprofil.nutzerprofil_id = t_profil.profil_id "
								+ "LEFT JOIN t_sperrung ON t_nutzerprofil.nutzerprofil_id = t_sperrung.nutzerprofil_id "
								+ "WHERE t_nutzerprofil.nutzerprofil_id !=" + profilId
								+ " AND (t_besuch.nutzerprofil_id !=" + profilId
								+ " OR t_besuch.fremdprofil_id IS NULL) "
								+ "AND (t_sperrung.fremdprofil_id !=" + profilId
								+ " OR t_sperrung.nutzerprofil_id IS NULL) ORDER BY t_nutzerprofil.nutzerprofil_id");


				// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
				// Nutzerprofil-Objekt erstellt.
				while (rs.next()) {
					Nutzerprofil nutzerprofil = new Nutzerprofil();
					nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
					nutzerprofil.setVorname(rs.getString("vorname"));
					nutzerprofil.setNachname(rs.getString("nachname"));
					nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
					nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
					nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
					nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
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
			 * Besuch setzen. 
			 */
			public void insertBesuch(int profilId, int fremdprofilId) { 
				Connection con = DBConnection.connection();
				
				try {
					Statement stmt = con.createStatement();

					stmt.executeUpdate("INSERT INTO t_besuch (nutzerprofil_id, fremdprofil_id) " + "VALUES (" 
					+ profilId + "," + fremdprofilId + ")");

				} catch (SQLException e2) {
					e2.printStackTrace();
				}	
			}
		
			
			/**
			 * *******************
			 * Brauchen wir das???
			 * *******************
			 */
			public int pruefeBesuch(int profilId, int fremdprofilId) {
				Connection con = DBConnection.connection();
				
				// Ergebnisvariable (Ausgang: Es liegt kein Besuch vor.)
				int besuchstatus = 0; 
				
				try {
					Statement stmt = con.createStatement();
					
					ResultSet rs = stmt.executeQuery("SELECT * FROM t_besuch "
							+ "WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id=" + fremdprofilId);
					
					if (rs.next()) {
				        // Es liegt ein Besuch vor.  
						besuchstatus = 1; 
				      } else {
				    	  // Es liegt kein Besuch vor. 
				    	  besuchstatus = 0; 
				      }
					
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				return besuchstatus; 
			}
			
			/**
			 * Aehnlichkeit hinzufuegen. 
			 */
			public void insertAehnlichkeit(int profilId, int fremdprofilId, int aehnlichkeit) { 
				Connection con = DBConnection.connection();
				
				try {
					Statement stmt = con.createStatement();

					stmt.executeUpdate("INSERT INTO t_aehnlichkeitnp (nutzerprofil_id, fremdprofil_id, aehnlichkeit) " + "VALUES (" 
					+ profilId + "," + fremdprofilId + "," + aehnlichkeit + ")");

				} catch (SQLException e2) {
					e2.printStackTrace();
				}	
			}
			
			
			/**
			 * Geordnete Partnervorschlaege ausgeben
			 */
			public List<Nutzerprofil> findGeordnetePartnervorschlaegeNp(int profilId) {
				Connection con = DBConnection.connection();

				// Ergebnisliste vorbereiten
				List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

				try {
					Statement stmt = con.createStatement();

					ResultSet rs = stmt
							.executeQuery(							
									"SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname, "
									+ "t_nutzerprofil.geburtsdatum, t_profil.geschlecht, t_profil.koerpergroesse, "
									+ "t_profil.haarfarbe, t_profil.raucher, t_profil.religion , t_aehnlichkeitnp.aehnlichkeit "
									+ "FROM t_nutzerprofil LEFT JOIN t_besuch "
									+ "ON t_nutzerprofil.nutzerprofil_id = t_besuch.fremdprofil_id "
									+ "LEFT JOIN t_profil ON t_nutzerprofil.nutzerprofil_id = t_profil.profil_id "
									+ "LEFT JOIN t_sperrung ON t_nutzerprofil.nutzerprofil_id = t_sperrung.nutzerprofil_id "
									+ "LEFT JOIN t_aehnlichkeitnp ON t_nutzerprofil.nutzerprofil_id = t_aehnlichkeitnp.fremdprofil_id "
									+ "WHERE t_nutzerprofil.nutzerprofil_id != 1 "
									+ "AND (t_besuch.nutzerprofil_id != 1 OR t_besuch.fremdprofil_id IS NULL) "
									+ "AND (t_sperrung.fremdprofil_id != 1 OR t_sperrung.nutzerprofil_id IS NULL) "
									+ "AND t_aehnlichkeitnp.nutzerprofil_id = 1 ORDER BY t_aehnlichkeitnp.aehnlichkeit DESC");


					// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
					// Nutzerprofil-Objekt erstellt.
					while (rs.next()) {
						Nutzerprofil nutzerprofil = new Nutzerprofil();
						nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
						nutzerprofil.setVorname(rs.getString("vorname"));
						nutzerprofil.setNachname(rs.getString("nachname"));
						nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
						nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
						nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
						nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
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
			 * Aehnlichkeit loeschen.
			 */
			public void deleteAehnlichkeit(int profilId) {
				Connection con = DBConnection.connection();

				try {
					Statement stmt = con.createStatement();

					stmt.executeUpdate("DELETE FROM t_aehnlichkeitnp WHERE nutzerprofil_id=" + profilId);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}

			}
			/*
			 * ***************************************************************************
			 * ABSCHNITT, Ende: Partnervorschläge
			 * ***************************************************************************
			 */
			
			/**
			 * Alle Nutzerprofile die mich nicht gesperrt haben
			 */
			
			public List<Nutzerprofil> findNutzerprofileOhneGesetzeSperrung(int profilId){
				
				Connection con = DBConnection.connection();

				// Ergebnisliste vorbereiten
				List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();
				
				try {
					Statement stmt = con.createStatement();
					
					ResultSet rs = stmt
							.executeQuery("SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, "
									+ "t_nutzerprofil.nachname, t_nutzerprofil.geburtsdatum, t_profil.geschlecht, "
									+ "t_profil.koerpergroesse, t_profil.haarfarbe, t_profil.raucher, t_profil.religion "
									+ "FROM t_nutzerprofil LEFT JOIN t_profil ON t_nutzerprofil.nutzerprofil_id = t_profil.profil_id "
									+ "LEFT JOIN t_sperrung ON t_nutzerprofil.nutzerprofil_id = t_sperrung.nutzerprofil_id "
									+ "WHERE t_nutzerprofil.nutzerprofil_id !=" + profilId + " AND (t_sperrung.fremdprofil_id !=" + profilId
									+ "OR t_sperrung.nutzerprofil_id IS NULL) ORDER BY t_nutzerprofil.nutzerprofil_id" );
					
					while (rs.next()){
						
						Nutzerprofil nutzerprofil = new Nutzerprofil();
						nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
						nutzerprofil.setVorname(rs.getString("vorname"));
						nutzerprofil.setNachname(rs.getString("nachname"));
						nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
						nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
						nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
						nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
						nutzerprofil.setRaucher(rs.getString("raucher"));
						nutzerprofil.setReligion(rs.getString("religion"));

						// HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
						result.add(nutzerprofil);
						
						
					}
				} catch (SQLException e2){
					e2.printStackTrace();
				}
				
				// Ergebnisliste zurÃ¼ckgeben
				return result;
			}
			
			
			/**
			 * Geordnete Partnervorschlaege ausgeben
			 */
			public List<Nutzerprofil> findGeordnetePartnervorschlaegeSp(int profilId) {
				Connection con = DBConnection.connection();

				// Ergebnisliste vorbereiten
				List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

				try {
					Statement stmt = con.createStatement();

					ResultSet rs = stmt
							.executeQuery(							
									"SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname,"
									+ " t_nutzerprofil.geburtsdatum, t_profil.geschlecht, t_profil.koerpergroesse, t_profil.haarfarbe,"
									+ " t_profil.raucher, t_profil.religion , t_aehnlichkeitsp.aehnlichkeit FROM t_nutzerprofil "
									+ "LEFT JOIN t_profil ON t_nutzerprofil.nutzerprofil_id = t_profil.profil_id LEFT JOIN t_sperrung"
									+ " ON t_nutzerprofil.nutzerprofil_id = t_sperrung.nutzerprofil_id LEFT JOIN t_aehnlichkeitsp"
									+ " ON t_nutzerprofil.nutzerprofil_id = t_aehnlichkeitsp.fremdprofil_id"
									+ " WHERE t_nutzerprofil.nutzerprofil_id != 1 AND (t_sperrung.fremdprofil_id != 1 "
									+ "OR t_sperrung.nutzerprofil_id IS NULL) AND t_aehnlichkeitsp.suchprofil_id = 1"
									+ " ORDER BY t_aehnlichkeitsp.aehnlichkeit DESC" );


					// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein
					// Nutzerprofil-Objekt erstellt.
					while (rs.next()) {
						Nutzerprofil nutzerprofil = new Nutzerprofil();
						nutzerprofil.setProfilId(rs.getInt("nutzerprofil_id"));
						nutzerprofil.setVorname(rs.getString("vorname"));
						nutzerprofil.setNachname(rs.getString("nachname"));
						nutzerprofil.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
						nutzerprofil.setGeschlecht(rs.getString("geschlecht"));
						nutzerprofil.setHaarfarbe(rs.getString("haarfarbe"));
						nutzerprofil.setKoerpergroesseInt(rs.getInt("koerpergroesse"));
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
		
}

