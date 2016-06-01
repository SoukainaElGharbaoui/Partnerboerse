package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

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
	 * Nutzerprofil löschen.
	 */
	void deleteNutzerprofil(int profilId) throws IllegalArgumentException;

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen.
	 */
	public Nutzerprofil getNutzerprofilById(int profilId) throws IllegalArgumentException;


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
	 * Suchprofil löschen.
	 */
	public void deleteSuchprofil(int profilId, String suchprofilName) throws IllegalArgumentException;

	/**
	 * Suchprofil anhand der Profil-ID auslesen. (EVTL NICHT NOTWENDIG)
	 */
	public Suchprofil getSuchprofilById(int profilId) throws IllegalArgumentException;

	/**
	 * Suchprofil anhand der Profil-ID UND des Namens auslesen. (ÜBERARBEITET VON MILENA - NOTWENIG)
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName) throws IllegalArgumentException;
	
	/**
	 * Existenz des Suchprofilnamens beim Anlegen überprüfen.
	 */
	public int pruefeSuchprofilname(int profilId, String suchprofilname) throws IllegalArgumentException;
	
	/**
	 * Existenz des Suchprofilnamens beim Editieren überprüfen.
	 */
	public String pruefeSuchprofilnameEdit(int profilId, int suchprofilId) throws IllegalArgumentException;
		
	/**
	 * Alle Suchprofile auslesen. (EVTL NICHT NOTWENDIG)
	 */
	public List<Suchprofil> getAllSuchprofile() throws IllegalArgumentException;

	/**
	 * Alle Suchprofile EINES NUTZERS auslesen. (ÜBERARBEITET VON MILENA -
	 * NOTWENIG)
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId) throws IllegalArgumentException;
	
	/**
	 * Suchprofil-Report
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */
	public List<Suchprofil> getAllSuchprofileFor(Nutzerprofil n) throws IllegalArgumentException;
	
	
	

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
	public Merkliste getGemerkteNutzerprofileFor(int profilId) throws IllegalArgumentException;

	// Vermerkstatus ermitteln.
	public int getVermerkstatus(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Vermerk einfügen.
	public void vermerkSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Vermerk löschen.
	public void vermerkLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException;

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
	public Sperrliste getGesperrteNutzerprofileFor(int profilId) throws IllegalArgumentException;

	// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int getSperrstatusFremdprofil(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Prüfen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Sperrung einfügen.
	public void sperrungSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Sperrung löschen.
	public void sperrungLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException;
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlägeNp
	 * *************************************************************************
	 * **
	 */

	// Alle unangesehenen Nutzerprofile auslesen.
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId) throws IllegalArgumentException;

	// Besuch setzen.
	public void besuchSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Aehnlichkeit berechnen
	public int berechneAehnlichkeitNpFor(int profilId, int fremdprofilId) throws IllegalArgumentException;

	// Aehnlichkeit in DB speichern
	public void aehnlichkeitSetzen(int profilId, int fremdprofilId, int aehnlichkeit) throws IllegalArgumentException;

	// Aehnlichkeit aus DB loeschen
	public void aehnlichkeitEntfernen(int profilId) throws IllegalArgumentException;

	// Ausgabe der Partnervorschlaege
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId) throws IllegalArgumentException;
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlägeNp
	 * *************************************************************************
	 * **
	 * 
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: PartnervorschlÃ¤geSp
	 * ***************************************************************************
	 */
	
	//Aehnlichkeit berechnen
		public int berechneAehnlichkeitSpFor(int suchprofilId, int fremdprofilId)
		throws IllegalArgumentException;
		
		//Aehnlichkeit in DB speichern
		public void aehnlichkeitSetzenSp (int nutzerprofilId, int suchprofilId, String suchprofilName, int fremdprofilId, int aehnlichkeitSp) throws IllegalArgumentException;
		
		//Aehnlichkeit aus DB loeschen
		public void aehnlichkeitEntfernenSp (int nutzerprofilId) throws IllegalArgumentException;
		
		// Alle Nutzerprofile die mich nicht gesperrt haben auslesen
		public List<Nutzerprofil> getNutzerprofileOhneGesetzteSperrung(int profilId) throws IllegalArgumentException;
		
//		//Ausgabe der Partnervorschlaege
		public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilName) throws IllegalArgumentException;
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: PartnervorschlÃ¤geSp
	 * ***************************************************************************
	 */


	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */
	
	public List<Eigenschaft> getAllEigenschaftenNeu()
			throws IllegalArgumentException;
	
	public Info createInfoNeu(int profilId, int eigenschaftId, String infotext)
			throws IllegalArgumentException;
	
	public List<String> getAllInfosNeu(int profilId)
			throws IllegalArgumentException;

	public void deleteAllInfosNeu(int profilId)
			throws IllegalArgumentException;

	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException;
	
	public void saveInfoNeu(int profilId, int eigenschaftId, String infotext)
				throws IllegalArgumentException;

	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException;
	
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException;

	
	
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

}
