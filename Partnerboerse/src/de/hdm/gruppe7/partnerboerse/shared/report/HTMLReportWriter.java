package de.hdm.gruppe7.partnerboerse.shared.report;

import java.util.Vector;

/**
 * Ein ReportWriter, der Reports mittels HTML formatiert. Das imZielformat vorliegende 
 * Ergebnis wird in der Variable reportText abgelegt und kann nach Aufruf der 
 * entsprechenden Prozessierungsmethode mit getReportText() ausgelesen werden.
 * 
 * @author Thies 
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Thies in der Vorlesung gewuenscht, als Grundlage 
 * uebernommen und beiNotwendigkeit an die Beduerfnisse des IT-Projekts SS 2016 "Partnerboerse" angepasst.
 */

public class HTMLReportWriter extends ReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * process...-Methoden) belegt. Format: HTML-Text
	 */
	private String reportText = "";

	/**
	 * Zuruecksetzen der Variable reportText.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Umwandeln eines Paragraph-Objekts in HTML.
	 * 
	 * @param p der Paragraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraph2HTML((CompositeParagraph) p);
		} else {
			return this.paragraph2HTML((SimpleParagraph) p);
		}
	}

	/**
	 * Umwandeln eines CompositeParagraph-Objekts in HTML.
	 *  
	 * @param p der CompositeParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * Umwandeln eines SimpleParagraph-Objekts in HTML.
	 * 
	 * @param p der SimpleParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(SimpleParagraph p) {
		return "<p>" + p.toString() + "</p>";
	}

	/**
	 * HTML-Header-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}

	/**
	 * HTML-Trailer-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getTrailer() {
		return "</body></html>";
	}
	
	/**
	 * Zunaechst wird ein Buffer angelegt, in den waehrend der Prozessierung die Ergebnisse 
	 * geschtieben werden. Danach werden nach und nach alle Bestandteile des Reports ausgelesen 
	 * und in HTML-Form uebersetzt. Am Ende wird der Buffer in einen String umgewandelt und der 
	 * reportText-Variable zugewiesen. Dies ermoeglich,das Ergebnis durch getReportText() auszulesen.
	 * 
	 * @param AllInfosOfNutzerReport 
	 */
	@Override
	public void process(AllInfosOfNutzerReport r) {
		
		this.resetReportText();

		StringBuffer result = new StringBuffer();


		result.append("<H3>" + r.getTitle() + "</H3>");


		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:400px;margin-bottom: 30px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:#7c9ef8;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver;margin-bottom: 30px\">"
								+ row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");
	
		this.reportText = result.toString();

	}

	/**
	 * Zunaechst wird ein Buffer angelegt, in den waehrend der Prozessierung die Ergebnisse 
	 * geschtieben werden. Danach werden nach und nach alle Bestandteile des Reports ausgelesen 
	 * und in HTML-Form uebersetzt. Am Ende wird der Buffer in einen String umgewandelt und der 
	 * reportText-Variable zugewiesen. Dies ermoeglich,das Ergebnis durch getReportText() auszulesen.
	 * 
	 * @param AllProfildatenOfNutzerReport 
	 */
	@Override
	public void process(AllProfildatenOfNutzerReport r) {
		this.resetReportText();

		StringBuffer result = new StringBuffer();


		result.append("<H3>" + r.getTitle() + "</H3>");


		Vector<Row> rows = r.getRows();
		result.append("<table style=\"width:400px\">");

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getNumColumns(); k++) {
				if (i == 0) {
					result.append("<td style=\"background:#7c9ef8;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver\">" + row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");

		this.reportText = result.toString();
	}

/**
 * Zu Beginn wird das Ergebnis vorhergehender Prozessierungen geloescht. Danach wird ein Buffer 
 * angelegt, in den waehrend der Prozessierung die Ergebnisse geschtieben werden. Danach werden 
 * nach und nach alle Bestandteile des Reports ausgelesen und in HTML-Form uebersetzt. Bei dem
 * AllPartnervorschlaegeNpReport handelt es sich um einen CompositeReport. Daher ist in r 
 * jeweils eine Telmenge der Subreports AllInfosOdNutzerReport und AllProfildatenOdNutzerReport
 * enthallten, fur die jeweils wieder die zugehoerige process-Methode aufgerufen wird. Dieses 
 * Ergebnis wird dann jeweils zum Buffer hinzugef�gt. Nach jeder Uebersetzung und Auslesen
 * eines Teilreports muss zunaechst noch einmal die Ergebnisvariable zurueckgestzt werden.Am 
 * Ende wird der Buffer in einen String umgewandelt und der reportText-Variable zugewiesen. 
 * Dies ermoeglich,das Ergebnis durch getReportText() auszulesen.
 * 
 * @param AllPartnervorschlaegeNpReport
 */
	@Override
	public void process(AllPartnervorschlaegeNpReport r) {

		this.resetReportText();

		StringBuffer result = new StringBuffer();


		result.append("<H3>" + r.getTitle() + "</H3>");
		result.append("<table><tr>");
		result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
		if (r.getHeaderData() != null) {
			result.append("<td>" + paragraph2HTML(r.getHeaderData()) + "</td>");
		}

		result.append("<td>" + paragraph2HTML(r.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

		

		for (int j = 0; j < r.getNumSubReports(); j = j + 2) {

			AllProfildatenOfNutzerReport subReport2 = (AllProfildatenOfNutzerReport) r.getSubReportAt(j);

			this.process(subReport2);

			result.append(this.reportText + "\n");

		
			AllInfosOfNutzerReport subReport = (AllInfosOfNutzerReport) r.getSubReportAt(j + 1);

			this.process(subReport);

			result.append(this.reportText + "\n");
		}
		
		this.reportText = result.toString();

	}

	/**
	 * Zu Beginn wird das Ergebnis vorhergehender Prozessierungen geloescht. Danach wird ein Buffer 
	 * angelegt, in den waehrend der Prozessierung die Ergebnisse geschtieben werden. Danach werden 
	 * nach und nach alle Bestandteile des Reports ausgelesen und in HTML-Form uebersetzt. Bei dem
	 * AllPartnervorschlaegeNpReport handelt es sich um einen CompositeReport. Daher ist in r 
	 * jeweils eine Telmenge der Subreports AllInfosOdNutzerReport und AllProfildatenOdNutzerReport
	 * enthallten, fur die jeweils wieder die zugehoerige process-Methode aufgerufen wird. Dieses 
	 * Ergebnis wird dann jeweils zum Buffer hinzugef�gt. Nach jeder Uebersetzung und Auslesen
	 * eines Teilreports muss zunaechst noch einmal die Ergebnisvariable zurueckgestzt werden.Am 
	 * Ende wird der Buffer in einen String umgewandelt und der reportText-Variable zugewiesen. 
	 * Dies ermoeglich,das Ergebnis durch getReportText() auszulesen.
	 * 
	 * @param AllPartnervorschlaegeSpReport
	 */
	public void process(AllPartnervorschlaegeSpReport r) {

		this.resetReportText();

		StringBuffer result = new StringBuffer();

		result.append("<H3>" + r.getTitle() + "</H3>");
		result.append("<table><tr>");
		result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");

		if (r.getHeaderData() != null) {
			result.append("<td>" + paragraph2HTML(r.getHeaderData()) + "</td>");
		}

		result.append("<td>" + paragraph2HTML(r.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

		for (int j = 0; j < r.getNumSubReports(); j = j + 2) {
			
			AllProfildatenOfNutzerReport subReport2 = (AllProfildatenOfNutzerReport) r.getSubReportAt(j);

			this.process(subReport2);

			result.append(this.reportText + "\n");

		
			AllInfosOfNutzerReport subReport = (AllInfosOfNutzerReport) r.getSubReportAt(j + 1);

			this.process(subReport);

			result.append(this.reportText + "\n");

	
			this.resetReportText();
		}

		
		this.reportText = result.toString();

	}

	/**
	 * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
	 * 
	 * @return ein String im HTML-Format
	 */
	public String getReportText() {
		return this.getHeader() + this.reportText + this.getTrailer();
	}

}
