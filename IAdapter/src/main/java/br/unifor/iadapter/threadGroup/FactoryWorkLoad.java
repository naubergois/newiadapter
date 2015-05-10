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

	
	
}
