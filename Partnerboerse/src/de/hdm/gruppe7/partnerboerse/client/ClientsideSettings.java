package de.hdm.gruppe7.partnerboerse.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.gruppe7.partnerboerse.shared.CommonSettings;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ClientsideSettings extends CommonSettings {

	private static PartnerboerseAdministrationAsync partnerboerseAdministration = null;

	private static ReportGeneratorAsync reportGenerator = null;

	private static PartnerboerseAdministrationAsync loginService = null;

	private static final String LOGGER_NAME = "PartnerboerseProjekt Web Client";

	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	private static Nutzerprofil aktuellerUser = null;

	public static Logger getLogger() {
		return log;
	}

	public static PartnerboerseAdministrationAsync getPartnerboerseAdministration() {

		if (partnerboerseAdministration == null) {
			partnerboerseAdministration = GWT.create(PartnerboerseAdministration.class);
		}
		return partnerboerseAdministration;
	}

	public static ReportGeneratorAsync getReportGenerator() {
		
		if (reportGenerator == null) {
			reportGenerator = GWT.create(ReportGenerator.class); 
		}
		return reportGenerator; 

	}

	public static void setAktuellerUser(Nutzerprofil np) {
		aktuellerUser = np;

	}

	public static Nutzerprofil getAktuellerUser() {
		return aktuellerUser;
	}
	
}




