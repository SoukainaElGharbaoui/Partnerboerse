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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class ShowFremdinfo extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	
	private FlexTable showInfoFlexTable = new FlexTable();
	
	private Label ueberschriftLabel = new Label("Infos dieser Person:");
	private Label informationLabel = new Label();
	
	private int row;
	private int eigenschaftIdInt;
	private int eigenschaftIdTable;
	private int zaehler;
	
	public boolean pruefeLeereTable() {
		
		for (int k = 2; k < showInfoFlexTable.getRowCount(); k++) {
			
			if (showInfoFlexTable.getText(k, 0) == null) {
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
	

	/**
	 * Konstruktor
	 */
	public ShowFremdinfo(int profilId) {
		this.add(verPanel);

		/**
		 * Label Ueberschrift
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		informationLabel.addStyleName("partnerboerse-label");

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTable.setText(0, 0, "Fremdprofil-Id");
		showInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		showInfoFlexTable.setText(0, 2, "Eigenschaft");
		showInfoFlexTable.setText(0, 3, "Infotext");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");
		
		
		
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
					
					row = showInfoFlexTable.getRowCount();
					
					for (Info i : listI) {
						
						row++;
						
						showInfoFlexTable.setText(row, 0, String.valueOf(i.getProfilId()));
						showInfoFlexTable.setText(row, 1, String.valueOf(i.getEigenschaftId()));
						showInfoFlexTable.setText(row, 3, i.getInfotext());
					}
					
					
					List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
					listE = result.get(listI);
					
					row  = 0;
					row = showInfoFlexTable.getRowCount();
					
					for (Eigenschaft e : listE) {
						
						row++; 
						
						eigenschaftIdInt = 0;
						eigenschaftIdInt = e.getEigenschaftId();
						
						for (int i = 2; i < showInfoFlexTable.getRowCount(); i++) {
							
							eigenschaftIdTable = 0;
							eigenschaftIdTable = Integer.valueOf(showInfoFlexTable.getText(i, 1));
							
							if (eigenschaftIdInt == eigenschaftIdTable) {
								
								showInfoFlexTable.setText(i, 2, e.getErlaeuterung());
							}
							
							else {
							}
						}
					}
				}
			
				boolean befuellt = pruefeLeereTable();
				
				if (befuellt == true) {
					
					ueberschriftLabel.setVisible(false);
					showInfoFlexTable.setVisible(false);
									
					informationLabel.setText("Dieser Nutzer hat bisher noch keine Infos angelegt.");
				}
			}
		});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);
	}
}
