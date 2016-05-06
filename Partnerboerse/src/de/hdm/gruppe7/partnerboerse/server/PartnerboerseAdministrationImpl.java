package de.hdm.gruppe7.partnerboerse.server;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.db.MerklisteMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Merkliste;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;


@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

	/**
	 * Referenz auf den DatenbankMapper, der Nutzerprofil-Objekte mit der
	 * Datenbank abgleicht.
	 */
	private NutzerprofilMapper nutzerprofilMapper = null;
	
	private MerklisteMapper merklisteMapper = null; 

	/**
	 * No-Argument-Konstruktor
	 * @throws IllegalArgumentException
	 */
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode, die für jede Instanz von <code>PartnerboerseAdministrationImpl</code> 
	 * aufgerufen werden muss. 
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
		this.merklisteMapper = MerklisteMapper.merklisteMapper(); 
	}

	/**
	 * Anlegen eines neuen Nutzerprofils. Dies führt implizit zu einem Speichern
	 * des neuen Nutzerprofils in der Datenbank.
	 * 
	 * Änderungen an Nutzerprofil-Objekten müssen stets durch Aufruf von
	 * {@link #save(Nutzerprofil nutzerprofil)} in die Datenbank transferiert
	 * werden.
	 * 
	 * @see save(Nutzerprofil nutzerprofil)
	 */
	@Override
	
	public Nutzerprofil createNutzerprofil(String vorname, String nachname, String geburtsdatum, String geschlecht,
			String haarfarbe, String koerpergroesse, String raucher, String religion) throws IllegalArgumentException {

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
	 * Auslesen eines Nutzerprofils anhand seiner ProfilId.
	 */
	@Override
	public Nutzerprofil getNutzerprofilById(int profilId) throws IllegalArgumentException {

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
	public void delete(Nutzerprofil nutzerprofil) throws IllegalArgumentException {

		nutzerprofilMapper.deleteNutzerprofil(nutzerprofil);
	}

	@Override
	public List<Nutzerprofil> getAngeseheneNpFor(Nutzerprofil nutzerprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * ABSCHNITT MERKLISTE: BEGINN
	 */
	
	public Vector<Merkliste> getMerkliste(int profilId) throws IllegalArgumentException {
		
		return this.merklisteMapper.findAllVermerkeFor(profilId); 
	}
	
	/**
	 * ABSCHNITT MERKLISTE: ENDE
	 */

}
