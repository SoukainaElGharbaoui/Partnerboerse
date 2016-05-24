
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
	
//	public String toString() {
//	    return super.toString() + "Eigenschaft-ID: #" + this.eigenschaftId 
//	    		+ "Erlaeuterung: " + this.erlaeuterung 
//	    		+ "Typ: " + this.typ; 
//	  }
//	
//	public boolean equals(Object o) {
//	    if (o != null && o instanceof Eigenschaft) {
//	      Eigenschaft eig = (Eigenschaft) o;
//	      try {
//	        return super.equals(eig);
//	      }
//	      catch (IllegalArgumentException e) {
//	        return false;
//	      }
//	    }
//	    return false;
//	  }

}