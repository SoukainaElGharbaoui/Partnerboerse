package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.Vector;

public class Sperrliste extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private Vector<Nutzerprofil> gesperrteNutzerprofile;

	public Vector<Nutzerprofil> getGesperrteNutzerprofile() {
		return gesperrteNutzerprofile;
	}

	public void setGesperrteNutzerprofile(Vector<Nutzerprofil> gesperrteNutzerprofile) {
		this.gesperrteNutzerprofile = gesperrteNutzerprofile;
	}

}
