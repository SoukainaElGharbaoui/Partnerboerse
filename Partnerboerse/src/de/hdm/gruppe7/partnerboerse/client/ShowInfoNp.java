
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
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;


public class ShowInfoNp extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	private Label ueberschriftLabel = new Label("Ihre Infos:");
	private Label informationLabel = new Label();

	private FlexTable showInfoFlexTable = new FlexTable();

	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	private int row;
	private int eigenschaftIdInt;
	private int eigenschaftIdTable;

	/**
	 * Konstruktor
	 * 
	 * @param integer
	 */
	public ShowInfoNp(int profilId) {
		this.add(verPanel);

		/**
		 * Label �berschrift
		 */

		ueberschriftLabel.addStyleName("partnerboerse-label");

		/**
		 * Label Button
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		showInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		showInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		showInfoFlexTable.setText(0, 2, "Eigenschaft");
		showInfoFlexTable.setText(0, 3, "Infotext");

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */
		showInfoFlexTable.setCellPadding(6);
		showInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		showInfoFlexTable.addStyleName("FlexTable");

		/**
		 * InfoLabel erstellen um Text auszugeben
		 */
		
		
	ClientsideSettings.getPartnerboerseAdministration().getAllInfos(new AsyncCallback<Map<List<Info>,List<Eigenschaft>>>() {

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
					
					row++;
					
					showInfoFlexTable.setText(row, 0, String.valueOf(i.getProfilId()));
					showInfoFlexTable.setText(row, 1, String.valueOf(i.getEigenschaftId()));
					showInfoFlexTable.setText(row, 3, i.getInfotext());
				}
				
				
				List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
				listE = result.get(listI);
				
				row  = 0;
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
		}
		
	});
		

		verPanel.add(showInfoFlexTable);
		verPanel.add(ueberschriftLabel);
		verPanel.add(showInfoFlexTable);
		verPanel.add(informationLabel);

		final Button loeschenButton = new Button("Alle Infos löschen");
		verPanel.add(buttonPanel);
		buttonPanel.add(loeschenButton);

		final Button bearbeitenButton = new Button("Bearbeiten");
		verPanel.add(buttonPanel);
		buttonPanel.add(bearbeitenButton);

		final Button erstellenButton = new Button("Infos anlegen");
		verPanel.add(buttonPanel);
		buttonPanel.add(erstellenButton);
		
		final Button createRestlicheInfosButton = new Button("Weitere Infos anlegen.");
		verPanel.add(buttonPanel);
		buttonPanel.add(erstellenButton);

		
		loeschenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().deleteAllInfosNeu(new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Beim Löschen aller Infos ist ein Fehler aufgetreten.");
					}

					@Override
					public void onSuccess(Void result) {
						informationLabel.setText("Das Löschen aller Infos hat funktioniert.");

						ShowEigenesNp showNp = new ShowEigenesNp(nutzerprofil);

						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(showNp);
					}
				});
			}
		});

		bearbeitenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditInfoNp editInfoNp = new EditInfoNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(editInfoNp);
			}
		});

		erstellenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateInfoNp createInfoNp = new CreateInfoNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createInfoNp);
			}
		});
		
		createRestlicheInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateUnusedInfos createRestlicheInfos = new CreateUnusedInfos();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createRestlicheInfos);
			}
		});

	}
}