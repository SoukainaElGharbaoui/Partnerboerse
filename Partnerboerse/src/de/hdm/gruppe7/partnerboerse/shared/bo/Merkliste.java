package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Vector;

public class Merkliste extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private Vector<Nutzerprofil> gemerkteNutzerprofile;

	public Vector<Nutzerprofil> getGemerkteNutzerprofile() {
		return gemerkteNutzerprofile;
	}

	public void setGemerkteNutzerprofile(Vector<Nutzerprofil> gemerkteNutzerprofile) {
		this.gemerkteNutzerprofile = gemerkteNutzerprofile;
	}

}
