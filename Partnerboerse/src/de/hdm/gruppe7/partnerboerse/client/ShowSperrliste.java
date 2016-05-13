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
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;

public class ShowSperrliste extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.  
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/** 
	 * Konstruktor hinzufügen. 
	 */
	public ShowSperrliste(){
		this.add(verPanel); 
		
		/**
		 * Überschrift-Label hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Diese Nutzerprofile befinden sich auf Ihrer Sperrliste:");
		
		/**
		 * Information-Label hinzufügen. 
		 */
		final Label infoLabel = new Label(); 
		
		/**
		 * Tabelle zur Anzeige der gesperrten Kontakte hinzufügen. 
		 */
		final FlexTable sperrlisteFlexTable = new FlexTable(); 

		/**
		 * Header-Zeile der Tabelle festlegen. 
		 */
		sperrlisteFlexTable.setText(0, 0, "F-ID");
		sperrlisteFlexTable.setText(0, 1, "Vorname");
		sperrlisteFlexTable.setText(0, 2, "Nachname");
		sperrlisteFlexTable.setText(0, 3, "Geburtstag");
		sperrlisteFlexTable.setText(0, 4, "Geschlecht");
		sperrlisteFlexTable.setText(0, 5, "Löschen");
		sperrlisteFlexTable.setText(0, 6, "Anzeigen");
		
		/** 
		 * Tabelle formatieren und CSS einbinden. 
		 */
		sperrlisteFlexTable.setCellPadding(6);
		sperrlisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		sperrlisteFlexTable.addStyleName("FlexTable");   
		
		/**
		 * Gesperrte Nutzerprofile anzeigen. 
		 */
		ClientsideSettings.getPartnerboerseAdministration().
		getGesperrteNutzerprofileFor(Benutzer.getProfilId(), new AsyncCallback<Vector<Sperrliste>>() {

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");
			}

			@Override
			// Vektor der gesperrten Profile abarbeiten. 
			public void onSuccess(Vector<Sperrliste> result) {
				
				// Anzahl der Zeilen ermitteln. 
				int row = sperrlisteFlexTable.getRowCount();
				
				// Tabelle mit Inhalten aus der Datenbank befüllen. 
				for(Sperrliste s : result) {
					row++;
					
					final String fremdprofilId = String.valueOf(s.getsFremdprofilId());
					sperrlisteFlexTable.setText(row, 0, fremdprofilId); 
					sperrlisteFlexTable.setText(row, 1, s.getsVorname()); 
					sperrlisteFlexTable.setText(row, 2, s.getsNachname());
					sperrlisteFlexTable.setText(row, 3, s.getsGeburtsdatum());
					sperrlisteFlexTable.setText(row, 4, s.getsGeschlecht()); 
					
					// Löschen-Button hinzufügen und ausbauen. 
					final Button loeschenButton = new Button("Löschen");
					sperrlisteFlexTable.setWidget(row, 5, loeschenButton); 
					
					// Anzeigen-Button hinzufügen und ausbauen. 
					final Button anzeigenButton = new Button("Anzeigen");
					sperrlisteFlexTable.setWidget(row, 6, anzeigenButton); 
					
					// Testzwecke: Index der FlexTable-Rows anzeigen. 
					sperrlisteFlexTable.setText(row, 7, String.valueOf(row)); 
					
					// ClickHandler für den Löschen-Button hinzufügen. 
					loeschenButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							
							/**
							 * Tabelle nach Fremdprofil-ID durchsuchen; Index = Die Zeile, die gelöscht werden soll. 
							 * Achtung: Die Tabelle darf erst ab Zeile 2 verwendet werden (Zeile 1 = Header-Zeile).
							 */
							for(int i=2; i<=sperrlisteFlexTable.getRowCount(); i++) {
					
									String fremdprofilIdFlexTable = sperrlisteFlexTable.getText(i, 0);
									
									if (Integer.valueOf(fremdprofilIdFlexTable) == Integer.valueOf(fremdprofilId)) {
										// Inhalte aus der Datenbank entfernen. 
										ClientsideSettings.getPartnerboerseAdministration().
										sperrungLoeschen(Benutzer.getProfilId(), Integer.valueOf(fremdprofilId), new AsyncCallback<Void>(){
			
											@Override
											public void onFailure(Throwable caught) {
												infoLabel.setText("Es trat ein Fehler auf.");
											}
			
											@Override
											public void onSuccess(Void result) {
												infoLabel.setText("Das Nutzerprofil wurde erfolgreich von Ihrer Sperrliste entfernt.");
											}
											
										});
										
										// Zeile in Tabelle löschen.
										sperrlisteFlexTable.removeRow(i);
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
		verPanel.add(sperrlisteFlexTable);  
		verPanel.add(infoLabel);
		
	}
		
}
