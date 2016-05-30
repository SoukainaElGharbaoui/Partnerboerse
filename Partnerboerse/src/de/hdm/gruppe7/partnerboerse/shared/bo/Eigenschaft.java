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

//package de.hdm.gruppe7.partnerboerse.shared.bo;
//
//public class Eigenschaft extends BusinessObject {
//
//	private static final long serialVersionUID = 1L;
//
//	public String bezeichnung;
//	public int eigenschaftId;
//	public String erlaeuterung;
//	public String eigenschaft;
//
//	public void setBezeichnung(String bezeichnung) {
//		this.bezeichnung = bezeichnung;
//	}
//
//	public String getBezeichnung() {
//		return bezeichnung;
//	}
//
//	public void setEigenschaftId(int eigenschaftId) {
//		this.eigenschaftId = eigenschaftId;
//	}
//
//	public int getEigenschaftId() {
//		return eigenschaftId;
//	}
//
//	public void setErlaeuterung(String erlaeuterung) {
//		this.erlaeuterung = erlaeuterung;
//	}
//
//	public String getErlaeuterung() {
//		return erlaeuterung;
//	}
//
//	public void setEigenschaft(String eigenschaft) {
//		this.eigenschaft = eigenschaft;
//	}
//
//	public String getEigenschaft() {
//		return eigenschaft;
//	}
//
//	// Konstruktor
//	public Eigenschaft() {
//		super();
//	}
//
//	public String toString() {
//		return super.toString() + "Eigenschaft-ID: #" + this.eigenschaftId + "Bezeichnung: " + this.bezeichnung
//				+ "Erlaeuterung: " + this.erlaeuterung + "Eigenschaft: " + this.eigenschaft;
//	}
//
//	public boolean equals(Object o) {
//		if (o != null && o instanceof Eigenschaft) {
//			Eigenschaft eig = (Eigenschaft) o;
//			try {
//				return super.equals(eig);
//			} catch (IllegalArgumentException e) {
//				return false;
//			}
//		}
//		return false;
//	}
//
//}
