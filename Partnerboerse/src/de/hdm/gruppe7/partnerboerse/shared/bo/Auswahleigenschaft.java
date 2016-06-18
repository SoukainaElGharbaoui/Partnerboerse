package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Realisierung einer Auswahleigenschaft. 
 */
public class Auswahleigenschaft extends Eigenschaft {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste von Optionen. 
	 */
	private List<String> optionen = new ArrayList<String>();
	
	/**
	 * Optionen auslesen. 
	 * @return optionen
	 */
	public List<String> getOptionen() {
		return optionen;
	}

	/**
	 * Optionen setzen.
	 * @param optionen
	 */
	public void setOptionen(List<String> optionen) {
		this.optionen = optionen;
	}	
	
}

