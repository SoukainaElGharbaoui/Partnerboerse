package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Info;

public class AuswahleigenschaftMapper {

	private static AuswahleigenschaftMapper auswahleigenschaftMapper = null;

	protected AuswahleigenschaftMapper() {

	}

	public static AuswahleigenschaftMapper auswahleigenschaftMapper() {
		if (auswahleigenschaftMapper == null) {
			auswahleigenschaftMapper = new AuswahleigenschaftMapper();

		}
		return auswahleigenschaftMapper;
	}

	public Auswahleigenschaft findByAuswahleigenschaftId(
			int auswahleigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT auswahleigenschaft_id, auswahltext FROM t_auswahleigenschaft "
							+ "WHERE auswahleigenschaft_id="
							+ auswahleigenschaftId);

			if (rs.next()) {
				Auswahleigenschaft auswahleigenschaft = new Auswahleigenschaft();
				auswahleigenschaft
						.setEigenschaftId(rs.getInt("eigenschaft_id"));
				auswahleigenschaft.setAuswahltext(rs.getString("auswahltext"));
				return auswahleigenschaft;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	public List<Auswahleigenschaft> findAllAuswahleigenschaften() {
		Connection con = DBConnection.connection();

		List<Auswahleigenschaft> result = new ArrayList<Auswahleigenschaft>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_auswahleigenschaft ORDER BY eigenschaft_id");

			while (rs.next()) {
				Auswahleigenschaft auswahleigenschaft = new Auswahleigenschaft();
				auswahleigenschaft
						.setEigenschaftId(rs.getInt("eigenschaft_id"));
				auswahleigenschaft.setAuswahltext(rs.getString("auswahltext"));

				
				result.add(auswahleigenschaft);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		
		return result;
	}

	public Auswahleigenschaft insertAuswahleigenschaft(
			Auswahleigenschaft auswahleigenschaft) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(eigenschaft_id) AS maxeigenschaft_id "
							+ "FROM t_auswahleigenschaft");

			
			if (rs.next()) {

				auswahleigenschaft.setEigenschaftId(rs
						.getInt("maxeigenschaft_id") + 1);

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_auswahleigenschaft (eigenschaft_id, auswahltext) "
						+ "VALUES("
						+ auswahleigenschaft.getEigenschaftId()
						+ ",'" + auswahleigenschaft.getAuswahltext() + "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return auswahleigenschaft;
	}

	public Auswahleigenschaft updateAuswahleigenschaft(
			Auswahleigenschaft auswahleigenschaft) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_auswahleigenschaft"
					+ "SET auswahltext=\"WHERE auswahleigenschaft_id="
					+ auswahleigenschaft.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return auswahleigenschaft;
	}

	public void deleteAuswahleigenschaft(Auswahleigenschaft auswahleigenschaft) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_auswahleigenschaft "
					+ "WHERE eigenschaft_id ="
					+ auswahleigenschaft.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
