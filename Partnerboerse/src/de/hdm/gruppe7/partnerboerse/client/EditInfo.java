package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditInfo extends VerticalPanel {
	
	int infoId;
	
	private VerticalPanel verPanel = new VerticalPanel();
	
	public EditInfo(){
		this.add(verPanel);
	}
	
	final Label ueberschriftLabel = new Label ("Info bearbeiten");
	
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	final FlexTable editInfoFlexTable = new FlexTable();
	
	editInfoFlexTable.setCellPadding(6);
	editInfoFlexTable.getColumnFormatter().addStyleName(0,
			"TableHeader");
	editInfoFlexTable.addStyleName("FlexTable");
	
	editInfoFlexTable.setText(0, 0, "");
	editInfoFlexTable.setText(0, 0, "");
	editInfoFlexTable.setText(1, 0, "");
	editInfoFlexTable.setText(2, 0, "");
	editInfoFlexTable.setText(3, 0, "");
	editInfoFlexTable.setText(4, 0, "");
	editInfoFlexTable.setText(5, 0, "");
	editInfoFlexTable.setText(6, 0, "");
	editInfoFlexTable.setText(7, 0, "");
	
	final Label editLabel = new Label();

	final ListBox ernaehrungListBox = new ListBox();
	ernaehrungListBox.addItem("vegetarisch");
	ernaehrungListBox.addItem("vegan");
	ernaehrungListBox.addItem("keine Angabe");
	editInfoFlexTable.setWidget(1, 2, ernaehrungListBox);
	
	final ListBox musikListBox = new ListBox();
	musikListBox.addItem("Pop");
	musikListBox.assItem("RnB");
	editInfoFlexTable.setWidget(2, 2, musikListBox);
	
}	
