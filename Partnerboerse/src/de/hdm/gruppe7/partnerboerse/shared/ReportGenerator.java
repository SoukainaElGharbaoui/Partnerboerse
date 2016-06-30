package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports. Diese Schnittstelle benutzt die gleiche Realisierungsgrundlage wir
 * das Paar {@link PartnerboerseAdministration} und {@PartnerboerseAdministrationImpl}. 
 * Zu technischen Erläuterung etwa bzgl. GWT RPC bzw. {@link RemoteServiceServlet}
 * siehe {@link PartnerboerseAdministration} und {@link PartnerboerseAdministrationImpl}.
 * </p>
 * <p>
 * Ein ReportGenerator bietet die Möglichkeit, eine Menge von Berichten
 * (Reports) zu erstellen, die Menge von Daten bzgl. bestimmter Sachverhalte des
 * Systems zweckspezifisch darstellen.
 * </p>
 * <p>
 * Die Klasse bietet eine Reihe von <code>create...</code>-Methoden, mit deren
 * Hilfe die Reports erstellt werden können. Jede dieser Methoden besitzt eine
 * dem Anwendungsfall entsprechende Parameterliste. Diese Parameter benötigt der
 * der Generator, um den Report erstellen zu können.
 * </p>
 * <p> 
 * Bei neu hinzukommenden Bedarfen an Berichten, kann diese Klasse auf einfache
 * Weise erweitert werden. Hierzu können zusätzliche <code>create...</code>
 * -Methoden implementiert werden. Die bestehenden Methoden bleiben davon
 * unbeeinflusst, so dass bestehende Programmlogik nicht verändert werden muss.
 * 
 * ------------------------------------------------------------------------------------------
 * Diese Klasse wurde, wie von Herrn Prof. Dr. Thies in der Vorlesung gewünscht, als Grundlage 
 * übernommen und bei Notwendigkeit an die Bedürfnisse des IT-Projekts SS 2016 "Partnerboerse" 
 * angepasst. 
 * 
 * Modifizierender @author Milena Weinmann
 * </p>
 * 
 * 
 */
import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllProfildatenOfNutzerReport;

/**
 * Synchrones Interface des ReportGenerator
 */
@RemoteServiceRelativePath("report")
public interface ReportGenerator extends RemoteService {


	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#init()
	 */
	public void init() throws IllegalArgumentException;


	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllInfosOfNutzerReport(Nutzerprofil)
	 */
	AllInfosOfNutzerReport createAllInfosOfNutzerReport(Nutzerprofil np) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllProfildatenOfNutzerReport(Nutzerprofil)
	 */
	AllProfildatenOfNutzerReport createAllProfildatenOfNutzerReport(Nutzerprofil np) throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllPartnervorschlaegeNpReport(Nutzerprofil)
	 */
	AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport(Nutzerprofil nutzerprofil)
			throws IllegalArgumentException;

	/**
	 * @see de.hdm.gruppe7.partnerboerse.server.report.ReportGeneratorImpl#createAllPartnervorschlaegeSpReport(Nutzerprofil, String)
	 */
	AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil, String suchprofilname)
			throws IllegalArgumentException;


}
