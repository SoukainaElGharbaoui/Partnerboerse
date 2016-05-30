package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class ShowPartnervorschlaege extends VerticalPanel {
	private int aehnlichkeit = 0;

	public ShowPartnervorschlaege() {

		/**
		 * Button "Partnervorschlaege mit Nutzerprofil anzeigen" hinzufügen.
		 */
		final Button showPartnervorschlaegeNpButton = new Button("Partnervorschlaege mit Nutzerprofil anzeigen");

		showPartnervorschlaegeNpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				ShowPartnervorschlaegeNp showPartnervorschlaegeNp = new ShowPartnervorschlaegeNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaegeNp);
			}

		});

		this.add(showPartnervorschlaegeNpButton);

		/**
		 * Button "Partnervorschlaege mit Suchprofil anzeigen" hinzufügen.
		 */
		final Button showPartnervorschlaegeSpButton = new Button("Partnervorschlaege mit Suchprofil anzeigen");

		showPartnervorschlaegeSpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowPartnervorschlaegeSp showPartnervorschlaegeSp = new ShowPartnervorschlaegeSp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaegeSp);
			}

		});

		this.add(showPartnervorschlaegeSpButton);

	}
}
