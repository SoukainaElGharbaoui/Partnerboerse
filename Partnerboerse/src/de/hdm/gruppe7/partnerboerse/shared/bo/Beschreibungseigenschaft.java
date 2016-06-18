package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung einer Beschreibungseigenschaft. 
 */
public class Beschreibungseigenschaft extends Eigenschaft {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Beschreibungstext. 
	 */
	private String beschreibungstext;
	
	/**
	 * Beschreibungstext auslesen. 
	 * @return beschreibungstext
	 */
	public String getBeschreibungstext() {
		return beschreibungstext;
	}
	
	/**
	 * Beschreibungstext setzen. 
	 * @param beschreibungstext
	 */
	public void setBeschreibungstext(String beschreibungstext) {
		this.beschreibungstext = beschreibungstext;
	}

}