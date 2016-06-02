package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.client.gui.NutzerprofilForm;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class Partnerboerse implements EntryPoint {

	// private PartnerboerseAdministrationAsync partnerboerseAdministration =
	// ClientsideSettings
	// .getPartnerboerseAdministration();
	// private ReportGeneratorAsync reportGeneratorAsync = ClientsideSettings
	// .getReportGenerator();
	// private VerticalPanel verPanel;
	// private VerticalPanel content;
	// private Label lbl;
	// private Navigator showEigenesNpButton;
	// private Navigator merklisteAnzeigenButton;
	// private Navigator sperrlisteAnzeigenButton;
	// private Navigator showSuchprofilButton;

	// private CreateNutzerprofil createNutzerprofil;
	// private Boolean test;
	// private LoginInfo loginInfo = new LoginInfo();
	private VerticalPanel loginPanel = new VerticalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();

	/**
	 * Diese Klasse sichert die Implementierung des Interface
	 * <code>EntryPoint</code>. Daher benötigen wir die Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */
	// private final PartnerboerseAdministrationAsync
	// partnerboerseAdministration = GWT
	// .create(PartnerboerseAdministration.class);

	public void onModuleLoad() {

		/**
		 * Das VerticalPanel wird einem DIV-Element names "Navigator" in der
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen
		 * Darstellungsort.
		 */
//		 RootPanel.get("Navigator").add(new Navigator());

		// Check login status using login service.
		// LoginServiceAsync loginService = GWT.create(Nutzerprofil.class);

		PartnerboerseAdministrationAsync partnerboerseAdministration = GWT.create(PartnerboerseAdministration.class);

		// loginService.login(GWT.getHostPageBaseURL() + "Partnerboerse.html",
		// new AsyncCallback<LoginInfo>() {
		// public void onFailure(Throwable error) {
		// RootPanel.get().add(new Label(error.toString()));
		// }
		//
		// public void onSuccess(LoginInfo result) {
		// RootPanel.get().add(new Label(result.toString()));
		// loginInfo = result;
		// if (loginInfo.isLoggedIn()) {
		// loadPartnerboerse();
		// } else {
		// loadLogin();
		// }
		// }
		// });

		try {
			partnerboerseAdministration.login(GWT.getHostPageBaseURL() + "Partnerboerse.html",
					new AsyncCallback<Nutzerprofil>() {

						public void onFailure(Throwable caught) {
							RootPanel.get().add(new Label(caught.toString()));
						}

						public void onSuccess(Nutzerprofil result) {
							// wenn der user eingeloggt ist
							if (result.isLoggedIn()) {
								signOutLink.setHref(result.getLogoutUrl());
								signOutLink.setText("Als " + result.getVorname() + " ausloggen");
								loginPanel.add(signOutLink);
								RootPanel.get("Navigator").add(new Navigator());
								RootPanel.get("Navigator").add(loginPanel);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// private void loadPartnerboerse() {
	// String emailAddress = loginInfo.getEmailAddress();
	// isUserRegistered(userEmail);
	//
	// verPanel = new VerticalPanel();
	//
	// showEigenesNpButton = new Navigator(loginInfo);
	// merklisteAnzeigenButton = new Navigator(loginInfo);
	// sperrlisteAnzeigenButton = new Navigator(loginInfo);
	// showSuchprofilButton = new Navigator(loginInfo);
	//
	// signOutLink.setHref(loginInfo.getLogoutUrl());
	// RootPanel.get("Navigator").add(signOutLink);
	//
	// content = new VerticalPanel();
	// lbl = new Label("Willkommen auf unserer Partnerboerse");
	// lbl.setStyleName("label");
	// content.add(lbl);
	//
	// RootPanel.get("Navigator").add(content);
	//
	// }

	// private void isUserRegistered(String userEmail) {
	//
	// AsyncCallback<Boolean> isUserRegisteredCallback = new
	// AsyncCallback<Boolean>() {
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// ClientsideSettings.getLogger().severe(
	// "Fehler bei Benutzerüberprüfung");
	//
	// }
	//
	// @Override
	// public void onSuccess(Boolean result) {
	// if (result == false) {
	// RootPanel.get("Details").clear();
	// RootPanel.get("Space").clear();
	//
	//
	// }
	//
	// }
	//
	// };
	// partnerboerseAdministration.isUserRegistered(userEmail,
	// isUserRegisteredCallback);
	// }

	// private void loadLogin() {
	// // Assemble login panel.
	// loginPanel.add(loginLabel);
	// loginPanel.add(signInLink);
	// RootPanel.get("Navigator").add(loginPanel);
	//
	// }
	// private void loadLogout(){
	// signOutLink.setHref(loginInfo.getLoginUrl());
	// loginPanel.add(loginLabel);
	// loginPanel.add(signOutLink);
	// RootPanel.get("Navigator").add(loginPanel);
	// }
	

}