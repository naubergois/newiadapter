package br.unifor.iadapter.threadGroup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DerbyDatabase {

	private static Connection conn = null;

	private final static String CREATEDATABASE = "create table workload("
			+ "NAME VARCHAR(130),RESPONSETIME VARCHAR(30),"
			+ "TYPE VARCHAR(30),USERS VARCHAR(30),ERROR VARCHAR(30),"
			+ "FIT VARCHAR(30),FUNCTION1 VARCHAR(30),FUNCTION2 VARCHAR(30),"
			+ "FUNCTION3 VARCHAR(30),FUNCTION4 VARCHAR(30),"
			+ "FUNCTION5 VARCHAR(30),FUNCTION6 VARCHAR(30),"
			+ "FUNCTION7 VARCHAR(30),FUNCTION8 VARCHAR(30),"
			+ "FUNCTION9 VARCHAR(30),FUNCTION10 VARCHAR(30),TESTPLAN VARCHAR(30),"
			+ "GENERATION VARCHAR(30),ACTIVE VARCHAR(30))";

	private final static String CREATETABLEAGENT = "CREATE TABLE agent ("
			+ "name varchar(45) DEFAULT NULL,"
			+ "running varchar(45) DEFAULT NULL,"
			+ "ip varchar(45) DEFAULT NULL"
			+ ") ENGINE=InnoDB DEFAULT CHARSET=latin";

	private final static String COLUMNS = "NAME,TYPE,"
			+ "USERS,RESPONSETIME,ERROR,FIT,"
			+ "FUNCTION1,FUNCTION2,FUNCTION3,FUNCTION4,"
			+ "FUNCTION5,FUNCTION6,FUNCTION7,FUNCTION8,"
			+ "FUNCTION9,FUNCTION10,TESTPLAN,GENERATION,ACTIVE";

	private final static String COLUMNSAGENT = "NAME,RUNNING," + "IP";

	private final static String PARAMETERS = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

	private final static String PARAMETERSAGENT = "?,?,?";

	private final static String SET = "NAME=?,TYPE=?,"
			+ "USERS=?,RESPONSETIME=?,ERROR=?,FIT=?,FUNCTION1=?,"
			+ "FUNCTION2=?,FUNCTION3=?,FUNCTION4=?,FUNCTION5=?,"
			+ "FUNCTION6=?,FUNCTION7=?,FUNCTION8=?,FUNCTION9=?,"
			+ "FUNCTION10=?,TESTPLAN=?,GENERATION=?,ACTIVE=?";

	private final static String SETAGENT = "NAME=?,RUNNING=?," + "IP=?";

	private final static String INSERTSQL = "insert into  workload("
			+ DerbyDatabase.COLUMNS + ") values (" + DerbyDatabase.PARAMETERS
			+ ")";

	private final static String INSERTSQLLog = "insert into  log("
			+ "log) values (?)";

	private final static String INSERTSQLAGENT = "insert into  agent("
			+ DerbyDatabase.COLUMNSAGENT + ") values ("
			+ DerbyDatabase.PARAMETERSAGENT + ")";

	public static PreparedStatement setParametersWhere(PreparedStatement ps,
			List objetos, String where, String testPlan) throws SQLException {
		ps = setParameters(ps, objetos, testPlan);
		ps.setString(20, where);
		ps.setString(21, testPlan);
		return ps;
	}

	public static PreparedStatement setParameters(PreparedStatement ps,
			List objetos, String testPlan) throws SQLException {
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(objetos.get(1)));
		ps.setString(3, String.valueOf(objetos.get(2)));
		ps.setString(4, String.valueOf(objetos.get(3)));
		ps.setString(5, String.valueOf(objetos.get(4)));
		ps.setString(6, String.valueOf(objetos.get(5)));
		ps.setString(7, String.valueOf(objetos.get(6)));
		ps.setString(8, String.valueOf(objetos.get(7)));
		ps.setString(9, String.valueOf(objetos.get(8)));
		ps.setString(10, String.valueOf(objetos.get(9)));
		ps.setString(11, String.valueOf(objetos.get(10)));
		ps.setString(12, String.valueOf(objetos.get(11)));
		ps.setString(13, String.valueOf(objetos.get(12)));
		ps.setString(14, String.valueOf(objetos.get(13)));
		ps.setString(15, String.valueOf(objetos.get(14)));
		ps.setString(16, String.valueOf(objetos.get(15)));
		ps.setString(17, testPlan);
		ps.setString(18, String.valueOf(objetos.get(16)));
		ps.setString(19, String.valueOf(objetos.get(17)));
		return ps;
	}

	public static Connection singleton() throws ClassNotFoundException,
			SQLException {
		if (DerbyDatabase.conn == null) {

			Properties prop = new Properties();
			InputStream input = null;

			String databaseIp = null;
			String user = null;
			String password = null;

			try {

				input = new FileInputStream("workload.properties");

				// load a properties file
				prop.load(input);

				// get the property value and print it out
				databaseIp = prop.getProperty("databaseip");
				user = prop.getProperty("user");
				password = prop.getProperty("password");

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			Class.forName("com.mysql.jdbc.Driver");

			DerbyDatabase.conn = DriverManager.getConnection("jdbc:mysql://"
					+ databaseIp + ":3306/workload?" + "user=" + user
					+ "&password=" + password);

			DatabaseMetaData meta = conn.getMetaData();

			ResultSet tables = meta.getTables(null, null, "workload", null);
			if (tables.next()) {

				System.out.println("tabela workload existe");

			} else {

				PreparedStatement ps = conn
						.prepareStatement(DerbyDatabase.CREATEDATABASE);
				ps.executeUpdate();

			}

		}
		return conn;
	}

	public static void createDatabase() throws ClassNotFoundException,
			SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(DerbyDatabase.CREATEDATABASE);

		try {
			ps.executeUpdate();
		} catch (SQLException e) {

			if (!(e.getLocalizedMessage()
					.contains("Table/View 'WORKLOAD' already exists"))) {
				e.printStackTrace();
			}
		}

	}

	public static void updateResponseTime(Long responseTime, String workload,
			String testPlan, String generation) throws ClassNotFoundException,
			SQLException {
		updateResponseTime(String.valueOf(responseTime), workload, testPlan,
				generation);
	}

	public static void updateResponseTime(String responseTime, String workload,
			String testPlan, String generation) throws ClassNotFoundException,
			SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=? AND GENERATION=?");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0) {

			ps = con.prepareStatement(""
					+ "SELECT RESPONSETIME FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
			ps.setString(1, workload);
			ps.setString(2, testPlan);
			ps.setString(3, generation);

			rs = ps.executeQuery();

			String responseTimeDatabase = "";
			while (rs.next()) {
				responseTimeDatabase = rs.getString(1);
			}

			long responseTimeDatabaseLong = Long.valueOf(responseTimeDatabase);

			long responseTimeLong = Long.valueOf(responseTime);

			if (responseTimeLong > responseTimeDatabaseLong) {

				ps = con.prepareStatement("" + "update workload set "

				+ "RESPONSETIME=? WHERE NAME=? and TESTPLAN=?");
				ps.setString(1, responseTime);
				ps.setString(2, workload);
				ps.setString(3, testPlan);
				ps.executeUpdate();
			}
		}

	}

	public static void updateError(String error, String workload,
			String testPlan, String generation) throws ClassNotFoundException,
			SQLException {

		if (error.equals(true)) {

			long rst = 0;

			Connection con = singleton();

			PreparedStatement ps = con
					.prepareStatement(""
							+ "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=? AND GENERATION=?");
			ps.setString(1, workload);
			ps.setString(2, testPlan);
			ps.setString(3, generation);

			ResultSet rs = ps.executeQuery();

			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {

				ps = con.prepareStatement("" + "update workload set "

				+ "ERROR=? WHERE NAME=? and TESTPLAN=?");
				ps.setString(1, error);
				ps.setString(2, workload);
				ps.setString(3, testPlan);
				ps.executeUpdate();

			}
		}

	}

	public static long selectResponseTime(String workload, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT RESPONSETIME FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		String responseTime = "0";
		while (rs.next()) {
			responseTime = rs.getString(1);
		}

		rst = Long.valueOf(responseTime);
		return rst;

	}

	public static String selectError(String workload, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT error FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		String error = "false";
		while (rs.next()) {
			error = rs.getString(1);
		}

		return error;

	}

	public static int deleteTestPlan(String testPlan)
			throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "DELETE FROM  workload WHERE  TESTPLAN=? ");

		ps.setString(1, testPlan);

		return ps.executeUpdate();

	}

	public static double selectFit(String workload, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		double rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT FIT FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		String fit = "0";
		while (rs.next()) {
			fit = rs.getString(1);
		}

		rst = Double.valueOf(fit);
		return rst;

	}

	public static double updateFitValue(String workload, String testPlan,
			String generation, long maxTime) throws ClassNotFoundException,
			SQLException {

		double fit = 0;
		long responseTime = selectResponseTime(workload, testPlan, generation);
		double fitDatabase = selectFit(workload, testPlan, generation);
		String error = selectError(workload, testPlan, generation);

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=? AND GENERATION=?");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0) {

			if (responseTime < maxTime) {

				if (error.equals("true")) {
					fit = Long.MAX_VALUE;
				} else {
					fit = responseTime;
				}

			} else {

				if (error.equals("true")) {
					fit = Long.MAX_VALUE;
				} else {
					fit = Long.MIN_VALUE;
				}

			}

			if (fitDatabase >= 0) {
				if ((fit < 0) || (fit > fitDatabase)) {
					ps = con.prepareStatement(""
							+ "UPDATE workload SET FIT=? WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
					ps.setString(1, String.valueOf(fit));
					ps.setString(2, workload);
					ps.setString(3, testPlan);
					ps.setString(4, generation);

					ps.executeUpdate();

				}
			}
		}

		if (fitDatabase < 0) {
			return fitDatabase;
		}

		if (fit > fitDatabase)
			return fit;
		else
			return fitDatabase;

	}

	public static void insertWorkLoads(Object[] objetos, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {
		insertWorkLoads(Arrays.asList(objetos), testPlan, generation);
	}

	public static void insertWorkLoads(List objetos, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(testPlan));

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement(DerbyDatabase.INSERTSQL);
			DerbyDatabase.setParameters(ps, objetos, testPlan);
		} else {

			ps = con.prepareStatement("" + "update workload set "
					+ DerbyDatabase.SET + " WHERE NAME=? AND TESTPLAN=?");
			DerbyDatabase.setParametersWhere(ps, objetos,
					String.valueOf(objetos.get(0)), testPlan);
		}

		ps.executeUpdate();

	}

	public static void insertLog(String message) throws ClassNotFoundException,
			SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(DerbyDatabase.INSERTSQLLog);
		ps.setString(1, message);
		ps.executeUpdate();

	}

	public static void createWorkLoadIfNotExist(List objetos, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(testPlan));

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement(DerbyDatabase.INSERTSQL);
			DerbyDatabase.setParameters(ps, objetos, testPlan);
			ps.executeUpdate();
		}

	}

	public static int verifyRunning() throws ClassNotFoundException,
			SQLException, InterruptedException {

		Thread.sleep(1000);

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM agent WHERE RUNNING='true'");

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}

		rs = null;
		ps = null;
		return count;

	}

	public static void deleteAgent(List objetos, String testPlan)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "DELETE  FROM  agent WHERE NAME=? AND IP=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(objetos.get(2)));
		ps.executeUpdate();

	}

	public static void deleteWorkLoad(String name, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "DELETE  FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
		ps.setString(1, name);
		ps.setString(2, testPlan);
		ps.setString(3, generation);
		ps.executeUpdate();

	}

	public static List<Agent> selectAgents() throws ClassNotFoundException,
			SQLException {

		List<Agent> list = new ArrayList<Agent>();

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "select *  FROM  agent ");

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Agent agent = new Agent();
			String name = rs.getString(1);
			String running = rs.getString(2);
			String ip = rs.getString(3);
			agent.setIp(ip);
			agent.setName(name);
			agent.setRunning(running);
			list.add(agent);
		}
		return list;

	}

	public static void updateAgentOrCreateIfNotExist(List objetos,
			String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(""
				+ "SELECT count(*) FROM  AGENT WHERE NAME=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement(DerbyDatabase.INSERTSQLAGENT);
			ps.setString(1, objetos.get(0).toString());
			ps.setString(2, objetos.get(1).toString());
			ps.setString(3, objetos.get(2).toString());
			ps.executeUpdate();
		} else {

			ps = con.prepareStatement("UPDATE AGENT SET RUNNING=? WHERE NAME=? AND IP=?");

			ps.setString(1, objetos.get(1).toString());
			ps.setString(2, objetos.get(0).toString());
			ps.setString(3, objetos.get(2).toString());
			ps.executeUpdate();
		}

	}

	public static List<WorkLoad> listWorkLoads(String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT "
						+ COLUMNS
						+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=? ORDER BY FIT DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = DerbyDatabase.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listAllWorkLoads(String testPlan)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=?  ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = DerbyDatabase.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listWorkLoadsByGeneration(String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT "
						+ COLUMNS
						+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=? ORDER BY FIT DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = DerbyDatabase.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static WorkLoad resultSetToWorkLoad(ResultSet rs)
			throws SQLException {
		String type = rs.getString(2);
		WorkLoad workload = FactoryWorkLoad.createWorkLoad(type);
		workload.setName(rs.getString(1));
		workload.setType(type);
		workload.setNumThreads(Integer.valueOf(rs.getString(3)));
		workload.setWorstResponseTime(Long.valueOf(rs.getString(4)));
		workload.setError(Boolean.valueOf(rs.getString(5)));
		workload.setFit(Double.valueOf(rs.getString(6)));
		workload.setFunction1(rs.getString(7));
		workload.setFunction2(rs.getString(8));
		workload.setFunction3(rs.getString(9));
		workload.setFunction4(rs.getString(10));
		workload.setFunction5(rs.getString(11));
		workload.setFunction6(rs.getString(12));
		workload.setFunction7(rs.getString(13));
		workload.setFunction8(rs.getString(14));
		workload.setFunction9(rs.getString(15));
		workload.setFunction10(rs.getString(16));
		if (rs.getString(18) != null) {
			workload.setGeneration(Integer.valueOf(rs.getString(18)));
		}
		if (rs.getString(19) != null) {
			workload.setActive(Boolean.valueOf(rs.getString(19)));
		}
		return workload;
	}

	public static long getResponseTime(String ip, String workLoad,
			String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement(""
						+ "SELECT RESPONSETIME FROM  workload WHERE NAME=? AND TESTPLAN=?");
		ps.setString(1, workLoad);
		ps.setString(2, testPlan);

		ResultSet rs = ps.executeQuery();

		long responseTime = 0;

		while (rs.next()) {
			responseTime = rs.getLong(1);
		}

		return responseTime;

	}

}
