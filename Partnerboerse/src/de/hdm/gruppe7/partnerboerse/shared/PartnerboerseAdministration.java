package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
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
	public void saveNutzerprofil(int profilId, String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException;


	/**
	 * Nutzerprofil l�schen.
	 */
	void deleteNutzerprofil(int profilId) throws IllegalArgumentException;

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen.
	 */
	public Nutzerprofil getNutzerprofilById(int profilId) throws IllegalArgumentException;
	
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
	 * @param suchprofilName 
	 * @param geschlecht 
	 * @param alterMinInt 
	 * @param alterMaxInt 
	 * @param koerpergroesseInt 
	 * @param haarfarbe 
	 * @param raucher 
	 * @param religion 
	 * @return Suchprofil
	 */
	public Suchprofil createSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion);

	/**
	 * Suchprofil aktualisieren.
	 */
	public void saveSuchprofil(int profilId, int suchprofilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException;

	/**
	 * Suchprofil loeschen.
	 */
	public void deleteSuchprofil(int suchprofilId, String suchprofilName) throws IllegalArgumentException;
	
	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId) throws IllegalArgumentException;
	
	/**
	 * Suchprofil anhand des Suchprofilnamens auslesen.
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName) throws IllegalArgumentException;

	/**
	 * Suchprofilname beim Anlegen eines Suchprofils ueberpruefen. 
	 */
	public int pruefeSuchprofilnameCreate(int profilId, String suchprofilname) throws IllegalArgumentException; 
	
	/**
	 * Suchprofilname beim Editieren eines Suchprofils ueberpruefen. 
	 */
	public int pruefeSuchprofilnameEdit(int profilId, int suchprofilId, String suchprofilname) throws IllegalArgumentException;

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
	 * Alle gemerkten Nutzerprofile eines Nutzers auslesen.
	 * @return Merkliste-Objekt
	 * @throws IllegalArgumentException
	 */
	public Merkliste getGemerkteNutzerprofileFor(int profilId) throws IllegalArgumentException;

	/**
	 * Vermerkstatus pruefen.
	 * @param profilId Profil-ID 
	 * @param fremdprofilId Fremdprofil-ID
	 * @return int Vermerkstatus
	 * @throws IllegalArgumentException
	 */
	public int pruefeVermerkstatus(int profilId, int fremdprofilId) throws IllegalArgumentException;
	
	/**
	 * Vermerkstatus aendern.
	 * @param fremdprofilId Fremdprofil-ID
	 * @return int Vermerkstatus
	 * @throws IllegalArgumentException
	 */
	public int vermerkstatusAendern(int profilId, int fremdprofilId) throws IllegalArgumentException;

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
	 * Alle gesperrten Nutzerprofile eines Nutzers auslesen.
	 * @return Sperrliste-Objekt
	 * @throws IllegalArgumentException
	 */
	public Sperrliste getGesperrteNutzerprofileFor(int profilId) throws IllegalArgumentException;
	
	/**
	 * Pruefen, ob Fremdprofil von Nutzer gesperrt wurde. 
	 * @param fremdprofilId Fremdprofil-ID
	 * @return int Sperrstatus
	 * @throws IllegalArgumentException
	 */
	public int pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId) throws IllegalArgumentException;
		
	/**
	 * Pruefen, ob Nutzer von Fremdprofil gesperrt wurde. 
	 * @param fremdprofilId Fremdprofil-ID
	 * @return int Sperrstatus
	 * @throws IllegalArgumentException
	 */
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId) throws IllegalArgumentException;	
	
	/**
	 * Sperrstatus aendern.
	 * @param fremdprofilId Fremdprofil-ID
	 * @return int Sperrstatus
	 * @throws IllegalArgumentException
	 */
	public int sperrstatusAendern(int profilId, int fremdprofilId) throws IllegalArgumentException;

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
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId) throws IllegalArgumentException;

	// Besuch setzen.
	public void besuchSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Aehnlichkeit berechnen
	public void berechneAehnlichkeitNpFor(int profilId) throws IllegalArgumentException;


	// Aehnlichkeit aus DB loeschen
	public void aehnlichkeitEntfernen(int profilId) throws IllegalArgumentException;

	// Ausgabe der Partnervorschlaege

	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId) throws IllegalArgumentException;
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
		public void berechneAehnlichkeitSpFor(int profilId)
		throws IllegalArgumentException;
		
		
		//Aehnlichkeit aus DB loeschen
		public void aehnlichkeitEntfernenSp (int profilId) throws IllegalArgumentException;
		
		
		
		//Ausgabe der Partnervorschlaege
		public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId, 
				String suchprofilName) throws IllegalArgumentException;
	
	
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
	
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften(int profilId) 	
			throws IllegalArgumentException;	
	
	public int createInfo(int profilId, List<Info> infos)
			throws IllegalArgumentException;
	
	public Map<List<Info>, List<Eigenschaft>> getAllInfos(int profilId) throws IllegalArgumentException;
	
	public List<Info> getAllInfosNeuReport(int profilId)
			throws IllegalArgumentException;

	public int deleteAllInfosNeu(int profilId)
			throws IllegalArgumentException;
	
	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException;
	
	public int saveInfo(int profilId, List<Info> listI)
			throws IllegalArgumentException;
	
	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException;
	
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException;

	public String getEigenschaftstextById(int eigenschaftId) throws IllegalArgumentException;

	

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */

	public boolean isUserRegistered(String userEmail);


	public Nutzerprofil login(String requestUri) throws Exception;
}

