package br.unifor.iadapter.threadGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;

public class RefreshRowWorkloadAction implements ActionListener {

	private WorkLoadThreadGroupGUI sender;

	public RefreshRowWorkloadAction(WorkLoadThreadGroupGUI aSender) {

		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {

		try {

			List<JMeterTreeNode> nodes = FindService
					.searchWorkLoadThreadGroupWithGui();
			WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
					.getTestElement();
			JMeterPluginsUtils.modelFromDerbyGui(sender.wtableModel,
					gp.getName());
			JMeterPluginsUtils
					.derbyAgentToTableModelRows(sender.wtableModelAgents);

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
