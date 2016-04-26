package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.*;
import java.util.List;
import java.util.Vector;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;






//import de.hdm.gruppe7.partnerboerse.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Nutzerprofil</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
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
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @see nutzerprofilMapper()
	   */
	
	 private static NutzerprofilMapper nutzerprofilMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected NutzerprofilMapper() {
	  }
	  
	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>NutzerprofilMapper.nutzerprofilMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
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
	   * wird genau ein Objekt zur�ckgegeben.
	   * 
	   * @param id Primärschlüsselattribut (->DB)
	   * @return Nutzerprofil-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	   *         nicht vorhandenem DB-Tupel.
	   */
	  public Nutzerprofil findByNutzerprofilId(int profilId) {
	    // DB-Verbindung holen
	    Connection con = DBConnection.connection();

	    try {
	      // Leeres SQL-Statement (JDBC) anlegen
	      Statement stmt = con.createStatement();

	      // Statement ausfüllen und als Query an die DB schicken
	      ResultSet rs = stmt.executeQuery("SELECT Profil_ID, Vorname, Nachname FROM T_NUTZERPROFIL "
	          + "WHERE Profil_ID=" + profilId);

	      /*
	       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
	       * werden. Prüfe, ob ein Ergebnis vorliegt.
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
	   * @return Eine Liste mit Nutzerprofil-Objekten, die sämtliche Nutzerprofile
	   *         repräsentieren. Bei evtl. Exceptions wird eine partiell gefüllte
	   *         oder ggf. auch eine leere Liste zurückgeliefert.
	   */
	  public List<Nutzerprofil> findAllNutzerprofile() {
	    Connection con = DBConnection.connection();

	    // Ergebnisliste vorbereiten
	    List<Nutzerprofil> result = new List<Nutzerprofil>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT Profil_ID, Vorname, Nachname FROM T_NUTZERPROFIL "
	          + " ORDER BY Profil_ID");

	      // Für jeden Eintrag im Suchergebnis wird nun ein Nutzerprofil-Objekt erstellt.
	      while (rs.next()) {
	        Nutzerprofil np = new Nutzerprofil();
	        np.setProfilId(rs.getInt("Profil_ID"));
	        np.setVorname(rs.getString("Vorname"));
	        np.setNachname(rs.getString("Nachname"));
	        
	        

	        // Hinzufügen des neuen Objekts zur Ergebnisliste
	        result.add(np);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Ergebnisliste zurückgeben
	    return result;
	  }
	
	  /**
	   * Wiederholtes Schreiben eines Objekts in die Datenbank.
	   * 
	   * @param np das Objekt, das in die DB geschrieben werden soll
	   * @return das als Parameter übergebene Objekt
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

	    // Um Analogie zu insert(Nutzerprofil np) zu wahren, geben wir np zurück
	    return np;
	  }
	  
	  /**
	   * L�schen der Daten eines <code>Nutzerprofil</code>-Objekts aus der Datenbank.
	   * 
	   * @param np das aus der DB zu l�schende "Objekt"
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
	   * Einf�gen eines <code>Nutzerprofil</code>-Objekts in die Datenbank. 
	   * Dabei wird auch der Prim�rschl�ssel des �bergebenen Objekts gepr�ft 
	   * und ggf. berichtigt.
	   * 
	   * @param np das zu speichernde Objekt
	   * @return das bereits �bergebene Objekt, jedoch mit ggf. korrigierter
	   * <code>profilId</code>.
	   */
	  public Nutzerprofil insert(Nutzerprofil np) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      /*
	       * Zun�chst schauen wir nach, welches der momentan h�chste
	       * Prim�rschl�sselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(Profil_ID) AS maxProfil_ID "
	          + "FROM T_NUTZERPROFIL");

	      // Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        /*
	         * np erh�lt den bisher maximalen, nun um 1 inkrementierten
	         * Prim�rschl�ssel.
	         */
	        np.setProfilId(rs.getInt("maxProfil_ID") + 1);

	        stmt = con.createStatement();

	        // Jetzt erst erfolgt die tats�chliche Einf�geoperation
	        stmt.executeUpdate("INSERT INTO T_NUTZERPROFIL (Profil_ID, "
	        + "Vorname, Nachname, Geburtsdatum)" 
	        + "VALUES(" + np.getProfilId() + "," + np.getVorname() 
	        + np.getNachname() + np.getGeburtsdatum() + ")");
	        
	        stmt.executeUpdate("INSERT INTO T_PROFIL (Profil_ID, "
	        + "Geschlecht, Haarfarbe, Koerpergroesse, Raucher)" 
	        + "VALUES(" + np.getGeschlecht() + "," + np.getHaarfarbe() 
	        + np.getKoerpergroesse() + np.getGeschlecht() + ")");
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    /*
	     * R�ckgabe, des evtl. korrigierten Nutzerprofils.
	     */
	    return np;
	  }

}
