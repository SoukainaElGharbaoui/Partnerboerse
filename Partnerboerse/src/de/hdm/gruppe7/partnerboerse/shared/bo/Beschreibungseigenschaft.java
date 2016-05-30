package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Beschreibungseigenschaft extends Eigenschaft {

	private static final long serialVersionUID = 1L;

	public String beschreibungstext;
	public int beschreibungsId;

	public void setBeschreibungstext(String beschreibungstext) {
		this.beschreibungstext = beschreibungstext;
	}

	public String getBeschreibungstext() {
		return beschreibungstext;
	}

	public void setBeschreibungsId(int beschreibungsId) {
		this.beschreibungsId = beschreibungsId;
	}

	public int getBeschreibungsId() {
		return beschreibungsId;
	}

}
