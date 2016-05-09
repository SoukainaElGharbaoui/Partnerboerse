package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class CreateInfo extends VerticalPanel {

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelInfo = new HorizontalPanel();
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateInfo() {
		this.add(verPanel);
		
		/**
		 * Erzeugen einer TextBox fuer die Info.
		 */
		final Label infoLabel = new Label("weitere Informationen zu deiner Person:");
		final TextBox infoTextBox = new TextBox();
		verPanel.add(horPanelInfo);
		horPanelInfo.add(infoTextBox);
		horPanelInfo.add(infoLabel);

		/**
		 * informationLabel für die Benutzerinformation erzeugen.
		 */
		final Label informationLabel = new Label();

		final Button createInfoButton = new Button(
				"Persönliche Information angeben");
		createInfoButton
				.setStylePrimaryName("partnerboerse-menubutton");
		verPanel.add(createInfoButton);

		/**
		 * informationLabel zum navPanel hinzufügen.
		 */
		verPanel.add(informationLabel);

		createInfoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.createInfo(infoTextBox.getText(),
								new AsyncCallback<Info>()  {

									@Override
									public void onFailure(Throwable caught) {
										informationLabel
												.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Info result) {
										informationLabel
												.setText("Das Nutzerprofil wurde erfolgreich angelegt");
									}

								});

			}
		});

	}
}