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
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.WorkLoadUtil;

public class DesactiveRowWorkloadAction implements ActionListener {
	private static final Logger log = LoggingManager.getLoggerForClass();

	WorkLoadThreadGroupGUI sender;

	public DesactiveRowWorkloadAction(WorkLoadThreadGroupGUI sender,
			PowerTableModel model) {

		this.sender = sender;

	}

	public void actionPerformed(ActionEvent e) {
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadThreadGroupWithGui();
		WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
				.getTestElement();

		try {
			MySQLDatabase.desactiveValue(gp.getName());
			WorkLoadUtil.modelFromDerbyGui(sender.getWtableModel(),
					gp.getName());
		} catch (ClassNotFoundException e2) {
			log.error(e2.getMessage());
		} catch (SQLException e2) {
			log.error(e2.getMessage());
		}

	}
}
