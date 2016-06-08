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
import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;

public class EditNutzerprofil extends VerticalPanel {

	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	public VerticalPanel verPanel = new VerticalPanel();
	public Label ueberschriftLabel = new Label("Nutzerprofil bearbeiten:");
	public FlexTable editNutzerprofilFlexTable = new FlexTable();
	public TextBox vornameTextBox = new TextBox();
	public TextBox nachnameTextBox = new TextBox();
	public ListBox geschlechtListBox = new ListBox();
	public TextBox emailTextBox = new TextBox();

	// Geburtsdatum
	public DateBox geburtsdatumDateBox = new DateBox();
	private Label geburtsdatumInhalt = new Label();
	private DateTimeFormat geburtsdatumFormat = DateTimeFormat.getFormat("yyyy-MM-dd");

	public TextBox koerpergroesseTextBox = new TextBox();
	public ListBox haarfarbeListBox = new ListBox();
	public ListBox raucherListBox = new ListBox();
	public ListBox religionListBox = new ListBox();

	public Label informationLabel = new Label();

	public Button editNutzerprofilButton = new Button("Speichern");

	// Konstruktor
	public EditNutzerprofil() {
		this.add(verPanel);

		ueberschriftLabel.addStyleName("partnerboerse-label");
		verPanel.add(ueberschriftLabel);

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
		editNutzerprofilFlexTable.setText(9, 0, "E-Mail");

		editNutzerprofilFlexTable.setWidget(1, 2, vornameTextBox);

		editNutzerprofilFlexTable.setWidget(2, 2, nachnameTextBox);

		geschlechtListBox.addItem("Weiblich");
		geschlechtListBox.addItem("Männlich");
		editNutzerprofilFlexTable.setWidget(3, 2, geschlechtListBox);

		// Geburtdatum
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

		editNutzerprofilFlexTable.setWidget(5, 2, koerpergroesseTextBox);

		haarfarbeListBox.addItem("Blond");
		haarfarbeListBox.addItem("Braun");
		haarfarbeListBox.addItem("Rot");
		haarfarbeListBox.addItem("Schwarz");
		haarfarbeListBox.addItem("Grau");
		haarfarbeListBox.addItem("Glatze");
		editNutzerprofilFlexTable.setWidget(6, 2, haarfarbeListBox);

		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");
		editNutzerprofilFlexTable.setWidget(7, 2, raucherListBox);

		religionListBox.addItem("Christlich");
		religionListBox.addItem("Juedisch");
		religionListBox.addItem("Muslimisch");
		religionListBox.addItem("Buddhistisch");
		religionListBox.addItem("Hinduistisch");
		editNutzerprofilFlexTable.setWidget(8, 2, religionListBox);
		
		editNutzerprofilFlexTable.setWidget(9, 2, emailTextBox);

		ClientsideSettings.getPartnerboerseAdministration().getNutzerprofilById(
				new AsyncCallback<Nutzerprofil>() {
					@Override
					public void onFailure(Throwable caught) {
						informationLabel.setText("Es trat ein Fehler auf.");
					}

					@Override
					public void onSuccess(Nutzerprofil result) {

						vornameTextBox.setText(result.getVorname());

						nachnameTextBox.setText(result.getNachname());

						for (int i = 0; i < geschlechtListBox.getItemCount(); i++) {
							if (result.getGeschlecht().equals(geschlechtListBox.getValue(i))) {
								geschlechtListBox.setSelectedIndex(i);
							}
						}

						geburtsdatumDateBox.setValue(result.getGeburtsdatumDate());

						koerpergroesseTextBox.setText(Integer.toString(result.getKoerpergroesseInt()));

						for (int i = 0; i < haarfarbeListBox.getItemCount(); i++) {
							if (result.getHaarfarbe().equals(haarfarbeListBox.getValue(i))) {
								haarfarbeListBox.setSelectedIndex(i);
							}
						}

						for (int i = 0; i < religionListBox.getItemCount(); i++) {
							if (result.getReligion().equals(religionListBox.getValue(i))) {
								religionListBox.setSelectedIndex(i);
							}
						}

						for (int i = 0; i < raucherListBox.getItemCount(); i++) {
							if (result.getRaucher().equals(raucherListBox.getValue(i))) {
								raucherListBox.setSelectedIndex(i);
							}
						}
						
						emailTextBox.setText(result.getEmailAddress());
					}
				});

		// Widgets zum VerticalPanel hinzufügen.
		verPanel.add(ueberschriftLabel);
		verPanel.add(editNutzerprofilFlexTable);
		verPanel.add(editNutzerprofilButton);
		verPanel.add(informationLabel);

		/**
		 * ClickHandler für den Button updateNutzerprofilButton
		 */

		editNutzerprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ClientsideSettings.getPartnerboerseAdministration().saveNutzerprofil(vornameTextBox.getText(),
						nachnameTextBox.getText(), geschlechtListBox.getSelectedItemText(), getGeburtsdatum(),
						Integer.parseInt(koerpergroesseTextBox.getText()), haarfarbeListBox.getSelectedItemText(),
						raucherListBox.getSelectedItemText(), religionListBox.getSelectedItemText(), emailTextBox.getText(),
						new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable caught) {
								informationLabel.setText("Es trat ein Fehler auf");
							}

							@Override
							public void onSuccess(Void result) {
								ShowEigenesNp showEigenesNp = new ShowEigenesNp(nutzerprofil);
//								ShowEigenesNp showEigenesNp = new ShowEigenesNp();
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showEigenesNp);

							}
						});
				

				

			}
		});

	}

	Date getGeburtsdatum() {
		Date geburtsdatum = geburtsdatumFormat.parse(geburtsdatumInhalt.getText());
		java.sql.Date sqlDate = new java.sql.Date(geburtsdatum.getTime());
		return sqlDate;
	}
}