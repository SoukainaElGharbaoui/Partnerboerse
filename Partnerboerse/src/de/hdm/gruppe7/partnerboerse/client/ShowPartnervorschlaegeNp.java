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
 * Diese Klasse dient dazu, Partnervorschlaege anhand des eigenen Nutzerprofils anzuzeigen.
 */
public class ShowPartnervorschlaegeNp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPnale erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/**
	 * Labels und Buttons erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Diese Profile könnten Ihnen gefallen:");
	private Label infoLabel = new Label();
	private Label informationLabel = new Label();
	private Label ergebnisLabel = new Label();
	private Button anzeigenButton;

	/**
	 * Tabelle zur Anzeige der Partnervorschlaege erzeugen.
	 */
	private FlexTable partnervorschlaegeNpFlexTable = new FlexTable();
	
	/**
	 * Neue Variable erzeugen, die die Anzahl der befuellten Zeilen enthaelt
	 */
	private int zaehler;
	
	/**
	 * Neue Methode definiert, die die Tabelle auf Inhalt prueft
	 * 
	 * @return boolean, zeigt ob die Tabelle leer ist oder nicht
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

	/**
	 * Konstruktor hinzufuegen.
	 */
	public ShowPartnervorschlaegeNp() {
		
		/**
		 * VerticelPanel wird dem Konstruktor hinzugefuegt.
		 */

		this.add(verPanel);

		/**
		 * CSS anwenden.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		informationLabel.addStyleName("partnerboerse-label");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
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
		
		/** 
		 * Es werden alle Partnervorschlaege
		 * anhand des des eigenen Nuzerprofis, nach Aehnlichkeit geordnet, ausgegeben.
		 * 
		 */

		ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeNp(nutzerprofil.getProfilId(),
				new AsyncCallback<List<Nutzerprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf");
					}

					@Override
					public void onSuccess(List<Nutzerprofil> result) {
						
						int row =0;
						
						/**
						 * Die Tabelle wird mit den Partnervorschlaegen befuellt.
						 */

						for (Nutzerprofil np : result) {
					
							final int fremdprofilId = np.getProfilId();
							
							row++;
							
							partnervorschlaegeNpFlexTable.setText(row, 0, String.valueOf(np.getProfilId()));
							partnervorschlaegeNpFlexTable.setText(row, 1, String.valueOf(np.getAehnlichkeit()) + "%");
							partnervorschlaegeNpFlexTable.setText(row, 2, np.getVorname());
							partnervorschlaegeNpFlexTable.setText(row, 3, np.getNachname());
							
							Date geburtsdatum = np.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
							
							partnervorschlaegeNpFlexTable.setText(row, 4, geburtsdatumString);
							partnervorschlaegeNpFlexTable.setText(row, 5, np.getGeschlecht());

							/**
							 * Der Anzeigen-Button fuer die Anzeige eines Fremdprofils wird erzeugt und der Tabelle hinzugefuegt.
							 */
							anzeigenButton = new Button("Anzeigen");
							partnervorschlaegeNpFlexTable.setWidget(row, 6, anzeigenButton);
							
							partnervorschlaegeNpFlexTable.setText(row, 7, String.valueOf(row));
							
							/**
							 * Der Clickhandler fuer den Azeigen-Button des Fremdprofils wird hinzufuegen.
							 * 
							 * Bei Betaetigung des Anzeigen-Buttons gelangt man auf die Seite auf der das Fremdprofil angezeigt wird.
							 */
							
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									/**
									 * Bei Betaetigung des Anzeigen-Button wird der Nutzer auf ein Fremdprofil weitergeleitet, 
									 * gleichzeitig wird das Fremprofil als besucht gesetzt und verschwindet somit aus der Tabelle der Partnervorschlaege.
									 * Da hier nur unangesehene Partnervorschlaege angezeigt werden.
									 */
									ClientsideSettings.getPartnerboerseAdministration().besuchSetzen(
											nutzerprofil.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

												@Override
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf.");
												}

												@Override
												public void onSuccess(Void result) {
													
													String profiltyp = "Fp";

													ShowFremdprofil showFremdprofil = new ShowFremdprofil(
															fremdprofilId, profiltyp);
													RootPanel.get("Details").clear();
													RootPanel.get("Details").add(showFremdprofil);
												}

											});
								}
							});
						}
						
						/**
						 * Ist die Tabelle leer wird das Informations-Label ausgegeben.
						 */
						boolean befuellt = pruefeLeereTable();
						
						if (befuellt == true) {
							
							ueberschriftLabel.setVisible(false);
							partnervorschlaegeNpFlexTable.setVisible(false);
							
							informationLabel.setText("Sie haben zurzeit keine unangesehenen Partnervorschläge.");
						}
					}
				});
		
		/**
		 * Alle Widgets dem VerticalPanel hinzufuegen.
		 */

		verPanel.add(ueberschriftLabel);
		verPanel.add(ergebnisLabel);
		verPanel.add(infoLabel);
		verPanel.add(informationLabel);
		verPanel.add(partnervorschlaegeNpFlexTable);

	}
}
