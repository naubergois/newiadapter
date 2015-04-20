package br.unifor.iadapter.threadGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;

public class AddRowWorkloadAction implements ActionListener {

	private JTable grid;
	private PowerTableModel tableModel;
	private JButton deleteRowButton;
	private Object[] defaultValues;
	private WorkLoadThreadGroupGUI sender;

	public AddRowWorkloadAction(WorkLoadThreadGroupGUI aSender, JTable grid,
			PowerTableModel tableModel, JButton deleteRowButton,
			Object[] defaultValues) {

		this.grid = grid;
		this.tableModel = tableModel;
		this.deleteRowButton = deleteRowButton;
		this.defaultValues = defaultValues;
		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {
		if (grid.isEditing()) {
			TableCellEditor cellEditor = grid.getCellEditor(
					grid.getEditingRow(), grid.getEditingColumn());
			cellEditor.stopCellEditing();
		}

		List<WorkLoad> workloadList = WorkLoadThreadGroup.createWorkloads();
		GuiPackage gp = GuiPackage.getInstance();
		if (gp != null) {

			JMeterTreeNode root = (JMeterTreeNode) gp.getTreeModel().getRoot();
			List<JMeterTreeNode> lista = WorkLoadThreadGroup
					.findWorkLoadTransactions(root, null);
			System.out.println(lista);
		}

		for (WorkLoad workLoad : workloadList) {

			Object[] rowObject = new Object[6];
			rowObject[0] = workLoad.getName();
			rowObject[1] = workLoad.getType();
			rowObject[2] = String.valueOf(workLoad.getNumThreads());
			rowObject[3] = String.valueOf(workLoad.getWorstResponseTime());
			rowObject[4] = String.valueOf(workLoad.isError());
			rowObject[5] = String.valueOf(workLoad.getFit());
			tableModel.addRow(rowObject);

			WorkLoadThreadGroupGUI.createTabChart(sender.getTabbedPane(),
					workLoad);

		}

		tableModel.fireTableDataChanged();


		int rowToSelect = tableModel.getRowCount() - 1;
		if (rowToSelect < grid.getRowCount()) {
			grid.setRowSelectionInterval(rowToSelect, rowToSelect);
		}
		sender.updateUI();
	}
}
