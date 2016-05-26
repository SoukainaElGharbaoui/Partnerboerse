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

	private int aehnlichkeit;
	private int aehnlichkeitSp;


    private String emailAddress;


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
	
	public int getAehnlichkeit(){
		return aehnlichkeit;
	}
	
	public void setAehnlichkeit (int aehnlichkeit){
		
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
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		
	}



	//Konstruktor
	public Nutzerprofil() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
