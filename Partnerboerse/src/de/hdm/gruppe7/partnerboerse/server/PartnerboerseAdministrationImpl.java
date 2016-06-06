
package de.hdm.gruppe7.partnerboerse.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.client.CreateNutzerprofil;
import de.hdm.gruppe7.partnerboerse.client.Navigator;
import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.MerklisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SperrlisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Benutzer;
import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Sperrliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;

@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

	/**
	 * Referenz auf die DatenbankMapper.
	 **/
	private NutzerprofilMapper nutzerprofilMapper = null;
	private SuchprofilMapper suchprofilMapper = null;
	private MerklisteMapper merklisteMapper = null;
	private SperrlisteMapper sperrlisteMapper = null;
	private InfoMapper infoMapper = null;
	private Nutzerprofil profil;

	/**
	 * No-Argument-Konstruktor.
	 */
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialsierungsmethode, die fÃ¼r jede Instanz von
	 * <code>PartnerboerseAdministrationImpl</code> aufgerufen werden muss.
	 */
	@Override
	public void init() throws IllegalArgumentException {
		this.nutzerprofilMapper = NutzerprofilMapper.nutzerprofilMapper();
		this.suchprofilMapper = SuchprofilMapper.suchprofilMapper();
		this.merklisteMapper = MerklisteMapper.merklisteMapper();
		this.sperrlisteMapper = SperrlisteMapper.sperrlisteMapper();
		this.infoMapper = InfoMapper.infoMapper();
	}

	public void setUser(Nutzerprofil n) {
		this.profil = n;
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	/**
	 * Nutzerprofil anlegen.
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress)
			throws IllegalArgumentException {

		// Neues Nutzerprofil-Objekt erstellen.
		// Nutzerprofil n = new Nutzerprofil();
		profil.setVorname(vorname);
		profil.setNachname(nachname);
		profil.setGeschlecht(geschlecht);
		profil.setGeburtsdatumDate(geburtsdatumDate);
		profil.setKoerpergroesseInt(koerpergroesseInt);
		profil.setHaarfarbe(haarfarbe);
		profil.setRaucher(raucher);
		profil.setReligion(religion);
		profil.setEmailAddress(emailAddress);

		// VorlÃ¤ufige Profil-ID setzen.
		profil.setProfilId(1);

		return this.nutzerprofilMapper.insertNutzerprofil(profil);
	}

	/**
	 * Nutzerprofil aktualisieren.
	 */
	public void saveNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion, String emailAddress)
			throws IllegalArgumentException {

		// Nutzerprofil n = new Nutzerprofil();
		profil.setVorname(vorname);
		profil.setNachname(nachname);
		profil.setGeschlecht(geschlecht);
		profil.setGeburtsdatumDate(geburtsdatumDate);
		profil.setKoerpergroesseInt(koerpergroesseInt);
		profil.setHaarfarbe(haarfarbe);
		profil.setRaucher(raucher);
		profil.setReligion(religion);
		profil.setEmailAddress(emailAddress);

		this.nutzerprofilMapper.updateNutzerprofil(profil);
	}

	/**
	 * Nutzerprofil lÃ¶schen.
	 */
	@Override
	public void deleteNutzerprofil() throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteNutzerprofil(profil.getProfilId());
	}

	/**
	 * Nutzerprofil anhand dessen Profil-ID auslesen.
	 */
	@Override
	public Nutzerprofil getNutzerprofilById() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(profil.getProfilId());

	}

	/**
	 * *********************************** UnnÃ¶tig, da gleicher Mapper-Aufruf!
	 * ***********************************
	 */
	public Nutzerprofil getFremdprofilById(int fremdprofilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
	}

	/**
	 * Alle Nutzerprofile auslesen.
	 */

	public List<Nutzerprofil> getAllNutzerprofile() throws IllegalArgumentException {

		return this.nutzerprofilMapper.findAllNutzerprofile();
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Suchprofil
	 * *************************************************************************
	 * **
	 */

	/**
	 * Suchprofil anlegen.
	 */
	@Override
	public Suchprofil createSuchprofil(String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException {

		Suchprofil s = new Suchprofil();
		s.setSuchprofilName(suchprofilName);
		s.setGeschlecht(geschlecht);
		s.setAlterMinInt(alterMinInt);
		s.setAlterMaxInt(alterMaxInt);
		s.setKoerpergroesseInt(koerpergroesseInt);
		s.setHaarfarbe(haarfarbe);
		s.setRaucher(raucher);
		s.setReligion(religion);

		profil.setProfilId(profil.getProfilId());

		return this.suchprofilMapper.insertSuchprofil(s, profil);
	}

	/**
	 * Suchprofil aktualisieren.
	 */
	public void saveSuchprofil(int profilId, String suchprofilName, String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException {

		Suchprofil s = new Suchprofil();
		s.setProfilId(profilId);
		s.setSuchprofilName(suchprofilName);
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
	 * Suchprofil lÃ¶schen.
	 */
	public void deleteSuchprofil(String suchprofilName) throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profil.getProfilId(), suchprofilName);
	}

	/**
	 * Alle Suchprofile auslesen. (EVTL. NICHT NOTWENDIG)
	 */
	public List<Suchprofil> getAllSuchprofile() throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofile();
	}

	/**
	 * Alle Suchprofile EINES NUTZERS auslesen. (ÜBERARBEITET VON MILENA -
	 * NOTWENIG)
	 */
	public List<Suchprofil> getAllSuchprofileFor() throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(profil.getProfilId());

	}

	/**
	 * Suchprofil anhand der Profil-ID UND des Namens auslesen. (ÃœBERARBEITET
	 * VON MILENA - NOTWENDIG)
	 */
	public Suchprofil getSuchprofilByName(String suchprofilName) throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilByName(profil.getProfilId(), suchprofilName);
	}

	/**
	 * Existenz des Suchprofilnamens beim Anlegen Ã¼berprÃ¼fen.
	 */
	public int pruefeSuchprofilname(String suchprofilname) throws IllegalArgumentException {
		return this.suchprofilMapper.pruefeSuchprofilname(profil.getProfilId(), suchprofilname);
	}

	/**
	 * Existenz des Suchprofilnamens beim Editieren Ã¼berprÃ¼fen.
	 */
	public String pruefeSuchprofilnameEdit(int suchprofilId) throws IllegalArgumentException {
		return this.suchprofilMapper.pruefeSuchprofilnameEdit(profil.getProfilId(), suchprofilId);
	}

	public Suchprofil getSuchprofilById(int suchprofilId) throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilById(suchprofilId);

	}

	public List<Suchprofil> getAllSuchprofileFor(Nutzerprofil n) throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(n);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Suchprofil
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Merkliste
	 * *************************************************************************
	 * **
	 */

	// Alle Vermerke eines Nutzerprofils auslesen.
	public Merkliste getGemerkteNutzerprofileFor() throws IllegalArgumentException {

		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		result = this.merklisteMapper.findGemerkteNutzerprofileFor(profil.getProfilId());

		Merkliste gemerkteNutzerprofile = new Merkliste();

		gemerkteNutzerprofile.setGemerkteNutzerprofile(result);

		return gemerkteNutzerprofile;
	}

	// Vermerkstatus ermitteln.
	public int getVermerkstatus(int fremdprofilId) throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profil.getProfilId(), fremdprofilId);
	}

	// Vermerk einfÃ¼gen.
	public void vermerkSetzen(int fremdprofilId) throws IllegalArgumentException {
		this.merklisteMapper.insertVermerk(profil.getProfilId(), fremdprofilId);
	}

	// Vermerk lÃ¶schen.
	public void vermerkLoeschen(int fremdprofilId) throws IllegalArgumentException {
		this.merklisteMapper.deleteVermerk(profil.getProfilId(), fremdprofilId);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Merkliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Sperrliste
	 * *************************************************************************
	 * **
	 */
	// Alle Sperrungen eines Nutzerprofils auslesen.
	public Sperrliste getGesperrteNutzerprofileFor() throws IllegalArgumentException {

		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		result = this.sperrlisteMapper.findGesperrteNutzerprofileFor(profil.getProfilId());

		Sperrliste gesperrteNutzerprofile = new Sperrliste();

		gesperrteNutzerprofile.setGesperrteNutzerprofile(result);

		return gesperrteNutzerprofile;
	}

	// PrÃ¼fen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int getSperrstatusFremdprofil(int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profil.getProfilId(), fremdprofilId);
	}

	// PrÃ¼fen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profil.getProfilId(), fremdprofilId);
	}

	// Sperrung einfÃ¼gen.
	public void sperrungSetzen(int fremdprofilId) throws IllegalArgumentException {
		this.sperrlisteMapper.insertSperrung(profil.getProfilId(), fremdprofilId);
	}

	// Sperrung lÃ¶schen.
	public void sperrungLoeschen(int fremdprofilId) throws IllegalArgumentException {
		this.sperrlisteMapper.deleteSperrung(profil.getProfilId(), fremdprofilId);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlÃ¤ge
	 * *************************************************************************
	 * **
	 */

	// Alle unangesehenen Nutzerprofile auslesen.
	public List<Nutzerprofil> getUnangeseheneNutzerprofile() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profil.getProfilId());
	}

	// Besuch setzen.
	public void besuchSetzen(int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profil.getProfilId(), fremdprofilId);
	}

	/**
	 * Methode zur Berechnung der Ähnlichkeit zwischen zwei Nutzerprofilen
	 */
	public int berechneAehnlichkeitNpFor(int fremdprofilId) throws IllegalArgumentException {

		// Erforderliche Daten abrufen
		Nutzerprofil referenzprofil = nutzerprofilMapper.findByNutzerprofilId(profil.getProfilId());
		Nutzerprofil vergleichsprofil = nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
		List<Info> referenzinfo = infoMapper.findAllInfosNeu(profil.getProfilId());
		List<Info> vergleichsinfo = infoMapper.findAllInfosNeu(fremdprofilId);

		// Variablen zur Berechnung der Aehnlichkeit
		int aehnlichkeit = 3;
		int counter = 7;

		// Vergleich der Profildaten
		if (referenzprofil.getGeschlecht().equals(vergleichsprofil.getGeschlecht())) {
			aehnlichkeit = aehnlichkeit - 3;
		}

		if (referenzprofil.getHaarfarbe().equals(vergleichsprofil.getHaarfarbe())) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		if (referenzprofil.getKoerpergroesseInt() == vergleichsprofil.getKoerpergroesseInt()) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		if (referenzprofil.getRaucher().equals(vergleichsprofil.getRaucher())) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		if (referenzprofil.getReligion().equals(vergleichsprofil.getReligion())) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		// Vergleich der Beschreibungsinfos
		for (Info rin : referenzinfo) {
			for (Info vin : vergleichsinfo) {
				if (rin.getEigenschaftId() == vin.getEigenschaftId()) {
					counter++;
					if (rin.getInfotext().equals(vin.getInfotext())) {
						aehnlichkeit = aehnlichkeit + 1;

					}
				}
			}
		}

		// Berechnung der Aehnlichkeit
		aehnlichkeit = aehnlichkeit * (100 / counter);

		// Rückgabewert
		return aehnlichkeit;

	}

	/**
	 * Aehnlichkeit setzen
	 */
	public void aehnlichkeitSetzen(int fremdprofilId, int aehnlichkeit) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertAehnlichkeit(profil.getProfilId(), fremdprofilId, aehnlichkeit);

	}

	/**
	 * Aehnlichkeit entfernen
	 */
	public void aehnlichkeitEntfernen() throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteAehnlichkeit(profil.getProfilId());
	}

	/**
	 * Methode zur Ausgabe einer Liste von Partnervorschlaegen (Unangesehene
	 * Profile, von denen man nicht gesperrt wurde, geordnet nach Aehnlichkeit)
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeNp(profil.getProfilId());

	}

	public int berechneAehnlichkeitSpFor(int suchprofilId, int fremdprofilId) throws IllegalArgumentException {

		Suchprofil referenzprofil = suchprofilMapper.findSuchprofilById(suchprofilId);
		Nutzerprofil vergleichsprofil = nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
		List<Info> referenzinfo = infoMapper.findAllInfosNeu(suchprofilId);
		List<Info> vergleichsinfo = infoMapper.findAllInfosNeu(fremdprofilId);

		int aehnlichkeitSp = 0;

		int counter = 100;

		if (referenzprofil.getGeschlecht().equals(vergleichsprofil.getGeschlecht())) {
			aehnlichkeitSp = aehnlichkeitSp + 40;
		}

		if (referenzprofil.getHaarfarbe().equals(vergleichsprofil.getHaarfarbe())) {
			aehnlichkeitSp = aehnlichkeitSp + 10;
		}

		if (referenzprofil.getKoerpergroesseInt() == vergleichsprofil.getKoerpergroesseInt()) {
			aehnlichkeitSp = aehnlichkeitSp + 10;
		}

		if (referenzprofil.getRaucher().equals(vergleichsprofil.getRaucher())) {
			aehnlichkeitSp = aehnlichkeitSp + 20;
		}

		if (referenzprofil.getReligion().equals(vergleichsprofil.getReligion())) {
			aehnlichkeitSp = aehnlichkeitSp + 20;
		}

		// Vergleich der Infos
		for (Info rin : referenzinfo) {
			for (Info vin : vergleichsinfo) {
				if (rin.getEigenschaftId() == vin.getEigenschaftId()) {
					counter = counter + 10;
					if (rin.getInfotext().equals(vin.getInfotext())) {
						aehnlichkeitSp = aehnlichkeitSp + 10;

					}
				}
			}

		}

		aehnlichkeitSp = (counter / 100 * aehnlichkeitSp);

		return aehnlichkeitSp;

	}

	// Alle Nutzerprofile die mich nicht gesperrt haben auslesen.
	public List<Nutzerprofil> getNutzerprofileOhneGesetzteSperrung() throws IllegalArgumentException {
		return this.nutzerprofilMapper.findNutzerprofileOhneGesetzeSperrung(profil.getProfilId());
	}

	public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(String suchprofilName) throws IllegalArgumentException {

		return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeSp(profil.getProfilId(), suchprofilName);

	}

	public void aehnlichkeitSetzenSp(int suchprofilId, String suchprofilName, int fremdprofilId, int aehnlichkeitSp)
			throws IllegalArgumentException {
		this.suchprofilMapper.insertAehnlichkeit(profil.getProfilId(), suchprofilId, suchprofilName, fremdprofilId,
				aehnlichkeitSp);
	}

	public void aehnlichkeitEntfernenSp() throws IllegalArgumentException {
		this.suchprofilMapper.deleteAehnlichkeitSp(profil.getProfilId());
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlÃ¤ge
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */

	public List<Eigenschaft> getAllEigenschaftenNeu() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenNeu();
	}	
	
	
	public List<Eigenschaft> getAllUnusedEigenschaftenNeu() throws IllegalArgumentException {
		List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
		listE = this.infoMapper.findAllUnusedEigenschaftenNeu(profil.getProfilId());
		System.out.println(listE);
		return listE;
	}
		
	
	public List<String> getAllInfosNeu() throws IllegalArgumentException {

		List<String> list1 = new ArrayList<String>();
		List<Info> result = new ArrayList<Info>();

		result = this.infoMapper.findAllInfosNeu(profil.getProfilId());

		for (Info i : result) {

			int eigenschaftId = i.getEigenschaftId();

			Eigenschaft e = new Eigenschaft();
			e = this.infoMapper.findEigenschaftByIdNeu(eigenschaftId);

			list1.add(String.valueOf(i.getProfilId()));
			list1.add(String.valueOf(eigenschaftId));
			list1.add(e.getErlaeuterung());
			list1.add(String.valueOf(i.getInfotext()));
			list1.add(e.getTyp());
		}
//		System.out.println(list1);
		return list1;
	}

	public List<String> getAllInfosNeuSp(int suchprofilId) throws IllegalArgumentException {

		List<String> list1 = new ArrayList<String>();
		List<Info> result = new ArrayList<Info>();

		result = this.infoMapper.findAllInfosNeu(suchprofilId);

		for (Info i : result) {

			int eigenschaftId = i.getEigenschaftId();

			Eigenschaft e = new Eigenschaft();
			e = this.infoMapper.findEigenschaftByIdNeu(eigenschaftId);

			list1.add(String.valueOf(i.getProfilId()));
			list1.add(String.valueOf(eigenschaftId));
			list1.add(e.getErlaeuterung());
			list1.add(String.valueOf(i.getInfotext()));
			list1.add(e.getTyp());
		}
//		System.out.println(list1);
		return list1;
	}

	public List<Info> getAllInfosNeuReport() throws IllegalArgumentException {
		return this.infoMapper.findAllInfosNeu(profil.getProfilId());
	}

	public Info createInfoNeu(int eigenschaftId, String infotext) throws IllegalArgumentException {

		Info i = new Info();
		i.setProfilId(profil.getProfilId());
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);

		return this.infoMapper.insertInfoNeu(i);
	}

	public Info createInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext) throws IllegalArgumentException {

		Info i = new Info();
		i.setProfilId(suchprofilId);
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);

		return this.infoMapper.insertInfoNeu(i);
	}

	public void deleteAllInfosNeu() throws IllegalArgumentException {
		this.infoMapper.deleteAllInfosNeu(profil.getProfilId());
	}

	public void deleteAllInfosNeuSp(int suchprofilId) throws IllegalArgumentException {
		this.infoMapper.deleteAllInfosNeu(suchprofilId);
	}

	public void deleteOneInfoNeu(int eigenschaftId) throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(profil.getProfilId(), eigenschaftId);
		System.out.println(profil.getProfilId() + ", " + eigenschaftId);
	}

	public void deleteOneInfoNeuSp(int suchprofilId, int eigenschaftId) throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(suchprofilId, eigenschaftId);
		System.out.println(suchprofilId + ", " + eigenschaftId);
	}

	public Beschreibungseigenschaft getEigBById(int eigenschaftId) throws IllegalArgumentException {
		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();
		eigB = this.infoMapper.findEigBByIdNeu(eigenschaftId);
		System.out.println(eigB.getBeschreibungstext());

		return eigB;
		// return this.infoMapper.findEigBByIdNeu(eigenschaftId);
	}

	public Auswahleigenschaft getEigAById(int eigenschaftId) throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAByIdNeu(eigenschaftId);
//		System.out.println(optionen.getOptionen());

		return optionen;
		// return this.infoMapper.findEigAById(eigenschaftId);
	}

	public void saveInfoNeu(int eigenschaftId, String infotext) throws IllegalArgumentException {

		System.out.println(profil.getProfilId() + ", " + eigenschaftId + ", " + infotext);

		Info i = new Info();
		i.setProfilId(profil.getProfilId());
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);

		this.infoMapper.updateInfosNeu(i);

	}

	public void saveInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext) throws IllegalArgumentException {

		System.out.println(suchprofilId + ", " + eigenschaftId + ", " + infotext);

		Info i = new Info();
		i.setProfilId(suchprofilId);
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);

		this.infoMapper.updateInfosNeu(i);

	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */

	@Override
	public boolean isUserRegistered(String userEmail) {
		return false;
	}

	public Nutzerprofil login(String requestUri) throws Exception {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Nutzerprofil n = new Nutzerprofil();
		if (user != null) {

			// EXISTING PROFILE
			Nutzerprofil bestehendesProfil = NutzerprofilMapper.nutzerprofilMapper()
					.findByNutzerprofilMitEmail(user.getEmail());
			if (bestehendesProfil != null) {
				n.setLoggedIn(true);
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService.createLogoutURL(requestUri));
				bestehendesProfil.setEmailAddress(user.getEmail());

				ClientsideSettings.setAktuellerUser(bestehendesProfil);
				return bestehendesProfil;
			} // NO PROFILE

			n.setLoggedIn(true);
			n.setEmailAddress(user.getEmail());

		} else { // USER = NULL
			n.setLoggedIn(false);

		}
		n.setLoginUrl(userService.createLoginURL(requestUri));
		return n;
	}

}
