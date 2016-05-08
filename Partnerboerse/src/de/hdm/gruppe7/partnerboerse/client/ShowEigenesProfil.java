package de.hdm.gruppe7.partnerboerse.client;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;



public class ShowEigenesProfil extends VerticalPanel {
	
	private VerticalPanel verPanel = new VerticalPanel();
	
	private HorizontalPanel horPanelVorname = new HorizontalPanel();
	
	public ShowEigenesProfil () {
		this.add(verPanel);
		
		final Label eigenesProfilLabel = new Label ();
		final Label infoLabel = new Label();
		final Label versuch= new Label();
		Nutzerprofil nutzerprofil= new Nutzerprofil();
		
		
		
		versuch.setText("Vorname"  );
		verPanel.add(versuch);
		
	
		ClientsideSettings.getPartnerboerseAdministration().
		getNutzerprofilById(nutzerprofil.getProfilId(), new AsyncCallback<Nutzerprofil>() {

			@Override
			public void onFailure(Throwable caught) {
				eigenesProfilLabel.setText("Fehler" + caught.toString());

				
				
			}

			
			public void onSuccess(Nutzerprofil nutzerprofil) {
				eigenesProfilLabel.setText("Ihr persönliches Nutzerprofil" + nutzerprofil.toString());
			
//				
//				Nutzerprofil profilM = NutzerprofilMapper.nutzerprofilMapper().findByNutzerprofilId(nutzerprofil.getProfilId());
//				
//				infoLabel.setText("Vorname" +profilM.getVorname());
//				
//		verPanel.add(horPanelVorname);
//			horPanelVorname.add(eigenesProfilLabel);
//			horPanelVorname.add(infoLabel);
				
			
//	
		
	//	infoLabel.setText("Vorname:")	;			
						
						
					
				
		
				}
			
		



			
			
			
		});
		
		/**
		 * Button "Profil loeschen" hinzufuegen
		 */
//			
//			final Button eigenesProfilLoeschenButton = new Button("Profil loeschen");
//			verPanel.add(eigenesProfilLoeschenButton);
//			
//			
//			eigenesProfilLoeschenButton.addClickHandler(new ClickHandler() {
//
//				public void onClick(ClickEvent event) {
//					// hier muss ein popUp Fenster hin das von gwt unterstuetzt wird
		
	//			Nutzerprofil nutzerprofil = new Nutzerprofil();		
	//			Nutzerprofil deleteProfil = NutzerprofilMapper.nutzerprofilMapper().deleteNutzerprofil(nutzerprofil);
		
		
//				}		
//			});	
//			
//			/**
//			 * Button "Profil aendern" hinzufuegen
//			 */
//			
//			final Button eigenesProfilÄndernButton = new Button("Profil eandern");
//			verPanel.add(eigenesProfilÄndernButton);
//			
//			
//			eigenesProfilÄndernButton.addClickHandler(new ClickHandler() {			
//					
//					public void onClick(ClickEvent event) {
//						EditNutzerprofil editNutzerprofil = new EditNutzerprofil(); 
//						RootPanel.get("Details").clear(); 
//						RootPanel.get("Details").add(editNutzerprofil); 				
//					}
//									
//					
//			});
//		
			
			
		
		
	}
	
	

}
