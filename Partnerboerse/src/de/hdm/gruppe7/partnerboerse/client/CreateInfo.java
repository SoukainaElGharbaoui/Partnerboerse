package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
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
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Die Klasse dient dazu, eine Info für das Nutzerprofil oder das Suchprofil zu
 * erstellen.
 */
public class CreateInfo extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt, das die Login-Informationen enthaelt,
	 * erzeugen.
	 */
	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();

	/**
	 * Vertikales Panel erzeugen.
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
	private String eigenschaftId = null;
	private int row;

	/**
	 * Widgets erzeugen.
	 */
	private FlexTable showEigenschaftFlexTable = new FlexTable();
	private Button createInfosButton = new Button("Infos anlegen");
	private Label ueberschriftLabel = new Label("Infos anlegen:");
	private Label informationLabel = new Label();

	/**
	 * Konstruktor erstellen.
	 *
	 * @param profilId
	 * @param profiltyp
	 */
	public CreateInfo(final int profilId, final String profiltyp) {
		this.add(verPanel);

		/**
		 * CSS anwenden und Tabelle formatieren.
		 */
		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");
		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Erste Spalte der Tabelle festlegen.
		 */
		showEigenschaftFlexTable.setText(0, 0, "Profil-Id");
		showEigenschaftFlexTable.setText(0, 1, "Eigenschaft-Id");
		showEigenschaftFlexTable.setText(0, 2, "Erlaeuterung");
		showEigenschaftFlexTable.setText(0, 3, "Anlegen");

		/**
		 * Die Eigenschaften werden mit Hilfe eines Maps aus der Datenbank
		 * ausgelesen. Erst die Beschreibungsinfos, danach die Auswahlinfos.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaften(
				new AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Beim Herausholen der Eigenschaften trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result) {

						row = showEigenschaftFlexTable.getRowCount();
						Set<List<Beschreibungseigenschaft>> output = result.keySet();

						for (List<Beschreibungseigenschaft> listEigB : output) {

							listB = listEigB;

							for (Beschreibungseigenschaft eigB : listEigB) {

								row++;

								eigenschaftId = null;

								showEigenschaftFlexTable.setText(row, 0, String.valueOf(profilId));

								eigenschaftId = String.valueOf(eigB.getEigenschaftId());

								showEigenschaftFlexTable.setText(row, 1, eigenschaftId);
								showEigenschaftFlexTable.setText(row, 2, eigB.getErlaeuterung());

								final TextArea textArea = new TextArea();

								showEigenschaftFlexTable.setWidget(row, 3, textArea);

								String defaultValue = eigB.getBeschreibungstext();

								textArea.getElement().setPropertyString("placeholder", defaultValue);
							}

							listA = result.get(listEigB);

							for (Auswahleigenschaft eigA : listA) {

								row++;

								showEigenschaftFlexTable.setText(row, 0, String.valueOf(profilId));

								showEigenschaftFlexTable.setText(row, 1, String.valueOf(eigA.getEigenschaftId()));
								showEigenschaftFlexTable.setText(row, 2, eigA.getErlaeuterung());

								final ListBox lb = new ListBox();

								showEigenschaftFlexTable.setWidget(row, 3, lb);

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

				for (int i = 2; i < showEigenschaftFlexTable.getRowCount(); i++) {

					k = 0;
					k = i - 2;

					String eigenschaftIdTable = showEigenschaftFlexTable.getText(i, 1);

					Widget w = showEigenschaftFlexTable.getWidget(i, 3);

					if (w instanceof TextArea) {

						infotextTable = ((TextArea) w).getText();

						if (infotextTable.equals(listB.get(k).getBeschreibungstext())) {
						}

						else if (((TextArea) w).getText().isEmpty()) {
							informationLabel.setText("Das Eingabefeld ist leer.");
						}

						else {
							Info info = new Info();
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}
					}

					else if (w instanceof ListBox) {

						infotextTable = ((ListBox) w).getSelectedItemText();

						if (infotextTable.equals("Keine Auswahl")) {
						}

						else {
							Info info = new Info();
							info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
							info.setInfotext(infotextTable);

							infos.add(info);
						}
					}
				}

				/**
				 * Die Infos werden in die Datenbank eingefügt.Danach wird
				 * geprueft, ob es sich um Nutzerprofil oder ein Suchprofil
				 * handelt. Handelt es sich um ein Nutzerprofil, dann wird man
				 * nach dem Anlegen auf das erstelle Nutzerprofil
				 * weitergeleitet. Es wird ebenfalls der logout hinzugefügt und
				 * gesetzt. Handelt es sich um Suchprofil, dann wird man nach
				 * dem Anlegen auf das entsprechende Suchprofil weitergeleitet.
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
									RootPanel.get("Navigator").add(new Navigator());

									RootPanel.get("Details").clear();
									RootPanel.get("Details").add(showNp);

									Anchor signOut = new Anchor();

									signOut.setHref(GWT.getHostPageBaseURL() + "Partnerboerse.html");
									signOut.setText("Als " + nutzerprofil.getVorname() + nutzerprofil.getProfilId()
											+ " ausloggen");

									RootPanel.get("Navigator").add(signOut);
								}

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
		verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenschaftFlexTable);
		verPanel.add(createInfosButton);
		verPanel.add(informationLabel);
	}
}
