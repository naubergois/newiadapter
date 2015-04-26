package br.unifor.iadapter.threadGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;

import org.apache.jmeter.gui.util.PowerTableModel;

public class DeleteAgentAction implements ActionListener {

	private JTable table;
	private PowerTableModel model;
	private WorkLoadThreadGroupGUI sender;

	public DeleteAgentAction(JTable table, PowerTableModel model,
			WorkLoadThreadGroupGUI sender) {
		this.table = table;
		this.model = model;
		this.sender = sender;
	}

	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();

		if (row >= 0) {

			Object[] workloadData = model.getRowData(row);

			try {
				DerbyDatabase.deleteAgent(
						new ArrayList(Arrays.asList(workloadData)), null);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			model.removeRow(row);
			model.fireTableDataChanged();
			sender.updateUI();

		}

	}

}
