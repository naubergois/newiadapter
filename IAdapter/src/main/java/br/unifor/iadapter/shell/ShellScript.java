package br.unifor.iadapter.shell;

import java.io.IOException;
import java.io.InputStream;

public class ShellScript {

	public int run(String command) {
		ProcessBuilder ps = new ProcessBuilder("bash", "-c", command);
		// use this to capture messages sent to stderr
		ps.redirectErrorStream(true);
		Process shell = null;
		int shellExitStatus = -1;
		try {
			shell = ps.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream shellIn = shell.getInputStream();
		try {
			shellExitStatus = shell.waitFor();
			// logger.info("call exit status:" + shellExitStatus);
			// logger.info("If exit status is not zero then call is not
			// successful. Check log file.");
		} catch (InterruptedException e) {
			// logger.error("error while call" + e);
			e.printStackTrace();
		} // wait for the shell to finish and get the return code
		return shellExitStatus;
	}

}
