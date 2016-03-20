/*Copyright [2016] [Frncisco Nauber Bernardo Gois]

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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.apache.jmeter.gui.util.PowerTableModel;

public class AddRowAction
        implements ActionListener {

    private JTable grid;
    private PowerTableModel tableModel;
    private JButton deleteRowButton;
    private Object[] defaultValues;
    private JComponent sender;

    public AddRowAction(JComponent aSender, JTable grid, PowerTableModel tableModel, JButton deleteRowButton, Object[] defaultValues) {
        this.grid = grid;
        this.tableModel = tableModel;
        this.deleteRowButton = deleteRowButton;
        this.defaultValues = defaultValues;
        this.sender = aSender;
    }

    public void actionPerformed(ActionEvent e) {
        if (grid.isEditing()) {
            TableCellEditor cellEditor = grid.getCellEditor(grid.getEditingRow(), grid.getEditingColumn());
            cellEditor.stopCellEditing();
        }

        tableModel.addRow(defaultValues);
        tableModel.fireTableDataChanged();

        // Enable DELETE (which may already be enabled, but it won't hurt)
        deleteRowButton.setEnabled(true);

        // Highlight (select) the appropriate row.
        int rowToSelect = tableModel.getRowCount() - 1;
        if (rowToSelect < grid.getRowCount()) {
            grid.setRowSelectionInterval(rowToSelect, rowToSelect);
        }
        sender.updateUI();
    }
}
