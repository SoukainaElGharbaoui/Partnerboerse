package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllSuchprofileOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;

/**
 * Diese Klasse dient zur Anzeige des Suchprofil-Reports.
 * @author Milena Weinmann
 *
 */
public class SuchprofilReport extends VerticalPanel {
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	VerticalPanel verPanel = new VerticalPanel(); 
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public SuchprofilReport(){
		this.add(verPanel);
		
		/**
		 * Label zur Information hinzufügen.
		 */
		final Label infoLabel = new Label(); 
		
		/**
		 * Nutzer auslesen.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById( 
				new AsyncCallback<Nutzerprofil>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf."); 
						
					}

					@Override
					public void onSuccess(Nutzerprofil n) {
						if(n != null) {
							/*
							 * Suchprofil-Report erzeugen.
							 */
							ClientsideSettings.getReportGenerator().
							createAllSuchprofileOfNutzerReport(n, 
							new AsyncCallback<AllSuchprofileOfNutzerReport>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf."); 
									
								}

								@Override
								public void onSuccess(AllSuchprofileOfNutzerReport report) {
							        if(report != null) {
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


	}

}
