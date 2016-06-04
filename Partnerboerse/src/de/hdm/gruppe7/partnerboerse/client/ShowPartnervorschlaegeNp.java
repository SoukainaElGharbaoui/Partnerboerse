package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;

public class ShowPartnervorschlaegeNp extends VerticalPanel {

	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	/**
	 * Variablen
	 */
	int ergebnis = 0;

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowPartnervorschlaegeNp() {

		this.add(verPanel);

		/**
		 * Überschrift-Label hinzufügen.
		 */
		final Label ueberschriftLabel = new Label("Diese Nutzerprofile koennten zu ihnen passen");
		ueberschriftLabel.addStyleDependentName("partnerboerse-label");
		verPanel.add(ueberschriftLabel);

		final Label infoLabel = new Label();
		final Label ergebnisLabel = new Label();

		/**
		 * Tabelle zur Anzeige der Partnervorschlaege hinzufuegen.
		 */
		final FlexTable partnervorschlaegeNpFlexTable = new FlexTable();

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		partnervorschlaegeNpFlexTable.setCellPadding(6);
		partnervorschlaegeNpFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		partnervorschlaegeNpFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		partnervorschlaegeNpFlexTable.setText(0, 0, "F-ID");
		partnervorschlaegeNpFlexTable.setText(0, 1, "Uebereinstimmung in %");
		partnervorschlaegeNpFlexTable.setText(0, 2, "Vorname");
		partnervorschlaegeNpFlexTable.setText(0, 3, "Nachname");
		partnervorschlaegeNpFlexTable.setText(0, 4, "Geburtsdatum");
		partnervorschlaegeNpFlexTable.setText(0, 5, "Geschlecht");
		partnervorschlaegeNpFlexTable.setText(0, 6, "Anzeigen");

		ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeNp(

				new AsyncCallback<List<Nutzerprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf");
					}

					@Override
					public void onSuccess(List<Nutzerprofil> result) {
						infoLabel.setText("Es trat kein Fehler auf");
						int row = partnervorschlaegeNpFlexTable.getRowCount();

						for (Nutzerprofil np : result) {

							final int fremdprofilId = np.getProfilId();
							row++;
							partnervorschlaegeNpFlexTable.setText(row, 0, String.valueOf(np.getProfilId()));
							partnervorschlaegeNpFlexTable.setText(row, 1, String.valueOf(np.getAehnlichkeit()) + "%");
							partnervorschlaegeNpFlexTable.setText(row, 2, np.getVorname());
							partnervorschlaegeNpFlexTable.setText(row, 3, np.getNachname());
							partnervorschlaegeNpFlexTable.setText(row, 4, String.valueOf(np.getGeburtsdatumDate()));
							partnervorschlaegeNpFlexTable.setText(row, 5, np.getGeschlecht());

							// Anzeigen-Button hinzufügen und ausbauen.
							final Button anzeigenButton = new Button("Anzeigen");
							partnervorschlaegeNpFlexTable.setWidget(row, 6, anzeigenButton);

							// ClickHandler für den Anzeigen-Button hinzufügen.
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									// Besuch in die Datenbank einfügen.
									ClientsideSettings.getPartnerboerseAdministration().besuchSetzen(
											fremdprofilId, new AsyncCallback<Void>() {


												@Override
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf.");
												}

												@Override
												public void onSuccess(Void result) {
													ShowFremdprofil showFremdprofil = new ShowFremdprofil(
															fremdprofilId);
													RootPanel.get("Details").clear();
													RootPanel.get("Details").add(showFremdprofil);
												}

											});
								}

							});

						}
					}

				});

		verPanel.add(ergebnisLabel);
		verPanel.add(infoLabel);
		verPanel.add(partnervorschlaegeNpFlexTable);

	}
}