package de.hdm.gruppe7.partnerboerse.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowMerkliste extends VerticalPanel {
	
	/**
	 * Panels und Widgets zur Anzeige des Merkzettels hinzufügen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/** 
	 * Konstruktor hinzufügen. 
	 */
	public ShowMerkliste(){
		this.add(verPanel); 
		
		/**
		 * FlexTable hinzufügen. 
		 */
		final FlexTable merklisteFlexTable = new FlexTable(); 
		
		final Label infoLabel = new Label(); 
		final Label infoLabel1 = new Label();
		
		merklisteFlexTable.setText(0, 0, "hi");
		
		
		ClientsideSettings.getPartnerboerseAdministration().
		getGemerkteProfileFor(Benutzer.getProfilId(), new AsyncCallback<Vector<Merkliste>>() {

			@Override
			public void onFailure(Throwable caught) {
				infoLabel.setText("ShowMerkzettel: Fehler bei Rückgabe Vector");
				infoLabel1.setText("ShowMerkzettel: Benutzer = " + Benutzer.getProfilId());
				infoLabel1.setText(caught.toString());
				
			}

			@Override
			public void onSuccess(Vector<Merkliste> result) {
				infoLabel.setText("ShowMerkzettel: Erfolgreiche Rückgabe Vector");
				infoLabel1.setText("ShowMerkzettel: Benutzer = " + Benutzer.getProfilId());
			}
			
		});
		
		verPanel.add(merklisteFlexTable); 
		verPanel.add(infoLabel);
		verPanel.add(infoLabel1);
		
	}	

}
