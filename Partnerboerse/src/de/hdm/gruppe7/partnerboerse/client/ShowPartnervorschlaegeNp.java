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

public class ShowPartnervorschlaegeNp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private Label ueberschriftLabel = new Label("Diese Profile könnten Ihnen gefallen:");
	private Label infoLabel = new Label();
	private Label informationLabel = new Label();
	private Label ergebnisLabel = new Label();
	private Button anzeigenButton;

	/**
	 * Tabelle zur Anzeige der Partnervorschlaege hinzufuegen.
	 */
	private FlexTable partnervorschlaegeNpFlexTable = new FlexTable();
	
	/**
	 * Neue Variable erstellt, die die Anzahl der befuellten Zeilen enthaelt
	 */
	private int zaehler;
	
	/**
	 * Neue Methode definiert, die die Tabelle auf Inhalt prueft
	 */
	public boolean pruefeLeereTable() {
		
		for (int k = 2; k < partnervorschlaegeNpFlexTable.getRowCount(); k++) {
			
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
	 * Konstruktor hinzufügen.
	 */
	public ShowPartnervorschlaegeNp() {

		this.add(verPanel);

		/**
		 * Überschrift-Label hinzufügen.
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

		ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeNp(nutzerprofil.getProfilId(),
				new AsyncCallback<List<Nutzerprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf");
					}

					@Override
					public void onSuccess(List<Nutzerprofil> result) {
						int row = partnervorschlaegeNpFlexTable.getRowCount();

						for (Nutzerprofil np : result) {
							System.out.println("Profil geholt: " + np);
							infoLabel.setText("Es trat kein Fehler auf");
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

							// Anzeigen-Button formatieren und CSS einbinden
							anzeigenButton = new Button("Anzeigen");
							partnervorschlaegeNpFlexTable.setWidget(row, 6, anzeigenButton);

							// ClickHandler für den Anzeigen-Button hinzufügen.
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									// Besuch in die Datenbank einfügen.
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
						
						boolean befuellt = pruefeLeereTable();
						
						if (befuellt == true) {
							
							ueberschriftLabel.setVisible(false);
							partnervorschlaegeNpFlexTable.setVisible(false);
							
							informationLabel.setText("Sie haben zurzeit keine unangesehenen Partnervorschläge.");
						}
					}
				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(ergebnisLabel);
		verPanel.add(infoLabel);
		verPanel.add(informationLabel);
		verPanel.add(partnervorschlaegeNpFlexTable);

	}
}
