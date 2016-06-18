package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
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

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

/**
 * Diese Klasse dient dazu, Partnervorschlaege anhand eines Suchprofils azuzeigen.
 * @author dunja
 *
 */

public class ShowPartnervorschlaegeSp extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
	 */
	
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * VerticalPanels und HorizontalPanels erzeugen.
	 */
	
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelTabelle = new HorizontalPanel();
	private HorizontalPanel auswahlPanel = new HorizontalPanel();
	
	/**
	 * Tabelle zur Anzeige der Partnervorschlaege erzeugen.
	 */
	
	private FlexTable partnervorschlaegeSpFlexTable = new FlexTable();
	
	/**
	 * Labels und Buttons erzeugen.
	 */
	
	private Label ueberschriftLabel = new Label("W�hlen Sie das Suchprofil aus, zu welchem Sie Partnervorschl�ge angezeigt bekommen m�chten:");
	private Label ueberschriftLabel2 = new Label("Diese Profile k�nnten Ihnen gefallen:");
	private Label infoLabel = new Label();
	private Label ergebnisLabel = new Label();
	private ListBox auswahlListBox = new ListBox();
	private Button anzeigenSpButton = new Button("Partnervorschl�ge anzeigen");
	private Button anzeigenButton;
	private Button createSuchprofilButton = new Button("Neues Suchprofil anlegen");
	 

	/**
	 * Konstruktor hinzufuegen.
	 * 
	 * @param a
	 */
	public ShowPartnervorschlaegeSp() {
		
		/**
		 * VerticalPanel und HorizontalPanel werden dem Konstruktor hinzugefuegt.
		 */

		this.add(verPanel);
		this.add(auswahlPanel);
		this.add(horPanelTabelle);
		
		/**
		 * CSS anwenden.
		 */
		
		ueberschriftLabel.addStyleName("partnerboerse-label");
		ueberschriftLabel2.addStyleName("partnerboerse-label");

		/**
		 * Die ListBox wird mit allen Suchprofil-Namen eines Nutzerprofils gef�llt.
		 * 
		 * Sind keine Suchprofile angelegt, werden der Anzeigen-Button und die ListBox nicht angezeigt. 
		 * Es erscheint dann das uberschriftLabel und der Suchprofil-Anlegen-Button.
		 * 
		 * Ist mindestens ein Suchprofil angelegt, wird die ListBox bef�llt und der Suchprofil-Anlegen-Button wird nicht angezeigt. 
		 * Der Suchprofil-Anlegen-Button wird wiederum angezeigt.
		 */
		
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
		

		/**
		 * ClickHandler fuer den Suchprofil-Anlegen-Button hinzufuegen.
		 * 
		 * Bei Betaetigung des Suchprofil-Anlegen-Buttons, gelangt man auf die Seite mit der man ein neues Suchprofil anlegt.
		 */
		
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateSuchprofil createSuchprofil = new CreateSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);
			}

		});
		
		/**
		 * Clickhandler fuer den Anzeigen-Button hinzufuegen.
		 * 
		 * Bei Betaetigung des AnzeigenButtons werden alle Partnervorschlaege
		 * anhand des gewaehlten Suchprofils, nach Aehnlichkeit geordnet, ausgegeben.
		 * 
		 */
		
		anzeigenSpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

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
								 * Bei jeder Auswahl eines Suchprofils wird die Tabelle komplett geloescht,
								 * damit diese mit den neuen Informationen bef�llt werden kann.
								 * 
								 */
								partnervorschlaegeSpFlexTable.removeAllRows();

								/**
								 * Tabelle formatieren und CSS einbinden.
								 * Tabelle wird bei jedem Klick komplett neu
								 * erstellt.
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
								partnervorschlaegeSpFlexTable.setText(0, 4, "Geburtsdatum");
								partnervorschlaegeSpFlexTable.setText(0, 5, "Geschlecht");
								partnervorschlaegeSpFlexTable.setText(0, 6, "Anzeigen");

								/**
								 * Die Tabelle wird mit den Partnervorschlaegen befuellt.
								 */
								
								int row = 0;
								
								for (Nutzerprofil np : result) {

									final int fremdprofilId = np.getProfilId();
									row++;
									partnervorschlaegeSpFlexTable.setText(row, 0, String.valueOf(np.getProfilId()));
									partnervorschlaegeSpFlexTable.setText(row, 1,
											String.valueOf(np.getAehnlichkeit()) + "%");
									partnervorschlaegeSpFlexTable.setText(row, 2, np.getVorname());
									partnervorschlaegeSpFlexTable.setText(row, 3, np.getNachname());
									
									Date geburtsdatum = np.getGeburtsdatumDate();
									String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
									
									partnervorschlaegeSpFlexTable.setText(row, 4, geburtsdatumString);
									partnervorschlaegeSpFlexTable.setText(row, 5, np.getGeschlecht());

									/**
									 * Der Anzeigen-Button f�r die Anzeige eines Fremdprofils wird erzeugt und der Tabelle hinzugef�gt.
									 */
									
									anzeigenButton = new Button("Anzeigen");
									partnervorschlaegeSpFlexTable.setWidget(row, 6, anzeigenButton);

									/**
									 * Der Clickhandler f�r den Azeigen-Button des Fremdprofils wird hinzufuegen.
									 * 
									 * Bei Betaetigung des Anzeigen-Buttons gelangt man auf die Seite auf der das Fremdprofil angezeigt wird.
									 */
									
									anzeigenButton.addClickHandler(new ClickHandler() {
										public void onClick(ClickEvent event) {
											ShowFremdprofil showFremdprofil = new ShowFremdprofil(fremdprofilId);
											RootPanel.get("Details").clear();
											RootPanel.get("Details").add(showFremdprofil);

										}

									});

								}

							}

						});
				
				/**
				 * Alle Widgets dem VerticalPanel und HorizontalPanel hinzufuegen.
				 * 
				 * Diese Widgets werden bei Betaetigung des Anzeigen-Buttons angezeigt.
				 */
				
				verPanel.add(ergebnisLabel);
				verPanel.add(infoLabel);
				verPanel.add(ueberschriftLabel2);
				horPanelTabelle.add(partnervorschlaegeSpFlexTable);
				verPanel.add(horPanelTabelle);

			}

		});
		
		
	

		/**
		 * Alle Widgets dem VerticalPanel und HorizontalPanel hinzufuegen.
		 * 
		 * Diese Widgets werden angezeigt sobald man �ber die Men�Bar das Feld "Partnervorschlaege anhand Suchprofil" ausw�hlt.
		 */

		verPanel.add(ueberschriftLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenSpButton);
		verPanel.add(createSuchprofilButton);	
		verPanel.add(auswahlPanel);

	}

}
