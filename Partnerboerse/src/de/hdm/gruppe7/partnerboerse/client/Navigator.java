package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;


public class Navigator extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	int aehnlichkeit = 0;

	public Navigator() {

		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("900px");
		menu.setAnimationEnabled(true);

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

		
		nutzerprofilMenu.addItem("Merklise anzeigen", new Command() {

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

		suchprofilMenu.addItem("Neues Suchprofil anlegen", new Command() {

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
				ShowSuchprofil showSuchprofil = new ShowSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSuchprofil);
			}
		});

		suchprofilMenu.addSeparator();

		MenuBar partnervorschlaegeMenu = new MenuBar(true);
		partnervorschlaegeMenu.setAnimationEnabled(true);

		partnervorschlaegeMenu.addItem("Unangesehene Partnervorschlaege anzeigen", new Command() {

			@Override
			public void execute() {

				ClientsideSettings.getPartnerboerseAdministration()
						.berechneAehnlichkeitNpFor(nutzerprofil.getProfilId(), new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(Void result) {

							}

						});

				ShowPartnervorschlaegeNp showPartnervorschlaegeNp = new ShowPartnervorschlaegeNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaegeNp);

			}

		});

		partnervorschlaegeMenu.addItem("Partnervorschlaege anhand von Suchprofilen anzeigen", new Command() {

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

							}

						});

				ShowPartnervorschlaegeSp showPartnervorschlaegeSp = new ShowPartnervorschlaegeSp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaegeSp);

			}

		});

		partnervorschlaegeMenu.addSeparator();

		menu.addItem(new MenuItem("Mein Profil", nutzerprofilMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Mein Suchprofil", suchprofilMenu));
		menu.addSeparator();
		menu.addItem(new MenuItem("Meine Partnervorschlaege", partnervorschlaegeMenu));

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
