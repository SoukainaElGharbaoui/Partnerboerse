package de.hdm.gruppe7.partnerboerse.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
 

public class Navigator extends VerticalPanel {
	
	private LoginInfo loginInfo;

	public Navigator(LoginInfo loginInfo){
		this.loginInfo = loginInfo;
	}

	int aehnlichkeit = 0;
	int aehnlichkeitSp= 0;
	public Navigator() {

		/*
		 * Nachdem der Benutzer sich an der Partnerbörse angemeldet hat wird
		 * seine eigene Profil-ID gesetzt (hier noch temporär - normalerweisen
		 * im Login nach Ermittlung über Mailadresse
		 */
		Benutzer b = new Benutzer();
		b.setProfilId(1);
		/**
		 * Button "Nutzerprofil anlegen" hinzufügen. !!! Gehört hier nicht
		 * hin, dient zurzeit jedoch als Beispiel !!!
		 * 
		 */
		
//		final Button loginButton = new Button("Anmelden");
//		loginButton.addClickHandler(new ClickHandler(){
//			
//			public void onClick(ClickEvent event) {
//				
//				ShowLogin showLogin = new ShowLogin();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(showLogin);
//				loginButton.setVisible(false);	
//				
//			}
//
//		});
//		
//		loginButton.setStyleName("navigatorbutton");
//		this.add(loginButton);
		
//		final Button logoutButton = new Button("Abmelden");
//		loginButton.addClickHandler(new ClickHandler(){
//			
//			public void onClick(ClickEvent event) {
//				
//				ShowLogin showLogin = new ShowLogin();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(showLogin);
//				logoutButton.setVisible(false);	
//				
//			}
//
//		});
		
//		logoutButton.setStyleName("navigatorbutton");
//		this.add(logoutButton);
		final Button nutzerprofilAnlegenButton = new Button(
				"Nutzerprofil anlegen");

		nutzerprofilAnlegenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				CreateNutzerprofil createNutzerprofil = new CreateNutzerprofil();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createNutzerprofil);
				nutzerprofilAnlegenButton.setVisible(false);
			}
		});
		
		nutzerprofilAnlegenButton.setStyleName("navigatorbutton"); 
		this.add(nutzerprofilAnlegenButton);

		/**
		 * Button "Nutzerprofil anzeigen" hinzufügen
		 */
		final Button showEigenesNpButton = new Button(
				"Mein Nutzerprofil");
		
		showEigenesNpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ShowEigenesNp showEigenesNp = new ShowEigenesNp();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showEigenesNp);
			}
		});
		
		showEigenesNpButton.setStyleName("navigatorbutton");
		this.add(showEigenesNpButton);
		
		/**
		 * Button "Info anlegen" hinzufügen
		 */
		final Button infoAnlegenButton = new Button("Info anlegen");
		
		infoAnlegenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				CreateInfo createInfo = new CreateInfo();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(createInfo);
			}
		});
		
		infoAnlegenButton.setStyleName("navigatorbutton"); 
		this.add(infoAnlegenButton);
		
		/**
		 * Button "Info anzeigen" hinzufügen
		 */
//		final Button showInfoButton = new Button("Info anzeigen");
//		
//		showInfoButton.addClickHandler(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				ShowInfo showInfo = new ShowInfo();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(showInfo);
//			}
//		});
//
//		this.add(showInfoButton);

		/**
		 * Button "Suchprofil anlegen" hinzufügen
		 */
//		final Button suchprofilAnlegenButton = new Button("Suchprofil anlegen");
//		
//		suchprofilAnlegenButton.addClickHandler(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				CreateSuchprofil createSuchprofil = new CreateSuchprofil();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(createSuchprofil);
//			}
//		});
//
//		suchprofilAnlegenButton.setStyleName("navigatorbutton"); 
//		this.add(suchprofilAnlegenButton);

		/**
		 * Button "Suchprofile anzeigen" hinzufügen.
		 */
		final Button showSuchprofilButton = new Button(
				"Meine Suchprofile");
		
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
		final Button sperrlisteAnzeigenButton = new Button(
				"Meine Sperrliste");
		
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
		final Button showPartnervorschlaegeButton = new Button(
				"Meine Partnervorschläge");
		
		showPartnervorschlaegeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				ClientsideSettings.getPartnerboerseAdministration().getUnangeseheneNutzerprofile(Benutzer.getProfilId(), new AsyncCallback<List<Nutzerprofil>>(){

					@Override
					public void onFailure(Throwable caught) {
					
					}

					@Override
					public void onSuccess(List<Nutzerprofil> result) {
						
						for (Nutzerprofil np : result) {
							
							final int fremdprofilId = np.getProfilId();
							
							ClientsideSettings.getPartnerboerseAdministration().berechneAehnlichkeitNpFor(Benutzer.getProfilId(), fremdprofilId, new AsyncCallback<Integer>(){

								@Override
								public void onFailure(Throwable caught) {
								
								}

								@Override
								public void onSuccess(Integer result) {
									aehnlichkeit = result;
									ClientsideSettings.getPartnerboerseAdministration().aehnlichkeitSetzen(Benutzer.getProfilId(), fremdprofilId, aehnlichkeit, new AsyncCallback<Void>(){

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											
										}

										@Override
										public void onSuccess(Void result) {
											// TODO Auto-generated method stub
											
										}
										
									});
								}
								
							});
							
					
						
						}
						
					}
					
				});
				
				//Ab hier wird die Aehnlichkeit zwischen den Suchprofilen und den Nutzerprofilen errechnet
				
				ClientsideSettings.getPartnerboerseAdministration().getAllSuchprofileFor(Benutzer.getProfilId(), new AsyncCallback<List<Suchprofil>>() {
					
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
//						
					}
					
					@Override
					public void onSuccess(List<Suchprofil> result1) {
						
						for (Suchprofil sp : result1){
							
							final int suchprofilId = sp.getProfilId();							
				final String suchprofilName = sp.getSuchprofilName();
				
							ClientsideSettings.getPartnerboerseAdministration().getAllNutzerprofile(new AsyncCallback<List<Nutzerprofil>>(){
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(List<Nutzerprofil> result) {
						
												
				for (Nutzerprofil np : result){
								
								
				final int fremdprofilId = np.getProfilId();
				
				
				ClientsideSettings.getPartnerboerseAdministration().berechneAehnlichkeitSpFor(suchprofilId, fremdprofilId,  suchprofilName , new AsyncCallback<Integer>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Integer result3) {
						aehnlichkeit = result3;
						
						
						
						
						ClientsideSettings.getPartnerboerseAdministration().aehnlichkeitSetzenSp(Benutzer.getProfilId(), "Nina",  fremdprofilId, aehnlichkeit, new AsyncCallback<Void>(){

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(Void result4) {
								// TODO Auto-generated method stub

																													}

																												});
																							}

																						});

																	}

																
															

														

									}

								});
							
							
							
							
							
						}
						
						
						
						
					}
				});
				
				
				
		
				
				
				ShowPartnervorschlaege showPartnervorschlaege = new ShowPartnervorschlaege();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showPartnervorschlaege);
				
			}

		});

		showPartnervorschlaegeButton.setStyleName("navigatorbutton"); 
		this.add(showPartnervorschlaegeButton);
		
		
		

	}

	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button eigenesProfilButton = new Button("Eigenes Profil anzeigen");
	// navPanel2.add(eigenesProfilButton);
	//
	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button profilBearbeitenButton = new
	// Button("Eigenes Profil bearbeiten");
	// navPanel2.add(profilBearbeitenButton);
	//
	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button eigenesProfilLoeschen = new
	// Button("Eigenes Profil l&ouml;schen");
	// navPanel2.add(eigenesProfilLoeschen);
	//
	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button merklisteButton = new Button("Merkliste anzeigen");
	// navPanel2.add(merklisteButton);
	//
	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button sperrlisteButton = new Button("Sperrliste anzeigen");
	// navPanel2.add(sperrlisteButton);
	//
	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button partnervorschlaegeOhneSpButton = new
	// Button("Unangesehene Partnervorschlaege anzeigen");
	// navPanel2.add(partnervorschlaegeOhneSpButton);
	//
	// /**
	// * Erzeugen eines Navigation-Buttons.
	// */
	// final Button partnervorschlaegeMitSpButton = new
	// Button("Partnervorschlaege auf Basis Ihrer Suche anzeigen");
	// navPanel2.add(partnervorschlaegeMitSpButton);
	//
	//
	//

}
