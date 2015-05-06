package br.unifor.iadapter.jmeter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTable;

import org.apache.jmeter.gui.util.PowerTableModel;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;

public class ClearRowWorkloadAction implements ActionListener {

	private JTable grid;
	private PowerTableModel tableModel;
	private JButton deleteRowButton;
	private Object[] defaultValues;
	private WorkLoadThreadGroupGUI sender;

	public ClearRowWorkloadAction(WorkLoadThreadGroupGUI aSender, JTable grid,
			PowerTableModel tableModel, JButton deleteRowButton,
			Object[] defaultValues) {

		this.grid = grid;
		this.tableModel = tableModel;
		this.deleteRowButton = deleteRowButton;
		this.defaultValues = defaultValues;
		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {
		
		try {
			MySQLDatabase.deleteTestPlan(sender.getName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.tableModel.clearData();

		sender.updateUI();
	}
}
