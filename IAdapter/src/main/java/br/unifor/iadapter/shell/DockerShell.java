package br.unifor.iadapter.shell;

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
		shell.run(DOCKERCOMMAND + DOCKERMEMORYOPTION + docker.getMemory() + DOCKERCPUSHAREOPTION + docker.getCpuShare()
				+ this.getDockerImage());

	}

	public void stop() {
		ShellScript shell = new ShellScript();
		shell.run(DOCKERSTOPCOMMAND + DOCKERMEMORYOPTION + this.getDockerImage());

	}

}
