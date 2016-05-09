package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowEigenesNp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowEigenesNp() {
		this.add(verPanel);

		/**
		 * Label für Überschrift erstellen
		 */
		final Label ueberschriftLabel = new Label("Eigenes Nutzerprofil");
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * Tabelle zur Anzeige des eigenen Profils erstellen.
		 */
		final FlexTable showEigenesNpFlexTable = new FlexTable();

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showEigenesNpFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showEigenesNpFlexTable.setText(1, 0, "Vorname");
		showEigenesNpFlexTable.setText(2, 0, "Nachname");
		showEigenesNpFlexTable.setText(3, 0, "Geschlecht");
		showEigenesNpFlexTable.setText(4, 0, "Geburtsdatum");
		showEigenesNpFlexTable.setText(5, 0, "Religion");
		showEigenesNpFlexTable.setText(6, 0, "Koerpergroesse");
		showEigenesNpFlexTable.setText(7, 0, "Haarfarbe");
		showEigenesNpFlexTable.setText(8, 0, "Raucher?");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenesNpFlexTable.setCellPadding(6);
		showEigenesNpFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		showEigenesNpFlexTable.addStyleName("FlexTable");
		
		/**
		 * InfoLabel erstellen um Text auszugeben
		 */

		final Label infoLabel = new Label();

		ClientsideSettings.getPartnerboerseAdministration()
				.getNutzerprofilById(Benutzer.getProfilId(),
						new AsyncCallback<Nutzerprofil>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");

							}

							@Override
							public void onSuccess(Nutzerprofil result) {

								// Nutzerprofil-Id aus der Datenabank holen
								// und in Tabelle eintragen
								
								final String nutzerprofilId = String
										.valueOf(result.getProfilId());
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
								
								showEigenesNpFlexTable.setText(4, 1,
										result.getGeburtsdatum());

								
								// Religion aus der Datenbank holen
								// und in Tabelle eintragen
								
								showEigenesNpFlexTable.setText(5, 1,
										result.getReligion());

								
								// Koerpergroesse aus der Datenbank holen
								// und in Tabelle eintragen
								
								showEigenesNpFlexTable.setText(6, 1,
										result.getKoerpergroesse());

								
								// Haarfarbe aus der Datenbank holen
								// und in Tabelle eintragen
								
								showEigenesNpFlexTable.setText(7, 1,
										result.getHaarfarbe());

								
								// Raucher aus der Datenbank holen
								// und in Tabelle eintragen
								
								showEigenesNpFlexTable.setText(8, 1,
										result.getRaucher());

							}
						});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenesNpFlexTable);
		verPanel.add(infoLabel);

		// Bearbeiten-Button hinzufügen und ausbauen.

		final Button bearbeitenButton = new Button("Bearbeiten");
		verPanel.add(buttonPanel);
		buttonPanel.add(bearbeitenButton);

		// Löschen-Button hinzufügen und ausbauen.

		final Button loeschenButton = new Button("Löschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);

		// ClickHandler für den Löschen-Button hinzufügen.

		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditNutzerprofil editNutzerprofil = new EditNutzerprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editNutzerprofil);

			}
		});

		// ClickHandler für den Löschen-Button hinzufügen.
		
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			}
		});
	}

}
