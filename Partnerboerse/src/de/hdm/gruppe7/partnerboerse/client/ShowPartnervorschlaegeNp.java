package de.hdm.gruppe7.partnerboerse.client;



import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowPartnervorschlaegeNp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	public String geschlecht;
	public String koerpergroesse;
	public String haarfarbe;
	public String raucher;
	public String religion;
	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowPartnervorschlaegeNp() {
		
		this.add(verPanel);

		/**
		 * Überschrift-Label hinzufügen.
		 */
		final Label ueberschriftLabel = new Label(
				"Diese Nutzerprofile koennten zu ihnen passen");
		this.add(ueberschriftLabel);
		verPanel.add(ueberschriftLabel); 
		
		/**
		 * Tabelle zur Anzeige der Partnervorschlaege hinzufuegen. 
		 */
		final FlexTable partnervorschlaegeNpFlexTable = new FlexTable(); 
		verPanel.add(partnervorschlaegeNpFlexTable); 
		
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
		 * PartnervorschlaegeNP anzeigen in den folgenden Zeilen 
		 */
		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		
		final Label infoLabel = new Label();

	
		ClientsideSettings
		.getPartnerboerseAdministration()
		.getNutzerprofilById(
				Benutzer.getProfilId(),
				new AsyncCallback<Nutzerprofil>() {
				
					public void onFailure(
							Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					
					public void onSuccess(Nutzerprofil benutzer) {
						geschlecht = benutzer.getGeschlecht();
						koerpergroesse = benutzer.getKoerpergroesse();
						haarfarbe = benutzer.getHaarfarbe();
						raucher = benutzer.getRaucher();
						religion = benutzer.getReligion();
						
					}
				


					
					
					
				});
		ClientsideSettings.getPartnerboerseAdministration()
		.getAllProfile(new AsyncCallback<List<Nutzerprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(List<Nutzerprofil> result) {
						int row = partnervorschlaegeNpFlexTable.getRowCount();
						
						for (Nutzerprofil np : result) {
							row++;
							
							int uebereinstimmung = 0;
							final String ProfilId = String.valueOf(np.getProfilId());
							partnervorschlaegeNpFlexTable.setText(row, 0, ProfilId);
							
							if (np.getGeschlecht() == geschlecht)
								uebereinstimmung = uebereinstimmung + 1;	
						
							if (np.getHaarfarbe() ==  haarfarbe)
								uebereinstimmung = uebereinstimmung + 1;
							
							if (np.getKoerpergroesse() ==  koerpergroesse)
								uebereinstimmung = uebereinstimmung + 1;
							
							if (np.getRaucher() ==  raucher)
								uebereinstimmung = uebereinstimmung + 1;
							
							if (np.getReligion() ==  religion)
								uebereinstimmung = uebereinstimmung + 1;
							
							int ergebnis = (100 / 5)* uebereinstimmung;
							
							partnervorschlaegeNpFlexTable.setText(row, 1, String.valueOf(ergebnis));
							partnervorschlaegeNpFlexTable.setText(row, 2, np.getVorname()); 
							partnervorschlaegeNpFlexTable.setText(row, 3, np.getNachname());
							partnervorschlaegeNpFlexTable.setText(row, 4, np.getGeburtsdatum());
							partnervorschlaegeNpFlexTable.setText(row, 5, np.getGeschlecht()); 
							
							// Anzeigen-Button hinzufügen und ausbauen. 
							final Button anzeigenButton = new Button("Anzeigen");
							partnervorschlaegeNpFlexTable.setWidget(row, 6, anzeigenButton); 
			
						
						// ClickHandler für den Anzeigen-Button hinzufügen. 
						anzeigenButton.addClickHandler(new ClickHandler(){
							public void onClick(ClickEvent event) {
								ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(ProfilId)); 
								RootPanel.get("Details").clear(); 
								RootPanel.get("Details").add(showFremdprofil); 
								
							}
							
						}); 
						}
					}

				});
		
	}
}