package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzerprofil(String vorname, String nachname,
			String geburtsdatum, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion,
			AsyncCallback<Nutzerprofil> callback);

	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);

	void save(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void delete(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void getAngeseheneNpFor(Nutzerprofil nutzerprofil,
			AsyncCallback<List<Nutzerprofil>> callback);

	/**
	 * ABSCHNITT MERKLISTE: BEGINN
	 */

	void getMerkliste(int profilId, AsyncCallback<Vector<Merkliste>> callback);

	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */

}
