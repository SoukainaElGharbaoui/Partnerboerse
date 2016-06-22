package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Die Klasse <b>Partnerboerse</b> implementiert den EntryPoint.
 * 
 * @author Nina
 */
public class Partnerboerse implements EntryPoint {

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	/**
	 * Deklaraion der Labels fuer die Startseite(n)
	 */
	private Label begrueßen = new Label("Herzlich Willkommen bei Lonely Hearts. ");
	private Label begrueßen2 = new Label("Bitte melde dich an, um die Webseite nutzen zu können.");

	private Label begrueßenN = new Label("Herzlich Willkommen bei LonelyHearts. ");
	private Label begrueßenN2 = new Label("Klicke dich nun durch die Webseite und finde andere Lonely Hearts");

	/**
	 * Deklaration fuer den Login und den Logout
	 */
	private HorizontalPanel loginPanel = new HorizontalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();
	
	/**
	 * Diese Klasse sichert die Implementierung des Interface
	 * EntryPoint. Daher benoetigen wir die Methode
	 * public void onModuleLoad(). Diese ist das GWT-Pendant der
	 * main()-Methode normaler Java-Applikationen.
	 */
	public void onModuleLoad() {

		/**
		 * CSS anwenden
		 */
		begrueßen.setStyleName("welcome-label");
		begrueßen2.setStyleName("welcome-label2");
		begrueßenN.setStyleName("welcome-label");
		begrueßenN2.setStyleName("welcome-label2");

		GWT.create(PartnerboerseAdministration.class);

		/**
		 * Zuerst wird die Domaene fuer die Partnerboerse definiert. Danach wird
		 * der aktuell angemeldete User gesetzt. Je nachdem ob der User aktuell
		 * eingeloggt ist oder nicht, erfolgen unterschiedliche Aktionen.
		 */
		try {
			ClientsideSettings.getPartnerboerseAdministration().login(GWT.getHostPageBaseURL() + "Partnerboerse.html",
					new AsyncCallback<Nutzerprofil>() {

						public void onFailure(Throwable caught) {
							RootPanel.get().add(new Label(caught.toString()));
						}

						public void onSuccess(Nutzerprofil result) {
							
							ClientsideSettings.setAktuellerUser(result);


							/**
							 * Wenn der User nicht eingeloggt ist: Der User wird
							 * begrue�t und der link zum login wird angezeigt
							 */
							if (!result.isLoggedIn()) {

								signInLink.setHref(result.getLoginUrl());
								loginPanel.add(signInLink);
								RootPanel.get("Navigator2").add(loginPanel);
								RootPanel.get("Details").add(begrueßen);
								RootPanel.get("Details").add(begrueßen2);
							}

							/**
							 * Wenn der User eingeloggt ist:
							 * 
							 */
							else if (result.isLoggedIn()) {

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
												public void onSuccess(Boolean pruefung) {
												
													
													if (pruefung == true) {
														
														String profiltyp = "Np";
														
														RootPanel.get("Navigator").add(new Navigator());
														RootPanel.get("Navigator2").add(loginPanel);
														
														RootPanel.get("Details")
															.add(new CreateNutzerprofil(profiltyp));
													}

													else {
														
														RootPanel.get("Navigator").add(new Navigator());
														RootPanel.get("Navigator2").add(loginPanel);
													
													}
												}
											});
								}

								if (result.getEmailAddress() == null) {
									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText(
											"Als " + result.getVorname() + result.getProfilId() + " ausloggen");

									loginPanel.add(signOutLink);
									RootPanel.get("Navigator2").add(loginPanel);
									RootPanel.get("Details").add(begrueßenN);
									RootPanel.get("Details").add(begrueßenN2);

								}

							}
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
