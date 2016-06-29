package de.hdm.gruppe7.partnerboerse.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.google.appengine.api.users.User;
//import com.google.appengine.api.users.UserService;
//import com.google.appengine.api.users.UserServiceFactory;
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;



import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.client.Partnerboerse;
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
 * @see PartnerboerseAdministration
 * @see PartnerboerseAdministrationAsync
 * @see RemoteServiceServlet
 */
@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
		implements PartnerboerseAdministration {

	/**
	 * Referenz auf die Mapper, die Objekte mit der Datenbank abgleichen. 
	 */
	private NutzerprofilMapper nutzerprofilMapper = null;
	private SuchprofilMapper suchprofilMapper = null;
	private MerklisteMapper merklisteMapper = null;
	private SperrlisteMapper sperrlisteMapper = null;
	private InfoMapper infoMapper = null;

	/**
	 * No-Argument-Konstruktor, der dazu dient, Client-seitig ein RemoteServiceServlet 
	 * GWT.create(Klassenname.class) zu erzeugen. 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialisierungsmethode. 
	 * @throws IllegalArgumentException
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
	 * Gibt das aktuelle Profil anhand der EMail zur�ck
	 * return nutzerprofil
	 */
	@Override
	public Nutzerprofil getNuterprofilByEmail (String email) {
		return nutzerprofilMapper.findByNutzerprofilMitEmail(email);

	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Beginn: Nutzerprofil
	 * *************************************************************************
	 * **
	 */
	/**
	 * Pruefen, ob der Nutzer in der Datenbank schon existiert.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration#login(String)
	 */
	public boolean pruefeObNutzerNeu(String userEmail) throws IllegalArgumentException {

		if (nutzerprofilMapper.findByNutzerprofilMitEmail(userEmail) == null) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * Ein Nutzerprofil-Objekt anlegen.
	 * @param vorname Vorname.
	 * @param nachname Nachname.
	 * @param geschlecht Geschlecht. 
	 * @param geburtsdatumDate Geburtsdatum.
	 * @param koerpergroesseInt Koerpergroesse.
	 * @param haarfarbe Haarfarbe.
	 * @param raucher Raucherstatus.
	 * @param religion Religion.
	 * @param emailAddress E-Mail.
	 * @return Das angelegte Nutzerprofil-Objekt.
	 * @throws IllegalArgumentException
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
	 * Ein Nutzerprofil-Objekt aktualisieren.
	 * @param profilId Profil-ID des Nutzerprofils, das aktualisiert werden soll.
	 * @param vorname Vorname.
	 * @param nachname Nachname.
	 * @param geschlecht Geschlecht.
	 * @param geburtsdatumDate Geburtsdatum.
	 * @param koerpergroesseInt Koerpergroesse.
	 * @param haarfarbe Haarfarbe.
	 * @param raucher Raucherstatus.
	 * @param religion Religion.
	 * @throws IllegalArgumentException
	 */
	public void saveNutzerprofil(int profilId, String vorname, String nachname,
			String geschlecht, Date geburtsdatumDate, int koerpergroesseInt,
			String haarfarbe, String raucher, String religion)
			throws IllegalArgumentException {

		Nutzerprofil n = new Nutzerprofil();
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
	 * Ein Nutzerprofil-Objekt loeschen.
	 * @param profilId Die Profil-ID des Nutzerprofils, das geloescht werden soll.
	 * @throws IllegalArgumentException
	 */
	public void deleteNutzerprofil(int profilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteNutzerprofil(profilId);
	}

	/**
	 * Ein Nutzerprofil-Objekt anhand der Profil-ID auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, das ausgelesen werden soll. 
	 * @return Das ausgelesene Nutzerprofil-Objekt.
	 * @throws IllegalArgumentException
	 */
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}

	/**
	 * Ein Suchprofil-Objekt anlegen.
	 * @param profilId Profil-ID des Suchprofils, das angelegt werden soll. 
	 * @param suchprofilName Suchprofilname. 
	 * @param geschlecht Geschlecht. 
	 * @param alterMinInt Alter von. 
	 * @param alterMaxInt Alter bis. 
	 * @param koerpergroesseInt Koerpergroesse.
	 * @param haarfarbe Haarfarbe. 
	 * @param raucher Raucherstatus. 
	 * @param religion Religion. 
	 * @return Das angelegte Suchprofil-Objekt.
	 * @throws IllegalArgumentException
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
	 * Ein Suchprofil-Objekt aktualisieren.
	 * @param profilId Profil-ID Nutzerprofils, dessen Suchprofil aktualisiert werden soll.
	 * @param suchprofilId Profil-ID des Suchprofils, das aktualisiert werden soll. 
	 * @param suchprofilName Suchprofilname. 
	 * @param geschlecht Geschlecht. 
	 * @param alterMinInt Alter von.
	 * @param alterMaxInt Alter bis. 
	 * @param koerpergroesseInt Koerpergroesse.
	 * @param haarfarbe Haarfarbe.
	 * @param raucher Raucherstatus.
	 * @param religion Religion.
	 * @throws IllegalArgumentException
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

	}

	/**
	 * Ein Suchprofil-Objekt loeschen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Suchprofil geloescht werden soll.
	 * @param suchprofilName Name des Suchprofils, das geloescht werden soll. 
	 * @throws IllegalArgumentException
	 */
	public void deleteSuchprofil(int profilId, String suchprofilName)
			throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profilId, suchprofilName);
	}

	/**
	 * Alle Suchprofil-Objekte eines Nutzerprofils auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Suchprofile ausgelesen werden sollen.
	 * @return Liste von ausgelesenen Suchprofil-Objekten.
	 * @throws IllegalArgumentException
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId) throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(profilId);
	}

	/**
	 * Ein Suchprofil-Objekt anhand der Profil-ID und anhand des Suchprofilnamens auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Suchprofil ausgelesen werden soll.
	 * @param suchprofilName Name des Suchprofils, das ausgelesen werden soll. 
	 * @return Das ausgelesene Suchprofil-Objekt.
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilByName(profilId,
				suchprofilName);
	}

	/**
	 * Ein Suchprofil-Objekt anhand Profil-ID auslesen.
	 * @param suchprofilId Profil-ID des Suchprofils, das ausgelesen werden soll. 
	 * @return Das ausgelesene Suchprofil-Objekt.
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilById(int suchprofilId) throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilById(suchprofilId);
	}

	/**
	 * Suchprofilname beim Anlegen eines Suchprofil-Objekts pruefen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Suchprofil geprueft werden soll. 
	 * @param suchprofilname Suchprofilname, der geprueft werden soll. 
	 * @return Status der angibt, ob der Suchprofilname bereits existiert oder leer ist. 
	 */
	public int pruefeSuchprofilnameCreate(int profilId, String suchprofilname)
			throws IllegalArgumentException {

		int existenz = this.suchprofilMapper.pruefeSuchprofilnameExistenz(
				profilId, suchprofilname);

		int ergebnis = 0;

		if (existenz == 1) {
			ergebnis = 1;
		}

		if (suchprofilname.isEmpty()) {
			ergebnis = 2;
		}

		return ergebnis;
	}

	/**
	 * Suchprofilname beim Editieren eines Suchprofil-Objekts pruefen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Suchprofil geprueft werden soll.
	 * @param suchprofilId Profil-ID des Suchprofils, das geprueft werden soll. 
	 * @param suchprofilname Suchprofilname, der geprueft werden soll. 
	 * @return Status der angibt, ob der Suchprofilname bereits existiert oder leer ist.
	 * @throws IllegalArgumentException
	 */
	public int pruefeSuchprofilnameEdit(int profilId, int suchprofilId,
			String suchprofilname) throws IllegalArgumentException {

		int existenz = this.suchprofilMapper.pruefeSuchprofilnameExistenz(
				profilId, suchprofilname);
		String suchprofilnameAktuell = this.suchprofilMapper
				.findSuchprofilById(suchprofilId).getSuchprofilName();

		int ergebnis = 0;
		
		/**
		 * Diese If-Anweisung prueft, ob der Suchprofilname beim Editieren
		 * veraendert wurde und ob der eingegebene Suchprofilname bereits
		 * existiert. Wenn ja, wird die Ergebnis-Variable auf 1 gesetzt.
		 */
		if (existenz == 1 && (!suchprofilname.equals(suchprofilnameAktuell))) {
			ergebnis = 1;
		}
		/**
		 * Diese If-Anweisung prueft ob, der Suchprofilname leer ist. 
		 * Wenn ja, wird die Ergebnis-Variable auf 2 gesetzt.
		 */
		if (existenz == 0 && (suchprofilname.isEmpty())) {
			ergebnis = 2;
		}

		return ergebnis;
	}

	/**
	 * Alle gemerkten Nutzerprofile eines Nutzerprofils auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen gemerkte Nutzerprofile ausgelesen werden sollen.
	 * @return Das ausgelesene Merkliste-Objekt.
	 * @throws IllegalArgumentException
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
	 * @param profilId Die Profil-ID des eigenen Nutzerprofils.
	 * @param fremdprofilId Die Profil-ID des Nutzerprofils, das auf die Existenz eines Vermerks ueberprueft werden soll. 
	 * @return Status, der angibt, ob bereits ein Vermerk vorliegt oder nicht.
	 * @throws IllegalArgumentException
	 */
	public int pruefeVermerkstatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId);
	}

	/**
	 * Vermerkstatus aendern.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Profil-ID des Nutzerprofils, dessen Vermerkstatus geaendert werden soll. 
	 * @return Status, der angibt, ob ein Vermerk vorliegt oder nicht.
	 * @throws IllegalArgumentException
	 */
	public int vermerkstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException {

		int vermerkstatus = this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId);

		if (vermerkstatus == 1) {
			this.merklisteMapper.deleteVermerk(profilId, fremdprofilId);

		} else {
			this.merklisteMapper.insertVermerk(profilId, fremdprofilId);
		}

		return vermerkstatus;
	}

	/**
	 * Alle gesperrten Nutzerprofile eines Nutzerprofils auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen gesperrte Nutzerprofile ausgelesen werden sollen.
	 * @return Das ausgelesene Sperrliste-Objekt.
	 * @throws IllegalArgumentException
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
	 * Pruefen, ob ein Fremdprofil vom eigenen Nutzerprofil gesperrt wurde.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Profil-ID des Nutzerprofils, das auf die Existenz einer Sperrung ueberprueft werden soll. 
	 * @return Status, der angibt, ob eine Sperrung vorliegt oder nicht. 
	 * @throws IllegalArgumentException
	 */
	public int pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId,
				fremdprofilId);
	}

	/**
	 * Pruefen, ob das eigene Nutzerprofil von einem Fremdprofil gesperrt wurde.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Profil-ID des Nutzerprofils, das das eigene Nutzerprofil evtl. gesperrt hat. 
	 * @return Status, der angibt, ob eine Sperrung vorliegt oder nicht. 
	 * @throws IllegalArgumentException
	 */
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profilId,
				fremdprofilId);
	}

	/**
	 * Sperrstatus aendern. 
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Profil-ID des Nutzerprofils, dessen Sperrstatus geaendert werden soll. 
	 * @return Status, der angibt, ob nach der Aenderung eine Sperrung vorliegt oder nicht. 
	 * @throws IllegalArgumentException
	 */
	public int sperrstatusAendern(int profilId, int fremdprofilId)
			throws IllegalArgumentException {

		int sperrstatus = this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId, fremdprofilId);
		
		if (sperrstatus == 1) {
			this.sperrlisteMapper.deleteSperrung(profilId, fremdprofilId);
		} else {
			this.sperrlisteMapper.insertSperrung(profilId, fremdprofilId);
			this.merklisteMapper.deleteVermerk(profilId, fremdprofilId);
		}

		return sperrstatus;
	}

	/**
	 * Alle unangesehenen Nutzerprofile eines Nutzerprofils auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen unangesehenen Nutzerprofile ausgelesen werden sollen.
	 * @return Liste von ausgelesenen Nutzerprofil-Objekten.
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
	}

	/**
	 * Besuch setzen.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @param fremdprofilId Profil-ID des Nutzerprofils, fuer das ein Besuch gesetzt werden soll. 
	 * @throws IllegalArgumentException
	 */
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId);
	}

	/**
	 * Aehnlichkeit zwischen einem den Profildaten und Infos eines Nutzerprofil und den Profildaten 
	 * und Infos anderer Nutzerprofile ermitteln.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @throws IllegalArgumentException
	 */
	public void berechneAehnlichkeitNpFor(int profilId)
			throws IllegalArgumentException {

		/**
		 * Die bisher bestehenden Aehnlichkeiten werden aus der Datenbank entfernt, 
		 * damit sie neu berechnet und gespeichert werden koennen. So sind die Werte 
		 * immer aktuell, da z.B. Aenderungen im Nutzerprofil bei der Berechnung
		 * beruecksichtig werden.
		 */
		this.nutzerprofilMapper.deleteAehnlichkeit(profilId);
		
		/**
		 * Alle unangesehenen Nutzerprofile eines Nutzerprofils und das eigene Nutzerprofil auslesen. 
		 */
		List<Nutzerprofil> vergleichsprofile = nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
		Nutzerprofil referenzprofil = nutzerprofilMapper.findByNutzerprofilId(profilId);
		
		/**
		 * Die Profildaten des Referenzprofils mit den Profildaten des Vergleichsprofils vergleichen.
		 */
		for (Nutzerprofil np : vergleichsprofile) {

			int aehnlichkeit = 3;
			int counter = 10;
			int vergleichsprofilId = np.getProfilId();

			if (np.getGeschlecht().equals(referenzprofil.getGeschlecht())) {
				aehnlichkeit = aehnlichkeit - 3;
			}

			if (np.getHaarfarbe().equals(referenzprofil.getHaarfarbe())) {
				aehnlichkeit = aehnlichkeit + 1;
			}

			if (np.getKoerpergroesseInt() + 5 >= referenzprofil
					.getKoerpergroesseInt()) {
				if (np.getKoerpergroesseInt() - 5 <= referenzprofil
						.getKoerpergroesseInt()) {
					aehnlichkeit = aehnlichkeit + 1;
				}
			}

			if (np.getRaucher().equals(referenzprofil.getRaucher())) {
				aehnlichkeit = aehnlichkeit + 1;
			}

			if (np.getReligion().equals(referenzprofil.getReligion())) {
				aehnlichkeit = aehnlichkeit + 1;
			}

			/**
			 * Das Geburtsdatum in ein Alter umrechnen. 
			 */
			GregorianCalendar geburtstagVgl = new GregorianCalendar();
			geburtstagVgl.setTime(referenzprofil.getGeburtsdatumDate());
			GregorianCalendar heute = new GregorianCalendar();
			int alter = heute.get(Calendar.YEAR)
					- geburtstagVgl.get(Calendar.YEAR);
			if (heute.get(Calendar.MONTH) < geburtstagVgl.get(Calendar.MONTH)) {
				alter = alter - 1;
			} else if (heute.get(Calendar.MONTH) == geburtstagVgl
					.get(Calendar.MONTH)) {
				if (heute.get(Calendar.DATE) <= geburtstagVgl
						.get(Calendar.DATE)) {
					alter = alter - 1;
				}
			}

			GregorianCalendar geburtstagRef = new GregorianCalendar();
			geburtstagRef.setTime(np.getGeburtsdatumDate());
			GregorianCalendar heute1 = new GregorianCalendar();
			int alterRef = heute1.get(Calendar.YEAR)
					- geburtstagRef.get(Calendar.YEAR);
			if (heute1.get(Calendar.MONTH) < geburtstagRef.get(Calendar.MONTH)) {
				alterRef = alterRef - 1;
			} else if (heute1.get(Calendar.MONTH) == geburtstagRef
					.get(Calendar.MONTH)) {
				if (heute1.get(Calendar.DATE) <= geburtstagRef
						.get(Calendar.DATE)) {
					alterRef = alterRef - 1;
				}
			}

			if (alter + 5 >= alterRef) {
				if (alter - 5 <= alterRef) {
					aehnlichkeit = aehnlichkeit + 3;
				}
			}
			
			/**
			 * Infos Referenzprofils und der des Vergleichsprofils auslesen.
			 */
			List<Info> referenzinfo = infoMapper.findAllInfosNeu(profilId);
			List<Info> vergleichsinfo = infoMapper.findAllInfosNeu(vergleichsprofilId);
			
			/**
			 * Infos des Referenzprofils und des Vergleichsprofils vergleichen.
			 */
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

			/**
			 * Aehnlichkeit berechnen. 
			 */
			aehnlichkeit = aehnlichkeit * (100 / counter);
			
			/**
			 * Aehnlichkeit in die Datenbank einfuegen.
			 */
			nutzerprofilMapper.insertAehnlichkeit(profilId, vergleichsprofilId,
					aehnlichkeit);

		}

	}

	/**
	 * Geordnete Partnervorschlaege fuer ein Nutzerprofil auslesen. 
	 * Es werden nur diejenigen Nutzerprofile ausgelesen, von denen das eigene Nutzerprofil nicht gesperrt wurde. 
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @return Liste von ausgelesenen Nutzerprofil-Objekten.
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper
				.findGeordnetePartnervorschlaegeNp(profilId);

	}

	/**
	 * 
	 * Aehnlichkeit zwischen einem der Suchprofile eines Nutzerprofils und den Profildaten
	 * und Infos anderer Nutzerprofile berechnen.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @throws IllegalArgumentException
	 */
	public void berechneAehnlichkeitSpFor(int profilId)
			throws IllegalArgumentException {

		/**
		 * Die Aehnlichkeiten werden aus der Datenbank geloescht, damit sie neu
		 * berechnet und gespeichert werden k�nnen. So sind die Werte immer
		 * aktuell, da Aenderungen im z.B. Suchprofil in der Berechnung
		 * ber�cksichtig werden.
		 */
		this.suchprofilMapper.deleteAehnlichkeit(profilId);

		/**
		 * Alle Suchprofile des Nutzers werden ausgelesen und alle Nutzerprofile
		 * die gegen den Nutzer keine Sperrung gesetzt haben.
		 */
		List<Suchprofil> referenzprofil = suchprofilMapper
				.findAllSuchprofileFor(profilId);
		List<Nutzerprofil> vergleichsprofil = nutzerprofilMapper
				.findNutzerprofileOhneGesetzeSperrung(profilId);

		/**
		 * Vergleich der Profildaten eines Suchprofils mit den Profildaten eines
		 * Nutzerprofils. Es werden nur Nutzeprofile bedacht, die keine Sperrung
		 * gegen den Nutzer gesetzt haben. Sind im Suchprofil Infos mit
		 * "Keine Auswahl" gesetzt, hei�t dies dem Nutzer sind diese Angaben
		 * egal.
		 */
		for (Suchprofil sp : referenzprofil) {
			for (Nutzerprofil np : vergleichsprofil) {
				int aehnlichkeitSp = 0;
				int counter = 15;

				int suchprofilId = sp.getProfilId();
				int fremdprofilId = np.getProfilId();

				if (sp.getGeschlecht().equals("Keine Auswahl")) {
					aehnlichkeitSp = aehnlichkeitSp + 5;

				} else {

					if (sp.getGeschlecht().equals(np.getGeschlecht())) {
						aehnlichkeitSp = aehnlichkeitSp + 5;
					}
				}

				if (sp.getHaarfarbe().equals("Keine Auswahl")) {
					aehnlichkeitSp = aehnlichkeitSp + 1;

				} else {

					if (sp.getHaarfarbe().equals(np.getHaarfarbe())) {
						aehnlichkeitSp = aehnlichkeitSp + 1;
					}

				}

				if (sp.getKoerpergroesseInt() + 5 >= np.getKoerpergroesseInt()) {
					if (sp.getKoerpergroesseInt() - 5 <= np
							.getKoerpergroesseInt()) {
						aehnlichkeitSp = aehnlichkeitSp + 2;
					}
				}

				if (sp.getRaucher().equals("Keine Auswahl")) {
					aehnlichkeitSp = aehnlichkeitSp + 1;

				} else {

					if (sp.getRaucher().equals(np.getRaucher())) {
						aehnlichkeitSp = aehnlichkeitSp + 1;
					}

				}
				if (sp.getRaucher().equals("Keine Auswahl")) {
					aehnlichkeitSp = aehnlichkeitSp + 1;

				} else {

					if (sp.getReligion().equals(np.getReligion())) {
						aehnlichkeitSp = aehnlichkeitSp + 1;
					}

				}
				/**
				 * Umrechnung des Datums im Suchprofil in ein Alter.
				 */
				GregorianCalendar geburtstag = new GregorianCalendar();
				geburtstag.setTime(np.getGeburtsdatumDate());
				GregorianCalendar heute = new GregorianCalendar();
				int alter = heute.get(Calendar.YEAR)
						- geburtstag.get(Calendar.YEAR);
				if (heute.get(Calendar.MONTH) < geburtstag.get(Calendar.MONTH)) {
					alter = alter - 1;
				} else if (heute.get(Calendar.MONTH) == geburtstag
						.get(Calendar.MONTH)) {
					if (heute.get(Calendar.DATE) <= geburtstag
							.get(Calendar.DATE)) {
						alter = alter - 1;
					}
				}

				if (sp.getAlterMaxInt() >= alter) {
					if (sp.getAlterMinInt() <= alter) {
						aehnlichkeitSp = aehnlichkeitSp + 5;
					}
				}

				/**
				 * Auslesen aller Infos aus einem Suchproifl und einem
				 * Vergleichsprofil.
				 */
				List<Info> referenzinfo = infoMapper
						.findAllInfosNeu(suchprofilId);
				List<Info> vergleichsinfo = infoMapper
						.findAllInfosNeu(fremdprofilId);

				/**
				 * Vergleich der Infos des Suchprofils mit den Infos aus dem
				 * Vergleichsprofil.
				 */
				for (Info rin : referenzinfo) {
					for (Info vin : vergleichsinfo) {
						if (rin.getEigenschaftId() == vin.getEigenschaftId()) {
							counter = counter + 1;

							if (rin.getInfotext().equals("Keine Auswahl")) {

								aehnlichkeitSp = aehnlichkeitSp + 1;

							} else {

								if (rin.getInfotext().isEmpty()) {
									aehnlichkeitSp = aehnlichkeitSp + 1;

								} else {
									if (rin.getInfotext().equals(
											vin.getInfotext())) {
										aehnlichkeitSp = aehnlichkeitSp + 1;
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
				 * Damit Partnervorschlaege mit dem richtigen im Suchprofil
				 * angegebenen Geschlecht angezeigt werden, wird die
				 * Aehnlichkeit in der Datenbank nur gespeichert, wenn das
				 * Geschlecht des Vergleichprofils mit dem Geschlecht des
				 * Suchprofils uebereinstimmt.
				 */

				if (sp.getGeschlecht().equals(np.getGeschlecht())) {
					suchprofilMapper.insertAehnlichkeit(profilId, suchprofilId,
							fremdprofilId, aehnlichkeitSp);

				} else {
					/**
					 * Ist im Suchprofil das Geschlecht mit "Keine Auswahl"
					 * gesetzt, wird die Aehnlichkeit unabhaengig vom Geschlecht
					 * des Vergelichsprofil in der Datenbank gespeichert.
					 */
					if (sp.getGeschlecht().equals("Keine Auswahl")) {
						suchprofilMapper.insertAehnlichkeit(profilId,
								suchprofilId, fremdprofilId, aehnlichkeitSp);

					}
				}

			}
		}
	}

	/**
	 * Geordnete Partnervorschlaege fuer ein Nutzerprofil auslesen. 
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @param suchprofilName Name des Suchprofils, fuer das die Partnervorschlaege ausgelesen werden sollen.
	 * @return Liste von ausgelesenen Nutzerprofil-Objekten.
	 * @throws IllegalArgumentException
	 */
	public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilname)
			throws IllegalArgumentException {

		Suchprofil sp = this.suchprofilMapper.findSuchprofilByName(profilId,
				suchprofilname);

		int suchprofilId = sp.getProfilId();
		return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeSp(
				profilId, suchprofilId);
	}


	/**
	 * Alle Eigenschaften auslesen.
	 * @return Liste von ausgelesenen Beschreibungs- und Auswahleigenschaft-Objekten.
	 * @throws IllegalArgumentException
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

				eigB.setErlaeuterung(listE.get(i).getErlaeuterung());

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
	 * 
	 * Alle Beschreibungs- und Auswahleigenschaft-Objekte, die vom eigenen Nutzerprofil noch nicht als Info angelegt wurden, auslesen.
	 * @param profilId Profil-ID des eigenen Nutzerprofils. 
	 * @return Liste von ausgelesenen Beschreibungs- und Auswahleigenschaft-Objekten.
	 * @throws IllegalArgumentException
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
	 * Alle Auswahleigenschaft-Objekte inklusive aller Auswahloptionen auslesen.
	 * @param Liste von Eigenschaften. 
	 * @return Liste von ausgelesenen Auswahleigenschaft-Objekten.
	 * @throws IllegalArgumentException
	 */
	@Override
	public List<Auswahleigenschaft> getAuswahleigenschaften(List<Eigenschaft> listE) throws IllegalArgumentException {

		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();

		for (int i = 0; i < listE.size(); i++) {

			Auswahleigenschaft eigA = new Auswahleigenschaft();
			eigA = this.infoMapper.findEigAByIdNeu(listE.get(i).getEigenschaftId());

			listEigA.add(eigA);
		}
		return listEigA;
	}
	
	/**
	 * Auswahleigenschaft-Objekte anhand der Eigenschaft-ID auslesen.
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die ausgelesen werden soll. 
	 * @return Das ausgelesene Auswahleigenschaft-Objekt.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Auswahleigenschaft getEigAById(int eigenschaftId) throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAByIdNeu(eigenschaftId);

		return optionen;
	}
	
	/**
	 * Beschreibungseigenschaft-Objekte anhand der Eigenschaft-ID auslesen.
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die ausgelesen werden soll. 
	 * @return Das ausgelesene Beschreibungseigenschaft-Objekt.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Beschreibungseigenschaft getEigBById(int eigenschaftId) throws IllegalArgumentException {
		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();
		eigB = this.infoMapper.findEigBByIdNeu(eigenschaftId);

		return eigB;
	}
	
	/**
	 * Info-Objekte anlegen.
	 * @param profilId Die Profil-ID des Nutzerprofils, fuer das ein Info-Objekt angelegt werden soll. 
	 * @param infos Liste von Infos, die angelegt werden sollen.
	 * @return Liste von angelegten Info-Objekten.
	 * @throws IllegalArgumentException
	 */
	@Override
	public List<Info> createInfo(int profilId, List<Info> infos) throws IllegalArgumentException {

		return this.infoMapper.insertInfoNeu(profilId, infos);
	}

	/**
	 * Alle Info-Objekte eines Nutzerprofils auslesen.
	 * @param profilId Profil-ID des Nutzerprofils, fuer das die Infos ausgelesen werden sollen.
	 * @return Liste von ausgelesenen Info- und Eigenschaft-Objekten.
	 * @throws IllegalArgumentException
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
	 * Alle Info-Objekte eines Nutzerprofils loeschen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Infos geloescht werden sollen.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteAllInfosNeu(int profilId) throws IllegalArgumentException {

		this.infoMapper.deleteAllInfosNeu(profilId);
	}

	/**
	 * Ein Info-Objekt eines Nutzers loeschen.
	 * @param profilId Profil-ID des Nutzerprofils, dessen Info geloescht werden soll. 
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, deren zugehoerige Info geloescht werden soll. 
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(profilId, eigenschaftId);
	}

	/**
	 * Ein Info-Objekte aktualisieren. 
	 * @param profilId Profil-ID des Nutzerprofils, dessen Infos aktualisiert werden sollen.
	 * @param listI Liste von Info-Objekten, die aktualisiert werden sollen.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void saveInfo(int profilId, List<Info> listI) throws IllegalArgumentException {
		this.infoMapper.updateInfos(profilId, listI);
	}

	/**
	 * Ein Beschreibungseigenschaft-Objekt anlegen (Administrator-Funktion).
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die angelegt werden soll.
	 * @param erlaeuterung Erlaeuterung der Eigenschaft. 
	 * @param typ Typ der Eigenschaft. 
	 * @param beschreibungstext Beschreibungstext der Eigenschaft. 
	 * @return Das angelegte Beschreibungseigenschaft-Objekt.
	 * @throws IllegalArgumentException
	 */
	public Beschreibungseigenschaft createBeschreibungseigenschaft(int eigenschaftId, String erlaeuterung, String typ,
			String beschreibungstext) throws IllegalArgumentException {

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
	 * Ein Auswahleigenschaft-Objekt anlegen (Administrator-Funktion).
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die angelegt werden soll.
	 * @param erlaeuterung Erlaeuterung der Eigenschaft. 
	 * @param typ Typ der Eigenschaft. 
	 * @param auswahloption Auswahltoption der Eigenschaft. 
	 * @return Das angelegte Auswahleigenschaft-Objekt.
	 * @throws IllegalArgumentException
	 */
	public Auswahleigenschaft createAuswahleigenschaft(int eigenschaftId,
			String erleauterung, String typ, List<String> auswahloptionen)
			throws IllegalArgumentException {

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
	 * Ein Beschreibungseigenschaft-Objekt aktualisieren (Administrator-Funktion).
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die aktualisiert werden soll.
	 * @param erlaeuterung Erlaeuterung der Eigenschaft. 
	 * @param typ Typ der Eigenschaft. 
	 * @param beschreibungstext Beschreibungstext der Eigenschaft. 
	 * @throws IllegalArgumentException
	 */
	public void saveBeschreibungseigenschaft(int eigenschaftId,
			String erlaeuterung, String typ, String beschreibungstext)
			throws IllegalArgumentException {

		Beschreibungseigenschaft b = new Beschreibungseigenschaft();
		b.setErlaeuterung(erlaeuterung);
		b.setTyp(typ);
		b.setBeschreibungstext(beschreibungstext);

		b.setEigenschaftId(eigenschaftId);

		this.infoMapper.updateBeschreibungseigenschaft(b);
		;
	}

	/**
	 * Ein Auswahleigenschaft-Objekt aktualisieren (Administrator-Funktion).
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die aktualisiert werden soll.
	 * @param erlaeuterung Erlaeuterung der Eigenschaft. 
	 * @param typ Typ der Eigenschaft. 
	 * @param auswahloption Auswahltoption der Eigenschaft. 
	 * @throws IllegalArgumentException
	 */
	public void saveAuswahleigenschaft(int eigenschaftId, String erlaeuterung,
			String typ, List<String> auswahloptionen)
			throws IllegalArgumentException {

		Auswahleigenschaft a = new Auswahleigenschaft();
		a.setErlaeuterung(erlaeuterung);
		a.setTyp(typ);
		a.setOptionen(auswahloptionen);

		a.setEigenschaftId(eigenschaftId);

		this.infoMapper.updateAuswahleigenschaft(a);
		;
	}

	/**
	 * Ein Beschreibungseigenschaft-Objekt loeschen.
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die geloescht werden soll. 
	 * @throws IllegalArgumentException
	 */
	public void deleteBeschreibungseigenschaft(int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteBeschreibungseigenschaft(eigenschaftId);
	}

	/**
	 * Ein Auswahleigenschaft-Objekt loeschen.
	 * @param eigenschaftId Eigenschaft-ID der Eigenschaft, die geloescht werden soll. 
	 * @throws IllegalArgumentException
	 */
	public void deleteAuswahleigenschaft(int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteAuswahleigenschaft(eigenschaftId);
	}

}
