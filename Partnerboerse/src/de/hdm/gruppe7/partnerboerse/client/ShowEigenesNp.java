package de.hdm.gruppe7.partnerboerse.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.client.gui.NutzerprofilForm;
import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;


public class ShowEigenesNp extends VerticalPanel {

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
//
//	private HorizontalPanel horPanelNpId = new HorizontalPanel();
//	private HorizontalPanel horPanelVorname = new HorizontalPanel();
//	private HorizontalPanel horPanelNachname = new HorizontalPanel();
//	private HorizontalPanel horPanelGeschlecht = new HorizontalPanel();
//	private HorizontalPanel horPanelGeburtsdatum = new HorizontalPanel();
//	private HorizontalPanel horPanelRaucher = new HorizontalPanel();
//	private HorizontalPanel horPanelKoerpergroesse = new HorizontalPanel();
//	private HorizontalPanel horPanelHaarfarbe = new HorizontalPanel();
//	private HorizontalPanel horPanelReligion = new HorizontalPanel();
//	private HorizontalPanel horPanelInfo = new HorizontalPanel();

		
//
//		final Label vornameLabel = new Label("Vorname: ");
//		final Label aktuellerVornameLabel = new Label();
//	
//		// /**
//		// * Infolabel erstellen
//		// */
//		// Label infoLabel1 = new Label();
//		// Label infoLabel2 = new Label();
//		// verPanel.add(horPanelInfo);
//		// horPanelInfo.add(infoLabel1);
//		// horPanelInfo.add(infoLabel2);
//
//		ClientsideSettings.getPartnerboerseAdministration()
//				.getNutzerprofilById(Benutzer.getProfilId(),
//						new AsyncCallback<Nutzerprofil>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								// infoLabel1.setText("Es ist ein Fehler aufgetreten.");
//							}
//
//							@Override
//							public void onSuccess(Nutzerprofil result) {
//								// infoLabel2.setText("Es hat funktioniert.");
//														
//
//								/**
//								 * Erzeugen eines Labels zum Anzeigen des
//								 * aktuellen Vornamens.
//								 */
//								aktuellerVornameLabel.setText(result.getVorname());
//
//							}
//						});
//	
//		verPanel.add(horPanelVorname);
//		verPanel.add(horPanelNpId);
//		horPanelVorname.add(vornameLabel);
//		horPanelVorname.add(aktuellerVornameLabel);
//		
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public ShowEigenesNp() {
		this.add(verPanel);

		/**
		 * Label für Überschrift erstellen
		 */
		final Label ueberschriftLabel = new Label("Eigenes Nutzerprofil");
		
		/**
		 * Tabelle zur Anzeige der gemerkten Kontakte hinzufügen. 
		 */
		final FlexTable showEigenesNpFlexTable = new FlexTable(); 
		
		/**
		 * Erste Zeile der Tabelle festlegen. 
		 */
		showEigenesNpFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showEigenesNpFlexTable.setText(1, 0, "Vorname");
		
		/** 
		 * Tabelle formatieren und CSS einbinden. 
		 */
		showEigenesNpFlexTable.setCellPadding(6);
		showEigenesNpFlexTable.getColumnFormatter().addStyleName(0, "merklisteHeader");
		showEigenesNpFlexTable.addStyleName("merklisteFlexTable");   
		
		final Label infoLabel = new Label(); 
		
		ClientsideSettings.getPartnerboerseAdministration()
		.getNutzerprofilById(Benutzer.getProfilId(),
				new AsyncCallback<Nutzerprofil>() {
			
					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
						
					}

					@Override
					public void onSuccess(Nutzerprofil result) {
				
						// Nutzerprofil-Id aus der Datenabank holen 
						// und in Tabelle eintragen
						final String nutzerprofilId = String.valueOf(result.getProfilId());
						showEigenesNpFlexTable.setText(0, 1, nutzerprofilId); 						
						
						// Vorname aus Datenbank aus der Datenbank holen 
						// und in Tabelle eintragen
						showEigenesNpFlexTable.setText(1, 1, result.getVorname());
									
		// Bearbeiten-Button hinzufügen und ausbauen. 
		final Button bearbeitenButton = new Button("Bearbeiten");
		showEigenesNpFlexTable.setWidget(2, 1, bearbeitenButton); 
		
		// Löschen-Button hinzufügen und ausbauen. 
		final Button loeschenButton = new Button("Löschen");
		showEigenesNpFlexTable.setWidget(2, 2, loeschenButton); 
		
		// ClickHandler für den Löschen-Button hinzufügen. 
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
								
		// ClickHandler für den Löschen-Button hinzufügen. 
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
		
		}
	});
			
	verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenesNpFlexTable); 
		verPanel.add(infoLabel);
		
	}
	
}

	