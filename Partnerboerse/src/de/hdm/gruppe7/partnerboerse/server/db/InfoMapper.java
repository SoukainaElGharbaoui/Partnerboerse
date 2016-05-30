package de.hdm.gruppe7.partnerboerse.server.db;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InfoMapper {

	private static InfoMapper infoMapper = null;

	protected InfoMapper() {

	}

	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}

		return infoMapper;
	}


	public List<Eigenschaft> findAllEigenschaftenNeu(){
		Connection con = DBConnection.connection();
		
		List<Eigenschaft> result = new ArrayList<Eigenschaft>();
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_eigenschaft1");
			
			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setEigenschaftId(rs.getInt("eigenschaft_id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));
				e.setTyp(rs.getString("typ"));

				result.add(e);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	
	public Info insertInfoNeu(Info i) {
		Connection con = DBConnection.connection();
		
		try {
			 Statement stmt = con.createStatement();
			
			 stmt.executeUpdate("INSERT INTO t_info1 (nutzerprofil_id, eigenschaft_id, infotext) "
			 + "VALUES(" + i.getNutzerprofilId() + "," + i.getEigenschaftId() 
			 + ",'" + i.getInfotext() +  "')");
			
			 } catch (SQLException e2) {
			 e2.printStackTrace();
			 
			 }
			 return i;
			 }
	
	
	
	public List<Info> findAllInfosNeu(int nutzerprofilId) {
		Connection con = DBConnection.connection();

		List<Info> result = new ArrayList<Info>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_info1 WHERE nutzerprofil_id="
							+ nutzerprofilId);

			while (rs.next()) {
				Info i = new Info();
				i.setNutzerprofilId(rs.getInt("nutzerprofil_id"));
				i.setEigenschaftId(rs.getInt("eigenschaft_id"));
				i.setInfotext(rs.getString("infotext"));

				result.add(i);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return result;
	}

	public Eigenschaft findEigenschaftById(int eigenschaftId) {
		Connection con = DBConnection.connection();

		Eigenschaft e = new Eigenschaft();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_eigenschaft1 WHERE eigenschaft_id="
							+ eigenschaftId);

			while (rs.next()) {
				e.setEigenschaftId(rs.getInt("eigenschaft_id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));
				e.setTyp(rs.getString("typ"));
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return e;
	}

	
	public void deleteAllInfosNeu(int profilId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_info1 "
					+ "WHERE nutzerprofil_id=" + profilId);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	public void deleteOneInfoNeu(int profilId, int eigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM t_info1 "
					+ "WHERE nutzerprofil_id=" + profilId
					+ " AND eigenschaft_id=" + eigenschaftId);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	
	public Auswahleigenschaft findEigAById(int eigenschaftId) {
		Connection con = DBConnection.connection();

		List<String> optionen = new ArrayList<String>();
		Auswahleigenschaft eigA = new Auswahleigenschaft();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_auswahleigenschaft1 "
							+ "WHERE eigenschaft_id=" + eigenschaftId);

			while (rs.next()) {

				eigA.setEigenschaftId(rs.getInt("eigenschaft_id"));

				String option = rs.getString("auswahloption");
				optionen.add(option);
			}

			eigA.setOptionen(optionen);
			return eigA;
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	
	
	public Beschreibungseigenschaft findEigBById(int eigenschaftId) {
		Connection con = DBConnection.connection();
		
		Beschreibungseigenschaft eigB = new Beschreibungseigenschaft();


		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_beschreibungseigenschaft1 "
							+ "WHERE eigenschaft_id=" + eigenschaftId);

			while (rs.next()) {

				eigB.setEigenschaftId(rs.getInt("eigenschaft_id"));
				eigB.setBeschreibungstext(rs.getString("beschreibungstext"));
			}

			return eigB;
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

}
