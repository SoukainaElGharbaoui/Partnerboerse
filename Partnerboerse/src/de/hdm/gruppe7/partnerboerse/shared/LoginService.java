package de.hdm.gruppe7.partnerboerse.shared;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.client.LoginInfo;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */

	
	/**
	 * URL zum Einloggen anfordern.
	 * 
	 * @param requestUri
	 * @return Nutzerprofil-Objekt, welches eingeloggt ist.
	 * @throws IllegalArgumentException 
	 */
	public LoginInfo login(String requestUri) throws IllegalArgumentException;
	
	


	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Login
	 * *************************************************************************
	 * **
	 */



}
