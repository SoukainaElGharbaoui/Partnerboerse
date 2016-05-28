package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

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
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class ShowInfo extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor
	 */
	public ShowInfo() {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */
		final Label ueberschriftLabel = new Label("Ihre Infos:");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Label Button
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		// Tabelle für Beschreibungsinfo

		/**
		 * Tabelle erzeugen, in der die Beschreibungsinfos dargestellt werden.
		 */
		final FlexTable showInfoFlexTable = new FlexTable();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showInfoFlexTable.setText(0, 1, "Eigenschaft");
		showInfoFlexTable.setText(0, 2, "Infotext");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		final Label informationLabel = new Label();

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(Benutzer.getProfilId(),
				new AsyncCallback<List<Info>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Info> result) {
						// Anzahl der Zeilen ermitteln.
						int row = showInfoFlexTable.getRowCount();

						// Tabelle mit Inhalten aus der Datenbank befüllen.
						for (Info i : result) {
							row++;

							final String nutzerprofilId = String.valueOf(i.getNutzerprofilId());
							// final String nutzerprofilId =
							// String.valueOf(iA.getNutzerprofilId());

							showInfoFlexTable.setText(row, 0, nutzerprofilId);
							showInfoFlexTable.setText(row, 1, i.getEigenschaftErlaeuterung());
							showInfoFlexTable.setText(row, 2, i.getInfotext());
							showInfoFlexTable.setText(row, 3, i.getOptionsbezeichnung());
						}
					}
				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);

		// Tabelle für Auswahlinfo

		/**
		 * Tabelle erzeugen, in der das Suchprofil dargestellt wird.
		 */
		// final FlexTable showInfoFlexTableAuswahl = new FlexTable();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		// showInfoFlexTableAuswahl.setText(0, 0, "Nutzerprofil-Id");
		// showInfoFlexTableAuswahl.setText(0, 1, "Eigenschaft");
		// showInfoFlexTableAuswahl.setText(0, 2, "Auswahloption");
		//
		// /**
		// * Tabelle formatieren und CSS einbinden.
		// */
		// showInfoFlexTableAuswahl.setCellPadding(6);
		// showInfoFlexTableAuswahl.getRowFormatter().addStyleName(0,
		// "TableHeader");
		// showInfoFlexTableAuswahl.addStyleName("FlexTable");

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(Benutzer.getProfilId(),
				new AsyncCallback<List<Info>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Info> result) {
						// Anzahl der Zeilen ermitteln.
						int row = showInfoFlexTable.getRowCount();

						// Tabelle mit Inhalten aus der Datenbank befüllen.
						for (Info iA : result) {
							row++;

							final String nutzerprofilId = String.valueOf(iA.getNutzerprofilId());

							showInfoFlexTable.setText(row, 0, nutzerprofilId);
							showInfoFlexTable.setText(row, 1, iA.getEigenschaftErlaeuterung());
							showInfoFlexTable.setText(row, 2, iA.getOptionsbezeichnung());
							showInfoFlexTable.setText(row, 3, iA.getInfotext());
						}
					}
				});

		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);

		// Löschen-Button hinzufügen und ausbauen.
		final Button loeschenButton = new Button("Alle Infos löschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);

		// Bearbeiten-Button hinzufügen und ausbauen.
		final Button bearbeitenButton = new Button("Bearbeiten");
		verPanel.add(buttonPanel);
		buttonPanel.add(bearbeitenButton);

		// ClickHandler für den Bearbeiten-Button hinzufügen.
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditInfo editInfo = new EditInfo();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editInfo);
			}
		});

		// ClickHandler für den Löschen-Button hinzufügen.
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().deleteAllInfos(Benutzer.getProfilId(),
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Es trat ein Fehler auf");
							}

							@Override
							public void onSuccess(Void result) {
								informationLabel.setText("Die gesamte Info wurde erfolgreich gelöscht");
							}

						});

			}
		});
	}
}
