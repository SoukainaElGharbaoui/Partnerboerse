package de.hdm.gruppe7.partnerboerse.server;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
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



/**
 * Implementierungsklasse des Interface PartnerboerseAdministration.
 * 
 * @see PartnerboerseAdministration
 * @see PartnerboerseAdministrationAsync
 * @see RemoteServiceServlet
 * @author dunja
 *
 */
@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
		implements PartnerboerseAdministration {

	private NutzerprofilMapper nutzerprofilMapper = null;
	private SuchprofilMapper suchprofilMapper = null;
	private MerklisteMapper merklisteMapper = null;
	private SperrlisteMapper sperrlisteMapper = null;
	private InfoMapper infoMapper = null;

	/**
	 * Dieser No-Argument-Konstruktor dient dazu, ein RemoteServiceServlet 
	 * durch GWT.create(Klassenname.class) clientseitig zu erzeugen. 
	 * Durch diese Instanzenmethode kann die Instanz initialisiert werden.
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {
	}
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#init()
	 * 
	 */
	@Override
	public void init() throws IllegalArgumentException {
		this.nutzerprofilMapper = NutzerprofilMapper.nutzerprofilMapper();
		this.suchprofilMapper = SuchprofilMapper.suchprofilMapper();
		this.merklisteMapper = MerklisteMapper.merklisteMapper();
		this.sperrlisteMapper = SperrlisteMapper.sperrlisteMapper();
		this.infoMapper = InfoMapper.infoMapper();
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Login
	 * *************************************************************************
	 * **
	 */

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#isUserRegistered(String)
	 */
	public boolean isUserRegistered(String userEmail) {
		return false;
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#login(String)
	 */
	public Nutzerprofil login(String requestUri) throws Exception {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Nutzerprofil n = new Nutzerprofil();
		if (user != null) {

			// EXISTING PROFILE
			Nutzerprofil bestehendesProfil = NutzerprofilMapper
					.nutzerprofilMapper().findByNutzerprofilMitEmail(
							user.getEmail());
			if (bestehendesProfil != null) {
				n.setLoggedIn(true);
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService
						.createLogoutURL(requestUri));
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
	
	
	public boolean pruefeObNutzerNeu(String userEmail) {
		
		Nutzerprofil np = this.nutzerprofilMapper.findByNutzerprofilMitEmail(userEmail);
		
		if (np == null) {
			return true;
		}
			
		else {
			return false;
		}
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Login
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */

	/**
	 * Anlegen eines neuen Nutzerprofils. Dies f�hrt implizit zu einem Speichern des
	 * neuen Nutzeprofils in der Datenbank.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #createNutzerprofil(String, String, String, Date, int, String, String, String, String)
	 */
	public Nutzerprofil createNutzerprofil(String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion,
			String emailAddress) throws IllegalArgumentException {

		Nutzerprofil n = new Nutzerprofil();
		n.setVorname(vorname);
		n.setNachname(nachname);
		n.setGeschlecht(geschlecht);
		n.setGeburtsdatumDate(geburtsdatumDate);
		n.setKoerpergroesseInt(koerpergroesseInt);
		n.setHaarfarbe(haarfarbe);
		n.setRaucher(raucher);
		n.setReligion(religion);
		n.setEmailAddress(emailAddress);

		n.setProfilId(1);
		
		n = this.nutzerprofilMapper.insertNutzerprofil(n);
		
		return n;
	}

	/**
	 * Nutzerprofil aktualisieren.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #saveNutzerprofil(int, String, String, String, Date, int, String, String, String)
	 */
	public void saveNutzerprofil(int profilId, String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {

		Nutzerprofil n = ClientsideSettings.getAktuellerUser();
		n.setVorname(vorname);
		n.setNachname(nachname);
		n.setGeschlecht(geschlecht);
		n.setGeburtsdatumDate(geburtsdatumDate);
		n.setKoerpergroesseInt(koerpergroesseInt);
		n.setHaarfarbe(haarfarbe);
		n.setRaucher(raucher);
		n.setReligion(religion);

		n.setProfilId(profilId);

		this.nutzerprofilMapper.updateNutzerprofil(n);
	}

	/**
	 * Nutzerprofil loeschen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteNutzerprofil(int)
	 */
	public void deleteNutzerprofil(int profilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteNutzerprofil(profilId);
	}

	/**
	 * Nutzerprofil anhand der Profil-ID auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getNutzerprofilById(int)
	 */
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}

	/**
	 * Fremdprofil anhand der Profil-ID auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getFremdprofilById(int)
	 */
	public Nutzerprofil getFremdprofilById(int fremdprofilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
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
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #createSuchprofil(int, String, String, int, int, int, String, String, String)
	 */
	public Suchprofil createSuchprofil(int profilId, String suchprofilName,
			String geschlecht, int alterMinInt, int alterMaxInt,
			int koerpergroesseInt, String haarfarbe, String raucher,
			String religion) throws IllegalArgumentException {

		Suchprofil s = new Suchprofil();
		s.setSuchprofilName(suchprofilName);
		s.setGeschlecht(geschlecht);
		s.setAlterMinInt(alterMinInt);
		s.setAlterMaxInt(alterMaxInt);
		s.setKoerpergroesseInt(koerpergroesseInt);
		s.setHaarfarbe(haarfarbe);
		s.setRaucher(raucher);
		s.setReligion(religion);

		return this.suchprofilMapper.insertSuchprofil(s, profilId);

	}

	/**
	 * Suchprofil aktualisieren.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration
	 * #saveSuchprofil(int, int, String, String, int, int, int, String, String, String)
	 */
	public void saveSuchprofil(int profilId, int suchprofilId,
			String suchprofilName, String geschlecht, int alterMinInt,
			int alterMaxInt, int koerpergroesseInt, String haarfarbe,
			String raucher, String religion) throws IllegalArgumentException {

		Suchprofil s = new Suchprofil();
		s.setProfilId(suchprofilId);
		s.setSuchprofilName(suchprofilName);
		s.setGeschlecht(geschlecht);
		s.setAlterMinInt(alterMinInt);
		s.setAlterMaxInt(alterMaxInt);
		s.setKoerpergroesseInt(koerpergroesseInt);
		s.setHaarfarbe(haarfarbe);
		s.setRaucher(raucher);
		s.setReligion(religion);

		this.suchprofilMapper.updateSuchprofil(s);
		this.suchprofilMapper.deleteAehnlichkeitSp(profilId);

	}

	/**
	 * Suchprofil loeschen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteSuchprofil(int, String)
	 */
	public void deleteSuchprofil(int profilId, String suchprofilName)
			throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profilId, suchprofilName);
	}

	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllSuchprofileFor(int)
	 */

	public List<Suchprofil> getAllSuchprofileFor(int profilId)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(profilId);

	}

	/**
	 * Suchprofil anhand der Profil-ID und des Suchprofilnamens auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getSuchprofilByName(int, String)
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilByName(profilId,
				suchprofilName);
	}
	
	/**
	 * Suchprofil anhand der Profil-ID und der Suchprofil-ID auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getSuchprofilById(int, int)
	 */
	
	public Suchprofil getSuchprofilById (int profilId, int suchprofilId)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilById(profilId,
				suchprofilId);		
	}
	
	/**
	 * Suchprofilname beim Anlegen eines Suchprofils pruefen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeSuchprofilnameCreate(int, String)
	 */
	public int pruefeSuchprofilnameCreate(int profilId, String suchprofilname)
			throws IllegalArgumentException {

		int existenz = this.suchprofilMapper.pruefeSuchprofilnameExistenz(
				profilId, suchprofilname);

		int ergebnis = 0;

		// Der Suchprofilname existiert bereits.
		if (existenz == 1) {
			ergebnis = 1;
		}

		// Der Suchprofilname ist leer.
		if (suchprofilname.isEmpty()) {
			ergebnis = 2;
		}

		return ergebnis;
	}

	/**
	 * Suchprofilname beim Editieren eines Suchprofils pruefen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeSuchprofilnameEdit(int, int, String)
	 */
	public int pruefeSuchprofilnameEdit(int profilId, int suchprofilId,
			String suchprofilname) throws IllegalArgumentException {

		int existenz = this.suchprofilMapper.pruefeSuchprofilnameExistenz(
				profilId, suchprofilname);
		String suchprofilnameAktuell = this.suchprofilMapper
				.getSuchprofilName(suchprofilId);

		int ergebnis = 0;

		// Der Suchprofilname wurde ver�ndert, es existiert jedoch bereits ein
		// gleichnamiges, anderes Suchprofil.
		if (existenz == 1 && (!suchprofilname.equals(suchprofilnameAktuell))) {
			ergebnis = 1;
		}

		// Der Suchprofilname existiert noch nicht, die TextBox ist jedoch leer.
		if (existenz == 0 && (suchprofilname.isEmpty())) {
			ergebnis = 2;
		}

		return ergebnis;
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

	/**
	 * Alle gemerkten Nutzerprofile eines Nutzers auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGemerkteNutzerprofileFor(int)
	 */
	public Merkliste getGemerkteNutzerprofileFor(int profilId)
			throws IllegalArgumentException {

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		result = this.merklisteMapper.findGemerkteNutzerprofileFor(profilId);

		Merkliste gemerkteNutzerprofile = new Merkliste();

		gemerkteNutzerprofile.setGemerkteNutzerprofile(result);

		return gemerkteNutzerprofile;
	}

	/**
	 * Vermerkstatus pruefen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeVermerkstatus(int, int)
	 */
	public int pruefeVermerkstatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId);
	}

	/**
	 * Vermerkstatus aendern.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#vermerkstatusAendern(int, int)
	 */
	public int vermerkstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException {

		int vermerkstatus = this.merklisteMapper.pruefeVermerk(profilId,
				fremdprofilId);

		if (vermerkstatus == 1) {
			this.merklisteMapper.deleteVermerk(profilId, fremdprofilId);

		} else {
			this.merklisteMapper.insertVermerk(profilId, fremdprofilId);
		}

		return vermerkstatus;
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

	/**
	 * Alle gesperrten Nutzerprofile eines Nutzers auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGesperrteNutzerprofileFor(int)
	 */
	public Sperrliste getGesperrteNutzerprofileFor(int profilId)
			throws IllegalArgumentException {

		List<Nutzerprofil> result = new ArrayList<Nutzerprofil>();

		result = this.sperrlisteMapper.findGesperrteNutzerprofileFor(profilId);

		Sperrliste gesperrteNutzerprofile = new Sperrliste();

		gesperrteNutzerprofile.setGesperrteNutzerprofile(result);

		return gesperrteNutzerprofile;
	}

	/**
	 * Pruefen, ob Fremdprofil von Nutzer gesperrt wurde.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#pruefeSperrstatusFremdprofil(int, int)
	 */
	public int pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId,
				fremdprofilId);
	}

	/**
	 * Pruefen, ob Nutzer von Fremdprofil gesperrt wurde.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getSperrstatusEigenesProfil(int, int)
	 */
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profilId,
				fremdprofilId);
	}

	/**
	 * Sperrstatus aendern.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#sperrstatusAendern(int, int)
	 */
	public int sperrstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException {

		int sperrstatus = this.sperrlisteMapper.pruefeSperrungFremdprofil(
				profilId, fremdprofilId);

		if (sperrstatus == 1) {
			this.sperrlisteMapper.deleteSperrung(profilId, fremdprofilId);
		} else {
			this.sperrlisteMapper.insertSperrung(profilId, fremdprofilId);
			this.merklisteMapper.deleteVermerk(profilId, fremdprofilId);
		}

		return sperrstatus;
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Sperrliste
	 * *************************************************************************
	 * **
	 */

	/*
	 * ************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlaegeNp
	 * *************************************************************************
	 * **
	 */

	/**
	 * Alle unangesehenen Nutzerprofile eines Nutzers auslesen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getUnangeseheneNutzerprofile(int)
	 */
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
	}

	/**
	 * Besuch setzen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#besuchSetzen(int, int)
	 */
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId);
	}

	/**
	 * Aehnlichkeit zwischen den Profildaten und Infos eines Nutzerprofils und
	 * den Profildaten und Infos anderer Nutzerprofilen berechnen.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#berechneAehnlichkeitNpFor(int)
	 */
	public void berechneAehnlichkeitNpFor(int profilId)
			throws IllegalArgumentException {
		
		this.nutzerprofilMapper.deleteAehnlichkeit(profilId);

		List<Nutzerprofil> vergleichsprofile = nutzerprofilMapper
				.findUnangeseheneNutzerprofile(profilId);
		Nutzerprofil referenzprofil = nutzerprofilMapper
				.findByNutzerprofilId(profilId);

		for (Nutzerprofil np : vergleichsprofile) {

			int aehnlichkeit = 3;
			int counter = 7;
			int vergleichsprofilId = np.getProfilId();

			if (np.getGeschlecht().equals(referenzprofil.getGeschlecht())) {
				aehnlichkeit = aehnlichkeit - 3;
			}

			if (np.getHaarfarbe().equals(referenzprofil.getHaarfarbe())) {
				aehnlichkeit = aehnlichkeit + 1;
			}
			
			if (np.getKoerpergroesseInt() +5 >= referenzprofil.getKoerpergroesseInt()) {
			if(np.getKoerpergroesseInt()-5 <= referenzprofil.getKoerpergroesseInt()){
				aehnlichkeit = aehnlichkeit + 1;
				}
			}

			if (np.getRaucher().equals(referenzprofil.getRaucher())) {
				aehnlichkeit = aehnlichkeit + 1;
			}

			if (np.getReligion().equals(referenzprofil.getReligion())) {
				aehnlichkeit = aehnlichkeit + 1;
			}
			
			// Berechnung des Alters des Fremdprofils
				 		GregorianCalendar geburtstagVgl = new GregorianCalendar();
				         geburtstagVgl.setTime(referenzprofil.getGeburtsdatumDate());
				         GregorianCalendar heute = new GregorianCalendar();
				         int alter = heute.get(Calendar.YEAR) - geburtstagVgl.get(Calendar.YEAR);
				         if (heute.get(Calendar.MONTH) < geburtstagVgl.get(Calendar.MONTH))
				         {
				             alter = alter - 1;
				         }
				         else if (heute.get(Calendar.MONTH) == geburtstagVgl.get(Calendar.MONTH)){
				             if (heute.get(Calendar.DATE) <= geburtstagVgl.get(Calendar.DATE)){
				                 alter = alter - 1;
				             }
				         }
				         // Berechnung des Alters des eigenen Profils
				         GregorianCalendar geburtstagRef = new GregorianCalendar();
				         geburtstagRef.setTime(np.getGeburtsdatumDate());
				         GregorianCalendar heute1 = new GregorianCalendar();
				         int alterRef = heute1.get(Calendar.YEAR) - geburtstagRef.get(Calendar.YEAR);
				         if (heute1.get(Calendar.MONTH) < geburtstagRef.get(Calendar.MONTH))
				         {
				             alterRef = alterRef - 1;
				         }
				         else if (heute1.get(Calendar.MONTH) == geburtstagRef.get(Calendar.MONTH))
				         {
				             if (heute1.get(Calendar.DATE) <= geburtstagRef.get(Calendar.DATE)){
				                 alterRef = alterRef - 1;
				             }
				         }
				         
				         if(alter+5 >= alterRef){
				        	if(alter-5 <= alterRef){
				        	  aehnlichkeit = aehnlichkeit +3;
				        	  }
				        	}

			List<Info> referenzinfo = infoMapper.findAllInfosNeu(profilId);
			List<Info> vergleichsinfo = infoMapper
					.findAllInfosNeu(vergleichsprofilId);

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

			aehnlichkeit = aehnlichkeit * (100 / counter);

			nutzerprofilMapper.insertAehnlichkeit(profilId, vergleichsprofilId,
					aehnlichkeit);

		}

	}


	/**
	 * Alle unangesehenen Partnervorschlaege fuer einen Nutzer auslesen. Es
	 * werden nur diejenigen Nutzerprofile ausgelesen, von denen der Nutzer
	 * nicht gesperrt wurde.
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGeordnetePartnervorschlaegeNp(int)
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper
				.findGeordnetePartnervorschlaegeNp(profilId);

	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlaegeNp
	 * *************************************************************************
	 * **
	 */

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: PartnervorschlaegeSp
	 * *************************************************************************
	 * **
	 */
	
	/**
	 * Aehnlichkeit zwischen einem Suchprofil eines Nutzers und den Profildaten 
	 * und Infos anderer Nutzerprofile berechnen. 
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#berechneAehnlichkeitSpFor(int)
	 */
	public void berechneAehnlichkeitSpFor(int profilId) throws IllegalArgumentException {
		
		/**
		 * Die Aehnlichkeiten werden aus der Datenbank geloescht, damit sie neu berechnet und gespeichert werden k�nnen.
		 * So sind die Werte immer aktuell, da Aenderungen im z.B. Suchprofil in der Berechnung ber�cksichtig werden. 
		 */
		this.suchprofilMapper.deleteAehnlichkeitSp(profilId);
		
		/**
		 * Alle Suchprofile des Nutzers werden ausgelesen und 
		 * alle Nutzerprofile die gegen den Nutzer keine Sperrung gesetzt haben.
		 */
		List<Suchprofil> referenzprofil = suchprofilMapper
				.findAllSuchprofileFor(profilId);
		List<Nutzerprofil> vergleichsprofil = nutzerprofilMapper.findNutzerprofileOhneGesetzeSperrung(profilId);
		
		/**
		 * Vergleich der Profildaten eines Suchprofils mit den Profildaten eines Nutzerprofils. 
		 * Es werden nur Nutzeprofile bedacht die keine Sperrung gegen den Nutzer gesetzt haben.
		 * Sind im Suchprofil Infos mit "Keine Auswahl" gesetzt, hei�t das dem Nutzer sind diese Angaben egal.
		 */
		for (Suchprofil sp : referenzprofil) {
			for (Nutzerprofil np : vergleichsprofil) {
				int aehnlichkeitSp = 0;
				int counter = 70;
				
				int suchprofilId = sp.getProfilId();
				int fremdprofilId = np.getProfilId();
				
				
				if(sp.getGeschlecht().equals("Keine Auswhal")){
					aehnlichkeitSp = aehnlichkeitSp + 30;
					
				} else {
				
					if (sp.getGeschlecht().equals(np.getGeschlecht())) {
					aehnlichkeitSp = aehnlichkeitSp + 30;
					}
				}
				
				if(sp.getHaarfarbe().equals("Keine Auswhal") ){				
					aehnlichkeitSp = aehnlichkeitSp + 10;
					
				} else {
					
					if (sp.getHaarfarbe().equals(np.getHaarfarbe())) {
					aehnlichkeitSp = aehnlichkeitSp + 10;
					}
					
				}
					
					if (sp.getKoerpergroesseInt()+5 >= np.getKoerpergroesseInt()) {
					if(sp.getKoerpergroesseInt()-5 <= np.getKoerpergroesseInt()){
					aehnlichkeitSp = aehnlichkeitSp + 10;
					}
					}
				
				
				if(sp.getRaucher().equals("Keine Auswahl")){
					aehnlichkeitSp = aehnlichkeitSp + 10;
					
				} else {
					
					if (sp.getRaucher().equals(np.getRaucher())) {
					aehnlichkeitSp = aehnlichkeitSp + 10;
					}
					
				}
				if (sp.getRaucher().equals("Keine Auswahl") ){
					aehnlichkeitSp = aehnlichkeitSp + 10;
					
				} else {
					
					if (sp.getReligion().equals(np.getReligion())) {
					aehnlichkeitSp = aehnlichkeitSp + 10;
					}
					
					
				}
				/**
				 * Umrechnung des Datums im Suchprofil in ein Alter.
				 */
				GregorianCalendar geburtstag = new GregorianCalendar();
				 		        geburtstag.setTime(np.getGeburtsdatumDate());
				 		        GregorianCalendar heute = new GregorianCalendar();
				 		        int alter = heute.get(Calendar.YEAR) - geburtstag.get(Calendar.YEAR);
				 		        if (heute.get(Calendar.MONTH) < geburtstag.get(Calendar.MONTH))
				 		        {
				 		            alter = alter - 1;
						        }
				 		        else if (heute.get(Calendar.MONTH) == geburtstag.get(Calendar.MONTH))
				 		        {
				 		            if (heute.get(Calendar.DATE) <= geburtstag.get(Calendar.DATE))
				 		            {
				 		                alter = alter - 1;
				 		            }
				 		        }
				 		        
				 		        if(sp.getAlterMaxInt() >= alter){
				 		        	if(sp.getAlterMinInt() <= alter){
				 		        		aehnlichkeitSp = aehnlichkeitSp +10;
				 		        	}
				 		        }
				
				
				
				/**
				 * Auslesen aller Infos aus einem Suchproifl und einem Vergleichsprofil.
				 */
				List<Info> referenzinfo = infoMapper
						.findAllInfosNeu(suchprofilId);
				List<Info> vergleichsinfo = infoMapper
						.findAllInfosNeu(fremdprofilId);
				
				/**
				 * Vergleich der Infos des Suchprofils mit den Infos aus dem Vergleichsprofil.
				 */
				for (Info rin : referenzinfo) {
					for (Info vin : vergleichsinfo) {
						if (rin.getEigenschaftId() == vin.getEigenschaftId()) {
							counter= counter + 2;
							
							if (rin.getInfotext().equals("Keine Auswahl") ){
								
								aehnlichkeitSp = aehnlichkeitSp + 2;
								
							} else {
								
								if (rin.getInfotext().isEmpty()){
									aehnlichkeitSp = aehnlichkeitSp + 2;
									
							} else {
								if (rin.getInfotext().equals(vin.getInfotext())) {
									aehnlichkeitSp = aehnlichkeitSp + 2;
								}
								}
								
							}
							
						}
					}
				}
				
				/**
				 * Berechnung des Prozentwertes.
				 */
				aehnlichkeitSp = aehnlichkeitSp * (100 / counter);
				
				/**
				 * Damit Partnervorschlaege mit dem richtigen, im Suchprofil angegebenen, Geschlecht angezeigt werden,
				 * wird die Aehnlichkeit in der Datenbank nur gespeichert,
				 * wenn das Geschlecht des Vergleichprofils mit dem Geschlecht des Suchprofils uebereinstimmt.
				 */
				
				if(sp.getGeschlecht().equals(np.getGeschlecht())){
					// Aehnlichkeit in die Datenbank setzen
						suchprofilMapper.insertAehnlichkeit(profilId,
						suchprofilId, fremdprofilId,
						aehnlichkeitSp);
									
				} else {
					/**
					 * Ist im Suchprofil das Geschlecht mit "Keine Auswahl" gesetzt,
					 *wird die Aehnlichkeit unabhaengig vom Geschlecht des Vergelichsprofil in der Datenbank gespeichert.
					 */
					if (sp.getGeschlecht().equals("Keine Auswahl")){
						// Aehnlichkeit in die Datenbank setzen
						suchprofilMapper.insertAehnlichkeit(profilId,
						suchprofilId, fremdprofilId,
						aehnlichkeitSp);
						
					}
				}
				
			}
		}
}


	/**
	 * Alle Partnervorschlaege anhand von Suchprofilen fuer einen Nutzer
	 * auslesen. Es werden nur die Nutzerprofile ausgelesen, von denen
	 * der Nutzer nicht gesperrt wurde.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getGeordnetePartnervorschlaegeSp(int, String)
	 */

	public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId,
			String suchprofilname) throws IllegalArgumentException {

		Suchprofil sp = this.suchprofilMapper.findSuchprofilByName(profilId,
				suchprofilname);

		int suchprofilId = sp.getProfilId();
		return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeSp(
				profilId, suchprofilId);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: PartnervorschlaegeSp
	 * *************************************************************************
	 * **
	 */
	
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Info
	 * *************************************************************************
	 * **
	 */

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllEigenschaften()
	 */
	@Override
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllEigenschaften()
			throws IllegalArgumentException {

		Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result = new HashMap<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>();

		List<Eigenschaft> listE = infoMapper.findAllEigenschaftenNeu();

		List<Beschreibungseigenschaft> listEigB = new ArrayList<Beschreibungseigenschaft>();
		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();

		for (int i = 0; i < listE.size(); i++) {

			if (listE.get(i).getTyp().equals("B")) {

				Beschreibungseigenschaft eigB = infoMapper
						.findEigBByIdNeu(listE.get(i).getEigenschaftId());

				eigB.setErlaeuterung(listE.get(i).erlaeuterung);

				listEigB.add(eigB);

			}

			else if (listE.get(i).getTyp().equals("A")) {

				Auswahleigenschaft eigA = new Auswahleigenschaft();
				eigA = this.infoMapper.findEigAByIdNeu(listE.get(i)
						.getEigenschaftId());

				eigA.setErlaeuterung(listE.get(i).getErlaeuterung());

				listEigA.add(eigA);

			}
		}

		result.put(listEigB, listEigA);
		return result;
	}


	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllUnusedEigenschaften(int)
	 */
	@Override
	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften(
			int profilId) throws IllegalArgumentException {

		Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result2 = new HashMap<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>();

		List<Eigenschaft> listE = infoMapper
				.findAllUnusedEigenschaftenNeu(profilId);

		List<Beschreibungseigenschaft> listEigB = new ArrayList<Beschreibungseigenschaft>();
		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();

		for (int i = 0; i < listE.size(); i++) {

			if (listE.isEmpty() == true) {
			}

			else {
				if (listE.get(i).getTyp().equals("B")) {

					Beschreibungseigenschaft eigB = infoMapper
							.findEigBByIdNeu(listE.get(i).getEigenschaftId());

					eigB.setErlaeuterung(listE.get(i).getErlaeuterung());
					eigB.setTyp(listE.get(i).getTyp());

					listEigB.add(eigB);
				}

				else if (listE.get(i).getTyp().equals("A")) {

					Auswahleigenschaft eigA = new Auswahleigenschaft();

					eigA = this.infoMapper.findEigAByIdNeu(listE.get(i)
							.getEigenschaftId());

					eigA.setErlaeuterung(listE.get(i).getErlaeuterung());
					eigA.setTyp(listE.get(i).getTyp());

					listEigA.add(eigA);
				}
			}
		}

		result2.put(listEigB, listEigA);
		return result2;
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllInfos(int)
	 */
	@Override
	public Map<List<Info>, List<Eigenschaft>> getAllInfos(int profilId)
			throws IllegalArgumentException {

		Map<List<Info>, List<Eigenschaft>> result = new HashMap<List<Info>, List<Eigenschaft>>();
		List<Info> listI = new ArrayList<Info>();
		List<Eigenschaft> listE = new ArrayList<Eigenschaft>();

		listI = this.infoMapper.findAllInfosNeu(profilId);

		for (Info i : listI) {

			Eigenschaft e = new Eigenschaft();
			e = this.infoMapper.findEigenschaftByIdNeu(i.getEigenschaftId());

			System.out.println(e.getErlaeuterung());

			listE.add(e);
		}

		result.put(listI, listE);
		return result;
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#createInfo(int, List)
	 */
	@Override
	public List<Info> createInfo(int profilId, List<Info> infos)
			throws IllegalArgumentException {

		return this.infoMapper.insertInfoNeu(profilId, infos);
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteAllInfosNeu(int)
	 */
	@Override
	public int deleteAllInfosNeu(int profilId) throws IllegalArgumentException {

		int ergebnis;

		this.infoMapper.deleteAllInfosNeu(profilId);

		Nutzerprofil np = this.nutzerprofilMapper
				.findByNutzerprofilId(profilId);

		if (np == null) {
			ergebnis = 1;
		}

		else {
			ergebnis = 0;
		}
		return ergebnis;
	}

	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteOneInfoNeu(int, int)
	 */
	@Override
	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(profilId, eigenschaftId);
	}

	
	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getEigBById(int)
	 */
	@Override
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException {
		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();
		eigB = this.infoMapper.findEigBByIdNeu(eigenschaftId);

		return eigB;
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAuswahleigenschaften(List)
	 */
	@Override
	public List<Auswahleigenschaft> getAuswahleigenschaften(
			List<Eigenschaft> listE) throws IllegalArgumentException {

		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();

		for (int i = 0; i < listE.size(); i++) {

			Auswahleigenschaft eigA = new Auswahleigenschaft();
			eigA = this.infoMapper.findEigAByIdNeu(listE.get(i)
					.getEigenschaftId());

			listEigA.add(eigA);
		}
		return listEigA;
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getEigAById(int)
	 */
	@Override
	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAByIdNeu(eigenschaftId);

		return optionen;
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#saveInfo(int, List)
	 */
	@Override
	public void saveInfo(int profilId, List<Info> listI)
			throws IllegalArgumentException {

		this.infoMapper.updateInfos(profilId, listI);
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getAllInfosNeuReport(int)
	 */
	@Override
	public List<Info> getAllInfosNeuReport(int profilId)
			throws IllegalArgumentException {
		return this.infoMapper.findAllInfosNeu(profilId);
	}

	/**
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#getEigenschaftstextById(int)
	 */
	@Override
	public String getEigenschaftstextById(int eigenschaftId)
			throws IllegalArgumentException {

		String erlaeuterung;
		erlaeuterung = this.infoMapper.findEigenschaftByIdNeu(eigenschaftId).getErlaeuterung();
		return erlaeuterung;
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Administrator-Funktionen
	 * *************************************************************************
	 * **
	 */
	
	/**
	 * Anlegen einer Beschreibungseigenschaft und das Speichern der Beschreibungseigenschaft in der Datenbank.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#createBeschreibungseigenschaft(int, String, String, String)
	 */
	
	public Beschreibungseigenschaft createBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext) throws IllegalArgumentException {

		Beschreibungseigenschaft b = new Beschreibungseigenschaft();
		b.setEigenschaftId(eigenschaftId);
		b.setErlaeuterung(erlaeuterung);
		b.setTyp(typ);
		b.setBeschreibungstext(beschreibungstext);
		

		b.setEigenschaftId(1);
		
		b = this.infoMapper.insertBeschreibungseigenschaft(b);
		
		return b;
	}
	
	/**
	 * Anlegen einer Auswahleigenschaft und das Speichern der Auswahleigenschaft in der Datenbank.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#createAuswahleigenschaft(int, String, String, List)
	 */
	public Auswahleigenschaft createAuswahleigenschaft(int eigenschaftId, String erleauterung,
			String typ, List<String>  auswahloptionen) throws IllegalArgumentException {

		Auswahleigenschaft a = new Auswahleigenschaft();
		a.setEigenschaftId(eigenschaftId);
		a.setErlaeuterung(erleauterung);
		a.setTyp(typ);
		a.setOptionen(auswahloptionen);
		

		a.setEigenschaftId(1);
		
		a = this.infoMapper.insertAuswahleigenschaft(a);
		
		return a;
	}
	
	
	/**
	 * Beschreibungseigenschaft-Objekt aktualisieren.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#saveBeschreibungseigenschaft(int, String, String, String)
	 */
	public void saveBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, String beschreibungstext)
			throws IllegalArgumentException {

		Beschreibungseigenschaft b = new Beschreibungseigenschaft();
		b.setErlaeuterung(erlaeuterung);
		b.setTyp(typ);
		b.setBeschreibungstext(beschreibungstext);
	
	

		b.setEigenschaftId(eigenschaftId);

		this.infoMapper.updateBeschreibungseigenschaft(b);;
	}
	
	/**
	 * Auswahleigenschaft-Objekt aktualisieren.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#saveAuswahleigenschaft(int, String, String, List)
	 */
	public void saveAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloptionen)
			throws IllegalArgumentException {

		Auswahleigenschaft a = new Auswahleigenschaft();
		a.setErlaeuterung(erlaeuterung);
		a.setTyp(typ);
		a.setOptionen(auswahloptionen);
	
	

		a.setEigenschaftId(eigenschaftId);

		this.infoMapper.updateAuswahleigenschaft(a);;
	}
	
	
	/**
	 * Beschreibungseigenschaft-Objekt loeschen.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteBeschreibungseigenschaft(int)
	 */
	public void deleteBeschreibungseigenschaft(int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteBeschreibungseigenschaft(eigenschaftId);
	}
	
	/**
	 * Auswahleigenschaft-Objekt loeschen.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#deleteAuswahleigenschaft(int)
	 */
	public void deleteAuswahleigenschaft(int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteAuswahleigenschaft(eigenschaftId);
	}
	
	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Administrator-Funktionen
	 * *************************************************************************
	 * **
	 */
}
//
