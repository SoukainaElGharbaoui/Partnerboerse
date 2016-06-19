package de.hdm.gruppe7.partnerboerse.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Die Klasse BusinessObject stellt die Basisklasse aller in diesem
 * Projekt fuer die Umsetzung des Fachkonzepts relevanten Klassen dar.
 */
public abstract class BusinessObject implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
	 */
	private int id = 0;

	/**
	 * Auslesen der ID.
	 * 
	 * @return Integer, welches die ID repraesentiert.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der ID
	 * @param id 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
	 */
	@Override
	public String toString() {
		
		return this.getClass().getName() + " #" + this.id;
	}

	/**
	 * 
	 * Feststellen der inhaltlichen Gleichheit zweier
	 *BusinessObject-Objekte. Die Gleichheit wird in diesem
	 * Beispiel auf eine identische ID beschraenkt.
	 */
	@Override
	public boolean equals(Object o) {
		  
		/**
		 * Abfragen, ob ein Objekt ungelich NULL ist und ob ein Objekt gecastet werden kann.
		 */
		  
		  
		if (o != null && o instanceof BusinessObject) {
			BusinessObject bo = (BusinessObject) o;
			try {
				if (bo.getId() == this.id)
					return true;
			} catch (IllegalArgumentException e) {
				
				return false;
			}
		}
		/**
		 * Wenn keine Gleichheit bestimmt werden konnte, dann wird false zuruekgegeben.
		 */
		return false;
	}

	/**
	 * <p>
	 * Erzeugen einer ganzen Zahl, die fuer das BusinessObject
	 * charakteristisch ist.
	 */
	@Override
	public int hashCode() {
		return this.id;
	}

}