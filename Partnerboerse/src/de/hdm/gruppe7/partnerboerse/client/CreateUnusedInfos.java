package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateUnusedInfos extends VerticalPanel {
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	/**
	 * VerticalPanel hinzufügen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private FlexTable showUnusedEigenschaftFlexTable = new FlexTable();
	
	private String eigenschaftId;
	private String beschreibungstext;
	
	private List<Beschreibungseigenschaft> listB;
	private List<Auswahleigenschaft> listA;
	
	private Button createInfosButton = new Button("Info anlegen");
	private Label ueberschriftLabel = new Label("Weitere Info anlegen:");
	private Label informationLabelB = new Label();
	private Label informationLabelA = new Label();
	private Label informationLabel = new Label();
	
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateUnusedInfos() {	
		this.add(verPanel);
		
		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showUnusedEigenschaftFlexTable.setText(0, 0, "Profil-Id");
		showUnusedEigenschaftFlexTable.setText(0, 1, "Eigenschaft-Id");
		showUnusedEigenschaftFlexTable.setText(0, 2, "Erlaeuterung");
		showUnusedEigenschaftFlexTable.setText(0, 3, "Anlegen"); 

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showUnusedEigenschaftFlexTable.setCellPadding(6);
		showUnusedEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showUnusedEigenschaftFlexTable.addStyleName("FlexTable");

		/**
		 * Überschrift-Label hinzufügen. 
		 */
		
		ueberschriftLabel.addStyleName("partnerboerse-label"); 
		
		ClientsideSettings.getPartnerboerseAdministration().getAllUnusedEigenschaften(
				new AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>>() {
					
					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Beim Herausholen der Eigenschaften trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(
							Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result) {
						
						informationLabel.setText("Die Methode wurde aufgerufen.");
						
						int row = showUnusedEigenschaftFlexTable.getRowCount();
						
						Set<List<Beschreibungseigenschaft>> output = result.keySet();

						for (List<Beschreibungseigenschaft> listEigB : output) {
							
							listB = listEigB;

							for (Beschreibungseigenschaft eigB : listEigB) {

								row++;

								eigenschaftId = null;
								beschreibungstext = null;
								
								showUnusedEigenschaftFlexTable.setText(row,	0, 
										String.valueOf(nutzerprofil.getProfilId()));

								eigenschaftId = String.valueOf(eigB.getEigenschaftId());

								showUnusedEigenschaftFlexTable.setText(row,	1, eigenschaftId);
								
								showUnusedEigenschaftFlexTable.setText(row, 2, eigB.getErlaeuterung());

								final TextArea textArea = new TextArea();

								showUnusedEigenschaftFlexTable.setWidget(row, 3, textArea);

								beschreibungstext = eigB.getBeschreibungstext();

								textArea.setText(beschreibungstext);
							}

							listA = result.get(listEigB);

							for (Auswahleigenschaft eigA : listA) {

								row++;
								
								showUnusedEigenschaftFlexTable.setText(row,
										0, String.valueOf(nutzerprofil.getProfilId()));
								
								showUnusedEigenschaftFlexTable.setText(row,
										1, String.valueOf(eigA.getEigenschaftId()));
								
								showUnusedEigenschaftFlexTable.setText(row, 2, eigA.getErlaeuterung());

								final ListBox lb = new ListBox();

								showUnusedEigenschaftFlexTable.setWidget(row, 3, lb);

								List<String> optionen = eigA.getOptionen();

								for (int i = 0; i < optionen.size(); i++) {
									lb.addItem(optionen.get(i));
								}

								for (int a = 0; a < lb.getItemCount(); a++) {

									if (lb.getValue(a).equals("Keine Auswahl")) {
										lb.setItemSelected(a, true);
									}
								}
							}
						}
						
					}		
					
				});
		
		
		createInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				List<Info> infos = new ArrayList<Info>();
				String infotextTable = null;
				
				int k;

				for (int i = 2; i < showUnusedEigenschaftFlexTable.getRowCount(); i++) {
					
					k = 0;
					k = i - 2;

					String eigenschaftIdTable = showUnusedEigenschaftFlexTable
							.getText(i, 1);

					Widget w = showUnusedEigenschaftFlexTable.getWidget(i, 3);
					
					if (w instanceof TextArea) {
						
						infotextTable = ((TextArea) w).getText();
						
						if (infotextTable.equals(listB.get(k).getBeschreibungstext())) {
						}
						
						else {
							Info info = new Info();
							info.setProfilId(nutzerprofil.getProfilId());
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
							info.setProfilId(nutzerprofil.getProfilId());
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}

					}
				}

				
				ClientsideSettings.getPartnerboerseAdministration().createInfo(
						infos, new AsyncCallback<List<Info>>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel
										.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(List<Info> result) {
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
		
		verPanel.add(ueberschriftLabel);  
		verPanel.add(showUnusedEigenschaftFlexTable);
		verPanel.add(createInfosButton);
		verPanel.add(informationLabelB);
		verPanel.add(informationLabelA);
		verPanel.add(informationLabel);
	}
}
