package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.client.Partnerboerse;

/**
 *
 */
public class Navigator extends HorizontalPanel {
	
	
	
	

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();
	
	private String logoutUrl = Partnerboerse.getLoginInfo().getLogoutUrl();

	int aehnlichkeit = 0;

	/**
	 * 
	 */
	public Navigator(Nutzerprofil np) {

		VerticalPanel verPanel1 = new VerticalPanel();
		this.add(verPanel1);
		
		/**
		 * Ab hier wird die Menuebar erstellt. Dabei werden abhaengig von der 
		 * Thematik einzelne vertikale aufklappbare Menues zur 
		 * horizontalen Menuehauptleiste "menu" hinzugefuegt.
		 */
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		/**

		 * Festlegen der Laenge der Menuebar und Einbinden von CSS.
		 */
		menu.setWidth("100%");
		menu.setHeight("36px");

		menu.setAnimationEnabled(true);
		menu.setStyleName("MenuBar");

		/**
		 * Menueleiste für das Nutzerprofil, in denen die Funktionen bezueglich des
		 * eigenen Profils zur Verfuegung gestellt werden.
		 */
		MenuBar nutzerprofilMenu = new MenuBar(true);
		nutzerprofilMenu.setAnimationEnabled(true);

		MenuItem profilAnzeigen = nutzerprofilMenu.addItem("Profil anzeigen",
				new Command() {
					@Override
					public void execute() {

						int profilId = nutzerprofil.getProfilId();
						String profiltyp = "Np";

						ShowNutzerprofil showNutzerprofil = new ShowNutzerprofil(
								profilId, profiltyp);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(showNutzerprofil);
					}
				});

		profilAnzeigen.setStyleName("MenuItem");

		/**
		 * Den einzelnen Menues werden verschiedene Items hinzugefuegt, denen 
		 * jeweils ein Command uebergeben wird. Wird ein bestimmtes Item 
		 * angeklickt, so wird der jeweilige Command ausgefuehrt.
		 */

		MenuItem merklisteAnzeigen = nutzerprofilMenu.addItem("Merkliste anzeigen", new Command() {

			@Override
			public void execute() {
				String listtyp = "M";
				ShowMerkliste showMerkliste = new ShowMerkliste(listtyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showMerkliste);
			}
		});
		

		merklisteAnzeigen.setStyleName("MenuItem");

		MenuItem sperrlisteAnzeigen = nutzerprofilMenu.addItem(
				"Sperrliste anzeigen", new Command() {


			@Override
			public void execute() {
				String listtyp = "S";
				ShowSperrliste showSperrliste = new ShowSperrliste(listtyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSperrliste);
			}
		});
		

		sperrlisteAnzeigen.setStyleName("MenuItem");

		nutzerprofilMenu.addSeparator();

		/**
		 * Menue für das Suchprofil
		 */
		MenuBar suchprofilMenu = new MenuBar(true);
		suchprofilMenu.setAnimationEnabled(true);

		MenuItem suchprofilAnlegen = suchprofilMenu.addItem(
				"Suchprofil anlegen", new Command() {

					@Override
					public void execute() {

						/**
						 * Sobald ein neues Suchprofil angelegt wird, wird die
						 * Tabelle in der Datenbank, die die Partnervorschlaege
						 * anhand von Suchprofilen enthaelt, geleert.
						 */

						String profiltyp = "Sp";

						CreateSuchprofil createSuchprofil = new CreateSuchprofil(
								profiltyp);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(createSuchprofil);

					}

				});

		suchprofilAnlegen.setStyleName("MenuItem");

		MenuItem suchprofilAnzeigen = suchprofilMenu.addItem("Suchprofile anzeigen", new Command() {
			@Override
			public void execute() {
				ClientsideSettings.getPartnerboerseAdministration()
				.getAllSuchprofileFor(
						nutzerprofil.getProfilId(),
						new AsyncCallback<List<Suchprofil>>() {

							@Override
							public void onFailure(
									Throwable caught) {
							}

							@Override
							public void onSuccess(
									List<Suchprofil> result) {
								
								int suchprofilId;
								
								if (result.isEmpty()) {
									suchprofilId = 0;
								} else {
									suchprofilId = result.get(0).getProfilId();
								}
								
								String profiltyp = "Sp";
								ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilId, profiltyp);
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showSuchprofil);

							}

						});
				
				}
		});
		
		suchprofilAnzeigen.setStyleName("MenuItem");

		suchprofilMenu.addSeparator();

		/**
		 * Menue für die Partnervorschlaege
		 */

		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);

		MenuItem unangesehenePartnervorschlaegeAnzeigen = partnervorschlaegeMenu
				.addItem("Unangesehene Partnervorschläge anzeigen",
						new Command() {

							@Override
							public void execute() {

								ClientsideSettings
										.getPartnerboerseAdministration()
										.berechneAehnlichkeitNpFor(
												nutzerprofil.getProfilId(),
												new AsyncCallback<Void>() {

													@Override
													public void onFailure(
															Throwable caught) {

													}

													@Override
													public void onSuccess(
															Void result) {
														String listtyp = "PvNp";
														ShowPartnervorschlaegeNp showPartnervorschlaegeNp = new ShowPartnervorschlaegeNp(listtyp);
														RootPanel.get("Details").clear();
														RootPanel.get("Details").add(showPartnervorschlaegeNp);

													}
												});
							
								}

							});

		unangesehenePartnervorschlaegeAnzeigen.setStyleName("MenuItem");
	
		MenuItem partnervorschlaegeSpAnzeigen = partnervorschlaegeMenu.addItem(
				"Partnervorschläge anhand von Suchprofilen anzeigen",
				new Command() {

					@Override
					public void execute() {

						ClientsideSettings.getPartnerboerseAdministration()
								.berechneAehnlichkeitSpFor(
										nutzerprofil.getProfilId(),
										new AsyncCallback<Void>() {

											@Override
											public void onFailure(
													Throwable caught) {
												// infoLabel
												// .setText("Es trat ein Fehler auf.");
											}


							@Override
							public void onSuccess(Void result3) {
								// infoLabel.setText("Es hier trat kein Fehler
								// auf.");
							String listtyp = "PvSp";
							ShowPartnervorschlaegeSp showPartnervorschlaegeSp = new ShowPartnervorschlaegeSp(listtyp);
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showPartnervorschlaegeSp);


											}
										});
					}
				});

		partnervorschlaegeSpAnzeigen.setStyleName("MenuItem");

		partnervorschlaegeMenu.addSeparator();
					MenuBar statusMenu = new MenuBar(true);
						statusMenu.setAnimationEnabled(true);
		
						statusMenu.addItem("Ausloggen", new Command() {
							@Override
							public void execute() {
		 					Window.Location.replace(logoutUrl);
		 						
					}
		 				});
		


/**
 * Hinzufuegen der vertikalen Menueleisten nutzerProfilMenu, 
 * suchprofilMenu und partnervorschlaegeMenu zur 
 * horizontalen Hauptleiste "menu" und Benennung der 
 * Menueleisten in der Menuebar per String-Uebergabe.
 */
		
		menu.addItem(new MenuItem("Mein Profil", nutzerprofilMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Meine Suchprofile", suchprofilMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Meine Partnervorschläge",
				partnervorschlaegeMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Ausloggen", statusMenu));

		// ////////////////////////////////////////////////////////////////////////////////
		// Create the report menu
		// ////////////////////////////////////////////////////////////////////////////////

		MenuBar reportMenu = new MenuBar(true);
		reportMenu.setAnimationEnabled(true);

		reportMenu.addItem("Partnervorschlagreport des Nuzterprofils anzeigen",
				new Command() {
					@Override
					public void execute() {
						ShowAllPartnervorschlaegeNpReport showAllPartnervorschlaegeNpReport = new ShowAllPartnervorschlaegeNpReport(Partnerboerse.getNp());
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(
								showAllPartnervorschlaegeNpReport);
					}
				});

		reportMenu.addItem("Partnervorschlagreport des Suchprofils anzeigen",
				new Command() {
					@Override
					public void execute() {
						ShowAllPartnervorschlaegeSpReport showAllPartnervorschlaegeSpReport = new ShowAllPartnervorschlaegeSpReport(Partnerboerse.getNp());
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(
								showAllPartnervorschlaegeSpReport);
					}
				});

		/**
		 * Hinzufügen der Menübar zum RootPanel
		 */
		RootPanel.get("Header").add(menu);

}
}
