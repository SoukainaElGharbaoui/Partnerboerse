package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Vertikales Panel erzeugen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	
	/**
	 * Widgets erzeugen. 
	 */
	private FlexTable merklisteFlexTable = new FlexTable();
	private Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Merkliste:");
	private Label infoLabel = new Label();
	private Button loeschenButton = new Button("Ausgewählte Profile von Merkliste entfernen");
	private Button anzeigenButton;
	private Button selectAllProfilsButton = new Button("Alle Profile markieren");
	private CheckBox cb;

	/**
	 * Attribute erzeugen
	 */
	private String listtyp;
	private int zaehler;
	private int fremdprofilId;
	
	/**
	 * Konstruktor erzeugen. 
	 * @param listtyp Der Listtyp der Seite, von der das Anzeigen des Fremprofils aufgerufen wird (Merkliste).
	 */
	public ShowMerkliste(String listtyp) {
		this.listtyp = listtyp;
		run();
	}
		
	/**
	 * Methode erstellen, die den Aufbau der Seite startet.
	 */
	public void run() {
	
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
		merklisteFlexTable.setText(0, 5, "Anzeigen");
		merklisteFlexTable.setText(0, 6, "Löschen");
		
		getMerkliste();
		
		
		/**
		 * ClickHandler fuer den Button zum Auswaehlen aller angezeigten Profile erzeugen. 
		 * Sobald dieser Button betaetigt wird, werden alle CheckBoxen der Tabelle ausgewaehlt.
		 */
		selectAllProfilsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				for (int i = 2; i < merklisteFlexTable.getRowCount(); i++) {

					CheckBox cb = (CheckBox) merklisteFlexTable.getWidget(i, 6);
					cb.setValue(true);
					
					merklisteFlexTable.setWidget(i, 6, cb);
				}
			}
		});
		
		/**
		 * ClickHandler fuer den Button zum Loeschen einer Sperrung erzeugen. 
		 * Sobald der Button betaetigt wird, wird die Sperrung sowohl aus der 
		 * Tabelle als auch aus der Datenbank entfernt.  
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				
				int rowTable = merklisteFlexTable.getRowCount();
				int fremdprofilId;
				infoLabel.setText("" + rowTable);
				
				zaehler = 0;
				
				/**
				 * Die For-Schleife ueberprueft Zeile fuer Zeile ob die Checkbox markiert ist oder nicht.
				 * Ist eine Checkbox markiert, wird die Fremprofil-ID des markierten Nutzerprofils ausgelesen
				 * und der Vermerkstatus des Nutzerprofils geaendert. 
				 * Das entsprechende Nutzerprofil wird dann aus der Merkliste entfernt.
				 */
				for (int k = 2; k < rowTable; k++) {
					
					boolean checked = ((CheckBox) merklisteFlexTable.getWidget(k, 6)).getValue();
					
					infoLabel.setText("" + checked);
					
					if (checked == true) {
						
						zaehler++;
						
						fremdprofilId = Integer.valueOf(merklisteFlexTable.getText(k, 0));
						
						infoLabel.setText("" + fremdprofilId);
						
						aendereVermerkstatus(fremdprofilId);
					 
						/**
						* Jeweilige Zeile der
						* Tabelle loeschen.
						*/
						merklisteFlexTable.removeRow(k);
						k--;
							
						/**
						 * Fall, die Merkliste ist leer, dann wird ein entsprechendes Label zur Information angezeigt.
						 */
						if (merklisteFlexTable.getRowCount() == 2) {
								
						ueberschriftLabel.setText("Sie haben sich zurzeit keine Profile gemerkt.");
						merklisteFlexTable.setVisible(false);
						buttonPanel.setVisible(false);
						infoLabel.setVisible(false);
								
						ueberschriftLabel.setVisible(true);
						}
					}
				}
				
				/**
				 * Fall, dass keine CheckBox ausgewaehlt wurde.
				 * Dann wird eine entsprechende Information angezeigt.
				 */
				if (zaehler == 0) {
					Window.alert("Es wurde nichts ausgewählt.");
				}
			}
		});
		
		/**
		 * Widgets zum vertikalen Panel hinzufuegen. 
		 */
		
		buttonPanel.add(loeschenButton);
		buttonPanel.add(selectAllProfilsButton);
		
		verPanel.add(ueberschriftLabel);
		verPanel.add(merklisteFlexTable);
		verPanel.add(buttonPanel);
		verPanel.add(infoLabel);
	}
	
	
	/**
	 * Nun werden alle gemerkten Nutzerprofile eines Nutzerprofils abgefragt. 
	 * Das Ergebnis der Abfrage ist ein Merkliste-Objekt. Die gemerkten Nutzerprofil-Objekte werden in einer
	 * Liste von Nutzerprofilen gespeichert. Diese Liste wird in einer Schleife durchlaufen und jedes 
	 * gemerkte Nutzerprofil wird in eine Zeile der Tabelle eingefuegt. Zudem wird der Tabelle bei jedem 
	 * Schleifendurchlauf je ein Button zum Loeschen und ein Button zum Anzeigen eines Vermerks hinzugefuegt. 
	 */
	public void getMerkliste() {
		
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

							fremdprofilId = n.getProfilId();

							merklisteFlexTable.setText(row, 0, String.valueOf(fremdprofilId));
							merklisteFlexTable.setText(row, 1, n.getVorname());
							merklisteFlexTable.setText(row, 2, n.getNachname());
							
							Date geburtsdatum = n.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
							
							merklisteFlexTable.setText(row, 3, geburtsdatumString); 
							merklisteFlexTable.setText(row, 4, n.getGeschlecht());
							
							anzeigenButton = new Button("Anzeigen");

							merklisteFlexTable.setWidget(row, 5, anzeigenButton);
							
							cb = new CheckBox();
							cb.setValue(false);
							merklisteFlexTable.setWidget(row, 6, cb);
							
							merklisteFlexTable.setText(row, 7, String.valueOf(row));
							

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
							buttonPanel.setVisible(false);
							
							ueberschriftLabel.setVisible(true);
						}
					}
				});
	}
	
	
	/**
	 * Methode, die die ausgewaehlten Fremdprofile fuer ein Nutzerprofil von der Merkliste streicht.
	 * @param fremdprofilId Die Fremdprofil-Id des Nutzerprofils, dessen Vermerkstatus geaendert werden soll.
	 */
	public void aendereVermerkstatus(int fremdprofilId) {
		
		ClientsideSettings.getPartnerboerseAdministration()
 		.vermerkstatusAendern(nutzerprofil.getProfilId(), fremdprofilId, 
 			new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText(
					"Beim Ändern des Vermerkstatus trat ein Fehler auf.");								
			}
	
			@Override
			public void onSuccess(Integer result) {
				
				infoLabel.setText(
					"Das Ändern des Vermerkstatus hat funktioniert.");			
			}
 		});
	}
	
	
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


}
