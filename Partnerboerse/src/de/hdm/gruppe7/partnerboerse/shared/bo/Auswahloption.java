package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Auswahloption extends Info {

	private static final long serialVersionUID = 1L;

	private int auswahloptionId;
	private String optionsbezeichnung;


	public Auswahloption() {
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
