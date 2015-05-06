package br.unifor.iadapter.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class DesactiveRowWorkloadAction implements ActionListener {

	private PowerTableModel model;
	WorkLoadThreadGroupGUI sender;

	public DesactiveRowWorkloadAction(WorkLoadThreadGroupGUI sender,
			PowerTableModel model) {

		this.sender = sender;
		this.model = model;

	}

	public void actionPerformed(ActionEvent e) {
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadThreadGroupWithGui();
		WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
				.getTestElement();

		try {
			MySQLDatabase.desactiveValue(gp.getName());
			JMeterPluginsUtils.modelFromDerbyGui(sender.getWtableModel(),
					gp.getName());
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}
}
