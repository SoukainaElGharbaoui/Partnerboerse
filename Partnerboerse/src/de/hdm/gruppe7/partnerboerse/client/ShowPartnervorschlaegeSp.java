package de.hdm.gruppe7.partnerboerse.client;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowPartnervorschlaegeSp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	public String geschlecht;
	public String haarfarbe;
	public String religion;
	public String raucher;
	public String koerpergroesse;
	public String eigenschaftId;
	

	/**
	 * Konstruktor hinzufügen.
	 * @param a 
	 */
	public ShowPartnervorschlaegeSp() {
		this.add(verPanel);

		/**
		 * Überschrift-Label hinzufügen.
		 */
		final Label ueberschriftLabel = new Label(
				"Diese Nutzerprofile koennten zu ihnen passen");
		this.add(ueberschriftLabel);
		
		verPanel.add(ueberschriftLabel); 
		
		/**
		 * Tabelle zur Anzeige der gemerkten Kontakte hinzufügen.
		 */
		final FlexTable partnervorschlaegeSpFlexTable = new FlexTable();
		 
//		final CellTable<Nutzerprofil> partner = new CellTable<Nutzerprofil>();
		
		verPanel.add(  partnervorschlaegeSpFlexTable);
		
//		 ListDataProvider<Nutzerprofil> dataProvider = new ListDataProvider<Nutzerprofil>();
//		 dataProvider.addDataDisplay(partner);
		
		
		/** 
		 * Tabelle formatieren und CSS einbinden. 
		 */
		partnervorschlaegeSpFlexTable.setCellPadding(6);
		partnervorschlaegeSpFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		partnervorschlaegeSpFlexTable.addStyleName("FlexTable");   
		
		
		/**
		 * Erste Zeile der Tabelle festlegen. 
		 */
		
		partnervorschlaegeSpFlexTable.setText(0, 0, "F-ID");
		partnervorschlaegeSpFlexTable.setText(0, 1, "Uebereinstimmung in %");
		partnervorschlaegeSpFlexTable.setText(0, 2, "Vorname");
		partnervorschlaegeSpFlexTable.setText(0, 3, "Nachname");
		partnervorschlaegeSpFlexTable.setText(0, 4, "Alter");
		partnervorschlaegeSpFlexTable.setText(0, 5, "Geschlecht");
		partnervorschlaegeSpFlexTable.setText(0, 6, "Anzeigen");
		
		
		/**
		 * PartnervorschlaegeSP anzeigen in den folgenden Zeilen 
		 */
	
																					
														
		ClientsideSettings	.getPartnerboerseAdministration()
			.getSuchprofilById(Benutzer.getProfilId(),new AsyncCallback<Suchprofil>() {

						@Override
						public void onFailure(Throwable caught) {
															
														

						}

						@Override
						public void onSuccess(Suchprofil result2 ){
										
								 haarfarbe = result2.getHaarfarbe();
								 geschlecht = result2.getGeschlecht();
								koerpergroesse = result2.getKoerpergroesse();
								religion = result2.getReligion();
								 raucher = result2.getRaucher();
																				
														
														
														
														

					}

				});
		
		
//		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaftenA(new AsyncCallback<List<Eigenschaft>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(List<Eigenschaft> result) {
//				
//				
//				
//				eigenschaftId = String.valueOf(result.get(0).getEigenschaftId());
//						
//				
//			}
//
//			
//			
//			
//		});
						
								
		
				
									
		// Aufruf des Suchprofils, welches zum Vergleich gentzt wird						
								
		Nutzerprofil nutzerprofil = new Nutzerprofil();

		ClientsideSettings.getPartnerboerseAdministration()
				.getAllNutzerprofile(nutzerprofil,
						new AsyncCallback<List<Nutzerprofil>>() {

							@Override
							public void onFailure(Throwable caught) {
								

							}

							public void onSuccess(List<Nutzerprofil> result) {
								
														//Variable festlegen die zur Speicherung der
														//�bereinstimmungen genutzt wird
								
														int uebreinstimmung = 0;
															
														    
														  
														
														// Anzahl der Zeilen in der FlexTable ermitteln. 
								
														int row =  partnervorschlaegeSpFlexTable.getRowCount();
														
														// Durchlaufen der Elemente aus result, bei jedem Duchlauf 
														//werden die Eigenschaften verglichen
														
														for(Nutzerprofil m : result){
															
															row++;
																											
															if (geschlecht == m.getGeschlecht())
																	
																uebreinstimmung = uebreinstimmung + 1;	
															
															
															if (haarfarbe ==  m.getHaarfarbe())
																	
																uebreinstimmung = uebreinstimmung + 1;

															
															if (koerpergroesse ==  m.getKoerpergroesse())
																uebreinstimmung = uebreinstimmung + 1;
															

															if (raucher ==  m.getRaucher())
																	
																uebreinstimmung= uebreinstimmung + 1;
															
															

															if (religion ==  m.getReligion())
																	
																uebreinstimmung = uebreinstimmung + 1;
															
															
															
															
															
															// die Anzahl der Uebereinstimmungen wird in prozent umgerechnet 
															//und in der Variable prozent gespeichert
															
															int prozent = (100 / 5)* uebreinstimmung;
															
														
															
															// die Variable muss f�r den n�chsten Durchlauf auf null gesetzt werden	
															uebreinstimmung = 0;
															
														
															
															//Berechnung des Alters 
															
//															GregorianCalendar geburtstag = new GregorianCalendar();
//													        geburtstag.setTime(m.getGeburtsdatum());
//													        GregorianCalendar heute = new GregorianCalendar();
//													        int alter = heute.get(Calendar.YEAR) - geburtstag.get(Calendar.YEAR);
//													        if (heute.get(Calendar.MONTH) < geburtstag.get(Calendar.MONTH))
//													        {
//													            alter = alter - 1;
//													        }
//													        else if (heute.get(Calendar.MONTH) == geburtstag.get(Calendar.MONTH))
//													        {
//													            if (heute.get(Calendar.DATE) <= geburtstag.get(Calendar.DATE))
//													            {
//													                alter = alter - 1;
//													            }
//													        }
															

															
																// if
															// (suchprofil.getAlterMax()
															// <=
															// nutzerprofil.getGeburtsdatum())
															// return a;

															// if
															// (suchprofil.getAlterMin()
															// >=
															// nutzerprofil.getGeburtsdatum())
															// return a;
													
															
															

														
															

															
															
															
															
															
															 
														
														// die FlexTable wird mit den Werten der Nutzerprofile und der 
														//Uebereinstimmungen in prozent gef�llt
															
															final String nutzerprofilId = String.valueOf(m.getProfilId());
															partnervorschlaegeSpFlexTable.setText(row, 0, nutzerprofilId); 
															partnervorschlaegeSpFlexTable.setText(row, 1, String.valueOf(prozent) + "%" ); 
															partnervorschlaegeSpFlexTable.setText(row, 2, m.getVorname());
															partnervorschlaegeSpFlexTable.setText(row, 3, m.getNachname());
															partnervorschlaegeSpFlexTable.setText(row, 4, m.getGeburtsdatum());
															partnervorschlaegeSpFlexTable.setText(row, 5, m.getGeschlecht());
															
//															TextColumn<Nutzerprofil> fremdIdSpalte = new TextColumn<Nutzerprofil>() {
//																public String getValue (Nutzerprofil result){
//																	return String.valueOf(result.getProfilId());
//																}
//																
//															};
////															
//															partner.addColumn(fremdIdSpalte, "F-Id");
////															
////													
////															
//															partner.addColumn(aehnlichkeitSpalte, "Aehnlichkeit");
//															
//															
////								
////															
////
//															TextColumn<Nutzerprofil> nameSpalte = new TextColumn<Nutzerprofil>() {
//																public String getValue (Nutzerprofil result){
//																	return result.getVorname();
//																}
//																
//															};
//															
//															partner.addColumn(nameSpalte, "Name");
//															
//															partner.setRowData(0, result);
															
														
															
															// Anzeigen-Button hinzufügen und ausbauen. 
															final Button anzeigenButton = new Button("Anzeigen");
															partnervorschlaegeSpFlexTable.setWidget(row, 6, anzeigenButton);
//															
															// ClickHandler für den Anzeigen-Button hinzufügen. 
															anzeigenButton.addClickHandler(new ClickHandler(){
																public void onClick(ClickEvent event) {
																	ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(nutzerprofilId)); 
																	RootPanel.get("Details").clear(); 
																	RootPanel.get("Details").add(showFremdprofil); 
																	
																	
																	
																	
												}

											});

								}

							}

						});

	}

}
