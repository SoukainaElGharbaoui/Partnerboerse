package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

/**
 * Diese Klasse dient dazu, ein Suchprofil eines Nutzers anzuzeigen. 
 */
public class ShowSuchprofil extends VerticalPanel {
	
	/**
	 * Neues Nutzerprofil-Objekt, das Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Panels erzeugen. 
	 */
	private VerticalPanel suchprofilPanel = new VerticalPanel();
	private VerticalPanel infoPanel = new VerticalPanel();
	private HorizontalPanel gesamtPanel = new HorizontalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label auswahlLabel = new Label("Wählen Sie das anzuzeigende Suchprofil aus.");
	private Label infoLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private FlexTable showSuchprofilFlexTable = new FlexTable();
	private Button createSuchprofilButton = new Button("Suchprofil anlegen");
	private Button anzeigenButton = new Button("Suchprofil anzeigen");
	private Button loeschenButton = new Button("Suchprofil löschen");
	private Button bearbeitenButton = new Button("Suchprofil bearbeiten"); 

	/**
	 * Konstruktor erstellen.
	 * @param suchprofilId 
	 */
	public ShowSuchprofil(int suchprofilId) {

		/**
		 * Widgets den Panels hinzufuegen.
		 */
		this.add(gesamtPanel);
		gesamtPanel.add(suchprofilPanel);
		suchprofilPanel.add(buttonPanel);
		gesamtPanel.add(infoPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren. 
		 */
		auswahlLabel.addStyleName("partnerboerse-label");
		showSuchprofilFlexTable.addStyleName("FlexTable");
		showSuchprofilFlexTable.setCellPadding(6);
		showSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showSuchprofilFlexTable.setText(0, 0, "Suchprofil-id");
		showSuchprofilFlexTable.setText(1, 0, "Suchprofilname");
		showSuchprofilFlexTable.setText(2, 0, "Geschlecht");
		showSuchprofilFlexTable.setText(3, 0, "Alter von");
		showSuchprofilFlexTable.setText(4, 0, "Alter bis");
		showSuchprofilFlexTable.setText(5, 0, "Koerpergroesse");
		showSuchprofilFlexTable.setText(6, 0, "Haarfarbe");
		showSuchprofilFlexTable.setText(7, 0, "Raucher");
		showSuchprofilFlexTable.setText(8, 0, "Religion");
		
		/**
		 * Suchprofil anhand der Suchprofil-ID auslesen und die Tabelle mit
		 * den Suchprofildaten befuellen. 
		 */
			ClientsideSettings.getPartnerboerseAdministration()
			.getSuchprofilById(nutzerprofil.getProfilId(), 
					suchprofilId, new AsyncCallback<Suchprofil>() {

				public void onFailure(Throwable caught) {
					infoLabel.setText("Es trat ein Fehler auf.");
				}

				public void onSuccess(Suchprofil result) {
					
					int suchprofilId = result.getProfilId();
					showSuchprofilFlexTable.setText(0, 1, String.valueOf(suchprofilId));
					showSuchprofilFlexTable.setText(1, 1, result.getSuchprofilName());
					showSuchprofilFlexTable.setText(2, 1, result.getGeschlecht());
					showSuchprofilFlexTable.setText(3, 1, Integer.toString(result.getAlterMinInt()));
					showSuchprofilFlexTable.setText(4, 1, Integer.toString(result.getAlterMaxInt()));
					showSuchprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));
					showSuchprofilFlexTable.setText(6, 1, result.getHaarfarbe());
					showSuchprofilFlexTable.setText(7, 1, result.getRaucher());
					showSuchprofilFlexTable.setText(8, 1, result.getReligion());
					
					/**
					 * Widgets und Panels den Panels hinzufuegen. 
					 */
					suchprofilPanel.add(showSuchprofilFlexTable);
					buttonPanel.add(bearbeitenButton);
					buttonPanel.add(loeschenButton);
					suchprofilPanel.add(buttonPanel);
					suchprofilPanel.add(infoLabel);
					
					/**
					 * Zusaetzlich zu den Suchprofildaten werden die Suchprofil-Infos 
					 * des jeweiligen Suchprofils angezeigt. 
					 */
					ShowInfo showInfo = new ShowInfo(suchprofilId);
					infoPanel.clear();
					infoPanel.add(showInfo);

				}

			});
			
		/**
		 * Alle Suchprofile des Nutzerprofils auslesen und die ListBox mit den Suchprofilnamen befuellen.
		 * Existieren zu diesem Nutzerprofil bisher keine Suchprofile, so wird eine Information ausgegeben, 
		 * die ueber diesen Zustand informiert. Zudem wird ein Button eingeblendet, durch den neue Suchprofile
		 * angelegt werden koennen. 
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(), 
				new AsyncCallback<List<Suchprofil>>() {

			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");
			}

			public void onSuccess(List<Suchprofil> result) {
				if (result.isEmpty()) {
					auswahlListBox.setVisible(false);
					anzeigenButton.setVisible(false);
					auswahlLabel.setText("Sie haben bisher kein Suchprofil angelegt.");

					createSuchprofilButton.setVisible(true); 

				} else {
					for (Suchprofil s : result) {
						auswahlListBox.addItem(s.getSuchprofilName());
					}
					createSuchprofilButton.setVisible(false);
				}
			}
		}); 
		
		/**
		 * ClickHandler fuer den Button zum Anlegen eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird die Seite zum Anlegen 
		 * eines Suchprofils aufgerufen. 
		 */
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateSuchprofil createSuchprofil = new CreateSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);
			}

		});
		
		/**
		 * ClickHandler fuer den Button zum Anzeigen eines Suchprofils erzeugen.
		 * Sobald dieser Button betaetigt wird, wird anhand der Profil-ID und 
		 * anhand des Suchprofilnames das jeweilige Suchprofil ausgelesen. 
		 * Anschliessend werden die Suchprofildaten in die Tabelle eingefuegt. 
		 */
		anzeigenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.getSuchprofilByName(nutzerprofil.getProfilId(), 
								auswahlListBox.getSelectedItemText(), new AsyncCallback<Suchprofil>() {

								public void onFailure(Throwable caught) {
									infoLabel.setText("Es trat ein Fehler auf.");
								}

								public void onSuccess(Suchprofil result) {
									
									int suchprofilId = result.getProfilId();
									showSuchprofilFlexTable.setText(0, 1, String.valueOf(suchprofilId));
									showSuchprofilFlexTable.setText(1, 1, result.getSuchprofilName());
									showSuchprofilFlexTable.setText(2, 1, result.getGeschlecht());
									showSuchprofilFlexTable.setText(3, 1, Integer.toString(result.getAlterMinInt()));
									showSuchprofilFlexTable.setText(4, 1, Integer.toString(result.getAlterMaxInt()));
									showSuchprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));
									showSuchprofilFlexTable.setText(6, 1, result.getHaarfarbe());
									showSuchprofilFlexTable.setText(7, 1, result.getRaucher());
									showSuchprofilFlexTable.setText(8, 1, result.getReligion());
									
									/**
									 * Zusaetzlich zu den Suchprofildaten werden die Suchprofil-Infos 
									 * des jeweiligen Suchprofils angezeigt. 
									 */
									ShowInfo showInfo = new ShowInfo(suchprofilId);
									infoPanel.clear();
									infoPanel.add(showInfo);

								}

							});
				
				/**
				 * Widgets und Panels den Panels hinzufuegen.
				 */
				suchprofilPanel.add(showSuchprofilFlexTable);
				buttonPanel.add(bearbeitenButton);
				buttonPanel.add(loeschenButton);
				suchprofilPanel.add(buttonPanel);
				suchprofilPanel.add(infoLabel);

			}

		});
			
		/**
		 * ClickHandler fuer den Button zum Bearbeiten eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird die Seite zum Bearbeiten eines
		 * Suchprofils aufgerufen. 
		 */
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditSuchprofil editSuchprofil = new EditSuchprofil(auswahlListBox.getSelectedItemText());
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editSuchprofil);

				}

			});
			
		/**
		 * ClickHandler fuer den Button zum Loeschen eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird das jeweilige Suchprofil 
		 * geloescht. Anschliessend wird die Seite zum Anzeigen der Suchprofile
		 * aufgerufen. 
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.deleteSuchprofil(nutzerprofil.getProfilId(), auswahlListBox.getSelectedItemText(), new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf");
							}

							public void onSuccess(Void result) {
								int suchprofilId = 0;
								ShowSuchprofil showSuchprofil = new ShowSuchprofil(suchprofilId);
								suchprofilPanel.clear();
								infoPanel.clear();
								suchprofilPanel.add(showSuchprofil);
							}

						});

				}

			});			
		
		/**
		 * Widgets und Panels den Panels hinzufuegen. 
		 */
		suchprofilPanel.add(auswahlLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		auswahlPanel.add(createSuchprofilButton);
		suchprofilPanel.add(auswahlPanel);

	} 

}
