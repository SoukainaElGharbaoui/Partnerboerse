package de.hdm.gruppe7.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.gruppe7.partnerboerse.shared.bo.Auswahleigenschaft;
import de.hdm.gruppe7.partnerboerse.shared.bo.Beschreibungseigenschaft;

public class BeschreibungseigenschaftMapper {

	private static BeschreibungseigenschaftMapper beschreibungseigenschaftMapper = null;

	protected BeschreibungseigenschaftMapper() {

	}

	public static BeschreibungseigenschaftMapper beschreibungseigenschaftMapper() {
		if (beschreibungseigenschaftMapper == null) {
			beschreibungseigenschaftMapper = new BeschreibungseigenschaftMapper();
		}

		return beschreibungseigenschaftMapper;
	}

	public Beschreibungseigenschaft findByBeschreibungseigenschaftId(
			int beschreibungseigenschaftId) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT eigenschaft_id, beschreibungstext FROM t_beschreibungseigenschaft "
							+ "WHERE eigenschaft_id="
							+ beschreibungseigenschaftId);

			if (rs.next()) {
				Beschreibungseigenschaft beschreibungseigenschaft = new Beschreibungseigenschaft();
				beschreibungseigenschaft.setEigenschaftId(rs
						.getInt("eigenschaft_id"));
				beschreibungseigenschaft.setBeschreibungstext(rs
						.getString("beschreibungstext"));
				return beschreibungseigenschaft;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	public List<Beschreibungseigenschaft> findAllBeschreibungseigenschaften() {
		Connection con = DBConnection.connection();

		List<Beschreibungseigenschaft> result = new ArrayList<Beschreibungseigenschaft>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM t_beschreibungseigenschaft ORDER BY eigenschaft_id");

			while (rs.next()) {
				Beschreibungseigenschaft beschreibungseigenschaft = new Beschreibungseigenschaft();
				beschreibungseigenschaft.setEigenschaftId(rs
						.getInt("eigenschaft_id"));
				beschreibungseigenschaft.setBeschreibungstext(rs
						.getString("beschreibungstext"));

				result.add(beschreibungseigenschaft);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public Beschreibungseigenschaft insertBeschreibungseigenschaft(
			Beschreibungseigenschaft beschreibungseigenschaft) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT MAX(eigenschaft_id) AS maxeigenschaft_id "
							+ "FROM t_beschreibungseigenschaft");

			if (rs.next()) {

				beschreibungseigenschaft.setEigenschaftId(rs
						.getInt("maxeigenschaft_id") + 1);

				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO t_beschreibungseigenschaft (eigenschaft_id, beschreibungstext) "
						+ "VALUES("
						+ beschreibungseigenschaft.getEigenschaftId()
						+ ",'"
						+ beschreibungseigenschaft.getBeschreibungstext()
						+ "')");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return beschreibungseigenschaft;
	}

	public Beschreibungseigenschaft updateBeschreibungseigenschaft(
			Beschreibungseigenschaft beschreibungseigenschaft) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE t_beschreibungseigenschaft"
					+ "SET beschreibungstext=\"WHERE eigenschaft_id="
					+ beschreibungseigenschaft.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return beschreibungseigenschaft;
	}

	public void deleteBeschreibungseigenschaft(
			Beschreibungseigenschaft beschreibungseigenschaft) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM t_beschreibungseigenschaft "
					+ "WHERE eigenschaft_id ="
					+ beschreibungseigenschaft.getEigenschaftId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
