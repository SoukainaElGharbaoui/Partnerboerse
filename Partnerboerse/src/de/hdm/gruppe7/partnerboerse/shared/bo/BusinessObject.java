package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt f�r die Umsetzung des Fachkonzepts relevanten Klassen dar.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Prim�rschl�ssel
 * bezeichnen w�rde. Fernen ist jedes <code>BusinessObject</code> als
 * {@link Serializable} gekennzeichnet. Durch diese Eigenschaft kann jedes
 * <code>BusinessObject</code> automatisch in eine textuelle Form �berf�hrt und
 * z.B. zwischen Client und Server transportiert werden. Bei GWT RPC ist diese
 * textuelle Notation in JSON (siehe http://www.json.org/) kodiert.
 * </p>
 */
public abstract class BusinessObject implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
	 */
	private int id = 0;

	// Konstruktor
	public BusinessObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Auslesen der ID.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
	 * Dies kann selbstverst�ndlich in Subklassen �berschrieben werden.
	 */
	@Override
	public String toString() {
		/*
		 * Wir geben den Klassennamen gefolgt von der ID des Objekts zur�ck.
		 */
		return this.getClass().getName() + " #" + this.id;
	}

	/**
	 * <p>
	 * Feststellen der <em>inhaltlichen</em> Gleichheit zweier
	 * <code>BusinessObject</code>-Objekte. Die Gleichheit wird in diesem
	 * Beispiel auf eine identische ID beschr�nkt.
	 * </p>
	 * <p>
	 * <b>ACHTUNG:</b> Die inhaltliche Gleichheit nicht mit dem Vergleich der
	 * <em>Identit�t</em> eines Objekts mit einem anderen verwechseln!!! Dies
	 * w�rde durch den Operator <code>==</code> bestimmt. Bei Unklarheit hierzu
	 * k�nnen Sie nocheinmal in die Definition des Sprachkerns von Java schauen.
	 * Die Methode <code>equals(...)</code> ist f�r jeden Referenzdatentyp
	 * definiert, da sie bereits in der Klasse <code>Object</code> in
	 * einfachster Form realisiert ist. Dort ist sie allerdings auf die simple
	 * Bestimmung der Gleicheit der Java-internen Objekt-ID der verglichenen
	 * Objekte beschr�nkt. In unseren eigenen Klassen k�nnen wir diese Methode
	 * �berschreiben und ihr mehr Intelligenz verleihen.
	 * </p>
	 */
	@Override
	public boolean equals(Object o) {
		/*
		 * Abfragen, ob ein Objekt ungleich NULL ist und ob ein Objekt gecastet
		 * werden kann, sind immer wichtig!
		 */
		if (o != null && o instanceof BusinessObject) {
			BusinessObject bo = (BusinessObject) o;
			try {
				if (bo.getId() == this.id)
					return true;
			} catch (IllegalArgumentException e) {
				/*
				 * Wenn irgendetwas schief geht, dann geben wir
				 * sicherheitshalber false zur�ck.
				 */
				return false;
			}
		}
		/*
		 * Wenn bislang keine Gleichheit bestimmt werden konnte, dann m�ssen
		 * schlie�lich false zur�ckgeben.
		 */
		return false;
	}

	/**
	 * <p>
	 * Erzeugen einer ganzen Zahl, die f�r das <code>BusinessObject</code>
	 * charakteristisch ist.
	 * </p>
	 * <p>
	 * Zusammen mit <code>equals</code> sollte diese Methode immer definiert
	 * werden. Manche Java-Klassen verwendenden <code>hashCode</code>, um
	 * initial ein Objekt (z.B. in einer Hashtable) zu identifizieren. Erst
	 * danach w�rde mit <code>equals</code> festgestellt, ob es sich tats�chlich
	 * um das gesuchte Objekt handelt.
	 */
	@Override
	public int hashCode() {
		return this.id;
	}

}