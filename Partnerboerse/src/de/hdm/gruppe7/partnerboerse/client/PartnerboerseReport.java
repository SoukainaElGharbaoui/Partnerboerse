package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class PartnerboerseReport extends VerticalPanel implements EntryPoint {

	ReportGeneratorAsync reportGenerator = null;

	Button unangesehenePartnervorschlaegeButton = new Button("Unangesehene Partnervorschläge");

	Button partnervorschlaegeSuchprofilButton = new Button("Partnervorschläge anhand von Suchprofilen");

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	private VerticalPanel loginPanel = new VerticalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();

	private ReportGeneratorAsync reportGeneratorAsync;


	@Override
	public void onModuleLoad() {

		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}


		reportGeneratorAsync = GWT.create(ReportGenerator.class);

		try {
			ClientsideSettings.getReportGenerator().login(GWT.getHostPageBaseURL() + "PartnerboerseReports.html",

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
									RootPanel.get("Details").add(new PartnerboerseReport());
									RootPanel.get("Navigator").add(loginPanel);
								}

								if (result.getEmailAddress() == null) {
									
									String profiltyp = "Np";
									
									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText("Als " + result.getVorname() + " ausloggen");
									loginPanel.add(signOutLink);
									RootPanel.get("Details").add(new PartnerboerseReport());

									RootPanel.get("Navigator").add(loginPanel);
									RootPanel.get("Details").add(new CreateNutzerprofil(profiltyp));

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

		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("100%");
		menu.setHeight("39px");
		menu.setStyleName("MenuBarRep");
		menu.setAnimationEnabled(true);

		// Create the file menu
		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);
	

		MenuItem unangesehenePartnervorschlaege = partnervorschlaegeMenu.addItem("Unangesehene Partnervorschlaege", new Command() {
			@Override
			public void execute() {
				ShowAllPartnervorschlaegeNpReport showAllPartnervorschlaegeNpReport = new ShowAllPartnervorschlaegeNpReport();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showAllPartnervorschlaegeNpReport);
			}
		});
		
		unangesehenePartnervorschlaege.setStyleName("MenuItemRep");

		MenuItem partnervorschlaegeSp = partnervorschlaegeMenu.addItem("Partnervorschlaege anhand von Suchprofilen", new Command() {

			@Override
			public void execute() {
				ShowAllPartnervorschlaegeSpReport showAllPartnervorschlaegeSpReport = new ShowAllPartnervorschlaegeSpReport();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showAllPartnervorschlaegeSpReport);

			}

		});
		
		partnervorschlaegeSp.setStyleName("MenuItemRep");
		
		
        partnervorschlaegeMenu.addSeparator();
        

		menu.addItem(new MenuItem("Meine Partnervorschlaege", partnervorschlaegeMenu));
		menu.addSeparator();

		// add the menu to the root panel
		RootPanel.get("Header").add(menu);

	}

}
