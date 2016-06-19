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

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {


	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor der implementierenden
	 * Klasse {@link PartnerboerseAdministrationImpltungImpl} notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;


	/**
	 * Methode, die einen fertigen Report vom Typ AllInfosOfNutzerReport zurueckliefert. 
	 * Der Report stellt alle Infos eines Nutzerprofils dar.
	 * 
	 * @param np Nutzerprofil-Objekt
	 * @return AllInfosOfNutzerReport Fertiges Report-Objekt vom Typ AllInfosOfNutzerReport
	 * @throws IllegalArgumentException
	 */
	AllInfosOfNutzerReport createAllInfosOfNutzerReport(Nutzerprofil np) throws IllegalArgumentException;

	/**
	 * Methode, die einen fertigen Report vom Typ AllProfildatenOfNutzerReport zurueckliefert.
	 * Der Report stellt alle Profildaten eines Nutzerprofils dar.
	 * 
	 * @param np Nutzerorifil-Objekt
	 * @return AllProfildatenOfNutzerReport Fertiges Report-Objekt vom Typ AllProfildatenOfNutzerReport
	 * @throws IllegalArgumentException
	 */
	AllProfildatenOfNutzerReport createAllProfildatenOfNutzerReport(Nutzerprofil np) throws IllegalArgumentException;

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeNpReport zurueckliefert.
	 * Der Report stellt alle unangesehenen Partnervorschlaege eines Nutzerprofils dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @return AllPartnervorschlaegeNpReport Fertiges Report-Objekt vom Typ AllPartnervorschlaegeNpReport
	 * @throws IllegalArgumentException
	 */
	AllPartnervorschlaegeNpReport createAllPartnervorschlaegeNpReport(Nutzerprofil nutzerprofil)
			throws IllegalArgumentException;

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeSpReport zurueckliefert.
	 * Der Report stellt alle Partnervorschlaege, die anhand eines Suchprofils ermittelt wurden, 
	 * f�r ein Nutzerprofil dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @param suchprofilname Name des Suchprofil-Objektes
	 * @return AllPartnervorschlaegeSpReport Fertiges Report-Objekt vom Typ AllPartnervorschlaegeSpReport
	 * @throws IllegalArgumentException
	 */
	AllPartnervorschlaegeSpReport createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil, String suchprofilname)
			throws IllegalArgumentException;

	public boolean isUserRegistered(String userEmail);

	public Nutzerprofil login(String requestUri) throws Exception;


}
