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


/**
 * Das asynchrone Gegenstück des Interface {@link PartnerboerseAdministration}
 * 
 * @author dunja
 *
 */
public interface PartnerboerseAdministrationAsync {

	/**
	 * Initialisierung des Objekts.
	 * 
	 * @param callback
	 */
	void init(AsyncCallback<Void> callback);
	

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#isUserRegistered(String)
	 * 
	 * @param userEmail
	 * @param isUserRegisteredCallback
	 */
	
	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#login(String)
	 * 
	 * @param requestUri
	 * @param callback
	 * @throws Exception
	 */
	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeObNutzerNeu(String)
	 * 
	 * @param userEmail
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #createNutzerprofil(String, String, String, Date, int, String, String, String, String)
	 * 
	 * @param vorname
	 * @param nachname
	 * @param geschlecht
	 * @param geburtsdatumDate
	 * @param koerpergroesseInt
	 * @param haarfarbe
	 * @param raucher
	 * @param religion
	 * @param emailAddress
	 * @param callback
	 */
	void createNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress,
			AsyncCallback<Nutzerprofil> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #saveNutzerprofil(int, String, String, String, Date, int, String, String, String)
	 * 
	 * @param profilId
	 * @param vorname
	 * @param nachname
	 * @param geschlecht
	 * @param geburtsdatumDate
	 * @param koerpergroesseInt
	 * @param haarfarbe
	 * @param raucher
	 * @param religion
	 * @param callback
	 */
	void saveNutzerprofil(int profilId, String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteNutzerprofil(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void deleteNutzerprofil(int profilId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getNutzerprofilById(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getFremdprofilById(int)
	 * 
	 * @param fremdprofilId
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #createSuchprofil(int, String, String, int, int, int, String, String, String)
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @param geschlecht
	 * @param alterMinInt
	 * @param alterMaxInt
	 * @param koerpergroesseInt
	 * @param haarfarbe
	 * @param raucher
	 * @param religion
	 * @param callback
	 */
	
	void createSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #saveSuchprofil(int, int, String, String, int, int, int, String, String, String)
	 * 
	 * @param profilId
	 * @param suchprofilId
	 * @param suchprofilName
	 * @param geschlecht
	 * @param alterMinInt
	 * @param alterMaxInt
	 * @param koerpergroesseInt
	 * @param haarfarbe
	 * @param raucher
	 * @param religion
	 * @param callback
	 */
	void saveSuchprofil(int profilId, int suchprofilId, String suchprofilName, String geschlecht,
			int alterMinInt, int alterMaxInt, int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteSuchprofil(int, String)
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @param callback
	 */
	void deleteSuchprofil(int profilId, String suchprofilName, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllSuchprofileFor(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getAllSuchprofileFor(int profilId, AsyncCallback<List<Suchprofil>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getSuchprofilByName(int, String)
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @param callback
	 */
	void getSuchprofilByName(int profilId, String suchprofilName, AsyncCallback<Suchprofil> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getSuchprofilById(int, int)
	 * 
	 * @param profilId
	 * @param suchprofilId
	 * @param callback
	 */
	void getSuchprofilById (int profilId, int suchprofilId, AsyncCallback<Suchprofil> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeSuchprofilnameCreate(int, String)
	 * 
	 * @param profilId
	 * @param suchprofilname
	 * @param callback
	 */
	void pruefeSuchprofilnameCreate(int profilId, String suchprofilname, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeSuchprofilnameEdit(int, int, String)
	 * 
	 * @param profilId
	 * @param suchprofilId
	 * @param suchprofilname
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGemerkteNutzerprofileFor(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getGemerkteNutzerprofileFor(int profilId, AsyncCallback<Merkliste> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeVermerkstatus(int, int)
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @param callback
	 */
	void pruefeVermerkstatus(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#vermerkstatusAendern(int, int)
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGemerkteNutzerprofileFor(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getGesperrteNutzerprofileFor(int profilId, AsyncCallback<Sperrliste> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeSperrstatusFremdprofil(int, int)
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @param callback
	 */
	void pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getSperrstatusEigenesProfil(int, int)
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @param callback
	 */
	void getSperrstatusEigenesProfil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#sperrstatusAendern(int, int)
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getUnangeseheneNutzerprofile(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getUnangeseheneNutzerprofile(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#besuchSetzen(int, int)
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @param callback
	 */
	void besuchSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#berechneAehnlichkeitNpFor(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void berechneAehnlichkeitNpFor(int profilId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGeordnetePartnervorschlaegeNp(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#berechneAehnlichkeitSpFor(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void berechneAehnlichkeitSpFor(int profilId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGeordnetePartnervorschlaegeSp(int, String)
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @param callback
	 */
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
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllEigenschaften()
	 * 
	 * @param callback
	 */
	void getAllEigenschaften(AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAuswahleigenschaften(List)
	 * 
	 * @param listE
	 * @param callback
	 */
	void getAuswahleigenschaften(List<Eigenschaft> listE, 
			AsyncCallback<List<Auswahleigenschaft>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllUnusedEigenschaften(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getAllUnusedEigenschaften(int profilId, AsyncCallback<Map<List<Beschreibungseigenschaft>, 
			List<Auswahleigenschaft>>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#createInfo(int, List)
	 * 
	 * @param profilId
	 * @param infos
	 * @param callback
	 */
	void createInfo(int profilId, List<Info> infos, AsyncCallback<Integer> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllInfos(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getAllInfos(int profilId, AsyncCallback<Map<List<Info>, List<Eigenschaft>>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllInfosNeuReport(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void getAllInfosNeuReport(int profilId, AsyncCallback<List<Info>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteAllInfosNeu(int)
	 * 
	 * @param profilId
	 * @param callback
	 */
	void deleteAllInfosNeu(int profilId, AsyncCallback<Integer> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteOneInfoNeu(int, int)
	 * 
	 * @param profilId
	 * @param eigenschaftId
	 * @param callback
	 */
	void deleteOneInfoNeu(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#saveInfo(int, List)
	 * 
	 * @param profilId
	 * @param listI
	 * @param callback
	 */
	void saveInfo(int profilId, List<Info> listI, AsyncCallback<Integer> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getEigAById(int)
	 * 
	 * @param eigenschaftId
	 * @param callback
	 */
	void getEigAById(int eigenschaftId, AsyncCallback<Auswahleigenschaft> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getEigBById(int)
	 * 
	 * @param eigenschaftId
	 * @param callback
	 */
	void getEigBById(int eigenschaftId, AsyncCallback<Beschreibungseigenschaft> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getEigenschaftstextById(int)
	 * 
	 * @param eigenschaftId
	 * @param callback
	 */
	void getEigenschaftstextById(int eigenschaftId,
			AsyncCallback<String> callback);
	
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Administrator-Funktionen
	 * *************************************************************************
	 * **
	 */
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #createBeschreibungseigenschaft(int, String, String, String)
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param beschreibungstext
	 * @param callback
	 */
	void createBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext, AsyncCallback<Beschreibungseigenschaft> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #createAuswahleigenschaft(int, String, String, List)
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param auswahloption
	 * @param callback
	 */
	void createAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloption, AsyncCallback<Auswahleigenschaft> callback) ;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #saveBeschreibungseigenschaft(int, String, String, String)
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param beschreibungstext
	 * @param callback
	 */
	void saveBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #saveAuswahleigenschaft(int, String, String, List)
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param auswahloption
	 * @param callback
	 */
	void saveAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloption, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteBeschreibungseigenschaft(int)
	 * 
	 * @param eigenschaftId
	 * @param callback
	 */
	void deleteBeschreibungseigenschaft(int eigenschaftId, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteAuswahleigenschaft(int)
	 * 
	 * @param eigenschaftId
	 * @param callback
	 */
	void deleteAuswahleigenschaft(int eigenschaftId, AsyncCallback<Void> callback);
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Administrator-Funktionen
	 * *************************************************************************
	 * **
	 */
}
