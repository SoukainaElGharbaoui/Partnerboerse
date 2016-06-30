package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.client.LoginInfo;

/**
 * Synchrone Schnittstelle fuer den Login.
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.LoginServiceImpl#login(String)
	 */
	public LoginInfo login(String requestUri) throws IllegalArgumentException;
	
}
