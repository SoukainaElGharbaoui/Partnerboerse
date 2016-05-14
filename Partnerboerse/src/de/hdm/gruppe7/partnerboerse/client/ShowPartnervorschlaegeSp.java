package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		
		verPanel.add(ueberschriftLabel); 
		
		/**
		 * Tabelle zur Anzeige der gemerkten Kontakte hinzufügen.
		 */
		final FlexTable partnervorschlaegeSpFlexTable = new FlexTable();
		verPanel.add(partnervorschlaegeSpFlexTable); 
		
		
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
		partnervorschlaegeSpFlexTable.setText(0, 4, "Alter");
		partnervorschlaegeSpFlexTable.setText(0, 5, "Geschlecht");
		partnervorschlaegeSpFlexTable.setText(0, 6, "Anzeigen");
		
		/**
		 * PartnervorschlaegeSP anzeigen in den folgenden Zeilen 
		 */
	
		
	
		
		Nutzerprofil nutzerprofil = new Nutzerprofil();

		ClientsideSettings.getPartnerboerseAdministration()
				.getAllNutzerprofile(nutzerprofil,
						new AsyncCallback<List<Nutzerprofil>>() {

							@Override
							public void onFailure(Throwable caught) {
								

							}

							@Override
							public void onSuccess(List<Nutzerprofil> result) {

								ClientsideSettings
										.getPartnerboerseAdministration()
										.getSuchprofilById(
												Benutzer.getProfilId(),
												new AsyncCallback<Suchprofil>() {

													@Override
													public void onFailure(
															Throwable caught) {
														

													}

													@Override
													public void onSuccess(
															Suchprofil result2) {
														
														int ergebnis = 0;
														int a1 = 1;
														
														for (int i = 1; i == result.size(); i++) {
																
															if (result2.getGeschlecht() == result.get(i).getGeschlecht())
																	
																	ergebnis = ergebnis + a1;	

															if (result2.getHaarfarbe() ==  result.get(i).getHaarfarbe())
																	
																ergebnis = ergebnis + a1;

															if (result2.getKoerpergroesse() ==  result.get(i).getKoerpergroesse())
																	
																ergebnis = ergebnis + a1;

															if (result2.getRaucher() ==  result.get(i).getRaucher())
																	
																ergebnis = ergebnis + a1;

															if (result2.getReligion() ==  result.get(i).getReligion())
																	
																ergebnis = ergebnis + a1;

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

															int prozent = (100 / 5)* ergebnis;
														partnervorschlaegeSpFlexTable.setText(1,1, prozent + "%");
														}
														
														
														
													

													}

												});

							}

						});
		
		
		


	}

}
