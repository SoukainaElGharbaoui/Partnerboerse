package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Benutzer implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	private int profilId;

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}
	
	public int getProfilId() {
		return this.profilId;
	}

	

}
