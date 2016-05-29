package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;

public class EditInfo extends VerticalPanel {

	/**
	 * VerticalPanels hinzufügen.
	 */

	private VerticalPanel verPanel = new VerticalPanel();
	Button loeschenButton = new Button("Löschen");
	


//	private int neueAuswahloptionId;
//	int eigenschaftIdA;
//	String bisherigeAuswahloption;
//	private int nutzerprofilIdInt;
//	private int eigenschaftIdInt;
//	private String infotext;
//	private String typ;
	
	private FlexTable editInfoFlexTable = new FlexTable();
	private int row;

	
	/**
	 * Konstruktor hinzufügen.
	 */

	public EditInfo() {
		this.add(verPanel);

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */

		editInfoFlexTable.setCellPadding(6);
		editInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		editInfoFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		editInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		editInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		editInfoFlexTable.setText(0, 2, "Eigenschaft");
		editInfoFlexTable.setText(0, 3, "Bearbeiten");
		editInfoFlexTable.setText(0, 4, "Löschen");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		final Label infoLabelProfilId = new Label();
		final Label infoLabelLoeschenB = new Label();
		final Label infoLabelSaveIB = new Label();

		final Label infoLabelLoeschenA = new Label();
		final Label infoLabelInfosA = new Label();
		final Label infoLabelOptionen = new Label();
		final Label infoLabelInfoA = new Label();
		final Label infoLabelSaveIA = new Label();
		final Label informationLabel = new Label();


		final Label ueberschriftLabel = new Label("Info bearbeiten:");
		ueberschriftLabel.addStyleName("partnerboerse-label");
		final Button updateInfosButton = new Button("&Auml;nderungen speichern");


		ClientsideSettings.getPartnerboerseAdministration().getAllInfosNeu(Benutzer.getProfilId(), 
				new AsyncCallback<List<String>>(){

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Fehler");						
					}

					@Override
					public void onSuccess(List<String> result) {
						informationLabel.setText("Das Anzeigen der Infos hat funktioniert.");			
						
						row = editInfoFlexTable.getRowCount();

						int size = result.size();
							
						for (int i = 0; i < size; i++) {

						String nutzerprofilId = result.get(i);
						String eigenschaftId = result.get(i+1);
						String erlaeuterung = result.get(i+2);
						final String infotext = result.get(i+3);
						final String typ = result.get(i+4);
						
						editInfoFlexTable.setText(row, 0, nutzerprofilId);
						editInfoFlexTable.setText(row, 1, eigenschaftId);
						editInfoFlexTable.setText(row, 2, erlaeuterung);
						
						final int nutzerprofilIdInt = Integer.valueOf(nutzerprofilId);
						final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);
						
						
						if (typ == "B") {
							
							TextBox tb = new TextBox();
							tb.setText(infotext);
							editInfoFlexTable.setWidget(row, 3, tb);
						}
						
						
						else if (typ == "A") {
							
							final ListBox lb = new ListBox();
							editInfoFlexTable.setWidget(row, 3, lb);
							
														
							ClientsideSettings.getPartnerboerseAdministration().getEigAById(
									eigenschaftIdInt, new AsyncCallback<Auswahleigenschaft>() {

										@Override
										public void onFailure(Throwable caught) {
											informationLabel.setText("Fehler bei Holen der Auswahloptionen.");													
										}

										@Override
										public void onSuccess(
												Auswahleigenschaft result) {
											
											List<String> optionen = new ArrayList<String>();
											optionen = result.getOptionen();
											
											for (int o = 0; o < optionen.size(); o++) {
												lb.addItem(optionen.get(o));
												
												informationLabel.setText("Das Herausholen der Auswahloptionen "
														+ "hat funktioniert.");
											}
											
											for (int a = 0; a < lb.getItemCount(); a++) {
												
												if (lb.getValue(a).equals(infotext)) {
													lb.setItemSelected(a, true);	
												}
												
												informationLabel.setText("Das Setzen der bisher "
														+ "ausgewählten Option funktioniert.");
											}
										}
									});
						}
 						
						loeschenButton = new Button("Löschen");
						editInfoFlexTable.setWidget(row, 4, loeschenButton);
						
						loeschenButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
									
									ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoNeu
									(nutzerprofilIdInt, eigenschaftIdInt, 
											new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											informationLabel.setText("Beim Löschen der Info trat ein Fehler auf.");																
										}

										@Override
										public void onSuccess(Void result) {
											informationLabel.setText("Das Löschen der Info hat funktioniert.");		
										}
									});

									editInfoFlexTable.removeRow(row);;								
							}
						});
							
						row++; 
						i++; i++; i++; i++; 
//						if (row == 3) {
//							break;
//						}

						}
						}
			});
		
		
						
						

//						loeschenButton.addClickHandler(new ClickHandler() {
//							public void onClick(ClickEvent event) {
//
//								for (int i = 2; i <= editInfoFlexTable
//										.getRowCount();) {
//
//									ClientsideSettings
//											.getPartnerboerseAdministration()
//											.deleteOneInfoB(
//													Benutzer.getProfilId(),
//													Integer.valueOf(eigenschaftId),
//													new AsyncCallback<Void>() {
//
//														@Override
//														public void onFailure(
//																Throwable caught) {
//															infoLabelLoeschenB
//																	.setText("Es trat ein Fehler auf");
//														}
//
//														@Override
//														public void onSuccess(
//																Void result) {
//															infoLabelLoeschenB
//																	.setText("Die Beschreibungsinfo wurde erfolgreich gelöscht");
//														}
//
//													});
//
//									editInfoFlexTable.removeRow(i);
//									break;
//								}
//							}
//						});

						
		
		
//		/**
//		 * GUI für Beschreibungsinfo
//		 */
//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosB(
//				Benutzer.getProfilId(), new AsyncCallback<List<Beschreibungsinfo>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						infoLabelProfilId.setText("Es trat ein Fehler auf.");
//					}
//
//					@Override
//					public void onSuccess(List<Beschreibungsinfo> result) {
//
//						infoLabelProfilId
//								.setText("Die Profil-Id wurde erfolgreich ermittelt.");
//
//						// Anzahl der Zeilen ermitteln.
//						int row = editInfoFlexTable.getRowCount();
//
//						// Tabelle mit Inhalten aus der Datenbank befüllen.
//						for (Beschreibungsinfo iB : result) {
//							row++;
//
//							final String eigenschaftId = String.valueOf(iB
//									.getEigenschaftId());
//							final String nutzerprofilId = String.valueOf(iB
//									.getNutzerprofilId());
//							
//							editInfoFlexTable.setText(row, 0, nutzerprofilId);
//							editInfoFlexTable.setText(row, 1, eigenschaftId);
//
//							final TextArea textArea = new TextArea();
//							textArea.setText(iB.getInfotext());
//
//							editInfoFlexTable.setWidget(row, 3, textArea);
//
//							final Button loeschenButton = new Button("Löschen");
//							editInfoFlexTable.setWidget(row, 4, loeschenButton);
//
//							loeschenButton.addClickHandler(new ClickHandler() {
//								public void onClick(ClickEvent event) {
//
//									for (int i = 2; i <= editInfoFlexTable
//											.getRowCount();) {
//
//										ClientsideSettings
//												.getPartnerboerseAdministration()
//												.deleteOneInfoB(
//														Benutzer.getProfilId(),
//														Integer.valueOf(eigenschaftId),
//														new AsyncCallback<Void>() {
//
//															@Override
//															public void onFailure(
//																	Throwable caught) {
//																infoLabelLoeschenB
//																		.setText("Es trat ein Fehler auf");
//															}
//
//															@Override
//															public void onSuccess(
//																	Void result) {
//																infoLabelLoeschenB
//																		.setText("Die Beschreibungsinfo wurde erfolgreich gelöscht");
//															}
//
//														});
//
//										editInfoFlexTable.removeRow(i);
//										break;
//									}
//								}
//							});
//
//							updateInfosButton
//									.addClickHandler(new ClickHandler() {
//										public void onClick(ClickEvent event) {
//
//											String neuerInfotext = textArea
//													.getText();
//
//											ClientsideSettings
//													.getPartnerboerseAdministration()
//													.saveInfoB(
//															Benutzer.getProfilId(),
//															Integer.valueOf(eigenschaftId),
//															neuerInfotext,
//															new AsyncCallback<Void>() {
//
//																@Override
//																public void onFailure(
//																		Throwable caught) {
//
//																	infoLabelSaveIB
//																			.setText("Beim Speichern des neuen Infotextes trat ein Fehler auf");
//																}
//
//																@Override
//																public void onSuccess(
//																		Void result) {
//																	infoLabelSaveIB
//																			.setText("Das Aktualisieren des Infotextes war erfolgreich");
//																}
//															});
//										}
//									});
//
//						}
//					}
//				});
//
//		/**
//		 * GUI für Auswahlinfo
//		 */
//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosA(
//				Benutzer.getProfilId(), new AsyncCallback<List<Info>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						infoLabelInfosA.setText("Es trat ein Fehler auf.");
//					}
//
//					@Override
//					public void onSuccess(List<Info> result) {
//
//						int row = editInfoFlexTable.getRowCount();
//
//						// Tabelle mit Inhalten aus der Datenbank befüllen.
//						for (Info iA : result) {
//							row++;
//
//							final String eigenschaftId = String.valueOf(iA
//									.getEigenschaftId());
//							final String nutzerprofilId = String.valueOf(iA
//									.getNutzerprofilId());
//
//							editInfoFlexTable.setText(row, 0, nutzerprofilId);
//							editInfoFlexTable.setText(row, 1, eigenschaftId);
//							editInfoFlexTable.setText(row, 2,
//									iA.getEigenschaftErlaeuterung());
//
//							final ListBox neueListBox = new ListBox();
//							
//							
//
//							ClientsideSettings.getPartnerboerseAdministration()
//									.getOptionById(
//											Integer.valueOf(eigenschaftId),
//											new AsyncCallback<Info>() {
//
//												@Override
//												public void onFailure(
//														Throwable caught) {
//													infoLabelInfosA
//															.setText("Es trat ein Fehler beim Herausholen "
//																	+ "der bisherigen Auswahloption auf");
//												}
//
//												@Override
//												public void onSuccess(
//														Info result) {
//
//													infoLabelInfosA
//															.setText("Das Herausholen der bisherigen Auswahl "
//																	+ "hat funktioniert");
//
//													bisherigeAuswahloption = result
//															.getOptionsbezeichnung();
//												}
//											});
//
//							ClientsideSettings
//									.getPartnerboerseAdministration()
//									.getAllAuswahloptionen(
//											Integer.valueOf(eigenschaftId),
//											new AsyncCallback<List<Auswahloption>>() {
//
//												@Override
//												public void onFailure(
//														Throwable caught) {
//													infoLabelOptionen
//															.setText("Es trat ein Fehler auf");
//												}
//
//												@Override
//												public void onSuccess(
//														List<Auswahloption> result) {
//
//													infoLabelOptionen
//															.setText("Das Festlegen der bisherigen Auswahloption hat "
//																	+ "funktioniert ");
//													
//													for (Auswahloption a : result) {
//														neueListBox.addItem(a
//																.getOptionsbezeichnung());
//														}
//													
//													for (int i = 0; i < neueListBox.getItemCount(); i++) {
//														
//														if (neueListBox.getValue(i).equals(bisherigeAuswahloption)) {
//															neueListBox.setItemSelected(i, true);
//														}
//													}
//												}
//											});
//
//
//							editInfoFlexTable.setWidget(row, 3, neueListBox);
//
//							final Button loeschenButton = new Button("Löschen");
//							editInfoFlexTable.setWidget(row, 4, loeschenButton);
//
//							loeschenButton.addClickHandler(new ClickHandler() {
//								public void onClick(ClickEvent event) {
//
//									for (int i = 2; i <= editInfoFlexTable
//											.getRowCount();) {
//
//										ClientsideSettings
//												.getPartnerboerseAdministration()
//												.deleteOneInfoA(
//														Benutzer.getProfilId(),
//														Integer.valueOf(eigenschaftId),
//														new AsyncCallback<Void>() {
//
//															@Override
//															public void onFailure(
//																	Throwable caught) {
//																infoLabelLoeschenA
//																		.setText("Es trat ein Fehler auf");
//															}
//
//															@Override
//															public void onSuccess(
//																	Void result) {
//																infoLabelLoeschenA
//																		.setText("Die Auswahlinfo wurde erfolgreich gelöscht");
//															}
//
//														});
//
//										editInfoFlexTable.removeRow(i);
//										break;
//									}
//								}
//							});
//
//							updateInfosButton
//									.addClickHandler(new ClickHandler() {
//										public void onClick(ClickEvent event) {
//
//											String optionsbezeichnung = neueListBox
//													.getSelectedItemText();
//											
//
//											ClientsideSettings
//													.getPartnerboerseAdministration()
//													.getInfoAById(
//															optionsbezeichnung,
//															Integer.valueOf(eigenschaftId),
//															new AsyncCallback<Info>() {
//
//																@Override
//																public void onFailure(
//																		Throwable caught) {
//																	infoLabelInfoA
//																			.setText("Es trat ein Fehler beim Herausholen "
//																					+ "der AuswahloptionId auf.");
//																}
//
//																@Override
//																public void onSuccess(
//																		Info result) {
//
//																	infoLabelInfoA
//																			.setText("Das Herausholen der Auswahloptions-Id"
//																					+ " hat funktioniert.");
//																	neueAuswahloptionId = result
//																			.getAuswahloptionId();
//																	eigenschaftIdA = result
//																			.getEigenschaftId();
//																	
//
//																	
//																
//																	ClientsideSettings
//																			.getPartnerboerseAdministration()
//																			.saveInfoA(
//																					Benutzer.getProfilId(),
//																					neueAuswahloptionId,
//																					eigenschaftIdA,
//																					new AsyncCallback<Void>() {
//
//																						@Override
//																						public void onFailure(
//																								Throwable caught) {
//																							infoLabelSaveIA
//																									.setText("Es trat ein Fehler beim Speichern "
//																											+ "der neuen Auswahloption auf.");
//																						}
//
//																						@Override
//																						public void onSuccess(
//																								Void result) {
//																							infoLabelSaveIA
//																									.setText("Das Aktualisieren der Auswahlinfo "
//																											+ "hat funktioniert.");
//
//																							 ShowEigenesNp
//																							 showEigenesNp
//																							 =
//																							 new
//																							 ShowEigenesNp();
//																							 RootPanel
//																							 .get("Details")
//																							 .clear();
//																							 RootPanel
//																							 .get("Details")
//																							 .add(showEigenesNp);
//																						}
//
//																					});
//
//																}
//															});
//										}
//									});
//
//						}
//					}
//				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(editInfoFlexTable);

//		verPanel.add(infoLabelProfilId);
//		verPanel.add(infoLabelLoeschenB);
//		verPanel.add(infoLabelSaveIB);
//
//		verPanel.add(infoLabelLoeschenA);
//		verPanel.add(infoLabelInfosA);
//		verPanel.add(infoLabelOptionen);
//		verPanel.add(infoLabelInfoA);
//		verPanel.add(infoLabelSaveIA);
//
//		verPanel.add(updateInfosButton);
		verPanel.add(informationLabel);

	}
}
