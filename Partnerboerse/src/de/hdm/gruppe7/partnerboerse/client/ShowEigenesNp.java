//package de.hdm.gruppe7.partnerboerse.client;
//
//import java.util.Date;
//
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.i18n.client.DateTimeFormat;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Anchor;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.FlexTable;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
//import de.hdm.gruppe7.partnerboerse.shared.bo.Nutzerprofil;
//
//
//public class ShowEigenesNp extends VerticalPanel {
//
//	/**
//	 * Neues Nutzerprofil-Objekt anlegen mit Login-Infos.
//	 */
//	private Nutzerprofil nutzerprofil = ClientsideSettings.getAktuellerUser();
//
//	/**
//	 * Panels hinzufuegen.
//	 */
//	private VerticalPanel verPanel1 = new VerticalPanel();
//	private VerticalPanel verPanel2 = new VerticalPanel();
//	private HorizontalPanel horPanel = new HorizontalPanel();
//	private HorizontalPanel buttonPanel = new HorizontalPanel();
//
//	/**
//	 * Widgets hinzufuegen.
//	 */
//	private Label ueberschriftLabel = new Label("Ihr Profil:");
//	private FlexTable showEigenesNpFlexTable = new FlexTable();
//	private Label infoLabel = new Label();
//	private Button loeschenButton = new Button("Profil löschen");
//	private Button bearbeitenButton = new Button("Profil bearbeiten");
//	private String profiltyp;
//
//	/**
//	 * Konstruktor hinzufuegen.
//	 * @param user Nutzerprofil
//	 */
//	public ShowEigenesNp() {
//
//		this.add(horPanel);
//		horPanel.add(verPanel1);
//		horPanel.add(verPanel2);
//
//		/**
//		 * CSS anwenden
//		 */
//		ueberschriftLabel.addStyleName("partnerboerse-label");
//
//		/**
//		 * Erste Spalte der Tabelle festlegen.
//		 */
//		showEigenesNpFlexTable.setText(0, 0, "Nutzerprofil-Id");
//		showEigenesNpFlexTable.setText(1, 0, "Vorname");
//		showEigenesNpFlexTable.setText(2, 0, "Nachname");
//		showEigenesNpFlexTable.setText(3, 0, "Geschlecht");
//		showEigenesNpFlexTable.setText(4, 0, "Geburtsdatum");
//		showEigenesNpFlexTable.setText(5, 0, "Körpergröße");
//		showEigenesNpFlexTable.setText(6, 0, "Haarfarbe");
//		showEigenesNpFlexTable.setText(7, 0, "Raucherstatus");
//		showEigenesNpFlexTable.setText(8, 0, "Religion");
//		showEigenesNpFlexTable.setText(9, 0, "EMail");
//
//		/**
//		 * Tabelle formatieren.
//		 */
//		showEigenesNpFlexTable.setCellPadding(6);
//		showEigenesNpFlexTable.getColumnFormatter().addStyleName(0,
//				"TableHeader");
//		showEigenesNpFlexTable.addStyleName("FlexTable");
//		
//		
//		/**
//		 * Die Variable profiltyp wird initialisiert.
//		 * Ihr wird der String "Sp" hinzugefügt, d.h., dass das Profil
//		 * vom Typ Suchprofil ist
//		 */
//		profiltyp = "Np";
//		
//
//		/**
//		 * Nutzerprofil anhand der Profil-ID auslesen.
//		 */
//		ClientsideSettings.getPartnerboerseAdministration()
//				.getNutzerprofilById(nutzerprofil.getProfilId(),
//						new AsyncCallback<Nutzerprofil>() {
//
//							public void onFailure(Throwable caught) {
//								infoLabel.setText("Es trat ein Fehler auf.");
//							}
//
//							public void onSuccess(Nutzerprofil result) {
//								
//								// Nutzerprofil-Id aus der Datenabank holen
//								// und in Tabelle eintragen
//								String nutzerprofilId = String.valueOf(result
//										.getProfilId());
//
//								nutzerprofil.setProfilId(Integer
//										.valueOf(nutzerprofilId));
//								showEigenesNpFlexTable.setText(0, 1,
//										nutzerprofilId);
//
//								// Vorname aus Datenbank aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(1, 1,
//										result.getVorname());
//
//								// Nachname aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(2, 1,
//										result.getNachname());
//
//								// Geschlecht aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(3, 1,
//										result.getGeschlecht());
//
//								// Geburtsdatum aus der Datenbank holen
//								// und in Tabelle eintragen
//								Date geburtsdatum = result.getGeburtsdatumDate();
//								String geburtsdatumString = DateTimeFormat.getFormat("dd.MM.yyyy").format(geburtsdatum);
//								showEigenesNpFlexTable.setText(4, 1, geburtsdatumString);
//
//
//								// Koerpergroesse aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(5, 1,
//										(Integer.toString(result
//												.getKoerpergroesseInt())));
//
//								// Haarfarbe aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(6, 1,
//										result.getHaarfarbe());
//
//								// Raucher aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(7, 1,
//										result.getRaucher());
//
//								// Religion aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(8, 1,
//										result.getReligion());
//
//								// EMail aus der Datenbank holen
//								// und in Tabelle eintragen
//								showEigenesNpFlexTable.setText(9, 1,
//										result.getEmailAddress());
//							}
//						});
//
//		/**
//		 * ClickHandler fuer den Button zum Bearbeiten hinzufuegen.
//		 */
//		bearbeitenButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				// Seite zum Bearbeiten des Nutzerprofils aufrufen.
//				EditNutzerprofil editNutzerprofil = new EditNutzerprofil();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(editNutzerprofil);
//			}
//		});
//
//		/**
//		 * ClickHandler fuer den Loeschen-Button hinzufuegen.
//		 */
//		loeschenButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				
//				if(Window.confirm("Möchten Sie Ihr Profil wirklich löschen?")) {
//					
//					//Nutzerprofil loeschen.
//					ClientsideSettings.getPartnerboerseAdministration()
//							.deleteNutzerprofil(nutzerprofil.getProfilId(),
//									new AsyncCallback<Void>() {
//
//										public void onFailure(
//												Throwable caught) {
//											infoLabel
//													.setText("Es trat ein Fehler auf.");
//										}
//
//										public void onSuccess(Void result) {
//				
//											HorizontalPanel loginPanel = new HorizontalPanel();
//											
//											Anchor signOutLink = new Anchor();
//											signOutLink.setHref(nutzerprofil.getLogoutUrl());
//											
//											signOutLink.setText("Bestätige das Löschen mit einem Klick.");
//											
//											loginPanel.add(signOutLink);
//							
//											
//											Anchor signIn = new Anchor();
//											signIn.setText("Jetzt einloggen");
//											 
//											RootPanel.get("Navigator").clear();
//											RootPanel.get("Details").clear();
//											 
//											RootPanel.get("Navigator").add(loginPanel);
//										}
//							});
//				}
//			}
//		});
//		
//		
//		/**
//		 * Infos anzeigen.
//		 */
//		ShowInfoNp showInfoNp = new ShowInfoNp(nutzerprofil.getProfilId(), profiltyp);
//
//		/**
//		 * Widgets den Panels hinzufuegen.
//		 */
//		verPanel1.add(ueberschriftLabel);
//		verPanel1.add(showEigenesNpFlexTable);
//		buttonPanel.add(bearbeitenButton);
//		buttonPanel.add(loeschenButton);
//		verPanel1.add(buttonPanel);
//		verPanel1.add(infoLabel);
//		verPanel2.add(showInfoNp);
//
//	}
//
//}
