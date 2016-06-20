package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

/**
 * Anlegen von bisher nicht angelegten Infos.
 */
public class CreateUnusedInfos extends VerticalPanel {

	/**
	 * VerticalPanel hinzufuegen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	/**
	 * Listen erzeugen.
	 */
	private List<Beschreibungseigenschaft> listB;
	private List<Auswahleigenschaft> listA;

	/**
	 * Attribute erzeugen
	 */
	private String eigenschaftId;
	private int row;
	private int zaehler;

	/**
	 * Widgets erzeugen.
	 */
	private FlexTable showUnusedEigenschaftFlexTable = new FlexTable();
	private Button createInfosButton = new Button("Infos anlegen");
	private Label ueberschriftLabel = new Label("Infos anlegen:");
	private Label informationLabelB = new Label();
	private Label informationLabelA = new Label();
	private Label informationLabel = new Label();
	private Label pfadLabelNpA = new Label("Zurück zu: Profil anzeigen");
	private Label pfadLabelSpA = new Label("Zurück zu: Suchprofil anzeigen");

	/**
	 * Prüft, ob die Tabelle leer ist.
	 * 
	 * @return boolean
	 */
	public boolean pruefeLeereTable() {

		for (int k = 2; k < showUnusedEigenschaftFlexTable.getRowCount(); k++) {

			if (showUnusedEigenschaftFlexTable.getText(k, 0) == null) {
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
	 * Konstruktor hinzufuegen.
	 * 
	 * @param profilId
	 * @param profiltyp
	 */
	public CreateUnusedInfos(final int profilId, final String profiltyp) {

		/**
		 * Vertikales Panel hinzufuegen.
		 */
		this.add(verPanel);

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showUnusedEigenschaftFlexTable.setCellPadding(6);
		showUnusedEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showUnusedEigenschaftFlexTable.addStyleName("FlexTable");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showUnusedEigenschaftFlexTable.setText(0, 0, "Profil-Id");
		showUnusedEigenschaftFlexTable.setText(0, 1, "Eigenschaft-Id");
		showUnusedEigenschaftFlexTable.setText(0, 2, "Erlaeuterung");
		showUnusedEigenschaftFlexTable.setText(0, 3, "Anlegen");

		if (profiltyp.equals("Np")) {
			pfadLabelNpA.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);

					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showNp);
				}

			});
		} else if (profiltyp.equals("Sp")) {

			pfadLabelSpA.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					ShowSuchprofil showSp = new ShowSuchprofil(profilId, profiltyp);

					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showSp);
				}

			});
		}

		/**
		 * Bisher nicht angelegte Eigenschaften anhand der Profil-ID aus der
		 * Datenbank auslesen und die Infodaten in die Tabelle einfuegen. Erst
		 * die Beschreibungsinfos, danach die Auswahlinfos.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllUnusedEigenschaften(profilId,
				new AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Beim Herausholen der Eigenschaften trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result) {

						informationLabel.setText("Die Methode wurde aufgerufen.");

						row = showUnusedEigenschaftFlexTable.getRowCount();

						Set<List<Beschreibungseigenschaft>> output = result.keySet();

						for (List<Beschreibungseigenschaft> listEigB : output) {

							listB = listEigB;

							for (Beschreibungseigenschaft eigB : listEigB) {

								row++;

								eigenschaftId = null;

								showUnusedEigenschaftFlexTable.setText(row, 0, String.valueOf(profilId));

								eigenschaftId = String.valueOf(eigB.getEigenschaftId());

								showUnusedEigenschaftFlexTable.setText(row, 1, eigenschaftId);

								showUnusedEigenschaftFlexTable.setText(row, 2, eigB.getErlaeuterung());

								final TextArea textArea = new TextArea();

								showUnusedEigenschaftFlexTable.setWidget(row, 3, textArea);

								String defaultValue = eigB.getBeschreibungstext();

								textArea.getElement().setPropertyString("placeholder", defaultValue);
							}

							listA = result.get(listEigB);

							for (Auswahleigenschaft eigA : listA) {

								row++;

								showUnusedEigenschaftFlexTable.setText(row, 0, String.valueOf(profilId));

								showUnusedEigenschaftFlexTable.setText(row, 1, String.valueOf(eigA.getEigenschaftId()));

								showUnusedEigenschaftFlexTable.setText(row, 2, eigA.getErlaeuterung());

								final ListBox lb = new ListBox();

								showUnusedEigenschaftFlexTable.setWidget(row, 3, lb);

								List<String> optionen = eigA.getOptionen();

								for (int i = 0; i < optionen.size(); i++) {
									lb.addItem(optionen.get(i));
								}

								for (int a = 0; a < lb.getItemCount(); a++) {

									if (lb.getValue(a).equals("Keine Auswahl")) {
										lb.setItemSelected(a, true);
									}
								}
							}
						}

						boolean befuellt = pruefeLeereTable();

						if (befuellt == true) {

							ueberschriftLabel.setText("Sie haben bereits alle Infos angelegt.");

							showUnusedEigenschaftFlexTable.setVisible(false);
							createInfosButton.setVisible(false);
							informationLabel.setVisible(false);

							ueberschriftLabel.setVisible(true);

							Button showInfosButton = new Button("Infos anzeigen");

							verPanel.add(showInfosButton);

							showInfosButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									if (profiltyp.equals("Np")) {

										ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);

										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(showNp);
									}

									/**
									 * Fall, die profilId gehoert zu Suchprofil
									 */
									else if (profiltyp.equals("Sp")) {

										ShowSuchprofil showSp = new ShowSuchprofil(profilId, profiltyp);

										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(showSp);
									}
								}
							});
						}
					}

				});

		/**
		 * ClickHandler fuer den Button zum Anlegen einer Info.
		 */
		createInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				List<Info> infos = new ArrayList<Info>();
				String infotextTable = null;

				int k;

				for (int i = 2; i < showUnusedEigenschaftFlexTable.getRowCount(); i++) {

					k = 0;
					k = i - 2;

					String eigenschaftIdTable = showUnusedEigenschaftFlexTable.getText(i, 1);

					Widget w = showUnusedEigenschaftFlexTable.getWidget(i, 3);

					if (w instanceof TextArea) {

						infotextTable = ((TextArea) w).getText();

						if (infotextTable.equals(listB.get(k).getBeschreibungstext())) {
						}

						else if (!((TextArea) w).getText().isEmpty()) {
							Info info = new Info();
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}

						else {
							informationLabelB.setText("Das Eingabefeld ist leer.");
						}

					}

					else if (w instanceof ListBox) {

						infotextTable = ((ListBox) w).getSelectedItemText();

						if (!infotextTable.equals("Keine Auswahl")) {

							Info info = new Info();
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}

						else {
							informationLabelB.setText("Das Eingabefeld ist leer.");
						}
					}
				}

				/**
				 * ClickHandler fuer den Button zum Anlegen einer Info.
				 */
				ClientsideSettings.getPartnerboerseAdministration().createInfo(profilId, infos,
						new AsyncCallback<List<Info>>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(List<Info> result) {
								informationLabel.setText("Die Infos wurden " + "erfolgreich angelegt.");

								if (profiltyp.equals("Np")) {

									ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);

									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showNp);
								}

								/**
								 * Fall, die profilId gehoert zu Suchprofil
								 */
								else if (profiltyp.equals("Sp")) {

									ShowSuchprofil showSp = new ShowSuchprofil(profilId, profiltyp);

									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showSp);
								}

							}
						});
			}
		});

		/**
		 * Widgets zum Panel hinzufuegen.
		 */
		if (profiltyp.equals("Np")) {
			verPanel.add(pfadLabelNpA);
		} else if (profiltyp.equals("Sp")) {
			verPanel.add(pfadLabelSpA);
		}

		verPanel.add(ueberschriftLabel);
		verPanel.add(showUnusedEigenschaftFlexTable);
		verPanel.add(createInfosButton);
		verPanel.add(informationLabelB);
		verPanel.add(informationLabelA);
		verPanel.add(informationLabel);
	}
}
