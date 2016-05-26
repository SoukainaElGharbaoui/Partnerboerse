
package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Eigenschaft extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	public int eigenschaftId;
	public String erlaeuterung;
	public String typ;

	
	public void setEigenschaftId(int eigenschaftId){
		this.eigenschaftId = eigenschaftId;
	}
	
	public int getEigenschaftId(){
		return eigenschaftId;
	}
	
	public void setErlaeuterung(String erlaeuterung){
		this.erlaeuterung = erlaeuterung;
	}
	
	public String getErlaeuterung(){
		return erlaeuterung;
	}
	
	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	//Konstruktor
	public Eigenschaft(){
	}

}