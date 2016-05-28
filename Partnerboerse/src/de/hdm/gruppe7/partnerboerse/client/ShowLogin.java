package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.client.LoginInfo;

public class ShowLogin extends VerticalPanel {

	private VerticalPanel verPanel = new VerticalPanel();

	public ShowLogin() {
		this.add(verPanel);

		final TextBox loginField = new TextBox();

		final Label ueberschriftLabel = new Label("Login");

		final Button loginButton = new Button("Anmelden");
		final Button logoutButton = new Button("Abmelden");

		verPanel.add(loginField);
		verPanel.add(loginButton);
		verPanel.add(logoutButton);
		verPanel.add(ueberschriftLabel);

	}
}
