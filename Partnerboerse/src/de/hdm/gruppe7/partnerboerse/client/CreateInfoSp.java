package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateInfoSp extends VerticalPanel {
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private FlexTable showEigenschaftFlexTable = new FlexTable();

	
	private Button createInfosButton = new Button("Suchprofil-Info anlegen");
	private Label ueberschriftLabel = new Label("Suchprofil-Info anlegen:");
	private Label informationLabelB = new Label();
	private Label informationLabelA = new Label();

	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateInfoSp(final int suchprofilId) {
		this.add(verPanel);

		/**
		 * Tabelle zur Anzeige der Eigenschaften hinzufügen.
		 */

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showEigenschaftFlexTable.setText(0, 0, "Eigenschaft-Id");
		showEigenschaftFlexTable.setText(0, 1, "Erlaeuterung");
		showEigenschaftFlexTable.setText(0, 2, "Anlegen");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");

		/**
		 * Überschrift-Label hinzufügen.
		 */

		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Information-Labels für die Benutzerinformation hinzufügen.
		 */

//		ClientsideSettings.getPartnerboerseAdministration()
//				.getAllEigenschaftenNeu(new AsyncCallback<List<Eigenschaft>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						informationLabelB.setText("Es trat ein Fehler auf");
//					}
//
//					@Override
//					public void onSuccess(List<Eigenschaft> result) {
//						int row = showEigenschaftFlexTable.getRowCount();
//
//						for (Eigenschaft e : result) {
//							row++;
//
//							String eigenschaftId = String.valueOf(e.getEigenschaftId());
//							final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);
//
//							showEigenschaftFlexTable.setText(row, 0, eigenschaftId);
//							showEigenschaftFlexTable.setText(row, 1, e.getErlaeuterung());
//
//							if (e.getTyp() == "B") {
//
//								final TextArea textArea = new TextArea();
//								showEigenschaftFlexTable.setWidget(row, 2, textArea);
//
//								ClientsideSettings.getPartnerboerseAdministration().getEigBById(eigenschaftIdInt,
//										new AsyncCallback<Beschreibungseigenschaft>() {
//
//											@Override
//											public void onFailure(Throwable caught) {
//												informationLabelB.setText("Beim Herausholen des Beschreibungstextes "
//														+ "ist ein Fehler aufgetreten.");
//											}
//
//											@Override
//											public void onSuccess(Beschreibungseigenschaft result) {
//												informationLabelB.setText("Das Herausholen des Beschreibungstextes hat "
//														+ "funktioniert.");
//
//												beschreibungstext = result.getBeschreibungstext();
//												textArea.setText(beschreibungstext);
//											}
//										});
//								
//
//								createInfosButton.addClickHandler(new ClickHandler() {
//									public void onClick(ClickEvent event) {
//										
//										nEingabeB = textArea.getText();
//										
//										if (nEingabeB.equals(beschreibungstext)) {
//
//											return;
//										}
//										
//										else if (!nEingabeB.equals(beschreibungstext)) {
//											
//											ClientsideSettings.getPartnerboerseAdministration().createInfoNeuSp(suchprofilId,
//													eigenschaftIdInt, nEingabeB, new AsyncCallback<Info>() {
//											
//															@Override
//															public void onFailure(Throwable caught) {
//																informationLabelB.setText("Es trat ein Fehler auf.");
//															}
//											
//															@Override
//															public void onSuccess(Info result) {
//																informationLabelB.setText("Die Infos wurden "
//																		+ "erfolgreich angelegt.");
//																
//																ShowSuchprofil showSp = new ShowSuchprofil();
//																RootPanel.get("Details").clear();
//																RootPanel.get("Details").add(showSp);
//															}
//												});
//										}
//										
//										else {
//											return;
//										}
//									}
//								}); 
//								
//								
//								createInfosButton.addClickHandler(new ClickHandler() {
//									public void onClick(ClickEvent event) {
//										
//										if (textArea.getText() != null) {
//											
//											if (textArea.getText() != beschreibungstext) {
//												nEingabe = textArea.getText();
//											}
//										}
//										
//										else {
//											informationLabelB.setText("Bitte machen Sie eine Eingabe im Textfeld.");
//										}
//										
//										
//										
////										profilId = Benutzer.getProfilId();
//										
//										ClientsideSettings.getPartnerboerseAdministration().createInfoNeu(suchprofilId,
//											eigenschaftIdInt, nEingabe, new AsyncCallback<Info>() {
//									
//													@Override
//													public void onFailure(Throwable caught) {
//														informationLabelB.setText("Es trat ein Fehler auf.");
//													}
//									
//													@Override
//													public void onSuccess(Info result) {
//														informationLabelB.setText("Die Infos wurden "
//																+ "erfolgreich angelegt." + suchprofilId);
//														
////														ShowEigenesNp showNp = new ShowEigenesNp();
////														RootPanel.get("Details").clear();
////														RootPanel.get("Details").add(showNp);
//													}
//										});
//								}
//								}); 
//
//								else {
//											informationLabelB.setText("Bitte machen Sie eine Eingabe im Textfeld.");
//										}

										// profilId = Benutzer.getProfilId();
//
//										ClientsideSettings.getPartnerboerseAdministration()
//												.createInfoNeuSp(eigenschaftIdInt, nEingabe, new AsyncCallback<Info>() {
//
//													@Override
//													public void onFailure(Throwable caught) {
//														informationLabelB.setText("Es trat ein Fehler auf.");
//													}
//
//													@Override
//													public void onSuccess(Info result) {
//														informationLabelB.setText("Die Infos wurden "
//																+ "erfolgreich angelegt." + suchprofilId);
//
////														 ShowEigenesNp showNp
////														 = new
////														 ShowEigenesNp();
////														 RootPanel.get("Details").clear();
////														 RootPanel.get("Details").add(showNp);
//													}
//												});
//									}
//								});
//>>>>>>> refs/heads/master
//							}
//
//					else if (e.getTyp() == "A") {
//
//								final ListBox lb = new ListBox();
//
//								ClientsideSettings.getPartnerboerseAdministration().getEigAById(eigenschaftIdInt,
//										new AsyncCallback<Auswahleigenschaft>() {
//
//											@Override
//											public void onFailure(Throwable caught) {
//												informationLabelA.setText("Beim Herausholen der Auswahloptionen "
//														+ "ist ein Fehler aufgetreten.");
//											}
//
//											@Override
//											public void onSuccess(Auswahleigenschaft result) {
//
//												List<String> optionen = result.getOptionen();
//
//												for (int i = 0; i < optionen.size(); i++) {
//													lb.addItem(optionen.get(i));
//												}
//												
//												for (int a = 0; a < lb.getItemCount(); a++) {
//													
//													if (lb.getValue(a).equals("Keine Auswahl")) {
//														lb.setItemSelected(a, true);	
//													}
//													
//													informationLabelA.setText("Das Setzen der Standard-Option "
//															+ "hat funktioniert.");
//												}
//											}
//										});
//								
//								showEigenschaftFlexTable.setWidget(row, 2, lb);
//
//								createInfosButton.addClickHandler(new ClickHandler() {
//									public void onClick(ClickEvent event) {
//										
//										nEingabeA = lb.getSelectedItemText();
//										
//										if(!nEingabeA.equals("Keine Auswahl")) {
//										
//											ClientsideSettings.getPartnerboerseAdministration().createInfoNeuSp(suchprofilId,
//													eigenschaftIdInt, nEingabeA, new AsyncCallback<Info>() {
//										
//														@Override
//														public void onFailure(Throwable caught) {
//															informationLabelA.setText("Es trat ein Fehler auf.");
//														}
//										
//														@Override
//														public void onSuccess(Info result) {
//															informationLabelA.setText("Die Infos wurden "
//																	+ "erfolgreich angelegt.");
//																														
//															ShowSuchprofil showSp = new ShowSuchprofil();
//															RootPanel.get("Details").clear();
//															RootPanel.get("Details").add(showSp);
//														}
//											});
//										}
//										
//										else {
//											return;
//										}
//										
//								}
//								}); 
//								
//							} 	// if-Typ == A Klammer
//						}		// for-Schleife ganz oben
//					}			// on success-methode ganz oben
//				
//				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenschaftFlexTable);
		verPanel.add(createInfosButton);
		verPanel.add(informationLabelB);
		verPanel.add(informationLabelA);
	}
}
