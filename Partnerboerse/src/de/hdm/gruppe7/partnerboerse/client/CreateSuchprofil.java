package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

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

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzuf�gen.
	 */
	public CreateSuchprofil() {
		this.add(verPanel);
	
		/**
		 * Label für Überschrift erstellen
		 */
		final Label ueberschriftLabel = new Label("Suchprofil anlegen:");
		ueberschriftLabel.addStyleName("partnerboerse-label"); 

		/**
		 * Tabelle zur Anzeige des eigenen Profils erstellen.
		 */
		final FlexTable createSuchprofilFlexTable = new FlexTable();
		
		
		/**
		 * Tabelle formatieren und CSS einbinden.
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
		 * Erzeugen von Eingabefeldern fuer die zweite Spalte
		 */
		final Label editLabel = new Label();
		final TextBox suchprofilnameTextBox = new TextBox(); 
		createSuchprofilFlexTable.setWidget(0, 2, suchprofilnameTextBox); 

		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createSuchprofilFlexTable.setWidget(1, 2, geschlechtListBox);
		
		final TextBox alterMinTextBox = new TextBox();
		createSuchprofilFlexTable.setWidget(2, 2, alterMinTextBox);

		final TextBox alterMaxTextBox = new TextBox();
		createSuchprofilFlexTable.setWidget(3, 2, alterMaxTextBox);

		final TextBox koerpergroesseTextBox = new TextBox();
		createSuchprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);

		final ListBox haarfarbeListBox = new ListBox();
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		createSuchprofilFlexTable.setWidget(5, 2, haarfarbeListBox);

		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Keine Auswahl");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		createSuchprofilFlexTable.setWidget(6, 2, raucherListBox);

		final ListBox religionListBox = new ListBox();
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createSuchprofilFlexTable.setWidget(7, 2, religionListBox);
		
		/**
		 * Zum Panel hinzufï¿½gen
		 */

		verPanel.add(ueberschriftLabel);
		verPanel.add(createSuchprofilFlexTable);
		verPanel.add(editLabel);

	/**
	 * infoLabel für die Benutzerinformation erzeugen.
	 */
	final Label infoLabel = new Label();
	final Label warnungLabel1 = new Label(); 
	final Label warnungLabel2 = new Label(); 

	final Button createSuchprofilButton = new Button(
			"Suchprofil anlegen");
	verPanel.add(createSuchprofilButton);

	/**
	 * infoLabel zum navPanel hinzuf�gen.
	 */
	verPanel.add(infoLabel);

	createSuchprofilButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			// Prüfen, ob Suchprofilname für diesen Nutzer bereis existiert.
//			ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(Benutzer.getProfilId(),
//					new AsyncCallback<List<Suchprofil>>() {
//
//						@Override
//						public void onFailure(Throwable caught) {
//							infoLabel.setText("Es trat ein Fehler auf."); 
//							
//						}
//
//						@Override
//						public void onSuccess(List<Suchprofil> result) {
//							for(Suchprofil s : result) {
//								if(suchprofilnameTextBox.getText().equals(s.getSuchprofilName())) {
//									warnungLabel1.setText("Der Suchprofilname existiert bereits.");
//									verPanel.add(warnungLabel1); 	
//								} 		
//							}
//	
//								
//						}
//				
//			});
			
			// Prüfen, ob Alter von < Alter bis. 
			if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
				warnungLabel2.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
				verPanel.add(warnungLabel2);
			} else {

			ClientsideSettings.getPartnerboerseAdministration()
					.createSuchprofil(suchprofilnameTextBox.getText(), 
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
//									infoLabel.setText("Das Suchprofil '" + suchprofilnameTextBox.getText() + "' wurde erfolgreich angelegt.");
									ShowSuchprofil showSuchprofil = new ShowSuchprofil();
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showSuchprofil);
								}

							});

		}
		
	}
	});

}
	
	
	
	
}
	
