package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;

public class ShowAllPartnervorschlaegeSpReport extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	VerticalPanel verPanel = new VerticalPanel(); 

	/**
	 * Label zur Information hinzufügen.
	 */
	final Label infoLabel = new Label(); 
	final Label ueberschriftLabel = new Label();
	
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowAllPartnervorschlaegeSpReport(){
	this.add(verPanel);
	final Label auswahlLabel = new Label("Waehlen Sie das anzuzeigende Suchprofil aus.");
	auswahlLabel.addStyleName("partnerboerse-label");
	final ListBox auswahlListBox = new ListBox();
	final Button anzeigenButton = new Button("Partnervorschlaege Report anzeigen");
		ueberschriftLabel.setText("Einen Moment bitte...");
	/**
	 * AuswahlListBox bef�llen
	 */
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(new AsyncCallback<List<Suchprofil>>() {

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");

			}

			@Override
			public void onSuccess(List<Suchprofil> result) {
				for (Suchprofil s : result) {
					auswahlListBox.addItem(s.getSuchprofilName());
				}

			}

		});

		
		
		/**
	 * Report auslesen.
	 */
	anzeigenButton.addClickHandler(new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			ClientsideSettings.getReportGenerator().createAllPartnervorschlaegeSpReport(auswahlListBox.getSelectedItemText(), 
					new AsyncCallback<AllPartnervorschlaegeSpReport>(){

				@Override
				public void onFailure(Throwable caught) {
					infoLabel.setText("Es trat ein Fehler auf."); 
				}

				@Override
				public void onSuccess(
						AllPartnervorschlaegeSpReport report) {
					 	if(report != null){
					 		/*
				        	 * Neue HTML-Seite fuer den Suchprofil-Report erzeugen.
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
			
	
	
	verPanel.add(infoLabel);
	verPanel.add(ueberschriftLabel);
	verPanel.add(auswahlLabel);
	verPanel.add(auswahlListBox);
	verPanel.add(anzeigenButton);
	
	
	}	
}