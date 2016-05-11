package de.hdm.gruppe7.partnerboerse.client;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
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
		final Label koerpergroesseLabel = new Label ("Koerpergroesse");
		final Label haarfarbeLabel = new Label ("Haarfarbe");
		final Label geschlechtLabel = new Label ("Geschlecht");
		final Label religionLabel = new Label ("Religion");
		final Label raucherLabel = new Label ("Raucher");
		

		
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
		
		verPanel.add(horPanelKoerpergroesse);
		horPanelKoerpergroesse.add(koerpergroesseTextBox);
		horPanelKoerpergroesse.add(koerpergroesseLabel);
		
		verPanel.add(horPanelHaarfarbe);
		horPanelHaarfarbe.add(haarfarbeListBox);
		horPanelHaarfarbe.add(haarfarbeLabel);
		
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		
		verPanel.add(horPanelGeschlecht);
		horPanelGeschlecht.add(geschlechtListBox);
		horPanelGeschlecht.add(geschlechtLabel);
		
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		
		verPanel.add(horPanelReligion);
		horPanelReligion.add(religionListBox);
		horPanelReligion.add(religionLabel);
		
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		
		verPanel.add(horPanelRaucher);
		horPanelRaucher.add(raucherListBox);
		horPanelRaucher.add(raucherLabel);
		
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		

		
		
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
								
								haarfarbeListBox.setItemText(0, result.getHaarfarbe());
								
								religionListBox.setItemText(0, result.getReligion());
								
								geschlechtListBox.setItemText(0,  result.getGeschlecht());
								
								raucherListBox.setItemText (0, result.getRaucher());
		
								
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
				
			
				ClientsideSettings.getPartnerboerseAdministration()
						.saveNutzerprofil(vornameTextBox.getText(),
								nachnameTextBox.getText(),
								geburtsdatumTextBox.getText(),
								 geschlechtListBox.getSelectedItemText(),
								 haarfarbeListBox.getSelectedItemText(),
								 koerpergroesseTextBox.getText(),
								 raucherListBox.getSelectedItemText(),
								 religionListBox.getSelectedItemText(),
								new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										infoLabel
												.setText("Es trat ein Fehler auf");
									}
									@Override
									public void onSuccess(Void result) {
										ShowEigenesNp showEigenesNp = new ShowEigenesNp();
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(showEigenesNp);
									}
								});
				
			}
		});
	}
}