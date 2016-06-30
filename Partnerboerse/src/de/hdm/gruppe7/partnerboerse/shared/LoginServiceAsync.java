package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.client.LoginInfo;

/**
 * Asynchrone Schnittstelle fuer den Login.
 */
public interface LoginServiceAsync {
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.LoginServiceImpl#login(String)
	 */	
	public void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
