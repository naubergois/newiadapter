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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.apache.jmeter.gui.util.PowerTableModel;

import br.unifor.iadapter.database.MySQLDatabase;

public class DeleteRowAction implements ActionListener {

	private JTable grid;
	private PowerTableModel tableModel;
	private JButton deleteRowButton;
	private final JComponent sender;

	public DeleteRowAction(JComponent aSender, JTable grid,
			PowerTableModel tableModel, JButton deleteRowButton) {
		this.grid = grid;
		this.tableModel = tableModel;
		this.deleteRowButton = deleteRowButton;
		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {
		if (grid.isEditing()) {
			TableCellEditor cellEditor = grid.getCellEditor(
					grid.getEditingRow(), grid.getEditingColumn());
			cellEditor.cancelCellEditing();
		}

		int rowSelected = grid.getSelectedRow();
		if (rowSelected >= 0) {

			Object[] workloadData = tableModel.getRowData(rowSelected);

			try {
				MySQLDatabase.deleteWorkLoad(String.valueOf(workloadData[0]),
						sender.getName(), String.valueOf(workloadData[16]));
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			tableModel.removeRow(rowSelected);
			tableModel.fireTableDataChanged();

			// Disable DELETE if there are no rows in the table to delete.
			if (tableModel.getRowCount() == 0) {
				// deleteRowButton.setEnabled(false);
			} // Table still contains one or more rows, so highlight (select)
				// the appropriate one.
			else {
				int rowToSelect = rowSelected;

				if (rowSelected >= tableModel.getRowCount()) {
					rowToSelect = rowSelected - 1;
				}

				grid.setRowSelectionInterval(rowToSelect, rowToSelect);
			}
			sender.updateUI();
		}
	}
}
