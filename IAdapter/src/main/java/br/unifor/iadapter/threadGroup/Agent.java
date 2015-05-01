package br.unifor.iadapter.threadGroup;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Agent {

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
			while (DerbyDatabase.verifyRunning() > 0)
				;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sinchronizeFinal() {
		try {
			while (DerbyDatabase.verifyRunningFinal() > 0)
				;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() {
		List<Object> listAgent = new ArrayList<Object>();
		listAgent.add(tg.getName() + tg.hashCode());
		listAgent.add("true");
		try {
			listAgent.add(java.net.InetAddress.getLocalHost());
			DerbyDatabase.deleteAgent(listAgent, null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void running() {

		List<Object> listAgent = new ArrayList<Object>();
		listAgent.add(tg.getName() + tg.hashCode());
		listAgent.add(new Boolean(true));
		try {
			listAgent.add(java.net.InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DerbyDatabase.updateAgentOrCreateIfNotExist(listAgent,
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

		try {
			DerbyDatabase.updateAgentOrCreateIfNotExist(listAgent,
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
