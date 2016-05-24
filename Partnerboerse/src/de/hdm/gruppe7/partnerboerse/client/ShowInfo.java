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

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahlinfo;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungsinfo;

public class ShowInfo extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	private String eigenschaftIdA;
	private String eigenschaftIdB;
	private String nutzerprofilId;
	private String optionsbezeichnung;
	private int row;
	private int i;
	
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
				new AsyncCallback<List<Beschreibungsinfo>>() {
		
					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Beschreibungsinfo> result) {
						
						row = showInfoFlexTable.getRowCount();

						for (Beschreibungsinfo i : result) {
							row++;

							nutzerprofilId = String.valueOf(i.getNutzerprofilId());
							eigenschaftIdB = String.valueOf(i.getEigenschaftId());

							showInfoFlexTable.setText(row, 0, nutzerprofilId);
							showInfoFlexTable.setText(row, 1, eigenschaftIdB);
							
							showInfoFlexTable.setText(row, 2, i.getInfotext());
						}
					}
				});
		
		verPanel.add(showInfoFlexTable);

		

		row = showInfoFlexTable.getRowCount();
		
		for (i = 0; i < row; i++) {

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosGesamt(Benutzer.getProfilId(), 
				new AsyncCallback<String[]>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf.");						
					}

					@Override
					public void onSuccess(String[] result) {
						
						informationLabel.setText("Es hat funktioniert.");	
						
////					row = showInfoFlexTable.getRowCount();
//						
//						for (String iA : result) {
//						i++;
						
						showInfoFlexTable.setText(i, 0, result[0]);
						showInfoFlexTable.setText(i, 1, result[1]);
						showInfoFlexTable.setText(i, 2, result[2]);
						}
					});
				}	
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
