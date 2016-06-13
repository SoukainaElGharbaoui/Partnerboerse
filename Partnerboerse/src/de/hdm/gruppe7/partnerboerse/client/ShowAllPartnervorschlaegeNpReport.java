package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllProfildatenOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

public class ShowAllPartnervorschlaegeNpReport extends VerticalPanel {
	
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
	public ShowAllPartnervorschlaegeNpReport(){
	this.add(verPanel);
	ueberschriftLabel.setText("Einen Moment bitte...");
	/**
	 * Report auslesen.
	 */
	
				ClientsideSettings.getReportGenerator().createAllPartnervorschlaegeNpReport(new AsyncCallback<AllPartnervorschlaegeNpReport>(){

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf."); 
					}

					@Override
					public void onSuccess(
							AllPartnervorschlaegeNpReport report) {
						 	if(report != null){
						 		/*
					        	 * Neue HTML-Seite fuer den Report erzeugen.
					        	 */
								HTMLReportWriter writer = new HTMLReportWriter();
						        writer.process(report);
						        RootPanel.get("Details").clear();
								RootPanel.get("Details").add(new HTML(writer.getReportText()));
						 	}
						
					}
					
				});
				

	verPanel.add(ueberschriftLabel);
	verPanel.add(infoLabel);
	
	}	
}
