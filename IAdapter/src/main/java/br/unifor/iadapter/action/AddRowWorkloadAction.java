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

import com.sun.javafx.scene.traversal.Algorithm;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.algorithm.InitialPopulationAlgorithm;
import br.unifor.iadapter.genetic.GeneWorkLoad;
import br.unifor.iadapter.searchclass.SearchClass;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;
import br.unifor.iadapter.util.WorkLoadUtil;

public class AddRowWorkloadAction implements ActionListener {

	private static final Logger log = LoggingManager.getLoggerForClass();

	private JTable grid;
	private PowerTableModel tableModel;

	private WorkLoadThreadGroupGUI sender;

	public AddRowWorkloadAction(WorkLoadThreadGroupGUI aSender, JTable grid, PowerTableModel tableModel,
			JButton deleteRowButton, Object[] defaultValues) {

		this.grid = grid;
		this.tableModel = tableModel;

		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {

		if (grid.isEditing()) {
			TableCellEditor cellEditor = grid.getCellEditor(grid.getEditingRow(), grid.getEditingColumn());
			cellEditor.stopCellEditing();
		}

		int users = 10;
		int maxMemory = 10;
		int maxCpuShare = 10;
		int population = Integer.valueOf(sender.getPopulationSize().getText());

		List<WorkLoad> workloadList = null;

		try {
			users = Integer.valueOf(sender.getThreadMax().getText());
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		}

		/*
		 * try { maxMemory = Integer.valueOf(sender.getMaxMemory().getText()); }
		 * catch (NumberFormatException e2) { e2.printStackTrace(); }
		 * 
		 * try { maxCpuShare =
		 * Integer.valueOf(sender.getMaxCpuShare().getText()); } catch
		 * (NumberFormatException e2) { e2.printStackTrace(); }
		 */
		try {

			InitialPopulationAlgorithm initial = new InitialPopulationAlgorithm();

			workloadList = GeneWorkLoad.createWorkLoadsFromChromossomeWithGui(initial, users, 1, population, maxMemory,
					maxCpuShare);
			workloadList.addAll(WorkLoadUtil.createWorkLoadNodes(initial, users, 1, maxMemory, maxCpuShare));

		} catch (Exception e1) {
			e1.printStackTrace();
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
