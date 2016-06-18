package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.List;

/**
 * Realisierung einer Sperrliste.
 */
public class Sperrliste extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Liste von gesperrten Nutzerprofil-Objekten. 
	 */
	private List<Nutzerprofil> gesperrteNutzerprofile;

	/**
	 * Gesperrte Nutzerprofile eines Nutzers ausesen.
	 * @return Liste von Nutzerprofil-Objekten.
	 */
	public List<Nutzerprofil> getGesperrteNutzerprofile() {
		return gesperrteNutzerprofile;
	}

	/**
	 * Gesperrte Nutzerprofile eines Nutzers setzen.
	 * @param gesperrteNutzerprofile 
	 */
	public void setGesperrteNutzerprofile(List<Nutzerprofil> gesperrteNutzerprofile) {
		this.gesperrteNutzerprofile = gesperrteNutzerprofile;
	}

}
