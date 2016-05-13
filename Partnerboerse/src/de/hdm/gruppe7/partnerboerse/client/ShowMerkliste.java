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
		 * Überschrift-Label hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Diese Nutzerprofile befinden sich auf Ihrer Merkliste:"); 
		
		/**
		 * Information-Label hinzufügen. 
		 */
		final Label infoLabel = new Label(); 
		
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
		merklisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		merklisteFlexTable.addStyleName("FlexTable");   
		
			/**
			 * Gemerkte Nutzerprofile anzeigen. 
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
				for(Merkliste m : result) {
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
					
					// Testzwecke: Index der FlexTable-Rows anzeigen. 
					merklisteFlexTable.setText(row, 7, String.valueOf(row)); 
					
					// ClickHandler für den Löschen-Button hinzufügen. 
					loeschenButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							
							/**
							 * Flextable nach FremdprofilID durchsuchen --> Index = Zeile die gelöscht werden soll
							 * Achtung: Die Flextable darf erst ab 2 benutzt werden (Zeile 1 = Headerzeilen)
							 */
							for(int i=2; i<=merklisteFlexTable.getRowCount(); i++) {
					
									String fremdprofilIdFlexTable = "";
									fremdprofilIdFlexTable = merklisteFlexTable.getText(i, 0);
									
									if (Integer.valueOf(fremdprofilIdFlexTable) == Integer.valueOf(fremdprofilId)){
										
										// Inhalte aus der Datenbank entfernen. 
										ClientsideSettings.getPartnerboerseAdministration().
										vermerkLoeschen(Benutzer.getProfilId(), Integer.valueOf(fremdprofilId), new AsyncCallback<Void>(){
			
											@Override
											public void onFailure(Throwable caught) {
												infoLabel.setText("Es trat ein Fehler auf.");
											}
			
											@Override
											public void onSuccess(Void result) {
												infoLabel.setText("Das Nutzerprofil wurde erfolgreich von Ihrer Merkliste entfernt.");
											}
											
										});
										
										// Zeile in Tabelle löschen. 
										merklisteFlexTable.removeRow(i);
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
		
	}	

}
