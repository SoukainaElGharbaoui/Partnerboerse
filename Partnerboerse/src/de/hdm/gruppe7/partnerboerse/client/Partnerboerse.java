package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Partnerboerse implements EntryPoint{

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */
	
	private final PartnerboerseAdministrationAsync partnerboerseAdministration = GWT.create(PartnerboerseAdministration.class);
	
	@Override
	public void onModuleLoad() {

		// Achtung: bei NutzerprofilForm ist partnerboerseVerwaltung drin,
		// brauchen wir evtl nicht wirklich

		/*
		 * Der Navigator ist als einspaltige Aneinanderreihung von Buttons
		 * realisiert. Daher bietet sich ein VerticalPanel als Container an.
		 */
		VerticalPanel navPanel = new VerticalPanel();
		VerticalPanel naviPanel = new VerticalPanel();
		HorizontalPanel horPanel = new HorizontalPanel();
		HorizontalPanel hor2Panel = new HorizontalPanel();
		HorizontalPanel hor3Panel = new HorizontalPanel();
		HorizontalPanel hor4Panel = new HorizontalPanel();
		HorizontalPanel hor5Panel = new HorizontalPanel();
		HorizontalPanel hor6Panel = new HorizontalPanel();
		HorizontalPanel hor7Panel = new HorizontalPanel();
		/*
		 * Das VerticalPanel wird einem DIV-Element namens "Navigator" in der
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen
		 * Darstellungsort.
		 */
		RootPanel.get("Details").add(navPanel);
		RootPanel.get("Navigator").add(naviPanel);

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
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button eigenesProfilButton = new Button("Eigenes Profil anzeigen");
		naviPanel.add(eigenesProfilButton);
		
		/**
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button profilBearbeitenButton = new Button("Eigenes Profil bearbeiten");
		naviPanel.add(profilBearbeitenButton);
		
		/**
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button eigenesProfilLoeschen = new Button("Eigenes Profil l&ouml;schen");
		naviPanel.add(eigenesProfilLoeschen);
		
		/**
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button merklisteButton = new Button("Merkliste anzeigen");
		naviPanel.add(merklisteButton);
		
		/**
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button sperrlisteButton = new Button("Sperrliste anzeigen");
		naviPanel.add(sperrlisteButton);
		
		/**
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button partnervorschlaegeOhneSpButton = new Button("Unangesehene Partnervorschlaege anzeigen");
		naviPanel.add(partnervorschlaegeOhneSpButton);
		
		/**
		 * Erzeugen eines Navigation-Buttons
		 */
		final Button partnervorschlaegeMitSpButton = new Button("Partnervorschlaege auf Basis Ihrer Suche anzeigen");
		naviPanel.add(partnervorschlaegeMitSpButton);
		
		
		
		/**
		 * Erzeugen eines Eingabefelds f�r den Vornamen
		 */
		final TextBox vornameTextBox = new TextBox ();
		final Label vornameLabel = new Label("Vorname angeben");
		
		navPanel.add(horPanel);
		horPanel.add(vornameLabel);	
	    horPanel.add(vornameTextBox);
	 
	    
	    /**
		 * Erzeugen eines EIngabefelds f�r den Nachnamen
		 */
		final TextBox nachnameTextBox = new TextBox ();
		final Label nachnameLabel = new Label("Nachname angeben");
		navPanel.add(hor2Panel);
		hor2Panel.add(nachnameLabel);
		hor2Panel.add(nachnameTextBox);
	    
	    
		
		/**
		 * Erzeugen einer Auswahl f�r das Geschlecht
		 */
		final Label geschlechtLabel = new Label("Geschlecht auswaehlen");
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		navPanel.add(hor3Panel);
		hor3Panel.add(geschlechtLabel);
	    hor3Panel.add(geschlechtListBox);
	    
	   
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
		final ListBox raucherListBox = new ListBox(); 
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichraucher");
		navPanel.add(hor4Panel);
		hor4Panel.add(raucherLabel);
	    hor4Panel.add(raucherListBox); 
	   

	    /**
		 * Erzeugen eines EIngabefelds f�r die Gr��e in cm
		 */
		final TextBox koerpergroesseTextBox = new TextBox ();
		final Label koerpergroesseLabel = new Label("Koerperroesse in cm angeben");
		navPanel.add(hor5Panel);
		hor5Panel.add(koerpergroesseLabel);	
	    hor5Panel.add(koerpergroesseTextBox);	   
	    
	    /**
		 * Erzeugen eines EIngabefelds f�r die Haarfarbe
		 */
		final ListBox haarfarbeListBox = new ListBox ();
		final Label haarfarbeLabel = new Label("Haarfarbe angeben");
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		navPanel.add(hor6Panel);
		hor6Panel.add(haarfarbeLabel);	
		hor6Panel.add(haarfarbeListBox);
		
		 /**
		 * Erzeugen eines EIngabefelds f�r die Religion
		 */
		final ListBox religionListBox = new ListBox ();
		final Label religionLabel = new Label("Religion angeben");
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		navPanel.add(hor7Panel);
		hor7Panel.add(religionLabel);	
		hor7Panel.add(religionListBox);
		
		
		final Label ergebnisLabel = new Label();
	    
	    /**
		 * Erzugen eines Button "Nutzerprofil anlegen"
		 */
		final Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
		createNutzerprofilButton.setStylePrimaryName("partnerboerse-menubutton");
	    navPanel.add(createNutzerprofilButton);
	    navPanel.add(ergebnisLabel);
	    createNutzerprofilButton.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				
				partnerboerseAdministration.createNutzerprofil(vornameTextBox.getText(),
						nachnameTextBox.getText(), geburtsdatumDatePicker.getValue(),
						geschlechtListBox.getSelectedItemText(), haarfarbeListBox.getSelectedItemText(), 
						koerpergroesseTextBox.getText(), raucherListBox.getSelectedItemText(), 
						religionListBox.getSelectedItemText(), new AsyncCallback<Nutzerprofil> (){

							@Override
							public void onFailure(Throwable caught) {
								ergebnisLabel.setText("Es trat ein unerwarteter Fehler auf");
								
							}

							@Override
							public void onSuccess(Nutzerprofil result) {
								ergebnisLabel.setText("Das Nutzerprofil wurde erfolgreich angelegt");
								
							}
					
				});
				
			}
	    	
	    	
	    });
	   
	}
}
