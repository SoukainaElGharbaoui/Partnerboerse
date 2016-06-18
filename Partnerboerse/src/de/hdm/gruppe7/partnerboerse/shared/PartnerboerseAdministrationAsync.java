package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */

	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);

	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;
	
	void pruefeObNutzerNeu(String userEmail, AsyncCallback<Boolean> callback);

	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Login
	 * *************************************************************************
	 * **
	 */
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	void createNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress,
			AsyncCallback<Nutzerprofil> callback);

	void saveNutzerprofil(int profilId, String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	void deleteNutzerprofil(int profilId, AsyncCallback<Void> callback);

	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);

	void getFremdprofilById(int fremdprofilId, AsyncCallback<Nutzerprofil> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Suchprofil
	 * *************************************************************************
	 * **
	 */

	void createSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);

	void saveSuchprofil(int profilId, int suchprofilId, String suchprofilName, String geschlecht,
			int alterMinInt, int alterMaxInt, int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	void deleteSuchprofil(int profilId, String suchprofilName, AsyncCallback<Void> callback);

	void getAllSuchprofileFor(int profilId, AsyncCallback<List<Suchprofil>> callback);
	
	void getSuchprofilByName(int profilId, String suchprofilName, AsyncCallback<Suchprofil> callback);
	
	void pruefeSuchprofilnameCreate(int profilId, String suchprofilname, AsyncCallback<Integer> callback);

	void pruefeSuchprofilnameEdit(int profilId, int suchprofilId, String suchprofilname,
			AsyncCallback<Integer> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Suchprofil
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Merkliste
	 * *************************************************************************
	 * **
	 */

	void getGemerkteNutzerprofileFor(int profilId, AsyncCallback<Merkliste> callback);

	void pruefeVermerkstatus(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	void vermerkstatusAendern(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Merkliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Sperrliste
	 * *************************************************************************
	 * **
	 */

	void getGesperrteNutzerprofileFor(int profilId, AsyncCallback<Sperrliste> callback);

	void pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	void getSperrstatusEigenesProfil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	void sperrstatusAendern(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlaegeNp
	 * *************************************************************************
	 * **
	 */

	void getUnangeseheneNutzerprofile(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	void besuchSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	
	void berechneAehnlichkeitNpFor(int profilId, AsyncCallback<Void> callback);

	void getGeordnetePartnervorschlaegeNp(int profilId, AsyncCallback<List<Nutzerprofil>> callback);


	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlaegeNp
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlaegeSp
	 * *************************************************************************
	 * **
	 */

	void berechneAehnlichkeitSpFor(int profilId, AsyncCallback<Void> callback);

	void getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilName,
			AsyncCallback<List<Nutzerprofil>> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlaegeSp
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */

	void getAllEigenschaften(AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>> callback);

	void getAuswahleigenschaften(List<Eigenschaft> listE, 
			AsyncCallback<List<Auswahleigenschaft>> callback);
	
	void getAllUnusedEigenschaften(int profilId, AsyncCallback<Map<List<Beschreibungseigenschaft>, 
			List<Auswahleigenschaft>>> callback);
	
	void createInfo(int profilId, List<Info> infos, AsyncCallback<List<Info>> callback);
	
	void getAllInfos(int profilId, AsyncCallback<Map<List<Info>, List<Eigenschaft>>> callback);
	
	void getAllInfosNeuReport(int profilId, AsyncCallback<List<Info>> callback);

	void deleteAllInfosNeu(int profilId, AsyncCallback<Integer> callback);
	
	void deleteOneInfoNeu(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	void saveInfo(int profilId, List<Info> listI, AsyncCallback<Void> callback);
	
	void getEigAById(int eigenschaftId, AsyncCallback<Auswahleigenschaft> callback);

	void getEigBById(int eigenschaftId, AsyncCallback<Beschreibungseigenschaft> callback);

	void getEigenschaftstextById(int eigenschaftId,
			AsyncCallback<String> callback);
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */
}
