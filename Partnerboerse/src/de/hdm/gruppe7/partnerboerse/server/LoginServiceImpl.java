package de.hdm.gruppe7.partnerboerse.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.LoginInfo;
import de.hdm.gruppe7.partnerboerse.shared.LoginService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Implementierungsklasse des Interfaces LoginService
 * @see LoginService
 * @see LoginServiceAsync
 * @see RemoteServiceServlet
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	/**
	 * Diese Methode fuert den Login aus und ruft die Daten von der Google
	 * Accounts API ab.
	 * @param requestUri 
	 * @return loginInfos
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

}
