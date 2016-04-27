package de.hdm.gruppe7.partnerboerse.client;

import de.hdm.gruppe7.partnerboerse.shared.FieldVerifier;


import de.hdm.thies.bankProjekt.client.CreateAccountDemo;
import de.hdm.thies.bankProjekt.client.DeleteAccountDemo;
import de.hdm.thies.bankProjekt.client.Showcase;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Partnerboerse implements EntryPoint {

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */
	@Override
	public void onModuleLoad() {

		// Achtung: bei NutzerprofilForm ist partnerboerseVerwaltung drin,
		// brauchen wir evtl nicht

		/*
		 * Der Navigator ist als einspaltige Aneinanderreihung von Buttons
		 * realisiert. Daher bietet sich ein VerticalPanel als Container an.
		 */
		VerticalPanel navPanel = new VerticalPanel();

		/*
		 * Das VerticalPanel wird einem DIV-Element namens "Navigator" in der
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen
		 * Darstellungsort.
		 */
		RootPanel.get("Navigator").add(navPanel);

		/*
		 * Ab hier bauen wir sukzessive den Navigator mit seinen Buttons aus.
		 */

		/*
		 * Neues Button Widget erzeugen und eine Beschriftung festlegen.
		 */
		final Button findNutzerprofilButton = new Button("Finde Nutzerprofil");

		/*
		 * Unter welchem Namen können wir den Button durch die CSS-Datei des
		 * Projekts formatieren?
		 */
		findNutzerprofilButton.setStylePrimaryName("partnerboerse-menubutton");

		/*
		 * Hinzufügen des Buttons zum VerticalPanel.
		 */
		navPanel.add(findNutzerprofilButton);

		/*
		 * Natürlich benötigt der Button auch ein Verhalten, wenn man mit der
		 * Maus auf ihn klickt. Hierzu registrieren wir einen ClickHandler,
		 * dessen onClick()-Methode beim Mausklick auf den zugehörigen Button
		 * aufgerufen wird.
		 */
		findNutzerprofilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				 /*
		         * Showcase instantiieren.
		         */
		        Showcase showcase = new FindCustomersByNameDemo();
		        /*
		         * Für die Ausgaben haben wir ein separates DIV-Element namens "Details"
		         * in die zugehörige HTML-Datei eingefügt. Bevor wir den neuen Showcase
		         * dort einbetten, löschen wir vorsichtshalber sämtliche bisherigen
		         * Elemente dieses DIV.
		         */
		        RootPanel.get("Details").clear();
		        RootPanel.get("Details").add(showcase);
		    

			}

		});
		
		
		 /*
	     * Ab hier folgen weitere Button-Definitionen, die nach exakt der gleichen
	     * Methode erfolgen wie beim ersten Button.
	     * 
	     * Da das Muster dazu sich mehrfach wiederholt, könnte man hier schon von
	     * einem unerwünschte Code Clone sprechen. Um dies stilistisch zu optimieren
	     * wäre z.B. die Verwendung des Factory oder Builder Pattern denkbar. 
	     * Hierauf wurde jedoch bewusst verzichtet, um den Komplexitätsgrad dieses
	     * Demonstrators nicht unnötig zu erhöhen. 
	     */
	    final Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
	    createNutzerprofilButton.setStylePrimaryName("partnerboerse-menubutton");
	    navPanel.add(createNutzerprofilButton);

	    createNutzerprofilButton.addClickHandler(new ClickHandler() {
	      @Override
		public void onClick(ClickEvent event) {
	        Showcase showcase = new CreateAccountDemo();
	        RootPanel.get("Details").clear();
	        RootPanel.get("Details").add(showcase);
	      }
	    });
	    
	    
	 // Nächste Button-Definition
	    final Button deleteNutzerprofilButton = new Button("Nutzerprofil löschen");
	    deleteNutzerprofilButton.setStylePrimaryName("partnerboerse-menubutton");
	    navPanel.add(deleteNutzerprofilButton);

	    deleteNutzerprofilButton.addClickHandler(new ClickHandler() {
	      @Override
		public void onClick(ClickEvent event) {
	        Showcase showcase = new DeleteAccountDemo();
	        RootPanel.get("Details").clear();
	        RootPanel.get("Details").add(showcase);
	      }
	    });
	    
	    
	    // Nächste Button-Definition, muss noch ausformuliert und implementiert werden
	    final Button showAllGesperrteNutzerprofileButton = new Button(
	        "Gesperrte Nutzerprofile anzeigen");
	    
	    //weitere Buttons für Info, merken etc.
	    
	}
}
