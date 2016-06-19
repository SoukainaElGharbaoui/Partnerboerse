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


/**
 * Synchrone Schnittstelle f�r die Verwaltung der Partnerboerse.
 * 
 */
@RemoteServiceRelativePath("partnerboerseadministration")
public interface PartnerboerseAdministration extends RemoteService {

	/**
	 * Initialisierung des Objekts.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */

	/**
	 * Pruefen, ob der Nutzer eingeloggt ist.
	 * 
	 * @param userEmail
	 * @return Boolscher Wert, er zeigt ob der Nutzer eingeloggt ist.
	 * @throws IllegalArgumentException 
	 */
	
	public boolean isUserRegistered(String userEmail) throws IllegalArgumentException;

	/**
	 * URL zum Einloggen anfordern.
	 * 
	 * @param requestUri
	 * @return Nutzerprofil-Objekt, welches eingeloggt ist.
	 * @throws IllegalArgumentException 
	 */
	public Nutzerprofil login(String requestUri) throws IllegalArgumentException;
	
	/**
	 * Pruefen, ob der Nutzer in der Datenbank schon existiert.
	 * 
	 * @param userEmail
	 * @return Boolscher Wert, der zeigt ob der Nutzer bereits in der Datenbank existiert.
	 * @throws IllegalArgumentException 
	 */
	public boolean pruefeObNutzerNeu(String userEmail) throws IllegalArgumentException;


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
	 * Ein Nutzerprofil-Objekt anlegen.
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
	 * @return Nutzerprofil-Objekt, welches angelegt wurde.
	 * @throws IllegalArgumentException
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion,
			String emailAddress) throws IllegalArgumentException;

	/**
	 * Ein Nutzerprofil-Objekt aktualisieren.
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
	 * @throws IllegalArgumentException
	 */
	public void saveNutzerprofil(int profilId, String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException;

	/**
	 * Ein Nutzerprofil-Objekt loeschen.
	 * 
	 * @param profilId
	 * @throws IllegalArgumentException
	 */
	void deleteNutzerprofil(int profilId) throws IllegalArgumentException;

	/**
	 * Ein Nutzerprofil-Objekt anhand der Profil-ID auslesen.
	 * 
	 * @param profilId
	 * @return Nutzerprofil-Objekt, welches ausgelesen wird
	 * @throws IllegalArgumentException
	 */
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException;

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
	 * Ein Suchprofil-Objekt anlegen.
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
	 * @return Suchprofil-Objekt, welches angelegt wurde.
	 * @throws IllegalArgumentException
	 */
	public Suchprofil createSuchprofil(int profilId, String suchprofilName,
			String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher,
			String religion) throws IllegalArgumentException;

	/**
	 * Suchprofil-Objekt aktualisieren.
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
	 * @throws IllegalArgumentException
	 */
	public void saveSuchprofil(int profilId, int suchprofilId,
			String suchprofilName, String geschlecht, int alterMinInt,
			int alterMaxInt, int koerpergroesseInt, String haarfarbe,
			String raucher, String religion) throws IllegalArgumentException;

	/**
	 * Suchprofil-Objekt loeschen.
	 * 
	 * @param suchprofilId
	 * @param suchprofilName
	 * @throws IllegalArgumentException
	 */
	public void deleteSuchprofil(int suchprofilId, String suchprofilName)
			throws IllegalArgumentException;

	/**
	 * Alle Suchprofil-Objekte eines Nutzers auslesen.
	 * 
	 * @param profilId
	 * @return Liste von Suchprofil-Objekten, welche ausgelesen werden
	 * @throws IllegalArgumentException
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * Ein Suchprofil-Objekt anhand der Profil-ID und des Suchprofilnamens auslesen.
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @return Suchprofil-Objekt, welches ausgelesen wird
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName)
			throws IllegalArgumentException;
	/**
	 * Ein Suchprofil-Objekt anhand der Profil-ID und der Suchprofil-ID auslesen.
	 * @param profilId
	 * @param suchprofilId
	 * @return Suchprofil-Objekt, welches ausgelesen wird.
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilById (int suchprofilId)
			throws IllegalArgumentException;

	/**
	 * Suchprofilname beim Anlegen eines Suchprofil-Objekt pruefen.
	 * 
	 * @param profilId
	 * @param suchprofilname
	 * @return Ergebnis, welches zeigt ob der Suchprofilname bereits existiert oder leer ist.
	 * @throws IllegalArgumentException
	 */
	public int pruefeSuchprofilnameCreate(int profilId, String suchprofilname)
			throws IllegalArgumentException;

	/**
	 * Suchprofilname beim Editieren eines Suchprofil-Objekt pruefen.
	 * 
	 * @param profilId
	 * @param suchprofilId
	 * @param suchprofilname
	 * @return Ergebnis, ob der Suchprofilname bereits existiert oder leer ist.
	 * @throws IllegalArgumentException
	 */
	public int pruefeSuchprofilnameEdit(int profilId, int suchprofilId,
			String suchprofilname) throws IllegalArgumentException;

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
	 * 
	 * @param profilId
	 * @return Merkliste-Objekt, Liste der ausgelesenen Nutzerprofile
	 * @throws IllegalArgumentException
	 */
	public Merkliste getGemerkteNutzerprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * Vermerkstatus pruefen.
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @return Status, ob bereits ein Vermerk vorliegt oder nicht.
	 * @throws IllegalArgumentException
	 */
	public int pruefeVermerkstatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * Vermerkstatus aendern.
	 * 
	 * @param profilId 
	 * @param fremdprofilId
	 * @return Status, ob bereits ein Vermerk vorliegt oder nicht.
	 * @throws IllegalArgumentException
	 */
	public int vermerkstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

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
	 * 
	 * @param profilId
	 * @return Sperrliste-Objekt, Liste der gespertten Nutzerprofile
	 * @throws IllegalArgumentException
	 */
	public Sperrliste getGesperrteNutzerprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * Pruefen, ob Fremdprofil von Nutzer gesperrt wurde.
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @return Status, ob Fremdprofil von Nutzer gesperrt wurde.
	 * @throws IllegalArgumentException
	 */
	public int pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * Pruefen, ob Nutzer von Fremdprofil gesperrt wurde.
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @return Status, ob Nutzer von Fremdprofil gesperrt wurde.
	 * @throws IllegalArgumentException
	 */
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * Sperrstatus aendern.
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @return Status, ob Fremdprofil von Nutzer gesperrt wurde.
	 * @throws IllegalArgumentException
	 */
	public int sperrstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

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
	 * Alle unangesehenen Nutzerprofile eines Nutzers auslesen.
	 * 
	 * @param profilId
	 * @return Liste von Nutzerprofil-Objekten
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException;

	/**
	 * Besuch setzen.
	 * 
	 * @param profilId
	 * @param fremdprofilId
	 * @throws IllegalArgumentException
	 */
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * Aehnlichkeit zwischen einem den Profildaten und Infos eines Nutzerprofil und den Profildaten 
	 * und Infos anderer Nutzerprofile berechnen.
	 * 
	 * @param profilId
	 * @throws IllegalArgumentException
	 */
	public void berechneAehnlichkeitNpFor(int profilId) throws IllegalArgumentException;

	/**
	 * Alle unangesehenen Partnervorschlaege fuer einen Nutzer auslesen.
	 * Es werden nur diejenigen Nutzerprofile ausgelesen, von denen 
	 * der Nutzer nicht gesperrt wurde. 
	 * 
	 * @param profilId
	 * @return Liste von Nutzerprofil-Objekten
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId) throws IllegalArgumentException;
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlaegeNp
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ABSCHNITT, Beginn: PartnervorschlaegeSp
	 * ***************************************************************************
	 */

	/**
	 * 
	 * Aehnlichkeit zwischen einem Suchprofil eines Nutzers und den Profildaten 
	 * und Infos anderer Nutzerprofile berechnen.
	 *  
	 * @param profilId
	 * @throws IllegalArgumentException
	 */
	public void berechneAehnlichkeitSpFor(int profilId)
			throws IllegalArgumentException;



	/**
	 * Alle Partnervorschlaege anhand von Suchprofilen fuer einen Nutzer auslesen.
	 * Es werden nur diejenigen Nutzerprofile ausgelesen, von denen der Nutzer 
	 * nicht gesperrt wurde. 
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @return Liste von Nutzerprofil-Objekten
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId,
			String suchprofilName) throws IllegalArgumentException;


	
	
	
	/*
	 * **************************************************************************
	 * ABSCHNITT, Ende: PartnervorschlaegeSp
	 * **************************************************************************
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */

	/**
	 * Alle Eigenschaften auslesen.
	 * 
	 * @return Liste von Beschreibungs- und Auswahleigenschaft-Objekte, welche ausgelesen werden
	 * @throws IllegalArgumentException
	 */
	
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllEigenschaften()
			throws IllegalArgumentException;

	/**
	 * Alle Auswahleigenschaften auslesen.
	 * 
	 * @param listE
	 * @return Liste von Auswahleigenschaft-Objekte, welche ausgelesen werden
	 * @throws IllegalArgumentException
	 */
	public List<Auswahleigenschaft> getAuswahleigenschaften(
			List<Eigenschaft> listE) throws IllegalArgumentException;

	/**
	 * 
	 * Auslesen aller Beschreibungs- und Auswahleigenschaften die vom Nutzer noch nicht als Info angelegt sind.
	 * 
	 * @param profilId
	 * @return Liste von Beschreibungs- und Auswahleigenschaft-Objekte, die ausgelesen werden
	 * @throws IllegalArgumentException
	 */
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften(
			int profilId) throws IllegalArgumentException;

	/**
	 * Info-Objekte anlegen.
	 * 
	 * @param profilId
	 * @param infos
	 * @return Info-objekt, welches angelegt wird.
	 * @throws IllegalArgumentException
	 */
	public List<Info> createInfo(int profilId, List<Info> infos)
			throws IllegalArgumentException;

	/**
	 * Alle Info-Objekte eines Nutzers auslesen.
	 * 
	 * @param profilId
	 * @return Liste von Info- und Eigenschaft-Objekte, die ausgelesen werden
	 * @throws IllegalArgumentException
	 */
	public Map<List<Info>, List<Eigenschaft>> getAllInfos(int profilId)
			throws IllegalArgumentException;

	public void deleteAllInfosNeu(int profilId) throws IllegalArgumentException;

	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException;

	public void saveInfo(int profilId, List<Info> listI)
			throws IllegalArgumentException;

	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException;

	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException;


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
	 * Beschreibungseigenschaft-Objekt anlegen. Fuer den Administrator.
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param beschreibungstext
	 * @return Beschreibungseigenschaft-Objekt, welches angelegt wird
	 * @throws IllegalArgumentException
	 */
	public Beschreibungseigenschaft createBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext) throws IllegalArgumentException;
	
	/**
	 * Auswahleigenschaft-Objekt anlegen. Fuer den Administrator.
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param auswahloptionen
	 * @return Auswahleigenschaft-Objekt, welches angelegt wird
	 * @throws IllegalArgumentException
	 */
	public Auswahleigenschaft createAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloptionen) throws IllegalArgumentException;
	
	/**
	 * Beschreibungseigenschaft-Objekt aktualisieren.
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param beschreibungstext
	 * @throws IllegalArgumentException
	 */
	public void saveBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext)
			throws IllegalArgumentException;
	
	/**
	 * Auswahleigenschaft-Objekt aktualisieren.
	 * 
	 * @param eigenschaftId
	 * @param erlaeuterung
	 * @param typ
	 * @param auswahloptionen
	 * @throws IllegalArgumentException
	 */
	public void saveAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloptionen)
			throws IllegalArgumentException;
	
	/**
	 * Beschreibungseigenschaft-Objekt loeschen.
	 * 
	 * @param eigenschaftId
	 * @throws IllegalArgumentException
	 */
	void deleteBeschreibungseigenschaft(int eigenschaftId) throws IllegalArgumentException;
	
	/**
	 * Auswahleigenschaft-Objekt loeschen.
	 * 
	 * @param eigenschaftId
	 * @throws IllegalArgumentException
	 */
	void deleteAuswahleigenschaft(int eigenschaftId) throws IllegalArgumentException;
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Administrator-Funktionen
	 * *************************************************************************
	 * **
	 */
	
}
