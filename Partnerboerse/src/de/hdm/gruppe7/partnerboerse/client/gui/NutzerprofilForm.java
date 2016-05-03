package de.hdm.gruppe7.partnerboerse.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;



/**
 * Formular fÃ¼r die Darstellung des selektierten Nutzerprofils
 */
public class NutzerprofilForm {
	PartnerboerseAdministrationAsync partnerboerseAdministration = ClientsideSettings.getPartnerboerseAdministration();
	
	Nutzerprofil nutzerprofilToDisplay = null;
	//CustomerAccountsTreeViewModel catvm = null; Alles mit catvm brauchen wir nur wenn wir NutzerprofilTreeViewModel verwenden
	//NumberFormat decimalFormatter = NumberFormat.getDecimalFormat();
	
	
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	Label profilIdValueLabel = new Label();
    Label balanceValueLabel = new Label();
	TextBox vornameTextBox = new TextBox();
	TextBox nachnameTextBox = new TextBox();
	//TextBox geburtstdatumBox = new TextBox();
	ListBox geschlechtListBox = new ListBox(); 
	ListBox raucherListBox = new ListBox();
	TextBox koerpergroesseTextBox = new TextBox();
	ListBox haarfarbeListBox = new ListBox();
	ListBox religionListBox = new ListBox();
	
	
	public NutzerprofilForm () {
		/**
		 * Das Grid-Widget erlaubt die Anordnung anderer Widgets in einem Gitter.
		 */
		Grid nutzerprofilGrid = new Grid(2, 6);
//		this.add(nutzerprofilGrid);
		
		Label nameLabel = new Label("Name");
		nutzerprofilGrid.setWidget(0, 0, nameLabel);
		nutzerprofilGrid.setWidget(1, 0,  vornameTextBox);
		nutzerprofilGrid.setWidget(1, 0,  nachnameTextBox);
		
//		Label geburtsdatumLabel = new Label("Geburtsdatum");
//		nutzerprofilGrid.setWidget(0, 1, geburtsdatumLabel);
//		nutzerprofilGrid.setWidget(1, 0,  geburtsdatumTextBox);
		
		Label geschlechtLabel = new Label("Geschlecht");
		nutzerprofilGrid.setWidget(0, 2, geschlechtLabel);
		nutzerprofilGrid.setWidget(1, 0,  geschlechtListBox);
		
		Label raucherLabel = new Label("Raucher?");
		nutzerprofilGrid.setWidget(0, 3, raucherLabel);
		nutzerprofilGrid.setWidget(1, 0,  raucherListBox);
		
		Label koerpergroesseLabel = new Label("Koerpergroesse");
		nutzerprofilGrid.setWidget(0, 4, koerpergroesseLabel);
		nutzerprofilGrid.setWidget(1, 0, koerpergroesseTextBox);
		
		Label haarfarbeLabel = new Label("Haarfarbe");
		nutzerprofilGrid.setWidget(0, 5, haarfarbeLabel);
		nutzerprofilGrid.setWidget(1, 0,  haarfarbeListBox);
		
		Label religionLabel = new Label("Konfession");
		nutzerprofilGrid.setWidget(0, 6, religionLabel);
		nutzerprofilGrid.setWidget(1, 0,  religionListBox);
		
		HorizontalPanel nutzerprofilButtonsPanel = new HorizontalPanel();
//		this.add(nutzerprofilButtonsPanel);
		

		Button changeButton = new Button("Ändern");
		changeButton.addClickHandler(new ChangeClickHandler());
		nutzerprofilButtonsPanel.add(changeButton);

		Button searchButton = new Button("Suchen");
		nutzerprofilButtonsPanel.add(searchButton);

		Button deleteButton = new Button("Löschen");
		deleteButton.addClickHandler(new DeleteClickHandler());
		nutzerprofilButtonsPanel.add(deleteButton);

		Button newButton = new Button("Neu");
		newButton.addClickHandler(new NewClickHandler());
		nutzerprofilButtonsPanel.add(newButton);
		
		
		
		
	}
	
	/**
	 * Die Änderung eines Kunden bezieht sich auf seinen Vor- und/oder
	 * Nachnamen. Es erfolgt der Aufruf der Service-Methode "save".
	 * 
	 */
	
	private class ChangeClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			if (nutzerprofilToDisplay != null) {
				nutzerprofilToDisplay.setVorname(vornameTextBox.getText());
				nutzerprofilToDisplay.setNachname(nachnameTextBox.getText());
				partnerboerseVerwaltung.save(nutzerprofilToDisplay, new SaveCallback());
			} else {
				Window.alert("kein Nutzerprofil ausgewählt");
			}
		}
	}
	
	private class SaveCallback implements AsyncCallback<Void> {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Änderung ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {
			// Die Änderung wird zum Kunden- und Kontenbaum propagiert.
			//catvm.updateNutzerprofil(nutzerprofilToDisplay);
		}
	}
	
	/**
	 * Das erfolgreiche Löschen eines Kunden führt zur Aktualisierung des
	 * Kunden- und Kontenbaumes.
	 * 
	 */
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (nutzerprofilToDisplay == null) {
				Window.alert("kein Kunde ausgewählt");
			} else {
				partnerboerseVerwaltung.delete(nutzerprofilToDisplay,
						new deleteNutzerprofilCallback(nutzerprofilToDisplay));
			}
		}
	}
	
	class deleteNutzerprofilCallback implements AsyncCallback<Void> {

		Nutzerprofil nutzerprofil = null;

		deleteNutzerprofilCallback(Nutzerprofil c) {
			nutzerprofil  = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Löschvorgang ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {
			if (nutzerprofil  != null) {
				setSelected(null);
				//catvm.removeCustomer(nutzerprofil);
			}
		}
	}
	
	/**
	 * Auch wenn ein neuer Kunde hinzukommt, muss der Kunden- und Kontenbaum
	 * aktualisiert werden.
	 * 
	 */
	
	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String vorname = vornameTextBox.getText();
			String nachname = nachnameTextBox.getText();
			//partnerboerseVerwaltung.createNutzerprofil(vorname, nachname, // wird in partnerboerseAdministrationAsync festgelegt
//					new CreateNutzerprofilCallback());
		}
	}
	
	class CreateNutzerprofilCallback implements AsyncCallback<Nutzerprofil> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Das Anlegen ist fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Nutzerprofil nutzerprofil) {
			if (nutzerprofil != null) {
				// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
				// Kontenbaum propagiert.
				//catvm.addNutzerprofil(nutzerprofil);
			}
		}
	}
	
	// catvm setter
//		void setCatvm(CustomerAccountsTreeViewModel catvm) {
//			this.catvm = catvm;
//		}
		
		/*
		 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
		 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
		 * gefüllt bzw. gelöscht.
		 */
		void setSelected(Nutzerprofil nutzerprofil) {
			if (nutzerprofil != null) {
				nutzerprofilToDisplay = nutzerprofil;
				vornameTextBox.setText(nutzerprofilToDisplay.getVorname());
				nachnameTextBox.setText(nutzerprofilToDisplay.getNachname());
				
			} else {
				vornameTextBox.setText("");
				nachnameTextBox.setText("");
				
			}
		}
	
	
	
}
