package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Date;
import java.util.List;

/**
 * Realisierung eines exemplarischen Nutzerprofils. 
 * @author Nina Baumgärtner
 */

public class Nutzerprofil extends Profil{

	private static final long serialVersionUID = 1L;
	
	private String vorname;
	private String nachname; 
	private Date geburtsdatum;
	private List <Nutzerprofil> partnervorschlaegeNp;
	private List <Nutzerprofil> partnervorschlaegeSp;
	private List <Nutzerprofil> angeseheneNp;
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
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
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
	
	
	// MUSS NOCH DEFINIERT WERDEN !!
	
	private int berechneAehnlichkeitsmassNp (Nutzerprofil np) {
		return 0; 
	}
	
	private int berechneAehnlichkeitsmassSp (Suchprofil sp) {
		return 0; 
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
