package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.List;

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
	 * Liste von Info-Objekten. 
	 */
	private List<Info> infos;
	
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

	/**
	 * Liste der Info-Objekte auslesen. 
	 * @return infos
	 */
	public List<Info> getInfos() {
		return infos;
	}

	/**
	 * Liste der Info-Objekte setzen. 
	 * @param infos
	 */
	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}

}
