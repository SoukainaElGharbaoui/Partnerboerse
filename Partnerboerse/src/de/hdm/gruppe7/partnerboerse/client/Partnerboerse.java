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

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class Partnerboerse implements EntryPoint {

	/**
	 * Diese Klasse sichert die Implementierung des Interface <code>EntryPoint</code>.
	 * Daher benötigen wir die Methode <code>public void onModuleLoad()</code>. 
	 * Diese ist das GWT-Pendant der<code>main()</code>-Methode normaler Java-Applikationen.
	 */
	private final PartnerboerseAdministrationAsync partnerboerseAdministration = GWT
			.create(PartnerboerseAdministration.class);

	@Override
	public void onModuleLoad() {

		/**
		 * VerticalPanels und HorizontalPanels erzeugen.
		 */
		VerticalPanel navPanel1 = new VerticalPanel();
		VerticalPanel navPanel2 = new VerticalPanel();
		HorizontalPanel horPanelVorname = new HorizontalPanel();
		HorizontalPanel horPanelNachname = new HorizontalPanel();
		HorizontalPanel horPanelGeschlecht = new HorizontalPanel();
		HorizontalPanel horPanelGeburtsdatum = new HorizontalPanel();
		HorizontalPanel horPanelRaucher = new HorizontalPanel();
		HorizontalPanel horPanelKoerpergroesse = new HorizontalPanel();
		HorizontalPanel horPanelHaarfarbe = new HorizontalPanel();
		HorizontalPanel horPanelReligion = new HorizontalPanel();
		
		RootPanel.get("Details").add(navPanel1);
		
		/**
		 * Das VerticalPanel wird einem DIV-Element names "Navigator" in der 
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen Darstellungsort.
		 */
		RootPanel.get("Navigator").add(navPanel2);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button eigenesProfilButton = new Button("Eigenes Profil anzeigen");
		navPanel2.add(eigenesProfilButton);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button profilBearbeitenButton = new Button("Eigenes Profil bearbeiten");
		navPanel2.add(profilBearbeitenButton);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button eigenesProfilLoeschen = new Button("Eigenes Profil l&ouml;schen");
		navPanel2.add(eigenesProfilLoeschen);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button merklisteButton = new Button("Merkliste anzeigen");
		navPanel2.add(merklisteButton);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button sperrlisteButton = new Button("Sperrliste anzeigen");
		navPanel2.add(sperrlisteButton);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button partnervorschlaegeOhneSpButton = new Button("Unangesehene Partnervorschlaege anzeigen");
		navPanel2.add(partnervorschlaegeOhneSpButton);

		/**
		 * Erzeugen eines Navigation-Buttons.
		 */
		final Button partnervorschlaegeMitSpButton = new Button("Partnervorschlaege auf Basis Ihrer Suche anzeigen");
		navPanel2.add(partnervorschlaegeMitSpButton);

		/**
		 * Erzeugen eines Eingabefelds fuer den Vornamen.
		 */
		final TextBox vornameTextBox = new TextBox();
		final Label vornameLabel = new Label("  Vorname");

		navPanel1.add(horPanelVorname);
		horPanelVorname.add(vornameTextBox);
		horPanelVorname.add(vornameLabel);
		
		/**
		 * Erzeugen eines Eingabefelds fuer den Nachnamen.
		 */
		final TextBox nachnameTextBox = new TextBox();
		final Label nachnameLabel = new Label("  Nachname");
		navPanel1.add(horPanelNachname);
		horPanelNachname.add(nachnameTextBox);
		horPanelNachname.add(nachnameLabel);

		/**
		 * Erzeugen einer Auswahl fuer das Geschlecht.
		 */
		final Label geschlechtLabel = new Label("  Geschlecht");
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		navPanel1.add(horPanelGeschlecht);
		horPanelGeschlecht.add(geschlechtListBox);
		horPanelGeschlecht.add(geschlechtLabel);

		/**
		 * Erzeugen einer TextBox fuer das Geburtsdatum.
		 */
		final Label geburtsdatumLabel = new Label("  Geburtsdatum");
		final TextBox geburtsdatumTextBox = new TextBox();
		navPanel1.add(horPanelGeburtsdatum);
		horPanelGeburtsdatum.add(geburtsdatumTextBox);
		horPanelGeburtsdatum.add(geburtsdatumLabel);

		/**
		 * Erzeugen einer Auswahl fuer den Raucherstatus.
		 */
		final Label raucherLabel = new Label("Raucherstatus");
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichraucher");
		navPanel1.add(horPanelRaucher);
		horPanelRaucher.add(raucherListBox);
		horPanelRaucher.add(raucherLabel);

		/**
		 * Erzeugen eines Eingabefelds fuer die Koerpergroesse.
		 */
		final TextBox koerpergroesseTextBox = new TextBox();
		final Label koerpergroesseLabel = new Label("Koerpergroesse (in cm)");
		navPanel1.add(horPanelKoerpergroesse);
		horPanelKoerpergroesse.add(koerpergroesseTextBox);
		horPanelKoerpergroesse.add(koerpergroesseLabel);

		/**
		 * Erzeugen einer Auswahl fuer die Haarfarbe.
		 */
		final ListBox haarfarbeListBox = new ListBox();
		final Label haarfarbeLabel = new Label("Haarfarbe");
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		navPanel1.add(horPanelHaarfarbe);
		horPanelHaarfarbe.add(haarfarbeListBox);
		horPanelHaarfarbe.add(haarfarbeLabel);
		
		/**
		 * Erzeugen einer Auswahl fuer die Religion. 
		 */
		final ListBox religionListBox = new ListBox();
		final Label religionLabel = new Label("Religion");
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		navPanel1.add(horPanelReligion);
		horPanelReligion.add(religionListBox);
		horPanelReligion.add(religionLabel);
		
		/**
		 * infoLabel für die Benutzerinformation erzeugen. 
		 */
		final Label infoLabel = new Label();

		/**
		 * createNutzerprofilButton erzeugen und zum navPanel hinzufügen.
		 */
		final Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
		createNutzerprofilButton.setStylePrimaryName("partnerboerse-menubutton");
		navPanel1.add(createNutzerprofilButton);

		/**
		 * infoLabel zum navPanel hinzufügen.
		 */
		navPanel1.add(infoLabel);

		/**
		 * ClickHandler für den createNutzerprofilButton erstellen.
		 */
		createNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/**
				 * Das Auslesen der "ListBox" funktioniert, die Werte werden in
				 * die Datenbank geschrieben!
				 */
				partnerboerseAdministration.createNutzerprofil(
						vornameTextBox.getText(), nachnameTextBox.getText(),
						geburtsdatumTextBox.getText(),
						geschlechtListBox.getSelectedItemText(),
						haarfarbeListBox.getSelectedItemText(),
						koerpergroesseTextBox.getText(),
						raucherListBox.getSelectedItemText(),
						religionListBox.getSelectedItemText(),
						new AsyncCallback<Nutzerprofil>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf");
							}

							@Override
							public void onSuccess(Nutzerprofil result) {
								infoLabel.setText("Das Nutzerprofil wurde erfolgreich angelegt");
							}

						});

			}

		});

	}
}
