package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class CreateInfo extends VerticalPanel {

	/**
	 * GUI für Beschreibungsinfo
	 */

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

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
		showEigenschaftFlexTable.setText(0, 2, "Infotext");

		showEigenschaftFlexTable.setText(0, 3, "Speichern");
//		showEigenschaftFlexTable.setText(0, 4, "Löschen");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");

		/**
		 * informationLabel für die Benutzerinformation erzeugen.
		 */
		final Label informationLabel = new Label();

		/**
		 * informationLabel zum navPanel hinzufügen.
		 */
		verPanel.add(informationLabel);

		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaftenB(new AsyncCallback<List<Eigenschaft>>() {

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
					speichernInfoButton.setStylePrimaryName("partnerboerse-menubutton");
					showEigenschaftFlexTable.setWidget(row, 3, speichernInfoButton);

					speichernInfoButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
						
							final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);

							ClientsideSettings.getPartnerboerseAdministration().createBeschreibungsinfo(Benutzer.getProfilId(),
									eigenschaftIdInt, textArea.getText(), new AsyncCallback<Info>() {

										@Override
										public void onFailure(Throwable caught) {
											informationLabel.setText("Es trat ein Fehler auf");
										}

										@Override
										public void onSuccess(Info result) {
											informationLabel.setText("Die Info wurde erfolgreich angelegt");
										}

									});
						}
					});

//					final Button loeschenInfoButton = new Button("Information löschen");
//					loeschenInfoButton.setStylePrimaryName("partnerboerse-menubutton");
//					showEigenschaftFlexTable.setWidget(row, 4, loeschenInfoButton);
				}

			}
		});

		verPanel.add(showEigenschaftFlexTable);
		verPanel.add(informationLabel);
	
	
	
	/**
	 * GUI für Auswahlinfo
	 */
	
	/**
	 * Tabelle zur Anzeige der Eigenschaften hinzufügen.
	 */
	final FlexTable showEigenschaftFlexTableAuswahl = new FlexTable();

	/**
	 * Erste Zeile der Tabelle festlegen.
	 */
	showEigenschaftFlexTableAuswahl.setText(0, 0, "Eigenschaft-Id");
	showEigenschaftFlexTableAuswahl.setText(0, 1, "Erlaeuterung");
	showEigenschaftFlexTableAuswahl.setText(0, 2, "Auswahloptionen");

	showEigenschaftFlexTableAuswahl.setText(0, 3, "Speichern");
//	showEigenschaftFlexTableAuswahl.setText(0, 4, "Löschen");

	/**
	 * Tabelle formatieren und CSS einbinden.
	 */
	showEigenschaftFlexTableAuswahl.setCellPadding(6);
	showEigenschaftFlexTableAuswahl.getRowFormatter().addStyleName(0, "TableHeader");
	showEigenschaftFlexTableAuswahl.addStyleName("FlexTable");

	/**
	 * informationLabel für die Benutzerinformation erzeugen.
	 */
	final Label informationLabelAuswahl = new Label();

	/**
	 * informationLabel zum navPanel hinzufügen.
	 */
	verPanel.add(informationLabelAuswahl);

	ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaftenA(new AsyncCallback<List<Eigenschaft>>() {

		@Override
		public void onFailure(Throwable caught) {
			informationLabelAuswahl.setText("Es trat ein Fehler auf");
		}

		@Override
		public void onSuccess(List<Eigenschaft> result) {
			// Anzahl der Zeilen ermitteln.
			int row = showEigenschaftFlexTableAuswahl.getRowCount();

			// Tabelle mit Inhalten aus der Datenbank befüllen.
			for (Eigenschaft e : result) {
				row++;

				final String eigenschaftId = String.valueOf(e.getEigenschaftId());

				showEigenschaftFlexTableAuswahl.setText(row, 0, eigenschaftId);

				showEigenschaftFlexTableAuswahl.setText(row, 1, e.getErlaeuterung());
				
				final ListBox neueListBox = new ListBox();

				showEigenschaftFlexTableAuswahl.setWidget(row, 2, neueListBox);
						
						ClientsideSettings.getPartnerboerseAdministration().getAllAuswahloptionen
							(Integer.valueOf(eigenschaftId), new AsyncCallback<List<Auswahloption>>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabelAuswahl.setText("Es trat ein Fehler auf");
								
							}

							@Override
							public void onSuccess(List<Auswahloption> result) {
							
//							int row2 = showEigenschaftFlexTableAuswahl.getRowCount();
							
							
							for(Auswahloption a : result){
//								row2++;
								
								neueListBox.addItem(a.getOptionsbezeichnung());
								
								
							}
							
							
							}
				
						});
						
						
					
				final Button speichernInfoButton = new Button("Information speichern");
				speichernInfoButton.setStylePrimaryName("partnerboerse-menubutton");
				showEigenschaftFlexTableAuswahl.setWidget(row, 3, speichernInfoButton);

				speichernInfoButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
					
						final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);

						ClientsideSettings.getPartnerboerseAdministration().createAuswahlinfo(Benutzer.getProfilId(),
								eigenschaftIdInt, neueListBox.getSelectedIndex(), new AsyncCallback<Info>() {

									@Override
									public void onFailure(Throwable caught) {
										informationLabelAuswahl.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Info result) {
										informationLabelAuswahl.setText("Die Info wurde erfolgreich angelegt");
									}

								});
					}
				});

				
				
//				final Button loeschenInfoButton = new Button("Information löschen");
//				loeschenInfoButton.setStylePrimaryName("partnerboerse-menubutton");
//				showEigenschaftFlexTableAuswahl.setWidget(row, 4, loeschenInfoButton);
			}
		}

	});

	verPanel.add(showEigenschaftFlexTableAuswahl);
	verPanel.add(informationLabelAuswahl);
	}
}