package br.unifor.iadapter.agent;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class Agent {

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private static final Logger log = LoggingManager.getLoggerForClass();

	private String workload;

	public String getWorkload() {
		return workload;
	}

	public void setWorkload(String workload) {
		this.workload = workload;
	}

	private String name;
	private String ip;
	private String running;

	public String getName() {
		return name;
	}

	public void setName(String name) {

		this.name = name;

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
	}

	private WorkLoadThreadGroup tg;

	public WorkLoadThreadGroup getTg() {
		return tg;
	}

	public void setTg(WorkLoadThreadGroup tg) {
		this.tg = tg;
	}

	public Agent(WorkLoadThreadGroup tg) {

		this.tg = tg;

	}

	public Agent() {

	}

	public static void sinchronize() {
		try {
			while (MySQLDatabase.verifyRunning() > 0)
				;
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}

	}

	public static void sinchronizeFinal() {
		try {
			while (MySQLDatabase.verifyRunningFinal() > 0)
				;
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}

	}

	public void delete() {
		List<Object> listAgent = new ArrayList<Object>();

		listAgent.add(tg.getName() + tg.hashCode());
		listAgent.add("true");
		try {
			listAgent.add(java.net.InetAddress.getLocalHost());
			MySQLDatabase.deleteAgent(listAgent, null);
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

	}

	public void running() {

		List<Object> listAgent = new ArrayList<Object>();
		listAgent.add(tg.getName() + tg.hashCode());
		listAgent.add(new Boolean(true));
		try {
			listAgent.add(java.net.InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		}
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(new Date());
		listAgent.add(s);

		try {
			MySQLDatabase.updateAgentOrCreateIfNotExist(listAgent,
					this.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runningFinal() {

		List<Object> listAgent = new ArrayList<Object>();
		listAgent.add(tg.getName() + tg.hashCode());
		listAgent.add("final");
		try {
			listAgent.add(java.net.InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(new Date());
		listAgent.add(s);

		try {
			MySQLDatabase.updateAgentOrCreateIfNotExist(listAgent,
					this.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
