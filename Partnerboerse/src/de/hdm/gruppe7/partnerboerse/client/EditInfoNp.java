
package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class EditInfoNp extends VerticalPanel {

	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	/**
	 * VerticalPanels hinzufügen.
	 */

	private VerticalPanel verPanel = new VerticalPanel();
	private Button loeschenButton;

	private FlexTable editInfoFlexTable = new FlexTable();
	private Label ueberschriftLabel = new Label("Info bearbeiten:");
	final Button updateInfosButton = new Button("&Auml;nderungen speichern");
	private Label informationLabel = new Label();

	private int row;
	private String infotext;
	private String typ;
	
	private List<Info> listInfos;
	private List<Eigenschaft> listE;
	

	/**
	 * Konstruktor hinzufügen.
	 */

	public EditInfoNp() {
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

		ueberschriftLabel.addStyleName("partnerboerse-label");
		
		
		ClientsideSettings.getPartnerboerseAdministration().getAllInfos(
				new AsyncCallback<Map<List<Info>,List<Eigenschaft>>>() {

			@Override
			public void onFailure(Throwable caught) {
				informationLabel.setText("Es ist ein Fehler beim Herausholen der Infos aufgetreten.");
			}

			@Override
			public void onSuccess(Map<List<Info>, List<Eigenschaft>> result) {
				
				Set<List<Info>> output = result.keySet();
				
				for (List<Info> listI : output) {
					
					if (listI.isEmpty()) {
						return;
					}
					
					else {
					
					listInfos = listI; 
					
					row = editInfoFlexTable.getRowCount();
					
					listE = new ArrayList<Eigenschaft>();
					listE = result.get(listI);
					
					for (int i = 0; i < listI.size(); i++) {
						
						row++;
						infotext = null;
						
						editInfoFlexTable.setText(row, 0, String.valueOf(listI.get(i).getProfilId()));
						editInfoFlexTable.setText(row, 1, String.valueOf(listI.get(i).getEigenschaftId()));
						
						loeschenButton = new Button("Löschen");
						editInfoFlexTable.setWidget(row, 4, loeschenButton);
						
						// löscht die letzte Zeile, egal wo man draufklickt. Aber das nur einmal.
						loeschenButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {

								editInfoFlexTable.removeRow(row);
							}
						});	
						
						
						infotext = listI.get(i).getInfotext();
						
						Eigenschaft e = new Eigenschaft();
						e = listE.get(i);
						
						editInfoFlexTable.setText(row, 2, e.getErlaeuterung());
						
						typ = e.getTyp();
						
						if (typ.equals("B")) {
							
							final TextArea textArea = new TextArea();
							textArea.setText(infotext);
							
							editInfoFlexTable.setWidget(row, 3, textArea);
						}
						
						
//						// löscht die letzte Zeile der Tabelle (egal wo man draufdrückt)
//						loeschenButton.addClickHandler(new ClickHandler() {
//							public void onClick(ClickEvent event) {
//
//								editInfoFlexTable.removeRow(row);
//							}
//						});	
						
					} // innere For-Schleife
					
//					// löscht die letzte Zeile, und nur diese, wenn man darauf klickt.
//					loeschenButton.addClickHandler(new ClickHandler() {
//						public void onClick(ClickEvent event) {
//
//							editInfoFlexTable.removeRow(row);
//						}
//					});	
				}
					
					
		ClientsideSettings.getPartnerboerseAdministration().getAuswahleigenschaften(listE, 
				new AsyncCallback<List<Auswahleigenschaft>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Beim Herausholen der Optionen ist ein "
								+ "Fehler aufgetreten.");
					}

					@Override
					public void onSuccess(List<Auswahleigenschaft> result) {
						
						int row2;
						int i;
												
						for (row2 = 2; row2 < editInfoFlexTable.getRowCount(); row2++) {
							
							i = 0;
							i = row2 - 2;
							
							if (listE.get(i).getTyp().equals("A")) {
								
								Auswahleigenschaft eigA = new Auswahleigenschaft();
								eigA = result.get(i);
								
								List<String> optionen = new ArrayList<String>();
								optionen = eigA.getOptionen();
								
								final ListBox lb = new ListBox();
								informationLabel.setText("Es handelt sich um eine Auswahleigenschaft.");
								
								for (int l = 0; l < optionen.size(); l++) {
									lb.addItem(optionen.get(l));
									
									informationLabel.setText("Das Holen der Auswahloptionen hat funktioniert.");
								}

								for (int a = 0; a < lb.getItemCount(); a++) {

									if (lb.getValue(a).equals(
											listInfos.get(i).getInfotext())) {
										lb.setItemSelected(a, true);
										
										informationLabel.setText("Das Setzen der bisherigen Auswahl hat funktioniert..");
									}
								}
								
								editInfoFlexTable.setWidget(row2, 3, lb);
							}
							
							else if (listE.get(i).getTyp().equals("B")) {
							}
						}
					}
				});
		
//		// löscht die letzte Zeile, aber nur wenn man genau darauf klickt. Sonst nichts anderes.
//			loeschenButton.addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//	
//					editInfoFlexTable.removeRow(row);
//				}
//			});	
		
		
		// löscht an dieser Stelle die erste Zeile und diese Info, wenn man auf die letzte Zeile klickt.
//		loeschenButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				
//				int l;
//				int m;
//				
//				for (l = 2; l < editInfoFlexTable.getRowCount(); l++) {
//					
//					m = 0;
//					m = l - 2;
//					
//					informationLabel.setText(l + ", " + m);
//					
//					int tableEigenschaftId = Integer.valueOf(editInfoFlexTable.getText(l, 1));
//					int eigenschaftIdInt = listInfos.get(m).getEigenschaftId();
//
//					informationLabel.setText(tableEigenschaftId + ", " + eigenschaftIdInt);
//					
//					if (Integer.valueOf(tableEigenschaftId) != eigenschaftIdInt) {
//						informationLabel.setText("Die EigenschaftIds stimmen nicht überein.");
//						
//						break;
//					}
//					
//					else if (Integer.valueOf(tableEigenschaftId) == eigenschaftIdInt) {
//						informationLabel.setText("Die EigenschaftIds stimmen überein.");
//
//						ClientsideSettings.getPartnerboerseAdministration()
//								.deleteOneInfoNeu(eigenschaftIdInt, 
//										new AsyncCallback<Void>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										informationLabel
//												.setText("Beim Löschen der Info trat ein Fehler auf.");
//									}
//
//									@Override
//									public void onSuccess(Void result) {
//										informationLabel.setText("Das Löschen der Info hat funktioniert.");
//									}
//						});
//						
//						editInfoFlexTable.removeRow(l);
//						break;
//					}
//				}
//			}
//		});

		
		
		
		
				} // erste For-Schleife
			}
		});
		
		
		updateInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				List<Info> infos = new ArrayList<Info>();

				for (int i = 2; i < editInfoFlexTable.getRowCount(); i++) {
					
					String infotextTable = null;
					
					String eigenschaftIdTable = editInfoFlexTable
							.getText(i, 1);

					Widget w = editInfoFlexTable.getWidget(i, 3);

					if (w instanceof TextArea) {

						infotextTable = ((TextArea) w).getText();
					}

					else if (w instanceof ListBox) {

						infotextTable = ((ListBox) w).getSelectedItemText();
					}

					Info info = new Info();
					info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
					info.setInfotext(infotextTable);

					infos.add(info);
				}
				
				
				ClientsideSettings.getPartnerboerseAdministration().saveInfo(
						infos, new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel
										.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Void result) {
								informationLabel.setText("Die Infos wurden "
										+ "erfolgreich angelegt.");

								ShowEigenesNp showNp = new ShowEigenesNp(
										nutzerprofil);
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showNp);

							}
						});
					}
				});
				
//		int i; 
//		for (i = 2; i < editInfoFlexTable.getRowCount(); i++) {
//		
//		loeschenButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//
//				editInfoFlexTable.removeRow(i);
//			}
//		});	
//		}
//
//		for (int i = 2; i < editInfoFlexTable.getRowCount(); i++) {
//			
//			Widget w = new Widget();
//			w = editInfoFlexTable.getWidget(i, 4);
//					
//			if (w instanceof Button) {
//
//				loeschenButton.addClickHandler(new ClickHandler() {
//					public void onClick(ClickEvent event) {
//						
//						informationLabel.setText("Loeschen-Klickhandler klappt.");
//					}
//				});
//			}
//		}
//		
		
//		// Verweigert im Moment die Weiterleitung auf die Seite bei Klick auf Bearbeiten-Button
//			loeschenButton.addClickHandler(new ClickHandler() {
//					public void onClick(ClickEvent event) {
//						
//						int l;
//						int m;
//						
//						for (l = 2; l < editInfoFlexTable.getRowCount(); l++) {
//							
//							m = 0;
//							m = l - 2;
//							
//							informationLabel.setText(l + ", " + m);
//							
//							int tableEigenschaftId = Integer.valueOf(editInfoFlexTable.getText(l, 1));
//							int eigenschaftIdInt = listInfos.get(m).getEigenschaftId();
//
//							informationLabel.setText(tableEigenschaftId + ", " + eigenschaftIdInt);
//							
//							if (Integer.valueOf(tableEigenschaftId) != eigenschaftIdInt) {
//								informationLabel.setText("Die EigenschaftIds stimmen nicht überein.");
//							}
//							
//							else if (Integer.valueOf(tableEigenschaftId) == eigenschaftIdInt) {
//								informationLabel.setText("Die EigenschaftIds stimmen überein.");
//
//								ClientsideSettings.getPartnerboerseAdministration()
//										.deleteOneInfoNeu(eigenschaftIdInt, 
//												new AsyncCallback<Void>() {
//
//											@Override
//											public void onFailure(Throwable caught) {
//												informationLabel
//														.setText("Beim Löschen der Info trat ein Fehler auf.");
//											}
//
//											@Override
//											public void onSuccess(Void result) {
//												informationLabel.setText("Das Löschen der Info hat funktioniert.");
//											}
//								});
//								
//								editInfoFlexTable.removeRow(l);
//								break;
//							}
//						}
//					}
//				});

			verPanel.add(ueberschriftLabel);
			verPanel.add(editInfoFlexTable);
			verPanel.add(informationLabel);
			verPanel.add(updateInfosButton);

		}
	}
		
		
		
		
		
		
		
		
		

//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosNeu(new AsyncCallback<List<String>>() {
//
//
//			@Override
//			public void onFailure(Throwable caught) {
//				informationLabel.setText("Fehler");
//			}
//
//			@Override
//			public void onSuccess(List<String> result) {
//				informationLabel.setText("Das Anzeigen der Infos hat funktioniert.");
//
//				row = editInfoFlexTable.getRowCount();
//
//				int size = result.size();
//
//				for (int i = 0; i < size; i++) {
//
//					String profilId = result.get(i);
//					String eigenschaftId = result.get(i + 1);
//					String erlaeuterung = result.get(i + 2);
//					final String infotext = result.get(i + 3);
//					final String typ = result.get(i + 4);
//
//					editInfoFlexTable.setText(row, 0, profilId);
//					editInfoFlexTable.setText(row, 1, eigenschaftId);
//					editInfoFlexTable.setText(row, 2, erlaeuterung);
//
//					profilIdInt = Integer.valueOf(profilId);
//					final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);
//
//					if (typ == "B") {
//
//						final TextBox tb = new TextBox();
//						tb.setText(infotext);
//						editInfoFlexTable.setWidget(row, 3, tb);
//
//						updateInfosButton.addClickHandler(new ClickHandler() {
//							public void onClick(ClickEvent event) {
//
//								String infotextNeuB = tb.getText();
//
//								ClientsideSettings.getPartnerboerseAdministration().saveInfoNeu(
//										eigenschaftIdInt, infotextNeuB, new AsyncCallback<Void>() {
//
//											@Override
//											public void onFailure(Throwable caught) {
//												informationLabel
//														.setText("Beim Aktualisieren ist ein Fehler " + "aufgetreten.");
//											}
//
//											@Override
//											public void onSuccess(Void result) {
//												informationLabel
//														.setText("Das Aktualisieren der Infos " + "hat funktioniert.");
//											}
//
//										});
//							}
//						});
//					}
//
//					else if (typ == "A") {
//
//						final ListBox lb = new ListBox();
//						editInfoFlexTable.setWidget(row, 3, lb);
//
//						ClientsideSettings.getPartnerboerseAdministration().getEigAById(eigenschaftIdInt,
//								new AsyncCallback<Auswahleigenschaft>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										informationLabel.setText("Fehler bei Holen der Auswahloptionen.");
//									}
//
//									@Override
//									public void onSuccess(Auswahleigenschaft result) {
//
//										List<String> optionen = new ArrayList<String>();
//										optionen = result.getOptionen();
//
//										for (int o = 0; o < optionen.size(); o++) {
//											lb.addItem(optionen.get(o));
//
//											informationLabel.setText(
//													"Das Herausholen der Auswahloptionen " + "hat funktioniert.");
//										}
//
//										for (int a = 0; a < lb.getItemCount(); a++) {
//
//											if (lb.getValue(a).equals(infotext)) {
//												lb.setItemSelected(a, true);
//											}
//
//											informationLabel.setText(
//													"Das Setzen der bisher " + "ausgewählten Option funktioniert.");
//										}
//									}
//								});
//						
//						
//						updateInfosButton.addClickHandler(new ClickHandler() {
//							public void onClick(ClickEvent event) {
//						
//						String infotextNeuA = lb.getSelectedValue();
//
//						ClientsideSettings.getPartnerboerseAdministration().saveInfoNeu(eigenschaftIdInt,
//								infotextNeuA, new AsyncCallback<Void>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										informationLabel.setText("Beim Aktualisieren ist ein Fehler " + "aufgetreten.");
//									}
//
//									@Override
//									public void onSuccess(Void result) {
//										informationLabel.setText("Das Aktualisieren der Infos " + "hat funktioniert.");
//										
//										ShowEigenesNp showEigenesNp = new ShowEigenesNp(nutzerprofil);
//										RootPanel.get("Details").clear();
//										RootPanel.get("Details").add(showEigenesNp);
//										
//									}
//
//								});
//							}
//						});
//
//					}
//
//					loeschenButton = new Button("Löschen");
//					editInfoFlexTable.setWidget(row, 4, loeschenButton);
//
//					loeschenButton.addClickHandler(new ClickHandler() {
//						public void onClick(ClickEvent event) {
//
//							for (int i = 1; i < editInfoFlexTable.getRowCount(); i++) {
//
//								String tableEigenschaftId = editInfoFlexTable.getText(i, 1);
//
//								if (Integer.valueOf(tableEigenschaftId) == eigenschaftIdInt) {
//
//									ClientsideSettings.getPartnerboerseAdministration()
//											.deleteOneInfoNeu(eigenschaftIdInt, new AsyncCallback<Void>() {
//
//												@Override
//												public void onFailure(Throwable caught) {
//													informationLabel
//															.setText("Beim Löschen der Info trat ein Fehler auf.");
//												}
//
//												@Override
//												public void onSuccess(Void result) {
//													informationLabel.setText("Das Löschen der Info hat funktioniert.");
//												}
//											});
//									editInfoFlexTable.removeRow(i);
//									break;
//								}
//
//							}
//
//						}
//					});
//
//					// updateInfosButton.addClickHandler(new ClickHandler() {
//					// public void onClick(ClickEvent event) {
//					//
//					// ClientsideSettings.getPartnerboerseAdministration().saveInfoNeu(
//					// profilIdInt, eigenschaftIdInt, infotextNeu,
//					// new AsyncCallback<Void>(){
//					//
//					// @Override
//					// public void onFailure(
//					// Throwable caught) {
//					// informationLabel.setText("Beim Aktualisieren ist ein
//					// Fehler "
//					// + "aufgetreten.");
//					// }
//					//
//					// @Override
//					// public void onSuccess(
//					// Void result) {
//					// informationLabel.setText("Das Aktualisieren der Infos "
//					// + "hat funktioniert.");
//					// }
//					//
//					//
//					// });
//					// }
//					// });
//
//					row++;
//					i++;
//					i++;
//					i++;
//					i++;
//				}
//			}
//		});
