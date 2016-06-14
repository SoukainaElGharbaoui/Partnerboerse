package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

@RemoteServiceRelativePath("partnerboerseadministration")
public interface PartnerboerseAdministration extends RemoteService {

	public void init() throws IllegalArgumentException;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */


	/**
	 * Nutzerprofil anlegen.
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress)
			throws IllegalArgumentException;

	/**
	 * Nutzerprofil aktualisieren.
	 */
	public void saveNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException;


	/**
	 * Nutzerprofil l�schen.
	 */
	void deleteNutzerprofil() throws IllegalArgumentException;

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen.
	 */
	public Nutzerprofil getNutzerprofilById() throws IllegalArgumentException;
	
	/**
	 * Fremdprofil anhand dessen Profil-ID auslesen.
	 */
	public Nutzerprofil getFremdprofilById(int fremdprofilId) throws IllegalArgumentException;


	/**
	 * Alle Nutzerprofile auslesen.
	 */
	public List<Nutzerprofil> getAllNutzerprofile() throws IllegalArgumentException;

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
	public Suchprofil createSuchprofil(String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion);

	/**
	 * Suchprofil aktualisieren.
	 */
	public void saveSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException;

	/**
	 * Suchprofil loeschen.
	 */
	public void deleteSuchprofil(String suchprofilName) throws IllegalArgumentException;
	
	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 */
	public List<Suchprofil> getAllSuchprofileFor() throws IllegalArgumentException;
	
	/**
	 * Suchprofil anhand des Suchprofilnamens auslesen.
	 */
	public Suchprofil getSuchprofilByName(String suchprofilName) throws IllegalArgumentException;

	/**
	 * Suchprofilname beim Anlegen eines Suchprofils ueberpruefen. 
	 */
	public int pruefeSuchprofilnameCreate(String suchprofilname) throws IllegalArgumentException; 
	
	/**
	 * Suchprofilname beim Editieren eines Suchprofils ueberpruefen. 
	 */
	public int pruefeSuchprofilnameEdit(int suchprofilId, String suchprofilname) throws IllegalArgumentException;

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
	public Merkliste getGemerkteNutzerprofileFor() throws IllegalArgumentException;

	// Vermerkstatus ermitteln.
	public int pruefeVermerkstatus(int fremdprofilId) throws IllegalArgumentException;
	
	// Vermerkstatus aendern.
	public int vermerkstatusAendern(int fremdprofilId) throws IllegalArgumentException;


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
	public Sperrliste getGesperrteNutzerprofileFor() throws IllegalArgumentException;
	
	// Pruefen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int pruefeSperrstatusFremdprofil(int fremdprofilId) throws IllegalArgumentException;
		
	// Pruefen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int fremdprofilId) throws IllegalArgumentException;	
	
	// Sperrstatus aendern.
	public int sperrstatusAendern(int fremdprofilId) throws IllegalArgumentException;


	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Partnervorschl�geNp
	 * *************************************************************************
	 * **
	 */

	// Alle unangesehenen Nutzerprofile auslesen.
	public List<Nutzerprofil> getUnangeseheneNutzerprofile() throws IllegalArgumentException;

	// Besuch setzen.
	public void besuchSetzen(int fremdprofilId) throws IllegalArgumentException;

	// Aehnlichkeit berechnen
	public void berechneAehnlichkeitNpFor() throws IllegalArgumentException;


	// Aehnlichkeit aus DB loeschen
	public void aehnlichkeitEntfernen() throws IllegalArgumentException;

	// Ausgabe der Partnervorschlaege
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp() throws IllegalArgumentException;
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Partnervorschl�geNp
	 * *************************************************************************
	 * **
	 * 
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: PartnervorschlägeSp
	 * ***************************************************************************
	 */
	
	//Aehnlichkeit berechnen
		public void berechneAehnlichkeitSpFor()
		throws IllegalArgumentException;
		
		
		//Aehnlichkeit aus DB loeschen
		public void aehnlichkeitEntfernenSp () throws IllegalArgumentException;
		
		
		
		//Ausgabe der Partnervorschlaege
		public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(String suchprofilName) throws IllegalArgumentException;
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: PartnervorschlägeSp
	 * ***************************************************************************
	 */


	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */

	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllEigenschaften() 	
			throws IllegalArgumentException;	
	
	public List<Auswahleigenschaft> getAuswahleigenschaften(List<Eigenschaft> listE) 
			throws IllegalArgumentException;
	
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften() 	
			throws IllegalArgumentException;	
	
//	public List<Eigenschaft> getAllUnusedEigenschaftenNeu()
//			throws IllegalArgumentException;
	
	public List<Eigenschaft> getAllUnusedEigenschaftenNeuSp(int suchprofilId)
			throws IllegalArgumentException;
	
	public List<Info> createInfo(List<Info> infos)
			throws IllegalArgumentException;
	
//	public Info createInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext)
//			throws IllegalArgumentException;
	
	public Map<List<Info>, List<Eigenschaft>> getAllInfos() throws IllegalArgumentException;
	
	public List<String> getAllInfosNeuSp(int suchprofilId) throws IllegalArgumentException;

	public List<Info> getAllInfosNeuReport(int profilId)
			throws IllegalArgumentException;


	public void deleteAllInfosNeu()
			throws IllegalArgumentException;
	
	public void deleteAllInfosNeuSp(int suchprofilId)
			throws IllegalArgumentException;

	public void deleteOneInfoNeu(int eigenschaftId)
			throws IllegalArgumentException;
	
	public void deleteOneInfoNeuSp(int suchprofilId, int eigenschaftId)
			throws IllegalArgumentException;
	
	public void saveInfo(List<Info> listI)
			throws IllegalArgumentException;
	
//	public void saveInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext)
//			throws IllegalArgumentException;

	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException;
	
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException;


	public String getEigenschaftstextById(int eigenschaftId) throws IllegalArgumentException;




	
//	public Info createBeschreibungsinfo(int profilId, int eigenschaftId, String infotext)
//			throws IllegalArgumentException;
//
//	public Info createAuswahlinfo(int profilId, int eigenschaftId, int auswahloptionId) throws IllegalArgumentException;
//
//	public void saveInfoA(int profilId, int neueAuswahloptionId, int eigenschaftId) throws IllegalArgumentException;
//
//	public void saveInfoB(int profilId, int eigenschaftId, String infotext) throws IllegalArgumentException;
//
//	public List<Eigenschaft> getAllEigenschaftenB() throws IllegalArgumentException;
//
//	public List<Eigenschaft> getAllEigenschaftenA() throws IllegalArgumentException;
//
//	public List<Auswahloption> getAllAuswahloptionen(int eigenschaftId) throws IllegalArgumentException;
//
//	public List<Info> getAllInfosB(int profilId) throws IllegalArgumentException;
//
//	public List<Info> getAllInfosA(int profilId) throws IllegalArgumentException;
//
//	public Info getOptionById(int eigenschaftId) throws IllegalArgumentException;
//
//	public Info getInfoAById(String optionsbezeichnung, int eigenschaftId) throws IllegalArgumentException;
//
//	public void deleteAllInfos(int profilId) throws IllegalArgumentException;
//
//	public void deleteOneInfoB(int profilId, int eigenschaftId) throws IllegalArgumentException;
//
//	public void deleteOneInfoA(int profilId, int eigenschaftId) throws IllegalArgumentException;
//
//	public List<Info> getAInfoByProfilId(int profilId) throws IllegalArgumentException;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */

	boolean isUserRegistered(String userEmail);

//	public Nutzerprofil insertEmail(int profilId, String emailAddress) throws IllegalArgumentException;

	Nutzerprofil login(String requestUri) throws Exception;
	
	public void setUser(Nutzerprofil n);



}