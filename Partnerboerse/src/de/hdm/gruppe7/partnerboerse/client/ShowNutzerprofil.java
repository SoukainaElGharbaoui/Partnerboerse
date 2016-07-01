package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Diese Klasse dient dazu, das eigene Nutzerprofil anzuzeigen.
 */
public class ShowNutzerprofil extends VerticalPanel {

	/**
	 * Panels erzeugen.
	 */
	private VerticalPanel nutzerprofilPanel = new VerticalPanel();
	private VerticalPanel infoPanel = new VerticalPanel();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Mein Profil:");
	private FlexTable showEigenesNpFlexTable = new FlexTable();
	private Label infoLabel = new Label();
	private Button loeschenButton = new Button("Profil löschen");
	private Button bearbeitenButton = new Button("Profil bearbeiten");

	/**
	 * Variable fuer die ProfilId erstellen.
	 */
	private int profilId;

	/**
	 * Variable fuer den Profiltyp erstellen.
	 */
	private String profiltyp;

	/**
	 * Konstruktor erstellen.
	 * 
	 * @param profilId
	 *            Die Profil-ID des Nutzerprofils, das angezeigt werden soll.
	 * @param profiltyp
	 *            Der Profiltyp (Nutzerprofil).
	 */
	public ShowNutzerprofil(int profilId, String profiltyp) {
		this.profilId = profilId;
		this.profiltyp = profiltyp;
		run();
	}

	/**
	 * Methode erstellen, die den Aufbau der Seite startet.
	 */
	public void run() {
		this.add(horPanel);
		horPanel.add(nutzerprofilPanel);
		horPanel.add(infoPanel);

		/**
		 * CSS anwenden und die Tabelle formatieren.
		 */
		ueberschriftLabel.addStyleName("partnerboerse-label");
		showEigenesNpFlexTable.addStyleName("FlexTable");
		showEigenesNpFlexTable.setCellPadding(6);
		showEigenesNpFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showEigenesNpFlexTable.setText(0, 0, "Vorname");
		showEigenesNpFlexTable.setText(1, 0, "Nachname");
		showEigenesNpFlexTable.setText(2, 0, "Geschlecht");
		showEigenesNpFlexTable.setText(3, 0, "Geburtsdatum");
		showEigenesNpFlexTable.setText(4, 0, "Körpergröße in cm");
		showEigenesNpFlexTable.setText(5, 0, "Haarfarbe");
		showEigenesNpFlexTable.setText(6, 0, "Raucherstatus");
		showEigenesNpFlexTable.setText(7, 0, "Religion");
		showEigenesNpFlexTable.setText(8, 0, "EMail");

		befuelleTabelle();

		/**
		 * ClickHandler fuer den Button zum Bearbeiten des Nutzerprofils
		 * erzeugen. Sobald der Button betaetigt wird, wird die Seite zum
		 * Bearbeiten des Nutzerprofils aufgerufen.
		 */
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditNutzerprofil editNutzerprofil = new EditNutzerprofil(profilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editNutzerprofil);
			}
		});

		/**
		 * ClickHandler fuer den Button zum Loeschen des Nutzerprofils erzeugen.
		 * Sobald der Button betaetigt wird, erscheint eine Bildschrimmeldung,
		 * die hinterfragt, ob das Nutzerprofil tatsaechlich geloescht werden
		 * soll. Wird diese mit "Ok" bestaetigt, wird das Nutzerprofil aus der
		 * Datenbank entfernt. Zudem wird der Nutzer ausgeloggt und auf die
		 * Login-Seite weitergeleitet.
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loescheNutzerprofil();
			}
		});

		/**
		 * Zusaetzlich zu den Profildaten werden die Infos des Nuterprofils
		 * angezeigt.
		 */
		String listtyp = "Np";
		ShowInfo showInfo = new ShowInfo(profilId, profiltyp, listtyp);

		/**
		 * Widgets den Panels hinzufuegen.
		 */
		nutzerprofilPanel.add(ueberschriftLabel);
		nutzerprofilPanel.add(showEigenesNpFlexTable);
		buttonPanel.add(bearbeitenButton);
		buttonPanel.add(loeschenButton);
		nutzerprofilPanel.add(buttonPanel);
		nutzerprofilPanel.add(infoLabel);
		infoPanel.add(showInfo);

	}

	/**
	 * Methode erstellen, die das eigene Nutzerprofil anhand der Profil-ID
	 * ausliest und die Profildaten in die Tabelle einfuegt.
	 */
	public void befuelleTabelle() {
		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(profilId,
				new AsyncCallback<Nutzerprofil>() {

					public void onFailure(Throwable caught) {
					}

					public void onSuccess(Nutzerprofil result) {
						showEigenesNpFlexTable.setText(0, 1, result.getVorname());
						showEigenesNpFlexTable.setText(1, 1, result.getNachname());
						showEigenesNpFlexTable.setText(2, 1, result.getGeschlecht());
						Date geburtsdatum = result.getGeburtsdatumDate();
						String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
						showEigenesNpFlexTable.setText(3, 1, geburtsdatumString);
						showEigenesNpFlexTable.setText(4, 1, Integer.toString(result.getKoerpergroesseInt()));
						showEigenesNpFlexTable.setText(5, 1, result.getHaarfarbe());
						showEigenesNpFlexTable.setText(6, 1, result.getRaucher());
						showEigenesNpFlexTable.setText(7, 1, result.getReligion());
						showEigenesNpFlexTable.setText(8, 1, result.getEmailAddress());
					}
				});
	}

	/**
	 * Methode erstellen, die das eigene Nutzerprofil loescht.
	 */
	public void loescheNutzerprofil() {
		if (Window.confirm("Möchten Sie Ihr Profil wirklich löschen?")) {

			ClientsideSettings.getPartnerboerseAdministration().deleteNutzerprofil(profilId, new AsyncCallback<Void>() {

				public void onFailure(Throwable caught) {
				}

				public void onSuccess(Void result) {

					Window.Location.replace(Partnerboerse.getLoginInfo().getLogoutUrl());

				}
			});
		}
	}

}
