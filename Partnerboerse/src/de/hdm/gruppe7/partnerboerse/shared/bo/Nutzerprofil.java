package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Date;
import java.util.List;


/**
 * Realisierung eines exemplarischen Nutzerprofils.
 * 
 * @author Nina BaumgÃ¤rtner
 */

public class Nutzerprofil extends Profil {

	private static final long serialVersionUID = 1L;

	
	private String vorname;
	private String nachname; 
	private String geburtsdatum; 
	private Date geburtsdatumDate;
	private List <Nutzerprofil> partnervorschlaegeNp;
	private List <Nutzerprofil> partnervorschlaegeSp;
	private List <Nutzerprofil> angeseheneNp;

	private int aehnlichkeit;
	private int aehnlichkeitSp;



	// WICHTIG FÜR LOGIN
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;
	private boolean status = false;

	

	// Konstruktor
	public Nutzerprofil() {
		super();
		// TODO Auto-generated constructor stub
	}

	// METHODEN NUTZERPROFIL
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	
	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Date getGeburtsdatumDate() {
		return geburtsdatumDate;
	}

	public void setGeburtsdatumDate(Date geburtsdatumDate) {
		this.geburtsdatumDate = geburtsdatumDate;
	}


	public int getAehnlichkeit() {

		return aehnlichkeit;
	}


	public void setAehnlichkeit(int aehnlichkeit) {

		this.aehnlichkeit = aehnlichkeit;
	}

	

	public int getAehnlichkeitSp (){
		return aehnlichkeitSp;
	}
	
	public void setAehnlichkeitSp (int aehnlichkeitSp){
		
		this.aehnlichkeitSp = aehnlichkeitSp;
	}


	public String getEmailAddress(){
		return emailAddress;
	}

	// METHODEN LOGIN
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

	public void setNickname(String nickname2) {

	}

	public String getNickname() {
		return nickname;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

}