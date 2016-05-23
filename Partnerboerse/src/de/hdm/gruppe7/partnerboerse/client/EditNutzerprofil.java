package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class EditNutzerprofil extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/**
	 * Konstruktor hinzufügen. 
	 */
	public EditNutzerprofil(int nutzerprofilId) {
		this.add(verPanel);
		
		/**
		 * Label �berschrift
		 */
		final Label ueberschriftLabel = new Label("Nutzerprofil bearbeiten:");
		ueberschriftLabel.addStyleName("partnerboerse-label"); 
		verPanel.add(ueberschriftLabel);

		
		
		/**
		 * Tabelle erzeugen, in der das Suchprofil dargestellt wird und bearbeitet werden kann.
		 */
		final FlexTable editNutzerprofilFlexTable = new FlexTable();
		
		/**
		 *  * Tabelle formatieren und CSS einbinden.
		 */
		editNutzerprofilFlexTable.setCellPadding(6);
		editNutzerprofilFlexTable.getColumnFormatter().addStyleName(0,
				"TableHeader");
		editNutzerprofilFlexTable.addStyleName("FlexTable");
		
		/**
		 * Erste Spalte der Tabelle festlegen.
		 */

		editNutzerprofilFlexTable.setText(0, 0, "Nutzerrofil-Id");
		editNutzerprofilFlexTable.setText(1, 0, "Vorname");
		editNutzerprofilFlexTable.setText(2, 0, "Nachname");
		editNutzerprofilFlexTable.setText(3, 0, "Geschlecht");
		editNutzerprofilFlexTable.setText(4, 0, "Geburtsdatum");
		editNutzerprofilFlexTable.setText(5, 0, "Körpergröße");
		editNutzerprofilFlexTable.setText(6, 0, "Haarfarbe");
		editNutzerprofilFlexTable.setText(7, 0, "Raucherstatus");
		editNutzerprofilFlexTable.setText(8, 0, "Religion");
		
		/**
		 * Drittte Spalte der Tabelle festlegen (Textboxen zum bearbeiten der Werte)
		 */

		
		final TextBox vornameTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(1, 2, vornameTextBox);
		
		final TextBox nachnameTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(2, 2, nachnameTextBox);
		
		final ListBox geschlechtListBox = new ListBox();
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editNutzerprofilFlexTable.setWidget(3, 2, geschlechtListBox);
	
		final TextBox geburtsdatumTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(4, 2, geburtsdatumTextBox);
		
		final TextBox koerpergroesseTextBox = new TextBox();
		editNutzerprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);
		
		final ListBox haarfarbeListBox = new ListBox();
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editNutzerprofilFlexTable.setWidget(6, 2, haarfarbeListBox);
		
		final ListBox raucherListBox = new ListBox();
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editNutzerprofilFlexTable.setWidget(7, 2, raucherListBox);
		
		final ListBox religionListBox = new ListBox();
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editNutzerprofilFlexTable.setWidget(8, 2, religionListBox);
	
		/**
		 * infoLabel für die Benutzerinformation erzeugen.
		 */
		final Label infoLabel = new Label();
		verPanel.add(infoLabel);

		final Label infoLabel2 = new Label();
		verPanel.add(infoLabel2);
		

		/**
		 * Die Textboxen werden zu anfangs mit den bestehenden Eigenschaften gef�llt.
		 */
		ClientsideSettings.getPartnerboerseAdministration()
				.getNutzerprofilById(Benutzer.getProfilId(),
						new AsyncCallback<Nutzerprofil>() {
							@Override
							public void onFailure(Throwable caught) {
								infoLabel2.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Nutzerprofil result) {

								vornameTextBox.setText(result.getVorname());
								
								nachnameTextBox.setText(result.getNachname());
								
								for(int i = 0; i < geschlechtListBox.getItemCount(); i++) {
									if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) { 
										geschlechtListBox.setSelectedIndex(i);
									}
								}
								
								geburtsdatumTextBox.setText(result.getGeburtsdatum());
								
								koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));
								
								for(int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
									if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) { 
										haarfarbeListBox.setSelectedIndex(i);
									}
								}

								for(int i = 0; i < religionListBox.getItemCount(); i++) {
									if (result.getReligion().equals(religionListBox.getValue(i))) { 
										religionListBox.setSelectedIndex(i);
									}
								}

								for(int i = 0; i < raucherListBox.getItemCount(); i++) {
									if (result.getRaucher().equals(raucherListBox.getValue(i))) { 
										raucherListBox.setSelectedIndex(i);
									}
								}
							}
						});
		
		verPanel.add(editNutzerprofilFlexTable);
		/**
		 * updateNutzerprofilButton, um das Nutzerprofil zu aktualisieren.
		 */
		final Button updateNutzerprofilButton = new Button(
				"&Auml;nderungen speichern");
		verPanel.add(updateNutzerprofilButton);
		/**
		 * ClickHandler für den Button updateNutzerprofilButton
		 */

		updateNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration()
						.saveNutzerprofil(vornameTextBox.getText(),
								nachnameTextBox.getText(),
								geschlechtListBox.getSelectedItemText(),
								geburtsdatumTextBox.getText(),
								Integer.parseInt(koerpergroesseTextBox.getText()),
								haarfarbeListBox.getSelectedItemText(),
								raucherListBox.getSelectedItemText(),
								religionListBox.getSelectedItemText(),
								new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										infoLabel.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Void result) {
										ShowEigenesNp showEigenesNp = new ShowEigenesNp();
										RootPanel.get("Details").clear();
										RootPanel.get("Details").add(
												showEigenesNp);

									}
								});

			}
		});
	}
}
