package de.hdm.gruppe7.partnerboerse.client;

import java.io.Serializable;

public class LoginInfo implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;
	private boolean status = false;
	
	public boolean isLoggedIn(){
		return loggedIn;
	}

	public void setLoggedIn(boolean b) {
		
	}
	
	public boolean getLoggedIn(){
		return loggedIn;
	}
	
	public void setLogoutUrl(String createLogoutURL) {
		
	}
	public String getLogoutUrl(){
		return logoutUrl;
	}

	public void setNickname(String nickname2) {
		
	}
	public String getNickname(){
		return nickname;
	}

	public void setEmailAddress(String email) {
		
	}
	
	public String getEmailAddress(){
		return emailAddress;
	}

	public void setLoginUrl(String createLoginURL) {
		
	}
	public String getLoginUrl(){
		return loginUrl;
	}
	
	public void setStatus(boolean status) {
		  this.status = status;
	  }
	  
	  public boolean getStatus() {
		  return status;
	  }

}
