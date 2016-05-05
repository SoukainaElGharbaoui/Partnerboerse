package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Navigator extends VerticalPanel {

	public Navigator() {
		final Label nutzerprofilAnlegenLabel = new Label("Nutzerprofil anlegen");
		nutzerprofilAnlegenLabel.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				CreateNutzerprofil createNutzerprofil = new CreateNutzerprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createNutzerprofil);
			}
		});
		
		this.add(nutzerprofilAnlegenLabel);	
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


