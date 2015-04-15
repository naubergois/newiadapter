package br.unifor.iadapter.threadGroup;

import java.util.ArrayList;
import java.util.List;

public class WorkLoadTests {
	
	private static int currentTest;
	
	public static int getCurrentTest() {
		return currentTest;
	}

	public static void setCurrentTest(int currentTest) {
		WorkLoadTests.currentTest = currentTest;
	}

	private static List<WorkLoad> tests=new ArrayList();

	public static List<WorkLoad> getTests() {
		return tests;
	}

	public static void setTests(List<WorkLoad> tests) {
		WorkLoadTests.tests = tests;
	}

}
