package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowPartnervorschlaege extends VerticalPanel {
	private int aehnlichkeit = 0;

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	public ShowPartnervorschlaege() {

		VerticalPanel verPanel1 = new VerticalPanel();
		final Label infoLabel = new Label();

		/**
		 * Button "Partnervorschlaege mit Nutzerprofil anzeigen" hinzufügen.
		 */
		final Button showPartnervorschlaegeNpButton = new Button("Partnervorschlaege mit Nutzerprofil anzeigen");

		showPartnervorschlaegeNpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				ShowPartnervorschlaegeNp showPartnervorschlaegeNp = new ShowPartnervorschlaegeNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaegeNp);
			}

		});

		this.add(showPartnervorschlaegeNpButton);

		/**
		 * Button "Partnervorschlaege mit Suchprofil anzeigen" hinzufügen.
		 */
		final Button showPartnervorschlaegeSpButton = new Button("Partnervorschlaege mit Suchprofil anzeigen");

		/**
		 * Bei Bet�tigung des Buttons werden alle Suchprofile des Nutzers mit
		 * allen Nutzerprofilen verglichen und in der Datenbank gespeichert
		 */

		showPartnervorschlaegeSpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.getAllSuchprofileFor(new AsyncCallback<List<Suchprofil>>() {

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(List<Suchprofil> result1) {

								for (Suchprofil sp : result1) {

									final int suchprofilId = sp.getProfilId();
									final String suchprofilName = sp.getSuchprofilName();

									// Alle Nutzerprofile werden aufgerufen

									ClientsideSettings.getPartnerboerseAdministration()
											.getNutzerprofileOhneGesetzteSperrung(new AsyncCallback<List<Nutzerprofil>>() {

												@Override
												public void onFailure(Throwable caught) {
													infoLabel.setText("Es trat ein Fehler auf.");

												}

												@Override
												public void onSuccess(List<Nutzerprofil> result) {

													// infoLabel.setText("Es
													// hier trat kein Fehler
													// auf.");
													for (Nutzerprofil np : result) {

														final int fremdprofilId = np.getProfilId();

														// Hier wird die
														// Aehnlichkeit aller
														// Nutzerprofile und den
														// Suchprofilen
														// errechnet
														ClientsideSettings.getPartnerboerseAdministration()
																.berechneAehnlichkeitSpFor(suchprofilId, fremdprofilId,
																		new AsyncCallback<Integer>() {

																			@Override
																			public void onFailure(Throwable caught) {
																				infoLabel.setText(
																						"Es trat ein Fehler auf.");
																			}

																			@Override
																			public void onSuccess(Integer result3) {
																				// infoLabel.setText("Es
																				// hier
																				// trat
																				// kein
																				// Fehler
																				// auf.");
																				aehnlichkeit = result3;

																				// die
																				// Aehnlichket
																				// wird
																				// in
																				// der
																				// Datenbank
																				// gespeichert
																				ClientsideSettings
																						.getPartnerboerseAdministration()
																						.aehnlichkeitSetzenSp(
																								suchprofilId,
																								suchprofilName,
																								fremdprofilId,
																								aehnlichkeit,
																								new AsyncCallback<Void>() {

																									@Override
																									public void onFailure(
																											Throwable caught) {
																										infoLabel
																												.setText(
																														"Es trat ein Fehler auf.");
																									}

																									@Override
																									public void onSuccess(
																											Void result4) {
																										// TODO
																										// Auto-generated
																										// method
																										// stub

																									}

																								});
																			}

																		});

													}

												}

											});

								}

							}
						});

				ShowPartnervorschlaegeSp showPartnervorschlaegeSp = new ShowPartnervorschlaegeSp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaegeSp);
			}

		});

		this.add(infoLabel);
		this.add(showPartnervorschlaegeSpButton);

	}
}
