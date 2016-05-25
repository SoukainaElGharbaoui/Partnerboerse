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

import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class EditSuchprofil extends VerticalPanel {

	int profilId;

	/**
	 * VerticalPanel hinzufügen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen. 
	 */
	public EditSuchprofil() {
		this.add(verPanel);

		/**
		 *  Überschrift-Label hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Suchprofil bearbeiten:");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 *  Button-Panel hinzufügen. 
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 *  Tabelle zur Anzeige und zur Bearbeitung des aktuellen Suchprofils hinzufügen. 
		 */
		final FlexTable editSuchprofilFlexTable = new FlexTable();

		/**
		 *  Tabelle formatieren und CSS einbinden.
		 */
		editSuchprofilFlexTable.setCellPadding(6);
		editSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		editSuchprofilFlexTable.addStyleName("FlexTable");

		/**
		 *  Erste Spalte der Tabelle festlegen.
		 */
		editSuchprofilFlexTable.setText(0, 0, "Suchprofil-Id");
		editSuchprofilFlexTable.setText(1, 0, "Geschlecht");
		editSuchprofilFlexTable.setText(2, 0, "Alter von");
		editSuchprofilFlexTable.setText(3, 0, "Alter bis");
		editSuchprofilFlexTable.setText(4, 0, "Körpergröße");
		editSuchprofilFlexTable.setText(5, 0, "Haarfarbe");
		editSuchprofilFlexTable.setText(6, 0, "Raucher");
		editSuchprofilFlexTable.setText(7, 0, "Religion");

		/**
		 * Dritte Spalte der Tabelle festlegen (TextBox/ListBox zur Bearbeitung der Werte). 
		 */
		// Edit-Label hinzufügen. 
		final Label editLabel = new Label();

		// Geschlecht-ListBox hinzufügen. 
		final ListBox geschlechtListBox = new ListBox();
//		geschlechtListBox.addItem("");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);
		
		// AlterMin-TextBox hinzufügen. 
		final TextBox alterMinTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(2, 2, alterMinTextBox);

		// AlterMax-TextBox hinzufügen. 
		final TextBox alterMaxTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(3, 2, alterMaxTextBox);

		// Körpergröße-TextBox hinzufügen. 
		final TextBox koerpergroesseTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);

		// Haarfarbe-ListBox hinzufügen. 
		final ListBox haarfarbeListBox = new ListBox();
//		haarfarbeListBox.addItem("");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editSuchprofilFlexTable.setWidget(5, 2, haarfarbeListBox);

		// Raucher-ListBox hinzufügen. 
		final ListBox raucherListBox = new ListBox();
//		raucherListBox.addItem("");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editSuchprofilFlexTable.setWidget(6, 2, raucherListBox);

		// Religion-ListBox hinzufügen. 
		final ListBox religionListBox = new ListBox();
//		religionListBox.addItem("");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editSuchprofilFlexTable.setWidget(7, 2, religionListBox);

		/**
		 * Daten des Suchprofils in die Tabelle einfügen. 
		 */
		// InfoLabel hinzufügen. 
		final Label infoLabel = new Label();

		// Entsprechendes Suchprofil aus der Datenbank auslesen. 
		ClientsideSettings.getPartnerboerseAdministration().getSuchprofilById(
				profilId, new AsyncCallback<Suchprofil>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(Suchprofil result) {
						
						final String suchprofilId = String.valueOf(result.getProfilId());
						editSuchprofilFlexTable.setText(0, 1, suchprofilId);
						
						// Geschlecht auslesen und einfügen. 		
						for(int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) { 
								geschlechtListBox.setSelectedIndex(i);
							}
						}
						
						// AlterMin auslesen und einfügen. 
						alterMinTextBox.setText(Integer.toString(result.getAlterMinInt()));

						// AlterMax auslesen und einfügen. 
						alterMaxTextBox.setText(Integer.toString(result.getAlterMaxInt()));
						
						// Körpergröße auslesen und einfügen. 
						koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));
						
						// Haarfarbe auslesen und einfügen. 
						for(int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) { 
								haarfarbeListBox.setSelectedIndex(i);
							}
						}
						
						// Raucherstatus auslesen und einfügen. 
						for(int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher().equals(raucherListBox.getValue(i))) { 
								raucherListBox.setSelectedIndex(i);
							}
						}

						// Religion auslesen und einfügen. 
						for(int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion().equals(religionListBox.getValue(i))) { 
								religionListBox.setSelectedIndex(i);
							}
						}
					}
				});

		/**
		 * Widgets zum VerticalPanel hinzufügen. 
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(editSuchprofilFlexTable);
		verPanel.add(infoLabel);
		verPanel.add(editLabel);

		/**
		 * Änderungen-Speichern-Button hinzufügen und ausbauen.
		 */
		final Button speichernButton = new Button("&Auml;nderungen speichern");
		verPanel.add(buttonPanel);
		buttonPanel.add(speichernButton);

		/**
		 * ClickHandler für den Änderungen-Speichern-Button hinzufügen.
		 */
		final Label informationLabel = new Label();
		verPanel.add(informationLabel);
		
		final Label warnungLabel = new Label(); 

		speichernButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
					warnungLabel.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
					verPanel.add(warnungLabel);
				} else {
				
				ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(
						geschlechtListBox.getSelectedItemText(),
						Integer.parseInt(alterMinTextBox.getText()), 
						Integer.parseInt(alterMaxTextBox.getText()),
						Integer.parseInt(koerpergroesseTextBox.getText()),
						haarfarbeListBox.getSelectedItemText(),
						raucherListBox.getSelectedItemText(),
						religionListBox.getSelectedItemText(),
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel
										.setText("Es trat ein Fehler auf");
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
			}
		});
	}
}
