package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Date;

/**
 * Realisierung eines Nutzerprofils.
 */
public class Nutzerprofil extends Profil {

	private static final long serialVersionUID = 1L;

	/**
	 * Vorname.
	 */
	private String vorname;
	/**
	 * Nachname.
	 */
	private String nachname;

	/**
	 * Geburtsdatum.
	 */
	private Date geburtsdatumDate;

	/**
	 * Aehnlichkeit zwischen einem Nutzerprofil und anderen Nutzerprofilen oder
	 * zwischen einem Suchprofil und anderen Nutzerprofilen.
	 */
	private int aehnlichkeit;

	/**
	 * E-Mail-Adresse.
	 */
	private String emailAddress;

	
	/**
	 * Login-Status, auf false gesetzt.
	 */
	private boolean loggedIn = false;

	/**
	 * Vorname auslesen.
	 * 
	 * @return vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Vorname setzen.
	 * 
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Nachname auslesen.
	 * 
	 * @return nachname
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Nachname setzen.
	 * 
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Geburtsdatum auslesen.
	 * 
	 * @return geburtsdatumDate
	 */
	public Date getGeburtsdatumDate() {
		return geburtsdatumDate;
	}

	/**
	 * Geburtsdatum setzen.
	 * 
	 * @param geburtsdatumDate
	 */
	public void setGeburtsdatumDate(Date geburtsdatumDate) {
		this.geburtsdatumDate = geburtsdatumDate;
	}

	/**
	 * Aehnlichkeit auslesen.
	 * 
	 * @return aehnlichkeit
	 */
	public int getAehnlichkeit() {
		return aehnlichkeit;
	}

	/**
	 * Aehnlichkeit setzen.
	 * 
	 * @param aehnlichkeit
	 */
	public void setAehnlichkeit(int aehnlichkeit) {
		this.aehnlichkeit = aehnlichkeit;
	}
	

	/**
	 * Email-Adresse setzen.
	 * 
	 * @param email
	 */
	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}
	
	
	/**
	 * E-Mail-Adresse auslesen.
	 * 
	 * @return emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Login-Status ermitteln.
	 * 
	 * @return loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;

	}

	/**
	 * Login-Status setzen.
	 * 
	 * @param b
	 */
	public void setLoggedIn(boolean b) {
		loggedIn = b;
	}

	/**
	 * Login-Status auslesen.
	 * 
	 * @return loggedIn
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}

}