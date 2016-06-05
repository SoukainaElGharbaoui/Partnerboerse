package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
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
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress) throws IllegalArgumentException;


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
	 * Suchprofil l�schen.
	 */
	public void deleteSuchprofil(String suchprofilName) throws IllegalArgumentException;

	/**
	 * Suchprofil anhand der Profil-ID auslesen. (EVTL NICHT NOTWENDIG)
	 */
	public Suchprofil getSuchprofilById(int profilId) throws IllegalArgumentException;

	/**
	 * Suchprofil anhand der Profil-ID UND des Namens auslesen. (�BERARBEITET VON MILENA - NOTWENIG)
	 */
	public Suchprofil getSuchprofilByName(String suchprofilName) throws IllegalArgumentException;
	
	/**
	 * Existenz des Suchprofilnamens beim Anlegen �berpr�fen.
	 */
	public int pruefeSuchprofilname(String suchprofilname) throws IllegalArgumentException;
	
	/**
	 * Existenz des Suchprofilnamens beim Editieren �berpr�fen.
	 */
	public String pruefeSuchprofilnameEdit(int suchprofilId) throws IllegalArgumentException;
		
	/**
	 * Alle Suchprofile auslesen. (EVTL NICHT NOTWENDIG)
	 */
	public List<Suchprofil> getAllSuchprofile() throws IllegalArgumentException;

	/**
	 * Alle Suchprofile EINES NUTZERS auslesen. (�BERARBEITET VON MILENA -
	 * NOTWENIG)
	 */
	public List<Suchprofil> getAllSuchprofileFor() throws IllegalArgumentException;
	
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
	public Merkliste getGemerkteNutzerprofileFor() throws IllegalArgumentException;

	// Vermerkstatus ermitteln.
	public int getVermerkstatus(int fremdprofilId) throws IllegalArgumentException;

	// Vermerk einf�gen.
	public void vermerkSetzen(int fremdprofilId) throws IllegalArgumentException;


	// Vermerk l�schen.
	public void vermerkLoeschen(int fremdprofilId) throws IllegalArgumentException;


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

	// Pr�fen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int getSperrstatusFremdprofil(int fremdprofilId) throws IllegalArgumentException;


	// Pr�fen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int fremdprofilId) throws IllegalArgumentException;


	// Sperrung einf�gen.

	public void sperrungSetzen(int fremdprofilId) throws IllegalArgumentException;


	// Sperrung l�schen.
	public void sperrungLoeschen(int fremdprofilId) throws IllegalArgumentException;

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
	public int berechneAehnlichkeitNpFor(int fremdprofilId) throws IllegalArgumentException;

	// Aehnlichkeit in DB speichern
	public void aehnlichkeitSetzen(int fremdprofilId, int aehnlichkeit) throws IllegalArgumentException;

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
		public int berechneAehnlichkeitSpFor(int suchprofilId, int fremdprofilId)
		throws IllegalArgumentException;
		
		//Aehnlichkeit in DB speichern
		public void aehnlichkeitSetzenSp (int suchprofilId, String suchprofilName, int fremdprofilId, int aehnlichkeitSp) throws IllegalArgumentException;
		
		//Aehnlichkeit aus DB loeschen
		public void aehnlichkeitEntfernenSp () throws IllegalArgumentException;
		
		// Alle Nutzerprofile die mich nicht gesperrt haben auslesen
		public List<Nutzerprofil> getNutzerprofileOhneGesetzteSperrung() throws IllegalArgumentException;
		
//		//Ausgabe der Partnervorschlaege
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
	
	public List<Eigenschaft> getAllEigenschaftenNeu()
			throws IllegalArgumentException;
	
	public Info createInfoNeu(int eigenschaftId, String infotext)
			throws IllegalArgumentException;
	
	public List<String> getAllInfosNeu() throws IllegalArgumentException;
	
	public List<String> getAllInfosNeuSp(int suchprofilId) throws IllegalArgumentException;


	public List<Info> getAllInfosNeuReport()
			throws IllegalArgumentException;


	public void deleteAllInfosNeu()
			throws IllegalArgumentException;

	public void deleteOneInfoNeu(int eigenschaftId)
			throws IllegalArgumentException;
	
	public void saveInfoNeu(int eigenschaftId, String infotext)
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
	
	public void setUser(Nutzerprofil n);

}
