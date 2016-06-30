package de.hdm.gruppe7.partnerboerse.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.PartnerboerseAdministrationImpl;
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
 * Implementierung des ReportGenerator-Interface.
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
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException {
		
		PartnerboerseAdministrationImpl p = new PartnerboerseAdministrationImpl();
		p.init();
		this.partnerboerseAdministration = p;
	}

	/**
	 * Zugehoerige PartnerboerseAdministration auslesen (interner Geburach).
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
	 * Schluss wird CompositeParagraph dem Report hinzugefuegt ueber setImprint.
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
	 * @param np Nutzerprofil-Objekt
	 * @return AllInfosOfNutzerReport Fertiges Report-Objekt vom Typ AllInfosOfNutzerReport
	 * @throws IllegalArgumentException
	 */

	public AllInfosOfNutzerReport createAllInfosOfNutzerReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		AllInfosOfNutzerReport result = new AllInfosOfNutzerReport();

		result.setTitle(" ");

		
		Row headline = new Row();
		
		headline.addColumn(new Column("Eigenschaft"));

		headline.addColumn(new Column("Infotext"));
		
		result.addRow(headline);
		
		Map<List<Info>,List<Eigenschaft>> resultMap = this.partnerboerseAdministration.getAllInfos(np.getProfilId());
		
		Set<List<Info>> output = resultMap.keySet();
		
		for (List<Info> listI : output) {
			
			List<Eigenschaft> listE = new ArrayList<Eigenschaft>();
			listE = resultMap.get(listI);
			
			for (int e = 0; e < listE.size(); e++) {
				
				Row infoRow = new Row();
				
				infoRow.addColumn(new Column(listE.get(e).getErlaeuterung()));
				
					infoRow.addColumn(new Column(listI.get(e).getInfotext()));
				
				result.addRow(infoRow);	
			}
		}
		
		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllProfildatenOfNutzerReport zurueckliefert.
	 * Der Report stellt alle Profildaten eines Nutzerprofils dar.
	 * 
	 * @param np Nutzerorifil-Objekt
	 * @return AllProfildatenOfNutzerReport Fertiges Report-Objekt vom Typ AllProfildatenOfNutzerReport
	 * @throws IllegalArgumentException
	 */
	@Override
	public AllProfildatenOfNutzerReport createAllProfildatenOfNutzerReport(
			Nutzerprofil np) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		AllProfildatenOfNutzerReport result = new AllProfildatenOfNutzerReport();

		result.setTitle(np.getVorname() + " " + np.getNachname());

		Row headline = new Row();

		headline.addColumn(new Column("Vorname"));

		headline.addColumn(new Column("Nachname"));

		headline.addColumn(new Column("Geschlecht"));

		headline.addColumn(new Column("Geburtsdatum"));

		headline.addColumn(new Column("Körpergröße"));

		headline.addColumn(new Column("Haarfarbe"));

		headline.addColumn(new Column("Raucherstatus"));

		headline.addColumn(new Column("Religion"));

		headline.addColumn(new Column("Email"));

		result.addRow(headline);

		Nutzerprofil n = this.partnerboerseAdministration.getNutzerprofilById(np
				.getProfilId());

		Row profildatenoRow = new Row();

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

		result.addRow(profildatenoRow);

		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeNpReport zurueckliefert.
	 * Der Report stellt alle unangesehenen Partnervorschlaege eines Nutzerprofils dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @return AllPartnervorschlaegeNpReport Fertiges Report-Objekt vom Typ AllPartnervorschlaegeNpReport
	 * @throws IllegalArgumentException
	 */
	@Override
	public AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport(Nutzerprofil np)
			throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		AllPartnervorschlaegeNpReport result = new AllPartnervorschlaegeNpReport();

		result.setTitle("Alle unangesehenen Partnervorschläge");

		this.addImprint(result);

		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();

		header.addSubParagraph(new SimpleParagraph(np.getVorname() + " "
				+ np.getNachname()));

		result.setHeaderData(header);


		List<Nutzerprofil> allNutzer = this.partnerboerseAdministration
				.getGeordnetePartnervorschlaegeNp(np.getProfilId());
		for (Nutzerprofil n : allNutzer) {

			result.addSubReport(this.createAllProfildatenOfNutzerReport(n));
			result.addSubReport(this.createAllInfosOfNutzerReport(n));

		}

		return result;
	}

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeSpReport zurueckliefert.
	 * Der Report stellt alle Partnervorschlaege, die anhand eines Suchprofils ermittelt wurden, 
	 * guer ein Nutzerprofil dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @param suchprofilname Name des Suchprofil-Objektes
	 * @return AllPartnervorschlaegeSpReport Fertiges Report-Objekt vom Typ AllPartnervorschlaegeSpReport
	 * @throws IllegalArgumentException
	 */
	@Override
	public AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil,
			String suchprofilname) throws IllegalArgumentException {

		if (this.getPartnerboerseAdministration() == null)
			return null;

		AllPartnervorschlaegeSpReport result = new AllPartnervorschlaegeSpReport();

		result.setTitle("Alle Partnervorschläge anhand des Suchprofils: "
				+ suchprofilname);

		this.addImprint(result);

		result.setCreated(new Date());

		CompositeParagraph header = new CompositeParagraph();

		header.addSubParagraph(new SimpleParagraph(nutzerprofil.getVorname() + " "
				+ nutzerprofil.getNachname()));
		
		result.setHeaderData(header);

		List<Nutzerprofil> allNutzer = this.partnerboerseAdministration
				.getGeordnetePartnervorschlaegeSp(nutzerprofil.getProfilId(), suchprofilname);

		for (Nutzerprofil np : allNutzer) {

			result.addSubReport(this.createAllProfildatenOfNutzerReport(np));
			result.addSubReport(this.createAllInfosOfNutzerReport(np));
		}

		return result;
	}


}

