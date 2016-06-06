package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.PartnervorschlaegeSpReport;

public class ShowPartnervorschlaegeSpReport extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	VerticalPanel verPanel = new VerticalPanel(); 
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowPartnervorschlaegeSpReport(){
	this.add(verPanel);
	
	/**
	 * Label zur Information hinzufügen.
	 */
	final Label infoLabel = new Label(); 
	final Label ueberschriftLabel = new Label();
	ueberschriftLabel.setText("Einen Moment bitte...");
	final ListBox auswahlListBox = new ListBox(); 
	final Button anzeigenSpButton = new Button("Partnervorschlaege Report anzeigen");
	/**
	 * Nutzer auslesen.
	 */
	ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(new AsyncCallback<Nutzerprofil>(){

		@Override
		public void onFailure(Throwable caught) {
			infoLabel.setText("Es HIER trat ein Fehler auf."); 
			
		}

		@Override
		public void onSuccess(Nutzerprofil n) {

			infoLabel.setText("Bitte waehlen Sie ein Suchprofil aus um fortzufahren"); 
			
		
			ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(
					new AsyncCallback<List<Suchprofil>>() {

						@Override
						public void onFailure(Throwable caught) {
							infoLabel.setText("Es trat ein Fehler auf."); 
							
						}

						@Override
						public void onSuccess(List<Suchprofil> result) {
							for(Suchprofil s : result) {
								auswahlListBox.addItem(s.getSuchprofilName()); 
								
					
							}
								
						}
				
			});
			


			
		}
		
	});
	
	anzeigenSpButton.addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event) {
			infoLabel.setText("Geklickt."); 
			 
			
			ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(new AsyncCallback<Nutzerprofil>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Nutzerprofil n) {
					
					ClientsideSettings.getReportGenerator().createPartnervorschlaegeSpReport(n, auswahlListBox.getSelectedItemText(), new AsyncCallback<PartnervorschlaegeSpReport>(){

						@Override
						public void onFailure(Throwable caught) {
							infoLabel.setText("Es trat ein Fehlerchen auf."); 
							
							
						}

						@Override
						public void onSuccess(PartnervorschlaegeSpReport report) {
							if(report != null){
						 		/*
					        	 * Neue HTML-Seite für den Suchprofil-Report erzeugen.
					        	 */
								HTMLReportWriter writer = new HTMLReportWriter();
						        writer.process(report);
						        RootPanel.get("Details").clear();
								RootPanel.get("Details").add(new HTML(writer.getReportText()));
						 	}
						
							
						}
						
					});
					
				}
				
			});
		
			
		}
		
	});
	

	verPanel.add(infoLabel);
	verPanel.add(ueberschriftLabel);
	verPanel.add(auswahlListBox);
	verPanel.add(anzeigenSpButton);
	
	
	
	}
}
