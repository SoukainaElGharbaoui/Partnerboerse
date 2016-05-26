package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Info extends BusinessObject {
	
	private static final long serialVersionUID = 1L;

	private int nutzerprofilId;
	private int eigenschaftId;
	private String infotext;

	
	//Konstruktor
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Getter- & Settermethoden

	public int getNutzerprofilId() {
		return nutzerprofilId;
	}

	public void setNutzerprofilId(int profilId) {
		this.nutzerprofilId = profilId;
	}

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

	public String getInfotext() {
		return infotext;
	}

	public void setInfotext(String infotext) {
		this.infotext = infotext;
	}
	
}
