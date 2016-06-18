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
 * Diese Klasse dient dazu, die Sperrliste eines Nutzers anzuzeigen. 
 */

public class ShowSperrliste extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

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
	private Button loeschenButton;
	
	/**
	 * Neuen Button zur Anzeige eines gesperrten Nutzerprofils erzeugen und Beschriftung festlegen. 
	 */
	private Button anzeigenButton;
	
	/**
	 * Neues Label zur Ausgabe einer Information erzeugen. 
	 */
	private Label infoLabel = new Label();

	/**
	 * Neue Variable erstellt, die die Anzahl der befüllten Zeilen enthält
	 */
	private int zaehler;
	
	/**
	 * Neue Methode definiert, die die Tabelle auf Inhalt prüft
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
	 * Konstruktor hinzufuegen.
	 */
	public ShowSperrliste() {
		this.add(verPanel);

		/**
		 * CSS auf das Label zur Anzeige der Ueberschrift anwenden. 
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Header-Zeile der Tabelle festlegen.

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
						
						// Vektor von gesperrten Nutzerprofilen erzeugen. 
						List<Nutzerprofil> gemerkteNutzerprofile = result.getGesperrteNutzerprofile();
						
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
							
							Date geburtsdatum = n.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
							
							sperrlisteFlexTable.setText(row, 3, geburtsdatumString);
							sperrlisteFlexTable.setText(row, 4, n.getGeschlecht());

							// Button zum Loeschen einer Sperrung in die jeweilige Zeile der Tabelle einfuegen. 
							loeschenButton = new Button("Löschen");
							sperrlisteFlexTable.setWidget(row, 5, loeschenButton);

							// Button zur Anzeige des Fremdprofils in die jeweilige Zeile der Tabelle einfuegen. 
							anzeigenButton = new Button("Anzeigen");
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
													nutzerprofil.getProfilId(), Integer.valueOf(fremdprofilId),
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
											
											if (sperrlisteFlexTable.getRowCount() == 3) {
												
												sperrlisteFlexTable.removeRow(i);
												
												ueberschriftLabel.setText("Sie haben zurzeit keine Profile gesperrt.");
												sperrlisteFlexTable.setVisible(false);
												ueberschriftLabel.setVisible(true);
											}
											
											else {
												sperrlisteFlexTable.removeRow(i);
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

									// Seite zur Anzeige des jeweiligen Fremdprofils aufrufen. 
									ShowFremdprofil showFremdprofil = new ShowFremdprofil(
											Integer.valueOf(fremdprofilId));
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showFremdprofil);
								}
							});
						}

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
