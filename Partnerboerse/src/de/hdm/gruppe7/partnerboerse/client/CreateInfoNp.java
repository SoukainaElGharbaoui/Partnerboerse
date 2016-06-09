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
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class CreateInfoNp extends VerticalPanel {

	Nutzerprofil nutzerprofil = new Nutzerprofil();

	private VerticalPanel verPanel = new VerticalPanel();
	private FlexTable showEigenschaftFlexTable = new FlexTable();

	private String eigenschaftId = null;
	private String beschreibungstext = null;

	private Button createInfosButton = new Button("Info anlegen");
	private Label ueberschriftLabel = new Label("Info anlegen:");
	private Label informationLabel = new Label();

	public CreateInfoNp() {
		this.add(verPanel);

		showEigenschaftFlexTable.setText(0, 0, "Eigenschaft-Id");
		showEigenschaftFlexTable.setText(0, 1, "Erlaeuterung");
		showEigenschaftFlexTable.setText(0, 2, "Anlegen");

		showEigenschaftFlexTable.setCellPadding(6);
		showEigenschaftFlexTable.getRowFormatter().addStyleName(0,
				"TableHeader");
		showEigenschaftFlexTable.addStyleName("FlexTable");

		ueberschriftLabel.addStyleName("partnerboerse-label");

		ClientsideSettings
				.getPartnerboerseAdministration()
				.getAllEigenschaften(
						new AsyncCallback<Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel
										.setText("Beim Herausholen der Eigenschaften trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(
									Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result) {

								int row = showEigenschaftFlexTable
										.getRowCount();
								Set<List<Beschreibungseigenschaft>> output = result
										.keySet();

								for (List<Beschreibungseigenschaft> listEigB : output) {

									for (Beschreibungseigenschaft eigB : listEigB) {

										row++;

										eigenschaftId = null;
										beschreibungstext = null;

										eigenschaftId = String.valueOf(eigB
												.getEigenschaftId());

										showEigenschaftFlexTable.setText(row,
												0, eigenschaftId);
										showEigenschaftFlexTable.setText(row,
												1, eigB.getErlaeuterung());

										final TextArea textArea = new TextArea();
										textArea.setTitle("TextArea");

										showEigenschaftFlexTable.setWidget(row,
												2, textArea);

										beschreibungstext = eigB
												.getBeschreibungstext();

										textArea.setText(beschreibungstext);
									}

									List<Auswahleigenschaft> listEigA = result
											.get(listEigB);

									for (Auswahleigenschaft eigA : listEigA) {

										row++;
										showEigenschaftFlexTable.setText(row,
												0, String.valueOf(eigA
														.getEigenschaftId()));
										showEigenschaftFlexTable.setText(row,
												1, eigA.getErlaeuterung());

										final ListBox lb = new ListBox();
										lb.setTitle("ListBox");

										showEigenschaftFlexTable.setWidget(row,
												2, lb);

										List<String> optionen = eigA
												.getOptionen();

										for (int i = 0; i < optionen.size(); i++) {
											lb.addItem(optionen.get(i));
										}

										for (int a = 0; a < lb.getItemCount(); a++) {

											if (lb.getValue(a).equals(
													"Keine Auswahl")) {
												lb.setItemSelected(a, true);
											}
										}
									}
								}
							}
						});

		createInfosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				List<Info> infos = new ArrayList<Info>();
				String infotextTable = null;

				for (int i = 2; i < showEigenschaftFlexTable.getRowCount(); i++) {

					String eigenschaftIdTable = showEigenschaftFlexTable
							.getText(i, 0);

					Widget w = showEigenschaftFlexTable.getWidget(i, 2);

					if (w instanceof TextArea) {

						infotextTable = ((TextArea) w).getText();
					}

					else if (w instanceof ListBox) {

						infotextTable = ((ListBox) w).getSelectedItemText();

					}

					Info info = new Info();
					info.setEigenschaftId(Integer.valueOf(eigenschaftIdTable));
					info.setInfotext(infotextTable);

					infos.add(info);
				}

				ClientsideSettings.getPartnerboerseAdministration().createInfo(
						infos, new AsyncCallback<List<Info>>() {

							@Override
							public void onFailure(Throwable caught) {
								informationLabel
										.setText("Es trat ein Fehler auf.");
							}

							@Override
							public void onSuccess(List<Info> result) {
								informationLabel.setText("Die Infos wurden "
										+ "erfolgreich angelegt.");

								ShowEigenesNp showNp = new ShowEigenesNp(
										nutzerprofil);
								RootPanel.get("Details").clear();
								RootPanel.get("Details").add(showNp);

							}
						});
			}
		});

		verPanel.add(ueberschriftLabel);
		verPanel.add(showEigenschaftFlexTable);
		verPanel.add(createInfosButton);
		verPanel.add(informationLabel);
	}
}
