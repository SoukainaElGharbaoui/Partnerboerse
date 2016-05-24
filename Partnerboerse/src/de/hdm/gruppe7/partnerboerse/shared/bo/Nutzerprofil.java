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
	private List <Nutzerprofil> partnervorschlaegeNp;
	private List <Nutzerprofil> partnervorschlaegeSp;
	private List <Nutzerprofil> angeseheneNp;
	private int aehnlichkeit;

	
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public Integer getAehnlichkeit() {
		return aehnlichkeit;
	}
	
	public void setAehnlichkeit(int aehnlichkeit) {
		
		this.aehnlichkeit = aehnlichkeit;
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
	public List<Nutzerprofil> getPartnervorschlaegeNp() {
		return partnervorschlaegeNp;
	}
	public void setPartnervorschlaegeNp(List<Nutzerprofil> partnervorschlaegeNp) {
		this.partnervorschlaegeNp = partnervorschlaegeNp;
	}
	public List<Nutzerprofil> getPartnervorschlaegeSp() {
		return partnervorschlaegeSp;
	}
	public void setPartnervorschlaegeSp(List<Nutzerprofil> partnervorschlaegeSp) {
		this.partnervorschlaegeSp = partnervorschlaegeSp;
	}
	public List<Nutzerprofil> getAngeseheneNp() {
		return angeseheneNp;
	}
	public void setAngeseheneNp(List<Nutzerprofil> angeseheneNp) {
		this.angeseheneNp = angeseheneNp;
	}
	
	//Konstruktor
	public Nutzerprofil() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	private void merkeProfil (Nutzerprofil np) {
		
	}
	
	private void entferneGemerktesProfil (Nutzerprofil np) {
		
	}
	
	private void sperreProfil (Nutzerprofil np) {
		
	}
	
	private void entsperreProfil (Nutzerprofil np) {
		
	}
	

	
}
