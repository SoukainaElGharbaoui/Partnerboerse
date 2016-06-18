package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

public class ShowAllPartnervorschlaegeNpReport extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Label zur Information hinzufügen.
	 */
	private Label infoLabel = new Label();
//	private Label informationLabel = new Label();
	private Label ueberschriftLabel = new Label();
	
//	private int zaehler;
//	
//	public boolean pruefeLeereTable() {
//		
//		for (int k = 2; k < merklisteFlexTable.getRowCount(); k++) {
//			
//			if (merklisteFlexTable.getText(k, 0) == null) {
//			}
//			
//			else {
//				zaehler++;
//			}
//		}
//		
//		if (zaehler == 0) {
//			return true;
//		}
//		
//		else {
//			return false;
//		}
//	}

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowAllPartnervorschlaegeNpReport() {
		this.add(verPanel);
		
		ueberschriftLabel.setText("Einen Moment bitte...");
		ueberschriftLabel.addStyleName("partnerboerse-label");
//		informationLabel.addStyleName("partnerboerse-label");

		/**
		 * Report auslesen.
		 */

		ClientsideSettings.getReportGenerator().createAllPartnervorschlaegeNpReport(nutzerprofil,
				new AsyncCallback<AllPartnervorschlaegeNpReport>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(AllPartnervorschlaegeNpReport report) {
						if (report != null) {
							/*
							 * Neue HTML-Seite fuer den Report erzeugen.
							 */
							HTMLReportWriter writer = new HTMLReportWriter();
							writer.process(report);
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(new HTML(writer.getReportText()));
						}
						
//						boolean befuellt = pruefeLeereTable();
//						
//						if (befuellt == true) {
//							
//							ueberschriftLabel.setVisible(false);
//							merklisteFlexTable.setVisible(false);
//											
//							informationLabel.setText("Sie haben sich derzeit keine Profile gemerkt.");
//						}

					}

				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(infoLabel);
//		verPanel.add(informationLabel);
	}
}
