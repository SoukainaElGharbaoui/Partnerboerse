package de.hdm.gruppe7.partnerboerse.shared.report;

import java.io.Serializable;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Diese Klasse stellt eine Menge einzelner Absaetze (SimpleParagraph-Objekte) dar. 
 * Diese werden als Unterabschnitte in einem Vector abgelegt verwaltet.
 * 
 * @author Thies
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewuenscht, als Grundlage 
 * uebernommen und bei Notwendigkeit an die Beduerfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 */

public class CompositeParagraph extends Paragraph implements IsSerializable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	  /**
	   * Speicherort der Unterabschnitte.
	   */
	private Vector<SimpleParagraph> subParagraphs = new Vector<SimpleParagraph>();
	
	  /**
	   * Einen Unterabschnitt hinzufuegen.
	   * 
	   * @param p der hinzuzufuegende Unterabschnitt.
	   */
	public void addSubParagraph(SimpleParagraph p) {
	   this.subParagraphs.addElement(p);
	}
	
	  /**
	   * Einen Unterabschnitt entfernen.
	   * 
	   * @param p der zu entfernende Unterabschnitt.
	   */
	public void removeSubParagraph(SimpleParagraph p) {
	    this.subParagraphs.removeElement(p);
	}
	
	  /**
	   * Auslesen saemtlicher Unterabschnitte.
	   * 
	   * @return Vector, der saemtliche Unterabschnitte enthält.
	   */
	public Vector<SimpleParagraph> getSubParagraphs() {
	    return this.subParagraphs;
	}
	
	  /**
	   * Auslesen der Anzahl der Unterabschnitte.
	   * 
	   * @return Anzahl der Unterabschnitte
	   */
	public int getNumParagraphs() {
	    return this.subParagraphs.size();
	}
	
	  /**
	   * Auslesen eines einzelnen Unterabschnitts.
	   * 
	   * @param i der Index des gewuenschten Unterabschnitts (0 <= i <n), mit n =
	   *          Anzahl der Unterabschnitte.
	   * 
	   * @return der gewuenschte Unterabschnitt.
	   */
	public SimpleParagraph getParagraphAt(int i) {
	    return this.subParagraphs.elementAt(i);
	}
	
	  /**
	   * Umwandeln eines CompositeParagraphin einenString. Dazu wird zunaecht ein leerer Buffer
	   * angelegt an den alle String-Repraesentationen der Unterabschnitte eingetragen werden.
	   * Danach folgt eine Schleife ueber alle Unterabschnitte, in der die jeweiligen 
	   * Unterabschnitte in einen String gewandelt werden und dem Buffer angehaengt wird.
	   * Am Ende wir der Buffer in einen String umgewandelt und zurueckgegeben.
	   * 
	   * @return String 
	   */
	public String toString() {
	   
	    StringBuffer result = new StringBuffer();

	    for (int i = 0; i < this.subParagraphs.size(); i++) {
	      SimpleParagraph p = this.subParagraphs.elementAt(i);

	      result.append(p.toString() + "\n");
	    }

	
	    return result.toString();
	  }

	
}
