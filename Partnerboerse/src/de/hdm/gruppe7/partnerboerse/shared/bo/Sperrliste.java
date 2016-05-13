package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Sperrliste extends BusinessObject {
	
private static final long serialVersionUID = 1L;
	
	private int sFremdprofilId; 
	private String sVorname; 
	private String sNachname; 
	private String sGeburtsdatum;
	private String sGeschlecht;
	
	public int getsFremdprofilId() {
		return sFremdprofilId;
	}
	public void setsFremdprofilId(int sFremdprofilId) {
		this.sFremdprofilId = sFremdprofilId;
	}
	public String getsVorname() {
		return sVorname;
	}
	public void setsVorname(String sVorname) {
		this.sVorname = sVorname;
	}
	public String getsNachname() {
		return sNachname;
	}
	public void setsNachname(String sNachname) {
		this.sNachname = sNachname;
	}
	public String getsGeburtsdatum() {
		return sGeburtsdatum;
	}
	public void setsGeburtsdatum(String sGeburtsdatum) {
		this.sGeburtsdatum = sGeburtsdatum;
	}
	public String getsGeschlecht() {
		return sGeschlecht;
	}
	public void setsGeschlecht(String sGeschlecht) {
		this.sGeschlecht = sGeschlecht;
	}

}
