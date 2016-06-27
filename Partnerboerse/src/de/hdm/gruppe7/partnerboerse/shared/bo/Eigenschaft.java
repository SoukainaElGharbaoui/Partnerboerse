package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung einer Eigenschaft.
 */
public class Eigenschaft extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Eigenschaft-ID.
	 */
	private int eigenschaftId;
	
	/**
	 * Erlaeuterung. 
	 */
	private String erlaeuterung;
	
	/**
	 * Typ. 
	 */
	private String typ;

	/**
	 * Eigenschaft-ID setzen. 
	 * @param eigenschaftId 
	 */
	public void setEigenschaftId(int eigenschaftId){
		this.eigenschaftId = eigenschaftId;
	}
	
	/**
	 * Eigenschaft-ID auslesen.
	 * @return eigenschaftId
	 */
	public int getEigenschaftId(){
		return eigenschaftId;
	}
	
	/**
	 * Erlaeuterung setzen. 
	 * @param erlaeuterung
	 */
	public void setErlaeuterung(String erlaeuterung){
		this.erlaeuterung = erlaeuterung;
	}
	
	/**
	 * Erlaeuterung auslesen. 
	 * @return erlaeuterung
	 */
	public String getErlaeuterung(){
		return erlaeuterung;
	}
	
	/**
	 * Typ auslesen. 
	 * @return typ 
	 */
	public String getTyp() {
		return typ;
	}

	/**
	 * Typ setzen. 
	 * @param typ
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}
	
}
