package de.hdm.gruppe7.partnerboerse.server.report;

import java.util.Date;
import java.util.List;









import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.client.ClientsideSettings;
import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.gruppe7.partnerboerse.server.db.InfoMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllSuchprofileOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Column;
import de.hdm.gruppe7.partnerboerse.shared.report.CompositeParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Report;
import de.hdm.gruppe7.partnerboerse.shared.report.Row;
import de.hdm.gruppe7.partnerboerse.shared.report.SimpleParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

/**
 * Implementierung des <code>ReportGenerator</code>-Interface. 
 * @see ReportGenerator
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewï¿½nscht, als Grundlage 
 * ï¿½bernommen und bei Notwendigkeit an die Bedï¿½rfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 * 
 * Modifizierender @author Milena Weinmann
 */

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements
		ReportGenerator {
	
	Nutzerprofil profil = new Nutzerprofil();

	private InfoMapper infoMapper = null;
	/**
	 * Ein ReportGenerator benï¿½tigt Zugriff auf die PartnerboerseAdministration, da diese die
	 * essentiellen Methoden fï¿½r die Koexistenz von Datenobjekten (vgl. bo-Package) bietet.
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
	     * Ein ReportGeneratorImpl-Objekt instantiiert fï¿½r seinen Eigenbedarf eine
	     * PartnerboerseAdministrationImpl-Instanz.
	     */
		PartnerboerseAdministrationImpl p = new PartnerboerseAdministrationImpl(); 
		p.init();
		this.partnerboerseAdministration = p; 
	}
	
	/**
	 *  Zugehï¿½rige PartnerboerseAdministration auslesen (interner Geburach). 
	 * @return das PartnerboerseAdministration-Objekt.
	 */
	protected PartnerboerseAdministration getPartnerboerseAdministration() {
		return this.partnerboerseAdministration; 
	}
	
	/**
	 *  Report-Impressum hinzufï¿½gen. 
	 * @param r der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {
		
	    /*
	     * Das Imressum soll mehrzeilig sein.
	     */
		CompositeParagraph imprint = new CompositeParagraph(); 
		
		imprint.addSubParagraph(new SimpleParagraph("Partnerboerse"));
		imprint.addSubParagraph(new SimpleParagraph("XYZ")); 
		
		/*
		 * Impressum zum Report hinzufï¿½gen.
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

		// Jeder Report hat einen Titel (Bezeichnung / ï¿½berschrift).
		result.setTitle("Suchprofil-Report fuer " + n.getVorname() + " " + n.getNachname()); 

		// Imressum hinzufï¿½gen.
		this.addImprint(result);

		/*
		 *  Erstellungsdatum hinzufï¿½gen.
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

		// Zusammengestellte Kopfdaten zum Report hinzufï¿½gen.
		result.setHeaderData(header);
		
	    /*
	     * Ab hier: Suchprofil-Informationen zeilenweise hinzufï¿½gen.
	     */
		// Kopfzeile fï¿½r die Suchprofil-Tabelle anlegen.
		Row headline = new Row();

		// ï¿½berschriften der Kopfzeile ablegen.
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Suchprofilname"));
		headline.addColumn(new Column("Geschlecht"));
		headline.addColumn(new Column("Alter von"));
		headline.addColumn(new Column("Alter bis"));
		headline.addColumn(new Column("Kï¿½rpergrï¿½ï¿½e"));
		headline.addColumn(new Column("Haarfarbe"));
		headline.addColumn(new Column("Raucherstatus"));
		headline.addColumn(new Column("Religion"));

		// Kopfzeile hinzufï¿½gen.
		result.addRow(headline);

	    /*
	     * Sï¿½mtliche Suchprofile des Nutzers ausgelesen und in die Tabelle eintragen.
	     */
		List<Suchprofil> suchprofile = this.partnerboerseAdministration.getAllSuchprofileFor(n); 

		for (Suchprofil s : suchprofile) {
			
			// Eine leere Zeile anlegen.
			Row suchprofilRow = new Row();

			// Zeile befï¿½llen.
			suchprofilRow.addColumn(new Column(String.valueOf(s.getProfilId())));
			suchprofilRow.addColumn(new Column(s.getSuchprofilName()));
			suchprofilRow.addColumn(new Column(s.getGeschlecht()));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getAlterMinInt())));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getAlterMaxInt())));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getKoerpergroesseInt())));
			suchprofilRow.addColumn(new Column(s.getHaarfarbe()));
			suchprofilRow.addColumn(new Column(s.getRaucher()));
			suchprofilRow.addColumn(new Column(s.getReligion()));

			// Zeile dem Report hinzufï¿½gen.
			result.addRow(suchprofilRow);
		}

	    /*
	     * Fertigen Report zurï¿½ckgeben.
	     */
		return result;

	}

	
	
	
	/**
	   * Erstellen von <code>AllInfosOfNutzerReport</code>-Objekten.
	   * 
	   */
	  @Override
	public AllInfosOfNutzerReport createAllInfosOfNutzerReport(
	      Nutzerprofil np) throws IllegalArgumentException {

	    if (this.getPartnerboerseAdministration() == null)
	      return null;

	    /*
	     * Zunächst legen wir uns einen leeren Report an.
	     */
	    AllInfosOfNutzerReport result = new AllInfosOfNutzerReport();

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
		header.addSubParagraph(new SimpleParagraph(np.getNachname() + ", "
				+ np.getVorname()));

		// Kundennummer aufnehmen
		header.addSubParagraph(new SimpleParagraph("Profil-ID.: "
				+ np.getProfilId()));

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
	  	
	    headline.addColumn(new Column("Eigenschaft"));
		
		 headline.addColumn(new Column("Infotext"));

	    // Hinzufügen der Kopfzeile
	    result.addRow(headline);

	    /*
	     * Nun werden sämtliche Infos des Kunden ausgelesen 
	     */
	    
	    
	    List<Info> infos = this.partnerboerseAdministration.getAllInfosNeuReport(np.getProfilId());

	    for (Info i : infos) {
		
			
	      // Eine leere Zeile anlegen.
	      Row infoRow = new Row();

	      // Spalten  hinzufügen

	    infoRow.addColumn(new Column(String.valueOf(i.getEigenschaftId())));
	    //infoRow.addColumn(new Column(this.partnerboerseAdministration.getEigenschaftstextById(i.getEigenschaftId())));
	    infoRow.addColumn(new Column(i.getInfotext()));

	      // und schließlich die Zeile dem Report hinzufügen.
	      result.addRow(infoRow);
	    }

	    /*
	     * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
	     */
	    return result;
	  }

	
	  /**
	   * Erstellen von <code>AllPartnervorschlaegeNpReport</code>-Objekten.
	   * 
	   * @return der fertige Report
	   */
	  @Override
	public AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport()
	      throws IllegalArgumentException {

	    if (this.getPartnerboerseAdministration() == null)
	      return null;

	    /*
	     * Zunächst legen wir uns einen leeren Report an.
	     */
	    AllPartnervorschlaegeNpReport result = new AllPartnervorschlaegeNpReport();

	    // Jeder Report hat einen Titel (Bezeichnung / überschrift).
	    result.setTitle("Alle unangesegenen Partnervorschlaege und deren Infos");

	    // Imressum hinzufügen
	    this.addImprint(result);

	 		/*
	 		 *  Erstellungsdatum hinzufï¿½gen.
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
		header.addSubParagraph(new SimpleParagraph(profil.getVorname() + " " + profil.getNachname())); 
		
		// Nutzerprofil-ID aufnehmen.
	    header.addSubParagraph(new SimpleParagraph("Nutzerprofil-ID: " + profil.getProfilId()));

		// Zusammengestellte Kopfdaten zum Report hinzufï¿½gen.
		result.setHeaderData(header);
	    /*
	     * Da AllAccountsOfAllCustomersReport-Objekte aus einer Sammlung von
	     * AllAccountsOfCustomerReport-Objekten besteht, benötigen wir keine
	     * Kopfdaten für diesen Report-Typ. Wir geben einfach keine Kopfdaten an...
	     */

	    /*
	     * Nun müssen sämtliche Kunden-Objekte ausgelesen werden. Anschließend wir
	     * für jedes Kundenobjekt c ein Aufruf von
	     * createAllAccountsOfCustomerReport(c) durchgeführt und somit jeweils ein
	     * AllAccountsOfCustomerReport-Objekt erzeugt. Diese Objekte werden
	     * sukzessive der result-Variable hinzugefügt. Sie ist vom Typ
	     * AllAccountsOfAllCustomersReport, welches eine Subklasse von
	     * CompositeReport ist.
	     */
		
	    List<Nutzerprofil> allNutzer = this.partnerboerseAdministration.getGeordnetePartnervorschlaegeNp();

	    for (Nutzerprofil np : allNutzer) {
	      /*
	       * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
	       */
	      result.addSubReport(this.createAllInfosOfNutzerReport(np));
	    }

	    /*
	     * Zu guter Letzt müssen wir noch den fertigen Report zurückgeben.
	     */
	    return result;
	  }

	
	  /**
	   * Erstellen von <code>AllPartnervorschlaegeSpReport</code>-Objekten.
	   * 
	   * @return der fertige Report
	   */
	  @Override
	public AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport()
	      throws IllegalArgumentException {

	    if (this.getPartnerboerseAdministration() == null)
	      return null;

	    /*
	     * Zunächst legen wir uns einen leeren Report an.
	     */
	    AllPartnervorschlaegeSpReport result = new AllPartnervorschlaegeSpReport();

	    // Jeder Report hat einen Titel (Bezeichnung / überschrift).
	    result.setTitle("Alle unangesegenen Partnervorschlaege und deren Infos");

	    // Imressum hinzufügen
	    this.addImprint(result);

	    /*
	     * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
	     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
	     */
	    result.setCreated(new Date());

	    /*
	     * Da AllAccountsOfAllCustomersReport-Objekte aus einer Sammlung von
	     * AllAccountsOfCustomerReport-Objekten besteht, benötigen wir keine
	     * Kopfdaten für diesen Report-Typ. Wir geben einfach keine Kopfdaten an...
	     */

	    /*
	     * Nun müssen sämtliche Kunden-Objekte ausgelesen werden. Anschließend wir
	     * für jedes Kundenobjekt c ein Aufruf von
	     * createAllAccountsOfCustomerReport(c) durchgeführt und somit jeweils ein
	     * AllAccountsOfCustomerReport-Objekt erzeugt. Diese Objekte werden
	     * sukzessive der result-Variable hinzugefügt. Sie ist vom Typ
	     * AllAccountsOfAllCustomersReport, welches eine Subklasse von
	     * CompositeReport ist.
	     */
	    List<Nutzerprofil> allNutzer = this.partnerboerseAdministration.getGeordnetePartnervorschlaegeSp("PerfectBoy");

	    for (Nutzerprofil np : allNutzer) {
	      /*
	       * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
	       */
	      result.addSubReport(this.createAllInfosOfNutzerReport(np));
	    }

	    /*
	     * Zu guter Letzt müssen wir noch den fertigen Report zurückgeben.
	     */
	    return result;
	  }
}