package br.unifor.iadapter.docker.webservice;

import javax.xml.ws.Endpoint;

public class DockerMain {
	
	public static void main(String[] args) {
		 Endpoint.publish("http://localhost:9999/ws/docker", new DockerWSImpl());
	}

}
