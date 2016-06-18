
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

	/**
	 * VerticalPanels hinzufügen.
	 */

	private VerticalPanel verPanel = new VerticalPanel();
	private Button loeschenButton;

	private FlexTable editInfoFlexTable = new FlexTable();
	private Label ueberschriftLabel = new Label("Infos bearbeiten:");
	final Button updateInfosButton = new Button("Infos speichern");
	private Label informationLabel = new Label();

	private int row;
	private String infotext;
	private String typ;
	
	private List<Info> listInfos;
	private List<Eigenschaft> listE;
	

	/**
	 * Konstruktor hinzufügen.
	 */

	public EditInfoNp(final int profilId) {
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
		editInfoFlexTable.setText(0, 0, "Profil-Id");
		editInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		editInfoFlexTable.setText(0, 2, "Eigenschaft");
		editInfoFlexTable.setText(0, 3, "Bearbeiten");
		editInfoFlexTable.setText(0, 4, "Löschen");

		ueberschriftLabel.addStyleName("partnerboerse-label");
		
		
		ClientsideSettings.getPartnerboerseAdministration().getAllInfos(profilId,
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
						final int eigenschaftId = listInfos.get(i).getEigenschaftId();
						
						
						editInfoFlexTable.setText(row, 0, String.valueOf(listInfos.get(i).getProfilId()));
						editInfoFlexTable.setText(row, 1, String.valueOf(eigenschaftId));
						
						loeschenButton = new Button("Löschen");
						editInfoFlexTable.setWidget(row, 4, loeschenButton);
						
						
						loeschenButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								
								for (int l = 2; l <= editInfoFlexTable.getRowCount(); l++) {
									
									int tableEigenschaftId = Integer.valueOf(editInfoFlexTable.getText(l, 1));
				
									if (Integer.valueOf(tableEigenschaftId) != eigenschaftId) {
									
										informationLabel.setText("Die EigenschaftIds stimmen nicht überein.");
									}
									
									else if (Integer.valueOf(tableEigenschaftId) == eigenschaftId) {
										
										informationLabel.setText("Die EigenschaftIds stimmen überein.");
				
										ClientsideSettings.getPartnerboerseAdministration()
												.deleteOneInfoNeu(profilId, eigenschaftId, 
														new AsyncCallback<Void>() {
				
													@Override
													public void onFailure(Throwable caught) {
														informationLabel
																.setText("Beim Löschen der Info trat ein Fehler auf.");
													}
				
													@Override
													public void onSuccess(Void result) {
														informationLabel.setText("Das Löschen der Info hat funktioniert.");
													}
										});
										
										editInfoFlexTable.removeRow(l);
										break;
									}
								}
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
					}
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
				} 
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
						
						if (((TextArea) w).getText().isEmpty()) {
							informationLabel.setText("Das Eingabefeld ist leer.");
							
							
							ClientsideSettings.getPartnerboerseAdministration().deleteOneInfoNeu(profilId, Integer.valueOf(eigenschaftIdTable),
									new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											informationLabel.setText("Beim Löschen der Info ist ein Fehler aufgetreten.");
										}

										@Override
										public void onSuccess(Void result) {
											informationLabel.setText("Die Info wurde gelöscht, da das Eingabefeld geleert wurde.");
										}
							});
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
						
						if (infotextTable.equals("Keine Auswahl")) {
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
				
				
				ClientsideSettings.getPartnerboerseAdministration().saveInfo(profilId,
						infos, new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel
										.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Integer result) {
								informationLabel.setText("Die Infos wurden "
										+ "erfolgreich angelegt.");
								
								if (result == 0) {
									
									ShowEigenesNp showNp = new ShowEigenesNp();
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showNp);
								}
								
								else if (result == 1) {
									
									int suchprofilId = profilId;
									ShowSuchprofil showSp = new ShowSuchprofil (suchprofilId);
									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showSp);
									
								
								}
							}
						});
					}
				});
				
			verPanel.add(ueberschriftLabel);
			verPanel.add(editInfoFlexTable);
			verPanel.add(informationLabel);
			verPanel.add(updateInfosButton);

		}
}