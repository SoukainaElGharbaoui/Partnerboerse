
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

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.client.CreateInfoNp;

public class CreateNutzerprofil extends VerticalPanel {
	
	/**
	 * Panel hinzufuegen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Widgets hinzufuegen.
	 */
	private Label ueberschriftLabel = new Label("Nutzerprofil anlegen:");
	private FlexTable createNutzerprofilFlexTable = new FlexTable();
	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	private TextBox emailTextBox = new TextBox();

	private DateBox geburtsdatumDateBox = new DateBox();
	private Label geburtsdatumInhalt = new Label();
	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("yyyy-MM-dd");

	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	
	private Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
	
	private Label infoLabel = new Label();
	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	final Label warnungLabel = new Label();

	/**
	 * Konstruktor hinzufuegen.
	 */
	public CreateNutzerprofil() {
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
		createNutzerprofilFlexTable.setCellPadding(6);
		createNutzerprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		createNutzerprofilFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		createNutzerprofilFlexTable.setText(0, 0, "Vorname");
		createNutzerprofilFlexTable.setText(1, 0, "Nachname");
		createNutzerprofilFlexTable.setText(2, 0, "Geschlecht");
		createNutzerprofilFlexTable.setText(3, 0, "Geburtsdatum");
		createNutzerprofilFlexTable.setText(4, 0, "Körpergröße");
		createNutzerprofilFlexTable.setText(5, 0, "Haarfarbe");
		createNutzerprofilFlexTable.setText(6, 0, "Raucherstatus");
		createNutzerprofilFlexTable.setText(7, 0, "Religion");
		createNutzerprofilFlexTable.setText(8, 0, "EMail");

		/**
		 * Zweite und dritte Spalte der Tabelle festlegen.
		 */
		createNutzerprofilFlexTable.setWidget(0, 2, vornameTextBox);
		createNutzerprofilFlexTable.setWidget(0, 3, reqLabel1);

		createNutzerprofilFlexTable.setWidget(1, 2, nachnameTextBox);
		createNutzerprofilFlexTable.setWidget(1, 3, reqLabel2);

		geschlechtListBox.addItem("nicht binär");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createNutzerprofilFlexTable.setWidget(2, 2, geschlechtListBox);

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
		createNutzerprofilFlexTable.setWidget(3, 2, geburtsdatumDateBox);

		createNutzerprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);

		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		haarfarbeListBox.addItem("Andere");
		createNutzerprofilFlexTable.setWidget(5, 2, haarfarbeListBox);

		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		createNutzerprofilFlexTable.setWidget(6, 2, raucherListBox);

		religionListBox.addItem("ohne Bekenntnis");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createNutzerprofilFlexTable.setWidget(7, 2, religionListBox);

		createNutzerprofilFlexTable.setWidget(8, 2, emailTextBox);
		
		/**
		 * ClickHandler fuer den Nutzerprofil-Anlegen-Button hinzufuegen.
		 */
		createNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Wenn kein Vorname angegeben wird...
				if (vornameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Vornamen ein");
					createNutzerprofilFlexTable.setWidget(0, 4, warnungLabel);

				// Wenn kein Nachname angegeben wird...
				} else if (nachnameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Nachnamen ein");
					createNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);

				} else {
					/**
					 * Nutzerprofil anlegen.
					 */
					ClientsideSettings.getPartnerboerseAdministration()
							.createNutzerprofil(
									vornameTextBox.getText(),
									nachnameTextBox.getText(),
									geschlechtListBox.getSelectedItemText(),
									getGeburtsdatum(),
									Integer.parseInt(koerpergroesseTextBox
											.getText()),
									haarfarbeListBox.getSelectedItemText(),
									raucherListBox.getSelectedItemText(),
									religionListBox.getSelectedItemText(),
									emailTextBox.getText(),
									new AsyncCallback<Nutzerprofil>() {

										public void onFailure(Throwable caught) {
											infoLabel.setText("Es trat ein Fehler auf");
										}
											
										public void onSuccess(Nutzerprofil result) {
											infoLabel.setText("Ihr Nutzerprofil wurde erfolgreich angelegt");
											
											CreateInfoNp createInfoNp = new CreateInfoNp();
											RootPanel.get("Details").clear();
											RootPanel.get("Details").add(createInfoNp);
										}
								});
						}
				}
		});
					
		/**
		 * Widgets zum Panel hinzufuegen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(createNutzerprofilFlexTable);
		verPanel.add(createNutzerprofilButton);
		verPanel.add(infoLabel);

	}
	
	/**
	 * Geburtsdatum erstellen.
	 */
	Date getGeburtsdatum() {
		Date geburtsdatum = geburtsdatumFormat.parse(geburtsdatumInhalt.getText());
		java.sql.Date sqlDate = new java.sql.Date(geburtsdatum.getTime());
		return sqlDate;
	}

}
