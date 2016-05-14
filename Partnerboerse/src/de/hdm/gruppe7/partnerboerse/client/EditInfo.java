package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class EditInfo extends VerticalPanel {
	
	int infoId;
	int profilId;
	
	private VerticalPanel verPanel = new VerticalPanel();
	
	public EditInfo(){
		this.add(verPanel);
	}
	
	final Label ueberschriftLabel = new Label ("Info bearbeiten");
	
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	final FlexTable editInfoFlexTable = new FlexTable();
	
	editInfoFlexTable.setCellPadding(6);
	editInfoFlexTable.getColumnFormatter().addStyleName(0,
			"TableHeader");
	editInfoFlexTable.addStyleName("FlexTable");
	
	editInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
	editInfoFlexTable.setText(1, 0, "Eigenschaft");
	editInfoFlexTable.setText(2, 0, "Infotext");
	
	final Label editLabel = new Label();

	final ListBox ernaehrungListBox = new ListBox();
	ernaehrungListBox.addItem("vegetarisch");
	ernaehrungListBox.addItem("vegan");
	ernaehrungListBox.addItem("keine Angabe");
	editInfoFlexTable.setWidget(1, 2, ernaehrungListBox);
	
	final ListBox musikListBox = new ListBox();
	musikListBox.addItem("Pop");
	musikListBox.addItem("RnB");
	editInfoFlexTable.setWidget(2, 2, musikListBox);
	
	final Label infoLabel2 = new Label();

	ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(
			profilId, new AsyncCallback<Info>() {

				@Override
				public void onFailure(Throwable caught) {
					infoLabel2.setText("Es trat ein Fehler auf.");
				}

				@Override
				public void onSuccess(Info result) {

					musikListBox.setItemText(0, result.getMusik());

					ernaehrungListBox.setItemText(0, result.getErnaehrung());

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

	speichernButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			ClientsideSettings.getPartnerboerseAdministration().saveInfo(
					ernaehrungListBox.getSelectedItemText(),
					musikListBox.getSelectedItemText(),
					new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							informationLabel
									.setText("Es trat ein Fehler auf");
						}

						@Override
						public void onSuccess(Void result) {
							informationLabel
									.setText("Info erfolgreich aktualisiert.");
							ShowInfo showInfo = new ShowInfo();
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showInfo);
						}

					});

		}
	});
}

		
