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
import de.hdm.gruppe7.partnerboerse.shared.bo.Suchprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllSuchprofileOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

	/**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
	   * {@link PartnerboerseAdministrationImpltungImpl} notwendig. 
	   * 
	   * @throws IllegalArgumentException
	   */
	  public void init() throws IllegalArgumentException;
	  
//	  /**
//	   * Einen <code>AllAccountsOfCustomerReport</code>-Reports erstellen. 
//	   * Dieser Report-Typ stellt sämtliche Suchprofile eines Nutzers dar.
//	   * 
//	   * @param c eine Referenz auf das Nutzerprofil-Objekt bzgl. dessen der Report
//	   *          erstellt werden soll.
//	   * @return das fertige Reportobjekt
//	   * @throws IllegalArgumentException
//	   * @see AllSuchprofileOfNutzerReport
//	   */
//	  public abstract AllSuchprofileOfNutzerReport createAllSuchprofileOfNutzerReport
//	  (Nutzerprofil n) throws IllegalArgumentException;



	AllInfosOfNutzerReport createAllInfosOfNutzerReport(Nutzerprofil np)
			throws IllegalArgumentException;

	AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport()
			throws IllegalArgumentException;

	AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport()
			throws IllegalArgumentException;
	
	boolean isUserRegistered(String userEmail);

//	public Nutzerprofil insertEmail(int profilId, String emailAddress) throws IllegalArgumentException;

	Nutzerprofil login(String requestUri) throws Exception;
	
	public void setUser(Nutzerprofil n);

	  
}
