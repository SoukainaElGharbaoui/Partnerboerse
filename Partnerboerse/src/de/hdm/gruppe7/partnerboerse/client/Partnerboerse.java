package de.hdm.gruppe7.partnerboerse.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.LoginService;
import de.hdm.gruppe7.partnerboerse.shared.LoginServiceAsync;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Die Klasse <b>Partnerboerse</b> implementiert den EntryPoint.
 * 
 */
public class Partnerboerse implements EntryPoint {

	// com.google.appengine.api.users.UserService userService =
	// com.google.appengine.api.users.UserServiceFactory
	// .getUserService();

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	
	/**
	 * Deklaraion der Labels fuer die Startseite(n)
	 */
	private Label begruessen = new Label(
			"Herzlich Willkommen bei Lonely Hearts. ");
	private Label begruessen2 = new Label(
			"Bitte melde dich an, um die Webseite nutzen zu koennen.");

	private Label begruessenN = new Label(
			"Herzlich Willkommen bei LonelyHearts. ");
	private Label begruessenN2 = new Label(
			"Klicke dich nun durch die Webseite und finde andere Lonely Hearts");

	/**
	 * Deklaration fuer den Login und den Logout
//	 */
//	private HorizontalPanel loginPanel = new HorizontalPanel();
//	private Anchor signInLink = new Anchor("Jetzt einloggen");
//	private Anchor signOutLink = new Anchor();
//
//	private Label testLabel;

	private static Nutzerprofil np = null;
	private static LoginInfo loginInfo = null;

	private PartnerboerseAdministrationAsync admin = ClientsideSettings
			.getPartnerboerseAdministration();
	private LoginServiceAsync loginService = ClientsideSettings
			.getLoginService();

	/**
	 * Diese Klasse sichert die Implementierung des Interface EntryPoint. Daher
	 * benoetigen wir die Methode public void onModuleLoad(). Diese ist das
	 * GWT-Pendant der main()-Methode normaler Java-Applikationen.
	 */
	public void onModuleLoad() {
		setStyles();

		
		// Loginservice von Google
		loginService.login(GWT.getHostPageBaseURL() + "Partnerboerse.html",
				loginExecute());

	}

	private AsyncCallback<LoginInfo> loginExecute() {
		AsyncCallback<LoginInfo> asynCallback = new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable caught) {


			}

			@Override
			public void onSuccess(LoginInfo result) {

				if (result.isLoggedIn()) {
					loginInfo = result;
					admin.pruefeObNutzerNeu(result.getEmailAddress(),
							pruefeObNutzerNeuExecute(result
									.getEmailAddress()));
				} else {
					Window.Location.replace(result.getLoginUrl());
				}
			}
		};

		return asynCallback;
	}

	private AsyncCallback<Boolean> pruefeObNutzerNeuExecute(String email) {
		AsyncCallback<Boolean> asynCallback = new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {


			}

			@Override
			public void onSuccess(Boolean result) {

				if (!result) {
					admin.getNuterprofilByEmail(loginInfo.getEmailAddress(),
							getNuterprofilByEmailExecute(loginInfo.getEmailAddress()));

				} else {
					Window.alert("neuer Nutzer");
//					CreateNutzerprofil createNutzerprofil = new CreateNutzerprofil(Np);
//					RootPanel.get("Details").clear();
//					RootPanel.get("Details").add(createNutzerprofil);
				}

			}
		};

		return asynCallback;
	}

	/**
	 * Gibt das aktuell-eingeloggte Nutzerprofil zur�ck
	 * 
	 * @return Nutzerprofil
	 */
	public static Nutzerprofil getNp() {
		return np;
	}

	/**
	 * Gibt das aktuell-eingeloggte Nutzerprofil zur�ck
	 * 
	 * @return Nutzerprofil
	 */
	public static LoginInfo getLoginInfo() {
		return loginInfo;
	}

	private AsyncCallback<Nutzerprofil> getNuterprofilByEmailExecute(
			String email) {
		AsyncCallback<Nutzerprofil> asynCallback = new AsyncCallback<Nutzerprofil>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Nutzerprofil result) {

				np = result;
				getMenu();

			}
		};
		return asynCallback;
	}

	private void setStyles() {
		begruessen.setStyleName("welcome-label");
		begruessen2.setStyleName("welcome-label2");
		begruessenN.setStyleName("welcome-label");
		begruessenN2.setStyleName("welcome-label2");

	}

	private void getMenu() {
		RootPanel.get("Navigator").add(new Navigator());
		// RootPanel.get("Navigator2").add(loginPanel);
	}


	//
	// /**
	// * Zuerst wird die Domaene fuer die Partnerboerse definiert. Danach wird
	// * der aktuell angemeldete User gesetzt. Je nachdem ob der User aktuell
	// * eingeloggt ist oder nicht, erfolgen unterschiedliche Aktionen.
	// */
	// try {
	// ClientsideSettings.getLoginService().login(GWT.getHostPageBaseURL() +
	// "Partnerboerse.html",
	// new AsyncCallback<Nutzerprofil>() {
	//
	//
	// public void onFailure(Throwable caught) {
	// RootPanel.get().add(new Label(caught.toString()));
	// }
	//
	// public void onSuccess(Nutzerprofil result) {
	// np = result;
	//
	//
	// final String eMail = nutzerprofil.getEmailAddress();
	//
	// // testLabel = new Label("User: " + result.getEmailAddress());
	// // RootPanel.get("Details").add(testLabel);
	//
	//
	// /**
	// * Wenn der User nicht eingeloggt ist: Der User wird
	// * begruesst und der link zum login wird angezeigt
	// */
	// if (result.getLoggedIn() == false) {
	//
	// testLabel = new Label("User is not logged in");
	// RootPanel.get("Details").add(testLabel);
	//
	// signInLink.setHref(result.getLoginUrl());
	// loginPanel.add(signInLink);
	// RootPanel.get("Navigator").add(loginPanel);
	// RootPanel.get("Details").add(begruessen);
	// RootPanel.get("Details").add(begruessen2);
	// }
	//
	// /**
	// * Wenn der User eingeloggt ist:
	// *
	// */
	// else if (result.getLoggedIn() == true) {
	//
	// // testLabel = new Label("User is logged in");
	// // RootPanel.get("Details").add(testLabel);
	// //
	// // ClientsideSettings.getLoginService()
	// // .pruefeObNutzerNeu(eMail,
	// // new AsyncCallback<Boolean>() {
	// //
	// // @Override
	// // public void onFailure(Throwable caught) {
	// // testLabel = new Label("E-mail: " + eMail);
	// // RootPanel.get("Details").add(testLabel);
	// //
	// // }
	// //
	// // @Override
	// // public void onSuccess(Boolean pruefung) {
	// //
	// // if (pruefung == true) {
	// //
	// // String profiltyp = "Np";
	// //
	// //// RootPanel.get("Navigator").add(new Navigator());
	// //// RootPanel.get("Navigator2").add(loginPanel);
	// //
	// // RootPanel.get("Details")
	// // .add(new CreateNutzerprofil(profiltyp));
	// // }
	// //
	// // else if (pruefung == false){
	// //
	// // RootPanel.get("Navigator").add(new Navigator());
	// // RootPanel.get("Navigator2").add(loginPanel);
	// //
	// // signOutLink.setHref(nutzerprofil.getLogoutUrl());
	// // signOutLink.setText(
	// // "Als " + nutzerprofil.getVorname() + nutzerprofil.getProfilId() +
	// " ausloggen");
	// //
	// // loginPanel.add(signOutLink);
	// //
	// // }
	// // }
	// // });
	//
	// RootPanel.get("Navigator").add(new Navigator());
	// RootPanel.get("Navigator2").add(loginPanel);
	//
	// } //else if
	// } // success
	// });
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
}
