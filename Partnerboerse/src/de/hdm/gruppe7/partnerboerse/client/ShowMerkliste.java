package de.hdm.gruppe7.partnerboerse.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;

public class ShowMerkliste extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.  
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/** 
	 * Konstruktor hinzufügen. 
	 */
	public ShowMerkliste(){
		this.add(verPanel); 
		
		/**
		 * Überschrift hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Diese Kontakte haben Sie sich gemerkt:"); 
		
		/**
		 * Tabelle zur Anzeige der gemerkten Kontakte hinzufügen. 
		 */
		final FlexTable merklisteFlexTable = new FlexTable(); 

		/**
		 * Erste Zeile der Tabelle festlegen. 
		 */
		merklisteFlexTable.setText(0, 0, "F-ID");
		merklisteFlexTable.setText(0, 1, "Vorname");
		merklisteFlexTable.setText(0, 2, "Nachname");
		merklisteFlexTable.setText(0, 3, "Geburtstag");
		merklisteFlexTable.setText(0, 4, "Geschlecht");
		merklisteFlexTable.setText(0, 5, "Löschen");
		merklisteFlexTable.setText(0, 6, "Anzeigen");
		  
		/** 
		 * Tabelle formatieren und CSS einbinden. 
		 */
		merklisteFlexTable.setCellPadding(6);
		merklisteFlexTable.getRowFormatter().addStyleName(0, "merklisteHeader");
		merklisteFlexTable.addStyleName("merklisteFlexTable");   
		
		// Testzwecke
		final Label infoLabel = new Label(); 
		final Label infoLabel1 = new Label();
		final Label infoLabel2 = new Label();
		final Label infoLabel3 = new Label();
		final Label infoLabel4 = new Label();
		
			/**
			 * Gemerkte Nutzerprofile eines bestimmten Nutzerprofils anzeigen. 
			 */
			ClientsideSettings.getPartnerboerseAdministration().
			getGemerkteNutzerprofileFor(Benutzer.getProfilId(), new AsyncCallback<Vector<Merkliste>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");
			}

			@Override
			public void onSuccess(Vector<Merkliste> result) {

				// Anzahl der Zeilen ermitteln. 
				int row = merklisteFlexTable.getRowCount();
				
				// Tabelle mit Inhalten aus der Datenbank befüllen. 
				for(Merkliste m : result){
					row++;
					
					final String fremdprofilId = String.valueOf(m.getmFremdprofilId());
					
					merklisteFlexTable.setText(row, 0, fremdprofilId); 
					merklisteFlexTable.setText(row, 1, m.getmVorname()); 
					merklisteFlexTable.setText(row, 2, m.getmNachname());
					merklisteFlexTable.setText(row, 3, m.getmGeburtsdatum());
					merklisteFlexTable.setText(row, 4, m.getmGeschlecht()); 
					
					// Löschen-Button hinzufügen und ausbauen. 
					final Button loeschenButton = new Button("Löschen");
					merklisteFlexTable.setWidget(row, 5, loeschenButton); 
					
					// Anzeigen-Button hinzufügen und ausbauen. 
					final Button anzeigenButton = new Button("Anzeigen");
					merklisteFlexTable.setWidget(row, 6, anzeigenButton); 
					
					merklisteFlexTable.setText(row, 7, String.valueOf(row)); 
					
					// ClickHandler für den Löschen-Button hinzufügen. 
					loeschenButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							
							/**
							 * Flextable nach FremdprofilID durchsuchen --> Index = Zeile die gelöscht werden soll
							 * 
							 * Achtung: Die Flextable darf erst ab 2 benutzt werden (Zeile 1 = Headerzeilen)
							 */
							for(int z=2; z<=merklisteFlexTable.getRowCount(); z++){
//									System.out.println("Werte aus for:"+ anzRecords + " " +z);
									infoLabel1.setText("Werte aus for: Anzahl = "+ merklisteFlexTable.getRowCount() + " Index =  " +z + " FPID =  " + fremdprofilId);
					
									String fpIDflextable = "";
									fpIDflextable = merklisteFlexTable.getText(z, 0);
									
									infoLabel3.setText("Werte für fpIDflextable = "  + fpIDflextable + " fremdprofilId = " + fremdprofilId );
									
									/*
									 * 
									 */
									if (Integer.valueOf(fpIDflextable) == Integer.valueOf(fremdprofilId)){
										// TODO : ZEILE aus DB löschen
										
										// Inhalte aus der Datenbank entfernen. 
										ClientsideSettings.getPartnerboerseAdministration().
										vermerkLoeschen(Benutzer.getProfilId(), Integer.valueOf(fremdprofilId), new AsyncCallback<Void>(){
			
											@Override
											public void onFailure(Throwable caught) {
												infoLabel.setText("ShowMerkzettel: Fehler bei Löschen DS");
												infoLabel1.setText("ShowMerkzettel: Benutzer = " + Benutzer.getProfilId());
												infoLabel2.setText(caught.toString());
												
											}
			
											@Override
											public void onSuccess(Void result) {
												infoLabel4.setText("DS erfolgreich gelöscht: Benutzer= " + Benutzer.getProfilId() + " FPID= " + Integer.valueOf(fremdprofilId));
												
											}
											
										});
										
										// Zeile in Flextabelle löschen
										merklisteFlexTable.removeRow(z);
										infoLabel.setText("Werte aus array: Fremfprofil = " + fremdprofilId + " Löschzeile aus Flextabe = " 
										+ merklisteFlexTable.getText(z, 0));
										break;
									}
								}			         
							
						}
						
					});
					
					// ClickHandler für den Anzeigen-Button hinzufügen. 
					anzeigenButton.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event) {
							ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId)); 
							RootPanel.get("Details").clear(); 
							RootPanel.get("Details").add(showFremdprofil); 
							
						}
						
					}); 
					
				}
			}
				
		});
		
		// Widgets zum VerticalPanel hinzufügen. 
		verPanel.add(ueberschriftLabel); 
		verPanel.add(merklisteFlexTable); 
		verPanel.add(infoLabel);
		verPanel.add(infoLabel1);
		verPanel.add(infoLabel2);
		verPanel.add(infoLabel3);
		verPanel.add(infoLabel4);
		
	}	

}
