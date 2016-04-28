package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

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

		HorizontalPanel horPanel = new HorizontalPanel();

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
	     * Ab hier folgen weitere Button-Definitionen, die nach exakt der gleichen
	     * Methode erfolgen wie beim ersten Button.
	     * 
	     * Da das Muster dazu sich mehrfach wiederholt, könnte man hier schon von
	     * einem unerwünschte Code Clone sprechen. Um dies stilistisch zu optimieren
	     * wäre z.B. die Verwendung des Factory oder Builder Pattern denkbar. 
	     * Hierauf wurde jedoch bewusst verzichtet, um den Komplexitätsgrad dieses
	     * Demonstrators nicht unnötig zu erhöhen. 
	     */
		
		/**
		 * Erzeugen eines EIngabefelds f�r den Vornamen
		 */
		final TextBox vornameTextBox = new TextBox ();
		final Label vornameLabel = new Label("Vorname angeben");
		navPanel.add(vornameLabel);	
	    navPanel.add(vornameTextBox);
		
	    
	    /**
		 * Erzeugen eines EIngabefelds f�r den Nachnamen
		 */
		final TextBox nachnameTextBox = new TextBox ();
		final Label nachnameLabel = new Label("Nachname angeben");
		navPanel.add(nachnameLabel);	
		navPanel.add(nachnameTextBox);
		
		/**
		 * Erzeugen einer Auswahl f�r das Geschlecht
		 */
		final Label geschlechtLabel = new Label("Geschlecht auswaehlen");
		final RadioButton weiblichRadioButton = new RadioButton("weiblich","weiblich");
		final RadioButton maennlichRadioButton = new RadioButton("maennlich","maennlich");
		navPanel.add(geschlechtLabel);
	    navPanel.add(weiblichRadioButton);
	    navPanel.add(maennlichRadioButton);
//	    FUNKTIONIERT NICHT
//	    if (weiblichRadioButton.getValue() == true){
//	    	maennlichRadioButton.setValue(false);
//	    }
//	    
	
		/**
		 * Erzeugen eines DatePickers f�r das Geburtsdatum
		 */
		final Label geburtsdatumLabel = new Label("Geburtsdatum angeben");
		final DatePicker geburtsdatumDatePicker = new DatePicker();
	    navPanel.add(geburtsdatumLabel);	
	    navPanel.add(geburtsdatumDatePicker);
	    
		
		/**
		 * Erzeugen einer CheckBox f�r Raucher
		 */
		final Label raucherLabel = new Label("Rauchen Sie?");
		final CheckBox raucherCheckBox = new CheckBox("Raucher");
		final CheckBox nichtRaucherCheckBox = new CheckBox("Nichtraucher");
		navPanel.add(raucherLabel);
	    navPanel.add(raucherCheckBox);
	    navPanel.add(nichtRaucherCheckBox);
//	    FUNKTIONIERT NICHT
//	    if (raucherCheckBox.getValue() == true){
//	   	
//	    	nichtRaucherCheckBox.setValue(false);
//	    }	else if (nichtRaucherCheckBox.getValue() == true){
//	    	raucherCheckBox.setValue(false);
//	    }  	else {raucherCheckBox.setValue(false);
//	        nichtRaucherCheckBox.setValue(false);
//	    }
	    
	    /**
		 * Erzeugen eines EIngabefelds f�r die Gr��e in cm
		 */
		final TextBox groesseTextBox = new TextBox ();
		final Label groesseLabel = new Label("Groesse angeben");
		navPanel.add(groesseLabel);	
	    navPanel.add(groesseTextBox);
	    
	    /**
		 * Erzeugen eines EIngabefelds f�r die Haarfarbe
		 */
		final ListBox haarfarbeListBox = new ListBox ();
		final Label haarfarbeLabel = new Label("Haarfarbe angeben");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		navPanel.add(haarfarbeLabel);	
		navPanel.add(haarfarbeListBox);
	   
	    /**
		 * Erzugen eines Button "Nutzerprofil anlegen"
		 */
		final Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
	    navPanel.add(createNutzerprofilButton);
	    
	}
}
