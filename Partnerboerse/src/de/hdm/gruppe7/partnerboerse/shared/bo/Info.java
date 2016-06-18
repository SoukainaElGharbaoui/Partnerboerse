package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung einer Info.
 */
public class Info extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Profil-ID.
	 * Fremdschluesselbeziehung zum Profil.
	 */
	private int profilId;
	
	/**
	 * Eigenschaft-ID.
	 * Fremdschluesselbeziehung zur Eigenschaft. 
	 */
	private int eigenschaftId;
	
	/**
	 * Infotext. 
	 */
	private String infotext;
	
	/**
	 * Profil-ID auslesen. 
	 * @return profilId
	 */
	public int getProfilId() {
		return profilId;
	}

	/**
	 * Profil-ID setzen. 
	 * @param profilId
	 */
	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

	/**
	 * Eigenschaft-ID auslesen.
	 * @return eigenschaftId
	 */
	public int getEigenschaftId() {
		return eigenschaftId;
	}

	/**
	 * EIgenschaft-ID setzen. 
	 * @param eigenschaftId
	 */
	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

	/**
	 * Infotext auslesen. 
	 * @return infotext
	 */
	public String getInfotext() {
		return infotext;
	}

	/**
	 * Infotext setzen.
	 * @param infotext
	 */
	public void setInfotext(String infotext) {
		this.infotext = infotext;
	}
}
