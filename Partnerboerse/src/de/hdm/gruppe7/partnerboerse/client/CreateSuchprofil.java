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

/**
 * Diese Klasse dient dazu, ein Suchprofil zu erstellen. 
 */
public class CreateSuchprofil extends VerticalPanel {
	
	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt, erzeugen. 
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Vertikales Panel erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Suchprofil anlegen:");
	private FlexTable createSuchprofilFlexTable = new FlexTable();
	private TextBox suchprofilNameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	private TextBox alterMinTextBox = new TextBox();
	private TextBox alterMaxTextBox = new TextBox();
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private Button createSuchprofilButton = new Button("Suchprofil anlegen");
	private Label reqLabel1 = new Label("* Pflichtfeld");
	private Label reqLabel2 = new Label("* Pflichtfeld");
	private Label reqLabel3 = new Label("* Pflichtfeld");
	private Label reqLabel4 = new Label("* Pflichtfeld");
	private Label infoLabel = new Label();
	private Label warnungLabel = new Label();
	
	/**
	 * Konstruktor erstellen. 
	 * @param profiltyp Der Profiltyp (Suchprofil).
	 */
	public CreateSuchprofil(final String profiltyp) {
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
		createSuchprofilFlexTable.addStyleName("FlexTable");
		createSuchprofilFlexTable.setCellPadding(6);
		createSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		createSuchprofilFlexTable.setText(0, 0, "Suchprofilname");
		createSuchprofilFlexTable.setText(1, 0, "Geschlecht");
		createSuchprofilFlexTable.setText(2, 0, "Alter von");
		createSuchprofilFlexTable.setText(3, 0, "Alter bis");
		createSuchprofilFlexTable.setText(4, 0, "Körpergröße");
		createSuchprofilFlexTable.setText(5, 0, "Haarfarbe");
		createSuchprofilFlexTable.setText(6, 0, "Raucher");
		createSuchprofilFlexTable.setText(7, 0, "Religion");

		/**
		 * Zweite Spalte der Tabelle festlegen.
		 * Hierzu werden die Widgets in die Tabelle eingefuegt und 
		 * die Items fuer die ListBoxen festgelegt. 
		 */
		createSuchprofilFlexTable.setWidget(0, 2, suchprofilNameTextBox);
		createSuchprofilFlexTable.setWidget(0, 3, reqLabel1);

		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);

		createSuchprofilFlexTable.setWidget(2, 2, alterMinTextBox);
		createSuchprofilFlexTable.setWidget(2, 3, reqLabel2);

		createSuchprofilFlexTable.setWidget(3, 2, alterMaxTextBox);
		createSuchprofilFlexTable.setWidget(3, 3, reqLabel3);

		createSuchprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);
		createSuchprofilFlexTable.setWidget(4, 3, reqLabel4);

		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		createSuchprofilFlexTable.setWidget(5, 2, haarfarbeListBox);

		raucherListBox.addItem("Keine Auswahl");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		createSuchprofilFlexTable.setWidget(6, 2, raucherListBox);

		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Ohne Bekenntnis"); 
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createSuchprofilFlexTable.setWidget(7, 2, religionListBox);
		
		/**
		 * ClickHandler fuer den Button zum Anlegen eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, werden die Eingaben sowohl auf 
		 * Vollstaendigkeit als auch auf Korrektheit ueberprueft. Sind Eingaben
		 * unvollstaendig oder inkorrekt, wird eine entsprechende Information 
		 * ueber diesen Zustand ausgegeben. 
		 */
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilnameCreate(nutzerprofil.getProfilId(),
						suchprofilNameTextBox.getText(), new AsyncCallback<Integer>() {
					
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Integer result) {
								
								boolean alterVonWert = isZahl(alterMinTextBox.getText()); 
								boolean alterBisWert = isZahl(alterMaxTextBox.getText()); 
								boolean koerpergroesseWert = isZahl(koerpergroesseTextBox.getText()); 
								
								if (result == 1) {
									warnungLabel.setText("Der Suchprofilname existiert bereits");
									createSuchprofilFlexTable.setWidget(0, 4, warnungLabel);
								} else if (result == 2) {
									warnungLabel.setText("Bitte geben Sie einen Suchprofilnamen an.");
									createSuchprofilFlexTable.setWidget(0, 4, warnungLabel);
								} else if (alterMinTextBox.getText().length() == 0){
									warnungLabel.setText("Bitte geben Sie 'Alter von' an.");
									createSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
								} else if (alterVonWert == false) {
									warnungLabel.setText("'Alter von' darf nur Zahlen enthalten.");
									createSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
								} else if (alterMaxTextBox.getText().length() == 0){
									warnungLabel.setText("Bitte geben Sie 'Alter bis' an.");
									createSuchprofilFlexTable.setWidget(3, 4, warnungLabel);
								} else if (alterBisWert == false) {
									warnungLabel.setText("'Alter bis' darf nur Zahlen enthalten.");
									createSuchprofilFlexTable.setWidget(3, 4, warnungLabel);	
								} else if (Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())){
									warnungLabel.setText("'Alter von' muss kleiner als 'Alter bis' sein.");
									createSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
								} else if (koerpergroesseTextBox.getText().length() == 0) {
									warnungLabel.setText("Bitte geben Sie eine Körpergröße an.");
									createSuchprofilFlexTable.setWidget(4, 4, warnungLabel);
								} else if (koerpergroesseWert == false) {
									warnungLabel.setText("Ihre Körpergröße darf nur Zahlen enthalten.");
									createSuchprofilFlexTable.setWidget(4, 4, warnungLabel);
								} else {
								
									/**
									 * Sind alle Eingaben vollstaendig und korrekt, wird das Nutzerprofil in die Datenbank eingefuegt.
									 * Anschließend wird die Seite zum Anlegen der Suchprofil-Infos aufgerufen.  
									 */
									ClientsideSettings.getPartnerboerseAdministration()
									.createSuchprofil(nutzerprofil.getProfilId(),
									suchprofilNameTextBox.getText(),
									geschlechtListBox.getSelectedItemText(),
									Integer.parseInt(alterMinTextBox.getText()),
									Integer.parseInt(alterMaxTextBox.getText()),
									Integer.parseInt(koerpergroesseTextBox.getText()),
									haarfarbeListBox.getSelectedItemText(),
									raucherListBox.getSelectedItemText(),
									religionListBox.getSelectedItemText(),
									new AsyncCallback<Suchprofil>() {


									@Override
									public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Suchprofil result) {
									int suchprofilId = result.getProfilId();
									CreateInfo createInfo = new CreateInfo(suchprofilId, profiltyp);
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(createInfo);
								}

							});

						}
					}

		         });

		      }

		});

		/**
		 * Widgets zum VerticalPanel hinzufuegen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(createSuchprofilFlexTable);
		verPanel.add(createSuchprofilButton);
		verPanel.add(infoLabel);

	}
	
	/**
	 * Methode erstellen, die ueberprueft, ob nur Zahlen eingegeben wurden. 
	 * @param name Der String, der ueberprueft wird. 
	 * @return Boolscher Wert, der angibt, ob es sich um eine Zahl handelt. 
	 */
	public boolean isZahl(String name) {
	    return name.matches("[0-9]+");
	}

}
