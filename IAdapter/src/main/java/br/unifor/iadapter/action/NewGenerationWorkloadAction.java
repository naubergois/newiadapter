package br.unifor.iadapter.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.FindService;

public class NewGenerationWorkloadAction implements ActionListener {
	

	

	public NewGenerationWorkloadAction() {

	

	}

	public void actionPerformed(ActionEvent e) {
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadThreadGroupWithGui();
		WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
				.getTestElement();

		WorkLoadThreadGroup.createNextGenerationElementsWithGui(gp);
	}
}
