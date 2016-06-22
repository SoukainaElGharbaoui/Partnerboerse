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
 * Diese Klasse dient dazu, die Merkliste eines Nutzerprofils anzuzeigen.
 */
public class ShowMerkliste extends VerticalPanel {

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
	private Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Merkliste:");
	private FlexTable merklisteFlexTable = new FlexTable();
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
	 * Konstruktor erzeugen. 
	 */
	public ShowMerkliste(final String listtyp) {
		this.add(verPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		merklisteFlexTable.addStyleName("FlexTable");
		merklisteFlexTable.setCellPadding(6);
		merklisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");

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
		 * Nun werden alle gemerkten Nutzerprofile eines Nutzerprofils abgefragt. 
		 * Das Ergebnis der Abfrage ist ein Merkliste-Objekt. Die gemerkten Nutzerprofil-Objekte werden in einer
		 * Liste von Nutzerprofilen gespeichert. Diese Liste wird in einer Schleife durchlaufen und jedes 
		 * gemerkte Nutzerprofil wird in eine Zeile der Tabelle eingefuegt. Zudem wird der Tabelle bei jedem 
		 * Schleifendurchlauf je ein Button zum Loeschen und ein Button zum Anzeigen eines Vermerks hinzugefuegt. 
		 */
		ClientsideSettings.getPartnerboerseAdministration().getGemerkteNutzerprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<Merkliste>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf."); 
					}

					public void onSuccess(Merkliste result) {
						
						List<Nutzerprofil> gemerkteNutzerprofile = result.getGemerkteNutzerprofile();
						
						int row = merklisteFlexTable.getRowCount();

						for (Nutzerprofil n : gemerkteNutzerprofile) {
							
							row++;

							final String fremdprofilId = String.valueOf(n.getProfilId());

							merklisteFlexTable.setText(row, 0, fremdprofilId);
							merklisteFlexTable.setText(row, 1, n.getVorname());
							merklisteFlexTable.setText(row, 2, n.getNachname());
							
							Date geburtsdatum = n.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
							
							merklisteFlexTable.setText(row, 3, geburtsdatumString); 
							merklisteFlexTable.setText(row, 4, n.getGeschlecht());

							final Button loeschenButton = new Button("Löschen");
							
							merklisteFlexTable.setWidget(row, 5, loeschenButton);
							
							final Button anzeigenButton = new Button("Anzeigen");

							merklisteFlexTable.setWidget(row, 6, anzeigenButton);

							merklisteFlexTable.setText(row, 7, String.valueOf(row));
							
							/**
							 * ClickHandler fuer den Button zum Loeschen einer Sperrung erzeugen. 
							 * Sobald der Button betaetigt wird, wird die Sperrung sowohl aus der 
							 * Tabelle als auch aus der Datenbank entfernt.  
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
																}
											});

											if (merklisteFlexTable.getRowCount() == 3) {
												
												merklisteFlexTable.removeRow(i);
												
												ueberschriftLabel.setText("Sie haben sich zurzeit keine Profile gemerkt.");
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
							 * Sobald der Button betaetigt wird, wird geprueft, ob das Nutzerprofil von 
							 * diesem Fremdprofil gesperrt wurde. Wenn ja, wird eine entsprechende 
							 * Information ausgegeben, die ueber diesen Zustand informiert. Wenn nein,
							 * wird die Seite zum Anzeigen eines Fremdprofils aufgerufen. 
							 */
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

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
															if (result == 0) {
																
																String profiltyp = "Fp";
																
																ShowFremdprofil showFremdprofil = new ShowFremdprofil(
																		Integer.valueOf(fremdprofilId), profiltyp, listtyp);
																RootPanel
																		.get("Details")
																		.clear();
																RootPanel
																		.get("Details")
																		.add(showFremdprofil);

															} else {
																Window.alert("Sie können dieses Nutzerprofil nicht anzeigen, da Sie von diesem gesperrt wurden.");
															}
														}
											});
								}
							});
						}
						
						/**
						 * Pruefen, ob die Tabelle leer ist. Falls dies der Fall ist, wird eine 
						 * entsprechende Information ueber diesen Zustand ausgegeben. 
						 */
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
