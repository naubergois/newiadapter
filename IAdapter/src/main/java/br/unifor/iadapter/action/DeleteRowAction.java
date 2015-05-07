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