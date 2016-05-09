package de.hdm.gruppe7.partnerboerse.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

	private int nutzerprofilId;

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
	 * Konstruktor hinzufügen.
	 */
	public ShowEigenesNp() {
		this.add(verPanel);

		/**
		 * Erzeugen der Info-Label.
		 */
		final Label infoLabel = new Label();
		final Label infoLabel1 = new Label();

		/**
		 * Erzeugen eines Labels zum Anzeigen des aktuellen Vornamens.
		 */

		// String eigenerVorname = NutzerprofilMapper.nutzerprofilMapper()
		// .findByNutzerprofilId(nutzerprofilId).getVorname();

		final Label aktuellerVornameLabel = new Label();
		final Label vornameLabel = new Label("Vorname");

		verPanel.add(horPanelVorname);
		horPanelVorname.add(aktuellerVornameLabel);
		horPanelVorname.add(vornameLabel);

		/**
		 * Infolabel dem verPanel hinzufügen
		 */
		verPanel.add(infoLabel);
		verPanel.add(infoLabel1);
		
		ClientsideSettings.getPartnerboerseAdministration()
			.getNutzerprofilById(Benutzer.getProfilId(), new AsyncCallback<Nutzerprofil>() {
		
						@Override
							public void onFailure(Throwable caught) {
								infoLabel
										.setText("ShowEigenesNp: Fehler bei Rückgabe des Nutzerprofils");
								infoLabel1.setText("ShowEigenesNp: Benutzer = "
										+ Benutzer.getProfilId());
								infoLabel1.setText(caught.toString());
							}

							@Override
							public void onSuccess(Nutzerprofil result) {
								infoLabel
										.setText("ShowEigenesNp: Erfolgreiche Rückgabe Vector");
								infoLabel1.setText("ShowEigenesNp: Benutzer = "
										+ Benutzer.getProfilId());
							}
						});
	}
}

