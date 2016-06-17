package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;






import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllSuchprofileOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewünscht, als Grundlage 
 * übernommen und bei Notwendigkeit an die Bedürfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 * 
 * Modifizierender @author Milena Weinmann
 */
public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);
	
//	void createAllSuchprofileOfNutzerReport(Nutzerprofil n,
//			AsyncCallback<AllSuchprofileOfNutzerReport> callback);

	void createAllInfosOfNutzerReport(Nutzerprofil np,
			AsyncCallback<AllInfosOfNutzerReport> callback);

	void createAllPartnervorschlaegeNpReport(
			AsyncCallback<AllPartnervorschlaegeNpReport> callback);

	void createAllPartnervorschlaegeSpReport(
			AsyncCallback<AllPartnervorschlaegeSpReport> callback);

	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);

	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;

	void setUser(Nutzerprofil n, AsyncCallback<Void> callback);


}
