package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;

public class PartnerboerseReport implements EntryPoint {
	
	ReportGeneratorAsync reportGenerator = null; 
	
	Button unangesehenePartnervorschlaegeButton = new Button("Unangesehene Partnervorschläge");
	
	Button partnervorschlaegeSuchprofilButton = new Button("Partnervorschläge anhand von Suchprofilen");

	@Override
	public void onModuleLoad() {
		
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}
		
		// ClickHandler etc. einbauen
		RootPanel.get("Navigator").add(unangesehenePartnervorschlaegeButton);
		
		// ClickHandler etc. einbauen
		RootPanel.get("Navigator").add(partnervorschlaegeSuchprofilButton);
	
	}
	
	

}
