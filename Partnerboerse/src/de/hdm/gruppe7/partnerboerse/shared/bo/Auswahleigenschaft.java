package de.hdm.gruppe7.partnerboerse.shared.bo;

public class Auswahleigenschaft extends Eigenschaft {

	private static final long serialVersionUID = 1L;
	private String[] optionen = new String[10];
	
	
	public String[] getOptionen() {
		return optionen;
	}

	public void setOptionen(String[] optionen) {
		this.optionen = optionen;
	}
}