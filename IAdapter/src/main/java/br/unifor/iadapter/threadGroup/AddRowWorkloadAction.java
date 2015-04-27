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
import org.jgap.InvalidConfigurationException;

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

		List<WorkLoad> workloadList = null;
		try {
			int users = 10;
			try {
				users = Integer.valueOf(sender.getThreadMax().getText());
			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}
			workloadList = GeneWorkLoad.createWorkLoadsFromChromossomeWithGui(
					users, 1);
			workloadList.addAll(FactoryWorkLoad.createWorkLoadNodes(users, 1));
		} catch (InvalidConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GuiPackage gp = GuiPackage.getInstance();
		if (gp != null) {

			List<JMeterTreeNode> lista = FindService
					.searchWorkLoadControllerWithGui();

		}

		for (WorkLoad workLoad : workloadList) {

			Object[] rowObject = JMeterPluginsUtils.getObjectList(workLoad);
			tableModel.addRow(rowObject);

		}

		tableModel.fireTableDataChanged();

		int rowToSelect = tableModel.getRowCount() - 1;
		if (rowToSelect < grid.getRowCount()) {
			grid.setRowSelectionInterval(rowToSelect, rowToSelect);
		}
		sender.updateUI();
	}
}
