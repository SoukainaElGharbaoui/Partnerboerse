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
<<<<<<< HEAD
		 * Label �berschrift
=======
		 *  Überschrift-Label hinzufügen. 
>>>>>>> refs/heads/master
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
		editSuchprofilFlexTable.setText(2, 0, "Körpergröße");
		editSuchprofilFlexTable.setText(3, 0, "Haarfarbe");
		editSuchprofilFlexTable.setText(4, 0, "Alter von");
		editSuchprofilFlexTable.setText(5, 0, "Alter bis");
		editSuchprofilFlexTable.setText(6, 0, "Raucher");
		editSuchprofilFlexTable.setText(7, 0, "Religion");

		/**
		 * Dritte Spalte der Tabelle festlegen (TextBox/ListBox zur Bearbeitung der Werte). 
		 */
		// Edit-Label hinzufügen. 
		final Label editLabel = new Label();

		// Geschlecht-ListBox hinzufügen. 
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);

		// Körpergröße-TextBox hinzufügen. 
		final TextBox koerpergroesseTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(2, 2, koerpergroesseTextBox);

		// Haarfarbe-ListBox hinzufügen. 
		final ListBox haarfarbeListBox = new ListBox();
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editSuchprofilFlexTable.setWidget(3, 2, haarfarbeListBox);

		// AlterMin-TextBox hinzufügen. 
		final TextBox alterMinTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(4, 2, alterMinTextBox);

		// AlterMax-TextBox hinzufügen. 
		final TextBox alterMaxTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(5, 2, alterMaxTextBox);

		// Raucher-ListBox hinzufügen. 
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Keine Auswahl");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editSuchprofilFlexTable.setWidget(6, 2, raucherListBox);

		// Religion-ListBox hinzufügen. 
		final ListBox religionListBox = new ListBox();
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editSuchprofilFlexTable.setWidget(7, 2, religionListBox);

		/**
<<<<<<< HEAD
		 * Text in Eingabefelder einf�gen
		 *
=======
		 * Daten des Suchprofils in die Tabelle einfügen. 
>>>>>>> refs/heads/master
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
						
						// Geschlecht auslesen und einfügen. 
						geschlechtListBox.setItemText(0, result.getGeschlecht());
						
						for(int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht() == geschlechtListBox.getValue(i)) { 
								geschlechtListBox.removeItem(i);
							}
						}
						
						// Körpergröße auslesen und einfügen. 
						koerpergroesseTextBox.setText(result.getKoerpergroesse());
						
						// Haarfarbe auslesen und einfügen. 
						haarfarbeListBox.setItemText(0, result.getHaarfarbe());
						
						for(int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe() == haarfarbeListBox.getValue(i)) { 
								haarfarbeListBox.removeItem(i);
							}
						}

						// AlterMin auslesen und einfügen. 
						alterMinTextBox.setText(result.getAlterMin());

						// AlterMax auslesen und einfügen. 
						alterMaxTextBox.setText(result.getAlterMax());
						
						// Raucherstatus auslesen und einfügen. 
						raucherListBox.setItemText(0, result.getRaucher());
						
						for(int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher() == raucherListBox.getValue(i)) { 
								raucherListBox.removeItem(i);
							}
						}

						// Religion auslesen und einfügen. 
						religionListBox.setItemText(0, result.getReligion());
						
						for(int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion() == religionListBox.getValue(i)) { 
								religionListBox.removeItem(i);
							}
						}
					}
				});

		/**
<<<<<<< HEAD
		 * Zum Panel hinzuf�gen
=======
		 * Widgets zum VerticalPanel hinzufügen. 
>>>>>>> refs/heads/master
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(editSuchprofilFlexTable);
		verPanel.add(infoLabel);
		verPanel.add(editLabel);

		/**
<<<<<<< HEAD
		 * �nderungen Speichern-Button hinzufügen und ausbauen.
=======
		 * Änderungen-Speichern-Button hinzufügen und ausbauen.
>>>>>>> refs/heads/master
		 */
		final Button speichernButton = new Button("&Auml;nderungen speichern");
		verPanel.add(buttonPanel);
		buttonPanel.add(speichernButton);

		/**
<<<<<<< HEAD
		 * ClickHandler f�r den Speichern-Button hinzuf�gen.
=======
		 * ClickHandler für den Änderungen-Speichern-Button hinzufügen.
>>>>>>> refs/heads/master
		 */
		final Label informationLabel = new Label();
		verPanel.add(informationLabel);

		speichernButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(
						alterMinTextBox.getText(), alterMaxTextBox.getText(),
						 
						geschlechtListBox.getSelectedItemText(),
						koerpergroesseTextBox.getText(),
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
								informationLabel
										.setText("Suchprofil erfolgreich aktualisiert.");
								ShowSuchprofil showSuchprofil = new ShowSuchprofil();
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showSuchprofil);
							}

						});

			}
		});
	}
}
