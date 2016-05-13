package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditInfo extends VerticalPanel {
	
	int infoId;
	
	private VerticalPanel verPanel = new VerticalPanel();
	
	public EditInfo(int infoId){
		this.infoId = infoId;
		this.add(verPanel);
	}
	
	final Label ueberschriftLabel = new Label ("Info bearbeiten");
	
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	
	

}
