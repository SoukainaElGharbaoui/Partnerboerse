
package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
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
	void saveNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, AsyncCallback<Void> callback);

	/**
	 * Nutzerprofil löschen.
	 */
	void deleteNutzerprofil(int profilId, AsyncCallback<Void> callback);

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen.
	 */
	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);


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
	void createSuchprofil(String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);

	/**
	 * Suchprofil aktualisieren.
	 */
	void saveSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, AsyncCallback<Void> callback);

	/**
	 * Suchprofil löschen.
	 */
	void deleteSuchprofil(int profilId, String suchprofilName, AsyncCallback<Void> callback);

	

	/**
	 * Alle Suchprofile auslesen. (EVTL NICHT NOTWENDIG)
	 */
	void getAllSuchprofile(AsyncCallback<List<Suchprofil>> callback);
	
	/**
	 * Alle Suchprofile EINES NUTZERS auslesen. (ÜBERARBEITET VON MILENA - NOTWENIG)
	 */
	void getAllSuchprofileFor(int profilId, AsyncCallback<List<Suchprofil>> callback); 

	
	/**
	 * Suchprofil anhand der Profil-ID UND des Namens auslesen. (ÜBERARBEITET VON MILENA - NOTWENIG)
	 */
	void getSuchprofilByName(int profilId, String suchprofilName, AsyncCallback<Suchprofil> callback); 
	
	/**
	 * Existens des Suchprofilnamens beim Anlegen überprüfen.
	 */
	void pruefeSuchprofilname(int profilId, String suchprofilname, AsyncCallback<Integer> callback);
	
	/**
	 * Existens des Suchprofilnamens beim Editieren überprüfen.
	 */
	void pruefeSuchprofilnameEdit(int profilId, int suchprofilId, AsyncCallback<String> callback);


	/**
	 * Suchprofil-Report
	 * @param n
	 * @param callback
	 */
	void getAllSuchprofileFor(Nutzerprofil n, AsyncCallback<List<Suchprofil>> callback); 

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
	void getVermerkstatus(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	// Vermerk einfügen.
	void vermerkSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);

	// Vermerk löschen.
	void vermerkLoeschen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);

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

	// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde.
	void getSperrstatusFremdprofil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	// Prüfen, ob Benutzer von Fremdprofil gesperrt wurde.
	void getSperrstatusEigenesProfil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	// Sperrung einfügen.
	void sperrungSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);

	// Sperrung löschen.
	void sperrungLoeschen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
<<<<<<< HEAD
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: PartnervorschlägeNp
	 * ***************************************************************************
=======
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Partnervorschläge
	 * *************************************************************************
	 * **
>>>>>>> refs/heads/master
	 */

	// Alle unangesehenen Nutzerprofile auslesen.
	void getUnangeseheneNutzerprofile(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	// Besuch setzen.
	void besuchSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);

	void berechneAehnlichkeitNpFor(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);

	void aehnlichkeitSetzen(int profilId, int fremdprofilId, int aehnlichkeit, AsyncCallback<Void> callback);

	void aehnlichkeitEntfernen(int profilId, AsyncCallback<Void> callback);

	void getGeordnetePartnervorschlaegeNp(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	/*
<<<<<<< HEAD
	 * ***************************************************************************
	 * ABSCHNITT, Ende: PartnervorschlägeNp
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: PartnervorschlägeSp
	 * ***************************************************************************
	 */
	
	void berechneAehnlichkeitSpFor(int suchprofilId, int fremdprofilId, String suchprofilName,
			AsyncCallback<Integer> callback);

	void aehnlichkeitSetzenSp(int suchprofilId, String suchprofilName,  int fremdprofilId,
			int aehnlichkeitSp,  AsyncCallback<Void> callback);

	void aehnlichkeitEntfernenSp(int suchprofilId, String suchprofilName, AsyncCallback<Void> callback);
	
	// Alle Nutzerprofile die mich nicht gesperrt haben auslesen
		void getNutzerprofileOhneGesetzteSperrung(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	void getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilName,
			AsyncCallback<List<Nutzerprofil>> callback);
	
	
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: PartnervorschlägeSp
	 * ***************************************************************************
=======
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Partnervorschläge
	 * *************************************************************************
	 * **
>>>>>>> refs/heads/master
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */

	void createBeschreibungsinfo(int profilId, int eigenschaftId, String infotext, AsyncCallback<Info> callback);

	void createAuswahlinfo(int profilId, int eigenschaftId, int auswahloptionId, AsyncCallback<Info> callback);

	void saveInfoA(int profilId, int neueAuswahloptionId, int eigenschaftId, AsyncCallback<Void> callback);

	void saveInfoB(int profilId, int eigenschaftId, String infotext, AsyncCallback<Void> callback);

	void getAllEigenschaftenB(AsyncCallback<List<Eigenschaft>> callback);

	void getAllEigenschaftenA(AsyncCallback<List<Eigenschaft>> callback);

	void getAllAuswahloptionen(int eigenschaftId, AsyncCallback<List<Auswahloption>> callback);

	void getAllInfosB(int profilId, AsyncCallback<List<Info>> callback);

	void getAllInfosA(int profilId, AsyncCallback<List<Info>> callback);

	void getOptionById(int eigenschaftId, AsyncCallback<Info> callback);

	void getInfoAById(String optionsbezeichnung, int eigenschaftId, AsyncCallback<Info> callback);

	void deleteAllInfos(int profilId, AsyncCallback<Void> callback);

	void deleteOneInfoB(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	void deleteOneInfoA(int profilId, int eigenschaftId, AsyncCallback<Void> callback);

	void getAInfoByProfilId(int profilId, AsyncCallback<List<Info>> callback);

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */


	void isUserRegistered(String userEmail, AsyncCallback<Boolean> isUserRegisteredCallback);


	void insertEmail(String emailAddress, AsyncCallback<Void> callback);

	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;

}
