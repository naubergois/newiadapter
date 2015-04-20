package br.unifor.iadapter.threadGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RefreshRowWorkloadAction implements ActionListener {

	private WorkLoadThreadGroupGUI sender;

	public RefreshRowWorkloadAction(WorkLoadThreadGroupGUI aSender) {

		this.sender = aSender;
	}

	public void actionPerformed(ActionEvent e) {

		try {
			JMeterPluginsUtils.modelFromDerby(sender.wtableModel);

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
