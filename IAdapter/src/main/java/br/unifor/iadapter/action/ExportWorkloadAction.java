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

public class ExportWorkloadAction implements ActionListener {
	private static final Logger log = LoggingManager.getLoggerForClass();

	public ExportWorkloadAction() {

	}

	public void actionPerformed(ActionEvent e) {
		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadThreadGroupWithGui();
		WorkLoadThreadGroup gp = (WorkLoadThreadGroup) nodes.get(0)
				.getTestElement();

		try {
			List<WorkLoad> list = MySQLDatabase.listAllWorkLoads(gp.getName());

			ExportCSVWorkloads export = new ExportCSVWorkloads();
			String content = export.export(list);

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
