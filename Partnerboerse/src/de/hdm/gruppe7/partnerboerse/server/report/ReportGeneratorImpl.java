package de.hdm.gruppe7.partnerboerse.server.report;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.ReportGenerator;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllSuchprofileOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.Column;
import de.hdm.gruppe7.partnerboerse.shared.report.CompositeParagraph;
import de.hdm.gruppe7.partnerboerse.shared.report.Report;
import de.hdm.gruppe7.partnerboerse.shared.report.Row;
import de.hdm.gruppe7.partnerboerse.shared.report.SimpleParagraph;

/**
 * Implementierung des <code>ReportGenerator</code>-Interface. 
 * @see ReportGenerator
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewünscht, als Grundlage 
 * übernommen und bei Notwendigkeit an die Bedürfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 * 
 * Modifizierender @author Milena Weinmann
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements
		ReportGenerator {

	/**
	 * Ein ReportGenerator benötigt Zugriff auf die PartnerboerseAdministration, da diese die
	 * essentiellen Methoden für die Koexistenz von Datenobjekten (vgl. bo-Package) bietet.
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
	     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
	     * PartnerboerseAdministrationImpl-Instanz.
	     */
		PartnerboerseAdministrationImpl p = new PartnerboerseAdministrationImpl(); 
		p.init();
		this.partnerboerseAdministration = p; 
	}
	
	/**
	 *  Zugehörige PartnerboerseAdministration auslesen (interner Geburach). 
	 * @return das PartnerboerseAdministration-Objekt.
	 */
	protected PartnerboerseAdministration getPartnerboerseAdministration() {
		return this.partnerboerseAdministration; 
	}
	
	/**
	 *  Report-Impressum hinzufügen. 
	 * @param r der um das Impressum zu erweiternde Report.
	 */
	protected void addImprint(Report r) {
		
	    /*
	     * Das Imressum soll mehrzeilig sein.
	     */
		CompositeParagraph imprint = new CompositeParagraph(); 
		
		imprint.addSubParagraph(new SimpleParagraph("Partnerbörse"));
		imprint.addSubParagraph(new SimpleParagraph("XYZ")); 
		
		/*
		 * Impressum zum Report hinzufügen.
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

		// Jeder Report hat einen Titel (Bezeichnung / Überschrift).
		result.setTitle("Suchprofil-Report für " + n.getVorname() + " " + n.getNachname()); 

		// Imressum hinzufügen.
		this.addImprint(result);

		/*
		 *  Erstellungsdatum hinzufügen.
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

		// Zusammengestellte Kopfdaten zum Report hinzufügen.
		result.setHeaderData(header);
		
	    /*
	     * Ab hier: Suchprofil-Informationen zeilenweise hinzufügen.
	     */
		// Kopfzeile für die Suchprofil-Tabelle anlegen.
		Row headline = new Row();

		// Überschriften der Kopfzeile ablegen.
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Suchprofilname"));
		headline.addColumn(new Column("Geschlecht"));
		headline.addColumn(new Column("Alter von"));
		headline.addColumn(new Column("Alter bis"));
		headline.addColumn(new Column("Körpergröße"));
		headline.addColumn(new Column("Haarfarbe"));
		headline.addColumn(new Column("Raucherstatus"));
		headline.addColumn(new Column("Religion"));

		// Kopfzeile hinzufügen.
		result.addRow(headline);

	    /*
	     * Sämtliche Suchprofile des Nutzers ausgelesen und in die Tabelle eintragen.
	     */
		List<Suchprofil> suchprofile = this.partnerboerseAdministration.getAllSuchprofileFor(n); 

		for (Suchprofil s : suchprofile) {
			
			// Eine leere Zeile anlegen.
			Row suchprofilRow = new Row();

			// Zeile befüllen.
			suchprofilRow.addColumn(new Column(String.valueOf(s.getProfilId())));
			suchprofilRow.addColumn(new Column(s.getSuchprofilName()));
			suchprofilRow.addColumn(new Column(s.getGeschlecht()));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getAlterMinInt())));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getAlterMaxInt())));
			suchprofilRow.addColumn(new Column(String.valueOf(s.getKoerpergroesseInt())));
			suchprofilRow.addColumn(new Column(s.getHaarfarbe()));
			suchprofilRow.addColumn(new Column(s.getRaucher()));
			suchprofilRow.addColumn(new Column(s.getReligion()));

			// Zeile dem Report hinzufügen.
			result.addRow(suchprofilRow);
		}

	    /*
	     * Fertigen Report zurückgeben.
	     */
		return result;

	}

}
