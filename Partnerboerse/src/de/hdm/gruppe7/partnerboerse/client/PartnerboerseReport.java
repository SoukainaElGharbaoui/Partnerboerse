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
 * Die Klasse PartnerboerseReports implementiert einen zweiten EntryPoint, über
 * den die Reports abgerufen werden können.
 * 
 */
public class PartnerboerseReport extends VerticalPanel implements EntryPoint {

	ReportGeneratorAsync reportGenerator = null;

	Button unangesehenePartnervorschlaegeButton = new Button(
			"Unangesehene Partnervorschlaege");

	Button partnervorschlaegeSuchprofilButton = new Button(
			"PartnervorschlÃ¤ge anhand von Suchprofilen");

	private static Nutzerprofil np = null;

	private VerticalPanel loginPanel = new VerticalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();

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
		loginService.login(GWT.getHostPageBaseURL(), loginExecute());

	}

	private void createNavigator() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("720px");
		menu.setHeight("36px");
		menu.setStyleName("MenuBarRep");
		menu.setAnimationEnabled(true);

		// Create the file menu
		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);

		MenuItem unangesehenePartnervorschlaege = partnervorschlaegeMenu
				.addItem("Unangesehene Partnervorschlaege", new Command() {
					@Override
					public void execute() {
						ShowAllPartnervorschlaegeNpReport showAllPartnervorschlaegeNpReport = new ShowAllPartnervorschlaegeNpReport(
								np);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(
								showAllPartnervorschlaegeNpReport);
					}
				});

		unangesehenePartnervorschlaege.setStyleName("MenuItemRep");

		MenuItem partnervorschlaegeSp = partnervorschlaegeMenu.addItem(
				"Partnervorschlaege anhand von Suchprofilen", new Command() {

					@Override
					public void execute() {
						ShowAllPartnervorschlaegeSpReport showAllPartnervorschlaegeSpReport = new ShowAllPartnervorschlaegeSpReport(np);

						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(
								showAllPartnervorschlaegeSpReport);

					}

				});

		partnervorschlaegeSp.setStyleName("MenuItemRep");

		partnervorschlaegeMenu.addSeparator();

		menu.addItem(new MenuItem("Meine Partnervorschlaege",
				partnervorschlaegeMenu));
		menu.addSeparator();

		// add the menu to the root panel
		RootPanel.get("Navigator").add(menu);
	}

	private AsyncCallback<LoginInfo> loginExecute() {
		AsyncCallback<LoginInfo> asynCallback = new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(LoginInfo result) {

				if (!result.isLoggedIn()) {
					Window.Location.replace(GWT.getHostPageBaseURL()
							+ editorHtmlName);
				} else {
					admin.getNuterprofilByEmail(result.getEmailAddress(),
							getNutzerprofilByEmailExecute(result
									.getEmailAddress()));

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

				np = result;
				createNavigator();

			}
		};
		return asynCallback;
	}

	/**
	 * Gibt das aktuell-eingeloggte Nutzerprofil zurück
	 * 
	 * @return Nutzerprofil
	 */
	public static Nutzerprofil getNp() {
		return np;
	}

}
