package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	/**
	 * VerticalPanel und HorizontalPanel hinzufügen.
	 */
	
	private HorizontalPanel horPanel = new HorizontalPanel();
	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();
	
	

	/**
	 * Konstruktor hinzuf�gen.
	 */
	public CreateSuchprofil() {
		this.add(horPanel);
		this.add(verPanel);
		this.add(verPanel2);
		
		horPanel.add(verPanel);
		horPanel.add(verPanel2);
	
		/**
		 * Label für Überschrift erstellen
		 */
		final Label ueberschriftLabel = new Label("Suchprofil anlegen:");
		ueberschriftLabel.addStyleName("partnerboerse-label"); 

		/**
		 * Tabelle zur Anzeige des eigenen Profils erstellen.
		 */
		final FlexTable createSuchprofilFlexTable = new FlexTable();
		
		final FlexTable showSuchprofilFlexTable= new FlexTable();
		
		
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
	final Label warnungLabel3 = new Label(); 

	final Button createSuchprofilButton = new Button(
			"Suchprofil anlegen");
	verPanel.add(createSuchprofilButton);

	/**
	 * infoLabel zum navPanel hinzuf�gen.
	 */
	verPanel.add(infoLabel);
	
	 /**
	  * ClickHandler für den Suchprofil-Anlegen-Button hinzufügen.
	  */
	createSuchprofilButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			// Prüfen, ob der Suchprofilname bereits existiert.
			ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilname(nutzerprofil.getProfilId(), suchprofilnameTextBox.getText(), 
					new AsyncCallback<Integer>() {

						public void onFailure(Throwable caught) {
							infoLabel.setText("Es trat ein Fehler auf."); 
						}

						public void onSuccess(Integer result) {
							int suchprofilnameVorhanden = 0; 
							if(result == 1) {
							suchprofilnameVorhanden = 1; 
							}
							
							// Wenn der Suchprofilname bereits existiert...
							if(suchprofilnameVorhanden == 1) {
//								Window.alert("Der Suchprofilname existiert bereits.");
								warnungLabel1.setText("Der Suchprofilname existiert bereits."); 
								verPanel.add(warnungLabel1);
								
							} else {
								
								// Prüfen, ob der Suchprofilname leer ist.
								if(suchprofilnameTextBox.getText().isEmpty()) {
//									Window.alert("Der Suchprofilname darf nicht leer sein.");
									warnungLabel2.setText("Der Suchprofilname darf nicht leer sein."); 
									verPanel.add(warnungLabel2);
									
								} else {
								
								// Prüfen, ob Alter von kleiner als Alter bis ist. 
								if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
//									Window.alert("'Alter von' muss kleiner als 'Alter bis' sein."); 
									warnungLabel3.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
									createSuchprofilFlexTable.setWidget(2, 3, warnungLabel1); 
									verPanel.add(warnungLabel3);
									
								} else {

									// Suchprofil anlegen.
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
//													ShowSuchprofil showSuchprofil = new ShowSuchprofil();
//													RootPanel.get("Details").clear();
//													RootPanel.get("Details").add(showSuchprofil);
												}

											});

									}
							}}

						}
				
			});
			
			ClientsideSettings.getPartnerboerseAdministration().
			getSuchprofilByName(suchprofilnameTextBox.getText(), new  AsyncCallback<Suchprofil>(){


				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Suchprofil result) {
					int suchprofilId = result.getProfilId();
					CreateInfoSp createInfoSp = new CreateInfoSp(
					suchprofilId);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(createInfoSp);
				}
				
				
			});
			
			

			
		}
		
	}); 


	
	
	

}
	
	
	
	
}
