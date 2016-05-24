package de.hdm.gruppe7.partnerboerse.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class EditNutzerprofil extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	private Label ueberschriftLabel = new Label("Nutzerprofil bearbeiten:");
	private FlexTable editNutzerprofilFlexTable = new FlexTable();
	private TextBox vornameTextBox = new TextBox();
	private TextBox nachnameTextBox = new TextBox();
	private ListBox geschlechtListBox = new ListBox();
	
	// Geburtsdatum
	private DateBox geburtsdatumDateBox = new DateBox(); 
	private Label geburtsdatumInhalt = new Label(); 
	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	private TextBox koerpergroesseTextBox = new TextBox();
	private ListBox haarfarbeListBox = new ListBox();
	private ListBox raucherListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	
	private Label informationLabel = new Label();

	private Button editNutzerprofilButton = new Button("Speichern");
	
	public EditNutzerprofil(int nutzerprofilId) {
		this.add(verPanel);
		
		ueberschriftLabel.addStyleName("partnerboerse-label"); 
		
		editNutzerprofilFlexTable.setCellPadding(6);
		editNutzerprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		editNutzerprofilFlexTable.addStyleName("FlexTable");

		editNutzerprofilFlexTable.setText(0, 0, "Nutzerrofil-Id");
		editNutzerprofilFlexTable.setText(1, 0, "Vorname");
		editNutzerprofilFlexTable.setText(2, 0, "Nachname");
		editNutzerprofilFlexTable.setText(3, 0, "Geschlecht");
		editNutzerprofilFlexTable.setText(4, 0, "Geburtsdatum");
		editNutzerprofilFlexTable.setText(5, 0, "Körpergröße");
		editNutzerprofilFlexTable.setText(6, 0, "Haarfarbe");
		editNutzerprofilFlexTable.setText(7, 0, "Raucherstatus");
		editNutzerprofilFlexTable.setText(8, 0, "Religion");
		
		// Vorname
		editNutzerprofilFlexTable.setWidget(1, 2, vornameTextBox);
		
		// Nachname
		editNutzerprofilFlexTable.setWidget(2, 2, nachnameTextBox);
		
		// Geschlecht
		geschlechtListBox.addItem("");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editNutzerprofilFlexTable.setWidget(3, 2, geschlechtListBox);
	
		// Geburtsdatum
		geburtsdatumDateBox.setFormat(new DateBox.DefaultFormat(geburtsdatumFormat));
		geburtsdatumDateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
		geburtsdatumDateBox.getDatePicker().setVisibleYearCount(20);
		
		geburtsdatumDateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date geburtsdatum = event.getValue();
				String geburtsdatumString = DateTimeFormat.getFormat("yyyy-MM-dd").format(geburtsdatum);
				geburtsdatumInhalt.setText(geburtsdatumString);
				}
			});
		
		geburtsdatumDateBox.setValue(new Date());
		
		editNutzerprofilFlexTable.setWidget(4, 2, geburtsdatumDateBox);
		
		// Körpergröße
		editNutzerprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);
		
		// Haarfarbe
		haarfarbeListBox.addItem("");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editNutzerprofilFlexTable.setWidget(6, 2, haarfarbeListBox);
		
		// Raucher
		raucherListBox.addItem("");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editNutzerprofilFlexTable.setWidget(7, 2, raucherListBox);
		
		// Religion
		religionListBox.addItem("");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editNutzerprofilFlexTable.setWidget(8, 2, religionListBox);
		
		// Bestehende Werte abrufen.
		ClientsideSettings.getPartnerboerseAdministration()
				.getNutzerprofilById(Benutzer.getProfilId(),
						new AsyncCallback<Nutzerprofil>() {
							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(Nutzerprofil result) {

								vornameTextBox.setText(result.getVorname());
								
								nachnameTextBox.setText(result.getNachname());
								
								geschlechtListBox.setItemText(0, result.getGeschlecht());
								
								for(int i = 0; i < geschlechtListBox.getItemCount(); i++) {
									if (result.getGeschlecht() == geschlechtListBox.getValue(i)) { 
										geschlechtListBox.removeItem(i);
									}
								}
								
								geburtsdatumDateBox.setValue(result.getGeburtsdatumDate());
								
								koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));
								
								haarfarbeListBox.setItemText(0, result.getHaarfarbe());
								
								for(int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
									if (result.getHaarfarbe() == haarfarbeListBox.getValue(i)) { 
										haarfarbeListBox.removeItem(i);
									}
								}

								religionListBox.setItemText(0, result.getReligion());
								
								for(int i = 0; i < religionListBox.getItemCount(); i++) {
									if (result.getReligion() == religionListBox.getValue(i)) { 
										religionListBox.removeItem(i);
									}
								}

								raucherListBox.setItemText(0, result.getRaucher());

								for(int i = 0; i < raucherListBox.getItemCount(); i++) {
									if (result.getRaucher() == raucherListBox.getValue(i)) { 
										raucherListBox.removeItem(i);
									}
								}
							}
						});
		
		// Widgets zum VerticalPanel hinzufügen.
		verPanel.add(ueberschriftLabel); 
		verPanel.add(editNutzerprofilFlexTable);
		verPanel.add(editNutzerprofilButton);
		verPanel.add(informationLabel); 
		
		editNutzerprofilButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				ClientsideSettings.getPartnerboerseAdministration().saveNutzerprofil
				(vornameTextBox.getText(), nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), 
						getGeburtsdatum(), Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(), 
						raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(), new AsyncCallback<Void> () {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Es trat ein Fehler auf");
								
							}

							@Override
							public void onSuccess(Void result) {
								ShowEigenesNp showEigenesNp = new ShowEigenesNp(); 
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showEigenesNp); 
								
							}
					
				});
				
			}
			
		}); 
		

	}
	
	Date getGeburtsdatum(){
		Date geburtsdatum = geburtsdatumFormat.parse(geburtsdatumInhalt.getText());
		java.sql.Date sqlDate = new java.sql.Date(geburtsdatum.getTime());
		return sqlDate;
	}
}
