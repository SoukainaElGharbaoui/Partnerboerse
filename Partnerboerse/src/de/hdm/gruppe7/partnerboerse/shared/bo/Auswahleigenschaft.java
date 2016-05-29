package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.ArrayList;
import java.util.List;

public class Auswahleigenschaft extends Eigenschaft {

	private static final long serialVersionUID = 1L;
	private List<String> optionen = new ArrayList<String>();
	
	public List<String> getOptionen() {
		return optionen;
	}

	public void setOptionen(List<String> optionen) {
		this.optionen = optionen;
	}	
	
}