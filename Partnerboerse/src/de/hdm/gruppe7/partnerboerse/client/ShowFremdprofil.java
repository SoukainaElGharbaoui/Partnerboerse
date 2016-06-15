package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowFremdprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanel hinzuf�gen.
	 */
	private VerticalPanel verPanel1 = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();

	/**
	 * HorizontalPanel hinzuf�gen.
	 */
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Vermerk-Button und Sperrung-Button hinzufuegen.
	 */
	private Button mButton = new Button();
	private Button sButton = new Button();

	/**
	 * Konstruktor hinzuf�gen.
	 */
	public ShowFremdprofil(final int fremdprofilId) {
		this.add(horPanel);

		/**
		 * �berschrift-Label hinzuf�gen.
		 */
		final Label ueberschriftLabel = new Label("Fremdprofil");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Information-Label hinzuf�gen.
		 */
		final Label infoLabel = new Label();

		/**
		 * Tabelle zur Anzeige des Fremdprofils erstellen.
		 */
		final FlexTable showFremdprofilFlexTable = new FlexTable();

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showFremdprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showFremdprofilFlexTable.setText(1, 0, "Vorname");
		showFremdprofilFlexTable.setText(2, 0, "Nachname");
		showFremdprofilFlexTable.setText(3, 0, "Geschlecht");
		showFremdprofilFlexTable.setText(4, 0, "Geburtsdatum");
		showFremdprofilFlexTable.setText(5, 0, "Körpergröße");
		showFremdprofilFlexTable.setText(6, 0, "Haarfarbe");
		showFremdprofilFlexTable.setText(7, 0, "Raucherstatus");
		showFremdprofilFlexTable.setText(8, 0, "Religion");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showFremdprofilFlexTable.setCellPadding(6);
		showFremdprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		showFremdprofilFlexTable.addStyleName("FlexTable");

		/**
		 * Entsprechendes Fremdprofil abrufen.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getFremdprofilById(fremdprofilId,
				new AsyncCallback<Nutzerprofil>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Nutzerprofil result) {

						// Nutzerprofil-Id aus der Datenabank holen
						// und in Tabelle eintragen
						final String nutzerprofilId = String.valueOf(result.getProfilId());
						showFremdprofilFlexTable.setText(0, 1, nutzerprofilId);

						// Vorname aus Datenbank aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(1, 1, result.getVorname());

						// Nachname aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(2, 1, result.getNachname());

						// Geschlecht aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(3, 1, result.getGeschlecht());

						// Geburtsdatum aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(4, 1, String.valueOf(result.getGeburtsdatumDate()));

						// Koerpergroesse aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));

						// Haarfarbe aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(6, 1, result.getHaarfarbe());

						// Raucherstatus aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(7, 1, result.getRaucher());

						// Religion aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(8, 1, result.getReligion());

					}
				});

		/**
		 * ABSCHNITT MERKLISTE UND SPERRLISTE BEGINN: Programmierung
		 * "Vermerk setzen" / "Vermerk löschen", "Sperrung setzen" /
		 * "Sperrung löschen" Button.
		 */

		// Beim Aufruf des Fremdprofils pruefen, ob dieses vom Benutzer gesperrt
		// wurde.
		ClientsideSettings.getPartnerboerseAdministration().pruefeSperrstatusFremdprofil(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Integer result) {
						if (result == 1) {
							// Falls eine Sperrung vorliegt, lautet die
							// Aufschrift des Sperrung-Buttons "Sperrung
							// loeschen".
							sButton.setText("Sperrung löschen");
							// Falls eine Sperrung vorliegt, wird der
							// Vermerk-Button ausgeblendet.
							mButton.setVisible(false);
						} else {
							// Falls keine Sperrung vorliegt, lautet die
							// Aufschrift des Sperrung-Buttons "Sperrung
							// setzen".
							sButton.setText("Sperrung setzen");
						}
					}
				});

		// ClickHandler fuer den Sperrung-Button hinzufuegen.
		sButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Sperrstatus aendern.
				ClientsideSettings.getPartnerboerseAdministration().sperrstatusAendern(nutzerprofil.getProfilId(),
						fremdprofilId, new AsyncCallback<Integer>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Integer result) {
								if (result == 0) {
									// Falls eine Sperrung vorliegt, wird die
									// Aufschrift des Sperrung-Buttons zu
									// "Sperrung loeschen" geaendert.
									sButton.setText("Sperrung löschen");
									// Falls eine Sperrung vorliegt, wird der
									// Vermerk-Button ausgeblendet.
									mButton.setVisible(false);
								} else {
									// Falls keine Sperrung vorliegt, wird die
									// Aufschrift des Sperrung-Buttons zu
									// "Sperrung setzen" geaendert.
									sButton.setText("Sperrung setzen");
									// Falls keine Sperrung vorliegt, wird die
									// Aufschrift des Vermerk-Buttons zu
									// "Sperrung setzen" geaendert.
									mButton.setText("Vermerk setzen");
									// Falls keine Sperrung vorliegt, wird der
									// Vermerk-Button eingeblendet.
									mButton.setVisible(true);
								}

							}

						});

			}

		});

		// Beim Aufruf des Fremdprofils pruefen, ob dieses vom Benutzer vermerkt
		// wurde.
		ClientsideSettings.getPartnerboerseAdministration().pruefeVermerkstatus(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Integer result) {
						if (result == 1) {
							// Falls ein Vermerk vorliegt, lautet die Aufschrift
							// des Vermerk-Buttons "Vermerk loeschen".
							mButton.setText("Vermerk löschen");
							// Falls kein Vermerk vorliegt, lautet die
							// Aufschrift des Vermerk-Buttons "Vermerk setzen".
						} else {
							mButton.setText("Vermerk setzen");
						}
					}
				});

		// ClickHandler fuer den Vermerk-Button hinzufuegen.
		mButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Vermerkstatus aendern.
				ClientsideSettings.getPartnerboerseAdministration().vermerkstatusAendern(nutzerprofil.getProfilId(),
						fremdprofilId, new AsyncCallback<Integer>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Integer result) {
								if (result == 0) {
									// Falls ein Vermerk vorliegt, wird die
									// Aufschrift des Vermerk-Buttons zu
									// "Vermerk loeschen" geaendert.
									mButton.setText("Vermerk löschen");
								} else {
									// Falls kein Vermerk vorliegt, wird die
									// Aufschrift des Vermerk-Buttons zu
									// "Vermerk setzen" geaendert.
									mButton.setText("Vermerk setzen");
								}

							}

						});

			}

		});

		buttonPanel.add(mButton);
		buttonPanel.add(sButton);

		/**
		 * ABSCHNITT MERKLISTE UND SPERRLISTE ENDE: Programmierung
		 * "Vermerk setzen" / "Vermerk löschen", "Sperrung setzen" /
		 * "Sperrung löschen" Button.
		 */

		verPanel1.add(ueberschriftLabel);
		verPanel1.add(showFremdprofilFlexTable);
		verPanel1.add(infoLabel);
		verPanel1.add(buttonPanel);
		horPanel.add(verPanel1);

		ShowFremdinfo fremdinfo = new ShowFremdinfo(fremdprofilId);
		verPanel2.add(fremdinfo);
		horPanel.add(verPanel2);

	}
}