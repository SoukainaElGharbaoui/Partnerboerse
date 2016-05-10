package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateInfo extends VerticalPanel {

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelInfo = new HorizontalPanel();
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateInfo() {
		this.add(verPanel);

		/**
		 * Tabelle zur Anzeige der Eigenschaften hinzufügen.
		 */
		final FlexTable showEigenschaftFlexTable = new FlexTable();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showEigenschaftFlexTable.setText(0, 0, "Eigenschaft-Id");
		showEigenschaftFlexTable.setText(0, 1, "Erlaeuterung");
		showEigenschaftFlexTable.setText(0, 2, "Eingabe");

		showEigenschaftFlexTable.setText(0, 3, "Speichern");
		showEigenschaftFlexTable.setText(0, 4, "Löschen");
		
		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0,
				"TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");

		/**
		 * Erzeugen einer TextBox fuer die Info.
		 */
		final Label infoLabel = new Label("weitere Informationen zu deiner Person:");
		final TextBox infoTextBox = new TextBox();
		verPanel.add(horPanelInfo);
		horPanelInfo.add(infoTextBox);
		horPanelInfo.add(infoLabel);

		/**
		 * informationLabel für die Benutzerinformation erzeugen.
		 */
		final Label informationLabel = new Label();


		/**
		 * informationLabel zum navPanel hinzufügen.
		 */
		verPanel.add(informationLabel);

		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaften(new AsyncCallback<List<Eigenschaft>>() {

			@Override
			public void onFailure(Throwable caught) {
				informationLabel.setText("Es trat ein Fehler auf");
			}

			@Override
			public void onSuccess(List<Eigenschaft> result) {
				// Anzahl der Zeilen ermitteln.
				int row = showEigenschaftFlexTable.getRowCount();

				// Tabelle mit Inhalten aus der Datenbank befüllen.
				for (Eigenschaft e : result) {
					row++;

					final String eigenschaftId = String.valueOf(e.getEigenschaftId());

					showEigenschaftFlexTable.setText(row, 0, eigenschaftId);
					showEigenschaftFlexTable.setText(row, 1, e.getErlaeuterung());
					
					final TextArea textArea = new TextArea();
					showEigenschaftFlexTable.setWidget(row, 2, textArea);
					
					final Button speichernInfoButton = new Button("Information speichern");
//					speichernInfoButton.setStylePrimaryName("partnerboerse-menubutton");
					showEigenschaftFlexTable.setWidget(row, 3, speichernInfoButton);

					final Button loeschenInfoButton = new Button("Information löschen");
//					loeschenInfoButton.setStylePrimaryName("partnerboerse-menubutton");
					showEigenschaftFlexTable.setWidget(row, 4, loeschenInfoButton);

					speichernInfoButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
		
//						ClientsideSettings.getPartnerboerseAdministration().createInfo(textArea.getText(),
//								new AsyncCallback<Info>() {
//		
//									@Override
//									public void onFailure(Throwable caught) {
//										informationLabel.setText("Es trat ein Fehler auf");
//									}
//		
//									@Override
//									public void onSuccess(Info result) {
//										informationLabel.setText("Die Info wurde erfolgreich angelegt");
//									}
//		
//								});
//		
					}
				});
				}

			}
		});


		verPanel.add(showEigenschaftFlexTable);
	}

}