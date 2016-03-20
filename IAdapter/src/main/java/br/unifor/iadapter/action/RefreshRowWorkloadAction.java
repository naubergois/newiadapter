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

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroupGUI;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.JMeterPluginsUtils;
import br.unifor.iadapter.util.WorkLoadUtil;

public class RefreshRowWorkloadAction implements ActionListener {

	private WorkLoadThreadGroupGUI sender;

	public RefreshRowWorkloadAction(WorkLoadThreadGroupGUI aSender) {

		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {

		try {

			List<JMeterTreeNode> nodes = FindService
					.searchWorkLoadThreadGroupWithGui();
			WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
					.getTestElement();

			WorkLoadUtil.modelFromDerbyGui(sender.getWtableModel(),
					gp.getName());
			JMeterPluginsUtils.derbyAgentToTableModelRows(sender
					.getWtableModelAgents());

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
