package de.hdm.gruppe7.partnerboerse.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

/**
 * Klasse zur Darstellung von Nutzerprofil-Objekten.
 * Solche Erweiterungen von <code>AbstractCell<T></code> dienen zur Erzeugung von
 * HTML-Code für benutzerdefinierte Objekte. 
 * 
 * In diesem Fall wird die <code>id</code>
 * eines Kontoobjekts mit einem vorangestellten "Kontonnr. " in einem <code>div-</code>Element
 * erzeugt.
 *
 */

public class NutzerprofilCell extends AbstractCell<Nutzerprofil>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			Nutzerprofil value, SafeHtmlBuilder sb) {
		
		 // Value can be null, so do a null check..
	      if (value == null) {
	        return;
	      }
	      	      
	      sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getVorname());
	      sb.appendHtmlConstant(", ");
	      
	      sb.appendEscaped(value.getNachname());
	      sb.appendHtmlConstant("</div>");
	      sb.appendHtmlConstant(", ");
	      	      
//	      sb.appendHtmlConstant("<div>");
//	      sb.appendEscaped(value.getGeburtsdatum());
//	      sb.appendHtmlConstant(", ");
	      
	      sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getGeschlecht());
	      sb.appendHtmlConstant(", ");
	      
	      sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getHaarfarbe());
	      sb.appendHtmlConstant(", ");
	      
	      sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getKoerpergroesse());
	      sb.appendHtmlConstant(", ");
	      

	      sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.isRaucher());
	      sb.appendHtmlConstant(", ");
	      

	      sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getReligion());
	      sb.appendHtmlConstant(", ");
	      
	}

}
