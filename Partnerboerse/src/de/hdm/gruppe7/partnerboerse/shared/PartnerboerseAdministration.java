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
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */

	/**
	 * Pruefen, ob der Nutzer eingeloggt ist.
	 * 
	 * @param userEmail
	 * @return Boolscher Wert, ob Nutzer eingeloggt ist.
	 */
	public boolean isUserRegistered(String userEmail);

	/**
	 * URL zum Einloggen anfordern.
	 * 
	 * @param requestUri
	 * @return Nutzerprofil-Objekt
	 * @throws Exception
	 */
	public Nutzerprofil login(String requestUri) throws Exception;
	
	/**
	 * Pruefen, ob der Nutzer in der Datenbank noch nicht existiert.
	 * 
	 * @param userEmail
	 * @return Boolscher WErt, ob Nutzer bereits in der Datenbank existiert.
	 * @throws Exception
	 */
	public boolean pruefeObNutzerNeu(String userEmail) throws Exception;


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
	 * Nutzerprofil anlegen.
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
	 * @return Nutzerprofil-Objekt
	 * @throws IllegalArgumentException
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion,
			String emailAddress) throws IllegalArgumentException;

	/**
	 * Nutzerprofil aktualisieren.
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
	 * Nutzerprofil loeschen.
	 * 
	 * @param profilId
	 * @throws IllegalArgumentException
	 */
	void deleteNutzerprofil(int profilId) throws IllegalArgumentException;

	/**
	 * Nutzerprofil anhand der Profil-ID auslesen.
	 * 
	 * @param profilId
	 * @return Nutzerprofil-Objekt
	 * @throws IllegalArgumentException
	 */
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException;

	/**
	 * Fremdprofil anhand der Profil-ID auslesen.
	 * 
	 * @param fremdprofilId
	 * @return Nutzerprofil-Objekt
	 * @throws IllegalArgumentException
	 */
	public Nutzerprofil getFremdprofilById(int fremdprofilId)
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
	 * Suchprofil anlegen.
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
	 * @return Suchprofil-Objekt
	 * @throws IllegalArgumentException
	 */
	public Suchprofil createSuchprofil(int profilId, String suchprofilName,
			String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher,
			String religion) throws IllegalArgumentException;

	/**
	 * Suchprofil aktualisieren.
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
	 * Suchprofil loeschen.
	 * 
	 * @param suchprofilId
	 * @param suchprofilName
	 * @throws IllegalArgumentException
	 */
	public void deleteSuchprofil(int suchprofilId, String suchprofilName)
			throws IllegalArgumentException;

	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 * 
	 * @param profilId
	 * @return Liste von Suchprofil-Objekten
	 * @throws IllegalArgumentException
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * Suchprofil anhand der Profil-ID und des Suchprofilnamens auslesen.
	 * 
	 * @param profilId
	 * @param suchprofilName
	 * @return Suchprofil-Objekt
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName)
			throws IllegalArgumentException;
	/**
	 * Suchprofil anhand der Profil-ID und der Suchprofil-ID auslesen.
	 * @param profilId
	 * @param suchprofilId
	 * @return Suchprofil-Objekt
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilById (int profilId, int suchprofilId)
			throws IllegalArgumentException;

	/**
	 * Suchprofilname beim Anlegen eines Suchprofils pruefen.
	 * 
	 * @param profilId
	 * @param suchprofilname
	 * @return Ergebnis, ob der Suchprofilname bereits existiert oder leer ist.
	 * @throws IllegalArgumentException
	 */
	public int pruefeSuchprofilnameCreate(int profilId, String suchprofilname)
			throws IllegalArgumentException;

	/**
	 * Suchprofilname beim Editieren eines Suchprofils pruefen.
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
	 * @return Merkliste-Objekt
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
	 * @return Sperrliste-Objekt
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
	 * @param profilId
	 * @return Liste von Nutzerprofil-Objekten
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException;

	/**
	 * Besuch setzen.
	 * @param profilId
	 * @param fremdprofilId
	 * @throws IllegalArgumentException
	 */
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * Aehnlichkeit zwischen einem den Profildaten und Infos eines Nutzerprofil und den Profildaten 
	 * und Infos anderer Nutzerprofile berechnen.
	 * @param profilId
	 * @throws IllegalArgumentException
	 */
	public void berechneAehnlichkeitNpFor(int profilId) throws IllegalArgumentException;

	/**
	 * Alle unangesehenen Partnervorschlaege fuer einen Nutzer auslesen.
	 * Es werden nur diejenigen Nutzerprofile ausgelesen, von denen 
	 * der Nutzer nicht gesperrt wurde. 
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
	 * @param profilId
	 * @throws IllegalArgumentException
	 */
	public void berechneAehnlichkeitSpFor(int profilId)
			throws IllegalArgumentException;



	/**
	 * Alle Partnervorschlaege anhand von Suchprofilen fuer einen Nutzer auslesen.
	 * Es werden nur diejenigen Nutzerprofile ausgelesen, von denen der Nutzer 
	 * nicht gesperrt wurde. 
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

	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllEigenschaften()
			throws IllegalArgumentException;

	public List<Auswahleigenschaft> getAuswahleigenschaften(
			List<Eigenschaft> listE) throws IllegalArgumentException;

	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften(
			int profilId) throws IllegalArgumentException;

	public int createInfo(int profilId, List<Info> infos)
			throws IllegalArgumentException;

	public Map<List<Info>, List<Eigenschaft>> getAllInfos(int profilId)
			throws IllegalArgumentException;

	public List<Info> getAllInfosNeuReport(int profilId)
			throws IllegalArgumentException;

	public int deleteAllInfosNeu(int profilId) throws IllegalArgumentException;

	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException;

	public int saveInfo(int profilId, List<Info> listI)
			throws IllegalArgumentException;

	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException;

	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException;

	public String getEigenschaftstextById(int eigenschaftId)
			throws IllegalArgumentException;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */
}
