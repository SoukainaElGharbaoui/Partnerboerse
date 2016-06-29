
package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Die Klasse dient dazu, eine Info für das Nutzerprofil oder das Suchprofil zu
 * erstellen.
 */
public class CreateInfo extends VerticalPanel {

	/**
	 * Neues Nutzerprofil-Objekt erzeugen, das die Login-Informationen enthaelt,
	 */
	private Nutzerprofil nutzerprofil = Partnerboerse.getNp();

	/**
	 * Panels erzeugen.
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
	private String eigenschaftId = null;
	private int row;

	/**
	 * Widgets erzeugen.
	 */
	private FlexTable showEigenschaftFlexTable = new FlexTable();
	private Button createInfosButton = new Button("Infos anlegen");
	private Button abbrechenButton = new Button("Abbrechen");
	private Label ueberschriftLabel = new Label("Infos anlegen:");

	/**
	 * Konstruktor erstellen.
	 *
	 * @param profilId
	 *            Die Profil-Id, entweder des Nutzerprofils oder des Suchprofils
	 * @param profiltyp
	 *            Der Profiltyp, entweder mit dem Inhalt "Np" (Nutzerprofil)
	 *            oder "Sp" (Suchprofil)
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
		 * Die Eigenschaften werden mit Hilfe eines Maps aus der Datenbank
		 * herausgeholt, ausgelesen und anschliessend der Tabelle hinzugefuegt.
		 * Erst die Beschreibungsinfos, danach die Auswahlinfos.
		 */
		ClientsideSettings.getPartnerboerseAdministration().getAllEigenschaften(
				new AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result) {

						row = showEigenschaftFlexTable.getRowCount();
						Set<List<Beschreibungseigenschaft>> output = result.keySet();

						for (List<Beschreibungseigenschaft> listEigB : output) {

							listB = listEigB;

							for (Beschreibungseigenschaft eigB : listEigB) {

								showEigenschaftFlexTable.setCellPadding(6);
								showEigenschaftFlexTable.getColumnFormatter().addStyleName(1, "TableHeader");
								showEigenschaftFlexTable.addStyleName("FlexTable");

								row++;

								eigenschaftId = null;
								Label id = new Label(String.valueOf(eigB.getEigenschaftId()));

								eigenschaftId = String.valueOf(eigB.getEigenschaftId());

								showEigenschaftFlexTable.setWidget(row, 0, id);
								showEigenschaftFlexTable.setText(row, 1, eigB.getErlaeuterung());
								id.setVisible(false);
								final TextArea textArea = new TextArea();

								showEigenschaftFlexTable.setWidget(row, 2, textArea);

								String defaultValue = eigB.getBeschreibungstext();

								textArea.getElement().setPropertyString("placeholder", defaultValue);
							}

							listA = result.get(listEigB);

							for (Auswahleigenschaft eigA : listA) {
								showEigenschaftFlexTable.setCellPadding(6);
								showEigenschaftFlexTable.getColumnFormatter().addStyleName(1, "TableHeader");
								showEigenschaftFlexTable.addStyleName("FlexTable");

								row++;

								Label id = new Label(String.valueOf(eigA.getEigenschaftId()));

								showEigenschaftFlexTable.setWidget(row, 0, id);
								showEigenschaftFlexTable.setText(row, 1, eigA.getErlaeuterung());
								id.setVisible(false);

								final ListBox lb = new ListBox();

								showEigenschaftFlexTable.setWidget(row, 2, lb);

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
		 * ClickHandler fuer den Button zum Anlegen der Infos. Die befuellte
		 * Tabelle wird durchlaufen und die Eingaben des Nutzers herausgelesen.
		 * Eine neue Info wird instanziiert und die Daten dieser Info
		 * zugewiesen. Ist ein Eingabefeld leer oder identisch mit dem
		 * vordefinierten Beschreibungstext, der im Eingabefeld erscheint, so
		 * wird die Info nicht angelegt. Ebenso bei der Angabe "Keine Auswahl"
		 * in den Auswahlboxen.
		 */

		createInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				List<Info> infos = new ArrayList<Info>();
				String infotextTable = null;

				int k;

				for (int i = 2; i < showEigenschaftFlexTable.getRowCount(); i++) {

					k = 0;
					k = i - 2;

					String eigenschaftIdTable = showEigenschaftFlexTable.getText(i, 0);

					Widget w = showEigenschaftFlexTable.getWidget(i, 2);

					if (w instanceof TextArea) {

						infotextTable = ((TextArea) w).getText();

						if (infotextTable.equals(listB.get(k).getBeschreibungstext())) {
						}

						else if (((TextArea) w).getText().isEmpty()) {
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
				 * Die Infos werden in die Datenbank eingefuegt. Danach wird
				 * geprueft, ob es sich um Nutzerprofil oder ein Suchprofil
				 * handelt. Handelt es sich um ein Nutzerprofil, dann wird man
				 * nach dem Anlegen auf das erstelle Nutzerprofil
				 * weitergeleitet. Es wird ebenfalls der Logout hinzugefügt und
				 * gesetzt. Handelt es sich um Suchprofil, dann wird man nach
				 * dem Anlegen auf das entsprechende Suchprofil weitergeleitet.
				 */
				ClientsideSettings.getPartnerboerseAdministration().createInfo(profilId, infos,
						new AsyncCallback<List<Info>>() {

							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(List<Info> result) {

								if (profiltyp.equals("Np")) {

									ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);

									Window.Location.replace("Partnerboerse.html");

									RootPanel.get("Details").clear();
									RootPanel.get("Navigator").add(new Navigator(nutzerprofil));
									RootPanel.get("Details").add(showNp);

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
		buttonPanel.add(createInfosButton);
		buttonPanel.add(abbrechenButton);
		
		verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenschaftFlexTable);
		verPanel.add(buttonPanel);
	}
}
