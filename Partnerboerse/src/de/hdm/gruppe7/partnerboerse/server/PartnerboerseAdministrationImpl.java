
package de.hdm.gruppe7.partnerboerse.server;

import java.sql.Array;
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
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahlinfo;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahloption;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungsinfo;
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
			String geschlecht, String geburtsdatum, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {

		Nutzerprofil nutzerprofil = new Nutzerprofil();
		nutzerprofil.setVorname(vorname);
		nutzerprofil.setNachname(nachname);
		nutzerprofil.setGeschlecht(geschlecht);
		nutzerprofil.setGeburtsdatum(geburtsdatum);
		nutzerprofil.setKoerpergroesseInt(koerpergroesseInt);
		nutzerprofil.setHaarfarbe(haarfarbe);
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
			String geschlecht, String geburtsdatum, int koerpergroesseInt, String haarfarbe, 
			String raucher, String religion) throws IllegalArgumentException {

		 this.nutzerprofilMapper.updateNutzerprofil(vorname, nachname,
				 geschlecht, geburtsdatum, koerpergroesseInt, haarfarbe, raucher, religion);

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
	 * Auslesen aller Nutzerprofile
	 * @return
	 */
	
	public List<Nutzerprofil> getAllNutzerprofile (Nutzerprofil nutzerprofil) {
		return this.nutzerprofilMapper.findAllNutzerprofile();
	}
	

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
	public int getVermerkstatus(int profilId, int fremdprofilId) throws IllegalArgumentException {
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
	
	public Suchprofil createSuchprofil(String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {
	
			Suchprofil suchprofil = new Suchprofil();
			suchprofil.setGeschlecht(geschlecht);
			suchprofil.setAlterMinInt(alterMinInt);
			suchprofil.setAlterMaxInt(alterMaxInt);
			suchprofil.setKoerpergroesseInt(koerpergroesseInt);
			suchprofil.setHaarfarbe(haarfarbe);
			suchprofil.setRaucher(raucher);
			suchprofil.setReligion(religion);
			
			suchprofil.setProfilId(1);

			return this.suchprofilMapper.insertSuchprofil(suchprofil);
	}


	public void saveSuchprofil(String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) 
			throws IllegalArgumentException {
		
		this.suchprofilMapper.updateSuchprofil(geschlecht, alterMinInt, alterMaxInt, 
				koerpergroesseInt, haarfarbe, raucher, religion);
	
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
	
	public Beschreibungsinfo createBeschreibungsinfo(int profilId, int eigenschaftId, String infotext) throws IllegalArgumentException {
		
		Beschreibungsinfo infoB = new Beschreibungsinfo();
		infoB.setNutzerprofilId(profilId);
		infoB.setEigenschaftId(eigenschaftId);
		infoB.setInfotext(infotext);
		
		return this.infoMapper.insertBeschreibungsinfo(infoB);
	}
	
	public Auswahlinfo createAuswahlinfo(int profilId, int eigenschaftId, int auswahloptionIdInt) throws IllegalArgumentException {
		
		Auswahlinfo infoA = new Auswahlinfo();
		infoA.setNutzerprofilId(profilId);
		infoA.setEigenschaftId(eigenschaftId);
		infoA.setAuswahloptionId(auswahloptionIdInt);
		
		return this.infoMapper.insertAuswahlinfo(infoA);
	}
	
	
	public void saveInfoB(int profilId, int eigenschaftId, String infotext)
			throws IllegalArgumentException {
		
		Beschreibungsinfo infoB = new Beschreibungsinfo();
		infoB.setNutzerprofilId(profilId);
		infoB.setEigenschaftId(eigenschaftId);
		infoB.setInfotext(infotext);
				
		this.infoMapper.updateInfoB(infoB);
	}
	
//	public void saveInfoA(int profilId, int neueAuswahloptionId, int eigenschaftId)
//	throws IllegalArgumentException {
//		
//		this.infoMapper.updateInfoA(profilId, neueAuswahloptionId, eigenschaftId);
//	
//	}

	
	public List<Eigenschaft> getAllEigenschaftenB() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenB();
	}
	
	public List<Eigenschaft> getAllEigenschaftenA() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenA();
	}
	
	public List<Auswahloption> getAllAuswahloptionen(int eigenschaftId) throws IllegalArgumentException {
		return this.infoMapper.findAllAuswahloptionen(eigenschaftId);
	}
	
	public List<Beschreibungsinfo> getAllInfosB(int profilId) throws IllegalArgumentException {
		return this.infoMapper.findAllInfosB(profilId);
	}
	
//	public List<Auswahlinfo> getAllInfosA(int profilId) throws IllegalArgumentException {
//		return this.infoMapper.findAllInfosA(profilId);
//	}
//	
//	public Auswahloption getOptionById(int eigenschaftId, int nutzerprofilId) throws IllegalArgumentException {
//		return this.infoMapper.findOptionById(eigenschaftId, nutzerprofilId);
//	}
	
	
	public String[] getAllInfosGesamt(int profilId) {
		
		String[] a = new String[3];
		
		List<Auswahlinfo> result = this.infoMapper.findAllInfosA(profilId);
		
		for(Auswahlinfo iA : result) {
			
			Auswahloption optionA = this.infoMapper.findOptionById(iA.getEigenschaftId(), iA.getNutzerprofilId());
			
			a[0] = String.valueOf(iA.getNutzerprofilId());
			a[1] = String.valueOf(iA.getEigenschaftId());
			a[2] = optionA.getOptionsbezeichnung();
		}
			return a;
	}
	
	
//	public Info getInfoAById(String optionsbezeichnung, int eigenschaftId) throws IllegalArgumentException {
//		return this.infoMapper.findByInfoAId(optionsbezeichnung, eigenschaftId);
//	}
	
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
	
	// Besuch hinzufuegen. 
		public void besuchSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
			this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId); 
		}
		
		public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId) throws IllegalArgumentException {
			return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
		}

//		public List<Info> getAInfoByProfilId(int profilId) throws IllegalArgumentException {
//			return this.infoMapper.findAInfoByProfilId(profilId); 
//		}


}

