package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

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

		// Ergebnisvariable, d.h. die NutzerprofilId
		int nutzerprofilIdInt = 0;

		try {

			Statement stmt = con.createStatement();

			// Holen der zu löschenden NutzerprofilId aus der Tabelle
			// t_nutzerprofil
			ResultSet rs = stmt
					.executeQuery("SELECT nutzerprofil_id AS np_id "
							+ "FROM t_nutzerprofil WHERE t_nutzerprofil.nutzerprofil_id="
							+ profilId);

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein.
			if (rs.next()) {
				nutzerprofilIdInt = rs.getInt("np_id");

				// Löschen der Daten in der Tabelle t_nutzerprofil mit der
				// entsprechenden ProfilId
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_nutzerprofil "
						+ "WHERE t_nutzerprofil.nutzerprofil_id=" + profilId);

				// Löschen der Daten in der Tabelle t_profil mit der
				// entsprechenden NutzerprofilId
				stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM t_profil WHERE t_profil.profil_id="
						+ nutzerprofilIdInt);

			}

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
	 * Alle Nutzerprofile auslesen.
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
	 * ABSCHNITT, Beginn: Merkliste
	 * ***************************************************************************
	 */
		
	 /**
	  * Alle Vermerke eines Nutzerprofils auslesen.
	  */
	 public Vector<Nutzerprofil> findGemerkteNutzerprofileFor(int profilId) {
			Connection con = DBConnection.connection();
			
			Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();
			
			try {
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname, "
						+ "t_nutzerprofil.geburtsdatum, t_profil.geschlecht "
						+ "FROM t_nutzerprofil, t_profil, t_vermerk "
						+ "WHERE t_vermerk.nutzerprofil_id=" + profilId 
						+ " AND t_nutzerprofil.nutzerprofil_id = t_vermerk.fremdprofil_id "
						+ "AND t_profil.profil_id = t_vermerk.fremdprofil_id"); 

				while (rs.next()) {
					Nutzerprofil n = new Nutzerprofil();
					n.setProfilId(rs.getInt("nutzerprofil_id")); 
					n.setVorname(rs.getString("vorname"));
					n.setNachname(rs.getString("nachname"));
					n.setGeburtsdatumDate(rs.getDate("geburtsdatum"));
					n.setGeschlecht(rs.getString("geschlecht"));
					
					// Hinzufügen des neuen Objekts zum Ergebnisvektor
					result.addElement(n);
				}
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			return result;
		}
		
		/**
		 * Vermerkstatus ermitteln. 
		 */
		public int pruefeVermerk(int profilId, int fremdprofilId) {
			Connection con = DBConnection.connection();
			
			// Ergebnisvariable (Ausgang: Es liegt kein Vermerk vor.)
			int vermerkstatus = 0; 
			
			try {
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM t_vermerk "
						+ "WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id=" + fremdprofilId);
				
				if (rs.next()) {
			        // Es liegt ein Vermerk vor.  
					vermerkstatus = 1; 
			      } else {
			    	  // Es liegt kein Vermerk vor. 
			    	  vermerkstatus = 0; 
			      }
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			return vermerkstatus; 
		}
		
		/**
		 * Vermerk einfügen. 
		 */
		public void insertVermerk(int profilId, int fremdprofilId) { 
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO t_vermerk (nutzerprofil_id, fremdprofil_id) " + "VALUES (" 
				+ profilId + "," + fremdprofilId + ")");

			} catch (SQLException e2) {
				e2.printStackTrace();
			}	
		}
		
		/**
		 * Vermerk löschen.
		 */
		public void deleteVermerk(int profilId, int fremdprofilId) {
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE FROM t_vermerk WHERE nutzerprofil_id=" + profilId
						+ " AND fremdprofil_id=" + fremdprofilId);

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

		}
		
		/*
		 * ***************************************************************************
		 * ABSCHNITT, Ende: Merkliste
		 * ***************************************************************************
		 */
		
		/*
		 * ***************************************************************************
		 * ABSCHNITT, Beginn: Sperrliste
		 * ***************************************************************************
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

				ResultSet rs = stmt.executeQuery("SELECT t_nutzerprofil.nutzerprofil_id, t_nutzerprofil.vorname, t_nutzerprofil.nachname, "
						+ "t_nutzerprofil.geburtsdatum, t_profil.geschlecht "
						+ "FROM t_nutzerprofil, t_profil, t_sperrung "
						+ "WHERE t_sperrung.nutzerprofil_id=" + profilId 
						+ " AND t_nutzerprofil.nutzerprofil_id = t_sperrung.fremdprofil_id "
						+ "AND t_profil.profil_id = t_sperrung.fremdprofil_id"); 

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
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM t_sperrung "
						+ "WHERE nutzerprofil_id=" + profilId + " AND fremdprofil_id=" + fremdprofilId);
				
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
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM t_sperrung "
						+ "WHERE nutzerprofil_id=" + fremdprofilId + " AND fremdprofil_id=" + profilId); 
				
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

				stmt.executeUpdate("INSERT INTO t_sperrung (nutzerprofil_id, fremdprofil_id) " + "VALUES (" 
				+ profilId + "," + fremdprofilId + ")");

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

				stmt.executeUpdate("DELETE FROM t_sperrung WHERE nutzerprofil_id=" + profilId
						+ " AND fremdprofil_id=" + fremdprofilId);

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

		}
		
		/*
		 * ***************************************************************************
		 * ABSCHNITT, Ende: Sperrliste
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
			
			/*
			 * ***************************************************************************
			 * ABSCHNITT, Ende: Partnervorschläge
			 * ***************************************************************************
			 */
		
		
}
