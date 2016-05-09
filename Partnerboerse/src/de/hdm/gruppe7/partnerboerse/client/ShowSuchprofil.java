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
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowSuchprofil extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	
	/**
	 * Konstruktor 
	 */
	public ShowSuchprofil() {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */
		final Label ueberschriftLabel = new Label("Aktuelles Suchprofil");
		
		/**
		 * Tabelle erzeugen in der das Suchprofil dargestellt wird. 
		 */
		final FlexTable showSuchprofilFlexTable = new FlexTable(); 
		
		/**
		 * Erste Zeile der Tabelle festlegen. 
		 */
		showSuchprofilFlexTable.setText(0, 0, "Suchprofil-Id");
		showSuchprofilFlexTable.setText(1, 0, "AlterMin");
		showSuchprofilFlexTable.setText(2, 0, "Optionen");
		
		/** 
		 * Tabelle formatieren und CSS einbinden. 
		 */
		showSuchprofilFlexTable.setCellPadding(6);
		showSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "merklisteHeader");
		showSuchprofilFlexTable.addStyleName("merklisteFlexTable");   
		
		final Label infoLabel = new Label(); 
		
		
		ClientsideSettings.getPartnerboerseAdministration()
		.getSuchprofilById(Benutzer.getProfilId(),
				new AsyncCallback<Suchprofil>() {
			
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
						
					}

					public void onSuccess(Suchprofil result) {
				
						// Suchprofil-Id aus der Datenabank holen 
						// und in Tabelle eintragen
						final String suchprofilId = String.valueOf(result.getProfilId());
						showSuchprofilFlexTable.setText(0, 1, suchprofilId); 						
						
						// Vorname aus Datenbank aus der Datenbank holen 
						// und in Tabelle eintragen
						showSuchprofilFlexTable.setText(1, 1, result.getAlterMin());
									
		// Bearbeiten-Button hinzufügen und ausbauen. 
		final Button bearbeitenButton = new Button("Bearbeiten");
		showSuchprofilFlexTable.setWidget(2, 1, bearbeitenButton); 
		
		// Löschen-Button hinzufügen und ausbauen. 
		final Button loeschenButton = new Button("Löschen");
		showSuchprofilFlexTable.setWidget(2, 2, loeschenButton); 
		
		// ClickHandler für den Löschen-Button hinzufügen. 
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
								
		// ClickHandler für den Löschen-Button hinzufügen. 
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
		
		}

	});
			
	verPanel.add(ueberschriftLabel);
		verPanel.add(showSuchprofilFlexTable); 
		verPanel.add(infoLabel);
		
	}
	
}

	
