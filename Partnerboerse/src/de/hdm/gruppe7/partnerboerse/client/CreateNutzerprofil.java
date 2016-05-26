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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateNutzerprofil extends VerticalPanel {

		private VerticalPanel verPanel = new VerticalPanel();

		private LoginInfo loginInfo;
		private Label ueberschriftLabel = new Label("Nutzerprofil anlegen:");
		private FlexTable createNutzerprofilFlexTable = new FlexTable();
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



		/**
		 * Konstruktor hinzufügen. 
		 */
	

		private Button createNutzerprofilButton = new Button("Nutzerprofil anlegen");
		

		public CreateNutzerprofil() {
			this.add(verPanel);

		ueberschriftLabel.addStyleName("partnerboerse-label"); 
	
		createNutzerprofilFlexTable.setCellPadding(6);
		createNutzerprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		createNutzerprofilFlexTable.addStyleName("FlexTable");
		
		createNutzerprofilFlexTable.setText(0, 0, "Vorname");
		createNutzerprofilFlexTable.setText(1, 0, "Nachname");
		createNutzerprofilFlexTable.setText(2, 0, "Geschlecht");
		createNutzerprofilFlexTable.setText(3, 0, "Geburtsdatum");
		createNutzerprofilFlexTable.setText(4, 0, "Körpergröße");
		createNutzerprofilFlexTable.setText(5, 0, "Haarfarbe");
		createNutzerprofilFlexTable.setText(6, 0, "Raucherstatus");
		createNutzerprofilFlexTable.setText(7, 0, "Religion");
		
		// Vorname
		createNutzerprofilFlexTable.setWidget(0, 2, vornameTextBox);
		
		// Nachname
		createNutzerprofilFlexTable.setWidget(1, 2, nachnameTextBox);
		
		// Geschlecht
		geschlechtListBox.addItem("Keine Auswahl");
		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		createNutzerprofilFlexTable.setWidget(2, 2, geschlechtListBox);
		
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
		
		createNutzerprofilFlexTable.setWidget(3, 2, geburtsdatumDateBox);
		
		// Körpergröße
		createNutzerprofilFlexTable.setWidget(4, 2, koerpergroesseTextBox);
		
		// Haarfarbe
		haarfarbeListBox.addItem("Keine Auswahl");
		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		createNutzerprofilFlexTable.setWidget(5, 2, haarfarbeListBox);
		
		// Raucher
		raucherListBox.addItem("Keine Angabe");
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		createNutzerprofilFlexTable.setWidget(6, 2, raucherListBox);
		
		// Religion
		religionListBox.addItem("Keine Auswahl");
		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		createNutzerprofilFlexTable.setWidget(7, 2, religionListBox);
		
		// Widgets zum VerticalPanel hinzufügen.
		verPanel.add(ueberschriftLabel);
		verPanel.add(createNutzerprofilFlexTable);
		verPanel.add(createNutzerprofilButton);
		verPanel.add(informationLabel); 


	}

		public CreateNutzerprofil(LoginInfo loginInfo) {
			this.loginInfo = loginInfo;
		
		// ClickHandler für den createNutzerprofil-Button hinzufügen.
		createNutzerprofilButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().createNutzerprofil
				(vornameTextBox.getText(), nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(),
				 getGeburtsdatum(), Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
				 raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(), new AsyncCallback<Nutzerprofil>() {

					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf");	
						
					}

					@Override
					public void onSuccess(Nutzerprofil result) {
						informationLabel.setText("Ihr Nutzerprofil wurde erfolgreich angelegt");	
						
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
