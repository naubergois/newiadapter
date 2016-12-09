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

package br.unifor.iadapter.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.searchclass.SearchClassWorkLoad;
import br.unifor.iadapter.threadGroup.FactoryWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class WorkLoadUtil {

	private static final Logger log = LoggingManager.getLoggerForClass();

	/**
	 * Get a random interval
	 * 
	 * @param min
	 *            minimal value
	 * @param max
	 *            maximal value
	 * @return return random int value in interval
	 */
	public static int randInt(int min, int max) {

		try {
			Random rand = new Random();

			int randomNum = rand.nextInt((max - min) + 1) + min;

			return randomNum;
		} catch (IllegalArgumentException e) {
			return 0;
		}
	}

	/**
	 * Get index from the workload type
	 * 
	 * @param type
	 * @return
	 */
	public static int getIndexByType(String type) {

		SearchClassWorkLoad searchClassWorkLoad = new SearchClassWorkLoad();
		List<String> types = searchClassWorkLoad.getClasses();

		return types.indexOf(type);
	}

	public static List<Integer> mutateParameter(List<Integer> parameter, List<String> nodes, WorkLoad workload) {
		Random random = new Random();
		int func = random.nextInt(10);

		if (func == 0) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 1) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 2) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 3) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 4) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 5) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 6) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 7) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 8) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 9) {
			int newFunc = random.nextInt(nodes.size());
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(newFunc);

		}
		return parameter;
	}
	
	
	public static List<Integer> mutateParameter(List<Integer> parameter, List<String> nodes, WorkLoad workload,int func,int newFunc) {
	
		if (func == 0) {
	
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 1) {
	
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 2) {
	
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 3) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 4) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 5) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 6) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 7) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 8) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(newFunc);
			parameter.add(getIndex(nodes, workload.getFunction10()));
		}
		if (func == 9) {
			
			parameter.add(getIndex(nodes, workload.getFunction1()));
			parameter.add(getIndex(nodes, workload.getFunction2()));
			parameter.add(getIndex(nodes, workload.getFunction3()));
			parameter.add(getIndex(nodes, workload.getFunction4()));
			parameter.add(getIndex(nodes, workload.getFunction5()));
			parameter.add(getIndex(nodes, workload.getFunction6()));
			parameter.add(getIndex(nodes, workload.getFunction7()));
			parameter.add(getIndex(nodes, workload.getFunction8()));
			parameter.add(getIndex(nodes, workload.getFunction9()));
			parameter.add(newFunc);

		}
		return parameter;
	}


	public static WorkLoad createRandomWorkLoad()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<JMeterTreeNode> nodes = FindService.searchWorkLoadControllerWithGui();

		WorkLoad workload = new WorkLoad();

		Integer random2 = 0;

		random2 = randInt(0, (int) nodes.size() - 1);

		Integer random3 = 0;

		random3 = randInt(0, (int) nodes.size() - 1);

		Integer random4 = 0;

		random4 = randInt(0, (int) nodes.size() - 1);

		Integer random5 = 0;

		random5 = randInt(0, (int) nodes.size() - 1);

		Integer random6 = 0;

		random6 = randInt(0, (int) nodes.size() - 1);

		Integer random7 = 0;

		random7 = randInt(0, (int) nodes.size() - 1);

		Integer random8 = 0;

		random8 = randInt(0, (int) nodes.size() - 1);

		Integer random9 = 0;

		random9 = randInt(0, (int) nodes.size() - 1);

		Integer random10 = 0;

		random10 = randInt(0, (int) nodes.size() - 1);

		Integer random11 = 0;

		random11 = randInt(0, (int) nodes.size() - 1);

		Integer random12 = 0;

		random12 = 0;

		Integer random13 = 0;

		random13 = 0;

		workload = FactoryWorkLoad.createWorkLoad();

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

		workload.setMemory(random12);
		workload.setCpuShare(random13);

		return workload;

	}

	

	

	public static WorkLoad getWorkLoad(ArrayList<?> object)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		if (object != null) {

			if (object.get(1) != null) {

				String type = object.get(1).toString();
				WorkLoad workload = FactoryWorkLoad.createWorkLoad(type);
				workload.setNumThreads(Integer.valueOf(object.get(2).toString()));
				workload.setName((object.get(0).toString()));
				workload.setType((type));
				workload.setWorstResponseTime(Long.valueOf(object.get(3).toString()));
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
				workload.setMemory(Integer.valueOf(object.get(33).toString()));
				workload.setMemory(Integer.valueOf(object.get(34).toString()));
				workload.setNumThreads(workload.getUsers1() + workload.getUsers2() + workload.getUsers3()
						+ workload.getUsers4() + workload.getUsers5() + workload.getUsers6() + workload.getUsers7()
						+ workload.getUsers8() + workload.getUsers9() + workload.getUsers10());

				return workload;
			}
		}
		return null;
	}

	public static String getNameNode(List<JMeterTreeNode> nodes, IntegerGene gene) {
		if (gene.intValue() < nodes.size()) {

			String name = nodes.get(gene.intValue()).getTestElement().getName();
			if (name == null) {
				nodes.get(gene.intValue()).getTestElement();
				name = TestElement.NAME;
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
				nodes.get(i).getTestElement();
				name = TestElement.NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static List<WorkLoad> createWorkLoadNodes(AbstractAlgorithm algorithm, int maxThreads, int generation,
			int maxMemory, int maxCpuShare)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<WorkLoad> returnList = new ArrayList<WorkLoad>();
		List<JMeterTreeNode> nodes = FindService.searchWorkLoadControllerWithGui();
		Object[] types = WorkLoad.getTypes();
		if ((nodes.size() > 0) && (types.length > 0) && (maxThreads > 0)) {

			int third = maxThreads / 3;

			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < types.length; j++) {
					for (int z = 0; (z < maxThreads); z = z + third) {

						WorkLoad workload = null;

						workload = FactoryWorkLoad.createWorkLoad();

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
						workload.setName(algorithm.getMethodName()+":" + workload.getType() + "-" + workload.getNumThreads() + "-"
								+ workload.getFunction1() + "-" + workload.getFunction2() + "-"
								+ workload.getFunction3() + "-" + workload.getFunction4() + "-"
								+ workload.getFunction5() + "-" + workload.getFunction6() + "-"
								+ workload.getFunction7() + "-" + workload.getFunction8() + "-"
								+ workload.getFunction9() + "-" + workload.getFunction10());

						workload.setGeneration(generation);
						workload.setActive(true);
						workload.setSearchMethod(algorithm.getClass().getCanonicalName());
						workload.calcUsers();
						workload.setMemory(maxMemory);
						workload.setCpuShare(maxCpuShare);
						returnList.add(workload);

					}

				}
			}
		}

		return returnList;
	}

	public static List<String> getScenarios(List<JMeterTreeNode> nodes) {
		List<String> scenarios = new ArrayList<>();
		for (JMeterTreeNode jMeterTreeNode : nodes) {
			scenarios.add(jMeterTreeNode.getName());
		}
		return scenarios;
	}

	public static String[] getSplitProperty(String property) {
		String[] algorithms = property.split(",");
		return algorithms;
	}

	public static WorkLoad createWorkLoadWithGui(AbstractAlgorithm algorithm, Gene[] genes, int generation)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		WorkLoad workload = null;

		List<JMeterTreeNode> nodesIni = FindService.searchWorkLoadControllerWithGui();
		List<String> nodes = getScenarios(nodesIni);

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
		IntegerGene gene12 = (IntegerGene) genes[12];
		IntegerGene gene13 = (IntegerGene) genes[13];
		IntegerGene gene14 = (IntegerGene) genes[14];
		IntegerGene gene15 = (IntegerGene) genes[15];
		IntegerGene gene16 = (IntegerGene) genes[16];
		IntegerGene gene17 = (IntegerGene) genes[17];
		IntegerGene gene18 = (IntegerGene) genes[18];
		IntegerGene gene19 = (IntegerGene) genes[19];
		IntegerGene gene20 = (IntegerGene) genes[20];
		IntegerGene gene21 = (IntegerGene) genes[21];
		IntegerGene gene22 = (IntegerGene) genes[22];
		IntegerGene gene23 = (IntegerGene) genes[23];

		workload = FactoryWorkLoad.createWorkLoad();
		
		

		workload.setFunction1(getNameFromString(nodes, gene2));
		workload.setUsers1(gene3.intValue());
		workload.setFunction2(getNameFromString(nodes, gene4));
		workload.setUsers2(gene5.intValue());
		workload.setFunction3(getNameFromString(nodes, gene6));
		workload.setUsers3(gene7.intValue());
		workload.setFunction4(getNameFromString(nodes, gene8));
		workload.setUsers4(gene9.intValue());
		workload.setFunction5(getNameFromString(nodes, gene10));
		workload.setUsers5(gene11.intValue());
		workload.setFunction6(getNameFromString(nodes, gene12));
		workload.setUsers6(gene13.intValue());
		workload.setFunction7(getNameFromString(nodes, gene14));
		workload.setUsers7(gene15.intValue());
		workload.setFunction8(getNameFromString(nodes, gene16));
		workload.setUsers8(gene17.intValue());
		workload.setFunction9(getNameFromString(nodes, gene18));
		workload.setUsers9(gene19.intValue());
		workload.setFunction10(getNameFromString(nodes, gene20));
		workload.setUsers10(gene21.intValue());
		int users = workload.getUsers1() + workload.getUsers2() + workload.getUsers3() + workload.getUsers4()
				+ workload.getUsers5() + workload.getUsers6() + workload.getUsers7() + workload.getUsers8()
				+ workload.getUsers9() + workload.getUsers10();
		workload.setNumThreads(users);
		workload.setName(algorithm.getMethodName()+":" + workload.getType() + "-" + workload.getNumThreads() + "-" + workload.getFunction1()
				+ "-" + workload.getFunction2() + "-" + workload.getFunction3() + "-" + workload.getFunction4() + "-"
				+ workload.getFunction5() + "-" + workload.getFunction6() + "-" + workload.getFunction7() + "-"
				+ workload.getFunction8() + "-" + workload.getFunction9() + "-" + workload.getFunction10());

		System.out.println(users + "-" + workload.getName() + "-" + workload.getUsers1() + "-" + workload.getUsers2()
				+ "-" + workload.getUsers3() + "-" + workload.getUsers4() + "-" + workload.getUsers5() + "-"
				+ workload.getUsers6() + "-" + workload.getUsers7() + "-" + workload.getUsers8() + "-"
				+ workload.getUsers9() + "-" + workload.getUsers10());
		workload.setGeneration(generation);
		workload.setSearchMethod(algorithm.getClass().getCanonicalName());
		workload.setActive(true);
		workload.setMemory(gene22.intValue());
		workload.setCpuShare(gene23.intValue());

		return workload;
	}

	public static WorkLoad resultSetToWorkLoad(ResultSet rs) throws SQLException {
		String type = rs.getString(2);
		
		WorkLoad workload=null;
		try {
			workload = FactoryWorkLoad.createWorkLoad(type);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			workload.setUsers10(Integer.valueOf(rs.getString(34)));
		}
		// if (rs.getString(35) != null) {
		// workload.setMemory(Integer.valueOf(rs.getString(35)));
		// }
		// if (rs.getString(36) != null) {
		// workload.setCpuShare(Integer.valueOf(rs.getString(36)));
		// }
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
			rowObject.add(String.valueOf(workload.getMemory()));
			rowObject.add(String.valueOf(workload.getCpuShare()));
			model.addRow(rowObject.toArray());
		}
		model.fireTableDataChanged();
	}

	public static Object[] getObjectList(WorkLoad workLoad) {

		Object[] rowObject = new Object[35];
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
		rowObject[33] = String.valueOf(workLoad.getUsers10());
		rowObject[34] = String.valueOf(workLoad.getUsers10());

		return rowObject;
	}

	public static List<String> getTestCasesFromElement(List<TestElement> list) {
		List<String> testCases = new ArrayList<String>();
		for (TestElement testElement : list) {
			testCases.add(testElement.getName());
		}
		return testCases;
	}

	public static WorkLoad getWorkLoadFromChromosome(AbstractAlgorithm algorithm, IChromosome chromosome,
			List<TestElement> list, int generation)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Gene[] gene = chromosome.getGenes();
		String type = (String) WorkLoad.getTypes()[((IntegerGene) gene[0]).intValue()];

		WorkLoad workload = FactoryWorkLoad.createWorkLoad(type);

		workload.setType(type);
		workload.setFit(0);

		int index = ((IntegerGene) gene[2]).intValue();
		if (index == -1) {
			workload.setFunction1("None");
		} else {
			workload.setFunction1(list.get(((IntegerGene) gene[2]).intValue()).getName());
		}
		workload.setUsers1(((IntegerGene) gene[3]).intValue());
		index = ((IntegerGene) gene[4]).intValue();
		if (index == -1) {
			workload.setFunction2("None");
		} else {
			workload.setFunction2(list.get(((IntegerGene) gene[4]).intValue()).getName());
		}
		workload.setUsers2(((IntegerGene) gene[5]).intValue());
		index = ((IntegerGene) gene[6]).intValue();
		if (index == -1) {
			workload.setFunction3("None");
		} else {
			workload.setFunction3(list.get(((IntegerGene) gene[6]).intValue()).getName());
		}
		workload.setUsers3(((IntegerGene) gene[7]).intValue());
		index = ((IntegerGene) gene[8]).intValue();
		if (index == -1) {
			workload.setFunction4("None");
		} else {
			workload.setFunction4(list.get(((IntegerGene) gene[8]).intValue()).getName());
		}
		workload.setUsers4(((IntegerGene) gene[9]).intValue());
		index = ((IntegerGene) gene[10]).intValue();
		if (index == -1) {
			workload.setFunction5("None");
		} else {
			workload.setFunction5(list.get(((IntegerGene) gene[10]).intValue()).getName());
		}
		workload.setUsers5(((IntegerGene) gene[11]).intValue());
		index = ((IntegerGene) gene[12]).intValue();
		if (index == -1) {
			workload.setFunction6("None");
		} else {
			workload.setFunction6(list.get(((IntegerGene) gene[12]).intValue()).getName());
		}
		workload.setUsers6(((IntegerGene) gene[13]).intValue());
		index = ((IntegerGene) gene[14]).intValue();
		if (index == -1) {
			workload.setFunction7("None");
		} else {
			workload.setFunction7(list.get(((IntegerGene) gene[14]).intValue()).getName());
		}
		workload.setUsers7(((IntegerGene) gene[15]).intValue());
		index = ((IntegerGene) gene[16]).intValue();
		if (index == -1) {
			workload.setFunction8("None");
		} else {
			workload.setFunction8(list.get(((IntegerGene) gene[16]).intValue()).getName());
		}
		workload.setUsers8(((IntegerGene) gene[17]).intValue());
		index = ((IntegerGene) gene[18]).intValue();
		if (index == -1) {
			workload.setFunction9("None");
		} else {
			workload.setFunction9(list.get(((IntegerGene) gene[18]).intValue()).getName());
		}
		workload.setUsers9(((IntegerGene) gene[19]).intValue());
		index = ((IntegerGene) gene[20]).intValue();
		if (index == -1) {
			workload.setFunction10("None");
		} else {
			workload.setFunction10(list.get(((IntegerGene) gene[20]).intValue()).getName());
		}
		workload.setUsers10(((IntegerGene) gene[21]).intValue());

		workload.setMemory(((IntegerGene) gene[22]).intValue());

		workload.setCpuShare(((IntegerGene) gene[23]).intValue());

		workload.setGeneration(generation);

		workload.setActive(true);

		workload.setSearchMethod(algorithm.getMethodName());

		if (workload.getName() == null) {
			workload.setName("");
		}
		workload.setNumThreads(workload.getUsers1() + workload.getUsers2() + workload.getUsers3() + workload.getUsers4()
				+ workload.getUsers5() + workload.getUsers6() + workload.getUsers7() + workload.getUsers8()
				+ workload.getUsers9() + workload.getUsers10());

		workload.setName(algorithm.getMethodName() + ":" + generation + ":" + workload.getType() + "-"
				+ workload.getNumThreads() + "-" + workload.getFunction1() + "-" + workload.getFunction2() + "-"
				+ workload.getFunction3() + "-" + workload.getFunction4() + "-" + workload.getFunction5() + "-"
				+ workload.getFunction6() + "-" + workload.getFunction7() + "-" + workload.getFunction8() + "-"
				+ workload.getFunction9() + "-" + workload.getFunction10());

		return workload;
	}

	public static boolean isNumeric(String str) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}

	public static int getGenerationFromName(String workloadName) {
		String terms[] = workloadName.split(":");
		if (terms.length > 0) {
			int max = 0;
			for (String string : terms) {

				if (string.length() > 0) {
					if (string.substring(0, 1).equals("G")) {
						String stringNumber = string.replace('G', '0');
						if (isNumeric(stringNumber)) {
							int number = Integer.valueOf(stringNumber);
							if (number > max) {
								max = number;
							}
						}

					}
				}
			}
			return max;
		} else {
			return 0;
		}
	}

	public static String getName(List<TestElement> nodes, int i) {
		if (i < nodes.size()) {

			String name = nodes.get(i).getName();
			if (name == null) {
				nodes.get(i);
				name = TestElement.NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static String getName(List<TestElement> nodes, Integer i) {
		return getName(nodes, i.intValue());
	}

	public static String getNameFromString(List<String> list, IntegerGene i) {
		return list.get(i.intValue());
	}

	public static String getNameFromString(List<String> list, Integer i) {
		return list.get(i.intValue());
	}

	public static String getName(List<TestElement> nodes, IntegerGene gene) {
		return getName(nodes, gene.intValue());
	}

	public static int getIndexFromElement(List<TestElement> nodes, String name) {
		return getIndex(getTestCasesFromElement(nodes), name);
	}

	public static int getIndex(List<String> nodes, String name) {

		return nodes.indexOf(name);
	}
	
	static class MyComparator implements Comparator {

		Map map;

		public MyComparator(Map map) {
			this.map = map;
		}

		public int compare(Object o1, Object o2) {

			return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));

		}
	}
	
	public static String getWorkLoadScenarios(WorkLoad workload,List<String> scenarios){
		
		HashMap<String,Integer> scenarioNumber=new HashMap<>();
		
		
		
		for (int i=0;i<10;i++){
			String scenario="";
			int users=0;
			if (i==0){
				scenario=workload.getFunction1();
				users=workload.getUsers1();
			}
			if (i==1){
				scenario=workload.getFunction2();
				users=workload.getUsers2();
			}
			if (i==2){
				scenario=workload.getFunction3();
				users=workload.getUsers3();
			}
			if (i==3){
				scenario=workload.getFunction4();
				users=workload.getUsers4();
			}
			if (i==4){
				scenario=workload.getFunction5();
				users=workload.getUsers5();
			}
			if (i==5){
				scenario=workload.getFunction6();
				users=workload.getUsers6();
			}
			if (i==6){
				scenario=workload.getFunction7();
				users=workload.getUsers7();
			}
			if (i==7){
				scenario=workload.getFunction8();
				users=workload.getUsers8();
			}
			if (i==8){
				scenario=workload.getFunction9();
				users=workload.getUsers9();
			}
			if (i==9){
				scenario=workload.getFunction10();
				users=workload.getUsers10();
			}
			if (scenarioNumber.containsKey(scenario)){
				int currentUsers=scenarioNumber.get(scenario);
				currentUsers+=users;
				scenarioNumber.put(scenario, currentUsers);
			}else{
				scenarioNumber.put(scenario, users);
			}
			
		}
		
		MyComparator comp = new MyComparator(scenarioNumber);
		Map<String, Integer> newMap = new TreeMap(comp);
		newMap.putAll(scenarioNumber);
		StringBuffer buffer=new StringBuffer();
		for (String string : newMap.keySet()) {
			buffer.append(string);
			
			
		}
		return buffer.toString();
		
	}

	public static int deltaUsers() {
		Random randomSignal = new Random();

		int signal = randomSignal.nextInt(2);

		int randomUser = randomSignal.nextInt(2);

		int deltaUsers = 0;

		if (signal == 0) {
			deltaUsers = -1 * randomUser;
		} else {
			deltaUsers = randomUser;
		}
		return deltaUsers;

	}

	public static WorkLoad createWorkLoad(AbstractAlgorithm algorithm, List<String> nodes, List<Integer> parametros,
			int generation, WorkLoad source)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		WorkLoad workload = null;

		workload = FactoryWorkLoad.createWorkLoad();

		workload.setFunction1(getNameFromString(nodes, parametros.get(1)));
		workload.setFunction2(getNameFromString(nodes, parametros.get(2)));
		workload.setFunction3(getNameFromString(nodes, parametros.get(3)));
		workload.setFunction4(getNameFromString(nodes, parametros.get(4)));
		workload.setFunction5(getNameFromString(nodes, parametros.get(5)));
		workload.setFunction6(getNameFromString(nodes, parametros.get(6)));
		workload.setFunction7(getNameFromString(nodes, parametros.get(7)));
		workload.setFunction8(getNameFromString(nodes, parametros.get(8)));
		workload.setFunction9(getNameFromString(nodes, parametros.get(9)));
		workload.setFunction10(getNameFromString(nodes, parametros.get(10)));

		int threads = Integer.valueOf(parametros.get(11));
		workload.setNumThreads(threads);
		workload.calcUsers();

		int users1 = 0;
		int users2 = 0;
		int users3 = 0;
		int users4 = 0;
		int users5 = 0;
		int users6 = 0;
		int users7 = 0;
		int users8 = 0;
		int users9 = 0;
		int users10 = 0;

		users1 = source.getUsers1() + deltaUsers();
		users2 = source.getUsers2() + deltaUsers();
		users3 = source.getUsers3() + deltaUsers();
		users4 = source.getUsers4() + deltaUsers();
		users5 = source.getUsers5() + deltaUsers();
		users6 = source.getUsers6() + deltaUsers();
		users7 = source.getUsers7() + deltaUsers();
		users8 = source.getUsers8() + deltaUsers();
		users9 = source.getUsers9() + deltaUsers();
		users10 = source.getUsers10() + deltaUsers();

		workload.setUsers1(users1);
		workload.setUsers2(users2);
		workload.setUsers3(users3);
		workload.setUsers4(users4);
		workload.setUsers5(users5);
		workload.setUsers6(users6);
		workload.setUsers7(users7);
		workload.setUsers8(users8);
		workload.setUsers9(users9);
		workload.setUsers10(users10);

		workload.setSearchMethod(algorithm.getMethodName());
		workload.setGeneration(parametros.get(12));
		workload.setActive(true);

		workload.setNumThreads(workload.getUsers1() + workload.getUsers2() + workload.getUsers3() + workload.getUsers4()
				+ workload.getUsers5() + workload.getUsers6() + workload.getUsers7() + workload.getUsers8()
				+ workload.getUsers9() + workload.getUsers10());

		if (workload.getNumThreads() <= 0) {
			workload.setNumThreads(1);
			workload.setUsers1(1);
			workload.setUsers2(0);
			workload.setUsers3(0);
			workload.setUsers4(0);
			workload.setUsers5(0);
			workload.setUsers6(0);
			workload.setUsers7(0);
			workload.setUsers8(0);
			workload.setUsers9(0);
			workload.setUsers10(0);
		}

		workload.setName(algorithm.getMethodName() + ":" + generation + ":" + parametros.get(12) + ":"
				+ workload.getType() + "-" + workload.getNumThreads() + "-" + workload.getFunction1() + "-"
				+ workload.getFunction2() + "-" + workload.getFunction3() + "-" + workload.getFunction4() + "-"
				+ workload.getFunction5() + "-" + workload.getFunction6() + "-" + workload.getFunction7() + "-"
				+ workload.getFunction8() + "-" + workload.getFunction9() + "-" + workload.getFunction10());

		return workload;
	}
	
	
	public static WorkLoad createWorkLoad(AbstractAlgorithm algorithm, List<String> nodes, List<Integer> parametros,
			int generation, WorkLoad source,int delta)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		WorkLoad workload = null;

		workload = FactoryWorkLoad.createWorkLoad();

		workload.setFunction1(getNameFromString(nodes, parametros.get(1)));
		workload.setFunction2(getNameFromString(nodes, parametros.get(2)));
		workload.setFunction3(getNameFromString(nodes, parametros.get(3)));
		workload.setFunction4(getNameFromString(nodes, parametros.get(4)));
		workload.setFunction5(getNameFromString(nodes, parametros.get(5)));
		workload.setFunction6(getNameFromString(nodes, parametros.get(6)));
		workload.setFunction7(getNameFromString(nodes, parametros.get(7)));
		workload.setFunction8(getNameFromString(nodes, parametros.get(8)));
		workload.setFunction9(getNameFromString(nodes, parametros.get(9)));
		workload.setFunction10(getNameFromString(nodes, parametros.get(10)));

		int threads = Integer.valueOf(parametros.get(11));
		workload.setNumThreads(threads);
		workload.calcUsers();

		int users1 = 0;
		int users2 = 0;
		int users3 = 0;
		int users4 = 0;
		int users5 = 0;
		int users6 = 0;
		int users7 = 0;
		int users8 = 0;
		int users9 = 0;
		int users10 = 0;

		users1 = source.getUsers1() + delta;
		users2 = source.getUsers2() + delta;
		users3 = source.getUsers3() + delta;
		users4 = source.getUsers4() + delta;
		users5 = source.getUsers5() + delta;
		users6 = source.getUsers6() + delta;
		users7 = source.getUsers7() + delta;
		users8 = source.getUsers8() + delta;
		users9 = source.getUsers9() + delta;
		users10 = source.getUsers10() + delta;
		
		
		System.out.println(" Delta "+delta);
		workload.calcUsers();
		if (users1<0){
			workload.setUsers1(0);
		}
		if (users2<0){
			workload.setUsers2(0);
		}
		if (users3<0){
			workload.setUsers3(0);
		}
		if (users4<0){
			workload.setUsers4(0);
		}
		if (users5<0){
			workload.setUsers5(0);
		}
		if (users6<0){
			workload.setUsers5(0);
		}
		if (users7<0){
			workload.setUsers7(0);
		}
		if (users8<0){
			workload.setUsers8(0);
		}
		if (users9<0){
			workload.setUsers9(0);
		}
		if (users10<0){
			workload.setUsers10(0);
		}


		workload.calcUsers();
		System.out.println("Nu threads "+workload.getNumThreads());

		
		

		workload.setUsers1(users1);
		workload.setUsers2(users2);
		workload.setUsers3(users3);
		workload.setUsers4(users4);
		workload.setUsers5(users5);
		workload.setUsers6(users6);
		workload.setUsers7(users7);
		workload.setUsers8(users8);
		workload.setUsers9(users9);
		workload.setUsers10(users10);

		workload.setSearchMethod(algorithm.getMethodName());
		workload.setGeneration(parametros.get(12));
		workload.setActive(true);

		workload.setNumThreads(workload.getUsers1() + workload.getUsers2() + workload.getUsers3() + workload.getUsers4()
				+ workload.getUsers5() + workload.getUsers6() + workload.getUsers7() + workload.getUsers8()
				+ workload.getUsers9() + workload.getUsers10());

		if (workload.getNumThreads() <= 0) {
			workload.setNumThreads(1);
			workload.setUsers1(1);
			workload.setUsers2(0);
			workload.setUsers3(0);
			workload.setUsers4(0);
			workload.setUsers5(0);
			workload.setUsers6(0);
			workload.setUsers7(0);
			workload.setUsers8(0);
			workload.setUsers9(0);
			workload.setUsers10(0);
		}

		workload.setName(algorithm.getMethodName() + ":" + generation + ":" + parametros.get(12) + ":"
				+ workload.getType() + "-" + workload.getNumThreads() + "-" + workload.getFunction1() + "-"
				+ workload.getFunction2() + "-" + workload.getFunction3() + "-" + workload.getFunction4() + "-"
				+ workload.getFunction5() + "-" + workload.getFunction6() + "-" + workload.getFunction7() + "-"
				+ workload.getFunction8() + "-" + workload.getFunction9() + "-" + workload.getFunction10());

		return workload;
	}
	
	
	public static WorkLoad createWorkLoadSamePath(AbstractAlgorithm algorithm, List<String> nodes, List<Integer> parametros,
			int generation, WorkLoad source)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		WorkLoad workload = null;

		workload = FactoryWorkLoad.createWorkLoad();

		workload.setFunction1(source.getFunction1());
		workload.setFunction2(source.getFunction2());
		workload.setFunction3(source.getFunction3());
		workload.setFunction4(source.getFunction4());
		workload.setFunction5(source.getFunction5());
		workload.setFunction6(source.getFunction6());
		workload.setFunction7(source.getFunction7());
		workload.setFunction8(source.getFunction8());
		workload.setFunction9(source.getFunction9());
		workload.setFunction10(source.getFunction10());

		int threads = Integer.valueOf(source.getNumThreads());
		workload.setNumThreads(threads);
		workload.calcUsers();

		int users1 = 0;
		int users2 = 0;
		int users3 = 0;
		int users4 = 0;
		int users5 = 0;
		int users6 = 0;
		int users7 = 0;
		int users8 = 0;
		int users9 = 0;
		int users10 = 0;

		users1 = source.getUsers1() + deltaUsers();
		users2 = source.getUsers2() + deltaUsers();
		users3 = source.getUsers3() + deltaUsers();
		users4 = source.getUsers4() + deltaUsers();
		users5 = source.getUsers5() + deltaUsers();
		users6 = source.getUsers6() + deltaUsers();
		users7 = source.getUsers7() + deltaUsers();
		users8 = source.getUsers8() + deltaUsers();
		users9 = source.getUsers9() + deltaUsers();
		users10 = source.getUsers10() + deltaUsers();

		workload.setUsers1(users1);
		workload.setUsers2(users2);
		workload.setUsers3(users3);
		workload.setUsers4(users4);
		workload.setUsers5(users5);
		workload.setUsers6(users6);
		workload.setUsers7(users7);
		workload.setUsers8(users8);
		workload.setUsers9(users9);
		workload.setUsers10(users10);

		workload.setSearchMethod(algorithm.getMethodName());
		workload.setGeneration(generation);
		workload.setActive(true);

		workload.setNumThreads(workload.getUsers1() + workload.getUsers2() + workload.getUsers3() + workload.getUsers4()
				+ workload.getUsers5() + workload.getUsers6() + workload.getUsers7() + workload.getUsers8()
				+ workload.getUsers9() + workload.getUsers10());

		if (workload.getNumThreads() <= 0) {
			workload.setNumThreads(1);
			workload.setUsers1(1);
			workload.setUsers2(0);
			workload.setUsers3(0);
			workload.setUsers4(0);
			workload.setUsers5(0);
			workload.setUsers6(0);
			workload.setUsers7(0);
			workload.setUsers8(0);
			workload.setUsers9(0);
			workload.setUsers10(0);
		}

		workload.setName(algorithm.getMethodName() + ":" + generation + ":" + generation + ":"
				+ workload.getType() + "-" + workload.getNumThreads() + "-" + workload.getFunction1() + "-"
				+ workload.getFunction2() + "-" + workload.getFunction3() + "-" + workload.getFunction4() + "-"
				+ workload.getFunction5() + "-" + workload.getFunction6() + "-" + workload.getFunction7() + "-"
				+ workload.getFunction8() + "-" + workload.getFunction9() + "-" + workload.getFunction10());

		return workload;
	}

}
