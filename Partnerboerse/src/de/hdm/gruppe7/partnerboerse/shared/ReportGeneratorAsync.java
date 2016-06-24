package de.hdm.gruppe7.partnerboerse.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
import de.hdm.gruppe7.partnerboerse.shared.report.AllInfosOfNutzerReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeSpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllPartnervorschlaegeNpReport;
import de.hdm.gruppe7.partnerboerse.shared.report.AllProfildatenOfNutzerReport;

/**
 * Das asynchrone Gegenstueck des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Fuer weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies
 *  --------------------------------------------------------------------
 *  Diese Klasse wurde, wie von Herrn Prof. Dr.Thies in der Vorlesung gewuenscht, 
 *  als Grundlage uebernommen und bei Notwendigkeit an die BedÃ¼rfnisse des IT-Projekts 
 *  SS 2016 "Partnerboerse" angepasst.
 * 
 */
public interface ReportGeneratorAsync {

	/**
	 * Initialisierung des Objekts.
	 * 
	 * @param callback
	 */
	void init(AsyncCallback<Void> callback);

	/**
	 * Methode, die einen fertigen Report vom Typ AllInfosOfNutzerReport zurueckliefert. 
	 * Der Report stellt alle Infos eines Nutzerprofils dar.
	 * 
	 * @param np Nutzerprofil-Objekt
	 * @param callback
	 */
	void createAllInfosOfNutzerReport(Nutzerprofil np, AsyncCallback<AllInfosOfNutzerReport> callback);

	/**
	 * Methode, die einen fertigen Report vom Typ AllProfildatenOfNutzerReport zurueckliefert.
	 * Der Report stellt alle Profildaten eines Nutzerprofils dar.
	 * 
	 * @param np Nutzerprofil-Objekt
	 * @param callback
	 */
	void createAllProfildatenOfNutzerReport(Nutzerprofil np, AsyncCallback<AllProfildatenOfNutzerReport> callback);

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeNpReport zurueckliefert.
	 * Der Report stellt alle unangesehenen Partnervorschlaege eines Nutzerprofils dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @param callback
	 */
	void createAllPartnervorschlaegeNpReport(Nutzerprofil nutzerprofil,
			AsyncCallback<AllPartnervorschlaegeNpReport> callback);

	/**
	 * Methode, die einen fertigen Report vom Typ AllPartnervorschlaegeSpReport zurueckliefert.
	 * Der Report stellt alle Partnervorschlaege, die anhand eines Suchprofils ermittelt wurden, 
	 * für ein Nutzerprofil dar.
	 * 
	 * @param nutzerprofil Nutzerprofil-Objekt
	 * @param suchprofilname Name des Suchprofil-Objektes
	 * @param callback
	 */
	void createAllPartnervorschlaegeSpReport(Nutzerprofil nutzerprofil, String suchprofilname,
			AsyncCallback<AllPartnervorschlaegeSpReport> callback);

}
