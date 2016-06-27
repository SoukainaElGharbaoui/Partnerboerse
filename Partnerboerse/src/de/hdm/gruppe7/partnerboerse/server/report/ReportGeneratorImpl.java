package de.hdm.gruppe7.partnerboerse.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllProfildatenOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Column;
import de.hdm.gruppe7.partnerboerse.shared.report.CompositeParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Report;
import de.hdm.gruppe7.partnerboerse.shared.report.Row;
import de.hdm.gruppe7.partnerboerse.shared.report.SimpleParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

/**
 * Implementierung des <code>ReportGenerator</code>-Interface.
 * 
 * @see ReportGenerator
 *      ------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewuenscht, 
 * als Grundlage uebernommen und bei Notwendigkeit an die Beduerfnisse des 
 * IT-Projekts SS 2016 "Partnerboerse" angepasst.
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements
		ReportGenerator {

	/**
	 * Ein ReportGenerator benoeigt Zugriff auf die PartnerboerseAdministration,
	 * da diese die essentiellen Methoden fuer die Koexistenz von Datenobjekten
	 * (vgl. bo-Package) bietet.
	 */
	private PartnerboerseAdministration partnerboerseAdministration = null;

	/**
	 * No-Argument-Konstruktor.
	 * 
	 * @throws IllegalArgumentException
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialisierungsmethode.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	public void init() throws IllegalArgumentException {
		
		PartnerboerseAdministrationImpl p = new PartnerboerseAdministrationImpl();
		p.init();
		this.partnerboerseAdministration = p;
	}

	/**
	 * Zugeh�rige PartnerboerseAdministration auslesen (interner Geburach).
	 * 
	 * @return das PartnerboerseAdministration-Objekt.
	 */
	protected PartnerboerseAdministration getPartnerboerseAdministration() {
		return this.partnerboerseAdministration;
	}

	/**
	 * Die Methode soll dem Report ein Impressum hinzufuegen. Dazu wird zunaechst
	 * ein neuer CompositeParagraph angelegt, da das Impressum mehrzeilig sein soll.
	 * Danach werden belibige SimpleParagraph dem CompositeParagraph hinzugefuegt. Zum
	 * Schluss wird CompositeParagraph dem Report hinzugef�gt �ber setImprint.
	 * 
	 * @param r der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {

		CompositeParagraph imprint = new CompositeParagraph();

		imprint.addSubParagraph(new SimpleParagraph("Lonely Hearts"));

		r.setImprint(imprint);
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllInfosOfNutzerReport zurueckliefert. 
	 * Der Report stellt alle Infos eines Nutzerprofils dar.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.ReportGenerator#createAllInfosOfNutzerReport(Nutzerprofil)
	 */

	public AllInfosOfNutzerReport createAllInfosOfNutzerReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		AllInfosOfNutzerReport result = new AllInfosOfNutzerReport();

		result.setTitle(" ");

	
		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von
		 * Nutzerprofil-Informationen.
		 */

		/*
		 * Zunaechst legen wir eine Kopfzeile fuer die Info-Tabelle an.
		 */
		Row headline = new Row();
		
		headline.addColumn(new Column("Eigenschaft"));

		headline.addColumn(new Column("Infotext"));
		
		// Hinzufuegen der Kopfzeile
		result.addRow(headline);
		
		/*
		 * Nun werden saemtliche Infos des Nutzerprofils ausgelesen
		 */
		Map<List<Info>,List<Eigenschaft>> resultMap = this.partnerboerseAdministration.getAllInfos(np.getProfilId());
		
		Set<List<Info>> output = resultMap.keySet();
		
		for (List<Info> listI : output) {
			
			List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
			listE = resultMap.get(listI);
			
			for (int e = 0; e < listE.size(); e++) {
				
				// Eine leere Zeile anlegen.
				Row infoRow = new Row();
				
				infoRow.addColumn(new Column(listE.get(e).getErlaeuterung()));
				
					infoRow.addColumn(new Column(listI.get(e).getInfotext()));
				
//				und schliesslich die Zeile dem Report hinzufuegen.
				result.addRow(infoRow);	
			}
		}
		
		/*
		 * Zum Schluss muss der fertige Report zurueckgeben werden.
		 */
		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllProfildatenOfNutzerReport zurueckliefert.
	 * Der Report stellt alle Profildaten eines Nutzerprofils dar.
	 *  
	 * @see de.hdm.gruppe7.partnerboerse.shared.ReportGenerator#createAllProfildatenOfNutzerReport(Nutzerprofil)
	 */
	@Override
	public AllProfildatenOfNutzerReport createAllProfildatenOfNutzerReport(
			Nutzerprofil np) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunaechst wird ein leerer Report angelegt.
		 */
		AllProfildatenOfNutzerReport result = new AllProfildatenOfNutzerReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle(np.getVorname() + " " + np.getNachname());

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufuegen von Profildaten.
		 */

		/*
		 * Zunaechst legen wir eine Kopfzeile fuer die Konto-Tabelle an.
		 */
		Row headline = new Row();

		headline.addColumn(new Column("Profil-ID"));

		headline.addColumn(new Column("Vorname"));

		headline.addColumn(new Column("Nachname"));

		headline.addColumn(new Column("Geschlecht"));

		headline.addColumn(new Column("Geburtsdatum"));

		headline.addColumn(new Column("Körpergröße"));

		headline.addColumn(new Column("Haarfarbe"));

		headline.addColumn(new Column("Raucherstatus"));

		headline.addColumn(new Column("Religion"));

		headline.addColumn(new Column("Email"));

		// Hinzufuegen der Kopfzeile
		result.addRow(headline);
		
		/*
		 * Nun werden saemtliche Profildaten des Nutzerprofils ausgelesen
		 */

		Nutzerprofil n = this.partnerboerseAdministration.getNutzerprofilById(np
				.getProfilId());

		// Eine leere Zeile anlegen.
		Row profildatenoRow = new Row();

		// Spalten hinzufuegen
		profildatenoRow.addColumn(new Column(String.valueOf(n.getProfilId())));
		profildatenoRow.addColumn(new Column(n.getVorname()));
		profildatenoRow.addColumn(new Column(n.getNachname()));
		profildatenoRow.addColumn(new Column(n.getGeschlecht()));
		profildatenoRow.addColumn(new Column(String.valueOf(n
				.getGeburtsdatumDate())));
		profildatenoRow.addColumn(new Column(String.valueOf(n
				.getKoerpergroesseInt())));
		profildatenoRow.addColumn(new Column(n.getHaarfarbe()));
		profildatenoRow.addColumn(new Column(n.getRaucher()));
		profildatenoRow.addColumn(new Column(n.getReligion()));
		profildatenoRow.addColumn(new Column(n.getEmailAddress()));

		// und schlie�lich die Zeile dem Report hinzufuegen.
		result.addRow(profildatenoRow);

		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeNpReport zurueckliefert.
	 * Der Report stellt alle unangesehenen Partnervorschlaege eines Nutzerprofils dar.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.ReportGenerator#createAllPartnervorschlaegeNpReport(Nutzerprofil) 
	 */
	@Override
	public AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunaechst wird ein leerer Report angelegt.
		 */
		AllPartnervorschlaegeNpReport result = new AllPartnervorschlaegeNpReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle("Alle unangesehenen Partnervorschläge");

		// Imressum hinzufuegen
		this.addImprint(result);

		/*
		 * Erstellungsdatum hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier: Kopfdaten des Reports zusammenstellen. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzerprofils aufnehmen.
		header.addSubParagraph(new SimpleParagraph(np.getVorname() + " "
				+ np.getNachname()));

		// Nutzerprofil-ID aufnehmen.
		header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: "
				+ np.getProfilId()));

		// Zusammengestellte Kopfdaten zum Report hinzufuegen.
		result.setHeaderData(header);

		/*
		 * Nun werden saemtliche Nutzerprofil-Objekte ausgelesen.
		 * Anschlie�end wird fuer jedes Nutzerprofil-Objekt n ein Aufruf von
		 * createAllProfildatenOfNutzerReport(n) und ein Aufruf von 
		 * createAllInfosOfNutzerReport(n) durchgefuehrt und somit jeweils
		 * ein AllProfildatenOfNutzerReport-Objekt und ein AllInfosOfNutzerReport-Objekt
		 * erzeugt. Diese Objekte werden sukzessive der result-Variable hinzugefuegt. 
		 * Sie ist vom Typ AllPartnervorschlaegeNpReport, welches eine Subklasse von
		 * CompositeReport ist.
		 */

		List<Nutzerprofil> allNutzer = this.partnerboerseAdministration
				.getGeordnetePartnervorschlaegeNp(np.getProfilId());
		for (Nutzerprofil n : allNutzer) {
			/*
			 * Anlegen des jew. Teil-Reports und Hinzufuegen zum Gesamt-Report.
			 */

			result.addSubReport(this.createAllProfildatenOfNutzerReport(n));
			result.addSubReport(this.createAllInfosOfNutzerReport(n));

		}

		/*
		 *Fertigen Report zurueckgeben.
		 */
		return result;
	}

	/**
	  * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeSpReport zurueckliefert.
	 * Der Report stellt alle Partnervorschlaege, die anhand eines Suchprofils ermittelt wurden, 
	 * f�r ein Nutzerprofil dar.
	 * 
	 * @see de.hdm.gruppe7.partnerboerse.shared.ReportGenerator#createAllPartnervorschlaegeSpReport(Nutzerprofil, String)
	 */
	@Override
	public AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil,
			String suchprofilname) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Anlegen eines leeren Reports.
		 */
		AllPartnervorschlaegeSpReport result = new AllPartnervorschlaegeSpReport();

		// Jeder Report hat einen Titel (Bezeichnung / Ueberschrift).
		result.setTitle("Alle Partnervorschläge anhand des Suchprofils: "
				+ suchprofilname);

		// Imressum hinzufuegen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufuegen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier: Kopfdaten des Reports zusammenstellen. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzers aufnehmen.
		header.addSubParagraph(new SimpleParagraph(nutzerprofil.getVorname() + " "
				+ nutzerprofil.getNachname()));

		// Nutzerprofil-ID aufnehmen.
		header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: "
				+ nutzerprofil.getProfilId()));
		
		// Zusammengestellte Kopfdaten zum Report hinzufuegen.
		result.setHeaderData(header);
		/*
		 * Nun werden saemtliche Nutzerprofil-Objekte ausgelesen.
		 * Anschlie�end wird fuer jedes Nutzerprofil-Objekt n ein Aufruf von
		 * createAllProfildatenOfNutzerReport(n) und ein Aufruf von 
		 * createAllInfosOfNutzerReport(n) durchgefuehrt und somit jeweils
		 * ein AllProfildatenOfNutzerReport-Objekt und ein AllInfosOfNutzerReport-Objekt
		 * erzeugt. Diese Objekte werden sukzessive der result-Variable hinzugefuegt. 
		 * Sie ist vom Typ AllPartnervorschlaegeSpReport, welches eine Subklasse von
		 * CompositeReport ist.
		 */
		List<Nutzerprofil> allNutzer = this.partnerboerseAdministration
				.getGeordnetePartnervorschlaegeSp(nutzerprofil.getProfilId(), suchprofilname);

		for (Nutzerprofil np : allNutzer) {
			/*
			 * Anlegen des jew. Teil-Reports und Hinzufuegen zum Gesamt-Report.
			 */
			result.addSubReport(this.createAllProfildatenOfNutzerReport(np));
			result.addSubReport(this.createAllInfosOfNutzerReport(np));
		}

		/*
		 * Fertigen Report zurueckgeben.
		 */
		return result;
	}

	
	
	


}

