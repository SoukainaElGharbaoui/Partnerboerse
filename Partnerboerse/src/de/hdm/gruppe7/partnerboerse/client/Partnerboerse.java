package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class Partnerboerse implements EntryPoint {

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	private Label begrueßen = new Label(
			"Herzlich Willkommen bei LonelyHearts. ");
	private Label begrueßen2 = new Label(
			"Bitte melde dich an, um die Seite nutzen zu können.");

	private Label begrueßenN = new Label(
			"Herzlich Willkommen bei LonelyHearts. ");
	private Label begrueßenN2 = new Label(
			"Klicke dich nun durch die Webseite und finde andere LonelyHearts");

	private HorizontalPanel loginPanel = new HorizontalPanel();
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

		begrueßen.setStyleName("welcome-label");
		begrueßen2.setStyleName("welcome-label2");
		begrueßenN.setStyleName("welcome-label");
		begrueßenN2.setStyleName("welcome-label2");

		// nutzerprofil = null;
		partnerboerseAdministration = GWT.create(PartnerboerseAdministration.class);

		try {
			ClientsideSettings.getPartnerboerseAdministration().login(GWT.getHostPageBaseURL() + "Partnerboerse.html",
					new AsyncCallback<Nutzerprofil>() {

						public void onFailure(Throwable caught) {
							RootPanel.get().add(new Label(caught.toString()));
						}

						public void onSuccess(Nutzerprofil result) {

							ClientsideSettings.setAktuellerUser(result);

							// wenn der user eingeloggt ist
							if (result.isLoggedIn()) {

								if (result.getEmailAddress() != null) {

									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText(
											"Als " + result.getVorname() + result.getProfilId() + " ausloggen");

									loginPanel.add(signOutLink);

									ClientsideSettings.getPartnerboerseAdministration()
											.pruefeObNutzerNeu(result.getEmailAddress(), new AsyncCallback<Boolean>() {

												@Override
												public void onFailure(Throwable caught) {
												}

												@Override
												public void onSuccess(Boolean result) {

													if (result == true) {
														RootPanel.get("Details").add(new CreateNutzerprofil());
													}

					else {

														RootPanel.get("Navigator").add(new Navigator());
														RootPanel.get("Navigator").add(loginPanel);
														RootPanel.get("Details").add(begrueßenN);
														RootPanel.get("Details").add(begrueßenN2);

													}
												}
											});
								}

								if (result.getEmailAddress() == null) {
									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText(
											"Als " + result.getVorname() + result.getProfilId() + " ausloggen");

									loginPanel.add(signOutLink);
									RootPanel.get("Navigator").add(loginPanel);
									RootPanel.get("Details").add(begrueßenN);
									RootPanel.get("Details").add(begrueßenN2);

								}
							}

							// wenn der user nicht eingeloggt ist
							else if (!result.isLoggedIn()) {
								signInLink.setHref(result.getLoginUrl());
								loginPanel.add(signInLink);
								RootPanel.get("Navigator").add(loginPanel);
								RootPanel.get("Details").add(begrueßen);
								RootPanel.get("Details").add(begrueßen2);
							}
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
