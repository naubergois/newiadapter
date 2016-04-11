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
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;

import org.apache.jmeter.gui.util.PowerTableModel;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;

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
				MySQLDatabase.deleteAgent(
						new ArrayList<Object>(Arrays.asList(workloadData)), null);
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
