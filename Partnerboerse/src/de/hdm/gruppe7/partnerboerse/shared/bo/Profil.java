package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung eines exemplarischen Profils.
 * 
 * @author Annina Weckerle
 */

public class Profil extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private int profilId; // zu Testzwecken
	private String geschlecht;
	private String raucher;
	private String koerpergroesse;
	private int koerpergroesseInt;
	private String haarfarbe;
	private String religion;

	public int getKoerpergroesseInt() {
		return koerpergroesseInt;
	}

	public void setKoerpergroesseInt(int koerpergroesseInt) {
		this.koerpergroesseInt = koerpergroesseInt;
	}

	public int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public String getRaucher() {
		return raucher;
	}

	public void setRaucher(String raucher) {
		this.raucher = raucher;
	}

	public String getKoerpergroesse() {
		return koerpergroesse;
	}

	public void setKoerpergroesse(String koerpergroesse) {
		this.koerpergroesse = koerpergroesse;
	}

	public String getHaarfarbe() {
		return haarfarbe;
	}

	public void setHaarfarbe(String haarfarbe) {
		this.haarfarbe = haarfarbe;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	// Konstruktor
	public Profil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Erzeugen einer einfachen textuellen Repr�sentation der jeweiligen
	 * Kontoinstanz.
	 */
	@Override
	public String toString() {
		return super.toString() + "Profil-ID: #" + this.profilId + "Geschlecht: " + this.geschlecht + "Raucher: "
				+ this.raucher + "K�rpergr��e: " + this.koerpergroesse + "Haarfarbe: " + this.haarfarbe + "Religion: "
				+ this.religion;
	}

	/**
	 * <p>
	 * Feststellen der <em>inhaltlichen</em> Gleichheit zweier Profil-Objekte.
	 * Die Gleichheit wird in diesem Beispiel auf eine identische Profil-Id
	 * beschr�nkt.
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
		 * Abfragen, ob ein Objekt ungl. NULL ist und ob ein Objekt gecastet
		 * werden kann, sind immer wichtig!
		 */
		if (o != null && o instanceof Profil) {
			Profil p = (Profil) o;
			try {
				return super.equals(p);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}

}
