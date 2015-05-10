package br.unifor.iadapter.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.testelement.TestElement;
import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.impl.IntegerGene;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.tabu.TabuElement;
import br.unifor.iadapter.threadGroup.FactoryWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class WorkLoadUtil {

	public static TabuElement convertTabu(WorkLoad workload,
			List<TestElement> nodes) {
		TabuElement tabu = new TabuElement();
		tabu.setUsers(workload.getUsers1());
		tabu.setFunc1(getIndex(nodes, workload.getFunction1()));
		tabu.setFunc2(getIndex(nodes, workload.getFunction2()));
		tabu.setFunc3(getIndex(nodes, workload.getFunction3()));
		tabu.setFunc4(getIndex(nodes, workload.getFunction4()));
		tabu.setFunc5(getIndex(nodes, workload.getFunction5()));
		tabu.setFunc6(getIndex(nodes, workload.getFunction6()));
		tabu.setFunc7(getIndex(nodes, workload.getFunction7()));
		tabu.setFunc8(getIndex(nodes, workload.getFunction8()));
		tabu.setFunc9(getIndex(nodes, workload.getFunction9()));
		tabu.setFunc10(getIndex(nodes, workload.getFunction10()));
		tabu.setTotal(tabu.getFunc1() + tabu.getFunc2() + tabu.getFunc3()
				+ tabu.getFunc4() + tabu.getFunc5() + tabu.getFunc6()
				+ tabu.getFunc7() + tabu.getFunc8() + tabu.getFunc9()
				+ tabu.getFunc10());
		return tabu;

	}

	public static int randInt(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static int getIndexType(String type) {
		int index = 0;
		int counter = 0;
		for (String typeString : WorkLoad.getTypes()) {
			if (typeString.equals(type)) {
				index = counter;
			}
			counter++;
		}
		return index;
	}

	public static List<WorkLoad> getNeighBoors(WorkLoad workload,
			List<TestElement> nodes, int maxUsers,int generation) {
		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (int i = 0; i < 10; i++) {
			WorkLoad neighboor = getNeighBoor(workload, nodes, maxUsers);
			neighboor.setGeneration(generation);
			list.add(neighboor);
		}

		return list;

	}

	public static WorkLoad getNeighBoor(WorkLoad workload,
			List<TestElement> nodes, int maxUsers) {
		Random random = new Random();
		List<Integer> parametros = new ArrayList<Integer>();
		parametros.add(getIndexType(workload.getType()));

		int func = random.nextInt(10);

		if (func == 0) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 1) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 2) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 3) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 4) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 5) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 6) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 7) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 8) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(newFunc);
			parametros.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 9) {
			int newFunc = random.nextInt(nodes.size());
			parametros.add(getIndex(nodes, workload.getFunction1()));
			parametros.add(getIndex(nodes, workload.getFunction2()));
			parametros.add(getIndex(nodes, workload.getFunction3()));
			parametros.add(getIndex(nodes, workload.getFunction4()));
			parametros.add(getIndex(nodes, workload.getFunction5()));
			parametros.add(getIndex(nodes, workload.getFunction6()));
			parametros.add(getIndex(nodes, workload.getFunction7()));
			parametros.add(getIndex(nodes, workload.getFunction8()));
			parametros.add(getIndex(nodes, workload.getFunction9()));
			parametros.add(newFunc);

		}

		int newUsers = (workload.getNumThreads() + randInt(
				workload.getNumThreads(), maxUsers)) / 2;
		parametros.add(newUsers);
		parametros.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = createWorkLoad(nodes, parametros, "TABU");

		return newWorkload;
	}

	public static WorkLoad createRandomWorkLoad() {
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadControllerWithGui();

		WorkLoad workload = new WorkLoad();

		Random random = new Random();

		Integer random1 = random.nextInt(WorkLoad.getTypes().length);
		Integer random2 = 0;

		random2 = random.nextInt((int) nodes.size() + 1);

		Integer random3 = 0;

		random3 = random.nextInt((int) nodes.size() + 1);

		Integer random4 = 0;

		random4 = random.nextInt((int) nodes.size() + 1);

		Integer random5 = 0;

		random5 = random.nextInt((int) nodes.size() + 1);

		Integer random6 = 0;

		random6 = random.nextInt((int) nodes.size() + 1);

		Integer random7 = 0;

		random7 = random.nextInt((int) nodes.size() + 1);

		Integer random8 = 0;

		random8 = random.nextInt((int) nodes.size() + 1);

		Integer random9 = 0;

		random9 = random.nextInt((int) nodes.size() + 1);

		Integer random10 = 0;

		random10 = random.nextInt((int) nodes.size() + 1);

		Integer random11 = 0;

		random11 = random.nextInt((int) nodes.size() + 1);

		if (random1 == 0) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[0]);

		}
		if (random1 == 1) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[1]);

		}

		workload.setFunction1(getNameNode(nodes, random2));
		workload.setFunction2(getNameNode(nodes, random3));
		workload.setFunction3(getNameNode(nodes, random4));
		workload.setFunction4(getNameNode(nodes, random5));
		workload.setFunction5(getNameNode(nodes, random6));
		workload.setFunction6(getNameNode(nodes, random7));
		workload.setFunction7(getNameNode(nodes, random8));
		workload.setFunction8(getNameNode(nodes, random9));
		workload.setFunction9(getNameNode(nodes, random10));
		workload.setFunction10(getNameNode(nodes, random11));

		return workload;

	}

	public static WorkLoad createWorkLoadTabuWithGui(int generation,
			int maxUsers) {

		WorkLoad workload = createRandomWorkLoad();
		int numberUsers = randInt(0, maxUsers - 1);
		workload.setNumThreads(numberUsers);
		workload.setName("TABU:" + workload.getType() + "-"
				+ workload.getNumThreads() + "-" + workload.getFunction1()
				+ "-" + workload.getFunction2() + "-" + workload.getFunction3()
				+ "-" + workload.getFunction4() + "-" + workload.getFunction5()
				+ "-" + workload.getFunction6() + "-" + workload.getFunction7()
				+ "-" + workload.getFunction8() + "-" + workload.getFunction9()
				+ "-" + workload.getFunction10());

		workload.setSearchMethod("TABU");
		workload.setGeneration(generation);
		workload.setActive(true);
		workload.calcUsers();
		return workload;
	}

	public static WorkLoad createWorkLoadTemperatureWithGui(int generation,
			int maxUsers) {

		WorkLoad workload = createRandomWorkLoad();
		workload.setNumThreads(maxUsers);
		workload.setName("SA:" + workload.getType() + "-"
				+ workload.getNumThreads() + "-" + workload.getFunction1()
				+ "-" + workload.getFunction2() + "-" + workload.getFunction3()
				+ "-" + workload.getFunction4() + "-" + workload.getFunction5()
				+ "-" + workload.getFunction6() + "-" + workload.getFunction7()
				+ "-" + workload.getFunction8() + "-" + workload.getFunction9()
				+ "-" + workload.getFunction10());

		workload.setSearchMethod("SA");
		workload.setGeneration(generation);
		workload.setActive(true);
		workload.calcUsers();
		return workload;
	}

	public static WorkLoad getWorkLoad(ArrayList object) {

		if (object != null) {

			String type = object.get(1).toString();
			WorkLoad workload = FactoryWorkLoad.createWorkLoad(type);
			workload.setNumThreads(Integer.valueOf(object.get(2).toString()));
			workload.setName((object.get(0).toString()));
			workload.setType((type));
			workload.setWorstResponseTime(Long
					.valueOf(object.get(3).toString()));
			workload.setError(Boolean.valueOf(object.get(4).toString()));
			workload.setFit(Double.valueOf(object.get(5).toString()));
			workload.setFunction1(object.get(6).toString());
			workload.setFunction2(object.get(7).toString());
			workload.setFunction3(object.get(8).toString());
			workload.setFunction4(object.get(9).toString());
			workload.setFunction5(object.get(10).toString());
			workload.setFunction6(object.get(11).toString());
			workload.setFunction7(object.get(12).toString());
			workload.setFunction8(object.get(13).toString());
			workload.setFunction9(object.get(14).toString());
			workload.setFunction10(object.get(15).toString());
			workload.setGeneration(Integer.valueOf(object.get(16).toString()));
			workload.setActive(Boolean.valueOf(object.get(17).toString()));
			workload.setPercentile90(Integer.valueOf(object.get(18).toString()));
			workload.setPercentile80(Integer.valueOf(object.get(19).toString()));
			workload.setPercentile70(Integer.valueOf(object.get(20).toString()));
			workload.setTotalErrors(Integer.valueOf(object.get(21).toString()));
			workload.setSearchMethod(object.get(22).toString());
			workload.setUsers1(Integer.valueOf(object.get(23).toString()));
			workload.setUsers2(Integer.valueOf(object.get(24).toString()));
			workload.setUsers3(Integer.valueOf(object.get(25).toString()));
			workload.setUsers4(Integer.valueOf(object.get(26).toString()));
			workload.setUsers5(Integer.valueOf(object.get(27).toString()));
			workload.setUsers6(Integer.valueOf(object.get(28).toString()));
			workload.setUsers7(Integer.valueOf(object.get(29).toString()));
			workload.setUsers8(Integer.valueOf(object.get(30).toString()));
			workload.setUsers9(Integer.valueOf(object.get(31).toString()));
			workload.setUsers10(Integer.valueOf(object.get(32).toString()));

			return workload;
		}
		return null;
	}

	public static String getName(List<JMeterTreeNode> nodes, IntegerGene gene) {
		if (gene.intValue() < nodes.size()) {

			String name = nodes.get(gene.intValue()).getTestElement().getName();
			if (name == null) {
				name = nodes.get(gene.intValue()).getTestElement().NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static String getNameNode(List<JMeterTreeNode> nodes,
			IntegerGene gene) {
		if (gene.intValue() < nodes.size()) {

			String name = nodes.get(gene.intValue()).getTestElement().getName();
			if (name == null) {
				name = nodes.get(gene.intValue()).getTestElement().NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static String getNameNode(List<JMeterTreeNode> nodes, int i) {
		if (i < nodes.size()) {

			String name = nodes.get(i).getTestElement().getName();
			if (name == null) {
				name = nodes.get(i).getTestElement().NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static List<WorkLoad> createWorkLoadNodes(int maxThreads,
			int generation) {

		List<WorkLoad> returnList = new ArrayList<WorkLoad>();
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadControllerWithGui();
		String[] types = WorkLoad.getTypes();
		if ((nodes.size() > 0) && (types.length > 0) && (maxThreads > 0)) {

			int rest = maxThreads / 20;

			if (rest < 20) {
				rest = 20;
			}

			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < types.length; j++) {
					for (int z = 0; z < maxThreads; z = z + rest) {

						WorkLoad workload = null;
						if (j == 0) {
							workload = FactoryWorkLoad.createWorkLoad(WorkLoad
									.getTypes()[0]);

						}
						if (j == 1) {
							workload = FactoryWorkLoad.createWorkLoad(WorkLoad
									.getTypes()[1]);

						}
						workload.setFunction1(getNameNode(nodes, i));
						workload.setFunction2(getNameNode(nodes, i));
						workload.setFunction3(getNameNode(nodes, i));
						workload.setFunction4(getNameNode(nodes, i));
						workload.setFunction5(getNameNode(nodes, i));
						workload.setFunction6(getNameNode(nodes, i));
						workload.setFunction7(getNameNode(nodes, i));
						workload.setFunction8(getNameNode(nodes, i));
						workload.setFunction9(getNameNode(nodes, i));
						workload.setFunction10(getNameNode(nodes, i));

						if (z > 0)
							workload.setNumThreads(z);
						else
							workload.setNumThreads(1);
						workload.setName(workload.getType() + "-"
								+ workload.getNumThreads() + "-"
								+ workload.getFunction1() + "-"
								+ workload.getFunction2() + "-"
								+ workload.getFunction3() + "-"
								+ workload.getFunction4() + "-"
								+ workload.getFunction5() + "-"
								+ workload.getFunction6() + "-"
								+ workload.getFunction7() + "-"
								+ workload.getFunction8() + "-"
								+ workload.getFunction9() + "-"
								+ workload.getFunction10());

						workload.setGeneration(generation);
						workload.setActive(true);
						workload.setSearchMethod("GENETICALGORITHM");
						returnList.add(workload);
						workload.calcUsers();

					}

				}
			}
		}

		return returnList;
	}

	public static WorkLoad createWorkLoadWithGui(Gene[] genes, int generation) {
		WorkLoad workload = null;

		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadControllerWithGui();

		IntegerGene gene = (IntegerGene) genes[0];
		IntegerGene gene1 = (IntegerGene) genes[1];
		IntegerGene gene2 = (IntegerGene) genes[2];
		IntegerGene gene3 = (IntegerGene) genes[3];
		IntegerGene gene4 = (IntegerGene) genes[4];
		IntegerGene gene5 = (IntegerGene) genes[5];
		IntegerGene gene6 = (IntegerGene) genes[6];
		IntegerGene gene7 = (IntegerGene) genes[7];
		IntegerGene gene8 = (IntegerGene) genes[8];
		IntegerGene gene9 = (IntegerGene) genes[9];
		IntegerGene gene10 = (IntegerGene) genes[10];
		IntegerGene gene11 = (IntegerGene) genes[11];
		if (gene.intValue() == 0) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[0]);

		}
		if (gene.intValue() == 1) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[1]);

		}

		workload.setFunction1(getName(nodes, gene2));
		workload.setFunction2(getName(nodes, gene3));
		workload.setFunction3(getName(nodes, gene4));
		workload.setFunction4(getName(nodes, gene5));
		workload.setFunction5(getName(nodes, gene6));
		workload.setFunction6(getName(nodes, gene7));
		workload.setFunction7(getName(nodes, gene8));
		workload.setFunction8(getName(nodes, gene9));
		workload.setFunction9(getName(nodes, gene10));
		workload.setFunction10(getName(nodes, gene11));
		workload.setNumThreads(gene1.intValue());
		workload.setName(workload.getType() + "-" + workload.getNumThreads()
				+ "-" + workload.getFunction1() + "-" + workload.getFunction2()
				+ "-" + workload.getFunction3() + "-" + workload.getFunction4()
				+ "-" + workload.getFunction5() + "-" + workload.getFunction6()
				+ "-" + workload.getFunction7() + "-" + workload.getFunction8()
				+ "-" + workload.getFunction9() + "-"
				+ workload.getFunction10());

		workload.setGeneration(generation);
		workload.setSearchMethod("GENETICALGORITHM");
		workload.setActive(true);
		workload.calcUsers();
		return workload;
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
		if (rs.getString(20) != null) {
			workload.setPercentile90(Long.valueOf(rs.getString(20)));
		}
		if (rs.getString(21) != null) {
			workload.setPercentile80(Long.valueOf(rs.getString(21)));
		}
		if (rs.getString(22) != null) {
			workload.setPercentile70(Long.valueOf(rs.getString(22)));
		}
		if (rs.getString(23) != null) {
			workload.setTotalErrors(Long.valueOf(rs.getString(23)));
		}
		if (rs.getString(24) != null) {
			workload.setSearchMethod(rs.getString(24));
		}
		if (rs.getString(25) != null) {
			workload.setUsers1(Integer.valueOf(rs.getString(25)));
		}
		if (rs.getString(26) != null) {
			workload.setUsers2(Integer.valueOf(rs.getString(26)));
		}
		if (rs.getString(27) != null) {
			workload.setUsers3(Integer.valueOf(rs.getString(27)));
		}
		if (rs.getString(28) != null) {
			workload.setUsers4(Integer.valueOf(rs.getString(28)));
		}
		if (rs.getString(29) != null) {
			workload.setUsers5(Integer.valueOf(rs.getString(29)));
		}
		if (rs.getString(30) != null) {
			workload.setUsers6(Integer.valueOf(rs.getString(30)));
		}
		if (rs.getString(31) != null) {
			workload.setUsers7(Integer.valueOf(rs.getString(31)));
		}
		if (rs.getString(32) != null) {
			workload.setUsers8(Integer.valueOf(rs.getString(32)));
		}
		if (rs.getString(33) != null) {
			workload.setUsers9(Integer.valueOf(rs.getString(33)));
		}
		if (rs.getString(34) != null) {
			workload.setUsers10(Integer.valueOf(rs.getString(33)));
		}
		return workload;
	}

	public static void modelFromDerbyGui(PowerTableModel model, String testPlan)
			throws ClassNotFoundException, SQLException {

		List<WorkLoad> list = MySQLDatabase.listAllWorkLoads(testPlan);
		model.clearData();
		for (int rowN = 0; rowN < list.size(); rowN++) {
			WorkLoad workload = list.get(rowN);
			ArrayList<String> rowObject = new ArrayList<String>();
			rowObject.add(workload.getName());
			rowObject.add(workload.getType());
			rowObject.add(String.valueOf(workload.getNumThreads()));
			rowObject.add(String.valueOf(workload.getWorstResponseTime()));
			rowObject.add(String.valueOf(workload.isError()));
			rowObject.add(String.valueOf(workload.getFit()));
			rowObject.add(workload.getFunction1());
			rowObject.add(workload.getFunction2());
			rowObject.add(workload.getFunction3());
			rowObject.add(workload.getFunction4());
			rowObject.add(workload.getFunction5());
			rowObject.add(workload.getFunction6());
			rowObject.add(workload.getFunction7());
			rowObject.add(workload.getFunction8());
			rowObject.add(workload.getFunction9());
			rowObject.add(workload.getFunction10());
			rowObject.add(String.valueOf(workload.getGeneration()));
			rowObject.add(String.valueOf(workload.isActive()));
			rowObject.add(String.valueOf(workload.getPercentile90()));
			rowObject.add(String.valueOf(workload.getPercentile80()));
			rowObject.add(String.valueOf(workload.getPercentile70()));
			rowObject.add(String.valueOf(workload.getTotalErrors()));
			rowObject.add(String.valueOf(workload.getSearchMethod()));
			rowObject.add(String.valueOf(workload.getUsers1()));
			rowObject.add(String.valueOf(workload.getUsers2()));
			rowObject.add(String.valueOf(workload.getUsers3()));
			rowObject.add(String.valueOf(workload.getUsers4()));
			rowObject.add(String.valueOf(workload.getUsers5()));
			rowObject.add(String.valueOf(workload.getUsers6()));
			rowObject.add(String.valueOf(workload.getUsers7()));
			rowObject.add(String.valueOf(workload.getUsers8()));
			rowObject.add(String.valueOf(workload.getUsers9()));
			rowObject.add(String.valueOf(workload.getUsers10()));
			model.addRow(rowObject.toArray());
		}
		model.fireTableDataChanged();
	}

	public static Object[] getObjectList(WorkLoad workLoad) {

		Object[] rowObject = new Object[33];
		rowObject[0] = workLoad.getName();
		rowObject[1] = workLoad.getType();
		rowObject[2] = String.valueOf(workLoad.getNumThreads());
		rowObject[3] = String.valueOf(workLoad.getWorstResponseTime());
		rowObject[4] = String.valueOf(workLoad.isError());
		rowObject[5] = String.valueOf(workLoad.getFit());
		rowObject[6] = String.valueOf(workLoad.getFunction1());
		rowObject[7] = String.valueOf(workLoad.getFunction2());
		rowObject[8] = String.valueOf(workLoad.getFunction3());
		rowObject[9] = String.valueOf(workLoad.getFunction4());
		rowObject[10] = String.valueOf(workLoad.getFunction5());
		rowObject[11] = String.valueOf(workLoad.getFunction6());
		rowObject[12] = String.valueOf(workLoad.getFunction7());
		rowObject[13] = String.valueOf(workLoad.getFunction8());
		rowObject[14] = String.valueOf(workLoad.getFunction9());
		rowObject[15] = String.valueOf(workLoad.getFunction10());
		rowObject[16] = String.valueOf(workLoad.getGeneration());
		rowObject[17] = String.valueOf(workLoad.isActive());
		rowObject[18] = String.valueOf(workLoad.getPercentile90());
		rowObject[19] = String.valueOf(workLoad.getPercentile80());
		rowObject[20] = String.valueOf(workLoad.getPercentile70());
		rowObject[21] = String.valueOf(workLoad.getTotalErrors());
		rowObject[22] = String.valueOf(workLoad.getSearchMethod());
		rowObject[23] = String.valueOf(workLoad.getUsers1());
		rowObject[24] = String.valueOf(workLoad.getUsers2());
		rowObject[25] = String.valueOf(workLoad.getUsers3());
		rowObject[26] = String.valueOf(workLoad.getUsers4());
		rowObject[27] = String.valueOf(workLoad.getUsers5());
		rowObject[28] = String.valueOf(workLoad.getUsers6());
		rowObject[29] = String.valueOf(workLoad.getUsers7());
		rowObject[30] = String.valueOf(workLoad.getUsers8());
		rowObject[31] = String.valueOf(workLoad.getUsers9());
		rowObject[32] = String.valueOf(workLoad.getUsers10());
		return rowObject;
	}

	public static WorkLoad getWorkLoadFromChromosome(Chromosome chromosome,
			List<TestElement> list, int generation) {
		Gene[] gene = chromosome.getGenes();
		String type = WorkLoad.getTypes()[((IntegerGene) gene[0]).intValue()];

		WorkLoad workload = FactoryWorkLoad.createWorkLoad(type);
		workload.setNumThreads(((IntegerGene) gene[1]).intValue());
		workload.setType(type);
		workload.setFit(0);

		int index = ((IntegerGene) gene[2]).intValue();
		if (index == -1) {
			workload.setFunction1("None");
		} else {
			workload.setFunction1(list.get(((IntegerGene) gene[2]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[3]).intValue();
		if (index == -1) {
			workload.setFunction2("None");
		} else {
			workload.setFunction2(list.get(((IntegerGene) gene[3]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[4]).intValue();
		if (index == -1) {
			workload.setFunction3("None");
		} else {
			workload.setFunction3(list.get(((IntegerGene) gene[4]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[5]).intValue();
		if (index == -1) {
			workload.setFunction4("None");
		} else {
			workload.setFunction4(list.get(((IntegerGene) gene[5]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[6]).intValue();
		if (index == -1) {
			workload.setFunction5("None");
		} else {
			workload.setFunction5(list.get(((IntegerGene) gene[6]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[7]).intValue();
		if (index == -1) {
			workload.setFunction6("None");
		} else {
			workload.setFunction6(list.get(((IntegerGene) gene[7]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[8]).intValue();
		if (index == -1) {
			workload.setFunction7("None");
		} else {
			workload.setFunction7(list.get(((IntegerGene) gene[8]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[9]).intValue();
		if (index == -1) {
			workload.setFunction8("None");
		} else {
			workload.setFunction8(list.get(((IntegerGene) gene[9]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[10]).intValue();
		if (index == -1) {
			workload.setFunction9("None");
		} else {
			workload.setFunction9(list.get(((IntegerGene) gene[10]).intValue())
					.getName());
		}
		index = ((IntegerGene) gene[11]).intValue();
		if (index == -1) {
			workload.setFunction10("None");
		} else {
			workload.setFunction10(list
					.get(((IntegerGene) gene[11]).intValue()).getName());
		}

		workload.setGeneration(generation);

		workload.setActive(true);

		workload.setSearchMethod("GENETICALGORITHM");

		workload.setName("G" + generation + ":" + workload.getType() + "-"
				+ workload.getNumThreads() + "-" + workload.getFunction1()
				+ "-" + workload.getFunction2() + "-" + workload.getFunction3()
				+ "-" + workload.getFunction4() + "-" + workload.getFunction5()
				+ "-" + workload.getFunction6() + "-" + workload.getFunction7()
				+ "-" + workload.getFunction8() + "-" + workload.getFunction9()
				+ "-" + workload.getFunction10() + workload.getUsers1() + "-"
				+ workload.getUsers2() + "-" + workload.getUsers3() + "-"
				+ workload.getUsers4() + "-" + workload.getUsers5() + "-"
				+ workload.getUsers6() + "-" + workload.getUsers7() + "-"
				+ workload.getUsers8() + "-" + workload.getUsers9() + "-"
				+ workload.getUsers10());

		return workload;
	}

	public static WorkLoad mutant(WorkLoad workLoad, int users, int maxUsers) {
		WorkLoad workloadMutation = new WorkLoad();
		int newUsers = (users + randInt(users, maxUsers)) / 2;
		workloadMutation.setNumThreads(newUsers);
		workloadMutation.setName("Mutation-" + newUsers + "-"
				+ workLoad.getName());
		workloadMutation.setType(workLoad.getType());
		workloadMutation.setActive(workLoad.isActive());
		workloadMutation.setEndRampUp(workLoad.getEndRampUp());
		workloadMutation.setError(workLoad.isError());
		workloadMutation.setFlightTime(workLoad.getFlightTime());
		workloadMutation.setFunction1(workLoad.getFunction1());
		workloadMutation.setFunction2(workLoad.getFunction2());
		workloadMutation.setFunction3(workLoad.getFunction3());
		workloadMutation.setFunction4(workLoad.getFunction4());
		workloadMutation.setFunction5(workLoad.getFunction5());
		workloadMutation.setFunction6(workLoad.getFunction6());
		workloadMutation.setFunction7(workLoad.getFunction7());
		workloadMutation.setFunction8(workLoad.getFunction8());
		workloadMutation.setFunction9(workLoad.getFunction9());
		workloadMutation.setFunction10(workLoad.getFunction10());
		workloadMutation.setGeneration(workLoad.getGeneration());
		workloadMutation.setPercentile70(0);
		workloadMutation.setPercentile80(0);
		workloadMutation.setPercentile90(0);

		workloadMutation.setSearchMethod("GENETICALGORITHM");

		workloadMutation.setTotalErrors(0);
		workloadMutation.calcUsers();
		return workloadMutation;

	}

	public static String getName(List<TestElement> nodes, int i) {
		if (i < nodes.size()) {

			String name = nodes.get(i).getName();
			if (name == null) {
				name = nodes.get(i).NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static int getIndex(List<TestElement> nodes, String name) {
		int index = 0;
		for (TestElement testElement : nodes) {
			if (testElement.getName().equals(name)) {
				index = nodes.indexOf(testElement);
			}

		}
		return index;
	}

	public static WorkLoad createWorkLoad(List<TestElement> nodes,
			List<Integer> parametros, String search) {
		WorkLoad workload = null;
		if (parametros.get(0) == 0) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[0]);

		}
		if (parametros.get(0) == 1) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[1]);

		}

		workload.setFunction1(getName(nodes, parametros.get(1)));
		workload.setFunction2(getName(nodes, parametros.get(2)));
		workload.setFunction3(getName(nodes, parametros.get(3)));
		workload.setFunction4(getName(nodes, parametros.get(4)));
		workload.setFunction5(getName(nodes, parametros.get(5)));
		workload.setFunction6(getName(nodes, parametros.get(6)));
		workload.setFunction7(getName(nodes, parametros.get(7)));
		workload.setFunction8(getName(nodes, parametros.get(8)));
		workload.setFunction9(getName(nodes, parametros.get(9)));
		workload.setFunction10(getName(nodes, parametros.get(10)));
		String prefix = "";
		if (search.equals("SA")) {
			prefix = "SA";
		}
		if (search.equals("TABU")) {
			prefix = "TABU";
		}
		workload.setNumThreads(parametros.get(11));
		workload.setName(prefix+":" + "G" + parametros.get(12) + ":"
				+ workload.getType() + "-" + workload.getNumThreads() + "-"
				+ workload.getFunction1() + "-" + workload.getFunction2() + "-"
				+ workload.getFunction3() + "-" + workload.getFunction4() + "-"
				+ workload.getFunction5() + "-" + workload.getFunction6() + "-"
				+ workload.getFunction7() + "-" + workload.getFunction8() + "-"
				+ workload.getFunction9() + "-" + workload.getFunction10());

		workload.setSearchMethod(search);
		workload.setGeneration(parametros.get(12));
		workload.setActive(true);
		workload.calcUsers();
		return workload;
	}

}
