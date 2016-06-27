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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

/**
 * Diese Klasse dient dazu, eine Info anzuzeigen.
 */
public class ShowInfo extends VerticalPanel {

	/**
	 * Panels erzeugen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();


	/**
	 * Attribute erzeugen.
	 */
	private String listtyp;
	private String profiltyp; 

	private int row;
	private int eigenschaftIdInt;
	private int eigenschaftIdTable;
	private int zaehler;
	private int profilId;

	/**
	 * Widgets erzeugen.
	 */
	private Label ueberschriftLabel = new Label("Infos zu diesem Profil:");
	private Label informationLabel = new Label();
	
	private Button erstelleRestlicheInfosButton = new Button("Infos anlegen");
	private Button bearbeitenButton = new Button("Infos bearbeiten");
	private Button loeschenButton = new Button("Alle Infos löschen");
	
	private FlexTable showInfoFlexTable = new FlexTable();
	
	/**
	 * Konstruktor hinzufuegen.
	 * 
	 * @param profilId Die Profil-Id des Profils (Nutzerprofil / Suchprofil / Fremdprofil)
	 * @param profiltyp Der Profiltyp des Profils (Nutzerprofil / Suchprofil / Fremdprofil)
	 * @param listtyp Der Listtyp der Seite, von der das Anzeigen der Infos aufgerufen wird (Nutzerprofil / Suchprofil / Fremdprofil)
	 */
	public ShowInfo(int profilId, String profiltyp, String listtyp) {
		this.profilId = profilId; 
		this.profiltyp = profiltyp; 
		this.listtyp = listtyp;
		
		run(); 
	}
	
	/**
	 * Methode erstellen, die den Aufbau der Seite startet.
	 */
	public void run() {
		
		/**
		 * Vertikales Panel hinzufuegen.
		 */
		this.add(verPanel);
		

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");
		ueberschriftLabel.addStyleName("partnerboerse-label");
		informationLabel.addStyleName("partnerboerse-label");

		getAllInfos(); 

		/**
		 * Widgets zum Panel hinzufuegen.
		 */
		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);
		

		/**
		 * Pruefen, ob es sich um ein Info eines Fremdprofils handelt. Wenn
		 * nicht, werden entsprechende Buttons hinzugefuegt. Loeschen, Bearbeiten
		 * und Anlegen.
		 */
		if (!profiltyp.equals("Fp")) {
			buttonPanel.add(erstelleRestlicheInfosButton);
			buttonPanel.add(bearbeitenButton);
			buttonPanel.add(loeschenButton);
			verPanel.add(buttonPanel);
		}

		/**
		 * ClickHandler fuer den Button zum Loeschen der gesamten Info. Es mus
		 * geprueft werden, ob es sich um die Info eines Nutzerprofils oder
		 * eines Suchprofils handelt.
		 */
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteAllInfos();
			}
		});

		/**
		 * ClickHandler fuer den Button zum Bearbeiten der Info erzeugen. Sobald
		 * der Button betaetigt wird, wird die Seite zum Bearbeiten der Info
		 * aufgerufen.
		 */
		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditInfo editInfo = new EditInfo(profilId, profiltyp, listtyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editInfo);
			}
		});

		/**
		 * ClickHandler fuer den Button zum Erstellen der noch nicht angelegten
		 * Infos erzeugen. Sobald der Button betaetigt wird, wird die Seite zum
		 * hinzufuegen der bislang nicht ngelegten Infos aufgerufen.
		 */
		erstelleRestlicheInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateUnusedInfos createRestlicheInfos = new CreateUnusedInfos(profilId, profiltyp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createRestlicheInfos);
			}
		});

	}
	
	/**
	 * Methode, die alle Infos anhand der Profil-ID aus der Datenbank ausliest und die
	 * Infos in die Tabelle einfuegt.
	 */
	public void getAllInfos() {
		
		ClientsideSettings.getPartnerboerseAdministration().getAllInfos(profilId,
				new AsyncCallback<Map<List<Info>, List<Eigenschaft>>>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es ist ein Fehler beim Herausholen der Infos aufgetreten.");
					}

					@Override
					public void onSuccess(Map<List<Info>, List<Eigenschaft>> result) {

						Set<List<Info>> output = result.keySet();

						for (List<Info> listI : output) {
							
							row = showInfoFlexTable.getRowCount();

							for (Info i : listI) {
								
								showInfoFlexTable.setCellPadding(6);
								showInfoFlexTable.getColumnFormatter().addStyleName(2, "TableHeader");
								showInfoFlexTable.addStyleName("FlexTable");
								
								row++;

								showInfoFlexTable.setText(row, 0, String.valueOf(i.getProfilId()));
								showInfoFlexTable.setText(row, 1, String.valueOf(i.getEigenschaftId()));
								showInfoFlexTable.setText(row, 3, i.getInfotext());
							}

							List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
							listE = result.get(listI);

							row = 0;
							row = showInfoFlexTable.getRowCount();

							for (Eigenschaft e : listE) {

								row++;

								eigenschaftIdInt = 0;
								eigenschaftIdInt = e.getEigenschaftId();

								for (int i = 2; i < showInfoFlexTable.getRowCount(); i++) {

									eigenschaftIdTable = 0;
									eigenschaftIdTable = Integer.valueOf(showInfoFlexTable.getText(i, 1));

									if (eigenschaftIdInt == eigenschaftIdTable) {

										showInfoFlexTable.setText(i, 2, e.getErlaeuterung());
									}

									else {
									}
								}
							}
						}

						boolean befuellt = pruefeLeereTable();

						if (befuellt == true) {

							ueberschriftLabel.setVisible(false);
							showInfoFlexTable.setVisible(false);
							loeschenButton.setVisible(false);
							bearbeitenButton.setVisible(false);

							informationLabel.setText("Zu diesem Profil existieren zurzeit keine Infos.");
						}
					}
				});
	}
	
	/**
	 * Prueft, ob die Tabelle leer ist.
	 * 
	 * @return boolean Boolscher Wert, der angibt, ob die Tabelle leer ist.
	 */
	public boolean pruefeLeereTable() {

		for (int k = 2; k < showInfoFlexTable.getRowCount(); k++) {

			if (showInfoFlexTable.getText(k, 0) == null) {
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
	 * Methode, die alle Infos loescht.
	 */
	public void deleteAllInfos() {
		
		ClientsideSettings.getPartnerboerseAdministration().deleteAllInfosNeu(profilId,
				new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Beim Löschen aller Infos ist ein Fehler aufgetreten.");
					}

					@Override
					public void onSuccess(Void result) {
						informationLabel.setText("Das Löschen aller Infos hat funktioniert.");

						/**
						 * Fall, profilId gehoert zu Nutzerprofil
						 */
						if (profiltyp == "Np") {

							ShowNutzerprofil showNp = new ShowNutzerprofil(profilId, profiltyp);

							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showNp);
						}

						/**
						 * Fall, profilId gehoert zu Suchprofil
						 */
						else if (profiltyp == "Sp") {

							int suchprofilId = profilId;
							ShowSuchprofil showSp = new ShowSuchprofil(suchprofilId, profiltyp);

							RootPanel.get("Details").clear();
							RootPanel.get("Details").add(showSp);
						}
					}
				});
	}
}
