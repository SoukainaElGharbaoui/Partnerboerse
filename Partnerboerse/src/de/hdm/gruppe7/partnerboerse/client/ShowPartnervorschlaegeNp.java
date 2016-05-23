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
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;


public class ShowPartnervorschlaegeNp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private String geschlecht;
	private int koerpergroesse;
	private String haarfarbe;
	private String raucher;
	private String religion;
	private String geburtsdatum;
	private int a2;
	private int a3;
	private int a4;
	private int a6;
	private int zwischenergebnis ;
	

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
		ueberschriftLabel.addStyleDependentName("partnerboerse-label"); 
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
		final Label infoLabel2 = new Label();

	
		ClientsideSettings
		.getPartnerboerseAdministration()
		.getNutzerprofilById(
				Benutzer.getProfilId(),
				new AsyncCallback<Nutzerprofil>() {
				
					public void onFailure(
							Throwable caught) {
						infoLabel.setText("Es trat ein hier Fehler auf.");
					}

					
					public void onSuccess(Nutzerprofil benutzer) {
						geschlecht = benutzer.getGeschlecht();
						koerpergroesse = Integer.valueOf(benutzer.getKoerpergroesse());
						haarfarbe = benutzer.getHaarfarbe();
						raucher = benutzer.getRaucher();
						religion = benutzer.getReligion();
						geburtsdatum = benutzer.getGeburtsdatum();
						
						
					}

					
				});
		
//		ClientsideSettings.getPartnerboerseAdministration().getAInfoByProfilId(Benutzer.getProfilId(), new AsyncCallback<List<Info>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				infoLabel.setText("Es trat ein riesen Fehler auf.");
//			}
//
//			@Override
//			public void onSuccess(List<Info> result) {
//				for (Info in : result) {
//					
//					if (in.getEigenschaftId() == 2) {
//						a2 = in.getAuswahloptionId();
//						}
//					
//					if (in.getEigenschaftId() == 3) {
//						a3 = in.getAuswahloptionId();
//						}
//					
//					if (in.getEigenschaftId() == 4) {
//						a4 = in.getAuswahloptionId();
//						}
//					
//					if (in.getEigenschaftId() == 6) {
//						a6 = in.getAuswahloptionId();
//						}
//				}
//				
//			}
//			
//		});
		 verPanel.add(infoLabel);
		 verPanel.add(infoLabel2);
		 
		ClientsideSettings.getPartnerboerseAdministration()
		.getUnangeseheneNutzerprofile(Benutzer.getProfilId(), new AsyncCallback<List<Nutzerprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein kleiner Fehler auf.");
					}

					public void onSuccess(List<Nutzerprofil> result) {
						
						int row = partnervorschlaegeNpFlexTable.getRowCount();
						
						for (Nutzerprofil np : result) {
							row++;
							
							int uebereinstimmung = 0;
							
							final int fremdprofilId = np.getProfilId();
						
							
									
									partnervorschlaegeNpFlexTable.setText(row, 0, String.valueOf(fremdprofilId));
									
//									ClientsideSettings.getPartnerboerseAdministration().getAInfoByProfilId(fremdprofilId, new AsyncCallback<List<Info>>() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//											infoLabel.setText("Es trat ein kleiner Fehler auf.");
//											
//										}
//
//										@Override
//										public void onSuccess(List<Info> result) {
//											int uebereinstimmung = 0;
//											
//											for (Info info : result) {
//												
//												
////											if (info.getEigenschaftId() == 1) {
////												if (a1 == info.getAuswahloptionId()) {
////													uebereinstimmung = uebereinstimmung + 1;
////												}
////												}
//											
//											if (info.getEigenschaftId() == 2 && a2 == info.getAuswahloptionId()) {
////												if (a2 == info.getAuswahloptionId()) {
//													uebereinstimmung = uebereinstimmung + 1;
////												}
//												}
//											
//											if (info.getEigenschaftId() == 3 && a3 == info.getAuswahloptionId()) {
////												if (a3 == info.getAuswahloptionId()) {
//													uebereinstimmung = uebereinstimmung + 1;
//													
////												}
//												}
//											
//											if (info.getEigenschaftId() == 4) {
//												if (a4 == info.getAuswahloptionId()) {
//													uebereinstimmung = uebereinstimmung + 1;
//												}
//												}
//											
////											if (info.getEigenschaftId() == 5) {
////												if (a5 == info.getAuswahloptionId()) {
////													uebereinstimmung = uebereinstimmung + 1;
////												}
////												}
//											
//											if (info.getEigenschaftId() == 6 ) {
//												if (a6 == info.getAuswahloptionId()) {
//													uebereinstimmung = uebereinstimmung + 1;
//												}
//												}
//											
//										}
//											 zwischenergebnis = uebereinstimmung; 
//											
//										}
//									});
									
									if (np.getGeschlecht() != geschlecht){
										uebereinstimmung = uebereinstimmung + 1;	
									}
									
									if (np.getHaarfarbe() ==  haarfarbe){
										uebereinstimmung = uebereinstimmung + 1;
									}
									
									if (Integer.valueOf(np.getKoerpergroesse()) < koerpergroesse + 10 ){
										uebereinstimmung = uebereinstimmung + 1;
									}
									
									else if (Integer.valueOf(np.getKoerpergroesse()) > koerpergroesse - 10 ){
										uebereinstimmung = uebereinstimmung + 1;
									}
									
									if (np.getRaucher() ==  raucher) {
										uebereinstimmung = uebereinstimmung + 1;
									}
									
									if (np.getReligion() ==  religion){ 
										uebereinstimmung = uebereinstimmung + 1;
									}
									
									if (np.getGeburtsdatum() == geburtsdatum){
										uebereinstimmung = uebereinstimmung + 1;
									}
									
									double ergebnis = (100 / 6) * uebereinstimmung;
									
									partnervorschlaegeNpFlexTable.setText(row, 1, String.valueOf(ergebnis) + "%");
									partnervorschlaegeNpFlexTable.setText(row, 2, np.getVorname()); 
									partnervorschlaegeNpFlexTable.setText(row, 3, np.getNachname());
									partnervorschlaegeNpFlexTable.setText(row, 4, np.getGeburtsdatum());
									partnervorschlaegeNpFlexTable.setText(row, 5, np.getGeschlecht()); 
									
									 infoLabel.setText(String.valueOf(zwischenergebnis));
									// Anzeigen-Button hinzufügen und ausbauen. 
									final Button anzeigenButton = new Button("Anzeigen");
									partnervorschlaegeNpFlexTable.setWidget(row, 6, anzeigenButton);
									
									 
									// ClickHandler für den Anzeigen-Button hinzufügen. 
									anzeigenButton.addClickHandler(new ClickHandler(){
										public void onClick(ClickEvent event) {
											
											// Besuch in die Datenbank einfügen. 
											ClientsideSettings.getPartnerboerseAdministration().besuchSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

												@Override
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf.");
												}

												@Override
												public void onSuccess(Void result) {
													ShowFremdprofil showFremdprofil = new ShowFremdprofil(fremdprofilId); 
													RootPanel.get("Details").clear(); 
													RootPanel.get("Details").add(showFremdprofil); 
												}
												
											});
									}
									
								}); 
								
				
						}
					}

				});
		
	}
}