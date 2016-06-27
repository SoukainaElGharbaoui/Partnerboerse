package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.GWT;

import de.hdm.gruppe7.partnerboerse.shared.CommonSettings;
import de.hdm.gruppe7.partnerboerse.shared.LoginService;
import de.hdm.gruppe7.partnerboerse.shared.LoginServiceAsync;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;

/**
 * Klasse mit Eigenschaften und Diensten, die fuer alle Client-seitigen Klassen
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
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens LoginService.
	 */
	private static LoginServiceAsync loginService = null;
	

	/**
	 * Anlegen und Auslesen der applikationsweit eindeutigen
	 * PartnerboerseAdministration. Diese Methode erstellt die
	 * PartnerboerseAdministration, sofern sie noch nicht existiert. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zurueckgegeben.
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
	 * zuvor angelegte Objekt zurueckgegeben.
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
	 * Anlegen und Auslesen des applikationsweit eindeutigen LoginService.
	 * Diese Methode erstellt den LoginService, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurueckgegeben.
	 * 
	 * @return Instanz des Typs LoginServiceAsync
	 */
	public static LoginServiceAsync getLoginService() {

		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}
		return loginService;

	}

}
