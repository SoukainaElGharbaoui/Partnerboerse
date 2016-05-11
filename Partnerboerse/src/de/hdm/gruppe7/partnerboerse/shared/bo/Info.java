package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Info extends BusinessObject {
	
	private static final long serialVersionUID = 1L;

	//Attribute
	private int infoId;
	private String infotext;
	private int eigenschaftId;
	private int nutzerprofilId;
	private int auswahloptionId;

	//Konstruktor
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Getter- & Settermethoden

	public int getAuswahloptionId() {
		return auswahloptionId;
	}

	public void setAuswahloptionId(int auswahloptionId) {
		this.auswahloptionId = auswahloptionId;
	}

	public int getNutzerprofilId() {
		return nutzerprofilId;
	}

	public void setNutzerprofilId(int profilId) {
		this.nutzerprofilId = profilId;
	}

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public String getInfotext() {
		return infotext;
	}
	public void setInfotext(String infotext) {
		this.infotext = infotext;
	}
	
//	public Eigenschaft getEigenschaft() {
//		return eigenschaft;
//	}
//	public int getEigenschaftId(){
//		return eigenschaft.getEigenschaftId();
//	}
//	public void setEigenschaftId(int eigenschaftId) {
//		this.eigenschaft.eigenschaftId = eigenschaftId;
//	}
	
//	public Nutzerprofil getProfil() {
//		return nutzerprofil;
//	}
//	public int getNutzerprofilId(){
//		return nutzerprofil.getProfilId();
//	}
//	public void setNutzerprofilId(int profilId) {
//		this.nutzerprofil.setProfilId(profilId);
//	}
}
