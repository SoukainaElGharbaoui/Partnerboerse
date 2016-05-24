
package de.hdm.gruppe7.partnerboerse.server;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
		implements PartnerboerseAdministration {

	/**
	 * Referenz auf die DatenbankMapper.
	 **/
	private NutzerprofilMapper nutzerprofilMapper = null;
	private SuchprofilMapper suchprofilMapper = null;
	private InfoMapper infoMapper = null;

	/**
	 * No-Argument-Konstruktor.
	 */
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException { 
	}

	/**
	 * Initialsierungsmethode, die für jede Instanz von <code>PartnerboerseAdministrationImpl</code> aufgerufen werden muss.
	 */
	@Override
	public void init() throws IllegalArgumentException {
		this.nutzerprofilMapper = NutzerprofilMapper.nutzerprofilMapper();
		this.suchprofilMapper = SuchprofilMapper.suchprofilMapper();
		this.infoMapper = InfoMapper.infoMapper();
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Nutzerprofil
	 * ***************************************************************************
	 */

	/**
	 * Nutzerprofil anlegen. 
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geschlecht, String geburtsdatum, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {

		// Neues Nutzerprofil-Objekt erstellen. 
		Nutzerprofil n = new Nutzerprofil();
		n.setVorname(vorname);
		n.setNachname(nachname);
		n.setGeschlecht(geschlecht);
		n.setGeburtsdatum(geburtsdatum);
		n.setKoerpergroesseInt(koerpergroesseInt);
		n.setHaarfarbe(haarfarbe);
		n.setRaucher(raucher);
		n.setReligion(religion);

		// Vorläufige Profil-ID setzen.
		n.setProfilId(1);

		return this.nutzerprofilMapper.insertNutzerprofil(n);
	}
	
	/**
	 * Nutzerprofil aktualisieren. 
	 */
	public void saveNutzerprofil(String vorname, String nachname, String geschlecht, 
			String geburtsdatum, int koerpergroesseInt, String haarfarbe, 
			String raucher, String religion) throws IllegalArgumentException {
		
		Nutzerprofil n = new Nutzerprofil(); 
		n.setVorname(vorname);
		n.setNachname(nachname);
		n.setGeschlecht(geschlecht);
		n.setGeburtsdatum(geburtsdatum);
		n.setKoerpergroesseInt(koerpergroesseInt);
		n.setHaarfarbe(haarfarbe);
		n.setRaucher(raucher);
		n.setReligion(religion);

		this.nutzerprofilMapper.updateNutzerprofil(n);
	}
	
	/**
	 * Nutzerprofil löschen.
	 */
	@Override
	public void deleteNutzerprofil(int profilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteNutzerprofil(profilId);	
	}

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen. 
	 */
	@Override
	public Nutzerprofil getNutzerprofilById(int profilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}
	
	/**
	 * ***********************************
	 * Unnötig, da gleicher Mapper-Aufruf!
	 * ***********************************
	 */
	public Nutzerprofil getFremdprofilById(int fremdprofilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
	}
	
	/**
	 * Alle Nutzerprofile auslesen.
	 */
	public List<Nutzerprofil> getAllNutzerprofile (Nutzerprofil nutzerprofil) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findAllNutzerprofile();
	}
	
	/**
	 * **********************************
	 * Unnötig, da gleicher Mapper-Aufruf!
	 * **********************************
	 */
	public List<Nutzerprofil> getAllProfile() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findAllNutzerprofile();
	}

	/**
	 * **********************************
	 * Brauchen wir das???
	 * **********************************
	 */
	@Override
	public List<Nutzerprofil> getAngeseheneNpFor(Nutzerprofil nutzerprofil)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Nutzerprofil
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Suchprofil
	 * ***************************************************************************
	 */
	
	/**
	 * Suchprofil anlegen. 
	 */
	public Suchprofil createSuchprofil(String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {
	
			Suchprofil s = new Suchprofil();
			s.setGeschlecht(geschlecht);
			s.setAlterMinInt(alterMinInt);
			s.setAlterMaxInt(alterMaxInt);
			s.setKoerpergroesseInt(koerpergroesseInt);
			s.setHaarfarbe(haarfarbe);
			s.setRaucher(raucher);
			s.setReligion(religion);
			
			s.setProfilId(1);

			return this.suchprofilMapper.insertSuchprofil(s);
	}

	/**
	 * Suchprofil aktualisieren. 
	 */
	public void saveSuchprofil(String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) 
			throws IllegalArgumentException {
		
			Suchprofil s = new Suchprofil();
			s.setGeschlecht(geschlecht);
			s.setAlterMinInt(alterMinInt);
			s.setAlterMaxInt(alterMaxInt);
			s.setKoerpergroesseInt(koerpergroesseInt);
			s.setHaarfarbe(haarfarbe);
			s.setRaucher(raucher);
			s.setReligion(religion);
		
		this.suchprofilMapper.updateSuchprofil(s);
	
	}

	/**
	 * Suchprofil löschen.
	 */
	public void deleteSuchprofil(int profilId) throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profilId);
	}
	
	/**
	 * Suchprofil anhand der Profil-ID auslesen.
	 */
	@Override
	public Suchprofil getSuchprofilById(int profilId) throws IllegalArgumentException {
		return this.suchprofilMapper.findBySuchprofilId(profilId);
	}

	/**
	 * Alle Suchprofile auslesen.
	 */
	public List<Suchprofil> getAllSuchprofile() throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofile();
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Suchprofil
	 * ***************************************************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Merkliste
	 * ***************************************************************************
	 */
	
	// Alle Vermerke eines Nutzerprofils auslesen.
	public Vector<Nutzerprofil> getGemerkteNutzerprofileFor(int profilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findGemerkteNutzerprofileFor(profilId);
	}
	
	// Vermerkstatus ermitteln. 
	public int getVermerkstatus(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.pruefeVermerk(profilId, fremdprofilId); 
	}
	
	// Vermerk einfügen. 
	public void vermerkSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertVermerk(profilId, fremdprofilId); 
	}
	
	// Vermerk löschen. 
	public void vermerkLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteVermerk(profilId, fremdprofilId); 
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Merkliste
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Sperrliste
	 * ***************************************************************************
	 */
	// Alle Sperrungen eines Nutzerprofils auslesen. 
	public Vector<Nutzerprofil> getGesperrteNutzerprofileFor(int profilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findGesperrteNutzerprofileFor(profilId);
	}
	
	// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde. 
	public int getSperrstatusFremdprofil(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.pruefeSperrungFremdprofil(profilId, fremdprofilId); 
	}
	
	// Prüfen, ob Benutzer von Fremdprofil gesperrt wurde. 
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.pruefeSperrungEigenesProfil(profilId, fremdprofilId); 
	}
	
	// Sperrung einfügen. 
	public void sperrungSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertSperrung(profilId, fremdprofilId); 
	}
		
	// Sperrung löschen. 
	public void sperrungLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteSperrung(profilId, fremdprofilId); 
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Sperrliste
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Partnervorschläge
	 * ***************************************************************************
	 */
	
	// Alle unangesehenen Nutzerprofile auslesen.
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
	}
	
	// Besuch setzen.
	public void besuchSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId); 
	}
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Partnervorschläge
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Info
	 * ***************************************************************************
	 */
	
	public Info createBeschreibungsinfo(int profilId, int eigenschaftId, String infotext) throws IllegalArgumentException {
		
		Info info = new Info();
		info.setNutzerprofilId(profilId);
		info.setEigenschaftId(eigenschaftId);
		info.setInfotext(infotext);
		
		return this.infoMapper.insertBeschreibungsinfo(info);
	}
	
	public Info createAuswahlinfo(int profilId, int eigenschaftId, int auswahloptionIdInt) throws IllegalArgumentException {
		
		Info info = new Info();
		info.setNutzerprofilId(profilId);
		info.setEigenschaftId(eigenschaftId);
		info.setAuswahloptionId(auswahloptionIdInt);
		
		return this.infoMapper.insertAuswahlinfo(info);
	}
	
	public void saveInfoA(int profilId, int neueAuswahloptionId, int eigenschaftId)
	throws IllegalArgumentException {
		
		this.infoMapper.updateInfoA(profilId, neueAuswahloptionId, eigenschaftId);
	
	}
	
	public void saveInfoB(int profilId, int eigenschaftId, String infotext)
			throws IllegalArgumentException {
				
		this.infoMapper.updateInfoB(profilId, eigenschaftId, infotext);
	}
	
	public List<Eigenschaft> getAllEigenschaftenB() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenB();
	}
	
	public List<Eigenschaft> getAllEigenschaftenA() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenA();
	}
	
	public List<Auswahloption> getAllAuswahloptionen(int eigenschaftId) throws IllegalArgumentException {
		return this.infoMapper.findAllAuswahloptionen(eigenschaftId);
	}
	
	public List<Info> getAllInfosB(int profilId) throws IllegalArgumentException {
		return this.infoMapper.findAllInfosB(profilId);
	}
	
	public List<Info> getAllInfosA(int profilId) throws IllegalArgumentException {
		return this.infoMapper.findAllInfosA(profilId);
	}
	
	public Info getOptionById(int eigenschaftId) throws IllegalArgumentException {
		return this.infoMapper.findOptionById(eigenschaftId);
	}
	
	public Info getInfoAById(String optionsbezeichnung, int eigenschaftId) throws IllegalArgumentException {
		return this.infoMapper.findByInfoAId(optionsbezeichnung, eigenschaftId);
	}
	
	public void deleteAllInfos(int profilId) throws IllegalArgumentException {
		
		this.infoMapper.deleteAllInfos(profilId);
	}
	
	public void deleteOneInfoB(int profilId, int eigenschaftId) throws IllegalArgumentException {
		
		this.infoMapper.deleteOneInfoB(profilId, eigenschaftId);
	}

	public void deleteOneInfoA(int profilId, int eigenschaftId) throws IllegalArgumentException {
	
		this.infoMapper.deleteOneInfoA(profilId, eigenschaftId);
	}

	public List<Info> getAInfoByProfilId(int profilId) throws IllegalArgumentException {
		return this.infoMapper.findAInfoByProfilId(profilId); 
	}
		
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Info
	 * ***************************************************************************
	 */

}

