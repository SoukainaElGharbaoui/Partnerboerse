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

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class EditSuchprofil extends VerticalPanel {
	
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
	 * Label Button
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

	editSuchprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
	editSuchprofilFlexTable.setText(1, 0, "Geschlecht");
	editSuchprofilFlexTable.setText(2, 0, "Koerpergroesse");
	editSuchprofilFlexTable.setText(3, 0, "Haarfarbe");
	editSuchprofilFlexTable.setText(4, 0, "AlterMax");
	editSuchprofilFlexTable.setText(5, 0, "AlterMin");
	editSuchprofilFlexTable.setText(6, 0, "Raucher");
	editSuchprofilFlexTable.setText(7, 0, "Religion");
	
	
	/**
	 * Zweite Spalte der Tabelle festlegen (Datenbankabfrage der Werte)
	 */
	
	final Label infoLabel = new Label();
	
	
	
	/**
	 * Drittte Spalte der Tabelle festlegen (Textboxen zum bearbeiten der Werte)
	 */
	
	final Label editLabel = new Label();
	
	final ListBox geschlechtListBox = new ListBox();
	geschlechtListBox.addItem("Keine Auswahl");
	geschlechtListBox.addItem("Weiblich");
	geschlechtListBox.addItem("M�nnlich");
	editSuchprofilFlexTable.setWidget(1, 3, geschlechtListBox);
	
	final TextBox koerpergroesseTextBox = new TextBox();
	editSuchprofilFlexTable.setWidget(2, 3, koerpergroesseTextBox);
	
	final ListBox haarfarbeListBox = new ListBox();
	haarfarbeListBox.addItem("Keine Auswahl");
	haarfarbeListBox.addItem("Blond");
	haarfarbeListBox.addItem("Braun");
	haarfarbeListBox.addItem("Rot");
	haarfarbeListBox.addItem("Schwarz");
	haarfarbeListBox.addItem("Grau");
	haarfarbeListBox.addItem("Glatze");
	editSuchprofilFlexTable.setWidget(3, 3, haarfarbeListBox);
	
	final TextBox alterMaxTextBox = new TextBox();
	editSuchprofilFlexTable.setWidget(4, 3, alterMaxTextBox);
	
	final TextBox alterMinTextBox = new TextBox();
	editSuchprofilFlexTable.setWidget(5, 3, alterMinTextBox);
	
	final ListBox raucherListBox = new ListBox();
	raucherListBox.addItem("Keine Angabe");
	raucherListBox.addItem("Raucher");
	raucherListBox.addItem("Nichtraucher");
	editSuchprofilFlexTable.setWidget(6, 3, raucherListBox);
	
	final ListBox religionListBox = new ListBox();
	religionListBox.addItem("Keine Auswahl");
	religionListBox.addItem("Christlich");
	religionListBox.addItem("Juedisch");
	religionListBox.addItem("Muslimisch");
	religionListBox.addItem("Buddhistisch");
	religionListBox.addItem("Hinduistisch");
	editSuchprofilFlexTable.setWidget(7, 3, religionListBox);
	
	
	
	/**
	 * Zum Panel hinzuf�gen
	 */
	
	verPanel.add(ueberschriftLabel);
	verPanel.add(editSuchprofilFlexTable);
	verPanel.add(infoLabel);
	verPanel.add(editLabel);

	
	
	
	/**
	 *  �nderungen Speichern-Button hinzufügen und ausbauen.
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
					.save(alterMinTextBox.getText(),
							alterMaxTextBox.getText(),
							geschlechtListBox.getSelectedItemText(),
							haarfarbeListBox.getSelectedItemText(),
							koerpergroesseTextBox.getText(),
							raucherListBox.getSelectedItemText(),
							religionListBox.getSelectedItemText(),
							new AsyncCallback<Suchprofil>() {

								@Override
								public void onFailure(Throwable caught) {
							
									informationLabel
											.setText("Es trat ein Fehler auf");
								}

								@Override
								public void onSuccess(Suchprofil result) {
								ShowSuchprofil showSuchprofil = new ShowSuchprofil();
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showSuchprofil);
								}

							});

					
				}
			});
	}
}
