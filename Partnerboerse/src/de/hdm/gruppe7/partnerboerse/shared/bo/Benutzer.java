package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Benutzer implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	private static int profilId = 1;

	public static int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

}
