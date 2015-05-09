package br.unifor.iadapter.threadGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.jgap.Gene;
import org.jgap.impl.IntegerGene;

import br.unifor.iadapter.threadGroup.workload.StressWorkload;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.FindService;

public class FactoryWorkLoad {

	public static WorkLoad createWorkLoad(String type) {
		WorkLoad workload = null;
		if (type.equals(WorkLoad.getTypes()[0])) {
			workload = new WorkLoad();

			workload.setType(WorkLoad.getTypes()[0]);

		}
		if (type.equals(WorkLoad.getTypes()[1])) {
			workload = new StressWorkload();

			workload.setType(WorkLoad.getTypes()[1]);

		}

		return workload;

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

	public static String getName(List<JMeterTreeNode> nodes, int i) {
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

	public static WorkLoad createWorkLoadTemperatureWithGui(int generation,
			int maxUsers) {
		WorkLoad workload = null;

		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadControllerWithGui();
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
			workload = createWorkLoad(WorkLoad.getTypes()[0]);

		}
		if (random1 == 1) {
			workload = createWorkLoad(WorkLoad.getTypes()[1]);

		}

		workload.setFunction1(getName(nodes, random2));
		workload.setFunction2(getName(nodes, random3));
		workload.setFunction3(getName(nodes, random4));
		workload.setFunction4(getName(nodes, random5));
		workload.setFunction5(getName(nodes, random6));
		workload.setFunction6(getName(nodes, random7));
		workload.setFunction7(getName(nodes, random8));
		workload.setFunction8(getName(nodes, random9));
		workload.setFunction9(getName(nodes, random10));
		workload.setFunction10(getName(nodes, random11));
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
		// workload.calcUsers();
		return workload;
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
			workload = createWorkLoad(WorkLoad.getTypes()[0]);

		}
		if (gene.intValue() == 1) {
			workload = createWorkLoad(WorkLoad.getTypes()[1]);

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
							workload = createWorkLoad(WorkLoad.getTypes()[0]);

						}
						if (j == 1) {
							workload = createWorkLoad(WorkLoad.getTypes()[1]);

						}
						workload.setFunction1(getName(nodes, i));
						workload.setFunction2(getName(nodes, i));
						workload.setFunction3(getName(nodes, i));
						workload.setFunction4(getName(nodes, i));
						workload.setFunction5(getName(nodes, i));
						workload.setFunction6(getName(nodes, i));
						workload.setFunction7(getName(nodes, i));
						workload.setFunction8(getName(nodes, i));
						workload.setFunction9(getName(nodes, i));
						workload.setFunction10(getName(nodes, i));

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
}
