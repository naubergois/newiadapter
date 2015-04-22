package br.unifor.iadapter.threadGroup;

import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.jgap.Gene;
import org.jgap.impl.IntegerGene;

public class FactoryWorkLoad {

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

	public static WorkLoad createWorkLoadWithGui(Gene[] genes) {
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
			workload = new WorkLoad();

			workload.setType(WorkLoad.getTypes()[0]);

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

		return workload;
	}

}
