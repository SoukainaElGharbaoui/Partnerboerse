package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class EditSuchprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanel hinzufuegen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Label fuer die Ueberschrift hinzufuegen.
	 */
	private Label ueberschriftLabel = new Label("Suchprofil bearbeiten:");

	/**
	 * Tabelle zur Anzeige und zur Bearbeitung des aktuellen Suchprofils
	 * hinzufuegen.
	 */
	private FlexTable editSuchprofilFlexTable = new FlexTable();

	/**
	 * TextBoxen und ListBoxen hinzufuegen.
	 */
	private TextBox suchprofilNameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	private TextBox alterMinTextBox = new TextBox();
	private TextBox alterMaxTextBox = new TextBox();
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();

	/**
	 * Button zum Speichern der Aenderungen hinzufuegen.
	 */
	private Button saveSuchprofilButton = new Button("Suchprofil speichern");

	/**
	 * Labels zur Benutzerinformation hinzufuegen.
	 */
	private Label infoLabel = new Label();
	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label reqLabel4 = new Label("* Pflichtfeld");
	private Label warnungLabel = new Label();
	
	private boolean alterVonWert; 
	private boolean alterBisWert; 
	private boolean koerpergroesseWert;

	/**
	 * Konstruktor hinzufuegen.
	 */
	public EditSuchprofil(final String suchprofilName) {
		this.add(verPanel);

		/**
		 * CSS auf Ueberschrift anwenden.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		reqLabel1.setStyleName("red_label");
		reqLabel2.setStyleName("red_label");
		reqLabel3.setStyleName("red_label");
		reqLabel4.setStyleName("red_label");
		warnungLabel.setStyleName("red_label");

		/**
		 * CSS auf Tabelle anwenden und Tabelle formatieren.
		 */
		editSuchprofilFlexTable.setCellPadding(6);
		editSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		editSuchprofilFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		editSuchprofilFlexTable.setText(0, 0, "Suchprofil-Id");
		editSuchprofilFlexTable.setText(1, 0, "Suchprofilname");
		editSuchprofilFlexTable.setText(2, 0, "Geschlecht");
		editSuchprofilFlexTable.setText(3, 0, "Alter von");
		editSuchprofilFlexTable.setText(4, 0, "Alter bis");
		editSuchprofilFlexTable.setText(5, 0, "Körpergröße");
		editSuchprofilFlexTable.setText(6, 0, "Haarfarbe");
		editSuchprofilFlexTable.setText(7, 0, "Raucher");
		editSuchprofilFlexTable.setText(8, 0, "Religion");

		/**
		 * Dritte Spalte der Tabelle festlegen (TextBox/ListBox zur Bearbeitung
		 * der Werte).
		 */
		editSuchprofilFlexTable.setWidget(1, 2, suchprofilNameTextBox);
		editSuchprofilFlexTable.setWidget(1, 3, reqLabel1);

		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editSuchprofilFlexTable.setWidget(2, 2, geschlechtListBox);

		editSuchprofilFlexTable.setWidget(3, 2, alterMinTextBox);
		editSuchprofilFlexTable.setWidget(3, 3, reqLabel2);

		editSuchprofilFlexTable.setWidget(4, 2, alterMaxTextBox);
		editSuchprofilFlexTable.setWidget(4, 3, reqLabel3);

		editSuchprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);
		editSuchprofilFlexTable.setWidget(5, 3, reqLabel4);

		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editSuchprofilFlexTable.setWidget(6, 2, haarfarbeListBox);

		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editSuchprofilFlexTable.setWidget(7, 2, raucherListBox);

		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editSuchprofilFlexTable.setWidget(8, 2, religionListBox);

		/**
		 * Daten des Suchprofils in die Tabelle einfuegen.
		 */
		ClientsideSettings.getPartnerboerseAdministration()
				.getSuchprofilByName(nutzerprofil.getProfilId(), suchprofilName,
						new AsyncCallback<Suchprofil>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Suchprofil result) {
						// Suchprofil-ID auslesen und einfuegen.
						final String suchprofilId = String.valueOf(result.getProfilId());
						editSuchprofilFlexTable.setText(0, 2, suchprofilId);

						// Suchprofilname auslesen und einfuegen.
						suchprofilNameTextBox.setText(result.getSuchprofilName());

						// Geschlecht auslesen und einfügen.
						for (int i = 0; i < geschlechtListBox.getItemCount(); i++) {
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
						for (int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) {
								haarfarbeListBox.setSelectedIndex(i);
							}
						}

						// Raucherstatus auslesen und einfügen.
						for (int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher().equals(raucherListBox.getValue(i))) {
								raucherListBox.setSelectedIndex(i);
							}
						}

						// Religion auslesen und einfügen.
						for (int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion().equals(religionListBox.getValue(i))) {
								religionListBox.setSelectedIndex(i);
							}
						}

					}

				});

		/**
		 * ClickHandler fuer den Suchprofil-Bearbeiten-Button hinzufuegen.
		 */
		saveSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Suchprofilname ueberpruefen.
				ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilnameEdit(nutzerprofil.getProfilId(),
						Integer.parseInt(editSuchprofilFlexTable.getText(0, 2)), suchprofilNameTextBox.getText(),
						new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Integer result) {
								
								alterVonWert = isZahl(alterMinTextBox.getText()); 
								alterBisWert = isZahl(alterMaxTextBox.getText()); 
								koerpergroesseWert = isZahl(koerpergroesseTextBox.getText()); 
								
								if (result == 1) {
									warnungLabel.setText("Der Suchprofilname existiert bereits");
									editSuchprofilFlexTable.setWidget(1, 4, warnungLabel);
								} else if (result == 2) {
									warnungLabel.setText("Bitte geben Sie einen Suchprofilnamen an.");
									editSuchprofilFlexTable.setWidget(1, 4, warnungLabel);
								} else if (alterMinTextBox.getText().length() == 0){
									warnungLabel.setText("Bitte geben Sie 'Alter von' an.");
									editSuchprofilFlexTable.setWidget(3, 4, warnungLabel);
								} else if (alterVonWert == false) {
									warnungLabel.setText("'Alter von' darf nur Zahlen enthalten.");
									editSuchprofilFlexTable.setWidget(3, 4, warnungLabel);
								} else if (alterMaxTextBox.getText().length() == 0){
									warnungLabel.setText("Bitte geben Sie 'Alter bis' an.");
									editSuchprofilFlexTable.setWidget(4, 4, warnungLabel);
								} else if (alterBisWert == false) {
									warnungLabel.setText("'Alter bis' darf nur Zahlen enthalten.");
									editSuchprofilFlexTable.setWidget(4, 4, warnungLabel);	
								} else if (Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())){
									warnungLabel.setText("'Alter von' muss kleiner als 'Alter bis' sein.");
									editSuchprofilFlexTable.setWidget(3, 4, warnungLabel);
								} else if (koerpergroesseTextBox.getText().length() == 0) {
									warnungLabel.setText("Bitte geben Sie eine Körpergröße an.");
									editSuchprofilFlexTable.setWidget(5, 4, warnungLabel);
								} else if (koerpergroesseWert == false) {
									warnungLabel.setText("Ihre Körpergröße darf nur Zahlen enthalten.");
									editSuchprofilFlexTable.setWidget(5, 4, warnungLabel);
								} else {
									
									// Suchprofil
									// aktualisieren.
									ClientsideSettings.getPartnerboerseAdministration()
									.saveSuchprofil(nutzerprofil.getProfilId(),
									Integer.parseInt(editSuchprofilFlexTable.getText(0, 2)),
									suchprofilNameTextBox.getText(),
									geschlechtListBox.getSelectedItemText(),
									Integer.parseInt(alterMinTextBox.getText()),
									Integer.parseInt(alterMaxTextBox.getText()),
									Integer.parseInt(
									koerpergroesseTextBox.getText()),
									haarfarbeListBox.getSelectedItemText(),
									raucherListBox.getSelectedItemText(),
									religionListBox.getSelectedItemText(),
									new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
									infoLabel.setText(
									"Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Void result) {
									String suchprofilName = suchprofilNameTextBox.getText();
									ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilName);
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showSuchprofil);
					}

				});

													
												
			}
			}

									
								
							

			});

			}
		});

		/**
		 * Widgets zum VerticalPanel hinzufügen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(editSuchprofilFlexTable);
		verPanel.add(saveSuchprofilButton);
		verPanel.add(infoLabel);
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
