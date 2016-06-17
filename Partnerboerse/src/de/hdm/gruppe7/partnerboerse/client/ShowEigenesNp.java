package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowEigenesNp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Panels hinzufuegen.
	 */
	private VerticalPanel verPanel1 = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();
	private VerticalPanel loeschenVerPanel = new VerticalPanel();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets hinzufuegen.
	 */
	private Label ueberschriftLabel = new Label("Ihr Profil:");
	private FlexTable showEigenesNpFlexTable = new FlexTable();
	private Label infoLabel = new Label();
	private Button loeschenButton = new Button("Löschen");
	private Button bearbeitenButton = new Button("Bearbeiten");
	private DialogBox loeschenDialogBox = new DialogBox();
	private Button jaButton = new Button("Ja");
	private Button neinButton = new Button("Nein");
	private Label loeschenLabel = new Label(
			"Möchten Sie Ihr Profil wirklich löschen?");

	/**
	 * Konstruktor hinzufuegen.
	 */
	public ShowEigenesNp() {

		this.add(horPanel);
		horPanel.add(verPanel1);
		horPanel.add(verPanel2);

		/**
		 * CSS anwenden.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showEigenesNpFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showEigenesNpFlexTable.setText(1, 0, "Vorname");
		showEigenesNpFlexTable.setText(2, 0, "Nachname");
		showEigenesNpFlexTable.setText(3, 0, "Geschlecht");
		showEigenesNpFlexTable.setText(4, 0, "Geburtsdatum");
		showEigenesNpFlexTable.setText(5, 0, "Körpergröße");
		showEigenesNpFlexTable.setText(6, 0, "Haarfarbe");
		showEigenesNpFlexTable.setText(7, 0, "Raucherstatus");
		showEigenesNpFlexTable.setText(8, 0, "Religion");
		showEigenesNpFlexTable.setText(9, 0, "EMail");

		/**
		 * Tabelle formatieren.
		 */
		showEigenesNpFlexTable.setCellPadding(6);
		showEigenesNpFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		showEigenesNpFlexTable.addStyleName("FlexTable");

		/**
		 * Nutzerprofil anhand der Profil-ID auslesen.
		 */
		ClientsideSettings.getPartnerboerseAdministration()
				.getNutzerprofilById(nutzerprofil.getProfilId(),
						new AsyncCallback<Nutzerprofil>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Nutzerprofil result) {
								
								// Nutzerprofil-Id aus der Datenabank holen
								// und in Tabelle eintragen
								String nutzerprofilId = String.valueOf(result
										.getProfilId());

								nutzerprofil.setProfilId(Integer
										.valueOf(nutzerprofilId));
								showEigenesNpFlexTable.setText(0, 1,
										nutzerprofilId);

								// Vorname aus Datenbank aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(1, 1,
										result.getVorname());

								// Nachname aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(2, 1,
										result.getNachname());

								// Geschlecht aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(3, 1,
										result.getGeschlecht());

								// Geburtsdatum aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(4, 1, String
										.valueOf(result.getGeburtsdatumDate()));

								// Koerpergroesse aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(5, 1,
										(Integer.toString(result
												.getKoerpergroesseInt())));

								// Haarfarbe aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(6, 1,
										result.getHaarfarbe());

								// Raucher aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(7, 1,
										result.getRaucher());

								// Religion aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(8, 1,
										result.getReligion());

								// EMail aus der Datenbank holen
								// und in Tabelle eintragen
								showEigenesNpFlexTable.setText(9, 1,
										result.getEmailAddress());
							}
						});

		/**
		 * ClickHandler fuer den Button zum Bearbeiten hinzufuegen.
		 */
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Seite zum Bearbeiten des Nutzerprofils aufrufen.
				EditNutzerprofil editNutzerprofil = new EditNutzerprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editNutzerprofil);
			}
		});

		/**
		 * ClickHandler fuer den Loeschen-Button hinzufuegen.
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// DialogBox, die abfragt, ob man das Nutzerprofil wirklich
				// loeschen moechte, ausbauen.
				loeschenDialogBox.setText("Information");
				loeschenDialogBox.setAnimationEnabled(true);
				loeschenVerPanel.add(loeschenLabel);
				loeschenVerPanel
						.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
				loeschenVerPanel.add(jaButton);
				loeschenVerPanel.add(neinButton);
				loeschenDialogBox.setWidget(loeschenVerPanel);
				loeschenDialogBox.center();

				/**
				 * ClickHandler fuer den Ja-Button hinzufuegen.
				 */
				jaButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {

						// Nutzerprofil loeschen.
						ClientsideSettings.getPartnerboerseAdministration()
								.deleteNutzerprofil(nutzerprofil.getProfilId(),
										new AsyncCallback<Void>() {

											public void onFailure(
													Throwable caught) {
												infoLabel
														.setText("Es trat ein Fehler auf.");
											}

											public void onSuccess(Void result) {
												/**
												 * Noch ausbauen: Weiterleitung
												 * auf Logout-Seite.
												 */
												
												HorizontalPanel loginPanel = new HorizontalPanel();
												
												Anchor signOutLink = new Anchor();
												signOutLink.setHref(nutzerprofil.getLogoutUrl());
												
												signOutLink.setText("Bestätige das Löschen mit einem Klick.");
												
												loginPanel.add(signOutLink);

												
												Anchor signIn = new Anchor();
												signIn.setText("Jetzt einloggen");
												 
												RootPanel.get("Navigator").clear();
												RootPanel.get("Details").clear();
												 
												RootPanel.get("Navigator").add(loginPanel);
											}
										});
						loeschenDialogBox.hide();
					}
				});

				/**
				 * ClickHandler fuer den Nein-Button hinzufuegen.
				 */
				neinButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						loeschenDialogBox.hide();
					}
				});
			}
		});

		
		
		/**
		 * Infos anzeigen.
		 */
		ShowInfoNp showInfoNp = new ShowInfoNp(nutzerprofil.getProfilId());

		/**
		 * Widgets den Panels hinzufuegen.
		 */
		verPanel1.add(ueberschriftLabel);
		verPanel1.add(showEigenesNpFlexTable);
		buttonPanel.add(loeschenButton);
		buttonPanel.add(bearbeitenButton);
		verPanel1.add(buttonPanel);
		verPanel1.add(infoLabel);
		verPanel2.add(showInfoNp);

	}

}
