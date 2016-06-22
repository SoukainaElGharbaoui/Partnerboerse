
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
 *
 */
public class EditInfo extends VerticalPanel {

	/**
	 * VerticalPanels erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Listen erzeugen.
	 */
	private List<Info> listInfos;
	private List<Eigenschaft> listE = new ArrayList<Eigenschaft>();

	/**
	 * Attribute erzeugen
	 */
	private int row;
	private String infotext;
	private String typ;
	private int zaehler;
	
	private int counter;
	private int counter2;

	/**
	 * Widgets erzeugen.
	 */
	private HorizontalPanel buttonPanel = new HorizontalPanel(); 
	
	private FlexTable editInfoFlexTable = new FlexTable();
	private Label ueberschriftLabel = new Label("Infos bearbeiten:");
	
	private Button updateInfosButton = new Button("Infos speichern");
	
	private Button createInfosButton = new Button("Infos anlegen");
	private Button loeschenButton = new Button("Ausgewählte Infos löschen");
	private Button selectAllInfosButton = new Button("Alle Infos markieren");
	
	private Label informationLabel = new Label();
	
	private CheckBox cb;
	
	private int profilId;
	private String profiltyp;
	

	/**
	 * Konstruktor hinzufügen.
	 * 
	 * @param profilId
	 * @param profiltyp
	 */
	public EditInfo(int profilId, String profiltyp) {
		this.profilId = profilId;
		this.profiltyp = profiltyp;
		
		run();
	}
	
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

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		editInfoFlexTable.setText(0, 0, "Profil-Id");
		editInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		editInfoFlexTable.setText(0, 2, "Eigenschaft");
		editInfoFlexTable.setText(0, 3, "Bearbeiten");
		editInfoFlexTable.setText(0, 4, "Löschen");
		editInfoFlexTable.setText(0, 5, "");

		
		befuelleTabelle();
		

		/**
		 * ClickHandler fuer den Button zum Editieren einer Info erzeugen.
		 * Sobald dieser Button betaetigt wird, werden die Eingaben sowohl auf
		 * Vollstaendigkeit als auch auf Korrektheit ueberprueft. Sind Eingaben
		 * unvollstaendig oder inkorrekt, wird eine entsprechende Information
		 * ueber diesen Zustand ausgegeben.
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
				speichereInfo(infos);
			}
		});
		
		
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
		 * dieser Button betaetigt wird, wird CreateInfo geöffnet.
		 */
		createInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				CreateInfo createInfo = new CreateInfo(profilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createInfo);
			}
		});
		
		
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
							
							if (editInfoFlexTable.getRowCount() == 2) {
								
								ueberschriftLabel.setText("Sie haben sich zurzeit keine Profile gemerkt.");
								editInfoFlexTable.setVisible(false);
								buttonPanel.setVisible(false);
								informationLabel.setVisible(false);
								
								ueberschriftLabel.setVisible(true);
							}
						}
					}
				}
			}
		});

		
		/**
		 * Widgets zum Panel hinzufuegen.
		 */
		buttonPanel.add(updateInfosButton);
		buttonPanel.add(loeschenButton);
		buttonPanel.add(selectAllInfosButton);
		
		verPanel.add(ueberschriftLabel);
		verPanel.add(editInfoFlexTable);
		verPanel.add(buttonPanel);
		verPanel.add(informationLabel);
	}
	
	
	public void befuelleTabelle() {
		
		/**
		 * Info anhand der Profil-ID aus der Datenbank auslesen.
		 */
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
	
	
	public void getAuswahleigenschaften() {
		
		/**
		 * Optionen der Auswahleigeschaften anhand der Liste
		 * aus Eigenschaften aus der Datenbank auslesen.
		 */
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
	 * Prüft, die ob Tabelle leer ist.
	 * 
	 * @return boolean
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