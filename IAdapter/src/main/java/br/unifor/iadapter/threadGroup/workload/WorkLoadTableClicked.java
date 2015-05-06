package br.unifor.iadapter.threadGroup.workload;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;

import org.apache.jmeter.gui.util.PowerTableModel;

import br.unifor.iadapter.jmeter.GraphPanelChart;
import br.unifor.iadapter.util.JMeterPluginsUtils;

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

		WorkLoad workLoad = JMeterPluginsUtils.getWorkLoad(new ArrayList(Arrays
				.asList(workloadData)));

		WorkLoadThreadGroupGUI.plotGraph(workLoad, chart);

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
