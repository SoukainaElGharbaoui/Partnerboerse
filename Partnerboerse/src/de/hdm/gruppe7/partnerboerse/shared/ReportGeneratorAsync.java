package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;





import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllProfildatenOfNutzerReport;

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
	
	void createAllInfosOfNutzerReport(Nutzerprofil np,
			AsyncCallback<AllInfosOfNutzerReport> callback);

	void createAllProfildatenOfNutzerReport(Nutzerprofil np,
			AsyncCallback<AllProfildatenOfNutzerReport> callback);
	
	void createAllPartnervorschlaegeNpReport(
			AsyncCallback<AllPartnervorschlaegeNpReport> callback);

	void createAllPartnervorschlaegeSpReport(String suchprofilname,
			AsyncCallback<AllPartnervorschlaegeSpReport> callback);


	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);

//	public void insertEmail(int profilId, String emailAddress, AsyncCallback<Nutzerprofil> callback);

	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;

	void setUser(Nutzerprofil n, AsyncCallback<Void> callback);
}
