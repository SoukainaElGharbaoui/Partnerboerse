
package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

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
	 * Klasse {@link PartnerboerseAdministrationImpl} notwendig. Diese Methode
	 * wird direkt nach der Instantiierung aufgerufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * Ein neues Nutzerprofil anlegen.
	 * 
	 * @param vorname
	 *            , nachname, geburtsdatum, geschlecht, haarfarbe
	 * @param koerpergroesse
	 *            , raucher, religion
	 * @return fertiges Nutzerprofil-Objekt
	 * @throws IllegalArgumentException
	 */

	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geburtsdatum, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion);
	
	
	/**
	 * Das existierende Nutzerprofil updaten.
	 * 
	 * @param vorname
	 *            , nachname, geburtsdatum, geschlecht, haarfarbe
	 * @param koerpergroesse
	 *            , raucher, religion
	 * @return fertiges Nutzerprofil-Objekt
	 * @throws IllegalArgumentException
	 */

	
	public void saveNutzerprofil(String vorname, String nachname,
			String geburtsdatum) throws IllegalArgumentException;


	/**
	 * Suchen eines Nutzerprofil-Objekts, dessen ProfilId bekannt ist.
	 * 
	 * @param profilId
	 *            ist die ProfilId.
	 * @return Das erste Nutzerprofil-Objekt, dass den Suchkriterien entspricht.
	 * @throws IllegalArgumentException
	 */

	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException;
	
	/**
	 * Suchen eines Fremdprofil-Objekt, dessen FremdprofilId bekannt ist
	 */
	
	public Nutzerprofil getFremdprofilById(int fremdprofilId)
			throws IllegalArgumentException;
	/**
	 * Speichern eines Nutzerprofil-Objekts in der Datenbank.
	 * 
	 * @param nutzerprofil
	 *            zu sicherndes Objekt.
	 * @throws IllegalArgumentException
	 */
//	public void save (String vorname, String nachname, String geschlecht, 
//			String haarfarbe,String koerpergroesse, String raucher, 
//			String religion, String geburtsdatum) throws IllegalArgumentException;
	
	/**
	 * l�schen eines Nutzerprofils in der Datenbank
	 * @param profilId
	 */

	void deleteNutzerprofil(int profilId);

	/**
	 * Suchen aller angesehenen Nutzerprofil-Objekte eines Nutzerprofils.
	 * 
	 * @param nutzerprofil
	 *            ist das Nutzerprofil.
	 * @return Liste von Nutzerprofil-Objekten, die den Suchkriterien
	 *         entsprechen.
	 * @throws IllegalArgumentException
	 */

	public List<Nutzerprofil> getAngeseheneNpFor(Nutzerprofil nutzerprofil)
			throws IllegalArgumentException;

	/**
	 * ABSCHNITT MERKLISTE: BEGINN
	 */
	// Alle Vermerke eines Nutzerprofils auslesen.
	public Vector<Merkliste> getGemerkteNutzerprofileFor(int profilId)
			throws IllegalArgumentException;
	
	// Vermerkstatus ermitteln. 
	public int getVermerkStatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException; 
	
	// Vermerk einfügen.
	public void vermerkEinfuegen(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	// Vermerk löschen. 
	public void vermerkLoeschen(int profilId, int fremdprofilId)
			throws IllegalArgumentException;
	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */
	
	
	
	/**
	 * ABSCHNITT SUCHPROFIL: BEGINN
	 */
	
	/**
	 * Ein neues Suchprofil anlegen.
	 * 
	 * @param alterMin, alterMax, geschlecht, haarfarbe, koerpergroesse, raucher, religion
	 * @return fertiges Suchprofil-Objekt
	 * @throws IllegalArgumentException
	 */

	public Suchprofil createSuchprofil(String alterMin, String alterMax, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion);
	
	/**
	 * Speichern eines Suchprofil-Objekts in der Datenbank.
	 * 
	 * @param suchprofil
	 *            zu sicherndes Objekt.
	 * @throws IllegalArgumentException
	 */
	public Suchprofil save(String alterMin, String alterMax, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion) throws IllegalArgumentException;

	/**
	 * L�schen des �bergebenen Suchprofils.
	 * 
	 * @param suchprofil
	 *            das zu loeschende Suchprofil
	 * @throws IllegalArgumentException
	 */
	public void deleteSuchprofil(int profilId)
			throws IllegalArgumentException;

	/**
	 * Ausgeben aller Suchprfile.
	 * @return Liste aller Suchprofile
	 * @throws IllegalArgumentException
	 */
	
	public List<Suchprofil> getAllSuchprofile()
			throws IllegalArgumentException;

	public Info createInfo(String infotext) 
			throws IllegalArgumentException;
	
	public List<Eigenschaft> getAllEigenschaften()
			throws IllegalArgumentException;
	
/**
 *Auslesen eines Suchprofils anhand der ID
 * @param profilId
 * @return
 * @throws IllegalArgumentException
 */
	
	
	public Suchprofil getSuchprofilById(int profilId)
			throws IllegalArgumentException;
	/**
	 * ABSCHNITT SUCHPROFIL: ENDE
	 */

}

		


