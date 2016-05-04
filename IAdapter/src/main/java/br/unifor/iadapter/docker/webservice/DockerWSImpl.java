package br.unifor.iadapter.docker.webservice;

import javax.jws.WebService;

import br.unifor.iadapter.docker.Docker;
import br.unifor.iadapter.shell.DockerShell;

@WebService(endpointInterface = "br.unifor.iadapter.docker.webservice.DockerWSInterface")
public class DockerWSImpl implements DockerWSInterface {

	@Override
	public void start(String image, String memory, String cpuShare, String sourcePort, String destPort) {
		Docker docker = new Docker();
		docker.setMemory(Integer.valueOf(memory));
		docker.setCpuShare(Integer.valueOf(cpuShare));

		DockerShell dockerS = new DockerShell();
		dockerS.setDockerImage(image);

		System.out.println("Running docker image");
		dockerS.run(docker, sourcePort, destPort);

		// TODO Auto-generated method stub

	}

	@Override
	public void stop(String image) {

	}

	@Override
	public void start(String image, String memory, String cpuShare, String commandLine) {
		Docker docker = new Docker();
		docker.setMemory(Integer.valueOf(memory));
		docker.setCpuShare(Integer.valueOf(cpuShare));

		DockerShell dockerS = new DockerShell();
		dockerS.setDockerImage(image);

		System.out.println("Running docker image");
		dockerS.run(docker, commandLine);

	}

}
