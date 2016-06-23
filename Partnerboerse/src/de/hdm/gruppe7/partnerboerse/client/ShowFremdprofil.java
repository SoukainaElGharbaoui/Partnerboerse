package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Diese Klasse dient dazu, ein Fremdprofil anzuzeigen.
 */
public class ShowFremdprofil extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Panels erzeugen.
	 */
	private HorizontalPanel ankerPanel = new HorizontalPanel(); 
	private VerticalPanel fremdprofilPanel = new VerticalPanel();
	private VerticalPanel infoPanel = new VerticalPanel();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label();
	private FlexTable showFremdprofilFlexTable = new FlexTable();
	private Button vermerkButton = new Button();
	private Button sperrungButton = new Button();
	private Label infoLabel = new Label();
	private Label pfadLabelM = new Label("Zurück zu: Merkliste anzeigen");
	private Label pfadLabelS = new Label("Zurück zu: Sperrliste anzeigen");
	private Label pfadLabelPvNp = new Label("Zurück zu: Unangesehene Partnervorschläge");
	private Label pfadLabelPvSp = new Label("Zurück zu: Partnervorschläge anhand von Suchprofilen");
	
	/**
	 * Variable fuer die Fremdprofil-ID erstellen.
	 */
	private int fremdprofilId; 
	
	/**
	 * Variable fuer den Profiltyp erstellen.
	 */
	private String profiltyp; 
	
	/**
	 * Variable fuer den Listtyp erstellen.
	 */
	private String listtyp; 

	/**
	 * Konstruktor erstellen.
	 * @param fremdprofilId Die Profil-ID des Fremdprofils, das angezeigt werden soll.
	 * @param profiltyp Der Profiltyp (Fremdprofil).
	 * @param listtyp Die Seite, von der das Fremdprofil aufgerufen wird (Merkliste, Sperrliste,
	 * 		  PartnervorschlaegeNp, PartnervorschlaegeSp). 
	 */
	public ShowFremdprofil(int fremdprofilId, String profiltyp, String listtyp) {
		this.fremdprofilId = fremdprofilId; 
		this.profiltyp = profiltyp; 
		this.listtyp = listtyp; 
		run(); 
	}
	
	/**
	 * Methode erstellen, die den Aufbau der Seite startet. 
	 */
	public void run() {
		this.add(ankerPanel);
		this.add(horPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		showFremdprofilFlexTable.addStyleName("FlexTable");
		showFremdprofilFlexTable.setCellPadding(6);
		showFremdprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		pfadLabelPvNp.addStyleName("partnerboerse-zurueckbutton");
		pfadLabelPvSp.addStyleName("partnerboerse-zurueckbutton");
		pfadLabelM.addStyleName("partnerboerse-zurueckbutton");
		pfadLabelS.addStyleName("partnerboerse-zurueckbutton");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showFremdprofilFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showFremdprofilFlexTable.setText(1, 0, "Vorname");
		showFremdprofilFlexTable.setText(2, 0, "Nachname");
		showFremdprofilFlexTable.setText(3, 0, "Geschlecht");
		showFremdprofilFlexTable.setText(4, 0, "Geburtsdatum");
		showFremdprofilFlexTable.setText(5, 0, "Körpergröße");
		showFremdprofilFlexTable.setText(6, 0, "Haarfarbe");
		showFremdprofilFlexTable.setText(7, 0, "Raucherstatus");
		showFremdprofilFlexTable.setText(8, 0, "Religion");

		pruefeListe(); 

		befuelleTabelle(); 

		pruefeSperrstatusFremdprofil(); 

		/**
		 * ClickHandler fuer den Sperrung-Button erzeugen. Je nach aktuellem
		 * Sperrstatus wird eine Sperrung gesetzt oder entfernt. Zudem wird 
		 * die Aufschrift des Sperrung-Buttons entweder in "Sperrung loeschen" 
		 * oder in "Sperrung setzen" geaendert. Bei Ersterem wird zudem der 
		 * Button zum Vermerken eines Fremdprofils ausgeblendet.
		 */
		sperrungButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sperrstatusAendern(); 
			}
		});

		pruefeVermerkstatus(); 

		/**
		 * ClickHandler fuer den Vermerk-Button erzeugen. Je nach aktuellem
		 * Vermerkstatus wird ein Vermerk gesetzt oder entfernt. Zudem wird 
		 * die Aufschrift des Vermerk-Buttonsn entweder in "Vermerk loeschen" 
		 * oder in "Vermerk setzen" geaendert.
		 */
		vermerkButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				vermerkstatusAendern(); 
			}
		});

		/**
		 * Sperrung-Button und Vermerk-Button dem Panel fuer die Button hinzufuegen.
		 */
		buttonPanel.add(vermerkButton);
		buttonPanel.add(sperrungButton);

		/**
		 * Je nach dem, von welcher Seite aus das Fremdprofil aufgerufen wurde, wird 
		 * ein entsprechendes Label zum Zurueckkehren zu dieser Seite eingeblendet. 
		 */
		if (listtyp.equals("M")) {
			ankerPanel.add(pfadLabelM);
		} else if (listtyp.equals("S")) {
			ankerPanel.add(pfadLabelS);
		} else if (listtyp.equals("PvNp")) {
			ankerPanel.add(pfadLabelPvNp);
		} else if (listtyp.equals("PvSp")) {
			ankerPanel.add(pfadLabelPvSp);
		}

		/**
		 * Widgets den Panels hinzufuegen. 
		 */
		fremdprofilPanel.add(ueberschriftLabel);
		fremdprofilPanel.add(showFremdprofilFlexTable);
		fremdprofilPanel.add(infoLabel);
		fremdprofilPanel.add(buttonPanel);
		horPanel.add(fremdprofilPanel);

		/**
		 * Zusaetzlich zu den Profildaten werden die Infos des Fremdprofils angezeigt.
		 */
		ShowInfo fremdinfo = new ShowInfo(fremdprofilId, profiltyp);
		infoPanel.add(fremdinfo);
		horPanel.add(infoPanel);

	}
	
	/**
	 * Methode erstellen, die prueft, von welcher Seite aus das Fremdprofil aufgerufen wurde. 
	 * Den einzelnen Labels zum Zurueckkehren wird ein ClickHandler hinzugefuegt. Hierdurch 
	 * kann bestimmt werden, auf Welche Seite zurueckgehert wird. 
	 */
	public void pruefeListe() {
		if (listtyp.equals("M")) {
			pfadLabelM.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ShowMerkliste showM = new ShowMerkliste(listtyp);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showM);
				}

			});
		} else if (listtyp.equals("S")) {

			pfadLabelS.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ShowSperrliste showS = new ShowSperrliste(listtyp);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showS);
				}

			});
		}

		else if (listtyp.equals("PvNp")) {
			pfadLabelPvNp.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ShowPartnervorschlaegeNp showPN = new ShowPartnervorschlaegeNp(listtyp);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showPN);
				}

			});
		}

		else if (listtyp.equals("PvSp")) {
			pfadLabelPvSp.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ShowPartnervorschlaegeSp showPS = new ShowPartnervorschlaegeSp(listtyp);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showPS);
				}

			});
		}
	}
	
	/**
	 * Methode erstellen, die ein Nutzerprofil anhand der Profil-ID ausliest und die Profildaten in die Tabelle einfuegt.
	 */
	public void befuelleTabelle() {
		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(fremdprofilId,
				new AsyncCallback<Nutzerprofil>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Nutzerprofil result) {

						ueberschriftLabel.setText("Profil von " + result.getVorname() + " " + result.getNachname());

						final String nutzerprofilId = String.valueOf(result.getProfilId());
						showFremdprofilFlexTable.setText(0, 1, nutzerprofilId);

						showFremdprofilFlexTable.setText(1, 1, result.getVorname());

						showFremdprofilFlexTable.setText(2, 1, result.getNachname());

						showFremdprofilFlexTable.setText(3, 1, result.getGeschlecht());

						Date geburtsdatum = result.getGeburtsdatumDate();
						String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
						showFremdprofilFlexTable.setText(4, 1, geburtsdatumString);

						showFremdprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));

						showFremdprofilFlexTable.setText(6, 1, result.getHaarfarbe());

						showFremdprofilFlexTable.setText(7, 1, result.getRaucher());

						showFremdprofilFlexTable.setText(8, 1, result.getReligion());
					}
				});
	}
	
	/**
	 * Puefen, ob das Fremdprofil vom Nutzerprofil gesperrt wurde. Falls ja,
	 * lautet die Aufschrift des Sperrung-Buttons "Sperrung loeschen". Falls
	 * nein, lautet die Aufschrift des Sperrung-Buttons "Sperrung setzen".
	 */
	public void pruefeSperrstatusFremdprofil(){
		ClientsideSettings.getPartnerboerseAdministration().pruefeSperrstatusFremdprofil(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Integer result) {
						if (result == 1) {
							sperrungButton.setText("Sperrung löschen");
							vermerkButton.setVisible(false);
						} else {
							sperrungButton.setText("Sperrung setzen");
						}
					}
				});
	}
	
	/**
	 * Methode erstellen, die den Sperrstatus aendert. 
	 */
	public void sperrstatusAendern() {
		ClientsideSettings.getPartnerboerseAdministration().sperrstatusAendern(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Integer result) {
						if (result == 0) {
							sperrungButton.setText("Sperrung löschen");
							vermerkButton.setVisible(false);
						} else {
							sperrungButton.setText("Sperrung setzen");
							vermerkButton.setText("Vermerk setzen");
							vermerkButton.setVisible(true);
						}
					}
				});
	}
	
	/**
	 * Puefen, ob das Fremdprofil vom Nutzerprofil vermerkt wurde. Falls ja,
	 * lautet die Aufschrift Vermerk-Buttons "Vermerk loeschen". Falls nein,
	 * lautet die Aufschrift des Vermerk-Buttons "Sperrung setzen".
	 */
	public void pruefeVermerkstatus() {
		ClientsideSettings.getPartnerboerseAdministration().pruefeVermerkstatus(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Integer result) {
						if (result == 1) {
							vermerkButton.setText("Vermerk löschen");
						} else {
							vermerkButton.setText("Vermerk setzen");
						}
					}
				});
	}
	
	/**
	 * Methode erstellen, die den Vermerkstatus aendert. 
	 */
	public void vermerkstatusAendern() {
		ClientsideSettings.getPartnerboerseAdministration().vermerkstatusAendern(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf.");
					}

					public void onSuccess(Integer result) {
						if (result == 0) {
							vermerkButton.setText("Vermerk löschen");
						} else {
							vermerkButton.setText("Vermerk setzen");
						}
					}
				});
	}
}