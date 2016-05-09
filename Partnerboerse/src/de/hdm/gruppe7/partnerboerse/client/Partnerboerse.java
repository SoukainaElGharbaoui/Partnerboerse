package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class Partnerboerse implements EntryPoint {

	/**
	 * Diese Klasse sichert die Implementierung des Interface <code>EntryPoint</code>.
	 * Daher benötigen wir die Methode <code>public void onModuleLoad()</code>. 
	 * Diese ist das GWT-Pendant der<code>main()</code>-Methode normaler Java-Applikationen.
	 */
	private final PartnerboerseAdministrationAsync partnerboerseAdministration = GWT
			.create(PartnerboerseAdministration.class);

	@Override
	public void onModuleLoad() {
	
		/**
		 * Das VerticalPanel wird einem DIV-Element names "Navigator" in der 
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen Darstellungsort.
		 */
		RootPanel.get("Navigator").add(new Navigator());

	}
	
}
