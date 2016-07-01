package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.gruppe7.partnerboerse.shared.LoginServiceAsync;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Die Klasse Partnerboerse implementiert den EntryPoint.
 * 
 */
public class Partnerboerse implements EntryPoint {


	/**
	 * Neues Nutzerprofil erzeugen
	 */
	Nutzerprofil nutzerprofil = new Nutzerprofil();

	
	/**
	 * Deklaraion der Labels fuer die Startseite(n)
	 */
	private Label begruessenN = new Label(
			"Herzlich Willkommen bei LonelyHearts. ");
	private Label begruessenN2 = new Label(
			"Klicke dich nun durch die Webseite und finde andere Lonely Hearts");

	/**
	 * Deklaration fuer den Login und den Logout
	 */
	private static Nutzerprofil np = null;
	private static LoginInfo loginInfo = null;
	
	private static String editorHtmlName = "Partnerboerse.html";

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

		/**
		 * Login-Methode aufrufen und anschließend auf die Hostpage leiten.
		 */
		loginService.login(GWT.getHostPageBaseURL() + editorHtmlName,
				loginExecute());

	}
	
	/**
	 * AsyncCallback für die Login-Mathode. Bei erhalt der LoginInfos wir die Methode
	 * pruefeObMutzerNeu() aufgerufen.
	 * 
	 * @return
	 */
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
	
	/**
	 * AsyncCallback für die Methode pruefeObMutzerNeu(). Falls der Wert false ist wird die Methode
	 * getNutzerByEmail() aufgerufen, sonst wird der Nutzer auf CreateNutzerprofil() weitergeleitet.
	 * 
	 * @return
	 */
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
					
					RootPanel.get("Details").add(begruessenN);
					RootPanel.get("Details").add(begruessenN2);

				} else {
					
					CreateNutzerprofil createNutzerprofil = new CreateNutzerprofil("Np");
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(createNutzerprofil);
					
				}

			}
		};

		return asynCallback;
	}

	/**
	 * Gibt das aktuell-eingeloggte Nutzerprofil zurueck
	 * 
	 * @return Nutzerprofil
	 */
	public static Nutzerprofil getNp() {
		return np;
	}

	/**
	 * Gibt die LoginInfos des aktuell-eingeloggten Nutzerprofils zurueck
	 * 
	 * @return loginInfo LoginInfo
	 */
	public static LoginInfo getLoginInfo() {
		return loginInfo;
	}

	
	/**
	 * AsyncCallback für die Methode getNuterprofilByEmail(). Wenn ein Nutzerprofil zurückgeliefert wird,
	 * wird die Methode getMenu() aurgerufen und das zurückgelieferte Nutzerprofil in die Variable np 
	 * gespeichert.
	 * @return
	 */
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

	/**
	 * Methode legt die CSS-Styles für verschiedene Labels fest.
	 */
	private void setStyles() {
		begruessenN.setStyleName("welcome-label");
		begruessenN2.setStyleName("welcome-label2");

	}

	/**
	 * Methode erzeugt ruft das Panel auf, durch welches die Menubar sichtbar wird.
	 */
	public static void getMenu() {
		RootPanel.get("Navigator").add(new Navigator(np));
	}

}