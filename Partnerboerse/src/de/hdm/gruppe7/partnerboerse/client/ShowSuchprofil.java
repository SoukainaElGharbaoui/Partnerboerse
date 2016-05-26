package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

public class ShowSuchprofil extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();
	
	private HorizontalPanel auswahlPanel = new HorizontalPanel(); 
	
	private HorizontalPanel buttonPanel = new HorizontalPanel(); 

	/**
	 * Konstruktor
	 */
	public ShowSuchprofil() {
		this.add(verPanel);
		
		final Label auswahlLabel = new Label("Wählen Sie das anzuzeigende Suchprofil aus."); 
		auswahlLabel.addStyleName("partnerboerse-label"); 
		
		final Label infoLabel = new Label(); 
		
		final ListBox auswahlListBox = new ListBox(); 
		
		final Button anzeigenButton = new Button("Anzeigen"); 
		
		final FlexTable showSuchprofilFlexTable = new FlexTable();
		
		showSuchprofilFlexTable.setCellPadding(6);
		showSuchprofilFlexTable.getColumnFormatter().addStyleName(0, "TableHeader");
		showSuchprofilFlexTable.addStyleName("FlexTable");
		
		showSuchprofilFlexTable.setText(0, 0, "Suchprofil-id");
		showSuchprofilFlexTable.setText(1, 0, "Suchprofilname");
		showSuchprofilFlexTable.setText(2, 0, "Geschlecht");
		showSuchprofilFlexTable.setText(3, 0, "Alter von");
		showSuchprofilFlexTable.setText(4, 0, "Alter bis");
		showSuchprofilFlexTable.setText(5, 0, "Koerpergroesse");
		showSuchprofilFlexTable.setText(6, 0, "Haarfarbe");
		showSuchprofilFlexTable.setText(7, 0, "Raucher");
		showSuchprofilFlexTable.setText(8, 0, "Religion");
		
		final Button loeschenButton = new Button("Löschen");
		
		final Button bearbeitenButton = new Button("Bearbeiten");
		
		final Button createSuchprofilButton = new Button("Neues Suchprofil anlegen"); 
		
		ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(Benutzer.getProfilId(),
				new AsyncCallback<List<Suchprofil>>() {

					@Override
					public void onFailure(Throwable caught) {
						infoLabel.setText("Es trat ein Fehler auf."); 
						
					}

					@Override
					public void onSuccess(List<Suchprofil> result) {
						for(Suchprofil s : result) {
							auswahlListBox.addItem(s.getSuchprofilName()); 
						}
							
					}
			
		});
		
		createSuchprofilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateSuchprofil createSuchprofil = new CreateSuchprofil(); 
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createSuchprofil);
			}
			
		}); 
		
		anzeigenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				ClientsideSettings.getPartnerboerseAdministration().getSuchprofilByName(Benutzer.getProfilId(),
						auswahlListBox.getSelectedItemText(), new AsyncCallback<Suchprofil>() { 

							@Override
							public void onFailure(Throwable caught) {
								infoLabel.setText("Es trat ein Fehler auf.");
								
							}

							@Override
							public void onSuccess(Suchprofil result) {
								
								// Suchprofil-ID
								final String suchprofilId = String.valueOf(result.getProfilId());
								showSuchprofilFlexTable.setText(0, 1, suchprofilId);
								
								// Suchprofilname 
								showSuchprofilFlexTable.setText(1, 1, result.getSuchprofilName());
								
								// Geschlecht 
								showSuchprofilFlexTable.setText(2, 1, result.getGeschlecht());
								
								// AlterMax
								showSuchprofilFlexTable.setText(3, 1, Integer.toString(result.getAlterMinInt()));
		
								// AlterMin 
								showSuchprofilFlexTable.setText(4, 1, Integer.toString(result.getAlterMaxInt()));
		
								// Koerpergroesse 
								showSuchprofilFlexTable.setText(5, 1, Integer.toString(result.getKoerpergroesseInt()));
		
								// Haarfarbe 
								showSuchprofilFlexTable.setText(6, 1, result.getHaarfarbe());
		
								// Raucher 
								showSuchprofilFlexTable.setText(7, 1, result.getRaucher());
		
								// Religion aus der Datenbank holen
								showSuchprofilFlexTable.setText(8, 1, result.getReligion());
								
							}
					
				});
				
				loeschenButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						ClientsideSettings.getPartnerboerseAdministration()
						.deleteSuchprofil(Benutzer.getProfilId(), auswahlListBox.getSelectedItemText(),
								new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										infoLabel.setText("Es trat ein Fehler auf");
									}

									@Override
									public void onSuccess(Void result) {
										infoLabel.setText("Das Suchprofil wurde erfolgreich gelöscht");
									}

								});
						
					}
					
				}); 
				
				bearbeitenButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						EditSuchprofil editSuchprofil = new EditSuchprofil(auswahlListBox.getSelectedItemText());
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(editSuchprofil);
						
					}
					
				}); 
				
				verPanel.add(showSuchprofilFlexTable);
				buttonPanel.add(bearbeitenButton);
				buttonPanel.add(loeschenButton);
				verPanel.add(buttonPanel);
				verPanel.add(infoLabel); 
				
			}
			
		}); 
		
		verPanel.add(createSuchprofilButton); 
		verPanel.add(auswahlLabel);
		auswahlPanel.add(auswahlListBox);
		auswahlPanel.add(anzeigenButton);
		verPanel.add(auswahlPanel); 
		
	}

}
