package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.client.Partnerboerse;


public class Navigator extends HorizontalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	int aehnlichkeit = 0;

	public Navigator() {

		
		VerticalPanel verPanel1 = new VerticalPanel();
		
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);

		menu.setWidth("900px");
		menu.setAnimationEnabled(true);
		menu.setStyleName("gwt-MenuBar-horizontal");

		// Create the file menu
		MenuBar nutzerprofilMenu = new MenuBar(true);
		nutzerprofilMenu.setAnimationEnabled(true);

		nutzerprofilMenu.addItem("Profil anzeigen", new Command() {
			@Override
			public void execute() {
				ShowEigenesNp showEigenesNp = new ShowEigenesNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showEigenesNp);
			}
		});


		nutzerprofilMenu.addItem("Merkliste anzeigen", new Command() {
			@Override
			public void execute() {
				ShowMerkliste showMerkliste = new ShowMerkliste();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showMerkliste);
			}
		});

		nutzerprofilMenu.addItem("Sperrliste anzeigen", new Command() {

			@Override
			public void execute() {
				ShowSperrliste showSperrliste = new ShowSperrliste();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSperrliste);
			}
		});
		
		nutzerprofilMenu.addSeparator();

		// Men� f�r das Suchprofil
		MenuBar suchprofilMenu = new MenuBar(true);
		suchprofilMenu.setAnimationEnabled(true);

		suchprofilMenu.addItem("Suchprofil anlegen", new Command() {

			@Override
			public void execute() {

				/**
				 * Sobald ein neues Suchprofil angelegt wird, wird die Tabelle
				 * in der Datenbank, die die Partnervorschlaege anhand von
				 * Suchprofilen enthaelt, geleert.
				 */

				CreateSuchprofil createSuchprofil = new CreateSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);

			}

		});

		suchprofilMenu.addItem("Suchprofile anzeigen", new Command() {
			@Override
			public void execute() {
				int suchprofilId = 0;
				ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilId);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSuchprofil);
			}
		});

		suchprofilMenu.addSeparator();

		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);

		partnervorschlaegeMenu.addItem("Unangesehene Partnervorschläge anzeigen", new Command() {

			@Override
			public void execute() {

				ClientsideSettings.getPartnerboerseAdministration()
						.berechneAehnlichkeitNpFor(nutzerprofil.getProfilId(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(Void result) {
								ShowPartnervorschlaegeNp showPartnervorschlaegeNp = new ShowPartnervorschlaegeNp();
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showPartnervorschlaegeNp);

							}

						});


			}

		});

		partnervorschlaegeMenu.addItem("Partnervorschläge anhand von Suchprofilen anzeigen", new Command() {

			@Override
			public void execute() {

				ClientsideSettings.getPartnerboerseAdministration()
						.berechneAehnlichkeitSpFor(nutzerprofil.getProfilId(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// infoLabel
								// .setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Void result3) {
								// infoLabel.setText("Es hier trat kein Fehler
								// auf.");
							ShowPartnervorschlaegeSp showPartnervorschlaegeSp = new ShowPartnervorschlaegeSp();
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showPartnervorschlaegeSp);

							}
				});

				
			}

		});
		
		partnervorschlaegeMenu.addSeparator();
		
		
		
//		partnervorschlaegeMenu.addSeparator();
//		
//		// Create the file menu
//				MenuBar statusMenu = new MenuBar(true);
//				statusMenu.setAnimationEnabled(true);
//
//				statusMenu.addItem("Ausloggen", new Command() {
//					@Override
//					public void execute() {
////						ShowEigenesNp showEigenesNp = new ShowEigenesNp();
////						RootPanel.get("Details").clear();
////						RootPanel.get("Details").add(showEigenesNp);
//						
//						Anchor signOutLink = new Anchor();
//
//						signOutLink.setHref(nutzerprofil.getLogoutUrl());
//						signOutLink.setText("Als " + nutzerprofil.getVorname() + " ausloggen");
//						
//					}
//				});
		
		

		menu.addItem(new MenuItem("Mein Profil", nutzerprofilMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Mein Suchprofil", suchprofilMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Meine Partnervorschlaege", partnervorschlaegeMenu));
		menu.addSeparator();
		
//		menu.addItem(new MenuItem("Ausloggen", statusMenu));

		//////////////////////////////////////////////////////////////////////////////////
		// Create the report menu
		//////////////////////////////////////////////////////////////////////////////////

		MenuBar reportMenu = new MenuBar(true);
		reportMenu.setAnimationEnabled(true);

		reportMenu.addItem("Partnervorschlagreport des Nuzterprofils anzeigen", new Command() {
			@Override
			public void execute() {
				ShowAllPartnervorschlaegeNpReport showAllPartnervorschlaegeNpReport = new ShowAllPartnervorschlaegeNpReport();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showAllPartnervorschlaegeNpReport);
			}
		});

		reportMenu.addItem("Partnervorschlagreport des Suchprofils anzeigen", new Command() {
			@Override
			public void execute() {
				ShowAllPartnervorschlaegeSpReport showAllPartnervorschlaegeSpReport = new ShowAllPartnervorschlaegeSpReport();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showAllPartnervorschlaegeSpReport);
			}
		});

		// add the menu to the root panel
		RootPanel.get("Navigator").add(menu);

	}
}
