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
 * Das asynchrone Gegenstueck des Interface {@link PartnerboerseAdministration}.
 */
public interface PartnerboerseAdministrationAsync {

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#init()
	 */
	void init(AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#isUserRegistered(String)
	 */
	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#login(String)
	 */
	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeObNutzerNeu(String) 
	 */
	void pruefeObNutzerNeu(String userEmail, AsyncCallback<Boolean> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createNutzerprofil
	 * (String, String, String, geschlecht, Date, int, String, String, String, String)
	 */
	void createNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress,
			AsyncCallback<Nutzerprofil> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveNutzerprofil
	 * (int, String, String, String, Date, int, String, String, String)
	 */
	void saveNutzerprofil(int profilId, String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteNutzerprofil(int)
	 */
	void deleteNutzerprofil(int profilId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getNutzerprofilById(int)
	 */
	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createSuchprofil
	 * (int, String, String, int, int, int, String, String, String)
	 */	
	void createSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveSuchprofil
	 * (int, int, String, String, int, int, int, String, String, String)
	 */
	void saveSuchprofil(int profilId, int suchprofilId, String suchprofilName, String geschlecht,
			int alterMinInt, int alterMaxInt, int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteSuchprofil(int, String)
	 */
	void deleteSuchprofil(int profilId, String suchprofilName, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllSuchprofileFor(int)
	 */
	void getAllSuchprofileFor(int profilId, AsyncCallback<List<Suchprofil>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getSuchprofilByName(int, String)
	 */
	void getSuchprofilByName(int profilId, String suchprofilName, AsyncCallback<Suchprofil> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getSuchprofilById(int)
	 */
	void getSuchprofilById (int suchprofilId, AsyncCallback<Suchprofil> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeSuchprofilnameCreate(int, String)
	 */
	void pruefeSuchprofilnameCreate(int profilId, String suchprofilname, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeSuchprofilnameEdit(int, int, String)
	 */
	void pruefeSuchprofilnameEdit(int profilId, int suchprofilId, String suchprofilname,
			AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGemerkteNutzerprofileFor(int)
	 */
	void getGemerkteNutzerprofileFor(int profilId, AsyncCallback<Merkliste> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeVermerkstatus(int, int)
	 */
	void pruefeVermerkstatus(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#vermerkstatusAendern(int, int)
	 */
	void vermerkstatusAendern(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGesperrteNutzerprofileFor(int)
	 */
	void getGesperrteNutzerprofileFor(int profilId, AsyncCallback<Sperrliste> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeSperrstatusFremdprofil(int, int)
	 */
	void pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getSperrstatusEigenesProfil(int, int)
	 */
	void getSperrstatusEigenesProfil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#sperrstatusAendern(int, int)
	 */
	void sperrstatusAendern(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getUnangeseheneNutzerprofile(int)
	 */
	void getUnangeseheneNutzerprofile(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#besuchSetzen(int, int)
	 */
	void besuchSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#berechneAehnlichkeitNpFor(int)
	 */
	void berechneAehnlichkeitNpFor(int profilId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGeordnetePartnervorschlaegeNp(int)
	 */
	void getGeordnetePartnervorschlaegeNp(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#berechneAehnlichkeitSpFor(int)
	 */
	void berechneAehnlichkeitSpFor(int profilId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGeordnetePartnervorschlaegeSp(int, String)
	 */
	void getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilName,
			AsyncCallback<List<Nutzerprofil>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllEigenschaften()
	 */
	void getAllEigenschaften(AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllUnusedEigenschaften(int)
	 */
	void getAllUnusedEigenschaften(int profilId, AsyncCallback<Map<List<Beschreibungseigenschaft>, 
			List<Auswahleigenschaft>>> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAuswahleigenschaften(List)
	 */
	void getAuswahleigenschaften(List<Eigenschaft> listE, 
			AsyncCallback<List<Auswahleigenschaft>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getEigAById(int)
	 */
	void getEigAById(int eigenschaftId, AsyncCallback<Auswahleigenschaft> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getEigBById(int)
	 */
	void getEigBById(int eigenschaftId, AsyncCallback<Beschreibungseigenschaft> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createInfo(int, List)
	 */
	void createInfo(int profilId, List<Info> infos, AsyncCallback<List<Info>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllInfos(int)
	 */
	void getAllInfos(int profilId, AsyncCallback<Map<List<Info>, List<Eigenschaft>>> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteAllInfosNeu(int)
	 */
	void deleteAllInfosNeu(int profilId, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteOneInfoNeu(int, int)
	 */
	void deleteOneInfoNeu(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveInfo(int, List)
	 */
	void saveInfo(int profilId, List<Info> listI, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createBeschreibungseigenschaft(int, String, String, String)
	 */
	void createBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext, AsyncCallback<Beschreibungseigenschaft> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createAuswahleigenschaft(int, String, String, List)
	 */
	void createAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloption, AsyncCallback<Auswahleigenschaft> callback) ;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveBeschreibungseigenschaft(int, String, String, String)
	 */
	void saveBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveAuswahleigenschaft(int, String, String, List)
	 */
	void saveAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloption, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteBeschreibungseigenschaft(int)
	 */
	void deleteBeschreibungseigenschaft(int eigenschaftId, AsyncCallback<Void> callback);
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteAuswahleigenschaft(int)
	 */
	void deleteAuswahleigenschaft(int eigenschaftId, AsyncCallback<Void> callback);
	
}
