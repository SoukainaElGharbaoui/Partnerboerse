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

/**
 * Diese Klasse dient dazu einen Report aller Partnervorschlaege anhand eines
 * Suchprofils für ein Nutzerprofil anzuzeigen.
 */
public class ShowAllPartnervorschlaegeSpReport extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();
	
	/**
	 * Widgets erzeugen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	private Label auswahlLabel = new Label(
			"Waehlen Sie ein Suchprofil aus, zu welchem Sie Partnervorschlaege anzeigen mÃ¶chten.");
	private Label infoLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private Button anzeigenButton = new Button("Partnervorschlaege anzeigen");

	/**
	 * Konstruktor erstellen.
	 */
	public ShowAllPartnervorschlaegeSpReport() {
		this.add(verPanel);
		auswahlLabel.addStyleName("partnerboerse-label");

		/**
		 * AuswahlListBox  für die Suchprofile mit allen Suchprofilen eines
		 * Nutzerprofils befuellen
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
		 * ClickHaendler für den Button, der das Suchprofil auswählt. 
		 * Für das ausgewaehlte Suchprofil wird anschliessend der Report für
		 * alle Partnervorschlaege anhand des Suchprofils ausgelesen.
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
		/**
		 * Widgets zum vertikalen Panel hinzufuegen. 
		 */
		verPanel.add(auswahlLabel);
		auswahlPanel.add(infoLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		verPanel.add(auswahlPanel);

	}
}
