package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.io.Serializable;

public class Benutzer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static int profilId;

	public static int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}
	

}