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

public class ShowInfo extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	private Label ueberschriftLabel = new Label("Ihre Infos:");
	private Label informationLabel = new Label();
	
	private FlexTable showInfoFlexTable = new FlexTable();

	/**
	 * Konstruktor
	 */
	public ShowInfo() {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */
		
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Label Button
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		showInfoFlexTable.setText(0, 2, "Eigenschaft");
		showInfoFlexTable.setText(0, 3, "Infotext");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosNeu(
				Benutzer.getProfilId(), new AsyncCallback<List<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Fehler");
					}

					@Override
					public void onSuccess(List<String> result) {
						informationLabel.setText("Es hat funktioniert! YEAH!");

						int row1 = showInfoFlexTable.getRowCount();
						int size = result.size();

						for (int i = 0; i < size; i++) {

							String nutzerprofilId = result.get(i);
							String eigenschaftId = result.get(i + 1);
							String erlaeuterung = result.get(i + 2);
							String infotext = result.get(i + 3);
//							String typ = result.get(i + 4);

							showInfoFlexTable.setText(row1, 0, nutzerprofilId);
							showInfoFlexTable.setText(row1, 1, eigenschaftId);
							showInfoFlexTable.setText(row1, 2, erlaeuterung);
							showInfoFlexTable.setText(row1, 3, infotext);

							row1++;
							i++;
							i++;
							i++;
							i++;
						}
					}
				});


		verPanel.add(showInfoFlexTable);
		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);

		final Button loeschenButton = new Button("Alle Infos löschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);

		final Button bearbeitenButton = new Button("Bearbeiten");
		verPanel.add(buttonPanel);
		buttonPanel.add(bearbeitenButton);

		
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				ClientsideSettings.getPartnerboerseAdministration().deleteAllInfosNeu(
						Benutzer.getProfilId(), new AsyncCallback<Void>(){

							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Beim Löschen aller Infos ist ein Fehler aufgetreten.");
							}

							@Override
							public void onSuccess(Void result) {
								informationLabel.setText("Das Löschen aller Infos hat funktioniert.");
								
								ShowEigenesNp showNp = new ShowEigenesNp();
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showNp);
							}
						});
			}
		});
		
		
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditInfo editInfo = new EditInfo();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editInfo);
			}
		});

	}
}
