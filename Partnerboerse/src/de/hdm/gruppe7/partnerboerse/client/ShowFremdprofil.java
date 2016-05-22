package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowFremdprofil extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.  
	 */
	private VerticalPanel verPanel1 = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();  
	
	/**
	 * HorizontalPanel hinzufügen.
	 */
	private HorizontalPanel horPanel = new HorizontalPanel(); 
	private HorizontalPanel buttonPanel = new HorizontalPanel(); 
	
	/** 
	 * Konstruktor hinzufügen. 
	 */
	public ShowFremdprofil(final int fremdprofilId) {
		this.add(horPanel); 
		
		/**
		 * Überschrift-Label hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Fremdprofil");
		ueberschriftLabel.addStyleName("partnerboerse-label");
		
		/**
		 * Information-Label hinzufügen.
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
		showFremdprofilFlexTable.setText(5, 0, "Koerpergroesse");
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
						showFremdprofilFlexTable.setText(5, 1, result.getKoerpergroesse());

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
		 * ABSCHNITT MERKLISTE BEGINN: Programmierung "Vermerk setzen" / "Vermerk löschen" Button.
		 */
		// Vermerkstatus überprüfen. 
		ClientsideSettings.getPartnerboerseAdministration().getVermerkStatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				String buttonText = "";

				// Button-Aufschrift entsprechend ermitteltem Vermerkstatus hinzufügen. 
				// Wenn kein Vermerk vorliegt...
				if(result == 0){
					buttonText = "Vermerk setzen"; 
				// Wenn ein Vermerk vorliegt...
				} else {
					buttonText = "Vermerk löschen"; 
				}	
				
				// Button zum VerticalPanel hinzufügen. 
				final Button mButton = new Button(buttonText); 
				buttonPanel.add(mButton);
				
				// ClickHandler hinzufügen. 
				mButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						// Wenn die Button-Aufschrift "Vermerk löschen" lautet... 
						if(mButton.getText() == "Vermerk löschen") {
						
							// Vermerk aus der Datenbank löschen. 
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
							
							// Vermerk in die Datenbank einfügen. 
							ClientsideSettings.getPartnerboerseAdministration().vermerkSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								@Override
								public void onSuccess(Void result) {
									mButton.setText("Vermerk löschen"); 
								}
								
							});
							
						}
						
					}
					
				}); 
				
			}
				
		});
		/**
		 * ABSCHNITT MERKLISTE ENDE: Programmierung "Vermerk setzen" / "Vermerk löschen" Button. 
		 */
		
		/**
		 * ABSCHNITT SPERRLISTE BEGINN: Programmierung "Sperrung setzen" / "Sperrung löschen" Button. 
		 */
		// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde.  
		ClientsideSettings.getPartnerboerseAdministration().getSperrstatusFremdprofil(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {
			
			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf."); 	
			}

			@Override
			public void onSuccess(Integer result) {
				
				String buttonText = "";

				// Button-Aufschrift entsprechend ermitteltem Sperrstatus hinzufügen. 
				if(result == 0){
					buttonText = "Sperrung setzen"; 
				} else {
					buttonText = "Sperrung löschen"; 
				}	
				
				// Button zum VerticalPanel hinzufügen. 
				final Button mButton = new Button(buttonText); 
				buttonPanel.add(mButton);
				
				// ClickHandler hinzufügen. 
				mButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						// Wenn die Button-Aufschrift "Sperrung löschen" lautet... 
						if(mButton.getText() == "Sperrung löschen") {
						
							// Sperrung aus der Datenbank löschen. 
							ClientsideSettings.getPartnerboerseAdministration().sperrungLoeschen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Void result) {
								mButton.setText("Sperrung setzen"); 
							}
							
						  });
							
						} 
						
						// Wenn die Button-Aufschrift "Sperrung setzen" lautet... 
						if(mButton.getText() == "Sperrung setzen") {
							
							// Sperrung in die Datenbank einfügen. 
							ClientsideSettings.getPartnerboerseAdministration().sperrungSetzen(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								@Override
								public void onSuccess(Void result) {
									mButton.setText("Sperrung löschen"); 
								}
									});
							
						ClientsideSettings.getPartnerboerseAdministration().getVermerkStatus(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>() {

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
		 * ABSCHNITT SPERRLISTE ENDE: Programmierung "Sperrung setzen" / "Sperrung löschen" Button. 
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
