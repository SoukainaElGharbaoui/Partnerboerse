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

/**
 * Diese Klasse dient dazu, Partnervorschlaege anhand des eigenen Nutzerprofils
 * anzuzeigen.
 */
public class ShowPartnervorschlaegeNp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt, erzeugen. 
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Panel erzeugen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Widgets erzeugen. 
	 */
	private Label ueberschriftLabel = new Label("Diese Profile könnten Ihnen gefallen:");
	private Label infoLabel = new Label();
	private Label informationLabel = new Label();
	private Label ergebnisLabel = new Label();
	private Button anzeigenButton;
	private FlexTable partnervorschlaegeNpFlexTable = new FlexTable();

	/**
	 * Variable erstellen, die die Anzahl der befuellten Zeilen enthaelt.
	 */
	private int zaehler;
	
	/**
	 * Variable fuer den Listtyp erstellen.  
	 */
	private String listtyp;
	
//	/**
//	 * Variable fuer die Fremdprofil-ID erstellen.
//	 */
//	private int fremdprofilId;

	/**
	 * Konstruktor erstellen.
	 * @param listtyp Der Listtyp (PvNp). 
	 */
	public ShowPartnervorschlaegeNp(String listtyp) {
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
		informationLabel.addStyleName("partnerboerse-label");
		partnervorschlaegeNpFlexTable.setCellPadding(6);
		partnervorschlaegeNpFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		partnervorschlaegeNpFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		partnervorschlaegeNpFlexTable.setText(0, 0, "F-ID");
		partnervorschlaegeNpFlexTable.setText(0, 1, "Uebereinstimmung in %");
		partnervorschlaegeNpFlexTable.setText(0, 2, "Vorname");
		partnervorschlaegeNpFlexTable.setText(0, 3, "Nachname");
		partnervorschlaegeNpFlexTable.setText(0, 4, "Geburtsdatum");
		partnervorschlaegeNpFlexTable.setText(0, 5, "Geschlecht");
		partnervorschlaegeNpFlexTable.setText(0, 6, "Anzeigen");
		
		befuelleTabelleNp();

		/**
		 * Widgets dem Panel hinzufuegen. 
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(ergebnisLabel);
		verPanel.add(infoLabel);
		verPanel.add(informationLabel);
		verPanel.add(partnervorschlaegeNpFlexTable);

	}
	
	/**
	 * Methode erstellen, die die anangesehenen Partnervorschlaege geordnet nach Aehnlichkeit ausgibt
	 * und in die Tabelle eintraegt. 
	 */
	public void befuelleTabelleNp() {
		ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeNp(nutzerprofil.getProfilId(),
				new AsyncCallback<List<Nutzerprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf");
					}

					@Override
					public void onSuccess(List<Nutzerprofil> result) {

						int row = 0;

						for (Nutzerprofil np : result) {

							final int fremdprofilId = np.getProfilId();

							row++;

							partnervorschlaegeNpFlexTable.setText(row, 0, String.valueOf(fremdprofilId));
							partnervorschlaegeNpFlexTable.setText(row, 1, String.valueOf(np.getAehnlichkeit()) + "%");
							partnervorschlaegeNpFlexTable.setText(row, 2, np.getVorname());
							partnervorschlaegeNpFlexTable.setText(row, 3, np.getNachname());

							Date geburtsdatum = np.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);

							partnervorschlaegeNpFlexTable.setText(row, 4, geburtsdatumString);
							partnervorschlaegeNpFlexTable.setText(row, 5, np.getGeschlecht());

							/**
							 * Anzeigen-Button fuer die Anzeige eines Fremdprofils erzeugen. 
							 */
							anzeigenButton = new Button("Anzeigen");
							partnervorschlaegeNpFlexTable.setWidget(row, 6, anzeigenButton);

							partnervorschlaegeNpFlexTable.setText(row, 7, String.valueOf(row));

							/**
							 * ClickHandler fuer den Button zum Anzeigen eines Fremdprofils erzeugen. 
							 * Bei Betaetigung dieses Buttons wird die Seite zum Anzeigen des Fremdprofils 
							 * aufgerufen. Zudem wird dem Fremdprofil ein Besuch gesetzt, damit es beim 
							 * naechsten Aufruf der unangesehenen Partnervorschlaege nicht mehr erscheint. 
							 */
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									ClientsideSettings.getPartnerboerseAdministration().besuchSetzen(
											nutzerprofil.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

												@Override
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf.");
												}

												@Override
												public void onSuccess(Void result) {
													String profiltyp = "Fp";
													String name = null;
													ShowFremdprofil showFremdprofil = new ShowFremdprofil(fremdprofilId, profiltyp, listtyp, name);
													RootPanel.get("Details").clear();
													RootPanel.get("Details").add(showFremdprofil);
												}

											});
									}
							});
						}

						
						/**
						 * Bestehen keine unangesehenen Partnervorschlaege, wird eine Information ueber diesen 
						 * Zustand ausgegeben. 
						 */
						boolean befuellt = pruefeLeereTable();
						if (befuellt == true) {
							ueberschriftLabel.setVisible(false);
							partnervorschlaegeNpFlexTable.setVisible(false);
							informationLabel.setText("Sie haben zurzeit keine unangesehenen Partnervorschläge.");
						}
					}
				});
		
	}

	
	
	/**
	 * Methode erstellen, die prueft, ob die Tabelle leer ist. 
	 * @return Boolscher Wert, der angibt, ob die Tabelle leer ist.
	 */
	public boolean pruefeLeereTable() {

		for (int k = 1; k < partnervorschlaegeNpFlexTable.getRowCount(); k++) {

			if (partnervorschlaegeNpFlexTable.getText(k, 0) == null) {
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

