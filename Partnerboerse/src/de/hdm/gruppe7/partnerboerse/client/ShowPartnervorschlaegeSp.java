package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowPartnervorschlaegeSp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Konstruktor hinzufügen.
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
		
	}
}
