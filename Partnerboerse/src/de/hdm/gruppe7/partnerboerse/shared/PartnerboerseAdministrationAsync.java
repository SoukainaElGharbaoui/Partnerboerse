package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

/**
 * Das asynchrone Gegenstueck des Interface {@link PartnerboerseAdministration}. 
 * Es wird semiautomatisch durch das Google Plugin erstellt und gepflegt. 
 * Daher erfolgt hier keine weitere Dokumentation. 
 * Für weitere Informationen siehe das synchrone Interface {@link PartnerboerseAdministration}.
 */

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

	// Alle Vermerke eines Nutzerprofils auslesen.
	void getGemerkteNutzerprofileFor(int profilId, AsyncCallback<Merkliste> callback);

	// Vermerkstatus ermitteln.
	void pruefeVermerkstatus(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	// Vermerkstatus aendern.
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

	// Alle Sperrungen eines Nutzerprofils auslesen.
	void getGesperrteNutzerprofileFor(int profilId, AsyncCallback<Sperrliste> callback);

	// Pruefen, ob Fremdprofil von Benutzer gesperrt wurde.
	void pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	// Pruefen, ob Benutzer von Fremdprofil gesperrt wurde.
	void getSperrstatusEigenesProfil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	// Sperrstatus aendern.
	void sperrstatusAendern(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Partnervorschl�ge
	 * *************************************************************************
	 * **
	 */

	// Alle unangesehenen Nutzerprofile auslesen.
	void getUnangeseheneNutzerprofile(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	void besuchSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	
	void aehnlichkeitEntfernen(int profilId, AsyncCallback<Void> callback);

	void berechneAehnlichkeitNpFor(int profilId, AsyncCallback<Void> callback);

	void getGeordnetePartnervorschlaegeNp(int profilId, AsyncCallback<List<Nutzerprofil>> callback);


	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Partnervorschl�ge
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlägeSp
	 * *************************************************************************
	 * **
	 */

	void berechneAehnlichkeitSpFor(int profilId, AsyncCallback<Void> callback);

	void aehnlichkeitEntfernenSp(int profilId, AsyncCallback<Void> callback);


	void getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilName,
			AsyncCallback<List<Nutzerprofil>> callback);



	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlägeSp
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
	
	void createInfo(int profilId, List<Info> infos, AsyncCallback<Integer> callback);
	
	void getAllInfos(int profilId, AsyncCallback<Map<List<Info>, List<Eigenschaft>>> callback);
	
	void getAllInfosNeuReport(int profilId, AsyncCallback<List<Info>> callback);

	void deleteAllInfosNeu(int profilId, AsyncCallback<Integer> callback);
	
	void deleteOneInfoNeu(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	void saveInfo(int profilId, List<Info> listI, AsyncCallback<Integer> callback);
	
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

