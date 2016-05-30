package de.hdm.gruppe7.partnerboerse.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.MerklisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SperrlisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
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
			String geschlecht, String geburtsdatum, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {

		this.nutzerprofilMapper.updateNutzerprofil(vorname, nachname,
				geschlecht, geburtsdatum, koerpergroesseInt, haarfarbe,
				raucher, religion);

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
	 * 
	 * @return
	 */

	public List<Nutzerprofil> getAllNutzerprofile(Nutzerprofil nutzerprofil) {
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
	public Vector<Merkliste> getGemerkteNutzerprofileFor(int profilId)
			throws IllegalArgumentException {
		return this.merklisteMapper.findAllVermerkeFor(profilId);
	}

	// Vermerkstatus ermitteln.
	public int getVermerkstatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId);
	}

	// Vermerk einfügen.
	public void vermerkSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.merklisteMapper.insertVermerk(profilId, fremdprofilId);
	}

	// Vermerk löschen.
	public void vermerkLoeschen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.merklisteMapper.deleteVermerk(profilId, fremdprofilId);
	}

	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */

	/**
	 * ABSCHNITT SPERRLISTE: BEGINN
	 */
	// Alle Sperrungen eines Nutzerprofils auslesen.
	public Vector<Sperrliste> getGesperrteNutzerprofileFor(int profilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.findAllSperrungenFor(profilId);
	}

	// Prüfen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int getSperrstatusFremdprofil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId,
				fremdprofilId);
	}

	// Prüfen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profilId,
				fremdprofilId);
	}

	// Sperrung einfügen.
	public void sperrungSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.sperrlisteMapper.insertSperrung(profilId, fremdprofilId);
	}

	// Sperrung löschen.
	public void sperrungLoeschen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.sperrlisteMapper.deleteSperrung(profilId, fremdprofilId);
	}

	/**
	 * ABSCHNITT SPERRLISTE: ENDE
	 */

	/**
	 * ABSCHNITT SUCHPROFIL: BEGINN
	 */

	public Suchprofil createSuchprofil(String geschlecht, int alterMinInt,
			int alterMaxInt, int koerpergroesseInt, String haarfarbe,
			String raucher, String religion) throws IllegalArgumentException {

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

	public void saveSuchprofil(String geschlecht, int alterMinInt,
			int alterMaxInt, int koerpergroesseInt, String haarfarbe,
			String raucher, String religion) throws IllegalArgumentException {

		this.suchprofilMapper.updateSuchprofil(geschlecht, alterMinInt,
				alterMaxInt, koerpergroesseInt, haarfarbe, raucher, religion);

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

	
	public List<Eigenschaft> getAllEigenschaftenNeu() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenNeu();
	}
	
	public List<String> getAllInfosNeu(int nutzerprofilId) throws IllegalArgumentException {

		List<String> list1 = new ArrayList<String>();
		List<Info> result = new ArrayList<Info>();
		
		result = this.infoMapper.findAllInfosNeu(nutzerprofilId);

		for (Info i : result) {

				int eigenschaftId = i.getEigenschaftId();

				Eigenschaft e = new Eigenschaft();
				e = this.infoMapper.findEigenschaftById(eigenschaftId);

				list1.add(String.valueOf(i.getNutzerprofilId()));
				list1.add(String.valueOf(eigenschaftId));
				list1.add(e.getErlaeuterung());
				list1.add(String.valueOf(i.getInfotext()));
				list1.add(e.getTyp());
			}
		System.out.println(list1);
		return list1;
	}
	
	
	public Info createInfoNeu(int profilId, int eigenschaftId, String infotext) 
			throws IllegalArgumentException {
		
		Info i = new Info();
		i.setNutzerprofilId(profilId);
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);
		
		return this.infoMapper.insertInfoNeu(i);
	}
	
	
	public void deleteOneInfoNeu(int profilId, int eigenschaftId) throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(profilId, eigenschaftId);
	}
	
	
	public void deleteAllInfosNeu(int profilId) throws IllegalArgumentException {
		this.infoMapper.deleteAllInfosNeu(profilId);
	}
	
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)  throws IllegalArgumentException {
		return this.infoMapper.findEigBById(eigenschaftId);
	}
	
	public Auswahleigenschaft getEigAById(int eigenschaftId) throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAById(eigenschaftId);
		System.out.println(optionen.getOptionen());
		
		return optionen;
//		return this.infoMapper.findEigAById(eigenschaftId);
		
	}
	

	public List<Nutzerprofil> getAllProfile() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findAllNutzerprofile();
	}


	// Besuch hinzufuegen.
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId);
	}

	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
	}

}
