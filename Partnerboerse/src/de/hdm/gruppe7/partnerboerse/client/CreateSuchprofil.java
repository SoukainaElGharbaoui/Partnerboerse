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

import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel horPanelAlterMin = new HorizontalPanel();
	private HorizontalPanel horPanelAlterMax = new HorizontalPanel();
	private HorizontalPanel horPanelGeschlecht = new HorizontalPanel();
	private HorizontalPanel horPanelRaucher = new HorizontalPanel();
	private HorizontalPanel horPanelKoerpergroesse = new HorizontalPanel();
	private HorizontalPanel horPanelHaarfarbe = new HorizontalPanel();
	private HorizontalPanel horPanelReligion = new HorizontalPanel();
	
	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateSuchprofil() {
		this.add(verPanel);
	
	/**
	 * Erzeugen eines Eingabefelds fuer den Vornamen.
	 */
	final TextBox alterMinTextBox = new TextBox();
	final Label alterMinLabel = new Label("Alter minimal");

	verPanel.add(horPanelAlterMin);
	horPanelAlterMin.add(alterMinTextBox);
	horPanelAlterMin.add(alterMinLabel);

	/**
	 * Erzeugen eines Eingabefelds fuer den Nachnamen.
	 */
	final TextBox alterMaxTextBox = new TextBox();
	final Label alterMaxLabel = new Label("Alter maximal");
	verPanel.add(horPanelAlterMax);
	horPanelAlterMax.add(alterMaxTextBox);
	horPanelAlterMax.add(alterMaxLabel);

	/**
	 * Erzeugen einer Auswahl fuer das Geschlecht.
	 */
	final Label geschlechtLabel = new Label("Geschlecht");
	final ListBox geschlechtListBox = new ListBox();
	geschlechtListBox.addItem("Keine Auswahl");
	geschlechtListBox.addItem("Weiblich");
	geschlechtListBox.addItem("MÃ¤nnlich");
	verPanel.add(horPanelGeschlecht);
	horPanelGeschlecht.add(geschlechtListBox);
	horPanelGeschlecht.add(geschlechtLabel);

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
	 * infoLabel fÃ¼r die Benutzerinformation erzeugen.
	 */
	final Label infoLabel = new Label();

	final Button createSuchprofilButton = new Button(
			"Suchprofil anlegen");
	createSuchprofilButton
			.setStylePrimaryName("partnerboerse-menubutton");
	verPanel.add(createSuchprofilButton);

	/**
	 * infoLabel zum navPanel hinzufügen.
	 */
	verPanel.add(infoLabel);

	createSuchprofilButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {

			ClientsideSettings.getPartnerboerseAdministration()
					.createSuchprofil(alterMinTextBox.getText(),
							alterMaxTextBox.getText(),
							geschlechtListBox.getSelectedItemText(),
							haarfarbeListBox.getSelectedItemText(),
							koerpergroesseTextBox.getText(),
							raucherListBox.getSelectedItemText(),
							religionListBox.getSelectedItemText(),
							new AsyncCallback<Suchprofil>() {

								@Override
								public void onFailure(Throwable caught) {
									infoLabel
											.setText("Es trat ein Fehler auf");
								}

								@Override
								public void onSuccess(Suchprofil result) {
									infoLabel
											.setText("Das Suchprofil wurde erfolgreich angelegt");
								}

							});

		}
	});

}
	
	
	
	
}
	
