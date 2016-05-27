package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;

/**
 * Realisierung eines exemplarischen Nutzerprofils. 
 * @author Nina BaumgÃ¤rtner
 */

public class Nutzerprofil extends Profil{

	private static final long serialVersionUID = 1L;
	
	private String vorname;
	private String nachname; 
	private String geburtsdatum; 
	private Date geburtsdatumDate;
	private List <Nutzerprofil> partnervorschlaegeNp;
	private List <Nutzerprofil> partnervorschlaegeSp;
	private List <Nutzerprofil> angeseheneNp;
	private int	aehnlichkeit;
    private String emailAddress;
    
    private boolean loggedIn;
	private String loginUrl;
	private String logoutUrl;
	
	public boolean isLoggedIn(){
		return loggedIn;
	}

	public void setLoggedIn(boolean b) {
		loggedIn = b;
	}
	
	public boolean getLoggedIn(){
		return loggedIn;
	}
	
	public void setLogoutUrl(String createLogoutURL) {
		this.logoutUrl = createLogoutURL;
	}
	public String getLogoutUrl(){
		return logoutUrl;
	}
	
	

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
	
	public String getEmailAddress(){
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		
	}


	//Konstruktor
	public Nutzerprofil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	

}
