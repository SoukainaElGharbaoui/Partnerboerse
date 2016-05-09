
package de.hdm.gruppe7.partnerboerse.server;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.MerklisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
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
		this.nutzerprofilMapper = NutzerprofilMapper.nutzerprofilMapper();
		this.suchprofilMapper = SuchprofilMapper.suchprofilMapper();
		this.merklisteMapper = MerklisteMapper.merklisteMapper();
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
	
	public Nutzerprofil updateNutzerprofil(String vorname){
		return null;
	}
//	public Nutzerprofil updateNutzerprofil(String vorname, String nachname,
//			String geburtsdatum, String geschlecht, String haarfarbe,
//			String koerpergroesse, String raucher, String religion) {
//		
//		Nutzerprofil nutzerprofil = new Nutzerprofil();
//		nutzerprofil.setVorname(vorname);
//		nutzerprofil.setNachname(nachname);
//		nutzerprofil.setGeburtsdatum(geburtsdatum);
//		nutzerprofil.setGeschlecht(geschlecht);
//		nutzerprofil.setHaarfarbe(haarfarbe);
//		nutzerprofil.setKoerpergroesse(koerpergroesse);
//		nutzerprofil.setRaucher(raucher);
//		nutzerprofil.setReligion(religion);
//		
//		// Vorläufige ProfilId setzen. 
//				nutzerprofil.setProfilId(1);
//
//		return this.nutzerprofilMapper.updateNutzerprofil(nutzerprofil);

//	}

	/**
	 * Auslesen eines Nutzerprofils anhand seiner ProfilId.
	 */
	@Override
	public Nutzerprofil getNutzerprofilById(int profilId)
			throws IllegalArgumentException {

		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}

	/**
	 * Speichern eines Nutzerprofils.
	 */
	@Override
	public void save(Nutzerprofil nutzerprofil) throws IllegalArgumentException {

		nutzerprofilMapper.updateNutzerprofil(nutzerprofil);
	}

	/**
	 * Löschen eines Nutzerprofils.
	 */
	@Override
	public void deleteNutzerprofil(Nutzerprofil nutzerprofil)
			throws IllegalArgumentException {

		nutzerprofilMapper.deleteNutzerprofil(nutzerprofil);
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

	// Gemerkte Nutzerprofile eines Nutzerprofils ermitteln. 
	public Vector<Merkliste> getGemerkteNutzerprofileFor(int profilId)
			throws IllegalArgumentException {

		return this.merklisteMapper.findAllVermerkeFor(profilId);
	}
	
	// Überprüfen, ob ein Vermerk besteht. 
	public int getVermerkStatus(int profilId, int fremdprofilId)
			throws IllegalArgumentException {
		
		return this.merklisteMapper.pruefeVermerk(profilId, fremdprofilId); 
	}
	
	// Bestimmten Vermerk eines Nutzerprofils löschen. 
	public void vermerkLoeschen(int profilId, int mFremdprofilId) throws IllegalArgumentException {
		
		this.merklisteMapper.deleteVermerk(profilId, mFremdprofilId); 
	}

	/**
	 * ABSCHNITT MERKLISTE: ENDE
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


	public void save(Suchprofil suchprofil) throws IllegalArgumentException {
		
		suchprofilMapper.updateSuchprofil(suchprofil);
	
	}


	public void deleteSuchprofil(int profilId) throws IllegalArgumentException {
		
		suchprofilMapper.deleteSuchprofil(profilId);
		
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
	
	public Info createInfo(String infotext) throws IllegalArgumentException {
		
		Info info = new Info();
		info.setInfotext(infotext);
		
		info.setInfoId(1);
		
		return this.infoMapper.insertInfo(info);
	}
	
	public List<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException {
		return this.infoMapper.findAllEigenschaften();
	}
}

