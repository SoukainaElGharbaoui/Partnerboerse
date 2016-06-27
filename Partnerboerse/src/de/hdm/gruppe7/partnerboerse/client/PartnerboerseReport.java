package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.LoginServiceAsync;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Die Klasse PartnerboerseReports implementiert einen zweiten EntryPoint, �ber
 * den die Reports abgerufen werden k�nnen.
 * 
 */
public class PartnerboerseReport extends VerticalPanel implements EntryPoint {

	ReportGeneratorAsync reportGenerator = null;

	Button unangesehenePartnervorschlaegeButton = new Button(
			"Unangesehene Partnervorschlaege");

	Button partnervorschlaegeSuchprofilButton = new Button(
			"Partnervorschläge anhand von Suchprofilen");

	private static Nutzerprofil np = null;

	private VerticalPanel loginPanel = new VerticalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();
	private String logoutUrl = null;
	private LoginInfo loginInfo = null;

	private static String editorHtmlName = "Partnerboerse.html";

	private PartnerboerseAdministrationAsync admin = ClientsideSettings
			.getPartnerboerseAdministration();
	private LoginServiceAsync loginService = ClientsideSettings
			.getLoginService();
	private ReportGeneratorAsync report = ClientsideSettings
			.getReportGenerator();

	@Override
	public void onModuleLoad() {
		// Loginservice von Google
		loginService.login(GWT.getHostPageBaseURL() + "PartnerboerseReports.html", loginExecute());

	}
	private void createNavigator() {

		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("100%");
		menu.setHeight("39px");
		menu.setStyleName("MenuBarRep");
		menu.setAnimationEnabled(true);

		// Create the file menu
		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);

		MenuItem unangesehenePartnervorschlaege = partnervorschlaegeMenu
				.addItem("Unangesehene Partnervorschläge", new Command() {
					@Override
					public void execute() {
						ClientsideSettings
						.getPartnerboerseAdministration()
						.berechneAehnlichkeitNpFor(
								np.getProfilId(),
								new AsyncCallback<Void>() {

									@Override
									public void onFailure(
											Throwable caught) {

									}

									@Override
									public void onSuccess(
											Void result) {
										ShowAllPartnervorschlaegeNpReport showAllPartnervorschlaegeNpReport = new ShowAllPartnervorschlaegeNpReport(
												np);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(
												showAllPartnervorschlaegeNpReport);

									}
								});
						
					}
				});

		unangesehenePartnervorschlaege.setStyleName("MenuItemRep");
		
		MenuBar partnervorschlaegeSpMenu = new MenuBar(true);
		partnervorschlaegeSpMenu.setAnimationEnabled(true);

		MenuItem partnervorschlaegeSp = partnervorschlaegeSpMenu.addItem(
				"Partnervorschläge anhand von Suchprofilen", new Command() {

					@Override
					public void execute() {
						ClientsideSettings.getPartnerboerseAdministration()
						.berechneAehnlichkeitSpFor(
								np.getProfilId(),
								new AsyncCallback<Void>() {

									@Override
									public void onFailure(
											Throwable caught) {
									}


									@Override
									public void onSuccess(Void result3) {
										ShowAllPartnervorschlaegeSpReport showAllPartnervorschlaegeSpReport = new ShowAllPartnervorschlaegeSpReport(np);

										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(
												showAllPartnervorschlaegeSpReport);


									}
								});
					
					}

				});

		partnervorschlaegeSp.setStyleName("MenuItemRep");

		partnervorschlaegeMenu.addSeparator();
		
		MenuBar statusMenu = new MenuBar(true);
		statusMenu.setAnimationEnabled(true);
		MenuItem ausloggenItem = 
				statusMenu.addItem("Ausloggen", new Command() {
			@Override
			public void execute() {
				Window.Location.replace(logoutUrl);
					
	        }
			});
		ausloggenItem.setStyleName("MenuItemRep");

		menu.addItem(new MenuItem("Unangesehene Partnervorschläge",
				partnervorschlaegeMenu));
		menu.addItem(new MenuItem("Partnervorschläge anhand von Suchprofilen", partnervorschlaegeSpMenu));
		menu.addItem(new MenuItem("Ausloggen", statusMenu));
		

		
		
		// add the menu to the root panel
		RootPanel.get("Header").add(menu);
	}

	private AsyncCallback<LoginInfo> loginExecute() {
		AsyncCallback<LoginInfo> asynCallback = new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(LoginInfo result) {
				logoutUrl = result.getLogoutUrl();
				loginInfo = result;
				if (!result.isLoggedIn()) {
					Window.Location.replace(result.getLoginUrl());
				} else {
					admin.pruefeObNutzerNeu(result.getEmailAddress(), pruefeObNutzerNeuExecute(result
							.getEmailAddress()));
				

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
							getNutzerprofilByEmailExecute(loginInfo.getEmailAddress()));
					
				} else {
					
					Window.Location.replace("Partnerboerse.html");
					
				}

			}
		};
		return asynCallback;
	}


	private AsyncCallback<Nutzerprofil> getNutzerprofilByEmailExecute(
			String email) {
		AsyncCallback<Nutzerprofil> asynCallback = new AsyncCallback<Nutzerprofil>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Nutzerprofil result) {
//				Window.Location.replace("Partnerboerse.html");
				np = result;
				createNavigator();

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

}
