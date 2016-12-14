/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package br.unifor.iadapter.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import br.unifor.iadapter.agent.Agent;
import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.algorithm.InitialPopulationAlgorithm;
import br.unifor.iadapter.algorithm.Q;
import br.unifor.iadapter.percentiles.PercentileCounter;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.WorkLoadUtil;

public class MySQLDatabase {

	public void insertSample(List<Object> objects) {

	}

	private static Connection conn = null;

	private final static String COLUMNS = "NAME,TYPE," + "USERS,RESPONSETIME,ERROR,FIT,"
			+ "FUNCTION1,FUNCTION2,FUNCTION3,FUNCTION4," + "FUNCTION5,FUNCTION6,FUNCTION7,FUNCTION8,"
			+ "FUNCTION9,FUNCTION10,TESTPLAN,GENERATION," + "ACTIVE,PERCENT90,PERCENT80,PERCENT70,TOTALERROR,"
			+ "SEARCHMETHOD,USER1,USER2,USER3,USER4,USER5,USER6" + ",USER7,USER8,USER9,USER10";

	private final static String COLUMNSAMPLES = "LABEL,RESPONSETIME," + "MESSAGE,INDIVIDUAL,GENERATION,TESTPLAN";

	private final static String INSERTSQLSAMPLE = "insert into  samples(" + MySQLDatabase.COLUMNSAMPLES + ") values ("
			+ MySQLDatabase.PARAMETERSSAMPLE + ")";

	private final static String COLUMNSAGENT = "name,running," + "ip,date";

	private final static String PARAMETERS = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

	private final static String PARAMETERSSAMPLE = "?,?,?,?,?,?";

	private final static String PARAMETERSAGENT = "?,?,?,?";

	private final static String SET = "NAME=?,TYPE=?," + "USERS=?,RESPONSETIME=?,ERROR=?,FIT=?,FUNCTION1=?,"
			+ "FUNCTION2=?,FUNCTION3=?,FUNCTION4=?,FUNCTION5=?," + "FUNCTION6=?,FUNCTION7=?,FUNCTION8=?,FUNCTION9=?,"
			+ "FUNCTION10=?,TESTPLAN=?,GENERATION=?,ACTIVE=?,PERCENT90=?,PERCENT80=?,"
			+ "PERCENT70=?,TOTALERROR=?,SEARCHMETHOD=?,USER1=?,USER2=?,"
			+ "USER3=?,USER4=?,USER5=?,USER6=?,USER7=?,USER8=?,USER9=?,USER10=?";

	private final static String SETAGENT = "name=?,running=?," + "ip=?";

	private final static String INSERTSQL = "insert into  workload(" + MySQLDatabase.COLUMNS + ") values ("
			+ MySQLDatabase.PARAMETERS + ")";

	private final static String INSERTSQLLog = "insert into  log(" + "log) values (?)";

	private final static String INSERTSQLAGENT = "insert into  agent(" + MySQLDatabase.COLUMNSAGENT + ") values ("
			+ MySQLDatabase.PARAMETERSAGENT + ")";

	public static PreparedStatement setParametersWhere(AbstractAlgorithm algorithm, PreparedStatement ps, List objetos,
			String where, String testPlan) throws SQLException {
		ps = setParameters(algorithm, ps, objetos, testPlan);

		System.out.println("Update " + where);
		System.out.println("Update " + testPlan);
		ps.setString(35, where);
		ps.setString(36, testPlan);

		return ps;
	}

	public static PreparedStatement setParameters(AbstractAlgorithm algorithm, PreparedStatement ps, List objetos,
			String testPlan) throws SQLException {
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
		ps.setString(20, String.valueOf(objetos.get(18)));
		ps.setString(21, String.valueOf(objetos.get(19)));
		ps.setString(22, String.valueOf(objetos.get(20)));
		ps.setString(23, String.valueOf(objetos.get(21)));

		ps.setString(24, String.valueOf(algorithm.getClass().getCanonicalName()));

		ps.setString(25, String.valueOf(objetos.get(23)));
		ps.setString(26, String.valueOf(objetos.get(24)));
		ps.setString(27, String.valueOf(objetos.get(25)));
		ps.setString(28, String.valueOf(objetos.get(26)));
		ps.setString(29, String.valueOf(objetos.get(27)));
		ps.setString(30, String.valueOf(objetos.get(28)));
		ps.setString(31, String.valueOf(objetos.get(29)));
		ps.setString(32, String.valueOf(objetos.get(30)));
		ps.setString(33, String.valueOf(objetos.get(31)));
		ps.setString(34, String.valueOf(objetos.get(32)));
		// ps.setString(35, String.valueOf(objetos.get(33)));
		return ps;
	}

	public static PreparedStatement setParametersSample(PreparedStatement ps, List objetos, String testPlan,
			String generation) throws SQLException {
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(objetos.get(1)));
		ps.setString(3, String.valueOf(objetos.get(2)));
		ps.setString(4, String.valueOf(objetos.get(3)));
		ps.setString(5, generation);
		ps.setString(6, testPlan);
		return ps;
	}

	public static Connection singleton() throws ClassNotFoundException, SQLException {
		if (MySQLDatabase.conn == null) {

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

			MySQLDatabase.conn = DriverManager.getConnection(
					"jdbc:mysql://" + databaseIp + ":3306/workspace?" + "user=" + user + "&password=" + password);

		}
		return conn;
	}

	public static void updateResponseTime(Long responseTime, String workload, String testPlan, String generation,
			PercentileCounter counter, String errors) throws ClassNotFoundException, SQLException {
		updateResponseTime(String.valueOf(responseTime), workload, testPlan, generation, counter, errors);
	}

	public static void updateResponseTime(String responseTime, String workload, String testPlan, String generation,
			PercentileCounter counter, String errors) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(
				"" + "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=? AND GENERATION=? AND ACTIVE='true'");
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
					+ "SELECT RESPONSETIME,PERCENT90,PERCENT80,PERCENT70,TOTALERROR FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=? AND ACTIVE='true'");
			ps.setString(1, workload);
			ps.setString(2, testPlan);
			ps.setString(3, generation);

			rs = ps.executeQuery();

			String responseTimeDatabase = "";
			String responseTimeDatabasePercent90 = "";
			String responseTimeDatabasePercent80 = "";
			String responseTimeDatabasePercent70 = "";
			String totalError = "";
			while (rs.next()) {
				responseTimeDatabase = rs.getString(1);
				responseTimeDatabasePercent90 = rs.getString(2);
				responseTimeDatabasePercent80 = rs.getString(3);
				responseTimeDatabasePercent70 = rs.getString(4);
				totalError = rs.getString(5);
			}

			long responseTimeDatabaseLong = Long.valueOf(responseTimeDatabase);
			long responseTimeDatabaseLongPercent90 = Long.valueOf(responseTimeDatabasePercent90);
			long responseTimeDatabaseLongPercent80 = Long.valueOf(responseTimeDatabasePercent80);
			long responseTimeDatabaseLongPercent70 = Long.valueOf(responseTimeDatabasePercent70);
			long totalErrorLong = 0;
			if (totalError != null) {
				totalErrorLong = Long.valueOf(totalError);
			}
			long errorsLong = 0;
			if (errors != null) {
				errorsLong = Long.valueOf(errors);
			}
			totalErrorLong += errorsLong;

			ps = con.prepareStatement("" + "update workload set "

					+ "TOTALERROR=? WHERE NAME=? and TESTPLAN=? AND ACTIVE='true'  AND GENERATION=?");
			ps.setString(1, String.valueOf(totalErrorLong));
			ps.setString(2, workload);
			ps.setString(3, testPlan);
			ps.setString(4, generation);
			ps.executeUpdate();

			if (responseTime != null) {

				long responseTimeLong = Long.valueOf(responseTime);

				if (responseTimeLong > responseTimeDatabaseLong) {

					ps = con.prepareStatement("" + "update workload set "

							+ "RESPONSETIME=? WHERE NAME=? and TESTPLAN=? AND ACTIVE='true'");
					ps.setString(1, responseTime);
					ps.setString(2, workload);
					ps.setString(3, testPlan);
					ps.executeUpdate();
				}
			}

			if (counter != null) {

				long responseTime90 = counter.estimatePercentile(90);
				long responseTime80 = counter.estimatePercentile(80);
				long responseTime70 = counter.estimatePercentile(70);

				if (responseTime90 > 0) {

					if (responseTime90 > responseTimeDatabaseLongPercent90) {

						ps = con.prepareStatement("" + "update workload set "

								+ "PERCENT90=? WHERE NAME=? and TESTPLAN=? AND ACTIVE='true'");
						ps.setString(1, String.valueOf(responseTime90));
						ps.setString(2, workload);
						ps.setString(3, testPlan);
						ps.executeUpdate();
					}

					if (responseTime80 > responseTimeDatabaseLongPercent80) {

						ps = con.prepareStatement("" + "update workload set "

								+ "PERCENT80=? WHERE NAME=? and TESTPLAN=? AND ACTIVE='true'");
						ps.setString(1, String.valueOf(responseTime80));
						ps.setString(2, workload);
						ps.setString(3, testPlan);
						ps.executeUpdate();
					}

					if (responseTime70 > responseTimeDatabaseLongPercent70) {

						ps = con.prepareStatement("" + "update workload set "

								+ "PERCENT70=? WHERE NAME=? and TESTPLAN=? AND ACTIVE='true'");
						ps.setString(1, String.valueOf(responseTime70));
						ps.setString(2, workload);
						ps.setString(3, testPlan);
						ps.executeUpdate();
					}

				}
			}

		}

	}

	public static void updateError(String error, String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {
		if (error != null) {

			if (error.equals("true")) {

				Connection con = singleton();

				PreparedStatement ps = con.prepareStatement(""
						+ "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=? AND GENERATION=? AND ACTIVE='true'");
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

							+ "ERROR=? WHERE NAME=? and TESTPLAN=? AND ACTIVE='true'");
					ps.setString(1, error);
					ps.setString(2, workload);
					ps.setString(3, testPlan);
					ps.executeUpdate();

				}
			}
		}

	}

	public static long selectResponseTimePercent90(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT PERCENT90 FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
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

	public static long selectResponseTimePercent70(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT PERCENT70 FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
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

	public static long selectResponseTimePercent80(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT PERCENT80 FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
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

	public static long selectResponseTime(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(
				"" + "SELECT RESPONSETIME FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
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

	public static String selectError(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(
				"" + "SELECT error FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=? AND ACTIVE='true'");
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

	public static long selectTotalError(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(
				"" + "SELECT TOTALERROR FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=? AND ACTIVE='true'");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		long totalError = 0;
		while (rs.next()) {
			totalError = Long.valueOf(rs.getString(1));
		}

		return totalError;

	}

	public static int deleteTestPlan(String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "DELETE FROM  samples WHERE  TESTPLAN=? ");

		ps.setString(1, testPlan);

		ps.executeUpdate();

		ps = con.prepareStatement("" + "DELETE FROM  workload WHERE  TESTPLAN=? ");

		ps.setString(1, testPlan);

		return ps.executeUpdate();

	}

	public static double selectFit(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		double rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT FIT FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
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

	public static long selectUsers(String workload, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		long rst = 0;

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT USERS FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		String user = "0";
		while (rs.next()) {
			user = rs.getString(1);
		}

		rst = Long.valueOf(user);
		return rst;

	}

	public static void updateActiveValue(String workload, String testPlan, String generation, long maxTime)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();
		PreparedStatement ps = con.prepareStatement(
				"" + "UPDATE workload SET ACTIVE='false' WHERE NAME=? AND TESTPLAN=? and GENERATION=?");

		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ps.executeUpdate();

	}

	public static void desactiveValue(String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();
		PreparedStatement ps = con
				.prepareStatement("" + "UPDATE workload SET ACTIVE='false' WHERE FIT!=0  and TESTPLAN=? ");

		ps.setString(1, testPlan);

		ps.executeUpdate();

	}

	public static double updateFitValue(String workload, String testPlan, String generation, long maxTime,
			WorkLoadThreadGroup tg, long responseTimeMaxPenalty) throws ClassNotFoundException, SQLException {

		double fit = 0;
		long responseTime = selectResponseTime(workload, testPlan, generation);
		long responseTime90Percent = selectResponseTimePercent90(workload, testPlan, generation);
		long responseTime80Percent = selectResponseTimePercent80(workload, testPlan, generation);
		long responseTime70Percent = selectResponseTimePercent80(workload, testPlan, generation);

		long users = selectUsers(workload, testPlan, generation);

		double fitDatabase = selectFit(workload, testPlan, generation);
		long totalError = selectTotalError(workload, testPlan, generation);

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=? AND GENERATION=? ");
		ps.setString(1, workload);
		ps.setString(2, testPlan);
		ps.setString(3, generation);

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0) {
			double penalty = 0;

			if (responseTime90Percent > maxTime) {
				double distance = responseTime90Percent - maxTime;
				penalty += responseTimeMaxPenalty * distance;
			}
			if (responseTime > maxTime) {
				double distance = responseTime - maxTime;
				penalty += responseTimeMaxPenalty * distance;
			}
			if (responseTime80Percent > maxTime) {
				double distance = responseTime80Percent - maxTime;
				penalty += responseTimeMaxPenalty * distance;
			}
			if (responseTime70Percent > maxTime) {
				double distance = responseTime70Percent - maxTime;
				penalty += responseTimeMaxPenalty * distance;
			}

			if (responseTime > 0) {

				fit = responseTime90Percent * Double.valueOf(tg.getResponse90FitWeight())
						+ responseTime80Percent * Double.valueOf(tg.getResponse80FitWeight())
						+ responseTime70Percent * Double.valueOf(tg.getResponse70FitWeight())
						+ responseTime * Double.valueOf(tg.getResponseMaxFitWeight())
						+ totalError * Double.valueOf(tg.getTotalErrorFitWeight())
						+ users * Double.valueOf(tg.getUserFitWeight()) + penalty;

				if ((fitDatabase >= 0) && (fitDatabase != 0.5)) {
					if ((fit < 0) || (fit > fitDatabase) || (fit == 0.5)) {
						ps = con.prepareStatement(
								"" + "UPDATE workload SET FIT=? WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
						ps.setString(1, String.valueOf(fit));
						ps.setString(2, workload);
						ps.setString(3, testPlan);
						ps.setString(4, generation);

						ps.executeUpdate();

					}
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

	public static void insertWorkLoads(AbstractAlgorithm algorithm, Object[] objetos, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {
		insertWorkLoads(algorithm, Arrays.asList(objetos), testPlan, generation);
	}

	public static void insertWorkLoads(AbstractAlgorithm algorithm, List objetos, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(testPlan));

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement(MySQLDatabase.INSERTSQL);
			MySQLDatabase.setParameters(algorithm, ps, objetos, testPlan);
		} else {

			System.out.println("Atualizando workload");

			ps = con.prepareStatement("" + "update workload set " + MySQLDatabase.SET + " WHERE NAME=? AND TESTPLAN=?");
			MySQLDatabase.setParametersWhere(algorithm, ps, objetos, String.valueOf(objetos.get(0)), testPlan);

			System.out.println("Objetos:" + String.valueOf(objetos.get(0)));

		}

		ps.executeUpdate();
		ps.close();
	}
	
	
	public static void insertQ(String range,String testplan,String state,double Q)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM  Q WHERE responsetime=? AND TESTPLAN=? and state=?");
		ps.setString(1, String.valueOf(range));
		ps.setString(2, String.valueOf(testplan));
		ps.setString(3, String.valueOf(state));

		ResultSet rs = ps.executeQuery();
		

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement("INSERT INTO Q(responsetime,testplan,state,Qvalue) VALUES (?,?,?,?)");
			ps.setString(1, String.valueOf(range));
			ps.setString(2, String.valueOf(testplan));
			ps.setString(3, String.valueOf(state));
			ps.setDouble(4, Q);
			ps.executeUpdate();
	
			
		} else {

			System.out.println("Atualizando q");

			PreparedStatement preparedStatement =con.prepareStatement("update Q set Qvalue=?  WHERE responsetime=? AND TESTPLAN=? and state=?");
			preparedStatement.setDouble(1, Q);
			preparedStatement.setString(2, String.valueOf(range));
			preparedStatement.setString(3, String.valueOf(testplan));
			preparedStatement.setString(4, String.valueOf(state));
			preparedStatement.executeUpdate();
			

		}

		
		ps.close();
	}
	
	public static int selectEpsilonQ(String testplan)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM  Q WHERE Qvalue=0 and testPlan=?");
		ps.setString(1, testplan);
		ResultSet rs = ps.executeQuery();
		

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		
		
		ps.close();
		
		return count;
	}
	
	
	public static void insertQifNotExist(String range,String testplan,String state,double Q)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM  Q WHERE responsetime=? AND TESTPLAN=? and state=?");
		ps.setString(1, String.valueOf(range));
		ps.setString(2, String.valueOf(testplan));
		ps.setString(3, String.valueOf(state));

		ResultSet rs = ps.executeQuery();
		

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement("INSERT INTO Q(responsetime,testplan,state,Qvalue) VALUES (?,?,?,?)");
			ps.setString(1, String.valueOf(range));
			ps.setString(2, String.valueOf(testplan));
			ps.setString(3, String.valueOf(state));
			ps.setDouble(4, Q);
			ps.executeUpdate();
	
			
		} 
		
		ps.close();
	}

	public static void insertSample(List objetos, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT count(*) FROM  samples WHERE LABEL=? AND INDIVIDUAL=? AND TESTPLAN=?");
		ps.setString(1, objetos.get(0).toString());
		ps.setString(2, objetos.get(3).toString());
		ps.setString(3, testPlan);

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count == 0) {

			ps = con.prepareStatement(MySQLDatabase.INSERTSQLSAMPLE);
			MySQLDatabase.setParametersSample(ps, objetos, testPlan, generation);

			ps.executeUpdate();
		} else {

			ps = con.prepareStatement(""
					+ "SELECT RESPONSETIME FROM  samples WHERE LABEL=? AND INDIVIDUAL=? AND TESTPLAN=? AND GENERATION=?");
			ps.setString(1, objetos.get(0).toString());
			ps.setString(2, objetos.get(3).toString());
			ps.setString(3, testPlan);
			ps.setString(4, generation);

			long responseTimeDatabase = 0;
			while (rs.next()) {
				responseTimeDatabase = rs.getLong(1);
			}

			Long responseTime = Long.valueOf(objetos.get(1).toString());

			if (responseTime > responseTimeDatabase) {

				ps = con.prepareStatement(""
						+ "UPDATE samples SET RESPONSETIME=? WHERE LABEL=? AND INDIVIDUAL=? AND TESTPLAN=? AND GENERATION=?");
				ps.setString(1, objetos.get(1).toString());
				ps.setString(2, objetos.get(0).toString());
				ps.setString(3, objetos.get(3).toString());
				ps.setString(4, testPlan);
				ps.setString(5, generation);

			}

		}

	}

	public static void insertLog(String message) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(MySQLDatabase.INSERTSQLLog);
		ps.setString(1, message);
		ps.executeUpdate();

	}

	public static void createWorkLoadIfNotExist(AbstractAlgorithm algorithm, List objetos, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM  workload WHERE NAME=? AND TESTPLAN=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(testPlan));

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement(MySQLDatabase.INSERTSQL);
			MySQLDatabase.setParameters(algorithm, ps, objetos, testPlan);
			ps.executeUpdate();
		}

	}

	public static int verifyRunning() throws ClassNotFoundException, SQLException, InterruptedException {

		Thread.sleep(1000);

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM agent WHERE running='true'");

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}

		rs = null;
		ps = null;
		return count;

	}

	public static int verifyRunningIp()
			throws ClassNotFoundException, SQLException, InterruptedException, UnknownHostException {

		Thread.sleep(1000);

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM agent WHERE running='true' and ip=?");
		ps.setString(1, java.net.InetAddress.getLocalHost().toString());

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}

		rs = null;
		ps = null;
		return count;

	}

	public static int verifyRunningFinal() throws ClassNotFoundException, SQLException, InterruptedException {

		Thread.sleep(1000);

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM agent WHERE running='final'");

		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}

		rs = null;
		ps = null;
		return count;

	}

	public static void deleteAgent(List objetos, String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "DELETE  FROM  agent WHERE NAME=? AND IP=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ps.setString(2, String.valueOf(objetos.get(2)));
		ps.executeUpdate();

	}
	
	
	public static void deleteQ( String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "DELETE  FROM  Q WHERE testplan=?");
		ps.setString(1, String.valueOf(testPlan));
		
		ps.executeUpdate();

	}

	public static void deleteWorkLoad(String name, String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "DELETE  FROM  workload WHERE NAME=? AND TESTPLAN=? and GENERATION=?");
		ps.setString(1, name);
		ps.setString(2, testPlan);
		ps.setString(3, generation);
		ps.executeUpdate();

		ps = con.prepareStatement("" + "DELETE  FROM  samples WHERE INDIVIDUAL=? AND TESTPLAN=?");
		ps.setString(1, name);
		ps.setString(2, testPlan);
		ps.executeUpdate();

	}

	public static List<Agent> selectAgents() throws ClassNotFoundException, SQLException {

		List<Agent> list = new ArrayList<Agent>();

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "select name,running,ip,date  FROM  agent ");

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Agent agent = new Agent();
			String name = rs.getString(1);
			String running = rs.getString(2);
			String ip = rs.getString(3);
			String date = rs.getString(4);
			agent.setIp(ip);
			agent.setName(name);
			agent.setRunning(running);
			agent.setDate(date);
			list.add(agent);
		}
		return list;

	}
	
	public static HashMap<String,Q> selectQ(String testPlan,String responseTime) throws ClassNotFoundException, SQLException {

		HashMap<String,Q> list = new HashMap<>();

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "select Qvalue,state  FROM  Q where testplan=? and responsetime=? ORDER BY Qvalue DESC");
		ps.setString(1, testPlan);
		ps.setString(2, responseTime);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Q q=new Q();
			
			double qvalue = rs.getDouble(1);
			
			String state = rs.getString(2);
			
			q.setQ(qvalue);
			q.setState(state);
			
			
			list.put(q.getState(), q);
		}
		return list;

	}
	
	
	public static List<Q> selectListQ(String testPlan,String responseTime) throws ClassNotFoundException, SQLException {

		List<Q> list = new ArrayList<>();

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "select Qvalue,state  FROM  Q where testplan=? and responsetime=? ORDER BY Qvalue DESC");
		ps.setString(1, testPlan);
		ps.setString(2, responseTime);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Q q=new Q();
			
			double qvalue = rs.getDouble(1);
			
			String state = rs.getString(2);
			
			q.setQ(qvalue);
			q.setState(state);
			
			
			list.add(q);
		}
		return list;

	}
	
	
	public static List<Q> selectListQZero(String testPlan, int responseTime) throws ClassNotFoundException, SQLException {

		List<Q> list = new ArrayList<>();

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "select Qvalue,state  FROM  Q where testplan=? and QValue=0 and responsetime=?");
		ps.setString(1, testPlan);
		ps.setInt(2, responseTime);
		

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Q q=new Q();
			
			double qvalue = rs.getDouble(1);
			
			String state = rs.getString(2);
			
			q.setQ(qvalue);
			q.setState(state);
			
			
			list.add(q);
		}
		return list;

	}

	public static void updateAgentOrCreateIfNotExist(@SuppressWarnings("rawtypes") List objetos, String testPlan)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT count(*) FROM  agent WHERE name=?");
		ps.setString(1, String.valueOf(objetos.get(0)));
		ResultSet rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count <= 0) {
			ps = con.prepareStatement(MySQLDatabase.INSERTSQLAGENT);
			ps.setString(1, objetos.get(0).toString());
			ps.setString(2, objetos.get(1).toString());
			ps.setString(3, objetos.get(2).toString());
			ps.setString(4, objetos.get(3).toString());
			ps.executeUpdate();
		} else {

			ps = con.prepareStatement("UPDATE agent SET running=? WHERE name=? AND ip=?");

			ps.setString(1, objetos.get(1).toString());
			ps.setString(2, objetos.get(0).toString());
			ps.setString(3, objetos.get(2).toString());
			ps.executeUpdate();
			ps.close();
		}

	}

	public static List<WorkLoad> listWorkLoads(String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=? AND ACTIVE='true' ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}
	
	public static List<WorkLoad> listWorkLoadsASC(String testPlan, int generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=? AND ACTIVE='true' ORDER BY FIT*1 ASC");
		ps.setString(1, testPlan);
		ps.setString(2, String.valueOf(generation));

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listWorkLoadsOrderName(String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=?  AND ACTIVE='true' ORDER BY NAME ");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listWorkLoadsForNewGeneration(AbstractAlgorithm algorithm, String testPlan,
			String generation) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN=? AND GENERATION=? "
						+ "AND SEARCHMETHOD='" + algorithm + "' ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}
	
	
	public static List<WorkLoad> listBestWorkLoadsForAll(String testPlan,int populationSize)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=?  " + "  ORDER BY FIT*1 DESC LIMIT "+populationSize);
		ps.setString(1, testPlan);
		

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listWorkLoadsForAllNewGeneration(String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=? " + "  ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listWorkLoadsForNewGenerationByMethod(String testPlan, String generation,
			AbstractAlgorithm algorithm,int limit) throws ClassNotFoundException, SQLException {

		Connection con = singleton();
		
		String sql="" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN="+testPlan
				+ "AND SEARCHMETHOD='" + algorithm.getClass().getCanonicalName() + "' and GENERATION='"+generation+" ORDER BY FIT*1 DESC LIMIT "
				+ limit;
		
		System.out.println("SQL "+sql);	

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN=? "
				+ "AND SEARCHMETHOD='" + algorithm.getClass().getCanonicalName() + "' and GENERATION=? ORDER BY FIT*1 DESC LIMIT "
				+ limit);
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}
	
	public static List<WorkLoad> listWorkLoadsForNewGenerationByMethodAllGenerations(String testPlan,
			AbstractAlgorithm algorithm,int populationSize) throws ClassNotFoundException, SQLException {

		Connection con = singleton();
		
	

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE ( TESTPLAN=? "
				+ "AND SEARCHMETHOD='" + algorithm.getClass().getCanonicalName() + "' ) OR generation=1 ORDER BY FIT*1 DESC LIMIT "+populationSize);
		ps.setString(1, testPlan);
		
		
		

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}
		
		System.out.println("New Tabu Best "+list);

		return list;

	}

	public static List<WorkLoad> listWorkLoadsALlForNewGeneration(String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=? and  GENERATION=? " + " ORDER BY FIT*1 DESC LIMIT 10");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static List<WorkLoad> listAllWorkLoads(String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN=?  ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static int listBestWorkloadUsers(String testPlan, String type) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement(
				"" + "SELECT USERS" + "  FROM  workload WHERE TESTPLAN=? " + " AND TYPE=? ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, type);

		ResultSet rs = ps.executeQuery();

		int users = 0;

		if (rs.next()) {
			users = Integer.valueOf(rs.getString(1));
		}

		return users;

	}

	public static int listBestWorkloadUsers(String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT USERS" + "  FROM  workload WHERE TESTPLAN=? " + "  ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		int users = 0;

		if (rs.next()) {
			users = Integer.valueOf(rs.getString(1));
		}

		return users;

	}

	public static int listWorstWorkload(String testPlan, String type) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT USERS" + "  FROM  workload WHERE TESTPLAN=? "
				+ "AND (ERROR='true' OR CAST(FIT AS DECIMAL)<0) " + "AND TYPE=? ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, type);

		ResultSet rs = ps.executeQuery();

		int users = 0;

		if (rs.next()) {
			users = Integer.valueOf(rs.getString(1));
		}

		return users;

	}

	public static List<WorkLoad> listBESTWorkloadAllPopulationSize(String testPlan, int populationSize)
			throws ClassNotFoundException, SQLException {

		List<WorkLoad> list = new ArrayList<WorkLoad>();
		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN=? "
				+ "  ORDER BY FIT*1 DESC LIMIT " + populationSize);
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;
	}

	public static List<WorkLoad> listBESTWorkloadPopulationSize(AbstractAlgorithm algorithm, String testPlan,
			int populationSize) throws ClassNotFoundException, SQLException {

		List<WorkLoad> list = new ArrayList<WorkLoad>();
		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN=? AND (SEARCHMETHOD='"
						+ algorithm.getClass().getCanonicalName()+ "' " + " OR SEARCHMETHOD='"+InitialPopulationAlgorithm.class.getCanonicalName() +"')  ORDER BY FIT*1 DESC LIMIT " + populationSize);
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;
	}
	
	
	public static List<WorkLoad> listPopulationGA(AbstractAlgorithm algorithm, String testPlan,
			int generation) throws ClassNotFoundException, SQLException {

		List<WorkLoad> list = new ArrayList<WorkLoad>();
		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT " + COLUMNS + "  FROM  workload WHERE TESTPLAN=? AND (SEARCHMETHOD='"
						+ algorithm.getClass().getCanonicalName()+ "' " + " OR SEARCHMETHOD='"+InitialPopulationAlgorithm.class.getCanonicalName() +"') AND GENERATION='"+generation+"'  ORDER BY FIT*1 DESC ");
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;
	}

	public static int listMaxUserWithNoErroWorkloadGenetic(String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT USERS" + "  FROM  workload WHERE TESTPLAN=? "
				+ "AND (CAST(TOTALERROR AS DECIMAL)=0) AND GENERATION=? " + "ORDER BY USERS*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		int users = 0;

		if (rs.next()) {
			users = Integer.valueOf(rs.getString(1));
		}

		return users;

	}

	public static int listWorstWorkload(String testPlan) throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT USERS" + "  FROM  workload WHERE TESTPLAN=? "
				+ "AND (ERROR='true' OR CAST(FIT AS DECIMAL)<0) " + " ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);

		ResultSet rs = ps.executeQuery();

		int users = 0;

		if (rs.next()) {
			users = Integer.valueOf(rs.getString(1));
		}

		return users;

	}

	public static List<WorkLoad> listWorkLoadsByGeneration(String testPlan, String generation)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con.prepareStatement("" + "SELECT " + COLUMNS
				+ "  FROM  workload WHERE TESTPLAN=? AND GENERATION=?" + " AND ACTIVE='true'  ORDER BY FIT*1 DESC");
		ps.setString(1, testPlan);
		ps.setString(2, generation);

		ResultSet rs = ps.executeQuery();

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		while (rs.next()) {
			WorkLoad workload = WorkLoadUtil.resultSetToWorkLoad(rs);
			list.add(workload);
		}

		return list;

	}

	public static long getResponseTime(String ip, String workLoad, String testPlan)
			throws ClassNotFoundException, SQLException {

		Connection con = singleton();

		PreparedStatement ps = con
				.prepareStatement("" + "SELECT RESPONSETIME FROM  workload WHERE NAME=? AND TESTPLAN=?");
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
