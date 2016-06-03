package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.UnangesehenePartnervorschlaegeReport;

public class InfosReport extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	VerticalPanel verPanel = new VerticalPanel(); 
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public InfosReport(){
	this.add(verPanel);
	
	/**
	 * Label zur Information hinzufügen.
	 */
	final Label infoLabel = new Label(); 
	final Label ueberschriftLabel = new Label();
	ueberschriftLabel.setText("Einen Moment bitte...");
	
	ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(1, new AsyncCallback<Nutzerprofil>(){

		@Override
		public void onFailure(Throwable caught) {
			infoLabel.setText("Es HIER trat ein Fehler auf."); 
		}

		@Override
		public void onSuccess(Nutzerprofil n) {
			if(n != null){
				
				ClientsideSettings.getReportGenerator().createAllInfosOfNutzerReport(n,new AsyncCallback<AllInfosOfNutzerReport>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(AllInfosOfNutzerReport report) {
			
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
		
		}
		
	});
	verPanel.add(infoLabel);
	verPanel.add(ueberschriftLabel);
	
	
	
	
	
}
}
