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


package br.unifor.iadapter.threadGroup.workload;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;

import org.apache.jmeter.gui.util.PowerTableModel;

import br.unifor.iadapter.jmeter.GraphPanelChart;
import br.unifor.iadapter.util.WorkLoadUtil;

public class WorkLoadTableClicked implements MouseListener {

	private JTable table;
	private WorkLoadThreadGroupGUI group;
	private PowerTableModel model;
	GraphPanelChart chart;

	public WorkLoadTableClicked(JTable table, WorkLoadThreadGroupGUI tg,
			PowerTableModel model, GraphPanelChart chart) {
		this.table = table;
		this.group = tg;
		this.model = model;
		this.chart = chart;
	}

	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e) {

		int row = table.getSelectedRow();

		Object[] workloadData = model.getRowData(row);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		WorkLoad workLoad = WorkLoadUtil.getWorkLoad(new ArrayList(Arrays
				.asList(workloadData)));

		if (workLoad != null) {

			WorkLoadThreadGroupGUI.plotGraph(workLoad, chart);

		}

		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
