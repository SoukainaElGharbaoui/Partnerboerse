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
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	/**
	 * Listen erzeugen.
	 */
	private List<Beschreibungseigenschaft> listB;
	private List<Auswahleigenschaft> listA;

	/**
	 * Attribute erzeugen
	 */
	private int row;
	private int zaehler;
	private int profilId;
	private String profiltyp;

	/**
	 * Widgets erzeugen.
	 */
	private FlexTable showUnusedEigenschaftFlexTable = new FlexTable();
	private Button createInfosButton = new Button("Infos anlegen");
	private Button abbrechenButton = new Button("Abbrechen");
	private Label ueberschriftLabel = new Label("Infos anlegen:");
	private Label informationLabelB = new Label();
	private Label informationLabelA = new Label();
	private Label informationLabel = new Label();
	private Label pfadLabelNpA = new Label("Zurück zu: Profil anzeigen");
	private Label pfadLabelSpA = new Label("Zurück zu: Suchprofil anzeigen");

	/**
	 * Konstruktor hinzufuegen.
	 * 
	 * @param profilId
	 * @param profiltyp
	 */
	public CreateUnusedInfos(int profilId, String profiltyp) {
		this.profilId = profilId;
		this.profiltyp = profiltyp;
		run();
	}

	/**
	 * Die Methode startet den Aufbau der Seite.
	 */
	public void run() {
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
		pfadLabelNpA.addStyleName("partnerboerse-zurueckbutton");
		pfadLabelSpA.addStyleName("partnerboerse-zurueckbutton");


		pruefeProfilart();

		befuelleTabelle();

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
						}
					}
				}

				erstelleInfo(infos);

			}
		});
		
		/**
		 * ClickHandler fuer den Button zum Abbrechen.
		 */
		abbrechenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (profiltyp.equals("Np")) {
					ShowNutzerprofil showNutzerprofil = new ShowNutzerprofil(profilId, profiltyp); 
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showNutzerprofil);
				}
				
				else if (profiltyp.equals("Sp")) {
					ShowSuchprofil showSp = new ShowSuchprofil(profilId, profiltyp); 
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showSp);
				}
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

		buttonPanel.add(createInfosButton);
		buttonPanel.add(abbrechenButton);
		verPanel.add(ueberschriftLabel);
		verPanel.add(showUnusedEigenschaftFlexTable);
		verPanel.add(buttonPanel);
		verPanel.add(informationLabelB);
		verPanel.add(informationLabelA);
		verPanel.add(informationLabel);
	}

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
	 * Bisher nicht angelegte Eigenschaften anhand der Profil-ID aus der
	 * Datenbank auslesen und die Infodaten in die Tabelle einfuegen. Erst die
	 * Beschreibungsinfos, danach die Auswahlinfos.
	 */
	public void befuelleTabelle() {

		ClientsideSettings.getPartnerboerseAdministration().getAllUnusedEigenschaften(profilId,
				new AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result) {
						showUnusedEigenschaftFlexTable.setCellPadding(6);
						showUnusedEigenschaftFlexTable.getColumnFormatter().addStyleName(2, "TableHeader");
						showUnusedEigenschaftFlexTable.addStyleName("FlexTable");

						row = showUnusedEigenschaftFlexTable.getRowCount();

						Set<List<Beschreibungseigenschaft>> output = result.keySet();

						for (List<Beschreibungseigenschaft> listEigB : output) {

							listB = listEigB;

							for (Beschreibungseigenschaft eigB : listEigB) {

								row++;

								Label id = new Label(String.valueOf(eigB.getEigenschaftId()));
								
								showUnusedEigenschaftFlexTable.setWidget(row, 1, id);
								id.setVisible(false);

								showUnusedEigenschaftFlexTable.setText(row, 2, eigB.getErlaeuterung());

								final TextArea textArea = new TextArea();

								showUnusedEigenschaftFlexTable.setWidget(row, 3, textArea);

								String defaultValue = eigB.getBeschreibungstext();

								textArea.getElement().setPropertyString("placeholder", defaultValue);
							}

							listA = result.get(listEigB);

							for (Auswahleigenschaft eigA : listA) {

								row++;

								Label id = new Label(String.valueOf(eigA.getEigenschaftId()));
								
								showUnusedEigenschaftFlexTable.setWidget(row, 1, id);
								id.setVisible(false);

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
							abbrechenButton.setVisible(false);
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
	}

	/**
	 * Erstellen der Info.
	 * 
	 * @param infos
	 */
	public void erstelleInfo(List<Info> infos) {
		ClientsideSettings.getPartnerboerseAdministration().createInfo(profilId, infos,
				new AsyncCallback<List<Info>>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<Info> result) {

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

	/**
	 * Prueft ob es sich um ein Nutzerprofil oder Suchprofil handelt.
	 */
	public void pruefeProfilart() {
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
	}
}
