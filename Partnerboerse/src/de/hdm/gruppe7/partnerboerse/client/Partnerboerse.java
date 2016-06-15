package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class Partnerboerse implements EntryPoint {
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();

	private VerticalPanel loginPanel = new VerticalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();
	private PartnerboerseAdministrationAsync partnerboerseAdministration;

	/**
	 * Diese Klasse sichert die Implementierung des Interface
	 * <code>EntryPoint</code>. Daher benötigen wir die Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */

	public void onModuleLoad() {

		/**
		 * Das VerticalPanel wird einem DIV-Element names "Navigator" in der
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen
		 * Darstellungsort.
		 */

		partnerboerseAdministration = GWT.create(PartnerboerseAdministration.class);

		try {
			ClientsideSettings.getPartnerboerseAdministration().login(GWT.getHostPageBaseURL() + "Partnerboerse.html",
					new AsyncCallback<Nutzerprofil>() {

						public void onFailure(Throwable caught) {
							RootPanel.get().add(new Label(caught.toString()));
						}

						public void onSuccess(Nutzerprofil result) {
							// wenn der user eingeloggt ist
							if (result.isLoggedIn()) {

								if (result.getEmailAddress() != null) {
									ClientsideSettings.setAktuellerUser(result);

									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText(
											"Als " + result.getVorname() + result.getProfilId() + " ausloggen");
									loginPanel.add(signOutLink);
									RootPanel.get("Navigator").add(new Navigator());
									RootPanel.get("Navigator").add(loginPanel);
								}

								if (result.getEmailAddress() == null) {
									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText("Als " + result.getVorname() + " ausloggen");
									loginPanel.add(signOutLink);
									RootPanel.get("Navigator").add(new Navigator());
									RootPanel.get("Navigator").add(loginPanel);
									RootPanel.get("Details").add(new CreateNutzerprofil());
								}
							}

							// wenn der user nicht eingeloggt ist
							if (!result.isLoggedIn()) {
								signInLink.setHref(result.getLoginUrl());
								loginPanel.add(signInLink);
								RootPanel.get("Navigator").add(loginPanel);
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
