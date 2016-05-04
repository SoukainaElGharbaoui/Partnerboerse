package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Navigator {
	
	public void onModuleLoad() {
		
	VerticalPanel navPanel = new VerticalPanel();
	RootPanel.get("Navigator").add(navPanel);
	
	final MenuBar nutzerProfilMenu = new MenuBar();
	
	nutzerProfilMenu.addItem("Nutzerprofil anzeigen", nutzerProfilMenu);
	nutzerProfilMenu.addItem("Nutzerprofil bearbeiten", nutzerProfilMenu);
	nutzerProfilMenu.addItem("Nutzerprofil l&ouml;schen", nutzerProfilMenu);
	navPanel.add(nutzerProfilMenu);
	
	final MenuBar menu = new MenuBar();
	
	menu.addItem("Sperrliste anzeigen", menu);
	menu.addItem("Merkliste anzeigen", menu);
	menu.addItem("Unangesehene Partnervorschl&auml;ge anzeigen", menu);
	menu.addItem("Partnervorschl&auml;ge mit Suchprofil anzeigen", menu);
	navPanel.add(menu);
	
	
}
}