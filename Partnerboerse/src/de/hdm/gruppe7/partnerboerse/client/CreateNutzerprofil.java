package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateNutzerprofil extends VerticalPanel {

	/**
	 * VerticalPanels und HorizontalPanels hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelVorname = new HorizontalPanel();
	private HorizontalPanel horPanelNachname = new HorizontalPanel();
	private HorizontalPanel horPanelGeschlecht = new HorizontalPanel();
	private HorizontalPanel horPanelGeburtsdatum = new HorizontalPanel();
	private HorizontalPanel horPanelRaucher = new HorizontalPanel();
	private HorizontalPanel horPanelKoerpergroesse = new HorizontalPanel();
	private HorizontalPanel horPanelHaarfarbe = new HorizontalPanel();
	private HorizontalPanel horPanelReligion = new HorizontalPanel();

	/**
	 * Konstruktor
	 */
	public CreateNutzerprofil() {
		this.add(verPanel);

		/**
		 * Erzeugen eines Eingabefelds fuer den Vornamen.
		 */
		final TextBox vornameTextBox = new TextBox();
		final Label vornameLabel = new Label("Vorname");

		verPanel.add(horPanelVorname);
		horPanelVorname.add(vornameTextBox);
		horPanelVorname.add(vornameLabel);

		/**
		 * Erzeugen eines Eingabefelds fuer den Nachnamen.
		 */
		final TextBox nachnameTextBox = new TextBox();
		final Label nachnameLabel = new Label("Nachname");
		verPanel.add(horPanelNachname);
		horPanelNachname.add(nachnameTextBox);
		horPanelNachname.add(nachnameLabel);

		/**
		 * Erzeugen einer Auswahl fuer das Geschlecht.
		 */
		final Label geschlechtLabel = new Label("Geschlecht");
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		verPanel.add(horPanelGeschlecht);
		horPanelGeschlecht.add(geschlechtListBox);
		horPanelGeschlecht.add(geschlechtLabel);

		/**
		 * Erzeugen einer TextBox fuer das Geburtsdatum.
		 */
		final Label geburtsdatumLabel = new Label("Geburtsdatum (TT.MM.YYYY)");
		final TextBox geburtsdatumTextBox = new TextBox();
		verPanel.add(horPanelGeburtsdatum);
		horPanelGeburtsdatum.add(geburtsdatumTextBox);
		horPanelGeburtsdatum.add(geburtsdatumLabel);

		/**
		 * Erzeugen einer Auswahl fuer den Raucherstatus.
		 */
		final Label raucherLabel = new Label("Raucherstatus");
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		verPanel.add(horPanelRaucher);
		horPanelRaucher.add(raucherListBox);
		horPanelRaucher.add(raucherLabel);

		/**
		 * Erzeugen eines Eingabefelds fuer die Koerpergroesse.
		 */
		final TextBox koerpergroesseTextBox = new TextBox();
		final Label koerpergroesseLabel = new Label("Koerpergroesse (cm)");
		verPanel.add(horPanelKoerpergroesse);
		horPanelKoerpergroesse.add(koerpergroesseTextBox);
		horPanelKoerpergroesse.add(koerpergroesseLabel);

		/**
		 * Erzeugen einer Auswahl fuer die Haarfarbe.
		 */
		final ListBox haarfarbeListBox = new ListBox();
		final Label haarfarbeLabel = new Label("Haarfarbe");
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		verPanel.add(horPanelHaarfarbe);
		horPanelHaarfarbe.add(haarfarbeListBox);
		horPanelHaarfarbe.add(haarfarbeLabel);

		/**
		 * Erzeugen einer Auswahl fuer die Religion.
		 */
		final ListBox religionListBox = new ListBox();
		final Label religionLabel = new Label("Religion");
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		verPanel.add(horPanelReligion);
		horPanelReligion.add(religionListBox);
		horPanelReligion.add(religionLabel);

		/**
		 * infoLabel für die Benutzerinformation erzeugen.
		 */
		final Label infoLabel = new Label();

		final Button createNutzerprofilButton = new Button(
				"Nutzerprofil anlegen");
		createNutzerprofilButton
				.setStylePrimaryName("partnerboerse-menubutton");
		verPanel.add(createNutzerprofilButton);

		/**
		 * infoLabel zum navPanel hinzufügen.
		 */
		verPanel.add(infoLabel);

		createNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.createNutzerprofil(vornameTextBox.getText(),
								nachnameTextBox.getText(),
								geburtsdatumTextBox.getText(),
								geschlechtListBox.getSelectedItemText(),
								haarfarbeListBox.getSelectedItemText(),
								koerpergroesseTextBox.getText(),
								raucherListBox.getSelectedItemText(),
								religionListBox.getSelectedItemText(),
								new AsyncCallback<Nutzerprofil>() {

									@Override
									public void onFailure(Throwable caught) {
										infoLabel
												.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Nutzerprofil result) {
										infoLabel
												.setText("Das Nutzerprofil wurde erfolgreich angelegt");
									}

								});

			}
		});

	}
}
