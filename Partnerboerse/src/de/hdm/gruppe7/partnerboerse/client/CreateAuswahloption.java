package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class CreateAuswahloption extends VerticalPanel {

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelOptionsbezeichnung = new HorizontalPanel();
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateAuswahloption() {
		this.add(verPanel);
		
		/**
		 * Erzeugen einer Auswahl fuer das Geschlecht.
		 */
		final Label optionsbezeichnungLabel = new Label("Auswahloptionen:");
		final ListBox optionsbezeichnungListBox = new ListBox();
		optionsbezeichnungListBox.addItem("Keine Auswahl");
		optionsbezeichnungListBox.addItem("Hund");
		optionsbezeichnungListBox.addItem("Katze");
		verPanel.add(horPanelOptionsbezeichnung);
		horPanelOptionsbezeichnung.add(optionsbezeichnungListBox);
		horPanelOptionsbezeichnung.add(optionsbezeichnungLabel);

		/**
		 * informationLabel für die Benutzerinformation erzeugen.
		 */
		final Label informationLabel = new Label();

		final Button createAuswahloptionButton = new Button(
				"Auswahl bestätigen");
		createAuswahloptionButton
				.setStylePrimaryName("partnerboerse-menubutton");
		verPanel.add(createAuswahloptionButton);

		/**
		 * informationLabel zum navPanel hinzufügen.
		 */
		verPanel.add(informationLabel);

		createAuswahloptionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.createAuswahloption(optionsbezeichnungListBox.getSelectedItemText(),
								new AsyncCallback<Auswahloption>()  {

									@Override
									public void onFailure(Throwable caught) {
										informationLabel
												.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Auswahloption result) {
										informationLabel
												.setText("Das Nutzerprofil wurde erfolgreich angelegt");
									}

								});

			}
		});

	}
}