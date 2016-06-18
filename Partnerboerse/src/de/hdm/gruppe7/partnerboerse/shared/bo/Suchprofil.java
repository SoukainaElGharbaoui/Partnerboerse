package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung eines Suchprofils. 
 */
public class Suchprofil extends Profil {

	private static final long serialVersionUID = 1L;

	/**
	 * Name des Suchprofils. 
	 */
	private String suchprofilName;
	
	/**
	 * Minimales Alter, das auf dem Suchprofil angegeben wird. 
	 */
	private int alterMinInt;
	
	/**
	 * Maximales Alter, das auf dem Suchprofil angegeben wird. 
	 */
	private int alterMaxInt;

	/**
	 * Suchprofilname auslesen. 
	 * @return suchprofilName
	 */
	public String getSuchprofilName() {
		return suchprofilName;
	}

	/**
	 * Suchprofilname setzen. 
	 * @param suchprofilName
	 */
	public void setSuchprofilName(String suchprofilName) {
		this.suchprofilName = suchprofilName;
	}

	/**
	 * Minimales Alter auslesen. 
	 * @return alterMinInt
	 */
	public int getAlterMinInt() {
		return alterMinInt;
	}

	/**
	 * Minimales Alter setzen. 
	 * @param alterMinInt
	 */
	public void setAlterMinInt(int alterMinInt) {
		this.alterMinInt = alterMinInt;
	}

	/**
	 * Maximales Alter auslesen. 
	 * @return alterMaxInt
	 */
	public int getAlterMaxInt() {
		return alterMaxInt;
	}

	/**
	 * Maximales Alter setzen. 
	 * @param alterMaxInt
	 */
	public void setAlterMaxInt(int alterMaxInt) {
		this.alterMaxInt = alterMaxInt;
	}

}
