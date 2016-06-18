
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


@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
		implements PartnerboerseAdministration {

	private NutzerprofilMapper nutzerprofilMapper = null;
	private SuchprofilMapper suchprofilMapper = null;
	private MerklisteMapper merklisteMapper = null;
	private SperrlisteMapper sperrlisteMapper = null;
	private InfoMapper infoMapper = null;

	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {
	}

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
	 * Pruefen, ob der Nutzer eingeloggt ist.
	 */
	public boolean isUserRegistered(String userEmail) {
		return false;
	}

	/**
	 * URL zum Einloggen anfordern.
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
	 * Nutzerprofil anlegen.
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
	 */
	public void deleteNutzerprofil(int profilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.deleteNutzerprofil(profilId);
	}

	/**
	 * Nutzerprofil anhand der Profil-ID auslesen.
	 */
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}

	/**
	 * Fremdprofil anhand der Profil-ID auslesen.
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
	 */
	public void deleteSuchprofil(int profilId, String suchprofilName)
			throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profilId, suchprofilName);
	}

	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 */

	public List<Suchprofil> getAllSuchprofileFor(int profilId)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(profilId);

	}

	/**
	 * Suchprofil anhand der Profil-ID und des Suchprofilnamens auslesen.
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilByName(profilId,
				suchprofilName);
	}
	
	/**
	 * Suchprofil anhand der Profil-ID und der Suchprofil-ID auslesen.
	 */
	
	public Suchprofil getSuchprofilById (int profilId, int suchprofilId)
			throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilById(profilId,
				suchprofilId);		
	}
	
	/**
	 * Suchprofilname beim Anlegen eines Suchprofils pruefen.
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
	 */
	public int pruefeVermerkstatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId);
	}

	/**
	 * Vermerkstatus aendern.
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
	 */
	public int pruefeSperrstatusFremdprofil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId,
				fremdprofilId);
	}

	/**
	 * Pruefen, ob Nutzer von Fremdprofil gesperrt wurde.
	 */
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profilId,
				fremdprofilId);
	}

	/**
	 * Sperrstatus aendern.
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
	 */
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
			throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
	}

	/**
	 * Besuch setzen.
	 */
	public void besuchSetzen(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId);
	}

	/**
	 * Aehnlichkeit zwischen den Profildaten und Infos eines Nutzerprofils und
	 * den Profildaten und Infos anderer Nutzerprofilen berechnen.
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
	 */
	/**
	 * Aehnlichkeit zwischen einem Suchprofil eines Nutzers und den Profildaten 
	 * und Infos anderer Nutzerprofile berechnen. 
	 */
	/**
	 * Aehnlichkeit zwischen einem Suchprofil eines Nutzers und den Profildaten 
	 * und Infos anderer Nutzerprofile berechnen. 
	 */
	public void berechneAehnlichkeitSpFor(int profilId) throws IllegalArgumentException {
		
		this.suchprofilMapper.deleteAehnlichkeitSp(profilId);
		
		List<Suchprofil> referenzprofil = suchprofilMapper
				.findAllSuchprofileFor(profilId);
		List<Nutzerprofil> vergleichsprofil = nutzerprofilMapper.findNutzerprofileOhneGesetzeSperrung(profilId);
		
		// Vergleich der Profildaten von jeweils einem Suchprofil und einem Nutzerprofil
		for (Suchprofil sp : referenzprofil) {
			for (Nutzerprofil np : vergleichsprofil) {
				int aehnlichkeitSp = 0;
				int counter = 70;
				
				int suchprofilId = sp.getProfilId();
				int fremdprofilId = np.getProfilId();
				String suchprofilName = sp.getSuchprofilName();
				
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
				
				
				
				// Holen aller Infos des Suchprofils und Nuterprofils
				List<Info> referenzinfo = infoMapper
						.findAllInfosNeu(suchprofilId);
				List<Info> vergleichsinfo = infoMapper
						.findAllInfosNeu(fremdprofilId);
				
				// Vergleich der Infos
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
				
				// Berechnung des Prozentwertes
				aehnlichkeitSp = aehnlichkeitSp * (100 / counter);
				
				if(sp.getGeschlecht().equals(np.getGeschlecht())){
					// Aehnlichkeit in die Datenbank setzen
						suchprofilMapper.insertAehnlichkeit(profilId,
						suchprofilId, fremdprofilId,
						aehnlichkeitSp);
									
				} else {
					
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
	 * auslesen. Es werden nur diejenigen Nutzerprofile ausgelesen, von denen
	 * der Nutzer nicht gesperrt wurde.
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

	public List<String> getAllInfosNeuSp(int suchprofilId)
			throws IllegalArgumentException {

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
		return list1;
	}

	public int createInfo(int profilId, List<Info> infos)
			throws IllegalArgumentException {

		int ergebnis;

		this.infoMapper.insertInfoNeu(profilId, infos);

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

	public void deleteAllInfosNeuSp(int suchprofilId)
			throws IllegalArgumentException {
		this.infoMapper.deleteAllInfosNeu(suchprofilId);
	}

	public void deleteOneInfoNeu(int profilId, int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(profilId, eigenschaftId);
	}

	public void deleteOneInfoNeuSp(int suchprofilId, int eigenschaftId)
			throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(suchprofilId, eigenschaftId);
	}

	public Beschreibungseigenschaft getEigBById(int eigenschaftId)
			throws IllegalArgumentException {
		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();
		eigB = this.infoMapper.findEigBByIdNeu(eigenschaftId);

		return eigB;
	}

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

	public Auswahleigenschaft getEigAById(int eigenschaftId)
			throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAByIdNeu(eigenschaftId);

		return optionen;
	}

	public int saveInfo(int profilId, List<Info> listI)
			throws IllegalArgumentException {

		int ergebnis;

		this.infoMapper.updateInfos(profilId, listI);

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

	// public void saveInfoNeuSp(int suchprofilId, int eigenschaftId, String
	// infotext) throws IllegalArgumentException {
	//
	// System.out.println(suchprofilId + ", " + eigenschaftId + ", " +
	// infotext);
	//
	// Info i = new Info();
	// i.setProfilId(suchprofilId);
	// i.setEigenschaftId(eigenschaftId);
	// i.setInfotext(infotext);
	//
	// this.infoMapper.updateInfosNeu(i);
	// }

	@Override
	public List<Info> getAllInfosNeuReport(int profilId)
			throws IllegalArgumentException {
		return this.infoMapper.findAllInfosNeu(profilId);
	}

	@Override
	public String getEigenschaftstextById(int eigenschaftId)
			throws IllegalArgumentException {

		return this.infoMapper.findEigenschaftstextById(eigenschaftId);
	}

	/*
	 * *************************************************************************
	 * ** ABSCHNITT, Ende: Info
	 * *************************************************************************
	 * **
	 */
}