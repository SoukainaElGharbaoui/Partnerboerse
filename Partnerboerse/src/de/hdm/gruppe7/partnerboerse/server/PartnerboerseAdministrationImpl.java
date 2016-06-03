
package de.hdm.gruppe7.partnerboerse.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
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
		Nutzerprofil n = new Nutzerprofil();
		n.setVorname(vorname);
		n.setNachname(nachname);
		n.setGeschlecht(geschlecht);
		n.setGeburtsdatumDate(geburtsdatumDate);
		n.setKoerpergroesseInt(koerpergroesseInt);
		n.setHaarfarbe(haarfarbe);
		n.setRaucher(raucher);
		n.setReligion(religion);
		// n.setEmailAddress(emailAddress);

		// VorlÃ¤ufige Profil-ID setzen.
		n.setProfilId(1);

		return this.nutzerprofilMapper.insertNutzerprofil(n);
	}

	/**
	 * Nutzerprofil aktualisieren.
	 */
	public void saveNutzerprofil(String vorname, String nachname, String geschlecht, Date geburtsdatumDate,
			int koerpergroesseInt, String haarfarbe, String raucher, String religion) throws IllegalArgumentException {

		Nutzerprofil n = new Nutzerprofil();
		n.setVorname(vorname);
		n.setNachname(nachname);
		n.setGeschlecht(geschlecht);
		n.setGeburtsdatumDate(geburtsdatumDate);
		n.setKoerpergroesseInt(koerpergroesseInt);
		n.setHaarfarbe(haarfarbe);
		n.setRaucher(raucher);
		n.setReligion(religion);

		this.nutzerprofilMapper.updateNutzerprofil(n);
	}

	/**
	 * Nutzerprofil lÃ¶schen.
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

		s.setProfilId(1);

		return this.suchprofilMapper.insertSuchprofil(s);
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
	public void deleteSuchprofil(int profilId, String suchprofilName) throws IllegalArgumentException {
		this.suchprofilMapper.deleteSuchprofil(profilId, suchprofilName);
	}

	

	/**
	 * Alle Suchprofile auslesen. (EVTL. NICHT NOTWENDIG)
	 */
	public List<Suchprofil> getAllSuchprofile() throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofile();
	}
	
	/**
	 * Alle Suchprofile EINES NUTZERS auslesen. (ÜBERARBEITET VON MILENA - NOTWENIG)
	 */
	public List<Suchprofil> getAllSuchprofileFor(int profilId) throws IllegalArgumentException {
		return this.suchprofilMapper.findAllSuchprofileFor(profilId);

	}
	/**
	 * Suchprofil anhand der Profil-ID UND des Namens auslesen. (ÃœBERARBEITET VON MILENA - NOTWENDIG)
	 */
	public Suchprofil getSuchprofilByName(int profilId, String suchprofilName) throws IllegalArgumentException {
		return this.suchprofilMapper.findSuchprofilByName(profilId, suchprofilName); 
	}
	
	/**
	 * Existenz des Suchprofilnamens beim Anlegen Ã¼berprÃ¼fen.
	 */
	public int pruefeSuchprofilname(int profilId, String suchprofilname) throws IllegalArgumentException {
		return this.suchprofilMapper.pruefeSuchprofilname(profilId, suchprofilname); 
	}
	
	/**
	 * Existenz des Suchprofilnamens beim Editieren Ã¼berprÃ¼fen.
	 */
	public String pruefeSuchprofilnameEdit(int profilId, int suchprofilId) throws IllegalArgumentException {
		return this.suchprofilMapper.pruefeSuchprofilnameEdit(profilId, suchprofilId); 
	}

	public Suchprofil getSuchprofilById (int suchprofilId) throws IllegalArgumentException {
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
	public Merkliste getGemerkteNutzerprofileFor(int profilId) throws IllegalArgumentException {

		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		result = this.merklisteMapper.findGemerkteNutzerprofileFor(profilId);

		Merkliste gemerkteNutzerprofile = new Merkliste();

		gemerkteNutzerprofile.setGemerkteNutzerprofile(result);

		return gemerkteNutzerprofile;
	}

	// Vermerkstatus ermitteln.
	public int getVermerkstatus(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId);
	}

	// Vermerk einfÃ¼gen.
	public void vermerkSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.merklisteMapper.insertVermerk(profilId, fremdprofilId);
	}

	// Vermerk lÃ¶schen.
	public void vermerkLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.merklisteMapper.deleteVermerk(profilId, fremdprofilId);
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
	public Sperrliste getGesperrteNutzerprofileFor(int profilId) throws IllegalArgumentException {

		Vector<Nutzerprofil> result = new Vector<Nutzerprofil>();

		result = this.sperrlisteMapper.findGesperrteNutzerprofileFor(profilId);

		Sperrliste gesperrteNutzerprofile = new Sperrliste();

		gesperrteNutzerprofile.setGesperrteNutzerprofile(result);

		return gesperrteNutzerprofile;
	}

	// PrÃ¼fen, ob Fremdprofil von Benutzer gesperrt wurde.
	public int getSperrstatusFremdprofil(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungFremdprofil(profilId, fremdprofilId);
	}

	// PrÃ¼fen, ob Benutzer von Fremdprofil gesperrt wurde.
	public int getSperrstatusEigenesProfil(int profilId, int fremdprofilId) throws IllegalArgumentException {
		return this.sperrlisteMapper.pruefeSperrungEigenesProfil(profilId, fremdprofilId);
	}

	// Sperrung einfÃ¼gen.
	public void sperrungSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.sperrlisteMapper.insertSperrung(profilId, fremdprofilId);
	}

	// Sperrung lÃ¶schen.
	public void sperrungLoeschen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.sperrlisteMapper.deleteSperrung(profilId, fremdprofilId);
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
	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId) throws IllegalArgumentException {
		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
	}

	// Besuch setzen.
	public void besuchSetzen(int profilId, int fremdprofilId) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertBesuch(profilId, fremdprofilId);
	}

			
		/**
		 * Methode zur Berechnung der Ähnlichkeit zwischen zwei Nutzerprofilen
		 */
		public int berechneAehnlichkeitNpFor(int profilId, int fremdprofilId) throws IllegalArgumentException {
		
			// Erforderliche Daten abrufen
			Nutzerprofil referenzprofil = nutzerprofilMapper.findByNutzerprofilId(profilId);
			Nutzerprofil  vergleichsprofil = nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
			List<Info> referenzinfo = infoMapper.findAllInfosNeu(profilId);
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
			for (Info rin : referenzinfo){
				for (Info vin : vergleichsinfo){
			if (rin.getEigenschaftId() == vin.getEigenschaftId()){
				counter++;
				if (rin.getInfotext().equals(vin.getInfotext())){
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
		 *  Aehnlichkeit setzen
		 */
		public void aehnlichkeitSetzen(int profilId, int fremdprofilId, int aehnlichkeit) throws IllegalArgumentException {
			this.nutzerprofilMapper.insertAehnlichkeit(profilId, fremdprofilId, aehnlichkeit); 

		}
		
		/**
		 * Aehnlichkeit entfernen
		 */
		public void aehnlichkeitEntfernen(int profilId) throws IllegalArgumentException {
			this.nutzerprofilMapper.deleteAehnlichkeit(profilId);
		}
		
		/**
		 * Methode zur Ausgabe einer Liste von Partnervorschlaegen (Unangesehene Profile, 
		 * von denen man nicht gesperrt wurde, geordnet nach Aehnlichkeit)
		 */
		public List<Nutzerprofil> getGeordnetePartnervorschlaegeNp(int profilId)
				throws IllegalArgumentException {
			return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeNp(profilId);

		}

		
		
		
public int berechneAehnlichkeitSpFor(int suchprofilId, int fremdprofilId) throws IllegalArgumentException {
		
			
			Suchprofil referenzprofil = suchprofilMapper.findSuchprofilById(suchprofilId);
			Nutzerprofil  vergleichsprofil = nutzerprofilMapper.findByNutzerprofilId(fremdprofilId);
			List<Info> referenzinfo = infoMapper.findInfosByProfilId(suchprofilId);
			List<Info> vergleichsinfo = infoMapper.findInfosByProfilId(fremdprofilId);
			
			int aehnlichkeitSp = 0;
			
			int counter = 100;
			
			if (referenzprofil.getGeschlecht().equals(vergleichsprofil.getGeschlecht())) {
				aehnlichkeitSp  = aehnlichkeitSp  + 40;
			}
			
			if (referenzprofil.getHaarfarbe().equals(vergleichsprofil.getHaarfarbe())) {
				aehnlichkeitSp  = aehnlichkeitSp  + 10;
			}
			
			
			if (referenzprofil.getKoerpergroesseInt() == vergleichsprofil.getKoerpergroesseInt()) {
				aehnlichkeitSp  = aehnlichkeitSp  + 10;
			}
			
			if (referenzprofil.getRaucher().equals(vergleichsprofil.getRaucher())) {
				aehnlichkeitSp  = aehnlichkeitSp  + 20;
			}
			
			if (referenzprofil.getReligion().equals(vergleichsprofil.getReligion())) {
				aehnlichkeitSp  = aehnlichkeitSp  + 20;
			}
			
			// Vergleich der Infos
						for (Info rin : referenzinfo){
							for (Info vin : vergleichsinfo){
						if (rin.getEigenschaftId() == vin.getEigenschaftId()){
							if (rin.getInfotext().equals(vin.getInfotext())){
								aehnlichkeitSp= aehnlichkeitSp + 10;
								counter = counter+10;
							}
						}
					}
					}
			
						aehnlichkeitSp = aehnlichkeitSp * (100 /counter);
			
		
			
			
			
			return aehnlichkeitSp;
			
		}
		
		
		public void aehnlichkeitSetzenSp (int nutzerprofilId, int suchprofilId, String suchprofilName,  int fremdprofilId, int aehnlichkeitSp) throws IllegalArgumentException {
			this.suchprofilMapper.insertAehnlichkeit(nutzerprofilId,suchprofilId, suchprofilName, fremdprofilId, aehnlichkeitSp); 
		}
		
		public void aehnlichkeitEntfernenSp(int nutzerprofilId) throws IllegalArgumentException {
			this.suchprofilMapper.deleteAehnlichkeitSp(nutzerprofilId);
		}
		
		// Alle Nutzerprofile die mich nicht gesperrt haben auslesen.
		public List<Nutzerprofil> getNutzerprofileOhneGesetzteSperrung (int profilId) throws IllegalArgumentException {
			return this.nutzerprofilMapper.findNutzerprofileOhneGesetzeSperrung(profilId);
		}
		
		public List<Nutzerprofil> getGeordnetePartnervorschlaegeSp(int profilId, String suchprofilName)
				throws IllegalArgumentException {
			
			return this.nutzerprofilMapper.findGeordnetePartnervorschlaegeSp(profilId, suchprofilName);
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

//	public Info createBeschreibungsinfo(int profilId, int eigenschaftId, String infotext)
//			throws IllegalArgumentException {
//
//		Info info = new Info();
//		info.setNutzerprofilId(profilId);
//		info.setEigenschaftId(eigenschaftId);
//		info.setInfotext(infotext);
//
//		return this.infoMapper.insertBeschreibungsinfo(info);
//	}
//
//
//	public Info createAuswahlinfo(int profilId, int eigenschaftId, int auswahloptionIdInt)
//			throws IllegalArgumentException {
//
//		Info info = new Info();
//		info.setNutzerprofilId(profilId);
//		info.setEigenschaftId(eigenschaftId);
//		info.setAuswahloptionId(auswahloptionIdInt);
//
//		return this.infoMapper.insertAuswahlinfo(info);
//	}
		
		
//		public void saveInfoB(int profilId, int eigenschaftId, String infotext) throws IllegalArgumentException {
//			this.infoMapper.updateInfoB(profilId, eigenschaftId, infotext);
//		}
		
		
//		public List<Eigenschaft> getAllEigenschaftenB() throws IllegalArgumentException {
//		return this.infoMapper.findAllEigenschaftenB();
//	}
		
		
//		public List<Eigenschaft> getAllEigenschaftenA() throws IllegalArgumentException {
//			return this.infoMapper.findAllEigenschaftenA();
//		}

	
	public List<Eigenschaft> getAllEigenschaftenNeu() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaftenNeu();
	}	
		
	public List<Info> getAllInfosNeuReport(int nutzerprofilId) throws IllegalArgumentException {
		return this.infoMapper.findAllInfosNeu(nutzerprofilId);
	}
	
	public List<String> getAllInfosNeu(int nutzerprofilId) throws IllegalArgumentException {

		List<String> list1 = new ArrayList<String>();
		List<Info> result = new ArrayList<Info>();
		
		result = this.infoMapper.findAllInfosNeu(nutzerprofilId);

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
		System.out.println(list1);
		return list1;
	}
		
	
	public Info createInfoNeu(int profilId, int eigenschaftId, String infotext) 
			throws IllegalArgumentException {
		
		Info i = new Info();
		i.setProfilId(profilId);
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);
		
		return this.infoMapper.insertInfoNeu(i);
	}

	public void deleteAllInfosNeu(int profilId) throws IllegalArgumentException {
		this.infoMapper.deleteAllInfosNeu(profilId);
	}
	
	public void deleteOneInfoNeu(int profilId, int eigenschaftId) throws IllegalArgumentException {
		this.infoMapper.deleteOneInfoNeu(profilId, eigenschaftId);
		System.out.println(profilId + ", " + eigenschaftId);
	}
	
	public Beschreibungseigenschaft getEigBById(int eigenschaftId)  throws IllegalArgumentException {
		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();
		eigB = this.infoMapper.findEigBByIdNeu(eigenschaftId);
		System.out.println(eigB.getBeschreibungstext());
		
		return eigB;
		//		return this.infoMapper.findEigBByIdNeu(eigenschaftId);
	}
	
	public Auswahleigenschaft getEigAById(int eigenschaftId) throws IllegalArgumentException {
		Auswahleigenschaft optionen = new Auswahleigenschaft();
		optionen = this.infoMapper.findEigAByIdNeu(eigenschaftId);
		System.out.println(optionen.getOptionen());
		
		return optionen;
//		return this.infoMapper.findEigAById(eigenschaftId);
	}
	
	public void saveInfoNeu(int profilId, int eigenschaftId, String infotext) throws IllegalArgumentException {

		System.out.println(profilId + ", " + eigenschaftId + ", " + infotext);
		
		Info i = new Info();
		i.setProfilId(profilId);
		i.setEigenschaftId(eigenschaftId);
		i.setInfotext(infotext);
		
		this.infoMapper.updateInfosNeu(i);

	}

//	public List<Auswahloption> getAllAuswahloptionen(int eigenschaftId) throws IllegalArgumentException {
//		return this.infoMapper.findAllAuswahloptionen(eigenschaftId);
//	}
//		
//	public List<Info> getAllInfosB(int profilId) throws IllegalArgumentException {
//		return this.infoMapper.findAllInfosB(profilId);
//	}
//
//	public List<Info> getAllInfosA(int profilId) throws IllegalArgumentException {
//		return this.infoMapper.findAllInfosA(profilId);
//	}
//	
//	public Info getOptionById(int eigenschaftId) throws IllegalArgumentException {
//		return this.infoMapper.findOptionById(eigenschaftId);
//	}
	
	
//	public Info getInfoAById(String optionsbezeichnung, int eigenschaftId) throws IllegalArgumentException {
//		return this.infoMapper.findByInfoAId(optionsbezeichnung, eigenschaftId);
//	}

//	public void deleteAllInfos(int profilId) throws IllegalArgumentException {
//		this.infoMapper.deleteAllInfos(profilId);
//	}

//	public void deleteOneInfoB(int profilId, int eigenschaftId) throws IllegalArgumentException {
//		this.infoMapper.deleteOneInfoB(profilId, eigenschaftId);
//	}

//	public List<Nutzerprofil> getUnangeseheneNutzerprofile(int profilId)
//			throws IllegalArgumentException {
//		return this.nutzerprofilMapper.findUnangeseheneNutzerprofile(profilId);
//	}

//		public void deleteOneInfoA(int profilId, int eigenschaftId) throws IllegalArgumentException {
//			this.infoMapper.deleteOneInfoA(profilId, eigenschaftId);
//	}


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

	@Override
	public void insertEmail(String emailAddress) throws IllegalArgumentException {
		this.nutzerprofilMapper.insertEmail(emailAddress);

	}

	public Nutzerprofil login(String requestUri) throws Exception {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Nutzerprofil n = new Nutzerprofil();
		// NutzerprofilMapper.nutzerprofilMapper().findByNutzerprofilMitEmail(user.getEmail());
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







