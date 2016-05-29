package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class EditSuchprofil extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen. 
	 */
	public EditSuchprofil(final String suchprofilName) {
		this.add(verPanel);

		/**
		 *  Überschrift-Label hinzufügen. 
		 */
		final Label ueberschriftLabel = new Label("Suchprofil bearbeiten:");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 *  Button-Panel hinzufügen. 
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 *  Tabelle zur Anzeige und zur Bearbeitung des aktuellen Suchprofils hinzufügen. 
		 */
		final FlexTable editSuchprofilFlexTable = new FlexTable();

		/**
		 *  Tabelle formatieren und CSS einbinden.
		 */
		editSuchprofilFlexTable.setCellPadding(6);
		editSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		editSuchprofilFlexTable.addStyleName("FlexTable");

		/**
		 *  Erste Spalte der Tabelle festlegen.
		 */
		editSuchprofilFlexTable.setText(0, 0, "Suchprofil-Id");
		editSuchprofilFlexTable.setText(1, 0, "Suchprofilname");
		editSuchprofilFlexTable.setText(2, 0, "Geschlecht");
		editSuchprofilFlexTable.setText(3, 0, "Alter von");
		editSuchprofilFlexTable.setText(4, 0, "Alter bis");
		editSuchprofilFlexTable.setText(5, 0, "Körpergröße");
		editSuchprofilFlexTable.setText(6, 0, "Haarfarbe");
		editSuchprofilFlexTable.setText(7, 0, "Raucher");
		editSuchprofilFlexTable.setText(8, 0, "Religion");

		/**
		 * Dritte Spalte der Tabelle festlegen (TextBox/ListBox zur Bearbeitung der Werte). 
		 */
		// Edit-Label hinzufügen. 
		final Label editLabel = new Label();
		
		final Label suchprofilIdLabel = new Label(); 
		editSuchprofilFlexTable.setWidget(0, 2, suchprofilIdLabel); 
		
		final TextBox suchprofilNameTextBox = new TextBox(); 
		editSuchprofilFlexTable.setWidget(1, 2, suchprofilNameTextBox); 

		// Geschlecht-ListBox hinzufügen. 
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editSuchprofilFlexTable.setWidget(2, 2, geschlechtListBox);
		
		// AlterMin-TextBox hinzufügen. 
		final TextBox alterMinTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(3, 2, alterMinTextBox);

		// AlterMax-TextBox hinzufügen. 
		final TextBox alterMaxTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(4, 2, alterMaxTextBox);

		// Körpergröße-TextBox hinzufügen. 
		final TextBox koerpergroesseTextBox = new TextBox();
		editSuchprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);

		// Haarfarbe-ListBox hinzufügen. 
		final ListBox haarfarbeListBox = new ListBox();
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editSuchprofilFlexTable.setWidget(6, 2, haarfarbeListBox);

		// Raucher-ListBox hinzufügen. 
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editSuchprofilFlexTable.setWidget(7, 2, raucherListBox);

		// Religion-ListBox hinzufügen. 
		final ListBox religionListBox = new ListBox();
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editSuchprofilFlexTable.setWidget(8, 2, religionListBox);

		/**
		 * Daten des Suchprofils in die Tabelle einfügen. 
		 */
		// InfoLabel hinzufügen. 
		final Label infoLabel = new Label();
		
		
		
		ClientsideSettings.getPartnerboerseAdministration().getSuchprofilByName(Benutzer.getProfilId(), suchprofilName, 
				new AsyncCallback<Suchprofil>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
						
					}

					public void onSuccess(Suchprofil result) {
						
						final String suchprofilId = String.valueOf(result.getProfilId());
						suchprofilIdLabel.setText(suchprofilId); 
						
						suchprofilNameTextBox.setText(result.getSuchprofilName()); 
						
						// Geschlecht auslesen und einfügen. 		
						for(int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) { 
								geschlechtListBox.setSelectedIndex(i);
							}
						}
						
						// AlterMin auslesen und einfügen. 
						alterMinTextBox.setText(Integer.toString(result.getAlterMinInt()));

						// AlterMax auslesen und einfügen. 
						alterMaxTextBox.setText(Integer.toString(result.getAlterMaxInt()));
						
						// Körpergröße auslesen und einfügen. 
						koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));
						
						// Haarfarbe auslesen und einfügen. 
						for(int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) { 
								haarfarbeListBox.setSelectedIndex(i);
							}
						}
						
						// Raucherstatus auslesen und einfügen. 
						for(int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher().equals(raucherListBox.getValue(i))) { 
								raucherListBox.setSelectedIndex(i);
							}
						}

						// Religion auslesen und einfügen. 
						for(int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion().equals(religionListBox.getValue(i))) { 
								religionListBox.setSelectedIndex(i);
							}
						}
						
					}
			
		});

		/**
		 * Widgets zum VerticalPanel hinzufügen. 
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(editSuchprofilFlexTable);
		verPanel.add(infoLabel);
		verPanel.add(editLabel);

		/**
		 * Änderungen-Speichern-Button hinzufügen und ausbauen.
		 */
		final Button speichernButton = new Button("Suchprofil aktualisieren");
		verPanel.add(buttonPanel);
		buttonPanel.add(speichernButton);

		/**
		 * ClickHandler für den Änderungen-Speichern-Button hinzufügen.
		 */
		final Label informationLabel = new Label();
		verPanel.add(informationLabel);
		
		final Label warnungLabel1 = new Label(); 
		final Label warnungLabel2 = new Label(); 
		final Label warnungLabel3 = new Label(); 

		speichernButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				// Prüfen, ob der Suchprofilname beim Editieren verändert wird.
				ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilnameEdit(Benutzer.getProfilId(), 
						Integer.parseInt(suchprofilIdLabel.getText()), new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Es trat ein Fehler auf."); 
								
							}

							@Override
							public void onSuccess(String result) {
								int suchprofilnameVeraendert = 1; 
								if(result.equals(suchprofilNameTextBox.getText())) {
									suchprofilnameVeraendert = 0; 
								} 
								
								// Wenn der Suchprofilname nicht verändert wurde...
								if(suchprofilnameVeraendert == 0) {
									
									// Prüfen, ob der Suchprofilname leer ist.
									if(suchprofilNameTextBox.getText().isEmpty()) {
										warnungLabel2.setText("Der Suchprofilname darf nicht leer sein."); 
										verPanel.add(warnungLabel2);
										
									} else {
									
									// Prüfen, ob Alter von kleiner als Alter bis ist. 
									if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
										warnungLabel3.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
										verPanel.add(warnungLabel3);
										
										} else {

										// Suchprofil aktualisieren.
										ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(
										Integer.parseInt(suchprofilIdLabel.getText()),
										suchprofilNameTextBox.getText(), 
										geschlechtListBox.getSelectedItemText(),
										Integer.parseInt(alterMinTextBox.getText()), 
										Integer.parseInt(alterMaxTextBox.getText()),
										Integer.parseInt(koerpergroesseTextBox.getText()),
										haarfarbeListBox.getSelectedItemText(),
										raucherListBox.getSelectedItemText(),
										religionListBox.getSelectedItemText(),
										new AsyncCallback<Void>() {
				
											@Override
											public void onFailure(Throwable caught) {
												informationLabel
														.setText("Es trat ein Fehler auf");
											}
				
											@Override
											public void onSuccess(Void result) {
												ShowSuchprofil showSuchprofil = new ShowSuchprofil();
												RootPanel.get("Details").clear();
												RootPanel.get("Details").add(showSuchprofil);
											}
				
										});

										}
								}
									
								}
								
								// Wenn der Suchprofilname verändert wurde...
								if(suchprofilnameVeraendert == 1) {
									// Prüfen, ob der aktualisierte Suchprofilname bereits existiert.
									ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilname(Benutzer.getProfilId(), suchprofilNameTextBox.getText(), 
											new AsyncCallback<Integer>() {
					
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf."); 
												}
					
												public void onSuccess(Integer result) {
													int suchprofilnameVorhanden = 0; 
													if(result == 1) {
													suchprofilnameVorhanden = 1; 
													}
													
													// Wenn der Suchprofilname bereits existiert...
													if(suchprofilnameVorhanden == 1) {
														warnungLabel1.setText("Der Suchprofilname existiert bereits."); 
														verPanel.add(warnungLabel1);
														
													} else {
														
														// Prüfen, ob der Suchprofilname leer ist.
														if(suchprofilNameTextBox.getText().isEmpty()) {
															warnungLabel2.setText("Der Suchprofilname darf nicht leer sein."); 
															verPanel.add(warnungLabel2);
															
														} else {
														
														// Prüfen, ob Alter von kleiner als Alter bis ist. 
														if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
															warnungLabel3.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
															verPanel.add(warnungLabel3);
															
															} else {
					
															// Suchprofil aktualisieren.
															ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(
															Integer.parseInt(suchprofilIdLabel.getText()),
															suchprofilNameTextBox.getText(), 
															geschlechtListBox.getSelectedItemText(),
															Integer.parseInt(alterMinTextBox.getText()), 
															Integer.parseInt(alterMaxTextBox.getText()),
															Integer.parseInt(koerpergroesseTextBox.getText()),
															haarfarbeListBox.getSelectedItemText(),
															raucherListBox.getSelectedItemText(),
															religionListBox.getSelectedItemText(),
															new AsyncCallback<Void>() {
									
																@Override
																public void onFailure(Throwable caught) {
																	informationLabel
																			.setText("Es trat ein Fehler auf");
																}
									
																@Override
																public void onSuccess(Void result) {
																	ShowSuchprofil showSuchprofil = new ShowSuchprofil();
																	RootPanel.get("Details").clear();
																	RootPanel.get("Details").add(showSuchprofil);
																}
									
															});
					
															}
													}}
					
												}
										
									});
								}
								
							}
					
				});
				
//				// Prüfen, ob der Suchprofilname bereits existiert.
//				ClientsideSettings.getPartnerboerseAdministration().pruefeSuchprofilname(Benutzer.getProfilId(), suchprofilNameTextBox.getText(), 
//						new AsyncCallback<Integer>() {
//
//							public void onFailure(Throwable caught) {
//								infoLabel.setText("Es trat ein Fehler auf."); 
//							}
//
//							public void onSuccess(Integer result) {
//								int suchprofilnameVorhanden = 0; 
//								if(result == 1) {
//								suchprofilnameVorhanden = 1; 
//								}
//								
//								// Wenn der Suchprofilname bereits existiert...
//								if(suchprofilnameVorhanden == 1) {
//									warnungLabel1.setText("Der Suchprofilname existiert bereits."); 
//									verPanel.add(warnungLabel1);
//									
//								} else {
//									
//									// Prüfen, ob der Suchprofilname leer ist.
//									if(suchprofilNameTextBox.getText().isEmpty()) {
//										warnungLabel2.setText("Der Suchprofilname darf nicht leer sein."); 
//										verPanel.add(warnungLabel2);
//										
//									} else {
//									
//									// Prüfen, ob Alter von kleiner als Alter bis ist. 
//									if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
//										warnungLabel3.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
//										verPanel.add(warnungLabel3);
//										
//										} else {
//
//										// Suchprofil aktualisieren.
//										ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(
//										Integer.parseInt(suchprofilIdLabel.getText()),
//										suchprofilNameTextBox.getText(), 
//										geschlechtListBox.getSelectedItemText(),
//										Integer.parseInt(alterMinTextBox.getText()), 
//										Integer.parseInt(alterMaxTextBox.getText()),
//										Integer.parseInt(koerpergroesseTextBox.getText()),
//										haarfarbeListBox.getSelectedItemText(),
//										raucherListBox.getSelectedItemText(),
//										religionListBox.getSelectedItemText(),
//										new AsyncCallback<Void>() {
//				
//											@Override
//											public void onFailure(Throwable caught) {
//												informationLabel
//														.setText("Es trat ein Fehler auf");
//											}
//				
//											@Override
//											public void onSuccess(Void result) {
//												ShowSuchprofil showSuchprofil = new ShowSuchprofil();
//												RootPanel.get("Details").clear();
//												RootPanel.get("Details").add(showSuchprofil);
//											}
//				
//										});
//
//										}
//								}}
//
//							}
//					
//				});
				
				
//				
//				
//				
//				if(Integer.parseInt(alterMinTextBox.getText()) > Integer.parseInt(alterMaxTextBox.getText())) {
//					warnungLabel3.setText("'Alter von' muss kleiner als 'Alter bis' sein."); 
//					verPanel.add(warnungLabel3);
//				} else {
//				
//				ClientsideSettings.getPartnerboerseAdministration().saveSuchprofil(
//						Integer.parseInt(suchprofilIdLabel.getText()),
//						suchprofilNameTextBox.getText(), 
//						geschlechtListBox.getSelectedItemText(),
//						Integer.parseInt(alterMinTextBox.getText()), 
//						Integer.parseInt(alterMaxTextBox.getText()),
//						Integer.parseInt(koerpergroesseTextBox.getText()),
//						haarfarbeListBox.getSelectedItemText(),
//						raucherListBox.getSelectedItemText(),
//						religionListBox.getSelectedItemText(),
//						new AsyncCallback<Void>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								informationLabel
//										.setText("Es trat ein Fehler auf");
//							}
//
//							@Override
//							public void onSuccess(Void result) {
//								informationLabel.setText("Das Suchprofil wurde erfolgreich aktualisiert.");
////								ShowSuchprofil showSuchprofil = new ShowSuchprofil();
////								RootPanel.get("Details").clear();
////								RootPanel.get("Details").add(showSuchprofil);
//							}
//
//						});
//
//			}
			}
		});
	}
}
