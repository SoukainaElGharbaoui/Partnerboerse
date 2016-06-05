package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class CreateInfoSp extends VerticalPanel {

	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private FlexTable showEigenschaftFlexTable = new FlexTable();

	private String beschreibungstext;
	private String nEingabe;

	private Button createInfosButton = new Button("Info anlegen");
	private Label ueberschriftLabel = new Label("Info anlegen:");
	private Label informationLabelB = new Label();
	private Label informationLabelA = new Label();

	/**
	 * Konstruktor hinzufügen.
	 */
	public CreateInfoSp(final int suchprofilId) {
		this.add(verPanel);

		/**
		 * Tabelle zur Anzeige der Eigenschaften hinzufügen.
		 */

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showEigenschaftFlexTable.setText(0, 0, "Eigenschaft-Id");
		showEigenschaftFlexTable.setText(0, 1, "Erlaeuterung");
		showEigenschaftFlexTable.setText(0, 2, "Anlegen");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");

		/**
		 * Überschrift-Label hinzufügen.
		 */

		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Information-Labels für die Benutzerinformation hinzufügen.
		 */

		ClientsideSettings.getPartnerboerseAdministration()
				.getAllEigenschaftenNeu(new AsyncCallback<List<Eigenschaft>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabelB.setText("Es trat ein Fehler auf");
					}

					@Override
					public void onSuccess(List<Eigenschaft> result) {
						int row = showEigenschaftFlexTable.getRowCount();

						for (Eigenschaft e : result) {
							row++;

							String eigenschaftId = String.valueOf(e.getEigenschaftId());
							final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);

							showEigenschaftFlexTable.setText(row, 0, eigenschaftId);
							showEigenschaftFlexTable.setText(row, 1, e.getErlaeuterung());

							if (e.getTyp() == "B") {

								final TextArea textArea = new TextArea();
								showEigenschaftFlexTable.setWidget(row, 2, textArea);

								ClientsideSettings.getPartnerboerseAdministration().getEigBById(eigenschaftIdInt,
										new AsyncCallback<Beschreibungseigenschaft>() {

											@Override
											public void onFailure(Throwable caught) {
												informationLabelB.setText("Beim Herausholen des Beschreibungstextes "
														+ "ist ein Fehler aufgetreten.");
											}

											@Override
											public void onSuccess(Beschreibungseigenschaft result) {
												informationLabelB.setText("Das Herausholen des Beschreibungstextes hat "
														+ "funktioniert.");

												beschreibungstext = result.getBeschreibungstext();
												textArea.setText(beschreibungstext);
											}
										});

								createInfosButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {

										if (textArea.getText() != null) {

											if (textArea.getText() != beschreibungstext) {
												nEingabe = textArea.getText();
											}
										}

								else {
											informationLabelB.setText("Bitte machen Sie eine Eingabe im Textfeld.");
										}

										// profilId = Benutzer.getProfilId();

										ClientsideSettings.getPartnerboerseAdministration()
												.createInfoNeu(eigenschaftIdInt, nEingabe, new AsyncCallback<Info>() {

													@Override
													public void onFailure(Throwable caught) {
														informationLabelB.setText("Es trat ein Fehler auf.");
													}

													@Override
													public void onSuccess(Info result) {
														informationLabelB.setText("Die Infos wurden "
																+ "erfolgreich angelegt." + suchprofilId);

//														 ShowEigenesNp showNp
//														 = new
//														 ShowEigenesNp();
//														 RootPanel.get("Details").clear();
//														 RootPanel.get("Details").add(showNp);
													}
												});
									}
								});
							}

					else if (e.getTyp() == "A") {

								final ListBox lb = new ListBox();

								ClientsideSettings.getPartnerboerseAdministration().getEigAById(eigenschaftIdInt,
										new AsyncCallback<Auswahleigenschaft>() {

											@Override
											public void onFailure(Throwable caught) {
												informationLabelA.setText("Beim Herausholen der Auswahloptionen "
														+ "ist ein Fehler aufgetreten.");
											}

											@Override
											public void onSuccess(Auswahleigenschaft result) {

												List<String> optionen = result.getOptionen();

												for (int i = 0; i < optionen.size(); i++) {
													lb.addItem(optionen.get(i));
												}
											}
										});
								showEigenschaftFlexTable.setWidget(row, 2, lb);

								createInfosButton.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {

										nEingabe = lb.getSelectedItemText();

										ClientsideSettings.getPartnerboerseAdministration()
												.createInfoNeu(eigenschaftIdInt, nEingabe, new AsyncCallback<Info>() {

													@Override
													public void onFailure(Throwable caught) {
														informationLabelA.setText("Es trat ein Fehler auf.");
													}

													@Override
													public void onSuccess(Info result) {
														informationLabelA
																.setText("Die Infos wurden " + "erfolgreich angelegt.");

														// ShowEigenesNp showNp
														// = new
														// ShowEigenesNp();
														// RootPanel.get("Details").clear();
														// RootPanel.get("Details").add(showNp);
													}
												});
									}
								});
							}

						}
					}
				});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenschaftFlexTable);
		verPanel.add(createInfosButton);
		verPanel.add(informationLabelB);
		verPanel.add(informationLabelA);
	}
}
