package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;

/**
 * Diese Klasse dient dazu, die Sperrliste eines Nutzerprofils anzuzeigen. 
 */

public class ShowSperrliste extends VerticalPanel {

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
	private Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Sperrliste:");
	private FlexTable sperrlisteFlexTable = new FlexTable();
	private Label infoLabel = new Label();

	/**
	 * Variable erstellen, die die Anzahl der befuellten Zeilen enthaelt. 
	 */
	private int zaehler;
	
	/**
	 * Neue Methode definiert, die prueft, ob die Tabelle leer ist. 
	 * @return Boolscher Wert, der angibt, ob die Tabelle leer ist. 
	 */
	public boolean pruefeLeereTable() {
		
		for (int k = 2; k < sperrlisteFlexTable.getRowCount(); k++) {
			
			if (sperrlisteFlexTable.getText(k, 0) == null) {
			}
			
			else {
				zaehler++;
			}
		}
		
		if (zaehler == 0) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	/**
	 * Konstruktor erstellen.
	 */
	public ShowSperrliste() {
		this.add(verPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren. 
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		sperrlisteFlexTable.addStyleName("FlexTable");
		sperrlisteFlexTable.setCellPadding(6);
		sperrlisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		
		/**
		 * Kopfzeile der Tabelle festlegen.
		 */
		sperrlisteFlexTable.setText(0, 0, "F-ID");
		sperrlisteFlexTable.setText(0, 1, "Vorname");
		sperrlisteFlexTable.setText(0, 2, "Nachname");
		sperrlisteFlexTable.setText(0, 3, "Geburtstag");
		sperrlisteFlexTable.setText(0, 4, "Geschlecht");
		sperrlisteFlexTable.setText(0, 5, "Löschen");
		sperrlisteFlexTable.setText(0, 6, "Anzeigen");

		/**
		 * Nun werden alle gesperrten Nutzerprofile eines Nutzerprofils abgefragt. 
		 * Das Ergebnis der Abfrage ist ein Sperrliste-Objekt. Die gesperrten Nutzerprofil-Objekte werden in einer
		 * Liste von Nutzerprofilen gespeichert. Diese Liste wird in einer Schleife durchlaufen und jedes 
		 * gesperrte Nutzerprofil wird in eine Zeile der Tabelle eingefuegt. Zudem wird der Tabelle bei jedem 
		 * Schleifendurchlauf je ein Button zum Loeschen und ein Button zum Anzeigen einer Sperrung hinzugefuegt. 
		 */
		ClientsideSettings.getPartnerboerseAdministration().getGesperrteNutzerprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<Sperrliste>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf."); 
					}

					/**
					 * Das Ergebnis der Abfrage ist ein Sperrliste-Objekt. 
					 * Die gesperrten Nutzerprofil-Objekte werden in einem Vektor von Nutzerprofilen gespeichert. 
					 * Dieser Vektor wird in einer Schleife durchlaufen und jedes gesperrte Nutzerprofil wird in 
					 * eine Zeile der Tabelle eingefuegt. 
					 */
					public void onSuccess(Sperrliste result) {
						
						List<Nutzerprofil> gemerkteNutzerprofile = result.getGesperrteNutzerprofile();
						
						int row = sperrlisteFlexTable.getRowCount();

						for (Nutzerprofil n : gemerkteNutzerprofile) {
							row++;

							final String fremdprofilId = String.valueOf(n.getProfilId());

							sperrlisteFlexTable.setText(row, 0, fremdprofilId);
							sperrlisteFlexTable.setText(row, 1, n.getVorname());
							sperrlisteFlexTable.setText(row, 2, n.getNachname());
							
							Date geburtsdatum = n.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
							
							sperrlisteFlexTable.setText(row, 3, geburtsdatumString);
							sperrlisteFlexTable.setText(row, 4, n.getGeschlecht());

							final Button loeschenButton = new Button("Löschen");
							sperrlisteFlexTable.setWidget(row, 5, loeschenButton);

							final Button anzeigenButton = new Button("Anzeigen");
							sperrlisteFlexTable.setWidget(row, 6, anzeigenButton);

							sperrlisteFlexTable.setText(row, 7, String.valueOf(row));

							/**
							 * ClickHandler fuer den Button zum Loeschen einer Sperrung erzeugen. 
							 * Sobald der Button betaetigt wird, wird die Sperrung sowohl aus der 
							 * Tabelle als auch aus der Datenbank entfernt.  
							 */
							loeschenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									/*
									 * Tabelle nach Fremdprofil-ID durchsuchen;
									 * Index = Die Zeile, die gelöscht werden
									 * soll. Achtung: Die Tabelle darf erst ab
									 * Zeile 2 verwendet werden (Zeile 1 =
									 * Kopfzeile).
									 */
									for (int i = 2; i <= sperrlisteFlexTable.getRowCount(); i++) {

										String fremdprofilIdFlexTable = sperrlisteFlexTable.getText(i, 0);

										if (Integer.valueOf(fremdprofilIdFlexTable) == Integer.valueOf(fremdprofilId)) {

											ClientsideSettings.getPartnerboerseAdministration().sperrstatusAendern(
													nutzerprofil.getProfilId(), Integer.valueOf(fremdprofilId),
													new AsyncCallback<Integer>() {

														public void onFailure(Throwable caught) {
															infoLabel.setText("Es trat ein Fehler auf.");
														}

														public void onSuccess(Integer result) {
															infoLabel.setText("Das Profil wurde erfolgreich von Ihrer Sperrliste entfernt.");
														}

													});

											sperrlisteFlexTable.removeRow(i);
											break;
										}
									}

								}

							});

							/**
							 * ClickHandler fuer den Button zum Anzeigen eines Fremdprofils erzeugen. 
							 * Sobald der Button betaetigt wird, wird die Seite zum Anzeigen eines 
							 * Fremdprofils aufgerufen. 
							 */
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId));
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showFremdprofil);
								}

							});

						}

						/**
						 * Pruefen, ob die Tabelle leer ist. Falls dies der Fall ist, wird eine 
						 * entsprechende Information ueber diesen Zustand ausgegeben. 
						 */
						boolean befuellt = pruefeLeereTable();
						
						if (befuellt == true) {
							
							ueberschriftLabel.setText("Sie haben zurzeit keine Profile gesperrt.");

							sperrlisteFlexTable.setVisible(false);
							ueberschriftLabel.setVisible(true);
						}
					}
			});

		/**
		 * Widgets zum vertikalen Panel hinzufuegen. 
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(sperrlisteFlexTable);
		verPanel.add(infoLabel);

	}

}
