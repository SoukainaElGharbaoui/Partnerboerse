package de.hdm.gruppe7.partnerboerse.server.report;

import java.util.Date;
import java.util.List;









import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllSuchprofileOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Column;
import de.hdm.gruppe7.partnerboerse.shared.report.CompositeParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.PartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Report;
import de.hdm.gruppe7.partnerboerse.shared.report.Row;
import de.hdm.gruppe7.partnerboerse.shared.report.SimpleParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.UnangesehenePartnervorschlaegeReport;

/**
 * Implementierung des <code>ReportGenerator</code>-Interface. 
 * @see ReportGenerator
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gew�nscht, als Grundlage 
 * �bernommen und bei Notwendigkeit an die Bed�rfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 * 
 * Modifizierender @author Milena Weinmann
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements
		ReportGenerator {

	private InfoMapper infoMapper = null;
	/**
	 * Ein ReportGenerator ben�tigt Zugriff auf die PartnerboerseAdministration, da diese die
	 * essentiellen Methoden f�r die Koexistenz von Datenobjekten (vgl. bo-Package) bietet.
	 */
	private PartnerboerseAdministration partnerboerseAdministration = null;

	/**
	 * No-Argument-Konstruktor.
	 * @throws IllegalArgumentException
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialisierungsmethode.
	 * @see #ReportGeneratorImpl()
	 */
	public void init() throws IllegalArgumentException {
		/*
	     * Ein ReportGeneratorImpl-Objekt instantiiert f�r seinen Eigenbedarf eine
	     * PartnerboerseAdministrationImpl-Instanz.
	     */
		PartnerboerseAdministrationImpl p = new PartnerboerseAdministrationImpl(); 
		p.init();
		this.partnerboerseAdministration = p; 
	}
	
	/**
	 *  Zugeh�rige PartnerboerseAdministration auslesen (interner Geburach). 
	 * @return das PartnerboerseAdministration-Objekt.
	 */
	protected PartnerboerseAdministration getPartnerboerseAdministration() {
		return this.partnerboerseAdministration; 
	}
	
	/**
	 *  Report-Impressum hinzuf�gen. 
	 * @param r der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {
		
	    /*
	     * Das Imressum soll mehrzeilig sein.
	     */
		CompositeParagraph imprint = new CompositeParagraph(); 
		
		imprint.addSubParagraph(new SimpleParagraph("Partnerb�rse"));
		imprint.addSubParagraph(new SimpleParagraph("XYZ")); 
		
		/*
		 * Impressum zum Report hinzuf�gen.
		 */
		r.setImprint(imprint); 
	}

	/**
	 * <code>AllAccountsOfCustomerReport</code>-Objekte erstellen.
	 * 
	 * @param n das Nutzerprofil-Objekt bzgl. dessen der Report erstellt werden soll.
	 * @return der fertige Report
	 */
	public AllSuchprofileOfNutzerReport createAllSuchprofileOfNutzerReport(Nutzerprofil n) 
			throws IllegalArgumentException {
		
		if (this.getPartnerboerseAdministration() == null) {
			return null;
		}

	    /*
	     * Leeren Report anlegen.
	     */
		AllSuchprofileOfNutzerReport result = new AllSuchprofileOfNutzerReport();

		// Jeder Report hat einen Titel (Bezeichnung / �berschrift).
		result.setTitle("Suchprofil-Report f�r " + n.getVorname() + " " + n.getNachname()); 

		// Imressum hinzuf�gen.
		this.addImprint(result);

		/*
		 *  Erstellungsdatum hinzuf�gen.
		 *  new Date() erzeugt autom. einen "Timestamp" des Zeitpunkts 
		 *  der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

	    /*
	     * Ab hier: Kopfdaten des Reports zusammenstellen. 
	     * Die Kopfdaten sind mehrzeilig, daher die Verwendung von CompositeParagraph.
	     */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzers aufnehmen.
		header.addSubParagraph(new SimpleParagraph(n.getVorname() + " " + n.getNachname())); 
		
		// Nutzerprofil-ID aufnehmen.
	    header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: " + n.getProfilId()));

		// Zusammengestellte Kopfdaten zum Report hinzuf�gen.
		result.setHeaderData(header);
		
	    /*
	     * Ab hier: Suchprofil-Informationen zeilenweise hinzuf�gen.
	     */
		// Kopfzeile f�r die Suchprofil-Tabelle anlegen.
		Row headline = new Row();

		// �berschriften der Kopfzeile ablegen.
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Suchprofilname"));
		headline.addColumn(new Column("Geschlecht"));
		headline.addColumn(new Column("Alter von"));
		headline.addColumn(new Column("Alter bis"));
		headline.addColumn(new Column("K�rpergr��e"));
		headline.addColumn(new Column("Haarfarbe"));
		headline.addColumn(new Column("Raucherstatus"));
		headline.addColumn(new Column("Religion"));

		// Kopfzeile hinzuf�gen.
		result.addRow(headline);

	    /*
	     * S�mtliche Suchprofile des Nutzers ausgelesen und in die Tabelle eintragen.
	     */
		List<Suchprofil> suchprofile = this.partnerboerseAdministration.getAllSuchprofileFor(n); 

		for (Suchprofil s : suchprofile) {
			
			// Eine leere Zeile anlegen.
			Row suchprofilRow = new Row();

			// Zeile bef�llen.
			suchprofilRow.addColumn(new Column(String.valueOf(s.getProfilId())));
			suchprofilRow.addColumn(new Column(s.getSuchprofilName()));
			suchprofilRow.addColumn(new Column(s.getGeschlecht()));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getAlterMinInt())));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getAlterMaxInt())));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getKoerpergroesseInt())));
			suchprofilRow.addColumn(new Column(s.getHaarfarbe()));
			suchprofilRow.addColumn(new Column(s.getRaucher()));
			suchprofilRow.addColumn(new Column(s.getReligion()));

			// Zeile dem Report hinzuf�gen.
			result.addRow(suchprofilRow);
		}

	    /*
	     * Fertigen Report zur�ckgeben.
	     */
		return result;

	}

	
	
	
	
	/**
	 * <code>UnangesehenePartnervorschlaegeReport</code>-Objekte erstellen.
	 * 
	 * @param n das Nutzerprofil-Objekt bzgl. dessen der Report erstellt werden soll.
	 * @return der fertige Report
	 */
	/* (non-Javadoc)
	 * @see de.hdm.gruppe7.partnerboerse.shared.ReportGenerator#createUnangesehenePartnervorschlaegeReport(de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil)
	 */
	public UnangesehenePartnervorschlaegeReport createUnangesehenePartnervorschlaegeReport(Nutzerprofil n) 
			throws IllegalArgumentException {
		
		if (this.getPartnerboerseAdministration() == null) {
			return null;
		}

	    /*
	     * Leeren Report anlegen.
	     */
		UnangesehenePartnervorschlaegeReport result = new UnangesehenePartnervorschlaegeReport();

		// Jeder Report hat einen Titel (Bezeichnung / �berschrift).
		result.setTitle("Unangesehene Partnervorschlaege Report f�r " + n.getVorname() + " " + n.getNachname()); 

		// Imressum hinzuf�gen.
		this.addImprint(result);

		/*
		 *  Erstellungsdatum hinzuf�gen.
		 *  new Date() erzeugt autom. einen "Timestamp" des Zeitpunkts 
		 *  der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

	    /*
	     * Ab hier: Kopfdaten des Reports zusammenstellen. 
	     * Die Kopfdaten sind mehrzeilig, daher die Verwendung von CompositeParagraph.
	     */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzers aufnehmen.
		header.addSubParagraph(new SimpleParagraph(n.getVorname() + " " + n.getNachname())); 
		
		// Nutzerprofil-ID aufnehmen.
	    header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: " + n.getProfilId()));

		// Zusammengestellte Kopfdaten zum Report hinzuf�gen.
		result.setHeaderData(header);
		
	    /*
	     * Ab hier: Suchprofil-Informationen zeilenweise hinzuf�gen.
	     */
		// Kopfzeile f�r die Suchprofil-Tabelle anlegen.
		Row headline = new Row();

		// �berschriften der Kopfzeile ablegen.
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Aehnlichkeit"));
		headline.addColumn(new Column("Vornamee"));
		headline.addColumn(new Column("Nachname"));
		headline.addColumn(new Column("Geschlecht"));
		headline.addColumn(new Column("Geburtsdatum"));
		headline.addColumn(new Column("K�rpergr��e"));
		headline.addColumn(new Column("Haarfarbe"));
		headline.addColumn(new Column("Raucherstatus"));
		headline.addColumn(new Column("Religion"));
		
		List<Eigenschaft> eigenschaften = this.partnerboerseAdministration.getAllEigenschaftenNeu();
		int max = 0;
		for(Eigenschaft e : eigenschaften) {
			
			headline.addColumn(new Column(e.getErlaeuterung()));
			max++;
		}


		// Kopfzeile hinzuf�gen.

		result.addRow(headline);

	    /*
	     * S�mtliche Suchprofile des Nutzers ausgelesen und in die Tabelle eintragen.
	     */
		List<Nutzerprofil> nutzerprofile = this.partnerboerseAdministration.getGeordnetePartnervorschlaegeNp(); 

		for (Nutzerprofil pv : nutzerprofile) {
			
			// Eine leere Zeile anlegen.
			Row nutzerprofilRow = new Row();

			// Zeile bef�llen.
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getProfilId())));
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getAehnlichkeit()) + "%"));
			nutzerprofilRow.addColumn(new Column(pv.getVorname()));
			nutzerprofilRow.addColumn(new Column(pv.getNachname()));
			nutzerprofilRow.addColumn(new Column(pv.getGeschlecht()));
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getGeburtsdatumDate())));
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getKoerpergroesseInt())));
			nutzerprofilRow.addColumn(new Column(pv.getHaarfarbe()));
			nutzerprofilRow.addColumn(new Column(pv.getRaucher()));
			nutzerprofilRow.addColumn(new Column(pv.getReligion()));

			List<Info> info = this.partnerboerseAdministration.getAllInfosNeuReport();
			int counter = 1;
			for (Info in : info) {
				
				while (counter < max){
					
				if (in.getEigenschaftId() == counter){
					nutzerprofilRow.addColumn(new Column(in.getInfotext()));
					break;
				} else {
					nutzerprofilRow.addColumn(new Column(""));	
					counter ++;
				}
				
				}
				counter ++;
			}
			// Zeile dem Report hinzuf�gen.
			result.addRow(nutzerprofilRow);
		}

	    /*
	     * Fertigen Report zur�ckgeben.
	     */
		return result;

	}
	
	
	/**
	 * <code>UnangesehenePartnervorschlaegeReport</code>-Objekte erstellen.
	 * 
	 * @param n das Nutzerprofil-Objekt bzgl. dessen der Report erstellt werden soll.
	 * @return der fertige Report
	 */
	/* (non-Javadoc)
	 * @see de.hdm.gruppe7.partnerboerse.shared.ReportGenerator#createUnangesehenePartnervorschlaegeReport(de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil)
	 */
	public PartnervorschlaegeSpReport createPartnervorschlaegeSpReport(Nutzerprofil n, String suchprofilname) 
			throws IllegalArgumentException {
		
		if (this.getPartnerboerseAdministration() == null) {
			return null;
		}

	    /*
	     * Leeren Report anlegen.
	     */
		PartnervorschlaegeSpReport result = new PartnervorschlaegeSpReport();

		// Jeder Report hat einen Titel (Bezeichnung / �berschrift).
		result.setTitle("Suchprofil Partnervorschlaege Report f�r " + n.getVorname() + " " + n.getNachname()); 

		// Imressum hinzuf�gen.
		this.addImprint(result);

		/*
		 *  Erstellungsdatum hinzuf�gen.
		 *  new Date() erzeugt autom. einen "Timestamp" des Zeitpunkts 
		 *  der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

	    /*
	     * Ab hier: Kopfdaten des Reports zusammenstellen. 
	     * Die Kopfdaten sind mehrzeilig, daher die Verwendung von CompositeParagraph.
	     */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzers aufnehmen.
		header.addSubParagraph(new SimpleParagraph(n.getVorname() + " " + n.getNachname())); 
		
		// Nutzerprofil-ID aufnehmen.
	    header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: " + n.getProfilId()));

		// Zusammengestellte Kopfdaten zum Report hinzuf�gen.
		result.setHeaderData(header);
		
	    /*
	     * Ab hier: Suchprofil-Informationen zeilenweise hinzuf�gen.
	     */
		// Kopfzeile f�r die Suchprofil-Tabelle anlegen.
		Row headline = new Row();

		// �berschriften der Kopfzeile ablegen.
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Aehnlichkeit"));
		headline.addColumn(new Column("Vornamee"));
		headline.addColumn(new Column("Nachname"));
		headline.addColumn(new Column("Geschlecht"));
		headline.addColumn(new Column("Geburtsdatum"));
		headline.addColumn(new Column("K�rpergr��e"));
		headline.addColumn(new Column("Haarfarbe"));
		headline.addColumn(new Column("Raucherstatus"));
		headline.addColumn(new Column("Religion"));
		
		List<Eigenschaft> eigenschaften = this.partnerboerseAdministration.getAllEigenschaftenNeu();
		int max = 0;
		for(Eigenschaft e : eigenschaften) {
			
			headline.addColumn(new Column(e.getErlaeuterung()));
			max++;
		}


		
		// Kopfzeile hinzuf�gen.
		result.addRow(headline);

	    /*
	     * S�mtliche Suchprofile des Nutzers ausgelesen und in die Tabelle eintragen.
	     */
		List<Nutzerprofil> nutzerprofile = this.partnerboerseAdministration.getGeordnetePartnervorschlaegeSp(suchprofilname); 

		for (Nutzerprofil pv : nutzerprofile) {
			
			// Eine leere Zeile anlegen.
			Row nutzerprofilRow = new Row();

			// Zeile bef�llen.
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getProfilId())));
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getAehnlichkeit()) + "%"));
			nutzerprofilRow.addColumn(new Column(pv.getVorname()));
			nutzerprofilRow.addColumn(new Column(pv.getNachname()));
			nutzerprofilRow.addColumn(new Column(pv.getGeschlecht()));
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getGeburtsdatumDate())));
			nutzerprofilRow.addColumn(new Column(String.valueOf(pv.getKoerpergroesseInt())));
			nutzerprofilRow.addColumn(new Column(pv.getHaarfarbe()));
			nutzerprofilRow.addColumn(new Column(pv.getRaucher()));
			nutzerprofilRow.addColumn(new Column(pv.getReligion()));
			
			List<Info> info = this.partnerboerseAdministration.getAllInfosNeuReport();
			int counter = 1;
			for (Info in : info) {
				
				while (counter < max){
					
				if (in.getEigenschaftId() == counter){
					nutzerprofilRow.addColumn(new Column(in.getInfotext()));
					break;
				} else {
					nutzerprofilRow.addColumn(new Column(""));	
					counter ++;
				}
				
				}
				counter ++;
			}
			// Zeile dem Report hinzuf�gen.
			result.addRow(nutzerprofilRow);
		}

	    /*
	     * Fertigen Report zur�ckgeben.
	     */
		return result;

	}
	
	
	
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