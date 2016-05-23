package de.hdm.gruppe7.partnerboerse.client;

import java.io.Serializable;

public class LoginInfo implements Serializable {
	
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;
	
	public boolean isLoggedIn(){
		return loggedIn;
	}

}
