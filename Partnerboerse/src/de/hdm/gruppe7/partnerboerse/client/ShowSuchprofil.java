package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

public class ShowSuchprofil extends VerticalPanel {
	
	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanels und HorizontalPanels hinzufuegen.
	 */
	private VerticalPanel suchprofilPanel = new VerticalPanel();
	private VerticalPanel infoPanel = new VerticalPanel();

	private HorizontalPanel gesamtPanel = new HorizontalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets hinzufuegen.
	 */
	private Label auswahlLabel = new Label("Wählen Sie das anzuzeigende Suchprofil aus.");
	private Label infoLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private FlexTable showSuchprofilFlexTable = new FlexTable();
	private Button createSuchprofilButton = new Button("Neues Suchprofil anlegen");
	private Button anzeigenButton = new Button("Anzeigen");
	private Button loeschenButton = new Button("Löschen");
	private Button bearbeitenButton = new Button("Bearbeiten");

	/**
	 * Konstruktor hinzufuegen.
	 */
	public ShowSuchprofil() {

		this.add(gesamtPanel);
		gesamtPanel.add(suchprofilPanel);
		gesamtPanel.add(infoPanel);

		/**
		 * CSS auf Auswahl-Label anwenden.
		 */
		auswahlLabel.addStyleName("partnerboerse-label");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showSuchprofilFlexTable.setCellPadding(6);
		showSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		showSuchprofilFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Zeile der FlexTable definieren.
		 */
		showSuchprofilFlexTable.setText(0, 0, "Suchprofil-id");
		showSuchprofilFlexTable.setText(1, 0, "Suchprofilname");
		showSuchprofilFlexTable.setText(2, 0, "Geschlecht");
		showSuchprofilFlexTable.setText(3, 0, "Alter von");
		showSuchprofilFlexTable.setText(4, 0, "Alter bis");
		showSuchprofilFlexTable.setText(5, 0, "Koerpergroesse");
		showSuchprofilFlexTable.setText(6, 0, "Haarfarbe");
		showSuchprofilFlexTable.setText(7, 0, "Raucher");
		showSuchprofilFlexTable.setText(8, 0, "Religion");

		/**
		 * Auswahl-ListBox mit allen Suchprofilnamen des Nutzers füllen.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(), 
				new AsyncCallback<List<Suchprofil>>() {

			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");
			}

			public void onSuccess(List<Suchprofil> result) {
				if (result.isEmpty()) {
					auswahlListBox.setVisible(false);
					anzeigenButton.setVisible(false);
					auswahlLabel.setText("Sie haben bisher kein Suchprofil angelegt.");

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
		 * ClickHandler fuer den Anzeigen-Button hinzufuegen.
		 */
		anzeigenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Tabelle mit Suchprofildaten befuellen.
				ClientsideSettings.getPartnerboerseAdministration()
						.getSuchprofilByName(nutzerprofil.getProfilId(), 
								auswahlListBox.getSelectedItemText(), new AsyncCallback<Suchprofil>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Suchprofil result) {
								
								// Suchprofil-ID
								int suchprofilId = result.getProfilId();
								showSuchprofilFlexTable.setText(0, 1, String.valueOf(suchprofilId));

								// Suchprofilname
								showSuchprofilFlexTable.setText(1, 1, result.getSuchprofilName());

								// Geschlecht
								showSuchprofilFlexTable.setText(2, 1, result.getGeschlecht());

								// AlterMax
								showSuchprofilFlexTable.setText(3, 1, Integer.toString(result.getAlterMinInt()));

								// AlterMin
								showSuchprofilFlexTable.setText(4, 1, Integer.toString(result.getAlterMaxInt()));

								// Koerpergroesse
								showSuchprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));

								// Haarfarbe
								showSuchprofilFlexTable.setText(6, 1, result.getHaarfarbe());

								// Raucher
								showSuchprofilFlexTable.setText(7, 1, result.getRaucher());

								// Religion
								showSuchprofilFlexTable.setText(8, 1, result.getReligion());

								// Infos
								ShowInfoNp showInfoNp = new ShowInfoNp(suchprofilId);
								infoPanel.clear();
								infoPanel.add(showInfoNp);

							}

						});

				/**
				 * ClickHandler fuer den Loeschen-Button hinzufuegen.
				 */
				loeschenButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {

						// Suchprofil loeschen.
						ClientsideSettings.getPartnerboerseAdministration()
								.deleteSuchprofil(nutzerprofil.getProfilId(), auswahlListBox.getSelectedItemText(), new AsyncCallback<Void>() {

									public void onFailure(Throwable caught) {
										infoLabel.setText("Es trat ein Fehler auf");
									}

									public void onSuccess(Void result) {
										ShowSuchprofil showSuchprofil = new ShowSuchprofil();
										suchprofilPanel.clear();
										infoPanel.clear();
										suchprofilPanel.add(showSuchprofil);
									}

								});

					}

				});

				/**
				 * ClickHandler fuer den Bearbeiten-Button hinzfuegen.
				 */
				bearbeitenButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						// Seite zum Bearbeiten eines Suchprofils hinzufuegen.
						EditSuchprofil editSuchprofil = new EditSuchprofil(auswahlListBox.getSelectedItemText());
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(editSuchprofil);

					}

				});

				suchprofilPanel.add(showSuchprofilFlexTable);
				buttonPanel.add(bearbeitenButton);
				buttonPanel.add(loeschenButton);
				suchprofilPanel.add(buttonPanel);
				suchprofilPanel.add(infoLabel);

			}

		});

		suchprofilPanel.add(auswahlLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		auswahlPanel.add(createSuchprofilButton);
		suchprofilPanel.add(auswahlPanel);

	}

}
