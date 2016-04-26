package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzerprofil(String vorname, String nachname, Date geburtsdatum,
			String geschlecht, String Haarfarbe, String koerpergroesse,
			boolean raucher, String religion,
			AsyncCallback<Nutzerprofil> callback);

}
