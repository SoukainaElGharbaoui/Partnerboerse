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
		 * ABSCHNITT MERKLISTE UND SPERRLISTE BEGINN: 
		 * Programmierung "Vermerk setzen" / "Vermerk löschen", "Sperrung setzen" / "Sperrung löschen" Button.
		 */
		
		// Vermerk-Button erzeugen. 
		final Button mButton = new Button();
		
		// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde. 
		ClientsideSettings.getPartnerboerseAdministration().getSperrstatusFremdprofil(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {

			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");	
			}

			public void onSuccess(Integer result) {
				// Wenn eine Sperrung vorliegt, wird der Vermerk-Button ausgeblendet. 
				if(result == 1){
					mButton.setVisible(false);  
				}			
			}			
		});
		
	 
		// Prüfen, ob Fremdprofil von Benutzer gemerkt wurde. 
		ClientsideSettings.getPartnerboerseAdministration().getVermerkstatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				// Wenn kein Vermerk vorliegt, lautet die Vermerk-Button-Aufschrift "Vermerk setzen". 
				if(result == 0) {
					mButton.setText("Vermerk setzen");
				// Wenn ein Vermerk vorliegt, lautet die Vermerk-Button-Aufschrift "Vermerk löschen". 
				} else {
					mButton.setText("Vermerk löschen");
				}	
				
				// Vermerk-Button zum VerticalPanel hinzufügen. 
				buttonPanel.add(mButton);
				
				// ClickHandler für den Vermerk-Button hinzufügen. 
				mButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						// Wenn die Vermerk-Button-Aufschrift "Vermerk löschen" lautet, wird der Vermerk aus der Datenbank entfernt. 
						if(mButton.getText() == "Vermerk löschen") {
						
							// Vermerk aus der Datenbank entfernen. 
							ClientsideSettings.getPartnerboerseAdministration().vermerkLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Void result) {
								// Vermerk-Button-Aufschrift zu "Vermerk setzen" ändern. 
								mButton.setText("Vermerk setzen"); 
							}
							
						  });
							
						} 
						
						// Wenn die Button-Aufschrift "Vermerk setzen" lautet, wird der Vermerk in die Datenbank eingefügt.  
						if(mButton.getText() == "Vermerk setzen") {
							
							// Vermerk in die Datenbank einfügen. 
							ClientsideSettings.getPartnerboerseAdministration().vermerkSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								// Vermerk-Button-Aufschrift zu "Vermerk löschen" ändern. 
								public void onSuccess(Void result) {
									mButton.setText("Vermerk löschen"); 
								} 
								
							});
							
						}
						
					}
					
				}); 
				
			}
				
		});

		// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde.  
		ClientsideSettings.getPartnerboerseAdministration().getSperrstatusFremdprofil(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				String buttonText = "";
				// Wenn keine Sperrung vorliegt, lautet die Sperrung-Button-Aufschrift "Sperrung setzen". 
				if(result == 0){
					buttonText = "Sperrung setzen"; 
				} else {
				// Wenn eine Sperrung vorliegt, lautet die Sperrung-Button-Aufschrift "Sperrung löschen". 
					buttonText = "Sperrung löschen"; 
				}	
				
				// Sperrung-Button zum VerticalPanel hinzufügen. 
				final Button sButton = new Button(buttonText); 
				buttonPanel.add(sButton); 
				
				// ClickHandler für den Sperrung-Button hinzufügen. 
				sButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						// Wenn die Button-Aufschrift "Sperrung löschen" lautet, wird die Sperrung aus der Datenbank entfernt. 
						if(sButton.getText() == "Sperrung löschen") {
						
							// Sperrung aus der Datenbank entfernen.
							ClientsideSettings.getPartnerboerseAdministration().sperrungLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							public void onSuccess(Void result) {
								// Sperrung-Button-Aufschrift zu "Sperrung setzen" ändern. 
								sButton.setText("Sperrung setzen"); 
								// Seite aktualisieren. 
								ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId)); 
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showFremdprofil);
							}
							
						  });
							
						} 
						
						// Wenn die Button-Aufschrift "Sperrung setzen" lautet, wird die Sperrung in die Datenbank eingefügt. 
						if(sButton.getText() == "Sperrung setzen") {
							
							// Sperrung in die Datenbank einfügen. 
							ClientsideSettings.getPartnerboerseAdministration().sperrungSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								public void onSuccess(Void result) {
									// Sperrung-Button-Aufschrift zu "Sperrung löschen" ändern. 
									sButton.setText("Sperrung löschen"); 
									// Seite aktualisieren. 
									ShowFremdprofil showFremdprofil = new ShowFremdprofil(Integer.valueOf(fremdprofilId)); 
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showFremdprofil);
								}
								
							});
							
							// Vermerkstatus überprüfen. 
							ClientsideSettings.getPartnerboerseAdministration().getVermerkstatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {

								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");	
								}

								public void onSuccess(Integer result) {
									// Wenn ein Vermerk vorliegt, wird dieser aus der Datenbank entfernt. 
									if(result == 1) {
									ClientsideSettings.getPartnerboerseAdministration().vermerkLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>(){

										public void onFailure(Throwable caught) {
											infoLabel.setText("Es trat ein Fehler auf.");	
										}

										public void onSuccess(Void result) {
											// Seite aktualisieren.
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
		 * ABSCHNITT MERKLISTE UND SPERRLISTE BEGINN: 
		 * Programmierung "Vermerk setzen" / "Vermerk löschen", "Sperrung setzen" / "Sperrung löschen" Button.
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
