package de.hdm.gruppe7.partnerboerse.client;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
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
		
		final Label ergebnisLabel = new Label();
		this.add(ergebnisLabel);
		
		verPanel.add(ueberschriftLabel); 
		
		/**
		 * Tabelle zur Anzeige der gemerkten Kontakte hinzufügen.
		 */

		final CellTable<Nutzerprofil> partner = new CellTable<Nutzerprofil>();
				
		verPanel.add(partner);
		
		ListDataProvider<Nutzerprofil> dataProvider = new ListDataProvider<Nutzerprofil>();
		
		dataProvider.addDataDisplay(partner);
		
		/**
		 * PartnervorschlaegeSP anzeigen in den folgenden Zeilen 
		 */
	
									Suchprofil suchprofil = new Suchprofil();	
									Nutzerprofil nutzerprofil= new Nutzerprofil();
									
		ClientsideSettings	.getPartnerboerseAdministration().getAllNutzerprofile( new AsyncCallback<List<Nutzerprofil>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<Nutzerprofil> result) {
				
				TextColumn<Nutzerprofil> nameColumn = new TextColumn<Nutzerprofil>() {
					      @Override
					      public String getValue(Nutzerprofil result) {
					        return result.getVorname() + " " + result.getNachname();
					      }
					      
					     
					    };
				
				partner.addColumn(nameColumn, "Name");
				
				partner.setRowData(0, result);
				
				
				for (Nutzerprofil m : result){
					
					final int fremdprofilId = m.getProfilId();
					
					ClientsideSettings	.getPartnerboerseAdministration().aehnlichkeitBerechnen(Benutzer.getProfilId(), fremdprofilId,
				new AsyncCallback<Integer>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Integer result2) {
						
						
					int 	ergebnis = rsult2;
					
					
					nutzerprofil.setAehnlichkeit(result2);
					
//					ergebnisLabel.setText(String.valueOf(ergebnis));
					
					TextColumn<Nutzerprofil> aehnlichkeitColumn = new TextColumn<Nutzerprofil>(){

						@Override
						public String getValue(Nutzerprofil object) {
							
							return String.valueOf(object.getAehnlichkeit());
						}
						
						
					};
					     
					      
					        
					     
					      
					     
					      
					    
					    partner.addColumn(newColumn(boundValue(result2)), newTextCell(), "Aehnlichkeit");
					    
					    
//					    
					    
						
					}
			
			
			
			
		});
					
					
					
					
					
				}
				
				
				
			}
			
			
			
			
		});												
														
		
			 

						
		verPanel.add(ergebnisLabel);
		
		

						
								
		
		
//						
//									
//														
//															
//															
//															
//															
//															
//														
//															
//															//Berechnung des Alters 
//															
////															GregorianCalendar geburtstag = new GregorianCalendar();
////													        geburtstag.setTime(m.getGeburtsdatum());
////													        GregorianCalendar heute = new GregorianCalendar();
////													        int alter = heute.get(Calendar.YEAR) - geburtstag.get(Calendar.YEAR);
////													        if (heute.get(Calendar.MONTH) < geburtstag.get(Calendar.MONTH))
////													        {
////													            alter = alter - 1;
////													        }
////													        else if (heute.get(Calendar.MONTH) == geburtstag.get(Calendar.MONTH))
////													        {
////													            if (heute.get(Calendar.DATE) <= geburtstag.get(Calendar.DATE))
////													            {
////													                alter = alter - 1;
////													            }
////													        }
//															
//
//															
//																// if
//															// (suchprofil.getAlterMax()
//															// <=
//															// nutzerprofil.getGeburtsdatum())
//															// return a;
//
//															// if
//															// (suchprofil.getAlterMin()
//															// >=
//															// nutzerprofil.getGeburtsdatum())
//															// return a;
//													
//															
//															
//
//														
//															
//
//															
//															
//															
//															
//															
//															 
//														
//											
//															
//															
//															
//														
//															
//															// Anzeigen-Button hinzufügen und ausbauen. 
//															final Button anzeigenButton = new Button("Anzeigen");
//															partnervorschlaegeSpFlexTable.setWidget(row, 6, anzeigenButton);
////															
//															// ClickHandler für den Anzeigen-Button hinzufügen. 
//															anzeigenButton.addClickHandler(new ClickHandler(){
//																public void onClick(ClickEvent event) {
//																	ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(nutzerprofilId)); 
//																	RootPanel.get("Details").clear(); 
//																	RootPanel.get("Details").add(showFremdprofil); 
//																	
//																	
//																	
//																	
//												}
//
//											});
//
//								}
//
//							}
//
//						});

	}

}
