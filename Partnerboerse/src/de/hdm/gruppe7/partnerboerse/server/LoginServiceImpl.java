package de.hdm.gruppe7.partnerboerse.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.client.LoginInfo;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.LoginService;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */

	/**
	 * URL zum Einloggen anfordern.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#login(String)
	 */

	/**
	 * Diese Methode führt den Login aus und ruft die Daten von der Google
	 * Accounts API ab.
	 */
	@Override
	public LoginInfo login(String requestUri) {
		System.out.print(requestUri);
		System.out.print("1");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		System.out.print("2");
		LoginInfo loginInfo = new LoginInfo();
		System.out.print("3");

		
		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Login
	 * *************************************************************************
	 * **
	 */

}
