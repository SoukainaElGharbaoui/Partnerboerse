package de.hdm.gruppe7.partnerboerse.server;

import java.sql.SQLException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.client.LoginInfo;
import de.hdm.gruppe7.partnerboerse.client.LoginService;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public LoginInfo login(String requestUri) {
	//
	// UserService userService = UserServiceFactory.getUserService();
	// User user = userService.getCurrentUser();
	// LoginInfo loginInfo = new LoginInfo();
	//
	// if (user != null){
	// loginInfo.setLoggedIn(true);
	// loginInfo.setEmailAddress(user.getEmail());
	// loginInfo.setNickname(user.getNickname());
	// loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
	// } else {
	// loginInfo.setLoggedIn(false);
	// loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
	// }
	// return loginInfo;
	// }

	public Nutzerprofil login(String requestUri) throws Exception {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Nutzerprofil n = new Nutzerprofil();
		// NutzerprofilMapper.nutzerprofilMapper().findByNutzerprofilMitEmail(user.getEmail());
		if (user != null) {

			// EXISTING PROFILE
			Nutzerprofil bestehendesProfil = NutzerprofilMapper.nutzerprofilMapper()
					.findByNutzerprofilMitEmail(user.getEmail());
			if (bestehendesProfil != null) {
				n.setLoggedIn(true);
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService.createLogoutURL(requestUri));
				bestehendesProfil.setEmailAddress(user.getEmail());

				ClientsideSettings.setAktuellerUser(bestehendesProfil);
				return bestehendesProfil;
			} // NO PROFILE

			n.setLoggedIn(true);
			n.setEmailAddress(user.getEmail());

		} else { // USER = NULL
			n.setLoggedIn(false);

		}
		n.setLoginUrl(userService.createLoginURL(requestUri));
		return n;
	}
}
