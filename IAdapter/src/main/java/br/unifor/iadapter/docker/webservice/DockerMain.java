package br.unifor.iadapter.docker.webservice;

import java.net.InetAddress;

import javax.xml.ws.Endpoint;

import br.unifor.iadapter.util.PropertieUtil;

public class DockerMain {

	public static void main(String[] args) {
		try {

			InetAddress thisIp = InetAddress.getLocalHost();
			// String thisIpAddress = thisIp.getHostAddress().toString();
			String thisIpAddress = PropertieUtil.getProperty("ip");
			System.out.println("Listening on " + "http://" + thisIpAddress + ":9999/ws/docker");
			Endpoint.publish("http://" + thisIpAddress + ":9999/ws/docker", new DockerWSImpl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
