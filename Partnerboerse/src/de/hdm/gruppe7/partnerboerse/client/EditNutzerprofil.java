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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;

public class EditNutzerprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt erzeugen.
	 */
	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
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
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private Label emailLabel = new Label();

	private Label infoLabel = new Label();
	private Label reqLabel1 = new Label("* Pflichtfeld"); 
	private Label reqLabel2 = new Label("* Pflichtfeld"); 
	private Label warnungLabel = new Label(); 

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
		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(
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
					warnungLabel.setText("Bitte geben Sie Ihren Vornamen ein");
					editNutzerprofilFlexTable.setWidget(1, 4, warnungLabel); 
				// Wenn kein Nachname angegeben wird...
				} else if (nachnameTextBox.getText().length() == 0) {
						warnungLabel.setText("Bitte geben Sie Ihren Nachnamen ein"); 
						editNutzerprofilFlexTable.setWidget(2, 4, warnungLabel); 
				} else {

				/**
				 * Nutzerprofil aktualisieren.
				 */
				ClientsideSettings.getPartnerboerseAdministration().saveNutzerprofil(vornameTextBox.getText(),
						nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), getGeburtsdatum(),
						Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
						raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(),
						new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf");
							}

							public void onSuccess(Void result) {
								// Seite zum Anzeigen des eigenen Nutzerprofils aufrufen.
								ShowEigenesNp showEigenesNp = new ShowEigenesNp(nutzerprofil);
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showEigenesNp);

							}
						});
				}

				/**
				 * Aehnlichkeit aus der Datenbank entfernen.
				 */
				ClientsideSettings.getPartnerboerseAdministration().aehnlichkeitEntfernenSp(

						new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
							}

							public void onSuccess(Void result) {
							}

						});

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