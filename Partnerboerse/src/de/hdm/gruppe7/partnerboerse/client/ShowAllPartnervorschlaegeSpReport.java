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

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;

public class ShowAllPartnervorschlaegeSpReport extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanel hinzufügen.
	 */
	VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Label zur Information hinzufügen.
	 */
	private Label infoLabel = new Label();
//	private Label informationLabel = new Label();
	private Label ueberschriftLabel = new Label();
	
	private Label auswahlLabel = new Label("WÃ¤hlen Sie das anzuzeigende Suchprofil aus.");
	private ListBox auswahlListBox = new ListBox();
	private Button anzeigenButton = new Button("Partnervorschlaege Report anzeigen");

	
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
	public ShowAllPartnervorschlaegeSpReport() {
		this.add(verPanel);
		
		auswahlLabel.addStyleName("partnerboerse-label");
//		informationLabel.addStyleName("partnerboerse-label");
		
		ueberschriftLabel.setText("Einen Moment bitte...");
		
		/**
		 * AuswahlListBox bef�llen
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<List<Suchprofil>>() {

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
		anzeigenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ClientsideSettings.getReportGenerator().createAllPartnervorschlaegeSpReport(nutzerprofil,
						auswahlListBox.getSelectedItemText(), new AsyncCallback<AllPartnervorschlaegeSpReport>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(AllPartnervorschlaegeSpReport report) {
								if (report != null) {
									/*
									 * Neue HTML-Seite fuer den
									 * Suchprofil-Report erzeugen.
									 */

									HTMLReportWriter writer = new HTMLReportWriter();
									writer.process(report);
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(new HTML(writer.getReportText()));
								}
							}
							
//							boolean befuellt = pruefeLeereTable();
//							
//							if (befuellt == true) {
//								
//								ueberschriftLabel.setVisible(false);
//								merklisteFlexTable.setVisible(false);
//												
//								informationLabel.setText("Sie haben sich derzeit keine Profile gemerkt.");
//							}
				});
			}
		});

		verPanel.add(infoLabel);
//		verPanel.add(informationLabel);
		verPanel.add(ueberschriftLabel);
		
		verPanel.add(auswahlLabel);
		verPanel.add(auswahlListBox);
		verPanel.add(anzeigenButton);

	}
}