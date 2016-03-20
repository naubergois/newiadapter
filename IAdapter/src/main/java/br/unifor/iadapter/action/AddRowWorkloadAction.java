/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package br.unifor.iadapter.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
		int population = Integer.valueOf(sender.getPopulationSize().getText());

		List<WorkLoad> workloadList = null;

		try {

			try {
				users = Integer.valueOf(sender.getThreadMax().getText());
			} catch (NumberFormatException e2) {
				e2.printStackTrace();
			}
			workloadList = GeneWorkLoad.createWorkLoadsFromChromossomeWithGui(
					users, 1, population);
			workloadList.addAll(WorkLoadUtil.createWorkLoadNodes(users, 1));

			List<WorkLoad> listSA = new ArrayList<WorkLoad>();
			List<WorkLoad> listTABU = new ArrayList<WorkLoad>();
			/*
			 * if (!(sender.getColaborative().isSelected())) { listSA =
			 * WorkLoadUtil.createWorkLoadTemperatureWithGuiSame( workloadList,
			 * 1, users); listTABU = WorkLoadUtil.createWorkLoadTABUWithGuiSame(
			 * workloadList, 1, users);
			 * 
			 * }
			 */

			workloadList.addAll(listSA);
			workloadList.addAll(listTABU);
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
