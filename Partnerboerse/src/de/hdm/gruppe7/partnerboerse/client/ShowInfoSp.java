package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowInfoSp extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	private Label ueberschriftLabel = new Label("Ihre Infos:");
	private Label informationLabel = new Label();

	private FlexTable showInfoFlexTable = new FlexTable();

	Suchprofil suchprofil = new Suchprofil();

	/**
	 * Konstruktor
	 * 
	 * @param integer
	 */
	public ShowInfoSp(final int suchprofilId) {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */

		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Label Button
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTable.setText(0, 0, "Suchprofil-Id");
		showInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		showInfoFlexTable.setText(0, 2, "Eigenschaft");
		showInfoFlexTable.setText(0, 3, "Infotext");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */

		ClientsideSettings.getPartnerboerseAdministration().getAllInfosNeuSp(suchprofilId,

				new AsyncCallback<List<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler beim Herausholen der Infos auf.");
					}

					@Override
					public void onSuccess(List<String> result) {
						informationLabel.setText("Es hat funktioniert! YEAH!");

						int row1 = showInfoFlexTable.getRowCount();
						int size = result.size();

						for (int i = 0; i < size; i++) {

							String suchprofilId = result.get(i);
							String eigenschaftId = result.get(i + 1);
							String erlaeuterung = result.get(i + 2);
							String infotext = result.get(i + 3);
							// String typ = result.get(i + 4);

							showInfoFlexTable.setText(row1, 0, suchprofilId);
							showInfoFlexTable.setText(row1, 1, eigenschaftId);
							showInfoFlexTable.setText(row1, 2, erlaeuterung);
							showInfoFlexTable.setText(row1, 3, infotext);

							row1++;
							i++;
							i++;
							i++;
							i++;
						}
					}
				});

		verPanel.add(showInfoFlexTable);
		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);

		final Button loeschenButton = new Button("Alle Infos löschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);

		final Button bearbeitenButton = new Button("Bearbeiten");
		verPanel.add(buttonPanel);
		buttonPanel.add(bearbeitenButton);

		final Button erstellenButton = new Button("Infos anlegen");
		verPanel.add(buttonPanel);
		buttonPanel.add(erstellenButton);

		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().deleteAllInfosNeuSp(suchprofilId,
						new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Beim Löschen aller Infos ist ein Fehler aufgetreten.");
							}

							@Override
							public void onSuccess(Void result) {
								informationLabel.setText("Das Löschen aller Infos hat funktioniert.");

								ShowSuchprofil showSp = new ShowSuchprofil();

								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showSp);
							}
						});
			}
		});

		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditInfoSp editInfoSp = new EditInfoSp(suchprofilId);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editInfoSp);
			}
		});

		erstellenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateInfoSp createInfoSp = new CreateInfoSp(suchprofilId);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createInfoSp);
			}
		});

	}
}