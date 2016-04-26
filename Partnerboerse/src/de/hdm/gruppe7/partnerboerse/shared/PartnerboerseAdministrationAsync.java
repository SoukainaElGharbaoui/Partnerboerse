package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzerprofil(String vorname, String nachname, Date geburtsdatum,
			String geschlecht, String Haarfarbe, int koerpergroesse,
			boolean raucher, String religion,
			AsyncCallback<Nutzerprofil> callback);

	void getNutzerprofilById(int profilId, AsyncCallback<Nutzerprofil> callback);

	void save(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void delete(Nutzerprofil nutzerprofil, AsyncCallback<Void> callback);

	void getAngeseheneNpFor(Nutzerprofil nutzerprofil,
			AsyncCallback<List<Nutzerprofil>> callback);

}
