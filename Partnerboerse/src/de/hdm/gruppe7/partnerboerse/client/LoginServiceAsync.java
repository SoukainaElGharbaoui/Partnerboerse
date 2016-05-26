package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

//	void login(String requestUri, AsyncCallback<LoginInfo> callback);
	
	void login(String requestUri, AsyncCallback<String> callback);
	
	void hallo(AsyncCallback<String> callback);

}
