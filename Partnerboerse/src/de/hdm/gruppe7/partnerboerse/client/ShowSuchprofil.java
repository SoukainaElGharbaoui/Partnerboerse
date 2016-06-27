package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
 * Diese Klasse dient dazu, die Suchprofile eines Nutzers anzuzeigen. 
 */
public class ShowSuchprofil extends VerticalPanel {
	
	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Panels erzeugen. 
	 */
	private HorizontalPanel ueberschriftPanel = new HorizontalPanel(); 
	private VerticalPanel suchprofilPanel = new VerticalPanel();
	private VerticalPanel infoPanel = new VerticalPanel();
	private HorizontalPanel gesamtPanel = new HorizontalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label auswahlLabel = new Label("Wählen Sie ein Suchprofil aus.");
	private Label ueberschriftLabel = new Label(); 
	private Label infoLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private FlexTable showSuchprofilFlexTable = new FlexTable();
	private Button createSuchprofilButton = new Button("Suchprofil anlegen");
	private Button anzeigenButton = new Button("Suchprofil anzeigen"); 
	private Button loeschenButton = new Button("Suchprofil löschen");
	private Button bearbeitenButton = new Button("Suchprofil bearbeiten"); 
	
	/**
	 * Variable fuer die Profil-ID des Suchprofils erstellen.
	 */
	private int suchprofilId; 
	
	/**
	 * Variable fuer den Profiltyp erstellen.
	 */
	private String profiltyp; 
	
	/**
	 * Variable fuer den Listtyp erstellen.
	 */
	private String listtyp = "Sp";
	
	/**
	 * Variable fuer die naechste Suchprofil-ID erstellen.
	 */
	private int naechsteSuchprofilId; 
	
	/**
	 * Liste erzeugen, die alle Suchprofile eines Nutzerprofils enthaelt. 
	 */
	private List<Suchprofil> listS = new ArrayList<Suchprofil>(); 
	


	/**
	 * Konstruktor erstellen.
	 * @param suchprofilId Die Profil-ID des Suchprofils, das angezeigt werden soll. 
	 * @param profiltyp Der Profiltyp (Suchprofil).
	 */
	public ShowSuchprofil(int suchprofilId, String profiltyp) {
		this.suchprofilId = suchprofilId; 
		this.profiltyp = profiltyp; 
		run(); 
	}
	
	/**
	 * Methode erstellen, die den Aufbau der Seite startet. 
	 */
	public void run() {
		
		/**
		 * Panels den Panels hinzufuegen.  
		 */
		this.add(ueberschriftPanel);
		this.add(auswahlPanel);
		this.add(gesamtPanel);
		gesamtPanel.add(suchprofilPanel);
		suchprofilPanel.add(buttonPanel);
		gesamtPanel.add(infoPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren. 
		 */
		auswahlLabel.addStyleName("partnerboerse-label");
		ueberschriftLabel.addStyleName("partnerboerse-label");
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
		
		getAllSuchprofile(); 
		
		getSuchprofil(); 
		
		/**
		 * ClickHandler fuer den Button zum Anlegen eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird die Seite zum Anlegen 
		 * eines Suchprofils aufgerufen. 
		 */
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateSuchprofil createSuchprofil = new CreateSuchprofil(profiltyp);
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
				getSuchprofilByName(); 
			}
		}); 
		
		/**
		 * ClickHandler fuer den Button zum Bearbeiten eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird die Seite zum Bearbeiten eines
		 * Suchprofils aufgerufen. 
		 */
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditSuchprofil editSuchprofil = new EditSuchprofil(showSuchprofilFlexTable.getText(1, 1), profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editSuchprofil);
			}
		});
			
		/**
		 * ClickHandler fuer den Button zum Loeschen eines Suchprofils erzeugen. 
		 * Sobald dieser Button betaetigt wird, wird das jeweilige Suchprofil 
		 * geloescht. Anschliessend wird die Seite zum Anzeigen der Suchprofile
		 * aufgerufen. Hier wird, falls verfuegbar, das naechste Suchprofil des
		 * Nutzerprofils angezeigt. 
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSuchprofil(); 			
				}
			});			
		
		/**
		 * Widgets und Panels den Panels hinzufuegen. 
		 */
		ueberschriftPanel.add(auswahlLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		auswahlPanel.add(createSuchprofilButton);

	} 
	
	/**
	 * Methode erstellen, die alle Suchprofile des Nutzerprofils ausliest und die ListBox mit den 
	 * Suchprofilnamen befuellt. Existieren zu diesem Nutzerprofil bisher keine Suchprofile, werden 
	 * die gewoehnlichen Inhalte der Seite ausgeblendet und es wird eine Information ausgegeben, 
	 * die ueber diesen Zustand informiert. Zudem wird ein Button eingeblendet, durch den neue 
	 * Suchprofile angelegt werden koennen. 
	*/
	public void getAllSuchprofile() {
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(), 
				new AsyncCallback<List<Suchprofil>>() {

			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf.");
			}

			public void onSuccess(List<Suchprofil> result) {
				listS = result; 
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
					
					for (int i = 0; i < auswahlListBox.getItemCount(); i++) {
						if (showSuchprofilFlexTable.getText(1, 1).equals(auswahlListBox.getValue(i))) {
							auswahlListBox.setSelectedIndex(i);
						}
					}
				}
			}
		}); 
	}
	
	/**
	 * Methode erstellen, die ein Suchprofil anhand der Profil-ID ausliest und die Tabelle 
	 * mit den Suchprofildaten befuellt.
	 */
	public void getSuchprofil() {
			ClientsideSettings.getPartnerboerseAdministration()
			.getSuchprofilById(suchprofilId, new AsyncCallback<Suchprofil>() {

				public void onFailure(Throwable caught) {
					infoLabel.setText("Es trat ein Fehler auf.");
				}

				public void onSuccess(Suchprofil result) {

					ueberschriftLabel.setText("Ihr Suchprofil '" + result.getSuchprofilName() + "':");
					showSuchprofilFlexTable.setText(0, 1, String.valueOf(suchprofilId));
					showSuchprofilFlexTable.setText(1, 1, result.getSuchprofilName());
					showSuchprofilFlexTable.setText(2, 1, result.getGeschlecht());
					showSuchprofilFlexTable.setText(3, 1, Integer.toString(result.getAlterMinInt()));
					showSuchprofilFlexTable.setText(4, 1, Integer.toString(result.getAlterMaxInt()));
					showSuchprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));
					showSuchprofilFlexTable.setText(6, 1, result.getHaarfarbe());
					showSuchprofilFlexTable.setText(7, 1, result.getRaucher());
					showSuchprofilFlexTable.setText(8, 1, result.getReligion());
					
					suchprofilPanel.add(ueberschriftLabel);
					suchprofilPanel.add(showSuchprofilFlexTable);
					buttonPanel.add(bearbeitenButton);
					buttonPanel.add(loeschenButton);
					suchprofilPanel.add(buttonPanel);
					suchprofilPanel.add(infoLabel);
					
					ShowInfo showInfo = new ShowInfo(suchprofilId, profiltyp, listtyp);
					infoPanel.clear();
					infoPanel.add(showInfo);

				}

			});
	}
	
	/**
	 * Methode erstellen, die ein Suchprofil anhand der Profil-ID und anhand des Suchprofilnamens 
	 * ausliest und die Tabelle mit den Suchprofildaten befuellt.
	 */
	public void getSuchprofilByName() {
		ClientsideSettings.getPartnerboerseAdministration()
		.getSuchprofilByName(nutzerprofil.getProfilId(), 
				auswahlListBox.getSelectedItemText(), new AsyncCallback<Suchprofil>() {

				public void onFailure(Throwable caught) {
					infoLabel.setText("Es trat ein Fehler auf.");
				}

				public void onSuccess(Suchprofil result) {
					
					ueberschriftLabel.setText("Ihr Suchprofil '" + result.getSuchprofilName() + "':");
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
					
					suchprofilPanel.add(ueberschriftLabel);
					suchprofilPanel.add(showSuchprofilFlexTable);
					buttonPanel.add(bearbeitenButton);
					buttonPanel.add(loeschenButton);
					suchprofilPanel.add(buttonPanel);
					suchprofilPanel.add(infoLabel);
					
					ShowInfo showInfo = new ShowInfo(suchprofilId, profiltyp, listtyp);
					infoPanel.clear();
					infoPanel.add(showInfo);
				}
			});
	}
	
	/**
	 * Methode erstellen, die ein Suchprofil loescht. Anschliessend wird die Seite zum Anzeigen der Suchprofile
	 * aufgerufen. Hier wird, falls verfuegbar, das naechste Suchprofil des Nutzerprofils angezeigt. 
	 */
	public void deleteSuchprofil() {
		if(Window.confirm("Möchten Sie dieses Suchprofil wirklich löschen?")){
		for(int i = 0; i < listS.size(); i++) {
			if(listS.get(i) != null) {
				if(!showSuchprofilFlexTable.getText(1, 1).equals(listS.get(i).getSuchprofilName())) {
					naechsteSuchprofilId = listS.get(i).getProfilId(); 
					break; 
				}
			}
		}
		
		ClientsideSettings.getPartnerboerseAdministration()
		.deleteSuchprofil(nutzerprofil.getProfilId(), showSuchprofilFlexTable.getText(1, 1), new AsyncCallback<Void>() {

			public void onFailure(Throwable caught) {
				infoLabel.setText("Es trat ein Fehler auf");
			}

			public void onSuccess(Void result) {
				ShowSuchprofil showSuchprofil = new ShowSuchprofil(naechsteSuchprofilId, profiltyp);
				ueberschriftPanel.clear();
				auswahlPanel.clear();
				suchprofilPanel.clear();
				infoPanel.clear();
				suchprofilPanel.add(showSuchprofil);
				suchprofilPanel.add(infoLabel);
			}
		});
	}
	}
}
