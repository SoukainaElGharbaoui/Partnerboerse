package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Info extends BusinessObject {
	
	private static final long serialVersionUID = 1L;

	//Attribute
	private int infoId;
	private String infotext;
	
	//Konstruktor
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Getter- & Settermethoden
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public String getInfotext() {
		return infotext;
	}
	public void setInfotext(String infotext) {
		this.infotext = infotext;
	}
	
}
