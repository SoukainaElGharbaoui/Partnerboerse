package de.hdm.gruppe7.partnerboerse.shared;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe7.partnerboerse.client.LoginInfo;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public interface LoginServiceAsync {
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */
	

	
public void login(String requestUri, AsyncCallback<LoginInfo> callback);


	
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Login
	 * *************************************************************************
	 * **
	 */
}
