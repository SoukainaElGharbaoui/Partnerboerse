package de.hdm.gruppe7.partnerboerse.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowMerkliste extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowMerkliste() {
		this.add(verPanel);

		/**
		 * Überschrift-Label hinzufügen.
		 */
		final Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Merkliste:");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Information-Label hinzufügen.
		 */
		final Label infoLabel = new Label();

		/**
		 * Tabelle zur Anzeige der gemerkten Kontakte hinzufügen.
		 */
		final FlexTable merklisteFlexTable = new FlexTable();

		/**
		 * Header-Zeile der Tabelle festlegen.
		 */
		merklisteFlexTable.setText(0, 0, "F-ID");
		merklisteFlexTable.setText(0, 1, "Vorname");
		merklisteFlexTable.setText(0, 2, "Nachname");
		merklisteFlexTable.setText(0, 3, "Geburtstag");
		merklisteFlexTable.setText(0, 4, "Geschlecht");
		merklisteFlexTable.setText(0, 5, "Löschen");
		merklisteFlexTable.setText(0, 6, "Anzeigen");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		merklisteFlexTable.setCellPadding(6);
		merklisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		merklisteFlexTable.addStyleName("FlexTable");

		ClientsideSettings.getPartnerboerseAdministration().getGemerkteNutzerprofileFor(
				new AsyncCallback<Merkliste>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Merkliste result) {
						Vector<Nutzerprofil> gemerkteNutzerprofile = result.getGemerkteNutzerprofile();
						int row = merklisteFlexTable.getRowCount();

						for (Nutzerprofil n : gemerkteNutzerprofile) {
							row++;

							final String fremdprofilId = String.valueOf(n.getProfilId());

							merklisteFlexTable.setText(row, 0, fremdprofilId);
							merklisteFlexTable.setText(row, 1, n.getVorname());
							merklisteFlexTable.setText(row, 2, n.getNachname());
							merklisteFlexTable.setText(row, 3, String.valueOf(n.getGeburtsdatumDate()));
							merklisteFlexTable.setText(row, 4, n.getGeschlecht());

							// Löschen-Button hinzufügen und ausbauen.
							final Button loeschenButton = new Button("Löschen");
							merklisteFlexTable.setWidget(row, 5, loeschenButton);

							// Anzeigen-Button hinzufügen und ausbauen.
							final Button anzeigenButton = new Button("Anzeigen");
							merklisteFlexTable.setWidget(row, 6, anzeigenButton);

							// Testzwecke: Index der FlexTable-Rows anzeigen.
							merklisteFlexTable.setText(row, 7, String.valueOf(row));

							// ClickHandler für den Löschen-Button hinzufügen.
							loeschenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									/**
									 * Tabelle nach Fremdprofil-ID durchsuchen;
									 * Index = Die Zeile, die gelöscht werden
									 * soll. Achtung: Die Tabelle darf erst ab
									 * Zeile 2 verwendet werden (Zeile 1 =
									 * Header-Zeile).
									 */
									for (int i = 2; i <= merklisteFlexTable.getRowCount(); i++) {

										String fremdprofilIdFlexTable = merklisteFlexTable.getText(i, 0);

										if (Integer.valueOf(fremdprofilIdFlexTable) == Integer.valueOf(fremdprofilId)) {

											// Inhalte aus der Datenbank
											// entfernen.
											ClientsideSettings.getPartnerboerseAdministration().vermerkstatusAendern(
													Integer.valueOf(fremdprofilId),
													new AsyncCallback<Integer>() {

														@Override
														public void onFailure(Throwable caught) {
															infoLabel.setText("Es trat ein Fehler auf.");
														}

														@Override
														public void onSuccess(Integer result) {
															infoLabel.setText(
																	"Das Nutzerprofil wurde erfolgreich von Ihrer Merkliste entfernt.");
														}

													});

											// Zeile in Tabelle löschen.
											merklisteFlexTable.removeRow(i);
											break;
										}
									}

								}

							});

							// ClickHandler für den Anzeigen-Button hinzufügen.
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

//									 Prüfen, ob Benutzer von Fremdprofil
//									 gesperrt wurde.
									ClientsideSettings.getPartnerboerseAdministration().getSperrstatusEigenesProfil(
											Integer.valueOf(fremdprofilId),
											new AsyncCallback<Integer>() {

												@Override
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf.");

												}

												@Override
												public void onSuccess(Integer result) {
													// Wenn keine Sperrung
													// vorliegt...
													if (result == 0) {
														ShowFremdprofil showFremdprofil = new ShowFremdprofil(
																Integer.valueOf(fremdprofilId));
														RootPanel.get("Details").clear();
														RootPanel.get("Details").add(showFremdprofil);

														// Wenn eine Sperrung
														// vorliegt...
													} else {
														
														// Bildschirmmeldung ausgeben.
														Window.alert("Sie können dieses Nutzerprofil nicht anzeigen, da Sie von diesem gesperrt wurden.");
														
////														
////														// DialobBox hinzufügen.
////														final DialogBox sperrungDialogBox = new DialogBox();
////
////														sperrungDialogBox.setText("Information");
////														sperrungDialogBox.setAnimationEnabled(true);
////														// Schließen-Button
////														// hinzufügen.
////														final Button schliessenButton = new Button("Schließen");
////														// VerticalPanel
////														// hinzufügen.
////														final VerticalPanel sperrungVerPanel = new VerticalPanel();
////														// Label hinzufügen.
////														final Label sperrungLabel = new Label(
////																"Sie können dieses Nutzerprofil nicht anzeigen, da Sie von diesem gesperrt wurden.");
////														// Widgets zum
////														// VerticalPanel
////														// hinzufügen.
////														sperrungVerPanel.add(sperrungLabel);
////														sperrungVerPanel
////																.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
////														sperrungVerPanel.add(schliessenButton);
////														sperrungDialogBox.setWidget(sperrungVerPanel);
////														sperrungDialogBox.center();
////
////														// ClickHandler für den
////														// Schließen-Button
////														// hinzufügen.
////														schliessenButton.addClickHandler(new ClickHandler() {
////
////															@Override
////															public void onClick(ClickEvent event) {
////																sperrungDialogBox.hide();
////
////															}
//
////														});
//
													}

												}

											});

								}

							});

						}

					}

				});

		// Widgets zum VerticalPanel hinzufügen.
		verPanel.add(ueberschriftLabel);
		verPanel.add(merklisteFlexTable);
		verPanel.add(infoLabel);

	}

}