package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllProfildatenOfNutzerReport;

/**
 * Das asynchrone Gegenstueck des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Fuer weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies
 *  --------------------------------------------------------------------
 *  Diese Klasse wurde, wie von Herrn Prof. Dr.Thies in der Vorlesung gewuenscht, 
 *  als Grundlage uebernommen und bei Notwendigkeit an die Bedürfnisse des IT-Projekts 
 *  SS 2016 "Partnerboerse" angepasst.
 * 
 */
public interface ReportGeneratorAsync {

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#init()
	 */
	void init(AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllInfosOfNutzerReport(Nutzerprofil)
	 */
	void createAllInfosOfNutzerReport(Nutzerprofil np, AsyncCallback<AllInfosOfNutzerReport> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllProfildatenOfNutzerReport(Nutzerprofil)
	 */
	void createAllProfildatenOfNutzerReport(Nutzerprofil np, AsyncCallback<AllProfildatenOfNutzerReport> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllPartnervorschlaegeNpReport(Nutzerprofil)
	 */
	void createAllPartnervorschlaegeNpReport(Nutzerprofil nutzerprofil,
			AsyncCallback<AllPartnervorschlaegeNpReport> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllPartnervorschlaegeSpReport(Nutzerprofil, String)
	 */
	void createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil, String suchprofilname,
			AsyncCallback<AllPartnervorschlaegeSpReport> callback);

}
