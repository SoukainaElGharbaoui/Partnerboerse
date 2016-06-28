package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

/**
 * Diese Klasse dient dazu, innerhalb des Reports Partnervorschlaege anhand
 * eines Nutzerprofils azuzeigen.
 */
public class ShowAllPartnervorschlaegeNpReport extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil;

	/**
	 * Vertikales Panel erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label infoLabel = new Label();
	private Label ueberschriftLabel = new Label();

	/**
	 * Konstruktor erstellen.
	 */
	public ShowAllPartnervorschlaegeNpReport(Nutzerprofil nutzerprofil) {
		this.nutzerprofil=nutzerprofil;
		run();
	}

	/**
	 * Die Methode startet den Aufbau der Seite.
	 */
	public void run() {
		this.add(verPanel);

		ueberschriftLabel.setText("Einen Moment bitte...");
		ueberschriftLabel.addStyleName("partnerboerse-label");


		reportAuslesen();
		verPanel.add(ueberschriftLabel);
		verPanel.add(infoLabel);
	}

	/**
	 * Report auslesen.
	 */
	public void reportAuslesen() {
		ClientsideSettings.getReportGenerator().createAllPartnervorschlaegeNpReport(nutzerprofil,
				new AsyncCallback<AllPartnervorschlaegeNpReport>() {

					@Override
					public void onFailure(Throwable caught) {
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
					}
				});

	}
}
