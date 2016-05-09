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

import de.hdm.gruppe7.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowSuchprofil extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor
	 */
	public ShowSuchprofil() {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */
		final Label ueberschriftLabel = new Label("Aktuelles Suchprofil");

		/**
		 * Label Button
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * Tabelle erzeugen, in der das Suchprofil dargestellt wird.
		 */
		final FlexTable showSuchprofilFlexTable = new FlexTable();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showSuchprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showSuchprofilFlexTable.setText(1, 0, "Geschlecht");
		showSuchprofilFlexTable.setText(2, 0, "Koerpergroesse");
		showSuchprofilFlexTable.setText(3, 0, "Haarfarbe");
		showSuchprofilFlexTable.setText(4, 0, "AlterMax");
		showSuchprofilFlexTable.setText(5, 0, "AlterMin");
		showSuchprofilFlexTable.setText(6, 0, "Raucher");
		showSuchprofilFlexTable.setText(7, 0, "Religion");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showSuchprofilFlexTable.setCellPadding(6);
		showSuchprofilFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		showSuchprofilFlexTable.addStyleName("FlexTable");

		final Label infoLabel = new Label();

		ClientsideSettings.getPartnerboerseAdministration().getSuchprofilById(
				Benutzer.getProfilId(), new AsyncCallback<Suchprofil>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");

					}

					public void onSuccess(Suchprofil result) {

						// Nutzerprofil-Id aus der Datenabank holen
						final String nutzerprofilId = String.valueOf(result
								.getProfilId());
						showSuchprofilFlexTable.setText(0, 1, nutzerprofilId);

						// Geschlecht aus Datenbank aus der Datenbank holen
						showSuchprofilFlexTable.setText(1, 1,
								result.getGeschlecht());
					}

				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showSuchprofilFlexTable);
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
				EditSuchprofil editSuchprofil = new EditSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editSuchprofil);
			}
		});

		// ClickHandler für den Löschen-Button hinzufügen.
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.deleteSuchprofil(Benutzer.getProfilId(),
								new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										infoLabel
												.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Void result) {
										infoLabel
												.setText("Das Suchprofil wurde erfolgreich gelöscht");
									}

								});

			}
		});

	}

}
