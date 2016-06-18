package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Diese Klasse dient dazu, die Merkliste eines Nutzers anzuzeigen.
 */

public class ShowMerkliste extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Vertikales Panel hinzufuegen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Neues Label zur Anzeige der Ueberschrift erzeugen und Beschriftung festlegen.
	 */
	private Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Merkliste:");

	/**
	 * Neue Tabelle zur Anzeige der gemerkten Kontakte erzeugen.
	 */
	private FlexTable merklisteFlexTable = new FlexTable();

	/**
	 * Neues Label zur Ausgabe einer Information erzeugen. 
	 */
	private Label infoLabel = new Label();
	
	/**
	 * Neue Variable erstellt, die die Anzahl der befüllten Zeilen enthält
	 */
	private int zaehler;
	
	private Button loeschenButton;
	private Button anzeigenButton;

	
	/**
	 * Neue Methode definiert, die die Tabelle auf Inhalt prüft
	 */
	public boolean pruefeLeereTable() {
		
		for (int k = 2; k < merklisteFlexTable.getRowCount(); k++) {
			
			if (merklisteFlexTable.getText(k, 0) == null) {
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
	 * Konstruktor hinzufuegen. 
	 */
	public ShowMerkliste() {
		this.add(verPanel);

		/**
		 * CSS auf das Label zur Anzeige der Ueberschrift anwenden. 
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Kopfzeile der Tabelle festlegen.
		 */
		merklisteFlexTable.setText(0, 0, "F-ID");
		merklisteFlexTable.setText(0, 1, "Vorname");
		merklisteFlexTable.setText(0, 2, "Nachname");
		merklisteFlexTable.setText(0, 3, "Geburtstag");
		merklisteFlexTable.setText(0, 4, "Geschlecht");
		merklisteFlexTable.setText(0, 5, "Löschen");
		merklisteFlexTable.setText(0, 6, "Anzeigen");

		/**
		 * CSS auf die Tabelle anwenden und die Tabelle formatieren. 
		 */
		merklisteFlexTable.setCellPadding(6);
		merklisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		merklisteFlexTable.addStyleName("FlexTable");

		/**
		 * Alle gemerkten Nutzerprofile eines Nutzers abfragen. 
		 */
		ClientsideSettings.getPartnerboerseAdministration().getGemerkteNutzerprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<Merkliste>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf."); 
					}

					/**
					 * Das Ergebnis der Abfrage ist ein Merkliste-Objekt. 
					 * Die gemerkten Nutzerprofil-Objekte werden in einem Vektor von Nutzerprofilen gespeichert. 
					 * Dieser Vektor wird in einer Schleife durchlaufen und jedes gemerkte Nutzerprofil wird in 
					 * eine Zeile der Tabelle eingefuegt. 
					 */
					public void onSuccess(Merkliste result) {
						
						// Vektor von gemerkten Nutzerprofilen erzeugen. 
						List<Nutzerprofil> gemerkteNutzerprofile = result.getGemerkteNutzerprofile();
						
						// Anzahl der Zeilen ermitteln. 
						int row = merklisteFlexTable.getRowCount();

						// Jedes gemerkte Nutzerprofil in eine Zeile der Tabelle einfuegen. 
						for (Nutzerprofil n : gemerkteNutzerprofile) {
							
							// Anzahl der Zeilen um 1 erhoehen. 
							row++;

							// Fremdprofil-ID des gemerkten Nutzerprofils ermitteln.
							final String fremdprofilId = String.valueOf(n.getProfilId());

							// Jeweilige Zeile der Tabelle mit den Nutzerprofil-Daten befuellen. 
							merklisteFlexTable.setText(row, 0, fremdprofilId);
							merklisteFlexTable.setText(row, 1, n.getVorname());
							merklisteFlexTable.setText(row, 2, n.getNachname());
							
							Date geburtsdatum = n.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
							
							merklisteFlexTable.setText(row, 3, geburtsdatumString); 
							merklisteFlexTable.setText(row, 4, n.getGeschlecht());

							// Neuen Button zum Loeschen eines Vermerk erzeugen.
							loeschenButton = new Button("Löschen");
							
							// Button zum Loeschen eines Vermerks in die jeweilige Zeile der Tabelle einfuegen. 
							merklisteFlexTable.setWidget(row, 5, loeschenButton);
							
							// Neuen Button zum Anzeigen eines Fremdprofils erzeugen.
							anzeigenButton = new Button("Anzeigen");

							// Button zur Anzeige des Fremdprofils in die jeweilige Zeile der Tabelle einfuegen. 
							merklisteFlexTable.setWidget(row, 6, anzeigenButton);

							// Zeilenindex der Tabelle in die jeweilige Zeile der Tabelle einfuegen. 
							merklisteFlexTable.setText(row, 7, String.valueOf(row));
							

							/**
							 * ClickHandler fuer den Button zum Loeschen eines Vermerks erzeugen. 
							 */
							loeschenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									/*
									 * Tabelle nach Fremdprofil-ID durchsuchen. 
									 * Index = Die Zeile, die gelöscht werden
									 * soll. Achtung: Die Tabelle darf erst ab
									 * Zeile 2 verwendet werden (Zeile 1 = Kopfzeile).
									 */
									
									for (int i = 2; i < merklisteFlexTable
											.getRowCount(); i++) {

										// Fremdprofil-ID der Tabelle ermitteln.
										String fremdprofilIdFlexTable = merklisteFlexTable
												.getText(i, 0);

										/*
										 *  Wenn die Fremdprofil-ID der Tabelle mit der
										 *  Fremdprofil-ID des Nutzerprofils uebereinstimmt,
										 *  wird der Vermerk aus der Datenbank entfernt. 
										 */
										if (Integer
												.valueOf(fremdprofilIdFlexTable) == Integer
												.valueOf(fremdprofilId)) {

											/**
											 * Vermerk aus der Datenbank entfernen.
											 */
											ClientsideSettings.getPartnerboerseAdministration().vermerkstatusAendern(
													nutzerprofil.getProfilId(), Integer.valueOf(fremdprofilId),
													new AsyncCallback<Integer>() {

																public void onFailure(
																		Throwable caught) {
																	infoLabel
																			.setText("Es trat ein Fehler auf.");
																}

																public void onSuccess(
																		Integer result) {
																	infoLabel
																			.setText("Das Profil wurde erfolgreich von Ihrer Merkliste entfernt.");
																}
											});

											// Jeweilige Zeile der Tabelle löschen.
											if (merklisteFlexTable.getRowCount() == 3) {
												
												merklisteFlexTable.removeRow(i);
												
												ueberschriftLabel.setText("Sie haben zurzeit keine Profile gesperrt.");
												infoLabel.setVisible(false);
												merklisteFlexTable.setVisible(false);
												ueberschriftLabel.setVisible(true);
											}
											
											else {
												merklisteFlexTable.removeRow(i);
												break;
											}
										}
									}
								}
							});

							/**
							 * ClickHandler fuer den Button zum Anzeigen eines Fremdprofils erzeugen. 
							 */
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									// Prüfen, ob Benutzer von Fremdprofil
									// gesperrt wurde.
									ClientsideSettings.getPartnerboerseAdministration().getSperrstatusEigenesProfil(
											nutzerprofil.getProfilId(), Integer.valueOf(fremdprofilId),
											new AsyncCallback<Integer>() {

														@Override
														public void onFailure(
																Throwable caught) {
															infoLabel
																	.setText("Es trat ein Fehler auf.");
														}

														@Override
														public void onSuccess(
																Integer result) {
															/*
															 * Wenn keine Sperrung vorliegt, wird der Nutzer auf die
															 * Seite des jeweiligen Fremdprofils weitergeleitet.
															 */
															if (result == 0) {
																ShowFremdprofil showFremdprofil = new ShowFremdprofil(
																		Integer.valueOf(fremdprofilId));
																RootPanel
																		.get("Details")
																		.clear();
																RootPanel
																		.get("Details")
																		.add(showFremdprofil);

																/*
																 * Wenn eine Sperrung vorliegt, wird eine Bildschirmmeldung
																 * ausgegeben, die den Nutzer über diesen Zustand informiert.
																 */
															} else {
																Window.alert("Sie können dieses Nutzerprofil nicht anzeigen, da Sie von diesem gesperrt wurden.");
															}
														}
											});
								}
							});
						}
						
						boolean befuellt = pruefeLeereTable();
											
						if (befuellt == true) {
							
							ueberschriftLabel.setText("Sie haben sich zurzeit keine Profile gemerkt.");
							merklisteFlexTable.setVisible(false);
							ueberschriftLabel.setVisible(true);
						}
					}
				});

		/**
		 * Widgets zum vertikalen Panel hinzufuegen. 
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(merklisteFlexTable);
		verPanel.add(infoLabel);

	}

}