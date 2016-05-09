package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class EditNutzerprofil extends VerticalPanel {
	
	int nutzerprofilId; 

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelVorname = new HorizontalPanel();
	private HorizontalPanel horPanelNachname = new HorizontalPanel();
	private HorizontalPanel horPanelGeschlecht = new HorizontalPanel();
	private HorizontalPanel horPanelGeburtsdatum = new HorizontalPanel();
	private HorizontalPanel horPanelRaucher = new HorizontalPanel();
	private HorizontalPanel horPanelKoerpergroesse = new HorizontalPanel();
	private HorizontalPanel horPanelHaarfarbe = new HorizontalPanel();
	private HorizontalPanel horPanelReligion = new HorizontalPanel();
	
	/**
	 * Konstruktor
	 */
	
	public EditNutzerprofil() {
		this.add(verPanel);
		
//	public EditNutzerprofil(int nutzerprofilId) {
//		this.nutzerprofilId = nutzerprofilId;
//		this.add(verPanel);

//		/**
//		 * Erzeugen eines Eingabefelds fuer den Vornamen.
//		 */
//		final TextBox vornameTextBox = new TextBox
//				(ClientsideSettings.getPartnerboerseAdministration()
//						.getNutzerprofilById(nutzerprofilId, new AsyncCallback<Nutzerprofil>()));
						
		final TextBox vornameTextBox = new TextBox();
		final Label vornameLabel = new Label("Vorname");
	
		verPanel.add(horPanelVorname);
		horPanelVorname.add(vornameTextBox);
		horPanelVorname.add(vornameLabel);
		
		
		/**
		 * infoLabel für die Benutzerinformation erzeugen.
		 */
		final Label infoLabel = new Label();
		verPanel.add(infoLabel);
		
		
		/**
		 * updateNutzerprofilButton, um das Nutzerprofil zu aktualisieren.
		 */
		final Button updateNutzerprofilButton = new Button(
				"Nutzerprofil updaten");
		updateNutzerprofilButton
				.setStylePrimaryName("partnerboerse-menubutton");
		verPanel.add(updateNutzerprofilButton);
		
		
		/**
		 * ClickHandler für den Button updateNutzerprofilButton
		 */
	updateNutzerprofilButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			ClientsideSettings.getPartnerboerseAdministration()
			.updateNutzerprofil(vornameTextBox.getText(),
					new AsyncCallback<Nutzerprofil>() {

//			ClientsideSettings.getPartnerboerseAdministration()
//					.createNutzerprofil(vornameTextBox.getText(),
//							nachnameTextBox.getText(),
//							geburtsdatumTextBox.getText(),
//							geschlechtListBox.getSelectedItemText(),
//							haarfarbeListBox.getSelectedItemText(),
//							koerpergroesseTextBox.getText(),
//							raucherListBox.getSelectedItemText(),
//							religionListBox.getSelectedItemText(),
//							new AsyncCallback<Nutzerprofil>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel
											.setText("Es trat ein Fehler auf");
								}

								@Override
								public void onSuccess(Nutzerprofil result) {
									infoLabel
											.setText("Das Nutzerprofil wurde erfolgreich angelegt");
								}

							});

		}
	});

}
}