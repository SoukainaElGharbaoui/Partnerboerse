package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
 * Diese Klasse dient dazu, Partnervorschlaege anhand von Suchprofilen
 * azuzeigen.
 */

public class ShowPartnervorschlaegeSp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt, erzeugen.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Panels erzeugen. 
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelTabelle = new HorizontalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen. 
	 */
	private Label ueberschriftLabel = new Label("Wählen Sie das Suchprofil aus, zu welchem Sie Partnervorschläge angezeigt bekommen möchten:");
	private Label ueberschriftLabel2 = new Label("Diese Profile könnten Ihnen gefallen:");
	private FlexTable partnervorschlaegeSpFlexTable = new FlexTable();
	private Label infoLabel = new Label();
	private Label ergebnisLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private Button anzeigenSpButton = new Button("Partnervorschläge anzeigen");
	private Button anzeigenButton;
	private Button createSuchprofilButton = new Button("Neues Suchprofil anlegen");
	
	/**
	 * Variable erstellen, die die Anzahl der befuellten Zeilen enthaelt.
	 */
	private int zaehler = 0;
	
	/**
	 * Variable fuer den Listtyp erstellen.
	 */
	private String listtyp;
	
	/**
	 * Konstruktor erstellen.
	 * @param listtyp Der Listtyp (PvSp)
	 */
	public ShowPartnervorschlaegeSp(String listtyp) {
		this.listtyp = listtyp;
		run();
	}

	/**
	 * Methode erstellen, die den Aufbau der Seite startet. 
	 */
	public void run() {
		this.add(verPanel);
		this.add(auswahlPanel);
		this.add(horPanelTabelle);
		
		
		/**
		 * CSS anwenden. 
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		ueberschriftLabel2.addStyleName("partnerboerse-label");

		befuelleListBox();

		/**
		 * ClickHandler fuer Button zum Anlegen eines Suchprofils erzeugen. 
		 * Bei der Betaetigung dieses Buttons gelangt man auf die Seite 
		 * zum Anlegen eines neuen Suchprofils. 
		 */
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String profiltyp = "Sp";
				CreateSuchprofil createSuchprofil = new CreateSuchprofil(profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);
			}
		});
		
		/**
		 * Clickhandler fuer den Button zum Anzeigen der Partnervorschlaege erzeugen. 
		 * Sobald dieser Button betaetigt wird, werden alle alle Partnervorschlaege
		 * anhand des gewaehlten Suchprofils, nach Aehnlichkeit geordnet,
		 * ausgegeben.
		 */
		anzeigenSpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				/**
				 * Bei jeder Auswahl eines Suchprofils wird die
				 * Tabelle komplett geleert, damit diese mit
				 * den neuen Informationen befüllt werden kann.
				 */
				partnervorschlaegeSpFlexTable.removeAllRows();

				/**
				 * Tabelle formatieren und CSS einbinden.
				 */
				partnervorschlaegeSpFlexTable.setCellPadding(6);
				partnervorschlaegeSpFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
				partnervorschlaegeSpFlexTable.addStyleName("FlexTable");
				
				/**
				 * Erste Zeile der Tabelle festlegen.
				 */
				partnervorschlaegeSpFlexTable.setText(0, 0 ,"F-ID");
				partnervorschlaegeSpFlexTable.setText(0, 1 ,"Uebereinstimmung in %");
				partnervorschlaegeSpFlexTable.setText(0, 2 ,"Vorname");
				partnervorschlaegeSpFlexTable.setText(0, 3 ,"Nachname");
				partnervorschlaegeSpFlexTable.setText(0, 4 ,"Geburtsdatum");
				partnervorschlaegeSpFlexTable.setText(0, 5 ,"Geschlecht");
				partnervorschlaegeSpFlexTable.setText(0, 6 ,"Anzeigen");
				
				erzeugeTabelleSp();

				/**
				 * Widgets den Panels hinzufuegen. 
				 * Diese Widgets werden angezeigt, sobald die Partnervorschlaege angezeigt werden.
				 */
				verPanel.add(ergebnisLabel);
				verPanel.add(infoLabel);
				verPanel.add(ueberschriftLabel2);
				horPanelTabelle.add(partnervorschlaegeSpFlexTable);
				verPanel.add(horPanelTabelle);
			}
		});

		/**
		 * Widgets den Panels hinzufuegen. 
		 * Diese Widgets werden angezeigt sobald man über die MenueBar das Feld
		 * "Partnervorschlaege anhand Suchprofil" auswaehlt.
		 */

		verPanel.add(ueberschriftLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenSpButton);
		verPanel.add(createSuchprofilButton);
		verPanel.add(auswahlPanel);

	}
	
	/**
	 * Methode erstellen, die die Tabelle auf Inhalt prueft.
	 * @return Boolscher Wert, der angibt, ob die Tabelle leer ist.
	 */
	public boolean pruefeLeereTable() {

		for (int k = 1; k < partnervorschlaegeSpFlexTable.getRowCount(); k++) {

			if (partnervorschlaegeSpFlexTable.getText(k, 0) == null) {
			}

			else {
				zaehler++;
			}
		}

		if (zaehler == 0) {
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * Befuellen der Tabelle mit den Partnervorschlaegen geordnet nach Aehnlichkeit.
	 */
	public void erzeugeTabelleSp() {
		
		horPanelTabelle.clear();

		ClientsideSettings.getPartnerboerseAdministration().getGeordnetePartnervorschlaegeSp(nutzerprofil.getProfilId(),
						auswahlListBox.getSelectedItemText(), new AsyncCallback<List<Nutzerprofil>>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf");
							}

							@Override
							public void onSuccess(List<Nutzerprofil> result) {

								/**
								 * Die Tabelle wird mit den Partnervorschlaegen
								 * befuellt.
								 */

								int row = 0;

								for (Nutzerprofil np : result) {

									final int fremdprofilId = np.getProfilId();
									row++;
									partnervorschlaegeSpFlexTable.setText(row, 0 , String.valueOf(np.getProfilId()));
									partnervorschlaegeSpFlexTable.setText(row, 1 ,String.valueOf(np.getAehnlichkeit())+ "%");
									partnervorschlaegeSpFlexTable.setText(row, 2 , np.getVorname());
									partnervorschlaegeSpFlexTable.setText(row, 3 , np.getNachname());

									Date geburtsdatum = np.getGeburtsdatumDate();
									String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);

									partnervorschlaegeSpFlexTable.setText(row, 4 , geburtsdatumString);
									partnervorschlaegeSpFlexTable.setText(row, 5 , np.getGeschlecht());

									/**
									 * Der Anzeigen-Button fuer die Anzeige
									 * eines Fremdprofils wird erzeugt und der
									 * Tabelle hinzugefuegt.
									 */

									anzeigenButton = new Button("Anzeigen");
									partnervorschlaegeSpFlexTable.setWidget(row, 6 , anzeigenButton);

									partnervorschlaegeSpFlexTable.setText(row, 7 , String.valueOf(row));

									/**
									 * Der Clickhandler fuer den Azeigen-Button des Fremdprofils wird hinzugefuegt.
									 * 
									 * Bei Betaetigung des Anzeigen-Buttons gelangt man auf die Seite auf der das
									 * Fremdprofil angezeigt wird.
									 */

									anzeigenButton.addClickHandler(new ClickHandler() {public void onClick(ClickEvent event) {

										String profiltyp = "Fp";

										ShowFremdprofil showFremdprofil = new ShowFremdprofil(fremdprofilId,profiltyp,listtyp);
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(showFremdprofil);
												}
											});
								}

								boolean befuellt = pruefeLeereTable();

								if (befuellt == true) {

									ueberschriftLabel.setText("Zu diesem Suchprofil existieren zurzeit keine passenden Partnervorschläge.");
									ueberschriftLabel.setVisible(true);

									partnervorschlaegeSpFlexTable.setVisible(false);
									ueberschriftLabel2.setVisible(false);
									infoLabel.setVisible(false);
									ergebnisLabel.setVisible(false);
									auswahlListBox.setVisible(false);
									anzeigenSpButton.setVisible(false);
									anzeigenButton.setVisible(false);
									createSuchprofilButton.setVisible(false);
								}
							}
						});

	}
	

	
	

	/**
	 * Die ListBox wird mit allen Suchprofil-Namen eines Nutzerprofils gefuellt.
	 * 
	 * Sind keine Suchprofile angelegt, werden der Anzeigen-Button und die
	 * ListBox nicht angezeigt. Es erscheint dann das uberschriftLabel und der
	 * Suchprofil-Anlegen-Button.
	 * 
	 * Ist mindestens ein Suchprofil angelegt, wird die ListBox befuellt und der
	 * Suchprofil-Anlegen-Button wird nicht angezeigt. Der
	 * Suchprofil-Anlegen-Button wird wiederum angezeigt.
	 */
	public void befuelleListBox() {

		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(nutzerprofil.getProfilId(),
						new AsyncCallback<List<Suchprofil>>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");

							}

							@Override
							public void onSuccess(List<Suchprofil> result) {

								if (result.isEmpty()) {
									auswahlListBox.setVisible(false);
									anzeigenSpButton.setVisible(false);
									ueberschriftLabel.setText("Sie haben bisher kein Suchprofil angelegt.");

									createSuchprofilButton.setVisible(true);

								} else {

									for (Suchprofil s : result) {
										auswahlListBox.addItem(s.getSuchprofilName());
									}

									createSuchprofilButton.setVisible(false);
									
								}
							}

						});

	}

}
