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


//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahlinfo;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
//import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungsinfo;

public class ShowInfo extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
//	private String eigenschaftId;
//	private String nutzerprofilId;
//	private String optionsbezeichnung;
//	private int row;
//	private int i;
	
	// Tabelle für Beschreibungsinfo

			/**
			 * Tabelle erzeugen, in der die Beschreibungsinfos dargestellt werden.
			 */
			final FlexTable showInfoFlexTable = new FlexTable();


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
		final Label informationLabel = new Label();
		
		
		ClientsideSettings.getPartnerboerseAdministration().getAllInfosNeu(Benutzer.getProfilId(), 
				new AsyncCallback<List<String>>(){

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
						String eigenschaftId = result.get(i+1);
						String erlaeuterung = result.get(i+2);
						String infotext = result.get(i+3);
						String typ = result.get(i+4);
									
						showInfoFlexTable.setText(row1, 0, nutzerprofilId);
						showInfoFlexTable.setText(row1, 1, eigenschaftId);
						showInfoFlexTable.setText(row1, 2, erlaeuterung);
						showInfoFlexTable.setText(row1, 3, infotext);
						
						row1++;
						i++; i++; i++; i++;
						}
					}
		});
		

//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(Benutzer.getProfilId(),
//				new AsyncCallback<List<Beschreibungsinfo>>() {
//		
//					@Override
//					public void onFailure(Throwable caught) {
//						informationLabel.setText("Es trat ein Fehler auf.");
//					}
//
//					@Override
//					public void onSuccess(List<Beschreibungsinfo> result) {
//						
//						int row = showInfoFlexTable.getRowCount();
//
//						for (Beschreibungsinfo i : result) {
//							row++;
//
//							nutzerprofilId = String.valueOf(i.getNutzerprofilId());
//							eigenschaftIdB = String.valueOf(i.getEigenschaftId());
//
//							showInfoFlexTable.setText(row, 0, nutzerprofilId);
//							showInfoFlexTable.setText(row, 1, eigenschaftIdB);
//							
//							showInfoFlexTable.setText(row, 2, i.getInfotext());
//						}
//					}
//				});
		
		verPanel.add(showInfoFlexTable);

		
//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosGesamt(Benutzer.getProfilId(), 
//				new AsyncCallback<Vector<String[]>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						informationLabel.setText("HIIIIILFE ! Es ist ein Fehler aufgetreten.");					
//					}
//
//					@Override
//					public void onSuccess(Vector<String[]> result) {
//					
//					int row1 = showInfoFlexTable.getRowCount();
//					int size = result.size();
//						
//					for (int i = 0; i < size; i++) {
//						
//					String[] st = (String[]) result.get(i);
//								
//					showInfoFlexTable.setText(row1, 0, st[0]);
//					showInfoFlexTable.setText(row1, 1, st[1]);
//					showInfoFlexTable.setText(row1, 2, st[2]);
//					
//					informationLabel.setText(st[2]);
//					}
//						 
//					}
//		});

//					@Override
//					public void onFailure(Throwable caught) {
//						informationLabel.setText("Es trat ein Fehler auf.");						
//					}
//
//					@Override
//					public void onSuccess(String[] result) {
//						
//						informationLabel.setText("Es hat funktioniert.");	
//						
//////					row = showInfoFlexTable.getRowCount();
////						
////						for (String iA : result) {
////						i++;
//						
//						showInfoFlexTable.setText(i, 0, result[0]);
//						showInfoFlexTable.setText(i, 1, result[1]);
//						showInfoFlexTable.setText(i, 2, result[2]);
//						}
//					});	
//		});
//						
//		}
		

//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(Benutzer.getProfilId(),
//				new AsyncCallback<List<Auswahlinfo>>() {
//			
//					@Override
//					public void onFailure(Throwable caught) {
//						informationLabel.setText("Es trat ein Fehler auf.");
//					}
//
//					@Override
//					public void onSuccess(List<Auswahlinfo> result) {
//
//						row = showInfoFlexTable.getRowCount();
//
//						for (Auswahlinfo iA : result) {
//							row++;
//
//							nutzerprofilId = String.valueOf(iA.getNutzerprofilId());
//							eigenschaftIdA = String.valueOf(iA.getEigenschaftId());
//
//							showInfoFlexTable.setText(row, 0, nutzerprofilId);
//							showInfoFlexTable.setText(row, 1, eigenschaftIdA);
//														
//						}
//					}
//		});
//		
//		verPanel.add(showInfoFlexTable);
//		verPanel.add(informationLabel);
//
//									
//		
//						row = showInfoFlexTable.getRowCount();
//						
//						for(int i = 0; i < row; i++) {
//							
//							if(!showInfoFlexTable.getText(i, 2).equals(null)){
//								
//							
//							ClientsideSettings.getPartnerboerseAdministration().getOptionById(
//									Integer.valueOf(showInfoFlexTable.getText(i, 1)), Integer.valueOf(
//											showInfoFlexTable.getText(i, 0)), 
//									new AsyncCallback<Auswahloption>() {
//							
//							
//							ClientsideSettings.getPartnerboerseAdministration().getOptionById(
//									Integer.valueOf(eigenschaftIdA), Integer.valueOf(
//											nutzerprofilId), 
//									new AsyncCallback<Auswahloption>() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//											informationLabel.setText("Es trat ein Fehler auf.");						
//										}
//
//										@Override
//										public void onSuccess(Auswahloption result) {
//											
//											informationLabel.setText("Das Herausholen der Optionsbezeichnung "
//													+ "hat funktioniert.");	
//											
//											optionsbezeichnung = result.getOptionsbezeichnung();
//											showInfoFlexTable.setText(row, 2, optionsbezeichnung);
//										}
//							});
//							}
//						}
//						}
//					}
//		});
							
		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);

		final Button loeschenButton = new Button("Alle Infos löschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);

		final Button bearbeitenButton = new Button("Bearbeiten");
		verPanel.add(buttonPanel);
		buttonPanel.add(bearbeitenButton);

		
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditInfo editInfo = new EditInfo();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editInfo);
			}
		});

		
//		loeschenButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//
//				ClientsideSettings.getPartnerboerseAdministration().deleteAllInfos(Benutzer.getProfilId(),
//						new AsyncCallback<Void>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								informationLabel.setText("Es trat ein Fehler auf");
//							}
//
//							@Override
//							public void onSuccess(Void result) {
//								informationLabel.setText("Die gesamte Info wurde erfolgreich gelöscht");
//							}
//
//						});
//
//			}
//		});
	}
}
