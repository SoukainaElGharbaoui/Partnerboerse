
package de.hdm.gruppe7.partnerboerse.shared.report;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Report, der alle unangesehen Partnervorschlaege eines Nutzerprofils darstellt.
 * In der Klasse werden keine attribute oder Methoden-Implementierungen ben�tigt, 
 * da die notwaendigen Attribute und Methoden-Impelementierungen in den Superklassen
 * vorliegt. Die Klasse ist lediglich daf�r notwendig um die Reports zu deklarieren 
 * und mit ihnen umgehen zu k�nnen.
 */

public class AllPartnervorschlaegeNpReport extends CompositeReport implements IsSerializable, Serializable {

	private static final long serialVersionUID = 1L;

}
