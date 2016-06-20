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


/**
 * @author dunja
 *
 */
public class Navigator extends HorizontalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	int aehnlichkeit = 0;

	/**
	 * 
	 */
	public Navigator() {

		

		VerticalPanel verPanel1 = new VerticalPanel();
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
		menu.setWidth("720px");
		menu.setHeight("36px");
		menu.setAnimationEnabled(true);
		menu.setStyleName("MenuBar");

		/**
		 * Menueleiste für das Nutzerprofil, in denen die Funktionen bezueglich des
		 * eigenen Profils zur Verfuegung gestellt werden.
		 */
		MenuBar nutzerprofilMenu = new MenuBar(true);
		nutzerprofilMenu.setAnimationEnabled(true);

		MenuItem profilAnzeigen = nutzerprofilMenu.addItem("Profil anzeigen", new Command() {
			@Override
			public void execute() {
				
				int profilId = nutzerprofil.getProfilId();
				String profiltyp = "Np";
				
				ShowNutzerprofil showNutzerprofil = new ShowNutzerprofil(profilId, profiltyp);
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
				ShowMerkliste showMerkliste = new ShowMerkliste();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showMerkliste);
			}
		});
		
		merklisteAnzeigen.setStyleName("MenuItem");

		MenuItem sperrlisteAnzeigen = nutzerprofilMenu.addItem("Sperrliste anzeigen", new Command() {

			@Override
			public void execute() {
				ShowSperrliste showSperrliste = new ShowSperrliste();
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

		MenuItem suchprofilAnlegen = suchprofilMenu.addItem("Suchprofil anlegen", new Command() {

			@Override
			public void execute() {

				/**
				 * Sobald ein neues Suchprofil angelegt wird, wird die Tabelle
				 * in der Datenbank, die die Partnervorschlaege anhand von
				 * Suchprofilen enthaelt, geleert.
				 */

				String profiltyp = "Sp";
				
				CreateSuchprofil createSuchprofil = new CreateSuchprofil(profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);

			}

		});
		
		suchprofilAnlegen.setStyleName("MenuItem");

		MenuItem suchprofilAnzeigen = suchprofilMenu.addItem("Suchprofile anzeigen", new Command() {
			@Override
			public void execute() {
				String profiltyp = "Sp";
				int suchprofilId = 0;
				ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSuchprofil);
			}
		});
		
		suchprofilAnzeigen.setStyleName("MenuItem");

		suchprofilMenu.addSeparator();
		
		/**
		 * Menue für die Partnervorschlaege
		 */

		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);

		MenuItem unangesehenePartnervorschlaegeAnzeigen =partnervorschlaegeMenu.addItem("Unangesehene Partnervorschläge anzeigen", new Command() {

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
		
		unangesehenePartnervorschlaegeAnzeigen.setStyleName("MenuItem");

		MenuItem partnervorschlaegeSpAnzeigen = partnervorschlaegeMenu.addItem("Partnervorschläge anhand von Suchprofilen anzeigen", new Command() {

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
		
		partnervorschlaegeSpAnzeigen.setStyleName("MenuItem");
		
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

		menu.addItem(new MenuItem("Meine Partnervorschläge", partnervorschlaegeMenu));
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

		/**
		 * Hinzufügen der Menübar zum RootPanel
		 */
		RootPanel.get("Navigator").add(menu);

	}
}
