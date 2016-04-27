package br.unifor.iadapter.docker.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface DockerWSInterface {

	@WebMethod
	void start(String image, String memory, String cpuShare);

	@WebMethod
	void stop(String image);

}
