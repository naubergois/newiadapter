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

	public void run(Docker docker) {
		ShellScript shell = new ShellScript();
		try {
			shell.run(DOCKERCOMMAND + DOCKERMEMORYOPTION + docker.getMemory() + DOCKERCPUSHAREOPTION + docker.getCpuShare()
					+ this.getDockerImage());
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
