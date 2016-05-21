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
	 * HorizontalPanel
	 */
	private HorizontalPanel horPanel = new HorizontalPanel();
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel1 = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();
	
	/**
	 * Label Button
	 */
	HorizontalPanel buttonPanel = new HorizontalPanel();
	

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowEigenesNp() {
		this.add(horPanel);

		/**
		 * Label für Überschrift erstellen
		 */
		final Label ueberschriftLabel = new Label("Ihr Nutzerprofil:");
		ueberschriftLabel.addStyleName("partnerboerse-label"); 

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
		showEigenesNpFlexTable.setText(5, 0, "Körpergröße");
		showEigenesNpFlexTable.setText(6, 0, "Haarfarbe");
		showEigenesNpFlexTable.setText(7, 0, "Raucherstatus");
		showEigenesNpFlexTable.setText(8, 0, "Religion");
		
		
		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenesNpFlexTable.setCellPadding(6);
		showEigenesNpFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
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
								
								// Koerpergroesse aus der Datenbank holen
								// und in Tabelle eintragen

								showEigenesNpFlexTable.setText(5, 1,
										result.getKoerpergroesse());
								
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




							}


				});
		verPanel1.add(ueberschriftLabel);
		verPanel1.add(showEigenesNpFlexTable);
		verPanel1.add(infoLabel);



								// Löschen-Button hinzufügen und ausbauen.
								final Button loeschenButton = new Button("Löschen");
								verPanel1.add(buttonPanel);
								buttonPanel.add(loeschenButton);

								// Bearbeiten-Button hinzufügen und ausbauen.
								final Button bearbeitenButton = new Button("Bearbeiten");
								verPanel1.add(buttonPanel);
								buttonPanel.add(bearbeitenButton);

								// ClickHandler für den Bearbeiten-Button
								// hinzufügen.

								bearbeitenButton.addClickHandler(new ClickHandler() {
											public void onClick(ClickEvent event) {
												EditNutzerprofil editNutzerprofil = new EditNutzerprofil(Benutzer.getProfilId());
												RootPanel.get("Details")
														.clear();
												RootPanel.get("Details").add(
														editNutzerprofil);

											}
										});

								// ClickHandler für den Löschen-Button
								// hinzufügen.

								loeschenButton
										.addClickHandler(new ClickHandler() {
											public void onClick(ClickEvent event) {

												ClientsideSettings
														.getPartnerboerseAdministration()
														.deleteNutzerprofil(
																Benutzer.getProfilId(),
																new AsyncCallback<Void>() {

																	@Override
																	public void onFailure(
																			Throwable caught) {
																		infoLabel
																				.setText("Es trat ein Fehler auf");
																	}

																	@Override
																	public void onSuccess(
																			Void result) {
																		infoLabel
																				.setText("Ihr Profil wurde erfolgreich gelöscht");
																	}

																});

											}
										});

		ShowInfo showInfo = new ShowInfo();
		verPanel2.add(showInfo);
		horPanel.add(verPanel1);
		horPanel.add(verPanel2);
								
							}
				

	}


