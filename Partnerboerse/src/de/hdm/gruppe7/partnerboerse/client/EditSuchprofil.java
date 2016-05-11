package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
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

public class EditSuchprofil extends VerticalPanel {
	
	int profilId;
	
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor
	 */
	public EditSuchprofil() {
		this.add(verPanel);

	
	/**
	 * Label �berschrift
	 */
	final Label ueberschriftLabel = new Label("Aktuelles Suchprofil bearbeiten");
	
	/**
	 * Panel Button
	 */
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	
	/**
	 * Tabelle erzeugen, in der das Suchprofil dargestellt wird und bearbeitet werden kann.
	 */
	final FlexTable editSuchprofilFlexTable = new FlexTable();
	
	
	/**
	 *  * Tabelle formatieren und CSS einbinden.
	 */
	editSuchprofilFlexTable.setCellPadding(6);
	editSuchprofilFlexTable.getColumnFormatter().addStyleName(0,
			"TableHeader");
	editSuchprofilFlexTable.addStyleName("FlexTable");
	
	
	/**
	 * Erste Spalte der Tabelle festlegen.
	 */

	editSuchprofilFlexTable.setText(0, 0, "Suchprofil-Id");
	editSuchprofilFlexTable.setText(1, 0, "Geschlecht");
	editSuchprofilFlexTable.setText(2, 0, "Koerpergroesse");
	editSuchprofilFlexTable.setText(3, 0, "Haarfarbe");
	editSuchprofilFlexTable.setText(4, 0, "Alter von");
	editSuchprofilFlexTable.setText(5, 0, "Alter bis");
	editSuchprofilFlexTable.setText(6, 0, "Raucher");
	editSuchprofilFlexTable.setText(7, 0, "Religion");
	
	
	
	
	/**
	 * Drittte Spalte der Tabelle festlegen (Textboxen zum bearbeiten der Werte)
	 */
	
	final Label editLabel = new Label();
	
	final ListBox geschlechtListBox = new ListBox();
	geschlechtListBox.addItem("Keine Auswahl");
	geschlechtListBox.addItem("Weiblich");
	geschlechtListBox.addItem("Männlich");
	editSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);
	
	final TextBox koerpergroesseTextBox = new TextBox();
	editSuchprofilFlexTable.setWidget(2, 2, koerpergroesseTextBox);
	
	final ListBox haarfarbeListBox = new ListBox();
	haarfarbeListBox.addItem("Keine Auswahl");
	haarfarbeListBox.addItem("Blond");
	haarfarbeListBox.addItem("Braun");
	haarfarbeListBox.addItem("Rot");
	haarfarbeListBox.addItem("Schwarz");
	haarfarbeListBox.addItem("Grau");
	haarfarbeListBox.addItem("Glatze");
	editSuchprofilFlexTable.setWidget(3, 2, haarfarbeListBox);
	
	
	final TextBox alterMinTextBox = new TextBox();
	editSuchprofilFlexTable.setWidget(4, 2, alterMinTextBox);
	
	
	final TextBox alterMaxTextBox = new TextBox();
	editSuchprofilFlexTable.setWidget(5, 2, alterMaxTextBox);
	
	
	final ListBox raucherListBox = new ListBox();
	raucherListBox.addItem("Keine Angabe");
	raucherListBox.addItem("Raucher");
	raucherListBox.addItem("Nichtraucher");
	editSuchprofilFlexTable.setWidget(6, 2, raucherListBox);
	
	final ListBox religionListBox = new ListBox();
	religionListBox.addItem("Keine Auswahl");
	religionListBox.addItem("Christlich");
	religionListBox.addItem("Juedisch");
	religionListBox.addItem("Muslimisch");
	religionListBox.addItem("Buddhistisch");
	religionListBox.addItem("Hinduistisch");
	editSuchprofilFlexTable.setWidget(7, 2, religionListBox);
	
	
	/**
	 * Text in Eingabefelder einf�gen
	 *
	 */
	
	final Label infoLabel2 = new Label();
	
	ClientsideSettings.getPartnerboerseAdministration()
	.getSuchprofilById(profilId, new AsyncCallback<Suchprofil>() {

		@Override
		public void onFailure(Throwable caught) {
			infoLabel2.setText("Es trat ein Fehler auf.");
										}

		@Override
		public void onSuccess(Suchprofil result) {
			
			koerpergroesseTextBox.setText(result.getKoerpergroesse());
			
			alterMinTextBox.setText(result.getAlterMin());
			
			alterMaxTextBox.setText(result.getAlterMax());
			
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
	verPanel.add(editSuchprofilFlexTable);
//	verPanel.add(infoLabel);
	verPanel.add(infoLabel2);
	verPanel.add(editLabel);
	
	/**
	 * Änderungen Speichern-Button hinzufügen und ausbauen.
	 */
			final Button speichernButton = new Button("&Auml;nderungen speichern");
			verPanel.add(buttonPanel);
			buttonPanel.add(speichernButton);

	/**
	 * ClickHandler f�r den Speichern-Button hinzuf�gen.
	 */
			final Label informationLabel = new Label();
			verPanel.add(informationLabel);	
			
			speichernButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
					ClientsideSettings.getPartnerboerseAdministration()
					.saveSuchprofil(alterMinTextBox.getText(), 
							alterMaxTextBox.getText(),
							geschlechtListBox.getSelectedItemText(),
							koerpergroesseTextBox.getText(),
							haarfarbeListBox.getSelectedItemText(),
							raucherListBox.getSelectedItemText(),
							religionListBox.getSelectedItemText(),new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									informationLabel.setText("Es trat ein Fehler auf");
								}

								@Override
								public void onSuccess(Void result) {
									informationLabel.setText("Suchprofil erfolgreich aktualisiert.");
									ShowSuchprofil showSuchprofil = new ShowSuchprofil();
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showSuchprofil);
								}

							});
					
				}
			});
	}
}
