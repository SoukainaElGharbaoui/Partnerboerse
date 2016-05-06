package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Navigator extends VerticalPanel {

	
	public Navigator() {
		/**
		 * Button "Nutzerprofil anlegen" hinzufügen.
		 * !!! Gehört hier nicht hin, dient zurzeit jedoch als Beispiel !!!
		 */
		final Button nutzerprofilAnlegenButton = new Button("Nutzerprofil anlegen");
		nutzerprofilAnlegenButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				CreateNutzerprofil createNutzerprofil = new CreateNutzerprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createNutzerprofil);
			}
		});
		
		this.add(nutzerprofilAnlegenButton);	
		
		/**
		 * Button "Merkzettel anzeigen" hinzufügen
		 */
		final Button merkzettelAnzeigenButton = new Button("Merkzettel anzeigen"); 
		merkzettelAnzeigenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowMerkzettel showMerkzettel = new ShowMerkzettel(); 
				RootPanel.get("Details").clear(); 
				RootPanel.get("Details").add(showMerkzettel); 				
			}		
		}); 
		
		this.add(merkzettelAnzeigenButton); 
		
		/**
		 * Button "Sperrliste anzeigen" hinzufügen
		 */
		final Button sperrlisteAnzeigenButton = new Button("Sperrliste anzeigen"); 
		sperrlisteAnzeigenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowSperrliste showSperrliste = new ShowSperrliste(); 
				RootPanel.get("Details").clear(); 
				RootPanel.get("Details").add(showSperrliste); 				
			}		
		}); 
		
		this.add(sperrlisteAnzeigenButton); 
	}
	
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button eigenesProfilButton = new Button("Eigenes Profil anzeigen");
//	navPanel2.add(eigenesProfilButton);
//
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button profilBearbeitenButton = new Button("Eigenes Profil bearbeiten");
//	navPanel2.add(profilBearbeitenButton);
//
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button eigenesProfilLoeschen = new Button("Eigenes Profil l&ouml;schen");
//	navPanel2.add(eigenesProfilLoeschen);
//
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button merklisteButton = new Button("Merkliste anzeigen");
//	navPanel2.add(merklisteButton);
//
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button sperrlisteButton = new Button("Sperrliste anzeigen");
//	navPanel2.add(sperrlisteButton);
//
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button partnervorschlaegeOhneSpButton = new Button("Unangesehene Partnervorschlaege anzeigen");
//	navPanel2.add(partnervorschlaegeOhneSpButton);
//
//	/**
//	 * Erzeugen eines Navigation-Buttons.
//	 */
//	final Button partnervorschlaegeMitSpButton = new Button("Partnervorschlaege auf Basis Ihrer Suche anzeigen");
//	navPanel2.add(partnervorschlaegeMitSpButton);
//
//	
//	
	
}


