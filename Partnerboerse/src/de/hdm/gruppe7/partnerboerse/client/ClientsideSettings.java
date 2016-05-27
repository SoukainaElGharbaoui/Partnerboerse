package de.hdm.gruppe7.partnerboerse.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.gruppe7.partnerboerse.shared.CommonSettings;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ClientsideSettings extends CommonSettings {

	private static PartnerboerseAdministrationAsync partnerboerseAdministration = null;

	private static ReportGeneratorAsync reportGenerator = null;

	private static LoginServiceAsync loginService = null;

	private static final String LOGGER_NAME = "PartnerboerseProjekt Web Client";

	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	private static Nutzerprofil aktuellerUser = null;

	public static Logger getLogger() {
		return log;
	}

	public static PartnerboerseAdministrationAsync getPartnerboerseAdministration() {

		if (partnerboerseAdministration == null) {
			partnerboerseAdministration = GWT
					.create(PartnerboerseAdministration.class);
		}
		return partnerboerseAdministration;
	}

	public static de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync getReportGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public static LoginServiceAsync getLoginService() {

		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}
	
	public static void setAktuellerUser( Nutzerprofil np){
		aktuellerUser = np;
		
	}

	public static Nutzerprofil getAktuellerUser() {
		return aktuellerUser;
	}

	// /**
	// * <p>
	// * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	// * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	// * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das
	// bereits
	// * zuvor angelegte Objekt zurückgegeben.
	// * </p>
	// *
	// * <p>
	// * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	// * <code>ReportGeneratorAsync reportGenerator
	// * = ClientSideSettings.getReportGenerator()</code> .
	// * </p>
	// *
	// * @return eindeutige Instanz des Typs <code>ReportGeneratorAsync</code>
	// */
	// public static ReportGeneratorAsync getReportGenerator() {
	// // Gab es bislang noch keine ReportGenerator-Instanz, dann...
	// if (reportGenerator == null) {
	// // Zunächst instantiieren wir ReportGenerator
	// reportGenerator = GWT.create(ReportGenerator.class);
	//
	// final AsyncCallback<Void> initReportGeneratorCallback = new
	// AsyncCallback<Void>() {
	// @Override
	// public void onFailure(Throwable caught) {
	// ClientsideSettings.getLogger().severe(
	// "Der ReportGenerator konnte nicht initialisiert werden!");
	// }
	//
	// @Override
	// public void onSuccess(Void result) {
	// ClientsideSettings.getLogger().info(
	// "Der ReportGenerator wurde initialisiert.");
	// }
	// };
	//
	// reportGenerator.init(initReportGeneratorCallback);
	// }
	//
	// // So, nun brauchen wir den ReportGenerator nur noch zurückzugeben.
	// return reportGenerator;
	// }

}
