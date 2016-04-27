package de.hdm.gruppe7.partnerboerse.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.CommonSettings;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Client-seitigen Klassen
 * relevant sind.
 */
public class ClientsideSettings extends CommonSettings{

	/**
	   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst
	   * namens <code>PartnerboerseAdministration</code>.
	   */
	  private static PartnerboerseAdministrationAsync partnerboerseVerwaltung = null;
	  
	  
	  /**
	   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst
	   * namens <code>ReportGenerator</code>.
	   */
	  private static ReportGeneratorAsync reportGenerator = null;
	  
	  
	  /**
	   * Name des Client-seitigen Loggers.
	   */
	  private static final String LOGGER_NAME = "PartnerboerseProjekt Web Client";
	  
	  
	  /**
	   * Instanz des Client-seitigen Loggers.
	   */
	  private static final Logger log = Logger.getLogger(LOGGER_NAME);
	  
	  
	  /**
	   * <p>
	   * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	   * </p>
	   * 
	   * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	   * 
	   * <pre>
	   * Logger logger = ClientSideSettings.getLogger();
	   * </pre>
	   * 
	   * und dann Nachrichten schreiben etwa mittels
	   * 
	   * <pre>
	   * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	   * </pre>
	   * 
	   * oder
	   * 
	   * <pre>
	   * logger.info(&quot;Lege neuen Kunden an.&quot;);
	   * </pre>
	   * 
	   * <p>
	   * Auf <em>angemessene Log Levels</em> achten! Severe und info sind nur
	   * Beispiele.
	   * </p>
	   * 
	   * <h2>HINWEIS:</h2>
	   * <p>
	   * Der auszugebende Log ist nun nicht mehr durch
	   * bedarfsweise Einfügen und Auskommentieren etwa von
	   * <code>System.out.println(...);</code> zu steuern. Künftig soll
	   * sämtliches Logging im Code belassen werden, sodass somit ohne abermaliges 
	   * Kompilieren der Log Level "von außen" durch die Datei 
	   * <code>logging.properties</code> gesteuert werden kann.
	   * Diese Datei findet sich im <code>war/WEB-INF</code>-Ordner. Der dort
	   * standardmäßig vorgegebene Log Level ist <code>WARN</code>. Dies würde
	   * bedeuten, dass keine <code>INFO</code>-Meldungen wohl aber
	   * <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn
	   * also auch Log des Levels <code>INFO</code> erwünscht wären, müssten in dieser
	   * Datei <code>.level = INFO</code> gesetzt werden.
	   * </p>
	   * 
	   * Weitere Infos siehe Dokumentation zu Java Logging.
	   * 
	   * @return die Logger-Instanz für die Server-Seite
	   */
	  public static Logger getLogger() {
		    return log;
		  }
	  
	  
	  /**
	   * <p>
	   * Anlegen und Auslesen der applikationsweit eindeutigen 
	   * PartnerboerseAdministration. Diese Methode erstellt die 
	   * PartnerboerseAdministration, sofern sie noch nicht existiert. Bei
	   * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	   * Objekt zurückgegeben.
	   * </p>
	   * 
	   * <p>
	   * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	   * <code>PartnerboerseAdministrationAsync partnerboerseVerwaltung
	   * = ClientSideSettings.getPartnerboerseVerwaltung()</code>
	   * .
	   * </p>
	   * 
	   * @return eindeutige Instanz des Typs <code>PartnerboerseAdministrationAsync</code>
	   */
	  public static PartnerboerseAdministrationAsync getPartnerboerseVerwaltung() {
	    // Gab es bislang noch keine PartnerboerseAdministration-Instanz, dann...
	    if (partnerboerseVerwaltung == null) {
	      // Zunächst instantiieren wir PartnerboerseAdministration
	      partnerboerseVerwaltung = GWT.create(PartnerboerseAdministration.class);
	    }

	    // So, nun brauchen wir die PartnerboerseAdministration nur noch zurückzugeben.
	    return partnerboerseVerwaltung;
	  }
	  
	  
	  /**
	   * <p>
	   * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	   * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	   * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	   * zuvor angelegte Objekt zurückgegeben.
	   * </p>
	   * 
	   * <p>
	   * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	   * <code>ReportGeneratorAsync reportGenerator 
	   * = ClientSideSettings.getReportGenerator()</code> .
	   * </p>
	   * 
	   * @return eindeutige Instanz des Typs <code>ReportGeneratorAsync</code>
	   */
	  public static ReportGeneratorAsync getReportGenerator() {
	    // Gab es bislang noch keine ReportGenerator-Instanz, dann...
	    if (reportGenerator == null) {
	      // Zunächst instantiieren wir ReportGenerator
	      reportGenerator = GWT.create(ReportGenerator.class);

	      final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
	        @Override
			public void onFailure(Throwable caught) {
	          ClientsideSettings.getLogger().severe(
	              "Der ReportGenerator konnte nicht initialisiert werden!");
	        }

	        @Override
			public void onSuccess(Void result) {
	          ClientsideSettings.getLogger().info(
	              "Der ReportGenerator wurde initialisiert.");
	        }
	      };

	      reportGenerator.init(initReportGeneratorCallback);
	    }

	    // So, nun brauchen wir den ReportGenerator nur noch zurückzugeben.
	    return reportGenerator;
	  }

}

