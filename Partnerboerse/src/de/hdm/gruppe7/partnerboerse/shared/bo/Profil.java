package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung eines Profils.
 */
public class Profil extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Profil-ID.
	 */
	private int profilId; 
	
	/**
	 * Geschlecht.
	 */
	private String geschlecht;
	
	/**
	 * Koerpergroesse.
	 */
	private int koerpergroesseInt;
	
	/**
	 * Haarfarbe.
	 */
	private String haarfarbe;
	
	/**
	 * Raucherstatus. 
	 */
	private String raucher;
	
	/**
	 * Religion.
	 */
	private String religion;
	
	/**
	 * Profil-ID auslesen.
	 * @return profilId
	 */
	public int getProfilId() {
		return this.profilId;
	}
	
	/**
	 * Profil-ID setzen.
	 * @param profilId
	 */
	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}
	
	/**
	 * Geschlecht auslesen.
	 * @return geschlecht
	 */
	public String getGeschlecht() {
		return geschlecht;
	}

	/**
	 * Geschlecht setzen.
	 * @param geschlecht
	 */
	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	
	/**
	 * Koerpergroesse auslesen. 
	 * @return koerpergroesseInt
	 */
	public int getKoerpergroesseInt() {
		return koerpergroesseInt;
	}

	/**
	 * Koerpergroesse setzen.
	 * @param koerpergroesseInt
	 */
	public void setKoerpergroesseInt(int koerpergroesseInt) {
		this.koerpergroesseInt = koerpergroesseInt;
	}
	
	/**
	 * Haarfarbe auslese.
	 * @return haarfarbe
	 */
	public String getHaarfarbe() {
		return haarfarbe;
	}

	/**
	 * Haarfarbe setzen. 
	 * @param haarfarbe
	 */
	public void setHaarfarbe(String haarfarbe) {
		this.haarfarbe = haarfarbe;
	}

	/**
	 * Raucherstatus auslesen.
	 * @return raucher
	 */
	public String getRaucher() {
		return raucher;
	}

	/**
	 * Raucherstatus setzen. 
	 * @param raucher
	 */
	public void setRaucher(String raucher) {
		this.raucher = raucher;
	}

	/**
	 * Religion auslesen. 
	 * @return religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * Religion setzen. 
	 * @param religion
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}

}