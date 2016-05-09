package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzerprofil(String vorname, String nachname,
			String geburtsdatum, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion,
			AsyncCallback<Nutzerprofil> callback);
	
	void updateNutzerprofil(String vorname, AsyncCallback<Nutzerprofil> callback);
	
//	void updateNutzerprofil(String vorname, String nachname,
//			String geburtsdatum, String geschlecht, String haarfarbe,
//			String koerpergroesse, String raucher, String religion,
//			AsyncCallback<Nutzerprofil> callback);

	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);

	void save(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void delete(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void getAngeseheneNpFor(Nutzerprofil nutzerprofil,
			AsyncCallback<List<Nutzerprofil>> callback);

	/**
	 * ABSCHNITT MERKLISTE: BEGINN
	 */

	void getGemerkteNutzerprofileFor(int profilId, AsyncCallback<Vector<Merkliste>> callback);

	void vermerkLoeschen(int profilId, int mFremdprofilId, AsyncCallback<Void> callback);

	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */

	/**
	 * ABSCHNITT SUCHPROFIL: BEGINN
	 */
	void createSuchprofil(String alterMin, String alterMax, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion,
			AsyncCallback<Suchprofil> callback);
	
	
	
	
	void save(Suchprofil suchprofil, AsyncCallback<Void> callback);

	void delete(Suchprofil suchprofil, AsyncCallback<Void> callback);

	void getAllSuchprofile(AsyncCallback<List<Suchprofil>> callback);

	void getSuchprofilById(int profilId, AsyncCallback<Suchprofil> callback);

		
	/**
	 * ABSCHNITT SUCHPROFIL: ENDE
	 */
	}
	
