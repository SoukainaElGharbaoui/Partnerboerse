package de.hdm.gruppe7.partnerboerse.shared.report;

import java.io.Serializable;

/**
 * Reports benoetigen die Moeglichkeit, Text strukturiert abspeichern zu koennen.
 * Dieser Text kann spaeter durch ReportWriter in verschiedene Zielformate konvertiert 
 * werden. Die Verwendung der Klasse String reicht hier nicht aus, da allein das 
 * Hinzufuegen eines Zeilenumbruchs zur Markierung eines Absatzendes Kenntnisse ueber 
 * das Zielformat voraussetzt. Im Falle einer rein textuellen Darstellung wuerde hierzu 
 * ein doppeltes "\n" genuegen. Bei dem Zielformat HTML muesste jedoch der gesamte
 * Absatz in entsprechendes Markup eingefuegt werden.
 * 
 * Paragraph ist Serializable, so das Objekte dieser Klasse durch das Netzwerk 
 * uebertragbar sind.
 * 
 * @see Report
 * @author Thies
------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewuenscht, als Grundlage 
 * uebernommen und bei Notwendigkeit an die Beduerfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 */

public abstract class Paragraph implements Serializable {
	
	private static final long serialVersionUID = 1L;

}
