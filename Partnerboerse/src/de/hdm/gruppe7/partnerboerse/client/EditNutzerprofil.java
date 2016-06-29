
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Diese Klasse dient dazu, ein Nutzerprofil zu bearbeiten. 
 */
public class EditNutzerprofil extends VerticalPanel {

	/**
	 * Panels erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Profil bearbeiten:");
	private FlexTable editNutzerprofilFlexTable = new FlexTable();
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
	private Button editNutzerprofilButton = new Button("Profil speichern");
	private Button abbrechenButton = new Button("Abbrechen");
	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label reqLabel4 = new Label("* Pflichtfeld");
	private Label infoLabel = new Label();
	private Label warnungLabel = new Label();
	private Label pfadLabelNpA = new Label("Zurück zu: Profil anzeigen");
	
	/**
	 * Variable fuer die Profil-ID erzeugen. 
	 */
	private int profilId; 
	
	/**
	 * Variable fuer den Profiltyp erzeugen. 
	 */
	private String profiltyp; 

	/**
	 * Konstruktor erstellen.
	 * @param profilId Die Profil-ID des aktuellen Nutzerprofils.  
	 * @param profiltyp Der Profiltyp (Nutzerprofil). 
	 */
	public EditNutzerprofil(final int profilId, String profiltyp) {
		this.profilId = profilId; 
		this.profiltyp = profiltyp; 
		run(); 
	}
	
	/**
	 * Methode erstellen, die den Aufbau der Seite startet. 
	 */
	public void run() {
		this.add(verPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		reqLabel1.setStyleName("red_label");
		reqLabel2.setStyleName("red_label");
		reqLabel3.setStyleName("red_label");
		reqLabel4.setStyleName("red_label");
		warnungLabel.setStyleName("red_label");
		editNutzerprofilFlexTable.addStyleName("FlexTable");
		editNutzerprofilFlexTable.setCellPadding(6);
		editNutzerprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		pfadLabelNpA.addStyleName("partnerboerse-zurueckbutton");
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		editNutzerprofilFlexTable.setText(0, 0, "Vorname");
		editNutzerprofilFlexTable.setText(1, 0, "Nachname");
		editNutzerprofilFlexTable.setText(2, 0, "Geschlecht");
		editNutzerprofilFlexTable.setText(3, 0, "Geburtsdatum");
		editNutzerprofilFlexTable.setText(4, 0, "Körpergröße in cm");
		editNutzerprofilFlexTable.setText(5, 0, "Haarfarbe");
		editNutzerprofilFlexTable.setText(6, 0, "Raucherstatus");
		editNutzerprofilFlexTable.setText(7, 0, "Religion");
		editNutzerprofilFlexTable.setText(8, 0, "E-Mail");

		/**
		 * Zweite und Dritte Spalte der Tabelle festlegen.
		 * Die Widgets werden in die Tabelle eingefuegt und die Items fuer die ListBoxen werden gesetzt. 
		 */
		editNutzerprofilFlexTable.setWidget(0, 1, vornameTextBox);
		editNutzerprofilFlexTable.setWidget(0, 2, reqLabel1);

		editNutzerprofilFlexTable.setWidget(1, 1, nachnameTextBox);
		editNutzerprofilFlexTable.setWidget(1, 2, reqLabel2);

		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editNutzerprofilFlexTable.setWidget(2, 1, geschlechtListBox);

		geburtsdatumDateBox.setFormat(new DateBox.DefaultFormat(geburtsdatumFormat));
		geburtsdatumDateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
		geburtsdatumDateBox.getDatePicker().setVisibleYearCount(20);
		geburtsdatumDateBox.setValue(new Date());
		
		
		
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
		
		editNutzerprofilFlexTable.setWidget(3, 1, geburtsdatumDateBox);
		editNutzerprofilFlexTable.setWidget(3, 2, reqLabel3);

		editNutzerprofilFlexTable.setWidget(4, 1, koerpergroesseTextBox);
		editNutzerprofilFlexTable.setWidget(4, 2, reqLabel4);

		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editNutzerprofilFlexTable.setWidget(5, 1, haarfarbeListBox);

		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editNutzerprofilFlexTable.setWidget(6, 1, raucherListBox);

		religionListBox.addItem("Ohne Bekenntnis");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editNutzerprofilFlexTable.setWidget(7, 1, religionListBox);

		editNutzerprofilFlexTable.setWidget(8, 1, emailLabel);

		befuelleTabelle(); 
		
		/**
		 * ClickHandler fuer den Button zum Speichern des eigenen Nutzerprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, werden die Eingaben sowohl auf 
		 * Vollstaendigkeit als auch auf Korrektheit ueberprueft. Sind Eingaben
		 * unvollstaendig oder inkorrekt, wird eine entsprechende Information 
		 * ueber diesen Zustand ausgegeben. Andernfalls wird das Nutzerprofil 
		 * gespeichert. Anschliessend wird die Seite zum Anzeigen des eigenen 
		 * Nutzeprofils aufgerufen.
		 */
		editNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				pruefeEingabe(); 
			}
		}); 

		/**
		 * ClickHandler fuer den Button zum Abbrechen des Bearbeitens eines Nutzerprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird die Seite zum Anzeigen des eigenen Nutzerprofils
		 * aufgerufen.
		 */
		abbrechenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ShowNutzerprofil showNutzerprofil = new ShowNutzerprofil(profilId, profiltyp); 
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showNutzerprofil);
			}
		}); 
		
		/**
		 * ClickHandler fuer das Label zum Zurueckkehren zum Nutzerprofil erzeugen. 
		 * Sobald dieses Label betaetigt wird, wird die Seite zum Anzeigen des eigenen 
		 * Nutzerprofils aufgerufen.
		 */
		pfadLabelNpA.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showNp);
			}

		});

		/**
		 * Widgets den Panels hinzufuegen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(editNutzerprofilFlexTable);
		buttonPanel.add(editNutzerprofilButton);
		buttonPanel.add(abbrechenButton);
		verPanel.add(buttonPanel);
		verPanel.add(infoLabel);
	}
	
	/**
	 * Methode erstellen, die das eigene Nutzerprofil ausliest und die Profildaten in die 
	 * Tabelle eintraegt. 
	 */
	public void befuelleTabelle() {
		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(profilId,
				new AsyncCallback<Nutzerprofil>() {

					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Nutzerprofil result) {

						vornameTextBox.setText(result.getVorname());

						nachnameTextBox.setText(result.getNachname());

						for (int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) {
								geschlechtListBox.setSelectedIndex(i);
							}
						}
						
						Date geburtsdatum = result.getGeburtsdatumDate(); 
						String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
						
						geburtsdatumDateBox.setValue(geburtsdatum);
						
						geburtsdatumInhalt.setText(geburtsdatumString);
						
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
		 * Widgets dem Panel hinzufuegen.
		 */
		verPanel.add(pfadLabelNpA);
		verPanel.add(ueberschriftLabel);
		verPanel.add(editNutzerprofilFlexTable);
		verPanel.add(editNutzerprofilButton);
		verPanel.add(infoLabel);
	}
	
	/**
	 * Methode erstellen, die die Eingaben auf Vollstaendigkeit und Korrektheit ueberprueft.
	 */
	public void pruefeEingabe() {
		boolean vornameWert = isBuchstabe(vornameTextBox.getText());
		boolean nachnameWert = isBuchstabe(nachnameTextBox.getText());
		boolean koerpergroesseWert = isZahl(koerpergroesseTextBox.getText());

		if (vornameTextBox.getText().length() == 0) {
			warnungLabel.setText("Bitte geben Sie Ihren Vornamen an.");
			editNutzerprofilFlexTable.setWidget(0, 3, warnungLabel);
		} else if (nachnameTextBox.getText().length() == 0) {
			warnungLabel.setText("Bitte geben Sie Ihren Nachnamen an.");
			editNutzerprofilFlexTable.setWidget(1, 3, warnungLabel);
		} else if (vornameWert == false) {
			warnungLabel.setText("Ihr Vorname darf keine Zahlen enthalten.");
			editNutzerprofilFlexTable.setWidget(0, 3, warnungLabel);
		} else if (nachnameWert == false) {
			warnungLabel.setText("Ihr Nachname darf keine Zahlen enthalten.");
			editNutzerprofilFlexTable.setWidget(1, 3, warnungLabel);
		} else if (geburtsdatumDateBox.getValue() == null) {
			warnungLabel.setText("Bitte geben Sie Ihr Geburtsdatum an.");
			editNutzerprofilFlexTable.setWidget(3, 3, warnungLabel);
		} else if (koerpergroesseTextBox.getText().length() == 0) {
			warnungLabel.setText("Bitte geben Sie Ihre Körpergröße an.");
			editNutzerprofilFlexTable.setWidget(4, 3, warnungLabel);
		} else if (koerpergroesseWert == false) {
			warnungLabel.setText("Ihre Körpergröße darf nur Zahlen enthalten.");
			editNutzerprofilFlexTable.setWidget(4, 3, warnungLabel);
		} else {
			aktualisiereNutzerprofil(); 
		}
	}
	
	/**
	 * Methode erstellen, die das eigene Nutzerprofil aktualisiert. Dies führt zum wiederholten 
	 * Schreiben des Nutzerprofils in die Datenbank.
	 */
	public void aktualisiereNutzerprofil() {
		ClientsideSettings.getPartnerboerseAdministration().saveNutzerprofil(
				profilId, vornameTextBox.getText(),
				nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), getGeburtsdatum(),
				Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
				raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(),
				new AsyncCallback<Void>() {
			

						public void onFailure(Throwable caught) {
						}

						public void onSuccess(Void result) {
						ShowNutzerprofil showNutzerprofil = new ShowNutzerprofil(profilId, profiltyp);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(showNutzerprofil);
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
	 * @param Aktuelles Geburtsdatum.
	 * @return Aktuelles, formatiertes Datum.
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
