package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.thies.bankProjekt.shared.BankVerwaltungImpl;

/**
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung 
 * der Partnerboerse. 
 */

/**
 * <code>@RemoteServiceRelativePath("partnerboerseadministration")</code> ist
 * bei der Adressierung des aus der zugehörigen Impl-Klasse entstehenden
 * Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
 * Servlets an.
 */

@RemoteServiceRelativePath("partnerboerseadministration")
public interface PartnerboerseAdministration extends RemoteService {

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor der implementierenden
	 * Klasse {@link PartnerboerseAdministrationImpl} notwendig. 
	 * Diese Methode wird direkt nach der Instantiierung aufgerufen.
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
	  /**
	   * Ein neues Nutzerprofil anlegen. 
	   * 
	   * @param vorname, nachname, geburtsdatum, geschlecht, haarfarbe
	   * @param koerpergroesse, raucher, religion 
	   * @return fertiges Nutzerprofil-Objekt
	   * @throws IllegalArgumentException
	   */
	
	public Nutzerprofil createNutzerprofil(String vorname, String nachname, 
			Date geburtsdatum, String geschlecht, String haarfarbe, 
			String koerpergroesse, boolean raucher, String religion) 
			throws IllegalArgumentException;
	
	public getNutzerprofilById() throws IllegalArgumentException; 
	
	  /**
	   * Speichern eines Nutzerprofils-Objekts in der Datenbank.
	   * 
	   * @param vorname, nachname, geburtsdatum, geschlecht, haarfarbe
	   * @param koerpergroesse, raucher, religion 
	   * @return fertiges Nutzerprofil-Objekt
	   * @throws IllegalArgumentException
	   */
	
	
	}
