
package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.regex.Pattern;



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
	private Label ueberschriftLabel = new Label("Nutzerprofil anlegen:");
	private FlexTable createNutzerprofilFlexTable = new FlexTable();
	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();;

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
	final Label warnungLabel1 = new Label();
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
		createNutzerprofilFlexTable.setWidget(4, 3, reqLabel3);

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

		createNutzerprofilFlexTable.setText(8, 2, nutzerprofil.getEmailAddress());

		/**
		 * ClickHandler fuer den Nutzerprofil-Anlegen-Button hinzufuegen.
		 */
		createNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				Pattern letterPattern = Pattern.compile("^[a-zA-Z]+$");
//
//				if (!(letterPattern.matcher(vornameTextBox.getText()).matches()))
//				      {
//					warnungLabel.setText("Blub");
//					createNutzerprofilFlexTable.setWidget(0, 5, warnungLabel);
//				}
				
				

				// Wenn kein Vorname angegeben wird...
				if (vornameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Vornamen an.");
					createNutzerprofilFlexTable.setWidget(0, 4, warnungLabel);

					// Wenn kein Nachname angegeben wird...
				} else if (nachnameTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihren Nachnamen an.");
					createNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
					
				// Wenn keine Koerpergroesse angegeben wird...	
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
				createNutzerprofilFlexTable.setWidget(0, 4, warnungLabel);
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
		        	createNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
		        }
				
				
				else if(geburtsdatumInhalt.getText().length() == 0){
					warnungLabel.setText("Bitte geben Sie Ihr Geburtsdatum an");
					createNutzerprofilFlexTable.setWidget(3, 3, warnungLabel);
				}
				else if (koerpergroesseTextBox.getText().length() == 0) {
					warnungLabel.setText("Bitte geben Sie Ihre Körpergröße an.");
					createNutzerprofilFlexTable.setWidget(4, 4, warnungLabel);
					
				} else {
					/**
					 * Nutzerprofil anlegen.
					 */
					ClientsideSettings.getPartnerboerseAdministration().createNutzerprofil(vornameTextBox.getText(),
							nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), getGeburtsdatum(),
							Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
							raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(),
							nutzerprofil.getEmailAddress(), new AsyncCallback<Nutzerprofil>() {

										public void onFailure(Throwable caught) {
											infoLabel.setText("Es trat ein Fehler auf");
										}
											
										public void onSuccess(Nutzerprofil result) {
											infoLabel.setText("Ihr Nutzerprofil wurde erfolgreich angelegt");
											
											int profilId = result.getProfilId();
											
											CreateInfoNp createInfoNp = new CreateInfoNp(profilId);
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
