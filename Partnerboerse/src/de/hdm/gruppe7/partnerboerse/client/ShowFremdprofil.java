package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowFremdprofil extends VerticalPanel {
	
	/**
	 * VerticalPanel hinzufügen.  
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	
	/** 
	 * Konstruktor hinzufügen. 
	 */
	public ShowFremdprofil(int fremdprofilId){
		this.add(verPanel); 
		
		final Label ueberschriftLabel = new Label("Fremdprofil"); 
		
		/**
		 * Merken/Nicht-Mehr-Merken-Button hinzufügen. 
		 */
		String buttonText = "";
		final Button mButton = new Button(buttonText); 
		
		
		/**
		 * Widgets zum VerticalPanel hinzufügen.
		 */
		verPanel.add(ueberschriftLabel); 
		verPanel.add(mButton);

	}
}
