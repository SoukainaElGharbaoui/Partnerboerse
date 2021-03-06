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

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

/**
 * Diese Klasse dient dazu, ein Suchprofil zu bearbeiten.
 */
public class EditSuchprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Panels erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private HorizontalPanel ankerPanel = new HorizontalPanel();
	private Label ueberschriftLabel = new Label("Suchprofil bearbeiten:");
	private FlexTable editSuchprofilFlexTable = new FlexTable();
	private TextBox suchprofilNameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	private TextBox alterMinTextBox = new TextBox();
	private TextBox alterMaxTextBox = new TextBox();
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private Button saveSuchprofilButton = new Button("Suchprofil speichern");
	private Button abbrechenButton = new Button("Abbrechen");
	private Label infoLabel = new Label();
	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label reqLabel4 = new Label("* Pflichtfeld");
	private Label warnungLabel = new Label();
	private Label pfadLabelSp = new Label("Zurück zu: Suchprofile anzeigen");

	/**
	 * Variable fuer die Suchprofilid erstellen.
	 */
	private int suchprofilId;

	/**
	 * Variable fuer den Suchprofilnamen erstellen.
	 */
	private String suchprofilName;

	/**
	 * Variable fuer den Profiltyp erstellen.
	 */
	private String profiltyp;

	/**
	 * Konstruktor erstellen.
	 * 
	 * @param suchprofilName
	 *            Der Name des Suchprofils, das editiert werden soll.
	 * @param profiltyp
	 *            Der Profiltyp (Suchprofil).
	 */
	public EditSuchprofil(final String suchprofilName, final String profiltyp) {
		this.suchprofilName = suchprofilName;
		this.profiltyp = profiltyp;
		run();
	}

	/**
	 * Methode erstellen, die den Aufbau der Seite startet.
	 */
	public void run() {
		this.add(ankerPanel);
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
		editSuchprofilFlexTable.addStyleName("FlexTable");
		pfadLabelSp.addStyleName("partnerboerse-zurueckbutton");
		editSuchprofilFlexTable.setCellPadding(6);
		editSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		editSuchprofilFlexTable.setText(0, 0, "Suchprofilname");
		editSuchprofilFlexTable.setText(1, 0, "Geschlecht");
		editSuchprofilFlexTable.setText(2, 0, "Alter von");
		editSuchprofilFlexTable.setText(3, 0, "Alter bis");
		editSuchprofilFlexTable.setText(4, 0, "Körpergröße in cm");
		editSuchprofilFlexTable.setText(5, 0, "Haarfarbe");
		editSuchprofilFlexTable.setText(6, 0, "Raucher");
		editSuchprofilFlexTable.setText(7, 0, "Religion");

		/**
		 * Zweite und Dritte Spalte der Tabelle festlegen. Die Widgets werden in
		 * die Tabelle eingefuegt und die Items fuer die ListBoxen werden
		 * gesetzt.
		 */
		editSuchprofilFlexTable.setWidget(0, 2, suchprofilNameTextBox);
		editSuchprofilFlexTable.setWidget(0, 3, reqLabel1);

		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);

		editSuchprofilFlexTable.setWidget(2, 2, alterMinTextBox);
		editSuchprofilFlexTable.setWidget(2, 3, reqLabel2);

		editSuchprofilFlexTable.setWidget(3, 2, alterMaxTextBox);
		editSuchprofilFlexTable.setWidget(3, 3, reqLabel3);

		editSuchprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);
		editSuchprofilFlexTable.setWidget(4, 3, reqLabel4);

		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editSuchprofilFlexTable.setWidget(5, 2, haarfarbeListBox);

		raucherListBox.addItem("Keine Auswahl");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editSuchprofilFlexTable.setWidget(6, 2, raucherListBox);

		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Ohne Bekenntnis");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editSuchprofilFlexTable.setWidget(7, 2, religionListBox);

		getSuchprofil();

		/**
		 * ClickHandler fuer den Button zum Speichern eines Suchprofils
		 * erzeugen. Sobald dieser Button betaetigt wird, werden die Eingaben
		 * auf Vollstaendigkeit, auf Korrektheit und auf die Existenz des
		 * Suchprofilnamens ueberprueft. Sind Eingaben unvollstaendig oder
		 * inkorrekt, wird eine entsprechende Information ueber diesen Zustand
		 * ausgegeben. Andernfalls wird das Suchprofil aktualisiert. Daraufhin
		 * wird die Seite zum Anzeigen der Suchprofile aufgerufen.
		 */
		saveSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				pruefeSuchprofil();
			}
		});

		/**
		 * ClickHandler fuer den Button zum Abbrechen des Bearbeitens eines
		 * Suchprofils erzeugen. Sobald dieser Button betaetigt wird, wird die
		 * Seite zum Anzeigen der Suchprofile aufgerufen.
		 */
		abbrechenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSuchprofil);
			}
		});

		/**
		 * ClickHandler fuer das Label erzeugen, mit dessen Hilfe zur Seite zur
		 * Anzeige der Suchprofile zurueckgekehrt werden kann.
		 */
		pfadLabelSp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ShowSuchprofil showSp = new ShowSuchprofil(suchprofilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSp);
			}

		});

		/**
		 * Widgets den Panels hinzufuegen.
		 */
		verPanel.add(pfadLabelSp);
		verPanel.add(ueberschriftLabel);
		verPanel.add(editSuchprofilFlexTable);
		buttonPanel.add(saveSuchprofilButton);
		buttonPanel.add(abbrechenButton);
		verPanel.add(buttonPanel);
		verPanel.add(infoLabel);
	}

	/**
	 * Suchprofil anhand der Profil-ID auslesen und die Suchprofildaten in die
	 * Tabelle einfuegen.
	 */
	public void getSuchprofil() {
		ClientsideSettings.getPartnerboerseAdministration().getSuchprofilByName(nutzerprofil.getProfilId(),
				suchprofilName, new AsyncCallback<Suchprofil>() {

					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Suchprofil result) {
						suchprofilId = result.getProfilId();

						suchprofilNameTextBox.setText(result.getSuchprofilName());

						for (int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) {
								geschlechtListBox.setSelectedIndex(i);
							}
						}

						alterMinTextBox.setText(Integer.toString(result.getAlterMinInt()));

						alterMaxTextBox.setText(Integer.toString(result.getAlterMaxInt()));

						koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));

						for (int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) {
								haarfarbeListBox.setSelectedIndex(i);
							}
						}

						for (int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher().equals(raucherListBox.getValue(i))) {
								raucherListBox.setSelectedIndex(i);
							}
						}

						for (int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion().equals(religionListBox.getValue(i))) {
								religionListBox.setSelectedIndex(i);
							}
						}

					}

				});

	}

	/**
	 * Methode erstellen, die die Eingaben auf Vollstaendigkeit, Korrektheit und
	 * auf die Existenz des Suchprofilnamens ueberprueft.
	 */
	public void pruefeSuchprofil() {
		ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilnameEdit(nutzerprofil.getProfilId(),
				suchprofilId, suchprofilNameTextBox.getText(), new AsyncCallback<Integer>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Integer result) {

						boolean alterVonWert = isZahl(alterMinTextBox.getText());
						boolean alterBisWert = isZahl(alterMaxTextBox.getText());
						boolean koerpergroesseWert = isZahl(koerpergroesseTextBox.getText());

						if (result == 1) {
							warnungLabel.setText("Der Suchprofilname existiert bereits");
							editSuchprofilFlexTable.setWidget(0, 4, warnungLabel);
						} else if (result == 2) {
							warnungLabel.setText("Bitte geben Sie einen Suchprofilnamen an.");
							editSuchprofilFlexTable.setWidget(0, 4, warnungLabel);
						} else if (alterMinTextBox.getText().length() == 0) {
							warnungLabel.setText("Bitte geben Sie 'Alter von' an.");
							editSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
						} else if (alterVonWert == false) {
							warnungLabel.setText("'Alter von' darf nur Zahlen enthalten.");
							editSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
						} else if (alterMaxTextBox.getText().length() == 0) {
							warnungLabel.setText("Bitte geben Sie 'Alter bis' an.");
							editSuchprofilFlexTable.setWidget(3, 4, warnungLabel);
						} else if (alterBisWert == false) {
							warnungLabel.setText("'Alter bis' darf nur Zahlen enthalten.");
							editSuchprofilFlexTable.setWidget(3, 4, warnungLabel);
						} else if (Integer.parseInt(alterMinTextBox.getText()) > Integer
								.parseInt(alterMaxTextBox.getText())) {
							warnungLabel.setText("'Alter von' muss kleiner als 'Alter bis' sein.");
							editSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
						} else if (koerpergroesseTextBox.getText().length() == 0) {
							warnungLabel.setText("Bitte geben Sie eine K�rpergr��e an.");
							editSuchprofilFlexTable.setWidget(4, 4, warnungLabel);
						} else if (koerpergroesseWert == false) {
							warnungLabel.setText("Ihre K�rpergr��e darf nur Zahlen enthalten.");
							editSuchprofilFlexTable.setWidget(4, 4, warnungLabel);
						} else {
							suchprofilSpeichern();
						}
					}
				});

	}

	/**
	 * Methode erstellen, die das Suchprofil aktualisiert. Dies führt zum
	 * wiederholten Schreiben des Suchprofils in die Datenbank.
	 */
	public void suchprofilSpeichern() {
		ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(nutzerprofil.getProfilId(), suchprofilId,
				suchprofilNameTextBox.getText(), geschlechtListBox.getSelectedItemText(),
				Integer.parseInt(alterMinTextBox.getText()), Integer.parseInt(alterMaxTextBox.getText()),
				Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
				raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(), new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Void result) {
						ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilId, profiltyp);
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(showSuchprofil);
					}
				});
	}

	/**
	 * Methode erstellen, die ueberprueft, ob nur Zahlen eingegeben wurden.
	 * 
	 * @param name
	 *            Der String, der ueberprueft wird.
	 * @return Boolscher Wert, der angibt, ob es sich um eine Zahl handelt.
	 */
	public boolean isZahl(String name) {
		return name.matches("[0-9]+");
	}
}
