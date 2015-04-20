package br.unifor.iadapter.threadGroup;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DerbyDatabase {

	private static Connection conn = null;

	public static Connection singleton() throws ClassNotFoundException,
			SQLException {
		if (DerbyDatabase.conn == null) {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			DerbyDatabase.conn = DriverManager
					.getConnection("jdbc:derby:memory:/dbworkload;create=true");

			DatabaseMetaData meta = conn.getMetaData();

			ResultSet tables = meta.getTables(null, null, "workload", null);
			if (tables.next()) {

				System.out.println("tabela existe");

			} else {

				PreparedStatement ps = conn
						.prepareStatement(""
								+ "create table workload("
								+ "NAME VARCHAR(30),RESPONSETIME VARCHAR(30),"
								+ "TYPE VARCHAR(30),USERS VARCHAR(30),ERROR VARCHAR(30),FIT VARCHAR(30))");
				ps.executeUpdate();

			}

		}
		return conn;
	}

	public static void createDatabase() throws ClassNotFoundException,
			SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "create table workload("
						+ "NAME VARCHAR(30),RESPONSETIME VARCHAR(30),"
						+ "TYPE VARCHAR(30),USERS VARCHAR(30),ERROR VARCHAR(30),FIT VARCHAR(30))");

		try {
			ps.executeUpdate();
		} catch (SQLException e) {

			if (!(e.getLocalizedMessage()
					.contains("Table/View 'WORKLOAD' already exists"))) {
				e.printStackTrace();
			}
		}

	}

	public static void updateResponseTime(Long responseTime, String workload)
			throws ClassNotFoundException, SQLException {
		updateResponseTime(String.valueOf(responseTime), workload);
	}

	public static void updateResponseTime(String responseTime, String workload)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM  workload WHERE NAME=?");
		ps.setString(1, workload);

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0) {
			ps = con.prepareStatement("" + "update workload set "

			+ "RESPONSETIME=? WHERE NAME=?");
			ps.setString(1, responseTime);
			ps.setString(2, workload);
			ps.executeUpdate();
		}

	}

	public static void insertWorkLoads(List objetos)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM  workload WHERE NAME=?");
		ps.setString(1, String.valueOf(objetos.get(0)));

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement("insert into  workload(" + "NAME,TYPE,"
					+ "USERS,RESPONSETIME,ERROR,FIT) values (?,?,?,?,?,?)");
			ps.setString(1, String.valueOf(objetos.get(0)));
			ps.setString(2, String.valueOf(objetos.get(1)));
			ps.setString(3, String.valueOf(objetos.get(2)));
			ps.setString(4, String.valueOf(objetos.get(3)));
			ps.setString(5, String.valueOf(objetos.get(4)));
			ps.setString(6, String.valueOf(objetos.get(5)));
		} else {
			ps = con.prepareStatement("" + "update workload set "
					+ "NAME=?,TYPE=?,"
					+ "USERS=?,RESPONSETIME=?,ERROR=?,FIT=? WHERE NAME=?");
			ps.setString(1, String.valueOf(objetos.get(0)));
			ps.setString(2, String.valueOf(objetos.get(1)));
			ps.setString(3, String.valueOf(objetos.get(2)));
			ps.setString(4, String.valueOf(objetos.get(3)));
			ps.setString(5, String.valueOf(objetos.get(4)));
			ps.setString(6, String.valueOf(objetos.get(5)));
			ps.setString(7, String.valueOf(objetos.get(0)));
		}

		ps.executeUpdate();

	}

	public static void createWorkLoadIfNotExist(List objetos)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM  workload WHERE NAME=?");
		ps.setString(1, String.valueOf(objetos.get(0)));

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement("insert into  workload(" + "NAME,TYPE,"
					+ "USERS,RESPONSETIME,ERROR,FIT) values (?,?,?,?,?,?)");
			ps.setString(1, String.valueOf(objetos.get(0)));
			ps.setString(2, String.valueOf(objetos.get(1)));
			ps.setString(3, String.valueOf(objetos.get(2)));
			ps.setString(4, String.valueOf(objetos.get(3)));
			ps.setString(5, String.valueOf(objetos.get(4)));
			ps.setString(6, String.valueOf(objetos.get(5)));
			ps.executeUpdate();
		}

	}

	public static List<WorkLoad> listWorkLoads() throws ClassNotFoundException,
			SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT NAME,TYPE,USERS,RESPONSETIME,ERROR,FIT FROM  workload ");

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = new WorkLoad();
			workload.setName(rs.getString(1));
			workload.setType(rs.getString(2));
			workload.setNumThreads(Integer.valueOf(rs.getString(3)));
			workload.setWorstResponseTime(Long.valueOf(rs.getString(4)));
			workload.setError(Boolean.valueOf(rs.getString(5)));
			workload.setFit(Double.valueOf(rs.getString(6)));

			list.add(workload);
		}

		return list;

	}

	public static long getResponseTime(String workLoad)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT RESPONSETIME FROM  workload WHERE NAME=?");
		ps.setString(1, workLoad);

		ResultSet rs = ps.executeQuery();

		long responseTime = 0;

		while (rs.next()) {
			responseTime = rs.getLong(1);
		}

		return responseTime;

	}

}
