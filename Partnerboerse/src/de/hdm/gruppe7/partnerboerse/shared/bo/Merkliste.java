package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.List;

/**
 * Realisierung einer Merkliste.
 */
public class Merkliste extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Liste von gemerkten Nutzerprofil-Objekten.  
	 */
	private List<Nutzerprofil> gemerkteNutzerprofile;

	/**
	 * Gemerkte Nutzerprofile eines Nutzers ausesen.
	 * @return Liste von Nutzerprofil-Objekten.
	 */
	public List<Nutzerprofil> getGemerkteNutzerprofile() {
		return gemerkteNutzerprofile;
	}

	/**
	 * Gemerkte Nutzerprofile eines Nutzers setzen.
	 * @param gemerkteNutzerprofile 
	 */
	public void setGemerkteNutzerprofile(List<Nutzerprofil> gemerkteNutzerprofile) {
		this.gemerkteNutzerprofile = gemerkteNutzerprofile;
	}

}
