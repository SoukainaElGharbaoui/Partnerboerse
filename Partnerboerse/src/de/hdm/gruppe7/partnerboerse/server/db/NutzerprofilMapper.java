package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.List;
import java.util.Vector;

import de.hdm.thies.bankProjekt.server.db.DBConnection;
import de.hdm.thies.bankProjekt.shared.bo.Account;




//import de.hdm.gruppe7.partnerboerse.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Nutzerprofil</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur VerfÃ¼gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelÃ¶scht werden kÃ¶nnen. Das Mapping ist bidirektional. D.h., Objekte kÃ¶nnen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @see ***
 * @author Soukaina ElGharbaoui
 */


public class NutzerprofilMapper {
	
	/**
	   * Die Klasse NutzerprofilMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fÃ¼r
	   * sÃ¤mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @see nutzerprofilMapper()
	   */
	
	 private static NutzerprofilMapper nutzerprofilMapper = null;

	  /**
	   * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected NutzerprofilMapper() {
	  }
	  
	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>NutzerprofilMapper.nutzerprofilMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafÃ¼r sorgt, dass nur eine einzige
	   * Instanz von <code>NutzerprofilMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> NutzerprofilMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
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
	   * Suchen eines Nutzerprofils mit vorgegebener Nutzerprofil ID. Da diese eindeutig ist,
	   * wird genau ein Objekt zurï¿½ckgegeben.
	   * 
	   * @param id PrimÃ¤rschlÃ¼sselattribut (->DB)
	   * @return Nutzerprofil-Objekt, das dem Ã¼bergebenen SchlÃ¼ssel entspricht, null bei
	   *         nicht vorhandenem DB-Tupel.
	   */
	  public Nutzerprofil findByNutzerprofilId(int profilId) {
	    // DB-Verbindung holen
	    Connection con = DBConnection.connection();

	    try {
	      // Leeres SQL-Statement (JDBC) anlegen
	      Statement stmt = con.createStatement();

	      // Statement ausfÃ¼llen und als Query an die DB schicken
	      ResultSet rs = stmt.executeQuery("SELECT Profil_ID, Vorname, Nachname FROM T_NUTZERPROFIL "
	          + "WHERE Profil_ID=" + profilId);

	      /*
	       * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel zurÃ¼ckgegeben
	       * werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
	       */
	      if (rs.next()) {
	        // Ergebnis-Tupel in Objekt umwandeln
	    	  Nutzerprofil np = new Nutzerprofil();
	        np.setProfilId(rs.getInt("Profil_ID"));
	        np.setVorname(rs.getString("Vorname"));
	        np.setNachname(rs.getString("Nachname"));
	        return np;
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	      return null;
	    }

	    return null;
	  }
	  
	  /**
	   * Auslesen aller Nutzerprofile.
	   * 
	   * @return Eine Liste mit Nutzerprofil-Objekten, die sÃ¤mtliche Nutzerprofile
	   *         reprÃ¤sentieren. Bei evtl. Exceptions wird eine partiell gefÃ¼llte
	   *         oder ggf. auch eine leere Liste zurÃ¼ckgeliefert.
	   */
	  public List<Nutzerprofil> findAllNutzerprofile() {
	    Connection con = DBConnection.connection();

	    // Ergebnisvektor vorbereiten
	    List<Nutzerprofil> result = new List<Nutzerprofil>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT Profil_ID, Vorname, Nachname FROM T_NUTZERPROFIL "
	          + " ORDER BY Profil_ID");

	      // FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Nutzerprofil-Objekt erstellt.
	      while (rs.next()) {
	        Nutzerprofil np = new Nutzerprofil();
	        np.setProfilId(rs.getInt("Profil_ID"));
	        np.setVorname(rs.getString("Vorname"));
	        np.setNachname(rs.getString("Nachname"));
	        
	        return np;

	        // HinzufÃ¼gen des neuen Objekts zur Ergebnisliste
	        result.addElement(np);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Ergebnisliste zurÃ¼ckgeben
	    return result;
	  }
	
	  /**
	   * Wiederholtes Schreiben eines Objekts in die Datenbank.
	   * 
	   * @param np das Objekt, das in die DB geschrieben werden soll
	   * @return das als Parameter Ã¼bergebene Objekt
	   */
	  public Nutzerprofil update(Nutzerprofil np) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE T_NUTZERPROFIL " + 
	      "SET Vorname=\", Nachname=\", Geburtsdatum=\""
	       + "WHERE Profil_ID=" + np.getProfilId()); 
	      
	      
	      stmt.executeUpdate("UPDATE T_PROFIL "+
	      "SET Koerpergroesse=\", Raucher=\", Geschlecht=\", Haarfarbe=\""
	       + "WHERE Profil_ID=" + np.getProfilId()); 

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Um Analogie zu insert(Nutzerprofil np) zu wahren, geben wir np zurÃ¼ck
	    return np;
	  }
	  
	  /**
	   * Löschen der Daten eines <code>Nutzerprofil</code>-Objekts aus der Datenbank.
	   * 
	   * @param np das aus der DB zu löschende "Objekt"
	   */
	  public void delete(Nutzerprofil np) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM T_NUTZERPROFIL " + "WHERE Profil_ID =" 
	      + np.getProfilId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	  }
	  
	  /**
	   * Einfügen eines <code>Nutzerprofil</code>-Objekts in die Datenbank. 
	   * Dabei wird auch der Primärschlüssel des übergebenen Objekts geprüft 
	   * und ggf. berichtigt.
	   * 
	   * @param np das zu speichernde Objekt
	   * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	   * <code>profilId</code>.
	   */
	  public Nutzerprofil insert(Nutzerprofil np) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      /*
	       * Zunächst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(Profil_ID) AS maxProfil_ID "
	          + "FROM T_NUTZERPROFIL");

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        /*
	         * np erhält den bisher maximalen, nun um 1 inkrementierten
	         * Primärschlüssel.
	         */
	        np.setProfilId(rs.getInt("maxProfil_ID") + 1);

	        stmt = con.createStatement();

	        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	        stmt.executeUpdate("INSERT INTO T_NUTZERPROFIL (Profil_ID, "
	        + "Vorname, Nachname, Geburtsdatum)" 
	        + "VALUES(" + np.getProfilId() + "," + np.getVorname() 
	        + np.getNachname() + np.getGeburtsdatum + ")");
	        
	        stmt.executeUpdate("INSERT INTO T_PROFIL (Profil_ID, "
	        + "Geschlecht, Haarfarbe, Koerpergroesse, Raucher)" 
	        + "VALUES(" + np.getGeschlecht() + "," + np.getHaarfarbe() 
	        + np.getKoerpergroesse() + np.getGeschlecht + ")");
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    /*
	     * Rückgabe, des evtl. korrigierten Nutzerprofils.
	     */
	    return np;
	  }

}
