package de.hdm.gruppe7.partnerboerse.shared;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

/**
 * Synchrone Schnittstelle fuer die Verwaltung der Partnerboerse.
 */
@RemoteServiceRelativePath("partnerboerseadministration")
public interface PartnerboerseAdministration extends RemoteService {

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#init()
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#isUserRegistered(String)
	 */
	public boolean isUserRegistered(String userEmail) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#login(String)
	 */
	public Nutzerprofil login(String requestUri) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeObNutzerNeu(String) 
	 */
	public boolean pruefeObNutzerNeu(String userEmail) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createNutzerprofil
	 * (String, String, String, geschlecht, Date, int, String, String, String, String)
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion,
			String emailAddress) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveNutzerprofil
	 * (int, String, String, String, Date, int, String, String, String)
	 */
	public void saveNutzerprofil(int profilId, String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteNutzerprofil(int)
	 */
	void deleteNutzerprofil(int profilId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getNutzerprofilById(int)
	 */
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createSuchprofil
	 * (int, String, String, int, int, int, String, String, String)
	 */
	public Suchprofil createSuchprofil(int profilId, String suchprofilName,
			String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher,
			String religion) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveSuchprofil
	 * (int, int, String, String, int, int, int, String, String, String)
	 */
	public void saveSuchprofil(int profilId, int suchprofilId,
			String suchprofilName, String geschlecht, int alterMinInt,
			int alterMaxInt, int koerpergroesseInt, String haarfarbe,
			String raucher, String religion) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteSuchprofil(int, String)
	 */
	public void deleteSuchprofil(int suchprofilId, String suchprofilName)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllSuchprofileFor(int)
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getSuchprofilByName(int, String)
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName)
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getSuchprofilById(int)
	 */
	public Suchprofil getSuchprofilById (int suchprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeSuchprofilnameCreate(int, String)
	 */
	public int pruefeSuchprofilnameCreate(int profilId, String suchprofilname)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeSuchprofilnameEdit(int, int, String)
	 */
	public int pruefeSuchprofilnameEdit(int profilId, int suchprofilId,
			String suchprofilname) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGemerkteNutzerprofileFor(int)
	 */
	public Merkliste getGemerkteNutzerprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeVermerkstatus(int, int)
	 */
	public int pruefeVermerkstatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#vermerkstatusAendern(int, int)
	 */
	public int vermerkstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGesperrteNutzerprofileFor(int)
	 */
	public Sperrliste getGesperrteNutzerprofileFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#pruefeSperrstatusFremdprofil(int, int)
	 */
	public int pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getSperrstatusEigenesProfil(int, int)
	 */
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#sperrstatusAendern(int, int)
	 */
	public int sperrstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getUnangeseheneNutzerprofile(int)
	 */
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#besuchSetzen(int, int)
	 */
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#berechneAehnlichkeitNpFor(int)
	 */
	public void berechneAehnlichkeitNpFor(int profilId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGeordnetePartnervorschlaegeNp(int)
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#berechneAehnlichkeitSpFor(int)
	 */
	public void berechneAehnlichkeitSpFor(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getGeordnetePartnervorschlaegeSp(int, String)
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId,
			String suchprofilName) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllEigenschaften()
	 */
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllEigenschaften()
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllUnusedEigenschaften(int)
	 */
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften(
			int profilId) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAuswahleigenschaften(List)
	 */
	public List<Auswahleigenschaft> getAuswahleigenschaften(
			List<Eigenschaft> listE) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getEigAById(int)
	 */
	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getEigBById(int)
	 */
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createInfo(int, List)
	 */
	public List<Info> createInfo(int profilId, List<Info> infos)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#getAllInfos(int)
	 */
	public Map<List<Info>, List<Eigenschaft>> getAllInfos(int profilId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteAllInfosNeu(int)
	 */
	public void deleteAllInfosNeu(int profilId) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteOneInfoNeu(int, int)
	 */
	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveInfo(int, List)
	 */
	public void saveInfo(int profilId, List<Info> listI)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createBeschreibungseigenschaft(int, String, String, String)
	 */
	public Beschreibungseigenschaft createBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#createAuswahleigenschaft(int, String, String, List)
	 */
	public Auswahleigenschaft createAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloptionen) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveBeschreibungseigenschaft(int, String, String, String)
	 */
	public void saveBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext)
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#saveAuswahleigenschaft(int, String, String, List)
	 */
	public void saveAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloptionen)
			throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteBeschreibungseigenschaft(int)
	 */
	void deleteBeschreibungseigenschaft(int eigenschaftId) throws IllegalArgumentException;
	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl#deleteAuswahleigenschaft(int)
	 */
	void deleteAuswahleigenschaft(int eigenschaftId) throws IllegalArgumentException;
	
}
