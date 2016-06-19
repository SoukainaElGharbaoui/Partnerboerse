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

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.HTMLReportWriter;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;

public class ShowAllPartnervorschlaegeSpReport extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();

	private Label auswahlLabel = new Label(
			"Wählen Sie ein Suchprofil aus, zu welchem Sie Partnervorschläge anzeigen möchten.");
	private Label infoLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private Button anzeigenButton = new Button("Partnervorschläge anzeigen");

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowAllPartnervorschlaegeSpReport() {
		this.add(verPanel);

		auswahlLabel.addStyleName("partnerboerse-label");

		/**
		 * AuswahlListBox befuellen
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<List<Suchprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(List<Suchprofil> result) {

						if (result.isEmpty()) {
							auswahlListBox.setVisible(false);
							anzeigenButton.setVisible(false);
							auswahlLabel.setText("Sie haben bisher kein Suchprofil angelegt.");

						} else {
							for (Suchprofil s : result) {
								auswahlListBox.addItem(s.getSuchprofilName());
							}
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
									RootPanel.get("Details").add(new ShowAllPartnervorschlaegeSpReport());
									RootPanel.get("Details").add(new HTML(writer.getReportText()));
								}
							}
						});
			}
		});

		verPanel.add(auswahlLabel);
		auswahlPanel.add(infoLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		verPanel.add(auswahlPanel);

	}
}
