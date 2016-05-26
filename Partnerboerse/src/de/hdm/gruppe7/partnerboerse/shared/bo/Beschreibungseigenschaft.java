package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Beschreibungseigenschaft extends Eigenschaft {
	
	private static final long serialVersionUID = 1L;
	private String beschreibungstext;

	public String getInfotext() {
		return beschreibungstext;
	}

	public void setInfotext(String infotext) {
		this.beschreibungstext = infotext;
	}
}