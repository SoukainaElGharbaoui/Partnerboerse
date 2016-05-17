
package de.hdm.gruppe7.partnerboerse.server;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.MerklisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SperrlisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
		implements PartnerboerseAdministration {

	/**
	 * Referenz auf den DatenbankMapper, der Nutzerprofil-Objekte mit der
	 * Datenbank abgleicht.
	 */
	private NutzerprofilMapper nutzerprofilMapper = null;
	
	private SuchprofilMapper suchprofilMapper = null;
	
	private MerklisteMapper merklisteMapper = null; 
	
	private SperrlisteMapper sperrlisteMapper = null; 
	
	private InfoMapper infoMapper = null;

	/**
	 * No-Argument-Konstruktor
	 * 
	 * @throws IllegalArgumentException
	 */
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode, die für jede Instanz von
	 * <code>PartnerboerseAdministrationImpl</code> aufgerufen werden muss.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {

		/*
		 * Ganz wesentlich ist, dass die PartnerboerseAdministration einen
		 * vollständigen Satz von Mappern besitzt, mit deren Hilfe sie dann mit
		 * der Datenbank kommunizieren kann.
		 */

		this.nutzerprofilMapper = NutzerprofilMapper.nutzerprofilMapper();
		this.suchprofilMapper = SuchprofilMapper.suchprofilMapper();
		this.merklisteMapper = MerklisteMapper.merklisteMapper();
		this.sperrlisteMapper = SperrlisteMapper.sperrlisteMapper(); 
		this.infoMapper = InfoMapper.infoMapper();

	}

	@Override
	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geburtsdatum, String geschlecht, String haarfarbe,
			String koerpergroesse, String raucher, String religion)
			throws IllegalArgumentException {

		Nutzerprofil nutzerprofil = new Nutzerprofil();
		nutzerprofil.setVorname(vorname);
		nutzerprofil.setNachname(nachname);
		nutzerprofil.setGeburtsdatum(geburtsdatum);
		nutzerprofil.setGeschlecht(geschlecht);
		nutzerprofil.setHaarfarbe(haarfarbe);
		nutzerprofil.setKoerpergroesse(koerpergroesse);
		nutzerprofil.setRaucher(raucher);
		nutzerprofil.setReligion(religion);

		// Vorläufige ProfilId setzen.
		nutzerprofil.setProfilId(1);

		return this.nutzerprofilMapper.insertNutzerprofil(nutzerprofil);

	}
	
	/**
	 * Aktualisieren eines Nutzerprofils.
	 */
	
	
	public void saveNutzerprofil(String vorname, String nachname,
			String geburtsdatum, String geschlecht, String haarfarbe, String koerpergroesse, 
			String raucher, String religion) throws IllegalArgumentException {

		 this.nutzerprofilMapper.updateNutzerprofil(vorname, nachname,
				 geburtsdatum, geschlecht, haarfarbe, koerpergroesse, raucher, religion);

	}

	/**
	 * Auslesen eines Nutzerprofils anhand seiner ProfilId.
	 */
	@Override
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException {

		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}
	
	/**
	 * Auslesen eines Fremdprofils anhand seiner fremdprofilId
	 */
	 
	public Nutzerprofil getFremdprofilById(int fremdprofilId)
			throws IllegalArgumentException {

		return this.nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
	}
	
	/**
	 * Speichern eines Nutzerprofils.
	 */
//	@Override
//	public void save(String vorname, String nachname, String geschlecht, 
//			String haarfarbe,String koerpergroesse, String raucher, 
//			String religion, String geburtsdatum) throws IllegalArgumentException {
//
//		Nutzerprofil nutzerprofil = new Nutzerprofil();
//		nutzerprofil.setVorname(vorname);
//		nutzerprofil.setNachname (nachname);
//		nutzerprofil.setGeschlecht(geschlecht);
//		nutzerprofil.setHaarfarbe(haarfarbe);
//		nutzerprofil.setKoerpergroesse(koerpergroesse);
//		nutzerprofil.setRaucher(raucher);
//		nutzerprofil.setReligion(religion);
//		
//		
//
//		this.nutzerprofilMapper.updateNutzerprofil(nutzerprofil);
//	}

	/**
	 * Löschen eines Nutzerprofils.
	 */
	@Override
	public void deleteNutzerprofil(int profilId)
			throws IllegalArgumentException {


		this.nutzerprofilMapper.deleteNutzerprofil(profilId);

		
	}

	@Override
	public List<Nutzerprofil> getAngeseheneNpFor(Nutzerprofil nutzerprofil)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ABSCHNITT MERKLISTE: BEGINN
	 */
	// Alle Vermerke eines Nutzerprofils auslesen.
	public Vector<Merkliste> getGemerkteNutzerprofileFor(int profilId) throws IllegalArgumentException {
		return this.merklisteMapper.findAllVermerkeFor(profilId);
	}
	
	// Vermerkstatus ermitteln. 
	public int getVermerkStatus(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId); 
	}
	
	// Vermerk einfügen. 
	public void vermerkSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.merklisteMapper.insertVermerk(profilId, fremdprofilId); 
	}
	
	// Vermerk löschen. 
	public void vermerkLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.merklisteMapper.deleteVermerk(profilId, fremdprofilId); 
	}
	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */
	
	/**
	 * ABSCHNITT SPERRLISTE: BEGINN
	 */
	// Alle Sperrungen eines Nutzerprofils auslesen. 
	public Vector<Sperrliste> getGesperrteNutzerprofileFor(int profilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.findAllSperrungenFor(profilId);
	}
	
	// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde. 
	public int getSperrstatusFremdprofil(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId, fremdprofilId); 
	}
	
	// Prüfen, ob Benutzer von Fremdprofil gesperrt wurde. 
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profilId, fremdprofilId); 
	}
	
	// Sperrung einfügen. 
	public void sperrungSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.sperrlisteMapper.insertSperrung(profilId, fremdprofilId); 
	}
		
	// Sperrung löschen. 
	public void sperrungLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.sperrlisteMapper.deleteSperrung(profilId, fremdprofilId); 
	}
	
	/**
	 * ABSCHNITT SPERRLISTE: ENDE
	 */
	

	/**
	 * ABSCHNITT SUCHPROFIL: BEGINN
	 */
	
	public Suchprofil createSuchprofil(String alterMin, String alterMax,
			String geschlecht, String haarfarbe, String koerpergroesse,
			String raucher, String religion) throws IllegalArgumentException {
	
			Suchprofil suchprofil = new Suchprofil();
			suchprofil.setAlterMin(alterMin);
			suchprofil.setAlterMax (alterMax);
			suchprofil.setGeschlecht(geschlecht);
			suchprofil.setHaarfarbe(haarfarbe);
			suchprofil.setKoerpergroesse(koerpergroesse);
			suchprofil.setRaucher(raucher);
			suchprofil.setReligion(religion);
			
			suchprofil.setProfilId(1);

			return this.suchprofilMapper.insertSuchprofil(suchprofil);
	}


	public void saveSuchprofil(String alterMin, String alterMax, String geschlecht, 
			String koerpergroesse, String haarfarbe, String raucher, String religion) 
			throws IllegalArgumentException {
		
		this.suchprofilMapper.updateSuchprofil(alterMin, alterMax, geschlecht,
				koerpergroesse, haarfarbe, raucher, religion);
	
	}


	public void deleteSuchprofil(int profilId) throws IllegalArgumentException {
		
		this.suchprofilMapper.deleteSuchprofil(profilId);
	}


	public List<Suchprofil> getAllSuchprofile() throws IllegalArgumentException {
		
		return this.suchprofilMapper.findAllSuchprofile();
		
	}
	
	
	/**
	 * Auslesen eines Suchprofils anhand seiner ProfilId.
	 */
	@Override
	public Suchprofil getSuchprofilById(int profilId)
			throws IllegalArgumentException {
		
		return this.suchprofilMapper.findBySuchprofilId(profilId);
	}
	
	/**
	 * ABSCHNITT SUCHPROFIL: BEGINN
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
	
	public void saveInfo(Info info)
	throws IllegalArgumentException {
		
		this.infoMapper.updateInfo(info);
	
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
	
	public List<Nutzerprofil> getAllProfile() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findAllNutzerprofile();
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
}

