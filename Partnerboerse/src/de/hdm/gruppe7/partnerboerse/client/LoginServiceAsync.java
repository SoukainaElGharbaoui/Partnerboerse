package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public interface LoginServiceAsync {

//	void login(String requestUri, AsyncCallback<LoginInfo> callback);
	
	void login(String requestUri, AsyncCallback<Nutzerprofil> callback) throws Exception;
	

}
