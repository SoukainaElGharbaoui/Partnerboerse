package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Auswahloption extends BusinessObject{

	private static final long serialVersionUID = 1L;

	//Attribute
	private int auswahloptionId;
	private String optionsbezeichnung;
	private Eigenschaft eigenschaft;

	//Konstruktor
	public Auswahloption() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Getter- & Settermethoden
	
	public Eigenschaft getEigenschaft() {
		return eigenschaft;
	}

	public void setEigenschaft(Eigenschaft eigenschaft) {
		this.eigenschaft = eigenschaft;
	}
	public int getAuswahloptionId() {
		return auswahloptionId;
	}
	public void setAuswahloptionId(int auswahloptionId) {
		this.auswahloptionId = auswahloptionId;
	}
	public String getOptionsbezeichnung() {
		return optionsbezeichnung;
	}
	public void setOptionsbezeichnung(String optionsbezeichnung) {
		this.optionsbezeichnung = optionsbezeichnung;
	}	
	
}