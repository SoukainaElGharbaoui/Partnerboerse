package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
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

/**
 * Diese Klasse dient dazu, eine Info zu bearbeiten.
 */
public class EditInfo extends VerticalPanel {

	/**
	 * Panels erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel(); 

	/**
	 * Listen erzeugen.
	 */
	private List<Info> listInfos;
	private List<Eigenschaft> listE = new ArrayList<Eigenschaft>();

	/**
	 * Attribute erzeugen
	 */
	private int row;
	private int zaehler;
	private int counter;
	private int counter2;
	private int profilId;
	
	private String infotext;
	private String typ;
	private String profiltyp;


	/**
	 * Widgets erzeugen.
	 */
	private FlexTable editInfoFlexTable = new FlexTable();
	
	private Label ueberschriftLabel = new Label("Infos bearbeiten:");
	private Label informationLabel = new Label();
	private Label pfadLabelNpA = new Label("Zurück zu: Profil anzeigen");
	private Label pfadLabelSpA = new Label("Zurück zu: Suchprofil anzeigen");
	
	private Button updateInfosButton = new Button("Infos speichern");
	private Button createInfosButton = new Button("Infos anlegen");
	private Button loeschenButton = new Button("Ausgewählte Infos löschen");
	private Button selectAllInfosButton = new Button("Alle Infos markieren");
	private Button abbrechenButton = new Button("Abbrechen");
	
	private CheckBox cb;
	
	/**
	 * Konstruktor hinzufuegen.
	 * 
	 * @param profilId Die Profil-ID des aktuellen Nutzerprofils.
	 * @param profiltyp Der Profiltyp (Nutzerprofil / Suchprofil).
	 * @param listtyp Der Listtyp der Seite, von der das Bearbeiten der Infos aufgerufen wird (Nutzerprofil / Suchprofil).
	 */
	public EditInfo(int profilId, String profiltyp, String listtyp) {
		this.profilId = profilId;
		this.profiltyp = profiltyp;
		
		run();
	}
	
	/**
	 * Methode erstellen, die den Aufbau der Seite startet.
	 */
	public void run() {

		/**
		 * Vertikales Panel hinzufuegen.
		 */
		this.add(verPanel);

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		editInfoFlexTable.setCellPadding(6);
		editInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		editInfoFlexTable.addStyleName("FlexTable");
		ueberschriftLabel.addStyleName("partnerboerse-label");
		pfadLabelNpA.addStyleName("partnerboerse-zurueckbutton");
		pfadLabelSpA.addStyleName("partnerboerse-zurueckbutton");
		

		/**
		 * Fall, profiltyp gehoert zu Nutzerprofil. 
		 * Bei Klick auf dieses Label wird auf die Seite zum Anzeigen des 
		 * Nutzerprofils geleitet.
		 */
		if (profiltyp.equals("Np")) {
			
			pfadLabelNpA.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);

					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showNp);
				}

			});
			
			/**
			 * Fall, profiltyp gehoert zu Suchprofil. 
			 * Bei Klick auf dieses Label wird auf die Seite zum Anzeigen des 
			 * Suchprofils geleitet.
			 */	
		} else if (profiltyp.equals("Sp")) {

			pfadLabelSpA.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ShowSuchprofil showSp = new ShowSuchprofil(profilId, profiltyp);

					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showSp);
				}
			});
		}

		
		befuelleTabelle();
		

		/**
		 * ClickHandler fuer den Button zum Editieren einer Info erzeugen.
		 * Sobald dieser Button betaetigt wird, werden die Eingaben der Tabelle sowohl auf
		 * Vollstaendigkeit als auch auf Korrektheit ueberprueft. Sind Eingaben
		 * unvollstaendig oder inkorrekt, wird eine entsprechende Information
		 * ueber diesen Zustand ausgegeben. Diese Daten aktualisieren die entsprechenden Daten
		 * in der Datenbank.
		 */
		updateInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				List<Info> infos = new ArrayList<Info>();

				for (int i = 2; i < editInfoFlexTable.getRowCount(); i++) {

					String infotextTable = null;

					int eigenschaftIdTable = Integer.valueOf(editInfoFlexTable.getText(i, 1));

					Widget w = editInfoFlexTable.getWidget(i, 3);

					if (w instanceof TextArea) {

						infotextTable = ((TextArea) w).getText();
						
						/**
						 * Fall, die TextArea ist leer. Dann wird
						 * die entsprechende Info in der Datenbank geloescht.
						 */
						if (((TextArea) w).getText().isEmpty()) {
							informationLabel.setText("Das Eingabefeld ist leer.");

							loescheInfo(eigenschaftIdTable);
						}

						else {
							Info info = new Info();
							info.setProfilId(profilId);
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}
					}

					else if (w instanceof ListBox) {

						infotextTable = ((ListBox) w).getSelectedItemText();
						
						/**
						 * Fall, der Wert der ListBox ist "Keine Auswahl". Dann wird
						 * die entsprechende Info in der Datenbank geloescht.
						 */
						if (infotextTable.equals("Keine Auswahl")) {
							
							loescheInfo(eigenschaftIdTable);
						}

						else {
							Info info = new Info();
							info.setProfilId(profilId);
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}
					}
				}
				speichereInfo(infos);
			}
		});
		
		/**
		 * ClickHandler fuer den Button zum Auswaehlen aller angezeigten Infos erzeugen. 
		 * Sobald dieser Button betaetigt wird, werden alle CheckBoxen der Tabelle ausgewaehlt.
		 */
		selectAllInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				for (int i = 2; i < editInfoFlexTable.getRowCount(); i++) {

					CheckBox cb = (CheckBox) editInfoFlexTable.getWidget(i, 4);
					cb.setValue(true);
					
					editInfoFlexTable.setWidget(i, 4, cb);
				}
			}
		});
		

		/**
		 * ClickHandler fuer den Button zum Anlegen einer Info erzeugen. Sobald
		 * dieser Button betaetigt wird, wird CreateInfo geoeffnet.
		 */
		createInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				CreateInfo createInfo = new CreateInfo(profilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createInfo);
			}
		});
		
		
		/**
		 * ClickHandler fuer den Button zum Abbrechen des Editierens erzeugen. Sobald
		 * dieser Button betaetigt wird, wird die Seite zum Anzeigen des eigenen Nutzerprofils
		 * angezeigt.
		 */
		abbrechenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ShowNutzerprofil showNutzerprofil = new ShowNutzerprofil(profilId, profiltyp); 
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showNutzerprofil);
			}
		});
		
		
		/**
		 * ClickHandler fuer den Button zum Loeschen der ausgewaehlten Infos erzeugen. Sobald
		 * dieser Button betaetigt wird, werden die ausgewaehlten Infos geloescht. Wurden alle
		 * Infos geloescht, wird die Seite zum Anzeigen des eigenen Nutzerprofils angezeigt.
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				for (int i = 2; i < editInfoFlexTable.getRowCount(); i++) {
					
					boolean checked = ((CheckBox) editInfoFlexTable.getWidget(i, 4)).getValue();
					
					 if (checked == true) {
						 counter++;
					 }
				}
				
				if (counter == 0) {
					Window.alert("Es wurde nichts ausgewählt.");
					return;
				}
				
				counter = counter + 2;
				
				if (counter == editInfoFlexTable.getRowCount()) {
					
					ueberschriftLabel.setText("" + counter2);
					informationLabel.setText("Alle ListBoxen sind ausgewählt.");
					
					loescheAlleInfos();
					
					ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showNp);
				}
						 
				
				else {
					
					int rowTable = editInfoFlexTable.getRowCount();

					for (int k = 2; k < rowTable; k++) {
						
						boolean checked2 = ((CheckBox) editInfoFlexTable.getWidget(k, 4)).getValue();
						
						if (checked2 == true) {
							
						 int eigenschaftId2 = Integer.valueOf(editInfoFlexTable.getText(k, 1));
						 
						 loescheInfo(eigenschaftId2);
						 
						 /**
							 * Jeweilige Zeile der
							 * Tabelle loeschen.
							 */
							editInfoFlexTable.removeRow(k);
							k--;
							
							
							/**
							 * Fall, es wurden alle Infos geloescht, dann wird auf die Seite zum Anzeigen des
							 * Nutzerprofils weitergeleitet.
							 */
							if (editInfoFlexTable.getRowCount() == 2) {
								
								ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showNp);
							}
						}
					}
					
					/**
					 * Fall, dass keine CheckBox ausgewaehlt wurde.
					 * Dann wird eine entsprechende Information angezeigt.
					 */
					if (zaehler == 0) {
						Window.alert("Es wurde nichts ausgewählt.");
					}
				}
			}
		});

		
		/**
		 * Widgets zum Panel hinzufuegen.
		 */
		
		if (profiltyp.equals("Np")) {
			verPanel.add(pfadLabelNpA);
		} else if (profiltyp.equals("Sp")) {
			verPanel.add(pfadLabelSpA);
		}
		
		buttonPanel.add(updateInfosButton);
		buttonPanel.add(loeschenButton);
		buttonPanel.add(selectAllInfosButton);
		buttonPanel.add(abbrechenButton);
		
		verPanel.add(ueberschriftLabel);
		verPanel.add(editInfoFlexTable);
		verPanel.add(buttonPanel);
		verPanel.add(informationLabel);
	}
	
	
	/**
	 * Methode, die die Infos anhand der Profil-ID aus der Datenbank ausliest und die Tabelle befuellt.
	 */
	public void befuelleTabelle() {
		
		ClientsideSettings.getPartnerboerseAdministration().getAllInfos(profilId,
				new AsyncCallback<Map<List<Info>, List<Eigenschaft>>>() {

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

								listE = result.get(listI);

								for (int i = 0; i < listI.size(); i++) {
									
									editInfoFlexTable.setCellPadding(6);
									editInfoFlexTable.getColumnFormatter().addStyleName(2, "TableHeader");
									editInfoFlexTable.addStyleName("FlexTable");

									row++;
									infotext = null;
									final int eigenschaftId = listInfos.get(i).getEigenschaftId();

									editInfoFlexTable.setText(row, 0, String.valueOf(listInfos.get(i).getProfilId()));
									editInfoFlexTable.setText(row, 1, String.valueOf(eigenschaftId));

									cb = new CheckBox();
									cb.setValue(false);
									editInfoFlexTable.setWidget(row, 4, cb);
									
									editInfoFlexTable.setText(row, 5, String.valueOf(row));


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
								}
							}
							getAuswahleigenschaften();
						}
					}
				});
	}
	
	
	/**
	 * Methode, die die Optionen der Auswahleigeschaften anhand der Liste
	 * aus Eigenschaften aus der Datenbank ausliest und in die Tabelle einfuegt.
	 */
	
	public void getAuswahleigenschaften() {
		
		ClientsideSettings.getPartnerboerseAdministration().getAuswahleigenschaften(listE,
				new AsyncCallback<List<Auswahleigenschaft>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText(
								"Beim Herausholen der Optionen ist ein " + "Fehler aufgetreten.");
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
								informationLabel
										.setText("Es handelt sich um eine Auswahleigenschaft.");

								for (int l = 0; l < optionen.size(); l++) {
									lb.addItem(optionen.get(l));

									informationLabel.setText(
											"Das Holen der Auswahloptionen hat funktioniert.");
								}

								for (int a = 0; a < lb.getItemCount(); a++) {

									if (lb.getValue(a).equals(listInfos.get(i).getInfotext())) {
										lb.setItemSelected(a, true);

										informationLabel.setText(
												"Das Setzen der bisherigen Auswahl hat funktioniert..");
									}
								}
								editInfoFlexTable.setWidget(row2, 3, lb);
							}

							else if (listE.get(i).getTyp().equals("B")) {
							}
						}
					}
				});
	}

	
	/**
	 * Loescht eine bestimmte Info des aktuellen Nutzerprofils aus der Datenbank.
	 * @param eigenschaftIdTable Id der zu loeschenden Eigenschaft
	 */
	public void loescheInfo(int eigenschaftIdTable) {
	
		ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoNeu(profilId,
				eigenschaftIdTable, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel
								.setText("Beim Löschen der Info ist ein Fehler aufgetreten.");
					}

					@Override
					public void onSuccess(Void result) {
						informationLabel.setText(
								"Die Info wurde gelöscht, da das Eingabefeld geleert wurde.");
					}
		});
	}
	
	
	/**
	 * Methode, die die angelegten Infos eines Nutzerprofils in der Datenbank speichert.
	 * @param infos Liste mit Infos zu einem Nutzerprofil
	 */
	public void speichereInfo(List<Info> infos) {
		
		/**
		 * ClickHandler fuer den Button zum Speichern einer Info
		 * erzeugen. Sobald dieser Button betaetigt wird, werden die
		 * Eingaben als Info zu einem Nutzrprofil oder Suchprofil
		 * gespeichert.
		 */
		ClientsideSettings.getPartnerboerseAdministration().saveInfo(profilId, infos,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(Void result) {
						informationLabel.setText("Die Infos wurden " + "erfolgreich angelegt.");

						if (profiltyp.equals("Np")) {
							ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showNp);
						}

						else if (profiltyp.equals("Sp")) {
							ShowSuchprofil showSp = new ShowSuchprofil(profilId, profiltyp);
							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showSp);
						}
					}
				});
	}
	
	
	/**
	 * Methode, die alle Infos loescht, wenn die Checkboxen aller Infos in der Tabelle ausgewaehlt sind.
	 */
	public void loescheAlleInfos() {
		
		ClientsideSettings.getPartnerboerseAdministration().deleteAllInfosNeu(profilId, 
				new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				informationLabel.setText("Es trat ein Fehler auf.");
			}

			@Override
			public void onSuccess(Void result) {
				informationLabel.setText("Es wurden alle Infos gelöscht.");
			}
		});
	}
	
	/**
	 * Methode, die prueft, ob die Tabelle leer ist.
	 * 
	 * @return boolean Boolscher Wert, der angibt, ob die Tabelle leer ist
	 */
	public boolean pruefeLeereTable() {

		for (int k = 2; k < editInfoFlexTable.getRowCount(); k++) {

			if (editInfoFlexTable.getText(k, 0) == null) {
			}

			else {
				zaehler++;
			}
		}

		if (zaehler == 0) {
			return true;
		}

		else {
			return false;
		}
	}



}