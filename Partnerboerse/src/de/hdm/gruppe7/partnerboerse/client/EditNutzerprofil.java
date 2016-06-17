package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class EditNutzerprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Panel hinzufuegen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Widgets hinzufuegen.
	 */
	private Label ueberschriftLabel = new Label("Nutzerprofil bearbeiten:");

	private FlexTable editNutzerprofilFlexTable = new FlexTable();

	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	private DateBox geburtsdatumDateBox = new DateBox();
	private Label geburtsdatumInhalt = new Label();
	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	private IntegerBox koerpergroesseTextBox = new IntegerBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private Label emailLabel = new Label();

	private Label infoLabel = new Label();

	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label warnungLabel = new Label();
	CharSequence zahl = "0";
	CharSequence zahl1 = "1";
	CharSequence zahl2 = "2";
	CharSequence zahl3 = "3";
	CharSequence zahl4 = "4";
	CharSequence zahl5 = "5";
	CharSequence zahl6 = "6";
	CharSequence zahl7 = "7";
	CharSequence zahl8 = "8";
	CharSequence zahl9 = "9";
	CharSequence alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜabcdefghijklmnopqrstuvwxyzäöü";

	private Button editNutzerprofilButton = new Button("Nutzerprofil bearbeiten");

	/**
	 * Konstruktor hinzufuegen.
	 */
	public EditNutzerprofil() {
		this.add(verPanel);

		/**
		 * CSS anwenden.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		reqLabel1.setStyleName("red_label");
		reqLabel2.setStyleName("red_label");
		reqLabel3.setStyleName("red_label");
		warnungLabel.setStyleName("red_label");

		/**
		 * Tabelle formatieren.
		 */
		editNutzerprofilFlexTable.setCellPadding(6);
		editNutzerprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		editNutzerprofilFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		editNutzerprofilFlexTable.setText(0, 0, "Nutzerrofil-Id");
		editNutzerprofilFlexTable.setText(1, 0, "Vorname");
		editNutzerprofilFlexTable.setText(2, 0, "Nachname");
		editNutzerprofilFlexTable.setText(3, 0, "Geschlecht");
		editNutzerprofilFlexTable.setText(4, 0, "Geburtsdatum");
		editNutzerprofilFlexTable.setText(5, 0, "Körpergröße");
		editNutzerprofilFlexTable.setText(6, 0, "Haarfarbe");
		editNutzerprofilFlexTable.setText(7, 0, "Raucherstatus");
		editNutzerprofilFlexTable.setText(8, 0, "Religion");
		editNutzerprofilFlexTable.setText(9, 0, "E-Mail");

		/**
		 * Zweite und Dritte Spalte der Tabelle festlegen.
		 */
		editNutzerprofilFlexTable.setWidget(1, 2, vornameTextBox);
		editNutzerprofilFlexTable.setWidget(1, 3, reqLabel1);

		editNutzerprofilFlexTable.setWidget(2, 2, nachnameTextBox);
		editNutzerprofilFlexTable.setWidget(2, 3, reqLabel2);

		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editNutzerprofilFlexTable.setWidget(3, 2, geschlechtListBox);

		geburtsdatumDateBox.setFormat(new DateBox.DefaultFormat(geburtsdatumFormat));
		geburtsdatumDateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
		geburtsdatumDateBox.getDatePicker().setVisibleYearCount(20);

		geburtsdatumDateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date geburtsdatum = event.getValue();
				String geburtsdatumString = DateTimeFormat.getFormat("yyyy-MM-dd").format(geburtsdatum);
				geburtsdatumInhalt.setText(geburtsdatumString);
			}
		});

		geburtsdatumDateBox.setValue(new Date());
		editNutzerprofilFlexTable.setWidget(4, 2, geburtsdatumDateBox);

		editNutzerprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);
		editNutzerprofilFlexTable.setWidget(5, 3, reqLabel3);

		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editNutzerprofilFlexTable.setWidget(6, 2, haarfarbeListBox);

		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editNutzerprofilFlexTable.setWidget(7, 2, raucherListBox);

		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editNutzerprofilFlexTable.setWidget(8, 2, religionListBox);

		editNutzerprofilFlexTable.setWidget(9, 2, emailLabel);

		/**
		 * Nutzerprofil auslesen.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(nutzerprofil.getProfilId(),
				new AsyncCallback<Nutzerprofil>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Nutzerprofil result) {

						vornameTextBox.setText(result.getVorname());

						nachnameTextBox.setText(result.getNachname());

						for (int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) {
								geschlechtListBox.setSelectedIndex(i);
							}
						}

						geburtsdatumDateBox.setValue(result.getGeburtsdatumDate());

						koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));

						for (int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) {
								haarfarbeListBox.setSelectedIndex(i);
							}
						}

						for (int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion().equals(religionListBox.getValue(i))) {
								religionListBox.setSelectedIndex(i);
							}
						}

						for (int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher().equals(raucherListBox.getValue(i))) {
								raucherListBox.setSelectedIndex(i);
							}
						}

						emailLabel.setText(result.getEmailAddress());
					}
				});

		/**
		 * ClickHandler für den Nutzerprofil-Bearbeiten-Button hinzufuegen.
		 */
		editNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Wenn kein Vorname angegeben wird...
				if (vornameTextBox.getText().length() == 0) {

					warnungLabel.setText("Bitte geben Sie Ihren Vornamen an.");
					editNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
					// Wenn kein Nachname angegeben wird...

				} else if (nachnameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Nachnamen an.");
					editNutzerprofilFlexTable.setWidget(2, 4, warnungLabel);
				} //Wenn der Vorname Zahlen enthält...
				else if(vornameTextBox.getText().contains(zahl)||
		        		vornameTextBox.getText().contains(zahl1)||
		        		vornameTextBox.getText().contains(zahl2)||
		        		vornameTextBox.getText().contains(zahl3)||
		        		vornameTextBox.getText().contains(zahl4)||
		        		vornameTextBox.getText().contains(zahl5)||
		        		vornameTextBox.getText().contains(zahl6)||
		        		vornameTextBox.getText().contains(zahl7)||
		        		vornameTextBox.getText().contains(zahl8)||
		        		vornameTextBox.getText().contains(zahl9)){
				warnungLabel.setText("Ihr Name darf keine Zahlen enthalten");
				editNutzerprofilFlexTable.setWidget(0, 4, warnungLabel);
				}
		        //Wenn der Nachname Zahlen enthält...
				else if(nachnameTextBox.getText().contains(zahl)||
		        		nachnameTextBox.getText().contains(zahl1)||
		        		nachnameTextBox.getText().contains(zahl2)||
		        		nachnameTextBox.getText().contains(zahl3)||
		        		nachnameTextBox.getText().contains(zahl4)||
		        		nachnameTextBox.getText().contains(zahl5)||
		        		nachnameTextBox.getText().contains(zahl6)||
		        		nachnameTextBox.getText().contains(zahl7)||
		        		nachnameTextBox.getText().contains(zahl8)||
		        		nachnameTextBox.getText().contains(zahl9)){
		        	warnungLabel.setText("Ihr Name darf keine Zahlen enthalten");
		        	editNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
		        }	
				// Wenn keine Koerpergroesse angegeben wird...
				else if (koerpergroesseTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihre Körpergröße an.");
					editNutzerprofilFlexTable.setWidget(5, 4, warnungLabel);

				} else if(koerpergroesseTextBox.getText().contains(alphabet)){
					warnungLabel.setText("Ihre Eingabe darf nur Zahlen enthalten");
					editNutzerprofilFlexTable.setWidget(5, 5, warnungLabel);
				}
				else {

				/**
				 * Nutzerprofil aktualisieren.
				 */
				ClientsideSettings.getPartnerboerseAdministration().saveNutzerprofil(
						nutzerprofil.getProfilId(), vornameTextBox.getText(),
						nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), getGeburtsdatum(),
						Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
						raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(),
						new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf");
							}

							public void onSuccess(Void result) {
								// Seite zum Anzeigen des eigenen Nutzerprofils aufrufen.
								ShowEigenesNp showEigenesNp = new ShowEigenesNp();
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showEigenesNp);
							}
					});

				}

			}
		});

		/**
		 * Widgets zum Panel hinzufuegen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(editNutzerprofilFlexTable);
		verPanel.add(editNutzerprofilButton);
		verPanel.add(infoLabel);
	}

	/**
	 * Geburtsdatum ermitteln.
	 */
	Date getGeburtsdatum() {
		Date geburtsdatum = geburtsdatumFormat.parse(geburtsdatumInhalt.getText());
		java.sql.Date sqlDate = new java.sql.Date(geburtsdatum.getTime());
		return sqlDate;
	}
}
