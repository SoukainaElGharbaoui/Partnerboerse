package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Date;
import java.util.List;

/**
 * Realisierung eines Nutzerprofils.
 */
public class Nutzerprofil extends Profil {

	private static final long serialVersionUID = 1L;

	/**
	 * Vorname des Nutzers. 
	 */
	private String vorname;
	
	/**
	 * Nachname des Nutzers. 
	 */
	private String nachname; 
	
	/**
	 * Geburtsdatum des Nutzers. 
	 */
	private Date geburtsdatumDate;

	/**
	 * Aehnlichkeit zwischen einem Nutzerprofil und anderen Nutzerprofilen 
	 * oder zwischen einem Suchprofil und anderen Nutzerprofilen.
	 */
	private int aehnlichkeit;
	
	/**
	 * E-Mail-Adresse des Nutzers. 
	 */
	private String emailAddress;

	/*
	 * ************************************
	 * Beginn: Eventuell hier falsch platziert 
	 * ************************************
	 */
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private boolean status = false;
	
	/*
	 * ************************************
	 * Ende: Eventuell hier falsch platziert 
	 * ************************************
	 */

	/**
	 * Vorname des Nutzers auslesen.
	 * @return vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Vorname des Nutzers setzen.
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	/**
	 * Nachname des Nutzers auslesen.
	 * @return nachname
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Nachname des Nutzers setzen.
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Geburtsdatum des Nutzers auslesen.
	 * @return geburtsdatumDate
	 */
	public Date getGeburtsdatumDate() {
		return geburtsdatumDate;
	}

	/**
	 * Geburtsdatum des Nutzers setzen. 
	 * @param geburtsdatumDate
	 */
	public void setGeburtsdatumDate(Date geburtsdatumDate) {
		this.geburtsdatumDate = geburtsdatumDate;
	}

	/**
	 * Aehnlichkeit auslesen.
	 * @return aehnlichkeit
	 */
	public int getAehnlichkeit() {
		return aehnlichkeit;
	}

	/**
	 * Aehnlichkeit setzen. 
	 * @param aehnlichkeit
	 */
	public void setAehnlichkeit(int aehnlichkeit) {
		this.aehnlichkeit = aehnlichkeit;
	}
	
	/**
	 * E-Mail-Adresse des Nutzers auslesen.
	 * @return emailAddress
	 */
	public String getEmailAddress(){
		return emailAddress;
	}
	
	/**
	 * E-Mail-Adresse des Nutzes setzen. 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/*
	 * ************************************
	 * Beginn: Eventuell hier falsch platziert 
	 * ************************************
	 */
	
	public boolean isLoggedIn() {
		return loggedIn;

	}

	public void setLoggedIn(boolean b) {
		loggedIn = b;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLogoutUrl(String createLogoutURL) {
		this.logoutUrl = createLogoutURL;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}
	
	/*
	 * ************************************
	 * Beginn: Eventuell hier falsch platziert 
	 * ************************************
	 */

}