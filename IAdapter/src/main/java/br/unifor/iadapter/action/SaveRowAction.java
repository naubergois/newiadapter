package br.unifor.iadapter.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class SaveRowAction implements ActionListener {

	private static final Logger log = LoggingManager.getLoggerForClass();

	private PowerTableModel tableModel;

	private JComponent sender;

	public SaveRowAction(JComponent aSender, PowerTableModel tableModel,
			JButton deleteRowButton, Object[] defaultValues) {

		this.tableModel = tableModel;

		this.sender = aSender;

	}

	public void actionPerformed(ActionEvent e) {
		try {
			List<JMeterTreeNode> nodes = FindService
					.searchWorkLoadThreadGroupWithGui();
			WorkLoadThreadGroup wg = (WorkLoadThreadGroup) nodes.get(0)
					.getTestElement();
			JMeterPluginsUtils.tableModelRowsToDerby(tableModel, wg,
					String.valueOf(wg.getGeneration()));
		} catch (ClassNotFoundException e1) {
			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		sender.updateUI();
	}
}
