package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowFremdprofil extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.  
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/** 
	 * Konstruktor hinzufügen. 
	 */
	public ShowFremdprofil(int fremdprofilId) {
		this.add(verPanel); 
		
		/**
		 * Label für die Überschrift hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Fremdprofil"); 
		
		/**
		 * Tabelle zur Anzeige des Fremdprofils erstellen.
		 */
		final FlexTable showFremdprofilFlexTable = new FlexTable();
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showFremdprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showFremdprofilFlexTable.setText(1, 0, "Vorname");
		showFremdprofilFlexTable.setText(2, 0, "Nachname");
		showFremdprofilFlexTable.setText(3, 0, "Geschlecht");
		showFremdprofilFlexTable.setText(4, 0, "Geburtsdatum");
		showFremdprofilFlexTable.setText(5, 0, "Religion");
		showFremdprofilFlexTable.setText(6, 0, "Koerpergroesse");
		showFremdprofilFlexTable.setText(7, 0, "Haarfarbe");
		showFremdprofilFlexTable.setText(8, 0, "Raucher?");
		
		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showFremdprofilFlexTable.setCellPadding(6);
		showFremdprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		showFremdprofilFlexTable.addStyleName("FlexTable");
		
		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		final Label infoLabel = new Label();
		
		
		ClientsideSettings.getPartnerboerseAdministration()
		.getFremdprofilById( fremdprofilId,
				new AsyncCallback<Nutzerprofil>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					
					
					public void onSuccess(Nutzerprofil result) {
						
						
						

						// Nutzerprofil-Id aus der Datenabank holen
						// und in Tabelle eintragen
						final String nutzerprofilid = String.valueOf(result.getProfilId());
						showFremdprofilFlexTable.setText(0, 1, nutzerprofilid);

						// Vorname aus Datenbank aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(1, 1, result.getVorname());

						// Nachname aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(2, 1, result.getNachname());

						// Geschlecht aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(3, 1, result.getGeschlecht());

						// Geburtsdatum aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(4, 1, result.getGeburtsdatum());

						// Religion aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(5, 1, result.getReligion());

						// Koerpergroesse aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(6, 1, result.getKoerpergroesse());

						// Haarfarbe aus der Datenbank holen
						// und in Tabelle eintragen				
						showFremdprofilFlexTable.setText(7, 1, result.getHaarfarbe());

						// Raucher aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(8, 1, result.getRaucher());
					}


				
				});
		
		/**
		 * TEIL MILENA BEGINN: Programmierung "Vermerk setzen" / "Vermerk löschen" Button.
		 */
		// Vermerkstatus überprüfen. 
		ClientsideSettings.getPartnerboerseAdministration().getVermerkStatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				String buttonText = "";

				// Button-Aufschrift entsprechend ermitteltem Vermerkstatus hinzufügen. 
				if(result == 0){
					buttonText = "Vermerk setzen"; 
				} else {
					buttonText = "Vermerk löschen"; 
				}	
				
				// "Vermerk setzen" / "Vermerk löschen" Button hinzufügen. 
				final Button mButton = new Button(buttonText); 
				verPanel.add(mButton);
				
				// ClickHandler hinzufügen. 
				mButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						infoLabel.setText("test");
						verPanel.add(infoLabel);
						
//						ClientsideSettings.getPartnerboerseAdministration().vermerkLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								infoLabel.setText("Fehler");
//								
//							}
//
//							@Override
//							public void onSuccess(Void result) {
//								infoLabel.setText("Erfolg");
//								
//							}
//							
//						});
						
					}
					
				}); 
				
			}
				
		});
		
		/**
		 * TEIL MILENA ENDE: Programmierung "Vermerk setzen" / "Vermerk löschen" Button 
		 */
	
		verPanel.add(ueberschriftLabel);
		verPanel.add(showFremdprofilFlexTable);
		verPanel.add(infoLabel);

	}
}
