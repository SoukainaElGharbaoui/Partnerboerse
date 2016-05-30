package de.hdm.gruppe7.partnerboerse.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	Nutzerprofil login(String requestUri) throws Exception;

}
