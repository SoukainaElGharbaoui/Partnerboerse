package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * @author Nina Baumgärtner
 */

public class DBConnection {

	/**
	 *Instanziierung 
	 * 
	 * @see (InfoMapper.infoMapper())
	 * @see (MerklisteMapper.merklisteMapper()) 
	 * @see (NutzerprofilMapper.nutzerprofilMapper())
	 * @see (SperrlisteMapper.sperrlisteMapper())
	 * @see (SuchprofilMapper.suchprofilMapper())
	 */

	private static Connection con = null;

	/**
	 * Die URL, mit deren Hilfe die Datenbank angesprochen wird.
	 */

	private static String googleUrl = "jdbc:google:mysql://partnerboerselonelyhearts:partnerboerselonelyheartsdb/partnerboerse?user=root&password=root";
	private static String localUrl = "jdbc:mysql://127.0.0.1:3306/partnerboerse?user=root&password=";

	/**
	 * Erzeugung der statischen Methode, stellt die Singelton-Eigenschaft sicher.
	 * 
	 * @return Das DBConncetion-Objekt.
	 * @see con
	 */

	public static Connection connection() {

		if (con == null) {
			String url = null;
			try {
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
					
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
					
				} else {
					
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
			}
				
				con = DriverManager.getConnection(url);
			} catch (Exception e) {
				con = null;
				e.printStackTrace();
			}
		}

		return con;
	}

}
