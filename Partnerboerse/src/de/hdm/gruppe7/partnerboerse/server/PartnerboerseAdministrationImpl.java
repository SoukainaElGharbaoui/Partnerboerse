package de.hdm.gruppe7.partnerboerse.server;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe7.partnerboerse.server.db.NutzerprofilMapper;
import de.hdm.gruppe7.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * <p>
 * Implementierungsklasse des Interface <code>PartnerboerseAdministration</code>
 * . Diese Klasse ist <em>die</em> Klasse, die neben {@link ReportGeneratorImpl}
 * sämtliche Applikationslogik (oder engl. Business Logic) aggregiert. Sie ist
 * wie eine Spinne, die sämtliche Zusammenhänge in ihrem Netz (in unserem Fall
 * die Daten der Applikation) überblickt und für einen geordneten Ablauf und
 * dauerhafte Konsistenz der Daten und Abläufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * lässt schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgeführt werden, die das System
 * von einem konsistenten Zustand in einen anderen, auch wieder konsistenten
 * Zustand überführen. Wenn dies zwischenzeitig scheitern sollte, dann ist das
 * jeweilige Transaction Script dafür verwantwortlich, eine Fehlerbehandlung
 * durchzuführen.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link PartnerboerseAdministration}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfügung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link PartnerboerseAdministrationAsync}:
 * <code>PartnerboerseAdministrationImpl</code> und
 * <code>PartnerboerseAdministration</code> bilden nur die Server-seitige Sicht
 * der Applikationslogik ab. Diese basiert vollständig auf synchronen
 * Funktionsaufrufen. Wir müssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zusätzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterstützt. Weitere Informationen unter
 * {@link PartnerboerseAdministrationAsync}.</li>
 * <li>{@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig über GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis für die Anbindung von <code>PartnerboerseAdministrationImpl</code> an
 * die Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehören der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Einzig
 * nachvollziehbares Argument für einen solchen Ansatz ist die Steigerung der
 * Performance umfangreicher Datenbankoperationen. Doch auch dieses Argument
 * zieht nur dann, wenn wirklich große Datenmengen zu handhaben sind. In einem
 * solchen Fall würde man jedoch eine entsprechend erweiterte Architektur
 * realisieren, die wiederum sämtliche Applikationslogik in der
 * Applikationsschicht isolieren würde. Also, keine Applikationslogik in die
 * Mapper-Klassen "stecken" sondern dies auf die Applikationsschicht
 * konzentrieren!
 * </p>
 * <p>
 * Sämtliche Methoden, die mittels GWT RPC aufgerufen werden, können ein
 * <code>throws IllegalArgumentException</code> in der Methodendeklaration
 * aufweisen. Diese Methoden dürfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions können z.B.
 * Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 * 
 * @see PartnerboerseAdministration
 * @see PartnerboerseAdministrationAsync
 * @see RemoteServiceServlet
 */
@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

	/**
	 * Referenz auf den DatenbankMapper, der Nutzerprofil-Objekte mit der
	 * Datenbank abgleicht.
	 */
	private NutzerprofilMapper nutzerprofilMapper = null;

	// Konstruktor
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #ReportGeneratorImpl()}. Diese Methode
	 * muss für jede Instanz von <code>BankVerwaltungImpl</code> aufgerufen
	 * werden.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die PartnerboerseAdministration einen
		 * vollständigen Satz von Mappern besitzt, mit deren Hilfe sie dann mit
		 * der Datenbank kommunizieren kann.
		 */
		this.nutzerprofilMapper = NutzerprofilMapper.nutzerprofilMapper();
	}

	/**
	 * Anlegen eines neuen Nutzerprofils. Dies führt implizit zu einem Speichern
	 * des neuen Nutzerprofils in der Datenbank.
	 * 
	 * Änderungen an Nutzerprofil-Objekten müssen stets durch Aufruf von
	 * {@link #save(Nutzerprofil nutzerprofil)} in die Datenbank transferiert
	 * werden.
	 * 
	 * @see save(Nutzerprofil nutzerprofil)
	 */
	@Override

	public Nutzerprofil createNutzerprofil(String vorname, String nachname, String geburtsdatum, String geschlecht,
			String haarfarbe, String koerpergroesse, String raucher, String religion) throws IllegalArgumentException {

		Nutzerprofil nutzerprofil = new Nutzerprofil();
		nutzerprofil.setProfilId(1);
		nutzerprofil.setVorname(vorname);
		nutzerprofil.setNachname(nachname);
		nutzerprofil.setGeburtsdatum(geburtsdatum);
		nutzerprofil.setGeschlecht(geschlecht);
		 nutzerprofil.setHaarfarbe(haarfarbe);
		 nutzerprofil.setKoerpergroesse(koerpergroesse);
		 nutzerprofil.setRaucher(raucher);
		 nutzerprofil.setReligion(religion);

		return this.nutzerprofilMapper.insertNutzerprofil(nutzerprofil);

	}

	// public Nutzerprofil createNutzerprofil(String vorname, String nachname,
	// Date geburtsdatum, String geschlecht, String haarfarbe,
	// String koerpergroesse, String raucher, String religion)
	// throws IllegalArgumentException {
	//
	// Nutzerprofil nutzerprofil = new Nutzerprofil();
	// nutzerprofil.setVorname(vorname);
	// nutzerprofil.setNachname(nachname);
	// nutzerprofil.setGeburtsdatum(geburtsdatum);
	// nutzerprofil.setGeschlecht(geschlecht);
	// nutzerprofil.setHaarfarbe(haarfarbe);
	// nutzerprofil.setKoerpergroesse(koerpergroesse);
	// nutzerprofil.setRaucher(raucher);
	// nutzerprofil.setReligion(religion);
	//
	// /*
	// * Setzen einer vorläufigen Profilnr. Der insert-Aufruf liefert dann ein
	// * Objekt, dessen Nummer mit der Datenbank konsistent ist.
	// */
	// nutzerprofil.setProfilId(1);
	//
	// // Objekt in der DB speichern.
	// return this.nutzerprofilMapper.insertNutzerprofil(nutzerprofil);
	// }

	/**
	 * Auslesen eines Nutzerprofils anhand seiner ProfilId.
	 */
	@Override
	public Nutzerprofil getNutzerprofilById(int profilId) throws IllegalArgumentException {

		return this.nutzerprofilMapper.findByNutzerprofilId(profilId);
	}

	/**
	 * Speichern eines Nutzerprofils.
	 */
	@Override
	public void save(Nutzerprofil nutzerprofil) throws IllegalArgumentException {

		nutzerprofilMapper.updateNutzerprofil(nutzerprofil);
	}

	/**
	 * Löschen eines Nutzerprofils.
	 */
	@Override
	public void delete(Nutzerprofil nutzerprofil) throws IllegalArgumentException {

		nutzerprofilMapper.deleteNutzerprofil(nutzerprofil);
	}

	@Override
	public List<Nutzerprofil> getAngeseheneNpFor(Nutzerprofil nutzerprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
