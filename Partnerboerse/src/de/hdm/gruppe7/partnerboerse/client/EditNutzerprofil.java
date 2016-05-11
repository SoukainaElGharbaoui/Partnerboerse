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

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
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
	
		
	public EditNutzerprofil(int nutzerprofilId) {
		this.nutzerprofilId = nutzerprofilId;
		this.add(verPanel);

		/**
		 * Erzeugen eines Eingabefelds fuer den Vornamen.
		 */
		final TextBox vornameTextBox = new TextBox();
		final  TextBox nachnameTextBox = new TextBox();
		final TextBox  geburtsdatumTextBox = new TextBox();
		final ListBox geschlechtListBox = new ListBox();
		final ListBox haarfarbeListBox = new ListBox();
		final TextBox koerpergroesseTextBox = new TextBox();
		final ListBox raucherListBox = new ListBox();
		final ListBox religionListBox = new ListBox();
		
		
		final Label ueberschriftLabel = new Label("Hier koennen sie ihr Nutzerprofil bearbeiten!");
		final Label vornameLabel = new Label ("Vorname");
		final Label nachnameLabel = new Label ("Nachname");
		final Label geburtsdatumLabel = new Label ("Geburtsdatum");
//		final Label koerpergroesseLabel = new Label ("Geburtsdatum");
		
		
//		final TextBox vornameTextBox1 = new TextBox();
//		final Label vornameLabel = new Label("Vorname");
		
		verPanel.add(ueberschriftLabel);
		
		verPanel.add(horPanelVorname);
		horPanelVorname.add(vornameTextBox);
		horPanelVorname.add(vornameLabel);
		
		verPanel.add(horPanelNachname);
		horPanelNachname.add(nachnameTextBox);
		horPanelNachname.add(nachnameLabel);
		
		verPanel.add(horPanelGeburtsdatum);
		horPanelGeburtsdatum.add(geburtsdatumTextBox);
		horPanelGeburtsdatum.add(geburtsdatumLabel);
		
//		verPanel.add(horPanelKoerpergroesse);
//		horPanelGeburtsdatum.add(koerpergroesseTextBox);
//		horPanelGeburtsdatum.add(koerpergroesseLabel);
		
//		horPanelVorname.add(vornameTextBox1);
//		horPanelVorname.add(vornameLabel);
		
		
		/**
		 * infoLabel für die Benutzerinformation erzeugen.
		 */
		final Label infoLabel = new Label();
		verPanel.add(infoLabel);
		
		final Label infoLabel2 = new Label();
		verPanel.add(infoLabel2);
		
		
		
		ClientsideSettings.getPartnerboerseAdministration()
				.getNutzerprofilById(nutzerprofilId,
						new AsyncCallback<Nutzerprofil>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel2.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Nutzerprofil result) {

								// final String nutzerprofilId2 =
								// String.valueOf(result.getProfilId());

								vornameTextBox.setText(result.getVorname());

								nachnameTextBox.setText(result.getNachname());

								geburtsdatumTextBox.setText(result
										.getGeburtsdatum());

								koerpergroesseTextBox.setText(result
										.getKoerpergroesse());

							}

						});
					
	
		
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
				
				// geschlechtListBox.getSelectedItemText(),
								// haarfarbeListBox.getSelectedItemText(),
								// koerpergroesseTextBox.getText(),
								// raucherListBox.getSelectedItemText(),
								// religionListBox.getSelectedItemText(),

				ClientsideSettings.getPartnerboerseAdministration()
						.saveNutzerprofil(vornameTextBox.getText(),
								nachnameTextBox.getText(),
								geburtsdatumTextBox.getText(),
								
								new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										infoLabel
												.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Void result) {
										infoLabel
												.setText("Das Nutzerprofil wurde erfolgreich angelegt");
									}

								});
				

			}
		});

	}
}
