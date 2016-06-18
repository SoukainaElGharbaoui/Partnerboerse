package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowPartnervorschlaegeSp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	private HorizontalPanel horPanelTabelle = new HorizontalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	
	/**
	 * Tabelle zur Anzeige der Partnervorschlaege hinzufuegen.
	 */
	private FlexTable partnervorschlaegeSpFlexTable = new FlexTable();

	private Label ueberschriftLabel = new Label(
			"Wählen Sie das Suchprofil aus, zu welchem Sie Partnervorschläge anzeigen möchten:");
	
	private Label ueberschriftLabel2 = new Label("Diese Profile könnten Ihnen gefallen:");
	private Label infoLabel = new Label();
	private Label ergebnisLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private Button anzeigenSpButton = new Button("Partnervorschläge anzeigen");
	private Button anzeigenButton;
	private Button createSuchprofilButton = new Button("Neues Suchprofil anlegen");
	
	/**
	 * Variablen
	 */
	int ergebnis = 0;

	/**
	 * Konstruktor hinzufügen.
	 * 
	 * @param a
	 */
	public ShowPartnervorschlaegeSp() {

		this.add(verPanel);
		this.add(auswahlPanel);
		this.add(horPanelTabelle);

		/**
		 * Label, AuswahlBox und Buttons erstellen
		 */
		
		ueberschriftLabel.addStyleName("partnerboerse-label");
		ueberschriftLabel2.addStyleName("partnerboerse-label");

		/**
		 * die AuswahlBox wird mit allen Suchprofilen des Nutzers gef�llt
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<List<Suchprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");

					}

					@Override
					public void onSuccess(List<Suchprofil> result) {
						for (Suchprofil s : result) {
							auswahlListBox.addItem(s.getSuchprofilName());
						}
						
							if (result.isEmpty()) {
								auswahlListBox.setVisible(false);
								anzeigenSpButton.setVisible(false);
								ueberschriftLabel.setText("Sie haben bisher kein Suchprofil angelegt.");

								createSuchprofilButton.setVisible(true); 

							} else {
								for (Suchprofil s : result) {
									auswahlListBox.addItem(s.getSuchprofilName());
								}
								createSuchprofilButton.setVisible(false);
							}
					}

				});

		/**
		 * ClickHandler fuer den Suchprofil-Anlegen-Button hinzufuegen.
		 */
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Seite zum Anlegen eines neuen Suchprofils aufrufen.
				CreateSuchprofil createSuchprofil = new CreateSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);
			}

		});
		
		/**
		 * Bei Bet�tigung des AnzeigenButtons werden alle Partnervorschlaege
		 * anhand des gew�hlen Suchprofils ausgegeben, nach Aehnlichkeit
		 * geordnet
		 */

		anzeigenSpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				horPanelTabelle.clear();

				ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeSp(nutzerprofil.getProfilId(),
						auswahlListBox.getSelectedItemText(), new AsyncCallback<List<Nutzerprofil>>() {


							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf");
							}

							@Override
							public void onSuccess(List<Nutzerprofil> result) {

								// Bei jeder Auswahl eines Suchprofils soll die
								// Tabelle komplett gel�scht werden
								partnervorschlaegeSpFlexTable.removeAllRows();

								/**
								 * Tabelle formatieren und CSS einbinden.
								 * Tabelle wird bei jedem Klick komplett neu
								 * erstellt
								 */
								partnervorschlaegeSpFlexTable.setCellPadding(6);
								partnervorschlaegeSpFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
								partnervorschlaegeSpFlexTable.addStyleName("FlexTable");
								/**
								 * Erste Zeile der Tabelle festlegen.
								 */
								partnervorschlaegeSpFlexTable.setText(0, 0, "F-ID");
								partnervorschlaegeSpFlexTable.setText(0, 1, "Uebereinstimmung in %");
								partnervorschlaegeSpFlexTable.setText(0, 2, "Vorname");
								partnervorschlaegeSpFlexTable.setText(0, 3, "Nachname");
								partnervorschlaegeSpFlexTable.setText(0, 4, "Geburtsdatum");
								partnervorschlaegeSpFlexTable.setText(0, 5, "Geschlecht");
								partnervorschlaegeSpFlexTable.setText(0, 6, "Anzeigen");

								// Tabelle wird bef�llt und die Zeilenanzahl auf
								// 0 gesetzt
								int row = 0;
								for (Nutzerprofil np : result) {

									final int fremdprofilId = np.getProfilId();
									row++;
									partnervorschlaegeSpFlexTable.setText(row, 0, String.valueOf(np.getProfilId()));
									partnervorschlaegeSpFlexTable.setText(row, 1,
											String.valueOf(np.getAehnlichkeit()) + "%");
									partnervorschlaegeSpFlexTable.setText(row, 2, np.getVorname());
									partnervorschlaegeSpFlexTable.setText(row, 3, np.getNachname());
									
									Date geburtsdatum = np.getGeburtsdatumDate();
									String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
									
									partnervorschlaegeSpFlexTable.setText(row, 4, geburtsdatumString);
									partnervorschlaegeSpFlexTable.setText(row, 5, np.getGeschlecht());

									// Anzeigen-Button f�r das Fremdprofil
									// hinzufügen und ausbauen.
									anzeigenButton = new Button("Anzeigen");
									partnervorschlaegeSpFlexTable.setWidget(row, 6, anzeigenButton);

									// ClickHandler für den Anzeigen-Button
									// hinzufügen.
									anzeigenButton.addClickHandler(new ClickHandler() {
										public void onClick(ClickEvent event) {
											ShowFremdprofil showFremdprofil = new ShowFremdprofil(fremdprofilId);
											RootPanel.get("Details").clear();
											RootPanel.get("Details").add(showFremdprofil);

										}

									});

								}

							}

						});
				/**
				 * Alle Elemente dem verPanel hinzuf�gen
				 */
				verPanel.add(ergebnisLabel);
				verPanel.add(infoLabel);
				verPanel.add(ueberschriftLabel2);
				horPanelTabelle.add(partnervorschlaegeSpFlexTable);
				verPanel.add(horPanelTabelle);

			}

		});

		/**
		 * Alle Elemente dem vertical und horizontal Panel hinzuf�gen
		 */

		verPanel.add(ueberschriftLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenSpButton);
		verPanel.add(createSuchprofilButton);	
		verPanel.add(auswahlPanel);

	}

}
