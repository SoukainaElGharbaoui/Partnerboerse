package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;




public class EditNutzerprofil extends VerticalPanel {
	
	int nutzerprofilId; 
	
	VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor
	 */
	public EditNutzerprofil() {
		//this.nutzerprofilId = nutzerprofilId;
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */
		final Label ueberschriftLabel = new Label("Aktuelles Nutzerprofil bearbeiten");
		
		/**
		 * Panel Button
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();	
		
		
		/**
		 * Tabelle erzeugen, in der das Nutzerprofil dargestellt wird und bearbeitet werden kann.
		 */
		final FlexTable editNutzerprofilFlexTable = new FlexTable();
		
		
		/**
		 *  * Tabelle formatieren und CSS einbinden.
		 */
		editNutzerprofilFlexTable.setCellPadding(6);
		editNutzerprofilFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		editNutzerprofilFlexTable.addStyleName("FlexTable");
		
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */

		editNutzerprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
		editNutzerprofilFlexTable.setText(1, 0, "Vorname");
		editNutzerprofilFlexTable.setText(2, 0, "Nachname");
		editNutzerprofilFlexTable.setText(3, 0, "Geschlecht");
		editNutzerprofilFlexTable.setText(4, 0, "Geburtsdatum");
		editNutzerprofilFlexTable.setText(5, 0, "Raucher");
		editNutzerprofilFlexTable.setText(6, 0, "Koerpergroesse");
		editNutzerprofilFlexTable.setText(7, 0, "Haarfarbe");
		editNutzerprofilFlexTable.setText(8, 0, "Religion");

		
		
		/**
		 * Drittte Spalte der Tabelle festlegen (Textboxen zum bearbeiten der Werte)
		 */
		
		final Label editLabel = new Label();
		
		final TextBox vornameTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(1, 2, vornameTextBox);
		
		final TextBox nachnameTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(2, 2, nachnameTextBox);
		
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editNutzerprofilFlexTable.setWidget(3, 2, geschlechtListBox);
		
		final TextBox geburtsdatumTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(4, 2, geburtsdatumTextBox);
	
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editNutzerprofilFlexTable.setWidget(5, 2, raucherListBox);
		
		final TextBox koerpergroesseTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(6, 2, koerpergroesseTextBox);
		
		final ListBox haarfarbeListBox = new ListBox();
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editNutzerprofilFlexTable.setWidget(7, 2, haarfarbeListBox);
		
		final ListBox religionListBox = new ListBox();
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editNutzerprofilFlexTable.setWidget(8, 2, religionListBox);
		
		
		/**
		 * Text in Eingabefelder einf�gen
		 *
		 */
		
		final Label infoLabel2 = new Label();
		
				ClientsideSettings.getPartnerboerseAdministration()
				.getNutzerprofilById(nutzerprofilId, new AsyncCallback<Nutzerprofil>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel2.setText("Es trat ein Fehler auf.");
															}

							@Override
							public void onSuccess(Nutzerprofil result) {
								
								vornameTextBox.setText(result.getVorname());
								
								nachnameTextBox.setText(result.getNachname());
								
								koerpergroesseTextBox.setText(result.getKoerpergroesse());
								
								geburtsdatumTextBox.setText(result.getGeburtsdatum());
									
								raucherListBox.setItemText(0, result.getRaucher());
								
								geschlechtListBox.setItemText(0, result.getGeschlecht());
								
								haarfarbeListBox.setItemText(0, result.getHaarfarbe());
								
								religionListBox.setItemText(0, result.getReligion());
							
							}
				});
		
				
				/**
				 * Zum Panel hinzuf�gen
				 */
				
				verPanel.add(ueberschriftLabel);
				verPanel.add(editNutzerprofilFlexTable);
//				verPanel.add(infoLabel);
				verPanel.add(infoLabel2);
				verPanel.add(editLabel);
				
				/**
				 *  �nderungen Speichern-Button hinzufügen und ausbauen.
				 */
						final Button speichernButton = new Button("&Auml;nderungen speichern");
						verPanel.add(buttonPanel);
						buttonPanel.add(speichernButton);
						
						final Label informationLabel = new Label();
						verPanel.add(informationLabel);		
						
						
						speichernButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								
								ClientsideSettings.getPartnerboerseAdministration()
								.updateNutzerprofil(vornameTextBox.getText(), nachnameTextBox.getText(),
										geburtsdatumTextBox.getText(),
										geschlechtListBox.getSelectedItemText(),
										haarfarbeListBox.getSelectedItemText(),
										koerpergroesseTextBox.getText(),
										raucherListBox.getSelectedItemText(),
										religionListBox.getSelectedItemText(),
										new AsyncCallback<Nutzerprofil>() {


													@Override
													public void onFailure(Throwable caught) {
														informationLabel
																.setText("Es trat ein Fehler auf");
													}

													@Override
													public void onSuccess(Nutzerprofil result) {
														informationLabel
																.setText("Das Nutzerprofil wurde erfolgreich gespeichert");
													}
										
										});

		}
});

	
	

}
}
