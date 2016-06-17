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

import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

	/**
	 * VerticalPanel hinzufuegen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/**
	 * Label fuer das Pflichtfeld.
	 */
	private Label reqLabel = new Label("* Pflichtfeld");
	
	/**
	 * Label fuer die Ueberschrift hinzufuegen.
	 */
	private Label ueberschriftLabel = new Label("Suchprofil anlegen:");

	/**
	 * Tabelle zum Anlegen des Suchprofils erstellen.
	 */
	private FlexTable createSuchprofilFlexTable = new FlexTable();

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
	 * Button zum Anlegen der Aenderungen hinzufuegen.
	 */
	private Button createSuchprofilButton = new Button("Suchprofil anlegen");

	/**
	 * Label für die Benutzerinformation hinzufuegen.
	 */
	private Label infoLabel = new Label();
	private Label warnungLabel = new Label();

	/**
	 * Konstruktor hinzufuegen.
	 */
	public CreateSuchprofil() {
		this.add(verPanel);

		/**
		 * CSS auf Ueberschrift und Pflichtfeld anwenden.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label"); 
		reqLabel.setStyleName("red_label");
		warnungLabel.setStyleName("red_label");

		/**
		 * CSS auf Tabelle anwenden und Tabelle formatieren.
		 */
		createSuchprofilFlexTable.setCellPadding(6);
		createSuchprofilFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		createSuchprofilFlexTable.addStyleName("FlexTable");

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
		 * Zweite Spalte der Tabelle festlegen (TextBox/ListBox zur Angabe der
		 * Werte).
		 */
		createSuchprofilFlexTable.setWidget(0, 2, suchprofilNameTextBox);
		createSuchprofilFlexTable.setWidget(0, 3, reqLabel);

		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);

		createSuchprofilFlexTable.setWidget(2, 2, alterMinTextBox);

		createSuchprofilFlexTable.setWidget(3, 2, alterMaxTextBox);

		createSuchprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);

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
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createSuchprofilFlexTable.setWidget(7, 2, religionListBox);

		/**
		 * ClickHandler für den Suchprofil-Anlegen-Button hinzufügen.
		 */
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				// Suchprofilname ueberpruefen. 
				ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilnameCreate(suchprofilNameTextBox.getText(), 
						new AsyncCallback<Integer>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Integer result) {
								
								if(alterMinTextBox.getText().length()==0 && alterMaxTextBox.getText().length()==0){
									warnungLabel.setText("Bitte geben Sie einen Altersrahmen für Ihr Suchprofil an");
									createSuchprofilFlexTable.setWidget(2, 4, warnungLabel);
								}
								if(koerpergroesseTextBox.getText().length()==0){
									warnungLabel.setText("Bitte geben Sie eine Körpergröße an");
									createSuchprofilFlexTable.setWidget(4, 4, warnungLabel);
								}
								// Wenn der Suchprofilname bereits existiert...
								if(result == 1) {
									warnungLabel.setText("Der Suchprofilname existiert bereits");	
									createSuchprofilFlexTable.setWidget(0, 4, warnungLabel); 
								} else {
									// Wenn der Suchprofilname leer ist...
									if (result == 2) {
										warnungLabel.setText("Der Suchprofilname darf nicht leer sein."); 
										createSuchprofilFlexTable.setWidget(0, 4, warnungLabel); 
									} else {
										// Wenn Alter von groesser als Alter bis ist...
										if (Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
											warnungLabel.setText("'Alter von' muss kleiner als 'Alter bis' sein.");
											createSuchprofilFlexTable.setWidget(2, 4, warnungLabel); 
									} else {
										// Suchprofil anlegen.
										 ClientsideSettings.getPartnerboerseAdministration().createSuchprofil(
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
											 // Suchprofil-ID ermitteln und an CreateInfoSp weitergeben.
											 int suchprofilId = result.getProfilId();
											 CreateInfoSp createInfoSp = new CreateInfoSp(suchprofilId);
											 RootPanel.get("Details").clear();
											 RootPanel.get("Details").add(createInfoSp);
										 }
										
										 });
										
									}
								}
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

}
