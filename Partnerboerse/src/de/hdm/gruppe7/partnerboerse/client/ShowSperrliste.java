package de.hdm.gruppe7.partnerboerse.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;

/**
 * Diese Klasse dient dazu, die Sperrliste eines Nutzers anzuzeigen. 
 */

public class ShowSperrliste extends VerticalPanel {

	/**
	 * Vertikales Panel erzeugen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/**
	 * Neues Label zur Anzeige der Ueberschrift erzeugen und Beschriftung festlegen.
	 */
	private Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Sperrliste:");
	
	/**
	 * Neue Tabelle zur Anzeige der gesperrten Kontakte erzeugen.
	 */
	private FlexTable sperrlisteFlexTable = new FlexTable();
	
	/**
	 * Neuen Button zum Loeschen einer Sperrung erzeugen und Beschriftung festlegen. 
	 */
	private Button loeschenButton = new Button("Löschen");
	
	/**
	 * Neuen Button zur Anzeige eines gesperrten Nutzerprofils erzeugen und Beschriftung festlegen. 
	 */
	private Button anzeigenButton = new Button("Anzeigen");
	
	/**
	 * Neues Label zur Ausgabe einer Information erzeugen. 
	 */
	private Label infoLabel = new Label();

	/**
	 * Konstruktor hinzufuegen.
	 */
	public ShowSperrliste() {
		this.add(verPanel);

		/**
		 * CSS auf das Label zur Anzeige der Ueberschrift anwenden. 
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");

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
		 * CSS auf die Tabelle anwenden und die Tabelle formatieren.
		 */
		sperrlisteFlexTable.setCellPadding(6);
		sperrlisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		sperrlisteFlexTable.addStyleName("FlexTable");

		/**
		 * Alle gesperrten Nutzerprofile eines Nutzers abfragen.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getGesperrteNutzerprofileFor(
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
						
						// Vektor von gesperrten Nutzerprofilen erzeugen. 
						Vector<Nutzerprofil> gemerkteNutzerprofile = result.getGesperrteNutzerprofile();
						
						// Anzahl der Zeilen ermitteln.
						int row = sperrlisteFlexTable.getRowCount();

						// Jedes gesperrte Nutzerprofil in eine Zeile der Tabelle einfuegen. 
						for (Nutzerprofil n : gemerkteNutzerprofile) {
							row++;

							// Fremdprofil-ID des gemerkten Nutzerprofils ermitteln.
							final String fremdprofilId = String.valueOf(n.getProfilId());

							// Jeweilige Zeile der Tabelle mit den Nutzerprofil-Daten befuellen. 
							sperrlisteFlexTable.setText(row, 0, fremdprofilId);
							sperrlisteFlexTable.setText(row, 1, n.getVorname());
							sperrlisteFlexTable.setText(row, 2, n.getNachname());
							sperrlisteFlexTable.setText(row, 3, String.valueOf(n.getGeburtsdatumDate()));
							sperrlisteFlexTable.setText(row, 4, n.getGeschlecht());

							// Button zum Loeschen einer Sperrung in die jeweilige Zeile der Tabelle einfuegen. 
							sperrlisteFlexTable.setWidget(row, 5, loeschenButton);

							// Button zur Anzeige des Fremdprofils in die jeweilige Zeile der Tabelle einfuegen. 
							sperrlisteFlexTable.setWidget(row, 6, anzeigenButton);

							// Zeilenindex der Tabelle in die jeweilige Zeile der Tabelle einfuegen. 
							sperrlisteFlexTable.setText(row, 7, String.valueOf(row));

							/**
							 * ClickHandler fuer den Button zum Loeschen eines Vermerks erzeugen. 
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

										// Fremdprofil-ID der Tabelle ermitteln.
										String fremdprofilIdFlexTable = sperrlisteFlexTable.getText(i, 0);

										/*
										 *  Wenn die Fremdprofil-ID der Tabelle mit der
										 *  Fremdprofil-ID des Nutzerprofils uebereinstimmt,
										 *  wird die Sperrung aus der Datenbank entfernt. 
										 */
										if (Integer.valueOf(fremdprofilIdFlexTable) == Integer.valueOf(fremdprofilId)) {

											/**
											 * Sperrung aus der Datenbank entfernen.
											 */
											ClientsideSettings.getPartnerboerseAdministration().sperrstatusAendern(
													Integer.valueOf(fremdprofilId),
													new AsyncCallback<Integer>() {

														public void onFailure(Throwable caught) {
															infoLabel.setText("Es trat ein Fehler auf.");
														}

														public void onSuccess(Integer result) {
															infoLabel.setText(
																	"Das Profil wurde erfolgreich von Ihrer Sperrliste entfernt.");
														}

													});

											// Jeweilige Zeile der Tabelle loeschen.
											sperrlisteFlexTable.removeRow(i);
											break;
										}
									}

								}

							});

							/**
							 * ClickHandler fuer den Button zum Anzeigen eines Fremdprofils erzeugen. 
							 */
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									// Seite zur Anzeige des jeweiligen Fremdprofils aufrufen. 
									ShowFremdprofil showFremdprofil = new ShowFremdprofil(
											Integer.valueOf(fremdprofilId));
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showFremdprofil);
								}

							});

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