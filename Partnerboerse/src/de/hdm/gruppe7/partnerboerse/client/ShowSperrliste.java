package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;

/**
 * Diese Klasse dient dazu, die Sperrliste eines Nutzerprofils anzuzeigen.
 */

public class ShowSperrliste extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt,
	 * erzeugen.
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Vertikales und Horizontales Panel erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Diese Profile befinden sich auf Ihrer Sperrliste:");
	private FlexTable sperrlisteFlexTable = new FlexTable();
	private Label infoLabel = new Label();
	private CheckBox cb;
	private Button anzeigenButton;
	private Button loeschenButton = new Button("Ausgewählte Profile von Sperrliste entfernen");
	private Button selectAllProfilsButton = new Button("Alle Profile markieren");

	/**
	 * Attribute erzeugen.
	 */
	private int zaehler;
	private String listtyp;

	/**
	 * Konstruktor erstellen.
	 * 
	 * @param listtyp
	 *            Der Listtyp der Seite, von der das Anzeigen des Fremdprofils
	 *            aufgerufen wird (Sperrliste).
	 */
	public ShowSperrliste(String listtyp) {
		this.listtyp = listtyp;
		run();
	}

	/**
	 * Startet den Aufruf der Seite. Es werden alle Nutzerprofile ausgelesen,
	 * die gesperrt wurden. Diese werden in die Tabelle eingefuegt.
	 */
	public void run() {

		/**
		 * Vertical Panel wird hinzugefeugt.
		 */
		this.add(verPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		sperrlisteFlexTable.addStyleName("FlexTable");
		sperrlisteFlexTable.setCellPadding(6);
		sperrlisteFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		
//		sperrlisteFlexTable.getFlexCellFormatter().set

		/**
		 * Kopfzeile der Tabelle festlegen.
		 */
//		sperrlisteFlexTable.setText(0, 0, "F-ID");
		sperrlisteFlexTable.setText(0, 1, "Vorname");
		sperrlisteFlexTable.setText(0, 2, "Nachname");
		sperrlisteFlexTable.setText(0, 3, "Geburtstag");
		sperrlisteFlexTable.setText(0, 4, "Geschlecht");
		sperrlisteFlexTable.setText(0, 5, "Anzeigen");
		sperrlisteFlexTable.setText(0, 6, "Löschen");

		getGesperrteNutzerprofile();

		/**
		 * ClickHandler fuer den Alles-Auswaehlen-Button erzeugen. Bei
		 * Betaetigung des Alles-Auswaehlen-Buttons werden alle Checkboxen
		 * markiert und alle Eintraege koennen geloescht werden.
		 */
		selectAllProfilsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				for (int i = 2; i < sperrlisteFlexTable.getRowCount(); i++) {

					CheckBox cb = (CheckBox) sperrlisteFlexTable.getWidget(i, 6);
					cb.setValue(true);

					sperrlisteFlexTable.setWidget(i, 6, cb);
				}
			}
		});

		/**
		 * ClickHandler fuer den Button zum Loeschen einer Sperrung erzeugen.
		 * Sobald der Button betaetigt wird, wird die Sperrung sowohl aus der
		 * Tabelle als auch aus der Datenbank entfernt.
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				int rowTable = sperrlisteFlexTable.getRowCount();
				int fremdprofilId;
				infoLabel.setText("" + rowTable);

				zaehler = 0;

				/**
				 * Die For-Schleife ueberprueft Zeile fuer Zeile ob die Checkbox
				 * markiert ist oder nicht. Ist eine Checkbox markiert, wird die
				 * Fremprofil-ID des markierten Nutzerprofils ausgelesen und der
				 * Vermerkstatus des Nutzerprofils geaendert. Das entsprechende
				 * Nutzerprofil wird dann aus der Sperrliste entfernt.
				 */
				for (int k = 2; k < rowTable; k++) {

					boolean checked = ((CheckBox) sperrlisteFlexTable.getWidget(k, 6)).getValue();

					infoLabel.setText("" + checked);

					if (checked == true) {

						zaehler++;
						fremdprofilId = Integer.valueOf(sperrlisteFlexTable.getText(k, 0));

						infoLabel.setText("" + fremdprofilId);

						aendereVermerkstatus(fremdprofilId);

						/**
						 * Jeweilige Zeile der Tabelle loeschen.
						 */
						sperrlisteFlexTable.removeRow(k);
						k--;

						/**
						 * Fall, die Merkliste ist leer, dann wird ein
						 * entsprechendes Label zur Information angezeigt.
						 */
						if (sperrlisteFlexTable.getRowCount() == 2) {

							ueberschriftLabel.setText("Sie haben zurkeit keine Profile gesperrt.");
							sperrlisteFlexTable.setVisible(false);
							buttonPanel.setVisible(false);
							infoLabel.setVisible(false);

							ueberschriftLabel.setVisible(true);
						}
					}
				}

				/**
				 * Fall, dass keine CheckBox ausgewaehlt wurde. Dann wird eine
				 * entsprechende Information angezeigt.
				 */
				if (zaehler == 0) {
					Window.alert("Es wurde nichts ausgewählt.");
				}
			}
		});

		/**
		 * Widgets zum vertikalen Panel hinzufuegen.
		 */

		buttonPanel.add(loeschenButton);
		buttonPanel.add(selectAllProfilsButton);

		verPanel.add(ueberschriftLabel);
		verPanel.add(sperrlisteFlexTable);
		verPanel.add(infoLabel);
		verPanel.add(buttonPanel);
	}

	/**
	 * Nun werden alle gesperrten Nutzerprofile eines Nutzerprofils abgefragt.
	 * Das Ergebnis der Abfrage ist ein Sperrliste-Objekt. Die gesperrten
	 * Nutzerprofil-Objekte werden in einer Liste von Nutzerprofilen
	 * gespeichert. Diese Liste wird in einer Schleife durchlaufen und jedes
	 * gesperrte Nutzerprofil wird in eine Zeile der Tabelle eingefuegt. Zudem
	 * wird der Tabelle bei jedem Schleifendurchlauf je ein Button zum Loeschen
	 * und ein Button zum Anzeigen einer Sperrung hinzugefuegt.
	 */
	public void getGesperrteNutzerprofile() {

		ClientsideSettings.getPartnerboerseAdministration().getGesperrteNutzerprofileFor(nutzerprofil.getProfilId(),
				new AsyncCallback<Sperrliste>() {

					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Sperrliste result) {

						List<Nutzerprofil> gemerkteNutzerprofile = result.getGesperrteNutzerprofile();

						int row = sperrlisteFlexTable.getRowCount();

						for (Nutzerprofil n : gemerkteNutzerprofile) {
							row++;
							
							Label id = new Label(String.valueOf(n.getProfilId()));

							final String fremdprofilId = String.valueOf(n.getProfilId());

							sperrlisteFlexTable.setWidget(row, 0, id);
							
							id.setVisible(false);
							sperrlisteFlexTable.setText(row, 1, n.getVorname());
							sperrlisteFlexTable.setText(row, 2, n.getNachname());

							Date geburtsdatum = n.getGeburtsdatumDate();
							String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);

							sperrlisteFlexTable.setText(row, 3, geburtsdatumString);
							sperrlisteFlexTable.setText(row, 4, n.getGeschlecht());

							anzeigenButton = new Button("Anzeigen");
							sperrlisteFlexTable.setWidget(row, 5, anzeigenButton);

							cb = new CheckBox();
							cb.setValue(false);
							sperrlisteFlexTable.setWidget(row, 6, cb);

							/**
							 * ClickHandler fuer den Button zum Anzeigen eines
							 * Fremdprofils erzeugen. Sobald der Button
							 * betaetigt wird, wird die Seite zum Anzeigen eines
							 * Fremdprofils aufgerufen.
							 */
							anzeigenButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									String profiltyp = "Fp";
									String suchprofilName = null;

									ShowFremdprofil showFremdprofil = new ShowFremdprofil(
											Integer.valueOf(fremdprofilId), profiltyp, listtyp, suchprofilName);

									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showFremdprofil);
								}
							});
						}

						/**
						 * Pruefen, ob die Tabelle leer ist. Falls dies der Fall
						 * ist, wird eine entsprechende Information ueber diesen
						 * Zustand ausgegeben.
						 */
						boolean befuellt = pruefeLeereTable();

						if (befuellt == true) {

							ueberschriftLabel.setText("Sie haben zurzeit keine Profile gesperrt.");

							sperrlisteFlexTable.setVisible(false);
							buttonPanel.setVisible(false);
							ueberschriftLabel.setVisible(true);
						}
					}
				});
	}

	/**
	 * Methode, die die ausgewaehlten Fremdprofile fuer ein Nutzerprofil von der
	 * Sperrliste streicht.
	 * 
	 * @param fremdprofilId
	 *            Die Fremdprofil-Id des Nutzerprofils, dessen Vermerkstatus
	 *            geaendert werden soll.
	 */
	public void aendereVermerkstatus(int fremdprofilId) {

		ClientsideSettings.getPartnerboerseAdministration().vermerkstatusAendern(nutzerprofil.getProfilId(),
				fremdprofilId, new AsyncCallback<Integer>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Integer result) {
					}
				});
	}

	/**
	 * Neue Methode definieren, die prueft, ob die Tabelle leer ist.
	 * 
	 * @return Boolscher Wert, der angibt, ob die Tabelle leer ist.
	 */
	public boolean pruefeLeereTable() {

		for (int k = 2; k < sperrlisteFlexTable.getRowCount(); k++) {

			if (sperrlisteFlexTable.getText(k, 0) == null) {
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

}
