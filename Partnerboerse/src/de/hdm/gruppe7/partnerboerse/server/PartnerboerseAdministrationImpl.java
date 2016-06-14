package de.hdm.gruppe7.partnerboerse.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	Nutzerprofil profil = new Nutzerprofil();

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
			int koerpergroesseInt, String haarfarbe, String raucher, String religion)
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
		
		this.suchprofilMapper.deleteAehnlichkeitSp(profil.getProfilId()); 
	}

	/**
	 * Suchprofil loeschen.
	 */
	public void deleteSuchprofil(String suchprofilName) throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profil.getProfilId(), suchprofilName);
	}

	/**
	 * Alle Suchprofile eines Nutzers auslesen.
	 */
	public List<Suchprofil> getAllSuchprofileFor() throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(profil.getProfilId());
	}

	/**
	 * Suchprofil anhand anhand des Suchprofilnamens auslesen. 
	 */
	public Suchprofil getSuchprofilByName(String suchprofilName) throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilByName(profil.getProfilId(), suchprofilName);
	}
	
	/**
	 * Suchprofilname beim Anlegen eines Suchprofils ueberpruefen. 
	 */
	public int pruefeSuchprofilnameCreate(String suchprofilname) throws IllegalArgumentException {
		
		int existenz = this.suchprofilMapper.pruefeSuchprofilnameExistenz(profil.getProfilId(), suchprofilname);
		
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
	 * Suchprofilname beim Editieren eines Suchprofils ueberpruefen. 
	 */
	public int pruefeSuchprofilnameEdit(int suchprofilId, String suchprofilname) throws IllegalArgumentException {
		
		int existenz = this.suchprofilMapper.pruefeSuchprofilnameExistenz(profil.getProfilId(), suchprofilname);
		String suchprofilnameAktuell = this.suchprofilMapper.getSuchprofilName(profil.getProfilId(), suchprofilId); 
		
		int ergebnis = 0; 
		
		// Der Suchprofilname wurde verändert, es existiert jedoch bereits ein gleichnamiges, anderes Suchprofil.
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

	// Alle Vermerke eines Nutzerprofils auslesen.
	public Merkliste getGemerkteNutzerprofileFor() throws IllegalArgumentException {

		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		result = this.merklisteMapper.findGemerkteNutzerprofileFor(profil.getProfilId());

		Merkliste gemerkteNutzerprofile = new Merkliste();

		gemerkteNutzerprofile.setGemerkteNutzerprofile(result);

		return gemerkteNutzerprofile;
	}

	// Vermerkstatus ermitteln.
	public int pruefeVermerkstatus(int fremdprofilId) throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profil.getProfilId(), fremdprofilId);
	}

	// Vermerkstatus aendern.
	public int vermerkstatusAendern(int fremdprofilId)
			throws IllegalArgumentException {
	
		// Vermerkstatus ermitteln.
		int vermerkstatus = this.merklisteMapper.pruefeVermerk(profil.getProfilId(),
				fremdprofilId);

		if (vermerkstatus == 1) {
			// Wenn ein Vermerk vorliegt, wird dieser gelöscht.
			this.merklisteMapper.deleteVermerk(profil.getProfilId(), fremdprofilId);
		} else {
			// Wenn kein Vermerk vorliegt, wird ein Vermerk gesetzt.
			this.merklisteMapper.insertVermerk(profil.getProfilId(), fremdprofilId);
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

	// Alle Sperrungen eines Nutzerprofils auslesen.
	public Sperrliste getGesperrteNutzerprofileFor() throws IllegalArgumentException {

		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		result = this.sperrlisteMapper.findGesperrteNutzerprofileFor(profil.getProfilId());

		Sperrliste gesperrteNutzerprofile = new Sperrliste();

		gesperrteNutzerprofile.setGesperrteNutzerprofile(result);

		return gesperrteNutzerprofile;
	}

	// Pruefen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int pruefeSperrstatusFremdprofil(int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profil.getProfilId(), fremdprofilId);
	}

	// Pruefen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profil.getProfilId(), fremdprofilId);
	}

	// Sperrstatus aendern.
	public int sperrstatusAendern(int fremdprofilId) throws IllegalArgumentException {

		// Sperrstatus ermitteln. 
		int sperrstatus = this.sperrlisteMapper.pruefeSperrungFremdprofil(profil.getProfilId(), fremdprofilId);

		if (sperrstatus == 1) {
			// Wenn eine Sperrung vorliegt, wird diese gelöscht.
			this.sperrlisteMapper.deleteSperrung(profil.getProfilId(), fremdprofilId);
		} else {
			// Wenn keine Sperrung vorliegt, wird eine Sperrung gesetzt
			// und der entsprechende Vermerk entfernt.
			this.sperrlisteMapper.insertSperrung(profil.getProfilId(), fremdprofilId);
			this.merklisteMapper.deleteVermerk(profil.getProfilId(), fremdprofilId);
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
	 * Methode zur Berechnung der Ã„hnlichkeit zwischen zwei Nutzerprofilen
	 */
	public void berechneAehnlichkeitNpFor()
			throws IllegalArgumentException {

		// Erforderliche Daten abrufen
		List<Nutzerprofil> referenzprofil = nutzerprofilMapper.findUnangeseheneNutzerprofile(profil.getProfilId());			
		Nutzerprofil vergleichsprofil = nutzerprofilMapper	.findByNutzerprofilId(profil.getProfilId());
		

		for (Nutzerprofil np : referenzprofil){
			
			
			int fremdprofilId = np.getProfilId();
			int profilId = vergleichsprofil.getProfilId();
			
		// Variablen zur Berechnung der Aehnlichkeit
		int aehnlichkeit = 3;
		int counter = 7;

		// Vergleich der Profildaten
		if (np.getGeschlecht().equals(
				vergleichsprofil.getGeschlecht())) {
			aehnlichkeit = aehnlichkeit - 3;
		}

		if (np.getHaarfarbe().equals(
				vergleichsprofil.getHaarfarbe())) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		if (np.getKoerpergroesseInt() == vergleichsprofil
				.getKoerpergroesseInt()) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		if (np.getRaucher().equals(vergleichsprofil.getRaucher())) {
			aehnlichkeit = aehnlichkeit + 1;
		}

		if (np.getReligion().equals(vergleichsprofil.getReligion())) {
			aehnlichkeit = aehnlichkeit + 1;
		}
		
		
		List<Info> referenzinfo = infoMapper.findAllInfosNeu(fremdprofilId);
		List<Info> vergleichsinfo = infoMapper.findAllInfosNeu(profilId);

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
		
		nutzerprofilMapper.insertAehnlichkeit(profilId, fremdprofilId, aehnlichkeit);
		
		}

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

	public void berechneAehnlichkeitSpFor() throws IllegalArgumentException {

		List<Suchprofil> referenzprofil = suchprofilMapper
				.findAllSuchprofileFor(profil.getProfilId());
		List<Nutzerprofil> vergleichsprofil = nutzerprofilMapper.findNutzerprofileOhneGesetzeSperrung(profil.getProfilId());
		
		// Vergleich der Profildaten von jeweils einem Suchprofil und einem Nutzerprofil
		for (Suchprofil sp : referenzprofil) {
			for (Nutzerprofil np : vergleichsprofil) {

				int aehnlichkeitSp = 0;
				int counter = 70;
				
				int suchprofilId = sp.getProfilId();
				int fremdprofilId = np.getProfilId();
				String suchprofilName = sp.getSuchprofilName();

				if (sp.getGeschlecht().equals(np.getGeschlecht())) {
					aehnlichkeitSp = aehnlichkeitSp + 30;
				}

				if (sp.getHaarfarbe().equals(np.getHaarfarbe())) {
					aehnlichkeitSp = aehnlichkeitSp + 10;
				}

				if (sp.getKoerpergroesseInt() == np.getKoerpergroesseInt()) {
					aehnlichkeitSp = aehnlichkeitSp + 10;
				}

				if (sp.getRaucher().equals(np.getRaucher())) {
					aehnlichkeitSp = aehnlichkeitSp + 10;
				}

				if (sp.getReligion().equals(np.getReligion())) {
					aehnlichkeitSp = aehnlichkeitSp + 10;

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
							if (rin.getInfotext().equals(vin.getInfotext())) {
								aehnlichkeitSp = aehnlichkeitSp + 2;

							}
						}
					}
				}
				
				// Berechnung des Prozentwertes
				aehnlichkeitSp = aehnlichkeitSp * (100 / counter);
				
				// Aehnlichkeit in die Datenbank setzen
				suchprofilMapper.insertAehnlichkeit(profil.getProfilId(),
						suchprofilId, suchprofilName, fremdprofilId,
						aehnlichkeitSp);

			}
		}

	}

	

	public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(String suchprofilName) throws IllegalArgumentException {

		return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeSp(profil.getProfilId(), suchprofilName);

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

	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllEigenschaften()
			throws IllegalArgumentException {
		
		Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result = new HashMap<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>();

		List<Eigenschaft> listE = infoMapper.findAllEigenschaftenNeu();

		List<Beschreibungseigenschaft> listEigB = new ArrayList<Beschreibungseigenschaft>();
		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();

		for (int i = 0; i < listE.size(); i++) {

			if (listE.get(i).getTyp().equals("B")) {

				Beschreibungseigenschaft eigB = infoMapper.findEigBByIdNeu(listE.get(i).getEigenschaftId());
				
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
//		System.out.println(result.toString());
		return result;
	}


	public Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> getAllUnusedEigenschaften()
			throws IllegalArgumentException {
		
		Map<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>> result2 = 
				new HashMap<List<Beschreibungseigenschaft>, List<Auswahleigenschaft>>();

		List<Eigenschaft> listE = infoMapper.findAllUnusedEigenschaftenNeu(1);
//		System.out.println("ListE: " + listE);

		List<Beschreibungseigenschaft> listEigB = new ArrayList<Beschreibungseigenschaft>();
		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();

		for (int i = 0; i < listE.size(); i++) {
			
//			System.out.println("Erläuterung: " + listE.get(i).getErlaeuterung());
			
			if (listE.isEmpty() == true) {
				
//				System.out.println("Es wurden alle möglichen Infos angelegt, die Liste der übrigen Eigenschaften ist leer.");
			}

			else {
				if (listE.get(i).getTyp().equals("B")) {
					
//					System.out.println("Es handelt sich um den Typ B.");

					Beschreibungseigenschaft eigB = infoMapper.findEigBByIdNeu(listE.get(i).getEigenschaftId());
					
					eigB.setErlaeuterung(listE.get(i).getErlaeuterung());
					eigB.setTyp(listE.get(i).getTyp());

					listEigB.add(eigB);
				}

				else if (listE.get(i).getTyp().equals("A")) {
					
//					System.out.println("Es handelt sich um den Typ A.");
	
					Auswahleigenschaft eigA = new Auswahleigenschaft();
					
					eigA = this.infoMapper.findEigAByIdNeu(listE.get(i).getEigenschaftId());
					
					eigA.setErlaeuterung(listE.get(i).getErlaeuterung());
					eigA.setTyp(listE.get(i).getTyp());
	
					listEigA.add(eigA);
				}
			}
		}

		result2.put(listEigB, listEigA);
//		System.out.println(" ListB: " + listEigB.toString());
//		System.out.println(" ListA: " + listEigA.toString());

		return result2;
	}
	
	
//	public List<Eigenschaft> getAllUnusedEigenschaftenNeu() throws IllegalArgumentException {
//		List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
//		listE = this.infoMapper.findAllUnusedEigenschaftenNeu(profil.getProfilId());
//		System.out.println(listE);
//		return listE;
//	}
	
	
	
	
	public List<Eigenschaft> getAllUnusedEigenschaftenNeuSp(int suchprofilId) throws IllegalArgumentException {
		List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
		listE = this.infoMapper.findAllUnusedEigenschaftenNeu(suchprofilId);
		System.out.println(listE);
		return listE;
	}
		
	
	
	public Map<List<Info>, List<Eigenschaft>> getAllInfos() throws IllegalArgumentException {
		
		Map<List<Info>, List<Eigenschaft>> result = new HashMap<List<Info>, List<Eigenschaft>>();
		List<Info> listI = new ArrayList<Info>();
		List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
		
		listI = this.infoMapper.findAllInfosNeu(profil.getProfilId());
		
		for (Info i : listI) {
			
			Eigenschaft e = new Eigenschaft();
			e = this.infoMapper.findEigenschaftByIdNeu(i.getEigenschaftId());
			
			System.out.println(e.getErlaeuterung());
			
			listE.add(e);
		}
		
		result.put(listI, listE);
		return result;
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

	
	public List<Info> createInfo(List<Info> infos)
			throws IllegalArgumentException {
			
			System.out.println(infos);
			
			return this.infoMapper.insertInfoNeu(profil.getProfilId(), infos);
		}

	
//	public Info createInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext) throws IllegalArgumentException {
//
//		Info i = new Info();
//		i.setProfilId(suchprofilId);
//		i.setEigenschaftId(eigenschaftId);
//		i.setInfotext(infotext);
//
//		return this.infoMapper.insertInfoNeu(i);
//	}

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
	
	
	public List<Auswahleigenschaft> getAuswahleigenschaften(List<Eigenschaft> listE) throws IllegalArgumentException {
	
		List<Auswahleigenschaft> listEigA = new ArrayList<Auswahleigenschaft>();
				
		for (int i = 0; i < listE.size(); i++) {
			
			Auswahleigenschaft eigA = new Auswahleigenschaft();
			eigA = this.infoMapper.findEigAByIdNeu(listE.get(i).getEigenschaftId());
			
			listEigA.add(eigA);
		}
		return listEigA;
	}

	
	public Auswahleigenschaft getEigAById(int eigenschaftId) throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAByIdNeu(eigenschaftId);

		return optionen;
	}
	
	public void saveInfo(List<Info> listI) throws IllegalArgumentException {
		this.infoMapper.updateInfos(profil.getProfilId(), listI);
	}


//	public void saveInfoNeuSp(int suchprofilId, int eigenschaftId, String infotext) throws IllegalArgumentException {
//
//		System.out.println(suchprofilId + ", " + eigenschaftId + ", " + infotext);
//
//		Info i = new Info();
//		i.setProfilId(suchprofilId);
//		i.setEigenschaftId(eigenschaftId);
//		i.setInfotext(infotext);
//
//		this.infoMapper.updateInfosNeu(i);
//	}



	@Override
	public List<Info> getAllInfosNeuReport(int profilId)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.infoMapper.findAllInfosNeu(1);
	}

	@Override
	public String getEigenschaftstextById(int eigenschaftId)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.infoMapper.findEigenschaftstextById(eigenschaftId);
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