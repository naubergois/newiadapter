package br.unifor.iadapter.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.jgap.InvalidConfigurationException;

import br.unifor.iadapter.genetic.GeneWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;
import br.unifor.iadapter.util.WorkLoadUtil;

public class AddRowWorkloadAction implements ActionListener {

	private static final Logger log = LoggingManager.getLoggerForClass();

	private JTable grid;
	private PowerTableModel tableModel;

	private WorkLoadThreadGroupGUI sender;

	public AddRowWorkloadAction(WorkLoadThreadGroupGUI aSender, JTable grid,
			PowerTableModel tableModel, JButton deleteRowButton,
			Object[] defaultValues) {

		this.grid = grid;
		this.tableModel = tableModel;

		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {

		if (grid.isEditing()) {
			TableCellEditor cellEditor = grid.getCellEditor(
					grid.getEditingRow(), grid.getEditingColumn());
			cellEditor.stopCellEditing();
		}

		int users = 10;

		List<WorkLoad> workloadList = null;

		try {

			try {
				users = Integer.valueOf(sender.getThreadMax().getText());
			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}
			workloadList = GeneWorkLoad.createWorkLoadsFromChromossomeWithGui(
					users, 1);
			workloadList.addAll(WorkLoadUtil.createWorkLoadNodes(users, 1));
			workloadList.add(WorkLoadUtil.createWorkLoadTemperatureWithGui(1,
					users));
			workloadList.add(WorkLoadUtil.createWorkLoadTemperatureWithGui(1,
					users));
			workloadList.add(WorkLoadUtil.createWorkLoadTabuWithGui(1, users));
			workloadList.add(WorkLoadUtil.createWorkLoadTabuWithGui(1, users));
		} catch (InvalidConfigurationException e1) {
			log.error(e1.getMessage());
		}

		for (WorkLoad workLoad : workloadList) {

			Object[] rowObject = WorkLoadUtil.getObjectList(workLoad);
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
