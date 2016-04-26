package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;


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
			int koerpergroesse, boolean raucher, String religion) 
			throws IllegalArgumentException;
		
	 /**
	   * Suchen eines Nutzerprofil-Objekts, dessen ProfilId bekannt ist.
	   * 
	   * @param profilId ist die ProfilId.
	   * @return Das erste Nutzerprofil-Objekt, dass den Suchkriterien entspricht.
	   * @throws IllegalArgumentException
	   */
		
	public Nutzerprofil getNutzerprofilById(int profilId) throws IllegalArgumentException; 
		
	
	 /**
	   * Speichern eines Nutzerprofil-Objekts in der Datenbank.
	   * 
	   * @param nutzerprofil zu sicherndes Objekt.
	   * @throws IllegalArgumentException
	   */
	  public void save(Nutzerprofil nutzerprofil) throws IllegalArgumentException;
	  
	  
	  /**
	   * L�schen des �bergebenen Nutzerprofils.
	   * 
	   * @param nutzerprofil das zu l�schende Nutzerprofil
	   * @throws IllegalArgumentException
	   */
	  public void delete(Nutzerprofil nutzerprofil) throws IllegalArgumentException;
	
	  
	  /**
	   * Suchen aller angesehenen Nutzerprofil-Objekte eines Nutzerprofils.
	   * 
	   * @param nutzerprofil ist das Nutzerprofil.
	   * @return Liste von Nutzerprofil-Objekten, die den 
	   * Suchkriterien entsprechen.
	   * @throws IllegalArgumentException
	   */
	  
	  public List<Nutzerprofil> getAngeseheneNpFor(Nutzerprofil nutzerprofil)
	  throws IllegalArgumentException;
	}
