package de.hdm.gruppe7.partnerboerse.server.report;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
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
 *      ------------------ Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in
 *      der Vorlesung gewï¿½nscht, als Grundlage ï¿½bernommen und bei
 *      Notwendigkeit an die Bedï¿½rfnisse des IT-Projekts SS 2016
 *      "Partnerboerse" angepasst.
 * 
 *      Modifizierender @author Milena Weinmann
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements
		ReportGenerator {

	Nutzerprofil profil = new Nutzerprofil();

	/**
	 * Ein ReportGenerator benï¿½tigt Zugriff auf die
	 * PartnerboerseAdministration, da diese die essentiellen Methoden fï¿½r die
	 * Koexistenz von Datenobjekten (vgl. bo-Package) bietet.
	 */
	private PartnerboerseAdministration partnerboerseAdministration = null;

	public void setUser(Nutzerprofil n) {
		this.profil = n;
	}

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
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert fï¿½r seinen Eigenbedarf
		 * eine PartnerboerseAdministrationImpl-Instanz.
		 */
		PartnerboerseAdministrationImpl p = new PartnerboerseAdministrationImpl();
		p.init();
		this.partnerboerseAdministration = p;
	}

	/**
	 * Zugehï¿½rige PartnerboerseAdministration auslesen (interner Geburach).
	 * 
	 * @return das PartnerboerseAdministration-Objekt.
	 */
	protected PartnerboerseAdministration getPartnerboerseAdministration() {
		return this.partnerboerseAdministration;
	}

	/**
	 * Report-Impressum hinzufï¿½gen.
	 * 
	 * @param r
	 *            der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {

		/*
		 * Das Imressum soll mehrzeilig sein.
		 */
		CompositeParagraph imprint = new CompositeParagraph();

		imprint.addSubParagraph(new SimpleParagraph("Lonely Hearts"));

		/*
		 * Impressum zum Report hinzufï¿½gen.
		 */
		r.setImprint(imprint);
	}

	/**
	 * Erstellen von <code>AllInfosOfNutzerReport</code>-Objekten.
	 * 
	 */

	public AllInfosOfNutzerReport createAllInfosOfNutzerReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunï¿½chst legen wir uns einen leeren Report an.
		 */
		AllInfosOfNutzerReport result = new AllInfosOfNutzerReport();

		// Jeder Report hat einen Titel (Bezeichnung / ï¿½berschrift).
		result.setTitle(" ");

		// Imressum hinzufï¿½gen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufï¿½gen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die
		 * oben auf dem Report stehen) des Reports. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(new SimpleParagraph("Infos von: \n"
				+ np.getNachname() + ", " + np.getVorname()));

		// // Kundennummer aufnehmen
		// header.addSubParagraph(new SimpleParagraph("Profil-ID.: "
		// + np.getProfilId()));

		// Hinzufï¿½gen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufï¿½gen von
		 * Konto-Informationen.
		 */

		/*
		 * Zunï¿½chst legen wir eine Kopfzeile fï¿½r die Konto-Tabelle an.
		 */
		Row headline = new Row();

		/*
		 * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
		 * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
		 * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
		 * ï¿½berschriften ab.
		 */

		headline.addColumn(new Column("Eigenschaft"));

		headline.addColumn(new Column("Infotext"));

		// Hinzufï¿½gen der Kopfzeile
		result.addRow(headline);

		/*
		 * Nun werden sï¿½mtliche Infos des Kunden ausgelesen
		 */

		List<Info> infos = this.partnerboerseAdministration
				.getAllInfosNeuReport(np.getProfilId());

		for (Info i : infos) {

			// Eine leere Zeile anlegen.
			Row infoRow = new Row();

			// Spalten hinzufï¿½gen

			// infoRow.addColumn(new
			// Column(String.valueOf(i.getEigenschaftId())));
			infoRow.addColumn(new Column(this.partnerboerseAdministration
					.getEigenschaftstextById(i.getEigenschaftId())));
			infoRow.addColumn(new Column(i.getInfotext()));

			// und schlieï¿½lich die Zeile dem Report hinzufï¿½gen.
			result.addRow(infoRow);
		}

		/*
		 * Zum Schluss mï¿½ssen wir noch den fertigen Report zurï¿½ckgeben.
		 */
		return result;
	}

	@Override
	public AllProfildatenOfNutzerReport createAllProfildatenOfNutzerReport(
			Nutzerprofil np) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunächst legen wir uns einen leeren Report an.
		 */
		AllProfildatenOfNutzerReport result = new AllProfildatenOfNutzerReport();

		// Jeder Report hat einen Titel (Bezeichnung / Überschrift).
		result.setTitle(np.getVorname() + " " + np.getNachname());

		// Imressum hinzufügen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die
		 * oben auf dem Report stehen) des Reports. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(new SimpleParagraph("Profildaten von: "
				+ np.getNachname() + ", " + np.getVorname()));

		// // Kundennummer aufnehmen
		// header.addSubParagraph(new SimpleParagraph("Profil-ID.: "
		// + np.getProfilId()));

		// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		/*
		 * Ab hier erfolgt ein zeilenweises Hinzufügen von Konto-Informationen.
		 */

		/*
		 * Zunächst legen wir eine Kopfzeile für die Konto-Tabelle an.
		 */
		Row headline = new Row();

		/*
		 * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
		 * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
		 * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
		 * Überschriften ab.
		 */

		headline.addColumn(new Column("Profil-ID"));

		headline.addColumn(new Column("Vorname"));

		headline.addColumn(new Column("Nachname"));

		headline.addColumn(new Column("Geschlecht"));

		headline.addColumn(new Column("Geburtsdatum"));

		headline.addColumn(new Column("Koerpergroessse"));

		headline.addColumn(new Column("Haarfarbe"));

		headline.addColumn(new Column("Raucherstatus"));

		headline.addColumn(new Column("Religion"));

		headline.addColumn(new Column("Email"));

		// Hinzufügen der Kopfzeile
		result.addRow(headline);
		/*
		 * Nun werden sämtliche Infos des Kunden ausgelesen
		 */

		Nutzerprofil n = this.partnerboerseAdministration.getFremdprofilById(np
				.getProfilId());

		System.out.println("Profil geholt:" + n.getVorname() + n.getNachname()
				+ n.getGeschlecht() + n.getGeburtsdatumDate()
				+ n.getKoerpergroesseInt() + n.getHaarfarbe() + n.getRaucher()
				+ n.getReligion() + n.getEmailAddress());
		// Eine leere Zeile anlegen.
		Row profildatenoRow = new Row();

		// Spalten hinzufügen
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

		// und schließlich die Zeile dem Report hinzufügen.
		result.addRow(profildatenoRow);

		return result;
	}

	/**
	 * Erstellen von <code>AllPartnervorschlaegeNpReport</code>-Objekten.
	 * 
	 * @return der fertige Report
	 */
	@Override
	public AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunï¿½chst legen wir uns einen leeren Report an.
		 */
		AllPartnervorschlaegeNpReport result = new AllPartnervorschlaegeNpReport();

		// Jeder Report hat einen Titel (Bezeichnung / überschrift).
		result.setTitle("Alle unangesehenen Partnervorschlaege");

		// Imressum hinzufï¿½gen
		this.addImprint(result);

		/*
		 * Erstellungsdatum hinzufï¿½gen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier: Kopfdaten des Reports zusammenstellen. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Nutzers aufnehmen.
		header.addSubParagraph(new SimpleParagraph(np.getVorname() + " "
				+ np.getNachname()));

		// Nutzerprofil-ID aufnehmen.
		header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: "
				+ np.getProfilId()));

		// Zusammengestellte Kopfdaten zum Report hinzufï¿½gen.
		result.setHeaderData(header);
		/*
		 * Da AllAccountsOfAllCustomersReport-Objekte aus einer Sammlung von
		 * AllAccountsOfCustomerReport-Objekten besteht, benï¿½tigen wir keine
		 * Kopfdaten fï¿½r diesen Report-Typ. Wir geben einfach keine Kopfdaten
		 * an...
		 */

		/*
		 * Nun mï¿½ssen sï¿½mtliche Kunden-Objekte ausgelesen werden.
		 * Anschlieï¿½end wir fï¿½r jedes Kundenobjekt c ein Aufruf von
		 * createAllAccountsOfCustomerReport(c) durchgefï¿½hrt und somit jeweils
		 * ein AllAccountsOfCustomerReport-Objekt erzeugt. Diese Objekte werden
		 * sukzessive der result-Variable hinzugefï¿½gt. Sie ist vom Typ
		 * AllAccountsOfAllCustomersReport, welches eine Subklasse von
		 * CompositeReport ist.
		 */

		List<Nutzerprofil> allNutzer = this.partnerboerseAdministration
				.getGeordnetePartnervorschlaegeNp(np.getProfilId());
		System.out.println("Partnervorschlaege:" + allNutzer);
		for (Nutzerprofil n : allNutzer) {
			/*
			 * Anlegen des jew. Teil-Reports und Hinzufï¿½gen zum Gesamt-Report.
			 */

			result.addSubReport(this.createAllProfildatenOfNutzerReport(n));
			result.addSubReport(this.createAllInfosOfNutzerReport(n));

		}

		/*
		 * Zu guter Letzt mï¿½ssen wir noch den fertigen Report zurï¿½ckgeben.
		 */
		return result;
	}

	/**
	 * Erstellen von <code>AllPartnervorschlaegeSpReport</code>-Objekten.
	 * 
	 * @return der fertige Report
	 */
	@Override
	public AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil,
			String suchprofilname) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		/*
		 * Zunï¿½chst legen wir uns einen leeren Report an.
		 */
		AllPartnervorschlaegeSpReport result = new AllPartnervorschlaegeSpReport();

		// Jeder Report hat einen Titel (Bezeichnung / überschrift).
		result.setTitle("Alle Partnervorschlaege anhand des Suchprofils: "
				+ suchprofilname);

		// Imressum hinzufï¿½gen
		this.addImprint(result);

		/*
		 * Datum der Erstellung hinzufï¿½gen. new Date() erzeugt autom. einen
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
		
		// Zusammengestellte Kopfdaten zum Report hinzufï¿½gen.
		result.setHeaderData(header);
		/*
		 * Nun mï¿½ssen sï¿½mtliche Kunden-Objekte ausgelesen werden.
		 * Anschlieï¿½end wir fï¿½r jedes Kundenobjekt c ein Aufruf von
		 * createAllAccountsOfCustomerReport(c) durchgefï¿½hrt und somit jeweils
		 * ein AllAccountsOfCustomerReport-Objekt erzeugt. Diese Objekte werden
		 * sukzessive der result-Variable hinzugefï¿½gt. Sie ist vom Typ
		 * AllAccountsOfAllCustomersReport, welches eine Subklasse von
		 * CompositeReport ist.
		 */
		List<Nutzerprofil> allNutzer = this.partnerboerseAdministration
				.getGeordnetePartnervorschlaegeSp(nutzerprofil.getProfilId(), suchprofilname);

		for (Nutzerprofil np : allNutzer) {
			/*
			 * Anlegen des jew. Teil-Reports und Hinzufï¿½gen zum Gesamt-Report.
			 */
			result.addSubReport(this.createAllProfildatenOfNutzerReport(np));
			result.addSubReport(this.createAllInfosOfNutzerReport(np));
		}

		/*
		 * Zu guter Letzt mï¿½ssen wir noch den fertigen Report zurï¿½ckgeben.
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

}