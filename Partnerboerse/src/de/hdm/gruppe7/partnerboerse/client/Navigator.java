package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.shared.GWT;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

import de.hdm.gruppe7.partnerboerse.shared.bo.Profil;

import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;


public class Navigator extends VerticalPanel {
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();

	int aehnlichkeit = 0;

	public Navigator() {
		VerticalPanel verPanel1 = new VerticalPanel();
		
//		Tree t = new Tree(); 
//		
//		TreeItem meinNutzerprofil = new TreeItem(); 
//		meinNutzerprofil.setText("Mein Nutzerprofil");
//		meinNutzerprofil.addTextItem("Meine Nutzerprofildaten"); 
//		meinNutzerprofil.addTextItem("Meine Infos"); 
//		
//		TreeItem meineSuchprofile = new TreeItem(); 
//		meineSuchprofile.setText("Meine Suchprofile");
//		
//		TreeItem andereNutzerprofile = new TreeItem(); 
//		andereNutzerprofile.setText("Andere Nutzerprofile");
//		andereNutzerprofile.addTextItem("Meine Merkliste"); 
//		andereNutzerprofile.addTextItem("Meine Sperrliste"); 
//		
//		TreeItem meinePartnervorschlaege = new TreeItem(); 
//		meinePartnervorschlaege.setText("Meine Partnervorschläge");
//		meinePartnervorschlaege.addTextItem("Meine unangesehenen Partnervorschläge"); 
//		meinePartnervorschlaege.addTextItem("Meine Partnervorschläge anhand von Suchprofilen"); 
//		
//		t.addItem(meinNutzerprofil); 
//		t.addItem(meineSuchprofile);
//		t.addItem(andereNutzerprofile);
//		t.addItem(meinePartnervorschlaege); 
//
//		this.add(t); 
		

		/**
		 * Button "Nutzerprofil anlegen" hinzufügen. !!! Gehört hier nicht hin,
		 * dient zurzeit jedoch als Beispiel !!!
		 * 
		 */

		// final Button loginButton = new Button("Anmelden");
		// loginButton.addClickHandler(new ClickHandler(){
		//
		// public void onClick(ClickEvent event) {
		//
		// ShowLogin showLogin = new ShowLogin();
		// RootPanel.get("Details").clear();
		// RootPanel.get("Details").add(showLogin);
		// loginButton.setVisible(false);
		//
		// }
		//
		// });
		//
		// loginButton.setStyleName("navigatorbutton");
		// this.add(loginButton);

		// final Button logoutButton = new Button("Abmelden");
		// loginButton.addClickHandler(new ClickHandler(){
		//
		// public void onClick(ClickEvent event) {
		//
		// ShowLogin showLogin = new ShowLogin();
		// RootPanel.get("Details").clear();
		// RootPanel.get("Details").add(showLogin);
		// logoutButton.setVisible(false);
		//
		// }
		//
		// });
		
		/**
		 * Information-Label hinzuf�gen.
		 */
		final Label infoLabel = new Label();


		// logoutButton.setStyleName("navigatorbutton");
		// this.add(logoutButton);
		
		final Button nutzerprofilAnlegenButton = new Button("Nutzerprofil anlegen");

		nutzerprofilAnlegenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				
				CreateNutzerprofil createNutzerprofil = new CreateNutzerprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createNutzerprofil);
				
				
				
			
				
			}
		});

		nutzerprofilAnlegenButton.setStyleName("navigatorbutton");
		this.add(nutzerprofilAnlegenButton);

		/**
		 * Button "Nutzerprofil anzeigen" hinzufügen
		 */
		final Button showEigenesNpButton = new Button("Mein Nutzerprofil");
		showEigenesNpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowEigenesNp showEigenesNp = new ShowEigenesNp(nutzerprofil);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showEigenesNp);
			}
		});

		showEigenesNpButton.setStyleName("navigatorbutton");
		this.add(showEigenesNpButton);

		


		
		/**
		 * Button "Suchprofile anzeigen" hinzufügen.
		 */
		final Button showSuchprofilButton = new Button("Meine Suchprofile");
		showSuchprofilButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowSuchprofil showSuchprofil = new ShowSuchprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSuchprofil);
			}

		});

		showSuchprofilButton.setStyleName("navigatorbutton");
		this.add(showSuchprofilButton);

		/**
		 * Button "Merkliste anzeigen" hinzufügen
		 */
		final Button merklisteAnzeigenButton = new Button("Meine Merkliste");
		merklisteAnzeigenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowMerkliste showMerkliste = new ShowMerkliste();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showMerkliste);
			}
		});

		merklisteAnzeigenButton.setStyleName("navigatorbutton");
		this.add(merklisteAnzeigenButton);

		/**
		 * Button "Sperrliste anzeigen" hinzufügen
		 */
		final Button sperrlisteAnzeigenButton = new Button("Meine Sperrliste");
		sperrlisteAnzeigenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowSperrliste showSperrliste = new ShowSperrliste();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showSperrliste);
			}
		});

		sperrlisteAnzeigenButton.setStyleName("navigatorbutton");
		this.add(sperrlisteAnzeigenButton);

		/**
		 * Button "Partnervorschlaege anzeigen" hinzufügen.
		 */
		final Button showPartnervorschlaegeButton = new Button("Meine Partnervorschläge");
		showPartnervorschlaegeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {


				ClientsideSettings.getPartnerboerseAdministration().getUnangeseheneNutzerprofile(

						new AsyncCallback<List<Nutzerprofil>>() {

							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(List<Nutzerprofil> result) {

								for (Nutzerprofil np : result) {

									final int fremdprofilId = np.getProfilId();

									ClientsideSettings.getPartnerboerseAdministration().berechneAehnlichkeitNpFor(
											fremdprofilId, new AsyncCallback<Integer>() {

												@Override
												public void onFailure(Throwable caught) {

												}

												@Override
												public void onSuccess(Integer result) {
													aehnlichkeit = result;
													ClientsideSettings.getPartnerboerseAdministration()
															.aehnlichkeitSetzen(fremdprofilId,

																	aehnlichkeit, new AsyncCallback<Void>() {

																		@Override
																		public void onFailure(Throwable caught) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub

																		}

																		@Override
																		public void onSuccess(Void result) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub

																		}

																	});
												}

											});

								}

							}

						});


				
				//Ab hier wird die Aehnlichkeit zwischen den Suchprofilen und den Nutzerprofilen errechnet
				
//				ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(Benutzer.getProfilId(), new AsyncCallback<List<Suchprofil>>() {
//					
//					
//					@Override
//					public void onFailure(Throwable caught) {
//						infoLabel.setText("Es trat ein Fehler auf.");
//					}
//					
//					@Override
//					public void onSuccess(List<Suchprofil> result1) {
//						
//						for (Suchprofil sp : result1){
//							
//							final int suchprofilId = sp.getProfilId();							
//				final String suchprofilName = sp.getSuchprofilName();
//				
//							ClientsideSettings.getPartnerboerseAdministration().getAllNutzerprofile(new AsyncCallback<List<Nutzerprofil>>(){
//					
//					@Override
//					public void onFailure(Throwable caught) {
//						infoLabel.setText("Es trat ein Fehler auf.");
//						
//					}
//					
//					@Override
//					public void onSuccess(List<Nutzerprofil> result) {
//						
//												
//				for (Nutzerprofil np : result){
//								
//								
//				final int fremdprofilId = np.getProfilId();
//				
//				
//				ClientsideSettings.getPartnerboerseAdministration().berechneAehnlichkeitSpFor(suchprofilId, fremdprofilId,  suchprofilName , new AsyncCallback<Integer>(){
//
//					@Override
//					public void onFailure(Throwable caught) {
//						infoLabel.setText("Es trat ein Fehler auf.");
//					}
//
//					@Override
//					public void onSuccess(Integer result3) {
//						aehnlichkeit = result3;
//						
//						
//						
//						
//						ClientsideSettings.getPartnerboerseAdministration().aehnlichkeitSetzenSp(Benutzer.getProfilId(), suchprofilName,  fremdprofilId, aehnlichkeit, new AsyncCallback<Void>(){
//
//							@Override
//							public void onFailure(Throwable caught) {
//								infoLabel.setText("Es trat ein Fehler auf.");
//							}
//
//							@Override
//							public void onSuccess(Void result4) {
//								// TODO Auto-generated method stub
//
//																													}
//
//																												});
//																							}
//
//																						});
//
//																	}
//
//																
//															
//
//														
//
//									}
//
//								});
//							
//							
//							
//							
//							
//						}
//						
//						
//						
//						
//					}
//				});
//				
//				
//				
		
				
				

				ShowPartnervorschlaege showPartnervorschlaege = new ShowPartnervorschlaege();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaege);

			}

		});

		verPanel1.add(infoLabel);
		
		showPartnervorschlaegeButton.setStyleName("navigatorbutton");
		this.add(showPartnervorschlaegeButton);

	}
}
