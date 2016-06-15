package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
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
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	void createNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress,
			AsyncCallback<Nutzerprofil> callback);

	/**
	 * Nutzerprofil aktualisieren.
	 */
	void saveNutzerprofil(int profilId, String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	/**
	 * Nutzerprofil l�schen.
	 */
	void deleteNutzerprofil(AsyncCallback<Void> callback);

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen.
	 */
	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);
	

	/**
	 * Fremdprofil anhand dessen Profil-ID auslesen.
	 */
	void getFremdprofilById(int fremdprofilId, AsyncCallback<Nutzerprofil> callback);

	/**
	 * Alle Nutzerprofile auslesen.
	 */
	void getAllNutzerprofile(AsyncCallback<List<Nutzerprofil>> callback);

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

	/**
	 * Suchprofil anlegen.
	 */
	void createSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);

	/**
	 * Suchprofil aktualisieren.
	 */
	void saveSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, AsyncCallback<Void> callback);

	/**
	 * Suchprofil loeschen.
	 */
	void deleteSuchprofil(String suchprofilName, AsyncCallback<Void> callback);
	
	/**
	 * Alle Suchprofile eines Nutzers anzeigen.
	 */
	void getAllSuchprofileFor(int profilId, AsyncCallback<List<Suchprofil>> callback);
	
	/**
	 * Suchprofil anhand des Suchprofilnamens auslesen.
	 */
	void getSuchprofilByName(int profilId, String suchprofilName, AsyncCallback<Suchprofil> callback);
	
	/**
	 * Suchprofilname beim Anlegen eines Suchprofils ueberpruefen. 
	 */
	void pruefeSuchprofilnameCreate(String suchprofilname, AsyncCallback<Integer> callback); 
	
	/**
	 * Suchprofilname beim Editieren eines Suchprofils ueberpruefen. 
	 */
	void pruefeSuchprofilnameEdit(int suchprofilId, String suchprofilname, AsyncCallback<Integer> callback);

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
	void getGemerkteNutzerprofileFor(AsyncCallback<Merkliste> callback);

	// Vermerkstatus ermitteln.
	void pruefeVermerkstatus(int fremdprofilId, AsyncCallback<Integer> callback);

	// Vermerkstatus aendern.
	void vermerkstatusAendern(int fremdprofilId, AsyncCallback<Integer> callback);

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
	void getGesperrteNutzerprofileFor(AsyncCallback<Sperrliste> callback);

	// Pruefen, ob Fremdprofil von Benutzer gesperrt wurde. 
	void pruefeSperrstatusFremdprofil(int fremdprofilId, AsyncCallback<Integer> callback);

	// Pruefen, ob Benutzer von Fremdprofil gesperrt wurde.
	void getSperrstatusEigenesProfil(int fremdprofilId, AsyncCallback<Integer> callback);
	
	// Sperrstatus aendern.
	void sperrstatusAendern(int fremdprofilId, AsyncCallback<Integer> callback);


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
	void getUnangeseheneNutzerprofile(AsyncCallback<List<Nutzerprofil>> callback);

	// Besuch setzen.
	void besuchSetzen(int fremdprofilId, AsyncCallback<Void> callback);

	void berechneAehnlichkeitNpFor(AsyncCallback<Void> callback);

	void aehnlichkeitEntfernen(AsyncCallback<Void> callback);

	void getGeordnetePartnervorschlaegeNp(AsyncCallback<List<Nutzerprofil>> callback);

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

	void berechneAehnlichkeitSpFor(AsyncCallback<Void> callback);


	void aehnlichkeitEntfernenSp(AsyncCallback<Void> callback);


	void getGeordnetePartnervorschlaegeSp(String suchprofilName,
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


	void getAllEigenschaften(AsyncCallback<Map<List<Beschreibungseigenschaft>, 
			List<Auswahleigenschaft>>> callback);

	void getAuswahleigenschaften(List<Eigenschaft> listE, 
			AsyncCallback<List<Auswahleigenschaft>> callback);
	
	void getAllUnusedEigenschaften(int profilId, AsyncCallback<Map<List<Beschreibungseigenschaft>, 
			List<Auswahleigenschaft>>> callback);
	
	void getAllUnusedEigenschaftenNeuSp(int suchprofilId, AsyncCallback<List<Eigenschaft>> callback);

	void createInfo(int profilId, List<Info> infos, AsyncCallback<Integer> callback);
	
//	void createInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext, AsyncCallback<Info> callback);

	void getAllInfos(int profilId, AsyncCallback<Map<List<Info>, List<Eigenschaft>>> callback);
	
	void getAllInfosNeuReport(int profilId, AsyncCallback<List<Info>> callback);

	void getAllInfosNeuSp(int suchprofilId, AsyncCallback<List<String>> callback);

	void deleteAllInfosNeu(int profilId, AsyncCallback<Integer> callback);
	
	void deleteAllInfosNeuSp(int suchprofilId, AsyncCallback<Void> callback);

	void deleteOneInfoNeu(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	void deleteOneInfoNeuSp(int suchprofilId, int eigenschaftId, AsyncCallback<Void> callback);

	void saveInfo(int profilId, List<Info> listI, AsyncCallback<Integer> callback);
	
//	void saveInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext, AsyncCallback<Void> callback);

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

	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);

//	public void insertEmail(int profilId, String emailAddress, AsyncCallback<Nutzerprofil> callback);

	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;

	void setUser(Nutzerprofil n, AsyncCallback<Void> callback);

}
