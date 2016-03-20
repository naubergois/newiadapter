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
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.ExportCSVWorkloads;
import br.unifor.iadapter.util.FindService;

public class ExportPositiveWorkloadAction implements ActionListener {
	private static final Logger log = LoggingManager.getLoggerForClass();

	public ExportPositiveWorkloadAction() {

	}

	public void actionPerformed(ActionEvent e) {
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadThreadGroupWithGui();
		WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
				.getTestElement();

		try {
			List<WorkLoad> list = MySQLDatabase.listAllWorkLoads(gp.getName());

			ExportCSVWorkloads export = new ExportCSVWorkloads();
			String content = export.exportPositive(list);

			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("/home/me/Documents"));
			int retrival = chooser.showSaveDialog(null);
			if (retrival == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter fw = new FileWriter(chooser.getSelectedFile()
							+ ".csv");
					fw.write(content);
					fw.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (ClassNotFoundException e2) {
			log.error(e2.getMessage());
		} catch (SQLException e2) {
			log.error(e2.getMessage());
		}

	}
}
