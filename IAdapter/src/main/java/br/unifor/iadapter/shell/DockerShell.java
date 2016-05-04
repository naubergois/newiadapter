package br.unifor.iadapter.shell;

import java.io.IOException;

import br.unifor.iadapter.docker.Docker;

public class DockerShell {

	public final String DOCKERCOMMAND = "docker run ";

	public final String DOCKERSTOPCOMMAND = "docker run ";

	public final String DOCKERMEMORYOPTION = " -m ";

	public final String DOCKERCPUSHAREOPTION = " --cpu-shares ";

	private String dockerImage;

	public String getDockerImage() {
		return dockerImage;
	}

	public void setDockerImage(String dockerImage) {
		this.dockerImage = dockerImage;
	}

	public void run(Docker docker, String sourcePort, String destPort) {
		ShellScript shell = new ShellScript();
		try {
			shell.run(DOCKERCOMMAND + DOCKERMEMORYOPTION + docker.getMemory() + "MB " + DOCKERCPUSHAREOPTION
					+ docker.getCpuShare() + " -p " + sourcePort + ":" + destPort + " " + this.getDockerImage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run(Docker docker, String commandLine) {
		ShellScript shell = new ShellScript();
		try {
			String commandLineTemp1 = commandLine.replaceAll("#memory", String.valueOf(docker.getMemory()));

			String commandLineTemp2 = commandLineTemp1.replaceAll("#cpuShare", String.valueOf(docker.getCpuShare()));

			shell.run(commandLineTemp2);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop() {
		ShellScript shell = new ShellScript();
		try {
			shell.run(DOCKERSTOPCOMMAND + DOCKERMEMORYOPTION + this.getDockerImage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
