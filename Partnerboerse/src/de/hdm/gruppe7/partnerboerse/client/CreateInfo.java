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




import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahlinfo;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungsinfo;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class CreateInfo extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen. 
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
		showEigenschaftFlexTable.setText(0, 2, "Anlegen"); 

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");

		/**
		 * Überschrift-Label hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Info anlegen:"); 
		ueberschriftLabel.addStyleName("partnerboerse-label"); 
		
		/**
		 * Information-Labels für die Benutzerinformation hinzufügen.
		 */
		final Label informationLabelB = new Label();
		final Label informationLabelA = new Label();
		
		/**
		 * Info-Anlegen-Button hinzufügen. 
		 */
		final Button createInfosButton = new Button("Info anlegen");
		
		
		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaftenNeu(
				new AsyncCallback<List<Eigenschaft>>(){

					@Override
					public void onFailure(Throwable caught) {
						informationLabelB.setText("Es trat ein Fehler auf");
					}

					@Override
					public void onSuccess(List<Eigenschaft> result) {
						int row = showEigenschaftFlexTable.getRowCount();
		
						for (Eigenschaft e : result) {
							row++;
		
							final String eigenschaftId = String.valueOf(e.getEigenschaftId());
							
							showEigenschaftFlexTable.setText(row, 0, eigenschaftId);
							showEigenschaftFlexTable.setText(row, 1, e.getErlaeuterung());
							
							if (e.getTyp() == "B") {
								
								final TextArea textArea = new TextArea();
								showEigenschaftFlexTable.setWidget(row, 2, textArea);
							}
							
							else if (e.getTyp() == "A") {
								
								final ListBox lb = new ListBox();
								
								ClientsideSettings.getPartnerboerseAdministration().getEigAById(
										Integer.valueOf(eigenschaftId), new AsyncCallback<Auswahleigenschaft>() {

											@Override
											public void onFailure(
													Throwable caught) {
												informationLabelA.setText("Beim Herausholen der Auswahloptionen "
														+ "ist ein Fehler aufgetreten.");												
											}

											@Override
											public void onSuccess(
													Auswahleigenschaft result) {
												
												List<String> optionen = result.getOptionen();
												
												for(int i = 0; i < optionen.size(); i++) {
													lb.addItem(optionen.get(i));
												}
											}	
								});
								showEigenschaftFlexTable.setWidget(row, 2, lb);
							}
							
							
							
							
							
						}
					}
		});
		
		
		
//		/**
//		 * GUI für die Beschreibungseigenschaften definieren.
//		 */
//		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaftenB(new AsyncCallback<List<Eigenschaft>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				informationLabelB.setText("Es trat ein Fehler auf");
//			}
//
//			@Override
//			public void onSuccess(List<Eigenschaft> result) {
//				
//				// Anzahl der Zeilen ermitteln.
//				int row = showEigenschaftFlexTable.getRowCount();
//
//				// Tabelle mit Inhalten aus der Datenbank befüllen.
//				for (Eigenschaft e : result) {
//					row++;
//
//					final String eigenschaftId = String.valueOf(e.getEigenschaftId());
//					
//					showEigenschaftFlexTable.setText(row, 0, eigenschaftId);
//					showEigenschaftFlexTable.setText(row, 1, e.getErlaeuterung());
//
//					final TextArea textArea = new TextArea();
//					
//					showEigenschaftFlexTable.setWidget(row, 2, textArea);
//					
//					
//					
//					// ClickHandler für den Info-Anlegen-Button hinzufügen. 
//					createInfosButton.addClickHandler(new ClickHandler() {
//
//						public void onClick(ClickEvent event) {
//							
//							final int eigenschaftIdInt = Integer.valueOf(eigenschaftId); 
//							
//							
//							
////							ClientsideSettings.getPartnerboerseAdministration().createBeschreibungsinfo(Benutzer.getProfilId(),
////							eigenschaftIdInt, textArea.getText(), new AsyncCallback<Beschreibungsinfo>() {
////
////								@Override
////								public void onFailure(Throwable caught) {
////									informationLabelB.setText("Es trat ein Fehler auf");
////								}
////
////								@Override
////								public void onSuccess(Beschreibungsinfo result) {
////									informationLabelB.setText("Die Beschreibungsinfo wurde erfolgreich angelegt");
////								}
////
////							});
////				
////						}
////						
////					}); 
////
////				}
////
////			}
////		});
//	
//	/**
//	 * GUI für die Auswahleigenschaften hinzufügen. 
//	 */
////	ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaftenA(new AsyncCallback<List<Eigenschaft>>() {
////
////		@Override
////		public void onFailure(Throwable caught) {
////			informationLabelA.setText("Es trat ein Fehler auf");
////		}
////
////		@Override
////		public void onSuccess(List<Eigenschaft> result) {
////			// Anzahl der Zeilen ermitteln.
////			int row = showEigenschaftFlexTable.getRowCount();
////
////			// Tabelle mit Inhalten aus der Datenbank befüllen.
////			for (Eigenschaft e : result) {
////				row++;
////
////				final String eigenschaftId = String.valueOf(e.getEigenschaftId());
////
////				showEigenschaftFlexTable.setText(row, 0, eigenschaftId);
////				showEigenschaftFlexTable.setText(row, 1, e.getErlaeuterung());
////				
////				final ListBox neueListBox = new ListBox();
////
////				showEigenschaftFlexTable.setWidget(row, 2, neueListBox);
////						
////						// Auswahloptionen abrufen. 
////						ClientsideSettings.getPartnerboerseAdministration().getAllAuswahloptionen
////							(Integer.valueOf(eigenschaftId), new AsyncCallback<List<Auswahloption>>() {
////
////							@Override
////							public void onFailure(Throwable caught) {
////								informationLabelA.setText("Es trat ein Fehler auf");	
////							}
////
////							@Override
////							public void onSuccess(List<Auswahloption> result) {
////							
////							for(Auswahloption a : result){
////								neueListBox.addItem(a.getOptionsbezeichnung());
////							}
////							}
////				
////						});
//						
////						// ClickHandler für den Info-Anlegen-Button hinzufügen. 
////						createInfosButton.addClickHandler(new ClickHandler() {
////
////							public void onClick(ClickEvent event) {
////								final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);
////								
////									ClientsideSettings.getPartnerboerseAdministration().createAuswahlinfo(Benutzer.getProfilId(), 
////											eigenschaftIdInt, neueListBox.getSelectedIndex(), new AsyncCallback<Auswahlinfo>() {
////								
////									@Override
////									public void onFailure(Throwable caught) {
////									informationLabelA.setText("Es trat ein Fehler auf");
////									}
////								
////									@Override
////									public void onSuccess(Auswahlinfo result) {
////									informationLabelA.setText("Die Auswahlinfo wurde erfolgreich angelegt");
////									}
////								
////									});
////								
////							}
////							
////						}); 
////						
////			}
////		}
////
////	});
//	
	verPanel.add(ueberschriftLabel);  
	verPanel.add(showEigenschaftFlexTable);
	verPanel.add(createInfosButton);
	verPanel.add(informationLabelB);
	verPanel.add(informationLabelA);
	}
}