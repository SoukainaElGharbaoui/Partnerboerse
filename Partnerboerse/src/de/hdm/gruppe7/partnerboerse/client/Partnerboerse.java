package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
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
		 * Hinzufügen des Buttons zum VerticalPanel.
		 */
		navPanel.add(findNutzerprofilButton);
		
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
	    final Label vornameLabel = new Label("Vorname angeben");
	    final Label nachnameLabel = new Label("Nachname angeben");
	    final Label geburtsdatumLabel = new Label("Geburtsdatum angeben");
	    navPanel.add(createNutzerprofilButton);
	    navPanel.add(vornameLabel);	    
	    navPanel.add(nachnameLabel);	    
	    navPanel.add(geburtsdatumLabel);	    
	    
	    
	 // Nächste Button-Definition
	    final Button deleteNutzerprofilButton = new Button("Nutzerprofil löschen");
	    navPanel.add(deleteNutzerprofilButton);
	    
	}
}
