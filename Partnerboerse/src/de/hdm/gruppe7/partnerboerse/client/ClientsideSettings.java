package de.hdm.gruppe7.partnerboerse.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.gruppe7.partnerboerse.shared.CommonSettings;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Client-seitigen Klassen
 * relevant sind
 * 
 * @author Nina
 *
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens PartnerboerseAdministration.
	 */
	private static PartnerboerseAdministrationAsync partnerboerseAdministration = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens ReportGenerator.
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
	 * Instanz des Nutzerprofils
	 */
	private static Nutzerprofil aktuellerUser = null;

	/**
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * Anlegen und Auslesen der applikationsweit eindeutigen
	 * PartnerboerseAdministration. Diese Methode erstellt die
	 * PartnerboerseAdministration, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurückgegeben.
	 * 
	 * @return Instanz des Typs PartnerboerseAdministrationAsync
	 */
	public static PartnerboerseAdministrationAsync getPartnerboerseAdministration() {

		if (partnerboerseAdministration == null) {
			partnerboerseAdministration = GWT.create(PartnerboerseAdministration.class);
		}
		return partnerboerseAdministration;
	}

	/**
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	 * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurückgegeben.
	 * 
	 * @return Instanz des Typs ReportGeneratorAsync
	 */
	public static ReportGeneratorAsync getReportGenerator() {

		if (reportGenerator == null) {
			reportGenerator = GWT.create(ReportGenerator.class);
		}
		return reportGenerator;

	}

	/**
	 * Setter-Methode zu aktuellerUser
	 * 
	 * @param np
	 */
	public static void setAktuellerUser(Nutzerprofil np) {
		aktuellerUser = np;

	}

	/**
	 * Getter-Methode zu aktuellerUser
	 * 
	 * @return aktuellerUser
	 */
	public static Nutzerprofil getAktuellerUser() {
		return aktuellerUser;
	}

}
