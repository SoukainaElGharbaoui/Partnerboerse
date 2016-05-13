package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateNutzerprofil extends VerticalPanel {

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor
	 */
	public CreateNutzerprofil() {
		this.add(verPanel);

		
		/**
		 * Label �berschrift
		 */

		final Label ueberschriftLabel = new Label("Nutzerprofil anlegen");

		verPanel.add(ueberschriftLabel);

		/**
		 * Tabelle erzeugen, in der das Suchprofil dargestellt wird und bearbeitet werden kann.
		 */
		final FlexTable createNutzerprofilFlexTable = new FlexTable();
		
		/**
		 *  * Tabelle formatieren und CSS einbinden.
		 */
		createNutzerprofilFlexTable.setCellPadding(6);
		createNutzerprofilFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		createNutzerprofilFlexTable.addStyleName("FlexTable");
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */

		createNutzerprofilFlexTable.setText(0, 0, "Nutzerrofil-Id");
		createNutzerprofilFlexTable.setText(1, 0, "Vorname");
		createNutzerprofilFlexTable.setText(2, 0, "Nachname");
		createNutzerprofilFlexTable.setText(3, 0, "Geburtsdatum");
		createNutzerprofilFlexTable.setText(4, 0, "Koerpergroesse");
		createNutzerprofilFlexTable.setText(5, 0, "Haarfarbe");
		createNutzerprofilFlexTable.setText(6, 0, "Geschlecht");
		createNutzerprofilFlexTable.setText(7, 0, "Raucher");
		createNutzerprofilFlexTable.setText(8, 0, "Religion");
		
		
		/**
		 * Drittte Spalte der Tabelle festlegen (Textboxen zum bearbeiten der Werte)
		 */

		
		final TextBox vornameTextBox = new TextBox();
		createNutzerprofilFlexTable.setWidget(1, 2, vornameTextBox);
		
		final TextBox nachnameTextBox = new TextBox();
		createNutzerprofilFlexTable.setWidget(2, 2, nachnameTextBox);
		
	
		final TextBox geburtsdatumTextBox = new TextBox();
		createNutzerprofilFlexTable.setWidget(3, 2, geburtsdatumTextBox);
		
		final TextBox koerpergroesseTextBox = new TextBox();
		createNutzerprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);
		
		final ListBox haarfarbeListBox = new ListBox();
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		createNutzerprofilFlexTable.setWidget(5, 2, haarfarbeListBox);
		
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createNutzerprofilFlexTable.setWidget(6, 2, geschlechtListBox);
		
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		createNutzerprofilFlexTable.setWidget(7, 2, raucherListBox);
		
		final ListBox religionListBox = new ListBox();
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createNutzerprofilFlexTable.setWidget(8, 2, religionListBox);
	
		
		verPanel.add(createNutzerprofilFlexTable);
		

		/**
		 * informationLabel für die Benutzerinformation erzeugen.
		 */
		final Label informationLabel = new Label();

		final Button createNutzerprofilButton = new Button(
				"Nutzerprofil anlegen");
		createNutzerprofilButton
				.setStylePrimaryName("partnerboerse-menubutton");
		verPanel.add(createNutzerprofilButton);

		/**
		 * informationLabel zum navPanel hinzufügen.
		 */
		verPanel.add(informationLabel);

		createNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.createNutzerprofil(vornameTextBox.getText(),
								nachnameTextBox.getText(),
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
												.setText("Das Nutzerprofil wurde erfolgreich angelegt");
									}

								});

			}
		});

	}
}
