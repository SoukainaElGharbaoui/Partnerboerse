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

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowSuchprofil extends VerticalPanel {

	private HorizontalPanel horPanel = new HorizontalPanel();

	private VerticalPanel verPanel = new VerticalPanel();

	private VerticalPanel verPanel2 = new VerticalPanel();

	private VerticalPanel verPanel3 = new VerticalPanel();

	private HorizontalPanel auswahlPanel = new HorizontalPanel();

	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Konstruktor
	 */
	public ShowSuchprofil() {

		this.add(horPanel);

		this.add(verPanel);
		this.add(verPanel2);
		this.add(verPanel3);

		horPanel.add(verPanel2);
		horPanel.add(verPanel3);

		/**
		 * Labels, AuswahlBox, Buttons und FlexTable erstellen
		 */

		final Label auswahlLabel = new Label("WÃ¤hlen Sie das anzuzeigende Suchprofil aus.");
		auswahlLabel.addStyleName("partnerboerse-label");

		final Label infoLabel = new Label();

		final ListBox auswahlListBox = new ListBox();

		final FlexTable showSuchprofilFlexTable = new FlexTable();

		final Button anzeigenButton = new Button("Anzeigen");

		final Button loeschenButton = new Button("LÃ¶schen");

		final Button bearbeitenButton = new Button("Bearbeiten");

		final Button createSuchprofilButton = new Button("Neues Suchprofil anlegen");
		/**
		 * FelxTable formatieren
		 */

		showSuchprofilFlexTable.setCellPadding(6);
		showSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		showSuchprofilFlexTable.addStyleName("FlexTable");

		/*
		 * Erste Zeile der FlexTable definieren
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
		 * Die AuswahlBox wird mit allen Suchprofilen des Nutzers gefüllt
		 */

		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(new AsyncCallback<List<Suchprofil>>() {

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");

			}

			@Override
			public void onSuccess(List<Suchprofil> result) {
				for (Suchprofil s : result) {
					auswahlListBox.addItem(s.getSuchprofilName());
				}

			}

		});

		/**
		 * Bei Betätigung des createSuchrprofilButtons werden alle
		 * Aehnlichkeiten gelöscht
		 */

		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().aehnlichkeitEntfernenSp(new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {

					}

					public void onSuccess(Void result) {

					}

				});

				CreateSuchprofil createSuchprofil = new CreateSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);
			}

		});

		anzeigenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.getSuchprofilByName(auswahlListBox.getSelectedItemText(), new AsyncCallback<Suchprofil>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");

							}

							@Override
							public void onSuccess(Suchprofil result) {

								// Suchprofil-ID
								final String suchprofilId = String.valueOf(result.getProfilId());
								showSuchprofilFlexTable.setText(0, 1, suchprofilId);

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

								// Religion aus der Datenbank holen
								showSuchprofilFlexTable.setText(8, 1, result.getReligion());
								
								ShowInfoSp showInfoSp = new ShowInfoSp(Integer.valueOf(suchprofilId));
								verPanel3.clear();
								verPanel3.add(showInfoSp);
//								
//								ShowInfoSp showInfoSp = new ShowInfoSp(
//										Integer.valueOf(showSuchprofilFlexTable.getText(0, 1)));
//								verPanel3.add(showInfoSp);
							}

						});
				

				loeschenButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {



						ClientsideSettings.getPartnerboerseAdministration()
								.deleteSuchprofil(auswahlListBox.getSelectedItemText(), new AsyncCallback<Void>() {


									@Override
									public void onFailure(Throwable caught) {
										infoLabel.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Void result) {
										infoLabel.setText("Das Suchprofil wurde erfolgreich gelÃ¶scht");
									}

								});

					}

				});

				bearbeitenButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						EditSuchprofil editSuchprofil = new EditSuchprofil(auswahlListBox.getSelectedItemText());
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(editSuchprofil);

					}

				});

				verPanel2.add(showSuchprofilFlexTable);
				buttonPanel.add(bearbeitenButton);
				buttonPanel.add(loeschenButton);
				verPanel2.add(buttonPanel);
				verPanel2.add(infoLabel);

			}

		});

		verPanel.add(createSuchprofilButton);
		verPanel.add(auswahlLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		verPanel.add(auswahlPanel);

	}

}