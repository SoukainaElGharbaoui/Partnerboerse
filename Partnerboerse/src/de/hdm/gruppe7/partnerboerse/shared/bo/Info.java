package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Info extends BusinessObject {

	private static final long serialVersionUID = 1L;

//	// Attribute
//	private int infoId;
//	private String infotext;
//	private int eigenschaftId;
//	private String eigenschaftErlaeuterung;
	
	private int profilId;
	private int eigenschaftId;
	private String infotext;

	// Konstruktor
	public Info() {
	}
	
	// Getter- & Settermethoden

//	public int getAuswahloptionId() {
//		return auswahloptionId;
//	}
//
//	public void setAuswahloptionId(int auswahloptionId) {
//		this.auswahloptionId = auswahloptionId;
//	}

	public int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}


//	public int getInfoId() {
//		return infoId;
//	}
//
//	public void setInfoId(int infoId) {
//		this.infoId = infoId;
//	}


	public String getInfotext() {
		return infotext;
	}

	public void setInfotext(String infotext) {
		this.infotext = infotext;
	}

}
