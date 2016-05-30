
package de.hdm.gruppe7.partnerboerse.shared;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;


import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzerprofil(String vorname, String nachname,
			String geschlecht, String geburtsdatum, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion,
			AsyncCallback<Nutzerprofil> callback);
	
	void saveNutzerprofil(String vorname, String nachname,
			String geschlecht, String geburtsdatum, int koerpergroesseInt, String haarfarbe, 
			String raucher, String religion, AsyncCallback<Void> callback);
	


	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);
	
	void getAllNutzerprofile(Nutzerprofil nutzerprofil, AsyncCallback<List<Nutzerprofil>> callback );

//	void save(String vorname, String nachname, String geschlecht, 
//			String haarfarbe,String koerpergroesse, String raucher, 
//			String religion, String geburtsdatum, AsyncCallback<Void> callback);
	
	

	void deleteNutzerprofil(int profilId, AsyncCallback<Void> callback);

	void getAngeseheneNpFor(Nutzerprofil nutzerprofil,
			AsyncCallback<List<Nutzerprofil>> callback);
	
	void getFremdprofilById (int fremdprofilId, AsyncCallback<Nutzerprofil> callback);

	/**
	 * ABSCHNITT MERKLISTE: BEGINN
	 */
	// Alle Vermerke eines Nutzerprofils auslesen. 
	void getGemerkteNutzerprofileFor(int profilId, AsyncCallback<Vector<Merkliste>> callback);
	
	// Vermerkstatus ermitteln. 
	void getVermerkstatus(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);
	
	// Vermerk einfügen. 
	void vermerkSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);

	// Vermerk löschen. 
	void vermerkLoeschen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */
	
	/**
	 * ABSCHNITT SPERRLISTE: BEGINN
	 */
	// Alle Sperrungen eines Nutzerprofils auslesen. 
	void getGesperrteNutzerprofileFor(int profilId, AsyncCallback<Vector<Sperrliste>> callback);
	
	// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde. 
	void getSperrstatusFremdprofil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);
	
	// Prüfen, ob Benutzer von Fremdprofil gesperrt wurde. 
	void getSperrstatusEigenesProfil(int profilId, int fremdprofilId, AsyncCallback<Integer> callback);
	
	// Sperrung einfügen. 
	void sperrungSetzen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	
	// Sperrung löschen. 
	void sperrungLoeschen(int profilId, int fremdprofilId, AsyncCallback<Void> callback);
	/**
	 * ABSCHNITT SPERRLISTE: ENDE
	 */

	/**
	 * ABSCHNITT SUCHPROFIL: BEGINN
	 */
	void createSuchprofil(String geschlecht, int alterMinInt, int alterMaxInt, 
			int koerpergroesseInt, String haarfarbe, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);
	
	void saveSuchprofil(String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, 
			AsyncCallback<Void> callback);

	void deleteSuchprofil(int profilId, AsyncCallback<Void> callback);

	void getAllSuchprofile(AsyncCallback<List<Suchprofil>> callback);

	void getSuchprofilById(int profilId, AsyncCallback<Suchprofil> callback);




	/**
	 * ABSCHNITT SUCHPROFIL: ENDE
	 */	
	
	
	/**
	 * ABSCHNITT Info: BEGINN
	 */
	
	
	void getAllEigenschaftenNeu(AsyncCallback<List<Eigenschaft>> callback);
	
	void createInfoNeu(int profilId, int eigenschaftId, String infotext, 
			AsyncCallback<Info> callback);
	
	void getAllInfosNeu(int profilId,
			AsyncCallback<List<String>> callback);
	

	void deleteOneInfoNeu(int nutzerprofilId, int eigenschaftId,
			AsyncCallback<Void> callback);

	
	void getEigAById(int eigenschaftId,
			AsyncCallback<Auswahleigenschaft> callback);
	
	
	
	/**
	 * ABSCHNITT Info: ENDE
	 */	
  
	void getAllProfile(AsyncCallback<List<Nutzerprofil>> callback);


	void besuchSetzen(int profilId, int fremdprofilId,
			AsyncCallback<Void> callback);
	
	void getUnangeseheneNutzerprofile(int profilId, AsyncCallback<List<Nutzerprofil>> callback);

	


}
	
