
package de.hdm.gruppe7.partnerboerse.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class EditInfoNp extends VerticalPanel {

	Nutzerprofil nutzerprofil = new Nutzerprofil();
	
	/**
	 * VerticalPanels hinzufügen.
	 */

	private VerticalPanel verPanel = new VerticalPanel();
	Button loeschenButton = new Button("Löschen");

	private FlexTable editInfoFlexTable = new FlexTable();
	private Label ueberschriftLabel = new Label("Info bearbeiten:");
	final Button updateInfosButton = new Button("&Auml;nderungen speichern");
	private Label informationLabel = new Label();

	private int row;
	// private int eigenschaftIdInt;
	private int profilIdInt;
//	private String infotext;
	// private String infotextNeu;
//	private String typ;

	/**
	 * Konstruktor hinzufügen.
	 */

	public EditInfoNp() {
		this.add(verPanel);

		/**
		 * Tabelle formatieren und CSS einbinden.
		 */

		editInfoFlexTable.setCellPadding(6);
		editInfoFlexTable.getRowFormatter().addStyleName(0, "TableHeader");
		editInfoFlexTable.addStyleName("FlexTable");

		/**
		 * Erste Zeile der Tabelle festlegen.
		 */
		editInfoFlexTable.setText(0, 0, "Nutzerprofil-Id");
		editInfoFlexTable.setText(0, 1, "Eigenschaft-Id");
		editInfoFlexTable.setText(0, 2, "Eigenschaft");
		editInfoFlexTable.setText(0, 3, "Bearbeiten");
		editInfoFlexTable.setText(0, 4, "Löschen");

		ueberschriftLabel.addStyleName("partnerboerse-label");

//		ClientsideSettings.getPartnerboerseAdministration().getAllInfosNeu(new AsyncCallback<List<String>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				informationLabel.setText("Fehler");
//			}
//
//			@Override
//			public void onSuccess(List<String> result) {
//				informationLabel.setText("Das Anzeigen der Infos hat funktioniert.");
//
//				row = editInfoFlexTable.getRowCount();
//
//				int size = result.size();
//
//				for (int i = 0; i < size; i++) {
//
//					String profilId = result.get(i);
//					String eigenschaftId = result.get(i + 1);
//					String erlaeuterung = result.get(i + 2);
//					final String infotext = result.get(i + 3);
//					final String typ = result.get(i + 4);
//
//					editInfoFlexTable.setText(row, 0, profilId);
//					editInfoFlexTable.setText(row, 1, eigenschaftId);
//					editInfoFlexTable.setText(row, 2, erlaeuterung);
//
//					profilIdInt = Integer.valueOf(profilId);
//					final int eigenschaftIdInt = Integer.valueOf(eigenschaftId);
//
//					if (typ == "B") {
//
//						final TextBox tb = new TextBox();
//						tb.setText(infotext);
//						editInfoFlexTable.setWidget(row, 3, tb);
//
//						updateInfosButton.addClickHandler(new ClickHandler() {
//							public void onClick(ClickEvent event) {
//
//								String infotextNeuB = tb.getText();
//
//								ClientsideSettings.getPartnerboerseAdministration().saveInfoNeu(
//										eigenschaftIdInt, infotextNeuB, new AsyncCallback<Void>() {
//
//											@Override
//											public void onFailure(Throwable caught) {
//												informationLabel
//														.setText("Beim Aktualisieren ist ein Fehler " + "aufgetreten.");
//											}
//
//											@Override
//											public void onSuccess(Void result) {
//												informationLabel
//														.setText("Das Aktualisieren der Infos " + "hat funktioniert.");
//											}
//
//										});
//							}
//						});
//					}
//
//					else if (typ == "A") {
//
//						final ListBox lb = new ListBox();
//						editInfoFlexTable.setWidget(row, 3, lb);
//
//						ClientsideSettings.getPartnerboerseAdministration().getEigAById(eigenschaftIdInt,
//								new AsyncCallback<Auswahleigenschaft>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										informationLabel.setText("Fehler bei Holen der Auswahloptionen.");
//									}
//
//									@Override
//									public void onSuccess(Auswahleigenschaft result) {
//
//										List<String> optionen = new ArrayList<String>();
//										optionen = result.getOptionen();
//
//										for (int o = 0; o < optionen.size(); o++) {
//											lb.addItem(optionen.get(o));
//
//											informationLabel.setText(
//													"Das Herausholen der Auswahloptionen " + "hat funktioniert.");
//										}
//
//										for (int a = 0; a < lb.getItemCount(); a++) {
//
//											if (lb.getValue(a).equals(infotext)) {
//												lb.setItemSelected(a, true);
//											}
//
//											informationLabel.setText(
//													"Das Setzen der bisher " + "ausgewählten Option funktioniert.");
//										}
//									}
//								});
//						
//						
//						updateInfosButton.addClickHandler(new ClickHandler() {
//							public void onClick(ClickEvent event) {
//						
//						String infotextNeuA = lb.getSelectedValue();
//
//						ClientsideSettings.getPartnerboerseAdministration().saveInfoNeu(eigenschaftIdInt,
//								infotextNeuA, new AsyncCallback<Void>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										informationLabel.setText("Beim Aktualisieren ist ein Fehler " + "aufgetreten.");
//									}
//
//									@Override
//									public void onSuccess(Void result) {
//										informationLabel.setText("Das Aktualisieren der Infos " + "hat funktioniert.");
//										
//										ShowEigenesNp showEigenesNp = new ShowEigenesNp(nutzerprofil);
//										RootPanel.get("Details").clear();
//										RootPanel.get("Details").add(showEigenesNp);
//										
//									}
//
//								});
//							}
//						});
//
//					}
//
//					loeschenButton = new Button("Löschen");
//					editInfoFlexTable.setWidget(row, 4, loeschenButton);
//
//					loeschenButton.addClickHandler(new ClickHandler() {
//						public void onClick(ClickEvent event) {
//
//							for (int i = 1; i < editInfoFlexTable.getRowCount(); i++) {
//
//								String tableEigenschaftId = editInfoFlexTable.getText(i, 1);
//
//								if (Integer.valueOf(tableEigenschaftId) == eigenschaftIdInt) {
//
//									ClientsideSettings.getPartnerboerseAdministration()
//											.deleteOneInfoNeu(eigenschaftIdInt, new AsyncCallback<Void>() {
//
//												@Override
//												public void onFailure(Throwable caught) {
//													informationLabel
//															.setText("Beim Löschen der Info trat ein Fehler auf.");
//												}
//
//												@Override
//												public void onSuccess(Void result) {
//													informationLabel.setText("Das Löschen der Info hat funktioniert.");
//												}
//											});
//									editInfoFlexTable.removeRow(i);
//									break;
//								}
//
//							}
//
//						}
//					});
//
//					// updateInfosButton.addClickHandler(new ClickHandler() {
//					// public void onClick(ClickEvent event) {
//					//
//					// ClientsideSettings.getPartnerboerseAdministration().saveInfoNeu(
//					// profilIdInt, eigenschaftIdInt, infotextNeu,
//					// new AsyncCallback<Void>(){
//					//
//					// @Override
//					// public void onFailure(
//					// Throwable caught) {
//					// informationLabel.setText("Beim Aktualisieren ist ein
//					// Fehler "
//					// + "aufgetreten.");
//					// }
//					//
//					// @Override
//					// public void onSuccess(
//					// Void result) {
//					// informationLabel.setText("Das Aktualisieren der Infos "
//					// + "hat funktioniert.");
//					// }
//					//
//					//
//					// });
//					// }
//					// });
//
//					row++;
//					i++;
//					i++;
//					i++;
//					i++;
//				}
//			}
//		});

		verPanel.add(ueberschriftLabel);
		verPanel.add(editInfoFlexTable);
		verPanel.add(informationLabel);
		verPanel.add(updateInfosButton);

	}
}