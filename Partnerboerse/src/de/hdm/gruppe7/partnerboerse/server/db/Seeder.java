package de.hdm.gruppe7.partnerboerse.server.db;

import java.util.Date;

import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;

public class Seeder {
	

		
		/* !!Was ist das und warum?
		 * Der Seeder füllt die DB mit Dummy-Daten. Denn um zu Testen sind 
		 * Testdaten sehr praktisch:
		 * 
		 * 1. Im Laufe der Entwicklung muss die Datenbank immer wieder neu 
		 * aufgesetzt werden: zB weil neue Spalten hinzugefügt oder gelöscht 
		 * werden müssen. Jedes Mal neue Daten einzugeben ist sehr anstrengend.
		 * 
		 * 2. Mit Testdaten lässt sich die Funktionalität der Datenbank/Applikation 
		 * schnell testen.Wenn Tabellen und Daten nicht richtig zusammenpassen, 
		 * fällt das mit einem Seeder schnell auf.
		 */

		/** !!Wie es funktioniert:
		 *  Für jede MapperKlasse (bsp: SomeObjectMapper) braucht es einen Block,
		 *  der in der Vorlage eingerahmt wird von den Kommentaren 'someTable start' 
		 *  und 'someTable end'. Jeder Block besteht aus einer Methode (bsp: seedSomeTable()), 
		 *  die die Datenbank füllt und Arrays aus den nötigen Werten (bsp: 'someValues').
		 *  
		 *  Die seedSomeTable() Methode ruft in der entsprechenden MapperKlasse
		 *  (bsp: SomeObjectMapper) die insert() Methode auf (bsp: insertSObject()).
		 */
		
		/* !!HowTo:
		 * 
		 * 0. In das Package mit den MapperKlassen diese SeederKlasse implementieren.
		 * 
		 * 1. ein neuen Block starten. zB **** profilTable start ****
		 * 
		 * 2. eine Methode schreiben. zB seedProfilTable()
		 * 
		 * 3. für jedes Attribut des BusinessObjects einen Array anlegen
		 * 	  mit den benötigten Werten. zB 10xVornamen, 10xNachnamen und 10xAlter
		 * 
		 * 4. Eine Schleife in die seedProfilTable() schreiben, die die Mapper.insert()
		 *    benutzt und dafür die Werte aus den Arrays aus 3. benutzt.
		 *    
		 * 5. Die neue Methode aus 2. in die seed() Methode schreiben. 
		 *    5.1 Die createTable() des Mappers in die migrate() Methode schreiben.
		 * 
		 * 6. Von wo auch immer die seed() Methode aufrufen. Die ruft alle andern Methoden
		 *    auf und befüllt eure Datenbank.
		 *    
		 * 7. Sich freuen wie n Iltis! 
		 * 
		 */

		public void init(){
			migrate();
			seed();
		}
		
		private void migrate(){
			NutzerprofilMapper.nutzerprofilMapper().createNutzerprofilTable();
			//nächster Mapper@createTable();
		}
		
		private void seed(){
			seedNutzerprofilTable();
			//nächste seedSomeTable();	
		}
		
		/************* someTable start *******************/
		public void seedNutzerprofilTable(){
			for(int i = 0; i < someProfilIds.length; i++){
				Nutzerprofil nutzerprofil = new Nutzerprofil();
				nutzerprofil.setProfilId(someProfilIds[i]);
				nutzerprofil.setVorname(someVornamen[i]);
				nutzerprofil.setNachname(someNachnamen[i]);

				// nutzerprofil.setGeburtsdatum(someGeburtsdaten[i]);
				NutzerprofilMapper.nutzerprofilMapper().insertNutzerprofil(nutzerprofil);
			}
		}
		
		private int[]someProfilIds = {
			5, 6, 7, 12, 54, 2, 98, 1, 54, 90
		};
		
		private String[]someVornamen = {
			"Thomas", "Hanna", "Dieter", "Gerd", "Mike",
			"Kerstin", "Anna", "Peter", "Martin", "Tim"	
		};
		
		private String[]someNachnamen = {
				"Bauer", "Baumgaertner", "Gogol", "Maier", "Friedrichsen",
				"Schwab", "Mueller", "Gier", "Kappel", "Schmidt"	
			};
		
//		// Date Notation???
//		private Date[] someGeburtsdaten = {
//				//1962-08-14, 1955-03-27 
 
//		};

			
		/************* someTable end *******************/

		/************* someOtherTable start *******************/
		//genau wie bei 'someTable'


	}

