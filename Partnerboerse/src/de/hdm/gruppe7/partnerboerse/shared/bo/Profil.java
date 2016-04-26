package de.hdm.gruppe7.partnerboerse.shared.bo;

/**
 * Realisierung eines exemplarischen Profils. 
 * @author Annina Weckerle
 */

public class Profil extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private int profilId;
	private String geschlecht; 
	private boolean raucher;
	private int koerpergroesse;
	private String haarfarbe;
	private String religion;
	
	
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
	public boolean isRaucher() {
		return raucher;
	}
	public void setRaucher(boolean raucher) {
		this.raucher = raucher;
	}
	public int getKoerpergroesse() {
		return koerpergroesse;
	}
	public void setKoerpergroesse(int koerpergroesse) {
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
	
	/**
	   * Erzeugen einer einfachen textuellen Repräsentation der jeweiligen
	   * Kontoinstanz.
	   */
	  @Override
	public String toString() {
	    return super.toString() + "Profil-ID: #" + this.profilId 
	    		+ "Geschlecht: " + this.geschlecht + "Raucher: " + this.raucher 
	    		+ "Körpergröße: " + this.koerpergroesse + "Haarfarbe: " 
	    		+ this.haarfarbe + "Religion: " + this.religion;
	  }

	  /**
	   * <p>
	   * Feststellen der <em>inhaltlichen</em> Gleichheit zweier Profil-Objekte.
	   * Die Gleichheit wird in diesem Beispiel auf eine identische Profil-Id
	   * beschränkt.
	   * </p>
	   * <p>
	   * <b>ACHTUNG:</b> Die inhaltliche Gleichheit nicht mit dem Vergleich der
	   * <em>Identität</em> eines Objekts mit einem anderen verwechseln!!! Dies
	   * würde durch den Operator <code>==</code> bestimmt. Bei Unklarheit hierzu
	   * können Sie nocheinmal in die Definition des Sprachkerns von Java schauen.
	   * Die Methode <code>equals(...)</code> ist für jeden Referenzdatentyp
	   * definiert, da sie bereits in der Klasse <code>Object</code> in einfachster
	   * Form realisiert ist. Dort ist sie allerdings auf die simple Bestimmung der 
	   * Gleicheit der Java-internen Objekt-ID der verglichenen Objekte beschränkt.
	   * In unseren eigenen Klassen können wir diese Methode überschreiben und ihr
	   * mehr Intelligenz verleihen.
	   * </p>
	   */
	  @Override
	public boolean equals(Object o) {
	    /*
	     * Abfragen, ob ein Objekt ungl. NULL ist und ob ein Objekt gecastet werden
	     * kann, sind immer wichtig!
	     */
	    if (o != null && o instanceof Profil) {
	      Profil p = (Profil) o;
	      try {
	        return super.equals(p);
	      }
	      catch (IllegalArgumentException e) {
	        return false;
	      }
	    }
	    return false;
	  }
	
}
