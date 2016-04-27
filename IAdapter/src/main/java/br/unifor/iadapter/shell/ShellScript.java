package br.unifor.iadapter.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellScript {

	public int run(String command) throws IOException, InterruptedException {
		Runtime r = Runtime.getRuntime();
		String[] commands = new String[1];
		commands[0] = " docker";

		Process p = r.exec("sh", commands);
		p.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";

		while ((line = b.readLine()) != null) {
			System.out.println(line);
		}

		b.close();
		return 1;
	}

}
