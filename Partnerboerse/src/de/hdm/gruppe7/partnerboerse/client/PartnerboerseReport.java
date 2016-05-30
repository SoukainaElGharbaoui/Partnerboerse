package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.gruppe7.partnerboerse.shared.ReportGeneratorAsync;

public class PartnerboerseReport implements EntryPoint {
	
	ReportGeneratorAsync reportGenerator = null; 

	@Override
	public void onModuleLoad() {
		
		RootPanel.get("Navigator").add(new Navigator());
		
	}
	
	

}
