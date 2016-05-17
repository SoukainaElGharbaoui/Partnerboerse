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
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;


public class EditInfo extends VerticalPanel {

	/**
	 * VerticalPanels hinzufügen.
	 */
	
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen.
	 */

	public EditInfo() {
		this.add(verPanel);

		/**
		 * Tabelle zur Anzeige der Eigenschaften hinzufügen.
		 */
		
		final FlexTable editInfoFlexTable = new FlexTable();
		
		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		
		editInfoFlexTable.setCellPadding(6);
		editInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		editInfoFlexTable.addStyleName("FlexTable");

//		final ListBox ernaehrungListBox = new ListBox();
//		ernaehrungListBox.addItem("vegetarisch");
//		ernaehrungListBox.addItem("vegan");
//		ernaehrungListBox.addItem("keine Angabe");
//		editInfoFlexTable.setWidget(1, 2, ernaehrungListBox);
//
//		final ListBox musikListBox = new ListBox();
//		musikListBox.addItem("Pop");
//		musikListBox.addItem("RnB");
//		editInfoFlexTable.setWidget(2, 2, musikListBox);
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		
//		editInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
//		editInfoFlexTable.setText(1, 0, "Eigenschaft");
//		editInfoFlexTable.setText(2, 0, "Infotext");
		
		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		editInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		editInfoFlexTable.setText(0, 1, "Eigenschaft");
		editInfoFlexTable.setText(0, 2, "Infotext");
		editInfoFlexTable.setText(0, 3, "Auswahloption");
		editInfoFlexTable.setText(0, 4, "Löschen");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		final Label infoLabelB = new Label();
		final Label infoLabelA = new Label();
		final Label editLabel = new Label();
		final Label ueberschriftLabel = new Label("Eigene Info bearbeiten");
		
		/**
		 * informationLabel zum navPanel hinzufügen.
		 */
		verPanel.add(infoLabelB);
		verPanel.add(infoLabelA);
		verPanel.add(editLabel);
		verPanel.add(ueberschriftLabel);

		
		/**
		 * GUI für Beschreibungsinfo
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(
				Benutzer.getProfilId(), new AsyncCallback<List<Info>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabelB.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Info> result) {
						// Anzahl der Zeilen ermitteln.
						int row = editInfoFlexTable.getRowCount();

						// Tabelle mit Inhalten aus der Datenbank befüllen.
						for (Info iB : result) {
							row++;

							final String nutzerprofilId = String.valueOf(iB.getNutzerprofilId());

							final TextArea textArea = new TextArea();
							editInfoFlexTable.setWidget(row, 2, textArea);

							editInfoFlexTable.setText(row, 0, nutzerprofilId);
							editInfoFlexTable.setText(row, 1, iB.getEigenschaftErlaeuterung());
							editInfoFlexTable.setText(row, 2, iB.getInfotext());
							
							final Button loeschenButton = new Button("Löschen");
							editInfoFlexTable.setWidget(row, 4, loeschenButton);
							
							loeschenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

//									final int eigenschaftIdInt = Integer.valueOf(iB.getEigenschaftId());
									
									ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoB(Benutzer.getProfilId(), 
											Eigenschaft.getEigenschaftId(), new AsyncCallback<Void>() {

												@Override
												public void onFailure(Throwable caught) {
													infoLabelB.setText("Es trat ein Fehler auf");
												}

												@Override
												public void onSuccess(Void result) {
													infoLabelB.setText("Die Beschreibungsinfo wurde erfolgreich gelöscht");
												}

											});
								}
							});

						}
					}
				});

	verPanel.add(editInfoFlexTable);	
	verPanel.add(infoLabelB);	
	
	
	
	/**
	 * GUI für Auswahlinfo
	 */
	ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(
			Benutzer.getProfilId(), new AsyncCallback<List<Info>>() {

				@Override
				public void onFailure(Throwable caught) {
					infoLabelA.setText("Es trat ein Fehler auf.");
				}

				@Override
				public void onSuccess(List<Info> result) {

					int row = editInfoFlexTable.getRowCount();

					// Tabelle mit Inhalten aus der Datenbank befüllen.
					for (Info iA : result) {
						row++;
						
						final String nutzerprofilId = String.valueOf(iA.getNutzerprofilId());

						editInfoFlexTable.setText(row, 0, nutzerprofilId);
						editInfoFlexTable.setText(row, 1, iA.getEigenschaftErlaeuterung());
						editInfoFlexTable.setText(row, 2, iA.getOptionsbezeichnung());
						
						final Button loeschenButton = new Button("Löschen");
						loeschenButton.setStylePrimaryName("partnerboerse-menubutton");
						editInfoFlexTable.setWidget(row, 4, loeschenButton);
						
						
						loeschenButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
							
//							final int eigenschaftIdInt = Integer.valueOf(iA.getEigenschaftId());
							final String eigenschaftId = String.valueOf(iA.getEigenschaftId());
								
							for(int i=2; i<=editInfoFlexTable.getRowCount(); i++) {
							String eigenschaftIdFlexTable = editInfoFlexTable.getText(i, 0);
							if (Integer.valueOf(eigenschaftIdFlexTable) == Integer.valueOf(eigenschaftId)) {
								
								
								ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoA(Benutzer.getProfilId(),
										Integer.valueOf(eigenschaftId), new AsyncCallback<Void>() {

											@Override
											public void onFailure(Throwable caught) {
												infoLabelA.setText("Es trat ein Fehler auf");
											}

											@Override
											public void onSuccess(Void result) {
												infoLabelA.setText("Die Auswahlinfo wurde erfolgreich gelöscht");
											}

										});
								// Zeile in Tabelle löschen. 
								editInfoFlexTable.removeRow(i);
								break;
							}}
							}
						});
					

					}
						
				}
			});	
	
	verPanel.add(editInfoFlexTable);
	verPanel.add(infoLabelA);
		
	/**
	 * Finalen Löschen & Speichern Button hinzufügen	
	 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * ï¿½nderungen Speichern-Button hinzufÃ¼gen und ausbauen.
		 */
		final Button speichernButton = new Button("&Auml;nderungen speichern");
		verPanel.add(buttonPanel);
		buttonPanel.add(speichernButton);

		/**
		 * ï¿½nderungen Löschen-Button hinzufÃ¼gen und ausbauen.
		 */
		final Button loeschenButton = new Button("&Auml;nderungen loeschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);
		
		/**
		 * ClickHandler fï¿½r den Speichern-Button hinzufï¿½gen.
		 */
		final Label informationLabel = new Label();
		verPanel.add(informationLabel);

		// speichernButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// ClientsideSettings.getPartnerboerseAdministration().saveInfo(
		// ernaehrungListBox.getSelectedItemText(),
		// musikListBox.getSelectedItemText(),
		// new AsyncCallback<Void>() {
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
		// }});
		
		
		
		
		////////////////////////////////////////////////////////////////////
		
		
		
		

		// ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(
		// Benutzer.getProfilId(), new AsyncCallback <List<Info>>() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// infoLabel2.setText("Es trat ein Fehler auf.");
		// }
		//
		// @Override
		// public void onSuccess(List<Info> result) {
		//
		// musikListBox.setItemText(0, result.getMusik());
		//
		// ernaehrungListBox.setItemText(0, result.getErnaehrung());
		//
		// }
		// });

		/**
		 * Zum Panel hinzufï¿½gen
		 */

//		verPanel.add(ueberschriftLabel);
//		verPanel.add(editInfoFlexTable);
		// verPanel.add(infoLabel);
//		verPanel.add(infoLabelA);
//		verPanel.add(editLabel);



		

//		loeschenButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//
//				ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoB(Benutzer.getProfilId(), 
//						 eigenschaftId, new AsyncCallback<Void>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								informationLabel.setText("Es trat ein Fehler auf");
//							}
//
//							@Override
//							public void onSuccess(Void result) {
//								informationLabel.setText("Die Beschreibungsinfo wurde erfolgreich gelöscht");
//							}
//
//						});
//				
//				ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoA(Benutzer.getProfilId(),
//						eigenschaftId, new AsyncCallback<Void>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								informationLabel.setText("Es trat ein Fehler auf");
//							}
//
//							@Override
//							public void onSuccess(Void result) {
//								informationLabel.setText("Die Auswahlinfo wurde erfolgreich gelöscht");
//							}
//
//						});
//			}
//		});



		
//		verPanel.add(ueberschriftLabel);
//		verPanel.add(editInfoFlexTable);

		
		// Tabelle für Auswahlinfos
		

//		final Label editLabel = new Label();

////		final ListBox ernaehrungListBox = new ListBox();
//		ernaehrungListBox.addItem("vegetarisch");
//		ernaehrungListBox.addItem("vegan");
//		ernaehrungListBox.addItem("keine Angabe");
//		editInfoFlexTable.setWidget(1, 3, ernaehrungListBox);
//
////		final ListBox musikListBox = new ListBox();
//		musikListBox.addItem("Pop");
//		musikListBox.addItem("RnB");
//		editInfoFlexTable.setWidget(2, 3, musikListBox);

//		final Label infoLabel2 = new Label();

		

		/**
		 * Zum Panel hinzufï¿½gen
		 */

//		verPanel.add(ueberschriftLabel);
//		verPanel.add(editInfoFlexTable);
//		// verPanel.add(infoLabel);
//		verPanel.add(infoLabelA);
//		verPanel.add(editLabel);

		/**
		 * ï¿½nderungen Speichern-Button hinzufÃ¼gen und ausbauen.
		 */
//		final Button speichernButton2 = new Button("&Auml;nderungen speichern");
//		verPanel.add(buttonPanel);
//		buttonPanel.add(speichernButton2);
//
//		/**
//		 * ClickHandler fï¿½r den Speichern-Button hinzufï¿½gen.
//		 */
////		final Label informationLabel = new Label();
//		verPanel.add(informationLabel);
		
		

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
