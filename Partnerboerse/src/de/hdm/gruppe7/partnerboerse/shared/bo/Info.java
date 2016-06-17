package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.util.List;

public class Info extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private int profilId;
	private int eigenschaftId;
	private String infotext;


	public int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

	public String getInfotext() {
		return infotext;
	}

	public void setInfotext(String infotext) {
		this.infotext = infotext;
	}
}
