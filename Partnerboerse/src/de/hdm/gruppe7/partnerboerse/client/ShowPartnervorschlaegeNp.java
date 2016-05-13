package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowPartnervorschlaegeNp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowPartnervorschlaegeNp() {
		
		this.add(verPanel);

		/**
		 * Überschrift-Label hinzufügen.
		 */
		final Label ueberschriftLabel = new Label(
				"Diese Nutzerprofile koennten zu ihnen passen");
		this.add(ueberschriftLabel);
		verPanel.add(ueberschriftLabel); 
		
		/**
		 * Tabelle zur Anzeige der Partnervorschlaege hinzufuegen. 
		 */
		final FlexTable partnervorschlaegeNpFlexTable = new FlexTable(); 
		verPanel.add(partnervorschlaegeNpFlexTable); 
		
		/** 
		 * Tabelle formatieren und CSS einbinden. 
		 */
		partnervorschlaegeNpFlexTable.setCellPadding(6);
		partnervorschlaegeNpFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		partnervorschlaegeNpFlexTable.addStyleName("FlexTable");   
		

		/**
		 * Erste Zeile der Tabelle festlegen. 
		 */
		partnervorschlaegeNpFlexTable.setText(0, 0, "F-ID");
		partnervorschlaegeNpFlexTable.setText(0, 1, "Uebereinstimmung in %");
		partnervorschlaegeNpFlexTable.setText(0, 2, "Vorname");
		partnervorschlaegeNpFlexTable.setText(0, 3, "Nachname");
		partnervorschlaegeNpFlexTable.setText(0, 4, "Alter");
		partnervorschlaegeNpFlexTable.setText(0, 5, "Geschlecht");
		partnervorschlaegeNpFlexTable.setText(0, 6, "Anzeigen");
		
		
		/**
		 * PartnervorschlaegeNP anzeigen in den folgenden Zeilen 
		 */
	}
}