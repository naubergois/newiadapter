package br.unifor.iadapter.docker.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class DockerClient {

	public void startDocker(String ip, String dockerImage, int memory, int cpuShare,String sourcePort,String destPort) {
		try {
			startDocker(ip, dockerImage, String.valueOf(memory), String.valueOf(cpuShare),sourcePort,destPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startDocker(String ip, String dockerImage, String memory, String cpuShare,String sourcePort,String destPort)
			throws MalformedURLException {
		URL url = new URL("http://" + ip + ":9999/ws/docker?wsdl");

		QName qname = new QName("http://webservice.docker.iadapter.unifor.br/", "DockerWSImplService");

		Service service = Service.create(url, qname);

		DockerWSInterface hello = service.getPort(DockerWSInterface.class);

		hello.start(dockerImage, memory, cpuShare,sourcePort,destPort);

	}

	public void stopDocker(String ip, String dockerImage) {
		try {
			URL url = new URL("http://" + ip + ":9999/ws/docker?wsdl");

			QName qname = new QName("http://webservice.docker.iadapter.unifor.br/", "DockerWSImplService");

			Service service = Service.create(url, qname);

			DockerWSInterface hello = service.getPort(DockerWSInterface.class);

			hello.stop(dockerImage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://192.168.25.164:9999/ws/docker?wsdl");

		// 1st argument service URI, refer to wsdl document above
		// 2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://webservice.docker.iadapter.unifor.br/", "DockerWSImplService");

		Service service = Service.create(url, qname);

		DockerWSInterface hello = service.getPort(DockerWSInterface.class);

		hello.start("jloisel/jpetstore6", "10", "10","8080","8080");
		
		Thread.sleep(20000);
		
		hello.stop("jloisel/jpetstore6");

	}

}
