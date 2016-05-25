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
		 * Variablen
		 */
		int ergebnis = 0; 
	

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
		ueberschriftLabel.addStyleDependentName("partnerboerse-label"); 
		verPanel.add(ueberschriftLabel); 
		
		final Label infoLabel = new Label();
		final Label ergebnisLabel = new Label();
		
		/**
		 * Tabelle zur Anzeige der Partnervorschlaege hinzufuegen. 
		 */
		final FlexTable partnervorschlaegeSpFlexTable = new FlexTable(); 
		
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
		partnervorschlaegeSpFlexTable.setText(0, 4, "Geburtsdatum");
		partnervorschlaegeSpFlexTable.setText(0, 5, "Geschlecht");
		partnervorschlaegeSpFlexTable.setText(0, 6, "Anzeigen");
		
		
		
		ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeSp(Benutzer.getProfilId(), new  AsyncCallback<List<Nutzerprofil>>(){

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf");
				
			}

			@Override
			public void onSuccess(List<Nutzerprofil> result) {
				infoLabel.setText("Es trat kein Fehler auf");
				int row = partnervorschlaegeSpFlexTable.getRowCount();
				
				for (Nutzerprofil np : result){
					
					final int fremdprofilId = np.getProfilId();
					row++;
					partnervorschlaegeSpFlexTable.setText(row, 0, String.valueOf(np.getProfilId())); 
					partnervorschlaegeSpFlexTable.setText(row, 1, String.valueOf(np.getAehnlichkeitSp()) + "%");
					partnervorschlaegeSpFlexTable.setText(row, 2, np.getVorname()); 
					partnervorschlaegeSpFlexTable.setText(row, 3, np.getNachname());
					partnervorschlaegeSpFlexTable.setText(row, 4, String.valueOf(np.getGeburtsdatumDate()));
					partnervorschlaegeSpFlexTable.setText(row, 5, np.getGeschlecht()); 
					
					// Anzeigen-Button hinzufügen und ausbauen. 
					final Button anzeigenButton = new Button("Anzeigen");
					partnervorschlaegeSpFlexTable.setWidget(row, 6, anzeigenButton);
					
					// ClickHandler für den Anzeigen-Button hinzufügen. 
					anzeigenButton.addClickHandler(new ClickHandler(){
						public void onClick(ClickEvent event){
							ShowFremdprofil showFremdprofil = new ShowFremdprofil(fremdprofilId); 
							RootPanel.get("Details").clear(); 
							RootPanel.get("Details").add(showFremdprofil); 
							
							
						}
						
						
					});
					
				}
				
			}
			
			
		});						
		
				
		
			
			
			
												
														
		
	
				
		

								
													

															
															
															
															
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
													
															
															

														
														
														
														
		verPanel.add(ergebnisLabel);
		verPanel.add(infoLabel);
		verPanel.add(partnervorschlaegeSpFlexTable);					


	}

}
