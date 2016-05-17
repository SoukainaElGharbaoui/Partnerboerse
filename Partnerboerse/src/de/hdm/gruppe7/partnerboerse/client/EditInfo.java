package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class EditInfo extends VerticalPanel {

	int infoId;
	int profilId;

	private VerticalPanel verPanel = new VerticalPanel();

	public EditInfo() {
		this.add(verPanel);

		final Label ueberschriftLabel = new Label("Info bearbeiten");

		HorizontalPanel buttonPanel = new HorizontalPanel();

		// Tabelle für Beschreibungsinfo

		/**
		 * Tabelle erzeugen, in der die Beschreibungsinfos dargestellt werden.
		 */
		final FlexTable editInfoFlexTable = new FlexTable();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		editInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		editInfoFlexTable.setText(0, 1, "Eigenschaft");
		editInfoFlexTable.setText(0, 2, "Infotext");
		editInfoFlexTable.setText(0, 3, "Auswahloption");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		editInfoFlexTable.setCellPadding(6);
		editInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		editInfoFlexTable.addStyleName("FlexTable");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		final Label infoLabel = new Label();

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(
				Benutzer.getProfilId(), new AsyncCallback<List<Info>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Info> result) {
						// Anzahl der Zeilen ermitteln.
						int row = editInfoFlexTable.getRowCount();

						// Tabelle mit Inhalten aus der Datenbank befüllen.
						for (Info i : result) {
							row++;

							final String nutzerprofilId = String.valueOf(i
									.getNutzerprofilId());

							final TextArea textArea = new TextArea();
							editInfoFlexTable.setWidget(row, 2, textArea);

							editInfoFlexTable.setText(row, 0, nutzerprofilId);
							editInfoFlexTable.setText(row, 1,
									i.getEigenschaftErlaeuterung());
							editInfoFlexTable.setText(row, 2, i.getInfotext());
						}
					}
				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(editInfoFlexTable);

		
		// Tabelle für Auswahlinfos
		

		final Label editLabel = new Label();

		final ListBox ernaehrungListBox = new ListBox();
		ernaehrungListBox.addItem("vegetarisch");
		ernaehrungListBox.addItem("vegan");
		ernaehrungListBox.addItem("keine Angabe");
		editInfoFlexTable.setWidget(1, 3, ernaehrungListBox);

		final ListBox musikListBox = new ListBox();
		musikListBox.addItem("Pop");
		musikListBox.addItem("RnB");
		editInfoFlexTable.setWidget(2, 3, musikListBox);

		final Label infoLabel2 = new Label();

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(
				Benutzer.getProfilId(), new AsyncCallback<List<Info>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel2.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Info> result) {

						int row = editInfoFlexTable.getRowCount();

						// Tabelle mit Inhalten aus der Datenbank befüllen.
						for (Info iA : result) {
							row++;
							final String nutzerprofilId = String.valueOf(iA
									.getNutzerprofilId());

							editInfoFlexTable.setText(row, 0,
									nutzerprofilId);
							editInfoFlexTable.setText(row, 1,
									iA.getEigenschaftErlaeuterung());
							editInfoFlexTable.setText(row, 2,
									iA.getOptionsbezeichnung());

						}
					}
				});

		/**
		 * Zum Panel hinzufï¿½gen
		 */

		verPanel.add(ueberschriftLabel);
		verPanel.add(editInfoFlexTable);
		// verPanel.add(infoLabel);
		verPanel.add(infoLabel2);
		verPanel.add(editLabel);

		/**
		 * ï¿½nderungen Speichern-Button hinzufÃ¼gen und ausbauen.
		 */
		final Button speichernButton = new Button("&Auml;nderungen speichern");
		verPanel.add(buttonPanel);
		buttonPanel.add(speichernButton);

		/**
		 * ClickHandler fï¿½r den Speichern-Button hinzufï¿½gen.
		 */
		final Label informationLabel = new Label();
		verPanel.add(informationLabel);
		
		

		 //speichernButton.addClickHandler(new ClickHandler() {
		 //public void onClick(ClickEvent event) {
		 //ClientsideSettings.getPartnerboerseAdministration().saveInfo(
		 //ernaehrungListBox.getSelectedItemText(),
		 //musikListBox.getSelectedItemText(),
		 //new AsyncCallback<Void>() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// informationLabel
		// .setText("Es trat ein Fehler auf");
		// }
		//
		// @Override
		// public void onSuccess(Void result) {
		// informationLabel
		// .setText("Info erfolgreich aktualisiert.");
		// ShowInfo showInfo = new ShowInfo();
		// RootPanel.get("Details").clear();
		// RootPanel.get("Details").add(showInfo);
		// }
		//
		// });
		// loeschenButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		//
		// ClientsideSettings.getPartnerboerseAdministration()
		// .deleteInfo(Benutzer.getProfilId(),
		// new AsyncCallback<Void>() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// infoLabel
		// .setText("Es trat ein Fehler auf");
		// }
		//
		// @Override
		// public void onSuccess(Void result) {
		// infoLabel
		// .setText("Die Info wurde erfolgreich gelöscht");
		// }

	}

}
