package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class PartnerboerseReport extends VerticalPanel implements EntryPoint {
	
	ReportGeneratorAsync reportGenerator = null; 
	
	Button unangesehenePartnervorschlaegeButton = new Button("Unangesehene Partnervorschläge");
	
	Button partnervorschlaegeSuchprofilButton = new Button("Partnervorschläge anhand von Suchprofilen");
	
	Nutzerprofil nutzerprofil = new Nutzerprofil();

	private VerticalPanel loginPanel = new VerticalPanel();
	private Anchor signInLink = new Anchor("Jetzt einloggen");
	private Anchor signOutLink = new Anchor();
	private PartnerboerseAdministrationAsync partnerboerseAdministration;

	private ReportGeneratorAsync reportGeneratorAsync;


	@Override
	public void onModuleLoad() {
		
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}
		

		reportGenerator = GWT.create(ReportGenerator.class);

		try {
			ClientsideSettings.getReportGenerator().login(GWT.getHostPageBaseURL() + "PartnerboerseReports.html",
				new AsyncCallback<Nutzerprofil>() {

						public void onFailure(Throwable caught) {
							RootPanel.get().add(new Label(caught.toString()));
						}

						public void onSuccess(Nutzerprofil result) {
							// wenn der user eingeloggt ist
							if (result.isLoggedIn()) {

								if (result.getEmailAddress() != null) {
									reportGenerator.setUser(result, new AsyncCallback<Void>() {
										public void onFailure(Throwable caught) {
										}

										public void onSuccess(Void result) {
										}
									});

									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText(
											"Als " + result.getVorname() + result.getProfilId() + " ausloggen");
								    loginPanel.add(signOutLink);
									RootPanel.get("Details").add(new PartnerboerseReport());
									RootPanel.get("Header").add(loginPanel);
								}

								if (result.getEmailAddress() == null) {
									signOutLink.setHref(result.getLogoutUrl());
									signOutLink.setText("Als " + result.getVorname() + " ausloggen");
									loginPanel.add(signOutLink);
									RootPanel.get("Details").add(new PartnerboerseReport());
									RootPanel.get("Header").add(loginPanel);
									RootPanel.get("Details").add(new CreateNutzerprofil());
								}


							}

							// wenn der user nicht eingeloggt ist
     						if (!result.isLoggedIn()) {
								signInLink.setHref(result.getLoginUrl());
								loginPanel.add(signInLink);
								RootPanel.get("Header").add(loginPanel);
							}
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("900px");
		menu.setAnimationEnabled(true);

		   // Create the file menu
		   MenuBar partnervorschlaegeMenu = new MenuBar(true);
		   partnervorschlaegeMenu.setAnimationEnabled(true);

		   partnervorschlaegeMenu.addItem("Unangesehene Partnervorschlaege", new Command() {
		      @Override
		      public void execute() {
		    	  ShowAllPartnervorschlaegeNpReport showAllPartnervorschlaegeNpReport = new ShowAllPartnervorschlaegeNpReport();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(showAllPartnervorschlaegeNpReport);
		      }
		   });
		   
		   partnervorschlaegeMenu.addItem("Partnervorschlaege anhand von Suchprofilen", new Command(){

			@Override
			public void execute() {
				ShowAllPartnervorschlaegeSpReport showAllPartnervorschlaegeSpReport = new ShowAllPartnervorschlaegeSpReport();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showAllPartnervorschlaegeSpReport);
				
			}
			   
		   });
		   
		  
		   
		   partnervorschlaegeMenu.addSeparator();

		   menu.addItem(new MenuItem("Meine Partnervorschlaege", partnervorschlaegeMenu));

		   //add the menu to the root panel
		   RootPanel.get("Details").add(menu);
		
		
	}



	
	
	

}
