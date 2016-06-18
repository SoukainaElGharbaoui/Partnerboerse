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
	private Label ueberschriftLabel = new Label("Profil bearbeiten:");

	private FlexTable editNutzerprofilFlexTable = new FlexTable();

	private Label nutzerprofilIdLabel = new Label();
	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	private DateBox geburtsdatumDateBox = new DateBox();
	private Label geburtsdatumInhalt = new Label();

	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private Label emailLabel = new Label();

	private Label infoLabel = new Label();

	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label reqLabel4 = new Label("* Pflichtfeld");
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

	private Button editNutzerprofilButton = new Button("Profil speichern");

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
		reqLabel4.setStyleName("red_label");
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
		editNutzerprofilFlexTable.setWidget(0, 1, nutzerprofilIdLabel);
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
				String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
				geburtsdatumInhalt.setText(geburtsdatumString);
			}
		});

		geburtsdatumDateBox.setValue(new Date());
		editNutzerprofilFlexTable.setWidget(4, 2, geburtsdatumDateBox);
		editNutzerprofilFlexTable.setWidget(4, 3, reqLabel3);

		editNutzerprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);
		editNutzerprofilFlexTable.setWidget(5, 3, reqLabel4);

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
						
						nutzerprofilIdLabel.setText(String.valueOf(result.getProfilId()));

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

				boolean vornameWert = isBuchstabe(vornameTextBox.getText()); 
				boolean nachnameWert = isBuchstabe(nachnameTextBox.getText()); 
				boolean koerpergroesseWert = isZahl(koerpergroesseTextBox.getText()); 

				if (vornameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Vornamen an.");
					editNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
				} else if (nachnameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Nachnamen an.");
					editNutzerprofilFlexTable.setWidget(2, 4, warnungLabel);

				} else if (vornameWert == false){
					warnungLabel.setText("Ihr Vorname darf keine Zahlen enthalten.");
					editNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
				} else if (nachnameWert == false){
					warnungLabel.setText("Ihr Nachname darf keine Zahlen enthalten.");
					editNutzerprofilFlexTable.setWidget(2, 4, warnungLabel);
				} else if (geburtsdatumInhalt.getText().length() == 0){
					warnungLabel.setText("Bitte geben Sie Ihr Geburtsdatum an.");
					editNutzerprofilFlexTable.setWidget(4, 4, warnungLabel);
				} else if (koerpergroesseTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihre Körpergröße an.");
					editNutzerprofilFlexTable.setWidget(5, 4, warnungLabel);
				} else if (koerpergroesseWert == false){
					warnungLabel.setText("Ihre Körpergröße darf nur Zahlen enthalten.");
					editNutzerprofilFlexTable.setWidget(5, 4, warnungLabel);
				} else {

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
	
	/**
	 * Methode erstellen, die ueberprueft, ob nur Buchstaben eingegeben wurden.
	 */
	public boolean isBuchstabe(String name) {
	    return name.matches("[a-zA-Z]+");
	}
	
	/**
	 * Methode erstellen, die ueberprueft, ob nur Zahlen eingegeben wurden. 
	 */
	public boolean isZahl(String name) {
	    return name.matches("[0-9]+");
	}
}
