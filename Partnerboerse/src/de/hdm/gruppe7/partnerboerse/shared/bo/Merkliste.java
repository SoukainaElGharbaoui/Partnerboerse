package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Merkliste extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private int mFremdprofilId; 
	private String mVorname; 
	private String mNachname; 
	private String mGeburtsdatum;
	private String mGeschlecht;
	

	public int getmFremdprofilId() {
		return mFremdprofilId;
	}
	public void setmFremdprofilId(int mFremdprofilId) {
		this.mFremdprofilId = mFremdprofilId;
	}
	public String getmVorname() {
		return mVorname;
	}
	public void setmVorname(String mVorname) {
		this.mVorname = mVorname;
	}
	public String getmNachname() {
		return mNachname;
	}
	public void setmNachname(String mNachname) {
		this.mNachname = mNachname;
	}
	public String getmGeburtsdatum() {
		return mGeburtsdatum;
	}
	public void setmGeburtsdatum(String mGeburtsdatum) {
		this.mGeburtsdatum = mGeburtsdatum;
	}
	public String getmGeschlecht() {
		return mGeschlecht;
	}
	public void setmGeschlecht(String mGeschlecht) {
		this.mGeschlecht = mGeschlecht;
	} 

	
}
