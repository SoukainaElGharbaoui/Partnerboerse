
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
import de.hdm.gruppe7.partnerboerse.client.CreateInfo;

/**
 * Diese Klasse dient dazu, ein Nutzerprofil anzulegen.
 */
public class CreateNutzerprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Vertikales Panel erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Nutzerprofil anlegen:");
	private FlexTable createNutzerprofilFlexTable = new FlexTable();
	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();;
	private DateBox geburtsdatumDateBox = new DateBox();
	private Label geburtsdatumInhalt = new Label();

	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
	private Label infoLabel = new Label();
	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label reqLabel4 = new Label("* Pflichtfeld");
	private Label warnungLabel = new Label();
	
	/**
	 * Variable fuer den Profiltyp erstellen.
	 */
	private String profiltyp; 

	/**
	 * Konstruktor erstellen.
	 * @param profiltyp Der Profiltyp (Nutzerprofil).
	 */
	public CreateNutzerprofil(String profiltyp) {
		this.profiltyp = profiltyp; 
		run(); 
	}
	
	/**
	 * Methode erstellen, die den Aufbau der Seite startet. 
	 */
	public void run() {
		this.add(verPanel);

		/**
		 * CSS anwenden und Tabelle formatieren.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		reqLabel1.setStyleName("red_label");
		reqLabel2.setStyleName("red_label");
		reqLabel3.setStyleName("red_label");
		reqLabel4.setStyleName("red_label");
		warnungLabel.setStyleName("red_label");
		createNutzerprofilFlexTable.addStyleName("FlexTable");
		createNutzerprofilFlexTable.setCellPadding(6);
		createNutzerprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");

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
		createNutzerprofilFlexTable.setText(8, 0, "E-Mail");

		/**
		 * Zweite und dritte Spalte der Tabelle festlegen. 
		 * Die Widgets werden in die Tabelle eingefuegt und die Items fuer die ListBoxen werden gesetzt. 
		 */
		createNutzerprofilFlexTable.setWidget(0, 2, vornameTextBox);
		createNutzerprofilFlexTable.setWidget(0, 3, reqLabel1);

		createNutzerprofilFlexTable.setWidget(1, 2, nachnameTextBox);
		createNutzerprofilFlexTable.setWidget(1, 3, reqLabel2);

		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createNutzerprofilFlexTable.setWidget(2, 2, geschlechtListBox);

		geburtsdatumDateBox.setFormat(new DateBox.DefaultFormat(geburtsdatumFormat));
		geburtsdatumDateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
		geburtsdatumDateBox.getDatePicker().setVisibleYearCount(20);

		geburtsdatumDateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date geburtsdatum = event.getValue();
				String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
				geburtsdatumInhalt.setText(geburtsdatumString);

				if (event.getValue().after(today())) {
					geburtsdatumDateBox.setValue(today(), false);
				}
			}
		});
		
		String todayString = DateTimeFormat.getFormat("dd.MM.yyyy").format(today());
		geburtsdatumInhalt.setText(todayString);
		geburtsdatumDateBox.setValue(new Date());
		createNutzerprofilFlexTable.setWidget(3, 2, geburtsdatumDateBox);
		createNutzerprofilFlexTable.setWidget(3, 3, reqLabel4);

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

		religionListBox.addItem("Ohne Bekenntnis");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createNutzerprofilFlexTable.setWidget(7, 2, religionListBox);

		createNutzerprofilFlexTable.setText(8, 2, nutzerprofil.getEmailAddress());

		/**
		 * ClickHandler fuer den Button zum Anlegen eines Nutzerprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, werden die Eingaben sowohl auf 
		 * Vollstaendigkeit als auch auf Korrektheit geprueft. Sind die Eingaben 
		 * unvollstaendig oder inkorrekt, wird eine entsprechende Fehlermeldung 
		 * ueber diesen Zustand ausgegeben. Andernfalls wird das Nutzerprofil 
		 * angelegt. Anschliessend wird die Seite zum Anlegen der Infos aufgerufen.
		 */
		createNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				pruefeEingabe(); 
			}
		});
			
		/**
		 * Widgets dem Panel hinzufuegen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(createNutzerprofilFlexTable);
		verPanel.add(createNutzerprofilButton);
		verPanel.add(infoLabel);
	}
	
	/**
	 * Methode erstellen, die die Eingabe des Nutzers auf Vollstaendigkeit und Korrektheit ueberprueft. 
	 */
	public void pruefeEingabe() {
		boolean vornameWert = isBuchstabe(vornameTextBox.getText());
		boolean nachnameWert = isBuchstabe(nachnameTextBox.getText());
		boolean koerpergroesseWert = isZahl(koerpergroesseTextBox.getText());

		if (vornameTextBox.getText().length() == 0) {
			warnungLabel.setText("Bitte geben Sie Ihren Vornamen an.");
			createNutzerprofilFlexTable.setWidget(0, 4, warnungLabel);
		} else if (nachnameTextBox.getText().length() == 0) {
			warnungLabel.setText("Bitte geben Sie Ihren Nachnamen an.");
			createNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
		} else if (vornameWert == false) {
			warnungLabel.setText("Ihr Vorname darf nur Buchstaben enthalten.");
			createNutzerprofilFlexTable.setWidget(0, 4, warnungLabel);
		} else if (nachnameWert == false) {
			warnungLabel.setText("Ihr Nachname darf nur Buchstaben enthalten.");
			createNutzerprofilFlexTable.setWidget(1, 4, warnungLabel);
		} else if (geburtsdatumDateBox.getValue() == null) {
			warnungLabel.setText("Bitte geben Sie Ihr Geburtsdatum an.");
			createNutzerprofilFlexTable.setWidget(3, 4, warnungLabel);
		} else if (koerpergroesseTextBox.getText().length() == 0) {
			warnungLabel.setText("Bitte geben Sie Ihre Körpergröße an.");
			createNutzerprofilFlexTable.setWidget(4, 4, warnungLabel);
		} else if (koerpergroesseWert == false) {
			warnungLabel.setText("Ihre Körpergröße darf nur Zahlen enthalten.");
			createNutzerprofilFlexTable.setWidget(4, 4, warnungLabel);
		} else {
			nutzerprofilAnlegen(); 
		}
	}
	
	/**
	 * Methode erstellen, die ein neues Nutzerprofil anlegt. Dies fürt zum Speichern des Nutzerprofils
	 * in der Datenbank. 
	 */
	public void nutzerprofilAnlegen() {
		
		ClientsideSettings.getPartnerboerseAdministration().createNutzerprofil(vornameTextBox.getText(),
				nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), getGeburtsdatum(),
				Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
				raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(),
				nutzerprofil.getEmailAddress(), new AsyncCallback<Nutzerprofil>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf");
					}

					public void onSuccess(Nutzerprofil result) {
					//	ClientsideSettings.setAktuellerUser(result);

						CreateInfo createInfo = new CreateInfo(result.getProfilId(), profiltyp);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(createInfo);
					}
				});
		
	}

	/**
	 * Methode erstellen, die das Geburtsdatum formatiert.
	 */
	Date getGeburtsdatum() {
		Date geburtsdatum = geburtsdatumFormat.parse(geburtsdatumInhalt.getText());
		java.sql.Date sqlDate = new java.sql.Date(geburtsdatum.getTime());
		return sqlDate;
	}

	/**
	 * Methode erstellen, die das aktuelle Datum ermittelt. 
	 * @return Aktuelles Datum.
	 */
	private static Date today() {
		return zeroTime(new Date());
	}

	/**
	 * Methode erstellen, die das aktuelle Datum formatiert. 
	 * @param date Das Datum, das formatiert werden soll.
	 * @return Formatiertes Datum.
	 */
	private static Date zeroTime(final Date date) {
		return DateTimeFormat.getFormat("yyyyMMdd").parse(DateTimeFormat.getFormat("yyyyMMdd").format(date));
	}

	/**
	 * Methode erstellen, die ueberprueft, ob nur Buchstaben eingegeben wurden.
	 * @param name Der String, der ueberprueft wird. 
	 * @return Boolscher Wert, der angibt, ob es sich um Buchstaben handelt.
	 */
	public boolean isBuchstabe(String name) {
		return name.matches("^[a-zA-ZäöüÄÖÜß ]+$");
	}

	/**
	 * Methode erstellen, die ueberprueft, ob nur Zahlen eingegeben wurden.
	 * @param name Der String, der ueberprueft wird. 
	 * @return Boolscher Wert, der angibt, ob es sich um Zahlen handelt.
	 */
	public boolean isZahl(String name) {
		return name.matches("[0-9]+");
	}
}