package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Suchprofil extends Profil {

	private static final long serialVersionUID = 1L;
	
	private int alterMinInt; 
	private int alterMaxInt; 
	
	public int getAlterMinInt() {
		return alterMinInt;
	}

	public void setAlterMinInt(int alterMinInt) {
		this.alterMinInt = alterMinInt;
	}

	public int getAlterMaxInt() {
		return alterMaxInt;
	}

	public void setAlterMaxInt(int alterMaxInt) {
		this.alterMaxInt = alterMaxInt;
	}

	/**
	 * Nur noch vorrübergehend
	 */
	private String alterMin;
	private String alterMax;
	
	public String getAlterMin() {
		return alterMin;
	}
	
	public void setAlterMin(String alterMin) {
		this.alterMin = alterMin;
	}
	
	public String getAlterMax() {
		return alterMax;
	}
	
	public void setAlterMax(String alterMax) {
		this.alterMax = alterMax;
	}
	
	

}
