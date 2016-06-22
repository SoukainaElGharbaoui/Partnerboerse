package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
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

		/**
		 * Ab hier wird die Menübar erstellt. Dabei werden abhängig von der 
		 * Thematik einzelne vertikale aufklappbare Menüs zur 
		 * horizontalen Menühauptleiste "menu" hinzugefügt.
		 */

		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		/**
		 * Festlegen der Länge der Menübar und Einbinden von CSS.
		 */
		menu.setWidth("720px");
		menu.setHeight("36px");
		menu.setAnimationEnabled(true);
		menu.setStyleName("MenuBar");

		/**
		 * Menüleiste für das Nutzerprofil, in denen die Funktionen bezüglich des
		 * eigenen Profils zur Verfügung gestellt werden.
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
		 * Den einzelnen Menüs werden verschiedene Items hinzugefügt, denen 
		 * jeweils ein Command übergeben wird. Wird ein bestimmtes Item 
		 * angeklickt, so wird der jeweilige Command ausgeführt.
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
		 * Menü für das Suchprofil
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
		 * Menü für die Partnervorschläge
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
                 * Hinzufügen der vertikalen Menüleisten nutzerProfilMenu, 
                 * suchprofilMenu und partnervorschlaegeMenu zur 
                 * horizontalen Hauptleiste "menu" und Benennung der 
                 * Menüleisten in der Menübar per String-Übergabe.
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
