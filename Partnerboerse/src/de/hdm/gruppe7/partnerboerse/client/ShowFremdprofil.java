package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowFremdprofil extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzuf�gen.  
	 */
	private VerticalPanel verPanel1 = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();  
	
	/**
	 * HorizontalPanel hinzuf�gen.
	 */
	private HorizontalPanel horPanel = new HorizontalPanel(); 
	private HorizontalPanel buttonPanel = new HorizontalPanel(); 
	
	/** 
	 * Konstruktor hinzuf�gen. 
	 */
	public ShowFremdprofil(final int fremdprofilId) {
		this.add(horPanel); 
		
		/**
		 * �berschrift-Label hinzuf�gen. 
		 */
		final Label ueberschriftLabel = new Label("Fremdprofil");
		ueberschriftLabel.addStyleName("partnerboerse-label");
		
		/**
		 * Information-Label hinzuf�gen.
		 */
		final Label infoLabel = new Label();
		
		/**
		 * Tabelle zur Anzeige des Fremdprofils erstellen.
		 */
		final FlexTable showFremdprofilFlexTable = new FlexTable();
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showFremdprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showFremdprofilFlexTable.setText(1, 0, "Vorname");
		showFremdprofilFlexTable.setText(2, 0, "Nachname");
		showFremdprofilFlexTable.setText(3, 0, "Geschlecht");
		showFremdprofilFlexTable.setText(4, 0, "Geburtsdatum");
		showFremdprofilFlexTable.setText(5, 0, "Körpergröße");
		showFremdprofilFlexTable.setText(6, 0, "Haarfarbe");
		showFremdprofilFlexTable.setText(7, 0, "Raucherstatus");
		showFremdprofilFlexTable.setText(8, 0, "Religion");
		
		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showFremdprofilFlexTable.setCellPadding(6);
		showFremdprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		showFremdprofilFlexTable.addStyleName("FlexTable");
		
		/**
		 * Entsprechendes Fremdprofil abrufen. 
		 */
		ClientsideSettings.getPartnerboerseAdministration()
		.getFremdprofilById(fremdprofilId,
				new AsyncCallback<Nutzerprofil>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Nutzerprofil result) {

						// Nutzerprofil-Id aus der Datenabank holen
						// und in Tabelle eintragen
						final String nutzerprofilId = String.valueOf(result.getProfilId());
						showFremdprofilFlexTable.setText(0, 1, nutzerprofilId);
						
						// Vorname aus Datenbank aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(1, 1, result.getVorname());

						// Nachname aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(2, 1, result.getNachname());

						// Geschlecht aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(3, 1, result.getGeschlecht());

						// Geburtsdatum aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(4, 1, result.getGeburtsdatum());

						// Koerpergroesse aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));

						// Haarfarbe aus der Datenbank holen
						// und in Tabelle eintragen				
						showFremdprofilFlexTable.setText(6, 1, result.getHaarfarbe());

						// Raucherstatus aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(7, 1, result.getRaucher());
						
						// Religion aus der Datenbank holen
						// und in Tabelle eintragen
						showFremdprofilFlexTable.setText(8, 1, result.getReligion());
				
					}
				});
		
		
				
		/**
		 * ABSCHNITT MERKLISTE BEGINN: Programmierung "Vermerk setzen" / "Vermerk l�schen" Button.
		 */
		
		
		final Button mButton = new Button();
		ClientsideSettings.getPartnerboerseAdministration().getSperrstatusFremdprofil(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");
				
			}

			@Override
			public void onSuccess(Integer result) {
				if(result == 1){
					mButton.setVisible(false);  
				}
			
			}
			
		});
		
	 
		// Vermerkstatus �berpr�fen. 
		ClientsideSettings.getPartnerboerseAdministration().getVermerkstatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				String buttonText = "";

				// Button-Aufschrift entsprechend ermitteltem Vermerkstatus hinzuf�gen. 
				// Wenn kein Vermerk vorliegt...
				if(result == 0) {
					mButton.setText("Vermerk setzen");
				// Wenn ein Vermerk vorliegt...
				} else {
					mButton.setText("Vermerk l�schen");
				}	
				
//				final Button mButton = new Button(buttonText); 
				buttonPanel.add(mButton);
				
				
				// ClickHandler hinzuf�gen. 
				mButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						// Wenn die Button-Aufschrift "Vermerk l�schen" lautet... 
						if(mButton.getText() == "Vermerk l�schen") {
						
							// Vermerk aus der Datenbank l�schen. 
							ClientsideSettings.getPartnerboerseAdministration().vermerkLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Void result) {
								mButton.setText("Vermerk setzen"); 
							}
							
						  });
							
						} 
						
						// Wenn die Button-Aufschrift "Vermerk setzen" lautet... 
						if(mButton.getText() == "Vermerk setzen") {
							
							// Vermerk in die Datenbank einf�gen. 
							ClientsideSettings.getPartnerboerseAdministration().vermerkSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								@Override
								public void onSuccess(Void result) {
									mButton.setText("Vermerk l�schen"); 
								} 
								
							});
							
						}
						
					}
					
				}); 
				
			}
				
		});
		/**
		 * ABSCHNITT MERKLISTE ENDE: Programmierung "Vermerk setzen" / "Vermerk l�schen" Button. 
		 */
		
		/**
		 * ABSCHNITT SPERRLISTE BEGINN: Programmierung "Sperrung setzen" / "Sperrung l�schen" Button. 
		 */
		// Pr�fen, ob Fremdprofil von Benutzer gesperrt wurde.  
		ClientsideSettings.getPartnerboerseAdministration().getSperrstatusFremdprofil(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				String buttonText = "";

				// Button-Aufschrift entsprechend ermitteltem Sperrstatus hinzuf�gen. 
				if(result == 0){
					buttonText = "Sperrung setzen"; 
				} else {
					buttonText = "Sperrung löschen"; 
				}	
				
				// Button zum VerticalPanel hinzuf�gen. 
				final Button sButton = new Button(buttonText); 
				buttonPanel.add(sButton); 
				
				// ClickHandler hinzuf�gen. 
				sButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						// Wenn die Button-Aufschrift "Sperrung l�schen" lautet... 
						if(sButton.getText() == "Sperrung l�schen") {
						
							// Sperrung aus der Datenbank l�schen. 
							ClientsideSettings.getPartnerboerseAdministration().sperrungLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Void result) {
								sButton.setText("Sperrung setzen"); 
								ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId)); 
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showFremdprofil);
							}
							
						  });
							
						} 
						
						// Wenn die Button-Aufschrift "Sperrung setzen" lautet... 
						if(sButton.getText() == "Sperrung setzen") {
							
							// Sperrung in die Datenbank einf�gen. 
							ClientsideSettings.getPartnerboerseAdministration().sperrungSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								@Override
								public void onSuccess(Void result) {
									sButton.setText("Sperrung l�schen"); 
									ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId)); 
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showFremdprofil);
								}
								
							});
							
							ClientsideSettings.getPartnerboerseAdministration().getVermerkstatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");	
								}

								@Override
								public void onSuccess(Integer result) {
									if(result == 1){
									ClientsideSettings.getPartnerboerseAdministration().vermerkLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>(){

										@Override
										public void onFailure(Throwable caught) {
											infoLabel.setText("Es trat ein Fehler auf.");	
										}

										@Override
										public void onSuccess(Void result) {
											infoLabel.setText("Gesperrt, von Merkliste entfernt.");
											ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId)); 
											RootPanel.get("Details").clear();
											RootPanel.get("Details").add(showFremdprofil);
											
										}
										
									});
									} 
									
								}
								
							});
							
							
							
						}
						
					}
					
				}); 
				
			}
				
		});
		/**
		 * ABSCHNITT SPERRLISTE ENDE: Programmierung "Sperrung setzen" / "Sperrung l�schen" Button. 
		 */			
		
		
		verPanel1.add(ueberschriftLabel);
		verPanel1.add(showFremdprofilFlexTable);
		verPanel1.add(infoLabel);
		verPanel1.add(buttonPanel);
		horPanel.add(verPanel1);
		
		ShowFremdinfo fremdinfo = new ShowFremdinfo(fremdprofilId);
		verPanel2.add(fremdinfo);
		horPanel.add(verPanel2);

	}
}
